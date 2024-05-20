import csv
import scrapy
from scrapy import Request
import json
import time
from PyDeepLX import PyDeepLX
import tldextract
import requests
import json

url = "http://translate.codeai.net.cn/api/index/translate?token=L0905eYhctBbeZuQYq8H"
headers = {
    'Content-Type': 'application/json'
}
# 将 cache_dir 参数设置为 False
extractor = tldextract.TLDExtract(cache_dir=False)

class Museum11Spider(scrapy.Spider):
    name = "museum11"
    allowed_domains = ["api.smb.museum"]
    start_urls = ['https://recherche.smb.museum/?language=de&limit=15&offset=0&sort=relevance&controls=none&conditions=AND%2BgeographicalReferences%2BChina']
    def parse(self, response):
        #for page in range(312):
        for page in range(1):
            url = f'https://api.smb.museum/search/?lang=de&limit=15&offset={page*15}&sort=-_score%2C-attachments%2C-%40lastSynced'
            payload = {
                "q_advanced": [
                    {
                        "field": "geographicalReferences",
                        "operator": "AND",
                        "q": "China"
                    }
                ]
            }
            yield scrapy.Request(
                url,
                method='POST',
                body=json.dumps(payload),
                headers={'Content-Type': 'application/json'},
                callback=self.parse_results
            )

    def parse_results(self, response):
        json_data =response.json()
        for art in json_data['objects']:
             temp ={}
             if 'title' in art:
                 temp['artifactName'] = art['title'].replace('\n', '').replace('\r', '')
             elif 'technicalTerm' in art:
                 temp['artifactName'] = art['technicalTerm'].replace('\n', '').replace('\r', '')

             kkeyword =temp['artifactName']
             payload = json.dumps({
                 "keywords": kkeyword,
                 "targetLanguage": "zh-cn"
             })
             response1 = requests.request("POST", url, headers=headers, data=payload)
             data = response1.json()
             temp['artifactName1']=data['data']['text']

             #temp['artifactName1'] = PyDeepLX.translate(temp['artifactName'], targetLang="ZH")

             temp['country'] ="中国"

             dating_list = art.get('dating', None)
             if dating_list is not None:
                 temp['relicTime'] = ' '.join(str(date) for date in dating_list)
                 payload = json.dumps({
                     "keywords": temp['relicTime'],
                     "targetLanguage": "zh-cn"
                 })
                 response1 = requests.request("POST", url, headers=headers, data=payload)
                 data = json.loads(response1.text)
                 if 'text' in data['data']:
                     temp['relicTime1'] = data['data']['text'].replace('\n', '').replace('\r', '')

             material_list = art.get('materialAndTechnique', None)
             if material_list is not None:
                 temp['material'] = ' '.join(material_list)
                 payload = json.dumps({
                     "keywords": temp['material'],
                     "targetLanguage": "zh-cn"
                 })
                 response1 = requests.request("POST", url, headers=headers, data=payload)
                 data = json.loads(response1.text)
                 if 'text' in data['data']:
                     temp['material1'] = data['data']['text'].replace('\n', '').replace('\r', '')

             temp['library'] = art.get('collection',None)
             if temp['library']:
                 payload = json.dumps({
                     "keywords": temp['library'],
                     "targetLanguage": "zh-cn"
                 })
                 response1 = requests.request("POST", url, headers=headers, data=payload)
                 data = json.loads(response1.text)
                 if 'text' in data['data']:
                     temp['library1'] = data['data']['text'].replace('\n', '').replace('\r', '')

             size_list = art.get('dimensionsAndWeight', None)
             if size_list is not None:
                 temp['size']=','.join(size_list)
                 payload = json.dumps({
                     "keywords": temp['size'],
                     "targetLanguage": "zh-cn"
                 })
                 response1 = requests.request("POST", url, headers=headers, data=payload)
                 data = json.loads(response1.text)
                 if 'text' in data['data']:
                     temp['size1'] = data['data']['text'].replace('\n', '').replace('\r', '')

             temp['description']=art.get('description', None)
             if temp['description'] is not None:
                 payload = json.dumps({
                     "keywords": temp['size'],
                     "targetLanguage": "zh-cn"
                 })
                 response1 = requests.request("POST", url, headers=headers, data=payload)
                 data = json.loads(response1.text)
                 if 'text' in data['data']:
                     temp['description1'] = data['data']['text'].replace('\n', '').replace('\r', '')
             # #de_list = art.get('description', None)
             # # if de_list is not None:
             #    #temp['description']=''.join(de_list)
             #    payload = json.dumps({
             #          "keywords": temp['description'],
             #          "targetLanguage": "zh-cn"
             #      })
             #      response1 = requests.request("POST", url, headers=headers, data=payload)
             #      data = json.loads(response1.text)
             #      if 'text' in data['data']:
             #        temp['description1'] = data['data']['text'].replace('\n', '').replace('\r', '')

             temp['moreUrl'] =art['permalink']
             id=art['id']
             # print(f'https://api.smb.museum/search/{id}/?projection=full')
             yield Request(
                 url=f'https://api.smb.museum/search/{id}/?projection=full', callback=self.parse_detail,
                 cb_kwargs={'item': temp}
             )
    def parse_detail(self, response, **kwargs):
        temp = kwargs['item']
        json_data1 = response.json()
        #temp['artifactName1'] = PyDeepLX.translate(temp['artifactName'], targetLang="ZH")
        temp['imageUrl']=str.replace(json_data1['assets'][0]['thumbnail'],'480x480','2400x2400')
        yield temp
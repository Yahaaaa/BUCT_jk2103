import scrapy
from scrapy import Request
from tldextract import TLDExtract
import requests
import json
url = "http://translate.codeai.net.cn/api/index/translate?token=L0905eYhctBbeZuQYq8H"
headers = {
    'Content-Type': 'application/json'
}
extractor = TLDExtract(cache_dir=False)
class Museum12Spider(scrapy.Spider):
    name = "museum12"
    allowed_domains = ["rijksmuseum.nl"]

    def start_requests(self):
        #for page in range(1,101):
        for page in range(1, 101):
            yield Request(url=f'https://www.rijksmuseum.nl/en/search?p={page}&ps=12&place=China&st=Objects')

    def parse(self, response, **kwargs):
        node_list = response.xpath("//*[@id='infinite-scroll-page-results']/figure")
        for node in node_list:
            temp={}
            # temp['artifactName']=node.xpath(".//div/figcaption/h2/a//text()").extract_first()
            # if temp['artifactName']:
            #     temp['artifactName'] = temp['artifactName'].strip()  # 去除前后空格
            temp['country'] = "中国"
            temp['library'] = "阿姆斯特丹国立博物馆"
            temp['link'] ="https://www.rijksmuseum.nl/" + node.xpath(".//a[@class='link-reverse']/@href").extract_first()
            temp['imageurl'] = node.xpath(".//img/@data-src").extract_first();
            if temp['imageurl']:
                temp['imageurl'] = str.replace(node.xpath(".//img/@data-src").extract_first(), 'w200', 'w1000');
            yield Request(
                url=temp['link'], callback=self.parse_detail,
                cb_kwargs={'item': temp}
            )

    def parse_detail(self,response,**kwargs):
        temp=kwargs['item']

        temp['size']=response.xpath('//*[@id="object-data-section"]/div[2]/section/article[3]/div/div[3]/p//text()').extract_first()
        temp['artifactName'] = response.xpath('//*[@id="object-data-section"]/div[2]/section/article[1]/div/div[1]/p//text()').extract_first()
        temp['relicTime'] = response.xpath('//*[@id="object-data-section"]/div[2]/section/article[2]/div/div[3]/p//text()').extract_first()
        temp['material'] = response.xpath('//*[@id="object-data-section"]/div[2]/section/article[3]/div/div[1]/ul/li/a//text()').extract_first()
        temp['description'] = response.xpath('//*[@id="object-data-section"]/div[2]/section/article[1]/div/div[4]/p//text()').extract_first()

        yield temp

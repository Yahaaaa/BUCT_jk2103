import scrapy
import requests
import scrapy
from scrapy import Request
from scrapy import Selector
from spider_jk2103.items import artifact
Dynasty = {
    "dynasty1": "新石器时代","dynasty2": "夏","dynasty3": "商","dynasty4": "西周","dynasty5": "东周","dynasty6": "春秋","dynasty7": "战国","dynasty8": "汉","dynasty9": "北魏","dynasty10": "东魏","dynasty11": "北齐","dynasty12": "隋代","dynasty13": "唐","dynasty14": "五代","dynasty15": "北宋","dynasty16": "辽","dynasty17": "金","dynasty18": "蒙元时代","dynasty19": "金—元","dynasty20": "元","dynasty21": "元—明","dynasty22": "明","dynasty23": "清","dynasty24": "现代"
}
Material={
     "material1":"铜","material2":"玉","material3":"石","material4":"纸","material5":"青铜","material6":"陶","material7":"瓷","material8":"绫","material9":"绫本","material10":"绢本","material11":"鎏金","material12":"银","material13":"玻璃","material14":"木"
}


class Museum9Spider(scrapy.Spider):
    name = "museum9"
    allowed_domains = ["shanximuseum.com"]
    #start_urls = ["https://www.shanximuseum.com/sx/collection/collection_list?offset=0&count=9"]
    def start_requests(self):
        for page in range(38):
            yield Request(url=f'https://www.shanximuseum.com/sx/collection/collection_list?offset={page*9}&count=9')
    def parse(self, response,**kwargs):
        json_data = response.json()
        for art in json_data['data']['list']:
            temp = {}
            temp['artifactName']=art['title']
            temp['country'] = "中国"
            temp['library'] = "山西博物馆"
            temp['relicTime'] = Dynasty[art['dynasty']]
            temp['material'] = Material[art['material']]
            #temp['imageUrl'] = response.urljoin(art['big_image'])
            temp['moreUrl'] = art['fullurl']
            yield Request(
                url=art['fullurl'],callback=self.parse_detail,
                cb_kwargs={'item':temp}
            )
    def parse_detail(self,response,**kwargs):
        temp=kwargs['item']
        # intros = response.xpath('//div[@class="cont"]//text()').getall()
        # intros=[intro.strip()for intro in intros] # 去除空格
        # temp['description']=''.join(intros)
        temp['imageUrl']=response.urljoin(response.xpath('//*[@class="zoomView"]/a/@href').extract_first())
        intros = response.xpath('//div[@class="cont"]//text()').getall()
        intros = [intro.strip() for intro in intros if intro.strip()]  # 去除空行并去除每个文本元素两侧的空格
        if intros:
            temp['size'] = intros[0]  # 存储首行
            temp['description'] = intros[1:]  # 存储剩余行

        yield temp
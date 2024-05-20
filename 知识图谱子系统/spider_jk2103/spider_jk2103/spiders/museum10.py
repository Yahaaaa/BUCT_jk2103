import csv
import re
import scrapy

class HbwwSpider(scrapy.Spider):
    name = "hbww"
    start_urls = ["https://hbsbwg.cjyun.org/diancang/index.html"]
    items = {}
    def start_requests(self):
        for url in self.start_urls:
            yield scrapy.Request(url, callback=self.parse)

    def parse(self, response, *kwargs):
        # 提取文物类别的URL
        category_urls = response.css('#collection2 li a::attr(href)').getall()
        for category_url in category_urls:
            full_category_url = response.urljoin(category_url)
            yield scrapy.Request(full_category_url, callback=self.parse_category)

    def parse_category(self, response):
        #如果是指定分类的网址，则进行特殊处理以提取文物详情页的url
        if response.url == "http://hbsbwg.cjyun.org/zgzb/index.html":
            detail_urls = response.css('ul.divcenter li a::attr(href)').getall()
            for detail_url in detail_urls:
                full_detail_url = response.urljoin(detail_url)
                yield scrapy.Request(full_detail_url, callback=self.parse_detail)
        if response.url == "http://hbsbwg.cjyun.org/gjsb/index.html":
            return
        else:
            # 提取当前分类的所有页面的文物详情页的URL
            detail_urls = response.css('.list_photo::attr(href)').getall()
            for detail_url in detail_urls:
                full_detail_url = response.urljoin(detail_url)
                yield scrapy.Request(full_detail_url, callback=self.parse_detail)
            # 提取分页信息
            next_page_links = response.css('#listpage a')
            for next_page_link in next_page_links:
                next_page_url = next_page_link.attrib.get('href')
                if next_page_url and 'javascript:void(0);' not in next_page_url:
                    full_next_page_url = response.urljoin(next_page_url)
                    yield scrapy.Request(full_next_page_url, callback=self.parse_category)
            # 提取下一个分类的URL
            next_category_link = response.xpath('//a[contains(text(), "下一页")]/@href').get()
            if next_category_link:
                next_category_url = response.urljoin(next_category_link)
                yield scrapy.Request(next_category_url, callback=self.parse_category)

    def parse_detail(self, response):
        # 提取文物名称
        artifact_name = response.css('#mainarticle h2::text').get()

        # 提取文物介绍，去除空白文本和多余的逗号，并移除 &nbsp;
        artifact_description = [
            re.sub(r"&nbsp;", " ", desc.strip().replace(',', '').replace('\xa0', ' ')) for desc in
            response.css('.article_content ::text').getall() if desc.strip()
        ]
        # 格式化文物介绍
        artifact_description = ' '.join(artifact_description)

        # 提取文物图片
        artifact_image = response.css('#mainarticle img::attr(src)').get()

        # 提取文物类别
        artifact_category = response.css('#listcate a:last-of-type::text').get().strip() if response.css('#listcate') else None

        # 提取时间信息（朝代）
        artifact_time = self.extract_time(response.css('.article_content p::text').get())

        # 提取尺寸
        artifact_size = self.extract_size(artifact_description)
        # 带括号的尺寸
        artifact_size_with_brackets = self.extract_size_with_brackets(artifact_description)

        # 如果文物名称中有尺寸信息，则添加到尺寸属性中
        size_in_name_match = re.search(r'（([\d.]+cm[*×][\d.]+cm[*×][\d.]+cm)）', artifact_name)
        if size_in_name_match:
            size_in_name = size_in_name_match.group(1)
            if artifact_size:
                artifact_size += ', ' + size_in_name
            else:
                artifact_size = size_in_name

        # 合并尺寸信息
        if artifact_size_with_brackets:
            if artifact_size:
                artifact_size += ', ' + artifact_size_with_brackets
            else:
                artifact_size = artifact_size_with_brackets
        # 将文物信息添加到对应类别的列表中
        if artifact_category not in self.items:
            self.items[artifact_category] = []
        self.items[artifact_category].append({
            'artifact_name': artifact_name,
            'artifact_country': '中国',
            'artifact_library': '湖北博物馆',
            'artifact_time': artifact_time,
            'artifact_size': artifact_size,
            'artifact_category': artifact_category,
            'artifact_image': artifact_image,
            'artifact_url': response.url,
            'artifact_description': artifact_description,
        })

    def extract_time(self, description):
        #朝代匹配模式
        # dynasty_patterns = (r'(?:西周|西周晚期|春秋|春秋晚期|春秋\（楚国\）|战国|战国早期|战国中晚期|秦|汉|西汉|西晋|晋|南朝|隋|唐|宋|北宋|南宋|元|明|明 宣德|明宣德|'
        #                     r'清|清 道光|清道光|清 嘉庆|清嘉庆|清 康熙|清康熙|清 雍正|清雍正|清 同治|清同治|清 乾隆|清乾隆|清 光绪|清光绪)')
        #明 崇祯 西周晚期 不行，手动添加
        dynasty_patterns = (
            r'(?:西周晚期|西周|春秋(?:晚期|\（楚国\）)?|战国(?:早期|中晚期)?|秦|汉|西汉|西晋|晋|南朝|隋|唐|宋|北宋|南宋|元|明\s崇祯|明\s宣德|清(?:\s?+(?:道光|嘉庆|康熙|雍正|同治|乾隆|光绪))?)')

        # 使用正则表达式匹配文物介绍开头的朝代信息
        match = re.search(dynasty_patterns, description)
        if match:
            return match.group()
        else:
            return None

    def extract_size_with_brackets(self, description):
        # 尺寸匹配模式，用于匹配带括号的尺寸信息
        size_patterns_with_brackets = r'尺寸：（(.*?)）'
        matches = re.findall(size_patterns_with_brackets, description)
        if matches:
            sizes = [match.strip() + 'cm' for match in matches]
            return '尺寸：' + ', '.join(sizes)
        else:
            return None

    def extract_size(self, description):
        # 尺寸匹配模式
        #能提取出长宽高文字，但没有单位跟着的提取不出来
        #size_patterns = r'(?:尺寸|长|宽|头宽|高|直径|通高|通直|厚|口径|腹径|底径|孔径|肩径|直径|子径|盖径|两耳间距|援长|内长|阑长|胡长|刃宽|重)[：:]?\s*(?:\d+(?:\.\d+)?(?:[-—～~×x*]\d+(?:\.\d+)?)?\s*(?:cm|毫米|mm|厘米|g))'
        #原来没有尺寸描述信息，但是没有单位的可以提取,现在可以了，区别在：。。。。。。重)[：:]?\s*(\d+(?:\.\d+)少了 ?:
        #厚/ 手动添加
        size_patterns = (r'(?:尺寸|底座|'
                         r'长|全长|最长|钲全长|钲下部长|残长|钲下部|钲上部长|钩长|钲上部|修长|舞长|甬长|剑身长|链长|钟长|钮长|面长|通长|布长|口长|流长|边长|口边长|銎口长|鼓身长|柄通长|戈长|盒长|勺身长|龛长|流尾长|展开全长|钟架长|剑长|'
                         r'宽|口宽|中宽|钮宽|最大口宽|底宽|端宽|最大底宽|器身宽|头宽|最宽|最宽处|舞宽|腹宽|最大腹宽|钩头宽|勺宽|屏宽|座宽|援宽|通宽|袋足宽|肩宽|尾宽|匕宽|连耳宽|中间宽|'
                         r'高|均高|圈足高|金爵高|镯高|镀金银托盘高|蛇俑高|甑高|全器高约|座高|鬲高|鼠俑高|银爵高|金壶高|通高|盖高|枚高|锺高|碗高|冠高|盏高|身高|端高|盏盘高|勺高|尊高|注子高|纽高|'
                         r'厚|最大厚|下厚|壁厚|钮厚|缘厚|厚约|'
                         r'口径|最大口径|鞘长|径|身径|口长径|銎径|勺径|上盘径|中盘径|底盘径|外径|内径|灯盘径|灯盘口径|座径|腹径|底径|低径|孔径|肩径|直径|弧尖直径|弧底直径|直经|柄径|子径|筒径|圈径|炉口径|托盘口径|盖径|圈足径|圈足底径|圆足径|勺口径|灯口径|盘口径|砚口径|盂口径|盂底径|'
                         r'耳距|两耳间距|两端距|内角间距|援长|内长|阑长|胡长|刃宽|重|总重|共重|腹深|舞修|铣间|残上|复原后长|复原通长|复原高|阑穿|分别长|分别重|Paper|Silk|Gold dusted paper|纸本|绢本|浅绛纸|泥金笺|金笺|绢彩笺|绫本|Gilded Paper|alum-treated paper)'
                         r'[：:]?\s*(?:\d+(?:\.\d+)?(?:[-—～~×x*]\d+(?:\.\d+)?)?(?:\s?cm|公分|毫米|mm|厘米|g)?)')

        # 使用正则表达式匹配文物介绍中的尺寸信息
        matches = re.findall(size_patterns, description)
        if matches:
            return ', '.join(matches)
        else:
            return None

    def close(self, reason):
        # 写入 CSV 文件
        csv_filename = "hbww.csv"
        with open(csv_filename, 'w', newline='', encoding='utf-8') as csvfile:
            fieldnames = [ 'artifact_name', 'artifact_country', 'artifact_library',
                          'artifact_time', 'artifact_size', 'artifact_category',
                          'artifact_image', 'artifact_url', 'artifact_description']
            writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
            writer.writeheader()
            for category, items in self.items.items():
                for item in items:
                    writer.writerow(item)

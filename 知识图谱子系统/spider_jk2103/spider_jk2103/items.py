# Define here the models for your scraped items
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/items.html

import scrapy


class SpiderJk2103Item(scrapy.Item):

    # define the fields for your item here like:
    # name = scrapy.Field()
    pass

class artifact(scrapy.Item):
    id=scrapy.Field();
    artifactName=scrapy.Field();
    artifactName1 = scrapy.Field();
    country = scrapy.Field();
    relicTime=scrapy.Field();
    relicTime1 = scrapy.Field();
    material =scrapy.Field();
    material1 = scrapy.Field();
    library=scrapy.Field();
    library1 = scrapy.Field();
    size=scrapy.Field();
    size1 = scrapy.Field();
    description=scrapy.Field();
    description1 = scrapy.Field();
    imageUrl=scrapy.Field();
    moreUrl=scrapy.Field();

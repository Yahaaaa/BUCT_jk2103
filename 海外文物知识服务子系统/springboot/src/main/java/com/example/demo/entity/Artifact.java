package com.example.demo.entity;

import java.util.List;

public class Artifact extends Entity {
    Integer id;//文物id 主键
//    String artifactName;   //文物名称
    String artifactNameChinese; //文物中文名称
//    String country;    //国家
    String relicTime;      //文物时代
//    String library; //博物馆
    String libraryChinese; //博物馆中文名
//    String material;        //类别
    String materialChinese;    //类别中文
//    String size;  //文物尺寸
    String sizeChinese;  //文物尺寸中文
//    String description;  //描述
    String descriptionChinese; //中文描述

    String imageUrl;    //图片原地址
//    String moreUrl;    //文物原地址
    public List<Comment> comments;//对应评论
//    id,relicTime,library_Chinese,imageUrl, material_Chinese,artifactName_Chinese, size_Chinese, description_Chinese

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }





    public String getArtifactNameChinese() {
        return artifactNameChinese;
    }

    public void setArtifactNameChinese(String artifactNameChinese) {
        this.artifactNameChinese = artifactNameChinese;
    }



    public String getRelicTime() {
        return relicTime;
    }

    public void setRelicTime(String relicTime) {
        this.relicTime = relicTime;
    }

    public String getMaterialChinese() {
        return materialChinese;
    }

    public void setMaterialChinese(String materialChinese) {
        this.materialChinese = materialChinese;
    }

    public String getLibraryChinese() {
        return libraryChinese;
    }

    public void setLibraryChinese(String libraryChinese) {
        this.libraryChinese = libraryChinese;
    }

    public String getSizeChinese() {
        return sizeChinese;
    }

    public void setSizeChinese(String sizeChinese) {
        this.sizeChinese = sizeChinese;
    }

    public String getDescriptionChinese() {
        return descriptionChinese;
    }

    public void setDescriptionChinese(String descriptionChinese) {
        this.descriptionChinese = descriptionChinese;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


    @Override
    public String toString() {
        return "Artifact{" +
                "id=" + id +
                ", artifactNameChinese='" + artifactNameChinese + '\'' +
                ", relicTime='" + relicTime + '\'' +
                ", libraryChinese='" + libraryChinese + '\'' +
                ", materialChinese='" + materialChinese + '\'' +
                ", sizeChinese='" + sizeChinese + '\'' +
                ", descriptionChinese='" + descriptionChinese + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", comments=" + comments +
                '}';
    }
}

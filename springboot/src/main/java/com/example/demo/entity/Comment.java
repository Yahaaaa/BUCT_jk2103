package com.example.demo.entity;



public class Comment extends Entity {
    Integer id;//评论id
    Integer userId;//用户id
    Integer artifactId;//文物id
    String content;//内容
    String createTime;//评论时间
    String userName;
    String avatarUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(Integer artifactId) {
        this.artifactId = artifactId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId=" + userId +
                ", artifactId=" + artifactId +
                ", content='" + content + '\'' +
                ", createTime='" + createTime + '\'' +
                ", userName='" + userName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}

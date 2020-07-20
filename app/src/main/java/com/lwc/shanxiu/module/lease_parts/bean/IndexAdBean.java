package com.lwc.shanxiu.module.lease_parts.bean;


import java.io.Serializable;

public class IndexAdBean implements Serializable {

    /**
     * urlType : 0
     * leaseId : 1585189354342SB
     * createTime : 2020-03-26 10:22:34
     * leaseDetail : 详情
     * isValid : 1
     * leaseTitle : 标题
     * leaseUrl : 跳转地址
     * imageLocalhost : 1
     * sn : 0
     * leaseImageUrl : 地址
     */

    private int urlType;
    private String id;
    private String createTime;
    private String detail;
    private int isValid;
    private String title;
    private String url;
    private int imageLocalhost;  //显示位置 1 上 2 下  3 左  4 右
    private int sn;
    private String imageUrl;

    public int getUrlType() {
        return urlType;
    }

    public void setUrlType(int urlType) {
        this.urlType = urlType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getImageLocalhost() {
        return imageLocalhost;
    }

    public void setImageLocalhost(int imageLocalhost) {
        this.imageLocalhost = imageLocalhost;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

package com.lwc.shanxiu.module.message.bean;

/**
 * Created by YouongeTao on 2019/8/28.
 * email: 2276559259@qq.com & youngetao@gmail.com
 */

public class PublishAndRequestBean {

    private String knowledgeId;
    private String knowledgeImage; //图片(多张逗号分隔)
    private Integer knowledgeImageType; //图片类型(1：单图；2：多图；3：视频)
    private String createTime; //创建时间
    private String knowledgeTitle;//标题
    private String knowledgePaper;//摘要
    private String knowledgeDetails;//文章详情
    private String knowledgeQuesion;//提问详情
    private String maintenanceName;//工程师名称
    private Integer browseNum; //浏览次数
    private Integer type;//类型 （1、发表 2、提问）
    private Integer status; //（1、处理中 2、通过 3、不通过）
    private String reason; // 不通过原因
    private String hasAward; // 是否能领取奖励
    private String activityId; // 活动id

    public String getKnowledgeId() {
        return knowledgeId;
    }

    public void setKnowledgeId(String knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    public String getKnowledgeImage() {
        return knowledgeImage;
    }

    public void setKnowledgeImage(String knowledgeImage) {
        this.knowledgeImage = knowledgeImage;
    }

    public Integer getKnowledgeImageType() {
        return knowledgeImageType;
    }

    public void setKnowledgeImageType(Integer knowledgeImageType) {
        this.knowledgeImageType = knowledgeImageType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getKnowledgeTitle() {
        return knowledgeTitle;
    }

    public void setKnowledgeTitle(String knowledgeTitle) {
        this.knowledgeTitle = knowledgeTitle;
    }

    public String getKnowledgePaper() {
        return knowledgePaper;
    }

    public void setKnowledgePaper(String knowledgePaper) {
        this.knowledgePaper = knowledgePaper;
    }

    public Integer getBrowseNum() {
        return browseNum;
    }

    public void setBrowseNum(Integer browseNum) {
        this.browseNum = browseNum;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getKnowledgeDetails() {
        return knowledgeDetails;
    }

    public void setKnowledgeDetails(String knowledgeDetails) {
        this.knowledgeDetails = knowledgeDetails;
    }

    public String getKnowledgeQuesion() {
        return knowledgeQuesion;
    }

    public void setKnowledgeQuesion(String knowledgeQuesion) {
        this.knowledgeQuesion = knowledgeQuesion;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMaintenanceName() {
        return maintenanceName;
    }

    public void setMaintenanceName(String maintenanceName) {
        this.maintenanceName = maintenanceName;
    }

    public String getHasAward() {
        return hasAward;
    }

    public void setHasAward(String hasAward) {
        this.hasAward = hasAward;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
}

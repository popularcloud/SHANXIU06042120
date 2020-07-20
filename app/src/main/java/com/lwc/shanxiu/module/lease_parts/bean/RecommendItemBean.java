package com.lwc.shanxiu.module.lease_parts.bean;

public class RecommendItemBean {

    /**
     * recommendId : 1
     * isValid : 1
     * recommendName : 精选推荐
     */

    private String recommendId;
    private int isValid;
    private String recommendName;

    public RecommendItemBean(){}

    public RecommendItemBean(String recommendName){
        this.recommendName = recommendName;
    }

    public String getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(String recommendId) {
        this.recommendId = recommendId;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public String getRecommendName() {
        return recommendName;
    }

    public void setRecommendName(String recommendName) {
        this.recommendName = recommendName;
    }
}

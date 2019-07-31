package com.lwc.shanxiu.bean;


import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * 何栋
 * 2017-12-06
 */
public class ActivityBean extends DataSupport implements Serializable {

    private String activityId;//": "123",                    //活动ID
    private String conditionId;//": "123",                  //触发ID
    private String conditionName;//": "报修",              //触发名称
    private String conditionIndex;//": "order/saveOrder",     //触发标示
    private String assignCrowd;//": 2,                     //活动指定人群 （1.所有 2.用户 3.工程师）
    private String activityName;//": "双十二红包雨"         //活动名称
    private String ruleUrl;//活动规则连接

    public String getRuleUrl() {
        return ruleUrl;
    }

    public void setRuleUrl(String ruleUrl) {
        this.ruleUrl = ruleUrl;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getConditionId() {
        return conditionId;
    }

    public void setConditionId(String conditionId) {
        this.conditionId = conditionId;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

    public String getConditionIndex() {
        return conditionIndex;
    }

    public void setConditionIndex(String conditionIndex) {
        this.conditionIndex = conditionIndex;
    }

    public String getAssignCrowd() {
        return assignCrowd;
    }

    public void setAssignCrowd(String assignCrowd) {
        this.assignCrowd = assignCrowd;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
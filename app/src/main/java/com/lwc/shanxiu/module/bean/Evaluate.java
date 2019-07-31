package com.lwc.shanxiu.module.bean;

import java.io.Serializable;

/**
 * @author 何栋
 * @date 2018/7/12 0012
 * @email 294663966@qq.com
 */
public class Evaluate implements Serializable{

    /**
     * createTime : 2018-07
     * synthesizeGrade : 5
     * commentContent : 回来
     */
    private String createTime;//时间
    private int synthesizeGrade;//评价星级
    private String commentContent;//评价内容
    private String orderContactName;//用户名称

    public String getOrderContactName() {
        return orderContactName;
    }

    public void setOrderContactName(String orderContactName) {
        this.orderContactName = orderContactName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getSynthesizeGrade() {
        return synthesizeGrade;
    }

    public void setSynthesizeGrade(int synthesizeGrade) {
        this.synthesizeGrade = synthesizeGrade;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}

package com.lwc.shanxiu.module.message.bean;

import java.io.Serializable;

/**
 * Created by 何栋 on 2018/03/25.
 * 294663966@qq.com
 * 我的消息
 */
public class MyMsg implements Serializable {

    private String createTime;       //创建时间
    private String messageTitle;     //消息标题
    private String objectId;         //对象ID
    private String messageContent;  //消息内容
    private String messageType;     //消息类型 1.系统消息 2.资讯消息 3.活动消息 4.个人消息
    private String messageImage;    //消息图片
    private String clickUrl;          //点击跳转的连接
    private boolean hasMessage;//是否有未读消息

    public boolean getHasMessage() {
        return hasMessage;
    }

    public void setHasMessage(boolean hasMessage) {
        this.hasMessage = hasMessage;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageImage() {
        return messageImage;
    }

    public void setMessageImage(String messageImage) {
        this.messageImage = messageImage;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }
}

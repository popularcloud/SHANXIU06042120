package com.lwc.shanxiu.module.message.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by 何栋 on 2018/03/25.
 * 294663966@qq.com
 * 检查是否有新消息
 */
public class HasMsg extends DataSupport implements Serializable {

    private String type;     //消息类型 1.系统消息 2.资讯消息 3.活动消息 4.个人消息
    private boolean hasMessage;//是否有未读消息

    public boolean getHasMessage() {
        return hasMessage;
    }

    public void setHasMessage(boolean hasMessage) {
        this.hasMessage = hasMessage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}

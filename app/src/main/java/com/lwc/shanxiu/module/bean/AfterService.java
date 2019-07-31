package com.lwc.shanxiu.module.bean;

import java.io.Serializable;

/**
 * @author 何栋
 * @version 1.0
 * @date 2017/6/15 08:31
 * @email 294663966@qq.com
 * 设备
 */
public class AfterService implements Serializable {
    //1:正常 2：申请返修 3：接受返修 4：开始处理 5：完成待确认 ',
    public static final int STATUS_GUOQI = 0;
    public static final int STATUS_ZHENGCHANG = 1;
    public static final int STATUS_YISHENQING = 2;
    public static final int STATUS_YIJIESHOU = 3;
    public static final int STATUS_CHULI = 4;
    public static final int STATUS_WANGCHENGDAIQUEREN = 5;
    private int statusId;// ": "状态ID",                    //状态ID
    private String processTitle;//  ": "进程标题",               //进程标题
    private String processContent;//  ": "进程内容",            //进程内容
    private String createTime;//  ": "创建时间",                //创建时间
    private String comment;//  ": "如果状态为评论状态则有",
    private int warrantyState;

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getProcessTitle() {
        return processTitle;
    }

    public void setProcessTitle(String processTitle) {
        this.processTitle = processTitle;
    }

    public String getProcessContent() {
        return processContent;
    }

    public void setProcessContent(String processContent) {
        this.processContent = processContent;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getWarrantyState() {
        return warrantyState;
    }

    public void setWarrantyState(int warrantyState) {
        this.warrantyState = warrantyState;
    }
}

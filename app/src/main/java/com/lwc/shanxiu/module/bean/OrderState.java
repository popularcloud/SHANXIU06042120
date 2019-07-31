package com.lwc.shanxiu.module.bean;

/**
 * @author 何栋
 * @version 2.0
 * @date 2017/3/14 16:54
 * @email 294663966@qq.com
 * 订单状态
 */
public class OrderState {

    private int statusId;// ": "状态ID",                    //状态ID
    private String processTitle;//  ": "进程标题",               //进程标题
    private String processContent;//  ": "进程内容",            //进程内容
    private String createTime;//  ": "创建时间",                //创建时间
    private Comment comment;//  ": "如果状态为评论状态则有",
    private String maintenanceId;//订单接单人ID
    private String waitMaintenanceId;//订单待结单人ID
    private String orderType;// ": "订单类型",              //订单类型(1.个人订单 2.政府订单  3.厂家售后）
    private int hasAward;//是否有红包
    private String exampleId;

    public String getExampleId() {
        return exampleId;
    }

    public void setExampleId(String exampleId) {
        this.exampleId = exampleId;
    }

    public int getHasAward() {
        return hasAward;
    }

    public void setHasAward(int hasAward) {
        this.hasAward = hasAward;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getWaitMaintenanceId() {
        return waitMaintenanceId;
    }

    public void setWaitMaintenanceId(String waitMaintenanceId) {
        this.waitMaintenanceId = waitMaintenanceId;
    }

    public String getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(String maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

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

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}

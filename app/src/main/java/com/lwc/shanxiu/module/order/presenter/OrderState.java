package com.lwc.shanxiu.module.order.presenter;

/**
 * @author 何栋
 * @version 1.0
 * @date 2017/3/14 16:54
 * @email 294663966@qq.com
 * 订单状态
 */
public class OrderState {

    private int uid;
    private int id;
    private String title;
    private int oid;
    private int ordertype;
    private String msg;
    private long ctime;
    private String formattime;
    private double specialty;
    private double reply;
    private double service;

    public double getSpecialty() {
        return specialty;
    }

    public void setSpecialty(double specialty) {
        this.specialty = specialty;
    }

    public double getReply() {
        return reply;
    }

    public void setReply(double reply) {
        this.reply = reply;
    }

    public double getService() {
        return service;
    }

    public void setService(double service) {
        this.service = service;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public int getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(int ordertype) {
        this.ordertype = ordertype;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public String getFormattime() {
        return formattime;
    }

    public void setFormattime(String formattime) {
        this.formattime = formattime;
    }
}

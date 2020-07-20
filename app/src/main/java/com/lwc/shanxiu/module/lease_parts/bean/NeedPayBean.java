package com.lwc.shanxiu.module.lease_parts.bean;

import java.util.Date;

public class NeedPayBean {

    private String money;
    private Date time;
    private boolean isChecked = false;
    private boolean isPaidIn = false;

    public NeedPayBean(){}

    public NeedPayBean(String money, Date time,Boolean isPaidIn){
        this.money = money;
        this.time = time;
        this.isPaidIn = isPaidIn;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isPaidIn() {
        return isPaidIn;
    }

    public void setPaidIn(boolean paidIn) {
        isPaidIn = paidIn;
    }
}

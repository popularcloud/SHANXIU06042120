package com.lwc.shanxiu.module.authentication.bean;

public class TopicBean {
    /**
     * num : 1
     * answerC :
     * answerB : 错
     * answerD :
     * title : 1.核心涉密岗位是指产生、管理、掌握或者经常处理绝密级事项的工作岗位。
     * type : 3
     * answerA : 对
     */

    private int num;
    private String a;
    private String b;
    private String c;
    private String title;
    private int type;
    private String d;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }
}

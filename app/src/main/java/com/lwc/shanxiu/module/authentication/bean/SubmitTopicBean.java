package com.lwc.shanxiu.module.authentication.bean;

public class SubmitTopicBean {

    private int num;
    private String answer;

    public SubmitTopicBean(){}

    public SubmitTopicBean(int num,String answer){
        this.num = num;
        this.answer = answer;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

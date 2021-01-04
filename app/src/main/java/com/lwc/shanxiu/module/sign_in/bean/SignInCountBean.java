package com.lwc.shanxiu.module.sign_in.bean;

public class SignInCountBean {

    /**
     * lateDayCount : 0
     * workDayCount : 0
     * outDayCount : 0
     * restDayCount : 25
     */

    private String lateDayCount;
    private String workDayCount;
    private String outDayCount;
    private String restDayCount;

    public String getLateDayCount() {
        return lateDayCount;
    }

    public void setLateDayCount(String lateDayCount) {
        this.lateDayCount = lateDayCount;
    }

    public String getWorkDayCount() {
        return workDayCount;
    }

    public void setWorkDayCount(String workDayCount) {
        this.workDayCount = workDayCount;
    }

    public String getOutDayCount() {
        return outDayCount;
    }

    public void setOutDayCount(String outDayCount) {
        this.outDayCount = outDayCount;
    }

    public String getRestDayCount() {
        return restDayCount;
    }

    public void setRestDayCount(String restDayCount) {
        this.restDayCount = restDayCount;
    }
}

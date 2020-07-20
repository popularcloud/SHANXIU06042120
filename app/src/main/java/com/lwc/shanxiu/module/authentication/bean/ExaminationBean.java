package com.lwc.shanxiu.module.authentication.bean;

public class ExaminationBean {
    /**
     * createTime : 2020-07-15 15:19:08
     * paperName : 测试
     * id : 1594797548878ZH
     * userName : 夫子
     * isPass : 0
     * userId : 1543195109561PL
     * paperId : 2
     * userScore : 0
     */

    private String createTime;
    private String paperName;
    private String id;
    private String userName;
    private int isPass;
    private String userId;
    private String paperId;
    private int userScore;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getIsPass() {
        return isPass;
    }

    public void setIsPass(int isPass) {
        this.isPass = isPass;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }
}

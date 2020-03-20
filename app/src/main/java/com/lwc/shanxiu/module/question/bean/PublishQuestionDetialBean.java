package com.lwc.shanxiu.module.question.bean;

import java.util.List;

public class PublishQuestionDetialBean {

    /**
     * quesionLevel : 9
     * isValid : 1
     * answers : [{"answerId":"1582702079733VG","answerDetail":"哈哈","isValid":1,"isSelect":0,"parentCompanyName":"密修公司","maintenanceId":"1540463005919CI","maintenanceName":"张三","parentCompanyId":"1540462770189FE","createTime":"2020-02-26 15:27:59","answerImg":"http://cdn.mixiu365.com/0_1557996403574","browseNum":1,"maintenanceHeadImage":"http://cdn.mixiu365.com/0_1557996403574","quesionId":"1582701289924QW","quesionTitle":"测试"}]
     * quesionDetail : 详情
     * parentCompanyName : 广东智修互联大数据有限公司
     * maintenanceId : 1540451894940MD
     * maintenanceName : 尬增
     * parentCompanyId : 1540392815742SO
     * createTime : 2020-02-26 15:14:49
     * quesionMessage : 1
     * quesionImg : http://cdn.mixiu365.com/0_1557996403574
     * browseNum : 3
     * maintenanceHeadImage : http://cdn.mixiu365.com/0_1557996403574
     * quesionId : 1582701289924QW
     * quesionTitle : 测试
     */

    private int quesionLevel;
    private int isValid;
    private String quesionDetail;
    private String parentCompanyName;
    private String maintenanceId;
    private String maintenanceName;
    private String parentCompanyId;
    private String createTime;
    private int quesionMessage;
    private String quesionImg;
    private int browseNum;
    private String maintenanceHeadImage;
    private String quesionId;
    private String quesionTitle;

    public int getIsAnswer() {
        return isAnswer;
    }

    public void setIsAnswer(int isAnswer) {
        this.isAnswer = isAnswer;
    }

    private int isAnswer;
    private List<AnswersBean> answers;



    public int getQuesionLevel() {
        return quesionLevel;
    }

    public void setQuesionLevel(int quesionLevel) {
        this.quesionLevel = quesionLevel;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public String getQuesionDetail() {
        return quesionDetail;
    }

    public void setQuesionDetail(String quesionDetail) {
        this.quesionDetail = quesionDetail;
    }

    public String getParentCompanyName() {
        return parentCompanyName;
    }

    public void setParentCompanyName(String parentCompanyName) {
        this.parentCompanyName = parentCompanyName;
    }

    public String getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(String maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public String getMaintenanceName() {
        return maintenanceName;
    }

    public void setMaintenanceName(String maintenanceName) {
        this.maintenanceName = maintenanceName;
    }

    public String getParentCompanyId() {
        return parentCompanyId;
    }

    public void setParentCompanyId(String parentCompanyId) {
        this.parentCompanyId = parentCompanyId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getQuesionMessage() {
        return quesionMessage;
    }

    public void setQuesionMessage(int quesionMessage) {
        this.quesionMessage = quesionMessage;
    }

    public String getQuesionImg() {
        return quesionImg;
    }

    public void setQuesionImg(String quesionImg) {
        this.quesionImg = quesionImg;
    }

    public int getBrowseNum() {
        return browseNum;
    }

    public void setBrowseNum(int browseNum) {
        this.browseNum = browseNum;
    }

    public String getMaintenanceHeadImage() {
        return maintenanceHeadImage;
    }

    public void setMaintenanceHeadImage(String maintenanceHeadImage) {
        this.maintenanceHeadImage = maintenanceHeadImage;
    }

    public String getQuesionId() {
        return quesionId;
    }

    public void setQuesionId(String quesionId) {
        this.quesionId = quesionId;
    }

    public String getQuesionTitle() {
        return quesionTitle;
    }

    public void setQuesionTitle(String quesionTitle) {
        this.quesionTitle = quesionTitle;
    }

    public List<AnswersBean> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswersBean> answers) {
        this.answers = answers;
    }

    public static class AnswersBean {
        /**
         * answerId : 1582702079733VG
         * answerDetail : 哈哈
         * isValid : 1
         * isSelect : 0
         * parentCompanyName : 密修公司
         * maintenanceId : 1540463005919CI
         * maintenanceName : 张三
         * parentCompanyId : 1540462770189FE
         * createTime : 2020-02-26 15:27:59
         * answerImg : http://cdn.mixiu365.com/0_1557996403574
         * browseNum : 1
         * maintenanceHeadImage : http://cdn.mixiu365.com/0_1557996403574
         * quesionId : 1582701289924QW
         * quesionTitle : 测试
         */

        private String answerId;
        private String answerDetail;
        private int isValid;
        private int isSelect;
        private String parentCompanyName;
        private String maintenanceId;
        private String maintenanceName;
        private String parentCompanyId;
        private String createTime;
        private String answerImg;
        private int browseNum;
        private String maintenanceHeadImage;
        private String quesionId;
        private String quesionTitle;

        public String getAnswerId() {
            return answerId;
        }

        public void setAnswerId(String answerId) {
            this.answerId = answerId;
        }

        public String getAnswerDetail() {
            return answerDetail;
        }

        public void setAnswerDetail(String answerDetail) {
            this.answerDetail = answerDetail;
        }

        public int getIsValid() {
            return isValid;
        }

        public void setIsValid(int isValid) {
            this.isValid = isValid;
        }

        public int getIsSelect() {
            return isSelect;
        }

        public void setIsSelect(int isSelect) {
            this.isSelect = isSelect;
        }

        public String getParentCompanyName() {
            return parentCompanyName;
        }

        public void setParentCompanyName(String parentCompanyName) {
            this.parentCompanyName = parentCompanyName;
        }

        public String getMaintenanceId() {
            return maintenanceId;
        }

        public void setMaintenanceId(String maintenanceId) {
            this.maintenanceId = maintenanceId;
        }

        public String getMaintenanceName() {
            return maintenanceName;
        }

        public void setMaintenanceName(String maintenanceName) {
            this.maintenanceName = maintenanceName;
        }

        public String getParentCompanyId() {
            return parentCompanyId;
        }

        public void setParentCompanyId(String parentCompanyId) {
            this.parentCompanyId = parentCompanyId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getAnswerImg() {
            return answerImg;
        }

        public void setAnswerImg(String answerImg) {
            this.answerImg = answerImg;
        }

        public int getBrowseNum() {
            return browseNum;
        }

        public void setBrowseNum(int browseNum) {
            this.browseNum = browseNum;
        }

        public String getMaintenanceHeadImage() {
            return maintenanceHeadImage;
        }

        public void setMaintenanceHeadImage(String maintenanceHeadImage) {
            this.maintenanceHeadImage = maintenanceHeadImage;
        }

        public String getQuesionId() {
            return quesionId;
        }

        public void setQuesionId(String quesionId) {
            this.quesionId = quesionId;
        }

        public String getQuesionTitle() {
            return quesionTitle;
        }

        public void setQuesionTitle(String quesionTitle) {
            this.quesionTitle = quesionTitle;
        }
    }
}

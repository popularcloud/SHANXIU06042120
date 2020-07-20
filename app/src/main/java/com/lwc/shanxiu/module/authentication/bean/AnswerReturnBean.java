package com.lwc.shanxiu.module.authentication.bean;

import java.util.List;

public class AnswerReturnBean {

    /**
     * score : 1
     * pass : 0
     * paper : [{"answer":"A","num":"1","status":1},{"answer":"B","num":"2","status":0}]
     */

    private int score;
    private int pass;
    private List<PaperBean> paper;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPass() {
        return pass;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }

    public List<PaperBean> getPaper() {
        return paper;
    }

    public void setPaper(List<PaperBean> paper) {
        this.paper = paper;
    }

    public static class PaperBean {
        /**
         * answer : A
         * num : 1
         * status : 1
         */

        private String answer;
        private String num;
        private int status;

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}

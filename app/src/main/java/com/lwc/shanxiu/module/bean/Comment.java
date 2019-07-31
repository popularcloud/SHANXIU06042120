package com.lwc.shanxiu.module.bean;

import java.io.Serializable;

public class Comment implements Serializable {

    private float service_attitude;//”:”服务态度”      //服务态度
    private float expertise_level;//”:”专业水平”       //专业水平
    private float specialty;//”:”上门速度”            //上门速度
    private String comment_content;//”:”评论内容”    //评论内容
    private float synthesize_grade;//评价星级
    private String comment_labels;//评价标签

    public float getSynthesize_grade() {
        return synthesize_grade;
    }

    public void setSynthesize_grade(float synthesize_grade) {
        this.synthesize_grade = synthesize_grade;
    }

    public String getComment_labels() {
        return comment_labels;
    }

    public void setComment_labels(String comment_labels) {
        this.comment_labels = comment_labels;
    }

    public float getSpecialty() {
        return specialty;
    }

    public void setSpecialty(float specialty) {
        this.specialty = specialty;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public float getService_attitude() {
            return service_attitude;
        }

    public void setService_attitude(float service_attitude) {
        this.service_attitude = service_attitude;
    }

    public float getExpertise_level() {
        return expertise_level;
    }

    public void setExpertise_level(float expertise_level) {
        this.expertise_level = expertise_level;
    }


    }
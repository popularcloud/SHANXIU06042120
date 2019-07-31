package com.lwc.shanxiu.module.bean;

import java.io.Serializable;

/**
 * @author 何栋
 * @date 2018/7/12 0012
 * @email 294663966@qq.com
 */
public class Parts implements Serializable{


    /**
     partsId:配件id
     partsName:配件名称
     partsModel:配件型号
     partsPrice:配件价格
     applicableModel:适用机型
     createTime:创建时间
     */

    private String partsId;
    private String partsName;
    private String partsModel;
    private String partsPrice;
    private String applicableModel;
    private String createTime;
    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPartsId() {
        return partsId;
    }

    public void setPartsId(String partsId) {
        this.partsId = partsId;
    }

    public String getPartsName() {
        return partsName;
    }

    public void setPartsName(String partsName) {
        this.partsName = partsName;
    }

    public String getPartsModel() {
        return partsModel;
    }

    public void setPartsModel(String partsModel) {
        this.partsModel = partsModel;
    }

    public String getPartsPrice() {
        return partsPrice;
    }

    public void setPartsPrice(String partsPrice) {
        this.partsPrice = partsPrice;
    }

    public String getApplicableModel() {
        return applicableModel;
    }

    public void setApplicableModel(String applicableModel) {
        this.applicableModel = applicableModel;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}

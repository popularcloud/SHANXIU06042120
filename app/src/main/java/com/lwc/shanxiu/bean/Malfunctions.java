package com.lwc.shanxiu.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by 何栋 on 2017/10/15.
 * 294663966@qq.com
 * 返修类型
 */
public class Malfunctions implements Serializable{

    public String createTime;//": "2018-1-10 10:28:55",  //创建时间
    public String exampleId;//": "2",                  //ID
    public String exampleName;//": "黑屏",            //故障实例名称
    public int faultType;//": 1,                     //故障类型(1.软件 2.硬件 3.其他）
    public int isValid;//": 1,                       //是否有效（0.无效 1.有效）
    public String maintainCost;//": 50,                 //维修价格
    public String modifyTime;//": "2018-01-10 10:28:57",
    public String visitCost;//": 20                     //上门费用

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getExampleId() {
        return exampleId;
    }

    public void setExampleId(String exampleId) {
        this.exampleId = exampleId;
    }

    public String getExampleName() {
        return exampleName;
    }

    public void setExampleName(String exampleName) {
        this.exampleName = exampleName;
    }

    public int getFaultType() {
        return faultType;
    }

    public void setFaultType(int faultType) {
        this.faultType = faultType;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public String getMaintainCost() {
        return maintainCost;
    }

    public void setMaintainCost(String maintainCost) {
        this.maintainCost = maintainCost;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getVisitCost() {
        return visitCost;
    }

    public void setVisitCost(String visitCost) {
        this.visitCost = visitCost;
    }
}

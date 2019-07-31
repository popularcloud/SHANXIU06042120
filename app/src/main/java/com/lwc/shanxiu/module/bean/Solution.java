package com.lwc.shanxiu.module.bean;

import com.lwc.shanxiu.pickerview.model.IPickerViewData;

import java.io.Serializable;

/**
 * @author 何栋
 * @version 1.0
 * @date 2017/4/21 14:33
 * @email 294663966@qq.com
 */
public class Solution implements Serializable, IPickerViewData {

    /**
     * status : 10000
     * data : http://120.77.242.131/upload/user/picture/5a5c61b70bb84a3fa41693bf7c6242ff.jpeg
     * info : 操作成功
     */
    private String exampleId;//": "1",                   //实例ID
    private String exampleName;//": "蓝屏",              //实例名称
    private String visitCost;//": 2000,                  //上门费
    private String maintainCost;//": 2000,               //维修费
    private String isFixation;//": 0//维修价格是否固定（0：否 1:是）
    private String deviceTypeName;

    public Solution(){}

    public Solution(String exampleId,String exampleName){
        this.exampleId = exampleId;
        this.exampleName = exampleName;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
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

    public String getVisitCost() {
        return visitCost;
    }

    public void setVisitCost(String visitCost) {
        this.visitCost = visitCost;
    }

    public String getMaintainCost() {
        return maintainCost;
    }

    public void setMaintainCost(String maintainCost) {
        this.maintainCost = maintainCost;
    }

    public String getIsFixation() {
        return isFixation;
    }

    public void setIsFixation(String isFixation) {
        this.isFixation = isFixation;
    }

    @Override
    public String getPickerViewText() {
        return exampleName;
    }
}

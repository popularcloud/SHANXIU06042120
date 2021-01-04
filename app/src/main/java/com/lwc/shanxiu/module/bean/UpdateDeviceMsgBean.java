package com.lwc.shanxiu.module.bean;

import java.io.Serializable;

/**
 * @author younge
 * @date 2019/6/26 0026
 * @email 2276559259@qq.com
 */
public class UpdateDeviceMsgBean implements Serializable{

    private String relevanceId;         //设备id
    private String userPhone;          //使用人号码
    private String userName;          //使用人
    private String userDetailCompanyName;  //使用人所属科室
    private String companyProvinceId;        //单位所在省ID
    private String companyProvinceName;    //单位公司所在省名称
    private String companyCityId;         //单位公司所在市ID
    private String companyCityName;    //单位公司所在市名称
    private String companyTownId;   //单位公司所在区ID
    private String companyTownName; //单位公司所在区名称
    private String updateReason;   //修改原因

    public String getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(String relevanceId) {
        this.relevanceId = relevanceId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getCompanyProvinceId() {
        return companyProvinceId;
    }

    public void setCompanyProvinceId(String companyProvinceId) {
        this.companyProvinceId = companyProvinceId;
    }

    public String getCompanyProvinceName() {
        return companyProvinceName;
    }

    public void setCompanyProvinceName(String companyProvinceName) {
        this.companyProvinceName = companyProvinceName;
    }

    public String getCompanyCityId() {
        return companyCityId;
    }

    public void setCompanyCityId(String companyCityId) {
        this.companyCityId = companyCityId;
    }

    public String getCompanyCityName() {
        return companyCityName;
    }

    public void setCompanyCityName(String companyCityName) {
        this.companyCityName = companyCityName;
    }

    public String getCompanyTownId() {
        return companyTownId;
    }

    public void setCompanyTownId(String companyTownId) {
        this.companyTownId = companyTownId;
    }

    public String getCompanyTownName() {
        return companyTownName;
    }

    public void setCompanyTownName(String companyTownName) {
        this.companyTownName = companyTownName;
    }

    public String getUpdateReason() {
        return updateReason;
    }

    public void setUpdateReason(String updateReason) {
        this.updateReason = updateReason;
    }


    public String getUserDetailCompanyName() {
        return userDetailCompanyName;
    }

    public void setUserDetailCompanyName(String userDetailCompanyName) {
        this.userDetailCompanyName = userDetailCompanyName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

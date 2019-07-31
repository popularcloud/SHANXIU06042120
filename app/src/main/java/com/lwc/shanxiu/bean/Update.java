package com.lwc.shanxiu.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author 何栋
 * @version 1.0
 * @date 2017/3/28 09:31
 * @email 294663966@qq.com
 * 版本升级信息
 */
public class Update {

    private String versionId;//": "版本ID",                //版本ID
    private String versionName;//": "版本名称",           //版本名称
    private String versionCode;//": "版本号",              //版本号
    private String fileSize;//": "文件大小",                //文件大小
    private String isForce;//": "0",                       //是否强制更新（0：否 1：是）
    private String deviceType;//": "0",                   // 设备类型 （0：ANDRIOD  1:IOS）
    private String message;//": "版本信息",              //版本信息
    private String url;//": "下载地址",                   //下载地址
    private String isValid;//": 1,                        //是否有效（0：否 1：是）
    private String createTime;//": "创建时间",            //创建时间
    private String modifyTime;//": "修改时间",           //修改时间

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getIsForce() {
        return isForce;
    }

    public void setIsForce(String isForce) {
        this.isForce = isForce;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

}

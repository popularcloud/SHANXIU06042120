package com.lwc.shanxiu.module.bean;

import java.io.Serializable;

/**
 * Created by 何栋 on 2017/10/15.
 * 294663966@qq.com
 * 故障
 */
public class Malfunction implements Serializable {


    private String reqairId;
    private String did;
    private String reqairName;
    private String deviceTypeName;

    public Malfunction(String malfunctionId, String repairname) {
        this.reqairId = malfunctionId;
        this.reqairName = repairname;
    }

    public Malfunction(String malfunctionId, String did, String repairname, String deviceTypeName) {
        this.reqairId = malfunctionId;
        this.did = did;
        this.reqairName = repairname;
        this.deviceTypeName = deviceTypeName;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    public String getReqairId() {
        return reqairId;
    }

    public void setReqairId(String reqairId) {
        this.reqairId = reqairId;
    }

    public String getReqairName() {
        return reqairName;
    }

    public void setReqairName(String reqairName) {
        this.reqairName = reqairName;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    @Override
    public String toString() {
        return "Malfunction{" +
                "reqairId=" + reqairId +
                ", did=" + did +
                ", reqairName='" + reqairName + '\'' +
                ", deviceTypeName='" + deviceTypeName + '\'' +
                '}';
    }
}

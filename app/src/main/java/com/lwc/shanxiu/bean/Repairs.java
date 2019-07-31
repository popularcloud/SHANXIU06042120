package com.lwc.shanxiu.bean;

import com.lwc.shanxiu.module.bean.Solution;
import com.lwc.shanxiu.pickerview.model.IPickerViewData;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 何栋 on 2017/10/15.
 * 294663966@qq.com
 * 返修类型
 */
public class Repairs extends DataSupport implements Serializable, IPickerViewData {

    private String deviceTypeId;
    private String deviceTypeName;
    private ArrayList<Solution> faults;

    public Repairs(String deviceTypeId, String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
        this.deviceTypeId = deviceTypeId;
    }

    public ArrayList<Solution> getFaults() {
        return faults;
    }

    public void setFaults(ArrayList<Solution> faults) {
        this.faults = faults;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    public String getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(String deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    @Override
    public String getPickerViewText() {
        return this.deviceTypeName;
    }
}

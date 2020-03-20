package com.lwc.shanxiu.module.bean;

import com.lwc.shanxiu.pickerview.model.IPickerViewData;

import java.io.Serializable;

/**
 * @author younge
 * @date 2019/2/27 0027
 * @email 2276559259@qq.com
 * 品牌
 */
public class DevicesPropertyBean implements Serializable {

    private String deviceKey;
    private String deviceValue;

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getDeviceValue() {
        return deviceValue;
    }

    public void setDeviceValue(String deviceValue) {
        this.deviceValue = deviceValue;
    }
}

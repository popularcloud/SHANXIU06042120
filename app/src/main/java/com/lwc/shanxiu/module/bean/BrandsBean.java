package com.lwc.shanxiu.module.bean;

import com.lwc.shanxiu.pickerview.model.IPickerViewData;

import java.io.Serializable;

/**
 * @author younge
 * @date 2019/2/27 0027
 * @email 2276559259@qq.com
 * 品牌
 */
public class BrandsBean implements Serializable, IPickerViewData {


    /**
     * deviceTypeBrandId : 2
     * createTime : 2019-02-26 18:05:11
     * deviceTypeBrand : 联想
     * deviceTypeBrandCode : B
     */

    private String deviceTypeBrandId;
    private String createTime;
    private String deviceTypeBrand;
    private String deviceTypeBrandCode;

    @Override
    public String getPickerViewText() {
        return deviceTypeBrand;
    }

    public String getDeviceTypeBrandId() {
        return deviceTypeBrandId;
    }

    public void setDeviceTypeBrandId(String deviceTypeBrandId) {
        this.deviceTypeBrandId = deviceTypeBrandId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDeviceTypeBrand() {
        return deviceTypeBrand;
    }

    public void setDeviceTypeBrand(String deviceTypeBrand) {
        this.deviceTypeBrand = deviceTypeBrand;
    }

    public String getDeviceTypeBrandCode() {
        return deviceTypeBrandCode;
    }

    public void setDeviceTypeBrandCode(String deviceTypeBrandCode) {
        this.deviceTypeBrandCode = deviceTypeBrandCode;
    }
}

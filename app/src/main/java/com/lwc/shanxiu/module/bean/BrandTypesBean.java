package com.lwc.shanxiu.module.bean;

import com.lwc.shanxiu.pickerview.model.IPickerViewData;

import java.io.Serializable;

/**
 * @author younge
 * @date 2019/2/27 0027
 * @email 2276559259@qq.com
 * 型号
 */
public class BrandTypesBean implements Serializable, IPickerViewData {


    /**
     * deviceTypeBrandId : 2
     * createTime : 2019-02-26 18:14:12
     * deviceTypeModel : 型号
     * deviceTypeModelId : 1551176052021RP
     */

    private String deviceTypeBrandId;
    private String createTime;
    private String deviceTypeModel;
    private String deviceTypeModelId;

    @Override
    public String getPickerViewText() {
        return deviceTypeModel;
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

    public String getDeviceTypeModel() {
        return deviceTypeModel;
    }

    public void setDeviceTypeModel(String deviceTypeModel) {
        this.deviceTypeModel = deviceTypeModel;
    }

    public String getDeviceTypeModelId() {
        return deviceTypeModelId;
    }

    public void setDeviceTypeModelId(String deviceTypeModelId) {
        this.deviceTypeModelId = deviceTypeModelId;
    }
}

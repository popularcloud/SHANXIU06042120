package com.lwc.shanxiu.module.bean;

import com.lwc.shanxiu.pickerview.model.IPickerViewData;

import java.io.Serializable;

/**
 * @author 何栋
 * @version 1.0
 * @date 2017/4/21 14:33
 * @email 294663966@qq.com
 */
public class DeviceType implements Serializable, IPickerViewData {

    /**
     * status : 10000
     * data : http://120.77.242.131/upload/user/picture/5a5c61b70bb84a3fa41693bf7c6242ff.jpeg
     * info : 操作成功
     */

    private String deviceTypeName;
    private String deviceTypeId;
    private String deviceTypeIcon;
    private String createTime;

    public String getDeviceTypeIcon() {
        return deviceTypeIcon;
    }

    public void setDeviceTypeIcon(String deviceTypeIcon) {
        this.deviceTypeIcon = deviceTypeIcon;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String getPickerViewText() {
        return deviceTypeName;
    }
}

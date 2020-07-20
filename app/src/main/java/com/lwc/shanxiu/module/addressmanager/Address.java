package com.lwc.shanxiu.module.addressmanager;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by 何栋 on 2017/10/15.
 * 294663966@qq.com
 * 用户地址
 */
public class Address extends DataSupport implements Serializable {

    private String provinceId;//": "7",                        //省ID
    private String createTime;//": "2017-8-31 17:25:34",        //创建时间
    private String contactPhone;//": "13219950722",           //联系电话
    private String cityId;//": "79",                           //市ID
    private String townName;//": "南城区",                  //区名称
    private String cityName;//": "东莞市",                   // 市名称
    private String townId;//": "714",                        //区ID
    private String provinceName;//": "广东省",               //省名称
    private int isDefault;//": 1,                          //是否默认 （0：否 1:是）
    private String contactName;//": "King",                  //联系人
    private String userAddressId;//": "170817164144587101AH",//地址ID
    private String longitude;//": 36.456789,                 //经度
    private String latitude;//": 123.236986,                  //纬度
    private String modifyTime;//": "2017-08-31 17:25:49",      //修改时间
    private String contactAddress;//": "冬日大厦"             //联系地址
    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUserAddressId() {
        return userAddressId;
    }

    public void setUserAddressId(String userAddressId) {
        this.userAddressId = userAddressId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }


    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getTownId() {
        return townId;
    }

    public void setTownId(String townId) {
        this.townId = townId;
    }
}

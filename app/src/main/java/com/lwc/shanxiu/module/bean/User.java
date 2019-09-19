package com.lwc.shanxiu.module.bean;

import android.text.TextUtils;

import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.utils.PatternUtil;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * @author 何栋
 * @version 1.0
 * @date 2017/4/12 10:39
 * @email 294663966@qq.com
 * 用户信息
 */
public class User extends DataSupport implements Serializable {

    private int autoAcceptOrder;//": 0,                            //是否自动接单 0：否 1：是
    private String companyId;//": "1",                               // 公司ID
    private String companyName;//": "子公司名称",                   //子公司名称
    private String isBusy;//": 0,                                     //是否繁忙 0：否 1：是
    private String isSecrecy;//": 0,                  //是否是认证工程师（0：否 1：是 2：申请中）
    private String maintenanceId;//": "170817164144587101BH",         //工程师ID
    private String maintenanceLatitude;//": 123.236986,                 //纬度
    private String maintenanceLongitude;//": 36.456789,                 //经度
    private String maintenancePhone;//": "13219950721",                //工程师姓名
    private String modifyTime;//": "2017-08-31 11:09:25",                 //最后修改时间
    private String parentCompanyId;//": "1",                            //所属父级公司ID
    private String parentCompanyName;//": "父级公司名称",              //所属父级公司名称
    private String deviceTypeMold;//": //父级公司维修类型 1. 办公设备 3.家用电器
    private int roleId;//": "4",             //用户权限ID
    private String maintenanceHeadImage;//": "https://IP/logo_white_fe6da1ec.png",  //用户头像
    private int isValid;//": 1,              //是否有效 0：否 1：是
    private String userName;//": "13219950721", // 用户名
    private float serviceAttitude;//": "0",       //服务态度
    private String maintenanceName;//": "King",  //维修师真是姓名
    private String token;//": "thisistoken",        //token
    private String userPhone;//": "13219950721", //维修时电话
    private float expertiseLevel;//": "0",        //专业水平
    private float maintenanceStar;//": "0",     //综合评分
    private String createTime;//": "2017-08-17 16:41:37",  //创建时间
    private int maintenanceSex;//": 1,        //维修时性别
    private String userId;//": "170817164144587101BH",  //维修时ID
    private float averageSpeed;//": "0",          //上门速度
    private int orderCount;//": 0,              //完成订单数量
    private String maintenanceSignature;//": "没有签名啦"  //维修时签名
    private String pwd;
    private String lat;
    private String lon;

    private String payPassword;//": "支付密码",          //支付密码（为null是没有设置）
    private String banlance;//": "0",                    //余额
    private String openid;//": "微信标示",               //微信标示
    private String aliToken;
    private String qrCode;//: "二维码字符串",                  //二维码字符串

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getAliToken() {
        return aliToken;
    }

    public void setAliToken(String aliToken) {
        this.aliToken = aliToken;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getBanlance() {
        if (!TextUtils.isEmpty(banlance) && PatternUtil.isNumer(banlance)){
            return Utils.chu(banlance, "100");
        } else {
            banlance = "0";
        }
        return banlance;
    }

    public void setBanlance(String banlance) {
        this.banlance = banlance;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public int getAutoAcceptOrder() {
        return autoAcceptOrder;
    }

    public void setAutoAcceptOrder(int autoAcceptOrder) {
        this.autoAcceptOrder = autoAcceptOrder;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIsSecrecy() {
        return isSecrecy;
    }

    public void setIsSecrecy(String isSecrecy) {
        this.isSecrecy = isSecrecy;
    }

    public String getIsBusy() {
        return isBusy;
    }

    public void setIsBusy(String isBusy) {
        this.isBusy = isBusy;
    }

    public String getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(String maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public String getMaintenanceLatitude() {
        return maintenanceLatitude;
    }

    public void setMaintenanceLatitude(String maintenanceLatitude) {
        this.maintenanceLatitude = maintenanceLatitude;
    }

    public String getMaintenanceLongitude() {
        return maintenanceLongitude;
    }

    public void setMaintenanceLongitude(String maintenanceLongitude) {
        this.maintenanceLongitude = maintenanceLongitude;
    }

    public String getMaintenancePhone() {
        return maintenancePhone;
    }

    public void setMaintenancePhone(String maintenancePhone) {
        this.maintenancePhone = maintenancePhone;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getParentCompanyId() {
        return parentCompanyId;
    }

    public void setParentCompanyId(String parentCompanyId) {
        this.parentCompanyId = parentCompanyId;
    }

    public String getParentCompanyName() {
        return parentCompanyName;
    }

    public void setParentCompanyName(String parentCompanyName) {
        this.parentCompanyName = parentCompanyName;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getMaintenanceHeadImage() {
        return maintenanceHeadImage;
    }

    public void setMaintenanceHeadImage(String maintenanceHeadImage) {
        this.maintenanceHeadImage = maintenanceHeadImage;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public float getServiceAttitude() {
        return serviceAttitude;
    }

    public void setServiceAttitude(float serviceAttitude) {
        this.serviceAttitude = serviceAttitude;
    }

    public String getMaintenanceName() {
        return maintenanceName;
    }

    public void setMaintenanceName(String maintenanceName) {
        this.maintenanceName = maintenanceName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public float getExpertiseLevel() {
        return expertiseLevel;
    }

    public void setExpertiseLevel(float expertiseLevel) {
        this.expertiseLevel = expertiseLevel;
    }

    public float getMaintenanceStar() {
        return maintenanceStar;
    }

    public void setMaintenanceStar(float maintenanceStar) {
        this.maintenanceStar = maintenanceStar;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getMaintenanceSex() {
        return maintenanceSex;
    }

    public void setMaintenanceSex(int maintenanceSex) {
        this.maintenanceSex = maintenanceSex;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public float getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(float averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public String getMaintenanceSignature() {
        return maintenanceSignature;
    }

    public void setMaintenanceSignature(String maintenanceSignature) {
        this.maintenanceSignature = maintenanceSignature;
    }

    public String getDeviceTypeMold() {
        return deviceTypeMold;
    }

    public void setDeviceTypeMold(String deviceTypeMold) {
        this.deviceTypeMold = deviceTypeMold;
    }
}

package com.lwc.shanxiu.module.bean;

/**
 * @author 何栋
 * @version 1.0
 * @date 2017/3/14 16:54
 * @email 294663966@qq.com
 * 订单状态
 */
public class OrderUser {

    private String maintenanceName;// ": "维修时姓名",      //维修时姓名
    private String maintenancePhone;// ": "维修时电话",      //维修时电话
    private String companyName;// ": "维修时公司名称",      //维修时公司名称
    private String userRealname;// ": "用户名称",             //用户名称
    private String userPhone;// ": "用户电话",                //用户电话
    private String userCompanyName;// ": "用户公司名称"     //用户公司名称
    private String orderLatitude;// ": "订单纬度",              //订单纬度
    private String orderLongitude;// ": "订单经度",             //订单经度
    private boolean isUser;

    public String getMaintenanceName() {
        return maintenanceName;
    }

    public void setMaintenanceName(String maintenanceName) {
        this.maintenanceName = maintenanceName;
    }

    public String getMaintenancePhone() {
        return maintenancePhone;
    }

    public void setMaintenancePhone(String maintenancePhone) {
        this.maintenancePhone = maintenancePhone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUserRealname() {
        return userRealname;
    }

    public void setUserRealname(String userRealname) {
        this.userRealname = userRealname;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getOrderLatitude() {
        return orderLatitude;
    }

    public void setOrderLatitude(String orderLatitude) {
        this.orderLatitude = orderLatitude;
    }

    public String getUserCompanyName() {
        return userCompanyName;
    }

    public void setUserCompanyName(String userCompanyName) {
        this.userCompanyName = userCompanyName;
    }

    public String getOrderLongitude() {
        return orderLongitude;
    }

    public void setOrderLongitude(String orderLongitude) {
        this.orderLongitude = orderLongitude;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
    }
}

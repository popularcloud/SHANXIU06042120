package com.lwc.shanxiu.module.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author younge
 * @date 2019/6/25 0025
 * @email 2276559259@qq.com
 */
public class LeaseDevicesHistoryBean implements Serializable {

    /**
     * deviceTypeModel : 惠普
     * deviceTypeName : 电脑
     * deviceTypeBrand : X5
     * userPhone : 123
     * userCompanyName : 123
     * createTime : 2018-08-09 10:11:12
     * orders : [{" orderId ":"订单编号"," orderType":"订单类型"," isSecrecy":"是否认证"," isAppeal":" 1"," orderContactName ":"订单联系人"," orderLongitude ":"订单经度"," orderLatitude ":"订单纬度"," orderImage ":"订单图片"," orderDescription ":"订单描述"," deviceTypeName ":"保修设备类型名称"," reqairName ":"维修技能名称"," userCompanyName ":"用户公司名称"," createTime ":"订单创建时间"," orderContactAddress ":"订单联系地址"," orderContactPhone":"订单联系电话"," repairCompanyName ":"保修时公司"}]
     */

    private String deviceTypeModel;
    private String deviceTypeName;
    private String deviceTypeBrand;
    private String userPhone;
    private String userName;
    private String userCompanyName;
    private String userCompanyAddrs;
    private String maintenanceCompanyName;
    private String maintenanceName;
    private String createTime;
    private String companyProvinceName;
    private String companyCityName;
    private String companyTownName;
    private List<OrdersBean> orders;

    public String getDeviceTypeModel() {
        return deviceTypeModel;
    }

    public void setDeviceTypeModel(String deviceTypeModel) {
        this.deviceTypeModel = deviceTypeModel;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    public String getDeviceTypeBrand() {
        return deviceTypeBrand;
    }

    public void setDeviceTypeBrand(String deviceTypeBrand) {
        this.deviceTypeBrand = deviceTypeBrand;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCompanyProvinceName() {
        return companyProvinceName;
    }

    public void setCompanyProvinceName(String companyProvinceName) {
        this.companyProvinceName = companyProvinceName;
    }

    public String getCompanyCityName() {
        return companyCityName;
    }

    public void setCompanyCityName(String companyCityName) {
        this.companyCityName = companyCityName;
    }

    public String getCompanyTownName() {
        return companyTownName;
    }

    public void setCompanyTownName(String companyTownName) {
        this.companyTownName = companyTownName;
    }

    public String getUserCompanyAddrs() {
        return userCompanyAddrs;
    }

    public void setUserCompanyAddrs(String userCompanyAddrs) {
        this.userCompanyAddrs = userCompanyAddrs;
    }

    public String getMaintenanceCompanyName() {
        return maintenanceCompanyName;
    }

    public void setMaintenanceCompanyName(String maintenanceCompanyName) {
        this.maintenanceCompanyName = maintenanceCompanyName;
    }

    public String getMaintenanceName() {
        return maintenanceName;
    }

    public void setMaintenanceName(String maintenanceName) {
        this.maintenanceName = maintenanceName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserCompanyName() {
        return userCompanyName;
    }

    public void setUserCompanyName(String userCompanyName) {
        this.userCompanyName = userCompanyName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<OrdersBean> getOrders() {
        return orders;
    }

    public void setOrders(List<OrdersBean> orders) {
        this.orders = orders;
    }

    public static class OrdersBean implements Serializable{
        /**
         *  orderId  : 订单编号
         *  orderType : 订单类型
         *  isSecrecy : 是否认证
         *  isAppeal :  1
         *  orderContactName  : 订单联系人
         *  orderLongitude  : 订单经度
         *  orderLatitude  : 订单纬度
         *  orderImage  : 订单图片
         *  orderDescription  : 订单描述
         *  deviceTypeName  : 保修设备类型名称
         *  reqairName  : 维修技能名称
         *  userCompanyName  : 用户公司名称
         *  createTime  : 订单创建时间
         *  orderContactAddress  : 订单联系地址
         *  orderContactPhone : 订单联系电话
         *  repairCompanyName  : 保修时公司
         */

        private String orderId;
        private String orderType;
        private String isSecrecy;
        private String isAppeal;
        private String orderContactName;
        private String orderLongitude;
        private String orderLatitude;
        private String orderImage;
        private String orderDescription;
        private String deviceTypeName;
        private String reqairName;
        private String userCompanyName;
        private String createTime;
        private String orderContactAddress;
        private String orderContactPhone;
        private String repairCompanyName;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getIsSecrecy() {
            return isSecrecy;
        }

        public void setIsSecrecy(String isSecrecy) {
            this.isSecrecy = isSecrecy;
        }

        public String getIsAppeal() {
            return isAppeal;
        }

        public void setIsAppeal(String isAppeal) {
            this.isAppeal = isAppeal;
        }

        public String getOrderContactName() {
            return orderContactName;
        }

        public void setOrderContactName(String orderContactName) {
            this.orderContactName = orderContactName;
        }

        public String getOrderLongitude() {
            return orderLongitude;
        }

        public void setOrderLongitude(String orderLongitude) {
            this.orderLongitude = orderLongitude;
        }

        public String getOrderLatitude() {
            return orderLatitude;
        }

        public void setOrderLatitude(String orderLatitude) {
            this.orderLatitude = orderLatitude;
        }

        public String getOrderImage() {
            return orderImage;
        }

        public void setOrderImage(String orderImage) {
            this.orderImage = orderImage;
        }

        public String getOrderDescription() {
            return orderDescription;
        }

        public void setOrderDescription(String orderDescription) {
            this.orderDescription = orderDescription;
        }

        public String getDeviceTypeName() {
            return deviceTypeName;
        }

        public void setDeviceTypeName(String deviceTypeName) {
            this.deviceTypeName = deviceTypeName;
        }

        public String getReqairName() {
            return reqairName;
        }

        public void setReqairName(String reqairName) {
            this.reqairName = reqairName;
        }

        public String getUserCompanyName() {
            return userCompanyName;
        }

        public void setUserCompanyName(String userCompanyName) {
            this.userCompanyName = userCompanyName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getOrderContactAddress() {
            return orderContactAddress;
        }

        public void setOrderContactAddress(String orderContactAddress) {
            this.orderContactAddress = orderContactAddress;
        }

        public String getOrderContactPhone() {
            return orderContactPhone;
        }

        public void setOrderContactPhone(String orderContactPhone) {
            this.orderContactPhone = orderContactPhone;
        }

        public String getRepairCompanyName() {
            return repairCompanyName;
        }

        public void setRepairCompanyName(String repairCompanyName) {
            this.repairCompanyName = repairCompanyName;
        }
    }
}

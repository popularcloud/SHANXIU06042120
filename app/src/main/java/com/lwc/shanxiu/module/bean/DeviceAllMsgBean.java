package com.lwc.shanxiu.module.bean;

import java.util.List;

/**
 * @author younge
 * @date 2019/6/25 0025
 * @email 2276559259@qq.com
 */
public class DeviceAllMsgBean {
    /**
     * deviceTypeModel : 惠普
     * deviceTypeName : 电脑
     * deviceTypeBrand : X5
     * createTime : 2018-08-09 10:11:12
     * userPhone : 13592782796
     * userCompanyName : 13592782796
     * maintenance_name : 13592782796
     * maintenance_company_name : 13592782796
     * companyProvinceName :
     * companyCityName :
     * companyTownName :
     * relevanceId  : 170831102758556102YU
     * updateTime : 2018-08-09 10:11:12
     * relevanceType : 1
     * orders : [{"orderId":"订单编号"," orderType":"订单类型"," isSecrecy":"是否认证"," isAppeal":" 1"," orderContactName ":"订单联系人"," orderLongitude ":"订单经度"," orderLatitude ":"订单纬度"," orderImage ":"订单图片"," orderDescription ":"订单描述"," deviceTypeName ":"保修设备类型名称"," reqairName ":"维修技能名称"," userCompanyName ":"用户公司名称"," createTime ":"订单创建时间"," orderContactAddress ":"订单联系地址"," orderContactPhone":"订单联系电话","repairCompanyName":"保修时公司"}]
     * updates : [{"createTime":"2018-08-09 10:11:12","userPhone":13592782796,"userCompanyName":13592782796,"maintenanceName":13592782796,"updateReason":"测试","companyProvinceName":"","companyCityName":"","companyTownName":""}]
     * records : [{"createTime":"2018-08-09 10:11:12","maintenanceName":13592782796,"maintenanceCompanyName":13592782796,"updateReason":13592782796,"updateType":2,"makeTime":"2018-08-09 10:11:12"}]
     */

    private String deviceTypeModel;
    private String deviceTypeName;
    private String deviceTypeBrand;
    private String createTime;
    private String userPhone;
    private String userCompanyName;
    private String maintenanceName;
    private String maintenanceCompanyName;
    private String companyProvinceName;
    private String companyProvinceId;
    private String companyCityName;
    private String companyCityId;
    private String companyTownName;
    private String companyTownId;
    private String relevanceId;
    private String updateTime;
    private String userName;
    private String userDetailCompanyName;
    private int relevanceType;  //设备状态（1：正常 2：报废 3：销毁 4：报废中）
    private int isRelevance;   //是否能报废销毁设备（0：否，1：是）
    private java.util.List<OrdersBean> orders;
    private java.util.List<UpdatesBean> updates;
    private java.util.List<RecordsBean> recoreds;

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

    public String getUserDetailCompanyName() {
        return userDetailCompanyName;
    }

    public void setUserDetailCompanyName(String userDetailCompanyName) {
        this.userDetailCompanyName = userDetailCompanyName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(String relevanceId) {
        this.relevanceId = relevanceId;
    }

    public int getIsRelevance() {
        return isRelevance;
    }

    public void setIsRelevance(int isRelevance) {
        this.isRelevance = isRelevance;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getRelevanceType() {
        return relevanceType;
    }

    public void setRelevanceType(int relevanceType) {
        this.relevanceType = relevanceType;
    }

    public List<OrdersBean> getOrders() {
        return orders;
    }

    public void setOrders(List<OrdersBean> orders) {
        this.orders = orders;
    }

    public List<UpdatesBean> getUpdates() {
        return updates;
    }

    public void setUpdates(List<UpdatesBean> updates) {
        this.updates = updates;
    }

    public List<RecordsBean> getRecoreds() {
        return recoreds;
    }

    public void setRecoreds(List<RecordsBean> recoreds) {
        this.recoreds = recoreds;
    }

    public String getCompanyProvinceId() {
        return companyProvinceId;
    }

    public void setCompanyProvinceId(String companyProvinceId) {
        this.companyProvinceId = companyProvinceId;
    }

    public String getCompanyCityId() {
        return companyCityId;
    }

    public void setCompanyCityId(String companyCityId) {
        this.companyCityId = companyCityId;
    }

    public String getCompanyTownId() {
        return companyTownId;
    }

    public void setCompanyTownId(String companyTownId) {
        this.companyTownId = companyTownId;
    }

    public static class OrdersBean {
        /**
         * orderId : 订单编号
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
         * repairCompanyName : 保修时公司
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

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
    }

    public static class UpdatesBean {

        /**
         * createTime : 2018-08-09 10:11:12
         * userPhone : 13592782796
         * userCompanyName : 13592782796
         * maintenanceName : 13592782796
         * updateReason : 测试
         * companyProvinceName :
         * companyCityName :
         * companyTownName :
         */

        private String createTime;
        private String userPhone;
        private String userCompanyName;
        private String maintenanceName;
        private String updateReason;
        private String companyProvinceName;
        private String companyCityName;
        private String companyTownName;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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

        public String getMaintenanceName() {
            return maintenanceName;
        }

        public void setMaintenanceName(String maintenanceName) {
            this.maintenanceName = maintenanceName;
        }

        public String getUpdateReason() {
            return updateReason;
        }

        public void setUpdateReason(String updateReason) {
            this.updateReason = updateReason;
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
    }

    public static class RecordsBean {

        /**
         * createTime : 2018-08-09 10:11:12
         * maintenanceName : 13592782796
         * maintenanceCompanyName : 13592782796
         * updateReason : 13592782796
         * updateType : 2
         * makeTime : 2018-08-09 10:11:12
         */

        private String createTime;
        private String maintenanceName;
        private String maintenanceCompanyName;
        private String updateReason;
        private int updateType;
        private String makeTime;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getMaintenanceName() {
            return maintenanceName;
        }

        public void setMaintenanceName(String maintenanceName) {
            this.maintenanceName = maintenanceName;
        }

        public String getMaintenanceCompanyName() {
            return maintenanceCompanyName;
        }

        public void setMaintenanceCompanyName(String maintenanceCompanyName) {
            this.maintenanceCompanyName = maintenanceCompanyName;
        }

        public String getUpdateReason() {
            return updateReason;
        }

        public void setUpdateReason(String updateReason) {
            this.updateReason = updateReason;
        }

        public int getUpdateType() {
            return updateType;
        }

        public void setUpdateType(int updateType) {
            this.updateType = updateType;
        }

        public String getMakeTime() {
            return makeTime;
        }

        public void setMakeTime(String makeTime) {
            this.makeTime = makeTime;
        }
    }
}

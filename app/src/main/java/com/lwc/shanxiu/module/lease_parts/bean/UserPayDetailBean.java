package com.lwc.shanxiu.module.lease_parts.bean;

import java.util.List;

public class UserPayDetailBean {
    /**
     * pay : [{"createTime":"2020-04-29 11:49:32","orderId":"1588126387979AA","payTime":"2020-04-29 11:49:32","payPrice":10000,"payId":"1","userId":"1568704065473PM"}]
     * order : {"goodsImg":"http://cdn.mixiu365.com/0_1550193535266?imageMogr2/crop/!702x702a11a0","saveSystem":3,"orderId":"1588126387979AA","orderTownName":"莞城区","leaseSpecsType":"规格类型","leaseSpecs":"规格","recommendName":"推荐","isEditor":0,"payLeaseTime":"2020-04-29 11:42:14","labelId":"标签id","phoneSystem":"iPhone 7 Plus","orderTownId":"837","goodsName":"商品名称","orderCityId":"94","orderProvinceName":"广东","orderContactName":"测试","leaseInTime":"2020-04-29 11:42:14","goodsRealNum":0,"statusId":"6","goodsPrice":14300,"isShare":0,"typeId":"商品类型id","typeDetailName":"类型小类","goodsDetailImg":"http://cdn.mixiu365.com/0_1550193638553?imageMogr2/crop/!750x351a0a60,http://cdn.mixiu365.com/0_1550193666950?imageMogr2/crop/!750x351a0a563,http://cdn.mixiu365.com/0_1550193682341?imageMogr2/crop/!750x351a0a983","orderProvinceId":"6","isPush":0,"recommendId":"推荐id","goodsId":"1550193683743LO","typeName":"商品类型","orderContactAddress":"东莞市澳南路与金牛路交叉口_测试号007","goodsParam":"商品参数","attributeId":"1550192911916EV：1550193067486MO,1550193002954JL：1550193253658ZZ,1550193015646XX：1550193109373BT,1550193056341IZ：1550193204332JX","renewalTime":"2020-07-29","orderContactPhone":"13603047489","orderCityName":"东莞","payPrice":9970,"statusName":"退款","attributeName":"品牌：金士顿,频率：DDR3 1600,单套容量：2G,平台：台式机内存","statusDeatilId":"101","labelName":"标签","goodsNum":1,"orderLatitude":23.035073,"leaseSpecsTypeId":"规格类型id","statusDeatilName":"退款中","isValid":1,"userId":"1568704065473PM","createTime":"2020-04-29 10:13:11","orderLongitude":113.756746,"typeDetailId":"类型小类id","goodsDetail":"商品详情"}
     */

    private OrderBean order;
    private List<PayBean> pay;
    private String payPrice;

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public List<PayBean> getPay() {
        return pay;
    }

    public void setPay(List<PayBean> pay) {
        this.pay = pay;
    }

    public static class OrderBean {
        /**
         * goodsImg : http://cdn.mixiu365.com/0_1550193535266?imageMogr2/crop/!702x702a11a0
         * saveSystem : 3
         * orderId : 1588126387979AA
         * orderTownName : 莞城区
         * leaseSpecsType : 规格类型
         * leaseSpecs : 规格
         * recommendName : 推荐
         * isEditor : 0
         * payLeaseTime : 2020-04-29 11:42:14
         * labelId : 标签id
         * phoneSystem : iPhone 7 Plus
         * orderTownId : 837
         * goodsName : 商品名称
         * orderCityId : 94
         * orderProvinceName : 广东
         * orderContactName : 测试
         * leaseInTime : 2020-04-29 11:42:14
         * goodsRealNum : 0
         * statusId : 6
         * goodsPrice : 14300
         * isShare : 0
         * typeId : 商品类型id
         * typeDetailName : 类型小类
         * goodsDetailImg : http://cdn.mixiu365.com/0_1550193638553?imageMogr2/crop/!750x351a0a60,http://cdn.mixiu365.com/0_1550193666950?imageMogr2/crop/!750x351a0a563,http://cdn.mixiu365.com/0_1550193682341?imageMogr2/crop/!750x351a0a983
         * orderProvinceId : 6
         * isPush : 0
         * recommendId : 推荐id
         * goodsId : 1550193683743LO
         * typeName : 商品类型
         * orderContactAddress : 东莞市澳南路与金牛路交叉口_测试号007
         * goodsParam : 商品参数
         * attributeId : 1550192911916EV：1550193067486MO,1550193002954JL：1550193253658ZZ,1550193015646XX：1550193109373BT,1550193056341IZ：1550193204332JX
         * renewalTime : 2020-07-29
         * orderContactPhone : 13603047489
         * orderCityName : 东莞
         * payPrice : 9970
         * statusName : 退款
         * attributeName : 品牌：金士顿,频率：DDR3 1600,单套容量：2G,平台：台式机内存
         * statusDeatilId : 101
         * labelName : 标签
         * goodsNum : 1
         * orderLatitude : 23.035073
         * leaseSpecsTypeId : 规格类型id
         * statusDeatilName : 退款中
         * isValid : 1
         * userId : 1568704065473PM
         * createTime : 2020-04-29 10:13:11
         * orderLongitude : 113.756746
         * typeDetailId : 类型小类id
         * goodsDetail : 商品详情
         */

        private String goodsImg;
        private int saveSystem;
        private String orderId;
        private String orderTownName;
        private String leaseSpecsType;
        private String leaseSpecs;
        private String recommendName;
        private int isEditor;
        private String payLeaseTime;
        private String labelId;
        private String phoneSystem;
        private String orderTownId;
        private String goodsName;
        private String orderCityId;
        private String orderProvinceName;
        private String orderContactName;
        private String leaseInTime;
        private int goodsRealNum;
        private String statusId;
        private int goodsPrice;
        private int isShare;
        private String typeId;
        private String typeDetailName;
        private String goodsDetailImg;
        private String orderProvinceId;
        private int isPush;
        private String recommendId;
        private String goodsId;
        private String typeName;
        private String orderContactAddress;
        private String goodsParam;
        private String attributeId;
        private String renewalTime;
        private String orderContactPhone;
        private String orderCityName;
        private int payPrice;
        private String statusName;
        private String attributeName;
        private String statusDeatilId;
        private String labelName;
        private int goodsNum;
        private double orderLatitude;
        private String leaseSpecsTypeId;
        private String statusDeatilName;
        private int isValid;
        private String userId;
        private String createTime;
        private double orderLongitude;
        private String typeDetailId;
        private String goodsDetail;
        private int leaseMonTime;
        private String havePay; //0否 1是
        private String renewalType; // 是否到期后续费（0否 1是）

        public String getGoodsImg() {
            return goodsImg;
        }

        public void setGoodsImg(String goodsImg) {
            this.goodsImg = goodsImg;
        }

        public int getSaveSystem() {
            return saveSystem;
        }

        public void setSaveSystem(int saveSystem) {
            this.saveSystem = saveSystem;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderTownName() {
            return orderTownName;
        }

        public void setOrderTownName(String orderTownName) {
            this.orderTownName = orderTownName;
        }

        public String getLeaseSpecsType() {
            return leaseSpecsType;
        }

        public void setLeaseSpecsType(String leaseSpecsType) {
            this.leaseSpecsType = leaseSpecsType;
        }

        public String getLeaseSpecs() {
            return leaseSpecs;
        }

        public void setLeaseSpecs(String leaseSpecs) {
            this.leaseSpecs = leaseSpecs;
        }

        public String getRecommendName() {
            return recommendName;
        }

        public void setRecommendName(String recommendName) {
            this.recommendName = recommendName;
        }

        public int getIsEditor() {
            return isEditor;
        }

        public void setIsEditor(int isEditor) {
            this.isEditor = isEditor;
        }

        public String getPayLeaseTime() {
            return payLeaseTime;
        }

        public void setPayLeaseTime(String payLeaseTime) {
            this.payLeaseTime = payLeaseTime;
        }

        public String getLabelId() {
            return labelId;
        }

        public void setLabelId(String labelId) {
            this.labelId = labelId;
        }

        public String getPhoneSystem() {
            return phoneSystem;
        }

        public void setPhoneSystem(String phoneSystem) {
            this.phoneSystem = phoneSystem;
        }

        public String getOrderTownId() {
            return orderTownId;
        }

        public void setOrderTownId(String orderTownId) {
            this.orderTownId = orderTownId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getOrderCityId() {
            return orderCityId;
        }

        public void setOrderCityId(String orderCityId) {
            this.orderCityId = orderCityId;
        }

        public String getOrderProvinceName() {
            return orderProvinceName;
        }

        public void setOrderProvinceName(String orderProvinceName) {
            this.orderProvinceName = orderProvinceName;
        }

        public String getOrderContactName() {
            return orderContactName;
        }

        public void setOrderContactName(String orderContactName) {
            this.orderContactName = orderContactName;
        }

        public String getLeaseInTime() {
            return leaseInTime;
        }

        public void setLeaseInTime(String leaseInTime) {
            this.leaseInTime = leaseInTime;
        }

        public int getGoodsRealNum() {
            return goodsRealNum;
        }

        public void setGoodsRealNum(int goodsRealNum) {
            this.goodsRealNum = goodsRealNum;
        }

        public String getStatusId() {
            return statusId;
        }

        public void setStatusId(String statusId) {
            this.statusId = statusId;
        }

        public int getGoodsPrice() {
            return goodsPrice;
        }

        public void setGoodsPrice(int goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public int getIsShare() {
            return isShare;
        }

        public void setIsShare(int isShare) {
            this.isShare = isShare;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getTypeDetailName() {
            return typeDetailName;
        }

        public void setTypeDetailName(String typeDetailName) {
            this.typeDetailName = typeDetailName;
        }

        public String getGoodsDetailImg() {
            return goodsDetailImg;
        }

        public void setGoodsDetailImg(String goodsDetailImg) {
            this.goodsDetailImg = goodsDetailImg;
        }

        public String getOrderProvinceId() {
            return orderProvinceId;
        }

        public void setOrderProvinceId(String orderProvinceId) {
            this.orderProvinceId = orderProvinceId;
        }

        public int getIsPush() {
            return isPush;
        }

        public void setIsPush(int isPush) {
            this.isPush = isPush;
        }

        public String getRecommendId() {
            return recommendId;
        }

        public void setRecommendId(String recommendId) {
            this.recommendId = recommendId;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getOrderContactAddress() {
            return orderContactAddress;
        }

        public void setOrderContactAddress(String orderContactAddress) {
            this.orderContactAddress = orderContactAddress;
        }

        public String getGoodsParam() {
            return goodsParam;
        }

        public void setGoodsParam(String goodsParam) {
            this.goodsParam = goodsParam;
        }

        public String getAttributeId() {
            return attributeId;
        }

        public void setAttributeId(String attributeId) {
            this.attributeId = attributeId;
        }

        public String getRenewalTime() {
            return renewalTime;
        }

        public void setRenewalTime(String renewalTime) {
            this.renewalTime = renewalTime;
        }

        public String getOrderContactPhone() {
            return orderContactPhone;
        }

        public void setOrderContactPhone(String orderContactPhone) {
            this.orderContactPhone = orderContactPhone;
        }

        public String getOrderCityName() {
            return orderCityName;
        }

        public void setOrderCityName(String orderCityName) {
            this.orderCityName = orderCityName;
        }

        public int getPayPrice() {
            return payPrice;
        }

        public void setPayPrice(int payPrice) {
            this.payPrice = payPrice;
        }

        public String getStatusName() {
            return statusName;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }

        public String getAttributeName() {
            return attributeName;
        }

        public void setAttributeName(String attributeName) {
            this.attributeName = attributeName;
        }

        public String getStatusDeatilId() {
            return statusDeatilId;
        }

        public void setStatusDeatilId(String statusDeatilId) {
            this.statusDeatilId = statusDeatilId;
        }

        public String getLabelName() {
            return labelName;
        }

        public void setLabelName(String labelName) {
            this.labelName = labelName;
        }

        public int getGoodsNum() {
            return goodsNum;
        }

        public void setGoodsNum(int goodsNum) {
            this.goodsNum = goodsNum;
        }

        public double getOrderLatitude() {
            return orderLatitude;
        }

        public void setOrderLatitude(double orderLatitude) {
            this.orderLatitude = orderLatitude;
        }

        public String getLeaseSpecsTypeId() {
            return leaseSpecsTypeId;
        }

        public void setLeaseSpecsTypeId(String leaseSpecsTypeId) {
            this.leaseSpecsTypeId = leaseSpecsTypeId;
        }

        public String getStatusDeatilName() {
            return statusDeatilName;
        }

        public void setStatusDeatilName(String statusDeatilName) {
            this.statusDeatilName = statusDeatilName;
        }

        public int getIsValid() {
            return isValid;
        }

        public void setIsValid(int isValid) {
            this.isValid = isValid;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public double getOrderLongitude() {
            return orderLongitude;
        }

        public void setOrderLongitude(double orderLongitude) {
            this.orderLongitude = orderLongitude;
        }

        public String getTypeDetailId() {
            return typeDetailId;
        }

        public void setTypeDetailId(String typeDetailId) {
            this.typeDetailId = typeDetailId;
        }

        public String getGoodsDetail() {
            return goodsDetail;
        }

        public void setGoodsDetail(String goodsDetail) {
            this.goodsDetail = goodsDetail;
        }

        public String getHavePay() {
            return havePay;
        }

        public void setHavePay(String havePay) {
            this.havePay = havePay;
        }

        public int getLeaseMonTime() {
            return leaseMonTime;
        }

        public void setLeaseMonTime(int leaseMonTime) {
            this.leaseMonTime = leaseMonTime;
        }

        public String getRenewalType() {
            return renewalType;
        }

        public void setRenewalType(String renewalType) {
            this.renewalType = renewalType;
        }
    }

    public static class PayBean {
        /**
         * createTime : 2020-04-29 11:49:32
         * orderId : 1588126387979AA
         * payTime : 2020-04-29 11:49:32
         * payPrice : 10000
         * payId : 1
         * userId : 1568704065473PM
         */

        private String createTime;
        private String orderId;
        private String payTime;
        private String payPrice;
        private String payId;
        private String userId;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public String getPayPrice() {
            return payPrice;
        }

        public void setPayPrice(String payPrice) {
            this.payPrice = payPrice;
        }

        public String getPayId() {
            return payId;
        }

        public void setPayId(String payId) {
            this.payId = payId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

    public String getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(String payPrice) {
        this.payPrice = payPrice;
    }


}

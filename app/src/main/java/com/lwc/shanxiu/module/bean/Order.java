package com.lwc.shanxiu.module.bean;

import com.lwc.shanxiu.module.partsLib.ui.bean.PartsDetailBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 何栋
 * @version 1.0
 * @date 2017/3/9 16:46
 * @email 294663966@qq.com
 * 订单
 */
public class Order implements Serializable{

    public static final int STATUS_DAIJIEDAN = 1;//待接单
    public static final int STATUS_JIEDAN = 2;//已接单
    public static final int STATUS_CHULI = 3;//处理中
    public static final int STATUS_ZHUANDAN = 4;//转单待确认
    public static final int STATUS_TONGYIFENJI = 5;//同意分级待对方确认
    public static final int STATUS_BAOJIA = 6;//已报价待确认
    public static final int STATUS_GUAQI = 7;//已挂起
    public static final int STATUS_YIWANCHENGDAIQUEREN = 8;//已完成待确认
    public static final int STATUS_DAIFANXIU = 9;//待返修
    public static final int STATUS_JUJUEFANXIU = 10;//拒绝返修
    public static final int STATUS_YIWANCHENG = 11;//已完成
    public static final int STATUS_YIPINGJIA = 12;//已评价
    public static final int STATUS_SHOUHOU = 13;//售后
    public static final int STATUS_YIQUXIAO = 14;//已取消
    public static final int STATUS_JUJUEWANGCHENG = 15;//用户拒绝完成
    public static final int STATUS_DAODAXIANCHANG = 16;//到达现场
    public static final int STATUS_JIANCEBAOJIA = 17;//检测并报价
    public static final int STATUS_QUERENBAOJIA = 18;//用户确认报价并支付
    public static final int STATUS_SHENQINGFANCHANG = 19;//申请返厂待确认
    public static final int STATUS_FANCHANGZHONG = 20;//返厂中
    public static final int STATUS_SONGHUIANZHUANG = 21;//送回安装待确认
    public static final int STATUS_FANCHANGGUAQI = 22;//个人订单返厂挂起 未报价

    private String orderId;//": "170831102758556102HB",         //订单ID
    private int statusId;//": "广东立升科技有限公司"          //状态ID
    private String statusName;//": "170831102758556102HB",     //状态名称
    private String deviceTypeName;//": "设备类型名称"          //设备类型名称
    private String reqairName;//": "故障名称",                   //故障名称
    private String orderDescription;//": "订单备注"               //订单备注
    private String orderImage;
    private String createTime;//": "订单创建时间",               //订单创建时间
    private String userHeadImage;//": "用户头像"               //用户头像
    private String userRealname;// ": "用户姓名",               //用户姓名
    private String userPhone;// ": "用户电话"                   //用户电话
    private String userCompanyName;// ": "用户公司名称",       //用户公司名称
    private String companyAddress;// ": "用户公司地址"          //用户公司地址
    private String orderLatitude;// ": "订单经度",                //订单经度
    private String orderLongitude;// ": "订单纬度"               //订单纬度
    private String  orderContactName;// ": "订单联系人",        //订单联系人
    private String orderContactAddress;// ": "订单联系地址",    //订单联系地址
    private String orderContactPhone;//": "订单联系电话",      //订单联系电话
    private String deviceTypeMold;//": "设备类型",      //设备类型
    private String isShare;//订单是否已分享 0：否 1：是
    private List<PartsDetailBean> accessories;//配件信息

    private String maintenanceId;//订单接单人ID
    private String waitMaintenanceId;//订单待结单人ID
    private String orderCompanyName;//用户报修指定的维修公司
    private String orderType;// ": "订单类型",              //订单类型(1.个人订单 2.政府订单, 3.厂家售后）
    private boolean hasRecord;//": "是否有检修记录 Bool类型",
    private String visitCost;//": "上门费",
    private String maintainCost;//": "维修费",
    private String otherCost;//": "其他费用",
    private boolean hasSettlement;//": "是否有结算记录  Bool类型",
    private String sumCost;//": "总金额",
    private int settlementStatus;//": "结算状态（1.未支付 2.已支付）",
    private String faultType;// "故障类型(1.软件 2.硬件 3.其他）",
    private String remark;//费用详情
    private int isSecrecy; //是否是私密定单（0：否 1：是）
    private int hasAward;//是否是奖励订单（0：否 1：是）"
    private String qrcodeIndex;//二维码id
    private String repairCompanyName;//维修公司名称
    private int isWarranty; //是否过保（0：否；1：是）
    private Manufactor manufactor;//售后厂家信息
    private String discountAmount;//优惠金额
    private List<Malfunction> orderRepairs;
    private String hardwareCost;
    private String packageType;

    public String getHardwareCost() {
        return hardwareCost;
    }

    public void setHardwareCost(String hardwareCost) {
        this.hardwareCost = hardwareCost;
    }

    public List<Malfunction> getOrderRepairs() {
        return orderRepairs;
    }

    public void setOrderRepairs(List<Malfunction> orderRepairs) {
        this.orderRepairs = orderRepairs;
    }
    public int getIsWarranty() {
        return isWarranty;
    }

    public void setIsWarranty(int isWarranty) {
        this.isWarranty = isWarranty;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Manufactor getManufactor() {
        return manufactor;
    }

    public void setManufactor(Manufactor manufactor) {
        this.manufactor = manufactor;
    }

    public String getRepairCompanyName() {
        return repairCompanyName;
    }

    public void setRepairCompanyName(String repairCompanyName) {
        this.repairCompanyName = repairCompanyName;
    }

    public String getQrcodeIndex() {
        return qrcodeIndex;
    }

    public void setQrcodeIndex(String qrcodeIndex) {
        this.qrcodeIndex = qrcodeIndex;
    }

    public int getHasAward() {
        return hasAward;
    }

    public void setHasAward(int hasAward) {
        this.hasAward = hasAward;
    }

    public int getIsSecrecy() {
        return isSecrecy;
    }

    public void setIsSecrecy(int isSecrecy) {
        this.isSecrecy = isSecrecy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFaultType() {
        return faultType;
    }

    public String getDeviceTypeMold() {
        return deviceTypeMold;
    }

    public void setDeviceTypeMold(String deviceTypeMold) {
        this.deviceTypeMold = deviceTypeMold;
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
    }

    public int getSettlementPlatform() {
        return settlementPlatform;
    }

    public void setSettlementPlatform(int settlementPlatform) {
        this.settlementPlatform = settlementPlatform;
    }

    public int getSettlementStatus() {
        return settlementStatus;
    }

    public void setSettlementStatus(int settlementStatus) {
        this.settlementStatus = settlementStatus;
    }

    public String getSumCost() {
        return sumCost;
    }

    public void setSumCost(String sumCost) {
        this.sumCost = sumCost;
    }

    public boolean isHasSettlement() {
        return hasSettlement;
    }

    public void setHasSettlement(boolean hasSettlement) {
        this.hasSettlement = hasSettlement;
    }

    public String getOtherCost() {
        return otherCost;
    }

    public void setOtherCost(String otherCost) {
        this.otherCost = otherCost;
    }

    public String getMaintainCost() {
        return maintainCost;
    }

    public void setMaintainCost(String maintainCost) {
        this.maintainCost = maintainCost;
    }

    public String getVisitCost() {
        return visitCost;
    }

    public void setVisitCost(String visitCost) {
        this.visitCost = visitCost;
    }

    public boolean isHasRecord() {
        return hasRecord;
    }

    public void setHasRecord(boolean hasRecord) {
        this.hasRecord = hasRecord;
    }

    private int settlementPlatform;//": "支付平台(1.余额 2.支付宝 3.微信)

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(String maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public String getWaitMaintenanceId() {
        return waitMaintenanceId;
    }

    public void setWaitMaintenanceId(String waitMaintenanceId) {
        this.waitMaintenanceId = waitMaintenanceId;
    }

    public String getOrderContactName() {
        return orderContactName;
    }

    public void setOrderContactName(String orderContactName) {
        this.orderContactName = orderContactName;
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

    public String getOrderImage() {
        return orderImage;
    }

    public void setOrderImage(String orderImage) {
        this.orderImage = orderImage;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
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

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserRealname() {
        return userRealname;
    }

    public void setUserRealname(String userRealname) {
        this.userRealname = userRealname;
    }

    public String getUserHeadImage() {
        return userHeadImage;
    }

    public void setUserHeadImage(String userHeadImage) {
        this.userHeadImage = userHeadImage;
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

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getOrderLatitude() {
        return orderLatitude;
    }

    public void setOrderLatitude(String orderLatitude) {
        this.orderLatitude = orderLatitude;
    }

    public String getOrderLongitude() {
        return orderLongitude;
    }

    public void setOrderLongitude(String orderLongitude) {
        this.orderLongitude = orderLongitude;
    }


    public String getOrderCompanyName() {
        return orderCompanyName;
    }

    public void setOrderCompanyName(String orderCompanyName) {
        this.orderCompanyName = orderCompanyName;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public class Manufactor implements Serializable{
        /**
         * manufactorAddress : 东莞
         * manufactorName : 联想
         * manufactorContacts : 李先生
         * manufactorPhone : 13592782796
         * manufactorId : 1531134382694TM
         */

        private String manufactorAddress;//厂家地址
        private String manufactorName;//厂家名称
        private String manufactorContacts;//厂家联系人
        private String manufactorPhone;//厂家联系电话
        private String manufactorId;

        public String getManufactorAddress() {
            return manufactorAddress;
        }

        public void setManufactorAddress(String manufactorAddress) {
            this.manufactorAddress = manufactorAddress;
        }

        public String getManufactorName() {
            return manufactorName;
        }

        public void setManufactorName(String manufactorName) {
            this.manufactorName = manufactorName;
        }

        public String getManufactorContacts() {
            return manufactorContacts;
        }

        public void setManufactorContacts(String manufactorContacts) {
            this.manufactorContacts = manufactorContacts;
        }

        public String getManufactorPhone() {
            return manufactorPhone;
        }

        public void setManufactorPhone(String manufactorPhone) {
            this.manufactorPhone = manufactorPhone;
        }

        public String getManufactorId() {
            return manufactorId;
        }

        public void setManufactorId(String manufactorId) {
            this.manufactorId = manufactorId;
        }
    }

    public String getIsShare() {
        return isShare;
    }

    public void setIsShare(String isShare) {
        this.isShare = isShare;
    }

    public List<PartsDetailBean> getAccessories() {
        return accessories;
    }

    public void setAccessories(List<PartsDetailBean> accessories) {
        this.accessories = accessories;
    }
}

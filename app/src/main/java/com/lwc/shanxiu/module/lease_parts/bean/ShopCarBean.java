package com.lwc.shanxiu.module.lease_parts.bean;

import java.io.Serializable;

public class ShopCarBean implements Serializable {
    /**
     * goodsImg : http://cdn.mixiu365.com/0_1550193535266?imageMogr2/crop/!702x702a11a0
     * goodsDetailImg : http://cdn.mixiu365.com/0_1550193638553?imageMogr2/crop/!750x351a0a60,http://cdn.mixiu365.com/0_1550193666950?imageMogr2/crop/!750x351a0a563,http://cdn.mixiu365.com/0_1550193682341?imageMogr2/crop/!750x351a0a983
     * recommendId : 推荐id
     * leaseSpecsType : 规格类型
     * goodsId : 1550193683743LO
     * leaseSpecs : 规格
     * typeName : 商品类型
     * goodsParam : 商品参数
     * recommendName : 推荐
     * attributeId : 1550192911916EV：1550193067486MO,1550193002954JL：1550193253658ZZ,1550193015646XX：1550193109373BT,1550193056341IZ：1550193204332JX
     * labelId : 标签id
     * attributeName : 品牌：金士顿,频率：DDR3 1600,单套容量：2G,平台：台式机内存
     * labelName : 标签
     * goodsName : 商品名称
     * goodsNum : 1
     * leaseSpecsTypeId : 规格类型id
     * isValid : 1
     * userId : 1568704065473PM
     * carId : 1588129945756QQ
     * createTime : 2020-04-29 11:12:25
     * goodsPrice : 14300
     * typeId : 商品类型id
     * typeDetailName : 类型小类
     * typeDetailId : 类型小类id
     * goodsDetail : 商品详情
     */

    private String goodsImg;
    private String goodsDetailImg;
    private String recommendId;
    private String leaseSpecsType;
    private String goodsId;
    private String leaseSpecs;
    private String typeName;
    private String goodsParam;
    private String recommendName;
    private String attributeId;
    private String labelId;
    private String attributeName;
    private String labelName;
    private String goodsName;
    private int goodsNum;
    private String leaseSpecsTypeId;
    private int isValid;
    private String userId;
    private String carId;
    private String createTime;
    private String goodsPrice;
    private String typeId;
    private String typeDetailName;
    private String typeDetailId;
    private String goodsDetail;
    private String payType; //（1月付 2季付）
    private String leaseMonTime;
    private boolean isChecked = false;
    private String remark;

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsDetailImg() {
        return goodsDetailImg;
    }

    public void setGoodsDetailImg(String goodsDetailImg) {
        this.goodsDetailImg = goodsDetailImg;
    }

    public String getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(String recommendId) {
        this.recommendId = recommendId;
    }

    public String getLeaseSpecsType() {
        return leaseSpecsType;
    }

    public void setLeaseSpecsType(String leaseSpecsType) {
        this.leaseSpecsType = leaseSpecsType;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getLeaseSpecs() {
        return leaseSpecs;
    }

    public void setLeaseSpecs(String leaseSpecs) {
        this.leaseSpecs = leaseSpecs;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getGoodsParam() {
        return goodsParam;
    }

    public void setGoodsParam(String goodsParam) {
        this.goodsParam = goodsParam;
    }

    public String getRecommendName() {
        return recommendName;
    }

    public void setRecommendName(String recommendName) {
        this.recommendName = recommendName;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getLeaseSpecsTypeId() {
        return leaseSpecsTypeId;
    }

    public void setLeaseSpecsTypeId(String leaseSpecsTypeId) {
        this.leaseSpecsTypeId = leaseSpecsTypeId;
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

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getLeaseMonTime() {
        return leaseMonTime;
    }

    public void setLeaseMonTime(String leaseMonTime) {
        this.leaseMonTime = leaseMonTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

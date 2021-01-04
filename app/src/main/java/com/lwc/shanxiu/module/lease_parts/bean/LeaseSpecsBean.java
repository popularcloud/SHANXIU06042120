package com.lwc.shanxiu.module.lease_parts.bean;

import java.io.Serializable;

public class LeaseSpecsBean implements Serializable {
    /**
     * leaseSpecsTypeId : 1
     * leaseSpecsType : 电脑
     * goodsId : 1
     * leaseSpecs : 规格
     * goodsPrice : 1000
     * isValid : 1
     * id : 1
     * goodsName : 商品名称
     */

    private String leaseSpecsTypeId;
    private String leaseSpecsType;
    private String goodsId;
    private String leaseSpecs;
    private int goodsPrice;
    private int isValid;
    private String id;
    private String goodsName;
    private int num;

    public String getLeaseSpecsTypeId() {
        return leaseSpecsTypeId;
    }

    public void setLeaseSpecsTypeId(String leaseSpecsTypeId) {
        this.leaseSpecsTypeId = leaseSpecsTypeId;
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

    public int getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(int goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

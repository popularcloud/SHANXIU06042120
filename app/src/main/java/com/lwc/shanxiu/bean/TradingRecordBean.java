package com.lwc.shanxiu.bean;

import android.text.TextUtils;

import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.utils.PatternUtil;

import java.io.Serializable;

/**
 * 交易记录
 * 
 * @Description TODO
 * @author cc
 * @date 2016年4月24日
 * @Copyright: lwc
 */
public class TradingRecordBean implements Serializable {

	/** 变量描述 */
	private static final long serialVersionUID = 1L;
	private String createTime;//": "2017-11-30 11:44:53",         //创建时间
	private String transactionAmount;//": 888,                  //金额（前端要除100）
	private String transactionMeans;//": 1,                     //交易方式（1.红包 2.支付宝 3.微信）
	private String paymentType;//": 0,                        //收支类型 （0.转入 1.转出）
	private String transactionNo;//": "151201349368690300",     //交易单号
	private String transactionStatus;//": 1,                 //交易状态(1.已完成 2.处理中 3.审核失败)
	private String transactionRemark;//": "通过活动：双十二红包雨，获取奖金8.88元"      //备注
	private int transactionScene;
	private String userRole;  //用户类型（3：政府用户 4：维修师 5：个人用户 6:公司）
	private String objectId;  //订单号

	public int getTransactionScene() {
		return transactionScene;
	}

	public void setTransactionScene(int transactionScene) {
		this.transactionScene = transactionScene;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getTransactionAmount() {
		if (!TextUtils.isEmpty(transactionAmount) && PatternUtil.isNumer(transactionAmount)){
			return Utils.chu(transactionAmount, "100");
		}
		return transactionAmount;
	}

	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getTransactionMeans() {
		return transactionMeans;
	}

	public void setTransactionMeans(String transactionMeans) {
		this.transactionMeans = transactionMeans;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getTransactionRemark() {
		return transactionRemark;
	}

	public void setTransactionRemark(String transactionRemark) {
		this.transactionRemark = transactionRemark;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
}

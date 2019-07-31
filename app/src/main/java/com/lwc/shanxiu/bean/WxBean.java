package com.lwc.shanxiu.bean;

import java.io.Serializable;

/**
 * 支付信息
 * 
 * @Description TODO
 * @author cc
 * @date 2016年4月24日
 * @Copyright: lwc
 */
public class WxBean implements Serializable {

	/** 变量描述 */
	private static final long serialVersionUID = 1L;
	private String sign;//
	private String tradeid;//":
	private String timeStamp;//
	private String packageStr;
	private String partnerId;
	private String appid;
	private String nonceStr;
	private String prepayId;
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTradeid() {
		return tradeid;
	}

	public void setTradeid(String tradeid) {
		this.tradeid = tradeid;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getPackageStr() {
		return packageStr;
	}

	public void setPackageStr(String packageStr) {
		this.packageStr = packageStr;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}
}

package com.lwc.shanxiu.module.bean;

import java.io.Serializable;

/**
 * 
 * 广告图
 *	
 */
public class ADInfo implements Serializable{

	private String advertisingId;//": "123",                                 //广告ID
	private String advertisingImageUrl;//": "http://g.hiphotos.baidu.com/image/pic/item/c2fdfc039245d68802b0694eaec27d1ed31b24ae.jpg",                                                     //图片地址
	private String advertisingUrl;//": "information/details/123",               //点击跳转地址
	private String advertisingTitle;//": "标题",                             //标题
	private String createTime;//": "2017-12-6 15:06:57",                     //创建时间
	private String imageLocalhost;//": 1,                                  //广告位置（1：资讯）
	private String isValid;//": 1,                                         //是否有效
	private String modifyTime;//": "2017-12-06 15:08:08",                   //修改时间
	private String sn;//": 0

	public ADInfo(){

	}

	public ADInfo(String imgUrl){
		advertisingImageUrl = imgUrl;
	}

	public String getAdvertisingId() {
		return advertisingId;
	}

	public void setAdvertisingId(String advertisingId) {
		this.advertisingId = advertisingId;
	}

	public String getAdvertisingImageUrl() {
		return advertisingImageUrl;
	}

	public void setAdvertisingImageUrl(String advertisingImageUrl) {
		this.advertisingImageUrl = advertisingImageUrl;
	}

	public String getAdvertisingUrl() {
		return advertisingUrl;
	}

	public void setAdvertisingUrl(String advertisingUrl) {
		this.advertisingUrl = advertisingUrl;
	}

	public String getAdvertisingTitle() {
		return advertisingTitle;
	}

	public void setAdvertisingTitle(String advertisingTitle) {
		this.advertisingTitle = advertisingTitle;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getImageLocalhost() {
		return imageLocalhost;
	}

	public void setImageLocalhost(String imageLocalhost) {
		this.imageLocalhost = imageLocalhost;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
}

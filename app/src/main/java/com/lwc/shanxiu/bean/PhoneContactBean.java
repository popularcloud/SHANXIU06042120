package com.lwc.shanxiu.bean;

import com.lwc.shanxiu.utils.PinyinUtil;

import android.annotation.SuppressLint;
import android.text.TextUtils;

/**
 * 手机联系人属性
 * 
 * @Description TODO
 * @author CodeApe
 * @version 1.0
 * @date 2013-12-12
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 * 
 */
@SuppressLint("DefaultLocale")
public class PhoneContactBean {

	/** 联系人ID */
	private Long contactID;
	/** 照片ID */
	private Long photoID;

	/** 首字母 */
	private String headChar;
	/** 姓名 */
	private String name;
	/** 头像 */
	private String icon;
	/** 手机号码 */
	private String phone;
	/** 姓名拼音 */
	private String pinyin;
	/** 姓名拼音首字母 */
	private String pinyinHead;
	/** 迪讯号 */
	private String c_user_no;

	/** 用户在平台注册的唯一标识 */
	private String userName;
	/** 用户详情Id */
	private String userInfoId;

	public String getC_user_no() {
		return c_user_no;
	}

	public void setC_user_no(String c_user_no) {
		this.c_user_no = c_user_no;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getHeadChar() {
		return headChar;
	}

	public void setHeadChar(String headChar) {
		this.headChar = headChar;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		if (TextUtils.isEmpty(name)) {
			setPinyin(PinyinUtil.toHanyuPinyinString(name).toUpperCase());
			setPinyinHead(PinyinUtil.toHanyuPinyinHeadString(name).toUpperCase());
		} else {
			setPinyin(PinyinUtil.toHanyuPinyinString(name).toUpperCase());
			setPinyinHead(PinyinUtil.toHanyuPinyinHeadString(name).toUpperCase());
		}
		char head = getPinyin().charAt(0);
		if (head < 'A' || head > 'Z') {
			head = '#';
		}
		setHeadChar(head + "");
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getPinyinHead() {
		return pinyinHead;
	}

	public void setPinyinHead(String pinyinHead) {
		this.pinyinHead = pinyinHead;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
		if (TextUtils.isEmpty(name)) {
			// setPinyin(PinyinUtil.toHanyuPinyinString(name).toUpperCase());
			setPinyinHead(PinyinUtil.toHanyuPinyinHeadString(name).toUpperCase());
		} else {
			setPinyin(PinyinUtil.toHanyuPinyinString(name).toUpperCase());
			setPinyinHead(PinyinUtil.toHanyuPinyinHeadString(name).toUpperCase());
		}
		char head = getPinyin().charAt(0);
		if (head < 'A' || head > 'Z') {
			head = '#';
		}
		setHeadChar(head + "");
	}

	public String getUserInfoId() {
		return userInfoId;
	}

	public void setUserInfoId(String userInfoId) {
		this.userInfoId = userInfoId;
	}

	public Long getContactID() {
		return contactID;
	}

	public void setContactID(Long contactID) {
		this.contactID = contactID;
	}

	public Long getPhotoID() {
		return photoID;
	}

	public void setPhotoID(Long photoID) {
		this.photoID = photoID;
	}

}

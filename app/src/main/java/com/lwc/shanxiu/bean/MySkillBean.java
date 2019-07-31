package com.lwc.shanxiu.bean;

import java.io.Serializable;

/**
 * 我的技能bean对象
 * 
 * @Description TODO
 * @author cc
 * @date 2016年4月24日
 * @Copyright: lwc
 */
public class MySkillBean implements Serializable {

	/** 变量描述 */
	private static final long serialVersionUID = 1L;

	private String deviceTypeName;//": "打印机",            //维修的设备类型名称
	private String skills;//": "打印卡纸,打印不清晰",           //维修的技能名称
	private String deviceTypeId;//": "170831102758556102YH" //维修的设备类型ID

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	public String getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(String deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}
}

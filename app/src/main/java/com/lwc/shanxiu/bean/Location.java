package com.lwc.shanxiu.bean;

import java.io.Serializable;

/**
 * 经纬度
 * 
 * @Description TODO
 * @author cc
 * @date 2015年12月15日
 * @Copyright: lwc
 */
public class Location implements Serializable {

	/** 变量描述 */
	private static final long serialVersionUID = 1L;
	private double longitude;
	private double latitude;
	private String strValue;
	private String cityName;
	private String cityCode;

	public String getStrValue() {
		return strValue;
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
}

package com.lwc.shanxiu.bean;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;

public class NearBean extends BaseBean {

	/** 变量描述 */
	private static final long serialVersionUID = 1L;
	// "uid": 10008,
	// "sex": 1,
	// "lon": 113.865068,
	// "phone": "13412744212",
	// "status": 1,
	// "location": 1404,
	// "ctime": 1451956922982,
	// "cid": 13,
	// "companyname": "广东立升科技有限公司",
	// "createtime": "2016-01-05 09:22:02",
	// "username": "张志荣",
	// "email": "349877976@qq.com",
	// "address": "南城，莞城，东城",
	// "rid": 4,
	// "realname": "张志荣",
	// "lat": 22.567123
	// avgservice服务avgspecialty专业avgreply上门
	private String companyname;
	private String name;
	private String lon;
	private String lat;
	private String username;
	private String uid;
	private String sex;
	private String phone;
	private String hasorder;
	private String skills;
	private String picture;
	private String distance;
	private String star;
	private double avgservice;
	private double avgspecialty;
	private double avgreply;
	private String msg;
	private String ordercount;
	private Bitmap bitmap;

	@Override
	protected void init(JSONObject jSon) throws JSONException {
		// TODO Auto-generated method stub
		// "uid": 10003,
		// "sex": 1,
		// "lon": 113.861068,
		// "phone": "12345678912",
		// "status": 1,
		// "location": 1634,
		// "ctime": 1451895367200,
		// "cid": 13,
		// "companyname": "广东立升科技有限公司",
		// "createtime": "2016-01-04 16:16:07",
		// "username": "test0769",
		// "rid": 4,
		// "email": "aaa@qq.com",
		// "lat": 22.565233
		setCompanyname(jSon.optString("companyname"));
		setName(jSon.optString("realname"));
		setLon(jSon.optString("lon"));
		setLat(jSon.optString("lat"));
		setUsername(jSon.optString("username"));
		setPhone(jSon.optString("phone"));
		setUid(jSon.optString("uid"));
		setHasorder(jSon.optString("hasorder"));
		setSkills(jSon.optString("skills"));
		setPicture(jSon.optString("picture"));
		setDistance(jSon.optString("location", "0"));
		setStar(jSon.optString("star", "5"));
		setMsg(jSon.optString("", "--"));
		setOrdercount(jSon.optString("ordercount", "0"));
		setAvgreply(jSon.optDouble("avgreply"));
		setAvgservice(jSon.optDouble("avgservice"));
		setAvgspecialty(jSon.optDouble("avgspecialty"));
	}

	public double getAvgservice() {
		return avgservice;
	}

	public double getAvgspecialty() {
		return avgspecialty;
	}

	public double getAvgreply() {
		return avgreply;
	}

	public void setAvgservice(double avgservice) {
		this.avgservice = avgservice;
	}

	public void setAvgspecialty(double avgspecialty) {
		this.avgspecialty = avgspecialty;
	}

	public void setAvgreply(double avgreply) {
		this.avgreply = avgreply;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public String getOrdercount() {
		return ordercount;
	}

	public void setOrdercount(String ordercount) {
		this.ordercount = ordercount;
	}

	public String getDistance() {
		return distance;
	}

	public String getStar() {
		return star;
	}

	public String getMsg() {
		return msg;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public void setStar(String star) {
		this.star = star;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getHasorder() {
		return hasorder;
	}

	public void setHasorder(String hasorder) {
		this.hasorder = hasorder;
	}

	public String getUid() {
		return uid;
	}

	public String getSex() {
		return sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCompanyname() {
		return companyname;
	}

	public String getName() {
		return name;
	}

	public String getLon() {
		return lon;
	}

	public String getLat() {
		return lat;
	}

	public String getUsername() {
		return username;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}

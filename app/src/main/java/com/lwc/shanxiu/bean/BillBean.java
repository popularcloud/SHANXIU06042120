package com.lwc.shanxiu.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 报价bean对象
 * 
 * @Description TODO
 * @author cc
 * @date 2015年12月15日
 * @Copyright: lwc
 */
public class BillBean extends BaseBean {

	/** 变量描述 */
	private static final long serialVersionUID = 1L;
	// "createtime": "2015-12-19 17:19:05",
	// "uid": 21,
	// "price": 12,
	// "status": 0,
	// "oid": 17,
	// "uqid": 4,
	// "fuid": 10,
	// "ctime": 1450516745101

	private String createtime;
	private String uid;
	private String price;
	private String status;
	private String oid;
	private String uqid;
	private String fuid;
	private String ctime;
	private String remark;

	@Override
	protected void init(JSONObject jSon) throws JSONException {
		setCreatetime(jSon.optString("createtime"));
		setUid(jSon.optString("fuid"));
		setPrice(jSon.optString("price"));
		setStatus(jSon.optString("status"));
		setOid(jSon.optString("oid"));
		setUqid(jSon.optString("uqid"));
		setFuid(jSon.optString("fuid"));
		setCtime(jSon.optString("ctime"));
		setRemark(jSon.optString("remark"));
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreatetime() {
		return createtime;
	}

	public String getUid() {
		return uid;
	}

	public String getPrice() {
		return price;
	}

	public String getStatus() {
		return status;
	}

	public String getOid() {
		return oid;
	}

	public String getUqid() {
		return uqid;
	}

	public String getFuid() {
		return fuid;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public void setUqid(String uqid) {
		this.uqid = uqid;
	}

	public void setFuid(String fuid) {
		this.fuid = fuid;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

}

package com.lwc.shanxiu.bean;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

/**
 * 菜单数据bean对象
 * 
 * @Description TODO
 * @author cc
 * @date 2015年11月11日
 * @Copyright: lwc
 */
public class MenuBean extends BaseBean {

	/** 变量描述 */
	private static final long serialVersionUID = 1L;
	/** id */
	private String id;
	/** 名称 */
	private String name;
	/** 描述 */
	private String description;
	/** 本地资源 */
	private int res;
	private boolean isChecked;

	// ------------以下是设备详情参数---------------------------------------
	// "dpstatus": 1,
	// "dpid": 6,
	// "name": "显卡",
	// "ctime": 1448378953480,
	// "id": 37,
	// "type": "string",
	// "value": "独显2G",
	// "did": 1,
	// "status": 1
	/**  */
	private String dpstatus;
	/** 设备类型属性id */
	private String dpid;
	/**  */
	private String ctime;
	/**  */
	private String value;
	/** 设备 */
	private String did;
	/**  */
	private String status;
	/**  */
	private String udpid;

	// ------------以下是公司菜单参数---------------------------------------
	// "companyname": "admin3",
	// "cid": "2"
	/** 公司名字 */

	/** 公司id */

	@Override
	protected void init(JSONObject jSon) throws JSONException {
		// "udpid": 82,
		// "status": 1,
		// "name": "cpu",
		// "value": "dfg",
		// "did": 1,
		// "dpid": 1,
		// "udid": 30,
		// "type": "string",
		// "ctime": 1450187772945,
		// "dpstatus": 1

		setId(jSon.optString("id"));
		setName(jSon.optString("name"));
		setDescription(jSon.optString("value"));
		setDpid(jSon.optString("dpid"));
		setDid(jSon.optString("did"));
		setUdpid(jSon.optString("udpid"));
		setStatus(jSon.optString("status"));
		setDpstatus(jSon.optString("dpstatus"));

		// --公司菜单属性
		if (!TextUtils.isEmpty(jSon.optString("companyname"))) {
			setName(jSon.optString("companyname"));
			setId(jSon.optString("cid"));
		}
		// --公司菜单属性
		if (!TextUtils.isEmpty(jSon.optString("parentid"))) {
			setName(jSon.optString("name"));
			setId(jSon.optString("dmId"));
		}
		// --公司菜单属性
		if (!TextUtils.isEmpty(jSon.optString("parentid"))) {
			setName(jSon.optString("name"));
			setId(jSon.optString("dmId"));
		}
		setChecked(false);
	}

	public String getUdpid() {
		return udpid;
	}

	public void setUdpid(String udpid) {
		this.udpid = udpid;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public String getDpstatus() {
		return dpstatus;
	}

	public String getDpid() {
		return dpid;
	}

	public String getCtime() {
		return ctime;
	}

	public String getValue() {
		return value;
	}

	public String getDid() {
		return did;
	}

	public String getStatus() {
		return status;
	}

	public void setDpstatus(String dpstatus) {
		this.dpstatus = dpstatus;
	}

	public void setDpid(String dpid) {
		this.dpid = dpid;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getRes() {
		return res;
	}

	public void setRes(int res) {
		this.res = res;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static MenuBean copy(MenuBean menuBean) {
		MenuBean m = new MenuBean();
		m.setId(menuBean.getId());
		m.setName(menuBean.getName());
		m.setDescription(menuBean.getDescription());
		m.setDpid(menuBean.getDpid());
		m.setDid(menuBean.getDid());
		m.setUdpid(menuBean.getUdpid());
		m.setStatus(menuBean.getStatus());
		m.setDpstatus(menuBean.getDpstatus());
		m.setChecked(false);
		return m;
	}

}

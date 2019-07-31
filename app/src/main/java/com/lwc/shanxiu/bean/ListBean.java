package com.lwc.shanxiu.bean;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 用于解析Json数据列表的bean
 *
 * @Description TODO
 * @author CodeApe
 * @version 1.0
 * @date 2014年12月31日
 * @Copyright: Copyright (c) 2014 Shenzhen Utoow Technology Co., Ltd. All rights reserved.
 *
 */
public class ListBean extends BaseBean {

	/**
	 * 
	 * @author CodeApe
	 * @version 1.0
	 * @date 2014年8月8日
	 */
	private static final long serialVersionUID = -2244381413323684472L;

	/** 需要进行json解析的数据类型 */
	private final Class<? extends BaseBean> clazz;

	/** 记录总数 */
	private int count;
	/** 当前面 */
	private int page;
	/** 页大小 */
	private int rows;

	/** 列表数据 */
	private ArrayList<? extends BaseBean> modelList;

	public ListBean(String msgStr, Class<? extends BaseBean> clazz) throws JSONException {
		this.clazz = clazz;
		init(new JSONObject(msgStr));
	}

	public ListBean(String msgStr, Class<? extends BaseBean> clazz, String listKey) throws JSONException {
		this.clazz = clazz;
		init(msgStr, listKey);
	}

	@Override
	protected void init(JSONObject jSon) throws JSONException {
		count = jSon.optInt("count", 0);
		page = jSon.optInt("page", 0);
		rows = jSon.optInt("rows", 0);
		modelList = toModelList(jSon.optString("data"), clazz);
	}

	protected void init(String jSon, String key) throws JSONException {
		count = 0;
		page = 0;
		rows = 0;
		modelList = toModelList(jSon, clazz);
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public ArrayList<? extends BaseBean> getModelList() {
		return modelList;
	}

	public void setModelList(ArrayList<? extends BaseBean> modelList) {
		this.modelList = modelList;
	}

	@Override
	public String toString() {
		return "ListBean [clazz=" + clazz + ", count=" + count + ", page=" + page + ", rows=" + rows + ", modelList=" + modelList + "]";
	}

}

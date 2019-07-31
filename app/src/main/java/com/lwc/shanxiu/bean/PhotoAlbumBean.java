package com.lwc.shanxiu.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PhotoAlbumBean implements Serializable {

	/**
	 * 
	 * @author CodeApe
	 * @version 1.0
	 * @date 2014年1月16日
	 */
	private static final long serialVersionUID = 1L;
	/** 相册名称 */
	private String name;
	/** 图片数量 */
	private String count;
	/** 相册第一张图片 */
	private int bitmap;

	/** 相册下的图片集合 */
	private List<PhotoBean> bitList = new ArrayList<PhotoBean>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public int getBitmap() {
		return bitmap;
	}

	public void setBitmap(int bitmap) {
		this.bitmap = bitmap;
	}

	public List<PhotoBean> getBitList() {
		return bitList;
	}

	public void setBitList(List<PhotoBean> bitList) {
		this.bitList = bitList;
	}

	@Override
	public String toString() {
		return "PhotoAlbumBean [name=" + name + ", count=" + count + ", bitmap=" + bitmap + ", bitList=" + bitList + "]";
	}

}

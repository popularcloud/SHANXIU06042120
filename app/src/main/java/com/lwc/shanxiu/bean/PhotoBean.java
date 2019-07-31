package com.lwc.shanxiu.bean;

import java.io.Serializable;

public class PhotoBean implements Serializable{

	/**
	 * 
	 * @author CodeApe
	 * @version 1.0
	 * @date 2014年1月16日
	 */
	private static final long serialVersionUID = -8044038882517813303L;

	/** 照片的Id */
	private int photoId;

	/** 图片路径 */
	private String photoPath;

	/** 照片是否呗选中 */
	private boolean isCheck;

	public PhotoBean() {

	}
	
	public PhotoBean(int photoId , String photoPath) {
		this.isCheck = false;
		this.photoId = photoId;
		this.photoPath = photoPath;
	}

	public PhotoBean(int photoId) {
		this.isCheck = false;
		this.photoId = photoId;
	}

	public PhotoBean(int photoId, boolean isCheck) {
		this.isCheck = isCheck;
		this.photoId = photoId;
	}

	public int getPhotoId() {
		return photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	@Override
	public String toString() {
		return "PhotoBean [photoId=" + photoId + ", photoPath=" + photoPath + ", isCheck=" + isCheck + "]";
	}

	

}

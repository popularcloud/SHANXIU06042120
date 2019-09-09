package com.lwc.shanxiu.module.bean;

import java.io.Serializable;

public class Tag implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2684657309332033242L;
	
	private int backgroundResId;
	private String labelId;
	private boolean isChecked=false;
	private int leftDrawableResId;
	private int rightDrawableResId;
	private String labelName;
	private int labelType;

	public int getLabelType() {
		return labelType;
	}

	public void setLabelType(int labelType) {
		this.labelType = labelType;
	}

	public Tag() {
	}

	public Tag(String paramInt, String paramString) {
		this.labelId = paramInt;
		this.labelName = paramString;
	}

	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public int getBackgroundResId() {
		return this.backgroundResId;
	}

	public int getLeftDrawableResId() {
		return this.leftDrawableResId;
	}

	public int getRightDrawableResId() {
		return this.rightDrawableResId;
	}

	public boolean isChecked() {
		return this.isChecked;
	}

	public void setBackgroundResId(int paramInt) {
		this.backgroundResId = paramInt;
	}

	public void setChecked(boolean paramBoolean) {
		this.isChecked = paramBoolean;
	}

	public void setLeftDrawableResId(int paramInt) {
		this.leftDrawableResId = paramInt;
	}

	public void setRightDrawableResId(int paramInt) {
		this.rightDrawableResId = paramInt;
	}

}

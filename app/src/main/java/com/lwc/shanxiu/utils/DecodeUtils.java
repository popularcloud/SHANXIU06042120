package com.lwc.shanxiu.utils;

import android.util.Base64;

/**
 * 加密解密的工具
 * 
 * @author 何栋
 * 
 */
public class DecodeUtils {
	/**
	 * 
	 * 加密
	 * 
	 * 
	 * 
	 * @param targetStr
	 * 
	 * @return
	 */
	public static String encode(String targetStr) {
		return Base64.encodeToString(targetStr.getBytes(), Base64.DEFAULT);
	}

	/**
	 * 
	 * 解密
	 * 
	 * 
	 * 
	 * @param targetStr
	 * 
	 * @return
	 */
	public static String decode(String targetStr) {
		return new String(Base64.decode(targetStr.getBytes(), Base64.DEFAULT));
	}
}

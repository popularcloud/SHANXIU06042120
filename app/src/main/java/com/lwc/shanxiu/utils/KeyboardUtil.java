/**
 * @Description TODO
 * @author zhou wan
 * @date 2015年2月10日
 * @Copyright: Copyright (c) 2015 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 */
package com.lwc.shanxiu.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * @Description TODO
 * @author zhou wan
 * @date 2015年2月10日
 * @Copyright: Copyright (c) 2015 Shenzhen Tentinet Technology Co., Ltd. Inc.
 *             All rights reserved.
 */
public class KeyboardUtil {
	/**
	 * 显示/隐藏 软键盘
	 * 
	 * @version 1.0
	 * @createTime 2014年6月11日,上午10:49:21
	 * @updateTime 2014年6月11日,上午10:49:21
	 * @createAuthor 王治粮
	 * @updateAuthor zhou wan
	 * @updateInfo
	 * @param show
	 *            true显示软键盘，false关闭软键盘
	 */
	public static void showInput(boolean show, Activity mActivity) {
		if (show) {
			InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
			if (imm != null&&mActivity.getCurrentFocus()!=null) {
				imm.showSoftInputFromInputMethod(mActivity.getCurrentFocus().getApplicationWindowToken(), 0);
			}
		} else {
			InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
			if (imm != null&&mActivity.getCurrentFocus()!=null) {
				imm.hideSoftInputFromWindow(mActivity.getCurrentFocus().getApplicationWindowToken(), 0);
			}
		}
	}
}

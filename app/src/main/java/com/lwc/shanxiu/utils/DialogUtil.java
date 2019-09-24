package com.lwc.shanxiu.utils;

import android.content.Context;
import android.text.TextUtils;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.bean.Order;
import com.lwc.shanxiu.widget.CustomDialog;
import com.lwc.shanxiu.widget.NavigateDialog;

import java.util.List;

/**
 * 对话框封装工具类
 * 
 * @author 何栋
 * @date 2013-03-20
 * @version 1.0
 */
public class DialogUtil {

	private static CustomDialog dialog;

	public static CustomDialog showUpdateAppDg(Context context, String title, String butText, String msg, CustomDialog.OnClickListener listener) {
		dialog = new CustomDialog(context);
		if (!TextUtils.isEmpty(title)) {
			dialog.setTitle(title);
		} else {
			dialog.setTitle(context.getString(R.string.prompt));
		}
		dialog.setCancelable(false);
		dialog.setGoneBut2();
		dialog.setMsgGra();
		dialog.setMessage(msg);
		dialog.setButton1Text(butText);
		dialog.setEnterBtn(listener);
//		dialog.setCancelBtn(new CustomDialog.OnClickListener() {
//
//			@Override
//			public void onClick(CustomDialog dialog, int id, Object object) {
//				dialog.dismiss();
//			}
//		});
		dialog.show();
		return dialog;
	}

	public static void showMessageUp(Context context, String title, String butText, String cancel, String msg, CustomDialog.OnClickListener listener, CustomDialog.OnClickListener listener2) {
		dialog = new CustomDialog(context);
		if (!TextUtils.isEmpty(title)) {
			dialog.setTitle(title);
		} else {
			dialog.setTitle(context.getString(R.string.prompt));
		}
		dialog.setButton1Text(butText);
		dialog.setMsgGra();
		dialog.setMessage(msg);

		dialog.setEnterBtn(listener);
		dialog.setButton2Text(cancel);
		if (listener2 != null) {
			dialog.setCancelBtn(listener2);
		} else {
			dialog.setCancelBtn(new CustomDialog.OnClickListener() {

				@Override
				public void onClick(CustomDialog dialog, int id, Object object) {
					dialog.dismiss();
				}
			});
		}
		dialog.show();
	}

	public static void showUpdateAppDg(Context context, String title,String butText, String cancel, String msg, CustomDialog.OnClickListener listener, CustomDialog.OnClickListener listener2) {
		dialog = new CustomDialog(context);
		if (!TextUtils.isEmpty(title)) {
			dialog.setTitle(title);
		} else {
			dialog.setTitle(context.getString(R.string.prompt));
		}
		dialog.setButton1Text(butText);
		dialog.setMsgGra();
		dialog.setMessage(msg);

		dialog.setEnterBtn(listener);
		dialog.setButton2Text(cancel);
		if (listener2 != null) {
			dialog.setCancelBtn(listener2);
		} else {
			dialog.setCancelBtn(new CustomDialog.OnClickListener() {

				@Override
				public void onClick(CustomDialog dialog, int id, Object object) {
					dialog.dismiss();
				}
			});
		}
		dialog.show();
	}

	public static void showNavigateDg(Context context, Order order, List<String> map) {
		NavigateDialog dialog = new NavigateDialog(context, R.style.dialog2);
		dialog.setOrder(order);
		if (map.indexOf("高德地图") < 0) {
			dialog.setGoneGaode();
		}
		if (map.indexOf("百度地图") < 0) {
			dialog.setGoneBaidu();
		}
		if (map.indexOf("腾讯地图") < 0) {
			dialog.setGonetengxun();
		}
		dialog.show();
	}

	/**
	 * 显示提示信息对话框
	 * 
	 * @version 1.0
	 * @createTime 2013-10-2,上午10:36:57
	 * @updateTime 2013-10-2,上午10:36:57
	 * @createAuthor 罗文忠
	 * @updateAuthor 罗文忠
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context
	 */
	public static void showMessageDg(Context context, String title, String msg, CustomDialog.OnClickListener listener) {
		dialog = new CustomDialog(context, R.style.dialog2);
		if (!TextUtils.isEmpty(title)) {
			dialog.setTitle(title);
		} else {
			dialog.setTitle(context.getString(R.string.prompt));
		}
		dialog.setMessage(msg);

		dialog.setEnterBtn(listener);
		dialog.setCancelBtn(new CustomDialog.OnClickListener() {

			@Override
			public void onClick(CustomDialog dialog, int id, Object object) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	public static void showMessageDg(Context context, String title,String butText, String cancel, String msg, CustomDialog.OnClickListener listener, CustomDialog.OnClickListener listener2) {
		dialog = new CustomDialog(context);
		if (!TextUtils.isEmpty(title)) {
			dialog.setTitle(title);
		} else {
			dialog.setTitle(context.getString(R.string.prompt));
		}
		dialog.setButton1Text(butText);
//		dialog.setMsgGra();
		dialog.setMessage(msg);

		dialog.setEnterBtn(listener);
		dialog.setButton2Text(cancel);
		if (listener2 != null) {
			dialog.setCancelBtn(listener2);
		} else {
			dialog.setCancelBtn(new CustomDialog.OnClickListener() {

				@Override
				public void onClick(CustomDialog dialog, int id, Object object) {
					dialog.dismiss();
				}
			});
		}
		dialog.show();
	}

}

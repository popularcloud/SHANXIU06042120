package com.lwc.shanxiu.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;

/**
 * 对话框工具类
 *
 * @author line类
 *
 */
public class DialogUtils {

	private Context context;
	private ProgressDialog defaultProgressDialog;
	private Dialog customProgressDialog;

	public DialogUtils(Context context) {
		this.context = context;
	}

	/**
	 * 显示默认的对话框
	 *
	 * @param message
	 */
	public void showDefaultProgressDialog(String message) {
		if (defaultProgressDialog == null) {
			defaultProgressDialog = new ProgressDialog(context);
			defaultProgressDialog.setCanceledOnTouchOutside(false);
			defaultProgressDialog.setMessage(message);
			if (!defaultProgressDialog.isShowing())
				defaultProgressDialog.show();
		}
	}

	/**
	 * 隐藏默认的对话框
	 */
	public void dissmissDefaultProgressDialog() {
		if (defaultProgressDialog != null && defaultProgressDialog.isShowing())
			defaultProgressDialog.dismiss();
	}

	/**
	 * 显示自定义布局的dialog
	 *
	 * @param progressDialogLayout
	 *            显示的布局文件
	 * @param style
	 *            dialog样式 <!-- 自定义loading dialog --> <style
	 *            name="loading_dialog" parent="android:style/Theme.Dialog">
	 *            <item name="android:windowFrame">@null</item> <item
	 *            name="android:windowNoTitle">true</item> <item
	 *            name="android:windowBackground"
	 *            >@android:color/transparent</item> <item
	 *            name="android:windowIsFloating">true</item> <item
	 *            name="android:windowContentOverlay">@null</item> </style>
	 */
	public void showCustomProgressDialog(int progressDialogLayout, int style) {
		if (customProgressDialog == null) {
			customProgressDialog = new Dialog(context, style);
			customProgressDialog.setCanceledOnTouchOutside(false);
			customProgressDialog.setCancelable(true);
			customProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
						if(customProgressDialog.isShowing()){
							customProgressDialog.dismiss();
						}
						return true;
					}
					return false;
				}
			});
			customProgressDialog.setContentView(progressDialogLayout);
			if (!customProgressDialog.isShowing()) {
				customProgressDialog.show();
			}
		}
	}

	/**
	 * 关闭自定义的dialog
	 */
	public void dismissCustomProgressDialog() {
		if (customProgressDialog != null && customProgressDialog.isShowing()) {
			try {
				customProgressDialog.dismiss();
			}catch (Exception e){}
		}
	}

	public boolean isShow(){
		return customProgressDialog.isShowing();
	}
}

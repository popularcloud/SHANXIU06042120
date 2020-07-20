package com.lwc.shanxiu.module.lease_parts.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.lwc.shanxiu.R;


/**
 * 拨打电话对话框
 * 
 */
public class GetPhoneDialog extends Dialog implements View.OnClickListener{

	/** 上下文 */
	private Context context;
	private Button btn_phone;
	private Button cancelBtn;
	CallBack callBack;
	public GetPhoneDialog(Context context, CallBack callBack, String one, String two) {
		super(context, R.style.BottomDialogStyle);
		// 拿到Dialog的Window, 修改Window的属性
		Window window = getWindow();
		window.getDecorView().setPadding(0, 0, 0, 0);
		// 获取Window的LayoutParams
		WindowManager.LayoutParams attributes = window.getAttributes();
		attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
		attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
		// 一定要重新设置, 才能生效
		window.setAttributes(attributes);
		this.callBack = callBack;
		init(context, one, two);
	}

	/**
	 *
	 * @param context
	 * @param theme
     */
	public GetPhoneDialog(Context context, int theme) {
		super(context, theme);
		init(context, null, null);
	}

	public GetPhoneDialog(Context context) {
		super(context, R.style.dialog_style);
		init(context, null, null);
	}

	/**
	 * 初始话对话框
	 * 
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context
	 * 
	 */
	protected void init(Context context, String one, String two) {
		this.context = context;
		this.setCancelable(false);
		this.setCanceledOnTouchOutside(true);
		this.setContentView(R.layout.cover_select_get_phone);

		btn_phone = (Button) findViewById(R.id.btn_phone);
		btn_phone.setText(two);
		cancelBtn = (Button) findViewById(R.id.btn_cancel_join);
		cancelBtn.setOnClickListener(this);
		btn_phone.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_phone:
				dismiss();
				callBack.twoClick();
				break;
			case R.id.btn_cancel_join:
				dismiss();
				callBack.cancelCallback();
				break;
		}
	}

	public interface CallBack {
		/**
		 * 拍照
		 */
		void twoClick();

		/**
		 * 取消
		 */
		void cancelCallback();
	}
}

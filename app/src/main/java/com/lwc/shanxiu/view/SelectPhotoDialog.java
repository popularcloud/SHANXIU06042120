package com.lwc.shanxiu.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.lwc.shanxiu.R;


/**
 * 自定义对话框
 * 
 */
public class SelectPhotoDialog extends Dialog implements View.OnClickListener{

	/** 上下文 */
	private Context context;
	private Button albumBtn;
	private Button photoGraphBtn;
	private Button cancelBtn;
	CallBack callBack;
	public SelectPhotoDialog(Context context, CallBack callBack, String one, String two) {
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
	public SelectPhotoDialog(Context context, int theme) {
		super(context, theme);
		init(context, null, null);
	}

	public SelectPhotoDialog(Context context) {
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
		this.setCanceledOnTouchOutside(false);
		this.setContentView(R.layout.cover_select_pop_layout);

		albumBtn = (Button) findViewById(R.id.btn_album);
		photoGraphBtn = (Button) findViewById(R.id.btn_photo_graph);
		//startRecordBtn = (Button) mMenuView.findViewById(R.id.btn_upload_record);
		cancelBtn = (Button) findViewById(R.id.btn_cancel_join);
		photoGraphBtn.setText(one);
		if(TextUtils.isEmpty(two)){
			albumBtn.setVisibility(View.GONE);
		}else{
			albumBtn.setText(two);
			albumBtn.setOnClickListener(this);
		}

		photoGraphBtn.setOnClickListener(this);
		cancelBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

			case R.id.btn_album:
				dismiss();
				callBack.twoClick();
				break;
			case R.id.btn_photo_graph:
				dismiss();
				callBack.oneClick();
				break;
			case R.id.btn_cancel_join:
				dismiss();
				callBack.cancelCallback();
				break;
		}
	}

	public interface CallBack {

		/**
		 * 调用图库
		 */
		void oneClick();

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

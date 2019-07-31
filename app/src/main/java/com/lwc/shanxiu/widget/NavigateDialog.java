package com.lwc.shanxiu.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.Order;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;

/**
 * 自定义对话框
 *
 */
public class NavigateDialog extends Dialog {


	/** 上下文 */
	private Context context;

	/** 标题 */
	private TextView txt_gaode;
	/** 提示信息 */
	private TextView txt_baidu;
	/** 等待信息 */
	private TextView txt_tengxun;
	private ToggleButton tBtnSecretWechat;
	private Order order;
	private ImageView iv_delete;

	public NavigateDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		init(context);
	}

	/**
	 *
	 * @param context
	 * @param theme
	 */
	public NavigateDialog(Context context, int theme) {
		super(context, theme);
		init(context);
	}

	public NavigateDialog(Context context) {
		super(context, R.style.dialog_style);
		init(context);
	}

	/**
	 * 初始话对话框
	 *
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param context
	 *
	 */
	protected void init(final Context context) {
		this.context = context;
		this.setCancelable(false);
		this.setCanceledOnTouchOutside(false);
		this.setContentView(R.layout.view_navigate_dialog);
		txt_gaode = (TextView) findViewById(R.id.txt_gaode);
		txt_baidu = (TextView)findViewById(R.id.txt_baidu);
		txt_tengxun = (TextView)findViewById(R.id.txt_tengxun);
		iv_delete = (ImageView)findViewById(R.id.iv_delete);
		tBtnSecretWechat = (ToggleButton) findViewById(R.id.tBtnSecretWechat);

		txt_gaode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean boo = tBtnSecretWechat.isChecked();
				if (boo) {
					SharedPreferencesUtils.getInstance(context).saveString("selectMap", "高德地图");
				}
				Utils.toGaodeMap(context, order.getOrderContactAddress().replace("_", ""), Double.parseDouble(order.getOrderLatitude()
				), Double.parseDouble(order.getOrderLongitude()));
				dismiss();
			}
		});

		txt_baidu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean boo = tBtnSecretWechat.isChecked();
				if (boo) {
					SharedPreferencesUtils.getInstance(context).saveString("selectMap", "百度地图");
				}
				Utils.toBaiduMap(context, order.getOrderContactAddress().replace("_", ""), Double.parseDouble(order.getOrderLatitude()
				), Double.parseDouble(order.getOrderLongitude()));
				dismiss();
			}
		});

		txt_tengxun.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean boo = tBtnSecretWechat.isChecked();
				if (boo) {
					SharedPreferencesUtils.getInstance(context).saveString("selectMap", "腾讯地图");
				}
				Utils.toTengxunMap(context, order.getOrderContactAddress().replace("_", ""), Double.parseDouble(order.getOrderLatitude()
				), Double.parseDouble(order.getOrderLongitude()));
				dismiss();
			}
		});

		iv_delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public void setGoneBaidu() {
		txt_baidu.setVisibility(View.GONE);
	}
	public void setGoneGaode() {
		txt_gaode.setVisibility(View.GONE);
	}
	public void setGonetengxun() {
		txt_tengxun.setVisibility(View.GONE);
	}
}

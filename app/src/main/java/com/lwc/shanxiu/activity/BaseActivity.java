package com.lwc.shanxiu.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.configs.BroadcastFilters;
import com.lwc.shanxiu.module.wallet.WalletActivity;
import com.lwc.shanxiu.utils.Constants;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.widget.SystemBarTintManager;

import butterknife.ButterKnife;

/**
 * 基类Activity
 * 
 * @Description 所有的Activity都继承自此Activity，并实现基类模板方法，本类的目的是为了规范团队开发项目时候的开发流程的命名， 基类也用来处理需要集中分发处理的事件、广播、动画等，如开发过程中有发现任何改进方案都可以一起沟通改进。
 * @author 何栋
 * @version 1.0
 * @date 2014年3月29日
 * @Copyright: Copyright (c) 2014 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 * 
 */
public abstract class BaseActivity extends FragmentActivity {

	/** 广播接收器 */
	public BroadcastReceiver receiver;
	/** 广播过滤器 */
	public IntentFilter filter;
	public Bundle savedInstanceState;
	/** 头部组件对象 */
	private TextView txtActionbarTitle, txtRight;
	private ImageView imgBack, imgRight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
		super.onCreate(savedInstanceState);
		this.savedInstanceState = savedInstanceState;
		setContentView(getContentViewId(savedInstanceState));
		ButterKnife.bind(this);
//		PushAgent.getInstance(this).onAppStart();
		/**
		 * 注意别在此处调用  下面几个抽象方法
		 * 在子类中
		 * super.onCreate(savedInstanceState);先调用这个方法， 所以在子类中做UI的相关操作，会找不到控件
		 */
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(this, true);
			SystemBarTintManager tintManager = new SystemBarTintManager(this);
			tintManager.setStatusBarTintEnabled(true);
			tintManager.setStatusBarTintDrawable(getResources().getDrawable(R.drawable.title_bg_new));
		}
		try {
			txtActionbarTitle = (TextView) findViewById(R.id.txtActionbarTitle);
			txtRight = (TextView) findViewById(R.id.txtRight);
			imgBack = (ImageView) findViewById(R.id.img_back);
			imgRight = (ImageView) findViewById(R.id.imgRight);
		} catch (Exception e) {}
		findViews();
		initGetData();
		widgetListener();
		init();

		registerReceiver();
	}

	/**
	 * 设置通知栏颜色
	 * @param activity
	 * @param on
     */
	@TargetApi(19)
	private static void setTranslucentStatus(Activity activity, boolean on) {

		Window win = activity.getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}
	/**
	 * 显示返回按钮
	 */
	protected void showBack() {
		if (imgBack != null) {
			imgBack.setVisibility(View.VISIBLE);
			imgBack.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onBackPressed();
				}
			});
		}
	}
	/**
	 * 设置页面标题
	 * @param title
	 */
	protected void setTitle(String title) {
		if (txtActionbarTitle != null && title != null) {
			txtActionbarTitle.setText(title);
		}
	}
	/**
	 * 设置右边按钮文字和点击监听事件
	 * @param right
	 */
	protected void setRightText(String right, View.OnClickListener listener) {
		if (txtRight != null && right != null) {
			txtRight.setVisibility(View.VISIBLE);
			txtRight.setText(right);
			txtRight.setOnClickListener(listener);
		}
	}
	/**
	 * 设置右边按钮图标和点击监听事件
	 * @param right
	 */
	protected void setRightImg(int right, View.OnClickListener listener) {
		if (imgRight != null && right != 0) {
			imgRight.setVisibility(View.VISIBLE);
			imgRight.setImageResource(right);
			imgRight.setOnClickListener(listener);
		}
	}

	/**
	 * 隐藏头部右边按钮
	 */
	protected void goneRight(){
		imgRight.setVisibility(View.GONE);
	}

	/**
	 * 显示头部右边按钮
	 */
	protected void visibleRight(){
		imgRight.setVisibility(View.VISIBLE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Constants.RED_ID_TO_RESULT) {
			IntentUtil.gotoActivityAndFinish(this, WalletActivity.class);
			finish();
		} else if (resultCode == Constants.RED_ID_RESULT){
			finish();
		}
	}

	/**
	 * 获取显示view的xml文件ID
	 * 
	 * 在Activity的 {@link #onCreate(Bundle)}里边被调用
	 * 
	 * @version 1.0
	 * @param savedInstanceState
	 * @createTime 2014年4月21日,下午2:31:19
	 * @updateTime 2014年4月21日,下午2:31:19
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return xml文件ID
	 */
	protected abstract int getContentViewId(Bundle savedInstanceState);

	/**
	 * 控件查找
	 * 
	 * @version 1.0
	 * @createTime 2014年4月21日,下午1:58:20
	 * @updateTime 2014年4月21日,下午1:58:20
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	protected abstract void findViews();

	/**
	 * 初始化应用程序，设置一些初始化数据都获取数据等操作
	 * 
	 * 在{@link #widgetListener()}之后被调用
	 * 
	 * @version 1.0
	 * @createTime 2014年4月21日,下午1:55:15
	 * @updateTime 2014年4月21日,下午1:55:15
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	protected abstract void init();

	/**
	 * 获取上一个界面传送过来的数据
	 * 
	 * 在{@link BaseActivity#init()}之前被调用
	 * 
	 * @version 1.0
	 * @createTime 2014年4月21日,下午2:42:56
	 * @updateTime 2014年4月21日,下午2:42:56
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	protected abstract void initGetData();

	/**
	 * 组件监听模块
	 * 
	 * 在{@link #findViews()}后被调用
	 * 
	 * @version 1.0
	 * @createTime 2014年4月21日,下午1:56:06
	 * @updateTime 2014年4月21日,下午1:56:06
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	protected abstract void widgetListener();

	/**
	 * 设置广播过滤器
	 * 
	 * @version 1.0
	 * @createTime 2014年5月22日,下午1:43:15
	 * @updateTime 2014年5月22日,下午1:43:15
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	protected void setFilterActions() {
		filter = new IntentFilter();
		filter.addAction(BroadcastFilters.ADD_MACHINE_SUCCESS);
		filter.addAction(BroadcastFilters.UPDATE_USER_INFO_ICON);
		filter.addAction(BroadcastFilters.UPDATE_PASSWORD);
		filter.addAction(BroadcastFilters.NOTIFI_DATA_MACHINE_LIST);
		filter.addAction(BroadcastFilters.SHOW_MACHINE_INFO);
		filter.addAction(BroadcastFilters.NOTIFI_MAIN_ORDER_MENTION);
		filter.addAction(BroadcastFilters.NOTIFI_NEARBY_PEOPLE);
		filter.addAction(BroadcastFilters.NOTIFI_MESSAGE_COUNT);
		filter.addAction(BroadcastFilters.NOTIFI_WAITTING_ORDERS);
		filter.addAction(BroadcastFilters.NOTIFI_ORDER_INFO_MENTION);
		filter.addAction(BroadcastFilters.NOTIFI_ORDER_INFO_GUAQI);
		filter.addAction(BroadcastFilters.NOTIFI_ORDER_INFO_TANCHUAN);
		filter.addAction(BroadcastFilters.NOTIFI_ORDER_GETTED_MENTION);
		filter.addAction(BroadcastFilters.NOTIFI_ORDER_PRIASE_MENTION);
		filter.addAction(BroadcastFilters.UPDATE_USER_LOGIN_SUCCESSED);
		filter.addAction(BroadcastFilters.NOTIFI_BUTTON_STATUS);
		filter.addAction(BroadcastFilters.LOGIN_OUT_ACTION);
		filter.addAction(BroadcastFilters.NOTIFI_NEAR_ORDER);
		filter.addAction(BroadcastFilters.NOTIFI_ORDER_INFO_CHANGE);
		filter.addAction(BroadcastFilters.NOTIFI_CLOSE_SLIDING_MENU);
	}



	/**
	 * 注册广播
	 * 
	 * @version 1.0
	 * @createTime 2014年5月22日,下午1:41:25
	 * @updateTime 2014年5月22日,下午1:41:25
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	protected void registerReceiver() {
		setFilterActions();
		receiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				BaseActivity.this.onReceive(context, intent);
			}
		};
		LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);

	}

	/**
	 * 广播监听回调
	 * 
	 * @version 1.0
	 * @createTime 2014年5月22日,下午1:40:30
	 * @updateTime 2014年5月22日,下午1:40:30
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context
	 *        上下文
	 * @param intent
	 *        广播附带内容
	 */
	protected void onReceive(Context context, Intent intent) {
		// TODO 父类集中处理共同的请求
	}

	@Override
	protected void onDestroy() {
		LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
		super.onDestroy();
	}

	/**
	 * 返回按钮点击回调
	 * @param v
     */
	public void onBack(View v) {
		finish();
	}

}

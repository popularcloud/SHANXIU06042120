package com.lwc.shanxiu.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.MainActivity;
import com.lwc.shanxiu.configs.BroadcastFilters;
import com.lwc.shanxiu.utils.LLog;

/**
 * 基类Fragment
 * 
 * @Description 所有的Fragment必须继承自此类
 * @author 何栋
 * @version 1.0
 * @date 2014年3月29日
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 * 
 */
public abstract class BaseFragment extends Fragment {
	/** 父视图 */
	protected View view_Parent;

//	/** 首页单选按钮组 */
//	protected RadioGroup radioGroup_main;

	/** 广播接收器 */
	protected BroadcastReceiver receiver;
	/** 广播过滤器 */
	public IntentFilter filter;
	private TextView txtActionbarTitle;
	protected ImageView imgBack;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		LLog.i("BaseFragment    onCreateView    ");
		view_Parent = getViews();
		try {
			txtActionbarTitle = (TextView) view_Parent.findViewById(R.id.txtActionbarTitle);
			imgBack = (ImageView) view_Parent.findViewById(R.id.img_back);
		} catch (Exception e) {}
		initGetData();
		findViews(savedInstanceState);
		widgetListener();
		init();
		registerReceiver();
		return view_Parent;
	}

	protected void showBack() {
		if (imgBack != null) {
			imgBack.setVisibility(View.VISIBLE);
			imgBack.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					getActivity().onBackPressed();
				}
			});
		}
	}

	protected void setTitle(String title) {
		if (txtActionbarTitle != null && title != null) {
			txtActionbarTitle.setText(title);
		}
	}

	/**
	 * 获取view
	 * 
	 * @version 1.0
	 * @createTime 2014年5月22日,上午10:09:05
	 * @updateTime 2014年5月22日,上午10:09:05
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return
	 */
	protected abstract View getViews();

	/**
	 * 控件查找
	 * 
	 * @version 1.0
	 * @param savedInstanceState
	 * @createTime 2014年5月22日,上午10:03:58
	 * @updateTime 2014年5月22日,上午10:03:58
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	protected abstract void findViews(Bundle savedInstanceState);

	/**
	 * 组件监听
	 * 
	 * @version 1.0
	 * @createTime 2014年5月22日,上午10:05:39
	 * @updateTime 2014年5月22日,上午10:05:39
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	protected abstract void widgetListener();

	/**
	 * 初始化
	 * 
	 * @version 1.0
	 * @createTime 2014年5月22日,上午10:05:06
	 * @updateTime 2014年5月22日,上午10:05:06
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	protected abstract void init();

	/**
	 * 是否已经创建
	 * 
	 * @version 1.0
	 * @createTime 2014年6月6日,上午11:16:54
	 * @updateTime 2014年6月6日,上午11:16:54
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return true已经创建，false未创建
	 */
	public boolean isCreated() {
		return view_Parent != null;
	}

	/**
	 * 获取初始数据
	 * 
	 * @version 1.0
	 * @createTime 2014年6月7日,上午11:13:55
	 * @updateTime 2014年6月7日,上午11:13:55
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	public abstract void initGetData();

	/**
	 * 设置广播过滤器，在此添加广播过滤器之后，所有的子类都将收到该广播
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

		// TODO 添加广播过滤器，在此添加广播过滤器之后，所有的子类都将收到该广播
		filter = new IntentFilter();
//		filter.addAction(BroadcastFilters.ADD_MACHINE_SUCCESS);
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
		filter.addAction(BroadcastFilters.NOTIFI_ORDER_INFO_CHANGE);
		filter.addAction(BroadcastFilters.NOTIFI_GET_NEAR_ORDER);
		filter.addAction(BroadcastFilters.NOTIFI_GET_ORDER_COUNT);
		filter.addAction(MainActivity.RECEIVER_ACTION);
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
				BaseFragment.this.onReceive(context, intent);
			}
		};
		LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, filter);
	}

	@Override
	public void onDestroy() {
		LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
		super.onDestroy();
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
		// TODO 接受到广播之后做的处理操作
	}

}

package com.lwc.shanxiu.view.pul;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;

/**
 * 下拉刷新列表控件
 * 
 * @Description TODO
 * @author CodeApe
 * @version 1.0
 * @date 2013-12-4
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 * 
 */
public class PullToRefreshListView extends PullToRefreshAdapterViewBase<ListView> {
	/** 支持下拉刷新 */
	public static final int MODE_PULL_TO_DOWN = 0x01;
	/** 支持上拉拉刷新 */
	public static final int MODE_PULL_TO_UP = 0x02;
	/** 同时支持上拉和下拉 */
	public static final int MODE_BOTH_UP_AND_DOWN = 0x03;

	/** 移动Item */
	private final int WHAT_MOVE_ITEM_TO_LAST = 1;

	/** 当前列表模式 */
	public static int currentListMode = MODE_BOTH_UP_AND_DOWN;

	/** view大小更改监听 */
	private OnSizeChangeListener listener;

	public PullToRefreshListView(Context context) {
		super(context);
	}

	public PullToRefreshListView(Context context, int mode) {
		super(context, mode);
	}

	public PullToRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected final ListView createRefreshableView(Context context, AttributeSet attrs) {
		ListView lv = new ListView(context, attrs);
		// Use Generated ID (from res/values/ids.xml)
		lv.setId(android.R.id.list);
		return lv;
	}

	@Override
	public ContextMenuInfo getContextMenuInfo() {
		return super.getContextMenuInfo();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		if (listener != null) {
			listener.onChange(w, h, oldw, oldh);
		}

		// handler.postDelayed(new Runnable() {
		//
		// @Override
		// public void run() {
		// HandlerUtil.sendMessage(handler, WHAT_MOVE_ITEM_TO_LAST);
		// }
		//
		// }, 100);
		super.onSizeChanged(w, h, oldw, oldh);
	}

	/**
	 * 设置size大小更改监听
	 * 
	 * @version 1.0
	 * @createTime 2013-11-28,下午4:31:49
	 * @updateTime 2013-11-28,下午4:31:49
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	public void setOnSizeChangListener(OnSizeChangeListener listener) {
		this.listener = listener;
	}

	/**
	 * view 大小更改监听事件
	 * 
	 * @Description TODO
	 * @author CodeApe
	 * @version 1.0
	 * @date 2013-11-28
	 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
	 * 
	 */
	public interface OnSizeChangeListener {

		/**
		 * View 大小更改监听事件
		 * 
		 * @version 1.0
		 * @createTime 2013-11-28,下午4:18:33
		 * @updateTime 2013-11-28,下午4:18:33
		 * @createAuthor CodeApe
		 * @updateAuthor CodeApe
		 * @updateInfo (此处输入修改内容,若无修改可不写.)
		 * 
		 * @param w
		 *        新的宽度
		 * @param h
		 *        新的高度
		 * @param oldw
		 *        旧的宽度
		 * @param oldh
		 *        新的高度
		 */
		public void onChange(int w, int h, int oldw, int oldh);
	}

	/** 异步处理对象 */
	private final Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case WHAT_MOVE_ITEM_TO_LAST:
				// PullToRefreshListView.this.getRefreshableView().setSelection(PullToRefreshListView.this.getRefreshableView().getCount() - 1);
				break;
			}
		}

	};

}

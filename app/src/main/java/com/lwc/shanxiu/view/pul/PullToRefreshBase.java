package com.lwc.shanxiu.view.pul;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.utils.DisplayUtil;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * 拖动刷新基础控件
 * 
 * @Description TODO
 * @author CodeApe
 * @version 1.0
 * @date 2013-12-3
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 * 
 * @param <T>
 */
public abstract class PullToRefreshBase<T extends View> extends LinearLayout {
	/**
	 * 平滑滚动的线程
	 * 
	 * @Description TODO
	 * @author CodeApe
	 * @version 1.0
	 * @date 2013-12-4
	 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
	 * 
	 */
	final class SmoothScrollRunnable implements Runnable {
		/** 滑动的动画毫秒数 */
		static final int ANIMATION_DURATION_MS = 190;
		static final int ANIMATION_FPS = 1000 / 60;
		private final Interpolator interpolator;
		/** 滑动的目标Y坐标 */
		private final int scrollToY;
		/** 滑动的原始Y坐标 */
		private final int scrollFromY;
		private final Handler handler;
		private boolean continueRunning = true;
		private long startTime = -1;
		private int currentY = -1;

		public SmoothScrollRunnable(Handler handler, int fromY, int toY) {
			this.handler = handler;
			this.scrollFromY = fromY;
			this.scrollToY = toY;
			this.interpolator = new AccelerateDecelerateInterpolator();
		}

		@Override
		public void run() {
			/**
			 * Only set startTime if this is the first time we're starting, else actually calculate the Y delta
			 */
			if (startTime == -1) {
				startTime = System.currentTimeMillis();
			} else {
				/**
				 * We do do all calculations in long to reduce software float calculations. We use 1000 as it gives us good accuracy and small
				 * rounding errors
				 */
				long normalizedTime = (1000 * (System.currentTimeMillis() - startTime)) / ANIMATION_DURATION_MS;
				normalizedTime = Math.max(Math.min(normalizedTime, 1000), 0);

				final int deltaY = Math.round((scrollFromY - scrollToY) * interpolator.getInterpolation(normalizedTime / 1000f));
				this.currentY = scrollFromY - deltaY;
				setHeaderScroll(currentY);
			}
			// If we're not at the target Y, keep going...
			if (continueRunning && scrollToY != currentY) {
				handler.postDelayed(this, ANIMATION_FPS);
			}
		}

		public void stop() {
			this.continueRunning = false;
			this.handler.removeCallbacks(this);
		}
	};

	// ===========================================================
	// Constants
	// ===========================================================

	/** 摩擦力 ？？？ */
	private static final float FRICTION = 2f;
	/** 往下拉刷新 */
	static final int PULL_TO_REFRESH = 0x0;
	/** 松开进行刷新 */
	static final int RELEASE_TO_REFRESH = 0x1;
	/** 刷新中 */
	static final int REFRESHING = 0x2;
	/** 手动刷新中 */
	static final int MANUAL_REFRESHING = 0x3;

	/** 支持下拉刷新 */
	public static final int MODE_PULL_DOWN_TO_REFRESH = 0x1;
	/** 支持上拉刷新 */
	public static final int MODE_PULL_UP_TO_REFRESH = 0x2;
	/** 支持下拉和上拉刷新 */
	public static final int MODE_BOTH = 0x3;
	/** 不支持下拉和上拉刷新 */
	public static final int MODE_NO = 0x4;

	/** 拖动刷新文本 */
	private String pull_label1;
	/** 释放刷新文本 */
	private String pull_label2;
	/** 正在加载文本 */
	private String pull_label3;
	/** 拖动刷新文本 */
	private String down_label1;
	/** 释放刷新文本 */
	private String down_label2;
	/** 正在加载文本 */
	private String down_label3;

	// ===========================================================
	// Fields
	// ===========================================================

	private int touchSlop;

	/** 初始点击Y坐标 */
	private float initialMotionY;
	/** 最后点击X坐标 */
	private float lastMotionX;
	/** 最后点击Y坐标 */
	private float lastMotionY;
	/** 是否允许滑动 */
	private boolean isBeingDragged = false;

	/** 当前拉动状态 （继续下拉还是松开刷新） */
	private int state = PULL_TO_REFRESH;
	/** 当前列表支持模式 （继续下拉还是松开刷新） */
	private int mode = MODE_PULL_DOWN_TO_REFRESH;
	/** 当前列表支持模式 （继续下拉还是松开刷新） */
	private int currentMode;

	/** 是否允许在刷新的时候滑动列表 */
	private boolean disableScrollingWhileRefreshing = false;
	/** 是否允许拖动刷新 */
	private boolean isPullToRefreshEnabled = true;

	T refreshableView;

	/** 下拉刷新时，顶部view */
	private LoadingLayout headerLayout;
	/** 上拉刷新时，顶部view */
	private LoadingLayout footerLayout;
	/** loadingview的高度 */
	private int headerHeight;

	private final Handler handler = new Handler();

	/** 刷新监听接口 */
	private OnRefreshListener onRefreshListener;

	/** 平滑滚动的线程 */
	private SmoothScrollRunnable currentSmoothScrollRunnable;
	/** 控制圆圈的开始进度 0.4代表拉到60%才开始加进度 */
	private final float p = 0.4f;

	private View pull_to_footview;
	/** 用于自己回调 */

	private CallBackInterF interF;

	public void setInterF(CallBackInterF interF) {
		this.interF = interF;
	}

	// ===========================================================
	// Constructors
	// ===========================================================
	public PullToRefreshBase(Context context) {
		super(context);
		init(context, null);
	}

	public PullToRefreshBase(Context context, int mode) {
		super(context);
		this.mode = mode;
		init(context, null);
	}

	public PullToRefreshBase(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	/**
	 * Deprecated. Use {@link #getRefreshableView()} from now on.
	 * 
	 * @deprecated
	 * @return The Refreshable View which is currently wrapped
	 */
	@Deprecated
	public final T getAdapterView() {
		return refreshableView;
	}

	/**
	 * Get the Wrapped Refreshable View. Anything returned here has already been added to the content view.
	 * 
	 * @return The View which is currently wrapped
	 */
	public final T getRefreshableView() {
		return refreshableView;
	}

	/**
	 * Whether Pull-to-Refresh is enabled
	 * 
	 * @return enabled
	 */
	public final boolean isPullToRefreshEnabled() {
		return isPullToRefreshEnabled;
	}

	/**
	 * 在加载数据的时候停止滑动
	 * 
	 * @version 1.0
	 * @createTime 2013-12-4,上午11:29:15
	 * @updateTime 2013-12-4,上午11:29:15
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return
	 */
	public final boolean isDisableScrollingWhileRefreshing() {
		return disableScrollingWhileRefreshing;
	}

	/**
	 * Returns whether the Widget is currently in the Refreshing state
	 * 
	 * @return true if the Widget is currently refreshing
	 */
	public final boolean isRefreshing() {
		return state == REFRESHING || state == MANUAL_REFRESHING;
	}

	/**
	 * 在设置是否允许滑动
	 * 
	 * @version 1.0
	 * @createTime 2013-12-4,上午11:30:15
	 * @updateTime 2013-12-4,上午11:30:15
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param disableScrollingWhileRefreshing
	 */
	public final void setDisableScrollingWhileRefreshing(boolean disableScrollingWhileRefreshing) {
		this.disableScrollingWhileRefreshing = disableScrollingWhileRefreshing;
	}

	/**
	 * 刷新完成
	 * 
	 * @version 1.0
	 * @createTime 2013-12-28,下午2:04:23
	 * @updateTime 2013-12-28,下午2:04:23
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	public final void onRefreshComplete() {
		if (state != PULL_TO_REFRESH) {
			resetHeader();
		}
	}

	/**
	 * 设置刷新监听
	 * 
	 * @version 1.0
	 * @createTime 2013-12-28,下午2:04:37
	 * @updateTime 2013-12-28,下午2:04:37
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param listener
	 */
	public final void setOnRefreshListener(OnRefreshListener listener) {
		onRefreshListener = listener;
	}

	/**
	 * 设置是否允许拖动刷新
	 * 
	 * @version 1.0
	 * @createTime 2013-12-28,下午2:05:06
	 * @updateTime 2013-12-28,下午2:05:06
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param enable
	 *        true 允许 ， false 不允许
	 */
	public final void setPullToRefreshEnabled(boolean enable) {
		this.isPullToRefreshEnabled = enable;
	}

	/**
	 * 设置释放刷新的文字
	 * 
	 * @version 1.0
	 * @createTime 2013-12-28,下午2:06:08
	 * @updateTime 2013-12-28,下午2:06:08
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param releaseLabel
	 */
	public void setReleaseLabel(String releaseLabel) {
		if (null != headerLayout) {
			headerLayout.setReleaseLabel(releaseLabel);
		}
		if (null != footerLayout) {
			footerLayout.setReleaseLabel(releaseLabel);
		}
	}

	/**
	 * 设置拖动刷新的文字
	 * 
	 * @version 1.0
	 * @createTime 2013-12-28,下午2:06:33
	 * @updateTime 2013-12-28,下午2:06:33
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param pullLabel
	 */
	public void setPullLabel(String pullLabel) {
		if (null != headerLayout) {
			headerLayout.setPullLabel(pullLabel);
		}
		if (null != footerLayout) {
			footerLayout.setPullLabel(pullLabel);
		}
	}

	/**
	 * 设置正在刷新的提示文字
	 * 
	 * @version 1.0
	 * @createTime 2013-12-28,下午2:06:57
	 * @updateTime 2013-12-28,下午2:06:57
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param refreshingLabel
	 */
	public void setRefreshingLabel(String refreshingLabel) {
		if (null != headerLayout) {
			headerLayout.setRefreshingLabel(refreshingLabel);
		}
		if (null != footerLayout) {
			footerLayout.setRefreshingLabel(refreshingLabel);
		}
	}

	/**
	 * 将状态设置为正在刷新
	 * 
	 * @version 1.0
	 * @createTime 2013-12-28,下午2:07:23
	 * @updateTime 2013-12-28,下午2:07:23
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	public final void setRefreshing() {
		this.setRefreshing(true);
	}

	/**
	 * 设置正在刷新
	 * 
	 * @version 1.0
	 * @createTime 2013-12-28,下午2:19:44
	 * @updateTime 2013-12-28,下午2:19:44
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param doScroll
	 */
	public final void setRefreshing(boolean doScroll) {
		if (!isRefreshing()) {
			setRefreshingInternal(doScroll);
			state = MANUAL_REFRESHING;
		}
	}

	/**
	 * 是否已经滑动到最顶端
	 * 
	 * @version 1.0
	 * @createTime 2013-12-28,下午2:20:13
	 * @updateTime 2013-12-28,下午2:20:13
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return
	 */
	public final boolean hasPullFromTop() {
		return currentMode != MODE_PULL_UP_TO_REFRESH;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public final boolean onTouchEvent(MotionEvent event) {
		if (!isPullToRefreshEnabled) {
			return false;
		}

		if (isRefreshing() && disableScrollingWhileRefreshing) {
			return true;
		}

		if (event.getAction() == MotionEvent.ACTION_DOWN && event.getEdgeFlags() != 0) {
			return false;
		}

		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE: {
			if (isBeingDragged) {
				lastMotionY = event.getY();
				this.pullEvent();
				return true;
			}
			break;
		}

		case MotionEvent.ACTION_DOWN: {
			if (isReadyForPull()) {
				lastMotionY = initialMotionY = event.getY();
				return true;
			}
			break;
		}

		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP: {
			if (isBeingDragged) {
				isBeingDragged = false;
				if (state == RELEASE_TO_REFRESH && null != onRefreshListener) {
					setRefreshingInternal(true);

					if (isReadyForPullDown()) {
						onRefreshListener.onPullToDownRefresh();
					} else if (isReadyForPullUp()) {
						onRefreshListener.onPullToUpRefresh();
					}

					// int top = 0;
					// if (footerLayout != null) {
					// Rect rect = new Rect();
					// footerLayout.getGlobalVisibleRect(rect);
					// top = rect.top;
					// }
					// if (isReadyForPullDown() && top == 0) {
					// onRefreshListener.onPullToDownRefresh();
					// } else if (isReadyForPullUp()) {
					// onRefreshListener.onPullToUpRefresh();
					// }

				} else {
					smoothScrollTo(0);
				}
				return true;
			}
			break;
		}
		}

		return false;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_UP && interF != null) {
			interF.call(0);
		}
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public final boolean onInterceptTouchEvent(MotionEvent event) {
		if (refreshableView != null && (refreshableView instanceof DeleteListView) && ((DeleteListView) refreshableView).isLock) {
			return false;
		}
		if (!isPullToRefreshEnabled) {
			return false;
		}

		if (isRefreshing() && disableScrollingWhileRefreshing) {
			return true;
		}

		final int action = event.getAction();

		if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
			isBeingDragged = false;
			return false;
		}

		if (action != MotionEvent.ACTION_DOWN && isBeingDragged) {
			return true;
		}

		switch (action) {
		case MotionEvent.ACTION_MOVE: {
			if (isReadyForPull()) {

				final float y = event.getY();
				final float dy = y - lastMotionY;
				final float yDiff = Math.abs(dy);
				final float xDiff = Math.abs(event.getX() - lastMotionX);

				if (yDiff > touchSlop && yDiff > xDiff) {
					if ((mode == MODE_PULL_DOWN_TO_REFRESH || mode == MODE_BOTH) && dy >= 0.0001f && isReadyForPullDown()) {
						lastMotionY = y;
						isBeingDragged = true;
						if (mode == MODE_BOTH) {
							currentMode = MODE_PULL_DOWN_TO_REFRESH;
						}
					} else if ((mode == MODE_PULL_UP_TO_REFRESH || mode == MODE_BOTH) && dy <= 0.0001f && isReadyForPullUp()) {
						lastMotionY = y;
						isBeingDragged = true;
						if (mode == MODE_BOTH) {
							currentMode = MODE_PULL_UP_TO_REFRESH;
						}
					}
				}
			}
			break;
		}
		case MotionEvent.ACTION_DOWN: {
			if (isReadyForPull()) {
				lastMotionY = initialMotionY = event.getY();
				lastMotionX = event.getX();
				isBeingDragged = false;
			}
			break;
		}
		}

		return isBeingDragged;
	}

	protected void addRefreshableView(Context context, T refreshableView) {
		addView(refreshableView, new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1.0f));
	}

	/**
	 * This is implemented by derived classes to return the created View. If you need to use a custom View (such as a custom ListView), override this
	 * method and return an instance of your custom class.
	 * 
	 * Be sure to set the ID of the view in this method, especially if you're using a ListActivity or ListFragment.
	 * 
	 * @param context
	 * @param attrs
	 *        AttributeSet from wrapped class. Means that anything you include in the XML layout declaration will be routed to the created View
	 * @return New instance of the Refreshable View
	 */
	protected abstract T createRefreshableView(Context context, AttributeSet attrs);

	protected final int getCurrentMode() {
		return currentMode;
	}

	protected final LoadingLayout getFooterLayout() {
		return footerLayout;
	}

	protected final LoadingLayout getHeaderLayout() {
		return headerLayout;
	}

	protected final int getHeaderHeight() {
		return headerHeight;
	}

	protected final int getMode() {
		return mode;
	}

	/**
	 * 是否准备好下拉（列表已经在最顶端）
	 * 
	 * @version 1.0
	 * @createTime 2013-12-28,下午2:09:37
	 * @updateTime 2013-12-28,下午2:09:37
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return
	 */
	protected abstract boolean isReadyForPullDown();

	/**
	 * 是否准备好上拉（列表是否已经在最底端）
	 * 
	 * @version 1.0
	 * @createTime 2013-12-28,下午2:10:15
	 * @updateTime 2013-12-28,下午2:10:15
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return
	 */
	protected abstract boolean isReadyForPullUp();

	// ===========================================================
	// Methods
	// ===========================================================

	protected void resetHeader() {
		state = PULL_TO_REFRESH;
		isBeingDragged = false;

		if (null != headerLayout) {
			headerLayout.reset();
		}
		if (null != footerLayout) {
			footerLayout.reset();
		}

		smoothScrollTo(0);
	}

	protected void setRefreshingInternal(boolean doScroll) {
		state = REFRESHING;

		if (null != headerLayout) {
			headerLayout.refreshing();
		}
		if (null != footerLayout) {
			footerLayout.refreshing();
		}

		if (doScroll) {
			smoothScrollTo(currentMode == MODE_PULL_DOWN_TO_REFRESH ? -headerHeight : headerHeight);
		}
	}

	protected final void setHeaderScroll(int y) {
		scrollTo(0, y);
	}

	/**
	 * 滑动列表到指定位置
	 * 
	 * @version 1.0
	 * @createTime 2013-12-28,下午2:12:55
	 * @updateTime 2013-12-28,下午2:12:55
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param y
	 */
	protected final void smoothScrollTo(int y) {
		if (null != currentSmoothScrollRunnable) {
			currentSmoothScrollRunnable.stop();
		}

		if (this.getScrollY() != y) {
			this.currentSmoothScrollRunnable = new SmoothScrollRunnable(handler, getScrollY(), y);
			handler.post(currentSmoothScrollRunnable);
		}
	}

	/**
	 * 初始化
	 * 
	 * @version 1.0
	 * @createTime 2013-12-28,下午2:14:07
	 * @updateTime 2013-12-28,下午2:14:07
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context
	 *        上下文
	 * @param attrs
	 *        详情参数配置
	 */
	@SuppressWarnings("deprecation")
	public void init(Context context, AttributeSet attrs) {

		setOrientation(LinearLayout.VERTICAL);

		touchSlop = ViewConfiguration.getTouchSlop();

		// Styleables from XML
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PullToRefresh);
		if (a.hasValue(R.styleable.PullToRefresh_mode2)) {
			mode = a.getInteger(R.styleable.PullToRefresh_mode2, MODE_PULL_DOWN_TO_REFRESH);
		}
		String pull1 = a.getString(R.styleable.PullToRefresh_pull_label1);
		String pull2 = a.getString(R.styleable.PullToRefresh_pull_label2);
		String pull3 = a.getString(R.styleable.PullToRefresh_pull_label3);
		String down1 = a.getString(R.styleable.PullToRefresh_down_label1);
		String down2 = a.getString(R.styleable.PullToRefresh_down_label2);
		String down3 = a.getString(R.styleable.PullToRefresh_down_label3);
		// Refreshable View
		// By passing the attrs, we can add ListView/GridView params via XML
		refreshableView = this.createRefreshableView(context, attrs);
		this.addRefreshableView(context, refreshableView);
		// 加载界面的文字描述 Loading View Strings
		pull_label1 = TextUtils.isEmpty(pull1) ? context.getString(R.string.pull_to_refresh_pull_label1) : pull1;
		pull_label2 = TextUtils.isEmpty(pull2) ? context.getString(R.string.pull_to_refresh_pull_label2) : pull2;
		pull_label3 = TextUtils.isEmpty(pull3) ? context.getString(R.string.pull_to_refresh_pull_label5) : pull3;
		down_label1 = TextUtils.isEmpty(down1) ? context.getString(R.string.pull_to_refresh_down_label1) : down1;
		down_label2 = TextUtils.isEmpty(down2) ? context.getString(R.string.pull_to_refresh_down_label2) : down2;
		down_label3 = TextUtils.isEmpty(down3) ? context.getString(R.string.pull_to_refresh_down_label5) : down3;

		// Add Loading Views
		if (mode == MODE_PULL_DOWN_TO_REFRESH || mode == MODE_BOTH) {
			headerLayout = new LoadingLayout(context, MODE_PULL_DOWN_TO_REFRESH, pull_label2, pull_label1, pull_label3);
			addView(headerLayout, 0, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
			measureView(headerLayout);
			headerHeight = headerLayout.getMeasuredHeight();
		}
		if (mode == MODE_PULL_UP_TO_REFRESH || mode == MODE_BOTH) {
			footerLayout = new LoadingLayout(context, MODE_PULL_UP_TO_REFRESH, down_label2, down_label1, down_label3);
			addView(footerLayout, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
			measureView(footerLayout);
			headerHeight = footerLayout.getMeasuredHeight();
			if (refreshableView instanceof ListView || refreshableView instanceof DeleteListView) {
				LinearLayout footerParent = new LinearLayout(context);
				pull_to_footview = View.inflate(context, R.layout.pull_to_footview, null);
				View pull_to_footview_rela = pull_to_footview.findViewById(R.id.pull_to_footview_rela);
				ViewGroup.LayoutParams params = pull_to_footview_rela.getLayoutParams();
				params.height = DisplayUtil.dip2px(context, 40);
				pull_to_footview_rela.setLayoutParams(params);
				footerParent.addView(pull_to_footview);
				pull_to_footview.setVisibility(View.GONE);
				((ListView) refreshableView).addFooterView(footerParent, null, false);
			}
		}

		// Styleables from XML
		if (a.hasValue(R.styleable.PullToRefresh_headerTextColor)) {
			final int color = a.getColor(R.styleable.PullToRefresh_headerTextColor, Color.BLACK);
			if (null != headerLayout) {
				headerLayout.setTextColor(color);
			}
			if (null != footerLayout) {
				footerLayout.setTextColor(color);
			}
		}

		if (a.hasValue(R.styleable.PullToRefresh_headerBackground)) {
			this.setBackgroundResource(a.getResourceId(R.styleable.PullToRefresh_headerBackground, Color.WHITE));
		}

		if (a.hasValue(R.styleable.PullToRefresh_adapterViewBackground)) {
			refreshableView.setBackgroundResource(a.getResourceId(R.styleable.PullToRefresh_adapterViewBackground, Color.WHITE));
		}
		a.recycle();

		// Hide Loading Views
		switch (mode) {
		case MODE_BOTH:
			setPadding(0, -headerHeight, 0, -headerHeight);
			break;
		case MODE_PULL_UP_TO_REFRESH:
			setPadding(0, 0, 0, -headerHeight);
			break;
		case MODE_PULL_DOWN_TO_REFRESH:
		default:
			setPadding(0, -headerHeight, 0, 0);
			break;
		}

		// If we're not using MODE_BOTH, then just set currentMode to current
		// mode
		if (mode != MODE_BOTH) {
			currentMode = mode;
		}
	}

	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	/**
	 * Actions a Pull Event
	 * 
	 * @return true if the Event has been handled, false if there has been no change
	 */
	private boolean pullEvent() {

		final int newHeight;
		final int oldHeight = this.getScrollY();

		switch (currentMode) {
		case MODE_PULL_UP_TO_REFRESH:
			newHeight = Math.round(Math.max(initialMotionY - lastMotionY, 0) / FRICTION);
			// newHeight = Math.round((initialMotionY - lastMotionY) /
			// FRICTION);
			break;
		case MODE_PULL_DOWN_TO_REFRESH:
		default:
			newHeight = Math.round(Math.min(initialMotionY - lastMotionY, 0) / FRICTION);
			// newHeight = Math.round((initialMotionY - lastMotionY) /
			// FRICTION);
			break;
		}

		setHeaderScroll(newHeight);

		if (newHeight != 0) {
			if (state == PULL_TO_REFRESH && headerHeight < Math.abs(newHeight)) {
				state = RELEASE_TO_REFRESH;

				switch (currentMode) {
				case MODE_PULL_UP_TO_REFRESH:
					footerLayout.releaseToRefresh();
					break;
				case MODE_PULL_DOWN_TO_REFRESH:
					headerLayout.releaseToRefresh();
					break;
				}

				return true;

			} else if (state == RELEASE_TO_REFRESH && headerHeight >= Math.abs(newHeight)) {
				state = PULL_TO_REFRESH;

				switch (currentMode) {
				case MODE_PULL_UP_TO_REFRESH:
					footerLayout.pullToRefresh();
					break;
				case MODE_PULL_DOWN_TO_REFRESH:
					headerLayout.pullToRefresh();
					break;
				}

				return true;
			}
		}

		return oldHeight != newHeight;
	}

	/**
	 * 是否准备好拖动
	 * 
	 * @version 1.0
	 * @createTime 2013-12-4,上午10:59:08
	 * @updateTime 2013-12-4,上午10:59:08
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return
	 */
	private boolean isReadyForPull() {
		switch (mode) {
		case MODE_PULL_DOWN_TO_REFRESH:
			return isReadyForPullDown();
		case MODE_PULL_UP_TO_REFRESH:
			return isReadyForPullUp();
		case MODE_BOTH:
			return isReadyForPullUp() || isReadyForPullDown();
		}
		return false;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	/**
	 * 刷新监听回调接口
	 * 
	 * @Description TODO
	 * @author CodeApe
	 * @version 1.0
	 * @date 2013-12-3
	 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
	 * 
	 */
	public static interface OnRefreshListener {

		/**
		 * 下拉刷新
		 * 
		 * @version 1.0
		 * @createTime 2013-12-4,上午11:03:56
		 * @updateTime 2013-12-4,上午11:03:56
		 * @createAuthor CodeApe
		 * @updateAuthor CodeApe
		 * @updateInfo (此处输入修改内容,若无修改可不写.)
		 * 
		 */
		public void onPullToDownRefresh();

		/**
		 * 上拉加载更多
		 * 
		 * @version 1.0
		 * @createTime 2013-12-4,上午11:04:11
		 * @updateTime 2013-12-4,上午11:04:11
		 * @createAuthor CodeApe
		 * @updateAuthor CodeApe
		 * @updateInfo (此处输入修改内容,若无修改可不写.)
		 * 
		 */
		public void onPullToUpRefresh();

	}

	/**
	 * 列表滑动到底部的时候监听
	 * 
	 * @Description TODO
	 * @author CodeApe
	 * @version 1.0
	 * @date 2013-12-28
	 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
	 * 
	 */
	public static interface OnLastItemVisibleListener {

		public void onLastItemVisible();

	}

	@Override
	public void setLongClickable(boolean longClickable) {
		getRefreshableView().setLongClickable(longClickable);
	}

	/**
	 * 显示没有更多内容的底部
	 * 
	 * @version 1.0
	 * @createTime 2015-5-19,下午2:22:55
	 * @updateTime 2015-5-19,下午2:22:55
	 * @createAuthor 綦巍
	 * @updateAuthor 綦巍
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param noMore
	 */
	public void setNoMore(boolean noMore) {
		if (noMore) {
			mode = MODE_PULL_DOWN_TO_REFRESH;
			currentMode = MODE_PULL_DOWN_TO_REFRESH;
			if (pull_to_footview != null) {
				pull_to_footview.setVisibility(View.VISIBLE);
			}
		} else {
			mode = MODE_BOTH;
			currentMode = MODE_BOTH;
			if (pull_to_footview != null) {
				pull_to_footview.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * 手动开启下拉刷新
	 * 
	 * @version 1.0
	 * @createTime 2015-4-9,下午7:10:37
	 * @updateTime 2015-4-9,下午7:10:37
	 * @createAuthor 綦巍
	 * @updateAuthor 綦巍
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 */
	public void goRefresh() {
		setHeaderScroll(-headerHeight);
		isBeingDragged = false;
		state = RELEASE_TO_REFRESH;
		currentMode = MODE_PULL_DOWN_TO_REFRESH;
		if (null != onRefreshListener) {
			setRefreshingInternal(true);
			onRefreshListener.onPullToDownRefresh();
		}
	}

	/**
	 * 设置不可上拉和下拉刷新
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年6月26日,上午11:54:15
	 * @updateTime 2015年6月26日,上午11:54:15
	 * @createAuthor yanzhong
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param bool
	 */
	public void setRefreshable(boolean canRefreshable) {
		if (canRefreshable) {
			mode = MODE_BOTH;
			currentMode = MODE_BOTH;
		} else {
			mode = MODE_NO;
			currentMode = MODE_NO;
		}
	}
}

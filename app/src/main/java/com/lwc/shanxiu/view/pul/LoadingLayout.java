package com.lwc.shanxiu.view.pul;

import com.lwc.shanxiu.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * 加载视图
 *
 * @Description TODO
 * @author CodeApe
 * @version 1.0
 * @date 2013-12-4
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 *
 */

public class LoadingLayout extends FrameLayout {

	static final int DEFAULT_ROTATION_ANIMATION_DURATION = 150;

	/** 刷新箭头 */
	private final CircleProgressBarView circleProgressBarView;
	/** 加载等待框 */
	private final View head_progressBar;
	/** 加载提示信息 */
	private final TextView head_tipsTextView;

	private String pullLabel;
	private String refreshingLabel;
	private String releaseLabel;

	// private final Animation rotateAnimation, resetRotateAnimation;
	private final RotateAnimation ra;

	public LoadingLayout(Context context, final int mode, String releaseLabel, String pullLabel, String refreshingLabel) {
		super(context);
		ra = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		ra.setDuration(1000);
		LinearInterpolator lin = new LinearInterpolator();
		ra.setInterpolator(lin);
		ra.setRepeatCount(Animation.INFINITE);
		ViewGroup header = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_head_elastic_scroll, this);
		head_tipsTextView = (TextView) header.findViewById(R.id.head_tipsTextView);
		circleProgressBarView = (CircleProgressBarView) header.findViewById(R.id.CircleProgressBarView2);
		head_progressBar = header.findViewById(R.id.head_progressBar);

		// final Interpolator interpolator = new LinearInterpolator();
		// rotateAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		// rotateAnimation.setInterpolator(interpolator);
		// rotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
		// rotateAnimation.setFillAfter(true);
		//
		// resetRotateAnimation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		// resetRotateAnimation.setInterpolator(interpolator);
		// resetRotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
		// resetRotateAnimation.setFillAfter(true);

		this.releaseLabel = releaseLabel;
		this.pullLabel = pullLabel;
		this.refreshingLabel = refreshingLabel;

		switch (mode) {
		case PullToRefreshBase.MODE_PULL_UP_TO_REFRESH:
			// headerImage.setImageResource(R.drawable.pulltorefresh_up_arrow);
			break;
		case PullToRefreshBase.MODE_PULL_DOWN_TO_REFRESH:
		default:
			// headerImage.setImageResource(R.drawable.pulltorefresh_down_arrow);
			break;
		}
	}

	public void reset() {
		head_tipsTextView.setText(pullLabel);
		circleProgressBarView.setVisibility(View.GONE);
		head_progressBar.setVisibility(View.GONE);
		head_progressBar.clearAnimation();
	}

	public void releaseToRefresh() {
		head_tipsTextView.setText(releaseLabel);
	}

	public void setPullLabel(String pullLabel) {
		this.pullLabel = pullLabel;
	}

	/**
	 * 显示刷新中的界面
	 *
	 * @version 1.0
	 * @createTime 2013-12-28,下午1:40:00
	 * @updateTime 2013-12-28,下午1:40:00
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param refreshingLabel
	 */
	public void refreshing() {
		head_tipsTextView.setText(refreshingLabel);
		circleProgressBarView.setVisibility(View.INVISIBLE);
		head_progressBar.setVisibility(View.VISIBLE);
		head_progressBar.startAnimation(ra);
	}

	/**
	 * 设置刷新提示文字
	 *
	 * @version 1.0
	 * @createTime 2013-12-28,下午1:41:12
	 * @updateTime 2013-12-28,下午1:41:12
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param refreshingLabel
	 */
	public void setRefreshingLabel(String refreshingLabel) {
		this.refreshingLabel = refreshingLabel;
	}

	/**
	 * 显示松开刷新的文字
	 *
	 * @version 1.0
	 * @createTime 2013-12-28,下午1:41:29
	 * @updateTime 2013-12-28,下午1:41:29
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param releaseLabel
	 */
	public void setReleaseLabel(String releaseLabel) {
		this.releaseLabel = releaseLabel;
	}

	/**
	 * 显示推动刷新的文本
	 *
	 * @version 1.0
	 * @createTime 2013-12-28,下午1:41:59
	 * @updateTime 2013-12-28,下午1:41:59
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 */
	public void pullToRefresh() {
		head_tipsTextView.setText(pullLabel);
		// headerImage.clearAnimation();
		// headerImage.startAnimation(resetRotateAnimation);
	}

	public void setTextColor(int color) {
		head_tipsTextView.setTextColor(color);
	}

	public void setMax(int max) {
		circleProgressBarView.setMax(max);
	}

	public void setProgress(int progress) {
		circleProgressBarView.setProgress(progress);
	}

}

package com.lwc.shanxiu.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 图片浏览器切换图片控件
 *
 * @author 何栋
 * @version 1.0
 * @date 2013-8-28
 *
 */
public class ImageBrowseViewpager extends ViewPager {

	/** 是否拦截viewpager的切换事件 */
	public static boolean VIEWPAGER_CANSCROLL = true;

	public ImageBrowseViewpager(Context context) {
		super(context);

	}

	public ImageBrowseViewpager(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	@Override
	protected boolean canScroll(View arg0, boolean arg1, int arg2, int arg3, int arg4) {
		return super.canScroll(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void computeScroll() {
		super.computeScroll();
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if (VIEWPAGER_CANSCROLL) {
			return super.onTouchEvent(arg0);
		} else {
			return false;
		}

	}

}

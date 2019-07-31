package com.lwc.shanxiu.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RadioButton;

import java.util.HashMap;

/**
 * 自定义Viewpager
 */
public class CustomViewPager extends ViewPager {

    private boolean isPagingEnabled = true;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //isPagingEnabled 等于 FALSE 就拦截掉 触摸事件
        return this.isPagingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onInterceptTouchEvent(event);
    }

    /**
     * 设置是否能滑动
     *
     * @param b true 可以滑动 ； flase  不可以滑动
     */
    public void setPagingEnabled(boolean b) {
        this.isPagingEnabled = b;
    }


    /**
     * 设置RadioButton是否选中
     * viewpager 大多数和 RadioButton配合使用
     *
     * @param rBtnHashMap 依次put RadioButton
     * @param position
     */
    public void setChecked(HashMap<Integer, RadioButton> rBtnHashMap, int position) {
        for (int i = 0; i < rBtnHashMap.size(); i++) {

            if (position == i) {
                RadioButton radioButton = rBtnHashMap.get(i);
                radioButton.setChecked(true);
            }
        }
    }
}
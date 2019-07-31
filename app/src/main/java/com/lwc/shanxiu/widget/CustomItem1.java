package com.lwc.shanxiu.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lwc.shanxiu.R;


/**
 * Created by 何栋 on 2016/12/15.
 * 294663966@qq.com
 * 双TextView, 左右
 */

public class CustomItem1 extends FrameLayout {

    private Context context;
    private TextView txtLeft, txtRight;
    private LinearLayout lLayout;

    public CustomItem1(Context context, AttributeSet attrs) {
        super(context, attrs);

        View v = LayoutInflater.from(context).inflate(R.layout.custom_item1, this);
        initView(v);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomItem1);

        //文字在屏幕中适配大小， 要用sp  ,以下两个方法没法完成屏幕适配
//        float txtSize = typedArray.getFloat(R.styleable.CustomItem1_text_left_size1, 28);
//        txtSize = DisplayUtils.px2sp(context, txtSize);
//        setLeftTxtSize(txtSize);
//
//        float txtRightSize = typedArray.getFloat(R.styleable.CustomItem1_text_right_size1, 28);
//        txtRightSize = DisplayUtils.px2sp(context, txtRightSize);
//        setRightTxtSize(txtRightSize);

        String nameLeft = typedArray.getString(R.styleable.CustomItem1_text_left1);
        setLeftText(nameLeft);

        String nameRight = typedArray.getString(R.styleable.CustomItem1_text_right1);
        setRightText(nameRight);

        int gravityRight = typedArray.getInt(R.styleable.CustomItem1_text_right_gravity1, 168);
        setRightTextGravity(gravityRight);

        int gravityLeft = typedArray.getInt(R.styleable.CustomItem1_text_left_gravity1, 168);
        setLeftTextGravity(gravityLeft);

        int color = typedArray.getInt(R.styleable.CustomItem1_text_right_color1, 168);
        setRightTextColor(color);


    }

    private void initView(View v) {

        txtLeft = (TextView) v.findViewById(R.id.txtLeft);
        txtRight = (TextView) v.findViewById(R.id.txtRight);
        lLayout = (LinearLayout) v.findViewById(R.id.lLayout);
    }

    /**
     * 设置文字的大小
     *
     * @param size
     */
    public void setLeftTxtSize(float size) {

        txtLeft.setTextSize(size);
    }

    /**
     * 设置文字的大小
     *
     * @param size
     */
    public void setRightTxtSize(float size) {

        txtRight.setTextSize(size);
    }

    /**
     * @param name
     */
    public void setLeftText(String name) {
        txtLeft.setText(name);
    }

    public void setRightText(String name) {

        txtRight.setText(name);
    }

    public void setRightTextColor(int color) {

        if (color == 168) {
            return;
        }
        txtRight.setTextColor(color);

    }

    public void setRightTextGravity(int gravity) {
        if (gravity == 168) {
            return;
        }

        switch (gravity) {

            case 0:
                txtRight.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                break;

            case 1:
                txtRight.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                break;
        }
    }


    /**
     * 设置左边文本的内容布局
     * 注： 用此方法将是 垂直居中
     *
     * @param gravity
     */
    public void setLeftTextGravity(int gravity) {
        if (gravity == 168) {
            return;
        }

        switch (gravity) {

            case 0:
                txtLeft.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);

                break;

            case 1:
                txtLeft.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                break;
        }
    }

    /**
     * 设置父容器的内边距
     *
     * @param parentPadding
     */
    public void setParentPadding(int parentPadding) {

        if (parentPadding == 0) {
            return;
        }
        lLayout.setPadding(parentPadding, parentPadding, parentPadding, parentPadding);
    }

    /**
     * 获取右边文本内容
     *
     * @return
     */
    public String getRightText() {

        return txtRight.getText().toString();
    }

    /**
     * 获取左边文本内容
     *
     * @return
     */
    public String getLeftText() {

        return txtLeft.getText().toString();
    }

    public TextView getRightTextView() {
        return txtRight;
    }
}

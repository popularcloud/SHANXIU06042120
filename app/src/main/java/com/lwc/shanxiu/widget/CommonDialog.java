package com.lwc.shanxiu.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lwc.shanxiu.R;

public class CommonDialog extends Dialog {


    public CommonDialog(@NonNull Context context) {
        super(context);
    }

    @Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.include_btn);
    //按空白处不能取消动画
    setCanceledOnTouchOutside(false);
    //初始化界面控件
    initView();
    //初始化界面控件的事件
    initEvent();
}

/**
 * 初始化界面的确定和取消监听器
 */
private void initEvent() {
}


@Override
public void show() {
    super.show();
}

/**
 * 初始化界面控件
 */
private void initView() {

}

/**
 * 设置确定取消按钮的回调
 */
public OnClickBottomListener onClickBottomListener;
public CommonDialog setOnClickBottomListener(OnClickBottomListener onClickBottomListener) {
    this.onClickBottomListener = onClickBottomListener;
    return this;
}
public interface OnClickBottomListener{
    /**
     * 点击确定按钮事件
     */
    public void onPositiveClick();
    /**
     * 点击取消按钮事件
     */
    public void onNegtiveClick();
}
}

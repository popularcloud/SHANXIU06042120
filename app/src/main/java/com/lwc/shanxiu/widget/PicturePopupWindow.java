
package com.lwc.shanxiu.widget;


import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.lwc.shanxiu.R;


/**
 * 拍照和图库
 */
public class PicturePopupWindow extends PopupWindow implements OnClickListener {

    private final Button btn4;
    private final View line4;
    private View line3;
    private Button albumBtn, photoGraphBtn, cancelBtn, btn3;

    private View mMenuView;
    private Activity context;
    private CallBack callBack;

    public PicturePopupWindow(Activity context, CallBack callBack, String one, String two, String three, String four) {
        super(context);
        this.context = context;
        this.callBack = callBack;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        /**
         * 定制界面
         */
        mMenuView = inflater.inflate(R.layout.cover_select_pop_layout, null);
        albumBtn = (Button) mMenuView.findViewById(R.id.btn_album);
        photoGraphBtn = (Button) mMenuView.findViewById(R.id.btn_photo_graph);
        btn3 = (Button) mMenuView.findViewById(R.id.btn3);
        line3 = mMenuView.findViewById(R.id.line3);
        btn4 = (Button) mMenuView.findViewById(R.id.btn4);
        line4 = mMenuView.findViewById(R.id.line4);
        cancelBtn = (Button) mMenuView.findViewById(R.id.btn_cancel_join);
        // 设置按钮监听
        photoGraphBtn.setText(one);
        albumBtn.setText(two);
        albumBtn.setOnClickListener(this);
        photoGraphBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        if (three != null) {
            line3.setVisibility(View.VISIBLE);
            btn3.setVisibility(View.VISIBLE);
            btn3.setText(three);
            btn3.setOnClickListener(this);
        }
        if (four != null) {
            line4.setVisibility(View.VISIBLE);
            btn4.setVisibility(View.VISIBLE);
            btn4.setText(four);
            btn4.setOnClickListener(this);
        }
        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);

        /**
         * 定制动画
         */
        // 设置SelectPicPopupWindow弹出窗体动画效果,可以有其他效果
        this.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });

    }

    public void backgroundAlpha(float f) {
        WindowManager.LayoutParams lp =context.getWindow().getAttributes();
        lp.alpha = f; //0.0-1.0
        if (f == 1) {
            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        context.getWindow().setAttributes(lp);
    }

    public void showWindow() {

        View view = context.getWindow().getDecorView();
        int screenHeight = view.getHeight();
        Rect viewRect = new Rect();
        view.getWindowVisibleDisplayFrame(viewRect);
        int offsetY = screenHeight - viewRect.bottom;
        showAtLocation(view, Gravity.BOTTOM, 0, offsetY);
        backgroundAlpha(0.6f);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_album:
                dismiss();
                callBack.twoClick();
                break;
            case R.id.btn_photo_graph:
                dismiss();
                callBack.oneClick();
                break;
            case R.id.btn_cancel_join:
                dismiss();
                callBack.cancelCallback();
                break;
            case R.id.btn3:
                dismiss();
                callBack.threeClick();
                break;
            case R.id.btn4:
                dismiss();
                callBack.fourClick();
                break;
        }
    }

    public interface CallBack {

        /**
         * 调用图库
         */
        void oneClick();

        /**
         * 拍照
         */
        void twoClick();

        /**
         * 取消
         */
        void cancelCallback();

        void threeClick();

        void fourClick();
    }
}


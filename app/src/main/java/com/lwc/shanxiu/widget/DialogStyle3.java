package com.lwc.shanxiu.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.lwc.shanxiu.R;

/**
 * @author 何栋
 * @version 1.0
 * @date 2017/4/24 14:51
 * @email 294663966@qq.com
 */
public class DialogStyle3 extends Dialog {

    private TextView txtContent;
    private Button btnLeft;
    private Button btnRight;
    private Context context;

    public DialogStyle3(Context context) {
        super(context, R.style.dialog2);
        //去掉标题栏
        Window win = getWindow();
        win.requestFeature(Window.FEATURE_NO_TITLE);
        this.context = context;
        this.setContentView(LayoutInflater.from(context).inflate(R.layout.dialog3_text, null));
        initViews();

    }


    private void initViews() {
        btnLeft = (Button) findViewById(R.id.btnLeft);
        btnRight = (Button) findViewById(R.id.btnRight);
        txtContent = (TextView) findViewById(R.id.txtContent);
    }


    /**
     * 设置内容
     *
     * @param content
     */
    public void setContext(String content) {
        txtContent.setText(content);
    }

    /**
     * 初始化dialog的内容
     *
     * @param content
     * @param leftBtnName
     * @param rightBtnName
     * @return
     */
    public DialogStyle3 initDialogContent(String content, String leftBtnName, String rightBtnName) {

        if (!TextUtils.isEmpty(content) && content != null) {
            txtContent.setText(content);
        }

        if (leftBtnName != null) {
            btnLeft.setText(leftBtnName);
        }

        if (rightBtnName != null) {
            btnRight.setText(rightBtnName);
        }
        return this;
    }

    /**
     * 设置对话框的点击事件
     *
     * @param listener
     */
    public DialogStyle3 setDialogClickListener(final DialogStyle3.DialogClickListener listener) {
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.leftClick(context, DialogStyle3.this);
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.rightClick(context, DialogStyle3.this);
            }
        });

        return this;
    }


    /**
     * 显示
     */
    public void showDialog1() {
        if (!isShowing())
            this.show();
    }

    /**
     * 隐藏
     */
    public void dismissDialog1() {
        if (isShowing())
            this.dismiss();
    }

    public interface DialogClickListener {
        void leftClick(Context context, DialogStyle3 dialog);

        void rightClick(Context context, DialogStyle3 dialog);
    }
}
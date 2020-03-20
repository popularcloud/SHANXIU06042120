package com.lwc.shanxiu.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lwc.shanxiu.R;


/**
 * Created by yang on 2016/10/10.
 * 374353845@qq.com
 * 弹框样式1
 */
public class DialogStyle1 extends Dialog {

    private TextView txtTitle;
    private Button btnLeft;
    private Button btnRight;
    private Context context;
    private EditText edtContent;

    public DialogStyle1(Context context) {
        super(context,R.style.dialog2);

        //去掉标题栏
        Window win = getWindow();
        win.requestFeature(Window.FEATURE_NO_TITLE);
        this.context = context;
        this.setContentView(LayoutInflater.from(context).inflate(R.layout.dialog1_text, null));
        initViews();
    }

    private void initViews() {
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        btnLeft = (Button) findViewById(R.id.btnLeft);
        btnRight = (Button) findViewById(R.id.btnRight);
        edtContent = (EditText) findViewById(R.id.edtContext);
    }

    public void setEdtEnabled(boolean bool) {
        edtContent.setEnabled(bool);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        txtTitle.setText(title);
    }

    public String getEdtText() {
        return edtContent.getText().toString();
    }

    public DialogStyle1 setEdtText(String content) {
        edtContent.setText(content);
        return this;
    }

    /**
     * 获取右边按钮
     * @return
     */
    public Button getRightBtn() {
        return btnRight;
    }

    /**
     * 初始化dialog的内容
     *
     * @param title
     * @param leftBtn
     * @param rightBtn
     * @return
     */
    public DialogStyle1 initDialogContent(String title, String leftBtn, String rightBtn) {
        if (TextUtils.isEmpty(title))
            txtTitle.setVisibility(View.GONE);
        else
            txtTitle.setText(title);
        if (leftBtn != null) {
            btnLeft.setText(leftBtn);
        }

        if (rightBtn != null) {
            btnRight.setText(rightBtn);
        }
        return this;
    }

    /**
     * 设置对话框的点击事件
     *
     * @param listener
     */
    public DialogStyle1 setDialogClickListener(final DialogClickListener listener) {
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.leftClick(context, DialogStyle1.this);
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.rightClick(context, DialogStyle1.this);
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
        void leftClick(Context context, DialogStyle1 dialog);

        void rightClick(Context context, DialogStyle1 dialog);
    }
}

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
public class DialogStyle2 extends Dialog {

    private TextView txtContent;
    private Button btnAffirm;
    private Context context;

    public DialogStyle2(Context context) {
        super(context);

        //去掉标题栏
        Window win = getWindow();
        win.requestFeature(Window.FEATURE_NO_TITLE);
        this.context = context;
        this.setContentView(LayoutInflater.from(context).inflate(R.layout.dialog2_text, null));
        initViews();


    }

    private void initViews() {
        btnAffirm = (Button) findViewById(R.id.btnAffirm);
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
     * @param btnName
     * @return
     */
    public DialogStyle2 initDialogContent(String content, String btnName) {

        if (!TextUtils.isEmpty(content) && content != null) {
            txtContent.setText(content);
        }

        if (btnName != null) {
            btnAffirm.setText(btnName);
        }
        return this;
    }

    /**
     * 设置对话框的点击事件
     *
     * @param listener
     */
    public DialogStyle2 setDialogClickListener(final DialogClickListener listener) {
        btnAffirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.affirmClick(context, DialogStyle2.this);
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
        void affirmClick(Context context, DialogStyle2 dialog);
    }
}

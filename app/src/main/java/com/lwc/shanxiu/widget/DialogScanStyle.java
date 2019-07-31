package com.lwc.shanxiu.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.interf.OnBtnClickCalBack;
import com.lwc.shanxiu.utils.ToastUtil;


/**
 * Created by yang on 2016/10/10.
 * 374353845@qq.com
 * 弹框样式1
 */
public class DialogScanStyle extends Dialog {

    private ImageView iv_scan;
    private ImageView iv_write;
    private ImageView iv_close;
    private TextView btnAffirm;
    private LinearLayout ll_write_num;
    private EditText et_num;

    OnBtnClickCalBack onBtnClickCalBack;

    private Context context;

    public DialogScanStyle(Context context, OnBtnClickCalBack onBtnClickCalBack) {
        super(context);

        this.onBtnClickCalBack = onBtnClickCalBack;
        //去掉标题栏
        Window win = getWindow();
        win.requestFeature(Window.FEATURE_NO_TITLE);
        this.context = context;
        this.setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_scant, null));
        initViews();
    }

    private void initViews() {
        btnAffirm = (TextView) findViewById(R.id.btnAffirm);
        iv_scan = (ImageView) findViewById(R.id.iv_scan);
        iv_write = (ImageView) findViewById(R.id.iv_write);
        ll_write_num = (LinearLayout) findViewById(R.id.ll_write_num);
        et_num = (EditText) findViewById(R.id.et_num);
        iv_close = (ImageView) findViewById(R.id.iv_close);


        iv_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanChecked(iv_scan);
            }
        });
        iv_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanChecked(iv_write);
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnAffirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int isVisiBility = ll_write_num.getVisibility();
                if(isVisiBility == View.GONE){  //扫码
                    onBtnClickCalBack.onClick(null);
                    dismiss();
                }else{  //填写编号
                   String numStr = et_num.getText().toString().trim();
                    if(TextUtils.isEmpty(numStr)){
                        ToastUtil.showToast(context,"请输入二维码编号");
                        return;
                    }
                    if(onBtnClickCalBack != null){
                        dismiss();
                        onBtnClickCalBack.onClick(numStr);
                    }
                }
            }
        });
    }


    private void scanChecked(ImageView presentView){
        if(presentView == iv_scan){
            iv_scan.setImageResource(R.drawable.ic_checked_scan);
            iv_write.setImageResource(R.drawable.ic_nochecked_scan);
            ll_write_num.setVisibility(View.GONE);
        }else{
            iv_scan.setImageResource(R.drawable.ic_nochecked_scan);
            iv_write.setImageResource(R.drawable.ic_checked_scan);
            ll_write_num.setVisibility(View.VISIBLE);
        }
    }
    /**
     * 设置内容
     *
     * @param content
     */
    public void setContext(String content) {
      //  txtContent.setText(content);
    }

    /**
     * 初始化dialog的内容
     *
     * @param content
     * @param btnName
     * @return
     */
    public DialogScanStyle initDialogContent(String content, String btnName) {

        if (!TextUtils.isEmpty(content) && content != null) {
           // txtContent.setText(content);
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
    public DialogScanStyle setDialogClickListener(final DialogClickListener listener) {
        btnAffirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.affirmClick(context, DialogScanStyle.this);
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
        void affirmClick(Context context, DialogScanStyle dialog);
    }
}

package com.lwc.shanxiu.module.user;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 修改个性签名
 * @author younge
 * @date 2018-08-08
 */
public class UpdateSignActivity extends BaseActivity {

    @BindView(R.id.txt_mention_1)
    TextView txt_mention_1;
    @BindView(R.id.et_mention_1)
    EditText et_mention_1;
    @BindView(R.id.btn_comment_submit)
    TextView btn_comment_submit;

    SharedPreferencesUtils preferencesUtils;
    private User user = null;
    private String signature;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_update_sign;
    }

    /**
     * 初始化布局组件
     */
    @Override
    protected void findViews() {
        ButterKnife.bind(this);

        String signature = getIntent().getStringExtra("signature");
        if(!TextUtils.isEmpty(signature)){
            et_mention_1.setText(signature);
            et_mention_1.setSelection(signature.length());
        }

        btn_comment_submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
              //  if (!TextUtils.isEmpty(et_mention_1.getText().toString().trim())) {
                    if (Utils.isFastClick(3000)) {
                        return;
                    }
                    upUserInfor(et_mention_1.getText().toString().trim());
                /*} else {
                    ToastUtil.showLongToast(UpdateSignActivity.this, "请输入您的签名！");
                }*/
            }
        });
        et_mention_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                txt_mention_1.setText(String.valueOf(s.length())+"/30");
            }
        });
        showBack();
    }

    /**
     * 点击返回按钮回调方法
     *
     * @param view
     */
    public void onBack(View view) {
        onBackPressed();
    }

    /**
     * 初始化基础数据
     */
    @Override
    protected void init() {
        setTitle("修改个性签名");
    }

    @Override
    protected void initGetData() {
    }

    @Override
    protected void widgetListener() {
    }

    /**
     * 更新用户信息
     *
     * @param sign
     */
    private void upUserInfor(String sign) {
        Map<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(sign)){
            map.put("signature", sign);
        }else{
            map.put("signature", "");
        }


        HttpRequestUtils.httpRequest(this, "修改个性签名", RequestValue.UP_USER_INFOR, map, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        ToastUtil.showLongToast(UpdateSignActivity.this, common.getInfo());
                        finish();
                        break;
                    default:
                        ToastUtil.showLongToast(UpdateSignActivity.this, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                if (msg != null && !msg.equals("")) {
                    ToastUtil.showLongToast(UpdateSignActivity.this, msg);
                } else {
                    ToastUtil.showLongToast(UpdateSignActivity.this, "请求失败，请稍候重试!");
                }
            }
        });
    }
}

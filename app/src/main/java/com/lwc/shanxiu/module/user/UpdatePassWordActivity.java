package com.lwc.shanxiu.module.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 修改密码
 * @author younge
 * @date 2018-08-08
 */
public class UpdatePassWordActivity extends BaseActivity {

    @BindView(R.id.txt_mention_1)
    TextView txt_mention_1;
    @BindView(R.id.et_mention_1)
    EditText et_mention_1;
    @BindView(R.id.txt_mention_2)
    TextView txt_mention_2;
    @BindView(R.id.et_mention_2)
    EditText et_mention_2;
    @BindView(R.id.et_mention_3)
    EditText et_mention_3;
    @BindView(R.id.btn_comment_submit)
    TextView btn_comment_submit;

    SharedPreferencesUtils preferencesUtils;
    private User user = null;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_update_pass_word;
    }

    /**
     * 初始化布局组件
     */
    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        btn_comment_submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkData()) {
                    if (Utils.isFastClick(3000)) {
                        return;
                    }
                    upUserInfor(et_mention_2.getText().toString().trim());
                }
            }
        });
        preferencesUtils = SharedPreferencesUtils.getInstance(this);
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
        setTitle("修改密码");
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
     * @param pwd
     */
    private void upUserInfor(String pwd) {
        Map<String, String> map = new HashMap<>();
        if (pwd != null)
            map.put("password", Utils.md5Encode(pwd));

        HttpRequestUtils.httpRequest(this, "修改密码", RequestValue.UP_USER_INFOR, map, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        ToastUtil.showToast(UpdatePassWordActivity.this, "密码修改成功，请重新登录！");
                        preferencesUtils.saveString("former_pwd", "");
                        preferencesUtils.saveString("token", "");
                        IntentUtil.gotoActivityAndFinish(UpdatePassWordActivity.this, LoginOrRegistActivity.class);
                        break;
                    default:
                        ToastUtil.showLongToast(UpdatePassWordActivity.this, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                if (msg != null && !msg.equals("")) {
                    ToastUtil.showLongToast(UpdatePassWordActivity.this, msg);
                } else {
                    ToastUtil.showLongToast(UpdatePassWordActivity.this, "请求失败，请稍候重试!");
                }
            }
        });
    }

    /**
     * 检查数据是否正确
     *
     * @return
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    private boolean checkData() {
        String trim = et_mention_1.getText().toString().trim();

     /*   if (TextUtils.isEmpty(trim)) {
            ToastUtil.showToast(UpdatePassWordActivity.this, "原始密码未输入");
            return false;
        }*/

        String str = et_mention_2.getText().toString().trim();

        if (TextUtils.isEmpty(str)) {
            ToastUtil.showToast(UpdatePassWordActivity.this, "新密码未输入");
            return false;
        }

        String newPassWordAgain = et_mention_3.getText().toString().trim();

        if (TextUtils.isEmpty(newPassWordAgain)) {
            ToastUtil.showToast(UpdatePassWordActivity.this, "请再次输入新密码");
            return false;
        }else{
            if(!newPassWordAgain.equals(str)){
                ToastUtil.showToast(UpdatePassWordActivity.this, "两次输入的新密码不一致");
                return false;
            }
        }

        String pwd = preferencesUtils.loadString("former_pwd");
        user = preferencesUtils.loadObjectData(User.class);

        if (!TextUtils.isEmpty(pwd) && !pwd.equals(trim)) {
            ToastUtil.showToast(UpdatePassWordActivity.this, "原始密码错误");
            return false;
        }
        if (pwd.equals(str)) {
            ToastUtil.showLongToast(UpdatePassWordActivity.this, "你输入的新密码和原密码相同！");
            return false;
        }
        if (user.getPayPassword() != null && user.getPayPassword().equals(str)) {
            ToastUtil.showLongToast(UpdatePassWordActivity.this, "登录密码不能与支付密码相同");
            return false;
        }
        if (str.length() < 6) {
            ToastUtil.showLongToast(UpdatePassWordActivity.this, "请输入6-16位长度新密码!");
            return false;
        }
        return true;
    }
}

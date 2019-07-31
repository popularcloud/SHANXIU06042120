package com.lwc.shanxiu.module.user;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.utils.CommonUtils;
import com.lwc.shanxiu.utils.DisplayUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;

import java.util.HashMap;

/**
 * 修改个人信息
 */
public class UpdateUserInfoActivity extends BaseActivity {
    /**
     * 修改类型：0修改密码1修改电话2地址3签名4姓名
     */
    static int type;
    private TextView txt_mention_1;
    private EditText et_mention_1;
    private TextView txt_mention_2;
    private EditText et_mention_2;
    private SharedPreferencesUtils preferencesUtils;
    private User user = null;


    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_update_user_info;
    }


    @Override
    protected void findViews() {
        setRightText("提交", new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(et_mention_1.getText().toString().trim())) {
                    switch (type) {
                        //改密码
                        case 0:
                            if (checkData()) {
                                upUserInfor(et_mention_2.getText().toString().trim(), null);
                            }
                            break;
                        //签名
                        case 3:
                            upUserInfor(null, et_mention_1.getText().toString().trim());
                            break;
                    }
                } else {
                    ToastUtil.showLongToast(UpdateUserInfoActivity.this, "请填写需要更改的内容！");
                }
            }
        });
        showBack();
        txt_mention_1 = (TextView) findViewById(R.id.txt_mention_1);
        et_mention_1 = (EditText) findViewById(R.id.et_mention_1);
        txt_mention_2 = (TextView) findViewById(R.id.txt_mention_2);
        et_mention_2 = (EditText) findViewById(R.id.et_mention_2);

        et_mention_1.setVisibility(View.VISIBLE);
    }

    @Override
    protected void init() {
        preferencesUtils = SharedPreferencesUtils.getInstance(UpdateUserInfoActivity.this);
        user = preferencesUtils.loadObjectData(User.class);

        switch (type) {
            case 0:
                setTitle("修改密码");
                et_mention_1.setHint("");
                et_mention_2.setHint("");
                et_mention_1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                et_mention_2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                et_mention_2.setVisibility(View.VISIBLE);
                txt_mention_2.setVisibility(View.VISIBLE);
                txt_mention_1.setVisibility(View.VISIBLE);
                break;
            case 3:
                setTitle("修改签名");
                et_mention_1.setInputType(EditorInfo.TYPE_TEXT_FLAG_IME_MULTI_LINE);
                et_mention_1.setText(user.getMaintenanceSignature());
                break;
            default:
                break;
        }
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
     * @param remark
     */
    private void upUserInfor(final String pwd, String remark) {
        if (Utils.isFastClick(1000)) {
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        if (pwd != null)
            map.put("password", Utils.md5Encode(pwd));
        if (remark != null)
            map.put("signature", remark);
        HttpRequestUtils.httpRequest(this, "updateUserInfo", RequestValue.UP_USER_INFOR, map, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        switch (type) {
                            case 0:
                                ToastUtil.showToast(UpdateUserInfoActivity.this, "密码修改成功，请重新登录！");
                                preferencesUtils.saveString("former_pwd","");
                                preferencesUtils.saveString("token", "");
                                IntentUtil.gotoActivityAndFinish(UpdateUserInfoActivity.this, LoginOrRegistActivity.class);
                                break;
                            case 3:
                                user.setMaintenanceSignature(et_mention_1.getText().toString().trim());
                                preferencesUtils.saveObjectData(user);
                                break;
                            default:
                                break;
                        }
                        DisplayUtil.showInput(false, UpdateUserInfoActivity.this);
                        ToastUtil.showToast(UpdateUserInfoActivity.this, common.getInfo());
                        finish();
                        break;
                    default:
                        ToastUtil.showLongToast(UpdateUserInfoActivity.this, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
                ToastUtil.showLongToast(UpdateUserInfoActivity.this, msg);
            }
        });
    }

    /**
     * 检查数据
     *
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    private boolean checkData() {
        LLog.i("输入框的密码   " +et_mention_1.getText().toString() + "   密码 " + user.getPwd());
        String trim = et_mention_1.getText().toString().trim();
        switch (type) {
            case 0:// 密码
                if (TextUtils.isEmpty(trim)) {
                    ToastUtil.showToast(UpdateUserInfoActivity.this, "原始密码未输入");
                    return false;
                }
                String str = et_mention_2.getText().toString().trim();
                if (TextUtils.isEmpty(str)) {
                    ToastUtil.showToast(UpdateUserInfoActivity.this, "新密码未输入");
                    return false;
                }
                String pwd = preferencesUtils.loadString("former_pwd");
                LLog.i("mima "  + trim + "   "  +user.getPwd());
                if (!trim.equals(pwd)) {
                    ToastUtil.showToast(UpdateUserInfoActivity.this, "原始密码错误");
                    return false;
                }
                if (str.equals(trim)) {
                    ToastUtil.showLongToast(UpdateUserInfoActivity.this, "输入的新密码和原密码相同!");
                    return false;
                }
                if (user.getPayPassword() != null && user.getPayPassword().equals(str)) {
                    ToastUtil.showLongToast(UpdateUserInfoActivity.this, "登录密码不能与支付密码相同");
                    return false;
                }
                if (str.length() < 6) {
                    ToastUtil.showLongToast(UpdateUserInfoActivity.this, "请输入6-16位长度的新密码!");
                    return false;
                }
                return true;
            case 1:// 电话
                if (TextUtils.isEmpty(trim)) {
                    ToastUtil.showToast(UpdateUserInfoActivity.this, "请输入手机号！");
                    return false;
                }
                if (!CommonUtils.isPhoneNum(trim)) {
                    ToastUtil.showToast(UpdateUserInfoActivity.this, "请输入正确的手机号！");
                    return false;
                }
                return true;
            case 2:// 地址
                if (TextUtils.isEmpty(trim)) {
                    ToastUtil.showToast(UpdateUserInfoActivity.this, "请输入地址！");
                    return false;
                }
                return true;
            case 3:// 签名
                if (TextUtils.isEmpty(trim)) {
                    ToastUtil.showToast(UpdateUserInfoActivity.this, "请输入签名信息！");
                    return false;
                }
                return true;
            case 4:// 姓名
                if (TextUtils.isEmpty(trim)) {
                    ToastUtil.showToast(UpdateUserInfoActivity.this, "请输入您的真实姓名！");
                    return false;
                }

            default:
        }
        return true;
    }
}
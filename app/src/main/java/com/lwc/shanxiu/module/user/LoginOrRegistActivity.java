package com.lwc.shanxiu.module.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.activity.MainActivity;
import com.lwc.shanxiu.bean.ActivityBean;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.configs.BroadcastFilters;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.module.message.bean.HasMsg;
import com.lwc.shanxiu.utils.Constants;
import com.lwc.shanxiu.utils.FileUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.KeyboardUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.SystemUtil;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.view.ProgressUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

/**
 * 登陆或注册页面
 *
 * @Description TODO
 * @date 2015年11月20日
 * @Copyright: lwc
 */
public class LoginOrRegistActivity extends BaseActivity implements OnClickListener {
    @BindView(R.id.edtPhone)
    EditText edtPhone;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.txtFindPassWord)
    TextView txtFindPassWord;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.tv_bb)
    TextView tv_bb;

    private SharedPreferencesUtils preferencesUtils;
    private User user;
    private ProgressUtils progressUtils;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        preferencesUtils = SharedPreferencesUtils.getInstance(this);
        progressUtils = new ProgressUtils();
        preferencesUtils.saveString("token", "");
    }

    @Override
    protected void init() {
        tv_bb.setText("MX"+ SystemUtil.getCurrentVersionName());
        String name = preferencesUtils.loadString("former_name");
        if(name != null && !name.equals(""))
            edtPhone.setText(name);
        String pwd = preferencesUtils.loadString("former_pwd");
        if (pwd != null && !pwd.equals(""))
            edtPassword.setText(pwd);
    }

    @Override
    protected void initGetData() {
    }

    @Override
    protected void widgetListener() {
        txtFindPassWord.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                login();
                break;
            case R.id.txtFindPassWord:
                IntentUtil.gotoActivity(LoginOrRegistActivity.this, FindPasswordActivity.class);
                break;
            default:
                break;
        }
    }

    private void login() {
        if (Utils.isFastClick(2000)) {
            return;
        }
        final String phone = edtPhone.getText().toString().trim();
        final String pwd = edtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showToast(LoginOrRegistActivity.this, "用户名不能为空！");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtil.showToast(LoginOrRegistActivity.this, "密码不能为空！");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", Utils.md5Encode(pwd));
        map.put("clientType", Constants.clientType);
        String DEVICE_ID = Utils.getDeviceId(this);
        map.put("code", DEVICE_ID);
        map.put("systemCode", SystemUtil.getSystemVersion());
        map.put("phoneType", SystemUtil.getDeviceBrand()+"_"+SystemUtil.getSystemModel());
        progressUtils.showCustomProgressDialog(this);
        HttpRequestUtils.httpRequest(this, "login", RequestValue.LOGIN_PHONE, map, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        user = JsonUtil.parserGsonToObject(JsonUtil.getGsonValueByKey(response, "data"), User.class);
                        user.setPwd(pwd);
                        if (TextUtils.isEmpty(user.getUserId())) {
                            if (!TextUtils.isEmpty(user.getMaintenanceId())){
                                user.setUserId(user.getMaintenanceId());
                            }
                        } else if (TextUtils.isEmpty(user.getMaintenanceId())) {
                            user.setMaintenanceId(user.getUserId());
                        }
                        // 构建该用户的专用文件目录
                        FileUtil.createAllFile();
                        ArrayList<User> userList = new ArrayList<>();
                        userList.add(user);
                        DataSupport.deleteAll(User.class);
                        DataSupport.saveAll(userList);
                        setJPushAlias(user.getUserId());
                        //如果是维修端，改变isRepairer 标志状态
                        preferencesUtils.saveString("former_name", phone);
                        preferencesUtils.saveString("former_pwd",pwd);
                        preferencesUtils.saveString("token", user.getToken());
                        SharedPreferencesUtils.setParam(LoginOrRegistActivity.this,"deviceTypeMold", user.getDeviceTypeMold());
                        preferencesUtils.saveObjectData(user);
                        getActivity();
                        KeyboardUtil.showInput(false, LoginOrRegistActivity.this);
                        IntentUtil.gotoActivityAndFinish(LoginOrRegistActivity.this, MainActivity.class);
                        sendBroadcast(new Intent(BroadcastFilters.UPDATE_USER_LOGIN_SUCCESSED));
                        overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
                        progressUtils.dismissCustomProgressDialog();
                        break;
                    default:
                        progressUtils.dismissCustomProgressDialog();
                        ToastUtil.showLongToast(LoginOrRegistActivity.this, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                progressUtils.dismissCustomProgressDialog();
                LLog.eNetError(e.toString());
                if (msg != null && !msg.equals("")) {
                    ToastUtil.showLongToast(LoginOrRegistActivity.this, msg);
                }
            }
        });
    }

    private void getActivity() {
        HttpRequestUtils.httpRequest(this, "getActivity", RequestValue.GET_CHECK_ACTIVITY, null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        DataSupport.deleteAll(ActivityBean.class);
                        List<ActivityBean> activityBeans = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<ActivityBean>>() {
                        });
                        if (activityBeans != null && activityBeans.size() > 0)
                            DataSupport.saveAll(activityBeans);
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void returnException(Exception e, String msg) {
            }
        });
    }

    //   @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //退出应用
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 设置极光推送的别名
     */
    private void setJPushAlias(String uid) {
        //以用户id作为别名
        JPushInterface.setAlias(this, Constants.sequence, uid);
    }

}
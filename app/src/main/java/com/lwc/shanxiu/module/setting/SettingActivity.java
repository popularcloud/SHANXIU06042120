package com.lwc.shanxiu.module.setting;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.activity.InformationDetailsActivity;
import com.lwc.shanxiu.activity.UserGuideActivity;
import com.lwc.shanxiu.configs.ServerConfig;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.module.user.RegistTwoActivity;
import com.lwc.shanxiu.module.user.UserAgreementActivity;
import com.lwc.shanxiu.utils.DialogUtil;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.SpUtil;
import com.lwc.shanxiu.utils.SystemUtil;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.widget.CustomDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.img_notifi)
    ImageView img_notifi;
    @BindView(R.id.img_voice)
    ImageView img_voice;
    @BindView(R.id.iv_red)
    ImageView iv_red;
    private SpUtil sp;
    private User user;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_setting;
    }

    @Override
    protected void onResume() {
        super.onResume();
        img_voice.setBackgroundResource(
                sp.getSPValue(SettingActivity.this.getString(R.string.spkey_file_is_ring) + user.getUserId(), true)
                        ? R.drawable.shezhi_anniu2 : R.drawable.shezhi_anniu1);
        setNotifiImgBack();
    }


    private void setNotifiImgBack(){
        boolean isOpened = false;
        try {
            isOpened = NotificationManagerCompat.from(this).areNotificationsEnabled();

            //Log.d("联网成功","进入setNotifiImgBack,状态为"+isOpened);
        } catch (Exception e) {
            e.printStackTrace();
            isOpened = false;
        }
        img_notifi.setBackgroundResource(isOpened ? R.drawable.shezhi_anniu2 : R.drawable.shezhi_anniu1);
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        setTitle("设置");
        showBack();
    }

    @OnClick({R.id.img_notifi, R.id.txtUserGuide, R.id.txtIssue, R.id.img_voice, R.id.txt_vision, R.id.btnOutLogin, R.id.txtFeedback,R.id.txtUserAgreement})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_notifi:
                Intent intent = new Intent();
                if (Build.VERSION.SDK_INT >= 26) {
                    // android 8.0引导
                    intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                    intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());
                } else if (Build.VERSION.SDK_INT >= 21) {
                    // android 5.0-7.0
                    intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                    intent.putExtra("app_package", getPackageName());
                    intent.putExtra("app_uid", getApplicationInfo().uid);
                } else {
                    // 其他
                    intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", getPackageName(), null));
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.img_voice:
                SpUtil.putSPValue(SettingActivity.this, SettingActivity.this.getString(R.string.spkey_file_userinfo), Context.MODE_PRIVATE,
                        SettingActivity.this.getString(R.string.spkey_file_is_ring) + user.getUserId(),
                        !sp.getSPValue(SettingActivity.this.getString(R.string.spkey_file_is_ring) + user.getUserId(), true));
                ToastUtil
                        .showToast(SettingActivity.this,
                                "声音震动提示" + (sp.getSPValue(
                                        SettingActivity.this.getString(R.string.spkey_file_is_ring) + user.getUserId(), true) ? "已开启"
                                        : "已关闭"));
                img_voice.setBackgroundResource(
                        sp.getSPValue(SettingActivity.this.getString(R.string.spkey_file_is_ring) + user.getUserId(), true)
                                ? R.drawable.shezhi_anniu2 : R.drawable.shezhi_anniu1);
                setNoti();
                break;
            case R.id.txt_vision:
                IntentUtil.gotoActivity(SettingActivity.this, VesionActivity.class);
                break;
            case R.id.txtUserAgreement:
                Bundle bundle = new Bundle();
                bundle.putString("url", ServerConfig.DOMAIN.replace("https", "http")+"/main/h5/agreement.html?isEngineer=1");
                bundle.putString("title", "用户注册协议");
                IntentUtil.gotoActivity(SettingActivity.this, InformationDetailsActivity.class,bundle);
                break;
            case R.id.btnOutLogin:
                DialogUtil.showMessageDg(SettingActivity.this, "温馨提示", "确定退出吗？", new CustomDialog.OnClickListener() {

                    @Override
                    public void onClick(CustomDialog dialog, int id, Object object) {
                        dialog.dismiss();
                        setResult(RESULT_OK);
                        onBackPressed();
                    }
                });
                break;
            case R.id.txtFeedback:
                IntentUtil.gotoActivity(SettingActivity.this, SuggestActivity.class);
                break;
            case R.id.txtUserGuide:
                IntentUtil.gotoActivity(SettingActivity.this, UserGuideActivity.class);
                break;
            case R.id.txtIssue:
                IntentUtil.gotoActivity(SettingActivity.this, IssueActivity.class);
                break;
            default:
                break;
        }
    }

    public void setNoti() {
        boolean spValue1 = sp.getSPValue(this.getString(R.string.spkey_file_is_ring) + user.getUserId(), true);
        if (spValue1) {
            Utils.setNotification1(this);
        } else if (!spValue1) {
            Utils.setNotification4(this);
        }
    }

    @Override
    protected void init() {
        sp = SpUtil.getSpUtil(this.getString(R.string.spkey_file_userinfo), Context.MODE_PRIVATE);
        user = SharedPreferencesUtils.getInstance(this).loadObjectData(User.class);
        SharedPreferencesUtils preferencesUtils = SharedPreferencesUtils.getInstance(this);
        String versionCode = preferencesUtils.loadString("versionCode");
        if (!TextUtils.isEmpty(versionCode) && Integer.parseInt(versionCode) > SystemUtil.getCurrentVersionCode()) {
            iv_red.setVisibility(View.VISIBLE);
        } else {
            iv_red.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initGetData() {
    }

    @Override
    protected void widgetListener() {
    }

}

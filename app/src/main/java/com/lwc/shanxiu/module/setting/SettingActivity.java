package com.lwc.shanxiu.module.setting;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.activity.UserGuideActivity;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.utils.DialogUtil;
import com.lwc.shanxiu.utils.IntentUtil;
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
        img_notifi.setBackgroundResource(
                sp.getSPValue(SettingActivity.this.getString(R.string.spkey_file_is_system_mention) + user.getUserId(), true)
                        ? R.drawable.shezhi_anniu2 : R.drawable.shezhi_anniu1);
        img_voice.setBackgroundResource(
                sp.getSPValue(SettingActivity.this.getString(R.string.spkey_file_is_ring) + user.getUserId(), true)
                        ? R.drawable.shezhi_anniu2 : R.drawable.shezhi_anniu1);
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        setTitle("设置");
        showBack();
    }

    @OnClick({R.id.img_notifi, R.id.txtUserGuide, R.id.txtIssue, R.id.img_voice, R.id.txt_vision, R.id.btnOutLogin, R.id.txtFeedback})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_notifi:
                boolean spValue = sp.getSPValue(SettingActivity.this.getString(R.string.spkey_file_is_system_mention) + user.getUserId(), true);
                SpUtil.putSPValue(SettingActivity.this, SettingActivity.this.getString(R.string.spkey_file_userinfo), Context.MODE_PRIVATE,
                        SettingActivity.this.getString(R.string.spkey_file_is_system_mention) + user.getUserId(),
                        !spValue);
                ToastUtil.showToast(SettingActivity.this,
                        "系统通知提示" + (spValue
                                ? "已关闭" : "已开启"));
                img_notifi.setBackgroundResource(
                        spValue
                                ? R.drawable.shezhi_anniu1 : R.drawable.shezhi_anniu2);
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

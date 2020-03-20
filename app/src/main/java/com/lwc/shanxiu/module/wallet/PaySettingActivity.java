package com.lwc.shanxiu.module.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.configs.ServerConfig;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.utils.DialogUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.widget.CustomDialog;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;


/**
 * 支付管理
 */
public class PaySettingActivity extends BaseActivity {

	@BindView(R.id.tv_nichen)
	TextView tv_nichen;
	@BindView(R.id.tv_close)
	TextView tv_close;
	@BindView(R.id.tv_set_pay_pwd)
	TextView tv_set_pay_pwd;

	private User user;
	private UMShareAPI uMShareAPI;
	private SharedPreferencesUtils preferencesUtils;

	@Override
	protected int getContentViewId(Bundle savedInstanceState) {
		return R.layout.activity_pay_setting;
	}

	@Override
	protected void onResume() {
		super.onResume();
		user = preferencesUtils.loadObjectData(User.class);
		updateView();
	}

	@Override
	protected void findViews() {
		ButterKnife.bind(this);
		setTitle("支付管理");
		showBack();
		uMShareAPI = UMShareAPI.get(this);
	}
	@OnClick({R.id.rl_wechat, R.id.rl_pay_pwd})
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_pay_pwd:
			IntentUtil.gotoActivity(PaySettingActivity.this, PayPwdActivity.class);
			break;
		case R.id.rl_wechat:
			if (TextUtils.isEmpty(user.getOpenid())) {
				DialogUtil.showMessageDg(PaySettingActivity.this, "温馨提示", "绑定微信成功后密修APP中的钱包余\n额可提现至所绑定的微信账号中！", new CustomDialog.OnClickListener() {

					@Override
					public void onClick(CustomDialog dialog, int id, Object object) {
						dialog.dismiss();
						/**
						 * 如果没有安装微信
						 */
						if (!uMShareAPI.isInstall(PaySettingActivity.this, SHARE_MEDIA.WEIXIN)) {
							ToastUtil.showToast(PaySettingActivity.this, "您手机上未安装微信，请先安装微信客户端！");
							return;
						}
						uMShareAPI.doOauthVerify(PaySettingActivity.this, SHARE_MEDIA.WEIXIN, new PaySettingActivity.MyUMAuthListener());
					}
				});
			} else {
				DialogUtil.showMessageDg(PaySettingActivity.this, "温馨提示", "解除绑定微信后密修APP中的\n钱包余额将不能微信提现！", new CustomDialog.OnClickListener() {

					@Override
					public void onClick(CustomDialog dialog, int id, Object object) {
						dialog.dismiss();
						uMShareAPI.deleteOauth(PaySettingActivity.this, SHARE_MEDIA.WEIXIN, new UMAuthListener() {

							@Override
							public void onError(SHARE_MEDIA platform, int arg1,
												Throwable arg2) {
							}
							@Override
							public void onStart(SHARE_MEDIA share_media) {
							}
							@Override
							public void onComplete(SHARE_MEDIA platform, int arg1,
												   Map<String, String> arg2) {
							}
							@Override
							public void onCancel(SHARE_MEDIA platform, int arg1) {
							}
						});
						updateUserData("", "");
					}
				});
			}
			break;
		default:
			break;
		}
	}

	class MyUMAuthListener implements UMAuthListener {

		@Override
		public void onCancel(SHARE_MEDIA arg0, int arg1) {
			Toast.makeText(PaySettingActivity.this, "授权取消", Toast.LENGTH_SHORT).show();
		}
		@Override
		public void onStart(SHARE_MEDIA share_media) {
		}
		@Override
		public void onComplete(SHARE_MEDIA arg0, int arg1,
							   Map<String, String> arg2) {
//			Toast.makeText(PaySettingActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
//				openid可以作为用户的唯一标识，将openid保存下来，就可以实现登录状态的检查了。
			String openId = arg2.get("openid");
			String access_token = arg2.get("access_token");
			updateUserData(openId, access_token);
		}

		@Override
		public void onError(SHARE_MEDIA arg0, int arg1, Throwable arg2) {
			Toast.makeText(PaySettingActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
		}
	}

	private void updateUserData(final String openId, final String tokey) {
		if (Utils.isFastClick(1000)) {
			return;
		}
		HashMap<String, String> params = new HashMap<>();
		params.put("openId", openId);
		HttpRequestUtils.httpRequest(this, "updateUserData 微信绑定解绑", RequestValue.UP_USER_INFOR, params, "POST", new HttpRequestUtils.ResponseListener() {
			@Override
			public void getResponseData(String response) {
				Common common = JsonUtil.parserGsonToObject(response, Common.class);
				if (common.getStatus().equals(ServerConfig.RESPONSE_STATUS_SUCCESS)) {
					if (openId != null && !openId.equals("")) {
						user.setOpenid(openId);
						preferencesUtils.saveObjectData(user);
						ToastUtil.showLongToast(PaySettingActivity.this, "绑定微信成功");
						getWxUserInfo(openId, tokey);//获取用户的信息
					} else {
						user.setOpenid(openId);
						preferencesUtils.saveObjectData(user);
						ToastUtil.showLongToast(PaySettingActivity.this, "解绑微信成功");
					}
					updateView();
				} else {
					ToastUtil.showLongToast(PaySettingActivity.this, common.getInfo());
				}
			}
			@Override
			public void returnException(Exception e, String msg) {
				LLog.eNetError("updateUserInfo1  " + e.toString());
				ToastUtil.showLongToast(PaySettingActivity.this, msg);
			}
		});
	}

	public void getWxUserInfo(String openId, String token) {
		String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+token+"&openid="+openId+"&lang=zh_CN";
		OkHttpUtils.get().url(url).build().execute(new StringCallback() {
			@Override
			public void onError(Call call, Exception e, int id) {
				LLog.eNetError(e.toString());
			}
			@Override
			public void onResponse(String response, int id) {
				System.out.print("联网---"+response);
				try {
					final JSONObject o = new JSONObject(response);
					final String nickname = o.optString("nickname");
					if (nickname != null && !nickname.equals("")) {
						preferencesUtils.saveString("wecahtName" + user.getUserId(), nickname);
						updateView();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	// 如果有使用任一平台的SSO授权, 则必须在对应的activity中实现onActivityResult方法, 并添加如下代码
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uMShareAPI.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void init() {
		preferencesUtils = SharedPreferencesUtils.getInstance(this);
	}

	public void updateView() {
		if (TextUtils.isEmpty(user.getOpenid())) {
			tv_nichen.setVisibility(View.GONE);
			tv_close.setText("绑定微信");
		} else {
			String name = preferencesUtils.loadString("wecahtName"+user.getUserId());
			if (!TextUtils.isEmpty(name)) {
				tv_nichen.setVisibility(View.VISIBLE);
				tv_nichen.setText("("+name+")");
			}
			tv_close.setText("解除绑定");
		}

		if (TextUtils.isEmpty(user.getPayPassword())) {
			tv_set_pay_pwd.setText("设置密码");
		} else {
			tv_set_pay_pwd.setText("重设密码");
		}
	}

	@Override
	protected void initGetData() {
	}

	@Override
	protected void widgetListener() {
	}

}

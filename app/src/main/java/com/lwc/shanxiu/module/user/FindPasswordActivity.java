package com.lwc.shanxiu.module.user;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.utils.CommonUtils;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 找回密码
 */
public class FindPasswordActivity extends BaseActivity {


	@BindView(R.id.edtPhone)
	EditText edtPhone;
	@BindView(R.id.edtCode)
	EditText edtCode;
	@BindView(R.id.btnCode)
	Button btnCode;
	@BindView(R.id.edtPassword)
	EditText edtPassword;
	private int count = 60;

	@Override
	protected int getContentViewId(Bundle savedInstanceState) {
		return R.layout.activity_find_pwd;
	}

	@Override
	protected void findViews() {
		ButterKnife.bind(this);
	}

	@Override
	public void init() {
	}

	@Override
	protected void initGetData() {
	}

	@Override
	protected void widgetListener() {
	}

	Handler handle = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (count == 0) {
				count = 60;
				btnCode.setEnabled(true);
				btnCode.setText("获取验证码");
				return;
			}
			btnCode.setText(count-- + "s");
			btnCode.setEnabled(false);
			handle.sendEmptyMessageDelayed(0, 1000);
		}
	};

	@OnClick({R.id.btnCode, R.id.btnRegister, R.id.tv_login})
	public void onViewClicked(View view) {
		String phone = null;
		switch (view.getId()) {
			case R.id.tv_login:
				finish();
				break;
			case R.id.btnCode:
				phone = edtPhone.getText().toString().trim();
				if (TextUtils.isEmpty(phone)) {
					ToastUtil.showToast(this, "请输入手机号码");
					return;
				}
				if (!CommonUtils.isPhoneNum(phone)) {
					ToastUtil.showToast(this, "请输入正确的手机号码");
					return;
				}
				getCode(phone);
				handle.sendEmptyMessageDelayed(0, 1000);
				break;
			case R.id.btnRegister:

				phone = edtPhone.getText().toString();
				String pwd = edtPassword.getText().toString();
				String code = edtCode.getText().toString();
				if (TextUtils.isEmpty(phone)) {
					ToastUtil.showLongToast(this, "请输入手机号码");
					return;
				}
				if (!CommonUtils.isPhoneNum(phone)) {
					ToastUtil.showLongToast(this, "请输入正确的手机号码");
					return;
				}
				if (TextUtils.isEmpty(code)) {
					ToastUtil.showLongToast(this, "请输入验证码");
					return;
				}
				if (code == null || code.length() < 4) {
					ToastUtil.showLongToast(this, "请输入正确的验证码");
					return;
				}
				if (TextUtils.isEmpty(pwd)) {
					ToastUtil.showLongToast(this, "请输入新密码");
					return;
				}
				if (pwd.length() < 6 || pwd.length() > 17) {
					ToastUtil.showLongToast(this, "请输入6-16位长度的新密码");
					return;
				}
				findPwd();
				break;
		}
	}

	public void getCode(String phone) {
		HttpRequestUtils.httpRequest(this, "getCode", RequestValue.GET_CODE+phone, null, "GET", new HttpRequestUtils.ResponseListener() {
			@Override
			public void getResponseData(String response) {
				Common common = JsonUtil.parserGsonToObject(response, Common.class);
				switch (common.getStatus()) {
					case "1":
						ToastUtil.showToast(FindPasswordActivity.this, "获取成功，请继续填写信息");
						break;
					default:
						ToastUtil.showLongToast(FindPasswordActivity.this, common.getInfo());
						break;
				}
			}
			@Override
			public void returnException(Exception e, String msg) {
				ToastUtil.showLongToast(FindPasswordActivity.this, msg);
			}
		});
	}

	private void findPwd() {
		if (Utils.isFastClick(1000)) {
			return;
		}
		Map<String, String> map = new HashMap<>();
		map.put("phone", edtPhone.getText().toString().trim());
		map.put("password", Utils.md5Encode(edtPassword.getText().toString().trim()));
		map.put("code", edtCode.getText().toString().trim());
		map.put("clientType", "maintenance");
		HttpRequestUtils.httpRequest(this, "findPwd", RequestValue.BACK_PWD, map, "POST", new HttpRequestUtils.ResponseListener() {
			@Override
			public void getResponseData(String response) {
				Common common = JsonUtil.parserGsonToObject(response, Common.class);
				switch (common.getStatus()) {
					case "1":
						SharedPreferencesUtils.getInstance(FindPasswordActivity.this).saveString("former_name",edtPhone.getText().toString().trim());
						SharedPreferencesUtils.getInstance(FindPasswordActivity.this).saveString("former_pwd","");
						ToastUtil.showLongToast(FindPasswordActivity.this, "找回成功，请使用新密码登录");
						overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
						finish();
						break;
					default:
						ToastUtil.showLongToast(FindPasswordActivity.this, common.getInfo());
						break;
				}
			}

			@Override
			public void returnException(Exception e, String msg) {
				if (msg != null && !msg.equals("")) {
					ToastUtil.showLongToast(FindPasswordActivity.this, msg);
				}
			}
		});
	}
}

package com.lwc.shanxiu.module.wallet;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.configs.ServerConfig;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayPwdActivity extends BaseActivity {

	@BindView(R.id.tv_tip)
	TextView tv_tip;
	@BindView(R.id.tv_msg)
	TextView tv_msg;
	@BindView(R.id.btnSubmit)
	Button btnSubmit;
	@BindView(R.id.et_1)
	EditText et_1;
	@BindView(R.id.et_2)
	EditText et_2;
	@BindView(R.id.et_3)
	EditText et_3;
	@BindView(R.id.et_4)
	EditText et_4;
	@BindView(R.id.et_5)
	EditText et_5;
	@BindView(R.id.et_6)
	EditText et_6;

	@BindView(R.id.ll_setting_pay_pwd)
	LinearLayout ll_setting_pay_pwd;
	@BindView(R.id.ll_upd_pay_pwd)
	LinearLayout ll_upd_pay_pwd;
	@BindView(R.id.et_code)
	EditText et_code;
	@BindView(R.id.et_pwd)
	EditText et_pwd;
	@BindView(R.id.tv_phone)
	TextView tv_phone;
	@BindView(R.id.btnCode)
	Button btnCode;
	@BindView(R.id.btnUpdPwd)
	Button btnUpdPwd;

	private User user;
	private SharedPreferencesUtils preferencesUtils;
	private String strPwd = "";
	private String phone="";

	@Override
	protected int getContentViewId(Bundle savedInstanceState) {
		return R.layout.activity_pay_pwd;
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onBack(View view) {
		onBackVili();
	}

	private void onBackVili(){
		if (TextUtils.isEmpty(strPwd)){
			finish();
		} else {
			et_1.setText(strPwd.substring(0,1));
			et_2.setText(strPwd.substring(1,2));
			et_3.setText(strPwd.substring(2,3));
			et_4.setText(strPwd.substring(3,4));
			et_5.setText(strPwd.substring(4,5));
			et_6.setText(strPwd.substring(5,6));
			tv_tip.setText("设置支付密码");
			btnSubmit.setText("下一步");
			tv_msg.setText("支付密码不能与登录密码一样");
			btnSubmit.setEnabled(true);
			strPwd = "";
			setFocusable(et_6);
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			onBackVili();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void findViews() {
		ButterKnife.bind(this);
		setTitle("支付密码");
	}
	@OnClick({R.id.btnSubmit, R.id.btnCode, R.id.btnUpdPwd})
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSubmit:
			String str = btnSubmit.getText().toString();
			if (str.equals("下一步")) {
				if (TextUtils.isEmpty(et_1.getText().toString().trim())) {
                    ToastUtil.showLongToast(PayPwdActivity.this, "请输入六位纯数字支付密码");
                    return;
                }
                if (TextUtils.isEmpty(et_2.getText().toString().trim())) {
                    ToastUtil.showLongToast(PayPwdActivity.this, "请输入六位纯数字支付密码");
                    return;
                }
				if (TextUtils.isEmpty(et_3.getText().toString().trim())) {
					ToastUtil.showLongToast(PayPwdActivity.this, "请输入六位纯数字支付密码");
					return;
				}
				if (TextUtils.isEmpty(et_4.getText().toString().trim())) {
					ToastUtil.showLongToast(PayPwdActivity.this, "请输入六位纯数字支付密码");
					return;
				}
				if (TextUtils.isEmpty(et_5.getText().toString().trim())) {
					ToastUtil.showLongToast(PayPwdActivity.this, "请输入六位纯数字支付密码");
					return;
				}
				if (TextUtils.isEmpty(et_6.getText().toString().trim())) {
					ToastUtil.showLongToast(PayPwdActivity.this, "请输入六位纯数字支付密码");
					return;
				}
				strPwd = et_1.getText().toString().trim()+et_2.getText().toString().trim()+et_3.getText().toString().trim()+et_4.getText().toString().trim()+et_5.getText().toString().trim()+et_6.getText().toString().trim();
				String pwd = preferencesUtils.loadString("former_pwd");
				if (strPwd.equals(pwd)) {
					et_1.setText("");
					et_2.setText("");
					et_3.setText("");
					et_4.setText("");
					et_5.setText("");
					et_6.setText("");
					btnSubmit.setEnabled(false);
					setFocusable(et_1);
					ToastUtil.showLongToast(PayPwdActivity.this, "支付密码不能与登录密码一样");
					return;
				}
				et_1.setText("");
				et_2.setText("");
				et_3.setText("");
				et_4.setText("");
				et_5.setText("");
				et_6.setText("");
				tv_tip.setText("再次输入，以确认密码");
				btnSubmit.setText("完成");
				tv_msg.setText("两次输入的密码必须一致");
				btnSubmit.setEnabled(false);
				setFocusable(et_1);
			} else if (str.equals("完成")) {
				if (TextUtils.isEmpty(et_1.getText().toString().trim())) {
					ToastUtil.showLongToast(PayPwdActivity.this, "请输入六位纯数字支付密码");
					return;
				}
				if (TextUtils.isEmpty(et_2.getText().toString().trim())) {
					ToastUtil.showLongToast(PayPwdActivity.this, "请输入六位纯数字支付密码");
					return;
				}
				if (TextUtils.isEmpty(et_3.getText().toString().trim())) {
					ToastUtil.showLongToast(PayPwdActivity.this, "请输入六位纯数字支付密码");
					return;
				}
				if (TextUtils.isEmpty(et_4.getText().toString().trim())) {
					ToastUtil.showLongToast(PayPwdActivity.this, "请输入六位纯数字支付密码");
					return;
				}
				if (TextUtils.isEmpty(et_5.getText().toString().trim())) {
					ToastUtil.showLongToast(PayPwdActivity.this, "请输入六位纯数字支付密码");
					return;
				}
				if (TextUtils.isEmpty(et_6.getText().toString().trim())) {
					ToastUtil.showLongToast(PayPwdActivity.this, "请输入六位纯数字支付密码");
					return;
				}
				String str2 = et_1.getText().toString().trim()+et_2.getText().toString().trim()+et_3.getText().toString().trim()+et_4.getText().toString().trim()+et_5.getText().toString().trim()+et_6.getText().toString().trim();
				if (!strPwd.equals(str2)) {
					ToastUtil.showLongToast(PayPwdActivity.this, "两次输入的密码不一样");
					return;
				}
				updateUserData(strPwd, "");
			}
			break;
		case R.id.btnCode:
			getCode(phone);
			break;
		case R.id.btnUpdPwd:
			if (TextUtils.isEmpty(et_code.getText().toString().trim())){
				ToastUtil.showLongToast(PayPwdActivity.this, "请输入验证码");
				return;
			}
			if (et_code.getText().toString().trim().length() < 4){
				ToastUtil.showLongToast(PayPwdActivity.this, "请输入正确的验证码");
				return;
			}
			if (TextUtils.isEmpty(et_pwd.getText().toString().trim())){
				ToastUtil.showLongToast(PayPwdActivity.this, "请输入新密码");
				return;
			}
			if (et_pwd.getText().toString().trim().length() != 6){
				ToastUtil.showLongToast(PayPwdActivity.this, "请输入六位纯数字新密码");
				return;
			}
			String pwd = preferencesUtils.loadString("former_pwd");
			if (et_pwd.getText().toString().trim().equals(pwd)) {
				ToastUtil.showLongToast(PayPwdActivity.this, "支付密码不能与登录密码一样");
				return;
			}
			updateUserData(et_pwd.getText().toString().trim(), et_code.getText().toString().trim());
			break;
		default:
			break;
		}
	}
	private int count = 60;
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

	public void getCode(String phone) {
		HttpRequestUtils.httpRequest(this, "getCode", RequestValue.GET_CODE+phone, null, "GET", new HttpRequestUtils.ResponseListener() {
			@Override
			public void getResponseData(String response) {
				Common common = JsonUtil.parserGsonToObject(response, Common.class);
				switch (common.getStatus()) {
					case "1":
						handle.sendEmptyMessageDelayed(0, 1000);
						ToastUtil.showToast(PayPwdActivity.this, "验证码发送成功，请继续填写信息");
						break;
					default:
						ToastUtil.showLongToast(PayPwdActivity.this, common.getInfo());
						break;
				}
			}
			@Override
			public void returnException(Exception e, String msg) {
				ToastUtil.showLongToast(PayPwdActivity.this, msg);
			}
		});
	}

	private void updateUserData(final String pwd, String code) {
		if (Utils.isFastClick(1000)) {
			return;
		}
		HashMap<String, String> params = new HashMap<>();
		params.put("payPassword", Utils.md5Encode(pwd));
		if (!TextUtils.isEmpty(code)) {
			params.put("code", code);
		}
		HttpRequestUtils.httpRequest(this, "updateUserData 支付密码设置", RequestValue.UP_USER_INFOR, params, "POST", new HttpRequestUtils.ResponseListener() {
			@Override
			public void getResponseData(String response) {
				Common common = JsonUtil.parserGsonToObject(response, Common.class);
				if (common.getStatus().equals(ServerConfig.RESPONSE_STATUS_SUCCESS)) {
					user.setPayPassword(Utils.md5Encode(pwd));
					preferencesUtils.saveObjectData(user);
					ToastUtil.showToast(PayPwdActivity.this, "保存成功");
					finish();
				} else {
					ToastUtil.showLongToast(PayPwdActivity.this, common.getInfo());
				}
			}
			@Override
			public void returnException(Exception e, String msg) {
				LLog.eNetError("updateUserInfo1  " + e.toString());
				ToastUtil.showLongToast(PayPwdActivity.this, msg);
			}
		});
	}

	@Override
	protected void init() {
		preferencesUtils = SharedPreferencesUtils.getInstance(this);
		user = preferencesUtils.loadObjectData(User.class);
		updateView();
	}

	public void updateView() {
		showBack();
		if (TextUtils.isEmpty(user.getPayPassword())) {
			setTitle("设置密码");
			ll_setting_pay_pwd.setVisibility(View.VISIBLE);
			ll_upd_pay_pwd.setVisibility(View.GONE);
		} else {
			setTitle("重设密码");
			ll_setting_pay_pwd.setVisibility(View.GONE);
			ll_upd_pay_pwd.setVisibility(View.VISIBLE);
			phone = user.getMaintenancePhone();
			if (TextUtils.isEmpty(phone)) {
				phone = user.getUserName();
			}
			tv_phone.setText(phone.substring(0,3)+"****"+phone.substring(7,11));
		}
	}

	@Override
	protected void initGetData() {
	}
	private boolean isDel = false;
	public void setFocus() {
		if (isDel) {
			return;
		}
		String et1 = et_1.getText().toString().trim();
		String et2 = et_2.getText().toString().trim();
		String et3 = et_3.getText().toString().trim();
		String et4 = et_4.getText().toString().trim();
		String et5 = et_5.getText().toString().trim();
		String et6 = et_6.getText().toString().trim();
		if (et1.length() == 1) {
			if (et2.length() == 1) {
				if (et3.length() == 1) {
					if (et4.length() == 1) {
						if (et5.length() == 1) {
							if (et6.length() == 1) {
								setFocusable(et_6);
							}
							setFocusable(et_6);
						} else {
							setFocusable(et_5);
						}
					} else {
						setFocusable(et_4);
					}
				} else {
					setFocusable(et_3);
				}
			} else {
				setFocusable(et_2);
			}
		} else {
			setFocusable(et_1);
		}
	}

	@Override
	protected void widgetListener() {
		customSelection(et_1);
		customSelection(et_2);
		customSelection(et_3);
		customSelection(et_4);
		customSelection(et_5);
		customSelection(et_6);
		/*et_1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus) {
					setFocus();
				}
			}
		});
		et_2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus) {
					setFocus();
				}
			}
		});
		et_3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus) {
					setFocus();
				}
			}
		});
		et_4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus) {
					setFocus();
				}
			}
		});
		et_5.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus) {
					setFocus();
				}
			}
		});
		et_6.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus) {
					setFocus();
				}
			}
		});*/
		et_1.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				if (et_1.getText().toString().trim().length()  == 1) {
					setFocusable(et_2);
				}
			}
		});
		et_2.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				if (et_2.getText().toString().trim().length()  == 1) {
					setFocusable(et_3);
				}
			}
		});
		et_3.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				if (et_3.getText().toString().trim().length()  == 1) {
					setFocusable(et_4);
				}
			}
		});
		et_4.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				if (et_4.getText().toString().trim().length()  == 1) {
					setFocusable(et_5);
				}
			}
		});
		et_5.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				if (et_5.getText().toString().trim().length()  == 1) {
					setFocusable(et_6);
				}
			}
		});
		et_6.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				if (et_6.getText().toString().trim().length()  == 1) {
					setFocusable(et_5);
					btnSubmit.setEnabled(true);
					setFocusable(et_6);
				}
			}
		});

		et_6.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
					int len = et_6.getText().toString().length();
					btnSubmit.setEnabled(false);
					if (len == 0) {
						isDel = true;
						et_5.setText("");
						et_6.clearFocus();
						setFocusable(et_5);
					} else {
						et_6.setText("");
					}
				}
				return true;
			}
		});
		et_5.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
					int len = et_5.getText().toString().length();
					if (len == 0) {
						isDel = true;
						et_4.setText("");
						et_5.clearFocus();
						setFocusable(et_4);
					} else {
						et_5.setText("");
					}
				}
				return true;
			}
		});
		et_4.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
					int len = et_4.getText().toString().length();
					if (len == 0) {
						isDel = true;
						et_3.setText("");
						et_4.clearFocus();
						setFocusable(et_3);
					} else {
						et_4.setText("");
					}
				}
				return true;
			}
		});
		et_3.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
					int len = et_3.getText().toString().length();
					if (len == 0) {
						isDel = true;
						et_2.setText("");
						et_3.clearFocus();
						setFocusable(et_2);
					} else {
						et_3.setText("");
					}
				}
				return true;
			}
		});
		et_2.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
					int len = et_2.getText().toString().length();
					if (len == 0) {
						isDel = true;
						et_1.setText("");
						et_2.clearFocus();
						setFocusable(et_1);
					} else {
						et_2.setText("");
					}
				}
				return true;
			}
		});
	}

	public void customSelection(EditText mInputEditTxt){
		mInputEditTxt.setCustomSelectionActionModeCallback(new ActionMode.Callback(){

			public boolean onCreateActionMode(ActionMode actionMode, Menu menu){
				return false;
			}
			public boolean onPrepareActionMode(ActionMode actionMode,Menu menu){
				return false;
			}
			public boolean onActionItemClicked(ActionMode actionMode,MenuItem menuItem){
				return false;
			}
			@Override
			public void onDestroyActionMode(ActionMode mode){
			}
		});
		mInputEditTxt.setLongClickable(false);
	}

	public void setFocusable(EditText mEditText) {
		mEditText.setFocusable(true);
		mEditText.setFocusableInTouchMode(true);
		mEditText.requestFocus();
		mEditText.requestFocusFromTouch();
		//String str = mEditText.getText().toString().trim();
	/*	if (str.length() > 0){
			mEditText.setSelection(str.length());
		}*/
/*		if(getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE) {
			InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMethodManager.showSoftInput(mEditText, 0);
		}*/
		isDel = false;
	}

}

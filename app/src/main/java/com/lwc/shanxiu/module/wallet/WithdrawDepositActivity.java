package com.lwc.shanxiu.module.wallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Dimension;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

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
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 提现
 * 
 * @author 何栋
 * @date 2017年11月29日
 */
public class WithdrawDepositActivity extends BaseActivity {

	@BindView(R.id.et_money)
	EditText et_money;
	@BindView(R.id.tv_money_hint)
	TextView tv_money_hint;
	@BindView(R.id.et_alipay)
	EditText et_alipay;
	@BindView(R.id.tv_money)
	TextView tv_money;
	@BindView(R.id.tBtnSecretWechat)
	ToggleButton tBtnSecretWechat;
	@BindView(R.id.tBtnSecretAlipay)
	ToggleButton tBtnSecretAlipay;
	@BindView(R.id.rl_alipay)
	RelativeLayout rl_alipay;
	private UMShareAPI uMShareAPI;
	private User user;
	private String money;
	private String alipayAccount;

	@Override
	protected int getContentViewId(Bundle savedInstanceState) {
		return R.layout.activity_withdraw_deposit;
	}

	@Override
	protected void findViews() {
		ButterKnife.bind(this);
		setTitle("提现");
		showBack();
		uMShareAPI = UMShareAPI.get(this);
	}

	@OnClick({R.id.tBtnSecretWechat, R.id.tBtnSecretAlipay, R.id.txtWithdraw, R.id.tv_all_money, R.id.rl_wechat_pay, R.id.rl_ali_pay,R.id.tv_money_hint})
	public void onClickView(View view) {
		switch (view.getId()) {
			case R.id.tv_all_money:
				if (!TextUtils.isEmpty(user.getBanlance()) && Double.parseDouble(user.getBanlance()) >= 1 ) {
					et_money.setText(user.getBanlance());
					tv_money_hint.setVisibility(View.GONE);
					et_money.setVisibility(View.VISIBLE);
					et_money.setFocusable(true);
					et_money.setFocusableInTouchMode(true);
					et_money.requestFocus();
				} else {
					ToastUtil.showLongToast(WithdrawDepositActivity.this, "钱包余额不足，提现金额必须>=1元！");
				}
				break;
			case R.id.tBtnSecretWechat:
			case R.id.rl_wechat_pay:
				tBtnSecretWechat.setChecked(true);
				tBtnSecretAlipay.setChecked(false);
				rl_alipay.setVisibility(View.GONE);
				break;
			case R.id.tBtnSecretAlipay:
			case R.id.rl_ali_pay:
				//TODO 支付宝暂时不开放
				tBtnSecretWechat.setChecked(false);
				tBtnSecretAlipay.setChecked(true);
				rl_alipay.setVisibility(View.VISIBLE);
				if (user != null && !TextUtils.isEmpty(user.getAliToken())) {
					et_alipay.setText(user.getAliToken());
				}
				break;
			case R.id.txtWithdraw:

				money = et_money.getText().toString().trim();
				if (TextUtils.isEmpty(money)) {
					ToastUtil.showLongToast(WithdrawDepositActivity.this, "请输入提现金额");
					return;
				}
				if (Float.parseFloat(money) < 1 || Float.parseFloat(money)>Float.parseFloat(user.getBanlance())){
					ToastUtil.showLongToast(WithdrawDepositActivity.this, "提现金额要大于1元且不能大于账户余额");
					return;
				}
				money = Utils.getMoney(money);
				if (tBtnSecretWechat.isChecked()) {
					if (TextUtils.isEmpty(user.getOpenid())) {
						DialogUtil.showMessageDg(WithdrawDepositActivity.this, "温馨提示", "您还未绑定微信账号\n无法提现到微信中！", new CustomDialog.OnClickListener() {

							@Override
							public void onClick(CustomDialog dialog, int id, Object object) {
								dialog.dismiss();
								IntentUtil.gotoActivity(WithdrawDepositActivity.this, PaySettingActivity.class);
							}
						});
						return;
					}

					/**
					 * 如果没有安装微信
					 */
					if (!uMShareAPI.isInstall(this, SHARE_MEDIA.WEIXIN)) {
						ToastUtil.showToast(WithdrawDepositActivity.this, "您手机上未安装微信，请先安装微信客户端！");
						return;
					}
				} else {
					alipayAccount = et_alipay.getText().toString().trim();
					if (TextUtils.isEmpty(alipayAccount)){
						ToastUtil.showToast(WithdrawDepositActivity.this, "请输入您的支付宝账号");
						return;
					}
				}
				if (TextUtils.isEmpty(user.getPayPassword())) {
					DialogUtil.showMessageDg(WithdrawDepositActivity.this, "温馨提示", "您还未设置支付密码\n请先去设置支付密码！", new CustomDialog.OnClickListener() {

						@Override
						public void onClick(CustomDialog dialog, int id, Object object) {
							dialog.dismiss();
							IntentUtil.gotoActivity(WithdrawDepositActivity.this, PaySettingActivity.class);
						}
					});
					return;
				}
				if (tBtnSecretAlipay.isChecked()) {
					DialogUtil.showMessageDg(WithdrawDepositActivity.this, "温馨提示", "将提现至支付宝账号:" + alipayAccount, new CustomDialog.OnClickListener() {
						@Override
						public void onClick(CustomDialog dialog, int id, Object object) {
							dialog.dismiss();
							Bundle bundle = new Bundle();
							bundle.putString("money", money);
							IntentUtil.gotoActivityForResult(WithdrawDepositActivity.this, InputPayPwdActivity.class, bundle, 12302);
						}
					});
				} else {
					Bundle bundle = new Bundle();
					bundle.putString("money", money);
					IntentUtil.gotoActivityForResult(WithdrawDepositActivity.this, InputPayPwdActivity.class, bundle, 12302);
				}
				break;
			case R.id.tv_money_hint:
				view.setVisibility(View.GONE);
				et_money.setVisibility(View.VISIBLE);
				et_money.setFocusable(true);
				et_money.setFocusableInTouchMode(true);
				et_money.requestFocus();

				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
				break;
			default:
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 12302 && resultCode == RESULT_OK) {
			String pwd = data.getStringExtra("PWD");
//			if (!user.getPayPassword().equals(pwd)){
//				ToastUtil.showLongToast(this, "支付密码错误");
//			} else {
				applyDeposit(pwd);
//			}
		}
	}

	private void applyDeposit(final String pwd) {
		if (Utils.isFastClick(2000)) {
			return;
		}
		HashMap<String, String> params = new HashMap<>();
		params.put("payPassword", pwd);
		params.put("transactionAmount", Utils.cheng(money, "100"));
		if (tBtnSecretWechat.isChecked()) {
			params.put("transactionMeans", "3");
		} else if (tBtnSecretAlipay.isChecked()) {
			params.put("transactionMeans", "2");
			params.put("aliToken", alipayAccount);
		}
		HttpRequestUtils.httpRequest(this, "applyDeposit 申请提现", RequestValue.POST_WITHDRAW_DEPOSIT, params, "POST", new HttpRequestUtils.ResponseListener() {
			@Override
			public void getResponseData(String response) {
				Common common = JsonUtil.parserGsonToObject(response, Common.class);
				if (common.getStatus().equals(ServerConfig.RESPONSE_STATUS_SUCCESS)) {
					ToastUtil.showToast(WithdrawDepositActivity.this, "提交成功，等待系统处理!");
					finish();
				} else if (common.getInfo().equals("支付密码错误")) {
					DialogUtil.showMessageDg(WithdrawDepositActivity.this, "温馨提示", "忘记密码", "重新输入", "您输入的支付密码错误!", new CustomDialog.OnClickListener() {
						@Override
						public void onClick(CustomDialog dialog, int id, Object object) {
							dialog.dismiss();
							IntentUtil.gotoActivity(WithdrawDepositActivity.this, PayPwdActivity.class);
						}
					}, new CustomDialog.OnClickListener() {
						@Override
						public void onClick(CustomDialog dialog, int id, Object object) {
							dialog.dismiss();
							Bundle bundle = new Bundle();
							bundle.putString("money", money);
							IntentUtil.gotoActivityForResult(WithdrawDepositActivity.this, InputPayPwdActivity.class, bundle, 12302);
						}
					});
				} else {
					ToastUtil.showLongToast(WithdrawDepositActivity.this, common.getInfo());
				}
			}
			@Override
			public void returnException(Exception e, String msg) {
				LLog.eNetError("updateUserInfo1  " + e.toString());
				ToastUtil.showLongToast(WithdrawDepositActivity.this, msg);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		user = SharedPreferencesUtils.getInstance(this).loadObjectData(User.class);
		tv_money.setText("可用余额"+user.getBanlance()+"元");
	}

	@Override
	protected void init() {
	}
	@Override
	protected void initGetData() {
	}
	@Override
	protected void widgetListener() {
		et_money.addTextChangedListener(new TextWatcher()
		{
			public void afterTextChanged(Editable edt)
			{
				String temp = edt.toString();
				if (temp.equals("0")) {
					et_money.setText("");
				}
				if (temp != null && temp.length()> 1 && temp.substring(0,1).equals("0")) {
					et_money.setText(temp.substring(1, temp.length()));
				}
				if(temp.lastIndexOf(".") != temp.indexOf(".") || temp.indexOf(".") == 0) {
					et_money.setText(temp.substring(0, temp.length()-1));
					et_money.setSelection(temp.length()-1);
					ToastUtil.showToast(WithdrawDepositActivity.this, "请输入正确的金额");
					return;
				}
				int posDot = temp.indexOf(".");
				if (posDot <= 0) return;
				if (temp.length() - posDot - 1 > 2)
				{
					edt.delete(posDot + 3, posDot + 4);
				}
			}
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
		});

	}
}

package com.lwc.shanxiu.module.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.map.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InputPayPwdActivity extends BaseActivity {

	@BindView(R.id.tv_money)
	TextView tv_money;
	@BindView(R.id.layout_psw)
	LinearLayout layout_psw;
	private StringBuffer stringBuffer = new StringBuffer();

	@Override
	protected int getContentViewId(Bundle savedInstanceState) {
		return R.layout.activity_input_password;
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	public void clickPwd(View v){
		if(stringBuffer.length()>=6){
			return;
		}
		stringBuffer.append(v.getTag().toString());
		ImageView iv = new ImageView(this);
		iv.setImageResource(R.drawable.black_spots);
		layout_psw.addView(iv);
		if(stringBuffer.length()==6){
			//获得密码,返给前一个页面,关闭当前页面
			try {
				String pwd = Utils.md5Encode(stringBuffer.toString());
				Intent data = new Intent();
				data.putExtra("PWD", pwd);
				setResult(RESULT_OK, data);
				finish();
			} catch (Exception e) {
			}
		}
	}

	@Override
	protected void findViews() {
		ButterKnife.bind(this);
		String money = getIntent().getStringExtra("money");
		tv_money.setText("¥"+money+"元");
	}
	@OnClick({R.id.cancle, R.id.delete})
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.cancle://取消支付
				finish();
				break;
			case R.id.delete://删除一个密码
				if(stringBuffer.length()>0){
					stringBuffer.deleteCharAt(stringBuffer.length()-1);
					layout_psw.removeViewAt(layout_psw.getChildCount()-1);
				}
				break;
			default:
			break;
		}
	}

	@Override
	protected void init() {
	}

	@Override
	protected void initGetData() {
	}

	@Override
	protected void widgetListener() {
	}
}

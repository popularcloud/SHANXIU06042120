package com.lwc.shanxiu.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.wallet.WithdrawPayActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "WXPayEntryActivity";
	private IWXAPI api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_result);
		api = WXAPIFactory.createWXAPI(this, WXContants.APP_ID);
		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

		switch (resp.errCode) {
			case 0:
				Toast.makeText(this, "支付成功！", Toast.LENGTH_LONG).show();
				break;
			case -2:
				WithdrawPayActivity.activity.payFailure();
				Toast.makeText(this,"支付取消！",Toast.LENGTH_LONG).show();
				break;
			case -1:
				Toast.makeText(this,"支付失败！",Toast.LENGTH_LONG).show();
				break;
			default:
				Toast.makeText(this,"支付出错！",Toast.LENGTH_LONG).show();
				break;
		}
		finish();
	}
}
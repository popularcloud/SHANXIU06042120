package com.lwc.shanxiu.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.lwc.shanxiu.wxapi.WXContants;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by yuqianli on 15/11/26.
 */
public class PayServant {
	private static PayServant ourInstance = new PayServant();

	public static PayServant getInstance() {
		return ourInstance;
	}

	private PayServant() {
	}

//	/**
//	 * check whether the device has authentication alipay account.
//	 * 查询终端设备是否存在支付宝认证账户
//	 */
//	public void aliPayCheckAccount(final Activity activity,
//			final AliPayCheckCallBack callBack) {
//		ThreadUtils.runOnNonUIthread(new Runnable() {A
//			@Override
//			public void run() {
//				// 构造PayTask 对象
//				PayTask payTask = new PayTask(activity);
//				// 调用查询接口，获取查询结果
//				boolean isExist = payTask.checkAccountIfExist();
//				callBack.OnCheckAccountExist(isExist);
//			}
//		});
//	}


	/**
	 * call alipay sdk pay. 调用SDK支付
	 *
	 * @param activity
	 * @param callback
	 */
	public void aliPay(JSONObject data, final Activity activity,
					   final AliPayCallBack callback) {
		try {
			final String orderStr = data.optString("orderStr");
			// ＊＊＊发起支付请求
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					// 构造PayTask 对象
					PayTask alipay = new PayTask(activity);
					Map<String, String> result = alipay.payV2(orderStr, true);
					// 调用支付接口，获取支付结果
					PayResult payResult = new PayResult(result);
					/**
					 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
					 */
//					String resultInfo = payResult.getResult();// 同步返回需要验证的信息
					final String resultStatus = payResult.getResultStatus();
					// 判断resultStatus 为9000则代表支付成功
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							if (TextUtils.equals(resultStatus, "9000")) {
								// 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
								Toast.makeText(activity, "支付成功", Toast.LENGTH_SHORT).show();
								callback.OnAliPayResult(true, false, "支付成功");
							} else {
								callback.OnAliPayResult(false, false, "支付失败");
								// 该笔订单真实的支付结果，需要依赖服务端的异步通知。
								Toast.makeText(activity, "支付失败", Toast.LENGTH_SHORT).show();
							}
						}
					});
				}
			};
			ExecutorServiceUtil.getInstance().execute(runnable);
		} catch (Exception e) {
			LLog.e("payservant", e.getMessage());
		}
	}

	public void weChatPay2(Activity activity, String appid, String partnerid,
			String prepayid, String noncestr, String timestamp,
			String packageValue, String sign) {
		IWXAPI api = WXAPIFactory.createWXAPI(activity, null);
		try {
			PayReq req = new PayReq();
			// req.appId = "wxf8b4f85f3a794e77"; // 测试用appId
			req.appId = appid;
			req.partnerId = partnerid;
			req.prepayId = prepayid;
			req.nonceStr = noncestr;
			req.timeStamp = timestamp;
			req.packageValue = packageValue;
			req.sign = sign;
			// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
			api.registerApp(WXContants.APP_ID);
			api.sendReq(req);
		} catch (Exception e) {
			Log.e("PAY_GET", "异常：" + e.getMessage());
			Toast.makeText(activity, "异常：" + e.getMessage(), Toast.LENGTH_SHORT)
					.show();
		}
	}
}

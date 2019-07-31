package com.lwc.shanxiu.activity;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.user.LoginOrRegistActivity;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;

/**
 * 原生和h5页面交互类
 * @author 何栋
 * @date 2017-07-30
 */
public class AndroidJSInterface {
	private Context mContext;
	public AndroidJSInterface(Context context){
		mContext = context;
	}

	/**
	 * h5页面调用，弹出提示信息
	 * @param msg 提示信息
     */
	@JavascriptInterface
    public void showToast(String msg) {
		ToastUtil.showToast(mContext, msg);
    }

	/**
	 * h5调用该方法，获取登录令牌
	 * @return 令牌
     */
	@JavascriptInterface
	public String getToken() {
		String DEVICE_ID = Utils.getDeviceId(mContext);
		return SharedPreferencesUtils.getInstance(mContext).loadString("token")+"_"+DEVICE_ID+"_"+"ANDROID";
	}

	/**
	 * 传入h5页面分享信息
	 * @param shareTitle 分享标题
	 * @param sharePhoto 分享图片
	 * @param flink 点击连接
	 * @param shareDes 描述
     * @param activityId 活动id
     */
	@JavascriptInterface
	public void setShare(String shareTitle, String sharePhoto, String flink, String shareDes, String activityId) {
		SharedPreferencesUtils.getInstance(mContext).saveString("shareTitle", shareTitle);
		SharedPreferencesUtils.getInstance(mContext).saveString("sharePhoto", sharePhoto);
		SharedPreferencesUtils.getInstance(mContext).saveString("flink", flink);
		SharedPreferencesUtils.getInstance(mContext).saveString("shareDes", shareDes);
		SharedPreferencesUtils.getInstance(mContext).saveString("activityId", activityId);
//		SharedPreferencesUtils.getInstance(mContext).saveString("checkShare", checkShare);
	}

	/**
	 * h5页面调用分享
	 * @param shareTitle 分享标题
	 * @param sharePhoto 分享图片
	 * @param flink 点击连接
	 * @param shareDes 描述
	 * @param activityId 活动id
	 */
	@JavascriptInterface
	public void showShare(String shareTitle, String sharePhoto, String flink, String shareDes, String activityId) {
		SharedPreferencesUtils.getInstance(mContext).saveString("shareTitle", shareTitle);
		SharedPreferencesUtils.getInstance(mContext).saveString("sharePhoto", sharePhoto);
		SharedPreferencesUtils.getInstance(mContext).saveString("flink", flink);
		SharedPreferencesUtils.getInstance(mContext).saveString("shareDes", shareDes);
		SharedPreferencesUtils.getInstance(mContext).saveString("activityId", activityId);
		if (InformationDetailsActivity.activity != null) {
			InformationDetailsActivity.activity.doOauthVerify();
		}
	}

	/**
	 * 调转到登录页面
	 */
	@JavascriptInterface
	public void gotoLogin() {
		IntentUtil.gotoActivity(mContext, LoginOrRegistActivity.class);
	}

	@JavascriptInterface
	public String getInterface(){
		return "android_js_interface";
	}
}

package com.lwc.shanxiu.configs;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDexApplication;

import com.lwc.shanxiu.map.LocationService;
import com.lwc.shanxiu.utils.FileUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.SystemUtil;
import com.lwc.shanxiu.wxapi.WXContants;
import com.taobao.sophix.SophixManager;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;

import org.litepal.LitePal;

import java.io.IOException;
import java.io.InputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

/**
 * 全局公用Application类
 * 
 * @Copyright: Copyright (c) 2013 何栋 Tentinet Technology Co., Ltd. Inc. All rights reserved.
 * 
 */
public class TApplication extends MultiDexApplication {//MultiDexApplication

	/** 全局上下文，可用于文本、图片、sp数据的资源加载，不可用于视图级别的创建和展示 */
	public static Context context;

	/**
	 * 整个应用程序的初始入口函数
	 * 
	 * 本方法内一般用来初始化程序的全局数据，或者做应用的长数据保存取回操作
	 * 
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	@Override
	public void onCreate() {
		CrashReport.initCrashReport(getApplicationContext(), "030e83393a", false);
		// queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中
		SophixManager.getInstance().queryAndLoadNewPatch();

		// 实例化全局调用的上下文
		context = getApplicationContext();
		super.onCreate();
		startAlarm();
//		UMConfigure.init(this, "56ea90d7e0f55af7c6002d12", "mixiu", UMConfigure.DEVICE_TYPE_PHONE, "c967448458484522ffd212b3b729847f");
//
//		PushAgent mPushAgent = PushAgent.getInstance(this);
////注册推送服务，每次调用register方法都会回调该接口
//		mPushAgent.register(new IUmengRegisterCallback() {
//			@Override
//			public void onSuccess(String deviceToken) {
//				//注册成功会返回device token
//				LLog.e("联网 友盟deviceToken："+deviceToken);
//			}
//			@Override
//			public void onFailure(String s, String s1) {
//			}
//		});
		//友盟分享
		// 微信 appid appsecret
		PlatformConfig.setWeixin(WXContants.APP_ID, "a3462b868b826a7f29db97a8e77acf29");
		// 新浪微博 appkey appsecret
		// PlatformConfig.setSinaWeibo("3921700954","04b48b094faeb16683c32669824ebdad");
		//QQ空间
		PlatformConfig.setQQZone("1106294740", "9moOfi0GY2prjqkY");
//		PlatformConfig.setQQZone("1106524418", "gMmVmcewhHBaUPCg");
//		Config.DEBUG = true;
//		UMConfigure.init(this,"58c8823b677baa0d81001491"
//				,"umeng", UMConfigure.DEVICE_TYPE_PHONE,"");
		UMShareAPI.get(this);
//		UMConfigure.setEncryptEnabled(true);
//		InAppMessageManager.getInstance(context).setInAppMsgDebugMode(true);
//		//华为推送
//		HuaWeiRegister.register(this);
//		//魅族推送
//		MeizuRegister.register(this, "1001271", "50fe419d847b4c2ba6c8651a21ec251b");
//		//小米推送
//		MiPushRegistar.register(this, "2882303761517808555", "5591780829555");

		Intent intent = new Intent("com.lwc.shanxiu.push");
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
		// 每五秒唤醒一次
		long second = 15 * 1000;
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), second, pendingIntent);

		// 读取应用基础信息
		ServerConfig.VERSION_CODE = SystemUtil.getCurrentVersionCode();
		ServerConfig.VERSION_NAME = SystemUtil.getCurrentVersionName();

		LitePal.initialize(this);
		// 本地文件构建
		FileUtil.createAllFile();

		initJPush();
		initHttp();
	}

	public void startAlarm(){
		/**
		 首先获得系统服务
		 */
		AlarmManager am = (AlarmManager)
				getSystemService(Context.ALARM_SERVICE);

		/** 设置闹钟的意图，我这里是去调用一个服务，该服务功能就是获取位置并且上传*/
		Intent intent = new Intent(this, LocationService.class);
		PendingIntent pendSender = PendingIntent.getService(this, 0, intent, 0);
		am.cancel(pendSender);

		/**AlarmManager.RTC_WAKEUP 这个参数表示系统会唤醒进程；我设置的间隔时间是10分钟 */
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 9*60*1000, pendSender);
	}

	public void initHttp() {
//        设置具体的证书
		HttpsUtils.SSLParams sslParams = null;
		//        双向认证
		sslParams = HttpsUtils.getSslSocketFactory(null, null, "qg2k9p79deag8");

		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.hostnameVerifier(new HostnameVerifier() {
					@Override
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				})
				.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
				.build();
		OkHttpUtils.initClient(okHttpClient);
	}

	/**
	 * 初始化极光推送
	 */
	private void initJPush(){
		LLog.i("初始化推送");
		//极光推送
		JPushInterface.setDebugMode(false);
		JPushInterface.init(getApplicationContext());

	}

}

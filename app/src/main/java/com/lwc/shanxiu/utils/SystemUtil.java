package com.lwc.shanxiu.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.lwc.shanxiu.configs.TApplication;

import java.util.List;

/**
 * 应用工具类》
 * 
 * @Description
 * @author 何栋
 * @date 2013-7-19
 */
public class SystemUtil {


	private static long lastClickTime;
	/**
	 * 防止连续点击触发重复提交事件
	 *
	 * @return
	 */
	public synchronized static boolean isFastClick(int number) {
		long time = System.currentTimeMillis();
		if ( time - lastClickTime < number) {
			return true;
		}
		lastClickTime = time;
		return false;
	}
	/**
	 * 获取当前app版本号
	 * 
	 * @version 1.0
	 * @createTime 2013-10-21,下午3:45:06
	 * @updateTime 2013-10-21,下午3:45:06
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return
	 */
	public static int getCurrentVersionCode() {
		int versionCode = 1;
		// 获取packagemanager的实例
		PackageManager packageManager = TApplication.context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(TApplication.context.getPackageName(), 0);
			versionCode = packInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			versionCode = 1;
		}
		return versionCode;
	}

	/**
	 * 获取当前系统版本名称
	 * 
	 * @version 1.0
	 * @createTime 2013-10-21,下午3:45:17
	 * @updateTime 2013-10-21,下午3:45:17
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return
	 */
	public static String getCurrentVersionName() {
		String versionName = "";
		// 获取packagemanager的实例
		PackageManager packageManager = TApplication.context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(TApplication.context.getPackageName(), 0);
			versionName = packInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			versionName = "1.0.0";
		}
		return versionName;
	}

	/**
	 * 获取本机的Mac地址
	 * 
	 * @version 1.0
	 * @createTime 2013-11-13,上午11:58:43
	 * @updateTime 2013-11-13,上午11:58:43
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return 本机mac
	 */
	public static String getPhoneMac() {
		WifiManager wifi = (WifiManager) TApplication.context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		String mac = info.getMacAddress();
		return mac;
	}

	/**
	 * 判断系统语言是否中文
	 *
	 * @version 1.0
	 * @createTime 2014年9月29日,下午3:09:00
	 * @updateTime 2014年9月29日,下午3:09:00
	 * @createAuthor WangYuWen
	 * @updateAuthor WangYuWen
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @return
	 */
	public static boolean IsSystemLanguage() {
		if (TApplication.context.getResources().getConfiguration().locale.getCountry().equals("CN")
				|| TApplication.context.getResources().getConfiguration().locale.getCountry().equals("TW")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取系统语言
	 *
	 * @version 1.0
	 * @createTime 2014年9月29日,下午3:09:00
	 * @updateTime 2014年9月29日,下午3:09:00
	 * @createAuthor WangYuWen
	 * @updateAuthor WangYuWen
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @return 只返回英文和中文
	 */
	public static String getSystemLanguage() {
		if (TApplication.context.getResources().getConfiguration().locale.getCountry().equals("CN")
				|| TApplication.context.getResources().getConfiguration().locale.getCountry().equals("TW")) {
			return "zh";
		} else {
			return "en";
		}
	}

	/**
	 * 获取项目名称
	 *
	 * <h3>Version
	 * <h3/>1.0
	 * <h3>CreateTime
	 * <h3/>2015/11/9,13:47
	 * <h3>UpdateTime
	 * <h3/>2015/11/9,13:47
	 * <h3>CreateAuthor
	 * <h3/>Str1ng
	 * <h3>UpdateAuthor
	 * <h3/>
	 * <h3>UpdateInfo
	 * <h3/>(此处输入修改内容,若无修改可不写.)
	 * 
	 * @return 返回项目名称
	 */
	public static String getAppName() {
		PackageManager pm = TApplication.context.getPackageManager();
		String appName = TApplication.context.getApplicationInfo().loadLabel(pm).toString();
		return appName;
	}

	/**
	 * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
	 * @param context
	 * @return true 表示开启
	 */
	public static final boolean isOPen(final Context context) {
		LocationManager locationManager
				= (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		// 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
		boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
//		boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		if (gps) {
			return true;
		}
		return false;
	}

	public static void openGPS(Context context, boolean open) {
		if (Build.VERSION.SDK_INT <19) {
			Settings.Secure.setLocationProviderEnabled(context.getContentResolver(),
					LocationManager.GPS_PROVIDER, open);
		}else{
			if(!open){
				Settings.Secure.putInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE, android.provider.Settings.Secure.LOCATION_MODE_OFF);
			}else{
				Settings.Secure.putInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE, android.provider.Settings.Secure.LOCATION_MODE_BATTERY_SAVING);
			}
		}
	}

	/**
	 * 强制帮用户打开GPS
	 * @param context
	 */
	public static final void openGPS(Context context) {
		Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//		Toast.makeText(context, "打开后直接点击返回键即可，若不打开返回下次将再次出现", Toast.LENGTH_SHORT).show();
		((Activity)context).startActivityForResult(intent, 0); // 设置完成后返回到原来的界面
//
//		Intent GPSIntent = new Intent();
//		GPSIntent.setClassName("com.android.settings",
//				"com.android.settings.widget.SettingsAppWidgetProvider");
//		GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
//		GPSIntent.setData(Uri.parse("custom:3"));
//		try {
//			PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
//		} catch (PendingIntent.CanceledException e) {
//			e.printStackTrace();
//		}
	}

	/**
	 * 获取当前手机系统版本号
	 *
	 * @return  系统版本号
	 */
	public static String getSystemVersion() {
		return android.os.Build.VERSION.RELEASE;
	}

	/**
	 * 获取手机型号
	 *
	 * @return  手机型号
	 */
	public static String getSystemModel() {
		return android.os.Build.MODEL;
	}

	/**
	 * 获取手机厂商
	 *
	 * @return  手机厂商
	 */
	public static String getDeviceBrand() {
		return android.os.Build.BRAND;
	}

	/**
	 * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
	 *
	 * @return  手机IMEI
	 */
	public static String getIMEI(Context ctx) {
		TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
		if (tm != null) {
			return tm.getDeviceId();
		}
		return null;
	}

	/**
	 * 获取应用详情页面intent
	 *
	 * @return
	 */
	public static Intent getAppDetailSettingIntent() {
		Intent localIntent = new Intent();
		localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (Build.VERSION.SDK_INT >= 9) {
			localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
			localIntent.setData(Uri.fromParts("package", "com.lwc.shanxiu", null));
		} else if (Build.VERSION.SDK_INT <= 8) {
			localIntent.setAction(Intent.ACTION_VIEW);
			localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
			localIntent.putExtra("com.android.settings.ApplicationPkgName", "com.lwc.shanxiu");
		}
		return localIntent;
	}

	public static boolean isBackground(Context context) {
		try {
			ActivityManager activityManager = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
					.getRunningAppProcesses();
			for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
				if (appProcess.processName.equals(context.getPackageName())) {
                /*
                BACKGROUND=400 EMPTY=500 FOREGROUND=100
                GONE=1000 PERCEPTIBLE=130 SERVICE=300 ISIBLE=200
                 */
					LLog.i(context.getPackageName(), "此appimportace ="
							+ appProcess.importance
							+ ",context.getClass().getName()="
							+ context.getClass().getName());
					if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
						LLog.i(context.getPackageName(), "处于后台"
								+ appProcess.processName);
						return true;
					} else {
						LLog.i(context.getPackageName(), "处于前台"
								+ appProcess.processName);
						return false;
					}
				}
			}
		} catch (Exception e){}
		return false;
	}
}

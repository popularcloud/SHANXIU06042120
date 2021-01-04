/**
 *
 */
package com.lwc.shanxiu.map;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.DecimalFormat;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

import static com.lwc.shanxiu.controler.global.GlobalValue.MY_PERMISSIONS_REQUEST_CALL_PHONE;

/**
 * 辅助工具类
 *
 * @创建时间： 2015年11月24日 上午11:46:50
 * @文件名称: Utils.java
 * @类型名称: Utils
 */
public class Utils {
	/**
	 * 开始定位
	 */
	public final static int MSG_LOCATION_START = 0;
	/**
	 * 定位完成
	 */
	public final static int MSG_LOCATION_FINISH = 1;
	/**
	 * 停止定位
	 */
	public final static int MSG_LOCATION_STOP = 2;

	public final static String KEY_URL = "URL";
	public final static String URL_H5LOCATION = "file:///android_asset/location.html";

	/**
	 * 根据定位结果返回定位信息的字符串
	 *
	 * @param location
	 * @return
	 */
	public synchronized static String getLocationStr(AMapLocation location) {
		if (null == location) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		//errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
		if (location.getErrorCode() == 0) {
			sb.append("定位成功" + "\n");
			sb.append("定位类型: " + location.getLocationType() + "\n");
			sb.append("经    度    : " + location.getLongitude() + "\n");
			sb.append("纬    度    : " + location.getLatitude() + "\n");
			sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
			sb.append("提供者    : " + location.getProvider() + "\n");

			sb.append("海    拔    : " + location.getAltitude() + "米" + "\n");
			sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
			sb.append("角    度    : " + location.getBearing() + "\n");
			if (location.getProvider().equalsIgnoreCase(
					android.location.LocationManager.GPS_PROVIDER)) {
				// 以下信息只有提供者是GPS时才会有
				// 获取当前提供定位服务的卫星个数
				sb.append("星    数    : "
						+ location.getSatellites() + "\n");
			}

			//逆地理信息
			sb.append("国    家    : " + location.getCountry() + "\n");
			sb.append("省            : " + location.getProvince() + "\n");
			sb.append("市            : " + location.getCity() + "\n");
			sb.append("城市编码 : " + location.getCityCode() + "\n");
			sb.append("区            : " + location.getDistrict() + "\n");
			sb.append("区域 码   : " + location.getAdCode() + "\n");
			sb.append("地    址    : " + location.getAddress() + "\n");
			sb.append("兴趣点    : " + location.getPoiName() + "\n");
			//定位完成的时间
			sb.append("定位时间: " + formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");

		} else {
			//定位失败
			sb.append("定位失败" + "\n");
			sb.append("错误码:" + location.getErrorCode() + "\n");
			sb.append("错误信息:" + location.getErrorInfo() + "\n");
			sb.append("错误描述:" + location.getLocationDetail() + "\n");
		}
		//定位之后的回调时间
		sb.append("回调时间: " + formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");
		return sb.toString();
	}

	private static SimpleDateFormat sdf = null;

	/**
	 * 判定输入汉字
	 *
	 * @param c
	 * @return
	 */
	public static boolean isChinese(char c) {
		if ((c >= 0x4e00) && (c <= 0x9fbb)) {
			return true;
		} else {
			return false;
		}
//		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
//		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
//				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
//				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
//				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
//				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
//				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
//			return true;
//		}
//		return false;
	}

	public static void lxkf(Context context, String phone) {
// 检查是否获得了权限（Android6.0运行时权限）
		if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
// 没有获得授权，申请授权
			if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CALL_PHONE)) {
// 返回值：
//如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
//如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
//如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
// 弹窗需要解释为何需要该权限，再次请求授权
				Toast.makeText(context, "请授权！", Toast.LENGTH_LONG).show();
// 帮跳转到该应用的设置界面，让用户手动授权
				Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
				Uri uri = Uri.fromParts("package", "com.lwc.common", null);
				intent.setData(uri);
				context.startActivity(intent);
			} else {
// 不需要解释为何需要该权限，直接请求授权
				ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
			} } else {
// 已经获得授权，可以打电话
			CallPhone(context, phone);
		}
	}

	private static void CallPhone(Context context, String phone) {
		if (!TextUtils.isEmpty(phone)) {
			phone = "tel:"+phone;
		}
		Intent intent = new Intent(Intent.ACTION_CALL);
		Uri data = Uri.parse(phone);
		intent.setData(data);
		context.startActivity(intent);
	}

	public synchronized static String formatUTC(long l, String strPattern) {
		if (TextUtils.isEmpty(strPattern)) {
			strPattern = "yyyy-MM-dd HH:mm:ss";
		}
		if (sdf == null) {
			try {
				sdf = new SimpleDateFormat(strPattern, Locale.CHINA);
			} catch (Throwable e) {
			}
		} else {
			sdf.applyPattern(strPattern);
		}
		return sdf == null ? "NULL" : sdf.format(l);
	}

	//自定义报警通知（震动铃声都不要）
	public static void setNotification4(Context context){
		BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(context);
		builder.statusBarDrawable = R.drawable.logo_new;
//		builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为自动消失
		builder.notificationDefaults = Notification.DEFAULT_LIGHTS;// 设置为铃声与震动都不要
		JPushInterface.setDefaultPushNotificationBuilder(builder);
	}

	//自定义报警通知（震动铃声都要）
	public static void setNotification1(Context context){
		BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(context);
		builder.statusBarDrawable = R.drawable.logo_new;//消息栏显示的图标
//		builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为自动消失
		builder.notificationDefaults = Notification.DEFAULT_SOUND| Notification.DEFAULT_VIBRATE|Notification.DEFAULT_LIGHTS;// 设置为铃声与震动都要
		JPushInterface.setDefaultPushNotificationBuilder(builder);
	}
	//自定义报警通知（铃声）
	public static void setNotification2(Context context){
		BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(context);
		builder.statusBarDrawable = R.drawable.logo_new;
		builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为自动消失
		builder.notificationDefaults = Notification.DEFAULT_SOUND|Notification.DEFAULT_LIGHTS;// 设置为铃声与震动都要
		JPushInterface.setDefaultPushNotificationBuilder(builder);
	}
	//自定义报警通知（震动）
	public static void setNotification3(Context context){
		BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(context);
		builder.statusBarDrawable = R.drawable.logo_new;
		builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为自动消失
		builder.notificationDefaults = Notification.DEFAULT_VIBRATE|Notification.DEFAULT_LIGHTS;// 震动
		JPushInterface.setDefaultPushNotificationBuilder(builder);
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 返回时间戳和随机数组成的图片名字
	 * @return
	 */
	public static String getImgName() {
		Random random = new Random();
		int x = random.nextInt(999999);
		String key = new Date().getTime() + "_" + x + ".jpg";
		return key;
	}

	public static Intent getExplicitIntent(Context context, Intent implicitIntent) {

		if (context.getApplicationInfo().targetSdkVersion < Build.VERSION_CODES.LOLLIPOP) {
			return implicitIntent;
		}

		// Retrieve all services that can match the given intent
		PackageManager pm = context.getPackageManager();
		List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);
		// Make sure only one match was found
		if (resolveInfo == null || resolveInfo.size() != 1) {
			return null;
		}
		// Get component info and create ComponentName
		ResolveInfo serviceInfo = resolveInfo.get(0);
		String packageName = serviceInfo.serviceInfo.packageName;
		String className = serviceInfo.serviceInfo.name;
		ComponentName component = new ComponentName(packageName, className);
		// Create a new intent. Use the old one for extras and such reuse
		Intent explicitIntent = new Intent(implicitIntent);
		// Set the component to be explicit
		explicitIntent.setComponent(component);
		return explicitIntent;
	}


	/**
	 * 　　* 保存文件 　　* @param toSaveString 　　* @param filePath
	 */
	public static void saveFile(String toSaveString, String fileName, boolean append) {
		try {
			String sdCardRoot = Environment.getExternalStorageDirectory()
					.getAbsolutePath();
			File saveFile = new File(sdCardRoot + "/" + fileName);
			if (!saveFile.exists()) {
				File dir = new File(saveFile.getParent());
				dir.mkdirs();
				saveFile.createNewFile();
			}
			FileOutputStream outStream = new FileOutputStream(saveFile, append);
			outStream.write(toSaveString.getBytes());
			outStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Notification buildNotification(Context context) {
		Notification.Builder builder = new Notification.Builder(context);
		builder.setContentText("service");
		return builder.getNotification();
	}

	public static void startWifi(Context context) {
		WifiManager wm = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		wm.setWifiEnabled(true);
		wm.reconnect();
	}

	public static boolean isWifiEnabled(Context context) {
		WifiManager wm = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		return wm.isWifiEnabled();
	}

	public static String getManufacture(Context context) {
		return Build.MANUFACTURER;
	}

	private static String CLOSE_BRODECAST_INTENT_ACTION_NAME="com.lwc.shanxiu.map.CloseService";


	public static Intent getCloseBrodecastIntent() {
		return new Intent(CLOSE_BRODECAST_INTENT_ACTION_NAME);
	}

	public static IntentFilter getCloseServiceFilter() {
		return new IntentFilter(CLOSE_BRODECAST_INTENT_ACTION_NAME);
	}

	public static Drawable getDrawable(Activity activity, int zh_new) {
		Drawable drawable= activity.getResources().getDrawable(zh_new);
		drawable.setBounds(0,0,drawable.getMinimumWidth(), drawable.getMinimumHeight());
		return drawable;
	}

	public static class CloseServiceReceiver extends BroadcastReceiver {

		Service mService;

		public CloseServiceReceiver(Service service) {
			this.mService = service;
		}

		@Override
		public void onReceive(Context context, Intent intent) {
			if (mService == null) {
				return;
			}
			mService.onDestroy();
		}
	}

	public static String md5Encode(String inStr){
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			Log.e("Utils", e.getMessage());
			return "";
		}
		try {
			byte[] byteArray = inStr.getBytes("UTF-8");
			byte[] md5Bytes = md5.digest(byteArray);
			StringBuffer hexValue = new StringBuffer();
			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16) {
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
			}
			return hexValue.toString().toLowerCase();
		} catch (Exception e) {
			return inStr;
		}
	}

	public static void deleteFile(String path, Context context) {
		if(!TextUtils.isEmpty(path)){
			try {
				Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				ContentResolver mContentResolver = context.getContentResolver();
				String where = MediaStore.Images.Media.DATA + "='" + path + "'";
				//删除图片
				mContentResolver.delete(uri, where, null);

				//发送广播
				Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
				File file = new File(path);
				Uri uri1 = Uri.fromFile(file);
				intent.setData(uri1);
				LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
				if (file.exists()) {
					file.delete();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static String jia(String v1, String v2){
		BigDecimal bd1 = new BigDecimal(v1);
		BigDecimal bd2 = new BigDecimal(v2);
		return getNumber(bd1.add(bd2).toString()+""); // 加
	}

	public static String jian(String v1, String v2){
		BigDecimal bd1 = new BigDecimal(v1);
		BigDecimal bd2 = new BigDecimal(v2);
		return getNumber(bd1.subtract(bd2).toString()+""); // 减
	}

	public static String cheng(String v1, String v2){
		BigDecimal bd1 = new BigDecimal(v1);
		BigDecimal bd2 = new BigDecimal(v2);
		return getNumber(bd1.multiply(bd2).toString()); // 乘
	}

	public static String chu(String v1, String v2){
		BigDecimal bd1 = new BigDecimal(v1);
		BigDecimal bd2 = new BigDecimal(v2);
		return getNumber(bd1.divide(bd2, 2, BigDecimal.ROUND_HALF_UP).toString()); // 除
	}

	public static String getNumber(String str) {
		if (str.endsWith(".00") || str.endsWith(".0") || str.endsWith(".000") || str.endsWith(".0000")) {
			str = str.substring(0, str.indexOf("."));
		}
		return str;
	}

	/**
	 * 保留两位小数
	 * @param str
	 * @return
     */
	/*public static Double formatTwoPoint(String str) {


		DecimalFormat df = null;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
			df = new DecimalFormat("#.00");
			String money = df.format(str);
		}

		return f1;
	}*/
	public static String formatTwoPoint(Double str) {
		if(str == 0d){
			return "0.00";
		}
		DecimalFormat df = null;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
			df = new DecimalFormat("######0.00");
			return df.format(str);
		}else{
			BigDecimal bg = new BigDecimal(str);
			double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			return String.valueOf(f1);
		}
	}

	/**
	 * 对textview 中指定文本进行字体颜色 大小 特殊处理
	 * @param start
	 * @param end
	 * @param color
	 * @param str
	 * @return
	 */
	public static SpannableStringBuilder getSpannableStringBuilder(int start, int end, int color, String str) {
		return getSpannableStringBuilder(start, end, color, str, 15);
	}

	/**
	 * 对textview 中指定文本进行字体颜色 大小 特殊处理
	 * @param start
	 * @param end
	 * @param color
	 * @param str
	 * @return
	 */
	public static SpannableStringBuilder getSpannableStringBuilder(int start, int end, int color, String str, int textSize) {
		SpannableStringBuilder builder = new SpannableStringBuilder(str);
		ForegroundColorSpan redSpan = new ForegroundColorSpan(color);
		builder.setSpan(redSpan, start, end,  Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		builder.setSpan(new AbsoluteSizeSpan(textSize,true), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return builder;
	}

	public static SpannableStringBuilder getSpannableStringBuilder(int start, int end, int color, String str, int textSize, boolean isBold) {
		SpannableStringBuilder builder = new SpannableStringBuilder(str);
		ForegroundColorSpan redSpan = new ForegroundColorSpan(color);
		builder.setSpan(redSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		builder.setSpan(new AbsoluteSizeSpan(textSize, true), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		builder.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return builder;
	}

	public static SpannableStringBuilder getSpannableStringBuilder(int start, int end, String str, int textSize, boolean isBold) {
		SpannableStringBuilder builder = new SpannableStringBuilder(str);
		builder.setSpan(new AbsoluteSizeSpan(textSize, true), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		builder.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return builder;
	}

	public static SpannableStringBuilder getSpannableStringBuilder(int start, int end, int color, int bgColor, String str, int textSize, boolean isBold) {
		SpannableStringBuilder builder = new SpannableStringBuilder(str);
		ForegroundColorSpan redSpan = new ForegroundColorSpan(color);
		BackgroundColorSpan bgSpan = new BackgroundColorSpan(bgColor);
		builder.setSpan(redSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		builder.setSpan(bgSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		builder.setSpan(new AbsoluteSizeSpan(textSize, true), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		builder.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return builder;
	}

	public static String getDeviceId(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String DEVICE_ID = tm.getDeviceId();
		if (DEVICE_ID == null || DEVICE_ID.equals("") || DEVICE_ID.length() < 8) {
			String device_id = SharedPreferencesUtils.getInstance(context).loadString("device_id");
			if (TextUtils.isEmpty(device_id)) {
				Random random = new Random();
				int x = random.nextInt(99);
				DEVICE_ID = new Date().getTime() + "" + x;
				SharedPreferencesUtils.getInstance(context).saveString("device_id", DEVICE_ID);
			} else {
				DEVICE_ID = device_id;
			}
		} else {
			SharedPreferencesUtils.getInstance(context).saveString("device_id", DEVICE_ID);
		}
		return DEVICE_ID;
	}

	public static String getMoney(String money) {
		if (money.contains(".")){
			int index = money.indexOf(".");
			if (index == money.length()-1) {
				money = money+"00";
			} else if (index == money.length()-2) {
				money = money+"0";
			}
		} else {
			money = money+".00";
		}
		return money;
	}

	// 获得独一无二的Psuedo ID
	public static String getUniquePsuedoID() {
		String serial = null;

		String m_szDevIDShort = "35" + Build.BOARD.length() % 10
				+ Build.BRAND.length() % 10 +

				Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

				Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

				Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

				Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

				Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

				Build.USER.length() % 10; // 13 位

		try {
			serial = android.os.Build.class.getField("SERIAL").get(null)
					.toString();
			// API>=9 使用serial号
			return new UUID(m_szDevIDShort.hashCode(), serial.hashCode())
					.toString();
		} catch (Exception exception) {
			// serial需要一个初始化
			serial = "serial"; // 随便一个初始化
		}
		// 使用硬件信息拼凑出来的15位号码
		return new UUID(m_szDevIDShort.hashCode(), serial.hashCode())
				.toString();
	}

	public static String ToDBC(String input) {
		input = stringFilter(input);
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}

		return new String(c);
	}

	public static String stringFilter(String str) {
		str = str.replaceAll("!", "! ").replaceAll(":", ": ").replaceAll(",", ", ").replaceAll("'", "' ").replaceAll("\"", "\" ")
				.replaceAll("！", "! ").replaceAll("：", ": ").replaceAll("，", ", ").replaceAll("‘", "' ").replaceAll("“", "\" ");// 替换中文标号
		String regEx = "[『』]"; // 清除掉特殊字符
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 判断缓存的String数据是否到期
	 *
	 * @param str
	 * @return true：到期了 false：还没有到期
	 */
	public static boolean isDue(String str) {
		return isDue(str.getBytes());
	}

	/**
	 * 判断缓存的byte数据是否到期
	 *
	 * @param data
	 * @return true：到期了 false：还没有到期
	 */
	public static boolean isDue(byte[] data) {
		String[] strs = getDateInfoFromDate(data);
		if (strs != null && strs.length == 2) {
			String saveTimeStr = strs[0];
			while (saveTimeStr.startsWith("0")) {
				saveTimeStr = saveTimeStr
						.substring(1, saveTimeStr.length());
			}
			long saveTime = Long.valueOf(saveTimeStr);
			long deleteAfter = Long.valueOf(strs[1]);
			if (System.currentTimeMillis() > saveTime + deleteAfter * 1000) {
				return true;
			}
		}
		return false;
	}

	public static String newStringWithDateInfo(int second, String strInfo) {
		return createDateInfo(second) + strInfo;
	}

	public static byte[] newByteArrayWithDateInfo(int second, byte[] data2) {
		byte[] data1 = createDateInfo(second).getBytes();
		byte[] retdata = new byte[data1.length + data2.length];
		System.arraycopy(data1, 0, retdata, 0, data1.length);
		System.arraycopy(data2, 0, retdata, data1.length, data2.length);
		return retdata;
	}

	public static String clearDateInfo(String strInfo) {
		if (strInfo != null && hasDateInfo(strInfo.getBytes())) {
			strInfo = strInfo.substring(strInfo.indexOf(mSeparator) + 1,
					strInfo.length());
		}
		return strInfo;
	}

	public static byte[] clearDateInfo(byte[] data) {
		if (hasDateInfo(data)) {
			return copyOfRange(data, indexOf(data, mSeparator) + 1,
					data.length);
		}
		return data;
	}

	private static boolean hasDateInfo(byte[] data) {
		return data != null && data.length > 15 && data[13] == '-'
				&& indexOf(data, mSeparator) > 14;
	}

	private static String[] getDateInfoFromDate(byte[] data) {
		if (hasDateInfo(data)) {
			String saveDate = new String(copyOfRange(data, 0, 13));
			String deleteAfter = new String(copyOfRange(data, 14,
					indexOf(data, mSeparator)));
			return new String[] { saveDate, deleteAfter };
		}
		return null;
	}

	private static int indexOf(byte[] data, char c) {
		for (int i = 0; i < data.length; i++) {
			if (data[i] == c) {
				return i;
			}
		}
		return -1;
	}

	private static byte[] copyOfRange(byte[] original, int from, int to) {
		int newLength = to - from;
		if (newLength < 0)
			throw new IllegalArgumentException(from + " > " + to);
		byte[] copy = new byte[newLength];
		System.arraycopy(original, from, copy, 0,
				Math.min(original.length - from, newLength));
		return copy;
	}

	private static final char mSeparator = ' ';

	private static String createDateInfo(int second) {
		String currentTime = System.currentTimeMillis() + "";
		while (currentTime.length() < 13) {
			currentTime = "0" + currentTime;
		}
		return currentTime + "-" + second + mSeparator;
	}

	/*
     * Bitmap → byte[]
     */
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		if (bm == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/*
     * byte[] → Bitmap
     */
	public static Bitmap Bytes2Bimap(byte[] b) {
		if (b.length == 0) {
			return null;
		}
		return BitmapFactory.decodeByteArray(b, 0, b.length);
	}

	/*
     * Drawable → Bitmap
     */
	public static Bitmap drawable2Bitmap(Drawable drawable) {
		if (drawable == null) {
			return null;
		}
		// 取 drawable 的长宽
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();
		// 取 drawable 的颜色格式
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		// 建立对应 bitmap
		Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		// 建立对应 bitmap 的画布
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		// 把 drawable 内容画到画布中
		drawable.draw(canvas);
		return bitmap;
	}

	/*
     * Bitmap → Drawable
     */
	@SuppressWarnings("deprecation")
	public static Drawable bitmap2Drawable(Bitmap bm) {
		if (bm == null) {
			return null;
		}
		return new BitmapDrawable(bm);
	}

	private static long lastClickTime;

	private static String urlStr = "";
	/**
	 * 防止连续点击触发重复提交事件
	 *
	 * @return
	 */
	public synchronized static boolean isFastClick(int number,String url) {
		long time = System.currentTimeMillis();
		if ( time - lastClickTime < number && urlStr.equals(url)) {
			return true;
		}

		urlStr = url;
		lastClickTime = time;
		return false;
	}

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
	 * @author Administrator
	 * @time 2018/3/5  14:42
	 * @describe 获取所有已安装应用
	 */
	public static List<String> getApkList(Context context) {
		//获取packagemanager
		final PackageManager packageManager = context.getPackageManager();
		//获取所有已安装程序的包信息
		List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
		//用于存储所有已安装程序的包名
		List<String> packageNames = new ArrayList<String>();
		//从pinfo中将包名字逐一取出，压入pName list中
		if (packageInfos != null) {
			for (int i = 0; i < packageInfos.size(); i++) {
				String packName = packageInfos.get(i).packageName;
				packageNames.add(packName);
			}
		}
		return packageNames;
	}

	public static void toMap(Context context, String name, String address, double lat, double lon) {
		if (name.equals("高德地图")) {
			toGaodeMap(context, address, lat, lon);
		} else if (name.equals("百度地图")) {
			toBaiduMap(context, address, lat, lon);
		} else if (name.equals("腾讯地图")) {
			toTengxunMap(context, address, lat, lon);
		}
	}

	public static void toGaodeMap(Context context, String name, double lat, double lon) {
		try {
			Intent intent = Intent.getIntent("androidamap://navi?sourceApplication=" + context.getResources().getString(R.string.app_name) + "&poiname="+name+"&lat="+lat+"&lon="+lon+"&dev=0");
			context.startActivity(intent);
		} catch (URISyntaxException e) {
			ToastUtil.show(context, "您尚未安装高德地图或地图版本过低");
			Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
			Intent AIntent = new Intent(Intent.ACTION_VIEW, uri);
			context.startActivity(AIntent);
			e.printStackTrace();
		}
	}

	public static void toBaiduMap(Context context, String name, double lat, double lon) {
		try {
			Intent intent = new Intent();
			double[] doubles = gaoDeToBaidu(lat, lon);
			intent.setData(Uri.parse("baidumap://map/marker?location=" + doubles[0] + "," + doubles[1]
					+ "&title=" + context.getResources().getString(R.string.app_name)
					+ "&content=" + name + "&traffic=on"));
//                                intent.setData(Uri.parse("baidumap://map/marker?location=" + getIntent().getStringExtra(LAT) + "," + getIntent().getStringExtra(LNG)
//                                        + "&title=" + getIntent().getStringExtra(SHOP_TITLE)
//                                        + "&content=" + getIntent().getStringExtra(SHOP_PHONE) + "&traffic=on"));

			context.startActivity(intent);
		} catch (Exception e) {
			ToastUtil.show(context, "您尚未安装百度地图app或app版本过低");
		}
	}

	/**
	 * @author Administrator
	 * @time 2018/5/14  13:35
	 * @describe 高德转百度（火星坐标gcj02ll–>百度坐标bd09ll）
	 */
	public static double[] gaoDeToBaidu(double gd_lat, double gd_lon) {
		double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
		double x = gd_lon, y = gd_lat;
		double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
		double tempLon = z * Math.cos(theta) + 0.0065;
		double tempLat = z * Math.sin(theta) + 0.006;
		double[] gps = {tempLat, tempLon};
		return gps;
	}

	public static void toTengxunMap(Context context, String name, double lat, double lon) {
		try {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.addCategory(Intent.CATEGORY_DEFAULT);
			//将功能Scheme以URI的方式传入data
			Uri uri = Uri.parse("qqmap://map/routeplan?type=drive&to=" + name
					+ "&tocoord=" + lat + "," + lon);
			intent.setData(uri);
			context.startActivity(intent);
		} catch (Exception e) {
			ToastUtil.show(context, "您尚未安装腾讯地图或地图版本过低");
			Uri uri = Uri.parse("market://details?id=com.tencent.map");
			Intent TIntent = new Intent(Intent.ACTION_VIEW, uri);
			context.startActivity(TIntent);
			e.printStackTrace();
		}
	}

	public static void toWebGaoDeMap(Context context, double sLat, double sLon, String toLat, String toLon) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		// 驾车导航
		intent.setData(Uri.parse("http://uri.amap.com/navigation?from=" + sLon + "," + sLat + "&to="+ toLon + "," + toLat + "&mode=car&src=nyx_super"));
		context.startActivity(intent); // 启动调用
	}

	/**
	 * 复制内容到剪切板
	 *
	 * @param copyStr
	 * @return
	 */
	public static boolean copy(String copyStr,Context context) {
		try {
			//获取剪贴板管理器
			ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
			// 创建普通字符型ClipData
			ClipData mClipData = ClipData.newPlainText("Label", copyStr);
			// 将ClipData内容放到系统剪贴板里。
			cm.setPrimaryClip(mClipData);
			return true;
		} catch (Exception e) {
			Log.d("复制失败",e.getMessage());
			return false;
		}
	}

	public static String getTimeMill(long time) {
		String timeStr = null;
		long hour = 0;
		long minute = 0;
		long second = 0;
		if (time <= 0)
			return "00:00";
		else {
			minute = time / (1000 * 60);
			if (minute < 60) {
				second = (time % (1000 * 60)) / 1000;

				timeStr = unitFormat(minute) + "分钟" + unitFormat(second) + "秒";
			} else {
				hour = minute / 60;
//				if (hour < 24) {
				minute = minute % 60;
				second = time - hour * 3600 - minute * 60;
				timeStr = unitFormat(hour) + "小时" + unitFormat(minute) + "分" + unitFormat(second) + "秒";
//				} else {
//					int day = hour/24;
//					hour = hour % 24;
//					timeStr = day + "天" + hour + "小时";
//				}

			}
		}
		return timeStr;
	}

	public static String getTimeMillTwo(long time) {
		String timeStr = null;
		long hour = 0;
		long minute = 0;
		long second = 0;
		if (time <= 0)
			return "00:00";
		else {
			minute = time / (1000 * 60);
			if (minute < 60) {
				second = (time % (1000 * 60)) / 1000;

				timeStr = unitFormat(minute) + ":" + unitFormat(second) + "";
			} else {
				hour = minute / 60;
//				if (hour < 24) {
				minute = minute % 60;
				second = time - hour * 3600 - minute * 60;
				timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second) + "";
//				} else {
//					int day = hour/24;
//					hour = hour % 24;
//					timeStr = day + "天" + hour + "小时";
//				}

			}
		}
		return timeStr;
	}

	private static String unitFormat(long i) {
		String retStr = null;
		if (i >= 0 && i < 10)
			retStr = "0" + Long.toString(i);
		else
			retStr = "" + i;
		return retStr;
	}
}

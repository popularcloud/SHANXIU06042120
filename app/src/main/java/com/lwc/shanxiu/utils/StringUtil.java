package com.lwc.shanxiu.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.configs.FileConfig;
import com.lwc.shanxiu.configs.TApplication;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.widget.EditText;

/**
 * 字符串操作工具类
 * 
 * @author 罗文忠
 * @version 1.0
 * @date 2013-7-27
 * 
 */
public class StringUtil {

	/**
	 * 转换成千进制.
	 * 
	 * @param number
	 *        原数.
	 * @return
	 * @version 1.0
	 * @createTime 2014年1月20日,下午5:44:05
	 * @updateTime 2014年1月20日,下午5:44:05
	 * @createAuthor paladin
	 * @updateAuthor paladin
	 * @updateInfo
	 */
	public static String getThousandSystem(String number) {
		DecimalFormat df = new DecimalFormat("###,###,###,###");
		return df.format(Integer.valueOf(number));
	}

	/*
	 * 获取头像地址
	 */
	public static String getUserLocalportraitImagePath(String netPath) {
		if (TextUtils.isEmpty(netPath)) {
			return "";
		} else if (netPath.lastIndexOf("=") == -1) {
			return FileConfig.PATH_USER_IMAGE + (netPath.substring(netPath.lastIndexOf("/") + 1));
		} else {
			return FileConfig.PATH_USER_IMAGE + (netPath.substring(netPath.lastIndexOf("=") + 1)).replace("&", "") + ".png";
		}

	}

	/**
	 * 转换成千进制.
	 * 
	 * @param number
	 *        原数.
	 * @return
	 * @version 1.0
	 * @createTime 2014年1月20日,下午5:44:05
	 * @updateTime 2014年1月20日,下午5:44:05
	 * @createAuthor paladin
	 * @updateAuthor paladin
	 * @updateInfo
	 */
	public static String getThousandSystem(int number) {
		DecimalFormat df = new DecimalFormat("###,###,###,###");
		return df.format(number);
	}

	/**
	 * 生成带颜色字体
	 * 
	 * @version 1.0
	 * @createTime 2013-8-1,下午5:45:05
	 * @updateTime 2013-8-1,下午5:45:05
	 * @createAuthor 罗文忠
	 * @updateAuthor 罗文忠
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param text
	 *        需要处理的文本
	 * @param color
	 *        文本颜色 rgb #ffffff
	 * @return 处理后的html格式文本
	 */
	public static String makeColorText(String text, String color) {

		return "<font color=" + color + ">" + text + "</font>";
	}

	/**
	 * 生成大号字体
	 * 
	 * @version 1.0
	 * @createTime 2013-8-1,下午5:45:05
	 * @updateTime 2013-8-1,下午5:45:05
	 * @createAuthor 罗文忠
	 * @updateAuthor 罗文忠
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param text
	 *        需要处理的文本
	 * @return 处理后的html格式文本
	 */
	public static String makeBigText(String text, int size) {
		String htmlStart = "<font >";
		String htmlEnd = "</font >";
		for (int i = 0; i < size; i++) {
			htmlStart += "<big>";
			htmlEnd = "</big>" + htmlEnd;
		}
		return htmlStart + text + htmlEnd;
	}

	/**
	 * 生成大号带颜色字体
	 * 
	 * @version 1.0
	 * @createTime 2013-8-1,下午5:45:05
	 * @updateTime 2013-8-1,下午5:45:05
	 * @createAuthor 罗文忠
	 * @updateAuthor 罗文忠
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param text
	 *        需要处理的文本
	 * @param color
	 *        文本颜色 rgb #ffffff
	 * @return 处理后的html格式文本
	 */
	public static String makeBigColorText(String text, String color) {

		return "<font color=" + color + "><big>" + text + "</big></font>";
	}

	/**
	 * 根据网络图片路径,获取本地图片路径.
	 * 
	 * @param netPath
	 *        网络图片路径
	 * @author 刘艺谋
	 * @version 1.0, 2013-4-5
	 */
	public static String getLocalImagePath(String netPath) {
		if (TextUtils.isEmpty(netPath)) {
			return "";
		}
		return FileConfig.PATH_IMAGES + netPath.substring(netPath.lastIndexOf("/") + 1);
	}

	/**
	 * 获取本地私有文件路径
	 * 
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param netPath
	 * @return
	 */
	public static String getUserLocalImagePath(String netPath) {
		if (TextUtils.isEmpty(netPath)) {
			return "";
		}
		return FileConfig.PATH_USER_IMAGE + netPath.substring(netPath.lastIndexOf("/") + 1);
	}

	/**
	 * 获取本地私有文件路径
	 * 
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param parent
	 *        父文件夹
	 * @param netPath
	 *        图片网络路径
	 * @return 本地文件存储绝对路径
	 */
	public static String getUserLocalImagePath(String parent, String netPath) {
		if (TextUtils.isEmpty(netPath)) {
			return "";
		}
		return parent + netPath.substring(netPath.lastIndexOf("/") + 1);
	}

	/**
	 * 获取用户缩略图
	 * 
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param netPath
	 * @return
	 */
	public static String getUserLocalThumPath(String netPath) {
		if (TextUtils.isEmpty(netPath)) {
			return "";
		}
		return FileConfig.PATH_USER_THUMBNAIL + netPath.substring(netPath.lastIndexOf("/") + 1);
	}

	/**
	 * 获取网络文件在本地缓存的路径
	 * 
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param netPath
	 *        网络文件路径
	 * @param localParentPath
	 *        本地文件缓存目录
	 * @return 本地缓存文件的绝对路径
	 */
	public static String getLocalCachePath(String netPath, String localParentPath) {
		return localParentPath + netPath.substring(netPath.lastIndexOf("/") + 1);
	}

	/**
	 * 判断参数.
	 * 
	 * @param params
	 *        需要判断的字符串.
	 * @return 若为空则返回空字符串,否则返回原字符串.
	 * @version 1.0
	 * @createTime 2013年11月1日,下午12:01:50
	 * @updateTime 2013年11月1日,下午12:01:50
	 * @createAuthor paladin
	 * @updateAuthor paladin
	 * @updateInfo
	 */
	public static String getParamsForString(String params) {
		if (null == params) {
			return "";
		} else {
			return params;
		}
	}

	/**
	 * 判断参数.
	 * 
	 * @param params
	 *        需要判断的字符串.
	 * @return 若为空则返回0字符串,否则返回原字符串.
	 * @version 1.0
	 * @createTime 2013年11月1日,下午12:01:50
	 * @updateTime 2013年11月1日,下午12:01:50
	 * @createAuthor paladin
	 * @updateAuthor paladin
	 * @updateInfo
	 */
	public static String getParamsForNumber(String params) {
		if (TextUtils.isEmpty(params)) {
			return "0";
		} else {
			return params;
		}
	}

	/**
	 * 格式化文件大小
	 * 
	 * @version 1.0
	 * @createTime 2013-7-27,下午4:06:54
	 * @updateTime 2013-7-27,下午4:06:54
	 * @createAuthor 罗文忠
	 * @updateAuthor 罗文忠
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param size
	 */
	public static String formatFileSize(float size) {
		DecimalFormat format = new DecimalFormat("###,###,##0.00");
		if (size < 1024) {
			format.applyPattern("###,###,##0.00B");
		} else if (size >= 1024 && size < 1024 * 1024) {
			size /= 1024;
			format.applyPattern("###,###,##0.00KB");
		} else if (size >= 1024 * 1024 && size < 1024 * 1024 * 1024) {
			size /= (1024 * 1024);
			format.applyPattern("###,###,##0.00MB");
		} else if (size >= 1024 * 1024 * 1024 && size < 1024 * 1024 * 1024 * 1024) {
			size /= (1024 * 1024 * 1024);
			format.applyPattern("###,###,##0.00GB");
		} else if (size >= 1024 * 1024 * 1024 * 1024 && size < 1024 * 1024 * 1024 * 1024 * 1024) {
			size /= (1024 * 1024 * 1024 * 1024);
			format.applyPattern("###,###,##0.00GB");
		}
		return format.format(size);
	}

	/**
	 * 计算时间
	 * 
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param time
	 *        原始时间(yyyy-MM-dd hh:mm:ss)
	 * @return 处理后的时间
	 */
	public static String caculateTime(String time) {
		if (TextUtils.isEmpty(time)) {
			return "";
		}
		String newTime = "";
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
			long timeMillis = date.getTime();
			long currentTimeMillis = System.currentTimeMillis();
			long caculTime = ((currentTimeMillis - timeMillis) / 1000);
			if (caculTime < 1) {
				newTime = 1 + TApplication.context.getString(R.string.second);
			} else if (caculTime < 60 && caculTime >= 1) { // 秒
				newTime = caculTime + TApplication.context.getString(R.string.second);
			} else if (caculTime >= 60 && caculTime < 60 * 60) { // 分
				caculTime /= 60;
				newTime = caculTime + TApplication.context.getString(R.string.minute);
			} else if (caculTime >= 60 * 60 && caculTime < 60 * 60 * 24) { // 时
				caculTime /= 60 * 60;
				newTime = caculTime + TApplication.context.getString(R.string.hour);
			} else if (caculTime >= 60 * 60 * 24 && caculTime < 60 * 60 * 24 * 4) { // 天
				caculTime /= 60 * 60 * 24;
				newTime = caculTime + TApplication.context.getString(R.string.day);
			} else {
				SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
				newTime = df.format(date);
			}

		} catch (ParseException e) {
			newTime = time;
			e.printStackTrace();
		}

		return newTime;
	}

	/**
	 * 计算时间
	 * 
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param time
	 *        原始时间(yyyy-MM-dd hh:mm:ss)
	 * @return 处理后的时间
	 */
	public static String caculateData(String time) {
		if (TextUtils.isEmpty(time)) {
			return "";
		}
		String newTime = "";
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
			long timeMillis = date.getTime();
			long currentTimeMillis = System.currentTimeMillis();
			long caculTime = ((currentTimeMillis - timeMillis) / 1000);
			if (caculTime < 1) {
				newTime = 1 + TApplication.context.getString(R.string.second);
			} else if (caculTime < 60 && caculTime >= 1) { // 秒
				newTime = caculTime + TApplication.context.getString(R.string.second);
			} else if (caculTime >= 60 && caculTime < 60 * 60) { // 分
				caculTime /= 60;
				newTime = caculTime + TApplication.context.getString(R.string.minute);
			} else if (caculTime >= 60 * 60 && caculTime < 60 * 60 * 24) { // 时
				caculTime /= 60 * 60;
				newTime = caculTime + TApplication.context.getString(R.string.hour);
			} else if (caculTime >= 60 * 60 * 24 && caculTime < 60 * 60 * 24 * 4) { // 天
				caculTime /= 60 * 60 * 24;
				newTime = caculTime + TApplication.context.getString(R.string.day);
			} else {
				SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
				newTime = df.format(date);
			}

		} catch (ParseException e) {
			newTime = time;
			e.printStackTrace();
		}

		return newTime;
	}

	/**
	 * 计算声音时长
	 *
	 * @version 1.0
	 * @createTime 2014年7月22日,下午4:35:30
	 * @updateTime 2014年7月22日,下午4:35:30
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param time
	 *        需要计算的时间长度long格式
	 * @return
	 */
	public static String caculateSoundTime(String time) {
		if (TextUtils.isEmpty(time)) {
			return "";
		}
		String timeLength = "";
		if (Long.parseLong(time) / 1000 / 60 > 0) {
			timeLength += (Long.parseLong(time) / 1000 / 60) + "'";
		}
		timeLength += (Long.parseLong(time) / 1000 % 60) + "\"";

		return timeLength;
	}

	/**
	 * 计算消息发送时间
	 * 
	 * @version 1.0
	 * @createTime 2013-12-6,下午8:41:04
	 * @updateTime 2013-12-6,下午8:41:04
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param date
	 *        目标时间
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String caculateMessageTime(String date) {
		String newTime = "";
		SimpleDateFormat sdf = new SimpleDateFormat();

		// 当前日期
		Date currentDate = new Date(System.currentTimeMillis());
		// 源日期
		Date sourceDate;

		Calendar calendar = Calendar.getInstance();// 获取Calendar实例

		// 当前星期
		int weekPosition = 0;
		// 星期
		String[] weeks = TApplication.context.getResources().getStringArray(R.array.weeks);

		try {
			sourceDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);

			// 当前周在当年的第几周
			calendar.setTime(currentDate);
			int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);

			// 源日期在当年的第几周
			calendar.setTime(sourceDate);
			int sourceWeek = calendar.get(Calendar.WEEK_OF_YEAR);

			// 星期下标
			weekPosition = sourceDate.getDay();

			sdf.applyPattern("yyyy-MM-dd");
			if (sdf.format(currentDate).equals(sdf.format(sourceDate))) {// 当天，显示小时
				sdf.applyPattern("HH:mm");
				newTime = sdf.format(sourceDate);
			} else if (currentWeek == sourceWeek) {// 本周之内
				newTime = weeks[weekPosition];
			} else {// 显示日期
				sdf.applyPattern("yy-MM-dd");
				newTime = sdf.format(sourceDate);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newTime;
	}

	/**
	 * 格式化时间
	 * 
	 * @version 1.0
	 * @createTime 2013-11-18,下午9:06:13
	 * @updateTime 2013-11-18,下午9:06:13
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param time
	 *        原始时间啊， 格式 yyyy-MM-dd hh:mm:ss
	 * @param pattern
	 *        新时间格式
	 * @return
	 */
	public static String formatTime(String time, String pattern) {

		if (TextUtils.isEmpty(time)) {
			return "1970-01-01";
		}
		String newTime = "";
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
			long timeMillis = date.getTime();
			newTime = new SimpleDateFormat(pattern).format(timeMillis);

		} catch (ParseException e) {
			newTime = time;
			e.printStackTrace();
		}

		return newTime;
	}

	/**
	 * 比较时间
	 * 
	 * @version 1.0
	 * @createTime 2013-11-22,下午5:23:41
	 * @updateTime 2013-11-22,下午5:23:41
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param time1
	 *        时间1（格式 yyyy-MM-dd HH:mm:ss）
	 * @param time2
	 *        时间2（格式 yyyy-MM-dd HH:mm:ss）
	 * @return 1(time1 > time2) 、 0(time1 == time2) 、 -1(除1，0的情况，都会返回-1)
	 */
	public static int compareTime(String time1, String time2) {
		int status = -1;
		Date date1;
		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time1);
			Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time2);
			long timeMillis1 = date1.getTime();
			long timeMillis2 = date2.getTime();

			if (timeMillis1 == timeMillis2) {
				status = 0;
			} else if (timeMillis1 > timeMillis2) {
				status = 1;
			} else {
				status = -1;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			status = -1;
		}

		return status;
	}

	/**
	 * 格式化时间
	 * 
	 * @version 1.0
	 * @createTime 2013-11-15,下午11:23:18
	 * @updateTime 2013-11-15,下午11:23:18
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param timeMillis
	 *        时间戳
	 * @param pattern
	 *        时间正则
	 * @return 返回格式后的时间
	 */
	public static String formatTime(long timeMillis, String pattern) {

		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		return sdf.format(new Date(timeMillis));
	}

	/**
	 * 更改时间格式
	 * 
	 * @version 1.0
	 * @createTime 2014年1月3日,下午2:08:33
	 * @updateTime 2014年1月3日,下午2:08:33
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param time
	 *        原时间
	 * @param fromPattern
	 *        原始格式
	 * @param toPattern
	 *        目标格式
	 * @return 格式化之后的
	 */
	@SuppressWarnings("deprecation")
	public static String changeTimeFormat(String time, String fromPattern, String toPattern, boolean isMonth) {

		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(toPattern);
		try {
			date = new SimpleDateFormat(fromPattern).parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (isMonth) {
			if (SystemUtil.IsSystemLanguage()) {
				return sdf.format(date);
			} else {
				if (date.getMonth() + 1 == 1) {
					return "Jan";
				} else if (date.getMonth() + 1 == 2) {
					return "Feb";
				} else if (date.getMonth() + 1 == 3) {
					return "Mar";
				} else if (date.getMonth() + 1 == 4) {
					return "Apr";
				} else if (date.getMonth() + 1 == 5) {
					return "May";
				} else if (date.getMonth() + 1 == 6) {
					return "Jun";
				} else if (date.getMonth() + 1 == 7) {
					return "Jul";
				} else if (date.getMonth() + 1 == 8) {
					return "Aug";
				} else if (date.getMonth() + 1 == 9) {
					return "Sep";
				} else if (date.getMonth() + 1 == 10) {
					return "Oct";
				} else if (date.getMonth() + 1 == 11) {
					return "Nov";
				} else if (date.getMonth() + 1 == 12) {
					return "Dec";
				} else {
					return sdf.format(date);
				}
			}
		} else {
			return sdf.format(date);
		}
	}

	/**
	 * 获取指定时间格式的时间戳
	 * 
	 * @version 1.0
	 * @createTime 2014年1月3日,下午1:55:49
	 * @updateTime 2014年1月3日,下午1:55:49
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param time
	 *        时间
	 * @param pattern
	 *        格式
	 * @return
	 */
	public static long getTimeMillis(String time, String pattern) {

		Date date = null;
		try {
			date = new SimpleDateFormat(pattern).parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return date.getTime();
	}

	/**
	 * 判断字符串是否是标准时间格式
	 * 
	 * @version 1.0
	 * @createTime 2014-3-25,下午7:46:25
	 * @updateTime 2014-3-25,下午7:46:25
	 * @createAuthor liujingguo
	 * @updateAuthor liujingguo
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param time
	 *        判断的字符串
	 * @param fromPattern
	 *        时间格式
	 * @return 是返回true，不是返回false
	 */
	public static boolean valueTime(String time, String fromPattern) {

		try {
			new SimpleDateFormat(fromPattern).parse(time);
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 描述：获取EditText控件所输入的文字 并去除空字符
	 * 
	 * @version 1.0
	 * @createTime 2014-4-21 上午10:50:04
	 * @updateTime 2014-4-21 上午10:50:04
	 * @createAuthor XinGo
	 * @updateAuthor
	 * @param editTextInput
	 *        要获取文本内容的edittext控件
	 * @updateInfo (修改内容描述)
	 */
	public static String trimEditTextInput(EditText editTextInput) {
		return editTextInput.getText().toString().trim();
	}

	/**
	 * 字符串转换整形,
	 *
	 * @version 1.0
	 * @createTime 2014年9月2日,下午6:16:12
	 * @updateTime 2014年9月2日,下午6:16:12
	 * @createAuthor WangYuWen
	 * @updateAuthor WangYuWen
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param str
	 *        字符串
	 * @return 空返回0
	 */
	public static int StringToInt(String str) {
		if (TextUtils.isEmpty(str)) {
			return 0;
		} else {
			return Integer.parseInt(str);
		}
	}

	/**
	 * 获取手机的mac地址
	 *
	 * @version 1.0
	 * @createTime 2014年11月13日,下午9:29:34
	 * @updateTime 2014年11月13日,下午9:29:34
	 * @createAuthor WangYuWen
	 * @updateAuthor WangYuWen
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param context
	 * @return
	 */
	public static String getMacAddress(Context context) {
		// 获取mac地址：
		String macAddress = "000000000000";
		try {
			WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
			if (null != info) {
				if (!TextUtils.isEmpty(info.getMacAddress()))
					macAddress = info.getMacAddress().replace(":", "");
				else
					return macAddress;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return macAddress;
		}
		return macAddress;
	}
}

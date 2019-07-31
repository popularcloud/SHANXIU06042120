package com.lwc.shanxiu.utils;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.configs.TApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	/** 秒 */
	private static String seconds = TApplication.context.getString(R.string.second);
	/** 分 */
	private static String minutes = TApplication.context.getString(R.string.minute);
	/** 时 */
	private static String hours = TApplication.context.getString(R.string.hour);
	/** 刚刚 */
	private static String before = TApplication.context.getString(R.string.befor);

	/**
	 * 获取系统当前时间
	 * 
	 * @version 1.0
	 * @createTime 2013-10-24,上午9:58:21
	 * @updateTime 2013-10-24,上午9:58:21
	 * @createAuthor liujingguo
	 * @updateAuthor liujingguo
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return 时间字符串
	 */
	public static String getDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取发送时间
	 * 
	 * @updateInfo
	 * @return
	 */
	public static String getSendDate(Long time) {
		Date currentTime = new Date(time);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取当前时间点.
	 * 
	 * @return 当前时间.
	 * @version 1.0
	 * @createTime 2013年11月25日,下午5:44:39
	 * @updateTime 2013年11月25日,下午5:44:39
	 * @createAuthor paladin
	 * @updateAuthor paladin
	 * @updateInfo
	 */
	public static String getTime() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		String time = formatter.format(currentTime);
		return time;
	}


	/**
	 * 获取当前时间戳.
	 * 
	 * @return 当前时间的时间戳.
	 * @version 1.0
	 * @createTime 2014年5月14日,下午3:50:15
	 * @updateTime 2014年5月14日,下午3:50:15
	 * @createAuthor paladin
	 * @updateAuthor paladin
	 * @updateInfo
	 */
	public static long getCurrentTime() {
//		Long time = Long.valueOf(TApplication.CONNECT_DATE);
//		if (Math.abs(time - System.currentTimeMillis()) >= 5 * 100) {
//			return TApplication.CONNECT_DATE;
//		} else {
			return System.currentTimeMillis();
//		}
	}

	/**
	 * 获取当前时间(按指定格式).
	 * 
	 * @param pattern
	 *        时间格式.
	 * @return 当前时间(按指定格式).
	 * @version 1.0
	 * @createTime 2014年5月14日,下午3:54:59
	 * @updateTime 2014年5月14日,下午3:54:59
	 * @createAuthor paladin
	 * @updateAuthor paladin
	 * @updateInfo
	 */
	public static String getCurrentTime(String pattern) {
		return timestampToPatternTime(getCurrentTime(), pattern);
	}

	/**
	 * 将时间戳转换为指定时间(按指定格式).
	 * 
	 * @param timestamp
	 *        时间戳.
	 * @param pattern
	 *        时间格式.
	 * @return 指定时间(按指定格式).
	 * @version 1.0
	 * @createTime 2014年5月14日,下午3:58:22
	 * @updateTime 2014年5月14日,下午3:58:22
	 * @createAuthor paladin
	 * @updateAuthor paladin
	 * @updateInfo
	 */
	public static String timestampToPatternTime(long timestamp, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date(timestamp));
	}

	/**
	 * 将指定格式的时间转换为时间戳.
	 * 
	 * @param time
	 *        时间(按指定格式).
	 * @param pattern
	 *        时间格式.
	 * @return 指定时间的时间戳.
	 * @throws ParseException
	 *         当传入的时间与时间格式不匹配或传入不能识别的时间格式时抛出异常.
	 * @version 1.0
	 * @createTime 2014年5月14日,下午4:04:18
	 * @updateTime 2014年5月14日,下午4:04:18
	 * @createAuthor paladin
	 * @updateAuthor paladin
	 * @updateInfo
	 */
	public static long patternTimeToTimestamp(String time, String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = sdf.parse(time);
		return date.getTime();
	}

	/**
	 * 计算距离当前时间.
	 * 
	 * @param timestamp
	 *        当时时间戳.
	 * @return 若距离当前时间小于1分钟则返回xx秒之前，若距离当前时间小于1小时则返回xx分钟之前，若距离当前时间小于1天则返回xx小时前， 若时间为今年则返回MM-dd，否则返回yyyy-MM-dd.
	 * @version 1.0
	 * @createTime 2014年6月20日,上午10:36:55
	 * @updateTime 2014年6月20日,上午10:36:55
	 * @createAuthor paladin
	 * @updateAuthor paladin
	 * @updateInfo
	 */
	public static String fromTheCurrentTime(long timestamp) {
		if (timestamp < 1000000000000l) {
			timestamp = timestamp * 1000;
		}
		long timeDifference = (getCurrentTime() - timestamp) / 1000;
		if (timeDifference < 0) {
			return before;
		} else if (timeDifference < 60) {
			return String.valueOf(timeDifference) + seconds;
		} else if (timeDifference / 60 < 60) {
			return String.valueOf(timeDifference / 60) + minutes;
		} else if (timeDifference / 3600 < 24) {
			return String.valueOf(timeDifference / 3600) + hours;
		} else if (timestampToPatternTime(timestamp, "yyyy").equals(getCurrentTime("yyyy"))) {
			return timestampToPatternTime(timestamp, "MM-dd");
		} else {
			return timestampToPatternTime(timestamp, "yyyy-MM-dd");
		}
	}

	/**
	 * 计算距离当前时间.
	 * 
	 * @param time
	 *        当时时间
	 * @param pattern
	 *        时间格式.
	 * @return 若距离当前时间小于1分钟则返回xx秒之前，若距离当前时间小于1小时则返回xx分钟之前，若距离当前时间小于1天则返回xx小时前， 若时间为今年则返回MM-dd，否则返回yyyy-MM-dd.若传入的时间与时间格式不匹配则返回null.
	 * @version 1.0
	 * @createTime 2014年6月20日,上午10:39:37
	 * @updateTime 2014年6月20日,上午10:39:37
	 * @createAuthor paladin
	 * @updateAuthor paladin
	 * @updateInfo
	 */
	public static String fromTheCurrentTime(String time, String pattern) {
		try {
			return fromTheCurrentTime(patternTimeToTimestamp(time, pattern));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @return 该秒数转换为 * days * hours * minutes * seconds 后的格式
	 * @author fy.zhang
	 */
	public static String formatDuring(long mss) {
		long days = mss / (60 * 60 * 24);
		long hours = (mss % (60 * 60 * 24)) / (60 * 60);
		long minutes = (mss % (60 * 60)) / 60;
		long seconds = mss % 60;
		return days + " 天 " + hours + " 小时 " + minutes + " 分 ";
	}

}

package com.lwc.shanxiu.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lwc.shanxiu.configs.FileConfig;

public class LogUtil {

	/** 是否打印XMPP日志 */
	public static final boolean IS_SHOW_XMPP_LOG = false;

	/** 是否显示日志 */
	public static final boolean IS＿SHOW_LOG = true;

	/**
	 * 写入错误日志.
	 *
	 * @param ex
	 *        异常信息.
	 * @version 1.0
	 * @createTime 2013-10-21,下午8:17:51
	 * @updateTime 2013-10-21,下午8:17:51
	 * @createAuthor paladin
	 * @updateAuthor paladin
	 * @updateInfo
	 */
	public static void writeError(Throwable ex) {
		try {
			SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy_MM_dd");
			SimpleDateFormat momentFormat = new SimpleDateFormat("HH:mm:ss");
			Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
			String day = dayFormat.format(curDate);
			String moment = momentFormat.format(curDate);
			StringBuffer timeDivider = new StringBuffer();
			StringBuffer overDivider = new StringBuffer();
			for (int i = 0; i < 20; i++) {
				timeDivider.append("-");
				overDivider.append("=");
			}
			StringBuffer sb = new StringBuffer();
			StackTraceElement[] element = ex.getCause().getStackTrace();
			sb.append(moment + "\n");
			sb.append(timeDivider.toString() + "\n");
			sb.append(ex.getMessage() + "\n");
			for (int i = 0; i < element.length; i++) {
				sb.append(element[i].toString() + "\n");
			}
			sb.append(overDivider.toString() + "\n");
			File file = new File(FileConfig.PATH_LOG + "err" + day);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream ops = new FileOutputStream(file, true);
			err(sb); // 控制台输出错误日志
			ops.write(sb.toString().getBytes());
			ops.flush();
			ops.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 错误日志
	 *
	 * @version 1.0
	 * @createTime 2013-11-18,上午9:01:59
	 * @updateTime 2013-11-18,上午9:01:59
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param log
	 */
	public static void err(Object log) {
		StackTraceElement ste = new Throwable().getStackTrace()[1];
		if (IS＿SHOW_LOG) { // 日志输出开关
			System.err.println(ste.getClassName() + "." + ste.getMethodName() + "()==>" + log);
		}
	}

	/**
	 * 日志输出
	 *
	 * @version 1.0
	 * @createTime 2013-11-9,下午3:21:54
	 * @updateTime 2013-11-9,下午3:21:54
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param log
	 *        需要输出的日志信息
	 */
	public static void out(Object log) {
		StackTraceElement ste = new Throwable().getStackTrace()[1];
		if (IS＿SHOW_LOG) { // 日志输出开关
			System.out.println(ste.getClassName() + "." + ste.getMethodName() + "()==>" + log);
		}
	}

	/**
	 * 日志输出
	 *
	 * @version 1.0
	 * @createTime 2013-11-9,下午3:26:23
	 * @updateTime 2013-11-9,下午3:26:23
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param log
	 *        需要输出的日志
	 * @param isShow
	 *        是否显示日志
	 */
	public static void out(Object log, boolean isShow) {
		StackTraceElement ste = new Throwable().getStackTrace()[1];
		if (isShow && IS＿SHOW_LOG) {
			// 日志输出开关
			System.out.println(ste.getClassName() + "." + ste.getMethodName() + "()==>" + log);
		}
	}

	/**
	 * 日志输出
	 *
	 * @version 1.0
	 * @createTime 2013-11-9,下午3:26:23
	 * @updateTime 2013-11-9,下午3:26:23
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 *
	 * @param tag
	 *        日志标记
	 * @param log
	 *        需要输出的日志
	 */
	public static void out(Object tag, Object log) {
		StackTraceElement ste = new Throwable().getStackTrace()[1];
		if (IS＿SHOW_LOG) { // 日志输出开关
			System.out.println(ste.getClassName() + "." + ste.getMethodName() + "(" + tag + ")==>" + log);
		}
	}

	/**
	 * 日志输出
	 *
	 * @version 1.0
	 * @createTime 2013-11-9,下午3:26:23
	 * @updateTime 2013-11-9,下午3:26:23
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 *
	 * @param tag
	 *        输出日志的标记
	 * @param log
	 *        需要输出的日志
	 * @param isShow
	 *        是否显示日志
	 */
	public static void out(Object tag, Object log, boolean isShow) {
		StackTraceElement ste = new Throwable().getStackTrace()[1];
		if (isShow && IS＿SHOW_LOG) { // 日志输出开关
			System.out.println(ste.getClassName() + "." + ste.getMethodName() + "(" + tag + ")==>" + log);
		}
	}

	public static void outThrowable(Object log) {
		if (IS＿SHOW_LOG) { // 日志输出开关
			System.out.println(log);
			System.out.println(toStackTraceString(new Throwable()));
		}
	}

	/**
	 *
	 * 描述：构造异常的输出信息
	 *
	 * createTime 2014-3-25 下午4:39:16 createAuthor 健兴
	 *
	 * updateTime 2014-3-25 下午4:39:16 updateAuthor 健兴 updateInfo
	 *
	 * @param throwable
	 * @return
	 */
	public static String toStackTraceString(Throwable throwable) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(throwable.toString() + "\n");

		StackTraceElement[] stack = throwable.getStackTrace();
		for (StackTraceElement element : stack) {
			stringBuffer.append("\tat " + element + "\n");
		}

		StackTraceElement[] parentStack = stack;
		while (throwable != null) {
			stringBuffer.append("Caused by: " + "\n");
			stringBuffer.append(throwable + "\n");
			StackTraceElement[] currentStack = throwable.getStackTrace();
			int duplicates = countDuplicates(currentStack, parentStack);
			for (int i = 0; i < currentStack.length - duplicates; i++) {
				stringBuffer.append("\tat " + currentStack[i] + "\n");
			}
			if (duplicates > 0) {
				stringBuffer.append("\t... " + duplicates + " more" + "\n");
			}
			parentStack = currentStack;
			throwable = throwable.getCause();
		}
		return stringBuffer.toString();
	}

	/**
	 *
	 * 描述：异常记录数目
	 *
	 * createTime 2014-3-25 下午4:37:55 createAuthor 健兴
	 *
	 * updateTime 2014-3-25 下午4:37:55 updateAuthor 健兴 updateInfo
	 *
	 * @param currentStack
	 * @param parentStack
	 * @return
	 */
	private static int countDuplicates(StackTraceElement[] currentStack, StackTraceElement[] parentStack) {
		int duplicates = 0;
		int parentIndex = parentStack.length;
		for (int i = currentStack.length; --i >= 0 && --parentIndex >= 0;) {
			StackTraceElement parentFrame = parentStack[parentIndex];
			if (parentFrame.equals(currentStack[i])) {
				duplicates++;
			} else {
				break;
			}
		}
		return duplicates;
	}

}

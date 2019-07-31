package com.lwc.shanxiu.utils;

import com.lwc.shanxiu.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/**
 * Intent跳转工具类
 *
 * @Description 用于简化页面跳转搞得重复代码
 * @version 1.0
 * @date 2014年12月31日
 * @Copyright: Copyright (c) 2014 Shenzhen Utoow Technology Co., Ltd. All rights reserved.
 *
 */
public class IntentUtil {

	/**
	 * 普通的方式打开一个Activiy
	 *
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param context
	 *        上下文
	 * @param gotoClass
	 *        需要打开的Activity
	 */
	public static void gotoActivity(Context context, Class<?> gotoClass) {
		Intent intent = new Intent();
		intent.setClass(context, gotoClass);
		context.startActivity(intent);
		((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
	}
	/**
	 * 打开一个Activity并关闭当前页面
	 *
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param context
	 *        上下文
	 * @param gotoClass
	 *        需要打开的Activity
	 */
	public static void gotoActivityToTopAndFinish(Context context, Class<?> gotoClass) {
		Intent intent = new Intent();
		intent.setClass(context, gotoClass);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
		((Activity) context).finish();
		((Activity) context).overridePendingTransition(0, R.anim.enter_exit_loading);
	}

	/**
	 * 用单例模式打开一个Activity并关闭当前页面，可携带数据
	 *
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param context
	 *        上下文
	 * @param gotoClass
	 *        需要跳转的页面
	 * @param bundle
	 *        携带的数据
	 */
	public static void gotoActivityToTopAndFinish(Context context, Class<?> gotoClass, Bundle bundle) {
		Intent intent = new Intent();
		intent.putExtras(bundle);
		intent.setClass(context, gotoClass);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
		((Activity) context).finish();
		((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
	}

	/**
	 * 普通的方式打开一个activity，并携带数据
	 *
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param context
	 *        上下文
	 * @param gotoClass
	 *        需要打开的Activity
	 * @param bundle
	 *        携带的数据
	 */
	public static void gotoActivity(Context context, Class<?> gotoClass, Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(context, gotoClass);
		intent.putExtras(bundle);
		context.startActivity(intent);
		((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
	}

	/**
	 * 用Result的方式跳转到指定页面，不携带数据
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param context
	 *        上下文
	 * @param gotoClass
	 *        要跳转的Activity
	 * @param requestCode
	 *        页面跳转请求码
	 */
	public static void gotoActivityForResult(Context context, Class<?> gotoClass, int requestCode) {
		Intent intent = new Intent();
		intent.setClass(context, gotoClass);
		((Activity) context).startActivityForResult(intent, requestCode);
		((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
	}

	/**
	 * 用Result的形式跳转到指定页面，并携带数据
	 *
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param context
	 *        上下文
	 * @param gotoClass
	 *        要跳转的页面
	 * @param bundle
	 *        携带的数据
	 * @param requestCode
	 *        跳转搞到页面的请求码
	 */
	public static void gotoActivityForResult(Context context, Class<?> gotoClass, Bundle bundle, int requestCode) {
		Intent intent = new Intent();
		intent.setClass(context, gotoClass);
		intent.putExtras(bundle);
		((Activity) context).startActivityForResult(intent, requestCode);
		((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
	}

	/**
	 * 跳转至指定activity,并关闭当前activity.
	 *
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param context
	 *        上下文
	 * @param gotoClass
	 *        需要跳转的Activity界面
	 */
	public static void gotoActivityAndFinish(Context context, Class<?> gotoClass) {
		Intent intent = new Intent();
		intent.setClass(context, gotoClass);
		context.startActivity(intent);
		((Activity) context).finish();
		((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
	}

	/**
	 * 携带传递数据跳转至指定activity,并关闭当前activity.
	 *
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param context
	 *        上下文
	 * @param gotoClass
	 *        需要跳转的页面
	 * @param bundle
	 *        附带数据
	 */
	public static void gotoActivityAndFinish(Context context, Class<?> gotoClass, Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(context, gotoClass);
		intent.putExtras(bundle);
		context.startActivity(intent);
		((Activity) context).finish();
		((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
	}

	/**
	 * 跳转至指定activity,不关闭当前页面
	 *
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param context
	 *        上下文
	 * @param gotoClass
	 *        需要跳转的界面Activity
	 */
	public static void gotoActivityToTop(Context context, Class<?> gotoClass) {
		Intent intent = new Intent();
		intent.setClass(context, gotoClass);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
		((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
	}

	/**
	 * 携带传递数据跳转至指定activity,不关闭当前activity.
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param context
	 *        上下文
	 * @param gotoClass
	 *        跳转的activity
	 * @param bundle
	 *        附带的数据
	 */
	public static void gotoActivityToTop(Context context, Class<?> gotoClass, Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(context, gotoClass);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtras(bundle);
		context.startActivity(intent);
		((Activity) context).overridePendingTransition(R.anim.enter_exit, R.anim.enter_enter);
	}

	/**
	 * 跳转到发送短信界面
	 *
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param context
	 *        上下文
	 * @param phoneNum
	 *        手机号码
	 * @param content
	 *        短信内容
	 */
	public static void gotoSendMsmActivity(Context context, String phoneNum, String content) {
		Uri uri = Uri.parse("smsto:" + phoneNum);
		Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
		intent.putExtra("sms_body", content);
		context.startActivity(intent);
	}

	/**
	 * 关闭某个activity
	 *
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param activity
	 *        需要关闭的activity对象
	 */
	public static void finish(Activity activity) {
		activity.finish();
		activity.overridePendingTransition(R.anim.exit_enter, R.anim.exit_exit);
	}
}

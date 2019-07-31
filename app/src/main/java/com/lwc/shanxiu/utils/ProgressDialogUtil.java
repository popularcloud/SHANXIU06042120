package com.lwc.shanxiu.utils;

import com.lwc.shanxiu.utils.Cancel.RunnableTask;
import com.lwc.shanxiu.widget.CustomDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.util.Log;

/**
 * ProgressBar操作工具类
 * 
 * @author 罗文忠
 * @date 2013-03-19
 * @version 1.0.0
 * 
 */
public class ProgressDialogUtil {
	public static String TAG = "showDialog1";

	private static CustomDialog progressDialog;

	/**
	 * 
	 * 描述：兼容以前的版本的showDialog方法
	 * 
	 * createTime 2014-3-17 上午11:58:14 createAuthor kpxingxing
	 * 
	 * updateTime 2014-3-17 上午11:58:14 updateAuthor kpxingxing updateInfo
	 * 
	 * @param context
	 * @param text
	 */
	public static void showDialog(Context context, String text) {
		showDialog(context, text, (Thread) null);
	}

	/**
	 * 显示等待对话框
	 * 
	 * @author 罗文忠
	 * @version 1.0
	 * @date 2013-03-20
	 * @param text
	 *        提示内容
	 * 
	 * @updateDate 2014-03-14
	 * @updateAuthor kpxingxing
	 * @updateInfo 添加thread参数用于停止线程的运行
	 * 
	 */
	public static void showDialog(Context context, String text, final Thread thread) {
		progressDialog = new CustomDialog(context);
		progressDialog.setCancelable(true);
		progressDialog.setProgress(text);
		progressDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				if (thread != null) {
					Log.d(TAG, "thread.interrupt()");
					thread.interrupt();
				}
			}
		});
		progressDialog.show();
		if (thread != null) {
			thread.start();
		}
	}

	/**
	 * 
	 * 描述：显示等待对话框
	 * 
	 * createTime 2014-3-17 上午11:37:42 createAuthor kpxingxing
	 * 
	 * updateTime 2014-3-17 上午11:37:42 updateAuthor kpxingxing updateInfo
	 * 
	 * @param context
	 * @param text
	 * @param task
	 *        进行操作的任务RunnableTask
	 */
	public static void showDialog(Context context, String text, final RunnableTask task) {
		progressDialog = new CustomDialog(context);
		progressDialog.setCancelable(true);
		progressDialog.setProgress(text);
		progressDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				// progressDialog = null;
				if (task != null) {
					Log.d(TAG, "thread.interrupt()");
					task.interrupt();
				}
			}
		});
		progressDialog.show();
		if (task != null) {
			DataBaseOperQueue.addTask(task);
		}
	}

	/**
	 * 关闭等待对话框
	 * 
	 * @author 罗文忠
	 * @version 1.0
	 * @date 2013-03-20
	 */
	public static void dismissDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

}

package com.lwc.shanxiu.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import com.lwc.shanxiu.configs.TApplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;

/**
 * apk工具类
 * 
 * @Description TODO
 * @author CodeApe
 * @version 1.0
 * @date 2013-11-18
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 * 
 */
public class ApkUtil {

	private static int lastProgress;
	private static ProgressDialog pd;
	private static File apkFile;
	/**
	 * 检测应用是否已经安装
	 * 
	 * @version 1.0
	 * @createTime 2013-11-18,上午12:41:45
	 * @updateTime 2013-11-18,上午12:41:45
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param packageName
	 *        路径包名
	 * @return true 已经安装 ， false 未安装
	 */
	public static boolean checkPackageInstall(String packageName) {
		PackageInfo packageInfo;
		try {
			packageInfo = TApplication.context.getPackageManager().getPackageInfo(packageName, 0);
		} catch (NameNotFoundException e) {
			packageInfo = null;
			e.printStackTrace();
		}
		if (packageInfo == null) {
			return false;
		} else {
			return true;
		}
	}

	public static void downloadAPK(final Activity activity, final String path) {
		pd = new ProgressDialog(activity, AlertDialog.THEME_HOLO_LIGHT);
		pd.setCancelable(false);
		pd.setMessage("正在下载最新版APP，请稍后...");
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.show();
		pd.setProgress(0);
		new Thread() {
			public void run() {
				try {
					InputStream input = null;
					HttpURLConnection urlConn = null;
					URL url = new URL(path);
					urlConn = (HttpURLConnection) url.openConnection();
					urlConn.setRequestProperty("Accept-Encoding", "identity");
					urlConn.setReadTimeout(10000);
					urlConn.setConnectTimeout(10000);
					input = urlConn.getInputStream();
					int total = urlConn.getContentLength();
					File sd = Environment.getExternalStorageDirectory();
					boolean can_write = sd.canWrite();
					if (!can_write) {
						ToastUtil.showLongToast(activity, "SD卡不可读写");
					} else {
//                        File file = null;
						OutputStream output = null;
						String savedFilePath = sd.getAbsolutePath()+"/shanxiu.apk";
						apkFile = new File(savedFilePath);
						output = new FileOutputStream(apkFile);
						byte[] buffer = new byte[1024];
						int temp = 0;
						int read = 0;
						while ((temp = input.read(buffer)) != -1) {
							output.write(buffer, 0, temp);
							read += temp;
							float progress = ((float) read) / total;
							int progress_int = (int) (progress * 100);
							if (lastProgress != progress_int) {
								lastProgress = progress_int;
								final int pro = progress_int;
								activity.runOnUiThread(new Runnable() {

									@Override
									public void run() {
										pd.setProgress(pro);
									}
								});
							}
						}
						output.flush();
						output.close();
						input.close();
						activity.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								if (pd != null && pd.isShowing()) {
									pd.setProgress(100);
									pd.dismiss();
								}
							}
						});
						installApk(apkFile,activity);
					}
				} catch (Exception e) {
					e.printStackTrace();
					Log.d("联网成功!","安装失败"+e.getMessage());
				}
			}
		}.start();
	}

	/**
	 * 安装apk
	 *
	 * @version 1.0
	 * @createTime 2013-11-18,上午1:43:05
	 * @updateTime 2013-11-18,上午1:43:05
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param file apk文件
	 */
	public static void installApk(File file,Context context) {

		Intent intent = new Intent(Intent.ACTION_VIEW);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			//Android N 写法
			intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			Uri contentUri = FileProvider.getUriForFile(context, "com.lwc.shanxiu.fileProvider", file);
			intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
		} else {
			// Android N之前的老版本写法
			intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}
		TApplication.context.startActivity(intent);
		//	android.os.Process.killProcess(android.os.Process.myPid());


	/*	Uri fileUri =null;
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
			fileUri=FileProvider.getUriForFile(context,
					context.getPackageName()+"com.lwc.common.FileProvider",file);
		}else{
			fileUri=Uri.fromFile(file);
		}
		Intent installIntent=new Intent(Intent.ACTION_VIEW);installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		installIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);installIntent.setAction(Intent.ACTION_VIEW);installIntent.setDataAndType(fileUri,
				"application/vnd.android.package-archive");
		context.startActivity(installIntent);*/

	/*	Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		//application/vnd.android.package-archive 对应的MIME 是 .apk
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		TApplication.context.startActivity(intent);
		android.os.Process.killProcess(android.os.Process.myPid());*/
	}

	public static String sHA1(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), PackageManager.GET_SIGNATURES);
			byte[] cert = info.signatures[0].toByteArray();
			MessageDigest md = null;
			md = MessageDigest.getInstance("SHA1");
			byte[] publicKey = md.digest(cert);
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < publicKey.length; i++) {
				String appendString = Integer.toHexString(0xFF & publicKey[i])
						.toUpperCase(Locale.US);
				if (appendString.length() == 1)
					hexString.append("0");
				hexString.append(appendString);
				hexString.append(":");
			}
			String result = hexString.toString();
			return result.substring(0, result.length()-1);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

}

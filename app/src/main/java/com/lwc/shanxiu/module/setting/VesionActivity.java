package com.lwc.shanxiu.module.setting;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.bean.Update;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.utils.ApkUtil;
import com.lwc.shanxiu.utils.DialogUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.SystemUtil;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.utils.VersionUpdataUtil;
import com.lwc.shanxiu.widget.CustomDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 版本信息界面
 * 
 * @Description TODO
 * @author 何栋
 * @date 2016年4月24日
 * @Copyright: lwc
 */
public class VesionActivity extends BaseActivity {

	private Update update;
	private TextView tvNew;
	private ImageView iv_red;
	private ProgressDialog pd;

	@Override
	protected int getContentViewId(Bundle savedInstanceState) {
		return R.layout.activity_vesion_info;
	}

	@Override
	protected void findViews() {
		setTitle("版本信息");
		showBack();
		((TextView) findViewById(R.id.txt_vesion_info)).setText("MX  " + SystemUtil.getCurrentVersionName());
		tvNew = (TextView) findViewById(R.id.tv_new);
		iv_red = (ImageView) findViewById(R.id.iv_red);
		tvNew.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (update != null && !TextUtils.isEmpty(update.getUrl()) && !TextUtils.isEmpty(update.getVersionCode()) && VersionUpdataUtil.isNeedUpdate(VesionActivity.this, Integer.parseInt(update.getVersionCode()))) {
					DialogUtil.showMessageDg(VesionActivity.this, "版本更新", "立即更新", "稍后再说", update.getMessage(), new CustomDialog.OnClickListener() {
						@Override
						public void onClick(CustomDialog dialog, int id, Object object) {

							if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
								if(getPackageManager().canRequestPackageInstalls()){
									downloadAPK(update.getUrl());
									dialog.dismiss();
								}else{
									dialog.dismiss();
									DialogUtil.showMessageUp(VesionActivity.this, "授予安装权限", "立即设置", "取消", "检测到您没有授予安装应用的权限，请在设置页面授予", new CustomDialog.OnClickListener() {
										@Override
										public void onClick(CustomDialog dialog, int id, Object object) {
											Uri uri = Uri.parse("package:"+getPackageName());
											Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,uri);
											startActivityForResult(intent, 19900);
											dialog.dismiss();
										}
									}, null);
								}
							}else{
								downloadAPK(update.getUrl());
								dialog.dismiss();
							}
						}
					}, null);
				}
			}
		});
	}

	private int lastProgress;
	private void downloadAPK(final String path) {
		pd = new ProgressDialog(this, AlertDialog.THEME_HOLO_LIGHT);
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
						ToastUtil.showLongToast(VesionActivity.this, "SD卡不可读写");
					} else {
						File file = null;
						OutputStream output = null;
						String savedFilePath = sd.getAbsolutePath()+"/shanxiu.apk";
						file = new File(savedFilePath);
						output = new FileOutputStream(file);
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
								runOnUiThread(new Runnable() {

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
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								if (pd != null && pd.isShowing()) {
									pd.setProgress(100);
									pd.dismiss();
								}
							}
						});
						ApkUtil.installApk(file,VesionActivity.this);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		getVersionInfor();
	}

	/**
	 * 获取升级接口版本信息
	 */
	private void getVersionInfor() {
		Map<String,String> params = new HashMap<>();
		params.put("clientType","maintenance");
		HttpRequestUtils.httpRequest(this, "getVersionInfo", RequestValue.UPDATE_APP, params, "GET", new HttpRequestUtils.ResponseListener() {
			@Override
			public void getResponseData(String response) {
				Common common = JsonUtil.parserGsonToObject(response, Common.class);
				switch (common.getStatus()) {
					case "1":
						update = JsonUtil.parserGsonToObject(JsonUtil.getGsonValueByKey(response, "data"), Update.class);
						if (update != null && !TextUtils.isEmpty(update.getVersionCode())) {
							int version = Integer.valueOf(update.getVersionCode());
							//需要更新
							if (version > SystemUtil.getCurrentVersionCode()  && update.getIsValid().equals("1")) {
								iv_red.setVisibility(View.VISIBLE);
								tvNew.setText("发现新版本"+update.getVersionName());
							} else {
								iv_red.setVisibility(View.GONE);
								tvNew.setText("暂无新版本");
							}
						}
						break;
					default:
						break;
				}
			}

			@Override
			public void returnException(Exception e, String msg) {
			}
		});
	}

	@Override
	protected void initGetData() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void widgetListener() {
		// TODO Auto-generated method stub
	}

}

package com.lwc.shanxiu.module.user;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.AndroidJSInterface;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.activity.MainActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.configs.ServerConfig;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.PhotoToken;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.module.message.ui.PublishActivity;
import com.lwc.shanxiu.module.message.ui.PublishAndRequestListActivity;
import com.lwc.shanxiu.module.order.ui.activity.FeeStandardActivity;
import com.lwc.shanxiu.module.order.ui.activity.QuoteAffirmActivity;
import com.lwc.shanxiu.module.setting.ShareActivity;
import com.lwc.shanxiu.utils.DecodeUtils;
import com.lwc.shanxiu.utils.ExecutorServiceUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.QiniuUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.wxapi.WXContants;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 用户协议
 *
 * @author 何栋
 * @date 2017年11月29日
 */
public class UserAgreementActivity extends BaseActivity {

	@BindView(R.id.progressBar1)
	ProgressBar progressbar;
	@BindView(R.id.webView1)
	WebView webview;
	@BindView(R.id.tv_agree)
	TextView tv_agree;
	@BindView(R.id.tv_noAgree)
	TextView tv_noAgree;
	private int oldProgress;
	private FullscreenHolder fullscreenContainer;
	private View customView;
	private WebChromeClient.CustomViewCallback customViewCallback;
	private UMShareAPI uMShareAPI;
	public static UserAgreementActivity activity;
	private SharedPreferencesUtils preferencesUtils;
	private User user;
	Map<String,String> params = new HashMap<>();
	private PhotoToken token;
	private ProgressDialog pd;
	private String imgPath1;
	private List<String> urlStrs = new ArrayList();

	@Override
	protected int getContentViewId(Bundle savedInstanceState) {
		return R.layout.activity_user_agreement;
	}

	@Override
	protected void findViews() {
		activity = this;
		preferencesUtils = SharedPreferencesUtils.getInstance(this);
		user = preferencesUtils.loadObjectData(User.class);
		ButterKnife.bind(this);
		uMShareAPI = UMShareAPI.get(this);
	}

	@Override
	protected void init() {
		String url = getIntent().getStringExtra("url");
		String title = getIntent().getStringExtra("title");
		String paramsStr = getIntent().getStringExtra("params");
		params = JsonUtil.parserGsonToMap(paramsStr,new TypeToken<HashMap<String, String>>(){});

		showBack();

		setTitle(title);

		webViewSetting();
		loadWeb(url);

		pd = new ProgressDialog(this);
		pd.setMessage("正在上传图片...");
		pd.setCancelable(false);

		handle.sendEmptyMessageDelayed(0, 1000);
	}

	private void getToken(final File photoPath) {
		if (token != null) {
			return;
		}
		HttpRequestUtils.httpRequest(this, "获取上传图片token", RequestValue.GET_PICTURE, null, "GET", new HttpRequestUtils.ResponseListener() {
			@Override
			public void getResponseData(String response) {
				LLog.iNetSucceed(" getPhotoToken " + response);
				Common common = JsonUtil.parserGsonToObject(response, Common.class);
				switch (common.getStatus()) {
					case "1":
						token = JsonUtil.parserGsonToObject(JsonUtil.getGsonValueByKey(response, "data"), PhotoToken.class);
						if (photoPath != null) {
							uploadPhoto(photoPath);
						}
						break;
					default:
						break;
				}
			}

			@Override
			public void returnException(Exception e, String msg) {
				LLog.eNetError(e.toString());
			}
		});
	}

	private void uploadPhoto(final File path) {
		if (token == null) {
			getToken(path);
			return;
		}
		pd.show();
		final String key = Utils.getImgName();
		UploadManager um = QiniuUtil.getUploadManager();
		um.put(path, key, token.getSecurityToken(),
				new UpCompletionHandler() {
					@Override
					public void complete(String key, ResponseInfo info,
										 JSONObject response) {
						if (info.isOK()) {
							String url = "";
							if(!TextUtils.isEmpty(token.getDomain())){
								url = token.getDomain()+ key;
							}else{
								url = ServerConfig.RUL_IMG + key;
							}
							if (TextUtils.isEmpty(imgPath1)) {
								imgPath1 = url;
							} else {
								imgPath1 = imgPath1+","+url;
							}
							urlStrs.remove(path.getPath());
//                            Utils.deleteFile(path.getPath(), CannotMaintainActivity.this);

							switch (urlStrs.size()){
								case 2:
									params.put("idcard_face",url);
									break;
								case 1:
									params.put("Idcard_back",url);
									break;
								case 0:
									params.put("company_img",url);
									break;
							}

							if (urlStrs.size() > 0 && !TextUtils.isEmpty(urlStrs.get(0))){
								uploadPhoto(new File(urlStrs.get(0)));
							} else {
								if(urlStrs.size() == 0){
									submitData();
								}
								pd.dismiss();
							}
							LLog.i("联网  图片地址" + url);
						} else {
							pd.dismiss();
							ToastUtil.showLongToast(UserAgreementActivity.this, "图片上传失败");
						}
					}
				}, null);
	}

	/**
	 * 提交数据到服务器
	 */
	private void submitData() {
		HttpRequestUtils.httpRequest(this, "工程师注册",RequestValue.COMPANY_ADDBUSSINESS, params, "POST", new HttpRequestUtils.ResponseListener() {
			@Override
			public void getResponseData(String response) {
				Common common = JsonUtil.parserGsonToObject(response, Common.class);
				switch (common.getStatus()) {
					case "1":
						ToastUtil.showToast(UserAgreementActivity.this,"注册成功!请等待审核认证");
						SharedPreferencesUtils.getInstance(UserAgreementActivity.this).setParam(UserAgreementActivity.this,"former_name", DecodeUtils.encode(params.get("user_phone")));
						finish();
						Bundle bundle = new Bundle();
						bundle.putString("deviceTypeMold",params.get("device_type_mold"));
						bundle.putString("title", "收费标准");
						bundle.putBoolean("isRegister",true);
						IntentUtil.gotoActivity(UserAgreementActivity.this, FeeStandardActivity.class, bundle);
						break;
					default:
						ToastUtil.showToast(UserAgreementActivity.this,common.getInfo());
						break;
				}
			}

			@Override
			public void returnException(Exception e, String msg) {
				LLog.eNetError(e.toString());
			}
		});
	}

	private int count = 5;
	/**
	 * 验证码倒计时
	 */
	Handler handle = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (count == 0) {
				tv_agree.setEnabled(true);
				tv_agree.setText("我接受");
				return;
			}
			tv_agree.setText("我接受("+count-- + "s)");
			tv_agree.setEnabled(false);
			handle.sendEmptyMessageDelayed(0, 1000);
		}
	};

	public void loadWeb(String url) {

		webview.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}
		});
		webview.setWebChromeClient(new WebChromeClient(){

			@Override
			public void onReceivedTitle(WebView view, String title) {
			}

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				System.out.println("---------------"+newProgress);
				if (newProgress == 100) {
					oldProgress = 0;
					progressbar.setProgress(newProgress);
					progressbar.setVisibility(View.GONE);
//					String checkShare = preferencesUtils.loadString("checkShare");
//					if (!TextUtils.isEmpty(checkShare) && checkShare.trim().equals("1")) {
//						goneRight();
//					}
				} else {
					if (progressbar.getVisibility() == View.GONE)
						progressbar.setVisibility(View.VISIBLE);
					if (oldProgress < 90) {
						if (oldProgress >= newProgress) {
							newProgress = oldProgress+10;
						} else if (oldProgress < (newProgress+10)){
							newProgress = newProgress+10;
						}
					}
					progressbar.setProgress(newProgress);
					oldProgress = newProgress;
				}
				super.onProgressChanged(view, newProgress);
			}

			@Override
			public View getVideoLoadingProgressView() {
				FrameLayout frameLayout = new FrameLayout(UserAgreementActivity.this);
				frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
				return frameLayout;
			}

			@Override
			public void onShowCustomView(View view, CustomViewCallback callback) {
				showCustomView(view, callback);
			}

			@Override
			public void onHideCustomView() {
				hideCustomView();
			}
		});
		webview.loadUrl(url);

	}

	public void refresh() {
		webview.reload();
	}

	@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface", "NewApi" })
	@JavascriptInterface
	private void webViewSetting() {

		WebSettings webSettings = webview.getSettings();
		webSettings.setDefaultTextEncodingName("utf-8");
		webSettings.setJavaScriptEnabled(true);
		webSettings.setPluginState(WebSettings.PluginState.ON);
		webSettings.setUseWideViewPort(true); // 关键点
		webSettings.setAllowFileAccess(true); // 允许访问文件
		webSettings.setSupportZoom(true); // 支持缩放
		webSettings.setLoadWithOverviewMode(true);
//		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
		}
		webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

		webSettings.setMediaPlaybackRequiresUserGesture(false);
		webSettings.setPluginState(WebSettings.PluginState.ON);

//		webview.setWebChromeClient(new WebChromeClient());

		webview.requestFocus();
		webview.setHorizontalScrollBarEnabled(false);//水平不显示
		webview.setVerticalScrollBarEnabled(false); //垂直不显示
//        webview.setScrollBarStyle(0);
		addJSInterface();
	}

	@JavascriptInterface
	private void addJSInterface() {
		AndroidJSInterface ajsi = new AndroidJSInterface(this);
		webview.addJavascriptInterface(ajsi, ajsi.getInterface());
	}

	@Override
	protected void initGetData() {
	}

	@Override
	protected void widgetListener() {
		tv_agree.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				/*Bundle bundle = new Bundle();
				bundle.putString("deviceTypeMold",params.get("device_type_mold"));
				bundle.putString("title","收费标准");
				bundle.putBoolean("isRegister",true);
				IntentUtil.gotoActivity(UserAgreementActivity.this, FeeStandardActivity.class, bundle);*/

				//先上传图片
				urlStrs.add(params.get("idcard_face"));
				urlStrs.add(params.get("Idcard_back"));
				urlStrs.add(params.get("company_img"));
				uploadPhoto(new File(params.get("idcard_face")));

			}
		});

		tv_noAgree.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
	}

	@Override
	public void onDestroy() {
		if (webview != null) {
			ViewGroup parent = (ViewGroup) webview.getParent();
			if (parent != null) {
				parent.removeView(webview);
			}
			webview.removeAllViews();
			webview.destroy();
		}
		super.onDestroy();
	}

	/** 视频播放全屏 **/
	private void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {
		// if a view already exists then immediately terminate the new one
		if (customView != null) {
			callback.onCustomViewHidden();
			return;
		}

		this.getWindow().getDecorView();

		FrameLayout decor = (FrameLayout) getWindow().getDecorView();
		fullscreenContainer = new FullscreenHolder(this);
		fullscreenContainer.addView(view);
		decor.addView(fullscreenContainer);
		customView = view;
		setStatusBarVisibility(false);
		customViewCallback = callback;
	}

	/** 隐藏视频全屏 */
	private void hideCustomView() {
		if (customView == null) {
			return;
		}

		setStatusBarVisibility(true);
		FrameLayout decor = (FrameLayout) getWindow().getDecorView();
		decor.removeView(fullscreenContainer);
		fullscreenContainer = null;
		customView = null;
		customViewCallback.onCustomViewHidden();
		webview.setVisibility(View.VISIBLE);
	}

	/** 全屏容器界面 */
	static class FullscreenHolder extends FrameLayout {

		public FullscreenHolder(Context ctx) {
			super(ctx);
			setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
		}

		@Override
		public boolean onTouchEvent(MotionEvent evt) {
			return true;
		}
	}

	private void setStatusBarVisibility(boolean visible) {
		int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
		getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				/** 回退键 事件处理 优先级:视频播放全屏-网页回退-关闭页面 */
				if (customView != null) {
					hideCustomView();
				} else if (webview.canGoBack()) {
					webview.goBack();
				} else {
					finish();
				}
				return true;
			default:
				return super.onKeyUp(keyCode, event);
		}
	}

	@Override
	public void finish() {
		super.finish();
	}
}



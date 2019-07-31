package com.lwc.shanxiu.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.Toast;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.configs.ServerConfig;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.module.setting.ShareActivity;
import com.lwc.shanxiu.utils.ExecutorServiceUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.wxapi.WXContants;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 资讯详情
 *
 * @author 何栋
 * @date 2017年11月29日
 */
public class InformationDetailsActivity extends BaseActivity {

	@BindView(R.id.progressBar1)
	ProgressBar progressbar;
	@BindView(R.id.webView1)
	WebView webview;
	private int oldProgress;
	private FullscreenHolder fullscreenContainer;
	private View customView;
	private WebChromeClient.CustomViewCallback customViewCallback;
	private UMShareAPI uMShareAPI;
	public static InformationDetailsActivity activity;
	private SharedPreferencesUtils preferencesUtils;
	private User user;

	@Override
	protected int getContentViewId(Bundle savedInstanceState) {
		return R.layout.activity_information_details;
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
		showBack();
		if (!TextUtils.isEmpty(title)) {
			setTitle(title);
		} else {
			setTitle("资讯详情");
		}
		if ((TextUtils.isEmpty(title) || (!title.equals("导航")) && !title.equals("扫描结果"))) {
			setRightImg(R.drawable.news_share, new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String shareTitle = preferencesUtils.loadString("shareTitle");
					String sharePhoto = preferencesUtils.loadString("sharePhoto");
					String flink = preferencesUtils.loadString("flink");
					String shareDes = preferencesUtils.loadString("shareDes");
					Bundle bundle = new Bundle();
					bundle.putString("shareContent", shareDes);
					bundle.putString("shareTitle", shareTitle);
					bundle.putString("FLink", flink);
					bundle.putString("urlPhoto", sharePhoto);
					IntentUtil.gotoActivity(InformationDetailsActivity.this, ShareActivity.class, bundle);
					overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				}
			});
		}
		webViewSetting();
		loadWeb(url);
	}

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
				FrameLayout frameLayout = new FrameLayout(InformationDetailsActivity.this);
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

	public void doOauthVerify() {
		String openId = preferencesUtils.loadString("openId"+user.getUserId());
		String nickname = preferencesUtils.loadString("nickname"+user.getUserId());
		String headimgurl = preferencesUtils.loadString("headimgurl"+user.getUserId());
		String unionid = preferencesUtils.loadString("unionid"+user.getUserId());
		if (TextUtils.isEmpty(openId) || TextUtils.isEmpty(nickname) || TextUtils.isEmpty(headimgurl) || TextUtils.isEmpty(unionid)) {
			/**
			 * 如果没有安装微信
			 */
			if (!uMShareAPI.isInstall(this, SHARE_MEDIA.WEIXIN)) {
				ToastUtil.showToast(this, "您手机上未安装微信，请先安装微信客户端！");
				return;
			}
			uMShareAPI.doOauthVerify(this, SHARE_MEDIA.WEIXIN, new MyUMAuthListener());
		} else {
			String shareTitle = preferencesUtils.loadString("shareTitle");
			String sharePhoto = preferencesUtils.loadString("sharePhoto");
			String flink = preferencesUtils.loadString("flink");
			String shareDes = preferencesUtils.loadString("shareDes");
			Bundle bundle = new Bundle();
			bundle.putString("shareContent", shareDes);
			bundle.putString("shareTitle", shareTitle.replace("wechatName", nickname));
			bundle.putString("FLink", flink+"?userPhone="+ preferencesUtils.loadString("former_name")+"&openId="+openId+"&weChatName="+nickname+"&unionid="+unionid);
			bundle.putString("urlPhoto", sharePhoto);
			bundle.putString("goneQQ", "true");
			IntentUtil.gotoActivity(InformationDetailsActivity.this, ShareActivity.class, bundle);
			overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
//			updateUserData(openId, nickname, headimgurl);
		}
	}

	class MyUMAuthListener implements UMAuthListener {

		@Override
		public void onCancel(SHARE_MEDIA arg0, int arg1) {
			Toast.makeText(InformationDetailsActivity.this, "授权取消", Toast.LENGTH_SHORT).show();
		}
		@Override
		public void onStart(SHARE_MEDIA share_media) {
		}
		@Override
		public void onComplete(SHARE_MEDIA arg0, int arg1,
							   Map<String, String> arg2) {
//			Toast.makeText(PaySettingActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
//				openid可以作为用户的唯一标识，将openid保存下来，就可以实现登录状态的检查了。
			String openId = arg2.get("openid");
			String access_token = arg2.get("access_token");
			String refresh_token = arg2.get("refresh_token");
			checkAccessToken(openId, access_token, refresh_token);
		}

		@Override
		public void onError(SHARE_MEDIA arg0, int arg1, Throwable arg2) {
			Toast.makeText(InformationDetailsActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
		}
	}

	private void updateUserData(final String openId, final String nickName, final String headUrl, final String unionid) {
		HashMap<String, String> params = new HashMap<>();
		params.put("openId", openId);
		params.put("weChatName", nickName);
		params.put("weChatHeadImg", headUrl);
		params.put("userPhone", preferencesUtils.loadString("former_name"));
		params.put("unionid", unionid);
		HttpRequestUtils.httpRequest(this, "updateUserData 微信绑定", RequestValue.UP_UPLOAD_INFO, params, "POST", new HttpRequestUtils.ResponseListener() {

			@Override
			public void getResponseData(String response) {
				Common common = JsonUtil.parserGsonToObject(response, Common.class);
				if (common.getStatus().equals(ServerConfig.RESPONSE_STATUS_SUCCESS)) {
					ToastUtil.showLongToast(InformationDetailsActivity.this, "绑定微信成功,分享连接到微信即可得现金红包一个！");
					String shareTitle = preferencesUtils.loadString("shareTitle");
					String sharePhoto = preferencesUtils.loadString("sharePhoto");
					String flink = preferencesUtils.loadString("flink");
					String shareDes = preferencesUtils.loadString("shareDes");
					Bundle bundle = new Bundle();
					bundle.putString("shareContent", shareDes);
					bundle.putString("shareTitle", shareTitle.replace("wechatName", nickName));
					bundle.putString("FLink", flink+"?userPhone="+ preferencesUtils.loadString("former_name")+"&openId="+openId+"&weChatName="+nickName+"&unionid="+unionid);
					bundle.putString("urlPhoto", sharePhoto);
					bundle.putString("goneQQ", "true");
					IntentUtil.gotoActivity(InformationDetailsActivity.this, ShareActivity.class, bundle);
					overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
				} else {
					ToastUtil.showLongToast(InformationDetailsActivity.this, common.getInfo());
				}
			}
			@Override
			public void returnException(Exception e, String msg) {
				LLog.eNetError("updateUserInfo1  " + e.toString());
				ToastUtil.showLongToast(InformationDetailsActivity.this, msg);
			}
		});
	}
	public static String httpGet(String url) throws IOException {
		OkHttpClient httpClient = new OkHttpClient();
		Request request = new Request.Builder()
				.url(url)
				.build();
		Response response = httpClient.newCall(request).execute();
		return response.body().string(); // 返回的是string 类型，json的mapper可以直接处理
	}

	public void checkAccessToken(final String accessToken, final String openid, final String refresh_token) {
		final String requestUrl = "https://api.weixin.qq.com/sns/auth?access_token="+accessToken+"&openid="+openid;
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					String response = httpGet(requestUrl);
					final JSONObject o = new JSONObject(response);
					int code = o.getInt("errcode");
					if (code == 0) {
						getWxUserInfo(openid, accessToken);
					} else {
						refreshToken(refresh_token);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		ExecutorServiceUtil.getInstance().execute(runnable);
	}

	public void refreshToken(final String refresh_token) {
		final String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="+ WXContants.APP_ID+"&grant_type=refresh_token&refresh_token="+refresh_token;
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					String response = httpGet(url);
					final JSONObject o = new JSONObject(response);
//					int code = o.getInt("errcode");
//					if (code == 0) {
					String accessToken = o.getString("access_token");
					String openId = o.getString("openid");
					getWxUserInfo(openId, accessToken);
//					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		ExecutorServiceUtil.getInstance().execute(runnable);
	}

	public void getWxUserInfo(final String openId, String token) {
		final String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+token+"&openid="+openId;
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					String response = httpGet(url);
					final JSONObject o = new JSONObject(response);
					final String nickname = o.optString("nickname");
					String headimgurl = o.optString("headimgurl");
					String unionid = o.optString("unionid");
					preferencesUtils.saveString("openId"+user.getUserId(), openId);
					preferencesUtils.saveString("nickname"+user.getUserId(), nickname);
					preferencesUtils.saveString("headimgurl"+user.getUserId(), headimgurl);
					preferencesUtils.saveString("unionid"+user.getUserId(), unionid);
					updateUserData(openId, nickname, headimgurl, unionid);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		ExecutorServiceUtil.getInstance().execute(runnable);
	}

	// 如果有使用任一平台的SSO授权, 则必须在对应的activity中实现onActivityResult方法, 并添加如下代码
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uMShareAPI.onActivityResult(requestCode, resultCode, data);
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
		preferencesUtils.saveString("checkShare", "");
		if (MainActivity.activity == null) {
			IntentUtil.gotoActivityAndFinish(this, MainActivity.class);
		}
		super.finish();
	}
}



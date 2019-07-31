package com.lwc.shanxiu.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.configs.ServerConfig;
import com.lwc.shanxiu.module.user.LoginOrRegistActivity;
import com.lwc.shanxiu.utils.IntentUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 资讯详情
 * 
 * @author 何栋
 * @date 2017年11月29日
 */
public class UserGuideActivity extends BaseActivity {

	@BindView(R.id.webView1)
	WebView webview;

	@Override
	protected int getContentViewId(Bundle savedInstanceState) {
		return R.layout.activity_user_guide;
	}

	@Override
	protected void findViews() {
		ButterKnife.bind(this);
		webViewSetting();
	}

	@Override
	protected void init() {
		loadWeb(ServerConfig.DOMAIN.replace("https", "http") + "/main/tutorial/engTutorial.html");
	}

	public void loadWeb(String url) {

		webview.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
				finish();
//				view.loadUrl(url);
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
				super.onProgressChanged(view, newProgress);
			}

			@Override
			public View getVideoLoadingProgressView() {
				FrameLayout frameLayout = new FrameLayout(UserGuideActivity.this);
				frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
				return frameLayout;
			}

			@Override
			public boolean onJsAlert(WebView view, String url, String message,
									 JsResult result) {
				// TODO Auto-generated method stub
				return super.onJsAlert(view, url, message, result);
			}
		});
		webview.loadUrl(url);
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

//	@Override
//	public boolean onKeyUp(int keyCode, KeyEvent event) {
//		switch (keyCode) {
//			case KeyEvent.KEYCODE_BACK:
//				finish();
//			default:
//				return super.onKeyUp(keyCode, event);
//		}
//	}

	@Override
	public void finish() {
		if (MainActivity.activity == null) {
			IntentUtil.gotoActivityAndFinish(this, LoginOrRegistActivity.class);
		}
		super.finish();
	}
}

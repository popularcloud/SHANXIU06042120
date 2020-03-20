package com.lwc.shanxiu.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.configs.ServerConfig;
import com.lwc.shanxiu.utils.IntentUtil;

import butterknife.BindView;

/**
 * Created by 何栋 on 2017/10/15.
 * 294663966@qq.com
 */
public class InformationActivity extends BaseActivity {


    @BindView(R.id.progressBar1)
    ProgressBar progressbar;
    @BindView(R.id.webView1)
    WebView webview;


    @Override
    protected void widgetListener() {
    }

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_information;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void findViews() {
        setTitle("资讯");
        showBack();
    }

    @Override
    public void init() {
    }

    @Override
    public void initGetData() {
        webViewSetting();
        loadWeb();
    }

    public void loadWeb() {
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                IntentUtil.gotoActivity(InformationActivity.this, InformationDetailsActivity.class, bundle);
                return true;
            }
        });
        webview.loadUrl(ServerConfig.DOMAIN.replace("https","http")+"/main/h5/userNews.html?clientType=maintenance");
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
//        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setPluginState(WebSettings.PluginState.ON);

        webview.setWebChromeClient(new WebChromeClient());

        webview.requestFocus();
        webview.setHorizontalScrollBarEnabled(false);//水平不显示
        webview.setVerticalScrollBarEnabled(false); //垂直不显示
//        webview.setScrollBarStyle(0);
        addJSInterface();
    }

    @JavascriptInterface
    public void addJSInterface() {
        AndroidJSInterface ajsi = new AndroidJSInterface(InformationActivity.this);
        webview.addJavascriptInterface(ajsi, ajsi.getInterface());
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
}

package com.lwc.shanxiu.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.gyf.immersionbar.ImmersionBar;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.AndroidJSInterface;
import com.lwc.shanxiu.activity.InformationDetailsActivity;
import com.lwc.shanxiu.configs.ServerConfig;
import com.lwc.shanxiu.utils.IntentUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 何栋 on 2017/10/15.
 * 294663966@qq.com
 */
public class InformationFragment extends BaseFragment {


    @BindView(R.id.progressBar1)
    ProgressBar progressbar;
    @BindView(R.id.webView1)
    WebView webview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;

    }

    @Override
    protected View getViews() {
        return View.inflate(getActivity(), R.layout.fragment_information, null);
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser  && getActivity() != null){
            ImmersionBar.with(getActivity())
                    .statusBarColor(R.color.white)
                    .statusBarDarkFont(true)
                    .navigationBarColor(R.color.white).init();
        }
    }

    @Override
    protected void widgetListener() {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle("资讯");
        webViewSetting();
        loadWeb();
    }



    @Override
    public void init() {
    }

    @Override
    public void initGetData() {

    }

    public void loadWeb() {
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                IntentUtil.gotoActivity(getActivity(), InformationDetailsActivity.class, bundle);
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
    private void addJSInterface() {
        AndroidJSInterface ajsi = new AndroidJSInterface(getActivity());
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

package com.lwc.shanxiu.module.message.ui;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.activity.ImageBrowseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.configs.ServerConfig;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.message.bean.KnowledgeDetailBean;
import com.lwc.shanxiu.module.setting.ShareActivity;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.ToastUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author younge
 * @date 2019/6/4 0004
 * @email 2276559259@qq.com
 */
public class KnowledgeDetailWebActivity extends BaseActivity {

    @BindView(R.id.wv_detail)
    WebView wv_detail;

    private String knowledgeId;
    private KnowledgeDetailBean detailBean;

    String baseUrl = ServerConfig.DOMAIN.replace("https","http") +"/main/h5/knowledgeDetails.html";

    private ArrayList<String> showImgList = new ArrayList<>();
    private WebSettings webSettings;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        return R.layout.activity_knowledge_web_detail;
    }

    @Override
    protected void findViews() {
        knowledgeId = getIntent().getStringExtra("knowledgeId");
        showBack();
        setTitle("详情");
        /*setRightImg(R.drawable.ic_share, new View.OnClickListener() { //分享
            @Override
            public void onClick(View view) {
                if (detailBean != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("shareTitle", detailBean.getKnowledgeTitle());
                    bundle.putString("shareContent", detailBean.getKnowledgePaper());
                    bundle.putString("FLink", baseUrl+"?knowledge_id=" + detailBean.getKnowledgeId());
                    IntentUtil.gotoActivity(KnowledgeDetailWebActivity.this, ShareActivity.class, bundle);
                }
            }
        });*/
    }


    @Override
    protected void init() {

    }

    @Override
    protected void initGetData() {
        HttpRequestUtils.httpRequest(this, "知识图谱详情", RequestValue.GET_KNOWLEDGE_DETAILS + "/" + knowledgeId, null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        detailBean = JsonUtil.parserGsonToObject(JsonUtil.getGsonValueByKey(response, "data"), KnowledgeDetailBean.class);
                        if (detailBean != null) {
                            showData();
                        } else {
                            ToastUtil.showToast(KnowledgeDetailWebActivity.this, "加载出错，文章不存在！");
                        }
                        break;
                    default:
                        ToastUtil.showToast(KnowledgeDetailWebActivity.this, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
            }
        });
    }

    @Override
    protected void widgetListener() {

    }

    /**
     * 显示文章
     */
    private void showData() {

        webSettings = wv_detail.getSettings();//获取webview设置属性
      //  webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//把html中的内容放大webview等宽的一列中
        webSettings.setJavaScriptEnabled(true);//支持js

        webSettings.setBlockNetworkImage(false);
// 允许从任何来源加载内容，即使起源是不安全的；
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setBuiltInZoomControls(true); //设置可以缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webSettings.setMediaPlaybackRequiresUserGesture(false); //设置是否自动播放视频

        String loadUrl = baseUrl+"?knowledge_id="+detailBean.getKnowledgeId();
        Log.d("联网成功",loadUrl);
        wv_detail.loadUrl(loadUrl);

        wv_detail.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //setWebImageClick(wv_detail,"imageInterface");
                webSettings.setBlockNetworkImage(false);
                //判断webview是否加载了，图片资源
                if (!webSettings.getLoadsImagesAutomatically()) {
                    //设置wenView加载图片资源
                    webSettings.setLoadsImagesAutomatically(true);
                }
                super.onPageFinished(view,url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                //handler.proceed();
            }
        });
    }


    /**
     * 将html文本内容中包含img标签的图片，宽度变为屏幕宽度，高度根据宽度比例自适应
     **/
    public String getNewContent(String htmltext) {
        try {
            Document doc = Jsoup.parse(htmltext);

    /*        Elements head = doc.getElementsByTag("head");
            for (Element element : head) {
                element.append("<meta name=\"viewport\" content=\"target-densitydpi=device-dpi, width=device-width, initial-scale=1.0, user-scalable=no\" />");
            }*/

            showImgList.clear();

            Elements elements = doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("style", "width: 80%; height: auto;");
                String src =  element.getElementsByAttribute("src").attr("src");
                showImgList.add(src);
            }
            Elements videos = doc.getElementsByTag("video");
            for (Element element : videos) {
                element.attr("style", "width: 80%; height: auto;");
            }
            return doc.toString();
        } catch (Exception e) {
            return htmltext;
        }
    }


    /**
     * 设置网页中图片的点击事件
     * @param view
     *
     */
    public void setWebImageClick(WebView view, String method) {
        String jsCode="javascript:(function(){" +
                "var imgs=document.getElementsByTagName(\"img\");" +
                "for(var i=0;i<imgs.length;i++){" +
                "imgs[i].pos = i;"+
                "imgs[i].onclick=function(){" +
                "window."+method+".openImage(this.src,this.pos);" +
                "}}})()";
        view.loadUrl(jsCode);
      //  return jsCode;
    }

    public class ImageJavascriptInterface {
        private Context context;

        public ImageJavascriptInterface(Context context) {
            this.context = context;
        }

        //java回调js代码，不要忘了@JavascriptInterface这个注解，不然点击事件不起作用
        @JavascriptInterface
        public void openImage(String img, int pos) {


            Log.d("联网成功","点击了图片");
        /*    ArrayList arrayList = new ArrayList();
            for (String item : imageUrls) {
                arrayList.add(item);
            }*/
            //实现自己的图片浏览页面
            //ImagePagerActivity.showActivity(context, arrayList, pos);

            Intent intent = new Intent(KnowledgeDetailWebActivity.this, ImageBrowseActivity.class);
            intent.putExtra("index", pos);
            intent.putStringArrayListExtra("list", showImgList);
            startActivity(intent);
        }
    }
}

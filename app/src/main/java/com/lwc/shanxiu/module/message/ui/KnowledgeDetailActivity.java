package com.lwc.shanxiu.module.message.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.activity.ImageBrowseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.message.adapter.OtherKnowlegeArticleAdapter;
import com.lwc.shanxiu.module.message.bean.KnowledgeDetailBean;
import com.lwc.shanxiu.module.message.bean.LikeArticleBean;
import com.lwc.shanxiu.module.question.ui.activity.QuestionDetailActivity;
import com.lwc.shanxiu.module.setting.ShareActivity;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.TimeUtil;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.widget.MyWebView;

import org.byteam.superadapter.OnItemClickListener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * @author younge
 * @date 2019/6/4 0004
 * @email 2276559259@qq.com
 */
public class KnowledgeDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_view_count)
    TextView tv_view_count;
    @BindView(R.id.tv_create_time)
    TextView tv_create_time;
    @BindView(R.id.wv_detail)
    MyWebView wv_detail;
    @BindView(R.id.tv_author)
    TextView tv_author;
    @BindView(R.id.rv_article)
    RecyclerView rv_article;
    @BindView(R.id.rl_content)
    LinearLayout rl_content;
    @BindView(R.id.ll_article)
    LinearLayout ll_article;

    private String knowledgeId;
    private OtherKnowlegeArticleAdapter adapter;
    private KnowledgeDetailBean detailBean;

    private ArrayList<String> showImgList = new ArrayList<>();

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        return R.layout.activity_knowledge_detail;
    }

    @Override
    protected void findViews() {
        knowledgeId = getIntent().getStringExtra("knowledgeId");
        showBack();
        setTitle("详情");
        setRightImg(R.drawable.ic_share, new View.OnClickListener() { //分享
            @Override
            public void onClick(View view) {
                if (detailBean != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("shareTitle", detailBean.getKnowledgeTitle());
                    bundle.putString("shareContent", detailBean.getKnowledgePaper());
                    bundle.putString("FLink", "https://www.ls-mx.com/main/h5/knowledgeDetails.html?knowledge_id=" + detailBean.getKnowledgeId());
                    IntentUtil.gotoActivity(KnowledgeDetailActivity.this, ShareActivity.class, bundle);
                }
            }
        });
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
                            ToastUtil.showToast(KnowledgeDetailActivity.this, "加载出错，文章不存在！");
                        }
                        break;
                    default:
                        ToastUtil.showToast(KnowledgeDetailActivity.this, common.getInfo());
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

    private void initRecycleView(List<LikeArticleBean> likeArticleBeans) {
        if (likeArticleBeans == null || likeArticleBeans.size() < 1) { //没有相关推荐时 隐藏
            ll_article.setVisibility(View.GONE);
            return;
        }
        ll_article.setVisibility(View.VISIBLE);
        rv_article.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OtherKnowlegeArticleAdapter(this, likeArticleBeans, R.layout.item_like_knowledge);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("knowledgeId", adapter.getItem(position).getKnowledge_id());
                IntentUtil.gotoActivity(KnowledgeDetailActivity.this, KnowledgeDetailActivity.class, bundle);
            }
        });

        rv_article.setAdapter(adapter);
    }

    /**
     * 显示文章
     */
    private void showData() {
        tv_title.setText(detailBean.getKnowledgeTitle());
        tv_view_count.setText(detailBean.getBrowseNum() + "次");

        if (TextUtils.isEmpty(detailBean.getMaintenanceName())) {
            tv_author.setVisibility(View.GONE);
        } else {
            tv_author.setVisibility(View.VISIBLE);
            tv_author.setText(detailBean.getMaintenanceName());
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
        Date date = null;
        try {
            date = sdf.parse(detailBean.getCreateTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateStr = TimeUtil.getTimeFormatText(date);
        tv_create_time.setText(dateStr);

        WebSettings webSettings = wv_detail.getSettings();//获取webview设置属性
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//把html中的内容放大webview等宽的一列中
        webSettings.setJavaScriptEnabled(true);//支持js

        webSettings.setSupportZoom(false); // 支持缩放
       // webSettings.setBuiltInZoomControls(true); //设置可以缩放
       // webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webSettings.setMediaPlaybackRequiresUserGesture(false); //设置是否自动播放视频
        //webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口，
       // webSettings.setUseWideViewPort(true);
        //webSettings.setLoadWithOverviewMode(true);//缩放至屏幕大小



      //  wv_detail.addJavascriptInterface(new HeightGetter(), "jo");


        String knowledgeDetails = getNewContent(detailBean.getKnowledgeDetails());
       // String endContext = knowledgeDetails +setWebImageClick(wv_detail,"ImageJavascriptInterface");
       // String context = getNewContent(knowledgeDetails);
        Log.d("联网成功",knowledgeDetails);
        wv_detail.loadDataWithBaseURL(null, knowledgeDetails, "text/html", "UTF-8", null);

        wv_detail.addJavascriptInterface(new ImageJavascriptInterface(KnowledgeDetailActivity.this), "imageInterface");
        wv_detail.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                initRecycleView(detailBean.getLike());
                setWebImageClick(wv_detail,"imageInterface");
            }
        });

        wv_detail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
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

            Intent intent = new Intent(KnowledgeDetailActivity.this, ImageBrowseActivity.class);
            intent.putExtra("index", pos);
            intent.putStringArrayListExtra("list", showImgList);
            startActivity(intent);
        }
    }
}

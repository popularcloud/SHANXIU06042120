package com.lwc.shanxiu.module.message.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.message.bean.KnowledgeDetailBean;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.TimeUtil;
import com.lwc.shanxiu.utils.ToastUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

/**
 * @author younge
 * @date 2019/6/4 0004
 * @email 2276559259@qq.com
 */
public class KnowledgeDetailActivity extends BaseActivity{

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_view_count)
    TextView tv_view_count;
    @BindView(R.id.tv_create_time)
    TextView tv_create_time;
    @BindView(R.id.wv_detail)
    WebView wv_detail;
    @BindView(R.id.tv_author)
    TextView tv_author;


    private String knowledgeId;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_knowledge_detail;
    }

    @Override
    protected void findViews() {
      knowledgeId = getIntent().getStringExtra("knowledgeId");
        showBack();
        setTitle("详情");
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initGetData() {
        HttpRequestUtils.httpRequest(this, "知识图谱详情", RequestValue.GET_KNOWLEDGE_DETAILS+"/"+knowledgeId, null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                       KnowledgeDetailBean detailBean = JsonUtil.parserGsonToObject(JsonUtil.getGsonValueByKey(response, "data"),KnowledgeDetailBean.class);
                       if(detailBean != null){
                           showData(detailBean);
                       }else{
                           ToastUtil.showToast(KnowledgeDetailActivity.this,"加载出错，文章不存在！");
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

    private void showData(KnowledgeDetailBean detailBean){
        tv_title.setText(detailBean.getKnowledgeTitle());
        tv_view_count.setText(detailBean.getBrowseNum()+"次");

        if(TextUtils.isEmpty(detailBean.getMaintenanceName())){
            tv_author.setVisibility(View.GONE);
        }else{
            tv_author.setVisibility(View.VISIBLE);
            tv_author.setText(detailBean.getMaintenanceName());
        }

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
        Date date= null;
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
        webSettings.setBuiltInZoomControls(false); // 显示放大缩小
        webSettings.setSupportZoom(true); // 可以缩放
        webSettings.setMediaPlaybackRequiresUserGesture(false);

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        String context = getNewContent(detailBean.getKnowledgeDetails());
        wv_detail.loadDataWithBaseURL(null, context, "text/html", "UTF-8", null);
    }

    /**
     * 将html文本内容中包含img标签的图片，宽度变为屏幕宽度，高度根据宽度比例自适应
     **/
    public String getNewContent(String htmltext){
        try {
            Document doc= Jsoup.parse(htmltext);
            Elements elements=doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("width","100%").attr("height","auto");
            }

            Elements videos=doc.getElementsByTag("video");
            for (Element element : videos) {
                element.attr("width","100%").attr("height","auto");
            }
            return doc.toString();
        } catch (Exception e) {
            return htmltext;
        }
    }
}

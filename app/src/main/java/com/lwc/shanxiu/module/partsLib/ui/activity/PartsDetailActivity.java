package com.lwc.shanxiu.module.partsLib.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.activity.ImageBrowseActivity;
import com.lwc.shanxiu.activity.InformationDetailsActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.ADInfo;
import com.lwc.shanxiu.module.order.ui.activity.QuoteAffirmActivity;
import com.lwc.shanxiu.module.partsLib.ui.adapter.SpecAdapter;
import com.lwc.shanxiu.module.partsLib.ui.bean.PartsBean;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.view.ImageCycleView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author younge
 * @date 2018/12/25 0025
 * @email 2276559259@qq.com
 */
public class PartsDetailActivity extends BaseActivity {

    @BindView(R.id.rv_specs)
    RecyclerView rv_specs;
    @BindView(R.id.ad_view)
    ImageCycleView mAdView;//轮播图
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_good_detail_txt)
    TextView tv_good_detail_txt;
    @BindView(R.id.btnLook)
    TextView btnLook;
    @BindView(R.id.btnAdd)
    TextView btnAdd;
    @BindView(R.id.wv_content)
    WebView wv_content;
    private SpecAdapter specAdapter;

    private ArrayList<ADInfo> infos = new ArrayList<>();//广告轮播图
    private String accessories_id;
    private String[] ads;

    /**
     * 购物清单数据
     */
    private List<PartsBean> addListBeans = new ArrayList<>();
    private PartsBean partsBeans;
    private boolean isAdd;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_parts_detail;
    }

    @Override
    protected void findViews() {
        Intent myIntent = getIntent();
        accessories_id = myIntent.getStringExtra("accessories_id");
        showBack();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle("详情");

        //获取购物清单
        String addListsStr = (String) SharedPreferencesUtils.getParam(this,"addListBeans","");
        List<PartsBean> beanArrayList = JsonUtil.parserGsonToArray(addListsStr, new TypeToken<ArrayList<PartsBean>>() {});
        addListBeans.clear();
        if(beanArrayList != null){
            addListBeans.addAll(beanArrayList);
        }
    }

    @Override
    protected void init() {
        btnLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.gotoActivity(PartsDetailActivity.this,BuyListActivity.class);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAdd = false;
                int position = 0;
                for(int i =0;i<addListBeans.size();i++){
                    if(partsBeans.getAccessoriesId().equals(addListBeans.get(i).getAccessoriesId())){
                        isAdd = true;
                        position = i;
                        break;
                    }
                }
                if(isAdd){
                    addListBeans.get(position).setSumSize(addListBeans.get(position).getSumSize()+1);
                    SharedPreferencesUtils.getInstance(PartsDetailActivity.this).setParam(PartsDetailActivity.this,"addListBeans",JsonUtil.parserObjectToGson(addListBeans));
                    ToastUtil.showToast(PartsDetailActivity.this,"添加成功!");
                }else{
                    //加入清单
                    addListBeans.add(partsBeans);
                    //保存到本地
                    SharedPreferencesUtils.getInstance(PartsDetailActivity.this).setParam(PartsDetailActivity.this,"addListBeans",JsonUtil.parserObjectToGson(addListBeans));
                    ToastUtil.showToast(PartsDetailActivity.this,"添加成功!");
                }
            }
        });
    }

    @Override
    protected void initGetData() {
       /* Map<String,String> params = new HashMap<>();
        params.put("accessories_id",accessories_id);*/
        HttpRequestUtils.httpRequest(this, "getAccessories", RequestValue.GET_ACCESSORIES+"?accessories_id="+accessories_id,null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":

                        partsBeans = JsonUtil.parserGsonToObject(JsonUtil.getGsonValueByKey(response, "data"),PartsBean.class);

                        if(partsBeans.getAccessoriesDetailImg() != null){
                            ads =partsBeans.getAccessoriesDetailImg().split(",");
                            for(int i = 0;i < ads.length;i ++){
                                ADInfo adInfo = new ADInfo();
                                adInfo.setAdvertisingImageUrl(ads[i]);
                                infos.add(adInfo);
                            }
                            if ( infos != null && infos.size() > 0) {
                                mAdView.setImageResources(infos, mAdCycleViewListener);
                                if(infos.size() > 1){
                                    mAdView.startImageCycle();
                                }
                            }
                        }

                        GridLayoutManager gridLayoutManager = new GridLayoutManager(PartsDetailActivity.this,2);
                        rv_specs.setLayoutManager(gridLayoutManager);
                        List<String> stringList = new ArrayList<>();
                        if(partsBeans.getAttributeName() != null) {
                            String [] accessoresParams =partsBeans.getAttributeName().split(",");
                            for (int i = 0; i < ((accessoresParams.length > 4)?4:accessoresParams.length); i++) {
                                stringList.add(accessoresParams[i]);
                            }
                        }
                        if(TextUtils.isEmpty(partsBeans.getAttributeName())){
                            rv_specs.setVisibility(View.GONE);
                        }
                        specAdapter = new SpecAdapter(PartsDetailActivity.this,stringList,R.layout.item_parts_detial);
                        rv_specs.setAdapter(specAdapter);

                        tv_title.setText(partsBeans.getAccessoriesName());


                        String priceString ="¥ "+ Utils.getMoney(String.valueOf(partsBeans.getAccessoriesPrice()/100));
                        SpannableStringBuilder stringBuilder=new SpannableStringBuilder(priceString);
                        AbsoluteSizeSpan ab=new AbsoluteSizeSpan(12,true);
                        //文本字体绝对的大小
                        stringBuilder.setSpan(ab,0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tv_price.setText(stringBuilder);


                        if(TextUtils.isEmpty(partsBeans.getAccessoriesDetail())){
                            tv_good_detail_txt.setVisibility(View.GONE);
                        }

                        WebSettings webSettings = wv_content.getSettings();//获取webview设置属性
                        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//把html中的内容放大webview等宽的一列中
                        webSettings.setJavaScriptEnabled(true);//支持js
                        //  webSettings.setBuiltInZoomControls(true); // 显示放大缩小
                        webSettings.setSupportZoom(true); // 可以缩放

                        String context = getNewContent(partsBeans.getAccessoriesDetail());
                        wv_content.loadDataWithBaseURL(null, context, "text/html", "UTF-8", null);
                        break;
                    default:
                        ToastUtil.showToast(PartsDetailActivity.this,common.getInfo());
                        break;
                }
            }
            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showToast(PartsDetailActivity.this,msg);
            }
        });
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

            return doc.toString();
        } catch (Exception e) {
            return htmltext;
        }
    }
    @Override
    protected void widgetListener() {

    }

    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            // 点击图片后,有内链和外链的区别
      /*      Bundle bundle = new Bundle();
            if (!TextUtils.isEmpty(info.getAdvertisingUrl()))
                bundle.putString("url", info.getAdvertisingUrl());
            if (!TextUtils.isEmpty(info.getAdvertisingTitle()))
                bundle.putString("title", info.getAdvertisingTitle());
            IntentUtil.gotoActivity(PartsDetailActivity.this, InformationDetailsActivity.class, bundle);*/
            if(ads != null){
                ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(ads));
                Intent intent = new Intent(PartsDetailActivity.this, ImageBrowseActivity.class);
                intent.putExtra("index", position);
                intent.putStringArrayListExtra("list", arrayList);
                startActivity(intent);
            }
        }

        @Override
        public void displayImage(final String imageURL, final ImageView imageView) {
            ImageLoaderUtil.getInstance().displayFromNetD(PartsDetailActivity.this, imageURL, imageView);// 使用ImageLoader对图片进行加装！
        }
    };
}

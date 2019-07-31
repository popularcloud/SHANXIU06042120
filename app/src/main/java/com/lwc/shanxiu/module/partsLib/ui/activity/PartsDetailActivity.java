package com.lwc.shanxiu.module.partsLib.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.view.ImageCycleView;

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
    private SpecAdapter specAdapter;

    private ArrayList<ADInfo> infos = new ArrayList<>();//广告轮播图
    private String accessories_id;
    private String[] ads;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_parts_detail;
    }

    @Override
    protected void findViews() {
        Intent myIntent = getIntent();
        accessories_id = myIntent.getStringExtra("accessories_id");
    }

    @Override
    protected void onResume() {
        super.onResume();
        showBack();
        setTitle("配件详情");
    }

    @Override
    protected void init() {

    }

    @OnClick({R.id.btnReturn,R.id.btnLook})
    public void onBtnClick(View view){
        switch (view.getId()){
            case R.id.btnLook:
                IntentUtil.gotoActivity(this,BuyListActivity.class);
                break;
            case R.id.btnReturn:
                IntentUtil.gotoActivity(this,QuoteAffirmActivity.class);
                break;

        }
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
                        PartsBean partsBeans = JsonUtil.parserGsonToObject(JsonUtil.getGsonValueByKey(response, "data"),PartsBean.class);
                        if(partsBeans.getAccessoriesDetailImg() != null){
                            ads =partsBeans.getAccessoriesDetailImg().split(",");
                            for(int i = 0;i < ads.length;i ++){
                                ADInfo adInfo = new ADInfo();
                                adInfo.setAdvertisingImageUrl(ads[i]);
                                infos.add(adInfo);
                            }
                            if ( infos != null && infos.size() > 0) {
                                mAdView.setImageResources(infos, mAdCycleViewListener);
                                mAdView.startImageCycle();



                            }
                        }

                        GridLayoutManager gridLayoutManager = new GridLayoutManager(PartsDetailActivity.this,1);
                        rv_specs.setLayoutManager(gridLayoutManager);
                        List<String> stringList = new ArrayList<>();
                        if(partsBeans.getAttributeName() != null){
                            String [] attributeNames =partsBeans.getAttributeName().split(",");
                            for(int i = 0;i < attributeNames.length; i ++){
                                stringList.add(attributeNames[i]);
                            }
                        }

                        if(partsBeans.getAccessoriesParam() != null){
                            String [] accessoriesParam =partsBeans.getAccessoriesParam().split(",");
                            for(int i = 0;i < accessoriesParam.length; i ++){
                                stringList.add(accessoriesParam[i]);
                            }
                        }

                        specAdapter = new SpecAdapter(PartsDetailActivity.this,stringList,R.layout.item_parts_detial);
                        rv_specs.setAdapter(specAdapter);

                        tv_title.setText(partsBeans.getAccessoriesName());
                        tv_price.setText("￥"+ Utils.getMoney(String.valueOf(partsBeans.getAccessoriesPrice()/100)));
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

    @Override
    protected void widgetListener() {

    }

    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            // 点击图片后,有内链和外链的区别
         /*   Bundle bundle = new Bundle();
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

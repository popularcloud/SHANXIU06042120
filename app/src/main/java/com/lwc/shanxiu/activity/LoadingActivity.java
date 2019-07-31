package com.lwc.shanxiu.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.bean.ActivityBean;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.bean.ADInfo;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.module.user.LoginOrRegistActivity;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.view.CountDownProgressView;
import com.lwc.shanxiu.view.ImageCycleView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * loading界面
 *
 * @author 何栋
 * @date 2017年11月20日
 * @Copyright: lwc  友盟UmengNotifyClickActivity
 */
public class LoadingActivity extends Activity {

    private static String TAG = LoadingActivity.class.getName();

    private SharedPreferencesUtils preferencesUtils = null;
    private User user = null;
    private ImageView imageView, iv_gz;
    private CountDownProgressView countdownProgressView;
    private boolean gotoLogin = false;
    private ImageCycleView ad_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
//        PushAgent.getInstance(this).onAppStart();
        imageView = (ImageView) findViewById(R.id.imageView);
        ad_view = (ImageCycleView) findViewById(R.id.ad_view);
        iv_gz = (ImageView) findViewById(R.id.iv_gz);
        countdownProgressView = (CountDownProgressView) findViewById(R.id.countdownProgressView);

        initEngines();
        init();

        countdownProgressView.setProgressListener(new CountDownProgressView.OnProgressListener() {
            @Override
            public void onProgress(int progress) {
                if (progress == 0 && !gotoLogin) {
                    gotoActivity();
                }
            }
        });
        countdownProgressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLogin = true;
                gotoActivity();
            }
        });
    }
//友盟推送
//    @Override
//    public void onMessage(Intent intent) {
//        super.onMessage(intent);  //此方法必须调用，否则无法统计打开数
//        String body = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
//        Log.i(TAG, body);
//    }

    private void gotoActivity() {
        if (user == null) {
            IntentUtil.gotoActivityAndFinish(LoadingActivity.this, LoginOrRegistActivity.class);
            return;
        }
        String token = preferencesUtils.loadString("token");
        if (token != null && !token.equals("")) {
            IntentUtil.gotoActivityAndFinish(LoadingActivity.this, MainActivity.class);
        } else {
            IntentUtil.gotoActivityAndFinish(LoadingActivity.this, LoginOrRegistActivity.class);
        }
    }

    public void init() {
        boolean isFirstTime = preferencesUtils.loadBooleanData("isfirsttime");
//        if (!isFirstTime) {
//            preferencesUtils.saveBooleanData("isfirsttime", true);
//            IntentUtil.gotoActivityAndFinish(LoadingActivity.this, UserGuideActivity.class);
//            return;
//        }
        getBoot();
        String token = preferencesUtils.loadString("token");
        if (token != null && !token.equals("")) {
            getActivity();
        }

    }

    /**
     * 查询启动广告图
     */
    private void getBoot() {
      /*  HashMap<String, String> map = new HashMap<>();
        map.put("type", "2");*/
        HttpRequestUtils.httpRequest(this, "advertising", RequestValue.GET_ADVERTISING+"?local=2&role=2", null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                       try{
                           ArrayList<ADInfo> infos = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<ADInfo>>() {});
                           if(infos == null || infos.size() < 1){
                               gotoActivity();
                               return;
                           }
                            ADInfo info = infos.get(0);
                            String imgUrls = info.getAdvertisingImageUrl();
                            ArrayList<ADInfo> adInfos = new ArrayList<ADInfo>();
                            if(!TextUtils.isEmpty(imgUrls)){
                                String[] urls = imgUrls.split(",");
                                for (String url: urls) {
                                    ADInfo ads = new ADInfo(url);
                                    adInfos.add(ads);
                                }

                                ad_view.setVisibility(View.VISIBLE);
                                imageView.setVisibility(View.GONE);
                                ad_view.setImageResources(adInfos, mAdCycleViewListener);
                                ad_view.startImageCycle();

                                iv_gz.setVisibility(View.GONE);
                                countdownProgressView.setVisibility(View.VISIBLE);
                                countdownProgressView.start();
                            }else{
                                gotoActivity();
                            }
                       }catch (Exception ex){
                           gotoActivity();
                       }
                        break;
                    default:
                        gotoActivity();
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                gotoActivity();
            }
        });
    }

    /**
     * 定义轮播图监听
     */
    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            // 点击图片后,有内链和外链的区别
          /*  Bundle bundle = new Bundle();
            if (!TextUtils.isEmpty(info.getAdvertisingUrl()))
                bundle.putString("url", info.getAdvertisingUrl());
            if (!TextUtils.isEmpty(info.getAdvertisingTitle()))
                bundle.putString("title", info.getAdvertisingTitle());
            IntentUtil.gotoActivity(LoadingActivity.this, InformationDetailsActivity.class, bundle);*/
        }

        @Override
        public void displayImage(String imageURL, final ImageView imageView) {
                ImageLoaderUtil.getInstance().displayFromNetD(LoadingActivity.this, imageURL, imageView);// 使用ImageLoader对图片进行加装！
        }
    };

    private void getActivity() {
        HttpRequestUtils.httpRequest(this, "getActivity", RequestValue.GET_CHECK_ACTIVITY, null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        DataSupport.deleteAll(ActivityBean.class);
                        List<ActivityBean> activityBeans = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<ActivityBean>>() {
                        });
                        if (activityBeans != null && activityBeans.size() > 0)
                            DataSupport.saveAll(activityBeans);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
            }
        });
    }

    public void initEngines() {
        preferencesUtils = SharedPreferencesUtils.getInstance(LoadingActivity.this);
        user = preferencesUtils.loadObjectData(User.class);
    }
}

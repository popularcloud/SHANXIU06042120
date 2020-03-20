package com.lwc.shanxiu.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.bean.ActivityBean;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.configs.ServerConfig;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.bean.ADInfo;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.module.user.LoginOrRegistActivity;
import com.lwc.shanxiu.utils.DialogUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.view.CountDownProgressView;
import com.lwc.shanxiu.view.ImageCycleView;
import com.lwc.shanxiu.widget.CustomDialog;
import com.tencent.wstt.gt.client.AbsGTParaLoader;
import com.tencent.wstt.gt.client.GT;
import com.tencent.wstt.gt.client.InParaManager;
import com.tencent.wstt.gt.client.OutParaManager;
import com.wkp.runtimepermissions.callback.PermissionCallBack;
import com.wkp.runtimepermissions.util.RuntimePermissionUtil;

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
    private ImageCycleView ad_view;
    //记录是否跳过倒计时
    private boolean skip = false;

    int hasPermissionCount = 0;
    int noPermissionCount = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去掉窗口标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏顶部状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_loading);
//        PushAgent.getInstance(this).onAppStart();
        imageView = (ImageView) findViewById(R.id.imageView);
        ad_view = (ImageCycleView) findViewById(R.id.ad_view);
        iv_gz = (ImageView) findViewById(R.id.iv_gz);
        countdownProgressView = (CountDownProgressView) findViewById(R.id.countdownProgressView);




        /*
         *  GT usage
         * 与GT控制台连接，同时注册输入输出参数
         */
       /* GT.connect(getApplicationContext(), new AbsGTParaLoader() {

            @Override
            public void loadInParas(InParaManager inPara) {
                *//*
                 * 注册输入参数，将在GT控制台上按顺序显示
                 *//*
                inPara.register("并发线程数", "TN", "1", "2", "3");
                inPara.register("KeepAlive", "KA", "false", "true");
                inPara.register("读超时", "超时", "5000", "10000","1000");
                inPara.register("连接超时", "连超时", "5000", "10000","1000");

                // 定义默认显示在GT悬浮窗的3个输入参数
                inPara.defaultInParasInAC("并发线程数", "KeepAlive", "读超时");

                // 设置默认无效的一个入参（GT1.1支持）
                inPara.defaultInParasInDisableArea("连接超时");
            }

            @Override
            public void loadOutParas(OutParaManager outPara) {
                *//*
                 * 注册输出参数，将在GT控制台上按顺序显示
                 *//*
                outPara.register("下载耗时", "耗时");
                outPara.register("实际带宽", "带宽");
                outPara.register("singlePicSpeed", "SSPD");
                outPara.register("NumberOfDownloadedPics", "NDP");

                // 定义默认显示在GT悬浮窗的3个输出参数
                outPara.defaultOutParasInAC("下载耗时", "实际带宽", "singlePicSpeed");
            }
        });*/

        initEngines();

        countdownProgressView.setProgressListener(new CountDownProgressView.OnProgressListener() {
            @Override
            public void onProgress(int progress) {
                if (skip){
                    countdownProgressView.stop();
                    return;
                }

                if (progress == 1) {
                    gotoActivity();
                }
            }
        });
        countdownProgressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity();
            }
        });
        applyPermission();
    }
//友盟推送
//    @Override
//    public void onMessage(Intent intent) {
//        super.onMessage(intent);  //此方法必须调用，否则无法统计打开数
//        String body = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
//        Log.i(TAG, body);
//    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void applyPermission() {
        RuntimePermissionUtil.checkPermissions(this, RuntimePermissionUtil.PHONE, new PermissionCallBack() {
            @Override
            public void onCheckPermissionResult(boolean hasPermission) {
                if (hasPermission) {
                    hasPermissionCount = hasPermissionCount + 1;
                    Log.d("联网成功","通过权限"+hasPermissionCount);
                }else {
                    //显示权限不具备的界面
                    toSettingPage();
                    noPermissionCount = noPermissionCount + 1;
                    Log.d("联网成功","拒绝权限"+noPermissionCount);
                }
                onPermissionFinish();
            }
        });
        //权限检查，回调是权限申请结果
        RuntimePermissionUtil.checkPermissions(this, RuntimePermissionUtil.CAMERA, new PermissionCallBack() {
            @Override
            public void onCheckPermissionResult(boolean hasPermission) {
                if (hasPermission) {
                    hasPermissionCount = hasPermissionCount + 1;
                    Log.d("联网成功","通过权限"+hasPermissionCount);
                }else {
                    //显示权限不具备的界面
                    toSettingPage();
                    noPermissionCount = noPermissionCount + 1;
                    Log.d("联网成功","拒绝权限"+noPermissionCount);
                }

                onPermissionFinish();
            }
        });
        RuntimePermissionUtil.checkPermissions(this, RuntimePermissionUtil.LOCATION, new PermissionCallBack() {
            @Override
            public void onCheckPermissionResult(boolean hasPermission) {
                if (hasPermission) {
                    hasPermissionCount = hasPermissionCount + 1;
                    Log.d("联网成功","通过权限"+hasPermissionCount);
                }else {
                    //显示权限不具备的界面
                    toSettingPage();
                    noPermissionCount = noPermissionCount + 1;
                    Log.d("联网成功","拒绝权限"+noPermissionCount);
                }
                onPermissionFinish();
            }
        });
        RuntimePermissionUtil.checkPermissions(this, RuntimePermissionUtil.CONTACTS, new PermissionCallBack() {
            @Override
            public void onCheckPermissionResult(boolean hasPermission) {
                if (hasPermission) {
                    hasPermissionCount = hasPermissionCount + 1;
                    Log.d("联网成功","通过权限"+hasPermissionCount);
                }else {
                    //显示权限不具备的界面
                    toSettingPage();
                    noPermissionCount = noPermissionCount + 1;
                    Log.d("联网成功","拒绝权限"+noPermissionCount);
                }
                onPermissionFinish();
            }
        });
        RuntimePermissionUtil.checkPermissions(this, RuntimePermissionUtil.STORAGE, new PermissionCallBack() {
            @Override
            public void onCheckPermissionResult(boolean hasPermission) {
                if (hasPermission) {
                    hasPermissionCount = hasPermissionCount + 1;
                    Log.d("联网成功","通过权限"+hasPermissionCount);
                }else {
                    //显示权限不具备的界面
                    toSettingPage();
                    noPermissionCount = noPermissionCount + 1;
                    Log.d("联网成功","拒绝权限"+noPermissionCount);
                }
                onPermissionFinish();
            }
        });
    }

    private void toSettingPage(){
        ToastUtil.showLongToast(this,"检测到该应用部分权限未授予，将影响app的正常使用，请前往应用管理中授权");
    }

    private void gotoActivity() {
        skip = true;
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

    public void onPermissionFinish(){

        if(hasPermissionCount + noPermissionCount < 5){
            return;
        }

        preferencesUtils = SharedPreferencesUtils.getInstance(LoadingActivity.this);
        user = preferencesUtils.loadObjectData(User.class);
        init();
    }

    public void init() {
        boolean isFirstTime = preferencesUtils.loadBooleanData("isfirsttime1");
        if (!isFirstTime) {
           DialogUtil.showMessageDg(LoadingActivity.this, "用户协议和隐私政策", "同意", "查看协议详情", "请你务必谨慎阅读，充分理解'用户协议'和'隐私政策'各条款;您可以点击查看协议详情了解更多信息，或者点击同意开始接受我们的服务？", new CustomDialog.OnClickListener() {
                @Override
                public void onClick(CustomDialog dialog, int id, Object object) {
                    preferencesUtils.saveBooleanData("isfirsttime1", true);//表示已首次启动过
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "9");
                    IntentUtil.gotoActivity(LoadingActivity.this, UserGuideActivity.class, bundle);
                    dialog.dismiss();
                }
            }, new CustomDialog.OnClickListener() {
                @Override
                public void onClick(CustomDialog dialog, int id, Object object) {
                    Bundle bundle = new Bundle();
                    bundle.putString("url", ServerConfig.DOMAIN.replace("https", "http") + "/main/h5/agreement.html?isEngineer=1");
                    bundle.putString("title", "用户注册协议");
                    IntentUtil.gotoActivity(LoadingActivity.this, InformationDetailsActivity.class, bundle);
                }
            });
            return;
        }
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

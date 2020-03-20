package com.lwc.shanxiu.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.gyf.immersionbar.ImmersionBar;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.bean.Update;
import com.lwc.shanxiu.configs.BroadcastFilters;
import com.lwc.shanxiu.configs.ServerConfig;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.fragment.InformationFragment;
import com.lwc.shanxiu.fragment.KnowledgeBaseFragment;
import com.lwc.shanxiu.fragment.MapFragment;
import com.lwc.shanxiu.fragment.MineFragment;
import com.lwc.shanxiu.fragment.MyRequestQuestionFragment;
import com.lwc.shanxiu.fragment.NearOrderFragment;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.BaseFragmentActivity;
import com.lwc.shanxiu.module.bean.LeaseDevicesHistoryBean;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.module.common_adapter.FragmentsPagerAdapter;
import com.lwc.shanxiu.module.message.bean.HasMsg;
import com.lwc.shanxiu.module.message.ui.KnowledgeSearchActivity;
import com.lwc.shanxiu.module.order.ui.activity.LeaseDevicesActivity;
import com.lwc.shanxiu.module.order.ui.activity.LeaseDevicesHistoryActivity;
import com.lwc.shanxiu.module.user.LoginOrRegistActivity;
import com.lwc.shanxiu.utils.ApkUtil;
import com.lwc.shanxiu.utils.DialogUtil;
import com.lwc.shanxiu.utils.ExampleUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.SpUtil;
import com.lwc.shanxiu.utils.SystemUtil;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.utils.VersionUpdataUtil;
import com.lwc.shanxiu.widget.CustomDialog;
import com.lwc.shanxiu.widget.CustomViewPager;
import com.lwc.shanxiu.widget.DialogStyle3;
import com.lwc.shanxiu.widget.DialogStyle4;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 程序入口,主页
 *
 * @date 2015年1月8日
 * @Copyright: Copyright (c) 2015 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 */
public class MainActivity extends BaseFragmentActivity {

    @BindView(R.id.cViewPager)
    CustomViewPager cViewPager;
    @BindView(R.id.radio_map)
    RadioButton radioMap;
    @BindView(R.id.radio_order)
    RadioButton radioOrder;
    @BindView(R.id.radio_friend)
    RadioButton radioFriend;
    @BindView(R.id.radio_mine)
    RadioButton radioMine;
    @BindView(R.id.radio_knowledge)
    RadioButton radioKnowledge;
    @BindView(R.id.txt_togo)
    TextView txtTogo;
    @BindView(R.id.iv_red_dian)
    ImageView iv_red_dian;
    @BindView(R.id.iv_red_dian_map)
    ImageView iv_red_dian_map;
    private DialogStyle3 dialogStyle3 = null;



    private SharedPreferencesUtils preferencesUtils;
    private User user;

    private HashMap<Integer, Fragment> fragmentHashMap;
    private HashMap rButtonHashMap;
    private MapFragment mapFragment;
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    public static final String MESSAGE_RECEIVED_ACTION = "com.lwc.guanxiu.MESSAGE_RECEIVED_ACTION";
    public static final String RECEIVER_ACTION = "location_in_background";
    private MessageReceiver mMessageReceiver;
    private int version;
    private ProgressDialog pd;
    //极光推送参数
    public static boolean isForeground = false;
    private String oldLat = "0.0";
    private String oldLon = "0.0";
    private String isForce;
    private NearOrderFragment nearOrderFragment;
    private MineFragment mineFragment;
    public static MainActivity activity;
    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
                Intent intent2 = new Intent(BroadcastFilters.NOTIFI_GET_NEAR_ORDER);
                sendBroadcast(intent2);
                hasMessage();
            } else if (intent.getAction().equals(RECEIVER_ACTION)) {
                String lat = intent.getStringExtra("lat");
                String lon = intent.getStringExtra("lon");
//                long time = new Date().getTime();
                if (null != lat && !lat.trim().equals("") && null != lon && !lon.trim().equals("")) {
//                    float calculateLineDistance = AMapUtils.calculateLineDistance(
//                            new LatLng(Double.parseDouble(lat), Double.parseDouble(lon)), new LatLng(Double.parseDouble(oldLat), Double.parseDouble(oldLon)));
                    if (!SystemUtil.isBackground(getBaseContext()) && mapFragment != null) {
                        mapFragment.updateView();
                        oldLat = lat;
                        oldLon = lon;
                    } else {
                        updateUserInfo(lat, lon, null);
                        oldLat = lat;
                        oldLon = lon;
                    }
                }
            } else if (intent.getAction().equals(BroadcastFilters.UPDATE_PASSWORD)){
                onBackPressed();
            }
        }
    }

    public void updateUserInfo(String lat, String lon, final String autoAcceptOrder) {
        String token = SharedPreferencesUtils.getInstance(this).loadString("token");
        if (token == null || token.equals("")) {
            return;
        }
        HashMap<String, String> params = new HashMap<String, String>();
        if (TextUtils.isEmpty(autoAcceptOrder)) {
            params.put("lat", lat);
            params.put("lon", lon);
        } else {
            params.put("autoAcceptOrder", autoAcceptOrder);
        }

        HttpRequestUtils.httpRequest(this, "上传维修师经纬度", RequestValue.UP_USER_INFOR, params, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        if (TextUtils.isEmpty(autoAcceptOrder)) {
                            LLog.iNetSucceed("上传经纬度  " + response);
                        } else {
                            if (autoAcceptOrder.equals("1")) {
                                user.setAutoAcceptOrder(1);
                                //ToastUtil.showLongToast(MainActivity.this, "您已成功开启自动接单");
                              //  txtTogo.setText("自动接单中...");
                                preferencesUtils.saveObjectData(user);
                            } else {
                                user.setAutoAcceptOrder(0);
                               // ToastUtil.showLongToast(MainActivity.this, "您已成功关闭自动接单");
                               // txtTogo.setText("开启自动接单");
                                preferencesUtils.saveObjectData(user);
                            }
                        }
                        break;
                    default:
                        ToastUtil.showLongToast(MainActivity.this, common.getInfo());
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError("上传经纬度  " + e.toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        isForeground = true;
        user = preferencesUtils.loadObjectData(User.class);
        if (user == null || TextUtils.isEmpty(preferencesUtils.loadString("token"))) {
            preferencesUtils.saveString("token", "");
            IntentUtil.gotoActivityAndFinish(MainActivity.this, LoginOrRegistActivity.class);
            ToastUtil.showLongToast(MainActivity.this, "登录失效，请重新登录!");
            return;
        }

        //查询消息
        hasMessage();
        //ToastUtil.showToast(MainActivity.this,"进入onResume");
        //检查app更新
        startUptateAPP();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isForeground = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_main_new);
        ButterKnife.bind(this);
        activity = this;
        initEngines();
        initView();
        openDoubleClickToExit();
        addFragmenInList();
        addRadioButtonIdInList();
        bindViewPage(fragmentHashMap);
        cViewPager.setCurrentItem(0, false);
        radioMap.setChecked(true);
        registerMessageReceiver();
        if (user != null && user.getUserId() != null) {
            boolean spValue1 = SpUtil.getSpUtil(this.getString(R.string.spkey_file_userinfo), Context.MODE_PRIVATE).getSPValue(this.getString(R.string.spkey_file_is_ring) + user.getUserId(), true);
            boolean spValue3 = SpUtil.getSpUtil(this.getString(R.string.spkey_file_userinfo), Context.MODE_PRIVATE).getSPValue(this.getString(R.string.spkey_file_is_system_mention) + user.getUserId(), true);
            if (spValue1 && !spValue3) {
                Utils.setNotification3(this);
            } else if (spValue1 && spValue3) {
                Utils.setNotification1(this);
            } else if (!spValue1 && spValue3) {
                Utils.setNotification2(this);
            } else if (!spValue1 && !spValue3) {
                Utils.setNotification4(this);
            }
        }
        ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true)
                .navigationBarColor(R.color.white).init();
    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        filter.addAction(RECEIVER_ACTION);
        filter.addAction(BroadcastFilters.UPDATE_PASSWORD);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    /**
     * 往rButtonHashMap中添加 RadioButton Id
     */
    private void addRadioButtonIdInList() {
        rButtonHashMap = new HashMap<>();
        rButtonHashMap.put(0, radioMap);
        rButtonHashMap.put(1, radioOrder);
        rButtonHashMap.put(2, radioKnowledge);
        rButtonHashMap.put(3, radioFriend);
        rButtonHashMap.put(4, radioMine);
    }

    /**
     * 往fragmentHashMap中添加 Fragment
     */
    private void addFragmenInList() {
        mapFragment = new MapFragment();
        fragmentHashMap = new HashMap<>();
        nearOrderFragment = new NearOrderFragment();
        mineFragment = new MineFragment();
        fragmentHashMap.put(0, mapFragment);
        fragmentHashMap.put(1, nearOrderFragment);
        fragmentHashMap.put(2, new KnowledgeBaseFragment());

        //查询显示权限
        if("3".equals(user.getCompanySecrecy())){ //资讯改为问答
            // 使用代码设置drawableTop
            Drawable drawable = getResources().getDrawable(R.drawable.tab_question_selector);
            // 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());
            radioFriend.setCompoundDrawables(null, drawable, null, null);
            radioFriend.setText("问答");
            fragmentHashMap.put(3, new MyRequestQuestionFragment());
        }else{ //问答变成资讯
            // 使用代码设置drawableTop
            Drawable drawable = getResources().getDrawable(R.drawable.tab_friend_selector);
            // 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());
            radioFriend.setCompoundDrawables(null, drawable, null, null);
            radioFriend.setText("资讯");
            fragmentHashMap.put(3, new InformationFragment());
        }

        fragmentHashMap.put(4, mineFragment);
    }

    private void bindViewPage(HashMap<Integer, Fragment> fragmentList) {
        //是否滑动
        cViewPager.setPagingEnabled(false);
        cViewPager.setOffscreenPageLimit(5);
        cViewPager.setAdapter(new FragmentsPagerAdapter(getSupportFragmentManager(), fragmentList));
        cViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                cViewPager.setChecked(rButtonHashMap, position);
                startUptateAPP();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void initView() {
        user = preferencesUtils.loadObjectData(User.class);
        if (user == null) {
            preferencesUtils.saveString("token", "");
            IntentUtil.gotoActivityAndFinish(MainActivity.this, LoginOrRegistActivity.class);
            ToastUtil.showLongToast(MainActivity.this, "登录失效，请重新登录!");
            return;
        }
    }

    @Override
    public void initEngines() {
        preferencesUtils = SharedPreferencesUtils.getInstance(this);
    }

    @Override
    public void getIntentData() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMessageReceiver != null)
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        // 注销服务
//        stopLocationService();
    }

    /**
     * 启动更新APP功能
     */
    private void startUptateAPP() {
        getVersionInfor();
    }
    /**
     * 获取升级接口版本信息
     */
    private void getVersionInfor() {
        Map<String,String> params = new HashMap<>();
        params.put("clientType","maintenance");
        HttpRequestUtils.httpRequest(this, "getVersionInfo", RequestValue.UPDATE_APP, params, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        Update update = JsonUtil.parserGsonToObject(JsonUtil.getGsonValueByKey(response, "data"), Update.class);
                        if (update != null && !TextUtils.isEmpty(update.getVersionCode())) {
                            String versionCode = String.valueOf(getAppVersionCode(MainActivity.this));
                            version = Integer.valueOf(update.getVersionCode());

                            if (versionCode != null && update.getVersionCode().equals(versionCode)) {
                                return;
                            }
                            final String akpPath = update.getUrl();
                            //需要更新
                            if (VersionUpdataUtil.isNeedUpdate(MainActivity.this, version) && update.getIsValid().equals("1")) {
                                if (mineFragment != null) {
                                    mineFragment.updateVersion();
                                }
                                startUpdateDialog(update,akpPath);
                            }
                        }
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

    private void startUpdateDialog(Update update,final String akpPath){
        isForce = update.getIsForce();
        if (isForce.equals("1")) {
            DialogUtil.showUpdateAppDg(MainActivity.this, "版本更新", "立即更新", update.getMessage(), new CustomDialog.OnClickListener() {

                @Override
                public void onClick(CustomDialog dialog, int id, Object object) {
                    checkpInstallPrmission(akpPath,dialog);
                }
            });
        } else {
            DialogUtil.showMessageUp(MainActivity.this, "版本更新", "立即更新", "稍后再说", update.getMessage(), new CustomDialog.OnClickListener() {
                @Override
                public void onClick(CustomDialog dialog, int id, Object object) {
                    checkpInstallPrmission(akpPath,dialog);
                }
            }, null);
        }
    }

    /**
     * 检查是否有安装权限
     */
    private void checkpInstallPrmission(final String apkPath,CustomDialog updateDialog){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if(getPackageManager().canRequestPackageInstalls()){
                ApkUtil.downloadAPK(MainActivity.this,apkPath);
                updateDialog.dismiss();
            }else{
                updateDialog.dismiss();
                DialogUtil.showMessageUp(MainActivity.this, "授予安装权限", "立即设置", "取消", "检测到您没有授予安装应用的权限，请在设置页面授予", new CustomDialog.OnClickListener() {
                    @Override
                    public void onClick(CustomDialog dialog, int id, Object object) {
                        Uri uri = Uri.parse("package:"+getPackageName());
                        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,uri);
                        startActivityForResult(intent, 19900);
                        dialog.dismiss();
                    }
                }, null);
            }
        }else{
            ApkUtil.downloadAPK(MainActivity.this,apkPath);
            updateDialog.dismiss();
        }
    }

    public static long getAppVersionCode(Context context) {
        long appVersionCode = 0;
        try {
            PackageInfo packageInfo = context.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                appVersionCode = packageInfo.versionCode;
            } else {
                appVersionCode = packageInfo.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("", e.getMessage());
        }
        return appVersionCode;
    }



    /**
     * 显示接单对话框
     */
    private void showIfOrderDialog() {
        dialogStyle3 = new DialogStyle3(MainActivity.this);
        dialogStyle3.initDialogContent("你确定开启自动接单吗？", null, null);
        dialogStyle3.setDialogClickListener(new DialogStyle3.DialogClickListener() {
            @Override
            public void leftClick(Context context, DialogStyle3 dialog) {
                dialog.dismissDialog1();
            }

            @Override
            public void rightClick(Context context, DialogStyle3 dialog) {
                dialog.dismissDialog1();
                updateUserInfo("", "", "1");
            }
        });
        dialogStyle3.showDialog1();
    }

    /**
     * 显示关闭接单对话框
     */
    private void showCloseOrderDialog() {
        dialogStyle3 = new DialogStyle3(MainActivity.this);
        dialogStyle3.initDialogContent("你确定关闭自动接单吗？", null, null);
        dialogStyle3.setDialogClickListener(new DialogStyle3.DialogClickListener() {
            @Override
            public void leftClick(Context context, DialogStyle3 dialog) {
                dialog.dismissDialog1();
            }

            @Override
            public void rightClick(Context context, DialogStyle3 dialog) {
                updateUserInfo("", "", "0");
                dialog.dismissDialog1();
            }
        });
        dialogStyle3.showDialog1();
    }

    @OnClick({R.id.radio_map, R.id.radio_order, R.id.radio_friend, R.id.radio_mine, R.id.img_center})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radio_map:
              /*  if (cViewPager.getCurrentItem() == 0) {
                    return;
                }*/
                cViewPager.setCurrentItem(0, false);
                mapFragment.getNewestOrder();
                nearOrderFragment.goneTj();
                txtTogo.setTextColor(Color.parseColor("#333333"));
                break;
            case R.id.radio_order:
                if (cViewPager.getCurrentItem() == 1) {
                    return;
                }
                nearOrderFragment.goneTj();
                cViewPager.setCurrentItem(1, false);
                txtTogo.setTextColor(Color.parseColor("#333333"));
                break;
            case R.id.radio_friend:
                if (cViewPager.getCurrentItem() == 3) {
                    return;
                }
                nearOrderFragment.goneTj();
                cViewPager.setCurrentItem(3, false);
                txtTogo.setTextColor(Color.parseColor("#333333"));
                break;
            case R.id.radio_mine:
                if (cViewPager.getCurrentItem() == 4) {
                    return;
                }
                nearOrderFragment.goneTj();
                cViewPager.setCurrentItem(4, false);
                Intent intent2 = new Intent(BroadcastFilters.NOTIFI_GET_ORDER_COUNT);
                sendBroadcast(intent2);
                txtTogo.setTextColor(Color.parseColor("#333333"));
                break;
            case R.id.img_center:
              /*  user = preferencesUtils.loadObjectData(User.class);
                if (user != null) {
                        if (!"自动接单中...".equals(txtTogo.getText().toString())) {
                            showIfOrderDialog();
                        } else {
                            showCloseOrderDialog();
                        }
                } else {
                    preferencesUtils.saveString("token", "");
                    IntentUtil.gotoActivityAndFinish(MainActivity.this, LoginOrRegistActivity.class);
                }*/

                //IntentUtil.gotoActivity(this, KnowledgeBaseActivity.class);

                if (cViewPager.getCurrentItem() == 2) {
                    return;
                }
                nearOrderFragment.goneTj();
                cViewPager.setCurrentItem(2, false);
                txtTogo.setTextColor(Color.parseColor("#1481ff"));
            /*    for (Object radioButton : rButtonHashMap.values()){
                    ((RadioButton)radioButton).setChecked(false);
                }*/
                radioKnowledge.setChecked(true);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1991 && resultCode == RESULT_OK){
            exit();
            preferencesUtils.saveString("token", "");
            IntentUtil.gotoActivityAndFinish(this, LoginOrRegistActivity.class);
            onBackPressed();
        }else if(requestCode == ServerConfig.REQUESTCODE_KNOWLEDGESEARCH){
            if(fragmentHashMap != null && fragmentHashMap.size() == 5){
                KnowledgeBaseFragment knowledgeBaseFragment = (KnowledgeBaseFragment) fragmentHashMap.get(2);
                if(data != null){
                    String searchData = data.getStringExtra("searchData");
                    if(!TextUtils.isEmpty(searchData)){
                        knowledgeBaseFragment.onSearched(searchData);
                    }
                }

            }
        }else if(requestCode == ServerConfig.REQUESTCODE_QUESTION){
            if(fragmentHashMap != null && fragmentHashMap.size() == 5){
                MyRequestQuestionFragment myRequestQuestionFragment = (MyRequestQuestionFragment) fragmentHashMap.get(3);
                if(data != null){
                    String searchData = data.getStringExtra("searchData");
                    if(!TextUtils.isEmpty(searchData)){
                        myRequestQuestionFragment.onSearched(searchData);
                    }
                }

            }
        }else if(requestCode == 8869 && resultCode == 1111){ //扫描二维码
            if(data != null){
                String showDialog = data.getStringExtra("showDialog");
                final String token = data.getStringExtra("qrcodeIndex");
                if("1".equals(showDialog)){
                    final DialogStyle4 dialogStyle4 = new DialogStyle4(MainActivity.this);
                    dialogStyle4.setDialogClickListener(new DialogStyle4.DialogClickListener() {
                        @Override
                        public void leftClick(Context context, DialogStyle4 dialog) {
                            dialogStyle4.dismissDialog();
                        }

                        @Override
                        public void rightClick(Context context, DialogStyle4 dialog) {
                            dialogStyle4.dismissDialog();
                            startDownlaodFile(token);
                        }
                    });
                    dialogStyle4.showDialog();
                }
            }
        }else if(requestCode == 8869 && resultCode == 1112){ //扫描二维码
            if(data != null){
                final String qrcodeIndex = data.getStringExtra("qrcodeIndex");
                if(user == null){
                    ToastUtil.showToast(MainActivity.this,"登录已失效，请先登录");
                    IntentUtil.gotoActivityAndFinish(MainActivity.this, LoginOrRegistActivity.class);
                    return;
                }
                if (!TextUtils.isEmpty(qrcodeIndex)) {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("qrcodeIndex", qrcodeIndex);
                    HttpRequestUtils.httpRequest(this, "扫描租赁二维码", RequestValue.SCAN_LEASECODE, params, "GET", new HttpRequestUtils.ResponseListener() {
                        @Override
                        public void getResponseData(String response) {
                            Common common = JsonUtil.parserGsonToObject(response, Common.class);
                            switch (common.getStatus()) {
                                case "1":
                                    String data = JsonUtil.getGsonValueByKey(response, "data");
                                    if (!TextUtils.isEmpty(data)) {
                                        LeaseDevicesHistoryBean leaseDevicesHistoryBean = JsonUtil.parserGsonToObject(data,LeaseDevicesHistoryBean.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("leaseDevicesHistoryBean",leaseDevicesHistoryBean);
                                        IntentUtil.gotoActivity(MainActivity.this, LeaseDevicesHistoryActivity.class,bundle);
                                        return;
                                    } else {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("qrcodeIndex",qrcodeIndex);
                                        IntentUtil.gotoActivity(MainActivity.this, LeaseDevicesActivity.class,bundle);
                                    }
                                    break;
                                default:
                                    ToastUtil.showToast(MainActivity.this, common.getInfo());
                                    break;
                            }


                        }

                        @Override
                        public void returnException(Exception e, String msg) {
                            ToastUtil.showToast(MainActivity.this, "" + msg);
                        }
                    });
                }
            }
        }
    }

    private void startDownlaodFile(String token){
        Map<String,String> params = new HashMap<>();
        params.put("token",token);
        HttpRequestUtils.httpRequest(this, "知识库文件下载", RequestValue.KNOWLEDGE_LOGIN, params, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                if (common.getStatus().equals("1")) {
                    ToastUtil.showToast(MainActivity.this,"正在开始开始下载，请稍后");
                }else{
                    ToastUtil.showToast(MainActivity.this,common.getInfo());
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showToast(MainActivity.this,"下载出错"+msg);
            }
        });
    }

    public void exit() {
        HttpRequestUtils.httpRequest(this, "退出登录", RequestValue.EXIT, null, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
            }

            @Override
            public void returnException(Exception e, String msg) {
            }
        });
    }

    private void hasMessage() {
        DataSupport.deleteAll(HasMsg.class);
        HttpRequestUtils.httpRequest(this, "hasMessage", RequestValue.HAS_MESSAGE, null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                if (common.getStatus().equals("1")) {
                    ArrayList<HasMsg> current = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<HasMsg>>() {
                    });
                    if (current != null && current.size() > 0) {
                        DataSupport.saveAll(current);
                    //    boolean isShow = false;
                        iv_red_dian_map.setVisibility(View.GONE);
                        mapFragment.showMsg(0);
                        for (int i=0; i<current.size();i++) {
                            if ("0".equals(current.get(i).getType()) && current.get(i).isHasMessage()) {
                                iv_red_dian_map.setVisibility(View.VISIBLE);
                                if(mapFragment != null){
                                    mapFragment.showMsg(current.get(i).getCount());
                                }
                                break;
                            }
                        }

                        String versionCode = preferencesUtils.loadString("versionCode");
                        if (!TextUtils.isEmpty(versionCode) && Integer.parseInt(versionCode) > SystemUtil.getCurrentVersionCode()) {
                            iv_red_dian.setVisibility(View.VISIBLE);
                        } else {
                            iv_red_dian.setVisibility(View.GONE);
                        }
                      //  mineFragment.updateNewMsg(isShow);
                    }
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
            }
        });
    }
}

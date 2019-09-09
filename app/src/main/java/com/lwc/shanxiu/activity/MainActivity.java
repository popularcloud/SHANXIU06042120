package com.lwc.shanxiu.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.bean.Update;
import com.lwc.shanxiu.configs.BroadcastFilters;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.fragment.InformationFragment;
import com.lwc.shanxiu.fragment.MapFragment;
import com.lwc.shanxiu.fragment.MineFragment;
import com.lwc.shanxiu.fragment.NearOrderFragment;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.BaseFragmentActivity;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.module.common_adapter.FragmentsPagerAdapter;
import com.lwc.shanxiu.module.message.bean.HasMsg;
import com.lwc.shanxiu.module.message.ui.KnowledgeBaseActivity;
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
import com.lwc.shanxiu.widget.CustomDialog;
import com.lwc.shanxiu.widget.CustomViewPager;
import com.lwc.shanxiu.widget.DialogStyle3;
import com.yanzhenjie.sofia.Sofia;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
    @BindView(R.id.txt_togo)
    TextView txtTogo;
    @BindView(R.id.iv_red_dian)
    ImageView iv_red_dian;
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
       /* if (user.getAutoAcceptOrder() == 1) {
            txtTogo.setText("自动接单中...");
        } else {
            txtTogo.setText("开启自动接单");
        }*/
        hasMessage();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isForeground = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Sofia.with(this)
                .statusBarBackground(Color.parseColor("#ffffff"))
                .statusBarDarkFont();
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
        rButtonHashMap.put(2, radioFriend);
        rButtonHashMap.put(3, radioMine);
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
        fragmentHashMap.put(2, new InformationFragment());
        fragmentHashMap.put(3, mineFragment);
    }

    private void bindViewPage(HashMap<Integer, Fragment> fragmentList) {
        //是否滑动
        cViewPager.setPagingEnabled(false);
        cViewPager.setOffscreenPageLimit(4);
        cViewPager.setAdapter(new FragmentsPagerAdapter(getSupportFragmentManager(), fragmentList));
        cViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                cViewPager.setChecked(rButtonHashMap, position);
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
        startUptateAPP();
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
                            version = Integer.valueOf(update.getVersionCode());
                            String versionCode = preferencesUtils.loadString("versionCode");
                            if (version > SystemUtil.getCurrentVersionCode()) {
                                iv_red_dian.setVisibility(View.VISIBLE);
                            } else {
                                iv_red_dian.setVisibility(View.GONE);
                            }
                            if (versionCode != null && update.getVersionCode().equals(versionCode)) {
                                return;
                            }
                            final String akpPath = update.getUrl();
                            //需要更新
                            if (version > SystemUtil.getCurrentVersionCode() && update.getIsValid().equals("1")) {
                                preferencesUtils.saveString("versionCode", update.getVersionCode());
                                if (mineFragment != null) {
                                    mineFragment.updateVersion();
                                }
                                isForce = update.getIsForce();
                                if (isForce.equals("1")) {
                                    DialogUtil.showUpdateAppDg(MainActivity.this, "版本更新", "立即更新", update.getMessage(), new CustomDialog.OnClickListener() {

                                        @Override
                                        public void onClick(CustomDialog dialog, int id, Object object) {
                                            downloadAPK(akpPath);
                                            dialog.dismiss();
                                        }
                                    });
                                } else {
                                    DialogUtil.showUpdateAppDg(MainActivity.this, "版本更新",  "立即更新", "稍后再说", update.getMessage(), new CustomDialog.OnClickListener() {
                                        @Override
                                        public void onClick(CustomDialog dialog, int id, Object object) {
                                            downloadAPK(akpPath);
                                            dialog.dismiss();
                                        }
                                    }, null);
                                }
                            }
                        } else {
                            preferencesUtils.saveString("versionCode", "0");
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
    private int lastProgress;
    private void downloadAPK(final String path) {
        pd = new ProgressDialog(this, AlertDialog.THEME_HOLO_LIGHT);
        pd.setCancelable(false);
        pd.setMessage("正在下载最新版APP，请稍后...");
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.show();
        pd.setProgress(0);

        new Thread() {
            @SuppressWarnings("resource")
            public void run() {
                try {
                    InputStream input = null;
                    HttpURLConnection urlConn = null;
                    URL url = new URL(path);
                    urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setRequestProperty("Accept-Encoding", "identity");
                    urlConn.setReadTimeout(10000);
                    urlConn.setConnectTimeout(10000);
                    input = urlConn.getInputStream();
                    int total = urlConn.getContentLength();
                    File sd = Environment.getExternalStorageDirectory();
                    boolean can_write = sd.canWrite();
                    if (!can_write) {
                        ToastUtil.showLongToast(MainActivity.this, "SD卡不可读写");
                    } else {
                        File file = null;
                        OutputStream output = null;
                        String savedFilePath = sd.getAbsolutePath()+"/shanxiu.apk";
                        file = new File(savedFilePath);
                        output = new FileOutputStream(file);
                        byte[] buffer = new byte[1024];
                        int temp = 0;
                        int read = 0;
                        while ((temp = input.read(buffer)) != -1) {
                            output.write(buffer, 0, temp);
                            read += temp;
                            float progress = ((float) read) / total;
                            int progress_int = (int) (progress * 100);
                            if (lastProgress != progress_int) {
                                lastProgress = progress_int;
                                final int pro = progress_int;
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        pd.setProgress(pro);
                                    }
                                });
                            }
                        }
                        output.flush();
                        output.close();
                        input.close();
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                if (pd != null && pd.isShowing()) {
                                    pd.setProgress(100);
                                    pd.dismiss();
                                }
                            }
                        });
                        ApkUtil.installApk(file);
                    }
                } catch (Exception e) {
                }
            }
        }.start();
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
                if (cViewPager.getCurrentItem() == 0) {
                    return;
                }
                cViewPager.setCurrentItem(0, false);
                mapFragment.getNewestOrder();
                nearOrderFragment.goneTj();
                break;
            case R.id.radio_order:
                if (cViewPager.getCurrentItem() == 1) {
                    return;
                }
                nearOrderFragment.goneTj();
                cViewPager.setCurrentItem(1, false);
                break;
            case R.id.radio_friend:
                if (cViewPager.getCurrentItem() == 2) {
                    return;
                }
                nearOrderFragment.goneTj();
                cViewPager.setCurrentItem(2, false);
                break;
            case R.id.radio_mine:
                if (cViewPager.getCurrentItem() == 3) {
                    return;
                }
                nearOrderFragment.goneTj();
                cViewPager.setCurrentItem(3, false);
                Intent intent2 = new Intent(BroadcastFilters.NOTIFI_GET_ORDER_COUNT);
                sendBroadcast(intent2);
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

                IntentUtil.gotoActivity(this, KnowledgeBaseActivity.class);
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
        }
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
                        boolean isShow = false;
                        for (int i=0; i<current.size();i++) {
                            if (current.get(i).getHasMessage()) {
                                isShow = true;
                                break;
                            }
                        }
                        if (isShow) {
                            iv_red_dian.setVisibility(View.VISIBLE);
                        } else {
                            String versionCode = preferencesUtils.loadString("versionCode");
                            if (!TextUtils.isEmpty(versionCode) && Integer.parseInt(versionCode) > SystemUtil.getCurrentVersionCode()) {
                                iv_red_dian.setVisibility(View.VISIBLE);
                            } else {
                                iv_red_dian.setVisibility(View.GONE);
                            }
                        }
                        mineFragment.updateNewMsg(isShow);
                    }
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
            }
        });
    }
}

package com.lwc.shanxiu.map;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.configs.SophixStubApplication;
import com.lwc.shanxiu.configs.TApplication;
import com.lwc.shanxiu.controler.http.NetManager;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.service.Alarmreceiver;
import com.lwc.shanxiu.service.NotificationReceiver;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.SystemUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.litepal.crud.DataSupport;

import java.util.HashMap;
import java.util.List;

import cn.jpush.android.service.PushService;
import okhttp3.Call;

/**
 * <p>
 * 创建时间：2016/10/27
 * 项目名称：LocationServiceDemo
 * <p>
 * 类说明：后台服务定位
 * <p>
 * <p>
 * modeified by liangchao , on 2017/01/17
 * update:
 * 1. 只有在由息屏造成的网络断开造成的定位失败时才点亮屏幕
 * 2. 利用notification机制增加进程优先级
 * </p>
 */
public class LocationService extends NotiService {

    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    GeocodeSearch geocoderSearch =new GeocodeSearch(TApplication.context);

    /**
     * 处理息屏关掉wifi的delegate类
     */
    private IWifiAutoCloseDelegate mWifiAutoCloseDelegate = new WifiAutoCloseDelegate();

    /**
     * 记录是否需要对息屏关掉wifi的情况进行处理
     */
    private boolean mIsWifiCloseable = false;
    public static Notification notification;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        applyNotiKeepMech(); //开启利用notification提高进程优先级的机制

        Intent broadcastIntent = new Intent(this.getApplicationContext(), NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.
                getBroadcast(this.getApplicationContext(), 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // 在API16之后，可以使用build()来进行Notification的构建 Notification
        notification = new Notification.Builder(this.getApplicationContext()).setContentTitle("密修工程师").setContentText("工程师在线").setContentIntent(pendingIntent).setSmallIcon(R.mipmap.ic_app_launcher).setWhen(System.currentTimeMillis()).build();
        startForeground(1, notification);

        if (mWifiAutoCloseDelegate.isUseful(getApplicationContext())) {
            mIsWifiCloseable = true;
            mWifiAutoCloseDelegate.initOnServiceStarted(getApplicationContext());
        }


        Log.e("ceshi","开始定位");

        startLocation();

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //读者可以修改此处的Minutes从而改变提醒间隔时间
        //此处是设置每隔55分钟启动一次
        //这是55分钟的毫秒数
        int Minutes = 8 * 60 * 1000;
        //SystemClock.elapsedRealtime()表示1970年1月1日0点至今所经历的时间
        long triggerAtTime = SystemClock.elapsedRealtime() + Minutes;
        //此处设置开启AlarmReceiver这个Service
        Intent i = new Intent(this, Alarmreceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        //ELAPSED_REALTIME_WAKEUP表示让定时任务的出发时间从系统开机算起，并且会唤醒CPU。
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);


        geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {

            @Override

            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

                String c = regeocodeResult.getRegeocodeAddress().getCity();

                String a = regeocodeResult.getRegeocodeAddress().getFormatAddress();

                //这里你可以点出很多信息，根据情况自己去获取

                com.lwc.shanxiu.bean.Location location = new com.lwc.shanxiu.bean.Location();

                location.setStrValue(regeocodeResult.toString());
                location.setLongitude(regeocodeResult.getRegeocodeQuery().getPoint().getLongitude());
                location.setLatitude(regeocodeResult.getRegeocodeQuery().getPoint().getLatitude());
                location.setCityName(regeocodeResult.getRegeocodeAddress().getCity());
                location.setCityCode(regeocodeResult.getRegeocodeAddress().getCityCode());
                SharedPreferencesUtils.getInstance(getApplicationContext()).saveObjectData(location);
                Log.e("ceshi","地址逆定理 定位结果：getCityCode" + regeocodeResult.getRegeocodeAddress().getCityCode()+"longitude："+regeocodeResult.getRegeocodeQuery().getPoint().getLongitude());
            }

            @Override

            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }

        });


        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        unApplyNotiKeepMech();
        stopLocation();

        super.onDestroy();
    }

    /**
     * 启动定位
     */
    void startLocation() {
        stopLocation();

        if (null == mLocationClient) {
            mLocationClient = new AMapLocationClient(this.getApplicationContext());
        }

        mLocationOption = new AMapLocationClientOption();
        // 使用连续
        mLocationOption.setOnceLocation(true);
        mLocationOption.setLocationCacheEnable(true);
//        // 每10秒定位一次
//        mLocationOption.setInterval(540000);

        // 地址信息
        mLocationOption.setNeedAddress(false);

        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.setLocationListener(locationListener);
        mLocationClient.startLocation();
    }

    /**
     * 停止定位
     */
    void stopLocation() {
        if (null != mLocationClient) {
            mLocationClient.stopLocation();
        }
    }

    private double oldLat = 0;
    private double oldLon = 0;
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {


            LatLonPoint point =new LatLonPoint(aMapLocation.getLatitude(),aMapLocation.getLongitude());
            RegeocodeQuery query =new RegeocodeQuery(point, 200,GeocodeSearch.AMAP);
            geocoderSearch.getFromLocationAsyn(query);

            //发送结果的通知
            sendLocationBroadcast(aMapLocation);

            if (!mIsWifiCloseable) {
                return;
            }

            if (aMapLocation.getErrorCode() == AMapLocation.LOCATION_SUCCESS) {
                mWifiAutoCloseDelegate.onLocateSuccess(getApplicationContext(), PowerManagerUtil.getInstance().isScreenOn(getApplicationContext()), NetUtil.getInstance().isMobileAva(getApplicationContext()));
            } else {
                mWifiAutoCloseDelegate.onLocateFail(getApplicationContext(), aMapLocation.getErrorCode(), PowerManagerUtil.getInstance().isScreenOn(getApplicationContext()), NetUtil.getInstance().isWifiCon(getApplicationContext()));
            }

        }

        private void sendLocationBroadcast(final AMapLocation aMapLocation) {
            //记录信息并发送广播
            Log.e("ceshi","联网 定位结果：" + aMapLocation.toString());
            new Thread(){
                public void run(){
            // 做网络请求操作
                    if (aMapLocation != null) {
                        double lat = aMapLocation.getLatitude();
                        double lon = aMapLocation.getLongitude();
                        if (lat == 0 || lon == 0) {
                            lat = oldLat;
                            lon = oldLon;
                        }
                        if (lon == 0 || lat == 0) {
                            return;
                        }
                        Intent service = new Intent(getBaseContext(), PushService.class);
                        getBaseContext().startService(service);
                        List<User> list = DataSupport.findAll(User.class);

        //                    String token = SharedPreferencesUtils.getInstance(getApplicationContext()).loadString("token");
                        if (list == null || list.size() == 0) {
                            return;
                        }
                        if (!com.lwc.shanxiu.utils.NetUtil.isNetworkAvailable(getBaseContext())) {
                            try {
                                LLog.iNetSucceed("网络不可用，请检查手机网络");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        String token = list.get(0).getToken();
                        LLog.iNetSucceed("联网 定位结果：" + token);
                        oldLat = lat;
                        oldLon = lon;
                        HashMap<String, String> params = new HashMap<>();
                        params.put("lat", lat + "");
                        params.put("lon", lon + "");
/*
                        com.lwc.shanxiu.bean.Location location = new com.lwc.shanxiu.bean.Location();

                       location.setStrValue(aMapLocation.toString());
                        location.setLongitude(aMapLocation.getLongitude());
                        location.setLatitude(aMapLocation.getLatitude());
                        location.setCityName(aMapLocation.getCity());
                        location.setCityCode(aMapLocation.getCityCode());
                        SharedPreferencesUtils.getInstance(getApplicationContext()).saveObjectData(location);*/

                        String DEVICE_ID = Utils.getDeviceId(getBaseContext());
                        LLog.iNetSucceed("方法: 上传经纬度" + ",参数：" + params + ",token" + token +",city" + aMapLocation.getCity()+",citycode" + aMapLocation.getCityCode());
                        Log.e("ceshi","方法: 上传经纬度" + ",参数：" + params + ",token" + token +",city" + aMapLocation.getCity()+",citycode" + aMapLocation.getCityCode());
                        OkHttpUtils.post().url(NetManager.getUrl(RequestValue.UP_USER_INFOR)).params(params).addHeader("token", token).addHeader("code", DEVICE_ID).addHeader("phoneSystem", "ANDROID").addHeader("versionCode", SystemUtil.getCurrentVersionCode() + "").build().execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                LLog.eNetError(e.toString());
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                LLog.iNetSucceed("上传经纬度 返回结果：" + response);
                                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                                if (common.getStatus().equals("2") || common.getStatus().equals("3") || common.getInfo().equals("令牌错误")) {
                                    SharedPreferencesUtils.getInstance(getApplicationContext()).saveString("token", "");
                                    stopLocation();
                                }
                            }
                        });
                    }
                }
            }.start();
        }
    };

}

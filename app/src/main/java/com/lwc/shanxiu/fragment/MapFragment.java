package com.lwc.shanxiu.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.google.gson.reflect.TypeToken;
import com.gyf.immersionbar.ImmersionBar;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.InformationDetailsActivity;
import com.lwc.shanxiu.activity.MainActivity;
import com.lwc.shanxiu.activity.NavigationActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.configs.BroadcastFilters;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.ADInfo;
import com.lwc.shanxiu.module.bean.Order;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.module.message.ui.MyMsgActivity;
import com.lwc.shanxiu.module.order.ui.activity.OrderDetailActivity;
import com.lwc.shanxiu.module.partsLib.ui.activity.PartsMainActivity;
import com.lwc.shanxiu.module.zxing.ui.CaptureActivity;
import com.lwc.shanxiu.utils.DialogUtil;
import com.lwc.shanxiu.utils.DisplayUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.MyMapUtil;
import com.lwc.shanxiu.utils.RouteTool;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.SystemUtil;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.view.ImageCycleView;
import com.lwc.shanxiu.view.TileButton;
import com.lwc.shanxiu.widget.CircleImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapFragment extends BaseFragment implements AMap.OnMarkerClickListener, AMap.OnInfoWindowClickListener, AMap.OnMarkerDragListener, AMap.OnMapLoadedListener,
        OnClickListener, AMap.InfoWindowAdapter, LocationSource, SensorEventListener, RouteSearch.OnRouteSearchListener {


    @BindView(R.id.img_icon)
    CircleImageView imgIcon;
    @BindView(R.id.txtMaintainName)
    TextView txtMaintainName;
    @BindView(R.id.imgRight)
    ImageView imgRight;
    @BindView(R.id.txtOrderStatus)
    TextView txtOrderStatus;
    @BindView(R.id.txtDistance)
    TextView txtDistance;
    @BindView(R.id.txtDaohang)
    TextView txtDaohang;
    @BindView(R.id.imgRightTwo)
    ImageView imgRightTwo;
    @BindView(R.id.iv_red_dian)
    TextView iv_red_dian;
    @BindView(R.id.ad_view)
    ImageCycleView mAdView;//轮播图

    RelativeLayout llGetOrderMention;
    /**
     * 地图对象
     */
    private AMap aMap;
    /**
     * 地图控件
     */
    private TextureMapView mapView;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private long lastTime = 0;
    private final int TIME_SENSOR = 100;
    private Marker mGPSMarker;
    private float mAngle;
    private TileButton img_location;
    private final List<Marker> mapMarkers = new ArrayList<>();
    private boolean isFirst = true;
    private OnLocationChangedListener mListener;
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private ArrayList<ADInfo> infos = new ArrayList<>();//广告轮播图
    /**
     * 用户位移超过100米的距离就进行位置更新提交
     */
//    private final float GO_DISTANCE = 10;
    private Marker marker;
    private RouteSearch routeSearch;

    //最新订单
    private Order newestOrder = null;
    private ImageLoaderUtil imageLoaderUtil;

    private User user = null;
    private SharedPreferencesUtils preferencesUtils;

    @Override
    protected View getViews() {
        return View.inflate(getActivity(), R.layout.fragment_map, null);
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        setTitle("地图");
        img_location = (TileButton) view_Parent.findViewById(R.id.img_location);
        llGetOrderMention = (RelativeLayout) view_Parent.findViewById(R.id.ll_get_order_mention);

        // 地图=====================================
        mapView = (TextureMapView) view_Parent.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState); // 此方法必须重写

        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        aMap = mapView.getMap();
        if (aMap != null) {
            LatLng localLatLng = new LatLng(113.75, 23.04);
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localLatLng, 18));
        }
    }
    /**
     * 启动定位
     */
    private void startLocation() {
        mLocationClient.startLocation();
    }
    /**
     * 初始化定位
     */
    private void initLocation() {
        if (getContext() != null) {
            MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
            myLocationStyle.strokeColor(Color.parseColor("#2E1481FF"));
            myLocationStyle.radiusFillColor(Color.parseColor("#2E1481FF"));// 设置圆形的填充颜色
            myLocationStyle.strokeWidth(1);
            myLocationStyle.anchor(0.5f, 0.5f);
            myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.location_marker)));

            mLocationClient = new AMapLocationClient(getContext());
            //设置定位回调监听
            mLocationClient.setLocationListener(aMapLocationListener);
            //初始化AMapLocationClientOption对象
            mLocationOption = new AMapLocationClientOption();
            //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
           // mLocationOption.setInterval(3 * 1000); //10秒
//            mLocationOption.setHttpTimeOut(20 * 1000);
            mLocationOption.setNeedAddress(true);
            mLocationOption.setOnceLocation(true);
            mLocationOption.setOnceLocationLatest(true);
            aMap.setMyLocationStyle(myLocationStyle);
            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
        }
    }

    @Override
    protected void widgetListener() {
        img_location.setOnClickListener(this);
        llGetOrderMention.setOnClickListener(this);
    }

    @Override
    protected void init() {
        imageLoaderUtil = ImageLoaderUtil.getInstance();
        preferencesUtils = SharedPreferencesUtils.getInstance(getContext());
        user = preferencesUtils.loadObjectData(User.class);
        initLocation();
        startLocation();

        flushData();
        routeSearch = new RouteSearch(getActivity());
        routeSearch.setRouteSearchListener(this);
        MyMapUtil.setUpMap(aMap, mGPSMarker, this);
        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {

            @Override
            public void onTouch(MotionEvent arg0) {
                switch (arg0.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        for (int i = 0; i < mapMarkers.size(); i++) {
                            mapMarkers.get(i).hideInfoWindow();
                        }
                        break;
                    default:
                        break;
                }
            }
        });

        mGPSMarker = aMap.addMarker(
                new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.location_marker)))
                        .anchor((float) 0.5, (float) 0.5));
    }

    /**
     * 刷新数据
     *
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    private void flushData() {
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        }
        Location myLocation = aMap.getMyLocation();
        if (myLocation != null) {
            aMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                            new CameraPosition(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()), 18, 30, 0)), 1000,
                    null);// aMap.getCameraPosition().zoom
        } else {
            isFirst = true;
        }
    }

    @Override
    public void initGetData() {
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        isFirst = true;
        super.onResume();
        mapView.onResume();
        registerSensorListener();
        getNewestOrder();
        getBannerData();

    }


    /**
     * 获取配件库首页轮播
     */
    public void getBannerData(){
        HttpRequestUtils.httpRequest(getActivity(), "getBannerData", RequestValue.GET_ADVERTISING+"?local=1&role=2",null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        ArrayList<ADInfo> adInfoArrayList = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<ADInfo>>() {});
                        infos.clear();
                        infos.addAll(adInfoArrayList);
                        if ( infos != null && infos.size() > 0) {
                            mAdView.setImageResources(infos, mAdCycleViewListener);
                            if(infos.size() > 1){
                                mAdView.startImageCycle();
                            }else{
                                mAdView.pushImageCycle();
                            }
                        }
                        break;
                    default:
                        ToastUtil.showToast(getActivity(),common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showToast(getActivity(),msg);
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
            Bundle bundle = new Bundle();
            if (!TextUtils.isEmpty(info.getAdvertisingUrl()))
                bundle.putString("url", info.getAdvertisingUrl());
            if (!TextUtils.isEmpty(info.getAdvertisingTitle()))
                bundle.putString("title", info.getAdvertisingTitle());
            IntentUtil.gotoActivity(getActivity(), InformationDetailsActivity.class, bundle);
        }

        @Override
        public void displayImage(final String imageURL, final ImageView imageView) {
            ImageLoaderUtil.getInstance().displayFromNetDCircular(getActivity(), imageURL, imageView,R.drawable.image_default_picture);// 使用ImageLoader对图片进行加装！
        }
    };


    public void showMsg(int msgCount){
        if(iv_red_dian != null){
            if(msgCount > 0){
                iv_red_dian.setVisibility(View.VISIBLE);
                if(msgCount < 99){
                    iv_red_dian.setText(String.valueOf(msgCount));
                }else{
                    iv_red_dian.setText("...");
                }

            }else{
                iv_red_dian.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && getActivity() != null){
            ImmersionBar.with(getActivity())
                    .statusBarColor(R.color.white)
                    .statusBarDarkFont(true).init();
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        if (this.marker != null) {
            this.marker.hideInfoWindow();
        }
        mapView.onPause();
        deactivate();

        if(mAdView != null){
            mAdView.pushImageCycle();
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
//        aMap.setCameraPosition(aMap.getCameraPosition());//保存地图状态
        super.onDestroy();
        mapView.onDestroy();

        if(mAdView != null){
            mAdView.pushImageCycle();
        }
    }

    /**
     * 对marker标注点点击响应事件
     */
    @Override
    public boolean onMarkerClick(final Marker marker) {
        this.marker = marker;
        return false;
    }

    /**
     * 监听点击infowindow窗口事件回调
     *
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    @Override
    public void onInfoWindowClick(Marker marker) {
        marker.hideInfoWindow();
    }

    /**
     * 监听拖动marker时事件回调
     */
    @Override
    public void onMarkerDrag(Marker marker) {
    }

    /**
     * 监听拖动marker结束事件回调
     */
    @Override
    public void onMarkerDragEnd(Marker marker) {
    }

    /**
     * 监听开始拖动marker事件回调
     */
    @Override
    public void onMarkerDragStart(Marker marker) {
    }

    /**
     * 监听amap地图加载成功事件回调
     */
    @Override
    public void onMapLoaded() {
        // 设置所有maker显示在当前可视区域地图中
        if (isFirst && currentBestLocation != null) {
            LLog.i("onMapLoaded  获取地图定位画图");
            if (mListener != null) {
                aMap.clear();
                mListener.onLocationChanged(currentBestLocation);//
            }
            // 显示系统小蓝点
            if (mGPSMarker != null) {
                mGPSMarker.setPosition(new LatLng(currentBestLocation.getLatitude(), currentBestLocation.getLongitude()));
            }
            aMap.animateCamera(
                    CameraUpdateFactory.newCameraPosition(new CameraPosition(
                            new LatLng(currentBestLocation.getLatitude(), currentBestLocation.getLongitude()), 18, 30, 0)),
                    1000, null);

        }
    }

    /**
     * 监听自定义infowindow窗口的infocontents事件回调
     */
    @Override
    public View getInfoContents(Marker marker) {
        View infoContent = getActivity().getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        boolean bool = render(marker, infoContent);
        if (!bool) {
            return null;
        }
        return infoContent;
    }

    /**
     * 监听自定义infowindow窗口的infowindow事件回调
     */
    @Override
    public View getInfoWindow(Marker marker) {
        View infoWindow = getActivity().getLayoutInflater().inflate(R.layout.custom_info_window, null);
        render(marker, infoWindow);
        return infoWindow;
    }

    /**
     * 自定义infowinfow窗口
     *
     * @param marker
     * @param view
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public boolean render(Marker marker, View view) {
//        if (newestOrder != null && marker.getPosition().latitude == Double.valueOf(newestOrder.getOrderLatitude()) && marker.getPosition().longitude == Double.valueOf(newestOrder.getOrderLongitude()) )

        if (newestOrder != null) {// TODO 以下需要自定义
            TextView name = ((TextView) view.findViewById(R.id.name));
            TextView status = ((TextView) view.findViewById(R.id.status));
            TextView company = ((TextView) view.findViewById(R.id.company));
            TextView phone = ((TextView) view.findViewById(R.id.phone));
            TextView skills = ((TextView) view.findViewById(R.id.skills));

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) skills.getLayoutParams();
            layoutParams.width = DisplayUtil.getScreenWidth(getActivity()) - DisplayUtil.dip2px(getActivity(), 100);
            if (newestOrder != null) {
                if (!TextUtils.isEmpty(newestOrder.getUserRealname())) {
                    name.setText("姓名:" + newestOrder.getUserRealname());
                } else {
                    name.setText("姓名:" + "暂无");
                }
                skills.setVisibility(View.GONE);
                status.setText("");
                if (newestOrder.getUserCompanyName() != null && !TextUtils.isEmpty(newestOrder.getUserCompanyName())) {
                    company.setText("公司:" + newestOrder.getUserCompanyName());
                } else {
                    company.setText("公司:" + "暂无");
                }

                if (newestOrder.getUserPhone() != null && !TextUtils.isEmpty(newestOrder.getUserPhone())) {
                    phone.setText("电话:" + newestOrder.getUserPhone());
                } else {
                    phone.setText("电话:" + "暂无");
                }
            } else {
                if (!TextUtils.isEmpty(user.getMaintenanceName())) {
                    name.setText("姓名:" + user.getMaintenanceName());
                } else {
                    name.setText("姓名:" + "暂无");
                }
                skills.setVisibility(View.GONE);
                status.setText("");
                if (!TextUtils.isEmpty(user.getCompanyName())) {
                    company.setText("公司:" + user.getCompanyName());
                } else {
                    company.setText("公司:" + "暂无");
                }

                if (!TextUtils.isEmpty(user.getMaintenancePhone())) {
                    phone.setText("电话:" + user.getMaintenancePhone());
                } else {
                    phone.setText("电话:" + "暂无");
                }
            }
            return true;
        }
        return false;
    }
    public AMapLocation currentBestLocation;
    AMapLocationListener aMapLocationListener = new AMapLocationListener() {

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            LLog.i("    onLocationChanged   ");
            if (mListener != null && aMapLocation != null && aMapLocation.getLatitude() != 0.0) {
                currentBestLocation = aMapLocation;
                com.lwc.shanxiu.bean.Location location = new com.lwc.shanxiu.bean.Location();
                location.setStrValue(aMapLocation.toString());
                location.setLongitude(aMapLocation.getLongitude());
                location.setLatitude(aMapLocation.getLatitude());
                preferencesUtils.saveObjectData(location);
                mGPSMarker.setPosition(new LatLng(currentBestLocation.getLatitude(), currentBestLocation.getLongitude()));
                aMap.clear();
                mListener.onLocationChanged(currentBestLocation);// 显示系统小蓝点
                user.setLat(String.valueOf(currentBestLocation.getLatitude()));
                user.setLon(String.valueOf(currentBestLocation.getLongitude()));
                isFirst = false;
                aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                new LatLng(currentBestLocation.getLatitude(), currentBestLocation.getLongitude()), 18, 30, 0)),
                        1000, null);
                updateUserData(currentBestLocation);
                setJuli();
            }
        }
    };

    public void updateView() {
        if (mapView == null || currentBestLocation == null) {
            return;
        }
        startLocation();
//        getNewestOrder();
    }

    public void updateUserData(AMapLocation aLocation) {
        if (Utils.isFastClick(1000)) {
            return;
        }

        SharedPreferencesUtils.setParam(getContext(),"address_city",aLocation.getCity().replace("市",""));
        HashMap<String, String> params = new HashMap<String, String>();
        if (aLocation != null && aLocation.getLatitude() > 0) {
            params.put("lat", aLocation.getLatitude() + "");
            params.put("lon", aLocation.getLongitude() + "");
        } else {
            return;
        }
        HttpRequestUtils.httpRequest(getActivity(), "updateLocation", RequestValue.UP_USER_INFOR, params, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                LLog.iNetSucceed("上传经纬度  " + response);
                try {
                    Intent intent = new Intent(BroadcastFilters.NOTIFI_GET_NEAR_ORDER);
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                } catch (Exception e){}
                getNewestOrder();
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError("updateUserInfo  " + e.toString());
            }
        });

    }

    /**
     * 高德地图 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        //给定位客户端对象设置定位参数
        if (mLocationClient != null) {
            mLocationClient.setLocationOption(mLocationOption);
            //启动定位
            mLocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
//        mListener = null;
        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
    }

    /**
     * 注册传感器监听
     */
    public void registerSensorListener() {
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * 取消注册传感器监听
     */
    public void unRegisterSensorListener() {
        mSensorManager.unregisterListener(this, mSensor);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
    }

    // 回调函数处理逻辑
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (System.currentTimeMillis() - lastTime < TIME_SENSOR) {
            return;
        }
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ORIENTATION: {
                float x = event.values[0];

                x += getScreenRotationOnPhone(getActivity());
                x %= 360.0F;
                if (x > 180.0F)
                    x -= 360.0F;
                else if (x < -180.0F)
                    x += 360.0F;
                if (Math.abs(mAngle - 90 + x) < 3.0f) {
                    break;
                }
                mAngle = x;
                if (mGPSMarker != null) {
                    mGPSMarker.setRotateAngle(-mAngle);
//                    aMap.invalidate();
                    aMap.reloadMap();
                }
                lastTime = System.currentTimeMillis();
            }
        }

    }

    /**
     * 获取当前屏幕旋转角度
     *
     * @return 0表示是竖屏; 90表示是左横屏; 180表示是反向竖屏; 270表示是右横屏
     */
    public static int getScreenRotationOnPhone(Context context) {
        if (context == null) {
            return 0;
        }
        final Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        switch (display.getRotation()) {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return -90;
        }
        return 0;
    }
    private WalkRouteResult walkRouteResult;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_location:
                startLocation();
//                mapView.onResume();
                if (currentBestLocation != null) {
                    aMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                            new CameraPosition(new LatLng(Double.valueOf(currentBestLocation.getLatitude()), Double.valueOf(currentBestLocation.getLongitude())),
                                    18, 30, 0)),
                            1000, null);
                }
                break;
            case R.id.ll_get_order_mention:// 进入订单详情
                if (newestOrder != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("orderId", newestOrder.getOrderId());
                    IntentUtil.gotoActivity(getActivity(), OrderDetailActivity.class, bundle);
                }
                break;
            case R.id.txtDaohang:
                String selectMap = preferencesUtils.loadString("selectMap");
                int size = getMapApk().size();
                if (!TextUtils.isEmpty(selectMap) || size == 1) {
                    if (!TextUtils.isEmpty(selectMap)) {
                        if (getMapApk().indexOf(selectMap) >= 0) {
                            Utils.toMap(getActivity(), selectMap, newestOrder.getOrderContactAddress(), Double.parseDouble(newestOrder.getOrderLatitude()), Double.parseDouble(newestOrder.getOrderLongitude()));
                        } else if (size == 1){
                            Utils.toMap(getActivity(), getMapApk().get(0), newestOrder.getOrderContactAddress(), Double.parseDouble(newestOrder.getOrderLatitude()), Double.parseDouble(newestOrder.getOrderLongitude()));
                        } else if (size > 1){
                            DialogUtil.showNavigateDg(getActivity(), newestOrder, getMapApk());
                        } else {
                            //TODO 手机上无地图软件
                            Bundle bund = new Bundle();
                            bund.putDouble("mLat", currentBestLocation.getLatitude());
                            bund.putDouble("mLon", currentBestLocation.getLongitude());
                            bund.putDouble("eLat", Double.parseDouble(newestOrder.getOrderLatitude()));
                            bund.putDouble("eLon", Double.parseDouble(newestOrder.getOrderLongitude()));
                            IntentUtil.gotoActivity(getActivity(), NavigationActivity.class, bund);
                        }
                    } else {
                        Utils.toMap(getActivity(), getMapApk().get(0), newestOrder.getOrderContactAddress(), Double.parseDouble(newestOrder.getOrderLatitude()), Double.parseDouble(newestOrder.getOrderLongitude()));
                    }
                } else if (size > 1){
                    DialogUtil.showNavigateDg(getActivity(), newestOrder, getMapApk());
                } else {
                    //TODO 手机上无地图软件
                    Bundle bund = new Bundle();
                    bund.putDouble("mLat", currentBestLocation.getLatitude());
                    bund.putDouble("mLon", currentBestLocation.getLongitude());
                    bund.putDouble("eLat", Double.parseDouble(newestOrder.getOrderLatitude()));
                    bund.putDouble("eLon", Double.parseDouble(newestOrder.getOrderLongitude()));
                    IntentUtil.gotoActivity(getActivity(), NavigationActivity.class, bund);                }
            default:
                break;
        }

    }

    @Override
    protected void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(BroadcastFilters.UPDATE_USER_INFO_ICON)) {
            flushData();
        } else if (intent.getAction().equals(BroadcastFilters.UPDATE_USER_LOGIN_SUCCESSED)) {

        } else if (intent.getAction().equals(BroadcastFilters.NOTIFI_NEARBY_PEOPLE)) {
        } else if (intent.getAction().equals(MainActivity.RECEIVER_ACTION)) {
            if (!isFirst && currentBestLocation != null) {
                mGPSMarker.setPosition(new LatLng(currentBestLocation.getLatitude(), currentBestLocation.getLongitude()));
                if (mListener != null)
                    mListener.onLocationChanged(currentBestLocation);// 显示系统小蓝点
                user.setLat(String.valueOf(currentBestLocation.getLatitude()));
                user.setLon(String.valueOf(currentBestLocation.getLongitude()));
                aMap.animateCamera( CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                new LatLng(currentBestLocation.getLatitude(), currentBestLocation.getLongitude()), 18, 30, 0)),
                        1000, null);
            }
        } else if (intent.getAction().equals(BroadcastFilters.NOTIFI_ORDER_GETTED_MENTION)) {
            super.onReceive(context, intent);
        }
    }

    @Override
    public void onBusRouteSearched(BusRouteResult arg0, int arg1) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult arg0, int arg1) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int rCode) {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        LLog.i("路线规划回调函数   onWalkRouteSearched    result " + result + "   rCode " + rCode);
        if (rCode == 1000 || rCode == 0) {
            if (result != null && result.getPaths() != null && result.getPaths().size() > 0) {
                walkRouteResult = result;
                WalkPath walkPath = walkRouteResult.getPaths().get(0);
                aMap.clear();// 清理地图上的所有覆盖物
                // WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(getActivity(), aMap, walkPath, walkRouteResult.getStartPos(),
                // walkRouteResult.getTargetPos());
                RouteTool walkRouteOverlay = new RouteTool(getActivity(), aMap, walkPath, walkRouteResult.getStartPos(),
                        walkRouteResult.getTargetPos());
                // walkRouteOverlay.walkRouteOverlay.removeFromMap();
                walkRouteOverlay.addToMap();
                walkRouteOverlay.zoomToSpan();// 缩放到视野内
                mGPSMarker.hideInfoWindow();
            } else {
            }
        }
    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {
    }

    private final int walkMode = RouteSearch.WalkDefault;// 步行默认模式

    /**
     * 显示路线
     */
    public void showRoute() {
        if (user.getLat() != null && newestOrder != null) {
            if (!TextUtils.isEmpty(newestOrder.getOrderLatitude()) && !TextUtils.isEmpty(newestOrder.getOrderLongitude())) {
                LatLonPoint startPoint = new LatLonPoint(Double.valueOf(user.getLat()), Double.valueOf(user.getLon()));
                LLog.i("本人经纬度  " + user.getLat() + "         " + user.getLon());
                LatLonPoint endPoint = new LatLonPoint(Double.valueOf(newestOrder.getOrderLatitude()), Double.valueOf(newestOrder.getOrderLongitude()));
                LLog.i("用户经纬度   " + newestOrder.getOrderLatitude() + "         " + newestOrder.getOrderLongitude());
                RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(startPoint, endPoint);
                RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo, walkMode);
                routeSearch.calculateWalkRouteAsyn(query);
            }
        }
    }

    /**
     * 获取最新订单
     * 有显示地图视图相关的操作
     */
    public void getNewestOrder() {
        if (SystemUtil.isBackground(getContext()) || SystemUtil.isFastClick(10000)) {
            return;
        }
        //当uid存在
        HttpRequestUtils.httpRequest(getActivity(), "getNewestOrder", RequestValue.ORDER_VIEW, null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        String date = JsonUtil.getGsonValueByKey(response, "data");
                        if (date == null || date.length() < 5) {
//                            aMap.clear();
//                            drawNavigationIcon();
                            llGetOrderMention.setVisibility(View.GONE);
                            break;
                        }
                        ArrayList<Order> newestOrders = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<Order>>() {
                        });
                        if (newestOrders != null && newestOrders.size() > 0) {
                            llGetOrderMention.setVisibility(View.VISIBLE);
                            newestOrder = newestOrders.get(0);
                            if (newestOrder != null) {
                                llGetOrderMention.setVisibility(View.VISIBLE);
                                setJuli();
                                String picture = newestOrder.getUserHeadImage();
                                if (!TextUtils.isEmpty(picture)) {
                                    imageLoaderUtil.displayFromNet(getContext(), newestOrder.getUserHeadImage(), imgIcon,R.drawable.ic_default_user);
                                } else {
                                    imageLoaderUtil.displayFromLocal(getContext(), imgIcon, R.drawable.ic_default_user);
                                }
                                txtOrderStatus.setText(newestOrder.getStatusName());
                                txtMaintainName.setText(newestOrder.getOrderContactName());

                                if (Order.STATUS_JIEDAN == newestOrder.getStatusId()) {
                                    for (int i = 0; i < mapMarkers.size(); i++) {
                                        mapMarkers.get(i).hideInfoWindow();
                                        mapMarkers.get(i).destroy();
                                    }
                                    mapMarkers.clear();
                                    showRoute();
                                }
                            } else {
                                llGetOrderMention.setVisibility(View.GONE);
                            }
                        }
                        break;
                    case "0":
                        LLog.iNetSucceed("   getNewestOrder 9999   " + common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
    }
    private void setJuli() {
        if (newestOrder == null || currentBestLocation == null) {
            return;
        }
        LatLng latLng2 = new LatLng(Double.parseDouble(newestOrder.getOrderLatitude()), Double.parseDouble(newestOrder.getOrderLongitude()));
        float calculateLineDistance = AMapUtils.calculateLineDistance(
                new LatLng(currentBestLocation.getLatitude(), currentBestLocation.getLongitude()), latLng2);
        txtDistance.setText("距离您  " + (calculateLineDistance > 1000 ? Utils.chu(calculateLineDistance+"", 1000+"") + "km" : (int)calculateLineDistance + " m"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        imgRight.setVisibility(View.VISIBLE);
        imgRight.setImageResource(R.drawable.ic_msg);
        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.gotoActivity(getActivity(), MyMsgActivity.class);
            }
        });
        imgRightTwo.setVisibility(View.VISIBLE);
        imgRightTwo.setImageResource(R.drawable.sweep_code);
        imgRightTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.gotoActivityForResult(getActivity(), CaptureActivity.class, 8869);
            }
        });
        txtDaohang.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
//        unRegisterSensorListener();
    }

    private List<String> getMapApk() {
        List<String> mydataList = new ArrayList<>();
        List<String> apkList = Utils.getApkList(getActivity());
        if (apkList.contains("com.baidu.BaiduMap")){
            mydataList.add("百度地图");
        }
        if (apkList.contains("com.autonavi.minimap")){
            mydataList.add("高德地图");
        }
        if (apkList.contains("com.tencent.map")){
            mydataList.add("腾讯地图");
        }
        return mydataList;
    }
}
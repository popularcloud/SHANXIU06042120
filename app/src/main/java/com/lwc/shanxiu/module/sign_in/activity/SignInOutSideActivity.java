package com.lwc.shanxiu.module.sign_in.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.utils.Constants;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.widget.CircleImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class SignInOutSideActivity extends BaseActivity implements AMap.OnMapLoadedListener, AMapLocationListener, GeocodeSearch.OnGeocodeSearchListener {


    @BindView(R.id.map)
    MapView mMapView;
    @BindView(R.id.tv_select_address)
    TextView tv_select_address;
    @BindView(R.id.tv_address)
    TextView tv_address;

    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_status)
    TextView tv_status;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_company)
    TextView tv_company;
    @BindView(R.id.et_visit_object)
    EditText et_visit_object;
    @BindView(R.id.img_head)
    CircleImageView img_head;

    @BindView(R.id.ll_sign_in)
    LinearLayout ll_sign_in;

    private AMap aMap;

    //逆地理编码对象
    private GeocodeSearch geocoderSearch;

    private Handler mHandle;

    private boolean IsCancel;


    //當前金緯度
    private double presentLatitude;
    private double presentLongitude;
    private String presentAddress;
    private String presentDetailAddress;


    private SimpleDateFormat df;
    private SimpleDateFormat dfSearch;
    private User user;

    private String cityStr;
    private AMapLocationClientOption mLocationOption;
    private AMapLocationClient mlocationClient;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_sign_in_out_side;
    }

    @Override
    protected void findViews() {
        initLocation();

        df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
        dfSearch = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式

        tv_date.setText(dfSearch.format(new Date()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
    }

    @Override
    public void init() {

        setTitle("签到");
        showBack();
        setRightText("签到记录","#1481ff", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("isFromMian",1);
                IntentUtil.gotoActivity(SignInOutSideActivity.this,SignInHistoryActivity.class,bundle);
            }
        });

        tv_select_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("city_str","东莞");
                bundle.putDouble("latitude",presentLatitude);
                bundle.putDouble("longitude",presentLongitude);
                IntentUtil.gotoActivityForResult(SignInOutSideActivity.this,SignInSelectAddressActivity.class,bundle, Constants.REQUEST_CODE_SELECT_ADDRESS);
            }
        });

        ll_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String,String> params = new HashMap<>();

                if(presentLatitude == 0){
                    ToastUtil.showToast(SignInOutSideActivity.this,"定位获取失败!请稍后重试");
                    return;
                }

                params.put("latitude",String.valueOf(presentLatitude));
                params.put("longitude",String.valueOf(presentLatitude));
                params.put("address",presentAddress);
                params.put("detailAddress",presentDetailAddress);
                params.put("signIn_time",tv_time.getText().toString().trim());

                String visitingObject = et_visit_object.getText().toString().trim();
                if(TextUtils.isEmpty(visitingObject)){
                    ToastUtil.showToast(SignInOutSideActivity.this,"请输入拜访对象！");
                    return;
                }

                params.put("visitingObject",visitingObject);
                params.put("company_id",user.getParentCompanyId());
                Bundle bundle = new Bundle();
                bundle.putString("params", JsonUtil.parserObjectToGson(params));
                IntentUtil.gotoActivity(SignInOutSideActivity.this,SignInSelectPicActivity.class,bundle);
                finish();
            }
        });

        user = SharedPreferencesUtils.getInstance(this).loadObjectData(User.class);
        tv_name.setText(user.getMaintenanceName());
        tv_company.setText(user.getParentCompanyName()); //这个才一定有
        ImageLoaderUtil.getInstance().displayFromNetD(this,user.getMaintenanceHeadImage(),img_head);
    }

    private void startTimer(){
        IsCancel = false;
        mHandle = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 999:
                        updateDataTime();
                        removeMessages(999);
                        if(!IsCancel){
                            sendEmptyMessageDelayed(999, 1000);
                        }
                        break;
                }
            }
        };

        mHandle.sendEmptyMessage(999);
    }

    private void updateDataTime(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String searchDate = df.format(new Date());// new Date()为获取当前系统时间
                tv_time.setText(searchDate);
            }
        });
    }

    /**
     * 初始化定位
     */
    private void initLocation() {

        //初始化3d地图
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }

        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;//定位一次，且将视角移动到地图中心点。
        myLocationStyle.strokeColor(Color.parseColor("#2E1481FF"));
        myLocationStyle.radiusFillColor(Color.parseColor("#2E1481FF"));// 设置圆形的填充颜色

        //设置希望展示的地图缩放级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));

        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.location_marker)));
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        mlocationClient = new AMapLocationClient(this);
        mLocationOption = new AMapLocationClientOption();
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        //启动定位
        mlocationClient.startLocation();

  /*      geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);*/
    }


    @Override
    protected void initGetData() {
    }

    @Override
    protected void widgetListener() {
    }

    @Override
    public void onMapLoaded() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            if(requestCode == Constants.REQUEST_CODE_SELECT_ADDRESS && resultCode == RESULT_OK){
                presentAddress = data.getStringExtra("address");
                presentDetailAddress = data.getStringExtra("detailAddress");
                presentLatitude = data.getDoubleExtra("latitude",0);
                presentLongitude = data.getDoubleExtra("longitude",0);
                tv_address.setText(presentAddress);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();

        startTimer();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();

        IsCancel = true;

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        if(i == 1000){
           String address =  regeocodeResult.getRegeocodeAddress().getFormatAddress();
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系

        presentLatitude = aMapLocation.getLatitude();
        presentLongitude = aMapLocation.getLongitude();


        cityStr = aMapLocation.getCity();
        presentDetailAddress = aMapLocation.getAddress();
        presentAddress = aMapLocation.getPoiName();
        tv_address.setText(presentAddress);

/*        LatLonPoint latLonPoint = new LatLonPoint(aMapLocation.getLatitude(),aMapLocation.getLongitude());
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);*/
    }
}

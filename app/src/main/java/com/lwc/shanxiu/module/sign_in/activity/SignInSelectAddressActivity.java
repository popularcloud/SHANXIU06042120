package com.lwc.shanxiu.module.sign_in.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.module.addressmanager.AutoListView;
import com.lwc.shanxiu.module.addressmanager.CityPickerActivity;
import com.lwc.shanxiu.module.addressmanager.PoiAdapter;
import com.lwc.shanxiu.module.addressmanager.PoiBean;
import com.lwc.shanxiu.utils.KeyboardUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 通过地图选择地址
 */
public class SignInSelectAddressActivity extends BaseActivity implements PoiSearch.OnPoiSearchListener, AutoListView.OnRefreshListener, AutoListView.OnLoadListener, AMap.OnCameraChangeListener {

    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.iv_delete)
    ImageView iv_delete;
    @BindView(R.id.lv_list)
    AutoListView lv_list;
    @BindView(R.id.my_map)
    MapView my_map;
    @BindView(R.id.fl_map)
    FrameLayout fl_map;

    private LatLonPoint lp;

    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;

    private AMapLocationClient locationClient = null;//定位类
    private double mLatitude,mLongitude;//定位的经纬度

    private PoiAdapter mAdapter;
    private int currentPage= 1;
    private List<PoiBean> poiData = new ArrayList<>();
    private AMapLocation mLoc;//首次进入定位成功信息
    private String mCity;
    private String cityStr;
    private GeocodeSearch geocoderSearch;
    private AMap aMap;
    private String keyword="";
    private boolean isFirst = true;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_sign_in_select_address;
    }

    @Override
    protected void findViews() {

        setTitle("地址微调");
        //获取初始数据
     //   Location location = SharedPreferencesUtils.getInstance(SelectAddressByMapActivity.this).loadObjectData(Location.class);
        cityStr = getIntent().getStringExtra("city_str");
        Double latitude = getIntent().getDoubleExtra("latitude",0);
        Double longitude = getIntent().getDoubleExtra("longitude",0);
        if (latitude != 0)
            lp = new LatLonPoint(latitude, longitude);
        initLocation();

     //   lv_list.onRefresh();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1560 && resultCode == RESULT_OK) {
            cityStr =  data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
        }
    }

    private void initLocation() {

        geocoderSearch = new GeocodeSearch(this);

     /*   //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        //设置定位参数
        locationClient.setLocationOption(getDefaultOption());
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
        locationClient.startLocation();*/

        //初始化3d地图
        my_map.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = my_map.getMap();
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

        //设置地图移动监听
        aMap.setOnCameraChangeListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        my_map.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        my_map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        my_map.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        my_map.onSaveInstanceState(outState);
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() {
        keyword = et_search.getText().toString().trim();

        // 商务写字楼|住宅小区|培训机构|学校|知名企业|购物中心|综合酒楼|公园|地铁站
        //"120201|120302|141400|141200|170100|060101|050101|110101|150500"
        query = new PoiSearch.Query(TextUtils.isEmpty(keyword)?"":keyword,"120201|120302|141400|141200|170100|060101|050101|110101|150500", cityStr);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        poiSearch = new PoiSearch(this, query);
        // 设置搜索区域为以lp点为圆心，其周围5000米范围
        if (lp != null)
            poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(lp.getLatitude(), lp.getLongitude()), 200,true));

        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();// 异步搜索
    }


    @Override
    public void init() {
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {
        mAdapter=new PoiAdapter(this,poiData);
        lv_list.setAdapter(mAdapter);
        lv_list.setOnRefreshListener(this);
        lv_list.setOnLoadListener(this);
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PoiBean poiBean = poiData.get((int) id);
                KeyboardUtil.showInput(false, SignInSelectAddressActivity.this);
                Intent i = new Intent();
                i.putExtra("address", poiBean.getTitleName());
                i.putExtra("detailAddress", poiBean.getSnippet()+poiBean.getTitleName());
                i.putExtra("latitude",poiBean.getPoint().getLatitude());
                i.putExtra("longitude", poiBean.getPoint().getLongitude());
                setResult(RESULT_OK, i);
                onBackPressed();
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = et_search.getText().toString().trim();
                if (!value.equals(keyword)) {
                    fl_map.setVisibility(View.GONE);
                    iv_delete.setVisibility(View.VISIBLE);
                    keyword = value;
                    lv_list.onRefresh();
                }

                if (TextUtils.isEmpty(value)){
                    iv_delete.setVisibility(View.VISIBLE);
                    fl_map.setVisibility(View.VISIBLE);
                }
            }
        });

        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search.setText("");
                iv_delete.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        if (rCode == 1000) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
               // if (result.getQuery().equals(query)) {// 是否是同一条
                lv_list.setVisibility(View.VISIBLE);
                try {
                    List<PoiItem> poiItems = result.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<PoiBean> tem = new ArrayList<>();
                    if (poiItems != null && poiItems.size() > 0) {
                        for (int i = 0; i < poiItems.size(); i++) {
                            PoiItem poiItem = poiItems.get(i);
                            PoiBean bean = new PoiBean();
                            bean.setTitleName(poiItem.getTitle());
                            bean.setCityName(poiItem.getCityName());
                            bean.setAd(poiItem.getAdName());
                            bean.setSnippet(poiItem.getSnippet());
                            bean.setPoint(poiItem.getLatLonPoint());
                           /* Log.e("yufs", "" + poiItem.getTitle() + "," + poiItem.getProvinceName() + ","
                                    + poiItem.getCityName() + ","
                                    + poiItem.getAdName() + ","//区
                                    + poiItem.getSnippet() + ","
                                    + poiItem.getLatLonPoint() + "\n");*/
                            tem.add(bean);
                        }
                        poiData.addAll(tem);
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                }
            }
            }
       // }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
      //  lv_list.onRefreshComplete();
        poiData.clear();
        mAdapter.notifyDataSetChanged();
        doSearchQuery();
    }

    @Override
    public void onLoad() {
        currentPage = currentPage + 1;
      //  lv_list.onLoadComplete();
        doSearchQuery();
    }


    /**
     * 地图移动回调
     * @param cameraPosition
     */
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        //ToastUtil.showLongToast(SelectAddressByMapActivity.this,"地图开始移动"+cameraPosition);
    }

    /**
     * 地图移动完成回调
     * @param cameraPosition
     */
    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        //ToastUtil.showLongToast(SelectAddressByMapActivity.this,"地图移动完成"+cameraPosition);

       // lv_list.onRefresh();

      if(isFirst){ //过滤第一次默认移动回调
         /*   if(cameraPosition != null){
                if(lp == null){
                    lp = new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude);
                }else{
                    lp.setLatitude(cameraPosition.target.latitude);
                    lp.setLongitude(cameraPosition.target.longitude);
                }
                lv_list.onRefresh();
            }*/
          lv_list.onRefresh();
        }

       isFirst = false;

    }
}

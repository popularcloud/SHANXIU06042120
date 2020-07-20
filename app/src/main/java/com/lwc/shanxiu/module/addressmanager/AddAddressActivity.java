package com.lwc.shanxiu.module.addressmanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Location;
import com.lwc.shanxiu.bean.PickerView;
import com.lwc.shanxiu.bean.Sheng;
import com.lwc.shanxiu.bean.Shi;
import com.lwc.shanxiu.bean.Xian;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.utils.FileUtil;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.view.ProgressUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 何栋 on 2017/10/15.
 * 294663966@qq.com
 * 添加地址
 */
public class AddAddressActivity extends BaseActivity implements IAddAddressView, GeocodeSearch.OnGeocodeSearchListener {

    @BindView(R.id.edtName)
    EditText edtName;
    @BindView(R.id.edtPhone)
    EditText edtPhone;
    @BindView(R.id.btnAffirm)
    TextView btnAffirm;
    @BindView(R.id.edtDetailAddress)
    TextView edtDetailAddress;
    /*    @BindView(R.id.txtAddress)
        TextView txtAddress;*/
    @BindView(R.id.edtAddress)
    TextView edtAddress;
    private AddAddressPresenter presenter;

    private ArrayList<PickerView> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private List<Sheng> shengs = new ArrayList<>();
    private List<Shi> shis = new ArrayList<>();
    private List<Xian> xians = new ArrayList<>();
    //排序后的城市
    private List<List<Shi>> sortShi = new ArrayList<>();
    //排序后的县
    private List<List<List<Xian>>> sortXian = new ArrayList<>();
    /**
     * 选中的省
     */
    private Sheng selectedSheng;
    /**
     * 选中的市
     */
    private Shi selectedShi;
    /**
     * 选中的县
     */
    private Xian selectedXian;

    private Address addressData;
    private PoiBean poiBean;
    ;
    private String cityCode;
    private User user;
    private Location location;

    private AMapLocationClient locationClient = null;//定位类
    private GeocodeSearch geocoderSearch;

    private ProgressUtils progressUtils;


    private boolean isLocation = true;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_add_address;
    }

    @Override
    protected void findViews() {
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Utils.isChinese(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };
        edtName.setFilters(new InputFilter[]{filter});
        progressUtils = new ProgressUtils();

        initLocation();
    }

    /**
     * 获取地区数据
     */
    private void getAreaData(String address_city, String address_district) {
        String sheng = FileUtil.readAssetsFile(this, "sheng.json");
        String shi = FileUtil.readAssetsFile(this, "shi.json");
        String xian = FileUtil.readAssetsFile(this, "xian.json");
        shengs = JsonUtil.parserGsonToArray(sheng, new TypeToken<ArrayList<Sheng>>() {
        });
        shis = JsonUtil.parserGsonToArray(shi, new TypeToken<ArrayList<Shi>>() {
        });
        xians = JsonUtil.parserGsonToArray(xian, new TypeToken<ArrayList<Xian>>() {
        });

        if (!TextUtils.isEmpty(address_city) && shis != null) {
            for (int i = 0; i < shis.size(); i++) {
                if (shis.get(i).getName().contains(address_city)) {
                    selectedShi = shis.get(i);
                    ;
                    Log.e("yufs", "选中的市：" + selectedShi.getName());
                    break;
                }
            }
            if (selectedShi != null) {
                for (int i = 0; i < shengs.size(); i++) {
                    if (shengs.get(i).getDmId().equals(selectedShi.getParentid())) {
                        selectedSheng = shengs.get(i);
                        Log.e("yufs", "选中的省：" + selectedSheng.getName());
                        break;
                    }
                }
                if (!TextUtils.isEmpty(address_district)) {
                    for (int i = 0; i < xians.size(); i++) {
                        if (xians.get(i).getName().contains(address_district)) {
                            selectedXian = xians.get(i);
                            Log.e("yufs", "选中的区县：" + selectedXian.getName());
                            break;
                        }
                    }
                }
            }

        }

    }


    @Override
    protected void init() {
        Intent intent = getIntent();
        addressData = (Address) intent.getSerializableExtra("address");
        user = SharedPreferencesUtils.getInstance(AddAddressActivity.this).loadObjectData(User.class);
        location = SharedPreferencesUtils.getInstance(AddAddressActivity.this).loadObjectData(Location.class);
        if (addressData == null) {
            setTitle("添加新地址");
            if (location != null) {
                String[] list = location.getStrValue().split("#");
                if (list != null && list.length > 0) {
                    Map<String, String> map = new HashMap<>();
                    for (int i = 0; i < list.length; i++) {
                        String str = list[i];
                        if (str != null && !str.equals("")) {
                            String[] arr = str.split("=");
                            if (arr != null && arr.length > 1) {
                                map.put(arr[0], arr[1]);
                            }
                        }
                    }
                    String province = map.get("province");
                    String city = map.get("city");
                    String address = map.get("address");
                    cityCode = map.get("cityCode");
                  /*  if (province != null && city != null && address != null) {
                        edtDetailAddress.setText(address.replace(province + city, ""));
                    }*/
                }
            }
            if (user != null) {
                if (!TextUtils.isEmpty(user.getUserName()))
                    edtName.setText(user.getUserName());
                if (!TextUtils.isEmpty(user.getUserPhone())) {
                    edtPhone.setText(user.getUserPhone());
                } else if (!TextUtils.isEmpty(user.getUserName())) {
                    edtPhone.setText(user.getUserName());
                }
            }
            //如果是新地址才开启定位
            locationClient.startLocation();
        } else {
            setTitle("修改地址");
            edtName.setText(addressData.getContactName());
            edtPhone.setText(addressData.getContactPhone());
            String[] arr = addressData.getContactAddress().split("_");
            edtDetailAddress.setText(arr[0]);
            if (arr.length > 1) {
                edtAddress.setText(arr[1]);
            }
            String province = addressData.getProvinceName();
            if (!TextUtils.isEmpty(province)) {
                selectedSheng = new Sheng();
                selectedSheng.setDmId(String.valueOf(addressData.getProvinceId()));
                selectedSheng.setName(addressData.getProvinceName());
            }
            String city = addressData.getCityName();
            if (!TextUtils.isEmpty(city)) {
                selectedShi = new Shi();
                selectedShi.setDmId(String.valueOf(addressData.getCityId()));
                selectedShi.setName(addressData.getCityName());
            }
            String county = addressData.getTownName();
            if (!TextUtils.isEmpty(county)) {
                selectedXian = new Xian();
                selectedXian.setDmId(String.valueOf(addressData.getTownId()));
                selectedXian.setName(addressData.getTownName());
            }
            // txtAddress.setText(province + "-" + city + "-" + county);
        }


        // getAreaData();
    }

    @Override
    protected void initGetData() {
        presenter = new AddAddressPresenter(this, this);
    }

    @Override
    protected void widgetListener() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1520 && resultCode == RESULT_OK) {
            if(poiBean == null){
                poiBean = new PoiBean();
            }
            poiBean.setTitleName(data.getStringExtra("address"));
            double latitude = data.getDoubleExtra("latitude", 0);
            double longitude = data.getDoubleExtra("longitude", 0);
            LatLonPoint latLonPoint = new LatLonPoint(latitude, longitude);
            poiBean.setPoint(latLonPoint);
            edtDetailAddress.setText(poiBean.getTitleName());
            isLocation = false;
            // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
            RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 500, GeocodeSearch.AMAP);
            geocoderSearch.getFromLocationAsyn(query);

            progressUtils.showDefaultProgressDialog(AddAddressActivity.this,"");
        }
    }

    @OnClick({R.id.btnAffirm, R.id.ll_select_address})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.ll_select_address:
                if (selectedXian == null) {
                    ToastUtil.showToast(this, "获取定位信息失败.请稍后");
                    locationClient.startLocation();
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("city_str", selectedShi.getName());
                IntentUtil.gotoActivityForResult(AddAddressActivity.this, SelectAddressByMapActivity.class, bundle, 1520);
                break;
            case R.id.btnAffirm:
                String name = edtName.getText().toString();
                String phone = edtPhone.getText().toString();
                String address = edtDetailAddress.getText().toString();

                if (name == null || TextUtils.isEmpty(name)) {
                    ToastUtil.showLongToast(this, "名字不能为空");
                    return;
                }

                if (phone == null || TextUtils.isEmpty(phone)) {
                    ToastUtil.showLongToast(this, "电话号码不能为空");
                    return;
                }

                if (address == null || TextUtils.isEmpty(address)) {
                    ToastUtil.showLongToast(this, "地址不能为空");
                    return;
                }
                if (phone.length() < 11) {
                    ToastUtil.showLongToast(this, "请输入正确的电话号码,例如：18812345678或07691234567");
                    return;
                }

                if (addressData == null && (selectedSheng == null || selectedShi == null || selectedXian == null)) {
                    ToastUtil.showLongToast(this, "请选择地区");
                    return;
                }
                String add = edtAddress.getText().toString().trim();
                if (TextUtils.isEmpty(add)) {
                    ToastUtil.showLongToast(this, "请填写详细地址");
                    return;
                }
                if (Utils.isFastClick(3000)) {
                    return;
                }
                double latitude = 0;
                double longitude = 0;
                String detailAddress = address + "_" + add;
                if (addressData == null) {
                    if (poiBean != null) {
                        latitude = poiBean.getPoint().getLatitude();
                        longitude = poiBean.getPoint().getLongitude();
                    } else if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                    presenter.addUserAddress(name, phone, detailAddress, selectedSheng.getDmId(), selectedShi.getDmId(), selectedXian.getDmId(), latitude, longitude);
                } else {
                    if (poiBean != null) {
                        latitude = poiBean.getPoint().getLatitude();
                        longitude = poiBean.getPoint().getLongitude();
                    } else if (addressData != null) {
                        if (!TextUtils.isEmpty(addressData.getLongitude())) {
                            latitude = Double.parseDouble(addressData.getLatitude());
                        }
                        if (!TextUtils.isEmpty(addressData.getLongitude())) {
                            longitude = Double.parseDouble(addressData.getLongitude());
                        }
                    }
                    if (selectedSheng != null && selectedShi != null && selectedXian != null) {
                        presenter.upUserAddress(addressData.getUserAddressId(), name, phone,
                                detailAddress, selectedSheng.getDmId(), selectedShi.getDmId(), selectedXian.getDmId(), latitude, longitude);
                    } else if (selectedSheng != null && selectedShi != null) {
                        presenter.upUserAddress(addressData.getUserAddressId(), name, phone,
                                detailAddress, selectedSheng.getDmId(), selectedShi.getDmId(), null, latitude, longitude);
                    } else if (selectedSheng != null) {
                        presenter.upUserAddress(addressData.getUserAddressId(), name, phone,
                                detailAddress, selectedSheng.getDmId(), null, null, latitude, longitude);
                    } else {
                        presenter.upUserAddress(addressData.getUserAddressId(), name, phone,
                                detailAddress, null, null, null, latitude, longitude);
                    }
                }
                break;
        }
    }


    /**
     * ###################定位信息操作###################################
     **/

    private void initLocation() {

        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);

        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        //设置定位参数
        locationClient.setLocationOption(getDefaultOption());
        // 设置定位监听
        locationClient.setLocationListener(locationListener);

    }

    /**
     * 设置定位相关信息
     *
     * @return
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(false); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }

    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation loc) {
            if (null != loc) {
                //解析定位结果
          /*      mCity = loc.getCity();
                mLoc=loc;
                lp.setLongitude(loc.getLongitude());
                lp.setLatitude(loc.getLatitude());*/
                //得到定位信息
                Log.e("yufs", "定位详细信息：" + loc.toString());
                LatLonPoint myPoint = new LatLonPoint(loc.getLatitude(), loc.getLongitude());
                if(poiBean == null){
                    poiBean = new PoiBean();
                }
                poiBean.setPoint(myPoint);
               // progressUtils.showDefaultProgressDialog(AddAddressActivity.this,"");
                // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
                RegeocodeQuery query = new RegeocodeQuery(myPoint, 500, GeocodeSearch.AMAP);
                geocoderSearch.getFromLocationAsyn(query);
            } else {
                ToastUtil.showLongToast(AddAddressActivity.this, "定位失败，请打开位置权限");
            }
        }
    };

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        progressUtils.dissmissDefaultProgressDialog();
        if(i == 1000){
            Log.e("yufs", "逆地理编码onRegeocodeSearched：");

            RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
            String mySheng = regeocodeAddress.getProvince();
            String myShi = regeocodeAddress.getCity();
            String myXian = regeocodeAddress.getDistrict();
            String detailAddress = regeocodeAddress.getFormatAddress();
            if(isLocation){
                poiBean.setTitleName(detailAddress);
                edtDetailAddress.setText(poiBean.getTitleName());
            }
            if (TextUtils.isEmpty(myXian)) {
                myXian = regeocodeAddress.getTownship();
            }
            Log.e("yufs", "逆地理编码定位详细信息：" + mySheng + "---" + myShi + "---" + myXian);
            if (!TextUtils.isEmpty(myXian) && !TextUtils.isEmpty(myShi)) {
                String subShi = myShi.replaceAll("市", "");
                String subXian = myXian.replaceAll("街道", "").replaceAll("镇", "").replaceAll("县", "");
                Log.e("yufs", "逆地理编码定位截取的县：" + subShi + subXian);
                getAreaData(subShi, subXian);
            } else {
                ToastUtil.showToast(AddAddressActivity.this, "定位失败!请开启定位后重试!");
            }
        }else{
            ToastUtil.showToast(AddAddressActivity.this,"获取地址失败!请重新选择");
        }

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }
}

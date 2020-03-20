package com.lwc.shanxiu.module.order.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.adapter.ComfigNameAdapter;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.bean.PickerView;
import com.lwc.shanxiu.bean.Sheng;
import com.lwc.shanxiu.bean.Shi;
import com.lwc.shanxiu.bean.Xian;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.bean.BrandTypesBean;
import com.lwc.shanxiu.module.bean.BrandsBean;
import com.lwc.shanxiu.module.bean.DeviceType;
import com.lwc.shanxiu.module.bean.LeaseCompanyBean;
import com.lwc.shanxiu.module.bean.LeaseDevicesHistoryBean;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.pickerview.OptionsPickerView;
import com.lwc.shanxiu.utils.FileUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.KeyboardUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.view.FullyLinearLayoutManager;
import com.lwc.shanxiu.widget.EditableSpinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 租赁设备
 */
public class LeaseDevicesActivity extends BaseActivity {

    @BindView(R.id.rv_deviceProperty)
    RecyclerView rv_deviceProperty;
    @BindView(R.id.et_companyName)
    EditText et_companyName;
    @BindView(R.id.et_companyContacts)
    EditText et_companyContacts;
    @BindView(R.id.et_companyPhone)
    EditText et_companyPhone;
    @BindView(R.id.tv_companyAddress)
    TextView tv_companyAddress;
    @BindView(R.id.et_companyDetailAddress)
    EditText et_companyDetailAddress;

    @BindView(R.id.tv_category)
    TextView tv_category;
    @BindView(R.id.tv_brand)
    TextView tv_brand;
    @BindView(R.id.tv_model)
    TextView tv_model;
    @BindView(R.id.es_spinner)
    EditableSpinner es_spinner;

    private String qrcodeIndex;

    private ArrayList<DeviceType> deviceTypes;
    private ArrayList<BrandsBean> brandsBeenList;
    private ArrayList<BrandTypesBean> brandTypesBeenList;
    private DeviceType checkedDeviceType;
    private BrandsBean checkedBrandsBean;
    private BrandTypesBean checkedBrandTypesBean;
    private OptionsPickerView companyOptions;
    private OptionsPickerView brandsOptions;
    private OptionsPickerView brandTypesOptions;


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
    private Sheng selectedSheng;
    private Shi selectedShi;
    private Xian selectedXian;

    private User user = null;
    private ArrayList<LeaseCompanyBean> leaseCompanyBeans = new ArrayList<>();
    private List<String> mOrders = new ArrayList<>();

    private boolean isShowSpinner = true;

    List<String> strsToList2 = new ArrayList<>();
    private ComfigNameAdapter comfigNameAdapter;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_lease_devices;
    }


    @Override
    protected void findViews() {
        setTitle("租赁设备信息");
        showBack();
        qrcodeIndex = getIntent().getStringExtra("qrcodeIndex");
        getAreaData();
        myThread.start();

        comfigNameAdapter = new ComfigNameAdapter(LeaseDevicesActivity.this, strsToList2, R.layout.item_config_name);
        FullyLinearLayoutManager fullyLinearLayoutManager = new FullyLinearLayoutManager(LeaseDevicesActivity.this);
        rv_deviceProperty.setLayoutManager(fullyLinearLayoutManager);
        rv_deviceProperty.setAdapter(comfigNameAdapter);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initGetData() {


    }

    private void getCompanyName(String searchCompanyName) {
        Map<String, String> params = new HashMap<>();
        params.put("userCompanyName", searchCompanyName);
        HttpRequestUtils.httpRequest(this, "获得租赁二维码设备信息", RequestValue.SCAN_CODELEASEINFO, params, "GET", new HttpRequestUtils.ResponseListener() {

            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        leaseCompanyBeans = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<LeaseCompanyBean>>() {
                        });

                        mOrders.clear();
                        for (int i = 0; i < leaseCompanyBeans.size(); i++) {
                            mOrders.add(leaseCompanyBeans.get(i).getUserCompanyName());
                        }

                        ArrayAdapter<String> mOrderAdapter = new ArrayAdapter<String>(LeaseDevicesActivity.this, android.R.layout.simple_spinner_dropdown_item, mOrders);

                        es_spinner.setAdapter(mOrderAdapter)
                                .setOnItemClickListener(new EditableSpinner.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int position) {

                                        // isShowSpinner = false;

                                        es_spinner.dismissEditableSpinner();

                                        LeaseCompanyBean leaseCompanyBean = leaseCompanyBeans.get(position);

                                        //   ToastUtil.showToast(LeaseDevicesActivity.this,leaseCompanyBean.getUserCompanyName());
                                        mOrders.clear();
                                        et_companyName.setText(leaseCompanyBean.getUserCompanyName());
                                        et_companyContacts.setText(leaseCompanyBean.getUserName());
                                        et_companyPhone.setText(leaseCompanyBean.getUserPhone());
                                        et_companyDetailAddress.setText(leaseCompanyBean.getUserCompanyAddrs());
                                        tv_companyAddress.setText(leaseCompanyBean.getCompanyProvinceName() + "-" + leaseCompanyBean.getCompanyCityName() + "-" + leaseCompanyBean.getCompanyTownName());


                                        selectedSheng = new Sheng();
                                        selectedSheng.setDmId(leaseCompanyBean.getCompanyProvinceId());
                                        selectedSheng.setName(leaseCompanyBean.getCompanyProvinceName());

                                        selectedShi = new Shi();
                                        selectedShi.setDmId(leaseCompanyBean.getCompanyCityId());
                                        selectedShi.setName(leaseCompanyBean.getCompanyCityName());

                                        selectedXian = new Xian();
                                        selectedXian.setDmId(leaseCompanyBean.getCompanyTownId());
                                        selectedXian.setName(leaseCompanyBean.getCompanyTownName());
                                    }
                                });

                        if (mOrders.size() > 0 && isShowSpinner) {
                            es_spinner.showEditableSpinner();
                            isShowSpinner = false;
                        }
                        break;
                    default:
                        LLog.i("getDeviceTypes" + common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
    }

    /**
     * 获取设备类型
     */
    private void getTypeAll() {
        HttpRequestUtils.httpRequest(this, "getDeviceTypes", RequestValue.SCAN_GETLEASEDEVICETYPES, null, "GET", new HttpRequestUtils.ResponseListener() {

            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        deviceTypes = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<DeviceType>>() {
                        });
                  /*      if(deviceTypes != null && deviceTypes.size() > 0){
                            initCompanyOptionsPicker();
                        }*/
                        break;
                    default:
                        LLog.i("getDeviceTypes" + common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
    }

    /**
     * 获取品牌数据
     */
    private void getBrands() {
        if (checkedDeviceType == null) {
            ToastUtil.showToast(this, "请先选择设备类别！");
            return;
        }

        Map<String, String> param = new HashMap<>();
        param.put("device_type_id", checkedDeviceType.getDeviceTypeId());
        HttpRequestUtils.httpRequest(this, "getBrands", RequestValue.SCAN_GETLEASEBRANDS, param, "GET", new HttpRequestUtils.ResponseListener() {

            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        LLog.i("getBrands:获取数据成功" + common.getInfo());
                        brandsBeenList = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<BrandsBean>>() {
                        });
                        if (brandsBeenList != null && brandsBeenList.size() > 0) {
                            initBrandsPicker();
                        }
                        break;
                    default:
                        LLog.i("getBrands" + common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
    }

    /**
     * 获取品牌型号数据
     */
    private void getBrandTypes() {
        if (checkedBrandsBean == null) {
            ToastUtil.showToast(this, "请先选择品牌！");
            return;
        }

        Map<String, String> param = new HashMap<>();
        param.put("device_type_brand_id", checkedBrandsBean.getDeviceTypeBrandId());
        HttpRequestUtils.httpRequest(this, "getModels", RequestValue.SCAN_GETLEASEMODELS, param, "GET", new HttpRequestUtils.ResponseListener() {

            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        LLog.i("getModels:获取数据成功" + common.getInfo());
                        brandTypesBeenList = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<BrandTypesBean>>() {
                        });
                        if (brandTypesBeenList != null && brandTypesBeenList.size() > 0) {
                            initBrandTypesPicker();
                        }

                        break;
                    default:
                        LLog.i("getModels" + common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
    }

    @Override
    protected void widgetListener() {
        et_companyName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && isShowSpinner) {
                    getCompanyName(s.toString());
                }

                if (TextUtils.isEmpty(s)) {
                    isShowSpinner = true;
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @OnClick({R.id.tv_category, R.id.tv_companyAddress, R.id.tv_brand, R.id.tv_model, R.id.btnAffirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_category:
                initCompanyOptionsPicker();
                break;
            case R.id.tv_companyAddress:
                showPickerView();
                break;
            case R.id.tv_brand:
                initBrandsPicker();
                break;
            case R.id.tv_model:
                initBrandTypesPicker();
                break;
            case R.id.btnAffirm:
                submitData();
                break;
        }
    }

    /**
     * 提交数据
     */
    private void submitData() {
        String companyName = et_companyName.getText().toString().trim();
        if (TextUtils.isEmpty(companyName)) {
            ToastUtil.showToast(LeaseDevicesActivity.this, "请输入承租方公司名称");
            return;
        }

        String companyContacts = et_companyContacts.getText().toString().trim();
        if (TextUtils.isEmpty(companyContacts)) {
            ToastUtil.showToast(LeaseDevicesActivity.this, "请输入承租方联系人");
            return;
        }

        String companyPhone = et_companyPhone.getText().toString().trim();
        if (TextUtils.isEmpty(companyPhone)) {
            ToastUtil.showToast(LeaseDevicesActivity.this, "请输入联系人电话");
            return;
        }

        final String companyAddress = tv_companyAddress.getText().toString().trim();
        if (TextUtils.isEmpty(companyAddress)) {
            ToastUtil.showToast(LeaseDevicesActivity.this, "请选择地区");
            return;
        }

        String companyDetailAddress = et_companyDetailAddress.getText().toString().trim();
        if (TextUtils.isEmpty(companyDetailAddress)) {
            ToastUtil.showToast(LeaseDevicesActivity.this, "请输入楼层，门牌号等");
            return;
        }

        String category = tv_category.getText().toString().trim();
        if (TextUtils.isEmpty(category)) {
            ToastUtil.showToast(LeaseDevicesActivity.this, "请选择类别");
            return;
        }

        String brand = tv_brand.getText().toString().trim();
        if (TextUtils.isEmpty(brand)) {
            ToastUtil.showToast(LeaseDevicesActivity.this, "请选择品牌");
            return;
        }

        String model = tv_model.getText().toString().trim();
        if (TextUtils.isEmpty(model)) {
            ToastUtil.showToast(LeaseDevicesActivity.this, "请选择型号");
            return;
        }


        Map<String, String> params = new HashMap<>();
        params.put("leaseQrcodeIndex", qrcodeIndex);
        params.put("userCompanyName", companyName);  //使用人单位
        params.put("userPhone", companyPhone); //使用人联系方式

        params.put("companyProvinceId", selectedSheng.getDmId()); //省id
        params.put("companyProvinceName", selectedSheng.getName()); //省名称
        params.put("companyCityId", selectedShi.getDmId()); //市id
        params.put("companyCityName", selectedShi.getName()); //市名称
        params.put("companyTownId", selectedXian.getDmId()); //区id
        params.put("companyTownName", selectedXian.getName()); //区名称

        params.put("deviceTypeId", checkedDeviceType.getDeviceTypeId()); //类型id
        params.put("deviceTypeName", checkedDeviceType.getDeviceTypeName()); //类型名称
        params.put("deviceTypeBrandId", checkedBrandsBean.getDeviceTypeBrandId()); //品牌id
        params.put("deviceTypeBrand", checkedBrandsBean.getDeviceTypeBrand()); //品牌名称
        params.put("deviceTypeModelId", checkedBrandTypesBean.getDeviceTypeModelId()); //型号id
        params.put("deviceTypeModel", checkedBrandTypesBean.getDeviceTypeModel()); //型号名称

        params.put("userName", companyContacts); //联系人
        params.put("userCompanyAddrs", companyDetailAddress); //使用人所属单位地址

        user = SharedPreferencesUtils.getInstance(this).loadObjectData(User.class);
        if (user != null) {
            params.put("maintenanceId", TextUtils.isEmpty(user.getUserId()) ? "" : user.getUserId()); //工程师id
            params.put("maintenanceName", TextUtils.isEmpty(user.getMaintenancePhone()) ? "" : user.getMaintenancePhone()); //工程师名称
            params.put("maintenanceCompanyId", TextUtils.isEmpty(user.getParentCompanyId()) ? "" : user.getParentCompanyId()); //型号id
            params.put("maintenanceCompanyName", TextUtils.isEmpty(user.getParentCompanyName()) ? "" : user.getParentCompanyName()); //型号名称
        }

        HttpRequestUtils.httpRequest(this, "绑定租赁设备", RequestValue.SCAN_ADDDEVICEINFO, params, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        ToastUtil.showLongToast(LeaseDevicesActivity.this, "绑定成功!");
                        finish();
                        break;
                    default:
                        ToastUtil.showLongToast(LeaseDevicesActivity.this, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
                ToastUtil.showLongToast(LeaseDevicesActivity.this, msg);
            }
        });
    }


    /**
     * 获取地区数据
     */
    private void getAreaData() {
        String sheng = FileUtil.readAssetsFile(this, "sheng.json");
        String shi = FileUtil.readAssetsFile(this, "shi.json");
        String xian = FileUtil.readAssetsFile(this, "xian.json");
        shengs = JsonUtil.parserGsonToArray(sheng, new TypeToken<ArrayList<Sheng>>() {
        });
        shis = JsonUtil.parserGsonToArray(shi, new TypeToken<ArrayList<Shi>>() {
        });
        xians = JsonUtil.parserGsonToArray(xian, new TypeToken<ArrayList<Xian>>() {
        });
    }

    private Thread myThread = new Thread() {
        @Override
        public void run() {
            super.run();
            loadOptionsPickerViewData();
        }
    };

    /**
     * 加载地区弹框数据
     */
    private void loadOptionsPickerViewData() {
        //添加省
        for (int i = 0; i < shengs.size(); i++) {
            Sheng sheng = shengs.get(i);
            options1Items.add(new PickerView(i, sheng.getName()));
        }
//        //添加市
        for (int j = 0; j < shengs.size(); j++) {
            Sheng sheng = shengs.get(j);
            ArrayList<String> options2Items_01 = new ArrayList<>();
            List<Shi> tempSortShi = new ArrayList<>();
            for (int z = 0; z < shis.size(); z++) {
                Shi shi = shis.get(z);
                if (sheng.getDmId().equals(shi.getParentid())) {
                    options2Items_01.add(shi.getName());
                    tempSortShi.add(shi);
                }
            }
            options2Items.add(options2Items_01);
            sortShi.add(tempSortShi);
        }
        //添加县
        for (int p = 0; p < shengs.size(); p++) { //遍历省级
            ArrayList<ArrayList<String>> provinceAreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            List<List<Xian>> tempProvince = new ArrayList<>();
            Sheng sheng = shengs.get(p);
            for (int s = 0; s < shis.size(); s++) {  //省级下的市
                ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
                List<Xian> tempXian = new ArrayList<>();
                Shi shi = shis.get(s);
                if (sheng.getDmId().equals(shi.getParentid())) {
                    for (int x = 0; x < xians.size(); x++) {
                        Xian xian = xians.get(x);
                        if (shi.getDmId().equals(xian.getParentid())) {
                            cityList.add(xian.getName());
                            tempXian.add(xian);
                        }
                    }
                    provinceAreaList.add(cityList);
                    tempProvince.add(tempXian);
                }
            }
            options3Items.add(provinceAreaList);
            sortXian.add(tempProvince);
        }
        LLog.i("options3Items    " + options3Items.toString());
    }


    /**
     * 弹出类别选择器
     */
    private void initCompanyOptionsPicker() {//条件选择器初始化
        if (deviceTypes == null) {
            getTypeAll();
            return;
        }
        if (companyOptions == null) {
            ArrayList<DeviceType> items = deviceTypes;
            companyOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    String tx = deviceTypes.get(options1).getDeviceTypeName();
                    tv_category.setText(tx);
                    tv_brand.setText("");
                    checkedBrandsBean = null;
                    tv_model.setText("");
                    checkedBrandTypesBean = null;
                    checkedDeviceType = deviceTypes.get(options1);
                    Log.i("onOptionsSelect", tx);
                    getBrands();

                    strsToList2.clear();
                    comfigNameAdapter.notifyDataSetChanged();
                }
            })
                    .setTitleText("请选择类别")
                    .setCancelText("取消")
                    .setSubmitText("确定")
                    .setContentTextSize(20)//设置滚轮文字大小
                    .setDividerColor(getResources().getColor(R.color.city_dialog_content_bg))//设置分割线的颜色
                    .setSelectOptions(0, 0)//默认选中项
                    .setBgColor(getResources().getColor(R.color.city_dialog_content_bg))
                    .setTitleBgColor(getResources().getColor(R.color.white))
                    .setTitleColor(getResources().getColor(R.color.city_dialog_black))
                    .setCancelColor(getResources().getColor(R.color.city_dialog_blue))
                    .setSubmitColor(getResources().getColor(R.color.city_dialog_blue))
                    .setTextColorCenter(getResources().getColor(R.color.city_dialog_content_black))
                    .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                    .setLabels("", "", "")
                    .build();
            companyOptions.setPicker(items);//二级选择器
            companyOptions.show();
        } else {
            companyOptions.show();
        }
    }

    /**
     * 品牌选择
     */
    private void initBrandsPicker() {//条件选择器初始化
        if (brandsBeenList == null) {
            getBrands();
            return;
        }
        //  if (brandsOptions != null) {
        ArrayList<BrandsBean> items = brandsBeenList;
        brandsOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = brandsBeenList.get(options1).getDeviceTypeBrand();
                tv_brand.setText(tx);
                tv_model.setText("");
                checkedBrandTypesBean = null;
                checkedBrandsBean = brandsBeenList.get(options1);
                Log.i("onOptionsSelect", tx);
                getBrandTypes();

                strsToList2.clear();
                comfigNameAdapter.notifyDataSetChanged();
            }
        })
                .setTitleText("请选择品牌")
                .setCancelText("取消")
                .setSubmitText("确定")
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(getResources().getColor(R.color.city_dialog_content_bg))//设置分割线的颜色
                .setSelectOptions(0, 0)//默认选中项
                .setBgColor(getResources().getColor(R.color.city_dialog_content_bg))
                .setTitleBgColor(getResources().getColor(R.color.white))
                .setTitleColor(getResources().getColor(R.color.city_dialog_black))
                .setCancelColor(getResources().getColor(R.color.city_dialog_blue))
                .setSubmitColor(getResources().getColor(R.color.city_dialog_blue))
                .setTextColorCenter(getResources().getColor(R.color.city_dialog_content_black))
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .build();
        brandsOptions.setPicker(items);//二级选择器
        brandsOptions.show();
    /*    } else {
            brandsOptions.show();
        }*/
    }

    /**
     * 弹出型号选择
     */
    private void initBrandTypesPicker() {//条件选择器初始化
        if (brandTypesBeenList == null) {
            getBrandTypes();
            return;
        }
        // if (brandTypesOptions == null) {
        ArrayList<BrandTypesBean> items = brandTypesBeenList;
        brandTypesOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = brandTypesBeenList.get(options1).getDeviceTypeModel();
                String configName = brandTypesBeenList.get(options1).getConfigName();

                if (!TextUtils.isEmpty(configName)) {
                    String[] splitConfigName = configName.split(",");
                    strsToList2.clear();
                    for (int i = 0; i < splitConfigName.length; i++) {
                        strsToList2.add(splitConfigName[i]);
                    }

                    comfigNameAdapter.notifyDataSetChanged();

                }

                tv_model.setText(tx);
                checkedBrandTypesBean = brandTypesBeenList.get(options1);

                Log.i("onOptionsSelect", tx);
            }
        })
                .setTitleText("请选择品牌型号")
                .setCancelText("取消")
                .setSubmitText("确定")
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(getResources().getColor(R.color.city_dialog_content_bg))//设置分割线的颜色
                .setSelectOptions(0, 0)//默认选中项
                .setBgColor(getResources().getColor(R.color.city_dialog_content_bg))
                .setTitleBgColor(getResources().getColor(R.color.white))
                .setTitleColor(getResources().getColor(R.color.city_dialog_black))
                .setCancelColor(getResources().getColor(R.color.city_dialog_blue))
                .setSubmitColor(getResources().getColor(R.color.city_dialog_blue))
                .setTextColorCenter(getResources().getColor(R.color.city_dialog_content_black))
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .build();
        brandTypesOptions.setPicker(items);//二级选择器
        brandTypesOptions.show();
     /*   } else {
            brandTypesOptions.show();
        }*/
    }

    private void showPickerView() {// 弹出城市选择器
        KeyboardUtil.showInput(false, this);
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置

                selectedSheng = shengs.get(options1);
                selectedShi = sortShi.get(options1).get(options2);
                selectedXian = sortXian.get(options1).get(options2).get(options3);
                String tx = selectedSheng.getName() + "-" +
                        selectedShi.getName() + "-" +
                        selectedXian.getName();
                tv_companyAddress.setText(tx);
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();
        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }
}

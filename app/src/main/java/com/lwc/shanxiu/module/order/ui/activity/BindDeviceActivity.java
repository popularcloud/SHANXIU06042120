package com.lwc.shanxiu.module.order.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.bean.PickerView;
import com.lwc.shanxiu.bean.Sheng;
import com.lwc.shanxiu.bean.Shi;
import com.lwc.shanxiu.bean.Xian;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.BrandTypesBean;
import com.lwc.shanxiu.module.bean.BrandsBean;
import com.lwc.shanxiu.module.bean.DeviceType;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.pickerview.OptionsPickerView;
import com.lwc.shanxiu.utils.FileUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.KeyboardUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 完成
 */
public class BindDeviceActivity extends BaseActivity {


    @BindView(R.id.tv_brand)
    TextView tv_brand;
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.tv_device)
    TextView tv_device;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.et_company_name)
    EditText et_company_name;
    @BindView(R.id.et_company_phone)
    EditText et_company_phone; //单位使用人电话
    
    @BindView(R.id.et_used_name)
    EditText et_used_name; //单位使用人 
    @BindView(R.id.et_used_room)
    EditText et_used_room; //使用科室
    
    private String device, companyName,qrcodeIndex,companyPhone;
    private ArrayList<DeviceType> deviceTypes  = new ArrayList<>();
    private ArrayList<BrandsBean> brandsBeenList = new ArrayList<>();
    private ArrayList<BrandTypesBean> brandTypesBeenList  = new ArrayList<>();
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
    private String usedName;
    private String usedRoom;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_bind_device;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        setTitle("二维码信息绑定");
        getAreaData();
        myThread.start();
        showBack();
        getTypeAll();
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

        String address_city = (String) SharedPreferencesUtils.getParam(this,"address_city","东莞");

        if (!TextUtils.isEmpty(address_city) && shis != null) {
            for (int i = 0; i < shis.size(); i++) {
                if (shis.get(i).getName().equals(address_city)) {
                    selectedShi = shis.get(i);
                    shis.remove(selectedShi);
                    break;
                }
            }
            if (selectedShi != null) {
                for (int i = 0; i < shengs.size(); i++) {
                    if (shengs.get(i).getDmId().equals(selectedShi.getParentid())) {
                        selectedSheng = shengs.get(i);
                        shengs.remove(selectedSheng);
                        break;
                    }
                }
                if (selectedSheng != null) {
                    //shengs.clear();
                    shengs.add(0,selectedSheng);
                    // shis.clear();
                    shis.add(0,selectedShi);
                }
            }
        }
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

    @Override
    protected void init() {
    }

    @Override
    protected void initGetData() {
        qrcodeIndex = getIntent().getStringExtra("qrcodeIndex");
    }

    @Override
    protected void widgetListener() {
    }

    /**
     * 获取设备类型
     */
    private void getTypeAll() {
        HttpRequestUtils.httpRequest(this, "getDeviceTypes", RequestValue.SCAN_GETDEVICETYPES, null, "POST", new HttpRequestUtils.ResponseListener() {

            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        deviceTypes = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<DeviceType>>() {
                        });
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
        if(checkedDeviceType == null){
            ToastUtil.showToast(this,"请先选择设备类型！");
            return;
        }

        Map<String,String> param = new HashMap<>();
        param.put("device_type_id",checkedDeviceType.getDeviceTypeId());
        HttpRequestUtils.httpRequest(this, "getBrands", RequestValue.GET_SCAN_GETBRANDS, param, "POST", new HttpRequestUtils.ResponseListener() {

            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        LLog.i("getBrands:获取数据成功" + common.getInfo());
                        brandsBeenList = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<BrandsBean>>() {
                        });
                        if(brandsBeenList != null && brandsBeenList.size() > 0){
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
        if(checkedBrandsBean == null){
            ToastUtil.showToast(this,"请先选择品牌！");
            return;
        }

        brandTypesBeenList.clear();

        Map<String,String> param = new HashMap<>();
        param.put("device_type_brand_id",checkedBrandsBean.getDeviceTypeBrandId());
        HttpRequestUtils.httpRequest(this, "getModels", RequestValue.GET_SCAN_GETMODELS, param, "POST", new HttpRequestUtils.ResponseListener() {

            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        LLog.i("getModels:获取数据成功" + common.getInfo());
                        brandTypesBeenList = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<BrandTypesBean>>() {
                        });

                        if(brandTypesBeenList != null && brandTypesBeenList.size() > 0){
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

    @OnClick({R.id.btnAffirm, R.id.rl_device_type, R.id.tv_address,R.id.tv_brand,R.id.tv_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAffirm:
                device = tv_device.getText().toString().trim();
                companyName = et_company_name.getText().toString().trim();
                companyPhone = et_company_phone.getText().toString().trim();
                usedName = et_used_name.getText().toString().trim();
                usedRoom = et_used_room.getText().toString().trim();
                if (TextUtils.isEmpty(companyName) ) {
                    ToastUtil.showLongToast(this, "请输入单位名称");
                    return;
                }   
                
                if (TextUtils.isEmpty(usedName) ) {
                    ToastUtil.showLongToast(this, "请输入使用人姓名");
                    return;
                }
                if (TextUtils.isEmpty(usedRoom) ) {
                    ToastUtil.showLongToast(this, "请输入使用科室");
                    return;
                }
                if (selectedSheng == null) {
                    ToastUtil.showLongToast(this, "请选择单位地址");
                    return;
                }
                if (TextUtils.isEmpty(device) || checkedDeviceType == null) {
                    ToastUtil.showLongToast(this, "请选择设备类别");
                    return;
                }
                if (checkedBrandsBean == null || TextUtils.isEmpty(checkedBrandsBean.getDeviceTypeBrand()) ) {
                    ToastUtil.showLongToast(this, "请选择品牌");
                    return;
                }
                if (checkedBrandTypesBean == null || TextUtils.isEmpty(checkedBrandTypesBean.getDeviceTypeModel()) ) {
                    ToastUtil.showLongToast(this, "请选择型号");
                   return;
                }
                addDeviceInfo();
                break;
            case R.id.rl_device_type:
                initCompanyOptionsPicker();
                break;
            case R.id.tv_address:
                showPickerView();
                break;
            case R.id.tv_brand:
                initBrandsPicker();
                break;
            case R.id.tv_type:
                initBrandTypesPicker();
                break;
        }
    }

    private void addDeviceInfo() {
        if (Utils.isFastClick(2000)) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("deviceTypeId", checkedDeviceType.getDeviceTypeId());
        map.put("deviceTypeName", checkedDeviceType.getDeviceTypeName());
        map.put("deviceTypeBrand", checkedBrandsBean.getDeviceTypeBrand());
        map.put("deviceTypeBrandId", checkedBrandsBean.getDeviceTypeBrandId());
        map.put("deviceTypeModelId", checkedBrandTypesBean.getDeviceTypeModelId());
        map.put("deviceTypeModel", checkedBrandTypesBean.getDeviceTypeModel());
        map.put("userCompanyName", companyName);
        map.put("userName", usedName);
        map.put("userDetailCompanyName", usedRoom);

        if (!TextUtils.isEmpty(companyPhone) ) {
            map.put("userPhone", companyPhone);
        }

        //工程师和工程师所属公司信息
        User user = SharedPreferencesUtils.getInstance(this).loadObjectData(User.class);

        String maintenanceId = TextUtils.isEmpty(user.getUserId())?"":user.getUserId();
        map.put("maintenanceId", maintenanceId);
        String maintenanceName = TextUtils.isEmpty(user.getUserPhone())?"":user.getUserPhone();
        map.put("maintenanceName",maintenanceName);
        String maintenanceCompanyName = TextUtils.isEmpty(user.getParentCompanyName())?"":user.getParentCompanyName();
        map.put("maintenanceCompanyName", maintenanceCompanyName);
        String maintenanceCompanyId = TextUtils.isEmpty(user.getParentCompanyId())?"":user.getParentCompanyId();
        map.put("maintenanceCompanyId", maintenanceCompanyId);

        map.put("companyProvinceId", selectedSheng.getDmId());
        map.put("companyProvinceName", selectedSheng.getName());
        map.put("companyCityId", selectedShi.getDmId());
        map.put("companyCityName", selectedShi.getName());
        map.put("companyTownId", selectedXian.getDmId());
        map.put("companyTownName", selectedXian.getName());


        map.put("qrcodeIndex", qrcodeIndex);
        HttpRequestUtils.httpRequest(this, "addDeviceInfo", RequestValue.SCAN_CODE_ADD_DEVICE_INFO, map, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        ToastUtil.showLongToast(BindDeviceActivity.this, common.getInfo());
                        setResult(RESULT_OK);
                        finish();
                        break;
                    default:
                        ToastUtil.showLongToast(BindDeviceActivity.this, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showLongToast(BindDeviceActivity.this, msg);
            }
        });
    }

    /**
     * 弹出类别选择器
     */
    private void initCompanyOptionsPicker() {//条件选择器初始化
        if (deviceTypes == null) {
            getTypeAll();
            return;
        }
       // if (companyOptions == null) {
            ArrayList<DeviceType> items = deviceTypes;
            companyOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    String tx = deviceTypes.get(options1).getDeviceTypeName();
                    tv_device.setText(tx);
                    checkedDeviceType = deviceTypes.get(options1);
                    Log.i("onOptionsSelect", tx);
                    if(brandsBeenList != null){
                        brandsBeenList.clear();
                    }
                    if(brandTypesBeenList != null){
                        brandTypesBeenList.clear();
                    }

                    checkedBrandsBean = null;
                    checkedBrandTypesBean = null;

                    tv_brand.setText("请选择品牌");
                    tv_type.setText("请选择型号");


                    getBrands();
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
    /*    } else {
            companyOptions.show();
        }*/
    }

    /**
     * 品牌选择
     */
    private void initBrandsPicker() {//条件选择器初始化
        if (brandsBeenList == null) {
            getBrands();
            return;
        }
      //  if (brandsOptions == null) {
            ArrayList<BrandsBean> items = brandsBeenList;
            brandsOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    String tx = brandsBeenList.get(options1).getDeviceTypeBrand();
                    tv_brand.setText(tx);
                    tv_type.setText("");

                    checkedBrandsBean = brandsBeenList.get(options1);
                    Log.i("onOptionsSelect", tx);

                    if(brandTypesBeenList != null){
                        brandTypesBeenList.clear();
                    }

                    checkedBrandTypesBean = null;

                    tv_type.setText("请选择型号");

                    getBrandTypes();
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
                    tv_type.setText(tx);
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

    private void showPickerView() {// 弹出选择器
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
                tv_address.setText(tx);
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

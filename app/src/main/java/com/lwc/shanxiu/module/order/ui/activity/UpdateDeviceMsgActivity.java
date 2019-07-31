package com.lwc.shanxiu.module.order.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.lwc.shanxiu.module.bean.UpdateDeviceMsgBean;
import com.lwc.shanxiu.pickerview.OptionsPickerView;
import com.lwc.shanxiu.utils.FileUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.KeyboardUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdateDeviceMsgActivity extends BaseActivity {


    @BindView(R.id.et_work_unit)
    EditText etWorkUnit;
    @BindView(R.id.et_user)
    EditText etUser;
    @BindView(R.id.tv_unit_address)
    TextView tv_unit_address;
    @BindView(R.id.et_update_reason)
    EditText etUpdateReason;
    @BindView(R.id.btnSubmit)
    TextView btnSubmit;
    @BindView(R.id.ll_address)
    LinearLayout ll_address;
    private UpdateDeviceMsgBean updateDeviceMsgBean;


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

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_update_device_msg;
    }

    @Override
    protected void findViews() {
        setTitle("修改");
        showBack();
        updateDeviceMsgBean  = (UpdateDeviceMsgBean) getIntent().getSerializableExtra("presentBean");
    }

    @Override
    protected void init() {
        if(updateDeviceMsgBean != null){
            etWorkUnit.setText(updateDeviceMsgBean.getUserCompanyName());
            etUser.setText(updateDeviceMsgBean.getUserPhone());
            tv_unit_address.setText(updateDeviceMsgBean.getCompanyProvinceName()+updateDeviceMsgBean.getCompanyCityName()+updateDeviceMsgBean.getCompanyTownName());
        }
        getAreaData();
        myThread.start();
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

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {

    }

    @OnClick({R.id.ll_address,R.id.btnSubmit})
    public void onBtnClick(View view){
        switch (view.getId()){
            case R.id.ll_address:
                showPickerView();
                break;
            case R.id.btnSubmit:
                String workUnit = etWorkUnit.getText().toString().trim();
                String user = etUser.getText().toString().trim();
                String unitAddress = tv_unit_address.getText().toString().trim();
                String reason = etUpdateReason.getText().toString().trim();
                if(TextUtils.isEmpty(workUnit)){
                    ToastUtil.showToast(this,"请填写使用单位");
                    return;
                }
                if(TextUtils.isEmpty(user)){
                    ToastUtil.showToast(this,"请填写使用人号码");
                    return;
                }
                if(TextUtils.isEmpty(unitAddress)){
                    ToastUtil.showToast(this,"请选择单位地址");
                    return;
                }
                if(TextUtils.isEmpty(reason)){
                    ToastUtil.showToast(this,"请填写修改原因");
                    return;
                }
                updateDeviceMsgBean.setUserCompanyName(workUnit);
                updateDeviceMsgBean.setUserPhone(user);
                updateDeviceMsgBean.setUpdateReason(reason);

                submitDate(updateDeviceMsgBean);
                break;
        }
    }

    private void submitDate(UpdateDeviceMsgBean updateDeviceMsgBean){
        Map<String, String> map = new HashMap<>();
        map.put("relevanceId",updateDeviceMsgBean.getRelevanceId());
        map.put("userPhone",updateDeviceMsgBean.getUserPhone());
        map.put("userCompanyName",updateDeviceMsgBean.getUserCompanyName());
        map.put("companyProvinceId",updateDeviceMsgBean.getCompanyProvinceId());
        map.put("companyProvinceName",updateDeviceMsgBean.getCompanyProvinceName());
        map.put("companyCityId",updateDeviceMsgBean.getCompanyCityId());
        map.put("companyCityName",updateDeviceMsgBean.getCompanyCityName());
        map.put("companyTownId",updateDeviceMsgBean.getCompanyTownId());
        map.put("companyTownName",updateDeviceMsgBean.getCompanyTownName());
        map.put("updateReason",updateDeviceMsgBean.getUpdateReason());
        HttpRequestUtils.httpRequest(this, "updateDeviceInfo", RequestValue.GET_SCAN_UPDATEDEVICEINFO, map, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        ToastUtil.showToast(UpdateDeviceMsgActivity.this,"设备信息修改成功");
                        SharedPreferencesUtils.setParam(UpdateDeviceMsgActivity.this,"needUpdate",true);
                        Bundle bundle = new Bundle();
                        bundle.putInt("pagePosition",0);
                        IntentUtil.gotoActivity(UpdateDeviceMsgActivity.this,RepairHistoryNewActivity.class);
                        finish();
                        break;
                    default:
                        ToastUtil.showToast(UpdateDeviceMsgActivity.this,common.getInfo());
                        break;
                }
            }
            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showLongToast(UpdateDeviceMsgActivity.this, e.toString());
            }
        });
    }


    private Map<String,String> ObjectToMap(UpdateDeviceMsgBean updateDeviceMsgBean){
        Map<String, String> map = new HashMap<>();
        if (updateDeviceMsgBean == null) {
            return map;
        }
        Class clazz = updateDeviceMsgBean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), String.valueOf(field.get(updateDeviceMsgBean)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
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



    private void showPickerView() {// 弹出选择器
        KeyboardUtil.showInput(false, this);
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置

                selectedSheng = shengs.get(options1);
                selectedShi = sortShi.get(options1).get(options2);
                selectedXian = sortXian.get(options1).get(options2).get(options3);

                if(updateDeviceMsgBean != null){
                    updateDeviceMsgBean.setCompanyProvinceId(selectedSheng.getDmId());
                    updateDeviceMsgBean.setCompanyProvinceName(selectedSheng.getName());
                    updateDeviceMsgBean.setCompanyCityId(selectedShi.getDmId());
                    updateDeviceMsgBean.setCompanyCityName(selectedShi.getName());
                    updateDeviceMsgBean.setCompanyTownId(selectedXian.getDmId());
                    updateDeviceMsgBean.setCompanyTownName(selectedXian.getName());
                }

                String tx = selectedSheng.getName() + "-" +
                        selectedShi.getName() + "-" +
                        selectedXian.getName();
                tv_unit_address.setText(tx);
                }
        }).setTitleText("城市选择")
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

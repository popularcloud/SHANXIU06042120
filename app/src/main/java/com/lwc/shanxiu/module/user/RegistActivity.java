package com.lwc.shanxiu.module.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.textservice.TextInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.bean.PickerView;
import com.lwc.shanxiu.bean.Sheng;
import com.lwc.shanxiu.bean.Shi;
import com.lwc.shanxiu.bean.Xian;
import com.lwc.shanxiu.configs.FileConfig;
import com.lwc.shanxiu.controler.global.GlobalValue;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.pickerview.OptionsPickerView;
import com.lwc.shanxiu.utils.CommonUtils;
import com.lwc.shanxiu.utils.DecodeUtils;
import com.lwc.shanxiu.utils.FileUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.KeyboardUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.PictureUtils;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;

/**
 * 注册页面
 *
 */
public class RegistActivity extends BaseActivity {

    @BindView(R.id.iv_id_back)
    ImageView iv_id_back;
    @BindView(R.id.iv_id_positive)
    ImageView iv_id_positive;
    @BindView(R.id.iv_business_license)
    ImageView iv_business_license;
    @BindView(R.id.cb_boy)
    CheckBox cb_boy;
    @BindView(R.id.cb_girl)
    CheckBox cb_girl;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_getCode)
    TextView tv_getCode;    
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.btnNext)
    TextView btnNext;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_passWord)
    EditText et_passWord;
    @BindView(R.id.et_passWordAgain)
    EditText et_passWordAgain;
    @BindView(R.id.et_idCard)
    EditText et_idCard;
    @BindView(R.id.et_addressDetail)
    EditText et_addressDetail;
    @BindView(R.id.et_creditCode)
    EditText et_creditCode;
    @BindView(R.id.et_company_name)
    EditText et_company_name;
    @BindView(R.id.et_code)
    EditText et_code;


    private String idPositivePath;
    private String idBackPath;
    private String businessLicensePath;

    private String maintenance_sex;

    private ImageView presentImgView;

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
    private String phone;


    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_regist;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        setTitle("注册");
        showBack();

        getAreaData();
        myThread.start();
    }

    @OnClick({R.id.iv_id_positive,R.id.iv_id_back,R.id.tv_address,R.id.iv_business_license,
            R.id.tv_getCode,R.id.btnNext})
    public void onBtnClick(View view){
        switch (view.getId()){
            case R.id.iv_id_positive:
                ToastUtil.showPhotoSelect(this, 1);
                presentImgView = iv_id_positive;
                break;
            case R.id.iv_id_back:
                ToastUtil.showPhotoSelect(this, 1);
                presentImgView = iv_id_back;
                break;
            case R.id.iv_business_license:
                ToastUtil.showPhotoSelect(this, 1);
                presentImgView = iv_business_license;
                break;
            case R.id.tv_address:
                showPickerView();
                break;
            case R.id.btnNext: //下一步
                submitData();
                break;
            case R.id.tv_getCode:
                phone = et_phone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.showToast(this, "请输入手机号码");
                    return;
                }
                if (!CommonUtils.isPhoneNum(phone)) {
                    ToastUtil.showToast(this, "请输入正确的手机号码");
                    return;
                }
                getCode(phone);
                break;
        }
    }

    private void submitData() {
        Map<String,String> params = new HashMap<>();

        String name =et_name.getText().toString().trim();
        if(TextUtils.isEmpty(name)){
            ToastUtil.showToast(this,"请填写真实姓名");
            return;
        }
        params.put("user_realname",name);

        if(TextUtils.isEmpty(maintenance_sex)){
            ToastUtil.showToast(this,"请选择性别");
            return;
        }
        params.put("maintenance_sex",maintenance_sex);

        String phone =et_phone.getText().toString().trim();
        if(TextUtils.isEmpty(phone)){
            ToastUtil.showToast(this,"请填写手机号码");
            return;
        }
        params.put("user_phone",phone);

        String passWord =et_passWord.getText().toString().trim();
        String passWordAgain =et_passWordAgain.getText().toString().trim();
        if(TextUtils.isEmpty(passWord) || passWord.length() < 6 || passWord.length() > 16){
            ToastUtil.showToast(this,"请设置6到16位的登录密码");
            return;
        }
        if(TextUtils.isEmpty(passWordAgain)){
            ToastUtil.showToast(this,"请确认登录密码");
            return;
        }
        if(!passWord.equals(passWordAgain)){
            ToastUtil.showToast(this,"两次输入的密码不一致");
            return;
        }
        SharedPreferencesUtils.getInstance(RegistActivity.this).setParam(RegistActivity.this,"former_pwd", DecodeUtils.encode("passWord"));
        params.put("user_password", Utils.md5Encode(passWord));

        String idCard =et_idCard.getText().toString().trim();
        if(TextUtils.isEmpty(idCard)){
            ToastUtil.showToast(this,"请填写身份证号");
            return;
        }
        params.put("user_idcard",idCard);

        if(TextUtils.isEmpty(idPositivePath)){
            ToastUtil.showToast(this,"请选择身份证正面照");
            return;
        }
        params.put("idcard_face",idPositivePath);

        if(TextUtils.isEmpty(idBackPath)){
            ToastUtil.showToast(this,"请选择身份证背面照");
            return;
        }
        params.put("Idcard_back",idBackPath);

        if(selectedSheng == null){
            ToastUtil.showToast(this,"请选择店铺/公司地址");
            return;
        }else{
            params.put("company_province_id",selectedSheng.getDmId());
            params.put("company_province_name",selectedSheng.getName());
            params.put("company_city_id",selectedShi.getDmId());
            params.put("company_city_name",selectedShi.getName());
            params.put("company_town_id",selectedXian.getDmId());
            params.put("company_town_name",selectedXian.getName());
        }

        String addressDetail =et_addressDetail.getText().toString().trim();
        if(TextUtils.isEmpty(addressDetail)){
            ToastUtil.showToast(this,"请填写详细地址");
            return;
        }
        params.put("company_address",addressDetail);

        String companyName =et_company_name.getText().toString().trim();
        if(TextUtils.isEmpty(companyName)){
            ToastUtil.showToast(this,"请填写公司名称");
            return;
        }
        params.put("company_name",companyName);

        String creditCode =et_creditCode.getText().toString().trim();
        if(TextUtils.isEmpty(creditCode)){
            ToastUtil.showToast(this,"请填写营业执照信用代码");
            return;
        }
        params.put("company_no",addressDetail);

        if(TextUtils.isEmpty(businessLicensePath)){
            ToastUtil.showToast(this,"请选择营业执照");
            return;
        }
        params.put("company_img",businessLicensePath);

        String code =et_code.getText().toString().trim();
        if(TextUtils.isEmpty(code)){
            ToastUtil.showToast(this,"请填写验证码");
            return;
        }
        params.put("code",code);

        Bundle bundle = new Bundle();
        bundle.putString("params",JsonUtil.parserObjectToGson(params));
        IntentUtil.gotoActivity(RegistActivity.this,RegistTwoActivity.class,bundle);
    }

    /**
     * 获取验证码
     * @param phone
     */
    public void getCode(String phone) {
        HttpRequestUtils.httpRequest(this, "getCode", RequestValue.GET_CODE+phone, null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        handle.sendEmptyMessageDelayed(0, 1000);
                        ToastUtil.showToast(RegistActivity.this, "验证码发送成功");
                        break;
                    default:
                        ToastUtil.showLongToast(RegistActivity.this, common.getInfo());
                        break;
                }
            }
            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showLongToast(RegistActivity.this, msg);
            }
        });
    }

    private int count = 60;
    /**
     * 验证码倒计时
     */
    Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (count == 0) {
                count = 60;
                tv_getCode.setEnabled(true);
                tv_getCode.setText("获取验证码");
                return;
            }
            tv_getCode.setText(count-- + "s");
            tv_getCode.setEnabled(false);
            handle.sendEmptyMessageDelayed(0, 1000);
        }
    };

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

        String address_province = (String) SharedPreferencesUtils.getParam(this,"address_province","广东");
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

    private void showPickerView() {
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

                .setTitleText("选择公司/店铺地址")
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

    private Thread myThread = new Thread() {
        @Override
        public void run() {
            super.run();
            loadOptionsPickerViewData();
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GlobalValue.PHOTO_REQUESTCODE:
                    List<Uri> mSelected = Matisse.obtainResult(data);
                    if(mSelected != null && mSelected.size() > 0){
                        String picPath = PictureUtils.getPath(RegistActivity.this,mSelected.get(0));
                        if(presentImgView != null){
                            ImageLoaderUtil.getInstance().displayFromLocal(RegistActivity.this,presentImgView,picPath);
                        }
                        switch (presentImgView.getId()){
                            case R.id.iv_id_positive:
                                idPositivePath = picPath;
                                break;
                            case R.id.iv_id_back:
                                idBackPath = picPath;
                                break;
                            case R.id.iv_business_license:
                                businessLicensePath = picPath;
                                break;
                        }
                    }
                    break;
                case GlobalValue.CAMERA_REQUESTCODE:
                    try {
                        Bitmap bm = (Bitmap) data.getExtras().get("data");
                        if (bm == null) {
                            return;
                        }
                        File f = FileUtil.saveMyBitmap(bm);
                        File file = new Compressor.Builder(RegistActivity.this).setMaxHeight(1080).setMaxWidth(1920)
                                .setQuality(85).setCompressFormat(Bitmap.CompressFormat.PNG).setDestinationDirectoryPath(FileConfig.PATH_IMAGES)
                                .build().compressToFile(f);
                        String filePath = file.getAbsolutePath();

                        if(presentImgView != null){
                            ImageLoaderUtil.getInstance().displayFromLocal(RegistActivity.this,presentImgView,filePath);
                        }

                        switch (presentImgView.getId()){
                            case R.id.iv_id_positive:
                                idPositivePath = filePath;
                                break;
                            case R.id.iv_id_back:
                                idBackPath = filePath;
                                break;
                            case R.id.iv_business_license:
                                businessLicensePath = filePath;
                                break;
                        }
                    } catch (Exception e) {
                    }
                    break;
            }
        }
    }

    @Override
    protected void initGetData() {
    }

    @Override
    protected void widgetListener() {
        cb_boy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cb_girl.setChecked(false);
                    maintenance_sex = "1";
                }
            }
        });
        cb_girl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cb_boy.setChecked(false);
                    maintenance_sex = "0";
                }
            }
        });
    }

}

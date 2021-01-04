package com.lwc.shanxiu.module.order.ui.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.activity.ImageBrowseActivity;
import com.lwc.shanxiu.adapter.MyGridViewPhotoAdpter;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.configs.FileConfig;
import com.lwc.shanxiu.configs.ServerConfig;
import com.lwc.shanxiu.controler.global.GlobalValue;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.interf.OnBtnClickCalBack;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.CodeInfoBean;
import com.lwc.shanxiu.module.bean.PhotoToken;
import com.lwc.shanxiu.module.zxing.ui.CaptureActivity;
import com.lwc.shanxiu.utils.FileUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.KeyboardUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.PictureUtils;
import com.lwc.shanxiu.utils.QiniuUtil;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.view.MyGridView;
import com.lwc.shanxiu.widget.DialogScanStyle;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.zhihu.matisse.Matisse;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;


/**
 * 完成
 */
public class AccomplishActivity extends BaseActivity {


    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.tv_wc)
    TextView tv_wc;
    @BindView(R.id.et_cause)
    EditText et_cause;
    @BindView(R.id.rl_device_type)
    RelativeLayout rl_device_type;
    @BindView(R.id.iv_scan_code)
    ImageView iv_scan_code;
    @BindView(R.id.tv_device)
    TextView tv_device;
    @BindView(R.id.tv_tip)
    TextView tv_tip;
    @BindView(R.id.gridview_my)
    MyGridView myGridview;
    private List<String> urlStrs = new ArrayList();
    private int countPhoto = 8;
    private String imgPath1="";
    private PhotoToken token;
    private ProgressDialog pd;
    private File dataFile;
    private String typeStr;
    private String cause;
    private int nian, yue, ri;
    private Calendar calendar;
    private YearPickerDialog yearPickerDialog;
    private String orderId, orderType, qrcodeIndex;
    private int hasAward, statusType;
    private MyGridViewPhotoAdpter adpter;
    private String qrcodeIndex_accomp;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_accomplish;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        calendar = Calendar.getInstance();
        pd = new ProgressDialog(this);
        pd.setMessage("正在上传图片...");
        pd.setCancelable(false);
        showBack();
        urlStrs.add("");
        adpter = new MyGridViewPhotoAdpter(this, urlStrs);
        myGridview.setAdapter(adpter);
        myGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (adpter.getItem(position).equals("")) {
                    int count = countPhoto;
                    if (adpter.getCount() > 1) {
                        count = count - adpter.getCount()+1;
                    }
                    showTakePopupWindow1(count);
                } else {
                    Intent intent = new Intent(AccomplishActivity.this, ImageBrowseActivity.class);
                    intent.putExtra("index", position);
                    intent.putStringArrayListExtra("list", (ArrayList)adpter.getLists());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void init() {
    }

    @Override
    protected void initGetData() {
        orderId = getIntent().getStringExtra("orderId");
        orderType = getIntent().getStringExtra("orderType");
        hasAward = getIntent().getIntExtra("hasAward", 0);
        qrcodeIndex = getIntent().getStringExtra("qrcodeIndex");
        statusType = getIntent().getIntExtra("statusType", 0);
        if (statusType == 0) {
            setTitle("维修措施");
            if (!TextUtils.isEmpty(orderType) && orderType.equals("2")) {
                tv_wc.setText("上传维修完成图片(选填)");
            } else {
                tv_wc.setText("上传维修完成图片");
            }
            if (TextUtils.isEmpty(qrcodeIndex)) {
                rl_device_type.setVisibility(View.VISIBLE);
            } else {
                rl_device_type.setVisibility(View.GONE);
            }
        } else {
            rl_device_type.setVisibility(View.GONE);
            setTitle("送回安装");
            tv_wc.setText("上传安装完成图片");
        }
        if (orderType != null && orderType.equals("3")) {
            getFinishMsg();
        }
    }

    @Override
    protected void widgetListener() {
    }

    @OnClick({R.id.btnAffirm, R.id.rl_repair_type, R.id.rl_device_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAffirm:
                typeStr = tv_type.getText().toString().trim();
                cause = et_cause.getText().toString().trim();
                if (TextUtils.isEmpty(cause)) {
                    ToastUtil.showLongToast(this, "请填写维修措施");
                    return;
                }
                urlStrs = adpter.getLists();
                if (statusType == 0) {
                    if (!TextUtils.isEmpty(orderType) && !orderType.equals("2")) {
                        if (urlStrs == null || urlStrs.size() == 0 || TextUtils.isEmpty(urlStrs.get(0))) {
                            ToastUtil.showLongToast(this, "请上传维修完成后的照片");
                            return;
                        }
                    }
                } else {
                    if (urlStrs == null || urlStrs.size() <= 0 || TextUtils.isEmpty(urlStrs.get(0))) {
                        ToastUtil.showLongToast(this, "请上传安装完成后的照片");
                        return;
                    }
                }
                if (urlStrs != null && urlStrs.size() > 0 && !TextUtils.isEmpty(urlStrs.get(0))) {
                    uploadPhoto(new File(urlStrs.get(0)));
                } else {
                    detection();
                }
                break;
            case R.id.rl_repair_type:
                KeyboardUtil.showInput(false, AccomplishActivity.this);
                onYearMonthDayTimePicker();
                break;
            case R.id.rl_device_type:

                DialogScanStyle dialogScanStyle = new DialogScanStyle(AccomplishActivity.this, new OnBtnClickCalBack() {
                    @Override
                    public void onClick(String scanStr) {
                        if(scanStr == null){
                            Bundle bundle = new Bundle();
                            bundle.putInt("type", 1);
                            IntentUtil.gotoActivityForResult(AccomplishActivity.this, CaptureActivity.class, bundle, 8869);
                        }else{
                            if(scanStr.trim().length() < 6){
                                ToastUtil.showToast(AccomplishActivity.this,"请输入完整的二维码编码");
                                return;
                            }
                           qrcodeIndex = scanStr.toUpperCase();
                            checkedScanStr();

                        }
                    }
                });
                dialogScanStyle.show();
                break;
        }
    }

    /**
     * 查看二维码是否存在
     */
    private void checkedScanStr(){
        Map<String, String> map = new HashMap<>();
        if(!TextUtils.isEmpty(qrcodeIndex_accomp)){
            map.put("leaseQrcodeIndex", qrcodeIndex_accomp);
        }else{
            map.put("qrcodeIndex", qrcodeIndex);
        }
        HttpRequestUtils.httpRequest(this, "/scan/codeInfo", RequestValue.SCAN_CODE_INFO, map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        CodeInfoBean codeInfoBean = JsonUtil.parserGsonToObject(JsonUtil.getGsonValueByKey(response,"data"), CodeInfoBean.class);
                        qrcodeIndex = codeInfoBean.getQrcodeIndex();
                        submitDate();
                        break;
                    default:
                        ToastUtil.showLongToast(AccomplishActivity.this, "未绑定的编号请使用扫码方式绑定");
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
                ToastUtil.showLongToast(AccomplishActivity.this, msg);
            }
        });
    }


    public void submitDate(){
        Map<String, String> map = new HashMap<>();
        if(!TextUtils.isEmpty(qrcodeIndex_accomp)){
            map.put("leaseQrcodeIndex", qrcodeIndex_accomp);
        }else{
            map.put("qrcodeIndex", qrcodeIndex);
        }
        HttpRequestUtils.httpRequest(this, "scanCode", RequestValue.SCAN_CODE, map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        if(TextUtils.isEmpty(qrcodeIndex_accomp)){
                            if (!response.contains("data")) {
                                Bundle bundle = new Bundle();
                                bundle.putString("qrcodeIndex", qrcodeIndex);
                                IntentUtil.gotoActivityForResult(AccomplishActivity.this, BindDeviceActivity.class, bundle, 1520);
                            } else {
                                tv_device.setVisibility(View.VISIBLE);
                                iv_scan_code.setVisibility(View.GONE);
                            }
                        }else{
                            tv_device.setVisibility(View.VISIBLE);
                            iv_scan_code.setVisibility(View.GONE);
                        }

                        break;
                    default:
                       // finish();
                        ToastUtil.showLongToast(AccomplishActivity.this, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
                ToastUtil.showLongToast(AccomplishActivity.this, msg);
            }
        });
    }

    /*private void selectDate() {
        Date date = new Date(new Date().getTime() + 604800000);
        calendar.setTime(date);
        nian = calendar.get(Calendar.YEAR);
        yue = calendar.get(Calendar.MONTH);
        ri = calendar.get(Calendar.DATE);
        if (yearPickerDialog != null && yearPickerDialog.isShowing()) {
            yearPickerDialog.dismiss();
        }
        yearPickerDialog = new AccomplishActivity.YearPickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DATE, dayOfMonth);
                int monthI = monthOfYear + 1;
                String month = "" + monthI;
                if ((monthOfYear + 1) < 10) {
                    month = "0" + monthI;
                }
                String dayStr = "" + dayOfMonth;
                if (dayOfMonth < 10) {
                    dayStr = "0" + dayOfMonth;
                }
                String str = year + "-" + month + "-" + dayStr;
                tv_type.setText(str);
            }
        }, nian, yue, ri);
        yearPickerDialog.getDatePicker().setMinDate(date.getTime());
        yearPickerDialog.show();
    }*/

    public void onYearMonthDayTimePicker() {
        final cn.addapp.pickers.picker.DatePicker picker = new cn.addapp.pickers.picker.DatePicker(this);

        //当前年
        int year = calendar.get(Calendar.YEAR);
        //当前月
        int month = (calendar.get(Calendar.MONTH))+1;
        //当前月的第几天：即当前日
        int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
        picker.setCanLoop(true);
        picker.setWheelModeEnable(true);
        picker.setTopPadding(15);
        picker.setRangeStart(2016, 8, 29);
        picker.setRangeEnd(2111, 1, 11);
        picker.setSelectedItem(year,month,day_of_month);
        picker.setWeightEnable(true);
        picker.setLabel("年","月","日");
        picker.setSelectedTextColor(Color.parseColor("#1481ff"));
        picker.setLineColor(Color.WHITE);
        picker.setOnDatePickListener(new cn.addapp.pickers.picker.DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                tv_type.setText(year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new cn.addapp.pickers.picker.DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }

    private void detection() {
        if (Utils.isFastClick(2000)) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("remark", cause);
        map.put("warrantyTime", typeStr);
        map.put("hasAward", "" + hasAward);
        map.put("images", imgPath1);
        if(!TextUtils.isEmpty(qrcodeIndex_accomp)){
            map.put("leaseQrcodeIndex", qrcodeIndex_accomp);
        }else if(!TextUtils.isEmpty(qrcodeIndex)){
            map.put("qrcodeIndex", qrcodeIndex);
        }

        if (statusType != 0) {
            map.put("operate", "1");
        } else {
            map.put("operate", "0");
        }
        HttpRequestUtils.httpRequest(this, "updateOrderStatus", RequestValue.FINISH_ORDER, map, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        ToastUtil.showLongToast(AccomplishActivity.this, common.getInfo());
                        finish();
                        break;
                    default:
                        ToastUtil.showLongToast(AccomplishActivity.this, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showLongToast(AccomplishActivity.this, msg);
            }
        });
    }

    private void showTakePopupWindow1(int count) {
        ToastUtil.showPhotoSelect(this, count);
        getToken(null);
    }

    private void uploadPhoto(final File path) {
        if (token == null) {
            getToken(path);
            return;
        }
        if (!pd.isShowing())
            pd.show();
        final String key = Utils.getImgName();
        UploadManager um = QiniuUtil.getUploadManager();
        um.put(path, key, token.getSecurityToken(),
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info,
                                         JSONObject response) {
                        if (info.isOK()) {
                            String url = "";
                            if(!TextUtils.isEmpty(token.getDomain())){
                                url = token.getDomain()+ key;
                            }else{
                                url = ServerConfig.RUL_IMG + key;
                            }
                            if (TextUtils.isEmpty(imgPath1)) {
                                imgPath1 = url;
                            } else {
                                imgPath1 = imgPath1+","+url;
                            }
                            urlStrs.remove(path.getPath());
//                            Utils.deleteFile(path.getPath(), AccomplishActivity.this);
                            if (urlStrs.size() > 0 && !TextUtils.isEmpty(urlStrs.get(0))){
                                uploadPhoto(new File(urlStrs.get(0)));
                            } else {
                                detection();
                                pd.dismiss();
                            }
                            LLog.i("联网  图片地址" + url);
                        } else {
                            pd.dismiss();
                            ToastUtil.showLongToast(AccomplishActivity.this, "图片上传失败");
                        }
                    }
                }, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 8869 && resultCode == RESULT_OK) {
            qrcodeIndex = data.getStringExtra("qrcodeIndex");
            tv_device.setVisibility(View.VISIBLE);
            iv_scan_code.setVisibility(View.GONE);
        }else if(requestCode == 8869 && resultCode == 1112){
            qrcodeIndex_accomp = data.getStringExtra("qrcodeIndex_accomp");

            Map<String, String> params = new HashMap<String, String>();
            params.put("qrcodeIndex", qrcodeIndex_accomp);
            HttpRequestUtils.httpRequest(this, "扫描租赁二维码", RequestValue.SCAN_LEASECODE, params, "GET", new HttpRequestUtils.ResponseListener() {
                @Override
                public void getResponseData(String response) {
                    Common common = JsonUtil.parserGsonToObject(response, Common.class);
                    switch (common.getStatus()) {
                        case "1":
                            String data = JsonUtil.getGsonValueByKey(response, "data");
                            if (!TextUtils.isEmpty(data)) {
                                tv_device.setVisibility(View.VISIBLE);
                                iv_scan_code.setVisibility(View.GONE);
                            } else {
                                ToastUtil.showToast(AccomplishActivity.this,"租赁设备未绑定，请到首页扫一扫绑定!");
                                qrcodeIndex_accomp = "";
                            }
                            break;
                        default:
                            ToastUtil.showToast(AccomplishActivity.this, common.getInfo());
                            break;
                    }
                }

                @Override
                public void returnException(Exception e, String msg) {
                    ToastUtil.showToast(AccomplishActivity.this, "" + msg);
                }
            });

        } else if(requestCode == 1520 && resultCode == RESULT_OK){
            qrcodeIndex = data.getStringExtra("qrcodeIndex");
            tv_device.setVisibility(View.VISIBLE);
            iv_scan_code.setVisibility(View.GONE);
        } else if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GlobalValue.PHOTO_REQUESTCODE:
                    List<Uri> mSelected = Matisse.obtainResult(data);
                    if (mSelected != null && mSelected.size() > 0) {
                        urlStrs = adpter.getLists();
                        urlStrs.remove(urlStrs.size()-1);
                        for(int i=0; i<mSelected.size(); i++) {
                            Uri uri = mSelected.get(i);
                            urlStrs.add(PictureUtils.getPath(AccomplishActivity.this, uri));
                        }
                    }
                    if (urlStrs.size() < countPhoto) {
                        urlStrs.add("");
                    }
                    adpter.setLists(urlStrs);
                    adpter.notifyDataSetChanged();
                    break;
                case GlobalValue.CAMERA_REQUESTCODE:
                    //上传图片
                    try {
                        Bitmap bm = (Bitmap) data.getExtras().get("data");
                        if (bm == null) {
                            return;
                        }
                        File f = FileUtil.saveMyBitmap(bm);
                        File file = new Compressor.Builder(AccomplishActivity.this).setMaxHeight(1080).setMaxWidth(1920)
                                .setQuality(85).setCompressFormat(Bitmap.CompressFormat.PNG).setDestinationDirectoryPath(FileConfig.PATH_IMAGES)
                                .build().compressToFile(f);
                        if (file != null) {
                            dataFile = file;
                        } else {
                            dataFile = f;
                        }
                    } catch (Exception e) {
                    }
                    if (dataFile != null) {
                        if (urlStrs != null && urlStrs.get(urlStrs.size()-1).equals("")) {
                            urlStrs.remove(urlStrs.size()-1);
                        }
                        urlStrs.add(dataFile.getAbsolutePath());
                        if (urlStrs.size() < countPhoto) {
                            urlStrs.add("");
                        }
                        adpter.setLists(urlStrs);
                        adpter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showToast(AccomplishActivity.this, "选择图片失败");
                    }
                    break;
            }
        }
    }

    private void getFinishMsg() {
        if (token != null) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderId);
        HttpRequestUtils.httpRequest(this, "getFinishMsg()", RequestValue.GET_FINISH_MSG, map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                LLog.iNetSucceed(" getFinishMsg " + response);
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        try {
                            JSONObject object = new JSONObject(JsonUtil.getGsonValueByKey(response, "data"));
                            String msg = object.optString("msg");
                            if (!TextUtils.isEmpty(msg)) {
                                tv_tip.setVisibility(View.VISIBLE);
                                tv_tip.setText(msg);
                            }
                        } catch (Exception e){}
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
    }

    private void getToken(final File photoPath) {
        if (token != null) {
            return;
        }
        HttpRequestUtils.httpRequest(this, "获取上传图片token", RequestValue.GET_PICTURE, null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                LLog.iNetSucceed(" getPhotoToken " + response);
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        token = JsonUtil.parserGsonToObject(JsonUtil.getGsonValueByKey(response, "data"), PhotoToken.class);
                        if (photoPath != null) {
                            uploadPhoto(photoPath);
                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
    }

    public class YearPickerDialog extends DatePickerDialog {
        public YearPickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
            super(context, callBack, year, monthOfYear, dayOfMonth);
        }

        public YearPickerDialog(Context context, int theme, DatePickerDialog.OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth) {
            super(context, theme, listener, year, monthOfYear, dayOfMonth);
        }

        @Override
        public void onDateChanged(DatePicker view, int year, int month, int day) {
            super.onDateChanged(view, year, month, day);
            this.setTitle(year + "年" + (month + 1) + "月" + day + "日");
        }
    }
}

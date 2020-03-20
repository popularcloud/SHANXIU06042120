package com.lwc.shanxiu.module.order.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.activity.ImageBrowseActivity;
import com.lwc.shanxiu.adapter.MyGridViewPhotoAdpter;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.bean.Repairs;
import com.lwc.shanxiu.configs.FileConfig;
import com.lwc.shanxiu.configs.ServerConfig;
import com.lwc.shanxiu.controler.global.GlobalValue;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.Order;
import com.lwc.shanxiu.module.bean.PhotoToken;
import com.lwc.shanxiu.pickerview.OptionsPickerView;
import com.lwc.shanxiu.utils.FileUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.KeyboardUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.PictureUtils;
import com.lwc.shanxiu.utils.QiniuUtil;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.view.MyGridView;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.zhihu.matisse.Matisse;

import org.json.JSONObject;

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
 * 设备返修
 */
public class EquipmentRepairActivity extends BaseActivity {


    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.et_cause)
    EditText et_cause;
    @BindView(R.id.tv_tip)
    TextView tv_tip;
    @BindView(R.id.gridview_my)
    MyGridView myGridview;
    private List<String> urlStrs = new ArrayList();
    private int countPhoto = 8;
    private String imgPath1;
    private PhotoToken token;
    private ProgressDialog pd;
    private File dataFile;
    private String cause;
    private OptionsPickerView pvOptions;
    private Repairs checkedRepairs;
    private Order myOrder;
    private MyGridViewPhotoAdpter adpter;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_equipment_repair;
    }

    @Override
    protected void findViews() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ButterKnife.bind(this);
        pd = new ProgressDialog(this);
        pd.setMessage("正在上传图片...");
        pd.setCancelable(false);
        setTitle("返厂维修");
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
                    Intent intent = new Intent(EquipmentRepairActivity.this, ImageBrowseActivity.class);
                    intent.putExtra("index", position);
                    intent.putStringArrayListExtra("list", (ArrayList)adpter.getLists());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void init() {
        getRepairses();
    }

    @Override
    protected void initGetData() {
        myOrder = (Order)getIntent().getSerializableExtra("data");
        if (!TextUtils.isEmpty(myOrder.getOrderType()) && myOrder.getOrderType().equals("3")) {
            if (myOrder.getIsWarranty() == 1) {
                tv_tip.setText("注：保修期外，除上门费外，还需收取用户检测费和维修费，具体以厂家报价为准！");
            } else {
                tv_tip.setText("注：保修期内，返修不收取用户费用！");
            }
            getFinishMsg();
        } else {
            tv_tip.setVisibility(View.GONE);
        }
    }

    private void getFinishMsg() {
        if (token != null) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("orderId", myOrder.getOrderId());
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

    @Override
    protected void widgetListener() {
    }

    @OnClick({R.id.btnAffirm, R.id.rl_repair_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAffirm:
                if (checkedRepairs == null) {
                    ToastUtil.showLongToast(this, "请选择返修设备类型");
                    return;
                }
                cause = et_cause.getText().toString().trim();
                if (TextUtils.isEmpty(cause)) {
                    ToastUtil.showLongToast(this, "请填写返修原因");
                    return;
                }
                if (adpter.getLists().size() == 0 || TextUtils.isEmpty(adpter.getLists().get(0))) {
                    ToastUtil.showLongToast(this, "请上传返修设备图片");
                    return;
                }
                urlStrs = adpter.getLists();
                if (urlStrs != null && urlStrs.size() > 0 && !TextUtils.isEmpty(urlStrs.get(0))) {
                    uploadPhoto(new File(urlStrs.get(0)));
                } else {
                    detection();
                }
                break;
            case R.id.rl_repair_type:
                KeyboardUtil.showInput(false, EquipmentRepairActivity.this);
                if (pvOptions != null) {
                    pvOptions.show();
                }
                break;
        }
    }

    private List<Repairs> repairses = new ArrayList<>();

    private void getRepairses() {
        Map<String, String> map = new HashMap<>();
        map.put("deviceMold", "2");
        map.put("orderId", myOrder.getOrderId());
        HttpRequestUtils.httpRequest(this, "查询返修类型", RequestValue.GET_MALFUNCTION, map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        if (repairses == null) {
                            repairses = new ArrayList<>();
                        } else {
                            repairses.clear();
                        }
                        repairses = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<Repairs>>() {
                        });
                        setOptionsPickerData();
                        break;
                    default:
                        ToastUtil.showLongToast(EquipmentRepairActivity.this, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
            }
        });
    }

    public void setOptionsPickerData() {
        if (pvOptions == null && repairses != null && repairses.size() > 0) {
            pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {


                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                //返回的分别是三个级别的选中位置
                    if (repairses.get(options1) == null) {
                        ToastUtil.showLongToast(EquipmentRepairActivity.this, "请选择返修设备类型");
                        return;
                    }
                    String tx = repairses.get(options1).getDeviceTypeName();
                    tv_type.setText(tx);
                    checkedRepairs = repairses.get(options1);
                }
            })
                    .setTitleText("设备类型")
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
            pvOptions.setPicker(repairses);//二级选择器
        } else if (pvOptions == null) {
            getRepairses();
        }
    }

    private void detection() {
        if (Utils.isFastClick(1000)) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("order_id", myOrder.getOrderId());
        map.put("type_id", checkedRepairs.getDeviceTypeId());
        map.put("type_name", checkedRepairs.getDeviceTypeName());
        map.put("repair_reason", cause);
        map.put("repair_images", imgPath1);
        HttpRequestUtils.httpRequest(this, "设备返修", RequestValue.POST_PERSONAL_DETECTION, map, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        ToastUtil.showToast(EquipmentRepairActivity.this, common.getInfo());
                        finish();
                        break;
                    default:
                        ToastUtil.showLongToast(EquipmentRepairActivity.this, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
                if (msg != null && !msg.equals("")) {
                    ToastUtil.showLongToast(EquipmentRepairActivity.this, msg);
                } else {
                    ToastUtil.showLongToast(EquipmentRepairActivity.this, "请求失败，请稍候重试!");
                }
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
                                url = token.getDomain() + key;
                            }else{
                                url = ServerConfig.RUL_IMG + key;
                            }
                            if (TextUtils.isEmpty(imgPath1)) {
                                imgPath1 = url;
                            } else {
                                imgPath1 = imgPath1+","+url;
                            }
                            urlStrs.remove(path.getPath());
//                            Utils.deleteFile(path.getPath(), EquipmentRepairActivity.this);
                            if (urlStrs.size() > 0 && !TextUtils.isEmpty(urlStrs.get(0))){
                                uploadPhoto(new File(urlStrs.get(0)));
                            } else {
                                detection();
                                pd.dismiss();
                            }
                            LLog.i("联网  图片地址" + url);
                        } else {
                            pd.dismiss();
                            ToastUtil.showLongToast(EquipmentRepairActivity.this, "图片上传失败");
                        }
                    }
                }, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GlobalValue.PHOTO_REQUESTCODE:
                    List<Uri> mSelected = Matisse.obtainResult(data);
                    if (mSelected != null && mSelected.size() > 0) {
                        urlStrs = adpter.getLists();
                        urlStrs.remove(urlStrs.size()-1);
                        for(int i=0; i<mSelected.size(); i++) {
                            Uri uri = mSelected.get(i);
                            urlStrs.add(PictureUtils.getPath(EquipmentRepairActivity.this, uri));
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
                        File file = new Compressor.Builder(EquipmentRepairActivity.this).setMaxHeight(1080).setMaxWidth(1920)
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
                        ToastUtil.showToast(EquipmentRepairActivity.this, "选择图片失败");
                    }
                    break;
            }
        }
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
}

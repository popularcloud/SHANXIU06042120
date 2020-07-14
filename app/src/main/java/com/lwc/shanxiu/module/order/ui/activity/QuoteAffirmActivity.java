package com.lwc.shanxiu.module.order.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.lwc.shanxiu.module.bean.Solution;
import com.lwc.shanxiu.module.partsLib.ui.activity.BuyListActivity;
import com.lwc.shanxiu.module.partsLib.ui.activity.PartsMainActivity;
import com.lwc.shanxiu.module.partsLib.ui.bean.PartsBean;
import com.lwc.shanxiu.pickerview.OptionsPickerView;
import com.lwc.shanxiu.utils.DialogUtil;
import com.lwc.shanxiu.utils.FileUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.KeyboardUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.PictureUtils;
import com.lwc.shanxiu.utils.QiniuUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.view.MyGridView;
import com.lwc.shanxiu.view.TypeItem;
import com.lwc.shanxiu.widget.CustomDialog;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.zhihu.matisse.Matisse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;

/**
 * 故障确认单
 */
public class QuoteAffirmActivity extends BaseActivity {


    @BindView(R.id.et_desc)
    EditText et_desc;
    @BindView(R.id.et_qtfy)
    EditText et_qtfy;
    @BindView(R.id.et_money_desc)
    EditText et_money_desc;
    @BindView(R.id.tv_solve_scheme)
    TextView tv_solve_scheme;
    @BindView(R.id.tv_smf)
    TextView tv_smf;
    @BindView(R.id.tv_rjf)
    EditText tv_rjf;
    @BindView(R.id.tv_total)
    TextView tv_total;
    @BindView(R.id.rl_rjf)
    RelativeLayout rl_rjf;
    @BindView(R.id.rl_smf)
    RelativeLayout rl_smf;
    @BindView(R.id.ll_jia)
    LinearLayout ll_jia;
    @BindView(R.id.layout_type_list)
    LinearLayout layout_type_list;
    @BindView(R.id.txtJia)
    TextView txtJia;
    @BindView(R.id.tv_msmf)
    TextView tv_msmf;
    @BindView(R.id.tv_unit)
    TextView tv_unit;
    @BindView(R.id.tv_errorMsg)
    TextView tv_errorMsg;
    @BindView(R.id.btnRefuse)
    Button btnRefuse;
/*    @BindView(R.id.et_yjfy)
    EditText et_yjfy; */
    @BindView(R.id.txtHardwareReplacement)
    TextView txtHardwareReplacement;
    @BindView(R.id.txtDetailedList)
    TextView txtDetailedList;
    @BindView(R.id.gridview_my)
    MyGridView myGridview;
    @BindView(R.id.txtDeviceType)
    TextView txtDeviceType;
    @BindView(R.id.rLayoutHardwareReplacement)
    RelativeLayout rLayoutHardwareReplacement;
    @BindView(R.id.rLayoutDetailedList)
    RelativeLayout rLayoutDetailedList;
    private List<String> urlStrs = new ArrayList();
    private int countPhoto = 8;
    private List<Solution> repairses = new ArrayList<>();
    private List<Solution> selectRepairses = new ArrayList<>();

    private List<Repairs> repairseList = new ArrayList<>();
    private List<List<Solution>> sortSolutions = new ArrayList<>();

    private PhotoToken token;
    private ProgressDialog pd;
    private File dataFile;
    private OptionsPickerView pvOptions;
    private Solution checkedRepairs;
    private Order myOrder;
    private String desc, other_cost, moneyDesc, imgPath1;
    private int request = 12302;
    private MyGridViewPhotoAdpter adpter;
    private String hardwareCost;
    private boolean isSelect;
    private String deviceTypeModel;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_quote_affirm;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        pd = new ProgressDialog(this);
        pd.setMessage("正在上传图片...");
        pd.setCancelable(false);
        showBack();
        setTitle("故障检测结果");
        setRightText("收费标准","#666666",new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          /*      if("0".equals(deviceTypeModel)){
                    ToastUtil.showToast(QuoteAffirmActivity.this,"请先选择设备类型");
                    return;
                }
                // 后台对应的收费标准  3 是办公设备  4是家用电器
                int typeId = 4;
                if("3".equals(deviceTypeModel)){
                    typeId = 4;
                }
                if("1".equals(deviceTypeModel)){
                    typeId = 3;
                }

                Bundle bundle = new Bundle();
                bundle.putString("url", ServerConfig.DOMAIN.replace("https", "http")+"/main/h5/charge.html?typeid="+typeId);
                bundle.putString("title", "收费标准");
                IntentUtil.gotoActivity(QuoteAffirmActivity.this, InformationDetailsActivity.class, bundle);*/

                Bundle bundle = new Bundle();
                bundle.putString("deviceTypeMold",myOrder.getDeviceTypeMold());
                bundle.putString("title", "收费标准");
                IntentUtil.gotoActivity(QuoteAffirmActivity.this, FeeStandardActivity.class, bundle);
            }
        });
        String str = "无法维修(填写原因)";
        btnRefuse.setText(Utils.getSpannableStringBuilder(0, 4, getResources().getColor(R.color.white), str, 15));

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
                    Intent intent = new Intent(QuoteAffirmActivity.this, ImageBrowseActivity.class);
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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent != null){
         /*   String checkedSum = intent.getStringExtra("checkedSum");

            if(allMoney != null){
                txtHardwareReplacement.setText(String.valueOf(allMoney/100));
                tv_unit.setVisibility(View.VISIBLE);
                jiaMoney();
            }

            if(!TextUtils.isEmpty(checkedSum)){
                txtDetailedList.setText("查看清单("+checkedSum+")");
            }*/

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.d("aaa","数据来了");

        //获取购物清单
        String addListsStr = (String) SharedPreferencesUtils.getParam(this,"addListBeans","");
        List<PartsBean> beanArrayList = JsonUtil.parserGsonToArray(addListsStr, new TypeToken<ArrayList<PartsBean>>() {});

        if(beanArrayList != null && beanArrayList.size() > 0){
            Double allMoney = 0d;
            int checkedSum = 0;
            for(int i = 0;i < beanArrayList.size();i++){
                PartsBean partsBean =  beanArrayList.get(i);
                // if(partsBean.isItemIsChecked()){
                allMoney += partsBean.getAccessoriesPrice() * partsBean.getSumSize();
                checkedSum += partsBean.getSumSize();
                //  }
            }
            txtHardwareReplacement.setText(Utils.getMoney(String.valueOf(allMoney/100)));
            tv_unit.setVisibility(View.VISIBLE);
            txtDetailedList.setText("查看清单("+checkedSum+")");

            jiaMoney();

        }else{
            tv_unit.setVisibility(View.GONE);
            txtHardwareReplacement.setText("");
            txtDetailedList.setText("查看清单");
        }

        //如果选择了硬件配件 就隐藏备注
      /*  if(!"".equals(txtHardwareReplacement)){
            et_money_desc.setVisibility(View.GONE);
        }else{
            et_money_desc.setVisibility(View.VISIBLE);
        }*/
    }

    @Override
    protected void initGetData() {
        myOrder = (Order)getIntent().getSerializableExtra("data");
        deviceTypeModel = getIntent().getStringExtra("deviceTypeModel");
        if("3".equals(deviceTypeModel)){
            txtDeviceType.setText("家用电器");
            rLayoutHardwareReplacement.setVisibility(View.GONE);
            rLayoutDetailedList.setVisibility(View.GONE);
        }else{
            txtDeviceType.setText("办公设备");
        }

        if (myOrder.getOrderType()!= null && myOrder.getOrderType().equals("3")) {
            ll_jia.setVisibility(View.GONE);
            txtJia.setVisibility(View.GONE);
        }
    }

    @Override
    protected void widgetListener() {
        et_qtfy.addTextChangedListener(new TextWatcher()
        {
            public void afterTextChanged(Editable edt)
            {
                jiaMoney();
            }
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
        });
        tv_rjf.addTextChangedListener(new TextWatcher()
        {
            public void afterTextChanged(Editable edt)
            {
                jiaMoney();
            }
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
        });
      /*  et_yjfy.addTextChangedListener(new TextWatcher()
        {
            public void afterTextChanged(Editable edt)
            {
                jiaMoney();
            }
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
        });*/
    }

    private void getRepairses() {
        if (Utils.isFastClick(2000)){
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("orderId", myOrder.getOrderId());
        HttpRequestUtils.httpRequest(this, "故障类型列表", RequestValue.GET_MALFUNCTION_FAULT+"?deviceMold="+deviceTypeModel, map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        if (myOrder.getOrderType()!= null && myOrder.getOrderType().equals("3")) {
                            if (repairses == null) {
                                repairses = new ArrayList<>();
                            } else {
                                repairses.clear();
                            }
                            if (response.contains("data"))
                                repairses = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<Solution>>() {
                                });
                            if (repairses == null || repairses.size() == 0) {
                                com.lwc.shanxiu.map.ToastUtil.show(QuoteAffirmActivity.this, "未查询到解决方案，请联系客服！");
                            }
                        } else {
                            if (repairseList == null) {
                                repairseList = new ArrayList<>();
                            } else {
                                repairseList.clear();
                            }
                            if (sortSolutions == null) {
                                sortSolutions = new ArrayList<>();
                            } else {
                                sortSolutions.clear();
                            }
                            try {
                                repairseList = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<Repairs>>() {
                                });
                                if (repairseList != null && repairseList.size() > 0) {
                                    for (int i = 0; i < repairseList.size(); i++) {
                                        Repairs repairs = repairseList.get(i);
                                        List<Solution> s = repairs.getFaults();
                                        if (s != null && s.size() > 0) {
                                            sortSolutions.add(s);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    default:
                        ToastUtil.showLongToast(QuoteAffirmActivity.this, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
            }
        });
    }

    @OnClick({R.id.btnAffirm, R.id.tv_msmf, R.id.tl_solve_scheme, R.id.btnRefuse,R.id.rLayoutHardwareReplacement,R.id.rLayoutDetailedList})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_msmf:
                if (!isSelect) {
                    tv_msmf.setCompoundDrawables(Utils.getDrawable(this, R.drawable.presentation_mode_choice), null, null, null);
                } else {
                    tv_msmf.setCompoundDrawables(Utils.getDrawable(this, R.drawable.presentation_mode_no_choice), null, null, null);
                }
                isSelect = !isSelect;
                jiaMoney();
                break;
            case R.id.btnAffirm:
                if (isVerify()) {
                    urlStrs = adpter.getLists();
                    if (urlStrs != null && urlStrs.size() > 0 && !TextUtils.isEmpty(urlStrs.get(0))) {
                        uploadPhoto(new File(urlStrs.get(0)));
                    } else {
                        affirm();
                    }
                }
                break;
            case R.id.btnRefuse:
                DialogUtil.showMessageDg(QuoteAffirmActivity.this, "温馨提示", "由于技术原因造成的无法维修，“您将无法获得基本上门费”！", new CustomDialog.OnClickListener() {

                    @Override
                    public void onClick(CustomDialog dialog, int id, Object object) {
                        dialog.dismiss();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data", myOrder);
                        IntentUtil.gotoActivityForResult(QuoteAffirmActivity.this, CannotMaintainActivity.class, bundle, request);
                    }
                });
                break;
            case R.id.tl_solve_scheme:
                KeyboardUtil.showInput(false, QuoteAffirmActivity.this);
                if (myOrder.getOrderType()!= null && myOrder.getOrderType().equals("3") && selectRepairses.size() == 1) {
                    com.lwc.shanxiu.map.ToastUtil.show(QuoteAffirmActivity.this, "厂家售后订单只能选择一项解决方案");
                    break;
                } else if (selectRepairses.size() == 5){
                    com.lwc.shanxiu.map.ToastUtil.show(QuoteAffirmActivity.this, "最多能选择5项解决方案");
                    break;
                }
                initOptionPicker();
                if (pvOptions != null) {
                    pvOptions.show();
                }
                break;
            case R.id.rLayoutHardwareReplacement: //更换硬件
                IntentUtil.gotoActivity(this, PartsMainActivity.class);
                break;
            case R.id.rLayoutDetailedList: //查看清单
                IntentUtil.gotoActivity(this, BuyListActivity.class);
                break;
        }
    }



    private void affirm() {
        if (myOrder.getOrderType()!= null && myOrder.getOrderType().equals("3") && checkedRepairs.getExampleId().equals("2")){
            //更换配件
            Bundle bundle = new Bundle();
            bundle.putSerializable("order", myOrder);
            bundle.putString("fault_describe", desc);
            bundle.putSerializable("solution", checkedRepairs);
            bundle.putString("imgs", imgPath1);
            IntentUtil.gotoActivityForResult(this, PartsListActivity.class, bundle, request);
            return;
        }
        if (Utils.isFastClick(1000)) {
            return;
        }
        JSONObject map = new JSONObject();
        try {

            map.put("orderId", myOrder.getOrderId());
            map.put("faultDescribe", desc);
            String exampleId = "";
            for (int i=0; i<selectRepairses.size(); i++) {
                exampleId = exampleId+selectRepairses.get(i).getExampleId()+"_"+selectRepairses.get(i).getMaintainCost()+",";
            }
            map.put("exampleId", exampleId.substring(0, exampleId.length()-1));

            if (myOrder.getOrderType() == null || !myOrder.getOrderType().equals("3")) {
                if (!TextUtils.isEmpty(other_cost)) {
                    map.put("otherCost", Utils.cheng(other_cost, "100"));
                } else {
                    map.put("otherCost", "0");
                }

                String rjf = tv_rjf.getText().toString().trim();
                if (!TextUtils.isEmpty(rjf)) {
                    map.put("maintainCost", Utils.cheng(rjf, "100"));
                }

                String yjf = txtHardwareReplacement.getText().toString().trim();
                if (!TextUtils.isEmpty(yjf)) {
                    map.put("hardwareCost", Utils.cheng(yjf, "100"));
                }else{
                    map.put("hardwareCost","0");
                }

                if (!TextUtils.isEmpty(moneyDesc)) {
                    map.put("remark", moneyDesc);
                } else {
                    map.put("remark", "");
                }
                if (isSelect) {
                    map.put("isWaiver", "1");
                } else {
                    map.put("isWaiver", "0");
                }

                String addListsStr = (String) SharedPreferencesUtils.getParam(this,"addListBeans","");
                List<PartsBean> beanArrayList = JsonUtil.parserGsonToArray(addListsStr, new TypeToken<ArrayList<PartsBean>>() {});
                if(!TextUtils.isEmpty(yjf) && beanArrayList != null && beanArrayList.size() > 0){
                    JSONArray jSONArray = new JSONArray();
                    for (int i = 0;i < beanArrayList.size();i++) {
                        PartsBean partsBean = beanArrayList.get(i);
                        if(partsBean.getSumSize() > 0){
                            JSONObject o = new JSONObject();
                            o.put("partsId", partsBean.getAccessoriesId());
                            o.put("partsNum",partsBean.getSumSize());
                            jSONArray.put(o);
                        }
                    }
                    map.put("partInfo",jSONArray);
                }else{
                    map.put("partInfo",new JSONArray());
                }
            }

            if (!TextUtils.isEmpty(imgPath1)) {
                map.put("describeImages", imgPath1);
            }
        } catch (Exception e){}
        HttpRequestUtils.httpRequestJson(this, "提交检测报告", RequestValue.POST_ORDER_PERSONAL_DETECTION, map, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":

                        //如果检测报告提交成功  则清空购物车
                        SharedPreferencesUtils.getInstance(QuoteAffirmActivity.this).setParam(QuoteAffirmActivity.this,"addListBeans","");

                        if (checkedRepairs.getExampleId().equals("3")) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("data", myOrder);
                            IntentUtil.gotoActivityAndFinish(QuoteAffirmActivity.this, EquipmentRepairActivity.class, bundle);
                        } else {
                            ToastUtil.showToast(QuoteAffirmActivity.this, common.getInfo());
                            finish();
                        }
                        break;
                    default:
                        ToastUtil.showToast(QuoteAffirmActivity.this, common.getInfo());
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showToast(QuoteAffirmActivity.this, msg);
            }
        });
    }

    /**
     * 验证数据是否正确
     * @return true 表示正确  false 有数据错误
     */
    private boolean isVerify() {
        desc = et_desc.getText().toString().trim();
        if (TextUtils.isEmpty(desc)) {
            ToastUtil.showToast(this, "请填写故障原因");
            return false;
        }
        if (adpter.getLists() == null || adpter.getLists().size() == 0 || TextUtils.isEmpty(adpter.getLists().get(0).trim())) {
            ToastUtil.showToast(this, "请上传故障图片");
            return false;
        }
        if (selectRepairses == null || selectRepairses.size() == 0) {
            ToastUtil.showToast(this, "请选择解决方案");
            return false;
        }
        if (myOrder.getOrderType()!= null && myOrder.getOrderType().equals("3")) {
            return true;
        }
        String rjf = tv_rjf.getText().toString().trim();
        if ((TextUtils.isEmpty(rjf) || TextUtils.isEmpty(rjf) || Integer.parseInt(rjf) <= 0)) {
            ToastUtil.showToast(this, "请填写维修服务费");
            return false;
        }
        other_cost = et_qtfy.getText().toString().trim();
//        if (checkedRepairs.getDeviceTypeId().equals("3")) {
//            if (TextUtils.isEmpty(other_cost) || Integer.parseInt(other_cost) <= 0) {
//                ToastUtil.showLongToast(this, "请填写其它费用，并且大于0");
//                return false;
//            }
//        }
        hardwareCost = txtHardwareReplacement.getText().toString().trim();
        moneyDesc = et_money_desc.getText().toString().trim();
        if (!TextUtils.isEmpty(other_cost) && Double.parseDouble(other_cost) > 0) {
            if (TextUtils.isEmpty(moneyDesc)) {
                ToastUtil.showToast(this, "请填写其它费用说明");
                return false;
            }
        }

        if(tv_errorMsg.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(tv_errorMsg.getText())){
            ToastUtil.showToast(this, tv_errorMsg.getText()+"");
            return false;
        }
      /*  if (!TextUtils.isEmpty(hardwareCost) && Double.parseDouble(hardwareCost) > 0) {
            moneyDesc = et_money_desc.getText().toString().trim();
            if (TextUtils.isEmpty(moneyDesc)) {
                ToastUtil.showToast(this, "请填写硬件更换费用详细说明");
                return false;
            }
        }*/
        return true;
    }

    /**
     * 解决方案选择器初始化
     */
    private void initOptionPicker() {
        if (pvOptions == null && ((repairses != null && repairses.size() > 0) || (repairseList != null && repairseList.size() > 0 && sortSolutions != null && sortSolutions.size() > 0))) {
            pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    if (myOrder.getOrderType()!= null && myOrder.getOrderType().equals("3")) {
                        checkedRepairs = repairses.get(options1);
                        selectRepairses.clear();
                        selectRepairses.add(checkedRepairs);
//                        tv_solve_scheme.setText(checkedRepairs.getExampleName());
                        updateTypeList();
                    } else {
                        checkedRepairs = sortSolutions.get(options1).get(options2);
                        checkedRepairs.setDeviceTypeName(repairseList.get(options1).getDeviceTypeName());
                        selectRepairses.add(checkedRepairs);
                        updateTypeList();
                    }
                }
            })
                    .setTitleText("解决方案")
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
            if (myOrder.getOrderType()!= null && myOrder.getOrderType().equals("3")) {
                pvOptions.setPicker(repairses);//二级选择器
            } else if (repairseList != null && sortSolutions != null) {
                pvOptions.setPicker(repairseList, sortSolutions);
            }
        } else if (pvOptions == null) {
            getRepairses();
        }
    }

    public void updateTypeList() {
        if (selectRepairses.size() > 0) {
            layout_type_list.setVisibility(View.VISIBLE);
            layout_type_list.removeAllViews();
            String smf = "0";
            boolean isFixation = false;
            String rjf = "0";
            for (int i = 0; i < selectRepairses.size(); i++) {
                TypeItem reItem = new TypeItem(this);
                Solution solution = selectRepairses.get(i);
                reItem.initData(solution,QuoteAffirmActivity.this);
                if (i == selectRepairses.size()-1) {
                    reItem.setLineGone();
                }
                reItem.setListener(solution, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Solution m = (Solution) v.getTag();
                        selectRepairses.remove(m);
                        updateTypeList();
                    }
                });
                layout_type_list.addView(reItem);
                if (!TextUtils.isEmpty(solution.getVisitCost())
                        && Integer.parseInt(solution.getVisitCost()) > 0 && Integer.parseInt(solution.getVisitCost()) > Integer.parseInt(smf)) {
                    smf = solution.getVisitCost();
                }

                if (!TextUtils.isEmpty(solution.getMaintainCost())
                        && Integer.parseInt(solution.getMaintainCost()) > 0) {
                    if (solution.getIsFixation().equals("1")) {
                        isFixation = true;
                    }
                    rjf = Utils.jia(rjf, solution.getMaintainCost());
                }
            }

            if (!TextUtils.isEmpty(rjf) && Integer.parseInt(rjf) >= 0) {
                tv_rjf.setText(Utils.chu(rjf,"100"));
                tv_rjf.setEnabled(false);
                tv_rjf.setClickable(false);
               /* if (!isFixation) {
                    tv_rjf.setText(Utils.chu(rjf,"100"));
                    tv_rjf.setEnabled(false);
                    tv_rjf.setClickable(false);
                } else {
                    tv_rjf.setClickable(true);
                    tv_rjf.setEnabled(true);
                    tv_rjf.setText(Utils.chu(rjf,"100"));
                }*/
                rl_rjf.setVisibility(View.VISIBLE);
            } else {
                tv_rjf.setText("0");
                rl_rjf.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(smf)) {
                tv_smf.setText(Utils.chu(smf, "100"));
                rl_smf.setVisibility(View.VISIBLE);
            } else {
                tv_smf.setText("0");
                rl_smf.setVisibility(View.GONE);
            }

            jiaMoney();
        } else {
            tv_smf.setText("0");
            rl_smf.setVisibility(View.GONE);
            tv_rjf.setText("0");
            rl_rjf.setVisibility(View.GONE);
            layout_type_list.setVisibility(View.GONE);
        }
    }

    public void RepairMoneyChange(){
        tv_errorMsg.setVisibility(View.GONE);
        long repairMoneys = 0;
        for(int i = 0;i < layout_type_list.getChildCount(); i++)
        {
            LinearLayout myll = (LinearLayout) layout_type_list.getChildAt(i);

            EditText et_money = (EditText) myll.findViewById(R.id.et_money);
            long changeMaintainCost = Long.parseLong(et_money.getText().toString().trim());
            selectRepairses.get(i).setMaintainCost(String.valueOf(changeMaintainCost*100));
            repairMoneys += changeMaintainCost;
        }
        tv_rjf.setText(String.valueOf(repairMoneys));

        jiaMoney();
    }

    public void setErrorMessage(String msg){
        tv_errorMsg.setVisibility(View.VISIBLE);
        tv_errorMsg.setText(msg);
    }

    public void jiaMoney() {
        String money = "0";
        int count = 0;
        String smf = tv_smf.getText().toString().trim();
        if (!isSelect && !TextUtils.isEmpty(smf) && Integer.parseInt(smf) > 0) {
            count++;
            money = Utils.jia(money, smf);
            rl_smf.setVisibility(View.VISIBLE);
        } else {
            rl_smf.setVisibility(View.GONE);
        }
        String rjf = tv_rjf.getText().toString().trim();
        if (!TextUtils.isEmpty(rjf) && Integer.parseInt(rjf) > 0) {
            count++;
            money = Utils.jia(money, rjf);
            rl_rjf.setVisibility(View.VISIBLE);
        } else {
            rl_rjf.setVisibility(View.GONE);
        }
        String qt = et_qtfy.getText().toString().trim();
        if (!TextUtils.isEmpty(qt) && Double.parseDouble(qt) > 0) {
            count++;
            money = Utils.jia(money, qt);
        }

        String yj = txtHardwareReplacement.getText().toString().trim();
        if (!TextUtils.isEmpty(yj) && Double.parseDouble(yj) > 0) {
            count++;
            money = Utils.jia(money, yj);
        }

        String s = "总计 ( "+count+" ) : "+Utils.getMoney(money)+" 元";
        tv_total.setText(Utils.getSpannableStringBuilder(s.indexOf(":")+1, s.length()-1, getResources().getColor(R.color.red_3a), s, 15));
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
//                            Utils.deleteFile(path.getPath(), QuoteAffirmActivity.this);
                            if (urlStrs.size() > 0 && !TextUtils.isEmpty(urlStrs.get(0))){
                                uploadPhoto(new File(urlStrs.get(0)));
                            } else {
                                affirm();
                                pd.dismiss();
                            }
                            LLog.i("联网  图片地址" + url);
                        } else {
                            pd.dismiss();
                            ToastUtil.showLongToast(QuoteAffirmActivity.this, "图片上传失败");
                        }
                    }
                }, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == request && resultCode == RESULT_OK) {
            onBackPressed();
        } else if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GlobalValue.PHOTO_REQUESTCODE:
                    List<Uri> mSelected = Matisse.obtainResult(data);
                    if (mSelected != null && mSelected.size() > 0) {
                        urlStrs = adpter.getLists();
                        if (urlStrs != null && urlStrs.get(urlStrs.size()-1).equals("")) {
                            urlStrs.remove(urlStrs.size()-1);
                        }
                        for(int i=0; i<mSelected.size(); i++) {
                            Uri uri = mSelected.get(i);
                            urlStrs.add(PictureUtils.getPath(QuoteAffirmActivity.this, uri));
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
                        File file = new Compressor.Builder(QuoteAffirmActivity.this).setMaxHeight(1080).setMaxWidth(1920)
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
                        urlStrs = adpter.getLists();
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
                        ToastUtil.showToast(QuoteAffirmActivity.this, "选择图片失败");
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

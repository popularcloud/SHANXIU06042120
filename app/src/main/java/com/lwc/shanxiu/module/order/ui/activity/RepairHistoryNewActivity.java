package com.lwc.shanxiu.module.order.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.bean.DeviceAllMsgBean;
import com.lwc.shanxiu.module.common_adapter.FragmentsPagerAdapter;
import com.lwc.shanxiu.module.order.ui.fragment.DeviceMsgFragment;
import com.lwc.shanxiu.module.order.ui.fragment.ScrapFragment;
import com.lwc.shanxiu.utils.ACache;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.widget.CustomViewPager;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class RepairHistoryNewActivity extends BaseActivity {

    @BindView(R.id.rBtnDeviceMsg)
    RadioButton rBtnDeviceMsg;
    @BindView(R.id.rBtnReimbursement)
    RadioButton rBtnReimbursement;
    @BindView(R.id.cViewPager)
    CustomViewPager cViewPager;
    @BindView(R.id.viewLine1)
    View viewLine1;
    @BindView(R.id.viewLine3)
    View viewLine3;
    private HashMap fragmentHashMap = null;
    private HashMap rButtonHashMap = null;
    private DeviceMsgFragment deviceMsgFragment;
    private ScrapFragment scrapFragment;
    private String qrcodeIndex;
    private String dataResponse;
    private int pagePosition = 0;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_repair_histoty_new;
    }

    @Override
    protected void findViews() {
        setTitle("设备维修历史");
        showBack();
        addFragmentInList();
        addRadioButtonIdInList();
        bindViewPage(fragmentHashMap);
        cViewPager.setCurrentItem(0, false);
        
        qrcodeIndex = getIntent().getStringExtra("qrcodeIndex");
        dataResponse = ACache.get(this).getAsString("qrcodeIndex"+qrcodeIndex);
      /*  if (TextUtils.isEmpty(dataResponse)) {
            getScanCode();
        } else {
            initData();
        }*/
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {

    }

    private void addFragmentInList() {
        fragmentHashMap = new HashMap<>();
        deviceMsgFragment = new DeviceMsgFragment();
        scrapFragment = new ScrapFragment();
        fragmentHashMap.put(0, deviceMsgFragment);
        fragmentHashMap.put(1, scrapFragment);
    }

    private void addRadioButtonIdInList() {
        rButtonHashMap = new HashMap<>();
        rButtonHashMap.put(0, rBtnDeviceMsg);
        rButtonHashMap.put(1, rBtnReimbursement);
    }

    private void bindViewPage(HashMap<Integer, Fragment> fragmentList) {
        //是否滑动
        cViewPager.setPagingEnabled(true);
        cViewPager.setAdapter(new FragmentsPagerAdapter(getSupportFragmentManager(), fragmentList));
        cViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                cViewPager.setChecked(rButtonHashMap, position);
                pagePosition = position;
                showLineView(position + 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void showLineView(int num) {

        switch (num) {
            case 1:
                viewLine1.setVisibility(View.VISIBLE);
                viewLine3.setVisibility(View.INVISIBLE);
                break;
            case 2:
                viewLine3.setVisibility(View.VISIBLE);
                viewLine1.setVisibility(View.INVISIBLE);
                break;
        }
    }


    @OnClick({R.id.rBtnDeviceMsg, R.id.rBtnReimbursement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rBtnDeviceMsg:
                showLineView(1);
                cViewPager.setCurrentItem(0);
                break;
            case R.id.rBtnReimbursement:
                showLineView(2);
                cViewPager.setCurrentItem(1);
                break;
        }
    }

    private void getScanCode() {
        Map<String, String> map = new HashMap<>();
        map.put("qrcodeIndex", qrcodeIndex );
        HttpRequestUtils.httpRequest(this, "getScanCode", RequestValue.SCAN_CODE, map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                dataResponse = response;
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        initData(pagePosition);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showLongToast(RepairHistoryNewActivity.this, e.toString());
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initData(intent.getIntExtra("pagePosition",0));
    }

    public void initData(int position) {
        pagePosition = position;

        boolean needUpdate = (boolean) SharedPreferencesUtils.getParam(this,"needUpdate",false);
        if(needUpdate){
            SharedPreferencesUtils.setParam(this,"needUpdate",false);
            getScanCode();
            return;
        }

        if(TextUtils.isEmpty(dataResponse)){
            getScanCode();
            return;
        }
        try {
            String data = JsonUtil.getGsonValueByKey(dataResponse, "data");
            DeviceAllMsgBean deviceAllMsgBean = JsonUtil.parserGsonToObject(data, DeviceAllMsgBean.class);
            if(fragmentHashMap != null && fragmentHashMap.size() == 2){
                if(pagePosition == 0){
                    ((DeviceMsgFragment)fragmentHashMap.get(0)).loadData(deviceAllMsgBean);
                }else if(pagePosition == 1){
                    ((ScrapFragment)fragmentHashMap.get(1)).loadData(deviceAllMsgBean,qrcodeIndex);
                }
            }
        } catch (Exception e) {
            com.lwc.shanxiu.map.ToastUtil.show(this, "数据解析错误"+e.getMessage());
            Log.e("数据解析错误",e.getMessage());
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 8869 && resultCode == RESULT_OK) {
            String qrcode = data.getStringExtra("qrcodeIndex");
            if(!qrcodeIndex.contains(qrcode)){
                ToastUtil.showToast(RepairHistoryNewActivity.this,"二维码错误!");
                return;
            }

            ((ScrapFragment)fragmentHashMap.get(1)).submitDate(qrcodeIndex);
        }
    }
}

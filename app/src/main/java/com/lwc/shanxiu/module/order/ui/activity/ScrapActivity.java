package com.lwc.shanxiu.module.order.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.KeyboardUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.addapp.pickers.common.LineConfig;
import cn.addapp.pickers.picker.DatePicker;
import cn.addapp.pickers.picker.DateTimePicker;

public class ScrapActivity extends BaseActivity {

    @BindView(R.id.et_update_reason)
    EditText etUpdateReason;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.btnSubmit)
    TextView btnSubmit;
    private String relevanceId;
    private Calendar calendar;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_scrap;
    }

    @Override
    protected void findViews() {
        setTitle("报废");
        showBack();
        relevanceId = getIntent().getStringExtra("relevanceId");
        calendar = Calendar.getInstance();

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


    @OnClick({R.id.ll_time,R.id.btnSubmit})
    public void onBtnClick(View view){
        switch (view.getId()){
            case R.id.ll_time:
                KeyboardUtil.showInput(false, ScrapActivity.this);
                onYearMonthDayTimePicker();
                break;
            case R.id.btnSubmit:
                String reason = etUpdateReason.getText().toString().trim();
                String orderTime = tvTime.getText().toString().trim();
                if(TextUtils.isEmpty(reason)){
                    ToastUtil.showToast(this,"请填写报废原因");
                    return;
                }
                if(TextUtils.isEmpty(orderTime)){
                    ToastUtil.showToast(this,"请选择预约时间");
                    return;
                }
                submitDate(reason,orderTime);
                break;
        }
    }

    public void onYearMonthDayTimePicker() {
       /* DateTimePicker picker = new DateTimePicker(this, DateTimePicker.HOUR_24);
        picker.setActionButtonTop(true);
        picker.setDateRangeStart(2017, 1, 1);
        picker.setDateRangeEnd(2025, 11, 11);

        //当前年
        int year = calendar.get(Calendar.YEAR);
        //当前月
        int month = (calendar.get(Calendar.MONTH))+1;
        //当前月的第几天：即当前日
        int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
        //当前时：HOUR_OF_DAY-24小时制；HOUR-12小时制
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        //当前分
        int minute = calendar.get(Calendar.MINUTE);
        picker.setSelectedItem(year,month,day_of_month,hour,minute);
        picker.setTimeRangeStart(9, 0);
        picker.setTimeRangeEnd(20, 30);
        picker.setCanLinkage(false);
        picker.setTitleText("请选择");
        picker.setLabel("年","月","日","时","分");

//        picker.setStepMinute(5);
        picker.setWeightEnable(true);
        picker.setWheelModeEnable(true);
        LineConfig config = new LineConfig();
        config.setColor(Color.WHITE);//线颜色
        config.setAlpha(120);//线透明度
        config.setVisible(false);//线不显示 默认显示
        picker.setLineConfig(config);
        picker.setLabel(null,null,null,null,null);
        picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                tvTime.setText(year + "-" + month + "-" + day + " " + hour + ":" + minute);
            }
        });
        picker.show();*/


        final DatePicker picker = new DatePicker(this);

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
        picker.setLineColor(Color.WHITE);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                tvTime.setText(year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
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

    private void submitDate(String reason,String orderTime){
        Map<String, String> map = new HashMap<>();
        map.put("relevanceId", relevanceId);
        map.put("updateReason",reason);
        map.put("makeTime", orderTime);
        HttpRequestUtils.httpRequest(this, "scrapDeviceInfo", RequestValue.GET_SCAN_SCRAPDEVICEINFO, map, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                       ToastUtil.showToast(ScrapActivity.this,"提交成功!");
                        SharedPreferencesUtils.setParam(ScrapActivity.this,"needUpdate",true);
                        Bundle bundle = new Bundle();
                        bundle.putInt("pagePosition",1);
                        IntentUtil.gotoActivity(ScrapActivity.this,RepairHistoryNewActivity.class,bundle);
                        finish();
                        break;
                    default:
                        ToastUtil.showToast(ScrapActivity.this,common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showLongToast(ScrapActivity.this, e.toString());
            }
        });
    }
}

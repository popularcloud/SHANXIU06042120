package com.lwc.shanxiu.module.sign_in.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.module.sign_in.adapter.SignInHistoryAdapter;
import com.lwc.shanxiu.module.sign_in.bean.SignInRecordBean;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.widget.CircleImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import cn.addapp.pickers.picker.DatePicker;


public class SignInHistoryActivity extends BaseActivity{

    @BindView(R.id.rl_title)
    RelativeLayout rl_title;

    @BindView(R.id.rv_data)
    RecyclerView rv_data;
    @BindView(R.id.tv_no_data)
    TextView tv_no_data;
    @BindView(R.id.img_head)
    CircleImageView img_head;

    @BindView(R.id.tv_present_date)
    TextView tv_present_date;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_company)
    TextView tv_company;

    private Calendar calendar;

    private SignInHistoryAdapter mAdapter;

    //List<List<SignInRecordBean>> listBean = new ArrayList<>();

    private SimpleDateFormat dfSearch;

    private User user;

    private String[] selDateList;

    private String searchDate;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_sign_in_history;
    }

    @Override
    protected void findViews() {


        int isFromMian = getIntent().getIntExtra("isFromMian",0);

        if(isFromMian == 1){
            setRightText("考勤日历", "#1481ff", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentUtil.gotoActivity(SignInHistoryActivity.this, CheckWorkHistoryActivity.class);
                }
            });
        }



        showBack();
        setTitle("签到记录");

        dfSearch = new SimpleDateFormat("yyyy-MM");//设置日期格式
        searchDate=  dfSearch.format(new Date());
        calendar = Calendar.getInstance();

        //当前年
        int year = calendar.get(Calendar.YEAR);
        //当前月
        int month = (calendar.get(Calendar.MONTH))+1;
        //当前月的第几天：即当前日
        // int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);

        tv_present_date.setText(year +"-"+ (month > 9?month:"0"+month));

        user = SharedPreferencesUtils.getInstance(this).loadObjectData(User.class);
        tv_name.setText(user.getMaintenanceName());
        tv_company.setText(user.getParentCompanyName()); //这个才一定有
        ImageLoaderUtil.getInstance().displayFromNetD(this,user.getMaintenanceHeadImage(),img_head);
    }

    @Override
    protected void init() {
        initRecyclview();
    }

    private void initRecyclview() {

        rv_data.setLayoutManager(new LinearLayoutManager(SignInHistoryActivity.this));
        mAdapter = new SignInHistoryAdapter(this,null,R.layout.item_sign_in_history);
        rv_data.setAdapter(mAdapter);

        getSignHistory();
    }

    private void getSignHistory(){

        Map<String,String> params = new HashMap<>();
        params.put("type","1"); //外勤
        params.put("dateTimeType","1"); //按月查询
        params.put("dateTime",searchDate);
        HttpRequestUtils.httpRequest(this, "获取考勤记录", RequestValue.SIGNINMANAGER_GETSIGNINHISTORY, params, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                if("1".equals(common.getStatus())){
                    LinkedHashMap<String, List<SignInRecordBean>> returnBean = JsonUtil.parserGsonToSortMap(JsonUtil.getGsonValueByKey(response, "data"),new TypeToken<LinkedHashMap<String, List<SignInRecordBean>>>(){});
                    if(returnBean != null){
                        Collection listBean =  returnBean.values();

                        List<List<SignInRecordBean>> lists = new ArrayList<>(listBean);

                        if(lists != null && lists.size() > 0){
                            rv_data.setVisibility(View.VISIBLE);
                            tv_no_data.setVisibility(View.GONE);
                            mAdapter.clear();
                            mAdapter.replaceAll(lists);
                        }else{
                            rv_data.setVisibility(View.GONE);
                            tv_no_data.setVisibility(View.VISIBLE);
                        }
                    }else {
                        rv_data.setVisibility(View.GONE);
                        tv_no_data.setVisibility(View.VISIBLE);
                    }

                    //ToastUtil.showToast(CheckWorkMainActivity.this,"获取考勤记录成功");
                    Log.d("联网成功",response);
                }else{
                    ToastUtil.showToast(SignInHistoryActivity.this,"获取考勤记录失败"+common.getInfo());
                    mAdapter.clear();
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
                ToastUtil.showToast(SignInHistoryActivity.this,"获取考勤记录失败"+msg);
            }
        });
    }


    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {
/*
        spinner_simple.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchDate = selDateList[position];
                getSignHistory();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        tv_present_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String presentDateStr = tv_present_date.getText().toString().trim();
                if(!TextUtils.isEmpty(presentDateStr)){
                    String [] presentDateSplit = presentDateStr.split("-");
                    onYearMonthDayTimePicker(Integer.parseInt(presentDateSplit[0]),Integer.parseInt(presentDateSplit[1]));
                }else{
                    onYearMonthDayTimePicker(null,null);
                }
            }
        });
    }

    public void onYearMonthDayTimePicker(Integer selYear,Integer selMonth) {
        final cn.addapp.pickers.picker.DatePicker picker = new cn.addapp.pickers.picker.DatePicker(this, DatePicker.YEAR_MONTH);


        if(selYear == null){
            //当前年
            selYear = calendar.get(java.util.Calendar.YEAR);
            //当前月
            selMonth = (calendar.get(java.util.Calendar.MONTH))+1;
        }

      //  tv_present_date.setText(year +"-"+ (month > 9?month:"0"+month));

  /*      picker.setCanLoop(true);
        picker.setWheelModeEnable(true);
        picker.setTopPadding(15);
        picker.setRangeStart(2016, 8, 29);
        picker.setRangeEnd(2111, 1, 11);
        picker.setSelectedItem(year,month);
        picker.setWeightEnable(true);
        picker.setSelectedTextColor(Color.parseColor("#1481ff"));
        picker.setLineColor(Color.WHITE);*/

        picker.setCanLoop(true);
        picker.setWheelModeEnable(true);
        picker.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        picker.setRangeStart(2016, 10, 14);
        picker.setRangeEnd(2111, 11, 11);
        picker.setSelectedItem(selYear,selMonth);
        picker.setWeightEnable(true);
        picker.setLabel("","","");
        picker.setSelectedTextColor(Color.parseColor("#1481ff"));
        picker.setLineColor(Color.WHITE);

        picker.setOnDatePickListener(new cn.addapp.pickers.picker.DatePicker.OnYearMonthPickListener() {
            @Override
            public void onDatePicked(String year, String month) {

                searchDate = year +"-"+ (month.length()==2?month:"0"+month);
                tv_present_date.setText(searchDate);
                getSignHistory();
            }
        });
 /*       picker.setOnWheelListener(new cn.addapp.pickers.picker.DatePicker.OnWheelListener() {
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
        });*/
        picker.show();
    }
}

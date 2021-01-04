package com.lwc.shanxiu.module.sign_in.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.module.sign_in.adapter.SignInHistoryAdapter;
import com.lwc.shanxiu.module.sign_in.adapter.SignInHistoryMainAdapter;
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
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.addapp.pickers.picker.DatePicker;

/**
 * 工作台
 */
public class CheckWorkHistoryActivity extends BaseActivity implements CalendarView.OnYearViewChangeListener, CalendarView.OnWeekChangeListener, CalendarView.OnCalendarLongClickListener, CalendarView.OnMonthChangeListener, CalendarView.OnCalendarSelectListener, CalendarView.OnYearChangeListener, CalendarView.OnCalendarInterceptListener, CalendarView.OnViewChangeListener{

    @BindView(R.id.rl_title)
    RelativeLayout rl_title;

    @BindView(R.id.calendarLayout)
    CalendarLayout calendarLayout;
    @BindView(R.id.calendarView)
    CalendarView mCalendarView;
    @BindView(R.id.img_head)
    CircleImageView img_head;

    @BindView(R.id.tv_present_date)
    TextView tv_present_date;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_company)
    TextView tv_company;

    @BindView(R.id.tv_msg)
    TextView tv_msg;
    @BindView(R.id.rv_data)
    RecyclerView rv_data;

    private int searchYear;
    private int searchMonth;

    private SimpleDateFormat dfSearch;

    private User user;

    private SignInHistoryMainAdapter mAdapter;
    private String searchDate;
    private int searchDay;

    private java.util.Calendar calendar;

    private HashMap<String, List<SignInRecordBean>> allSignHistoryMonth;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_check_work_history;
    }

    @Override
    protected void findViews() {

        int isFromMian = getIntent().getIntExtra("isFromMian",0);

        if(isFromMian == 1){
            setRightText("签到记录", "#1481ff", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentUtil.gotoActivity(CheckWorkHistoryActivity.this, SignInHistoryActivity.class);
                }
            });
        }


        showBack();
        setTitle("考勤日历");

        calendar = java.util.Calendar.getInstance();

        dfSearch = new SimpleDateFormat("yyyy-MM");//设置日期格式

        tv_present_date.setText(dfSearch.format(new Date()));

        user = SharedPreferencesUtils.getInstance(this).loadObjectData(User.class);
        tv_name.setText(user.getMaintenanceName());
        tv_company.setText(user.getParentCompanyName()); //这个才一定有
        ImageLoaderUtil.getInstance().displayFromNetD(this,user.getMaintenanceHeadImage(),img_head);

        searchYear = mCalendarView.getCurYear();
        searchMonth = mCalendarView.getCurMonth();
        searchDay = mCalendarView.getCurDay();

        tv_present_date.setText(searchYear+"-"+(searchMonth < 10?"0"+searchMonth:String.valueOf(searchMonth)));

        initCalendar();
        initRecyclview();
    }

    @Override
    protected void init() {

    }

    private void initCalendar() {
        mCalendarView.setOnYearChangeListener(this);
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnMonthChangeListener(this);
        mCalendarView.setOnCalendarLongClickListener(this, true);
        mCalendarView.setOnWeekChangeListener(this);
        mCalendarView.setOnYearViewChangeListener(this);

        //设置日期拦截事件，仅适用单选模式，当前无效
        mCalendarView.setOnCalendarInterceptListener(this);
        mCalendarView.setOnViewChangeListener(this);
        mCalendarView.setClickable(false);
    }

    private void initRecyclview() {
        rv_data.setLayoutManager(new LinearLayoutManager(CheckWorkHistoryActivity.this));
        mAdapter = new SignInHistoryMainAdapter(this,null,R.layout.item_sign_in_main_history);
        rv_data.setAdapter(mAdapter);
        getSignHistory();
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {

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

    private void getSignHistory(){
        searchDate = tv_present_date.getText().toString().trim();
        Map<String,String> params = new HashMap<>();
        params.put("type","0,1"); //外勤
        params.put("dateTimeType","1"); //按月查询
        params.put("dateTime",searchDate);
        HttpRequestUtils.httpRequest(this, "获取考勤记录", RequestValue.SIGNINMANAGER_GETSIGNINHISTORY, params, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                if("1".equals(common.getStatus())){

                    allSignHistoryMonth = JsonUtil.parserGsonToMap(JsonUtil.getGsonValueByKey(response, "data"),new TypeToken<HashMap<String, List<SignInRecordBean>>>(){});
                    if(allSignHistoryMonth != null){
                        for(String key : allSignHistoryMonth.keySet()){
                            String [] keys = key.split("-");
                            List<SignInRecordBean> signInRecordBeans = allSignHistoryMonth.get(key);
                            if(signInRecordBeans != null){
                                Calendar calendar = new Calendar();
                                calendar.setYear(Integer.parseInt(keys[0]));
                                calendar.setMonth(Integer.parseInt(keys[1]));
                                calendar.setDay(Integer.parseInt(keys[2]));
                                calendar.setScheme("正常");
                                for(int i = 0;i < signInRecordBeans.size();i ++){
                                    if(signInRecordBeans.get(i).getAbnormal() != 1){
                                        calendar.setScheme("异常");
                                    }
                                }
                                mCalendarView.addSchemeDate(calendar);
                            }

                        }
                        List<SignInRecordBean> presentSignInRecordBeans = allSignHistoryMonth.get(searchDate+(searchDay < 10?"-0"+searchDay:String.valueOf("-"+searchDay)));
                        if(presentSignInRecordBeans == null || presentSignInRecordBeans.size() < 1){
                            tv_msg.setVisibility(View.VISIBLE);
                            rv_data.setVisibility(View.GONE);
                        }else{
                            tv_msg.setVisibility(View.GONE);
                            rv_data.setVisibility(View.VISIBLE);
                            mAdapter.replaceAll(presentSignInRecordBeans);
                        }
                    }

                    //ToastUtil.showToast(CheckWorkMainActivity.this,"获取考勤记录成功");
                    Log.d("联网成功",response);
                }else{
                    ToastUtil.showToast(CheckWorkHistoryActivity.this,"获取考勤记录失败"+common.getInfo());
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
                ToastUtil.showToast(CheckWorkHistoryActivity.this,"获取考勤记录失败"+msg);
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

        //当前月的第几天：即当前日
        // int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);

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

            /*    searchMonth = month;
                searchYear = year;
                tv_present_date.setText(year+"-"+(searchMonth < 10?"0"+searchMonth:String.valueOf(searchMonth)));

                getSignHistory();*/

                mCalendarView.scrollToCalendar(Integer.parseInt(year),Integer.parseInt(month),1,true);
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


    //####################日历回调

    @Override
    public boolean onCalendarIntercept(Calendar calendar) {
        return false;
    }

    @Override
    public void onCalendarInterceptClick(Calendar calendar, boolean isClick) {

    }

    @Override
    public void onCalendarLongClickOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarLongClick(Calendar calendar) {

    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {

        searchYear = calendar.getYear();
       searchMonth = calendar.getMonth();
        searchDay = calendar.getDay();

        String key = searchDate+(searchDay < 10?"-0"+searchDay:String.valueOf("-"+searchDay));

        if(allSignHistoryMonth != null && allSignHistoryMonth.containsKey(key)){
            List<SignInRecordBean> signInRecordBeans = allSignHistoryMonth.get(key);
            if(signInRecordBeans == null || signInRecordBeans.size() < 1){
                tv_msg.setVisibility(View.VISIBLE);
                rv_data.setVisibility(View.GONE);
            }else{
                tv_msg.setVisibility(View.GONE);
                rv_data.setVisibility(View.VISIBLE);
                mAdapter.replaceAll(signInRecordBeans);
            }
        }else{
            tv_msg.setVisibility(View.VISIBLE);
            rv_data.setVisibility(View.GONE);
        }


    }

    @Override
    public void onMonthChange(int year, int month) {
        searchMonth = month;
        searchYear = year;
        tv_present_date.setText(year+"-"+(searchMonth < 10?"0"+searchMonth:String.valueOf(searchMonth)));

        getSignHistory();
    }

    @Override
    public void onViewChange(boolean isMonthView) {

    }

    @Override
    public void onWeekChange(List<Calendar> weekCalendars) {

    }

    @Override
    public void onYearChange(int year) {

    }

    @Override
    public void onYearViewChange(boolean isClose) {

    }
}

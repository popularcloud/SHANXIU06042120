package com.lwc.shanxiu.module.sign_in.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.bean.Location;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.bean.User;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class CheckWorkClockInActivity extends BaseActivity {

    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_status)
    TextView tv_status;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_company)
    TextView tv_company;
    @BindView(R.id.img_head)
    CircleImageView img_head;
    @BindView(R.id.ll_sign_in)
    LinearLayout ll_sign_in;

    @BindView(R.id.rv_data)
    RecyclerView rv_data;

    private SimpleDateFormat dfSearch;
    private SimpleDateFormat df;

    private boolean isSignIn = false;
    
    private Handler mHandle;
    private boolean IsCancel;
    private SignInHistoryMainAdapter mAdapter;

    List<SignInRecordBean> mBeanList = new ArrayList<>();

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_check_out_clock_in;
    }

    @Override
    protected void findViews() {


    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        IsCancel = true;
    }

    @Override
    public void init() {

        setTitle("考勤打卡");
        showBack();
        setRightText("考勤日历","#1481ff", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("isFromMian",1);
                IntentUtil.gotoActivity(CheckWorkClockInActivity.this, CheckWorkHistoryActivity.class,bundle);
            }
        });

        User user = SharedPreferencesUtils.getInstance(this).loadObjectData(User.class);
        tv_name.setText(user.getMaintenanceName());
        tv_company.setText(user.getParentCompanyName()); //这个才一定有
        ImageLoaderUtil.getInstance().displayFromNetD(this,user.getMaintenanceHeadImage(),img_head);

        df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
        dfSearch = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式

        rv_data.setLayoutManager(new LinearLayoutManager(CheckWorkClockInActivity.this));
        mAdapter = new SignInHistoryMainAdapter(this,mBeanList,R.layout.item_sign_in_main_history);
        rv_data.setAdapter(mAdapter);

        getSignHistory();
    }

    @Override
    protected void initGetData() {
    }

    @Override
    protected void widgetListener() {
    }

    @OnClick(R.id.ll_sign_in)
    public void onBtnClick(View view){
        switch (view.getId()){
            case R.id.ll_sign_in:
                if(!isSignIn){
                    isSignIn = true;
                    signIn();
                }
                break;
        }
    }

    private void startTimer(){
        IsCancel = false;
        mHandle = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 999:
                        updateDataTime();
                        removeMessages(999);
                        if(!IsCancel){
                            sendEmptyMessageDelayed(999, 1000);
                        }
                        break;
                }
            }
        };

        mHandle.sendEmptyMessage(999);
    }

    private void updateDataTime(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String searchDate = df.format(new Date());// new Date()为获取当前系统时间
                tv_time.setText(searchDate);
            }
        });
    }

    private void signIn(){

        Location location = SharedPreferencesUtils.getInstance(this).loadObjectData(Location.class);
        Map<String,String> params = new HashMap<>();
        if(location == null || location.getLatitude() == 0){
            ToastUtil.showToast(this,"定位获取中，请稍后重试!");
            return;
        }

        params.put("latitude",String.valueOf(location.getLatitude()));
        params.put("longitude",String.valueOf(location.getLongitude()));

        String statusTxt = tv_status.getText().toString().trim();
        if("上班打卡".equals(statusTxt)){
            params.put("status","0");
        }else{
            params.put("status","1");
        }

        HttpRequestUtils.httpRequest(this, "考勤打卡", RequestValue.SIGNINMANAGER_CLOCKIN, params, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                if("1".equals(common.getStatus())){
                    ToastUtil.showToast(CheckWorkClockInActivity.this,"打卡成功!");
                    getSignHistory();
                    Log.d("联网成功",response);
                }else{
                    ToastUtil.showToast(CheckWorkClockInActivity.this,"打卡失败"+common.getInfo());
                }

                isSignIn = false;
            }

            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showToast(CheckWorkClockInActivity.this,"打卡失败"+msg);
                isSignIn = false;
            }
        });
    }

    private void getSignHistory(){

        String searchDate = dfSearch.format(new Date());

        Map<String,String> params = new HashMap<>();
        params.put("type","0"); //内勤
        params.put("dateTimeType","0"); //按天查询
        params.put("dateTime",searchDate);
        HttpRequestUtils.httpRequest(this, "获取考勤记录", RequestValue.SIGNINMANAGER_GETSIGNINHISTORY, params, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                if("1".equals(common.getStatus())){

                    Map<String, List<SignInRecordBean>> returnBean = JsonUtil.parserGsonToMap(JsonUtil.getGsonValueByKey(response, "data"),new TypeToken<HashMap<String, List<SignInRecordBean>>>(){});

                    if(returnBean != null){
                        List<SignInRecordBean> signInRecordBeans = returnBean.get(dfSearch.format(new Date()));

                        mAdapter.replaceAll(signInRecordBeans);


                        //判断当前签到状态
                        int signInCount =signInRecordBeans==null?0:signInRecordBeans.size();
                     /*   for(int i = 0;i < signInRecordBeans.size();i++){
                            if(signInRecordBeans.get(i).getType() == 0){
                                signInCount = signInCount + 1;
                            }
                        }*/
                        if(signInCount % 2 == 0){
                            tv_status.setText("上班打卡");
                            ll_sign_in.setBackgroundResource(R.drawable.ic_yuan_blue);
                        }else{
                            tv_status.setText("下班打卡");
                            ll_sign_in.setBackgroundResource(R.drawable.ic_yuan_blue);
                        }

                    }

                    //ToastUtil.showToast(CheckWorkMainActivity.this,"获取考勤记录成功");
                    Log.d("联网成功",response);
                }else{
                    ToastUtil.showToast(CheckWorkClockInActivity.this,"获取考勤记录失败"+common.getInfo());
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
                ToastUtil.showToast(CheckWorkClockInActivity.this,"获取考勤记录失败"+msg);
            }
        });
    }
}

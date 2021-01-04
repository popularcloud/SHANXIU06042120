package com.lwc.shanxiu.module.sign_in.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.gyf.immersionbar.ImmersionBar;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.bean.Location;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.module.sign_in.adapter.SignInHistoryMainAdapter;
import com.lwc.shanxiu.module.sign_in.bean.SignInCountBean;
import com.lwc.shanxiu.module.sign_in.bean.SignInRecordBean;
import com.lwc.shanxiu.utils.DisplayUtil;
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

/**
 * 工作台
 */
public class CheckWorkMainActivity extends BaseActivity {

    @BindView(R.id.rl_title)
    RelativeLayout rl_title;

    @BindView(R.id.rv_data)
    RecyclerView rv_data;

    @BindView(R.id.tv_work_day)
    TextView tv_work_day;
    @BindView(R.id.tv_rest_day)
    TextView tv_rest_day;
    @BindView(R.id.tv_out_day)
    TextView tv_out_day;
    @BindView(R.id.tv_late_day)
    TextView tv_late_day;
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
    private SimpleDateFormat df;
    private SimpleDateFormat dfSearch;
    private Handler mHandle;


    private boolean IsCancel;
    private boolean isSignIn = false;
    private SignInHistoryMainAdapter mAdapter;

    List<SignInRecordBean> mBeanList = new ArrayList<>();


    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_check_work;
    }

    @Override
    protected void findViews() {
        imgRight.setVisibility(View.GONE);

        showBack();
        setTitle("工作台");
        df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
        dfSearch = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式

        rv_data.setLayoutManager(new LinearLayoutManager(CheckWorkMainActivity.this));
        mAdapter = new SignInHistoryMainAdapter(this,mBeanList,R.layout.item_sign_in_main_history);
        rv_data.setAdapter(mAdapter);
    }

    @Override
    protected void init() {

        int height = DisplayUtil.getStatusBarHeight(this);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rl_title.getLayoutParams();
        layoutParams.setMargins(0,height,0,0);
        rl_title.setLayoutParams(layoutParams);

        ImmersionBar.with(CheckWorkMainActivity.this)
                .statusBarColor(R.color.transparent)
                .statusBarDarkFont(false).init();

        User user = SharedPreferencesUtils.getInstance(this).loadObjectData(User.class);
        tv_name.setText(user.getMaintenanceName());
        tv_company.setText(user.getParentCompanyName()); //这个才一定有
        ImageLoaderUtil.getInstance().displayFromNetD(this,user.getMaintenanceHeadImage(),img_head);
    }

    @Override
    protected void initGetData() {

    }

    private void getSignInAccount(){
        HttpRequestUtils.httpRequest(this, "获取考勤统计", RequestValue.SIGNINMANAGER_GETPRESENTSIGNINSTATISTICS, null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                if("1".equals(common.getStatus())){
                    // ToastUtil.showToast(CheckWorkMainActivity.this,"获取考勤统计成功");

                    SignInCountBean signInCountBean = JsonUtil.parserGsonToObject(JsonUtil.getGsonValueByKey(response, "data"), SignInCountBean.class);
                    tv_work_day.setText(signInCountBean.getWorkDayCount());
                    tv_rest_day.setText(signInCountBean.getRestDayCount());
                    tv_out_day.setText(signInCountBean.getOutDayCount());
                    tv_late_day.setText(signInCountBean.getLateDayCount());

                    getSignHistory();
                }else{
                    ToastUtil.showToast(CheckWorkMainActivity.this,"获取考勤统计失败"+common.getInfo());
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
                ToastUtil.showToast(CheckWorkMainActivity.this,"获取考勤统计失败"+msg);
            }
        });
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

    @Override
    protected void widgetListener() {

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
                    ToastUtil.showToast(CheckWorkMainActivity.this,"获取考勤记录失败"+common.getInfo());
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
                ToastUtil.showToast(CheckWorkMainActivity.this,"获取考勤记录失败"+msg);
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
                    ToastUtil.showToast(CheckWorkMainActivity.this,"打卡成功!");
                    getSignInAccount();
                    Log.d("联网成功",response);
                }else{
                    ToastUtil.showToast(CheckWorkMainActivity.this,"打卡失败"+common.getInfo());
                }
                isSignIn = false;
            }

            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showToast(CheckWorkMainActivity.this,"打卡失败"+msg);
                isSignIn = false;
            }
        });
    }


    @OnClick({R.id.tv_kqdk,R.id.tv_cqrl,R.id.tv_wqqd,R.id.tv_qdjl,R.id.ll_sign_in})
    public void onBtnClick(View view){
        switch (view.getId()){
            case R.id.tv_kqdk:
                IntentUtil.gotoActivity(CheckWorkMainActivity.this, CheckWorkClockInActivity.class);
                break;
            case R.id.tv_cqrl:
                Bundle bundle01 = new Bundle();
                bundle01.putInt("isFromMian",1);
                IntentUtil.gotoActivity(CheckWorkMainActivity.this, CheckWorkHistoryActivity.class,bundle01);
                break;
            case R.id.tv_wqqd:
                IntentUtil.gotoActivity(CheckWorkMainActivity.this, SignInOutSideActivity.class);
                break;
            case R.id.tv_qdjl:
                Bundle bundle02 = new Bundle();
                bundle02.putInt("isFromMian",1);
                IntentUtil.gotoActivity(CheckWorkMainActivity.this, SignInHistoryActivity.class,bundle02);
                break;
            case R.id.ll_sign_in:
                if(!isSignIn){
                    isSignIn = true;
                    signIn();
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();

        //获取打卡统计
        getSignInAccount();
    }

    @Override
    protected void onPause() {
        super.onPause();
        IsCancel = true;
    }
}

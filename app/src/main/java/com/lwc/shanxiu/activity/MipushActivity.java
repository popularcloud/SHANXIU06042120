//package com.lwc.shanxiu.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//
//import com.lwc.shanxiu.R;
//import com.lwc.shanxiu.utils.IntentUtil;
//import com.umeng.message.UmengNotifyClickActivity;
//import com.umeng.socialize.utils.Log;
//
//import org.android.agoo.common.AgooConstants;
//
//public class MipushActivity extends UmengNotifyClickActivity {
//    private static String TAG = MipushActivity.class.getName();
//    @Override
//    protected void onCreate(Bundle bundle) {
//        super.onCreate(bundle);
//        setContentView(R.layout.loading_dialog);
//        IntentUtil.gotoActivityAndFinish(this, LoadingActivity.class);
//    }
//    @Override
//    public void onMessage(Intent intent) {
//        super.onMessage(intent);  //此方法必须调用，否则无法统计打开数
//        String body = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
//        Log.i(TAG, body);
//    }
//}
package com.lwc.shanxiu.activity;

import android.os.Bundle;

import com.gyf.immersionbar.ImmersionBar;
import com.lwc.shanxiu.R;


public class KnowledgeBaseActivity extends BaseActivity {


    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_my_knowledge;
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void init() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.blue_35)
                .statusBarDarkFont(true).init();
    }


    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {

    }
}

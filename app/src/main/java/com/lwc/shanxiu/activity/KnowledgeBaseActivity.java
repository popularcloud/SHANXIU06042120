package com.lwc.shanxiu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.gyf.immersionbar.ImmersionBar;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.configs.ServerConfig;
import com.lwc.shanxiu.fragment.KnowledgeBaseFragment;


public class KnowledgeBaseActivity extends BaseActivity {

    KnowledgeBaseFragment knowledgeBaseFragment;



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

        knowledgeBaseFragment = new KnowledgeBaseFragment();
        getSupportFragmentManager()    //
                .beginTransaction()
                .add(R.id.fragment_container,knowledgeBaseFragment)   // 此处的R.id.fragment_container是要盛放fragment的父容器
                .commit();
    }


    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ServerConfig.REQUESTCODE_KNOWLEDGESEARCH){
                if(data != null){
                    String searchData = data.getStringExtra("searchData");
                    if(!TextUtils.isEmpty(searchData)){
                        knowledgeBaseFragment.onSearched(searchData);
                    }
                }

            }
        }
}

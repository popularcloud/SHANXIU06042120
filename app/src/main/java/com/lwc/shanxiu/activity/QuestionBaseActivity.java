package com.lwc.shanxiu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.gyf.immersionbar.ImmersionBar;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.configs.ServerConfig;
import com.lwc.shanxiu.fragment.KnowledgeBaseFragment;
import com.lwc.shanxiu.fragment.MyRequestQuestionFragment;
import com.lwc.shanxiu.module.question.ui.activity.AnswerQuestionActivity;
import com.lwc.shanxiu.module.question.ui.adapter.RequestQuestionAdapter;


public class QuestionBaseActivity extends BaseActivity {


    MyRequestQuestionFragment requestQuestionFragment;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_question_base;
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
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true).init();


        requestQuestionFragment = new MyRequestQuestionFragment();
        getSupportFragmentManager()    //
                .beginTransaction()
                .add(R.id.fragment_container,requestQuestionFragment)   // 此处的R.id.fragment_container是要盛放fragment的父容器
                .commit();
    }


    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ServerConfig.REQUESTCODE_QUESTION){
                if(data != null){
                    String searchData = data.getStringExtra("searchData");
                    if(!TextUtils.isEmpty(searchData)){
                        requestQuestionFragment.onSearched(searchData);
                    }
            }
        }
    }
}

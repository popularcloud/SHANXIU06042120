package com.lwc.shanxiu.module.question.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Dimension;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.configs.ServerConfig;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.message.bean.SearchKeyWordBean;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.widget.TagsLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class QuestionSearchActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tl_tags)
    TagsLayout tl_tags;
    @BindView(R.id.ll_search)
    LinearLayout ll_search;
    @BindView(R.id.iv_delete)
    ImageView iv_delete;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_knowledge_search;
    }

    @Override
    protected void findViews() {
        img_back.setVisibility(View.GONE);
        tv_search.setText("取消");
        tv_search.setTextColor(Color.parseColor("#1481ff"));
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判断是否是“搜索”键*/
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    String key = et_search.getText().toString().trim();
                   /* if(TextUtils.isEmpty(key)){
                        ToastUtil.showToast(QuestionSearchActivity.this,"请输入您想要搜索的关键字");
                        return true;
                    }*/

                    //  这里记得一定要将键盘隐藏了
                    InputMethodManager imm = (InputMethodManager) v.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }

                    Intent resultData = new Intent();
                    resultData.putExtra("searchData",key);
                    setResult(ServerConfig.REQUESTCODE_KNOWLEDGESEARCH,resultData);
                    finish();
                    return true;
                }else{
                    //ToastUtil.showToast(getActivity(),"其他"+actionId);
                }
                return false;
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(s)){
                    iv_delete.setVisibility(View.GONE);
                }else{
                    iv_delete.setVisibility(View.VISIBLE);
                    iv_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            et_search.setText("");
                        }
                    });
                }
            }
        });

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll_search.getLayoutParams();
        layoutParams.setMargins(30, 0, 30, 0);
        ll_search.setLayoutParams(layoutParams);
        onGetKeyWord();
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

    /**
     * 获取搜索关键字
     */
    private void onGetKeyWord(){
        HttpRequestUtils.httpRequest(this, "获取搜索关键词", RequestValue.GET_QUESION_KNOWLEDGEKEYWORDRANK, null, "GET",  new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        List<SearchKeyWordBean> searchKeyWordBeenList = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"),new TypeToken<ArrayList<SearchKeyWordBean>>(){});
                        if(searchKeyWordBeenList != null){
                            tl_tags.setVisibility(View.VISIBLE);
                            tl_tags.removeAllViews();
                            for(int i = 0; i < searchKeyWordBeenList.size();i++){
                                SearchKeyWordBean searchKeyWordBean = searchKeyWordBeenList.get(i);
                                final TextView textView = new TextView(QuestionSearchActivity.this);
                                textView.setText(searchKeyWordBean.getSelectDetail());
                                textView.setTextColor(Color.parseColor("#999999"));
                                textView.setTextSize(Dimension.SP,12);
                                textView.setGravity(Gravity.CENTER);
                                textView.setPadding(25,15,25,15);
                                textView.setBackgroundResource(R.drawable.round_square_gray_big);
                              /*  int index = searchKeyWordBean.getKeyword_name().indexOf("：");
                                String tagStr = searchKeyWordBean.getKeyword_name().substring(index+1);*/
                                //textView.setText(searchKeyWordBean.getKeyword_name());
                                textView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String key = ((TextView)view).getText().toString();
                                        et_search.setText(key);
                                        Intent resultData = new Intent();
                                        resultData.putExtra("searchData",key);
                                        setResult(ServerConfig.REQUESTCODE_KNOWLEDGESEARCH,resultData);
                                        finish();
                                    }
                                });
                                tl_tags.addView(textView);
                            }

                        }else{
                            tl_tags.setVisibility(View.GONE);
                        }
                        break;
                    default:
                        ToastUtil.showToast(QuestionSearchActivity.this, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
            }
        });
    }
}

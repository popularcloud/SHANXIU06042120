package com.lwc.shanxiu.module.question.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.activity.MainActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.question.bean.QuestionIndexBean;
import com.lwc.shanxiu.module.question.ui.adapter.RequestAdapter;
import com.lwc.shanxiu.utils.BGARefreshLayoutUtils;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 我的提问
 */
public class MyRequestActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;
    @BindView(R.id.iv_no_data)
    ImageView iv_no_data;
    @BindView(R.id.tv_request_question)
    TextView tv_request_question;

    List<QuestionIndexBean> beanList = new ArrayList<>();
    private RequestAdapter adapter;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_my_request;
    }

    @Override
    protected void findViews() {
        showBack();
        setTitle("我的提问");
    }

    @Override
    protected void init() {

        BGARefreshLayoutUtils.initRefreshLayout(this, mBGARefreshLayout);

        bindRecycleView();

        recyclerView.setAdapter(adapter);

        //刷新控件监听器
        mBGARefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                page = 1;
                loadData();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                page++;
                loadData();
                return true;
            }
        });

        mBGARefreshLayout.beginRefreshing();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.gotoActivity(MyRequestActivity.this, MainActivity.class);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Boolean isRefresh = (Boolean) SharedPreferencesUtils.getParam(this,"isRefresh",false);
        if(isRefresh){
            mBGARefreshLayout.beginRefreshing();
        }else{
            updateReadAcount();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(mBGARefreshLayout != null){
            mBGARefreshLayout.beginRefreshing();
        }
    }

    /**
     * 更新浏览次数
     */
    private void updateReadAcount(){
        int position = (int) SharedPreferencesUtils.getParam(this,"itemPosition",-1);
        int viewCount = (int) SharedPreferencesUtils.getParam(this,"addViewCount",0);
        if(adapter != null && position != -1 && viewCount != 0 && beanList != null && adapter.getData().size() > position){
            adapter.getData().get(position).setBrowseNum(viewCount);
            adapter.notifyItemChanged(position);
        }
        SharedPreferencesUtils.setParam(this,"itemPosition",-1);
        SharedPreferencesUtils.setParam(this,"addViewCount",0);
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {

    }


    private void bindRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RequestAdapter(this, beanList, R.layout.item_request_question_1);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("quesionId", adapter.getItem(position).getQuesionId());
                IntentUtil.gotoActivity(MyRequestActivity.this, QuestionDetailActivity.class, bundle);
                //记录点击了item
                SharedPreferencesUtils.setParam(MyRequestActivity.this,"itemPosition",position);
            }
        });
    }


    private void loadData(){
        Map<String, String> map = new HashMap<>();
        map.put("curPage", ""+page);
        HttpRequestUtils.httpRequest(MyRequestActivity.this, "我的提问", RequestValue.QUESION_MAINTENANCEQUESION, map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        List<QuestionIndexBean> beanList = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"),new TypeToken<ArrayList<QuestionIndexBean>>(){});
                        loadDataToAdapter(beanList);
                        break;
                    default:
                        ToastUtil.showToast(MyRequestActivity.this, common.getInfo());
                        break;
                }
                BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);
            }

            @Override
            public void returnException(Exception e, String msg) {
                BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);
            }
        });
    }

    private void loadDataToAdapter(List<QuestionIndexBean> datas){
        if(page == 1){
            beanList = datas;
            adapter.replaceAll(datas);
            BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);

            if(datas == null || datas.size() < 1){
                //ToastUtil.showToast(KnowledgeBaseActivity.this,"暂无数据");
                iv_no_data.setVisibility(View.VISIBLE);
                mBGARefreshLayout.setVisibility(View.GONE);
            }else{
                iv_no_data.setVisibility(View.GONE);
                mBGARefreshLayout.setVisibility(View.VISIBLE);
            }
        }else{
            beanList.addAll(datas);
            adapter.addAll(datas);
            BGARefreshLayoutUtils.endLoadingMore(mBGARefreshLayout);

            if(datas == null || datas.size() < 1){
                ToastUtil.showToast(MyRequestActivity.this,"没有更多数据了");
            }
        }
    }

    @OnClick(R.id.tv_request_question)
    public void onBtnClick(View view){
        switch (view.getId()){
            case R.id.tv_request_question:
                Bundle bundle = new Bundle();
                bundle.putString("quesionId","");
                IntentUtil.gotoActivity(MyRequestActivity.this, PublishQuestionActivity.class,bundle);
                break;
        }
    }

    @Override
    public void onBack(View v) {
        super.onBack(v);

    }
}

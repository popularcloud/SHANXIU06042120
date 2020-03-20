package com.lwc.shanxiu.module.question.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.question.bean.AnswerItemBean;
import com.lwc.shanxiu.module.question.ui.adapter.AnswerAdapter;
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
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 我的回答
 */
public class MyAnswerActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;
    @BindView(R.id.iv_no_data)
    ImageView iv_no_data;

    List<AnswerItemBean> beanList = new ArrayList<>();
    private AnswerAdapter adapter;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_my_answer;
    }

    @Override
    protected void findViews() {
        showBack();
        setTitle("我的回答");
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateReadAcount();
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
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {

    }

    private void loadData(){
        Map<String, String> map = new HashMap<>();
        map.put("curPage", ""+page);
        HttpRequestUtils.httpRequest(MyAnswerActivity.this, "我的回答", RequestValue.QUESION_MAINTENANCEANSWER, map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        List<AnswerItemBean> beanList = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"),new TypeToken<ArrayList<AnswerItemBean>>(){});
                        loadDataToAdapter(beanList);
                        break;
                    default:
                        ToastUtil.showToast(MyAnswerActivity.this, common.getInfo());
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

    private void loadDataToAdapter(List<AnswerItemBean> datas){
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
                ToastUtil.showToast(MyAnswerActivity.this,"没有更多数据了");
            }
        }
    }

    private void bindRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AnswerAdapter(this, beanList, R.layout.item_answer_question);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("quesionId", adapter.getItem(position).getQuesionId());
                IntentUtil.gotoActivity(MyAnswerActivity.this, QuestionDetailActivity.class, bundle);
                //记录点击了item
                SharedPreferencesUtils.setParam(MyAnswerActivity.this,"itemPosition",position);
            }
        });
    }
}

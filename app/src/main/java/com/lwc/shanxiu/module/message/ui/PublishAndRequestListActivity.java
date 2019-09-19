package com.lwc.shanxiu.module.message.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.message.adapter.PublishAndRequestAdapter;
import com.lwc.shanxiu.module.message.bean.PublishAndRequestBean;
import com.lwc.shanxiu.module.message.bean.SearchConditionBean;
import com.lwc.shanxiu.utils.BGARefreshLayoutUtils;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.widget.TagsLayout;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 我的提问和文章列表
 */
public class PublishAndRequestListActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.tv_device_type)
    TextView tv_device_type;
    @BindView(R.id.tv_brand)
    TextView tv_brand;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.iv_no_data)
    ImageView iv_no_data;
    List<PublishAndRequestBean> beanList = new ArrayList<>();
    private PublishAndRequestAdapter adapter;
    private int page = 1;
    private String type_id = "";
    private String search_txt = "";

    private int searchType = 1; //1是发表 2.是提问

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_my_publish;
    }

    @Override
    protected void findViews() {
        searchType = getIntent().getIntExtra("searchType",1);
        if(searchType == 1){
            tv_device_type.setTextColor(Color.parseColor("#1481ff"));
            tv_brand.setTextColor(Color.parseColor("#000000"));
            searchType = 1;
        }else{
            tv_brand.setTextColor(Color.parseColor("#1481ff"));
            tv_device_type.setTextColor(Color.parseColor("#000000"));
            searchType = 2;
        }

        BGARefreshLayoutUtils.initRefreshLayout(this, mBGARefreshLayout);
        bindRecycleView();
    }

    private void bindRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PublishAndRequestAdapter(this, beanList, R.layout.item_publish_request);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                Bundle bundle2 = new Bundle();
                if(searchType == 1){
                    bundle2.putInt("operateType",3);
                }else{
                    bundle2.putInt("operateType",4);
                }
                bundle2.putString("knowledgeId",beanList.get(position).getKnowledgeId());
                bundle2.putString("hasAward",beanList.get(position).getHasAward());

              //  if(adapter.getData().get(position).getStatus() == 2){
                    IntentUtil.gotoActivity(PublishAndRequestListActivity.this, PublishDetailActivity.class,bundle2);
              /*  }else{
                    IntentUtil.gotoActivity(PublishAndRequestListActivity.this, PublishActivity.class,bundle2);
                }*/
            }
        });
    }

    @Override
    protected void initGetData() {
    }

    @Override
    protected void widgetListener() {

        recyclerView.setAdapter(adapter);

        //刷新控件监听器
        mBGARefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                page = 1;
                iv_no_data.setVisibility(View.GONE);
                mBGARefreshLayout.setVisibility(View.VISIBLE);
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
    protected void onResume() {
        super.onResume();
        if(mBGARefreshLayout != null){
            mBGARefreshLayout.beginRefreshing();
        }
    }

    @OnClick({R.id.tv_device_type,R.id.tv_brand,R.id.tv_search,R.id.iv_no_data})
    public void onBtnClick(View v){
        switch (v.getId()){
            case R.id.tv_device_type:
                tv_device_type.setTextColor(Color.parseColor("#1481ff"));
                tv_brand.setTextColor(Color.parseColor("#000000"));
                searchType = 1;
                mBGARefreshLayout.beginRefreshing();
                break;
            case R.id.tv_brand:
                tv_brand.setTextColor(Color.parseColor("#1481ff"));
                tv_device_type.setTextColor(Color.parseColor("#000000"));
                searchType = 2;
                mBGARefreshLayout.beginRefreshing();
                break;
            case R.id.iv_no_data:
                iv_no_data.setVisibility(View.GONE);
                mBGARefreshLayout.setVisibility(View.VISIBLE);
                mBGARefreshLayout.beginRefreshing();
                break;
            case R.id.tv_search:
                search_txt = et_search.getText().toString().trim();
                if(TextUtils.isEmpty(search_txt)){
                    ToastUtil.showToast(PublishAndRequestListActivity.this,"请输入要搜索的内容");
                    return;
                }
                mBGARefreshLayout.beginRefreshing();
                break;
        }
    }
    @Override
    protected void init() {

    }


    private void loadData(){
        search_txt = et_search.getText().toString().trim();
        Map<String,String> params = new HashMap<>();
        params.put("type", String.valueOf(searchType));
        params.put("wd", search_txt);
        params.put("curPage", ""+page);
        HttpRequestUtils.httpRequest(this, "获取提问和发表列表", RequestValue.GET_KNOWLEDGE_MAINTENANCEINDEX, params, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        List<PublishAndRequestBean> knowledgeBeanList = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"),new TypeToken<ArrayList<PublishAndRequestBean>>(){});
                        loadDataToAdapter(knowledgeBeanList);
                        break;
                    default:
                        ToastUtil.showToast(PublishAndRequestListActivity.this, common.getInfo());
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

    private void loadDataToAdapter(List<PublishAndRequestBean> datas){
        if(page == 1){
            BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);
            if(datas == null || datas.size() < 1){
                iv_no_data.setVisibility(View.VISIBLE);
                mBGARefreshLayout.setVisibility(View.GONE);
            }else{
                iv_no_data.setVisibility(View.GONE);
                mBGARefreshLayout.setVisibility(View.VISIBLE);
            }
            beanList.clear();
            beanList = datas;
            adapter.clear();
            adapter.addAll(beanList);
        }else{
            BGARefreshLayoutUtils.endLoadingMore(mBGARefreshLayout);
            if(datas == null || datas.size() < 1){
                ToastUtil.showToast(PublishAndRequestListActivity.this,"没有更多数据了");
                return;
            }else{
                beanList.addAll(datas);
                adapter.addAll(datas);
            }
        }
    }
}

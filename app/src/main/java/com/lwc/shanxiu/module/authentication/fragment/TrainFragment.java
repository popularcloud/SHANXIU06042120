package com.lwc.shanxiu.module.authentication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.gyf.immersionbar.ImmersionBar;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.BaseFragment;
import com.lwc.shanxiu.module.authentication.activity.SeeVideoActivity;
import com.lwc.shanxiu.module.authentication.adapter.TrainAdapter;
import com.lwc.shanxiu.module.authentication.bean.TrainBean;
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
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class TrainFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;
    private TrainAdapter adapter;
    @BindView(R.id.textTip)
    TextView textTip;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    //加载的page页
    private int page = 1;

    private SharedPreferencesUtils preferencesUtils = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_train, null);
        ButterKnife.bind(this, view);
        BGARefreshLayoutUtils.initRefreshLayout(getContext(), mBGARefreshLayout,false);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initEngines(view);
        init();
        setListener();
        bindRecycleView();

        ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true).init();

        tv_submit.setVisibility(View.GONE);
    }


    private void bindRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TrainAdapter(getContext(), null, R.layout.item_train);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("videoBean", adapter.getItem(position));
                IntentUtil.gotoActivity(getContext(), SeeVideoActivity.class, bundle);
            }
        });
        recyclerView.setAdapter(adapter);

        mBGARefreshLayout.beginRefreshing();
    }


    @Override
    protected void lazyLoad() {

    }

    @Override
    public void init() {
    }

    @Override
    public void initEngines(View view) {
        preferencesUtils = SharedPreferencesUtils.getInstance(getContext());
    }

    @Override
    public void getIntentData() {

    }

    @Override
    public void setListener() {
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
    }

    private void loadData(){
        Map<String, String> map = new HashMap<>();
        map.put("curPage", ""+page);
        HttpRequestUtils.httpRequest(getActivity(), "视频列表", RequestValue.EXAMMANAGE_GETEXAMVIDEO, map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        List<TrainBean> beanList = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"),new TypeToken<ArrayList<TrainBean>>(){});
                        if(page == 1){
                            notifyData(beanList);
                            mBGARefreshLayout.endRefreshing();
                        }else{
                            addData(beanList);
                            mBGARefreshLayout.endLoadingMore();
                        }
                        break;
                    default:
                        //ToastUtil.showToast(MyRequestActivity.this, common.getInfo());
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

    public void notifyData(List<TrainBean> myOrders) {
        adapter.replaceAll(myOrders);
        if(myOrders!= null && myOrders.size() > 0) {
            textTip.setVisibility(View.GONE);
        } else {
            textTip.setVisibility(View.VISIBLE);
        }
    }

    public void addData(List<TrainBean> myOrders) {
        if (myOrders == null || myOrders.size() == 0) {
            ToastUtil.showLongToast(getActivity(),"暂无更多数据");
            page--;
            return;
        }
        adapter.addAll(myOrders);
    }

}

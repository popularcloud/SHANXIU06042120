package com.lwc.shanxiu.module.authentication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.gyf.immersionbar.ImmersionBar;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.BaseFragment;
import com.lwc.shanxiu.module.authentication.adapter.TrainAdapter;
import com.lwc.shanxiu.module.authentication.adapter.WrongTopicAdapter;
import com.lwc.shanxiu.module.authentication.bean.WrongTopicBean;
import com.lwc.shanxiu.module.bean.Order;
import com.lwc.shanxiu.module.question.bean.QuestionIndexBean;
import com.lwc.shanxiu.utils.BGARefreshLayoutUtils;
import com.lwc.shanxiu.utils.HttpRequestUtils;
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

/**
 * 错题集
 */
public class WrongTopicFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;
    private WrongTopicAdapter adapter;
    @BindView(R.id.textTip)
    TextView textTip;
    @BindView(R.id.ll_clear)
    LinearLayout ll_clear;
    //加载的page页
    private int page = 1;

    private SharedPreferencesUtils preferencesUtils = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wrong_topic, null);
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
    }


    private void bindRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new WrongTopicAdapter(getContext(), null, R.layout.item_wrong_topic);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
   /*             Bundle bundle = new Bundle();
                bundle.putString("orderId", myOrders.get(position).getOrderId());
                IntentUtil.gotoActivity(getContext(), OrderDetailActivity.class, bundle);*/
            }
        });
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onResume() {
        super.onResume();
        mBGARefreshLayout.beginRefreshing();
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
        mBGARefreshLayout.setIsShowLoadingMoreView(false);
        mBGARefreshLayout.setPullDownRefreshEnable(false);
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

        ll_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearTopic();
            }
        });
    }

    private void loadData(){
        Map<String, String> map = new HashMap<>();
        map.put("curPage", ""+page);
        HttpRequestUtils.httpRequest(getActivity(), "错题列表", RequestValue.EXAMMANAGE_GETEXAMERRORBOOK, map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        List<WrongTopicBean> beanList = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"),new TypeToken<ArrayList<WrongTopicBean>>(){});
                        if(page == 1){
                            notifyData(beanList);
                            mBGARefreshLayout.endRefreshing();
                        }else{
                            addData(beanList);
                            mBGARefreshLayout.endLoadingMore();
                        }
                        break;
                    default:
                        ToastUtil.showToast(getActivity(), common.getInfo());
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

    private void clearTopic(){
        HttpRequestUtils.httpRequest(getActivity(), "清空错题列表", RequestValue.EXAMMANAGE_DELETEEXAMERRORBOOK, null, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        ToastUtil.showToast(getActivity(), common.getInfo());
                        mBGARefreshLayout.beginRefreshing();
                        break;
                    default:
                        ToastUtil.showToast(getActivity(), common.getInfo());
                        break;
                }

            }

            @Override
            public void returnException(Exception e, String msg) {
                BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);
            }
        });
    }

    public void notifyData(List<WrongTopicBean> myOrders) {
        adapter.replaceAll(myOrders);
        if(myOrders!= null && myOrders.size() > 0) {
            textTip.setVisibility(View.GONE);
        } else {
            textTip.setVisibility(View.VISIBLE);
        }
    }

    public void addData(List<WrongTopicBean> myOrders) {
        if (myOrders == null || myOrders.size() == 0) {
            ToastUtil.showLongToast(getActivity(),"暂无更多数据");
            page--;
            return;
        }
        adapter.addAll(myOrders);
    }

}

package com.lwc.shanxiu.module.lease_parts.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.BaseFragment;
import com.lwc.shanxiu.module.lease_parts.activity.LeaseGoodsDetailActivity;
import com.lwc.shanxiu.module.lease_parts.adapter.LeaseGoodsAdapter;
import com.lwc.shanxiu.module.lease_parts.bean.LeaseGoodBean;
import com.lwc.shanxiu.module.lease_parts.inteface_callback.OnListItemClick;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LeaseGoodListFragment extends BaseFragment {

    @BindView(R.id.rv_goods)
    RecyclerView rv_goods;

    private int distance = 0;
    private LeaseHomeFragment parentFragment;
    private String position = "0";
    private int scrollStatus = -1;  //0 滚动静止  1.按下滚动 2.抬起滚动

    private List<LeaseGoodBean> leaseGoodBeans = new ArrayList<>();

    private LeaseGoodsAdapter leaseGoodsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lease_good_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        parentFragment = (LeaseHomeFragment) getParentFragment();
        init();
        setListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void init() {

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rv_goods.setNestedScrollingEnabled(false);
        rv_goods.setLayoutManager(staggeredGridLayoutManager);
        leaseGoodsAdapter = new LeaseGoodsAdapter(getContext(), leaseGoodBeans, new OnListItemClick() {
            @Override
            public void onItemClick(Object o) {
                LeaseGoodBean leaseGoodBean = (LeaseGoodBean) o;
                Bundle bundle = new Bundle();
                bundle.putString("goodId",leaseGoodBean.getGoodsId());
                IntentUtil.gotoActivity(getActivity(), LeaseGoodsDetailActivity.class,bundle);
            }
        });
        rv_goods.setAdapter(leaseGoodsAdapter);
        getLeaseGood();
    }

    @Override
    public void initEngines(View view) {

    }

    @Override
    public void getIntentData() {

    }

    /**
     * 获取租赁商品数据
     */
    public void getLeaseGood() {
        HttpRequestUtils.httpRequest(getActivity(), "获取租赁商品数据", RequestValue.GET_PARTSMANAGE_GETPARTSGOODS, null, "GET", new HttpRequestUtils.ResponseListener() {

            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        String dataList = JsonUtil.getGsonValueByKey(response,"data");
                        String datas = JsonUtil.getGsonValueByKey(dataList,"data");
                        if("[]".equals(datas)){
                            leaseGoodBeans = new ArrayList<>();
                            leaseGoodsAdapter.setDataList(leaseGoodBeans);
                        }else{
                            leaseGoodBeans = JsonUtil.parserGsonToArray((String) datas, new TypeToken<ArrayList<LeaseGoodBean>>() {
                            });
                            if(leaseGoodBeans != null && leaseGoodBeans.size() > 0){

                                int line = leaseGoodBeans.size()/2;
                                if(leaseGoodBeans.size()%2 > 0){
                                    line = line + 1;
                                }
                                int lineHeight = Utils.dip2px(getContext(),330);
                                parentFragment.setViewpageHeight(line * lineHeight);

                                leaseGoodsAdapter.setDataList(leaseGoodBeans);
                            }
                        }
                        break;
                    default:
                        LLog.i("获取租赁商品数据" + common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
    }

    @Override
    public void setListener() {
    /*    rv_goods.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //Log.d("滚动","---的状态"+newState + "position=="+position);
                scrollStatus = newState;
                if(scrollStatus == 0){
                    if(distance > 10){
                        parentFragment.showOrHideHeader(false);
                    }
                    if(distance < -10){
                        parentFragment.showOrHideHeader(true);
                    }
                    distance = 0;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                    distance = distance + dy;
                   // Log.d("滚动","---的距离"+distance);
            }
        });*/
    }
}

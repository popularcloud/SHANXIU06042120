package com.lwc.shanxiu.module.order.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.BaseFragment;
import com.lwc.shanxiu.module.bean.Order;
import com.lwc.shanxiu.module.order.presenter.OrderListPresenter;
import com.lwc.shanxiu.module.order.ui.IOrderListFragmentView;
import com.lwc.shanxiu.module.order.ui.activity.OrderDetailActivity;
import com.lwc.shanxiu.module.order.ui.adapter.OrderListAdapter;
import com.lwc.shanxiu.utils.BGARefreshLayoutUtils;
import com.lwc.shanxiu.utils.IntentUtil;

import org.byteam.superadapter.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * @author 何栋
 * @version 1.0
 * @date 2017/3/9 18:02
 * @email 294663966@qq.com
 * 已取消
 */
public class CanceledFragment extends BaseFragment implements IOrderListFragmentView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;
    private OrderListAdapter adapter;
    private List<Order> myOrders;
    @BindView(R.id.textTip)
    TextView textTip;
    private OrderListPresenter presenter;
    //加载的page页
    private int page = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_published, null);
        ButterKnife.bind(this, view);
        BGARefreshLayoutUtils.initRefreshLayout(getContext(), mBGARefreshLayout);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initEngines(view);
        init();
        setListener();
        bindRecycleView();
        mBGARefreshLayout.beginRefreshing();  //请求网络数据
    }


    private void bindRecycleView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new OrderListAdapter(getContext(), myOrders, R.layout.item_order);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {

                Bundle bundle = new Bundle();
                bundle.putString("orderId", myOrders.get(position).getOrderId());
                IntentUtil.gotoActivity(getContext(), OrderDetailActivity.class, bundle);
            }
        });
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void lazyLoad() {

    }

    @Override
    public void init() {
    }

    @Override
    public void initEngines(View view) {
        presenter = new OrderListPresenter(getContext(), this);
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
                presenter.getOrders(1, 2);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {

                page++;
                presenter.loadOrders(page, 2);
                return true;
            }
        });
    }

    @Override
    public void notifyData(List<Order> myOrders) {
        this.myOrders = myOrders;
        adapter.replaceAll(myOrders);
        if(this.myOrders!= null && this.myOrders.size() > 0) {
            textTip.setVisibility(View.GONE);
        } else {
            textTip.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void addData(List<Order> myOrders) {
        this.myOrders.addAll(myOrders);
        adapter.addAll(myOrders);
    }

    @Override
    public BGARefreshLayout getBGARefreshLayout() {
        return mBGARefreshLayout;
    }
}

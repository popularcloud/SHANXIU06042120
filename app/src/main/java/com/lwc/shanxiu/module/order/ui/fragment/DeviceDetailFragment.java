package com.lwc.shanxiu.module.order.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.adapter.AfterServiceAdapter;
import com.lwc.shanxiu.module.BaseFragment;
import com.lwc.shanxiu.module.bean.AfterService;
import com.lwc.shanxiu.module.bean.Order;
import com.lwc.shanxiu.module.order.presenter.DeviceDetailPresenter;
import com.lwc.shanxiu.module.order.ui.IDeviceDetailFragmentView;
import com.lwc.shanxiu.module.order.ui.activity.OrderDetailActivity;
import com.lwc.shanxiu.utils.BGARefreshLayoutUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * @author 何栋
 * @version 1.0
 * @date 2017/3/13 17:39
 * @email 294663966@qq.com
 * 设备详情
 */
public class DeviceDetailFragment extends BaseFragment implements IDeviceDetailFragmentView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    //订单状态
    @BindView(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.tv_msg)
    TextView tv_msg;
    private Order myOrder = null;
    private DeviceDetailPresenter presenter;
    private List<AfterService> asList = new ArrayList<AfterService>();
    private AfterServiceAdapter adapter;
    private AfterService afterService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_detail, null);
        ButterKnife.bind(this, view);
        BGARefreshLayoutUtils.initRefreshLayout(getContext(), mBGARefreshLayout, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initEngines(view);
        getIntentData();
        bindRecycleView();
        setListener();
        getData();
    }

    private void bindRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AfterServiceAdapter(getContext(), asList, R.layout.item_order_status);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 请求网络获取数据
     */
    private void getData() {
        presenter.getOrderInfor(myOrder.getOrderId() + "", mBGARefreshLayout);
    }

    @Override
    protected void lazyLoad() {
    }

    @Override
    public void init() {
    }

    @Override
    public void initEngines(View view) {
        presenter = new DeviceDetailPresenter(getActivity(), this);
    }

    @Override
    public void getIntentData() {
        myOrder = (Order) getArguments().getSerializable("data");
    }

    @Override
    public void setListener() {
        //刷新控件监听器
        mBGARefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                getData();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                return false;
            }
        });
    }

    @Override
    public void setDeviceDetailInfor(List<AfterService> asList) {
        if (asList != null && asList.size() > 0){
            tv_msg.setVisibility(View.GONE);
            this.asList = asList;
            adapter.replaceAll(asList);
            afterService = this.asList.get(this.asList.size() - 1);
            int type = afterService.getWarrantyState();
            if (type == AfterService.STATUS_YISHENQING) {
                btn.setVisibility(View.VISIBLE);
                btn.setText("接受");
                if (OrderDetailActivity.activity != null) {
                    OrderDetailActivity.activity.setBohao(true);
                }
            } else if (type == AfterService.STATUS_YIJIESHOU) {
                btn.setVisibility(View.VISIBLE);
                btn.setText("开始处理");
                if (OrderDetailActivity.activity != null) {
                    OrderDetailActivity.activity.setBohao(true);
                }
            } else if (type == AfterService.STATUS_CHULI) {
                btn.setVisibility(View.VISIBLE);
                btn.setText("完成");
                if (OrderDetailActivity.activity != null) {
                    OrderDetailActivity.activity.setBohao(true);
                }
            } else {
                btn.setVisibility(View.GONE);
                if (OrderDetailActivity.activity != null && this.asList.get(0).getWarrantyState() == AfterService.STATUS_GUOQI || type == AfterService.STATUS_GUOQI) {
                    OrderDetailActivity.activity.setBohao(false);
                } else if (OrderDetailActivity.activity != null) {
                    OrderDetailActivity.activity.setBohao(true);
                }
            }
        } else {
            tv_msg.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.btn)
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn:
                String text = btn.getText().toString().trim();
                if (text.equals("接受")) {
                    presenter.updateOrderInfor(myOrder.getOrderId(), AfterService.STATUS_YIJIESHOU, mBGARefreshLayout);
                } else if (text.equals("开始处理")) {
                    presenter.updateOrderInfor(myOrder.getOrderId(), AfterService.STATUS_CHULI, mBGARefreshLayout);
                } else if (text.equals("完成")) {
                    presenter.updateOrderInfor(myOrder.getOrderId(), AfterService.STATUS_WANGCHENGDAIQUEREN, mBGARefreshLayout);
                }
                break;
        }
    }
}

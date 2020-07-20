package com.lwc.shanxiu.module.lease_parts.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.lwc.shanxiu.activity.BaseActivity;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * @author younge
 * @date 2018/12/19 0019
 * @email 2276559259@qq.com
 */
public class LeaseNeedPayActivity extends BaseActivity {
    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return 0;
    }

    @Override
    protected void findViews() {

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

   /* @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_msg)
    TextView tv_msg;
    @BindView(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;
    private LeaseOrderNeedPayAdapter adapter;
    private List<MyOrderBean> myOrders = new ArrayList<>();

    private int page = 1;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_lease_need_pay;
    }

    @Override
    protected void findViews() {
        bindRecycleView();
    }

    @Override
    protected void init() {
        setTitle("订单缴费");
        setRight(R.drawable.ic_message, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyMsg msg = new MyMsg();
                msg.setMessageType("5");
                msg.setMessageTitle("租赁消息");
                Bundle bundle = new Bundle();
                bundle.putSerializable("myMsg", msg);
                IntentUtil.gotoActivity(LeaseNeedPayActivity.this, MsgListActivity.class,bundle);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        //获取未读租赁消息
        MsgReadUtil.hasMessage(LeaseNeedPayActivity.this,tv_msg);
    }

    @Override
    protected void initGetData() {
    }


    @Override
    protected void widgetListener() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void bindRecycleView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(LeaseNeedPayActivity.this));
        adapter = new LeaseOrderNeedPayAdapter(LeaseNeedPayActivity.this, myOrders, R.layout.item_lease_order, 5, new OnOrderBtnClick() {
            @Override
            public void onItemClick(String btnText, int position,Object o) {
                switch (btnText){
                    case "联系客服":
                        GetPhoneDialog picturePopupWindow = new GetPhoneDialog(LeaseNeedPayActivity.this, new GetPhoneDialog.CallBack() {
                            @Override
                            public void twoClick() {
                                Utils.lxkf(MainActivity.activity, "400-881-0769");
                            }
                            @Override
                            public void cancelCallback() {
                            }
                        }, "", "呼叫 400-881-0769");
                        picturePopupWindow.show();
                        break;
                    case "退租":
                        MyOrderBean myOrder = (MyOrderBean) o;
                        OrderDetailBean orderDetailBean = new OrderDetailBean();
                        orderDetailBean.setGoodsImg(myOrder.getGoodsImg());
                        orderDetailBean.setGoodsName(myOrder.getGoodsName());
                        orderDetailBean.setGoodsRealNum(myOrder.getGoodsRealNum());
                        orderDetailBean.setGoodsNum(myOrder.getGoodsNum());
                        orderDetailBean.setPayPrice(myOrder.getPayPrice());
                        orderDetailBean.setGoodsPrice(myOrder.getGoodsPrice());
                        orderDetailBean.setGoodsId(myOrder.getGoodsId());
                        orderDetailBean.setOrderId(myOrder.getOrderId());
                        orderDetailBean.setLeaseSpecs(myOrder.getLeaseSpecs());
                        orderDetailBean.setLeaseMonTime(myOrder.getLeaseMonTime());
                        orderDetailBean.setPayType(myOrder.getPayType());

                        Bundle bundle05 = new Bundle();
                        bundle05.putSerializable("orderDetailBean", orderDetailBean);
                        bundle05.putInt("returnType", 3);
                        IntentUtil.gotoActivity(LeaseNeedPayActivity.this, LeaseApplyForRefundActivity.class, bundle05);
                        break;
                    case "缴费":
                    case "续租":
                        MyOrderBean myOrder2 = (MyOrderBean) o;
                        Bundle bundle = new Bundle();
                        bundle.putString("order_id",myOrder2.getOrderId());
                        IntentUtil.gotoActivity(LeaseNeedPayActivity.this, LeaseOrderNeedPayDetailActivity.class,bundle);
                        break;
                }
            }
        });

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("order_id",adapter.getItem(position).getOrderId());
                IntentUtil.gotoActivity(LeaseNeedPayActivity.this, LeaseOrderNeedPayDetailActivity.class,bundle);
            }
        });
        recyclerView.setAdapter(adapter);
        BGARefreshLayoutUtils.initRefreshLayout(this, mBGARefreshLayout);
        //刷新控件监听器
        mBGARefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                page = 1;
                getDateList();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                page ++;
                getDateList();
                return true;
            }
        });

        mBGARefreshLayout.beginRefreshing();
    }

    *//**
     * 获取我的订单
     *//*
    private void getDateList(){
        String requestUrl = RequestValue.LEASEMANAGE_QUERYLEASEORDERS+"?in_status_id=4,5,9&curPage="+page;
        HttpRequestUtils.httpRequest(LeaseNeedPayActivity.this, "获取需交费的订单",requestUrl, null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                if("1".equals(common.getStatus())){
                    // ToastUtil.showToast(getActivity(),"获取我的订单成功!");
                    myOrders = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response,"data"),new TypeToken<ArrayList<MyOrderBean>>(){});
                    if(page == 1){
                        adapter.replaceAll(myOrders);
                    }else{
                        adapter.addAll(myOrders);
                    }
                }else{
                    ToastUtil.showToast(LeaseNeedPayActivity.this,common.getInfo());
                }

                if(page == 1){
                    mBGARefreshLayout.endRefreshing();
                }else{
                    mBGARefreshLayout.endLoadingMore();
                }

            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
                if(page == 1){
                    mBGARefreshLayout.endRefreshing();
                }else{
                    mBGARefreshLayout.endLoadingMore();
                }
            }
        });
    }*/
}

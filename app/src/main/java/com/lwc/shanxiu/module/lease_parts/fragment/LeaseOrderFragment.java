package com.lwc.shanxiu.module.lease_parts.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.BaseFragment;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.module.lease_parts.activity.LeaseApplyForRefundActivity;
import com.lwc.shanxiu.module.lease_parts.activity.LeaseGoodLogisticsActivity;
import com.lwc.shanxiu.module.lease_parts.activity.LeaseOrderDetailActivity;
import com.lwc.shanxiu.module.lease_parts.activity.MyLeaseOrderListActivity;
import com.lwc.shanxiu.module.lease_parts.adapter.LeaseOrderListAdapter;
import com.lwc.shanxiu.module.lease_parts.bean.MyOrderBean;
import com.lwc.shanxiu.module.lease_parts.bean.OrderDetailBean;
import com.lwc.shanxiu.module.lease_parts.inteface_callback.OnOrderBtnClick;
import com.lwc.shanxiu.module.lease_parts.widget.CancelOrderDialog;
import com.lwc.shanxiu.utils.BGARefreshLayoutUtils;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.ToastUtil;

import org.byteam.superadapter.OnItemClickListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class LeaseOrderFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;
    private LeaseOrderListAdapter adapter;
    private List<MyOrderBean> myOrders = new ArrayList<>();;
    //加载的page页
    private int page = 1;
    @BindView(R.id.ll_null)
    LinearLayout ll_null;



    private int pageType = 0;

    private List<String> reasons = new ArrayList<>();
    private CancelOrderDialog cancelOrderDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lease_order, null);
        ButterKnife.bind(this, view);
        BGARefreshLayoutUtils.initRefreshLayout(getContext(), mBGARefreshLayout);

        pageType = getArguments().getInt("pageType");

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initEngines(view);
        init();
        setListener();
        bindRecycleView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mBGARefreshLayout != null){
            mBGARefreshLayout.beginRefreshing();
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if(mBGARefreshLayout != null){
                mBGARefreshLayout.beginRefreshing();
            }
        }
    }

    private void bindRecycleView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LeaseOrderListAdapter(getContext(), myOrders, R.layout.item_lease_order, pageType, new OnOrderBtnClick() {
            @Override
            public void onItemClick(String btnText, int position,final Object o) {
                final MyOrderBean myOrder = (MyOrderBean) o;
                switch (btnText){
                    case "取消订单":
                        reasons.clear();
                        reasons.add("多拍/拍错/不想要");
                        reasons.add("未按约定时间发货");
                        reasons.add("平台缺货");
                        reasons.add("其他原因");
                        cancelOrderDialog = new CancelOrderDialog(getContext(), new CancelOrderDialog.CallBack() {
                            @Override
                            public void onSubmit(int position) {
                                cancelOrderDialog.dismiss();
                                cancelLeaseOrder(reasons.get(position),myOrder.getOrderId());
                            }
                        },reasons,"取消订单","请选择取消订单原因",true);
                        cancelOrderDialog.show();
                        break;
                    case "确认收货":
                        inLease(myOrder.getOrderId());
                        break;
                    case "查看物流":
                        Bundle bundle = new Bundle();
                        bundle.putString("order_id",myOrder.getOrderId());
                        bundle.putInt("openType",1);
                        IntentUtil.gotoActivity(getContext(), LeaseOrderDetailActivity.class,bundle);
                        break;
                    case "删除订单":
                        delOrder(myOrder.getOrderId());
                        break;
                    case "退货":

                        OrderDetailBean orderDetailBean = new OrderDetailBean();
                        orderDetailBean.setGoodsImg(myOrder.getGoodsImg());
                        orderDetailBean.setGoodsName(myOrder.getGoodsName());
                        orderDetailBean.setGoodsRealNum(myOrder.getGoodsRealNum());
                        orderDetailBean.setLeaseMonTime(myOrder.getLeaseMonTime());
                        orderDetailBean.setPayType(myOrder.getPayType());
                        orderDetailBean.setGoodsNum(myOrder.getGoodsNum());
                        orderDetailBean.setGoodsPrice(myOrder.getGoodsPrice());
                        orderDetailBean.setPayPrice(myOrder.getPayPrice());
                        orderDetailBean.setGoodsId(myOrder.getGoodsId());
                        orderDetailBean.setOrderId(myOrder.getOrderId());
                        orderDetailBean.setLeaseSpecs(myOrder.getLeaseSpecs());

                        Bundle bundle02 = new Bundle();
                        bundle02.putSerializable("orderDetailBean", orderDetailBean);
                        bundle02.putInt("returnType", 0);
                        IntentUtil.gotoActivity(getActivity(), LeaseApplyForRefundActivity.class, bundle02);
                        break;
                }
            }
        });

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("order_id",adapter.getItem(position).getOrderId());
                bundle.putInt("openType",1);
                IntentUtil.gotoActivity(getContext(), LeaseOrderDetailActivity.class,bundle);
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
                    getDateList();
                }

                @Override
                public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                    page++;
                    getDateList();
                    return true;
                }
            });

        ll_null.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_null.setVisibility(View.GONE);
                mBGARefreshLayout.setVisibility(View.VISIBLE);
                mBGARefreshLayout.beginRefreshing();
            }
        });
    }


    /**
     * 获取我的订单
     */
    private void getDateList(){

        Map<String,String> params = new HashMap<>();
        params.put("curPage",String.valueOf(page));
        //0 表示全部订单
        if(pageType != 0){
            params.put("in_status_id",String.valueOf(pageType));

        }

        HttpRequestUtils.httpRequest(getActivity(), "获取我的订单+"+pageType, RequestValue.PARTSMANAGE_QUERYPARTSORDERS , params, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                if("1".equals(common.getStatus())){
                   // ToastUtil.showToast(getActivity(),"获取我的订单成功!");
                    myOrders = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response,"data"),new TypeToken<ArrayList<MyOrderBean>>(){});
                    if(page == 1){
                        adapter.replaceAll(myOrders);

                        if(myOrders != null && myOrders.size() > 0){
                            ll_null.setVisibility(View.GONE);
                            mBGARefreshLayout.setVisibility(View.VISIBLE);
                        }else{
                            ll_null.setVisibility(View.VISIBLE);
                            mBGARefreshLayout.setVisibility(View.GONE);
                        }

                    }else{
                        if(myOrders != null && myOrders.size() > 0){
                            adapter.addAll(myOrders);
                        }else{
                          ToastUtil.showToast(getContext(),"暂无更多数据!");
                        }
                    }
                }else{
                    ToastUtil.showToast(getActivity(),common.getInfo());
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
    }

    /**
     * 取消订单
     */
    private void cancelLeaseOrder(String reason,String orderId){
        Map<String,String> params = new HashMap<>();
        params.put("order_id",orderId);
        params.put("user_reason",reason);

        HttpRequestUtils.httpRequest(getActivity(), "取消订单",RequestValue.PARTSMANAGE_CANCELPARTSORDER, params, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                if("1".equals(common.getStatus())){
                     ToastUtil.showToast(getActivity(),common.getInfo());
                    mBGARefreshLayout.beginRefreshing();
                }else{
                    ToastUtil.showToast(getActivity(),common.getInfo());
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
    }

    /**
     * 确认收货
     * @param orderId
     */
    private void inLease(String orderId){
        Map<String,String> params = new HashMap<>();
        params.put("order_id",orderId);
        HttpRequestUtils.httpRequest(getActivity(), "确认收货",RequestValue.PARTSMANAGE_INPARTS, params, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                if("1".equals(common.getStatus())){
                    ToastUtil.showToast(getContext(),common.getInfo());
                    mBGARefreshLayout.beginRefreshing();
                }else{
                    ToastUtil.showToast(getContext(),common.getInfo());
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
    }

    /**
     * 撤销申请
     * @param orderId
     */
    private void retrunApply(String orderId){
        Map<String,String> params = new HashMap<>();
        params.put("branch_id",orderId);
        HttpRequestUtils.httpRequest(getActivity(), "撤销申请",RequestValue.PARTSMANAGE_UODOPARTSBRANCHORDER, params, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                if("1".equals(common.getStatus())){
                    ToastUtil.showToast(getContext(),common.getInfo());
                    mBGARefreshLayout.beginRefreshing();
                }else{
                    ToastUtil.showToast(getContext(),common.getInfo());
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
    }

    private void delOrder(String orderId){
        Map<String,String> params = new HashMap<>();
        params.put("order_id",orderId);
        HttpRequestUtils.httpRequest(getActivity(), "删除订单",RequestValue.PARTSMANAGE_DELETEPARTSORDER, params, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                if("1".equals(common.getStatus())){
                    ToastUtil.showToast(getContext(),common.getInfo());
                    mBGARefreshLayout.beginRefreshing();
                }else{
                    ToastUtil.showToast(getContext(),common.getInfo());
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
    }
}

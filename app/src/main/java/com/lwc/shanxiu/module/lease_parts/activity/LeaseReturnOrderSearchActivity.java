package com.lwc.shanxiu.module.lease_parts.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.lease_parts.adapter.LeaseOrderReturnListAdapter;
import com.lwc.shanxiu.module.lease_parts.bean.LeaseReturnDetailBean;
import com.lwc.shanxiu.module.lease_parts.bean.MyOrderBean;
import com.lwc.shanxiu.module.lease_parts.inteface_callback.OnOrderBtnClick;
import com.lwc.shanxiu.module.lease_parts.widget.CancelOrderDialog;
import com.lwc.shanxiu.module.lease_parts.widget.GetPhoneDialog;
import com.lwc.shanxiu.module.message.bean.MyMsg;
import com.lwc.shanxiu.module.message.ui.MsgListActivity;
import com.lwc.shanxiu.utils.BGARefreshLayoutUtils;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.MsgReadUtil;
import com.lwc.shanxiu.utils.ToastUtil;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * @author younge
 * @date 2018/12/19 0019
 * @email 2276559259@qq.com
 */
public class LeaseReturnOrderSearchActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_msg)
    TextView tv_msg;
    @BindView(R.id.et_search)
    TextView et_search;
    @BindView(R.id.iv_right)
    ImageView iv_right;
    @BindView(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;
    private LeaseOrderReturnListAdapter adapter;
    private List<LeaseReturnDetailBean> myOrders = new ArrayList<>();

    private int page = 1;

    private String wd;

    private List<String> reasons = new ArrayList<>();
    private CancelOrderDialog cancelOrderDialog;


    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_lease_order_search;
    }

    @Override
    protected void findViews() {
        bindRecycleView();
    }

    @Override
    protected void init() {
        setTitle("订单缴费");
        iv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyMsg msg = new MyMsg();
                msg.setMessageType("6");
                msg.setMessageTitle("租赁消息");
                Bundle bundle = new Bundle();
                bundle.putSerializable("myMsg", msg);
                IntentUtil.gotoActivity(LeaseReturnOrderSearchActivity.this, MsgListActivity.class,bundle);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        //获取未读租赁消息
        MsgReadUtil.hasMessage(LeaseReturnOrderSearchActivity.this,tv_msg);

        mBGARefreshLayout.beginRefreshing();
    }

    @Override
    protected void initGetData() {
    }


    @Override
    protected void widgetListener() {
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                wd = s.toString();
                mBGARefreshLayout.beginRefreshing();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void bindRecycleView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(LeaseReturnOrderSearchActivity.this));
        adapter = new LeaseOrderReturnListAdapter(LeaseReturnOrderSearchActivity.this, myOrders, R.layout.item_lease_order, 5, new OnOrderBtnClick() {
            @Override
            public void onItemClick(String btnText, int position,Object o) {
                final String id = (String) o;
                switch (btnText){
                    case "联系客服":
                        GetPhoneDialog picturePopupWindow = new GetPhoneDialog(LeaseReturnOrderSearchActivity.this, new GetPhoneDialog.CallBack() {
                            @Override
                            public void twoClick() {
                                Utils.lxkf(LeaseReturnOrderSearchActivity.this, "400-881-0769");
                            }
                            @Override
                            public void cancelCallback() {
                            }
                        }, "", "呼叫 400-881-0769");
                        picturePopupWindow.show();
                        break;
                    case "撤销申请":
                        retrunApply(id);
                        break;
                    case "删除订单":
                        delOrder(id);
                        break;
                }
            }
        });

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("branch_id",adapter.getItem(position).getBranchId());
                bundle.putInt("openType",1);
                IntentUtil.gotoActivity(LeaseReturnOrderSearchActivity.this, LeaseOrderRefundDetailActivity.class,bundle);
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

    /**
     * 获取我的订单
     */
    private void getDateList(){
        String requestUrl = RequestValue.PARTSMANAGE_QUERYPARTSBRANCHORDERS+"?curPage="+page;
        if(!TextUtils.isEmpty(wd)){
            requestUrl = requestUrl + "&wd="+wd;
        }
        HttpRequestUtils.httpRequest(LeaseReturnOrderSearchActivity.this, "备件库退货订单",requestUrl, null, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                if("1".equals(common.getStatus())){
                    // ToastUtil.showToast(getActivity(),"获取我的订单成功!");
                    myOrders = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response,"data"),new TypeToken<ArrayList<LeaseReturnDetailBean>>(){});
                    if(page == 1){
                        adapter.replaceAll(myOrders);
                    }else{
                        adapter.addAll(myOrders);
                    }
                }else{
                    ToastUtil.showToast(LeaseReturnOrderSearchActivity.this,common.getInfo());
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

        HttpRequestUtils.httpRequest(LeaseReturnOrderSearchActivity.this, "取消订单",RequestValue.PARTSMANAGE_CANCELPARTSORDER, params, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                if("1".equals(common.getStatus())){
                    ToastUtil.showToast(LeaseReturnOrderSearchActivity.this,common.getInfo());
                    mBGARefreshLayout.beginRefreshing();
                }else{
                    ToastUtil.showToast(LeaseReturnOrderSearchActivity.this,common.getInfo());
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
        HttpRequestUtils.httpRequest(LeaseReturnOrderSearchActivity.this, "确认收货",RequestValue.PARTSMANAGE_INPARTS, params, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                if("1".equals(common.getStatus())){
                    ToastUtil.showToast(LeaseReturnOrderSearchActivity.this,common.getInfo());
                    mBGARefreshLayout.beginRefreshing();
                }else{
                    ToastUtil.showToast(LeaseReturnOrderSearchActivity.this,common.getInfo());
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
        HttpRequestUtils.httpRequest(LeaseReturnOrderSearchActivity.this, "撤销申请",RequestValue.PARTSMANAGE_UODOPARTSBRANCHORDER, params, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                if("1".equals(common.getStatus())){
                    ToastUtil.showToast(LeaseReturnOrderSearchActivity.this,common.getInfo());
                    mBGARefreshLayout.beginRefreshing();
                }else{
                    ToastUtil.showToast(LeaseReturnOrderSearchActivity.this,common.getInfo());
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
        params.put("branch_id",orderId);
        HttpRequestUtils.httpRequest(LeaseReturnOrderSearchActivity.this, "删除订单",RequestValue.PARTSMANAGE_DELETEPARTSBRANCHORDER, params, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                if("1".equals(common.getStatus())){
                    ToastUtil.showToast(LeaseReturnOrderSearchActivity.this,common.getInfo());
                    mBGARefreshLayout.beginRefreshing();
                }else{
                    ToastUtil.showToast(LeaseReturnOrderSearchActivity.this,common.getInfo());
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
            }
        });
    }
}

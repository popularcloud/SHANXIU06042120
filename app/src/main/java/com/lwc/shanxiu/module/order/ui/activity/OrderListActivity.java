package com.lwc.shanxiu.module.order.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.ToastUtil;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.Order;
import com.lwc.shanxiu.module.order.ui.adapter.OrderListAdapter;
import com.lwc.shanxiu.utils.BGARefreshLayoutUtils;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.view.ProgressUtils;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * @author 何栋
 * @version 1.0
 * @date 2017/3/9 14:51
 * @email 294663966@qq.com
 * 我的订单
 */
public class OrderListActivity extends BaseActivity{

    //没有数据时显示
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.iv_empty)
    ImageView iv_empty;
    @BindView(R.id.tv_shili)
    TextView tv_shili;
    @BindView(R.id.textTip)
    TextView textTip;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;
    private OrderListAdapter orderListAdapter;
    private ArrayList<Order> myOrders = new ArrayList<>();
    private ProgressUtils progressUtils;
    private int curPage = 1;
    private String keyword;

    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_search;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        BGARefreshLayoutUtils.initRefreshLayout(this, mBGARefreshLayout);
        et_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }
            @Override
            public void afterTextChanged(Editable et) {
                String keyword = et.toString();
                if (keyword.length() > 0) {
                    iv_empty.setVisibility(View.VISIBLE);
                } else {
                    iv_empty.setVisibility(View.GONE);
                    tv_shili.setVisibility(View.VISIBLE);
                    myOrders.clear();
                    orderListAdapter.replaceAll(myOrders);
                }
            }
        });

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) et_search.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(OrderListActivity.this
                                            .getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);

                    // 跳转activity
                    keyword = et_search.getText().toString();
                    if (!TextUtils.isEmpty(keyword)) {
                        curPage = 1;
                        searchOrderList();
                    }
                    return true;
                }
                return false;
            }
        });

        mBGARefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                curPage++;
                searchOrderList();
                return true;
            }
        });
    }

    @Override
    protected void init() {
        progressUtils = new ProgressUtils();
    }

    @Override
    protected void initGetData() {
    }

    @Override
    protected void widgetListener() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderListAdapter = new OrderListAdapter(this, myOrders, R.layout.item_order);

        orderListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {

                Bundle bundle = new Bundle();
                bundle.putString("orderId", myOrders.get(position).getOrderId());
                IntentUtil.gotoActivity(OrderListActivity.this, OrderDetailActivity.class, bundle);
            }
        });

        recyclerView.setAdapter(orderListAdapter);
    }

    @OnClick({R.id.iv_empty, R.id.tv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_empty:
                et_search.setText("");
                tv_shili.setVisibility(View.VISIBLE);
                myOrders.clear();
                orderListAdapter.replaceAll(myOrders);
                break;
            case R.id.tv_search:
                keyword = et_search.getText().toString().trim();
                if (TextUtils.isEmpty(keyword)) {
                    ToastUtil.show(this, "请输入要搜索的订单内容");
                    return;
                }
                curPage=1;
                searchOrderList();
                break;
        }
    }

    /**
     * 搜索订单
     */
    private void searchOrderList() {
        if (Utils.isFastClick(1500)){
            if (curPage > 1){
                curPage--;
            }
            return;
        }
        tv_shili.setVisibility(View.GONE);
        progressUtils.showCustomProgressDialog(this);
        Map<String, String> map = new HashMap<>();
        map.put("pageSize", "20");
        map.put("curPage", curPage+"");
        map.put("keyword", keyword);
        map.put("type", "2");
        HttpRequestUtils.httpRequest(this, "searchOrderList", RequestValue.MY_ORDERS, map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        String json = JsonUtil.getGsonValueByKey(response, "data");
                        ArrayList<Order> orderList = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(json, "data"), new TypeToken<ArrayList<Order>>() {
                        });
                        if (orderList != null && orderList.size() > 0) {
                            if (curPage > 1 && myOrders != null) {
                                myOrders.addAll(orderList);
                            } else {
                                myOrders = orderList;
                            }
                        } else {
                            if (curPage == 1){
                                myOrders = new ArrayList<>();
                            } else {
                                curPage--;
                                com.lwc.shanxiu.utils.ToastUtil.showLongToast(OrderListActivity.this, "暂无更多订单");
                            }
                        }
                        orderListAdapter.replaceAll(myOrders);
                        if (myOrders != null && myOrders.size() > 0) {
                            textTip.setVisibility(View.GONE);
                        } else {
                            textTip.setVisibility(View.VISIBLE);
                        }
                        break;
                    default:
                        textTip.setVisibility(View.VISIBLE);
                        com.lwc.shanxiu.utils.ToastUtil.showLongToast(OrderListActivity.this, common.getInfo());
                        break;
                }
                progressUtils.dismissCustomProgressDialog();
                BGARefreshLayoutUtils.endLoadingMore(mBGARefreshLayout);
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError("searchOrderList  " + e.toString());
                BGARefreshLayoutUtils.endLoadingMore(mBGARefreshLayout);
                progressUtils.dismissCustomProgressDialog();
                if (!TextUtils.isEmpty(msg))
                    com.lwc.shanxiu.utils.ToastUtil.showLongToast(OrderListActivity.this, msg);
            }
        });
    }
}

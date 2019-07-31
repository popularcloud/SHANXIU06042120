package com.lwc.shanxiu.module.order.presenter;

import android.app.Activity;
import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.bean.Order;
import com.lwc.shanxiu.module.order.model.IOrderListModel;
import com.lwc.shanxiu.module.order.model.implement.OrderListModel;
import com.lwc.shanxiu.module.order.ui.IOrderListFragmentView;
import com.lwc.shanxiu.utils.BGARefreshLayoutUtils;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * @author 何栋
 * @version 1.0
 * @date 2017/3/9 17:01
 * @email 294663966@qq.com
 * 我的订单列表 3个fragment的控制层
 */
public class OrderListPresenter {

    private final String TAG = "OrderListPresenter";

    private IOrderListFragmentView iOrderListView;
    private IOrderListModel iOrderListModel;
    private BGARefreshLayout mBGARefreshLayout;
    private Context context;

    public OrderListPresenter(Context context, IOrderListFragmentView iOrderListView) {
        this.iOrderListView = iOrderListView;
        this.context = context;
        iOrderListModel = new OrderListModel();

        mBGARefreshLayout = iOrderListView.getBGARefreshLayout();
    }

    /**
     * 获取订单列表
     *
     * @param pageNow
     * @param type (1:进行中 2:完成)
     */
    public void getOrders(int pageNow, int type) {
        Map<String, String> map = new HashMap<>();
        map.put("type", ""+type);
        map.put("curPage", ""+pageNow);
        HttpRequestUtils.httpRequest((Activity) context, "getOrderList", RequestValue.MY_ORDERS, map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                LLog.iNetSucceed(TAG, response);
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        String json = JsonUtil.getGsonValueByKey(response, "data");
                        List<Order> myOrders = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(json, "data"), new TypeToken<ArrayList<Order>>() {
                        });
                        iOrderListView.notifyData(myOrders);
                        break;
                    default:
                        ToastUtil.showToast(context, common.getInfo());
                        break;
                }
                BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(TAG, e.toString());
                BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);
            }
        });
    }

    /**
     * 加载更多订单
     *
     * @param pageNow
     * @param type
     */
    public void loadOrders(int pageNow, int type) {
        Map<String, String> map = new HashMap<>();
        map.put("type", ""+type);
        map.put("curPage", ""+pageNow);
        HttpRequestUtils.httpRequest((Activity) context, "getOrderList", RequestValue.MY_ORDERS, map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                LLog.iNetSucceed(TAG, response);
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        String json = JsonUtil.getGsonValueByKey(response, "data");
                        List<Order> myOrders = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(json, "data"), new TypeToken<ArrayList<Order>>() {
                        });
                        LLog.i(TAG, myOrders.toString());
                        iOrderListView.addData(myOrders);
                        break;
                    default:
                        ToastUtil.showToast(context, common.getInfo());
                        break;
                }
                BGARefreshLayoutUtils.endLoadingMore(mBGARefreshLayout);
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(TAG, e.toString());
                ToastUtil.showToast(context, msg);
                BGARefreshLayoutUtils.endLoadingMore(mBGARefreshLayout);
            }
        });
    }
}

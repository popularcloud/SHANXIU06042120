package com.lwc.shanxiu.module.order.presenter;

import android.app.Activity;
import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.AfterService;
import com.lwc.shanxiu.module.order.ui.IDeviceDetailFragmentView;
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
 * @date 2017/3/15 09:01
 * @email 294663966@qq.com
 * 设备详情
 */
public class DeviceDetailPresenter {

    private final String TAG = "DeviceDetailPresenter";
    private IDeviceDetailFragmentView iDeviceDetailFragmentView;
    private Context context;

    public DeviceDetailPresenter(Context context, IDeviceDetailFragmentView iDeviceDetailFragmentView) {
        this.iDeviceDetailFragmentView = iDeviceDetailFragmentView;
        this.context = context;
    }

    /**
     * 获取售后订单信息
     * @param oid
     */
    public void getOrderInfor(String oid, final BGARefreshLayout mBGARefreshLayout) {

        Map<String, String> map = new HashMap<>();
        map.put("type", "2");
        HttpRequestUtils.httpRequest((Activity) context, "getOrderState", RequestValue.ORDER_STATE+oid, map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        List<AfterService> myOrders = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<AfterService>>() {
                        });
                        iDeviceDetailFragmentView.setDeviceDetailInfor(myOrders);
                        break;
                    default:
                        ToastUtil.showLongToast(context, common.getInfo());
                        break;
                }
                BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(TAG, e.toString());
                ToastUtil.showToast(context, msg);
                BGARefreshLayoutUtils.endRefreshing(mBGARefreshLayout);
            }
        });
    }

    /**
     * 更新售后订单信息
     * @param oid
     */
    public void updateOrderInfor(final String oid, int type, final BGARefreshLayout mBGARefreshLayout) {
        if (Utils.isFastClick(1000)) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("orderId", oid);
        String url = "";
        if (type == AfterService.STATUS_CHULI) {
            url = RequestValue.DISPOSE_AFTER_ORDER;
        } else if (type == AfterService.STATUS_YIJIESHOU) {
            url = RequestValue.UPDATE_AFTER_ORDER;
        } else if (type == AfterService.STATUS_WANGCHENGDAIQUEREN) {
            url = RequestValue.FINISH_AFTER_ORDER;
        }
        HttpRequestUtils.httpRequest((Activity) context, "updateAfterOrderStatus", url, map, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                LLog.iNetSucceed(TAG, response);
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        getOrderInfor(oid, mBGARefreshLayout);
                        break;
                    default:
                        ToastUtil.showLongToast(context, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(TAG, e.toString());
            }
        });
    }
}

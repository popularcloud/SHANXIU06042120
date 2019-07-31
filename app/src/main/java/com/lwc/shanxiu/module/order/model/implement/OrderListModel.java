package com.lwc.shanxiu.module.order.model.implement;

import com.lwc.shanxiu.controler.http.NetManager;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.order.model.IOrderListModel;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * @author 何栋
 * @version 1.0
 * @date 2017/3/9 09:16
 * @email 294663966@qq.com
 * 我的订单
 */
public class OrderListModel implements IOrderListModel {

    @Override
    public void getOrderInfor(String userId, String pageNow, String pageSize, String type, StringCallback callback) {
        OkHttpUtils.post().url(NetManager.getUrl(RequestValue.MY_ORDERS)).addParams("touid", userId).addParams("pageNow", pageNow)
                .addParams("pageSize", pageSize).addParams("t", type).build().execute(callback);
    }
}

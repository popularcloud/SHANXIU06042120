package com.lwc.shanxiu.module.order.model;

import com.zhy.http.okhttp.callback.StringCallback;

/**
 * @author 何栋
 * @version 1.0
 * @date 2017/3/15 08:14
 * @email 294663966@qq.com
 * 订单详情
 */
public interface IOrderDetailFramentModel {

    /**
     * 获取某个订单信息
     * @param userId
     * @param oid    订单id
     * @param callback
     */
    void getOrderInfor(String userId, String oid, StringCallback callback);
}

package com.lwc.shanxiu.module.order.model;

import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by Administrator-pc on 2017/3/9.
 */

public interface IOrderListModel {


    /**
     * 获取我的订单信息
     * @param userId
     * @param pageNow
     * @param pageSize
     * @param type  类型 1.已发布，2.未完成，3.已完成
     * @param callback
     */
    void getOrderInfor(String userId, String pageNow, String pageSize, String type, StringCallback callback);
}

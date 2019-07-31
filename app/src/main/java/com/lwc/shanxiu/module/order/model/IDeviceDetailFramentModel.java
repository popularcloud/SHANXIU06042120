package com.lwc.shanxiu.module.order.model;

import com.zhy.http.okhttp.callback.StringCallback;

/**
 * @author 何栋
 * @version 1.0
 * @date 2017/3/15 08:12
 * @email 294663966@qq.com
 * 设备详情
 */
public interface IDeviceDetailFramentModel {

    /**
     * 获取设备详情
     *
     * @param did      设备 id
     * @param udid     用户设备id
     * @param uid
     * @param callback
     */
    void getDeviceDetail(String did, String udid, String uid, StringCallback callback);

    /**
     * 获取某个订单信息
     * @param userId
     * @param oid    订单id
     * @param callback
     */
    void getOrderInfor(String userId, String oid, StringCallback callback);
}

package com.lwc.shanxiu.module.order.model;

import com.zhy.http.okhttp.callback.StringCallback;

/**
 * @author 何栋
 * @version 1.0
 * @date 2017/3/15 08:13
 * @email 294663966@qq.com
 * 订单状态
 */
public interface IOrderStateFramentModel {

    /**
     * 获取某个订单的状态
     * @param userId
     * @param oid    订单id
     * @param callback
     */
    void getOrderState(String userId, String oid, StringCallback callback);

    /**
     * 更新订单
     * @param uid
     * @param oid
     * @param ordertype 1待接单，2已接单，3处理中，4.已分级待确认，5.待对方确认，6.已报价待确认，7.已挂起，
     *                      8.已完成待确认，9.用户拒绝并返修，10.拒绝返修，11.已完成，12.已评价，20.已取消
     * @param callback
     */
    void upDataOrder(String uid, String oid, String ordertype, StringCallback callback);

    /**
     * 重置订单信息
     *
     * @param uid
     * @param oId
     */
    void resetOrder(final String uid, final String oId, StringCallback callback);
    /**
     * 更新订单
     * @param uid
     * @param oid
     * @param ordertype 1待接单，2已接单，3处理中，4.已分级待确认，5.待对方确认，6.已报价待确认，7.已挂起，
     *                      8.已完成待确认，9.待返修，10.拒绝返修，11.已完成，12.已评价，20.已取消
     * @param msg        操作订单的理由
     * @param callback
     */
    void upDataOrder(String uid, String oid, String ordertype, String msg, StringCallback callback);

    /**
     * 更新订单
     * @param uid
     * @param oid
     * @param ordertype 1待接单，2已接单，3处理中，4.已分级待确认，5.待对方确认，6.已报价待确认，7.已挂起，
     *                      8.已完成待确认，9.待返修，10.拒绝返修，11.已完成，12.已评价，20.已取消
     * @param msg        操作订单的理由
     * @param callback
     */
    void upDataOrder(String uid, String oid, String ordertype, String msg,String time, StringCallback callback);

    /**
     * 投诉工程师接口
     * @param uid
     * @param msg
     * @param callback
     */
    void complaint(String uid, String msg, StringCallback callback);
}

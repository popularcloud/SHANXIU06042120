package com.lwc.shanxiu.module.order.model.implement;

import com.lwc.shanxiu.controler.http.NetManager;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.order.model.IOrderStateFramentModel;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * @author 何栋
 * @version 1.0
 * @date 2017/3/15 08:16
 * @email 294663966@qq.com
 */
public class OrderStateFramentModel implements IOrderStateFramentModel {
    @Override
    public void getOrderState(String userId, String oid, StringCallback callback) {
        OkHttpUtils.post().url(NetManager.getUrl(RequestValue.ORDER_STATE)).addParams("touid", userId).addParams("oid", oid)
                .build().execute(callback);
    }

    @Override
    public void upDataOrder(String uid, String oid, String ordertype, StringCallback callback) {
        OkHttpUtils.post().url(NetManager.getUrl(RequestValue.UPDATE_ORDER)).addParams("uid", uid).addParams("oid", oid).addParams("ordertype", ordertype)
                .build().execute(callback);
    }

    @Override
    public void resetOrder(String uid, String oId, StringCallback callback) {
        OkHttpUtils.post().url(NetManager.getUrl(RequestValue.RESET_ORDER)).addParams("uid", uid).addParams("oid", oId)
                .build().execute(callback);
    }

    @Override
    public void upDataOrder(String uid, String oid, String ordertype, String msg,String warrantytime , StringCallback callback) {
        OkHttpUtils.post().url(NetManager.getUrl(RequestValue.UPDATE_ORDER)).addParams("uid", uid).addParams("oid", oid).addParams("ordertype", ordertype)
                .addParams("warrantytime",warrantytime )
                .addParams("msg", msg).build().execute(callback);
    }

    @Override
    public void upDataOrder(String uid, String oid, String ordertype, String msg, StringCallback callback) {
        OkHttpUtils.post().url(NetManager.getUrl(RequestValue.UPDATE_ORDER)).addParams("uid", uid).addParams("oid", oid).addParams("ordertype", ordertype)
                .addParams("msg", msg).build().execute(callback);
    }

    @Override
    public void complaint(String uid, String msg, StringCallback callback) {
        OkHttpUtils.post().url(NetManager.getUrl(RequestValue.COMPLAINT)).addParams("uid", uid).addParams("msg", msg).build().execute(callback);
    }
}

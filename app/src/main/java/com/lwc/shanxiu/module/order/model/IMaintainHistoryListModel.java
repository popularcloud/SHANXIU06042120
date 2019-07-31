package com.lwc.shanxiu.module.order.model;

import com.zhy.http.okhttp.callback.StringCallback;

/**
 * @author 何栋
 * @version 1.0
 * @date 2017/3/23 16:59
 * @email 294663966@qq.com
 * 维修历史
 */
public interface IMaintainHistoryListModel {


    /**
     * 获取维修历史列表
     * @param udid
     * @param pageNow
     * @param pageSize
     * @param callback
     */
    void getMaintainHistoryList(String udid, String pageNow, String pageSize, StringCallback callback);
}

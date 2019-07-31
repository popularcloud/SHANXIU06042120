package com.lwc.shanxiu.module.order.ui;


import com.lwc.shanxiu.module.bean.Order;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by 何栋 on 2017/3/9.
 */

public interface IOrderListFragmentView {


    void notifyData(List<Order> myOrders);

    void addData(List<Order> myOrders);

    /**
     * 获取刷新控件实例
     */
    BGARefreshLayout getBGARefreshLayout();
}

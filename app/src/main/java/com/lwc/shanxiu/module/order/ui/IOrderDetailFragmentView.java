package com.lwc.shanxiu.module.order.ui;


import com.lwc.shanxiu.module.bean.Order;

/**
 * @author 何栋
 * @version 1.0
 * @date 2017/3/15 09:20
 * @email 294663966@qq.com
 * 订单详情
 */
public interface IOrderDetailFragmentView {

    /**
     * 设置设备详情信息
     *
     * @param myOrder
     */
    void setDeviceDetailInfor(Order myOrder);
}

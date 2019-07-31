package com.lwc.shanxiu.module.order.ui;


import com.lwc.shanxiu.module.bean.AfterService;

import java.util.List;

/**
 * @author 何栋
 * @version 1.0
 * @date 2017/3/15 09:17
 * @email 294663966@qq.com
 * 设备详情
 */
public interface IDeviceDetailFragmentView {

    /**
     * 设置设备详情信息
     *
     * @param myOrder
     */
    void setDeviceDetailInfor(List<AfterService> myOrder);
}

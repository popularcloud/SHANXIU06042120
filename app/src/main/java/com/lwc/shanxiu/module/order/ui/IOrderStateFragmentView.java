package com.lwc.shanxiu.module.order.ui;


import com.lwc.shanxiu.module.bean.OrderState;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * @author 何栋
 * @version 1.0
 * @date 2017/3/15 09:20
 * @email 294663966@qq.com
 * 订单状态
 */
public interface IOrderStateFragmentView {

    void notifyData(List<OrderState> orderStates);

    /**
     * 设置显示状态的按钮文字
     * @param state
     */
    void setStateBtnText(String stateS,int state);

    void setOrderStates();

    BGARefreshLayout getBGARefreshLayout();

    /**
     * 切换界面底部两个布局操作的逻辑
     * @param isShowBototnDoubleButton ture 显示双按钮   flase  显示单按钮
     */
    void cutBottomButton(boolean isShowBototnDoubleButton,int state);

    /**
     * 设置FinishLayout 布局文字
     * @param leftName  左边文字
     * @param rightName 右边文字
     */
    void setFinishLayoutBtnName(String leftName, String rightName,int state);

    /**
     * 设置底部颜色
     * @param colour
     */
    void setBottomButtonColour(int colour,int state);
}

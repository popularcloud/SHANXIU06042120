package com.lwc.shanxiu.module.order.presenter;

import android.content.Context;
import android.graphics.Color;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.module.bean.OrderState;
import com.lwc.shanxiu.module.order.model.IOrderStateFramentModel;
import com.lwc.shanxiu.module.order.model.implement.OrderStateFramentModel;
import com.lwc.shanxiu.module.order.ui.IMaintainOrderStateView;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.ToastUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import okhttp3.Call;

/**
 * 创建于 2017/4/29.
 * 作者： 何栋
 * 邮箱： 294663966@qq.com
 */
public class MaintainOrderStatePresent {

    private final String TAG = "OrderStatePresenter";
    private IMaintainOrderStateView iMaintainOrderStateView;
    private IOrderStateFramentModel iOrderStateFramentModel;
    private BGARefreshLayout mBGARefreshLayout;
    private Context context;

    public MaintainOrderStatePresent(Context context, IMaintainOrderStateView iMaintainOrderStateView) {
        this.iMaintainOrderStateView = iMaintainOrderStateView;
        this.context = context;
        iOrderStateFramentModel = new OrderStateFramentModel();
        mBGARefreshLayout = iMaintainOrderStateView.getBGARefreshLayout();
    }

    /**
     * 获取订单状态
     *
     * @param userId
     * @param oid
     */
    public void getOrderState(String userId, String oid) {

        iOrderStateFramentModel.getOrderState(userId, oid, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LLog.eNetError(TAG, e.toString());
                ToastUtil.showLongToast(context, e.toString());
                mBGARefreshLayout.endRefreshing();
            }

            @Override
            public void onResponse(String response, int id) {
                LLog.iNetSucceed(TAG, response);
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {

                    case "10000":
                        List<OrderState> orderStates = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<OrderState>>() {
                        });
                        iMaintainOrderStateView.notifyData(orderStates);
                        if (orderStates != null && orderStates.size() > 0) {
                            int lastSize = orderStates.size() - 1;
                            String state = orderStates.get(lastSize).getProcessTitle();  //订单状态，也就是标题


                            switch (state) {
                                //订单在这三种状态，设置状态按钮文字为取消订单，背景颜色为红色
                                case "订单已提交":
                                    iMaintainOrderStateView.cutBottomButton(false);
                                    iMaintainOrderStateView.setStateBtnText("取消");
                                    iMaintainOrderStateView.setBottomButtonColour(Color.parseColor("#ff0000"));
                                    break;
                                case "维修员已接单":
                                    iMaintainOrderStateView.cutBottomButton(false);
                                    iMaintainOrderStateView.setStateBtnText("取消 ");
                                    iMaintainOrderStateView.setBottomButtonColour(Color.parseColor("#ff0000"));
                                    break;
                                case "维修员已完成":

                                    iMaintainOrderStateView.cutBottomButton(true);
                                    iMaintainOrderStateView.setFinishLayoutBtnName("拒绝并返修", "接受");
                                    break;
                                case "用户确认完成":
                                case "已完成":
                                    //显示单按钮布局
                                    iMaintainOrderStateView.cutBottomButton(false);
                                    iMaintainOrderStateView.setStateBtnText("评价");
                                    iMaintainOrderStateView.setBottomButtonColour(Color.parseColor("#2e344e"));
                                    break;

                                case "用户拒绝返修":
                                    iMaintainOrderStateView.cutBottomButton(false);
                                    iMaintainOrderStateView.setStateBtnText("待返修");
                                    iMaintainOrderStateView.setBottomButtonColour(Color.parseColor("#2e344e"));

                                    break;
                                case "维修员分级处理":
                                    iMaintainOrderStateView.cutBottomButton(true);
                                    iMaintainOrderStateView.setFinishLayoutBtnName("拒绝分级", "同意分级");
                                    break;

                                case "维修员拒绝返修":
                                    iMaintainOrderStateView.cutBottomButton(false);
                                    iMaintainOrderStateView.setBottomButtonColour(Color.parseColor("#fa2222"));
                                    iMaintainOrderStateView.setStateBtnText("投诉");
                                    break;

                                case "维修员提交报价":
                                    iMaintainOrderStateView.cutBottomButton(true);
                                    iMaintainOrderStateView.setFinishLayoutBtnName("拒绝", "确认");
                                    break;
                                default:
                                    //默认按钮只显示状态
                                    iMaintainOrderStateView.cutBottomButton(false);
                                    iMaintainOrderStateView.setStateBtnText(state);
                                    iMaintainOrderStateView.setBottomButtonColour(Color.parseColor("#9f9f9f"));
                                    break;
                            }
                        }
                        break;
                    case "9999":
                        ToastUtil.showLongToast(context, common.getInfo());
                        break;
                }

                mBGARefreshLayout.endRefreshing();
            }
        });
    }


    /**
     * 更新订单信息
     *
     * @param uid
     * @param oId
     * @param orderType
     */
    public void upDataOrder(final String uid, final String oId, String orderType) {

        iOrderStateFramentModel.upDataOrder(uid, oId, orderType, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LLog.eNetError(TAG, e.toString());

            }

            @Override
            public void onResponse(String response, int id) {

                LLog.iNetSucceed(TAG, response);
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {

                    case "9999":
                        ToastUtil.showLongToast(context, common.getInfo());
                        break;

                    case "10000":
                        getOrderState(uid, oId);
                        break;
                }


            }
        });
    }

    /**
     * 更新订单信息
     *
     * @param uid
     * @param oId
     * @param orderType
     * @param msg       更新数据理由
     */
    public void upDataOrder(final String uid, final String oId, String msg, String orderType) {

        iOrderStateFramentModel.upDataOrder(uid, oId, orderType, msg, new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LLog.eNetError(TAG, e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LLog.iNetSucceed(TAG, response);
                        Common common = JsonUtil.parserGsonToObject(response, Common.class);
                        switch (common.getStatus()) {

                            case "9999":
                                break;

                            case "10000":
                                getOrderState(uid, oId);
                                break;
                        }
                    }
                }
        );
    }

    /**
     * 投诉
     *
     * @param uId
     * @param msg
     */
    public void complaint(String uId, String msg) {

        iOrderStateFramentModel.complaint(uId, msg, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {

                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {

                    case "10000":
                        ToastUtil.showToast(context, "投诉成功");
                        break;
                    default:
                        ToastUtil.showLongToast(context, common.getInfo());
                        break;
                }
            }
        });
    }
}

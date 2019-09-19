package com.lwc.shanxiu.module.order.presenter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.Order;
import com.lwc.shanxiu.module.bean.OrderState;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.module.order.model.IOrderStateFramentModel;
import com.lwc.shanxiu.module.order.model.implement.OrderStateFramentModel;
import com.lwc.shanxiu.module.order.ui.IOrderStateFragmentView;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import okhttp3.Call;

/**
 * @author 何栋
 * @version 1.0
 * @date 2017/3/15 09:04
 * @email 294663966@qq.com
 * 订单状态
 */
public class OrderStatePresenter {
    private final String TAG = "OrderStatePresenter";
    private IOrderStateFragmentView iOrderStateFragmentView;
    private IOrderStateFramentModel iOrderStateFramentModel;
    private BGARefreshLayout mBGARefreshLayout;
    private Context context;

    public OrderStatePresenter(Context context, IOrderStateFragmentView iOrderStateFragmentView) {
        this.iOrderStateFragmentView = iOrderStateFragmentView;
        this.context = context;
        iOrderStateFramentModel = new OrderStateFramentModel();
        mBGARefreshLayout = iOrderStateFragmentView.getBGARefreshLayout();
    }

    /**
     * 获取订单状态
     *
     * @param oid
     */
    public void getOrderState(String oid) {
        Map<String, String> map = new HashMap<>();
        map.put("type", "1");
        HttpRequestUtils.httpRequest((Activity) context, "getOrderState", RequestValue.ORDER_STATE+oid, map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        List<OrderState> orderStates = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<OrderState>>() {
                        });
                        iOrderStateFragmentView.notifyData(orderStates);
                        if (orderStates != null && orderStates.size() > 0) {
                            int lastSize = orderStates.size() - 1;
                            OrderState orderState = orderStates.get(lastSize);
                            String lastTitle = orderState.getProcessTitle();
                            int state = orderState.getStatusId();  //订单状态，也就是标题
                            switch (state) {
                                //订单在这三种状态，设置状态按钮文字为取消订单，背景颜色为红色
                                case Order.STATUS_DAIJIEDAN:
                                    iOrderStateFragmentView.cutBottomButton(false, state);
                                    iOrderStateFragmentView.setStateBtnText("接单", state);
                                    iOrderStateFragmentView.setBottomButtonColour(Color.parseColor("#2e344e"), state);
                                    break;
                                case Order.STATUS_DAODAXIANCHANG:
//                                    iOrderStateFragmentView.cutBottomButton(false, state);
//                                    iOrderStateFragmentView.setStateBtnText("填写检测报告", state);
//                                    iOrderStateFragmentView.setBottomButtonColour(Color.parseColor("#2e344e"), state);
                                    iOrderStateFragmentView.cutBottomButton(true, state);
                                    iOrderStateFragmentView.setFinishLayoutBtnName("返厂维修", "填写检测报告", state);
                                    break;
                                case Order.STATUS_FANCHANGGUAQI:
                                    iOrderStateFragmentView.cutBottomButton(false, state);
                                    iOrderStateFragmentView.setStateBtnText("填写检测报告", state);
                                    iOrderStateFragmentView.setBottomButtonColour(Color.parseColor("#2e344e"), state);
                                    break;
                                case Order.STATUS_JIANCEBAOJIA:
                                    if (!orderState.getOrderType().equals("3")) {
                                        iOrderStateFragmentView.cutBottomButton(false, state);
                                        iOrderStateFragmentView.setStateBtnText("等待用户确认报价", state);
                                        iOrderStateFragmentView.setBottomButtonColour(Color.parseColor("#9f9f9f"), state);
                                    } else {
                                        iOrderStateFragmentView.cutBottomButton(false, state);
                                        iOrderStateFragmentView.setStateBtnText("返厂维修", state);
                                        iOrderStateFragmentView.setBottomButtonColour(Color.parseColor("#2e344e"), state);
                                    }
                                    break;
                                case Order.STATUS_QUERENBAOJIA:
//                                    iOrderStateFragmentView.cutBottomButton(false, state);
//                                    iOrderStateFragmentView.setStateBtnText("开始处理", state);
//                                    iOrderStateFragmentView.setBottomButtonColour(Color.parseColor("#2e344e"), state);
                                    iOrderStateFragmentView.cutBottomButton(true, state);
                                    iOrderStateFragmentView.setFinishLayoutBtnName("订单挂起", "开始处理", state);
                                    break;
                                case Order.STATUS_JIEDAN:
                                    String str = "开始处理";
                                    if (orderState.getOrderType() != null && !orderState.getOrderType().equals("2")) {
                                        str = "到达现场";
                                    }
                                    iOrderStateFragmentView.cutBottomButton(false, state);
                                    iOrderStateFragmentView.setStateBtnText(str, state);
                                    iOrderStateFragmentView.setBottomButtonColour(Color.parseColor("#2e344e"), state);
                                    break;

                                case Order.STATUS_CHULI:
                                        String str2 = "挂起";
                                        if (orderState.getOrderType().equals("1")) {
                                            str2 = "返厂维修";
                                            iOrderStateFragmentView.cutBottomButton(false, state);
                                            iOrderStateFragmentView.setStateBtnText("已完成", state);
                                        } else if (orderState.getOrderType().equals("2")) {
                                            str2 = "挂起";
                                            iOrderStateFragmentView.cutBottomButton(true, state);
                                            iOrderStateFragmentView.setFinishLayoutBtnName(str2, "已完成", state);
                                        }else if(orderState.getOrderType().equals("3")){
                                            iOrderStateFragmentView.cutBottomButton(false, state);
                                            iOrderStateFragmentView.setStateBtnText("已完成", state);
                                            iOrderStateFragmentView.setBottomButtonColour(Color.parseColor("#2e344e"), state);
                                        }
                                    break;
                                case Order.STATUS_ZHUANDAN:
                                    iOrderStateFragmentView.cutBottomButton(false, state);
                                    iOrderStateFragmentView.setStateBtnText("确认接单", state);
                                    iOrderStateFragmentView.setBottomButtonColour(Color.parseColor("#2e344e"), state);
                                    break;
                                case Order.STATUS_BAOJIA:
                                    //默认按钮只显示状态
                                    iOrderStateFragmentView.cutBottomButton(false, state);
                                    iOrderStateFragmentView.setStateBtnText("已报价待确认", state);
                                    iOrderStateFragmentView.setBottomButtonColour(Color.parseColor("#9f9f9f"), state);
                                    break;
                                case Order.STATUS_YIWANCHENGDAIQUEREN:
                                    iOrderStateFragmentView.cutBottomButton(false, state);
                                    iOrderStateFragmentView.setStateBtnText("已维修完成待确认", state);
                                    iOrderStateFragmentView.setBottomButtonColour(Color.parseColor("#9f9f9f"), state);
                                    break;
                                case Order.STATUS_YIWANCHENG:
                                    iOrderStateFragmentView.cutBottomButton(false, state);
                                    iOrderStateFragmentView.setStateBtnText("订单已完成", state);
                                    iOrderStateFragmentView.setBottomButtonColour(Color.parseColor("#9f9f9f"), state);
                                    break;
                                case Order.STATUS_GUAQI:
                                    if (!orderState.getOrderType().equals("3")) {
                                        iOrderStateFragmentView.cutBottomButton(false, state);
                                        iOrderStateFragmentView.setStateBtnText("继续处理", state);
                                        iOrderStateFragmentView.setBottomButtonColour(Color.parseColor("#2e344e"), state);
                                    } else {
                                        iOrderStateFragmentView.cutBottomButton(true, state);
                                        iOrderStateFragmentView.setFinishLayoutBtnName("转单", "继续处理", state);
                                    }
                                    break;
                                case Order.STATUS_FANCHANGZHONG:
                                    iOrderStateFragmentView.cutBottomButton(false, state);
                                    iOrderStateFragmentView.setStateBtnText("送回安装", state);
                                    iOrderStateFragmentView.setBottomButtonColour(Color.parseColor("#2e344e"), state);
                                    break;
                                case Order.STATUS_DAIFANXIU:
                                    iOrderStateFragmentView.cutBottomButton(true, state);
                                    iOrderStateFragmentView.setFinishLayoutBtnName("同意返修", "拒绝返修", state);
                                    break;
                                default:
                                    //默认按钮只显示状态
                                    iOrderStateFragmentView.cutBottomButton(false, state);
                                    iOrderStateFragmentView.setStateBtnText(lastTitle, state);
                                    iOrderStateFragmentView.setBottomButtonColour(Color.parseColor("#9f9f9f"), state);
                                    break;
                            }
                        }
                        break;
                    default:
                        ToastUtil.showLongToast(context, common.getInfo());
                        break;
                }
                mBGARefreshLayout.endRefreshing();
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(TAG, e.toString());
                ToastUtil.showToast(context, msg);
                mBGARefreshLayout.endRefreshing();
            }
        });
    }
    /**
     * 重置订单信息
     *
     * @param uid
     * @param oId
     */
    public void resetOrder(final String uid, final String oId) {

        iOrderStateFramentModel.resetOrder(uid, oId, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LLog.eNetError(TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {

                LLog.iNetSucceed(TAG, response);
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {

                    default:
                        ToastUtil.showLongToast(context, common.getInfo());
                        break;

                    case "10000":
                        getOrderState(oId);
                        break;
                }
            }
        });
    }

    /**
     * 更新订单信息
     *
     * @param oId
     * @param orderType
     */
    public void upDataOrder(final String oId, int orderType) {
        if (Utils.isFastClick(1000)) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("orderId", oId);
        String url = "";
        if (orderType == Order.STATUS_CHULI) {
            url = RequestValue.DISPOSE_ORDER;
        } else if (orderType == Order.STATUS_JIEDAN) {
            url = RequestValue.UPDATE_ORDER;
        } else if (orderType == Order.STATUS_YIWANCHENGDAIQUEREN) {
            url = RequestValue.FINISH_ORDER;
        } else if (orderType == Order.STATUS_TONGYIFENJI) {
            url = RequestValue.CHANGE_ORDER_QR;
        } else if (orderType == Order.STATUS_DAODAXIANCHANG) {
            url = RequestValue.POST_ARRIVE_SCENE;
            map.put("lat", String.valueOf(SharedPreferencesUtils.getParam(context,"presentLat","")));
            map.put("lon", String.valueOf(SharedPreferencesUtils.getParam(context,"presentLon","")));
        } else if (orderType == Order.STATUS_SONGHUIANZHUANG) {
            url =RequestValue.POST_ORDER_BACK;
        }
        HttpRequestUtils.httpRequest((Activity) context, "updateOrderStatus", url, map, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                LLog.iNetSucceed(TAG, response);
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        getOrderState(oId);
                        break;
                    default:
                        ToastUtil.showLongToast(context, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(TAG, e.toString());
            }
        });
    }

    public void upDataOrder(final String oId, int orderType,String lat,String lon) {
        if (Utils.isFastClick(1000)) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("orderId", oId);
        String url = "";
        if (orderType == Order.STATUS_CHULI) {
            url = RequestValue.DISPOSE_ORDER;
        } else if (orderType == Order.STATUS_JIEDAN) {
            url = RequestValue.UPDATE_ORDER;
        } else if (orderType == Order.STATUS_YIWANCHENGDAIQUEREN) {
            url = RequestValue.FINISH_ORDER;
        } else if (orderType == Order.STATUS_TONGYIFENJI) {
            url = RequestValue.CHANGE_ORDER_QR;
        } else if (orderType == Order.STATUS_DAODAXIANCHANG) {
            url = RequestValue.POST_ARRIVE_SCENE;
            map.put("lat",lat);
            map.put("lon",lon);
        } else if (orderType == Order.STATUS_SONGHUIANZHUANG) {
            url =RequestValue.POST_ORDER_BACK;
        }
        HttpRequestUtils.httpRequest((Activity) context, "updateOrderStatus", url, map, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                LLog.iNetSucceed(TAG, response);
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        getOrderState(oId);
                        break;
                    default:
                        ToastUtil.showLongToast(context, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(TAG, e.toString());
            }
        });
    }

    /**
     * 更新订单信息
     *
     * @param oId
     * @param msg       更新数据理由
     * @param warrantytime
     */
    public void upDataOrder(final String oId, String msg, String warrantytime) {
        Map<String, String> map = new HashMap<>();
        map.put("orderId", oId);
        map.put("remark", msg);
        map.put("warrantyTime", warrantytime);
        HttpRequestUtils.httpRequest((Activity) context, "updateOrderStatus", RequestValue.FINISH_ORDER, map, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                LLog.iNetSucceed(TAG, response);
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        getOrderState(oId);
                        break;
                    default:
                        ToastUtil.showLongToast(context, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showLongToast(context, msg);
                LLog.eNetError(TAG, e.toString());
            }
        });
    }
}

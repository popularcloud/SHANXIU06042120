package com.lwc.shanxiu.module.order.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.gyf.immersionbar.ImmersionBar;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.BaseFragment;
import com.lwc.shanxiu.module.bean.Order;
import com.lwc.shanxiu.module.bean.OrderState;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.module.order.presenter.OrderStatePresenter;
import com.lwc.shanxiu.module.order.ui.IOrderStateFragmentView;
import com.lwc.shanxiu.module.order.ui.activity.AccomplishActivity;
import com.lwc.shanxiu.module.order.ui.activity.EquipmentRepairActivity;
import com.lwc.shanxiu.module.order.ui.activity.FeeStandardActivity;
import com.lwc.shanxiu.module.order.ui.activity.OrderDetailActivity;
import com.lwc.shanxiu.module.order.ui.activity.QuoteAffirmActivity;
import com.lwc.shanxiu.module.order.ui.activity.RepairsListActivity;
import com.lwc.shanxiu.module.order.ui.activity.ToBillActivity;
import com.lwc.shanxiu.module.order.ui.adapter.OrderStateAdapter;
import com.lwc.shanxiu.utils.BGARefreshLayoutUtils;
import com.lwc.shanxiu.utils.DialogUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.widget.CustomDialog;
import com.lwc.shanxiu.widget.DialogStyle1;
import com.lwc.shanxiu.widget.PicturePopupWindow;
import com.yanzhenjie.sofia.Sofia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * @author 何栋
 * @version 1.0
 * @date 2017/3/13 17:38
 * @email 294663966@qq.com
 * 订单状态
 */
public class OrderStateFragment extends BaseFragment implements IOrderStateFragmentView ,AMapLocationListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    //订单状态
    @BindView(R.id.mBGARefreshLayout)
    BGARefreshLayout mBGARefreshLayout;
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.btnRepulse)
    Button btnRepulse;
    @BindView(R.id.btnAccept)
    Button btnAccept;
    @BindView(R.id.lLayoutDoubleButton)
    LinearLayout lLayoutDoubleButton;
    @BindView(R.id.lLayoutParent)
    LinearLayout lLayoutParent;

    private OrderStateAdapter adapter;
    private List<OrderState> orderStates;
    private Order myOrder = null;
    private OrderStatePresenter presenter;
    private SharedPreferencesUtils preferencesUtils;
    private User user;
    private PicturePopupWindow picturePopupWindow;
    private OrderState orderState;

    private Double lat;
    private Double lon;

    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_state, null);
        ButterKnife.bind(this, view);
        BGARefreshLayoutUtils.initRefreshLayout(getContext(), mBGARefreshLayout, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initEngines(view);
        getIntentData();
        init();
        bindRecycleView();
        setListener();

        ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true)
                .navigationBarColor(R.color.white).init();
    }

    @Override
    public void onResume() {
        super.onResume();
        mBGARefreshLayout.beginRefreshing();//执行加载数据
    }

    private void bindRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new OrderStateAdapter(getContext(), orderStates, R.layout.item_order_status);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void lazyLoad() {
    }

    @Override
    public void init() {
    }

    @Override
    public void initEngines(View view) {
        preferencesUtils = SharedPreferencesUtils.getInstance(getContext());
        presenter = new OrderStatePresenter(getContext(), this);

    }

    @Override
    public void getIntentData() {
        myOrder = (Order) getArguments().getSerializable("data");
        user = preferencesUtils.loadObjectData(User.class);
    }

    @Override
    public void setListener() {
        //刷新控件监听器
        mBGARefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                getData();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                return false;
            }
        });
    }

    /**
     * 请求网络获取数据
     */
    private void getData() {
        presenter.getOrderState(myOrder.getOrderId());
    }

    @Override
    public void notifyData(List<OrderState> orderStates) {
        this.orderStates = orderStates;
        adapter.replaceAll(orderStates);
        if (orderStates != null && orderStates.size() > 0) {
            int lastSize = orderStates.size() - 1;
            orderState = orderStates.get(lastSize);
            int state = orderState.getStatusId();
            try {
                if (OrderDetailActivity.activity != null &&state != Order.STATUS_YIQUXIAO && state != Order.STATUS_DAIJIEDAN && state != Order.STATUS_YIWANCHENG && state != Order.STATUS_YIPINGJIA) {
                    OrderDetailActivity.activity.setBohao(true);
                } else if (OrderDetailActivity.activity != null){
                    OrderDetailActivity.activity.setBohao(false);
                }
            } catch (Exception e){}
            recyclerView.scrollToPosition(orderStates.size()-1);
        }
    }

    @Override
    public void setStateBtnText(String stateS,int state) {
        invali(state);
        if (state != 4) {
            btn.setVisibility(View.VISIBLE);
            btn.setText(stateS);
        } else if (state == 4 && orderState.getWaitMaintenanceId() != null && user.getUserId().equals(orderState.getWaitMaintenanceId())) {
            btn.setVisibility(View.VISIBLE);
            btn.setText(stateS);
        } else {
            lLayoutDoubleButton.setVisibility(View.GONE);
            btn.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.btn, R.id.btnRepulse, R.id.btnAccept})
    public void onClick(View v) {

        switch (v.getId()) {
            //单按钮
            case R.id.btn:
                switch (btn.getText().toString()) {
                    case "接单":
                        if (myOrder.getOrderType() != null && myOrder.getOrderType().equals("1")) {
                            boolean isFindSf = preferencesUtils.loadBooleanData("isFindSf");
                            if (!isFindSf) {
                                DialogUtil.showUpdateAppDg(getActivity(), "温馨提示", "查看平台收费标准", "为了给用户良好的报修体验，工程师在接单之前，请先查看平台收费标准，工程师接单后，请按照平台制定的收费标准给用户进行报价，多谢合作！", new CustomDialog.OnClickListener() {
                                    @Override
                                    public void onClick(CustomDialog dialog, int id, Object object) {
                                        preferencesUtils.saveBooleanData("isFindSf", true);
                                        String deviceTypeModel = ((OrderDetailActivity)getActivity()).getDeviceModel();
                                /*        int typeId = 4;
                                        // 后台对应的收费标准  3 是办公设备  4是家用电器
                                        if("3".equals(deviceTypeModel)){
                                            typeId = 4;
                                        }
                                        if("1".equals(deviceTypeModel)){
                                            typeId = 3;
                                        }*/

                                /*        Bundle bundle = new Bundle();
                                        bundle.putString("url", ServerConfig.DOMAIN.replace("https", "http") + "/main/h5/charge.html?typeid="+typeId);
                                        bundle.putString("title", "收费标准");
                                        IntentUtil.gotoActivity(getActivity(), InformationDetailsActivity.class, bundle);*/


                                        Bundle bundle = new Bundle();
                                        bundle.putString("deviceTypeMold",deviceTypeModel);
                                        bundle.putString("title", "收费标准");
                                        IntentUtil.gotoActivity(getActivity(), FeeStandardActivity.class, bundle);

                                        dialog.dismiss();
                                    }
                                });
                            } else {
                                upDataOrder(Order.STATUS_JIEDAN);
                            }
                        } else {
                            upDataOrder(Order.STATUS_JIEDAN);
                        }
                        break;
                    case "到达现场":

                        //先获取工程师当前定位
                        startLocation();
                        break;
                    case "开始处理":
                        upDataOrder(Order.STATUS_CHULI);
                        break;
                    case "继续处理":
                        upDataOrder(Order.STATUS_CHULI);
                        break;
                    case "确认接单":
                        upDataOrder(Order.STATUS_TONGYIFENJI);
                        break;
                    case "送回安装":
                        if("2".equals(myOrder.getOrderType())){ //政府订单
                            upDataOrder(Order.STATUS_SONGHUIANZHUANG);
                        }else{
                            Bundle bundle = new Bundle();
                            if (myOrder.getHasAward() != 0)
                                bundle.putInt("hasAward", myOrder.getHasAward());
                            else if (orderState.getHasAward() != 0)
                                bundle.putInt("hasAward", orderState.getHasAward());
                            bundle.putString("orderId", myOrder.getOrderId());
                            if (!TextUtils.isEmpty(myOrder.getOrderType()))
                                bundle.putString("orderType", myOrder.getOrderType());
                            bundle.putInt("statusType", 1);
                            IntentUtil.gotoActivity(getActivity(), AccomplishActivity.class, bundle);
                        }
                        break;
                    case "返厂维修":
                        fanchang();
                        break;
                    case "已完成":
                        accomplish();
                        break;
                    case "填写检测报告":
                        String deviceTypeModel = ((OrderDetailActivity)getActivity()).getDeviceModel();
                        Bundle bundle2 = new Bundle();
                        bundle2.putSerializable("data", myOrder);
                        bundle2.putString("deviceTypeModel", deviceTypeModel);
                        IntentUtil.gotoActivity(getActivity(), QuoteAffirmActivity.class, bundle2);
                        break;
                }
                break;

            //双按钮
            //拒绝系列按钮
            case R.id.btnRepulse:

                switch (btnRepulse.getText().toString()) {
                    case "挂起":
                        initPop();
                        break;
                    case "订单挂起":
                        gq();
                        break;
                    case "返厂维修":
                        fanchang();
                        break;
                    case "同意返修":
                        upDataOrder(Order.STATUS_CHULI);
                        break;
                    case "转单":
                        publish();
                        break;
                }
                break;
            //同意系列按钮
            case R.id.btnAccept:
                switch (btnAccept.getText().toString()) {
                    case "已完成":
                        accomplish();
                        break;
                    case "拒绝返修":
                        upDataOrder(Order.STATUS_JUJUEFANXIU);
                        break;
                    case "继续处理":
                        upDataOrder(Order.STATUS_CHULI);
                        break;
                    case "开始处理":
                        upDataOrder(Order.STATUS_CHULI);
                        break;
                    case "填写检测报告":
                        String deviceTypeModel = ((OrderDetailActivity)getActivity()).getDeviceModel();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data", myOrder);
                        bundle.putString("deviceTypeModel", deviceTypeModel);
                        IntentUtil.gotoActivity(getActivity(), QuoteAffirmActivity.class, bundle);
                        break;
                }
                break;
        }
    }

    private void accomplish() {
        Bundle bundle = new Bundle();
        if (myOrder.getHasAward() != 0)
            bundle.putInt("hasAward", myOrder.getHasAward());
        else if (orderState.getHasAward() != 0)
            bundle.putInt("hasAward", orderState.getHasAward());
        if (!TextUtils.isEmpty(myOrder.getQrcodeIndex())) {
            bundle.putString("qrcodeIndex", myOrder.getQrcodeIndex());
        }
        bundle.putString("orderId", myOrder.getOrderId());
        if (!TextUtils.isEmpty(myOrder.getOrderType()))
            bundle.putString("orderType", myOrder.getOrderType());

        IntentUtil.gotoActivity(getActivity(), AccomplishActivity.class, bundle);
    }

    private void fanchang() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", myOrder);
        IntentUtil.gotoActivity(getActivity(), EquipmentRepairActivity.class, bundle);
    }

    /**
     * 更新订单接口
     *
     * @param state 状态
     */
    private void upDataOrder(int state) {
        presenter.upDataOrder(myOrder.getOrderId(), state);
    }

    @Override
    public void setOrderStates(){
        myOrder.setStatusId(2);
    }

    @Override
    public BGARefreshLayout getBGARefreshLayout() {
        return mBGARefreshLayout;
    }

    @Override
    public void cutBottomButton(boolean isShowFinishLayout,int state) {
        invali(state);
        if (state != 4) {
            if (isShowFinishLayout) {
                lLayoutDoubleButton.setVisibility(View.VISIBLE);
                btn.setVisibility(View.GONE);
            } else {
                lLayoutDoubleButton.setVisibility(View.GONE);
                btn.setVisibility(View.VISIBLE);
            }
        } else if (state == 4 && orderState != null && orderState.getWaitMaintenanceId() != null && user != null && user.getMaintenanceId().equals(orderState.getWaitMaintenanceId())) {
            if (isShowFinishLayout) {
                lLayoutDoubleButton.setVisibility(View.VISIBLE);
                btn.setVisibility(View.GONE);
            } else {
                lLayoutDoubleButton.setVisibility(View.GONE);
                btn.setVisibility(View.VISIBLE);
            }
        } else {
            lLayoutDoubleButton.setVisibility(View.GONE);
            btn.setVisibility(View.GONE);
        }
    }

    public void invali(int state) {
        if (state != 15 && state != 11 && state != 12 && state != 4 && state != 1 && orderState != null && user.getUserId()!= null && orderState.getMaintenanceId() != null && !user.getUserId().equals(orderState.getMaintenanceId())){
            ToastUtil.showLongToast(getActivity(), "该订单已由他工程师处理了！");
            try {
                getActivity().finish();
            } catch (Exception e){}
            return;
        }
    }

    @Override
    public void setFinishLayoutBtnName(String leftName, String rightName, int state) {
        invali(state);
        if (state != 4) {
            btnRepulse.setText(leftName);
            btnAccept.setText(rightName);
        } else if (state == 4 && orderState.getWaitMaintenanceId() != null && user.getUserId().equals(orderState.getWaitMaintenanceId())) {
            btnRepulse.setText(leftName);
            btnAccept.setText(rightName);
        } else {
            lLayoutDoubleButton.setVisibility(View.GONE);
            btn.setVisibility(View.GONE);
        }
    }

    @Override
    public void setBottomButtonColour(int colour, int state) {
        invali(state);
        if (state != 4) {
            if (colour == Color.parseColor("#9f9f9f")) {
                btn.setVisibility(View.GONE);
            }
        } else if (state == 4 && orderState.getWaitMaintenanceId() != null && user.getUserId().equals(orderState.getWaitMaintenanceId())) {
            if (colour == Color.parseColor("#9f9f9f")) {
                btn.setVisibility(View.GONE);
            }
        } else {
            lLayoutDoubleButton.setVisibility(View.GONE);
            btn.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化Popwindou
     */
    private void initPop() {
        picturePopupWindow = new PicturePopupWindow(getActivity(), new PicturePopupWindow.CallBack() {
            @Override
            public void twoClick() {
                Bundle bundle = new Bundle();
                bundle.putSerializable(getResources().getString(R.string.intent_key_data), myOrder);
                IntentUtil.gotoActivityForResult(getContext(), ToBillActivity.class, bundle, 6);
            }

            @Override
            public void oneClick() {
                publish();
            }

            @Override
            public void cancelCallback() {
            }

            @Override
            public void threeClick() {
                fanchang();
            }

            @Override
            public void fourClick() {
                gq();
            }
        }, "转单处理", "换件报价", "设备返修", "订单挂起");
        picturePopupWindow.showWindow();
    }

    public void publish() {
        Intent i = new Intent(getActivity(), RepairsListActivity.class);
        i.putExtra("data", myOrder);
        getActivity().startActivityForResult(i, 1013);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1013 && resultCode == getActivity().RESULT_OK) {
            getActivity().onBackPressed();
        }
    }

    public void gq() {
        DialogStyle1 dialogStyle1 = new DialogStyle1(getContext());
        dialogStyle1.initDialogContent("订单挂起", null, null);
        dialogStyle1.setDialogClickListener(new DialogStyle1.DialogClickListener() {
            @Override
            public void leftClick(Context context, DialogStyle1 dialog) {
                dialog.dismissDialog1();
            }

            @Override
            public void rightClick(Context context, DialogStyle1 dialog) {
                //订单挂起的理由
                String reason = dialog.getEdtText();
                if (reason == null || TextUtils.isEmpty(reason)) {
                    ToastUtil.showLongToast(getContext(), "请输入挂起原因!");
                } else {
                    upOrder(reason);
                    dialog.dismissDialog1();
                }
            }
        });
        dialogStyle1.showDialog1();
    }

    public void upOrder(String remark) {
        if (Utils.isFastClick(1000)) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("orderId", myOrder.getOrderId());
        map.put("remark", remark);
        HttpRequestUtils.httpRequest(getActivity(), "upOrder", RequestValue.ORDER_UP, map, "POST", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        mBGARefreshLayout.beginRefreshing();
                        ToastUtil.showLongToast(getActivity(), "订单已成功挂起！");
                        break;
                    default:
                        ToastUtil.showLongToast(getActivity(), common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                ToastUtil.showLongToast(getActivity(), msg);
            }
        });
    }

    /**
     * 开始定位
     */
    private void startLocation(){

        mlocationClient = new AMapLocationClient(getActivity());
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置返回地址信息，默认为true
        mLocationOption.setNeedAddress(true);
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //单次定位
        mLocationOption.setOnceLocation(true);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);

        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                lat = aMapLocation.getLatitude();
                lon = aMapLocation.getLongitude();

                Log.d("联网成功","获取了位置信息 lat:"
                        + lat + ", lon:"
                        + lon);
                presenter.upDataOrder(myOrder.getOrderId(), Order.STATUS_DAODAXIANCHANG,String.valueOf(lat),String.valueOf(lon));
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }

        }
    }
}

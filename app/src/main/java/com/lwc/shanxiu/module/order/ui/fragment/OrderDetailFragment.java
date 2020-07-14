package com.lwc.shanxiu.module.order.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.ImageBrowseActivity;
import com.lwc.shanxiu.adapter.MyGridViewPhotoAdpter;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.BaseFragment;
import com.lwc.shanxiu.module.bean.Malfunction;
import com.lwc.shanxiu.module.bean.Order;
import com.lwc.shanxiu.module.order.ui.IOrderDetailFragmentView;
import com.lwc.shanxiu.module.order.ui.adapter.HardwareDetailAdapter;
import com.lwc.shanxiu.module.user.RepairmanInfoActivity;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.view.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 何栋
 * @version 1.0
 * @date 2017/3/13 17:39
 * @email 294663966@qq.com
 * 订单详情
 */
public class OrderDetailFragment extends BaseFragment implements IOrderDetailFragmentView {

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.ll_jdr)
    LinearLayout ll_jdr;
    @BindView(R.id.tv_jdr)
    TextView tv_jdr;

    @BindView(R.id.cItemMaintainType)
    TextView cItemMaintainType;
    @BindView(R.id.cItemDeviceTypeMold)
    TextView cItemDeviceTypeMold;
    @BindView(R.id.cItemMalfunction)
    TextView cItemMalfunction;
    @BindView(R.id.lLayoutIcon)
    LinearLayout lLayoutIcon;
    @BindView(R.id.cItemOrderNo)
    TextView cItemOrderNo;
    @BindView(R.id.cItemOrderTime)
    TextView cItemOrderTime;

    @BindView(R.id.ll_pay_type)
    LinearLayout ll_pay_type;
    @BindView(R.id.payType)
    TextView payType;
    @BindView(R.id.iv_payType)
    ImageView iv_payType;
    @BindView(R.id.rl_ms)
    LinearLayout rl_ms;
    @BindView(R.id.rl_remark)
    LinearLayout rl_remark;
    @BindView(R.id.ll_money)
    LinearLayout ll_money;
    @BindView(R.id.rl_smf)
    RelativeLayout rl_smf;
    @BindView(R.id.rl_fwf)
    RelativeLayout rl_fwf;
    @BindView(R.id.rl_qtf)
    RelativeLayout rl_qtf;
    @BindView(R.id.tv_qtf)
    TextView tv_qtf;
    @BindView(R.id.rl_package_price)
    RelativeLayout rl_package_price;
    @BindView(R.id.tv_package_price)
    TextView tv_package_price;
    @BindView(R.id.tv_smf)
    TextView tv_smf;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_fwf)
    TextView tv_fwf;
    @BindView(R.id.tv_remark)
    TextView tv_remark;
    @BindView(R.id.tv_total)
    TextView tv_total;
    @BindView(R.id.tv_amount)
    TextView tv_amount;
    @BindView(R.id.tv_setMealAmount)
    TextView tv_setMealAmount;
    @BindView(R.id.rl_coupon)
    RelativeLayout rl_coupon;
    @BindView(R.id.rl_setMeal)
    RelativeLayout rl_setMeal;
    @BindView(R.id.tv_hardware)
    TextView tv_hardware;
    @BindView(R.id.rl_hardware)
    RelativeLayout rl_hardware;
    @BindView(R.id.gridview_my)
    MyGridView myGridview;
    @BindView(R.id.rv_hardware)
    RecyclerView rv_hardware;
    private List<String> urlStrs = new ArrayList();
    private MyGridViewPhotoAdpter adpter;
    private Order myOrder = null;
    private HardwareDetailAdapter hardwareDetailAdapter = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_detail, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initEngines(view);
        getIntentData();
        setListener();
        getData();
    }

    /**
     * 请求网络获取数据
     */
    private void getData() {
    }


    @Override
    protected void lazyLoad() {
    }

    @Override
    public void init() {
    }

    @Override
    public void initEngines(View view) {
    }

    @Override
    public void getIntentData() {
        myOrder = (Order) getArguments().getSerializable("data");
//        if (myOrder.getOrderType() != null && myOrder.getOrderType().equals("1")) {
        getOrderInfo();
//        } else {
//            setDeviceDetailInfor(myOrder);
//        }
    }

    /**
     * 获取订单详情
     */
    private void getOrderInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("orderId", myOrder.getOrderId());
        HttpRequestUtils.httpRequest(getActivity(), "查询订单详情", RequestValue.POST_ORDER_INFO, map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        myOrder = JsonUtil.parserGsonToObject(JsonUtil.getGsonValueByKey(response, "data"), Order.class);
                        if (myOrder == null) {
                            ToastUtil.showLongToast(getActivity(), "订单详情数据异常");
                            getActivity().onBackPressed();
                            return;
                        }
                        // 保存订单分享状态  分享的时候用与判断
                        SharedPreferencesUtils.setParam(getContext(),"isShare",myOrder.getIsShare());
                        SharedPreferencesUtils.setParam(getContext(),"settlementStatus",myOrder.getSettlementStatus());
                        setDeviceDetailInfor(myOrder);
                        break;
                    default:
                        ToastUtil.showLongToast(getActivity(), common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
                ToastUtil.showLongToast(getActivity(), msg);
            }
        });
    }

    @Override
    public void setListener() {
        adpter = new MyGridViewPhotoAdpter(getActivity(), urlStrs);
        adpter.setIsShowDel(false);
        myGridview.setAdapter(adpter);
        myGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ImageBrowseActivity.class);
                intent.putExtra("index", position);
                intent.putStringArrayListExtra("list", (ArrayList)adpter.getLists());
                startActivity(intent);
            }
        });
    }

    @Override
    public void setDeviceDetailInfor(Order myOrder) {
        LLog.i(myOrder.toString());

        if(!TextUtils.isEmpty(myOrder.getPackagePrice())){
            tv_package_price.setText(Utils.getMoney(Utils.chu(myOrder.getPackagePrice(),"100"))+"元");
        }else{
            rl_package_price.setVisibility(View.GONE);
        }


        if (!TextUtils.isEmpty(myOrder.getMaintenanceName())) {
            tv_jdr.setText(myOrder.getMaintenanceName());
            ll_jdr.setVisibility(View.VISIBLE);
        } else {
            ll_jdr.setVisibility(View.GONE);
        }
        cItemDeviceTypeMold.setText("1".equals(myOrder.getDeviceTypeMold())?"办公设备":"家用电器");
        final List<Malfunction> orderRepairs = myOrder.getOrderRepairs();
        if (orderRepairs != null && orderRepairs.size() > 0){
            String typeName = "";
            for (int i = 0; i< orderRepairs.size(); i++) {
                Malfunction ma = orderRepairs.get(i);
                if (i == orderRepairs.size()-1) {
                    typeName = typeName+ma.getDeviceTypeName()+"->"+ma.getReqairName();
                } else {
                    typeName = typeName+ma.getDeviceTypeName()+"->"+ma.getReqairName()+"\n";
                }
            }
            cItemMaintainType.setText(typeName);
        } else if (myOrder.getDeviceTypeName() != null && !TextUtils.isEmpty(myOrder.getDeviceTypeName())) {
            if (myOrder.getReqairName() != null && !myOrder.getReqairName().equals("")){
                cItemMaintainType.setText(myOrder.getDeviceTypeName()+"->"+myOrder.getReqairName());
            } else {
                cItemMaintainType.setText(myOrder.getDeviceTypeName());
            }
        } else {
            cItemMaintainType.setText("暂无");
        }
        if (!TextUtils.isEmpty(myOrder.getOrderDescription())) {
            cItemMalfunction.setText(myOrder.getOrderDescription());
            rl_ms.setVisibility(View.VISIBLE);
        } else {
            rl_ms.setVisibility(View.GONE);
        }
        String img = myOrder.getOrderImage();
        if (img != null && !img.equals("")) {
            String[] imgs = img.split(",");
            for (int i=0;i<imgs.length; i++) {
                urlStrs.add(imgs[i]);
            }
            adpter.setLists(urlStrs);
            adpter.notifyDataSetChanged();
        } else {
            lLayoutIcon.setVisibility(View.GONE);
        }
        cItemOrderNo.setText(myOrder.getOrderId());
        if (myOrder.getCreateTime() != null && !TextUtils.isEmpty(myOrder.getCreateTime())) {
            cItemOrderTime.setText(myOrder.getCreateTime());
        } else {
            cItemOrderTime.setText("暂无");
        }
        if (myOrder.isHasRecord()) {
            int count = 0;
            ll_money.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(myOrder.getVisitCost()) && Integer.parseInt(myOrder.getVisitCost()) > 0) {
                rl_smf.setVisibility(View.VISIBLE);
                count++;
                tv_smf.setText(Utils.getMoney(Utils.chu(myOrder.getVisitCost(), "100"))+" 元");
            } else {
                //rl_smf.setVisibility(View.GONE);
                tv_smf.setText("已免除");
                tv_smf.setTextColor(Color.parseColor("#ff3a3a"));
            }
            if (!TextUtils.isEmpty(myOrder.getMaintainCost()) && Integer.parseInt(myOrder.getMaintainCost()) > 0) {
                rl_fwf.setVisibility(View.VISIBLE);
                count++;
                tv_fwf.setText(Utils.getMoney(Utils.chu(myOrder.getMaintainCost(), "100"))+" 元");
                tv_title.setText("维修价格");
            } else {
                rl_fwf.setVisibility(View.GONE);
            }

            //硬件更换费
            if (!TextUtils.isEmpty(myOrder.getHardwareCost()) && Integer.parseInt(myOrder.getHardwareCost()) > 0) {
                count++;
                rl_hardware.setVisibility(View.VISIBLE);
                tv_hardware.setText(Utils.getMoney(Utils.chu(myOrder.getHardwareCost(), "100"))+" 元");
                rv_hardware.setVisibility(View.VISIBLE);
                hardwareDetailAdapter = new HardwareDetailAdapter(getContext(),myOrder.getAccessories(),R.layout.item_hardware_detail,false);
                rv_hardware.setLayoutManager(new LinearLayoutManager(getContext()));
                rv_hardware.setAdapter(hardwareDetailAdapter);

            } else {
                rl_hardware.setVisibility(View.GONE);
                rv_hardware.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(myOrder.getOtherCost()) && Integer.parseInt(myOrder.getOtherCost()) > 0) {
                rl_qtf.setVisibility(View.VISIBLE);
                count++;
                tv_qtf.setText(Utils.getMoney(Utils.chu(myOrder.getOtherCost(), "100"))+" 元");
                if (!TextUtils.isEmpty(myOrder.getRemark())) {
                    rl_remark.setVisibility(View.VISIBLE);
                    tv_remark.setText(myOrder.getRemark());
                } else {
                    rl_remark.setVisibility(View.GONE);
                }
            } else {
                rl_remark.setVisibility(View.GONE);
                rl_qtf.setVisibility(View.GONE);
            }

            //显示优惠券
            String money = myOrder.getSumCost();
            if (!TextUtils.isEmpty(myOrder.getDiscountAmount()) && !myOrder.getDiscountAmount().trim().equals("0")) {
                money = Utils.jian(myOrder.getSumCost(), myOrder.getDiscountAmount());
                rl_coupon.setVisibility(View.VISIBLE);
                tv_amount.setText("-"+Utils.getMoney(Utils.chu(myOrder.getDiscountAmount(), "100"))+" 元");
            }else{
                rl_coupon.setVisibility(View.GONE);
            }

            //显示套餐折扣
            if (!TextUtils.isEmpty(myOrder.getPackageType())) {
                String mealCost = "";
                switch (myOrder.getPackageType()){
                    case "1": //免除上门费
                        mealCost = myOrder.getVisitCost();
                        break;
                    case "2": //免除维修费
                        mealCost = myOrder.getMaintainCost();
                        break;
                    case "3": //免除上门维修费
                        mealCost = String.valueOf(Long.parseLong(myOrder.getMaintainCost())+Long.parseLong(myOrder.getVisitCost()));
                        break;
                }
                money = Utils.jian(money, mealCost);
                rl_setMeal.setVisibility(View.VISIBLE);
                tv_setMealAmount.setText("-"+Utils.getMoney(Utils.chu(mealCost, "100"))+" 元");
            }else{
                rl_setMeal.setVisibility(View.GONE);
            }


            String total = "总计 ( "+count+" ): "+Utils.getMoney(Utils.chu(money,"100"))+" 元";
            tv_total.setText(Utils.getSpannableStringBuilder(total.indexOf(":")+1, total.length(), getResources().getColor(R.color.red_3a), total, 15));
        } else {
            ll_money.setVisibility(View.GONE);
        }

        if (myOrder.isHasSettlement()) {
            ll_pay_type.setVisibility(View.VISIBLE);
            if (myOrder.getSettlementPlatform() == 1){
                payType.setText("余额支付");
                iv_payType.setImageResource(R.drawable.ic_package_order);
                //payType.setCompoundDrawables(Utils.getDrawable(getActivity(), R.drawable.ic_paytype_balance), null, null, null);
            } else if (myOrder.getSettlementPlatform() == 2){
                payType.setText("支付宝");
                iv_payType.setImageResource(R.drawable.account_alipay);
                // payType.setCompoundDrawables(Utils.getDrawable(getActivity(), R.drawable.account_alipay), null, null, null);
            } else if (myOrder.getSettlementPlatform() == 3){
                payType.setText("微信");
                iv_payType.setImageResource(R.drawable.account_wechat);
                //  payType.setCompoundDrawables(Utils.getDrawable(getActivity(), R.drawable.account_wechat), null, null, null);
            }
        } else {
            ll_pay_type.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(myOrder.getOrderContactName())) {
            tv_name.setText(myOrder.getOrderContactName());
        } else {
            tv_name.setText("暂无");
        }

        if (!TextUtils.isEmpty(myOrder.getOrderContactPhone())) {
            tvPhone.setText(myOrder.getOrderContactPhone());
        } else {
            tvPhone.setText("暂无");
        }
        if (!TextUtils.isEmpty(myOrder.getOrderContactAddress())) {
            tv_address.setText(myOrder.getOrderContactAddress().replace("_", ""));
        } else {
            tv_address.setText("暂无");
        }
    }

    @OnClick({R.id.ll_jdr})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_jdr:
                //TODO 查看接单人信息
                Bundle bundle = new Bundle();
                bundle.putSerializable("repair_id", myOrder.getMaintenanceId());
                IntentUtil.gotoActivity(getActivity(), RepairmanInfoActivity.class, bundle);
                break;
        }
    }

    /**
     * 获取设备类型
     */
    public String getDeviceModel(){
        if(myOrder != null){
            return myOrder.getDeviceTypeMold();
        }
        return "";
    }
}
package com.lwc.shanxiu.module.order.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.MainActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.BaseFragmentActivity;
import com.lwc.shanxiu.module.bean.Order;
import com.lwc.shanxiu.module.common_adapter.FragmentsPagerAdapter;
import com.lwc.shanxiu.module.order.ui.fragment.DeviceDetailFragment;
import com.lwc.shanxiu.module.order.ui.fragment.OrderDetailFragment;
import com.lwc.shanxiu.module.order.ui.fragment.OrderStateFragment;
import com.lwc.shanxiu.utils.DialogUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.LLog;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.widget.CustomDialog;
import com.lwc.shanxiu.widget.CustomViewPager;
import com.yanzhenjie.sofia.Sofia;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailActivity extends BaseFragmentActivity {

    @BindView(R.id.txtActionbarTitle)
    TextView txtActionbarTitle;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.rBtnState)
    RadioButton rBtnState;
    @BindView(R.id.rBtnOrderDetail)
    RadioButton rBtnOrderDetail;
    @BindView(R.id.rBtnDeviceDetail)
    RadioButton rBtnDeviceDetail;
    @BindView(R.id.viewLine1)
    View viewLine1;
    @BindView(R.id.viewLine2)
    View viewLine2;
    @BindView(R.id.viewLine3)
    View viewLine3;
    @BindView(R.id.cViewPager)
    CustomViewPager cViewPager;
    @BindView(R.id.txtEmptyView)
    TextView txtEmptyView;
    @BindView(R.id.imgRight)
    ImageView imgRight;

    private HashMap fragmentHashMap = null;
    private HashMap rButtonHashMap = null;
    private Order myOrder = null;
    public static OrderDetailActivity activity;
    private String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        activity = this;
        getIntentData();
//        if (myOrder != null) {
//            initData();
//        } else if (!TextUtils.isEmpty(orderId)){
            getOrderInfo();
//        }

        Sofia.with(this)
                .statusBarBackground(Color.parseColor("#ffffff"))
                .statusBarDarkFont();
    }

    private void initData(){
        addFragmenInList();
        addRadioButtonIdInList();
        bindViewPage(fragmentHashMap);
        initView();

        if (isDeviceDetailShow()) {
            rBtnDeviceDetail.setVisibility(View.VISIBLE);
            viewLine3.setVisibility(View.INVISIBLE);
        } else {
            rBtnDeviceDetail.setVisibility(View.GONE);
            viewLine3.setVisibility(View.GONE);
        }
    }

    /**
     * 获取订单详情
     */
    private void getOrderInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderId);
        HttpRequestUtils.httpRequest(this, "查询订单详情", RequestValue.POST_ORDER_INFO, map, "GET", new HttpRequestUtils.ResponseListener() {
            @Override
            public void getResponseData(String response) {
                Common common = JsonUtil.parserGsonToObject(response, Common.class);
                switch (common.getStatus()) {
                    case "1":
                        myOrder = JsonUtil.parserGsonToObject(JsonUtil.getGsonValueByKey(response, "data"), Order.class);
                        if (myOrder == null) {
                            ToastUtil.showLongToast(OrderDetailActivity.this, "订单详情数据异常");
                            finish();
                            return;
                        }
                        initData();
                        break;
                    default:
                        ToastUtil.showLongToast(OrderDetailActivity.this, common.getInfo());
                        break;
                }
            }

            @Override
            public void returnException(Exception e, String msg) {
                LLog.eNetError(e.toString());
                ToastUtil.showLongToast(OrderDetailActivity.this, msg);
            }
        });
    }

    public boolean isDeviceDetailShow() {
        if (myOrder != null && (myOrder.getStatusId() == 11 || myOrder.getStatusId() == 12 || myOrder.getStatusId() == 13)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void initView() {
        imgBack.setVisibility(View.VISIBLE);
        txtActionbarTitle.setText("订单详情");
        cViewPager.setCurrentItem(0);
        rBtnState.setChecked(true);
        showLineView(1);
        int statusId = myOrder.getStatusId();
        if (myOrder != null && statusId != Order.STATUS_YIQUXIAO && statusId != Order.STATUS_DAIJIEDAN && statusId != Order.STATUS_YIWANCHENG && statusId != Order.STATUS_YIPINGJIA) {
            setBohao(true);
        } else {
            imgRight.setVisibility(View.GONE);
        }
    }

    @Override
    public void initEngines() {
    }

    @Override
    public void getIntentData() {
        Intent intent = getIntent();
        myOrder = (Order) intent.getSerializableExtra("data");
        orderId = intent.getStringExtra("orderId");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        setBohao(false);
    }

    public void setBohao(boolean isShow) {
        if (isShow) {
            imgRight.setVisibility(View.VISIBLE);
            imgRight.setImageResource(R.drawable.bohao);
            imgRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phone = myOrder.getOrderContactPhone();
                    if (phone == null || phone.equals("")) {
                        phone = myOrder.getUserPhone();
                    }
                    DialogUtil.showMessageDg(OrderDetailActivity.this, "温馨提示", "确定拨打用户电话："+phone+" ？", new CustomDialog.OnClickListener() {

                        @Override
                        public void onClick(CustomDialog dialog, int id, Object object) {
                            dialog.dismiss();
                            Intent intent = new Intent(
                                    Intent.ACTION_CALL);
                            String phone = myOrder.getOrderContactPhone();
                            if (phone == null || phone.equals("")) {
                                phone = myOrder.getUserPhone();
                            }
                            Uri data = Uri.parse("tel:" + phone);
                            intent.setData(data);
                            startActivity(intent);
                        }
                    });
                }
            });
        } else {
            imgRight.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1013 && resultCode == RESULT_OK) {
            onBackPressed();
        }
    }

    /**
     * 往fragmentHashMap中添加 Fragment
     */
    private void addFragmenInList() {

        fragmentHashMap = new HashMap<>();

        Bundle bundle = new Bundle();
        bundle.putSerializable("data", myOrder);

        OrderStateFragment orderStateFragment = new OrderStateFragment();
        orderStateFragment.setArguments(bundle);
        OrderDetailFragment orderDetailFragment = new OrderDetailFragment();
        orderDetailFragment.setArguments(bundle);

        fragmentHashMap.put(0, orderStateFragment);
        fragmentHashMap.put(1, orderDetailFragment);
        if (isDeviceDetailShow()) {
            DeviceDetailFragment deviceDetailFragment = new DeviceDetailFragment();
            deviceDetailFragment.setArguments(bundle);
            fragmentHashMap.put(2, deviceDetailFragment);
        }
    }

    /**
     * 往rButtonHashMap中添加 RadioButton Id
     */
    private void addRadioButtonIdInList() {

        rButtonHashMap = new HashMap<>();
        rButtonHashMap.put(0, rBtnState);
        rButtonHashMap.put(1, rBtnOrderDetail);
        if (isDeviceDetailShow()) {
            rButtonHashMap.put(2, rBtnDeviceDetail);
        }
    }

    @OnClick({R.id.rBtnState, R.id.rBtnOrderDetail, R.id.rBtnDeviceDetail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rBtnState:
                cViewPager.setCurrentItem(0);
                showLineView(1);
                break;
            case R.id.rBtnOrderDetail:
                cViewPager.setCurrentItem(1);
                showLineView(2);
                break;
            case R.id.rBtnDeviceDetail:
                cViewPager.setCurrentItem(2);
                showLineView(3);
                break;
        }
    }


    /**
     * 获取设备类型
     */
    public String getDeviceModel(){
        if(fragmentHashMap != null){
           return ((OrderDetailFragment)fragmentHashMap.get(1)).getDeviceModel();
        }
        return "";
    }
    /**
     * 显示RadioButton线条
     *
     * @param num 1 ： 显示已发布下的线条  2 ： 显示未完成下的线条  3： 显示已完成下的线条  。未选中的线条将会被隐藏
     */
    private void showLineView(int num) {

        switch (num) {
            case 1:
                viewLine1.setVisibility(View.VISIBLE);
                viewLine2.setVisibility(View.INVISIBLE);
                if (isDeviceDetailShow()) {
                    viewLine3.setVisibility(View.INVISIBLE);
                }
                break;
            case 2:
                viewLine2.setVisibility(View.VISIBLE);
                viewLine1.setVisibility(View.INVISIBLE);
                if (isDeviceDetailShow()) {
                    viewLine3.setVisibility(View.INVISIBLE);
                }
                break;
            case 3:
                viewLine3.setVisibility(View.VISIBLE);
                viewLine2.setVisibility(View.INVISIBLE);
                viewLine1.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void bindViewPage(HashMap<Integer, Fragment> fragmentList) {
        //是否滑动
        cViewPager.setPagingEnabled(true);
        cViewPager.setAdapter(new FragmentsPagerAdapter(getSupportFragmentManager(), fragmentList));
        cViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                cViewPager.setChecked(rButtonHashMap, position);
                showLineView(position + 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setData(Order myOrder) {
        this.myOrder = myOrder;
    }

    @Override
    public void finish() {
        if (MainActivity.activity == null) {
            IntentUtil.gotoActivityAndFinish(this, MainActivity.class);
        }
        super.finish();
    }
}

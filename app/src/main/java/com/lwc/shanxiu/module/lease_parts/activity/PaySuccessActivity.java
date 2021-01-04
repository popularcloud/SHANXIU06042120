package com.lwc.shanxiu.module.lease_parts.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.lease_parts.fragment.LeaseShoppingCartFragment;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.PopupWindowUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 支付成功
 */
public class PaySuccessActivity extends BaseActivity {

    @BindView(R.id.tv_to_home)
    TextView tv_to_home;
    @BindView(R.id.tv_look_order)
    TextView tv_look_order;

    @BindView(R.id.fragment_container)
    FrameLayout fragment_container;
    @BindView(R.id.tv_price)
    TextView tv_price;

    private LeaseShoppingCartFragment leaseShoppingCartFragment;
    private FragmentManager fragmentManager;


    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_pay_success;
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void init() {
        setTitle("");

        leaseShoppingCartFragment = new LeaseShoppingCartFragment();
        fragmentManager = getSupportFragmentManager();

        setRightImg(R.drawable.ic_more_black, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindowUtil.showHeaderPopupWindow(PaySuccessActivity.this,imgRight,leaseShoppingCartFragment,fragment_container,fragmentManager);
            }
        });
        showBack();
        String price = getIntent().getStringExtra("price");

        tv_price.setText("总金额："+ Utils.getMoney(Utils.chu(price,"100")) + "元");
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {

    }

    @OnClick({R.id.tv_to_home,R.id.tv_look_order})
    public void onBtnClick(View view){
        switch (view.getId()){
            case R.id.tv_to_home:
                IntentUtil.gotoActivity(PaySuccessActivity.this, LeaseHomeActivity.class);
                finish();
                break;
            case R.id.tv_look_order:
                IntentUtil.gotoActivity(PaySuccessActivity.this, MyLeaseOrderListActivity.class);
                break;
        }
    }
}

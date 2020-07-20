package com.lwc.shanxiu.module.lease_parts.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;

import butterknife.BindView;

/**
 * 物流状态
 */
public class LeaseGoodLogisticsActivity extends BaseActivity {

    @BindView(R.id.imgRight)
    ImageView imgRight;


    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_logistics;
    }

    @Override
    protected void findViews() {

        setTitle("物流详情");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取未读租赁消息
       // MsgReadUtil.hasMessage(this,tv_msg);
    }

    @Override
    protected void init() {


        setRightImg(R.drawable.ic_more_black, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // PopupWindowUtil.showHeaderPopupWindow(LeaseApplyForRefundActivity.this,imgRight);
            }
        });

        ImmersionBar.with(LeaseGoodLogisticsActivity.this)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true)
                .navigationBarColor(R.color.gray_white).init();
    }


    private void loadToUI(){

    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {

    }
}

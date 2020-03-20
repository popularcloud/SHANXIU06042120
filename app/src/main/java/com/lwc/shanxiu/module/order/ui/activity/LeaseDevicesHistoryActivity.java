package com.lwc.shanxiu.module.order.ui.activity;

import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.module.bean.DeviceAllMsgBean;
import com.lwc.shanxiu.module.bean.LeaseDevicesHistoryBean;
import com.lwc.shanxiu.module.order.ui.adapter.DeviceMsgAdapter;
import com.lwc.shanxiu.module.order.ui.adapter.LeaseDevicesHistoryAdapter;

import java.io.Serializable;

import butterknife.BindView;

public class LeaseDevicesHistoryActivity extends BaseActivity {

    @BindView(R.id.expand_list)
    ExpandableListView expand_list;
    @BindView(R.id.btnUpdate)
    TextView btnUpdate;
    private boolean isPageLoadFinish = false;

    LeaseDevicesHistoryAdapter leaseDevicesHistoryAdapter;
    LeaseDevicesHistoryBean leaseDevicesHistoryBean;

   // private Serializable leaseDevicesHistoryBean;


    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.activity_lease_device_msg;
    }

    @Override
    protected void findViews() {
        setTitle("租赁设备维修历史");
        showBack();
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initGetData() {
        leaseDevicesHistoryBean = (LeaseDevicesHistoryBean) getIntent().getSerializableExtra("leaseDevicesHistoryBean");

        leaseDevicesHistoryAdapter = new LeaseDevicesHistoryAdapter(this,leaseDevicesHistoryBean);
        expand_list.setAdapter(leaseDevicesHistoryAdapter);
        for(int i = 0; i < leaseDevicesHistoryAdapter.getGroupCount(); i++){
            expand_list.expandGroup(i);
        }
    }

    @Override
    protected void widgetListener() {

    }
}

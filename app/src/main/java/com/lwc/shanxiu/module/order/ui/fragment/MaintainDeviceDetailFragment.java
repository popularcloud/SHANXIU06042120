package com.lwc.shanxiu.module.order.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.user.LoginOrRegistActivity;
import com.lwc.shanxiu.module.BaseFragment;
import com.lwc.shanxiu.module.bean.Order;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.module.order.ui.IMaintainDeviceDetailView;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.widget.CustomItem1;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建于 2017/4/29.
 * 作者： 何栋
 * 邮箱： 294663966@qq.com
 */
public class MaintainDeviceDetailFragment extends BaseFragment implements IMaintainDeviceDetailView {

    @BindView(R.id.cItemDeviceName)
    CustomItem1 cItemDeviceName;
    @BindView(R.id.cItemDeviceType)
    CustomItem1 cItemDeviceType;
    @BindView(R.id.cItemBrandType)
    CustomItem1 cItemBrandType;
    @BindView(R.id.cItemCPU)
    CustomItem1 cItemCPU;
    @BindView(R.id.cItemHardDisk)
    CustomItem1 cItemHardDisk;
    @BindView(R.id.cItemMemory)
    CustomItem1 cItemMemory;
    @BindView(R.id.cItemSystem)
    CustomItem1 cItemSystem;
    private Order myOrder = null;

    private SharedPreferencesUtils preferencesUtils = null;
    private User user = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maintain_device_detail, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initEngines(view);
        getIntentData();
        init();
        setListener();
        setDeviceDetailInfor(myOrder);
//        presenter.getDeviceDetail(myOrder.getDid() +"", myOrder.getUdid() + "", myOrder.getUid() + "");
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
        preferencesUtils = SharedPreferencesUtils.getInstance(getContext());
        user = preferencesUtils.loadObjectData(User.class);
        if (user == null) {
            IntentUtil.gotoActivity(getContext(), LoginOrRegistActivity.class);
            return;
        }
    }

    @Override
    public void getIntentData() {
        myOrder = (Order) getArguments().getSerializable("data");
    }

    @Override
    public void setListener() {

    }

    @Override
    public void setDeviceDetailInfor(Order myOrder) {

        cItemDeviceName.setRightText(myOrder.getDeviceTypeName());  //设备名称
        cItemDeviceType.setRightText(myOrder.getDeviceTypeName());  //设备类型
        cItemBrandType.setRightText(myOrder.getOrderDescription());     //品牌类型
    }

}

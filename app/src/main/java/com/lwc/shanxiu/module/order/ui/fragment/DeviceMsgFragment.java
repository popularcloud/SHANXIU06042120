package com.lwc.shanxiu.module.order.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.BaseFragment;
import com.lwc.shanxiu.module.bean.DeviceAllMsgBean;
import com.lwc.shanxiu.module.bean.UpdateDeviceMsgBean;
import com.lwc.shanxiu.module.order.ui.activity.RepairHistoryNewActivity;
import com.lwc.shanxiu.module.order.ui.activity.UpdateDeviceMsgActivity;
import com.lwc.shanxiu.module.order.ui.adapter.DeviceMsgAdapter;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author younge
 * @date 2019/6/25 0025
 * @email 2276559259@qq.com
 * 设备信息
 */
public class DeviceMsgFragment extends BaseFragment{

    @BindView(R.id.expand_list)
    ExpandableListView expand_list;
    @BindView(R.id.btnUpdate)
    TextView btnUpdate;
    private boolean isPageLoadFinish = false;

    DeviceMsgAdapter deviceMsgAdapter;
    DeviceAllMsgBean presentBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_msg, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((RepairHistoryNewActivity)getActivity()).initData(0);
        isPageLoadFinish = true;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && isPageLoadFinish){
            //boolean needUpdate = (boolean) SharedPreferencesUtils.getParam(getActivity(),"needUpdate",false);
            //if(needUpdate){
                ((RepairHistoryNewActivity)getActivity()).initData(0);
            //}
        }
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

    }

    @Override
    public void setListener() {
    }

    @OnClick({R.id.btnUpdate})
    public void onBtnClick(View view){
        switch (view.getId()){
            case R.id.btnUpdate:
                if(presentBean != null){
                    Bundle bundle = new Bundle();
                    UpdateDeviceMsgBean updateDeviceMsgBean = new UpdateDeviceMsgBean();
                    updateDeviceMsgBean.setRelevanceId(presentBean.getRelevanceId());
                    updateDeviceMsgBean.setCompanyProvinceName(presentBean.getCompanyProvinceName());
                    updateDeviceMsgBean.setCompanyProvinceId(presentBean.getCompanyProvinceId());
                    updateDeviceMsgBean.setCompanyCityName(presentBean.getCompanyCityName());
                    updateDeviceMsgBean.setCompanyCityId(presentBean.getCompanyCityId());
                    updateDeviceMsgBean.setCompanyTownName(presentBean.getCompanyTownName());
                    updateDeviceMsgBean.setCompanyTownId(presentBean.getCompanyTownId());
                    updateDeviceMsgBean.setUserPhone(presentBean.getUserPhone());
                    updateDeviceMsgBean.setUserCompanyName(presentBean.getUserCompanyName());
                    bundle.putSerializable("presentBean",updateDeviceMsgBean);
                    IntentUtil.gotoActivity(getActivity(), UpdateDeviceMsgActivity.class,bundle);
                }else{
                    ToastUtil.showToast(getContext(),"设备信息获取失败！");
                }
                break;
        }
    }

    public void loadData(DeviceAllMsgBean bean) {
        presentBean = bean;
        deviceMsgAdapter = new DeviceMsgAdapter(getContext(),presentBean);
        expand_list.setAdapter(deviceMsgAdapter);
        for(int i = 0; i < deviceMsgAdapter.getGroupCount(); i++){
            expand_list.expandGroup(i);
        }
    }
}

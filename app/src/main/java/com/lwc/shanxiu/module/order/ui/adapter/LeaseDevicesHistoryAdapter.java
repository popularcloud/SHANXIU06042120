package com.lwc.shanxiu.module.order.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.bean.DeviceAllMsgBean;
import com.lwc.shanxiu.module.bean.LeaseDevicesHistoryBean;
import com.lwc.shanxiu.module.order.ui.activity.OrderDetailActivity;
import com.lwc.shanxiu.utils.IntentUtil;

/**
 * @author younge
 * @date 2019/5/24 0024
 * @email 2276559259@qq.com
 */
public class LeaseDevicesHistoryAdapter extends BaseExpandableListAdapter{

    private LeaseDevicesHistoryBean leaseDevicesHistoryBean;
    private Context mContext;

    public LeaseDevicesHistoryAdapter(Context ctx, LeaseDevicesHistoryBean leaseDevicesHistoryBean){
        this.leaseDevicesHistoryBean = leaseDevicesHistoryBean;
        this.mContext = ctx;
    }

    @Override
    public int getGroupCount() {
        if(leaseDevicesHistoryBean == null){
            return 0;
        }else if(leaseDevicesHistoryBean != null && (leaseDevicesHistoryBean.getOrders() == null || leaseDevicesHistoryBean.getOrders().size() < 1)){
            return 1;
        }else{
            return 2;
        }

    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(groupPosition == 0){
            return 1;
        }else{
            return (leaseDevicesHistoryBean == null || leaseDevicesHistoryBean.getOrders() == null)?0:leaseDevicesHistoryBean.getOrders().size();
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView==null)
            convertView=View.inflate(mContext, R.layout.item_devicemsg_parent, null);
            TextView groupTv=(TextView) convertView.findViewById(R.id.tv_parent_name);
        if(groupPosition == 0){
            groupTv.setText("基本信息");
        }else{
            groupTv.setText("维修记录");
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        switch (groupPosition){
            case 0:
                convertView=View.inflate(mContext, R.layout.item_lease_devicemsg_child_basic, null);
                ViewHolder1 holder1 = new ViewHolder1(convertView);
                holder1.tv_work_unit.setText(leaseDevicesHistoryBean.getUserCompanyName());
                holder1.tv_user.setText(leaseDevicesHistoryBean.getUserName());
                holder1.tv_unit_address.setText(leaseDevicesHistoryBean.getCompanyProvinceName()+"-"+leaseDevicesHistoryBean.getCompanyCityName()+"-"+leaseDevicesHistoryBean.getCompanyTownName());
                holder1.tv_phone.setText(leaseDevicesHistoryBean.getUserPhone());
                holder1.tv_detail_address.setText(leaseDevicesHistoryBean.getUserCompanyAddrs());
                holder1.tv_type.setText(leaseDevicesHistoryBean.getDeviceTypeName());
                holder1.tv_brand.setText(leaseDevicesHistoryBean.getDeviceTypeBrand());
                holder1.tv_model.setText(leaseDevicesHistoryBean.getDeviceTypeModel());
                holder1.tv_engineer.setText(leaseDevicesHistoryBean.getMaintenanceName());
                holder1.tv_install_unit.setText(leaseDevicesHistoryBean.getMaintenanceCompanyName());
                holder1.tv_time.setText(leaseDevicesHistoryBean.getCreateTime());
                break;
            default:
                final LeaseDevicesHistoryBean.OrdersBean ordersBean = leaseDevicesHistoryBean.getOrders().get(childPosition);
                convertView=View.inflate(mContext, R.layout.item_lease_devicemsg_child_repair, null);
                ViewHolder2 holder2 = new ViewHolder2(convertView);
                holder2.tv_order_num.setText(ordersBean.getOrderId());
                holder2.tv_user.setText(ordersBean.getOrderContactName());
                holder2.tv_repair_type.setText(ordersBean.getDeviceTypeName()+" - "+ordersBean.getReqairName());
                holder2.tv_fault_desc.setText(TextUtils.isEmpty(ordersBean.getOrderDescription())?ordersBean.getReqairName():ordersBean.getOrderDescription());
                holder2.tv_time.setText(ordersBean.getCreateTime());

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("orderId",ordersBean.getOrderId());
                        IntentUtil.gotoActivity(mContext, OrderDetailActivity.class,bundle);
                    }
                });
                break;
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private class ViewHolder1{
        TextView tv_work_unit;
        TextView tv_user;
        TextView tv_unit_address;
        TextView tv_type;
        TextView tv_phone;
        TextView tv_brand;
        TextView tv_model;
        TextView tv_detail_address;
        TextView tv_engineer;
        TextView tv_install_unit;
        TextView tv_time;
        public ViewHolder1(View view){
            tv_work_unit = (TextView) view.findViewById(R.id.tv_work_unit);
            tv_user = (TextView) view.findViewById(R.id.tv_user);
            tv_unit_address = (TextView) view.findViewById(R.id.tv_unit_address);
            tv_type = (TextView) view.findViewById(R.id.tv_type);
            tv_phone = (TextView) view.findViewById(R.id.tv_phone);
            tv_brand = (TextView) view.findViewById(R.id.tv_brand);
            tv_model = (TextView) view.findViewById(R.id.tv_model);
            tv_detail_address = (TextView) view.findViewById(R.id.tv_detail_address);
            tv_engineer = (TextView) view.findViewById(R.id.tv_engineer);
            tv_install_unit = (TextView) view.findViewById(R.id.tv_install_unit);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
        }
    }

    private class ViewHolder2{
        TextView tv_order_num;
        TextView tv_user;
        TextView tv_repair_type;
        TextView tv_fault_desc;
        TextView tv_time;
        public ViewHolder2(View view){
            tv_order_num = (TextView) view.findViewById(R.id.tv_order_num);
            tv_user = (TextView) view.findViewById(R.id.tv_user);
            tv_repair_type = (TextView) view.findViewById(R.id.tv_repair_type);
            tv_fault_desc = (TextView) view.findViewById(R.id.tv_fault_desc);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
        }
    }
}

package com.lwc.shanxiu.module.order.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.bean.DeviceAllMsgBean;

/**
 * @author younge
 * @date 2019/5/24 0024
 * @email 2276559259@qq.com
 */
public class DeviceMsgAdapter extends BaseExpandableListAdapter{

    private DeviceAllMsgBean deviceAllMsgBean;
    private Context mContext;

    public DeviceMsgAdapter(Context ctx,DeviceAllMsgBean deviceAllMsgBean){
        this.deviceAllMsgBean = deviceAllMsgBean;
        this.mContext = ctx;
    }

    @Override
    public int getGroupCount() {
        return 3;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(groupPosition == 0){
            return (deviceAllMsgBean == null || deviceAllMsgBean.getUpdates() == null)?0:deviceAllMsgBean.getUpdates().size();
            //return 1;
        }else if(groupPosition == 1){
            return deviceAllMsgBean == null?0:1;
        }else if(groupPosition == 2){
            return (deviceAllMsgBean == null || deviceAllMsgBean.getOrders()== null)?0:deviceAllMsgBean.getOrders().size();
           // return 1;
        }else{
            return 0;
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
            if(deviceAllMsgBean == null || deviceAllMsgBean.getUpdates() == null || deviceAllMsgBean.getUpdates().size() == 0){
                groupTv.setText("暂无修改记录");
            }else{
                groupTv.setText("历史记录");
            }
        }else if(groupPosition == 1){
            groupTv.setText("基本信息");
        }else if(groupPosition == 2){
            if(deviceAllMsgBean == null || deviceAllMsgBean.getOrders() == null || deviceAllMsgBean.getOrders().size() == 0){
                groupTv.setText("暂无维修记录");
            }else{
                groupTv.setText("维修记录");
            }

        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        switch (groupPosition){
            case 0:
                DeviceAllMsgBean.UpdatesBean updatesBean = deviceAllMsgBean.getUpdates().get(childPosition);
                convertView=View.inflate(mContext, R.layout.item_devicemsg_child_history, null);
                ViewHolder0 holder0 = new ViewHolder0(convertView);
                holder0.tv_work_unit.setText(updatesBean.getUserCompanyName());
                holder0.tv_user.setText(updatesBean.getUserPhone());
                holder0.tv_unit_address.setText(updatesBean.getCompanyProvinceName()+ updatesBean.getCompanyCityName() + updatesBean.getCompanyTownName());
                holder0.tv_reason.setText(updatesBean.getUpdateReason());
                holder0.tv_time.setText(updatesBean.getCreateTime());
                break;
            case 1:
                convertView=View.inflate(mContext, R.layout.item_devicemsg_child_basic, null);
                ViewHolder1 holder1 = new ViewHolder1(convertView);
                holder1.tv_work_unit.setText(deviceAllMsgBean.getUserCompanyName());
                holder1.tv_user.setText(deviceAllMsgBean.getUserPhone());
                holder1.tv_unit_address.setText(deviceAllMsgBean.getCompanyProvinceName()+ deviceAllMsgBean.getCompanyCityName() + deviceAllMsgBean.getCompanyTownName());
                holder1.tv_type.setText(deviceAllMsgBean.getDeviceTypeName());
                holder1.tv_brand.setText(deviceAllMsgBean.getDeviceTypeBrand());
                holder1.tv_model.setText(deviceAllMsgBean.getDeviceTypeModel());
                holder1.tv_engineer.setText(deviceAllMsgBean.getMaintenanceName());
                holder1.tv_install_unit.setText(deviceAllMsgBean.getMaintenanceCompanyName());
                holder1.tv_time.setText(deviceAllMsgBean.getCreateTime());
                break;
            case 2:
                DeviceAllMsgBean.OrdersBean ordersBean = deviceAllMsgBean.getOrders().get(childPosition);
                convertView=View.inflate(mContext, R.layout.item_devicemsg_child_repair, null);
                ViewHolder2 holder2 = new ViewHolder2(convertView);
                holder2.tv_order_num.setText(ordersBean.getOrderId());
                holder2.tv_user.setText(ordersBean.getOrderContactName());
                holder2.tv_repair_type.setText(ordersBean.getDeviceTypeName()+" - "+ordersBean.getReqairName());
                holder2.tv_fault_desc.setText(TextUtils.isEmpty(ordersBean.getOrderDescription())?ordersBean.getReqairName():ordersBean.getOrderDescription());
                holder2.tv_time.setText(ordersBean.getCreateTime());
                break;
        }

   /*     if(convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(mContext, R.layout.item_fee_standard_child, null);
            holder.title=(TextView) convertView.findViewById(R.id.tv_title);
            holder.price=(TextView) convertView.findViewById(R.id.tv_price);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }*/
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private class ViewHolder0{
        TextView tv_work_unit;
        TextView tv_user;
        TextView tv_unit_address;
        TextView tv_reason;
        TextView tv_time;
        public ViewHolder0(View view){
            tv_work_unit = (TextView) view.findViewById(R.id.tv_work_unit);
            tv_user = (TextView) view.findViewById(R.id.tv_user);
            tv_unit_address = (TextView) view.findViewById(R.id.tv_unit_address);
            tv_reason = (TextView) view.findViewById(R.id.tv_reason);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
        }

    }

    private class ViewHolder1{
        TextView tv_work_unit;
        TextView tv_user;
        TextView tv_unit_address;
        TextView tv_type;
        TextView tv_brand;
        TextView tv_model;
        TextView tv_engineer;
        TextView tv_install_unit;
        TextView tv_time;
        public ViewHolder1(View view){
            tv_work_unit = (TextView) view.findViewById(R.id.tv_work_unit);
            tv_user = (TextView) view.findViewById(R.id.tv_user);
            tv_unit_address = (TextView) view.findViewById(R.id.tv_unit_address);
            tv_type = (TextView) view.findViewById(R.id.tv_type);
            tv_brand = (TextView) view.findViewById(R.id.tv_brand);
            tv_model = (TextView) view.findViewById(R.id.tv_model);
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

package com.lwc.shanxiu.module.order.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.Malfunction;
import com.lwc.shanxiu.module.bean.Order;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * @author 何栋
 * @version 1.0
 * @date 2017/3/9 16:27
 * @email 294663966@qq.com
 * 我的订单
 */
public class OrderListAdapter extends SuperAdapter<Order> {

    private Context context;
    public OrderListAdapter(Context context, List<Order> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Order item) {
        TextView textView = holder.findViewById(R.id.txtId);
        textView.setText("订单号：" + item.getOrderId());// 订单号
        holder.setText(R.id.txtTime, item.getCreateTime());
        holder.setText(R.id.txtMachineType, item.getDeviceTypeName()); //设备类型
        if (item.getOrderType().equals("3")) {
            holder.setVisibility(R.id.rl_company, View.GONE);
            holder.setVisibility(R.id.rl_order_type, View.VISIBLE);
            holder.setText(R.id.txtOrderType, "售后订单."+(item.getIsWarranty()==0?"保内":"保外"));
        } else {
            if (!TextUtils.isEmpty(item.getUserCompanyName())) {
                holder.setVisibility(R.id.rl_company, View.VISIBLE);
                holder.setText(R.id.txtUnit, item.getUserCompanyName());
            } else {
                holder.setVisibility(R.id.rl_company, View.GONE);
                holder.setText(R.id.txtUnit, "暂无");
            }
        }
        if (item.getIsSecrecy() == 1) {
            textView.setCompoundDrawables(null, null, Utils.getDrawable((Activity) this.context, R.drawable.renzheng), null);
        } else {
            textView.setCompoundDrawables(null, null, null, null);
        }
        List<Malfunction> orderRepairs = item.getOrderRepairs();
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
            holder.setText(R.id.txtMachineType, typeName);
        } else {
            holder.setText(R.id.txtMachineType, item.getDeviceTypeName()+"->"+item.getReqairName());
        }
        if (!TextUtils.isEmpty(item.getOrderDescription())) {
            holder.setText(R.id.txtReason, item.getOrderDescription().trim());
            holder.setVisibility(R.id.rl_ms, View.VISIBLE);
        } else {
            holder.setVisibility(R.id.rl_ms, View.GONE);
            holder.setText(R.id.txtReason, "暂无");// 故障原因
        }

        if (!TextUtils.isEmpty(item.getOrderContactName())) {
            holder.setText(R.id.txtOrderName, item.getOrderContactName());
        } else if (!TextUtils.isEmpty(item.getUserRealname())) {
            holder.setText(R.id.txtOrderName, item.getUserRealname());
        } else {
            holder.setText(R.id.txtOrderName, "暂无");
        }

        switch (item.getStatusId()){
            case Order.STATUS_YIWANCHENG:
                holder.setText(R.id.txtStatus,"待评价");
//                holder.setTextColor(R.id.txtStatus, getContext().getResources().getColor(R.color.stay_evaluate));
                break;
            case Order.STATUS_YIPINGJIA:
                holder.setText(R.id.txtStatus,"已评价");
                break;
            default:
                if (item.getStatusName() != null && !item.getStatusName().trim().equals("")) {
                    holder.setText(R.id.txtStatus, item.getStatusName());
                } else {
                    holder.setVisibility(R.id.txtStatus, View.GONE);
                }
                break;

        }
    }
}

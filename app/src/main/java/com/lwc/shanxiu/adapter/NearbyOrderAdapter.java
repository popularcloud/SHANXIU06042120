package com.lwc.shanxiu.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.bean.Location;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.Malfunction;
import com.lwc.shanxiu.module.bean.Order;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * @author 何栋
 * @version 1.0
 * @date 2017/4/19 13:22
 * @email 294663966@qq.com
 * 附近订单
 */
public class NearbyOrderAdapter extends SuperAdapter<Order> {

    private Context context;
    public NearbyOrderAdapter(Context context, List<Order> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Order item) {
        TextView textView = holder.findViewById(R.id.txt_id);
        textView.setText("订单号:" + item.getOrderId());// 订单号
        holder.setText(R.id.txt_time, item.getCreateTime());// 下单时间
        if (!TextUtils.isEmpty(item.getOrderType()) && item.getOrderType().equals("3")) {
            holder.setVisibility(R.id.rl_repair_company, View.GONE);
            holder.setVisibility(R.id.rl_company, View.GONE);
            holder.setVisibility(R.id.rl_order_type, View.VISIBLE);
            holder.setText(R.id.txtOrderType, "售后订单."+(item.getIsWarranty()==0?"保内":"保外"));
        } else {
            holder.setVisibility(R.id.rl_order_type, View.GONE);
            if (!TextUtils.isEmpty(item.getUserCompanyName())) {   //申请订单公司单位
                holder.setVisibility(R.id.rl_company, View.VISIBLE);
                holder.setText(R.id.txtUnit, item.getUserCompanyName());
            } else {
                holder.setText(R.id.txtUnit, "暂无");
                holder.setVisibility(R.id.rl_company, View.GONE);
            }
            if (!TextUtils.isEmpty(item.getRepairCompanyName())) {
                holder.setVisibility(R.id.rl_repair_company, View.VISIBLE);
                holder.setText(R.id.txtRepairCompanyName, item.getRepairCompanyName());
            } else {
                holder.setVisibility(R.id.rl_repair_company, View.GONE);
            }
        }

        if (item.getIsSecrecy() == 1) {
            textView.setCompoundDrawables(null, null, Utils.getDrawable((Activity) this.context, R.drawable.renzheng), null);
        } else {
            textView.setCompoundDrawables(null, null, null, null);
        }
        Location location = SharedPreferencesUtils.getInstance(context).loadObjectData(Location.class);
        if (location == null){
            location = new Location();
        }
        if (!TextUtils.isEmpty(item.getOrderContactName())) {
            //申请订单人名称
            holder.setText(R.id.txtOrderName, item.getOrderContactName());
        } else {
            holder.setText(R.id.txtUnit, "暂无");
        }

        LatLng latLng2 = new LatLng(Double.parseDouble(item.getOrderLatitude()), Double.parseDouble(item.getOrderLongitude()));
        float distance = AMapUtils.calculateLineDistance(
                new LatLng(location.getLatitude(), location.getLongitude()), latLng2);
        holder.setText(R.id.txtDistance, (distance > 1000 ? Utils.chu(distance+"", 1000+"") + "km" : (int)distance + "m"));

//        if (!TextUtils.isEmpty(item.getDeviceTypeName()) && !TextUtils.isEmpty(item.getReqairName())) {
//            holder.setText(R.id.txt_machine_type, item.getDeviceTypeName() +"->"+item.getReqairName());// 维修类型;
//        } else {
//            holder.setText(R.id.txt_machine_type, item.getDeviceTypeName());
//        }
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
            holder.setText(R.id.txt_machine_type, typeName);
        }

        if (!TextUtils.isEmpty(item.getOrderDescription())) {
            holder.setText(R.id.txt_reason, item.getOrderDescription());// 故障描述
        } else {
            if (!TextUtils.isEmpty(item.getReqairName())) {
                holder.setText(R.id.txt_reason, item.getReqairName());
            } else {
                holder.setText(R.id.txt_reason, "暂无");// 故障原因
            }
        }

    }
}

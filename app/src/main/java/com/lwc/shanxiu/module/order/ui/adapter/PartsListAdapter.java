package com.lwc.shanxiu.module.order.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.Order;
import com.lwc.shanxiu.module.bean.Parts;
import com.lwc.shanxiu.module.order.ui.activity.PartsListActivity;
import com.lwc.shanxiu.view.TileButton;

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
public class PartsListAdapter extends SuperAdapter<Parts> {

    private PartsListActivity activity;
    private Context context;
    public PartsListAdapter(Context context, List<Parts> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    public void setActivity(PartsListActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Parts item) {
        TextView textView = holder.findViewById(R.id.txtName);
        textView.setText("设备名称：" + item.getPartsName());
        holder.setText(R.id.txtType, "适用机型："+item.getApplicableModel());
        holder.setText(R.id.txtXinghao, "型号："+item.getPartsModel());
        holder.setText(R.id.txtMoney, "价格："+Utils.chu(item.getPartsPrice(), "100")+"元");
        holder.setText(R.id.txtNumber, ""+item.getNumber());
        TileButton but_jia = holder.findViewById(R.id.but_jia);
        TileButton but_jian = holder.findViewById(R.id.but_jian);
        but_jia.setTag(layoutPosition);
        but_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                if (activity!=null)
                activity.updateNumber(1, position);
            }
        });
        but_jian.setTag(layoutPosition);
        but_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                if (activity != null)
                activity.updateNumber(2, position);
            }
        });
    }
}

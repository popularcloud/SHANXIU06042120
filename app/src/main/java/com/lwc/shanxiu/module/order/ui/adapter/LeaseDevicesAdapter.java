package com.lwc.shanxiu.module.order.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.bean.DevicesPropertyBean;
import com.lwc.shanxiu.module.bean.Malfunction;
import com.lwc.shanxiu.module.bean.Order;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;


public class LeaseDevicesAdapter extends SuperAdapter<DevicesPropertyBean> {

    private Context context;

    public LeaseDevicesAdapter(Context context, List<DevicesPropertyBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, DevicesPropertyBean item) {
        TextView textView = holder.findViewById(R.id.txtId);
        holder.setText(R.id.tv_title_header, item.getDeviceKey());
        holder.setText(R.id.tv_title_content, item.getDeviceValue()); //设备类型
    }
}

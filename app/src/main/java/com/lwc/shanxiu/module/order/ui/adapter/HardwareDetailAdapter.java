package com.lwc.shanxiu.module.order.ui.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.partsLib.ui.bean.PartsDetailBean;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

public class HardwareDetailAdapter extends SuperAdapter<PartsDetailBean> {

    private Context context;
    private boolean isBig = true;
    public HardwareDetailAdapter(Context context, List<PartsDetailBean> items, int layoutResId,boolean isBig) {
        super(context, items, layoutResId);
        this.context = context;
        this.isBig = isBig;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, PartsDetailBean item) {

        holder.setText(R.id.tv_title,item.getAccessoriesName()+"("+item.getAccessoriesNum()+")");
        holder.setText(R.id.tv_price, Utils.getMoney(Utils.chu(item.getAccessoriesPrice(),"100"))+"元");
        if(!isBig){
            ((TextView)holder.findViewById(R.id.tv_title)).setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
            ((TextView)holder.findViewById(R.id.tv_price)).setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
        }
    }
}

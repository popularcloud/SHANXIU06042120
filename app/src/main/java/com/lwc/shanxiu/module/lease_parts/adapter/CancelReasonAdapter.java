package com.lwc.shanxiu.module.lease_parts.adapter;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.lease_parts.widget.OnCommClickCallBack;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

public class CancelReasonAdapter extends SuperAdapter<String> {

    private Context context;
    private OnCommClickCallBack onCommClickCallBack;
    private int presentPosition = -1;
    public CancelReasonAdapter(Context context, List<String> items, int layoutResId, OnCommClickCallBack clickCallBack) {
        super(context, items, layoutResId);
        this.context = context;
        this.onCommClickCallBack = clickCallBack;
    }

    public void setItemChecked(int position){
        presentPosition = position;
        //notifyDataSetChanged();
    }
    @Override
    public void onBind(SuperViewHolder holder, int viewType, final int layoutPosition, String item) {

        TextView tv_qb_pay = holder.itemView.findViewById(R.id.tv_qb_pay);
        CheckBox cb_box = holder.itemView.findViewById(R.id.cb_box);
        tv_qb_pay.setText(item);

        cb_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && onCommClickCallBack != null){
                    onCommClickCallBack.onCommClick(layoutPosition);
                }
            }
        });

        if(presentPosition == layoutPosition){
            cb_box.setChecked(true);
        }else{
            cb_box.setChecked(false);
        }

    }
}

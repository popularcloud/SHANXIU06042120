package com.lwc.shanxiu.module.lease_parts.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.bean.Repairs;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.lease_parts.activity.LeaseGoodsListActivity;
import com.lwc.shanxiu.module.lease_parts.viewholder.ItemRepairTypeViewHolder;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.utils.IntentUtil;

import java.util.List;

public class LeaseGoodTypeAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<Repairs> repairsList; //所有维修类型集合

    public LeaseGoodTypeAdapter(Context context, List<Repairs> repairsList) {
        mContext = context;
        this.repairsList = repairsList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View convertView = View.inflate(mContext, R.layout.item_lease_type, null);
            ItemRepairTypeViewHolder holder = new ItemRepairTypeViewHolder(convertView);
        return holder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItemRepairTypeViewHolder holder1 = (ItemRepairTypeViewHolder) holder;
        holder1.item_name.setText(repairsList.get(position).getDeviceTypeName() + "");
        ImageLoaderUtil.getInstance().displayFromNet(mContext, repairsList.get(position).getDeviceTypeIcon(),  holder1.item_image);

        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Bundle bundle = new Bundle();
                    bundle.putString("typeId",repairsList.get(position).getDeviceTypeId());
                    IntentUtil.gotoActivity(mContext, LeaseGoodsListActivity.class,bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return repairsList==null?0:repairsList.size();
    }

}




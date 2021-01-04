package com.lwc.shanxiu.module.authentication.adapter;

import android.content.Context;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.authentication.bean.ExaminationBean;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

public class ExaminationAdapter extends SuperAdapter<ExaminationBean> {

    private Context context;
    public ExaminationAdapter(Context context, List<ExaminationBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, ExaminationBean item) {

        holder.setText(R.id.tv_title,item.getPaperName());
        switch (item.getIsPass()){
            case 0:
                holder.setText(R.id.tv_status,"未考试");
                break;
            case 1:
                holder.setText(R.id.tv_status,"已通过");
                break;
            case 2:
                holder.setText(R.id.tv_status,"未通过");
                break;

        }
        holder.setText(R.id.tv_time,item.getCreateTime());
    }
}

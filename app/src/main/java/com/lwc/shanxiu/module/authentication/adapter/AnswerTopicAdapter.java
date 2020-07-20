package com.lwc.shanxiu.module.authentication.adapter;

import android.content.Context;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.authentication.bean.SubmitTopicBean;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

public class AnswerTopicAdapter extends SuperAdapter<String> {

    private Context context;
    private int presentPosition = -1;
    private List<SubmitTopicBean> submitTopicBeans;
    public AnswerTopicAdapter(Context context, List<String> items,List<SubmitTopicBean> submitTopicBeans, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
        this.submitTopicBeans = submitTopicBeans;
    }

    public void setItemChecked(int position){
        presentPosition = position;
        //notifyDataSetChanged();
    }
    @Override
    public void onBind(SuperViewHolder holder, int viewType, final int layoutPosition, String item) {
        holder.setText(R.id.tv_title,String.valueOf(layoutPosition+1));

        boolean isHas = false;
        for(int i = 0;i < submitTopicBeans.size();i++){
            SubmitTopicBean submitTopicBean = submitTopicBeans.get(i);
            if(submitTopicBean.getNum()-1 == layoutPosition){
                holder.setBackgroundResource(R.id.tv_title,R.drawable.circular_blue_shape);
                holder.setTextColor(R.id.tv_title,context.getResources().getColor(R.color.white));
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}

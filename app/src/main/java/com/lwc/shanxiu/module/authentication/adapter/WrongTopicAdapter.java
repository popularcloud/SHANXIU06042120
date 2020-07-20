package com.lwc.shanxiu.module.authentication.adapter;

import android.content.Context;
import android.view.View;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.authentication.bean.WrongTopicBean;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

public class WrongTopicAdapter extends SuperAdapter<WrongTopicBean> {

    private Context context;
    public WrongTopicAdapter(Context context, List<WrongTopicBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, WrongTopicBean item) {

        String topicType = "单选题";
        switch (item.getType()){
            case 1:
                topicType = "(单选题)";
                holder.setText(R.id.tv_answerA_txt,item.getA());
                holder.setText(R.id.tv_answerB_txt,item.getB());
                holder.setText(R.id.tv_answerC_txt,item.getC());
                holder.setText(R.id.tv_answerD_txt,item.getD());
                holder.setVisibility(R.id.ll_c,View.VISIBLE);
                holder.setVisibility(R.id.ll_d,View.VISIBLE);
                break;
            case 2:
                topicType = "(多选题)";
                holder.setText(R.id.tv_answerA_txt,item.getA());
                holder.setText(R.id.tv_answerB_txt,item.getB());
                holder.setText(R.id.tv_answerC_txt,item.getC());
                holder.setText(R.id.tv_answerD_txt,item.getD());
                holder.setVisibility(R.id.ll_c,View.VISIBLE);
                holder.setVisibility(R.id.ll_d,View.VISIBLE);
                break;
            case 3:
                topicType = "(判断题)";
                holder.setText(R.id.tv_answerA_txt,item.getA());
                holder.setText(R.id.tv_answerB_txt,item.getB());
                holder.setVisibility(R.id.ll_c,View.GONE);
                holder.setVisibility(R.id.ll_d,View.GONE);
                break;
        }
        holder.setText(R.id.tv_title,topicType+item.getTitleName());
    }
}

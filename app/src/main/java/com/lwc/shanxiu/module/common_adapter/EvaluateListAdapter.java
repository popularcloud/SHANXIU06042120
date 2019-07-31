package com.lwc.shanxiu.module.common_adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.RatingBar;


import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.bean.Evaluate;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;


/**
 * Created by 何栋 on 2017/10/15.
 * 294663966@qq.com
 * 评价
 */
public class EvaluateListAdapter extends SuperAdapter<Evaluate> {

    private Context context;
    public EvaluateListAdapter(Context context, List<Evaluate> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Evaluate item) {
        RatingBar ratingBar = holder.findViewById(R.id.ratingBar);
        ratingBar.setMax(500);
        ratingBar.setProgress(item.getSynthesizeGrade() * 100);
        holder.setText(R.id.txtDate, item.getCreateTime());  //订单号
        holder.setText(R.id.txtContent, "评价内容："+item.getCommentContent());
        if (!TextUtils.isEmpty(item.getOrderContactName())) {
            holder.setText(R.id.txtName, item.getOrderContactName().substring(0, 1)+"**");
        }
    }
}

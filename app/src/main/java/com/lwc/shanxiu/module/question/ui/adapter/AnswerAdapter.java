package com.lwc.shanxiu.module.question.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.message.bean.KnowledgeBaseBean;
import com.lwc.shanxiu.module.question.bean.AnswerItemBean;
import com.lwc.shanxiu.utils.TimeUtil;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author younge
 * @date 2019/6/4 0004
 * @email 2276559259@qq.com
 */
public class AnswerAdapter extends SuperAdapter<AnswerItemBean>{

    private Context context;
    public AnswerAdapter(Context context, List<AnswerItemBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, AnswerItemBean item) {
        holder.setText(R.id.tv_title, item.getQuesionTitle());
        holder.setText(R.id.tv_desc, item.getAnswerDetail());
        holder.setText(R.id.tv_view_count,item.getBrowseNum()+"次");

        if(!TextUtils.isEmpty(item.getCreateTime())){
            holder.setText(R.id.tv_create_time, item.getCreateTime().substring(0,10));
        }

        TextView tv_status = holder.findViewById(R.id.tv_status);
        if(item.getIsSelect() == 1){
            tv_status.setVisibility(View.VISIBLE);
        }else{
            tv_status.setVisibility(View.GONE);
        }
    }
}

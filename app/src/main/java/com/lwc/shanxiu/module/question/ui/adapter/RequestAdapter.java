package com.lwc.shanxiu.module.question.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Dimension;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.message.bean.KnowledgeBaseBean;
import com.lwc.shanxiu.module.question.bean.QuestionIndexBean;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.utils.TimeUtil;
import com.lwc.shanxiu.widget.TagsLayout;

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
public class RequestAdapter extends SuperAdapter<QuestionIndexBean>{

    private Context context;
    public RequestAdapter(Context context, List<QuestionIndexBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, QuestionIndexBean item) {
        holder.setText(R.id.tv_title, item.getQuesionTitle());
        holder.setText(R.id.tv_desc, item.getQuesionDetail());

        holder.setText(R.id.tv_view_count,item.getBrowseNum()+"次");
        holder.setText(R.id.tv_comment_count,item.getQuesionMessage()+"个");

        if(!TextUtils.isEmpty(item.getCreateTime())){
            holder.setText(R.id.tv_create_time, item.getCreateTime().substring(0,10));
        }
    }
}

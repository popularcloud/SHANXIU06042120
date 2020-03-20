package com.lwc.shanxiu.module.question.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.question.bean.QuestionIndexBean;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
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
public class RequestQuestionAdapter extends SuperAdapter<QuestionIndexBean>{

    private Context context;
    public RequestQuestionAdapter(Context context, List<QuestionIndexBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, QuestionIndexBean item) {
        holder.setText(R.id.tv_title, item.getQuesionTitle());

        TextView tv_desc = holder.findViewById(R.id.tv_desc);
        tv_desc.setText(item.getQuesionDetail());
       // holder.setText(R.id.tv_desc, item.getQuesionDetail());

        ImageView iv_desc_img = holder.findViewById(R.id.iv_desc_img);

        holder.setText(R.id.tv_view_count,item.getBrowseNum()+"次");
        holder.setText(R.id.tv_comment_count,item.getQuesionMessage()+"个");

        if(!TextUtils.isEmpty(item.getCreateTime())){
            holder.setText(R.id.tv_create_time, item.getCreateTime().substring(0,10));
        }
        if(!TextUtils.isEmpty(item.getQuesionImg())){
            iv_desc_img.setVisibility(View.VISIBLE);
            String[] imgs = item.getQuesionImg().split(",");
            ImageLoaderUtil.getInstance().displayFromNetDCircular(context,imgs[0],iv_desc_img,R.drawable.image_default_picture);
            tv_desc.setMaxLines(4);
        }else{
            iv_desc_img.setVisibility(View.GONE);
            tv_desc.setMaxLines(2);
        }
    }
}

package com.lwc.shanxiu.module.message.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Dimension;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.message.bean.KnowledgeBaseBean;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.utils.TimeUtil;
import com.lwc.shanxiu.widget.AutoLayoutTagsGroup;
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
public class KnowledgeBaseAdapter extends SuperAdapter<KnowledgeBaseBean>{

    private Context context;
    public KnowledgeBaseAdapter(Context context, List<KnowledgeBaseBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, KnowledgeBaseBean item) {
        holder.setText(R.id.tv_title, Html.fromHtml(item.getKnowledgeTitle()));
        holder.setText(R.id.tv_desc,  Html.fromHtml(item.getKnowledgePaper()));
        holder.setText(R.id.tv_view_count,item.getBrowseNum()+"次");

        if(!TextUtils.isEmpty(item.getCreateTime())){
            holder.setText(R.id.tv_create_time, item.getCreateTime().substring(0,10));
        }

        ImageView iv_header = holder.findViewById(R.id.iv_header);
        ImageView iv_is_video = holder.findViewById(R.id.iv_is_video);
        AutoLayoutTagsGroup tl_tags = holder.findViewById(R.id.tl_tags);
        RelativeLayout rl_header = holder.findViewById(R.id.rl_header);


        if(!TextUtils.isEmpty(item.getLabelName())){
            tl_tags.setVisibility(View.VISIBLE);
            String[] tags =item.getLabelName().split(",");
            tl_tags.removeAllViews();
            for (int i = 0; i < tags.length; i++) {
                if(i > 3){
                    break;
                }
                TextView textView = new TextView(mContext);
                textView.setText(tags[i]);
                textView.setTextColor(Color.parseColor("#1481ff"));
                textView.setTextSize(Dimension.SP,12);
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(5,0,5,0);
                textView.setBackgroundResource(R.drawable.round_square_blue_line);
         /*       int index = tags[i].indexOf("：");
                String tagStr = tags[i].substring(index+1);*/
                textView.setText(tags[i]);
                tl_tags.addView(textView);
            }
        }else{
            tl_tags.setVisibility(View.GONE);
        }

        if(item.getKnowledgeImageType() != 3){ //图片类型
            if(!TextUtils.isEmpty(item.getKnowledgeImage())){
                String[] images = item.getKnowledgeImage().split(",");
                rl_header.setVisibility(View.GONE);
                ImageLoaderUtil.getInstance().displayFromNetD(context,images[0],iv_header);
                iv_is_video.setVisibility(View.GONE);
            }else{
                rl_header.setVisibility(View.GONE);
            }

        }else{  //视频类型
            if(!TextUtils.isEmpty(item.getKnowledgeImage())){
                String[] images = item.getKnowledgeImage().split(",");
                rl_header.setVisibility(View.VISIBLE);
                ImageLoaderUtil.getInstance().displayFromNetD(context,images[0],iv_header);
                iv_is_video.setVisibility(View.VISIBLE);
            }else{
                rl_header.setVisibility(View.GONE);
            }
        }
    }
}

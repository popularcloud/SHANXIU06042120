package com.lwc.shanxiu.module.message.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.message.bean.KnowledgeBaseBean;
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
public class KnowledgeBaseAdapter extends SuperAdapter<KnowledgeBaseBean>{

    private Context context;
    public KnowledgeBaseAdapter(Context context, List<KnowledgeBaseBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, KnowledgeBaseBean item) {
        holder.setText(R.id.tv_title, item.getKnowledgeTitle());
        holder.setText(R.id.tv_desc, item.getKnowledgePaper());
        holder.setText(R.id.tv_view_count,item.getBrowseNum()+"次");

        //String转date
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
        Date date= null;
        try {
            date = sdf.parse(item.getCreateTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateStr = TimeUtil.getTimeFormatText(date);
        holder.setText(R.id.tv_create_time, dateStr);
        ImageView iv_header = holder.findViewById(R.id.iv_header);
        ImageView iv_is_video = holder.findViewById(R.id.iv_is_video);
        RelativeLayout rl_header = holder.findViewById(R.id.rl_header);
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

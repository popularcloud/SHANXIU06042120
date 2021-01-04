package com.lwc.shanxiu.module.authentication.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.authentication.bean.TrainBean;
import com.lwc.shanxiu.utils.ImageLoaderUtil;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

public class TrainAdapter extends SuperAdapter<TrainBean> {

    private Context context;
    private String presentVideo;
    public TrainAdapter(Context context, List<TrainBean> items, int layoutResId,String presentVideo) {
        super(context, items, layoutResId);
        this.context = context;
        this.presentVideo = presentVideo;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, TrainBean item) {

        if(!TextUtils.isEmpty(presentVideo) && item.getId().equals(presentVideo)){
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.height = 0;
            holder.itemView.setLayoutParams(layoutParams);
            holder.itemView.getRootView().setVisibility(View.GONE);
        }
        holder.setText(R.id.tv_title,item.getVideoName());
        holder.setText(R.id.tv_time,item.getDate());

        TextView tv_status = holder.itemView.findViewById(R.id.tv_status);
        if("1".equals(item.getIsPass())){
            tv_status.setText("已看完");
            holder.setTextColor(R.id.tv_title,context.getResources().getColor(R.color.gray_99));
            tv_status.setTextColor(context.getResources().getColor(R.color.red_money));
            Drawable drawable = getContext().getResources().getDrawable(R.drawable.ic_see_time_red);
            drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
            tv_status.setCompoundDrawables(drawable,null,null,null);
        }else{
            holder.setTextColor(R.id.tv_title,context.getResources().getColor(R.color.black));
            tv_status.setTextColor(context.getResources().getColor(R.color.btn_blue_nomal));
            Drawable drawable = getContext().getResources().getDrawable(R.drawable.ic_see_time);
            drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
            tv_status.setCompoundDrawables(drawable,null,null,null);
            if(TextUtils.isEmpty(item.getReadTime()) && !TextUtils.isEmpty(item.getTime())){
                tv_status.setText(""+Utils.getTimeMillTwo((Integer.valueOf(item.getTime()) * 1000)));
            }else{
                String times = Utils.jian(item.getTime() , item.getReadTime());
                tv_status.setText("剩余"+Utils.getTimeMillTwo((Integer.valueOf(times) * 1000)));
            }
        }

        ImageView imageView = holder.itemView.findViewById(R.id.iv_header);

        ImageLoaderUtil.getInstance().displayFromNetDCircular8(context,item.getImage(),imageView,R.drawable.img_default_load);
    }
}

package com.lwc.shanxiu.module.authentication.adapter;

import android.content.Context;
import android.text.TextUtils;
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
    public TrainAdapter(Context context, List<TrainBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, TrainBean item) {

        holder.setText(R.id.tv_title,item.getVideoName());
        holder.setText(R.id.tv_time,item.getCreateTime());

        TextView tv_status = holder.itemView.findViewById(R.id.tv_status);
        if("1".equals(item.getIsPass())){
            tv_status.setText("已看完");
        }else{
            if(TextUtils.isEmpty(item.getReadTime()) && !TextUtils.isEmpty(item.getTime())){
                tv_status.setText(""+Utils.getTimeMill((Integer.valueOf(item.getTime()) * 1000)));
            }else{
                String times = Utils.jian(item.getTime() , item.getReadTime());
                tv_status.setText("剩余"+Utils.getTimeMill((Integer.valueOf(times) * 1000)));
            }
        }

        ImageView imageView = holder.itemView.findViewById(R.id.iv_header);

        ImageLoaderUtil.getInstance().displayFromNetDCircular8(context,item.getImage(),imageView,R.drawable.img_default_load);
    }
}

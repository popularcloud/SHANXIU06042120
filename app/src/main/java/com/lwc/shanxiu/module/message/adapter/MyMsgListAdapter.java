package com.lwc.shanxiu.module.message.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.message.bean.MyMsg;
import com.lwc.shanxiu.utils.ImageLoaderUtil;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Created by 何栋 on 2017/10/15.
 * 294663966@qq.com
 * 我的订单
 */
public class MyMsgListAdapter extends SuperAdapter<MyMsg> {

    private final ImageLoaderUtil imageLoaderUtil;
    private Context context;
    public MyMsgListAdapter(Context context, List<MyMsg> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
        imageLoaderUtil = ImageLoaderUtil.getInstance();
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, MyMsg item) {

        holder.setText(R.id.tv_title, item.getMessageTitle());
        holder.setText(R.id.tv_time, item.getCreateTime());
        if (!TextUtils.isEmpty(item.getMessageType()) && item.getMessageType().equals("1")) {
            holder.setImageResource(R.id.iv_icon, R.drawable.newstyle_mixiu);
        } else if (!TextUtils.isEmpty(item.getMessageType()) && item.getMessageType().equals("2")) {
            holder.setImageResource(R.id.iv_icon, R.drawable.newstyle_information);
        } else if (!TextUtils.isEmpty(item.getMessageType()) && item.getMessageType().equals("3")) {
            holder.setImageResource(R.id.iv_icon, R.drawable.newstyle_gift);
        } else {
            holder.setImageResource(R.id.iv_icon, R.drawable.newstyle_oder);
        }
        if (item.getHasMessage()) {
            holder.setVisibility(R.id.iv_new_msg, View.VISIBLE);
        } else {
            holder.setVisibility(R.id.iv_new_msg, View.INVISIBLE);
        }
        if (!TextUtils.isEmpty(item.getMessageImage()) && item.getMessageImage().length() > 5) {
            holder.setVisibility(R.id.iv_imgs, View.VISIBLE);
            imageLoaderUtil.displayFromNet(context, item.getMessageImage(), (ImageView) holder.findViewById(R.id.iv_imgs));
        } else {
            holder.setVisibility(R.id.iv_imgs, View.GONE);
        }
        holder.setText(R.id.tv_desc, item.getMessageContent()); 
    }
}

package com.lwc.shanxiu.module.common_adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.interf.OnTagClickCallBack;
import com.lwc.shanxiu.module.bean.Tag;

import java.util.ArrayList;

/**
 * @author younge
 * @date 2019/3/27 0027
 * @email 2276559259@qq.com
 */
public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.ViewHolder>{

    private ArrayList<Tag> mTags;
    private Context context;
    private OnTagClickCallBack onTagClickCallBack;

    public TagsAdapter(Context context, ArrayList<Tag> mTags, OnTagClickCallBack onTagClickCallBack){
        this.mTags = mTags;
        this.context = context;
        this.onTagClickCallBack = onTagClickCallBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = View.inflate(context, R.layout.item_tags, null);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Tag tag = mTags.get(position);
        holder.tv_content.setText(tag.getLabelName());

        if(tag.isChecked()){
            holder.tv_content.setBackgroundResource(R.drawable.button_blue_cirle_fill);
            holder.tv_content.setTextColor(Color.WHITE);
        }else{
            holder.tv_content.setBackgroundResource(R.drawable.button_gray_cirle_shape);
            holder.tv_content.setTextColor(Color.BLACK);
        }

        if(onTagClickCallBack != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTagClickCallBack.onTagClickListener(tag);
                }
            });
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mTags == null?0:mTags.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_content;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}

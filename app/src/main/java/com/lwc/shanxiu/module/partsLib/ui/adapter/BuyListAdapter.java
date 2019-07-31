package com.lwc.shanxiu.module.partsLib.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.interf.BuyListItemInterface;
import com.lwc.shanxiu.module.partsLib.ui.bean.PartsBean;
import com.lwc.shanxiu.widget.DragListItem;
import com.lwc.shanxiu.widget.TagsLayout;

import java.util.List;

/**
 * @author younge
 * @date 2018/12/20 0020
 * @email 2276559259@qq.com
 */
public class BuyListAdapter extends RecyclerView.Adapter<BuyListAdapter.BuyListViewHolder>{


    private final List<PartsBean> items;
    private final LayoutInflater layoutInflater;
    private BuyListItemInterface buyListItemInterface;
    private Context context;

    public BuyListAdapter(Context context, List<PartsBean> items, BuyListItemInterface buyListItemInterface) {
        this.buyListItemInterface = buyListItemInterface;
        this.context = context;
        this.items = items;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public BuyListAdapter.BuyListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_buy_list, parent, false);
      /*  dragListItem = new DragListItem(context);
        dragListItem.setContentView(view);*/
        BuyListViewHolder viewHolder = new BuyListViewHolder(view);
        /*dragListItem.rollBack();
        dragListItem.setOnClickListener(new View.OnClickListener() {//给条目添加点击事件
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "点击了Item", Toast.LENGTH_SHORT).show();
            }
        });*/
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BuyListAdapter.BuyListViewHolder holder,final int position) {



        /*holder.hide_view.setOnClickListener(new View.OnClickListener() {//给隐藏的布局设置点击事件 比如点击删除功能
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "删除", Toast.LENGTH_SHORT).show();
                if(buyListItemInterface != null){
                    buyListItemInterface.onItemDel(position);
                }
            }
        });*/
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return items == null?0:items.size();
    }

   public class BuyListViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public CheckBox cbAddList;
        public TextView tv_prices;
        public TagsLayout tl_tags;
        public ImageView iv_display;

        public BuyListViewHolder(View holder){
            super(holder);
            tv_title = (TextView) holder.findViewById(R.id.tv_title);
            cbAddList = (CheckBox) holder.findViewById(R.id.cb_isAdd);
            tv_prices = (TextView) holder.findViewById(R.id.tv_prices);
            tl_tags = (TagsLayout) holder.findViewById(R.id.tl_tags);
            iv_display = (ImageView) holder.findViewById(R.id.iv_display);
        }
    }
}

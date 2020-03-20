package com.lwc.shanxiu.module.order.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.bean.FeeStandardBean;

import java.util.List;

/**
 * @author younge
 * @date 2019/5/24 0024
 * @email 2276559259@qq.com
 */
public class FeeStandardAdapter extends BaseExpandableListAdapter{

    private List<FeeStandardBean.DataBeanX> list;
    private Context mContext;

    public FeeStandardAdapter(Context ctx, List<FeeStandardBean.DataBeanX> list){
        this.list = list;
        this.mContext = ctx;
    }

    @Override
    public int getGroupCount() {
        return list == null?0:list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getData().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getData().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView==null)
            convertView=View.inflate(mContext, R.layout.item_fee_standard_parent, null);
        TextView groupTv=(TextView) convertView.findViewById(R.id.tv_typeName);
        TextView priceTv=(TextView) convertView.findViewById(R.id.tv_price);
        if(groupPosition == 0){
            priceTv.setText("价格");
        }else{
            priceTv.setText("");
        }
        groupTv.setText(list.get(groupPosition).getTypeName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(mContext, R.layout.item_fee_standard_child, null);
            holder.title=(TextView) convertView.findViewById(R.id.tv_title);
            holder.price=(TextView) convertView.findViewById(R.id.tv_price);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }
        holder.title.setText(list.get(groupPosition).getData().get(childPosition).getExampleName());

        if(list.get(groupPosition).getData().get(childPosition).getIsFixation() == 1){  //价格固定
            holder.price.setText(String.valueOf(list.get(groupPosition).getData().get(childPosition).getMaintainCost()/100)+"元");
        }else{ //价格可变
            holder.price.setText(String.valueOf(list.get(groupPosition).getData().get(childPosition).getMaintainCost()/100)+"元起");
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private class ViewHolder{
        private TextView title;
        private TextView price;
    }
}

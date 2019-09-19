package com.lwc.shanxiu.module.message.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.message.bean.SearchConditionBean;

import java.util.List;

/**
 * @author younge
 * @date 2019/6/5 0005
 * @email 2276559259@qq.com
 */
public class PopSelectAdapter extends Madapter{

    private Context context;
    private int selectColor = Color.parseColor("#f8f8f8"); //被选中后item的颜色，为了方便，所以在添加set方法
    private LayoutInflater inflater;
    private List<SearchConditionBean.OptionsBean> items;
    private int selectedPosition = -1;


    public PopSelectAdapter(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public List<SearchConditionBean.OptionsBean> getItems() {
        return items;
    }

    @Override
    public void setItems(List<SearchConditionBean.OptionsBean> items) {
        this.items = items;
    }

    @Override
    public void setSelectColor(int color) {
        this.selectColor = color;
    }

    @Override
    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    @Override
    public String getShowKey(int position, String key) {
       if (key.equals("brand_name")){
            return  items.get(position).getBrand_name();
       }else {
            return  items.get(position).getBrand_id();
        }
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PopSelectAdapter.ViewHolder holder;
        if(convertView == null){

            holder = new PopSelectAdapter.ViewHolder();
            convertView = (View)inflater.inflate(R.layout.item_pop_selectlist, parent , false);
            holder.name =(TextView) convertView.findViewById(R.id.tv_name);
            holder.code = (TextView)convertView.findViewById(R.id.tv_id);
            holder.employeesquery = (LinearLayout)convertView.findViewById(R.id.employeesquery);
            convertView.setTag(holder);
        }else{
            holder = (PopSelectAdapter.ViewHolder)convertView.getTag();
        }

        /**
         * 该项被选中时改变背景色
         */
        if(selectedPosition == position){
            holder.employeesquery.setBackgroundColor(selectColor);
            holder.name.setTextColor(Color.parseColor("#1481ff"));
        }else{
            holder.employeesquery.setBackgroundColor(Color.WHITE);
            holder.name.setTextColor(Color.parseColor("#000000"));
        }
        holder.name.setText(items.get(position).getBrand_name());
        holder.code.setText(items.get(position).getBrand_id());  //也可在ITTM里去掉这一项，写在Tag里
        return convertView;

    }

    class ViewHolder{
        TextView name;
        TextView code;
        LinearLayout employeesquery;
    }
}

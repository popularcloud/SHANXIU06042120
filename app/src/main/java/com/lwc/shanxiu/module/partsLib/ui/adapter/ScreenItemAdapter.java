package com.lwc.shanxiu.module.partsLib.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.partsLib.ui.bean.MyScreenItemBean;

import java.util.List;

/**
 * @author younge
 * @date 2018/12/29 0029
 * @email 2276559259@qq.com
 */
public class ScreenItemAdapter extends BaseAdapter{

    private Context context;
    List<MyScreenItemBean> screenItemBeans;
    public ScreenItemAdapter(Context context, List<MyScreenItemBean> screenItemBeans){
        this.context = context;
        this.screenItemBeans = screenItemBeans;
    }
    @Override
    public int getCount() {
        return screenItemBeans==null?0:screenItemBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return screenItemBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_screen, null);
            holder.tv_content = (TextView)convertView.findViewById(R.id.tv_content);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tv_content.setText(screenItemBeans.get(position).getName());
        return convertView;
    }

    static class ViewHolder{
        private TextView tv_content;
    }
}

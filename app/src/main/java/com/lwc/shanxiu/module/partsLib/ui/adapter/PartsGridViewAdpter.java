package com.lwc.shanxiu.module.partsLib.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.bean.PartsTypeBean;
import com.lwc.shanxiu.utils.ImageLoaderUtil;

import java.util.List;


/**
 * @author younge
 * @date 2018/12/19 0019
 * @email 2276559259@qq.com
 */
public class PartsGridViewAdpter extends BaseAdapter{

    private Context context;
    private List<PartsTypeBean> lists;//数据源
    private int mIndex; // 页数下标，标示第几页，从0开始
    private int mPargerSize=8;// 每页显示的最大的数量

    public PartsGridViewAdpter(Context context, List<PartsTypeBean> lists,
                               int mIndex, int mPargerSize) {
        this.context = context;
        this.lists = lists;
        this.mIndex = mIndex;
        this.mPargerSize = mPargerSize;
    }

    /**
     * 先判断数据及的大小是否显示满本页lists.size() > (mIndex + 1)*mPagerSize
     * 如果满足，则此页就显示最大数量lists的个数
     * 如果不够显示每页的最大数量，那么剩下几个就显示几个
     */
    @Override
    public int getCount() {
        return lists.size() > (mIndex + 1) * mPargerSize ?
                mPargerSize : (lists.size() - mIndex*mPargerSize);
    }

    @Override
    public PartsTypeBean getItem(int arg0) {
        return lists.get(arg0 + mIndex * mPargerSize);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0 + mIndex * mPargerSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.index_home_grid_item, null);
            holder.tv_name = (TextView)convertView.findViewById(R.id.item_name);
            holder.iv_nul = (ImageView)convertView.findViewById(R.id.item_image);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        //重新确定position因为拿到的总是数据源，数据源是分页加载到每页的GridView上的
        final int pos = position + mIndex * mPargerSize;//假设mPageSiez
        //假设mPagerSize=8，假如点击的是第二页（即mIndex=1）上的第二个位置item(position=1),那么这个item的实际位置就是pos=9
        holder.tv_name.setText(lists.get(pos).getTypeName() + "");
//        holder.iv_nul.setImageResource();
        ImageLoaderUtil.getInstance().displayFromNet(context, lists.get(pos).getTypeIcon(), holder.iv_nul,R.drawable.image_default_picture);
        //添加item监听
//        convertView.setOnClickListener(new View.OnClickListener() {
//            
//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//                Toast.makeText(context, "您点击了"  + lists.get(pos).getName(), Toast.LENGTH_SHORT).show();
//            }
//        });
        return convertView;
    }
    static class ViewHolder{
        private TextView tv_name;
        private ImageView iv_nul;
    }
}
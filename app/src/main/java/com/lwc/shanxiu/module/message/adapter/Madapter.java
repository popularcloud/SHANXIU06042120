package com.lwc.shanxiu.module.message.adapter;

import android.widget.BaseAdapter;

import com.lwc.shanxiu.module.message.bean.SearchConditionBean;

import java.util.List;

/**
 * @author younge
 * @date 2019/6/5 0005
 * @email 2276559259@qq.com
 */
public abstract class Madapter extends BaseAdapter {
    public abstract List<?> getItems();  //返回ListView的数据集
    public abstract void setItems(List<SearchConditionBean.OptionsBean> items);
    public abstract void setSelectColor(int color);    //修改选中后item的颜色
    public abstract void setSelectedPosition(int selectedPosition); //设置选中项
    public abstract String getShowKey(int position , String key);//获取选中值，必须有这个方法。
}

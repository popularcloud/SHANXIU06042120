package com.lwc.shanxiu.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 抽取的共用的adapter
 * 
 * @Description 仅适用于使用bean对象和list集合的构造方法的适配器，若有需要可以自行另外添加
 * @author 何栋
 * @date 2015年5月24日
 * @Copyright: Copyright (c) 2015 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 */
public abstract class ComAdapter<T> extends BaseAdapter {

	protected Context mContext;
	protected List<T> mDatas;
	private final int mLayoutId;

	/**
	 * 构造方法
	 * 
	 * @version 1.0
	 * @createTime 2015年5月24日,上午11:47:30
	 * @updateTime 2015年5月24日,上午11:47:30
	 * @createAuthor chencong
	 * @updateAuthor chencong
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param context
	 *        上下文对象
	 * @param datas
	 *        数据
	 * @param layoutId
	 *        需要实例化的条目布局
	 */
	public ComAdapter(Context context, List<T> datas, int layoutId) {
		this.mContext = context;
		this.mDatas = datas;
		this.mLayoutId = layoutId;
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public T getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ComViewHolder holder = ComViewHolder.getViewHolder(mContext, convertView, parent, mLayoutId, position);
		convert(holder, getItem(position));
		return holder.getConvertVeiw();
	}

	/**
	 * 子类需要实现的方法
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年5月24日,上午11:45:51
	 * @updateTime 2015年5月24日,上午11:45:51
	 * @createAuthor chencong
	 * @updateAuthor chencong
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param holder
	 * @param t
	 *        bean对象
	 */
	public abstract void convert(ComViewHolder holder, T t);

	public void setData(List<T> datas) {
		this.mDatas = datas;
		notifyDataSetChanged();
	}
}

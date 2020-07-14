package com.lwc.shanxiu.adapter;

import android.content.Context;

import com.lwc.shanxiu.R;

import java.util.List;

public class SimpleItemAdapter extends ComAdapter<String> {


	/**
	 * 构造方法
	 *
	 * @param context  上下文对象
	 * @param datas    数据
	 * @param layoutId
	 * @version 1.0
	 * @createTime 2015年5月24日, 上午11:47:30
	 * @updateTime 2015年5月24日, 上午11:47:30
	 * @createAuthor chencong
	 * @updateAuthor chencong
	 * @updateInfo (此处输入修改内容, 若无修改可不写.)
	 */
	public SimpleItemAdapter(Context context, List<String> datas, int layoutId) {
		super(context, datas, layoutId);
	}

	@Override
	public void convert(ComViewHolder holder, String s) {
		holder.setText(R.id.tv_name,s);
	}
}

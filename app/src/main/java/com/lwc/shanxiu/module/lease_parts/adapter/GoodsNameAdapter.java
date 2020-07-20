package com.lwc.shanxiu.module.lease_parts.adapter;

import android.content.Context;
import android.widget.TextView;


import com.lwc.shanxiu.R;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

public class GoodsNameAdapter extends SuperAdapter<String> {

	private Context mContext;

	public GoodsNameAdapter(Context context, List<String> items, int layoutResId) {
		super(context, items, layoutResId);
		mContext = context;
	}

	@Override
	public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, String item) {
		TextView tv_name = holder.itemView.findViewById(R.id.tv_name);
		tv_name.setText(item);
	}
}

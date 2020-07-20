package com.lwc.shanxiu.module.lease_parts.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.lease_parts.bean.LeaseGoodsTypeSmall;
import com.lwc.shanxiu.utils.ImageLoaderUtil;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

public class RightGoodsAdapter extends SuperAdapter<LeaseGoodsTypeSmall> {

	private Context mContext;

	public RightGoodsAdapter(Context context, List<LeaseGoodsTypeSmall> items, int layoutResId) {
		super(context, items, layoutResId);
		mContext = context;
	}

	@Override
	public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, LeaseGoodsTypeSmall item) {
		TextView tv_name = holder.itemView.findViewById(R.id.tv_name);
		ImageView iv_header = holder.itemView.findViewById(R.id.iv_header);
		tv_name.setText(item.getTypeDetailName());
		ImageLoaderUtil.getInstance().displayFromNet(mContext,item.getTypeDetailIcon(),iv_header, R.drawable.default_portrait_100);
	}
}

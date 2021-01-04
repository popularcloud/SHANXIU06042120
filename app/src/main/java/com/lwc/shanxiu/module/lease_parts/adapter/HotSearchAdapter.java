package com.lwc.shanxiu.module.lease_parts.adapter;

import android.content.Context;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.lease_parts.bean.SearchListWordBean;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

public class HotSearchAdapter extends SuperAdapter<SearchListWordBean.LeaseGoodsKeywordBean> {

	private Context mContext;

	public HotSearchAdapter(Context context, List<SearchListWordBean.LeaseGoodsKeywordBean> items, int layoutResId) {
		super(context, items, layoutResId);
		mContext = context;
	}

	@Override
	public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, SearchListWordBean.LeaseGoodsKeywordBean item) {

		TextView tv_name = holder.itemView.findViewById(R.id.tv_name);
		tv_name.setText(item.getKeyword_name());
		/*if(layoutPosition == selectPos){
			//tv_name.setTextColor(Color.parseColor("#000000"));
			TextPaint tp = tv_name.getPaint();
			tp.setFakeBoldText(true);
			tv_name.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
			tv_name.setBackgroundColor(Color.parseColor("#ffffff"));

			Drawable drawable= mContext.getResources().getDrawable(R.drawable.single_red_line);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
			tv_name.setCompoundDrawables(drawable,null,null,null);

		}else{
		//	tv_name.setTextColor(Color.parseColor("#000000"));
			TextPaint tp = tv_name.getPaint();
			tp.setFakeBoldText(false);
			tv_name.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
			tv_name.setBackgroundColor(Color.parseColor("#f0f1f6"));
			tv_name.setCompoundDrawables(null,null,null,null);
		}*/
	}
}

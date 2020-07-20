package com.lwc.shanxiu.module.lease_parts.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Dimension;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.lease_parts.bean.LeaseGoodBean;
import com.lwc.shanxiu.module.lease_parts.inteface_callback.OnListItemClick;
import com.lwc.shanxiu.module.lease_parts.viewholder.LeaseGoodsViewHolder;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.widget.TagsLayout;

import java.util.List;

/**
 * User: Liumj(liumengjie@365tang.cn)
 * Date: 2016-10-11
 * Time: 15:51
 * describe:首页列表适配器
 */
public class LeaseGoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private Context context;

	private List<LeaseGoodBean> leaseGoodBeans;
	private OnListItemClick onListItemClick;

	public LeaseGoodsAdapter(Context context,List<LeaseGoodBean> leaseGoodBeans,OnListItemClick onListItemClick){
		this.context = context;
		this.leaseGoodBeans = leaseGoodBeans;
		this.onListItemClick = onListItemClick;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_lease_good,parent,false);
		return new LeaseGoodsViewHolder(view);
	}

	public void setDataList(List<LeaseGoodBean> list){
		leaseGoodBeans = list;
		notifyDataSetChanged();
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

		final LeaseGoodBean leaseGoodBean = leaseGoodBeans.get(position);

		ImageView iv_img = holder.itemView.findViewById(R.id.iv_img);
		TextView tv_name = holder.itemView.findViewById(R.id.tv_name);
		TextView tv_spc = holder.itemView.findViewById(R.id.tv_spc);
		TextView tv_price = holder.itemView.findViewById(R.id.tv_price);


		ImageLoaderUtil.getInstance().displayFromNetDCircular(context,leaseGoodBean.getGoodsImg(),iv_img,R.drawable.image_default_picture);

		String goodsName = leaseGoodBean.getGoodsName();
		String goodsNameStr = "租赁  " + goodsName;
		SpannableStringBuilder showGoodsName = Utils.getSpannableStringBuilder(0, 2, context.getResources().getColor(R.color.transparent), goodsNameStr, 10, true);
		tv_name.setText(showGoodsName);

		tv_spc.setText(leaseGoodBean.getLeaseSpecs());


		String goodPrice = Utils.chu(leaseGoodBean.getGoodsPrice(),"100");
		String goodPriceStr = "￥"+goodPrice+"起";
		SpannableStringBuilder showPrices = Utils.getSpannableStringBuilder(1, goodPrice.length()+1,context.getResources().getColor(R.color.red_money), goodPriceStr, 18,true);
		tv_price.setText(showPrices);

		String lables = leaseGoodBean.getLabelName();
		TagsLayout tl_tags = holder.itemView.findViewById(R.id.tl_tags);
		if(!TextUtils.isEmpty(lables)){
			String [] tags = lables.split(",");
			if(tags.length > 0){
				tl_tags.setVisibility(View.VISIBLE);
				//String[] tags =item.getLabelName().split(",");
				tl_tags.removeAllViews();
				for (int i = 0; i < tags.length; i++) {
					if(i >= 3){
						break;
					}
					TextView textView = new TextView(context);
					textView.setText(tags[i]);
					textView.setTextColor(Color.parseColor("#ff3a3a"));
					textView.setTextSize(Dimension.SP,8);
					textView.setGravity(Gravity.CENTER);
					textView.setPadding(2,0,2,0);
					textView.setBackgroundResource(R.drawable.round_square_red_line);
					textView.setText(tags[i]);
					tl_tags.addView(textView);
				}
			}else{
				tl_tags.setVisibility(View.GONE);
			}
		}else{
			tl_tags.setVisibility(View.GONE);
		}

		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(onListItemClick != null){
					onListItemClick.onItemClick(leaseGoodBean);
				}
			}
		});

	}

	@Override
	public int getItemCount() {
		return leaseGoodBeans == null?0:leaseGoodBeans.size();
	}
}

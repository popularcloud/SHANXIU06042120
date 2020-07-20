package com.lwc.shanxiu.module.lease_parts.adapter;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.lwc.shanxiu.R;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.lease_parts.bean.LeaseReturnDetailBean;
import com.lwc.shanxiu.module.lease_parts.inteface_callback.OnOrderBtnClick;
import com.lwc.shanxiu.utils.ImageLoaderUtil;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

public class LeaseOrderReturnListAdapter extends SuperAdapter<LeaseReturnDetailBean> {

    private Context context;
    private int pageType = 0;

    private OnOrderBtnClick onOrderBtnClick;

    public LeaseOrderReturnListAdapter(Context context, List<LeaseReturnDetailBean> items, int layoutResId, int pageType, OnOrderBtnClick onOrderBtnClick) {
        super(context, items, layoutResId);
        this.context = context;
        this.pageType = pageType;
        this.onOrderBtnClick = onOrderBtnClick;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, final int layoutPosition, final LeaseReturnDetailBean item) {

        TextView tv_btn01 = holder.itemView.findViewById(R.id.tv_btn01);
        TextView tv_btn02 = holder.itemView.findViewById(R.id.tv_btn02);
        TextView tv_btn03 = holder.itemView.findViewById(R.id.tv_btn03);
        TextView tv_btn04 = holder.itemView.findViewById(R.id.tv_btn04);
        TextView tv_btn05 = holder.itemView.findViewById(R.id.tv_btn05);
        TextView txtStatus = holder.itemView.findViewById(R.id.txtStatus);
        ImageView iv_header = holder.itemView.findViewById(R.id.iv_header);
        TextView tv_title = holder.itemView.findViewById(R.id.tv_title);
        TextView tv_spece = holder.itemView.findViewById(R.id.tv_spece);
        TextView tv_price = holder.itemView.findViewById(R.id.tv_price);
        TextView tv_number = holder.itemView.findViewById(R.id.tv_number);
        TextView tv_total = holder.itemView.findViewById(R.id.tv_total);
        TextView tv_order_num = holder.itemView.findViewById(R.id.tv_order_num);

        txtStatus.setText(item.getStatusDeatilName());
        ImageLoaderUtil.getInstance().displayFromNetDCircular(context,item.getGoodsImg(),iv_header,R.drawable.image_default_picture);

        String goodsName = item.getGoodsName();
        String goodsNameStr = "租赁  " + goodsName;
        SpannableStringBuilder showGoodsName = Utils.getSpannableStringBuilder(0, 2, mContext.getResources().getColor(R.color.transparent), goodsNameStr, 10, true);
        tv_title.setText(showGoodsName);

        tv_spece.setText(item.getLeaseSpecs()+","+item.getLeaseMonTime()+"个月,"+("1".equals(item.getPayType())?"月付":"季付"));

        String returnPrice = Utils.chu((TextUtils.isEmpty(item.getMoney())?"0":item.getMoney()), "100");
        String goodsPrice = Utils.chu(item.getGoodsPrice(), "100");
        String goodsPriceStr = "￥" + goodsPrice;
        SpannableStringBuilder showPrices = Utils.getSpannableStringBuilder(1, goodsPrice.length()+1, context.getResources().getColor(R.color.black), goodsPriceStr, 24, true);
        tv_price.setText(Utils.getMoney(showPrices.toString()));

        tv_number.setText("X"+item.getGoodsNum());
        String totalMoney = "共计"+item.getGoodsNum()+"件商品 退款:"+ "￥" + returnPrice;
        tv_total.setText(Utils.getMoney(totalMoney));

        tv_order_num.setText("订单号:"+item.getBranchId());

        switch (item.getState()){
            case 0: //退款中
                tv_btn01.setVisibility(View.VISIBLE);
                tv_btn01.setText("撤销申请");
                tv_btn05.setVisibility(View.VISIBLE);
                tv_btn02.setVisibility(View.GONE);
                tv_btn03.setVisibility(View.GONE);
                tv_btn04.setVisibility(View.GONE);
                break;
            case 1: //成功
                tv_btn01.setVisibility(View.VISIBLE);
                tv_btn01.setText("删除订单");
                tv_btn05.setVisibility(View.GONE);
                tv_btn02.setVisibility(View.GONE);
                tv_btn03.setVisibility(View.GONE);
                tv_btn04.setVisibility(View.GONE);
                break;
            case 2: //失败
                tv_btn01.setVisibility(View.VISIBLE);
                tv_btn01.setText("删除订单");

                tv_btn02.setVisibility(View.GONE);
                tv_btn03.setVisibility(View.GONE);
                tv_btn04.setVisibility(View.GONE);

                tv_btn05.setVisibility(View.VISIBLE);
                break;
        }

        tv_btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView myView = (TextView) v;
                if(onOrderBtnClick != null){
                    onOrderBtnClick.onItemClick(myView.getText().toString(),layoutPosition,item.getBranchId());
                }
            }
        });

        tv_btn05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView myView = (TextView) v;
                if(onOrderBtnClick != null){
                    onOrderBtnClick.onItemClick(myView.getText().toString(),layoutPosition,item.getOrderId());
                }
            }
        });
    }
}

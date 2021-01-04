package com.lwc.shanxiu.module.lease_parts.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Dimension;
import android.text.SpannableStringBuilder;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.lease_parts.bean.LeaseGoodBean;
import com.lwc.shanxiu.module.lease_parts.inteface_callback.OnOrderBtnClick;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.widget.TagsLayout;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * @author younge
 * @date 2018/12/20 0020
 * @email 2276559259@qq.com
 */
public class LeaseCollectAdapter extends SuperAdapter<LeaseGoodBean>{

    private boolean isManager = false;
    private OnOrderBtnClick onOrderBtnClick;

    public LeaseCollectAdapter(Context context, List<LeaseGoodBean> items, int layoutResId,OnOrderBtnClick onOrderBtnClick) {
        super(context, items, layoutResId);
        this.onOrderBtnClick = onOrderBtnClick;
    }

    public void setManager(boolean isManager){
        this.isManager = isManager;
        notifyDataSetChanged();
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, final int layoutPosition, final LeaseGoodBean item) {
        TextView tv_title = holder.findViewById(R.id.tv_title);
        TextView tv_prices = holder.findViewById(R.id.tv_prices);
        TextView tv_specs = holder.findViewById(R.id.tv_specs);
        TagsLayout tl_tags = holder.findViewById(R.id.tl_tags);
        ImageView iv_display = holder.findViewById(R.id.iv_display);
        final CheckBox cb_isAdd = holder.findViewById(R.id.cb_isAdd);

        if(isManager){
           cb_isAdd.setVisibility(View.VISIBLE);
        }else{
            cb_isAdd.setVisibility(View.GONE);
        }

        cb_isAdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setChecked(isChecked);
                if(onOrderBtnClick != null){
                    onOrderBtnClick.onItemClick("",layoutPosition,"");
                }
            }
        });

        if(item.isChecked()){
            cb_isAdd.setChecked(true);
        }else{
            cb_isAdd.setChecked(false);
        }

        String goodsName = item.getGoodsName();
       // String goodsNameStr = "租赁  " + goodsName;
       // SpannableStringBuilder showGoodsName = Utils.getSpannableStringBuilder(0, 2, mContext.getResources().getColor(R.color.transparent), goodsNameStr, 10, true);
        tv_title.setText(goodsName);

        tv_specs.setText(item.getLeaseSpecs());
        String goodsPrice = Utils.chu(item.getGoodsPrice(), "100");
        String goodsPriceStr = "￥" + goodsPrice + "";
        SpannableStringBuilder showPrices = Utils.getSpannableStringBuilder(1, goodsPrice.length()+1, mContext.getResources().getColor(R.color.red_money), goodsPriceStr, 18, true);
        tv_prices.setText(showPrices);

        ImageLoaderUtil.getInstance().displayFromNetDCircular(mContext,item.getGoodsImg(),iv_display,R.drawable.image_default_picture);

        String lables = item.getLabelName();
        if(lables != null){
            String [] tags = lables.split(",");
            if(tags.length > 0){
                tl_tags.setVisibility(View.VISIBLE);
                //String[] tags =item.getLabelName().split(",");
                tl_tags.removeAllViews();
                for (int i = 0; i < tags.length; i++) {
                    if(i > 3){
                        break;
                    }
                    TextView textView = new TextView(mContext);
                    textView.setText(tags[i]);
                    textView.setTextColor(Color.parseColor("#ff3a3a"));
                    textView.setTextSize(Dimension.SP,9);
                    textView.setGravity(Gravity.CENTER);
                    textView.setPadding(5,0,5,0);
                    textView.setBackgroundResource(R.drawable.round_square_red_line);
                    textView.setText(tags[i]);
                    tl_tags.addView(textView);
                }
            }else{
                tl_tags.setVisibility(View.INVISIBLE);
            }
        }else{
            tl_tags.setVisibility(View.INVISIBLE);
        }

    }
}

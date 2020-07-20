package com.lwc.shanxiu.module.lease_parts.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.lease_parts.bean.ShopCarBean;
import com.lwc.shanxiu.utils.ImageLoaderUtil;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

public class LeaseConfirmAdapter extends SuperAdapter<ShopCarBean> {

    private Context context;
    public LeaseConfirmAdapter(Context context, List<ShopCarBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final ShopCarBean item) {

        ImageView iv_display = holder.itemView.findViewById(R.id.iv_display);
        TextView tv_title = holder.itemView.findViewById(R.id.tv_title);
        TextView tv_spece = holder.itemView.findViewById(R.id.tv_spece);
        TextView tv_prices = holder.itemView.findViewById(R.id.tv_prices);
        TextView tv_sum = holder.itemView.findViewById(R.id.tv_sum);
        EditText et_message = holder.itemView.findViewById(R.id.et_message);

        ImageLoaderUtil.getInstance().displayFromNetDCircular(context,item.getGoodsImg(),iv_display,R.drawable.image_default_picture);

        String goodsName = item.getGoodsName();
        String goodsNameStr = "租赁  " + goodsName;
        SpannableStringBuilder showGoodsName = Utils.getSpannableStringBuilder(0, 2, mContext.getResources().getColor(R.color.transparent), goodsNameStr, 10, true);
        tv_title.setText(showGoodsName);

        //tv_title.setText(item.getGoodsName());

        tv_spece.setText(item.getLeaseSpecs());

        String money = Utils.getMoney(Utils.chu(item.getGoodsPrice(),"100"));
        SpannableStringBuilder spannableStringBuilder = Utils.getSpannableStringBuilder(1,money.length() - 2,"￥"+ money,18,true);

        tv_prices.setText(spannableStringBuilder);
        tv_sum.setText("x"+item.getGoodsNum());


        et_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString().trim();
                item.setRemark(str);
            }
        });

    }
}

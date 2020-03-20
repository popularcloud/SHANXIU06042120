package com.lwc.shanxiu.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.bean.TradingRecordBean;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.utils.ImageLoaderUtil;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;


/**
 * Created by 何栋 on 2017/10/15.
 * 294663966@qq.com
 * 订单状态
 */
public class TradingRecordAdapter extends SuperAdapter<TradingRecordBean> {

    private ImageLoaderUtil imageLoaderUtil;
    private Context context;
    public TradingRecordAdapter(Context context, List<TradingRecordBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
        imageLoaderUtil = ImageLoaderUtil.getInstance();
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, TradingRecordBean item) {
        ImageView img = holder.getView(R.id.iv_type);
        String jj="+";
/*        if("6".equals(item.getUserRole())){
            if(item.getTransactionScene() == 1){
                img.setImageResource(R.drawable.account_red_envelopes);
            }else{
                switch (item.getTransactionMeans()){
                    case "1":
                        img.setImageResource(R.drawable.account_balance2);
                        break;
                    case "2":
                        img.setImageResource(R.drawable.account_alipay);
                        break;
                    case "3":
                        img.setImageResource(R.drawable.account_wechat);
                        break;
                }
            }
            if (item.getPaymentType().equals("0")){
                holder.setText(R.id.txtTitle, "订单结算");
                holder.setTextColor(R.id.tv_money, context.getResources().getColor(R.color.red_money));
            } else {
                jj="-";
                holder.setTextColor(R.id.tv_money, context.getResources().getColor(R.color.color_33));
                holder.setText(R.id.txtTitle, "订单结算");
            }
        }else{*/
            if (item.getTransactionScene() == 1) {

                if (("1").equals(item.getSpendType())){
                    holder.setText(R.id.txtTitle, "路费补贴");
                    holder.setTextColor(R.id.tv_money, context.getResources().getColor(R.color.red_3a));
                    img.setImageResource(R.drawable.ic_package_car);
                } else  if (("2").equals(item.getSpendType())){
                    holder.setText(R.id.txtTitle, "红包收入");
                    holder.setTextColor(R.id.tv_money, context.getResources().getColor(R.color.red_3a));
                    img.setImageResource(R.drawable.ic_paytype_red);
                }else  if (("5").equals(item.getSpendType())){
                    holder.setText(R.id.txtTitle, "接单奖励");
                    holder.setTextColor(R.id.tv_money, context.getResources().getColor(R.color.red_3a));
                    img.setImageResource(R.drawable.ic_package_gift);
                }else  if (("7").equals(item.getSpendType())){
                    holder.setText(R.id.txtTitle, "邀请维修");
                    holder.setTextColor(R.id.tv_money, context.getResources().getColor(R.color.red_3a));
                    img.setImageResource(R.drawable.ic_package_gift);
                }
            } else if (item.getTransactionScene() == 2) {
             //   if (item.getTransactionMeans().equals("1")) {
                    img.setImageResource(R.drawable.ic_package_order);

                    holder.setText(R.id.txtTitle, "维修订单");
                    holder.setTextColor(R.id.tv_money, context.getResources().getColor(R.color.red_3a));

             /*   } else if (item.getTransactionMeans().equals("2")) {
                    img.setImageResource(R.drawable.account_alipay);
                    if (item.getPaymentType().equals("0")){
                        holder.setText(R.id.txtTitle, "订单收入");
                        holder.setTextColor(R.id.tv_money, context.getResources().getColor(R.color.red_money));
                    } else {
                        jj="-";
                        holder.setTextColor(R.id.tv_money, context.getResources().getColor(R.color.color_33));
                        holder.setText(R.id.txtTitle, "支付宝支付");
                    }
                } else if (item.getTransactionMeans().equals("3")) {
                    img.setImageResource(R.drawable.account_wechat);
                    if (item.getPaymentType().equals("0")){
                        holder.setText(R.id.txtTitle, "订单收入");
                        holder.setTextColor(R.id.tv_money, context.getResources().getColor(R.color.red_money));
                    } else {
                        jj="-";
                        holder.setText(R.id.txtTitle, "微信支付");
                        holder.setTextColor(R.id.tv_money, context.getResources().getColor(R.color.color_33));
                    }
                }*/
            } else if(item.getTransactionScene() == 3) {
                if (item.getTransactionMeans().equals("1")) {
                    img.setImageResource(R.drawable.ic_package_order);
                    if (item.getPaymentType().equals("0")){
                        holder.setText(R.id.txtTitle, "维修订单");
                        holder.setTextColor(R.id.tv_money, context.getResources().getColor(R.color.red_3a));
                    } else {
                        jj="-";
                        holder.setTextColor(R.id.tv_money, context.getResources().getColor(R.color.color_33));
                        holder.setText(R.id.txtTitle, "钱包支出");
                    }
                } else if (item.getTransactionMeans().equals("2")) {
                    img.setImageResource(R.drawable.account_alipay);
                    if (item.getPaymentType().equals("0")){
                        holder.setText(R.id.txtTitle, "支付宝充值");
                        holder.setTextColor(R.id.tv_money, context.getResources().getColor(R.color.red_3a));
                    } else {
                        jj="-";
                        holder.setTextColor(R.id.tv_money, context.getResources().getColor(R.color.color_33));
                        holder.setText(R.id.txtTitle, "支付宝提现");
                    }
                } else if (item.getTransactionMeans().equals("3")) {
                    img.setImageResource(R.drawable.account_wechat);
                    if (item.getPaymentType().equals("0")){
                        holder.setText(R.id.txtTitle, "微信充值");
                        holder.setTextColor(R.id.tv_money, context.getResources().getColor(R.color.red_3a));
                    } else {
                        jj="-";
                        holder.setTextColor(R.id.tv_money, context.getResources().getColor(R.color.color_33));
                        holder.setText(R.id.txtTitle, "微信提现");
                    }
                }
            }
       // }

        holder.setText(R.id.tv_money, jj+ Utils.getMoney(item.getTransactionAmount()));         //金额
        holder.setText(R.id.txtTime, item.getCreateTime()); //时间

    }
}

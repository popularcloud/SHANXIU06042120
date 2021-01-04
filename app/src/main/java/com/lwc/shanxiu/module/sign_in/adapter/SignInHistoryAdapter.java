package com.lwc.shanxiu.module.sign_in.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gcssloop.widget.PagerGridLayoutManager;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.lease_parts.bean.LeaseGoodBean;
import com.lwc.shanxiu.module.sign_in.bean.SignInRecordBean;
import com.lwc.shanxiu.utils.DisplayUtil;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.Collection;
import java.util.List;


/**
 * @author younge
 * @date 2018/12/20 0020
 * @email 2276559259@qq.com
 */
public class SignInHistoryAdapter extends SuperAdapter<List<SignInRecordBean>>{

    private boolean isManager = false;

    public SignInHistoryAdapter(Context context, List<List<SignInRecordBean>> items, int layoutResId) {
        super(context,items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final List<SignInRecordBean> item) {

        if(item != null && item.size() > 0){
            String [] groupByTimeStr = item.get(0).getGroupByTime().split("-");
            if(groupByTimeStr != null){
                holder.setText(R.id.tv_date,groupByTimeStr[1]+"月"+groupByTimeStr[2]+"日");
            }

            for(int i = 0; i < item.size(); i++){

                SignInRecordBean signInRecordBean = item.get(i);

                LinearLayout linearLayout = new LinearLayout(mContext);
                int ll_pading = DisplayUtil.dip2px(mContext,15);
                linearLayout.setPadding(ll_pading,ll_pading,ll_pading,ll_pading);
                LinearLayout.LayoutParams ll_layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                ll_layoutParams.setMargins(DisplayUtil.dip2px(mContext,15),DisplayUtil.dip2px(mContext,11),DisplayUtil.dip2px(mContext,15),0);
                linearLayout.setLayoutParams(ll_layoutParams);
                linearLayout.setBackgroundResource(R.color.gray_f9);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                TextView tv_name = new TextView(mContext);
                LinearLayout.LayoutParams name_layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                tv_name.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                tv_name.setTextColor(Color.BLACK);
                tv_name.setLayoutParams(name_layoutParams);
                if(signInRecordBean.getSignIn_time() != null){
                    tv_name.setText(signInRecordBean.getSignIn_time().substring(11,16)+"    "+signInRecordBean.getAddress());
                }else{
                    tv_name.setText(signInRecordBean.getAddress());
                }

                TextView tv_address = new TextView(mContext);
                LinearLayout.LayoutParams address_layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                address_layoutParams.topMargin = DisplayUtil.dip2px(mContext,5);
                tv_address.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                tv_address.setTextColor(Color.parseColor("#9A9A9A"));
                tv_address.setLayoutParams(address_layoutParams);
                tv_address.setText("签到地点: "+signInRecordBean.getDetailAddress());

                TextView tv_visit_object = new TextView(mContext);
                LinearLayout.LayoutParams visit_object_layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                visit_object_layoutParams.topMargin =  DisplayUtil.dip2px(mContext,5);;
                tv_visit_object.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                tv_visit_object.setTextColor(Color.parseColor("#9A9A9A"));
                tv_visit_object.setLayoutParams(visit_object_layoutParams);
                tv_visit_object.setText("拜访对象: "+signInRecordBean.getVisitingObject());


                linearLayout.addView(tv_name);
                linearLayout.addView(tv_address);
                linearLayout.addView(tv_visit_object);

                LinearLayout ll_content = holder.itemView.findViewById(R.id.ll_content);
                ll_content.addView(linearLayout);
            }

        }



    }
}

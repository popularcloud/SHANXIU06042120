package com.lwc.shanxiu.module.sign_in.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.sign_in.bean.SignInRecordBean;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * @author younge
 * @date 2018/12/20 0020
 * @email 2276559259@qq.com
 */
public class SignInHistoryMainAdapter extends SuperAdapter<SignInRecordBean>{


    public SignInHistoryMainAdapter(Context context, List<SignInRecordBean> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, SignInRecordBean item) {

        TextView tv_time = holder.findViewById(R.id.tv_time);

        if(item.getType() == 0){ //内勤
            holder.setText(R.id.tv_status,item.getStatus() == 0?"上班打卡":"下班打卡");
            tv_time.setText("打卡时间:"+ (TextUtils.isEmpty(item.getSignIn_time())?"":item.getSignIn_time().substring(11,16)));
            holder.setText(R.id.tv_address,item.getAddress());

            if(item.getAbnormal() == 1){//正常打卡
                tv_time.setTextColor(Color.parseColor("#0A0A0A"));
            }else{ //异常打卡
                tv_time.setTextColor(Color.parseColor("#ff3a3a"));
            }
            holder.setVisibility(R.id.tv_visit_object, View.GONE);

        }else if(item.getType() == 1){ //外勤
            holder.setText(R.id.tv_status,"外勤签到");
            holder.setText(R.id.tv_time,"签到时间:"+ (TextUtils.isEmpty(item.getSignIn_time())?"":item.getSignIn_time().substring(11,16)));
            holder.setText(R.id.tv_address,item.getAddress());
            holder.setText(R.id.tv_visit_object,"拜访对象:"+item.getVisitingObject());
            holder.setVisibility(R.id.tv_visit_object, View.VISIBLE);
        }

        if(layoutPosition < getItemCount() - 1){
            holder.setVisibility(R.id.view_h_line02, View.VISIBLE);
        }

        if(layoutPosition  == 0){
            holder.setVisibility(R.id.view_h_line01, View.INVISIBLE);
        }


    }
}

package com.lwc.shanxiu.module.authentication.adapter;

import android.content.Context;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.authentication.bean.AnswerReturnBean;
import com.lwc.shanxiu.module.authentication.bean.SubmitTopicBean;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

public class AnswerTopicReturnAdapter extends SuperAdapter<String> {

    private Context context;
    private int presentPosition = -1;
    private List<AnswerReturnBean.PaperBean> paperBeans;
    public AnswerTopicReturnAdapter(Context context,List<String>  list,List<AnswerReturnBean.PaperBean> items, int layoutResId) {
        super(context, list, layoutResId);
        this.context = context;
        this.paperBeans = items;
    }

    public void setItemChecked(int position){
        presentPosition = position;
        //notifyDataSetChanged();
    }
    @Override
    public void onBind(SuperViewHolder holder, int viewType, final int layoutPosition, String item) {
        holder.setText(R.id.tv_title,String.valueOf((layoutPosition+1)));
        holder.setBackgroundResource(R.id.tv_title,R.drawable.circular_gray_line_shape);
        holder.setTextColor(R.id.tv_title,context.getResources().getColor(R.color.line_cc));
        for(int i = 0; i < paperBeans.size();i++){
            AnswerReturnBean.PaperBean paperBean = paperBeans.get(i);
            if((layoutPosition+1) == Integer.valueOf(paperBean.getNum())){
                if(paperBean.getStatus() == 0){ //错误
                    holder.setBackgroundResource(R.id.tv_title,R.drawable.circular_blue_shape);
                    holder.setTextColor(R.id.tv_title,context.getResources().getColor(R.color.white));
                }else if(paperBean.getStatus() == 1){ //正确
                    holder.setBackgroundResource(R.id.tv_title,R.drawable.circular_blue_line_shape);
                    holder.setTextColor(R.id.tv_title,context.getResources().getColor(R.color.btn_blue_nomal));
                }
            }
        }
    }
}

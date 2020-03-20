package com.lwc.shanxiu.module.question.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.ImageBrowseActivity;
import com.lwc.shanxiu.adapter.MyGridViewPhotoAdpter;
import com.lwc.shanxiu.module.question.bean.PublishQuestionDetialBean;
import com.lwc.shanxiu.module.question.bean.QuestionIndexBean;
import com.lwc.shanxiu.module.question.ui.activity.QuestionDetailActivity;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.utils.TimeUtil;
import com.lwc.shanxiu.view.MyGridView;
import com.lwc.shanxiu.widget.CircleImageView;
import com.lwc.shanxiu.widget.CustomDialog;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author younge
 * @date 2019/6/4 0004
 * @email 2276559259@qq.com
 */
public class QuestionDetailAdapter extends SuperAdapter<PublishQuestionDetialBean.AnswersBean>{

    private Context context;
    private Boolean isSelfQuestion;
    private OnAdoptListener myOnAdoptListener;
    private boolean isSelected = false;

    public QuestionDetailAdapter(Context context, List<PublishQuestionDetialBean.AnswersBean> items, int layoutResId,Boolean isSelfQuestion,OnAdoptListener onAdoptListener) {
        super(context, items, layoutResId);
        this.context = context;
        this.isSelfQuestion = isSelfQuestion;
        this.myOnAdoptListener = onAdoptListener;
    }

    public void setIsSelefQuestion(Boolean isSelfQuestion){
        this.isSelfQuestion = isSelfQuestion;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, final int layoutPosition, final PublishQuestionDetialBean.AnswersBean item) {

        CircleImageView img_head = holder.findViewById(R.id.img_head);
        if(!TextUtils.isEmpty(item.getMaintenanceName())){
            holder.setText(R.id.tv_name, item.getMaintenanceName()+"");
            ImageLoaderUtil.getInstance().displayFromNet(context,item.getMaintenanceHeadImage(),img_head,R.drawable.default_portrait_100);
        }else{ //如果工程师名字为空 则是匿名发布内容
            holder.setText(R.id.tv_name, "***");
            ImageLoaderUtil.getInstance().displayFromLocal(context,img_head,R.drawable.default_portrait_100);
        }
        holder.setText(R.id.tv_desc, item.getAnswerDetail());
        MyGridView myGridView = holder.findViewById(R.id.gridview_show);
        TextView tv_status = holder.findViewById(R.id.tv_status);
        final ImageView iv_adopt = holder.findViewById(R.id.iv_adopt);

        //holder.setText(R.id.tv_view_count,item.getBrowseNum()+"次");
       // holder.setText(R.id.tv_comment_count,item.getQuesionMessage()+"个");

        if(!TextUtils.isEmpty(item.getCreateTime())){
            holder.setText(R.id.tv_create_time, item.getCreateTime().substring(0,10));
        }
        if(!TextUtils.isEmpty(item.getCreateTime())){
            holder.setText(R.id.tv_create_time, item.getCreateTime().substring(0,10));
        }


        if(isSelfQuestion){  //是否为自己的问题
            iv_adopt.setVisibility(View.VISIBLE);
            tv_status.setVisibility(View.GONE);

            iv_adopt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final CustomDialog customDialog = new CustomDialog(context);
                    customDialog.setTitle("温馨提示");
                    customDialog.setMessage("答案一旦被采纳,将不能被修改");
                    customDialog.setButton1Text("确定");
                    customDialog.setButton2Text("取消");
                    customDialog.setEnterBtn(new CustomDialog.OnClickListener() {
                        @Override
                        public void onClick(CustomDialog dialog, int id, Object object) {
                            dialog.dismiss();

                            myOnAdoptListener.onClick(layoutPosition,item.getAnswerId());
                        }
                    });
                    customDialog.setCancelBtn(new CustomDialog.OnClickListener() {
                        @Override
                        public void onClick(CustomDialog dialog, int id, Object object) {
                            dialog.dismiss();
                        }
                    });
                    customDialog.show();
                }
            });
            if(layoutPosition == 0){ //如果是第一项  （每次采纳都会重新请求数据  后台会将被采纳的数据排序置顶，采纳后将不能更改）
                if(item.getIsSelect() == 1){ //并且被采纳 是否被采纳 0否  1是
                    iv_adopt.setImageResource(R.drawable.ic_adopt);
                    iv_adopt.setClickable(false);
                    isSelected = true;
                }else{
                    iv_adopt.setImageResource(R.drawable.ic_no_adopt);
                    iv_adopt.setClickable(true);
                    isSelected = false;
                }
            }else{
                if(isSelected){
                    iv_adopt.setClickable(false);
                }else{
                    iv_adopt.setClickable(true);
                }
            }
        }else{
            iv_adopt.setVisibility(View.GONE);
            if (item.getIsSelect() == 1){  //是否被采纳 0否  1是
                tv_status.setVisibility(View.VISIBLE);
            }else{
                tv_status.setVisibility(View.GONE);
            }
        }
        List<String> urlStrs = new ArrayList();
        if(!TextUtils.isEmpty(item.getAnswerImg())){
            String img = item.getAnswerImg();
            if (img != null && !img.equals("")) {
                String[] imgs = img.split(",");
                for (int i=0;i<imgs.length; i++) {
                    urlStrs.add(imgs[i]);
                }
            }

            final QuestionMyGridViewPhotoAdpter adpter;adpter = new QuestionMyGridViewPhotoAdpter(context, urlStrs);
            adpter.setIsShowDel(false);
            myGridView.setAdapter(adpter);
            myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(context, ImageBrowseActivity.class);
                    intent.putExtra("index", position);
                    intent.putStringArrayListExtra("list", (ArrayList)adpter.getLists());
                    context.startActivity(intent);
                }
            });
        }

        if(urlStrs.size()<1){
            myGridView.setVisibility(View.GONE);
        }
    }


    public interface OnAdoptListener {
        void onClick(int positon,String answerId);

    }
}

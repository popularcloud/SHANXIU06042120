package com.lwc.shanxiu.module.message.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.message.bean.KnowledgeBaseBean;
import com.lwc.shanxiu.module.message.bean.LikeArticleBean;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.utils.TimeUtil;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author younge
 * @date 2019/6/4 0004
 * @email 2276559259@qq.com
 */
public class OtherKnowlegeArticleAdapter extends SuperAdapter<LikeArticleBean>{

    private Context context;
    public OtherKnowlegeArticleAdapter(Context context, List<LikeArticleBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, LikeArticleBean item) {


        SpannableString content = new SpannableString(item.getKnowledge_title());
        UnderlineSpan underlineSpan = new UnderlineSpan();
        content.setSpan(underlineSpan, 0, item.getKnowledge_title().length(), 0);
        holder.setText(R.id.tv_title, content);
        TextView textView = holder.findViewById(R.id.tv_title);
       // textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
    }
}

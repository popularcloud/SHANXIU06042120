package com.lwc.shanxiu.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.bean.Location;
import com.lwc.shanxiu.bean.Repairman;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.widget.CircleImageView;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * 工程师列表适配器
 *
 * @author 何栋
 * @Description TODO
 * @date 2016年4月16日
 * @Copyright: lwc
 */
public class ComfigNameAdapter extends SuperAdapter<String> {

    private ImageLoaderUtil imageLoaderUtil;
    private Context context;

    public ComfigNameAdapter(Context context,List<String> datas, int layoutId) {
        super(context, datas, layoutId);
        this.context = context;
        imageLoaderUtil = ImageLoaderUtil.getInstance();
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final String t) {
        String[] skills = null;
        if (t != null) {
            skills = t.split(":");
            if(skills == null || skills.length < 2){
                skills = t.split("：");
            }
            if(skills != null && skills.length==2){
                holder.setText(R.id.tv_title,skills[0]);
                holder.setText(R.id.tv_content,skills[1]);
            }
        }
    }
}

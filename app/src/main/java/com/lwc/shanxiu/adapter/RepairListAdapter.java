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
import com.lwc.shanxiu.configs.TApplication;
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
public class RepairListAdapter extends SuperAdapter<Repairman> {

    private ImageLoaderUtil imageLoaderUtil;
    private Context context;

    public RepairListAdapter(Context context, List<Repairman> datas, int layoutId) {
        super(context, datas, layoutId);
        this.context = context;
        imageLoaderUtil = ImageLoaderUtil.getInstance();
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final Repairman t) {


        String[] skills = null;
        if (t != null && !TextUtils.isEmpty(t.getDeviceTypeName())) {
            skills = t.getDeviceTypeName().split(",");
        }
        String str = null;
        if (skills != null) {
            if (skills.length >= 3) {
                str = skills[0] + "  " + skills[1] + "  " + skills[2];
            } else {
                for (int i = 0; i < skills.length; i++) {
                    str += skills[i] + "  ";
                }
            }
        }
        if (t.getIsSecrecy().equals("1")) {
            holder.setVisibility(R.id.iv_mx, ComViewHolder.VISIBLE);
        } else {
            holder.setVisibility(R.id.iv_mx, ComViewHolder.GONE);
        }
        Location location = SharedPreferencesUtils.getInstance(context).loadObjectData(Location.class);
        if (location == null){
            location = new Location();
        }
        holder.setText(R.id.txtName, t.getMaintenanceName());
        holder.setText(R.id.txt_phone, t.getMaintenancePhone());
        LatLng latLng2 = new LatLng(Double.parseDouble(t.getMaintenanceLatitude()), Double.parseDouble(t.getMaintenanceLongitude()));
        float calculateLineDistance = AMapUtils.calculateLineDistance(
                new LatLng(location.getLatitude(), location.getLongitude()), latLng2);
        if (calculateLineDistance > 0) {
            holder.setVisibility(R.id.txtDistance, ComViewHolder.VISIBLE);
            holder.setText(R.id.txtDistance, (calculateLineDistance > 1000 ? String.format("%.2f", calculateLineDistance / 1000) + "km" : (int)calculateLineDistance + "m"));
        }
        holder.setText(R.id.txt_order_count, "擅长：" + str);
//        holder.setImageURI_o(R.id.img_icon, NetManager.HOST + "/" + t.getPicture(), 90);  //内存溢出
        CircleImageView img = holder.getView(R.id.img_icon);
        if (!TextUtils.isEmpty(t.getMaintenanceHeadImage())) {
            imageLoaderUtil.displayFromNet6(context, t.getMaintenanceHeadImage(), img);
        } else {
            img.setImageResource(R.drawable.default_portrait_100);
        }
        if (!TextUtils.isEmpty(t.getMaintenanceStar())) {
            holder.setRating(R.id.ratingBar_1, Float.parseFloat(t.getMaintenanceStar()));
        } else {
            holder.setRating(R.id.ratingBar_1, 0f);
        }
        holder.setText(R.id.txt_dones, "完成" + t.getOrderCount() + "单");
        holder.setOnClickListener(R.id.tb_bohao, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:"
                        + t.getMaintenancePhone());
                intent.setData(data);
                context.startActivity(intent);
            }
        });
    }
}

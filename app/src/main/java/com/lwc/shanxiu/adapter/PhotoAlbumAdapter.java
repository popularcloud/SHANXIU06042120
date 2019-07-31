package com.lwc.shanxiu.adapter;

import java.util.List;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.bean.PhotoAlbumBean;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Thumbnails;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 照片选择页面
 * 
 * @Description TODO
 * @author 何栋
 * @date 2015年3月30日
 * @Copyright: Copyright (c) 2015 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 */
public class PhotoAlbumAdapter extends BaseAdapter {

	/** 相册列表 */
	private final List<PhotoAlbumBean> list_Beans;
	/** 上下文 */
	private final Context context;

	public PhotoAlbumAdapter(List<PhotoAlbumBean> list, Context context) {
		this.list_Beans = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list_Beans.size();
	}

	@Override
	public Object getItem(int position) {
		return list_Beans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.item_photoalbum_listview, null);
			holder = new ViewHolder();
			holder.img_Photo = (ImageView) convertView.findViewById(R.id.item_photoalbum_img_photo);
			holder.txt_Name = (TextView) convertView.findViewById(R.id.item_photoalbum_txt_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		/** 通过ID 获取缩略图 */
		Bitmap bitmap = Thumbnails.getThumbnail(context.getContentResolver(), list_Beans.get(position).getBitmap(),
				Thumbnails.MICRO_KIND, null);
		holder.img_Photo.setImageBitmap(bitmap);
		holder.txt_Name.setText(list_Beans.get(position).getName() + " ( " + list_Beans.get(position).getCount() + " )");
		return convertView;
	}

	static class ViewHolder {
		ImageView img_Photo;
		TextView txt_Name;
	}

}

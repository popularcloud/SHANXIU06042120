package com.lwc.shanxiu.adapter;

import java.util.HashMap;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.PhotoAlbumActivity;
import com.lwc.shanxiu.bean.PhotoAlbumBean;
import com.lwc.shanxiu.utils.AlbumImageloader;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.utils.AlbumImageloader.ImageCallback;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

/**
 * 图片选择适配器
 * 
 * @Description TODO
 * @author 何栋
 * @version 1.0
 * @date 2014年1月16日
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 * 
 */
public class PhotoSelectAdapter extends BaseAdapter {

	/** 上下文 */
	private final Context context;
	/** 图库属性 */
	private final PhotoAlbumBean photoAlbumBean;

	/** 图片大小，根据手机屏幕自动计算大小 */
	private int size = 100;

	/** 是否选中 */
	public HashMap<Integer, Integer> map_IsCheck = new HashMap<Integer, Integer>();

	/** 图片布局 */
	private final LayoutParams params;

	/** 图片选中监听 */
	private final CheckClickListener listener;

	/** 图库异步图片加载器 */
	private final AlbumImageloader albumImageloader;
	/** 好友的列表对象 */
	private final GridView gridView;
	/** 手指还在触摸屏幕 */
	private boolean isTouch = false;
	/** 列表还在滑动中 */
	private boolean isScroll = false;

	/** 图片最大选中张数 */
	private int maxCounts = PhotoAlbumActivity.MAX_COUNTS;

	@SuppressWarnings("deprecation")
	public PhotoSelectAdapter(Context context, int maxCounts, PhotoAlbumBean photoAlbumBean, GridView gridView, CheckClickListener listener) {
		this.context = context;
		this.maxCounts = maxCounts;
		this.photoAlbumBean = photoAlbumBean;
		this.gridView = gridView;
		this.listener = listener;

		size = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth() / 3 - (4 * 5);
		params = new LayoutParams(size, size);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);

		albumImageloader = new AlbumImageloader(1, R.drawable.image_default_picture);
		this.gridView.setOnScrollListener(onScrollListener);
		this.gridView.setOnTouchListener(onTouchListener);
		initCheck();
	}

	/**
	 * 初始化选中状态
	 * 
	 * @version 1.0
	 * @createTime 2014年1月16日,下午7:07:51
	 * @updateTime 2014年1月16日,下午7:07:51
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	private void initCheck() {
		map_IsCheck.clear();
	}

	@Override
	public int getCount() {
		return photoAlbumBean.getBitList().size();
	}

	@Override
	public Object getItem(int position) {
		return photoAlbumBean.getBitList().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		if (convertView == null) {

			convertView = LayoutInflater.from(context).inflate(R.layout.item_photoselect_listview, null);
			holder = new ViewHolder();

			holder.view_Item = convertView.findViewById(R.id.item_photoselect_view_item);
			holder.img_Photo = (ImageView) convertView.findViewById(R.id.item_photoselect_img_photo);
			holder.view_Item.setLayoutParams(params);
			holder.cbox_Select = (CheckBox) convertView.findViewById(R.id.item_photoselect_cbox_select);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 通过ID 加载缩略图
		holder.img_Photo.setImageBitmap(
				albumImageloader.loadImageBitmap(position, photoAlbumBean.getBitList().get(position).getPhotoId(), new ImageCallback() {

					@Override
					public void imageLoaded(int position, Bitmap bitmap, int photoId) {
						if (null != bitmap) {
							holder.img_Photo.setImageBitmap(bitmap);
						}
						notifyDataSetChanged();
					}
				}));
		final boolean isCheck = photoAlbumBean.getBitList().get(position).isCheck();
		holder.cbox_Select.setChecked(isCheck);
		holder.cbox_Select.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (map_IsCheck.size() >= maxCounts && !map_IsCheck.containsKey(position)) {
					holder.cbox_Select.setChecked(isCheck);
					String format = context.getString(R.string.hint_send_image_max);
					ToastUtil.showLongToast(context, String.format(format, maxCounts));
					return;
				}
				boolean isCheck = photoAlbumBean.getBitList().get(position).isCheck();
				photoAlbumBean.getBitList().get(position).setCheck(!isCheck);
				if (isCheck) {// 如果已经选中，则取消选中
					map_IsCheck.remove(position);
				} else {// 否则添加选中
					map_IsCheck.put(position, position);
				}
				holder.cbox_Select.setChecked(!isCheck);
				listener.onClick(position);

			}
		});

		return convertView;
	}

	/**
	 * 加载图片范围适配
	 * 
	 * @author 何栋
	 * @version 1.0
	 * @date 2013-4-16
	 * 
	 */
	public void loadImage() {
		int start = gridView.getFirstVisiblePosition();
		int end = gridView.getLastVisiblePosition();
		if (end >= getCount()) {
			end = getCount() - 1;
		}
		albumImageloader.setLoadLimit(start, end);
		albumImageloader.unlock();
	}

	/**
	 * listview滑动事件监听
	 * 
	 * @author 何栋
	 * @version 1.0
	 * @date 2013-4-16
	 * 
	 */
	AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			switch (scrollState) {
			case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
				isScroll = true;
				albumImageloader.lock();
				break;
			case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
				isScroll = false;
				if (!isTouch) {
					loadImage();
				}
				break;
			case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
				isScroll = true;
				albumImageloader.lock();

				break;

			default:
				break;
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

		}
	};

	/**
	 * 触摸事件监听
	 * 
	 * @author 何栋
	 * @version 1.0
	 * @date 2013-4-16
	 * 
	 */
	OnTouchListener onTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
				isTouch = true;
				albumImageloader.lock();
				break;
			case MotionEvent.ACTION_UP:
				isTouch = false;
				if (!isScroll) {
					loadImage();
				}
				break;
			}
			return false;
		}
	};

	/**
	 * 按钮点选事件
	 * 
	 * @Description TODO
	 * @author 何栋
	 * @version 1.0
	 * @date 2014年1月17日
	 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
	 * 
	 */
	public interface CheckClickListener {

		/**
		 * 选中事件触发
		 * 
		 * @version 1.0
		 * @createTime 2014年1月17日,下午5:53:36
		 * @updateTime 2014年1月17日,下午5:53:36
		 * @createAuthor CodeApe
		 * @updateAuthor CodeApe
		 * @updateInfo (此处输入修改内容,若无修改可不写.)
		 * 
		 * @param position
		 */
		public void onClick(int position);

	}

	/**
	 * 内部view容器类
	 * 
	 * @Description TODO
	 * @author 何栋
	 * @version 1.0
	 * @date 2014年1月16日
	 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
	 * 
	 */
	private class ViewHolder {

		/** 照片 */
		private ImageView img_Photo;
		/** 选择按钮 */
		private CheckBox cbox_Select;
		/** item视图 */
		private View view_Item;
	}

}

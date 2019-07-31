package com.lwc.shanxiu.adapter;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.utils.AsyncImageloader;
import com.lwc.shanxiu.utils.AsyncImageloader.ImageCallback;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * 通用标签类ViewHolder
 * 
 * @Description 此类需要不断添加设置方法去完善，如果没有需要的方法，可以自行添加
 * @author 何栋
 * @date 2015年5月24日
 * @Copyright: Copyright (c) 2015 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 */
public class ComViewHolder {
	/** 用于保存view的集合 */
	private final SparseArray<View> mViews;
	/** 当前的位置 */
	private int mPosition;
	/** 复用view */
	private final View mConvertView;
	private final Context context;
	public static final int VISIBLE = View.VISIBLE;
	public static final int INVISIBLE = View.INVISIBLE;
	public static final int GONE = View.GONE;

	public int getPosition() {
		return mPosition;
	}

	/**
	 * 
	 * 构造方法
	 * 
	 * @version 1.0
	 * @createTime 2015年5月24日,上午10:32:18
	 * @updateTime 2015年5月24日,上午10:32:18
	 * @createAuthor 何栋
	 * @updateAuthor 何栋
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param context
	 * @param parent
	 * @param layoutId
	 * @param position
	 */
	public ComViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
		this.mPosition = position;
		this.context = context;
		this.mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
		mConvertView.setTag(this);
	}

	/**
	 * 静态获取对象方法
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年5月24日,上午10:31:47
	 * @updateTime 2015年5月24日,上午10:31:47
	 * @createAuthor 何栋
	 * @updateAuthor 何栋
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param context
	 * @param convertView
	 * @param parent
	 * @param layoutId
	 * @param position
	 * @return
	 */
	public static ComViewHolder getViewHolder(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
		if (convertView == null) {
			return new ComViewHolder(context, parent, layoutId, position);
		} else {
			ComViewHolder holder = (ComViewHolder) convertView.getTag();
			holder.mPosition = position;
			return holder;
		}
	}

	/**
	 * 通过id获取view
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年5月24日,上午10:36:23
	 * @updateTime 2015年5月24日,上午10:36:23
	 * @createAuthor 何栋
	 * @updateAuthor 何栋
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param viewId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	/**
	 * 获取convertView
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年5月24日,上午10:37:34
	 * @updateTime 2015年5月24日,上午10:37:34
	 * @createAuthor 何栋
	 * @updateAuthor 何栋
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @return
	 */
	public View getConvertVeiw() {
		return mConvertView;
	}

	/**
	 * 设置textview的文本
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年5月24日,上午10:56:00
	 * @updateTime 2015年5月24日,上午10:56:00
	 * @createAuthor 何栋
	 * @updateAuthor 何栋
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ComViewHolder setText(int viewId, CharSequence text) {
		TextView tv = getView(viewId);
		if (!TextUtils.isEmpty(text)) {
			tv.setText(text);
		} else {
			tv.setText("");
		}
		return this;
	}

	/**
	 * 设置checkedtextview的文本
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年5月24日,上午10:56:00
	 * @updateTime 2015年5月24日,上午10:56:00
	 * @createAuthor 何栋
	 * @updateAuthor 何栋
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ComViewHolder setCText(int viewId, CharSequence text) {
		CheckedTextView tv = getView(viewId);
		if (!TextUtils.isEmpty(text)) {
			tv.setText(text);
		} else {
			tv.setText("");
		}
		return this;
	}

	/**
	 * 设置imageview的图片
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年5月24日,上午10:56:00
	 * @updateTime 2015年5月24日,上午10:56:00
	 * @createAuthor 何栋
	 * @updateAuthor 何栋
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ComViewHolder setImageResource(int viewId, int resId) {
		ImageView img = getView(viewId);
		img.setImageResource(resId);
		return this;
	}

	/**
	 * 设置imageview的图片
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年5月24日,上午10:56:00
	 * @updateTime 2015年5月24日,上午10:56:00
	 * @createAuthor 何栋
	 * @updateAuthor 何栋
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ComViewHolder setBackGroundRes(int viewId, int resId) {
		ImageView img = getView(viewId);
		img.setBackgroundResource(resId);
		return this;
	}

	/**
	 * 设置imageview的图片
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年5月24日,上午10:56:00
	 * @updateTime 2015年5月24日,上午10:56:00
	 * @createAuthor 何栋
	 * @updateAuthor 何栋
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ComViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
		ImageView img = getView(viewId);
		img.setImageBitmap(bitmap);
		return this;
	}

	/**
	 * 设置imageview的图片
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年5月24日,上午10:56:00
	 * @updateTime 2015年5月24日,上午10:56:00
	 * @createAuthor 何栋
	 * @updateAuthor 何栋
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ComViewHolder setImageDrawable(int viewId, Drawable drawable) {
		ImageView img = getView(viewId);
		img.setImageDrawable(drawable);
		return this;
	}

	/**
	 * 设置imageview的图片
	 *
	 *
	 * @version 1.0
	 * @createTime 2015年5月24日,上午10:56:00
	 * @updateTime 2015年5月24日,上午10:56:00
	 * @createAuthor 何栋
	 * @updateAuthor 何栋
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ComViewHolder setImageURI(int viewId, String imagePath) {
		final ImageView img = getView(viewId);
		if (!TextUtils.isEmpty(imagePath)) {
			new AsyncImageloader(1, R.drawable.logo_new).loadImageBitmap(context, mPosition, imagePath, new ImageCallback() {

				@Override
				public void imageLoaded(int position, Bitmap bitmap, String imagePath) {
					if (bitmap != null) {
						img.setImageBitmap(bitmap);
					}
				}
			});
		}
		return this;
	}

	/**
	 * 设置imageview的图片【圆形头像】
	 *
	 *
	 * @version 1.0
	 * @createTime 2015年5月24日,上午10:56:00
	 * @updateTime 2015年5月24日,上午10:56:00
	 * @createAuthor 何栋
	 * @updateAuthor 何栋
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ComViewHolder setImageURI_o(int viewId, String imagePath, int size) {
		final ImageView img = getView(viewId);
		if (!TextUtils.isEmpty(imagePath)) {
			new AsyncImageloader(1, R.drawable.default_portrait_100, size, size / 2).loadImageBitmap(context, mPosition, imagePath,
					new ImageCallback() {

						@Override
						public void imageLoaded(int position, Bitmap bitmap, String imagePath) {
							if (bitmap != null) {
								img.setImageBitmap(bitmap);
							}
						}
					});
		}
		return this;
	}

	/**
	 * 设置imageview的图片(背景图片)
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年5月24日,上午10:56:00
	 * @updateTime 2015年5月24日,上午10:56:00
	 * @createAuthor 何栋
	 * @updateAuthor 何栋
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ComViewHolder setBackgroundResource(int viewId, int resId) {
		ImageView img = getView(viewId);
		img.setBackgroundResource(resId);
		return this;
	}

	/**
	 * 设置view的图片(背景图片)
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年5月24日,上午10:56:00
	 * @updateTime 2015年5月24日,上午10:56:00
	 * @createAuthor 何栋
	 * @updateAuthor 何栋
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ComViewHolder setBackgroundResourceforView(int viewId, int resId) {
		View img = getView(viewId);
		img.setBackgroundResource(resId);
		return this;
	}

	/**
	 * 设置imageview的颜色(背景颜色)
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年5月24日,上午10:56:00
	 * @updateTime 2015年5月24日,上午10:56:00
	 * @createAuthor 何栋
	 * @updateAuthor 何栋
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ComViewHolder setBackgroundColor(int viewId, int color) {
		ImageView img = getView(viewId);
		img.setBackgroundColor(color);
		return this;
	}

	/**
	 * 设置view的可见与否
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年5月24日,上午10:56:00
	 * @updateTime 2015年5月24日,上午10:56:00
	 * @createAuthor 何栋
	 * @updateAuthor 何栋
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ComViewHolder setVisible(int viewId, int visible) {
		View img = getView(viewId);
		img.setVisibility(visible);
		return this;
	}

	/**
	 * 设置字体颜色
	 * 
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param viewId
	 * @param color
	 */
	public ComViewHolder setTxtColor(int viewId, int color) {
		TextView t = getView(viewId);
		t.setTextColor(color);
		return this;
	}

	public ComViewHolder setRatingBar(int ratingbar, Float rating) {
		RatingBar rBar = getView(ratingbar);
		rBar.setRating(rating);
		return this;
	}
}

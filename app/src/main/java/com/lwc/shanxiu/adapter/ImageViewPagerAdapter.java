package com.lwc.shanxiu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;


import com.lwc.shanxiu.interf.LoadImageCallBack;
import com.lwc.shanxiu.utils.AsynUntil;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.widget.ImageBrowseViewpager;
import com.lwc.shanxiu.widget.ZoomImageView;

import java.util.ArrayList;

/**
 * 图片浏览器适配器
 * 
 * @version 1.0
 * @date 2013-8-28
 * 
 */
public class ImageViewPagerAdapter extends PagerAdapter {

	/** 上下文 */
	private final Context context;

	/** 图片列表 */
	private final ArrayList<String> list_Images;

	/** 屏幕宽度 */
	private final int screen_Width;
	/** 屏幕高度 */
	private final int screen_Height;

	private final View view_progress;

	public ImageViewPagerAdapter(Context context, ArrayList<String> list_Images, ImageBrowseViewpager mViewPager, int screen_Width, int screen_Height,
								 View view_progress) {
		this.context = context;
		this.list_Images = list_Images;
		this.screen_Height = screen_Height;
		this.screen_Width = screen_Width;
		this.view_progress = view_progress;
	}

	@Override
	public int getCount() {
		return list_Images.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		ZoomImageView view = (ZoomImageView) arg2;
		((ViewPager) arg0).removeView(view);
		view.RecycleBitmap();

	}

	@Override
	public Object instantiateItem(View view, int position) {
		ZoomImageView myImageView = loadImage(view, position);
		return myImageView;
	}

	/**
	 * 加载图片
	 *
	 * @version 1.0
	 * @createTime 2013-8-29,上午9:39:04
	 * @updateTime 2013-8-29,上午9:39:04
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 */
	public ZoomImageView loadImage(View view, int position) {
		final ZoomImageView myImageView = new ZoomImageView(context);
		myImageView.setLayoutParams(new LinearLayout.LayoutParams(screen_Width, screen_Height));
		myImageView.setScreenSize(context, screen_Width, screen_Height);
		myImageView.setId(position);
		final String netPath = list_Images.get(position);
		view_progress.setVisibility(View.VISIBLE);
		// String picFormat = netPath.substring(netPath.lastIndexOf(".")+1);
		// boolean isGif = picFormat.equalsIgnoreCase("gif");
		new AsynUntil().loadImageWithScale(context, netPath, new LoadImageCallBack() {

			@Override
			public void onCallBack(Bitmap bitmap) {
				if (bitmap != null) {
					myImageView.setImageBitmap(bitmap);
					myImageView.setBitmap(bitmap);
					view_progress.setVisibility(View.GONE);
				} else if (netPath.startsWith("http")){
					ImageLoaderUtil.getInstance().displayFromNetD(context, netPath, myImageView);
					view_progress.setVisibility(View.GONE);
				}
				// else{
				// LogUtil.out("myimageView ===>>> >>>>>>");
				// }
			}
		}, screen_Width, screen_Height);
		((ViewPager) view).addView(myImageView);

		return myImageView;
	}

}

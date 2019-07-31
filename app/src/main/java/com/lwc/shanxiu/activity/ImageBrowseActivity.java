package com.lwc.shanxiu.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.adapter.ImageViewPagerAdapter;
import com.lwc.shanxiu.view.TitleView;
import com.lwc.shanxiu.widget.ImageBrowseViewpager;
import com.lwc.shanxiu.widget.ZoomImageView;

import java.util.ArrayList;

/**
 * 图片浏览器
 * 
 * @version 1.0
 * @date 2013-8-28
 * 
 */
public class ImageBrowseActivity extends BaseActivity implements OnGestureListener {

	/** 进入的页面：2.为我的相册页面，1.为其他 */
	public static int type;
	/** 图片列表 */
	private ArrayList<String> list_Images;
	/** 图片适配器 */
	private ImageViewPagerAdapter adapter;
	/** 悬浮在图片顶层的view视图 */
	private View view_Top;
	/** 图片张数统计 */
	private TextView txt_PageCount, tv_count;
	/** 更多 */
	private ImageView img_more;
	// 可集成对象
	/** 图片浏览切换控件 */
	protected ImageBrowseViewpager mViewPager;
	/** 可缩放imageView控件 */
	protected ZoomImageView myImageView;
	/** 标题栏视图 */
	protected TitleView view_Title;
	/** 手势 */
	protected GestureDetector mGestureDetector;
	/** 触摸模式——无操作 */
	protected final int MODE_NONE = 0; // 无模
	/** 触摸模式——拖动 */
	protected final int MODE_DRAG = 1; // 拖动模式
	/** 触摸模式——缩放 */
	protected final int MODE_ZOOM = 2; // 缩放模式
	/** 屏幕宽度 */
	protected int screen_Width;
	/** 屏幕高度 */
	protected int screen_Height;
	/** 触摸模式——当前模式 */
	protected int current_Mode = MODE_NONE;
	/** 开始的X坐标 */
	protected float mStartX = 0;
	/** 开始的触摸点 */
	protected Point startPoint = new Point();

	/** 图片显示区域视图是否加载完毕 */
	protected boolean hasMeasured = false;
	/** 是否双击 */
	protected boolean isDoubleTap = false;
	/** 是否移动操作 */
	protected boolean isDrag = false;
	/** 是否处于放大状态 */
	protected boolean isZOOM = false;
	/** 是否收藏了图片至表情 */
	private final boolean isSaved = false;// true: 收藏了 false：未收藏

	/** 自定义的加载视图 */
	private View view_custom_progresView;

	public static final int SAVE_IMG_SUCCESS = 10;
	public static final int SAVE_IMG_FAIL = 11;
	/** 是否需要更多功能 */
	private final boolean needMore = true;
	private int index;

	@Override
	protected int getContentViewId(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		return R.layout.activity_image_browse;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void findViews() {
		mGestureDetector = new GestureDetector(this);
		mGestureDetector.setOnDoubleTapListener(new OnDoubleTapListener());

		view_Top = findViewById(R.id.activity_image_browse_view_top);
		// 设置标题的样式
		view_Title = (TitleView) findViewById(R.id.view_title);
		tv_count = (TextView) findViewById(R.id.tv_count);
		view_Title.setContentBackgroud(R.color.light_grey_black);
		view_Title.getTxt_title_name().setTextColor(Color.WHITE);
		view_Title.getImg_back().setImageResource(R.drawable.back);
		view_Title.hideLine(true);

		img_more = (ImageView) findViewById(R.id.img_more);

		mViewPager = (ImageBrowseViewpager) findViewById(R.id.activity_image_browse_viewpager);

		txt_PageCount = (TextView) findViewById(R.id.activity_image_browse_txt_pages);
		view_custom_progresView = findViewById(R.id.activity_image_browse_progress);
		view_custom_progresView.setVisibility(View.GONE);
		index = getIntent().getIntExtra("index", 0);
		list_Images = getIntent().getStringArrayListExtra("list");
		if (list_Images != null && list_Images.size() > 0 && list_Images.get(list_Images.size()-1).equals("")) {
			list_Images.remove(list_Images.size() -1);
		}
	}

	@Override
	protected void init() {
		view_Title.setTitle("图片浏览");
		getScreenSize();
	}

	@SuppressLint("NewApi")
	@Override
	protected void initGetData() {
	}

	@Override
	protected void widgetListener() {

		// 触摸事件监听
		mViewPager.setOnTouchListener(new ViewPageOnTouchListener());

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {

				String format = getString(R.string.activity_image_browser_page);
				txt_PageCount.setText(position + 1 + format + list_Images.size());
				tv_count.setText((position + 1) + "/" + list_Images.size());
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		// 返回
		view_Title.setBackButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isSaved) {
					setResult(RESULT_OK);
				}
				finish();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (isSaved) {
				setResult(RESULT_OK);
			}
			finish();
		}
		return false;
	}

	/**
	 * 获取显示区域大小
	 * 
	 * @version 1.0
	 * @createTime 2013-11-6,下午3:11:57
	 * @updateTime 2013-11-6,下午3:11:57
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	private void getScreenSize() {

		ViewTreeObserver vto = mViewPager.getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {

				if (!hasMeasured) {
					screen_Height = mViewPager.getMeasuredHeight();
					screen_Width = mViewPager.getMeasuredWidth();
					initViewPager();
					hasMeasured = true;
				}
				return true;
			}
		});
	}

	/**
	 * 舒适化viewpager对象
	 * 
	 * @version 1.0
	 * @createTime 2013-8-28,下午7:30:01
	 * @updateTime 2013-8-28,下午7:30:01
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	private void initViewPager() {

//		list_Images = new ArrayList<>();
		Rect frame = new Rect();
		getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		screen_Width = frame.width();
		screen_Height = frame.height();

		adapter = new ImageViewPagerAdapter(ImageBrowseActivity.this, list_Images, mViewPager, screen_Width, screen_Height, view_custom_progresView);
		mViewPager.setAdapter(adapter);

		String format = getString(R.string.activity_image_browser_page);

		mViewPager.setCurrentItem(index);
		txt_PageCount.setText((index+1) + format + list_Images.size());
		tv_count.setText((index+1) + "/" + list_Images.size());
	}

	/**
	 * 在退出界面的时候清空图片列表回收内存
	 *
	 * @version 1.0
	 * @createTime 2014年1月4日,下午3:22:44
	 * @updateTime 2014年1月4日,下午3:22:44
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 触摸事件监听
	 * 
	 * @version 1.0
	 * @date 2013-8-28
	 * 
	 */
	protected class ViewPageOnTouchListener implements OnTouchListener {

		@SuppressWarnings("deprecation")
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			myImageView = (ZoomImageView) mViewPager.findViewById(mViewPager.getCurrentItem());
			if (myImageView == null) {
				return false;
			}

			final int action = event.getAction();
			switch (action) {
			case MotionEvent.ACTION_DOWN:
				startPoint.x = (int) event.getX();
				startPoint.y = (int) event.getY();
				if (!(myImageView.getmBitmap() == null)) {
					mStartX = event.getRawX();
					myImageView.init(event);
					current_Mode = MODE_DRAG;
					isDoubleTap = false;
					isDrag = false;
				}
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
			case MotionEvent.ACTION_POINTER_2_DOWN:
			case MotionEvent.ACTION_POINTER_3_DOWN:
				if (!(myImageView.getmBitmap() == null)) {
					myImageView.getOldDist(event);
					current_Mode = MODE_ZOOM;
				}
				break;

			case MotionEvent.ACTION_UP:
				if (isDoubleTap) {// 如果是双击，则拦截
					return mGestureDetector.onTouchEvent(event);
				}

				// 是否触发了滑动事件
				if (Math.abs(startPoint.x - event.getX()) > 10 || Math.abs(startPoint.y - event.getY()) > 10) {
					isDrag = true;
				}

				if (!(myImageView.getmBitmap() == null) && current_Mode == MODE_DRAG) {
					myImageView.actionUp(current_Mode);
					myImageView.updateView();
				}
				break;
			case MotionEvent.ACTION_POINTER_UP:
			case MotionEvent.ACTION_POINTER_2_UP:
			case MotionEvent.ACTION_POINTER_3_UP:
				if (!(myImageView.getmBitmap() == null)) {
					myImageView.actionUp(current_Mode);
					myImageView.updateView();
				}
				current_Mode = MODE_NONE;
				break;
			case MotionEvent.ACTION_MOVE:
				if (isDoubleTap) {
					return mGestureDetector.onTouchEvent(event);
				}
				if (!(myImageView.getmBitmap() == null)) {
					if (current_Mode == MODE_DRAG) {
						float cal = calculate(mStartX, event.getRawX());
						if (myImageView.getNext() && cal > 0) {// 下一页
							return false;
						} else if (myImageView.getBack() && cal < 0) {// 上一页
							return false;
						} else {
							isZOOM = myImageView.actionDrag(event);
							myImageView.updateView();
							return isZOOM;
						}
					} else if (current_Mode == MODE_ZOOM) {
						myImageView.actionZoom(event);
						myImageView.updateView();
						return true;
					}
				}
				break;
			}
			return mGestureDetector.onTouchEvent(event);
		}

	}

	/**
	 * 计算两点之间的距离
	 * 
	 * @param x1
	 * @param x2
	 * @return
	 */
	protected float calculate(float x1, float x2) {
		// 在点(x1,y1)和点(x2,y2)之间用当前颜色画线段

		float pz = x1 - x2;// 计算两点间的距离
		return pz;
	}

	/**
	 * 双击事件
	 */
	private class OnDoubleTapListener implements GestureDetector.OnDoubleTapListener {

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			if (!isDrag) {// 如果有滑动操作，不算单击事件
				if (view_Top.isShown()) {
					view_Top.setVisibility(View.GONE);
				} else {
					view_Top.setVisibility(View.VISIBLE);
				}
			}
			return false;
		}

		@Override
		public boolean onDoubleTap(MotionEvent e) {

			isDoubleTap = true;
			if (!(myImageView.getmBitmap() == null)) {
				if (isZOOM) {
					myImageView.zoomToInit();
					myImageView.center();
					isZOOM = false;
				} else {
					myImageView.zoomToMax();
					myImageView.center();
					isZOOM = true;
				}
			}
			return false;
		}

		@Override
		public boolean onDoubleTapEvent(MotionEvent e) {

			return false;
		}

	}

	@Override
	public boolean onDown(MotionEvent e) {

		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {

		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

		return false;
	}

}
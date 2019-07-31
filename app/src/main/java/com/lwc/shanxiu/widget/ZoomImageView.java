package com.lwc.shanxiu.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * 可缩放ImageView
 * 
 * @version 1.0
 * @date 2013-8-28
 * 
 */
public class ZoomImageView extends ImageView {
	/** 屏幕宽度 */
	private int screen_Width;
	/** 屏幕高度 */
	private int screen_Height;

	/** 图片宽度 */
	private float image_Width;
	/** 图片高度 */
	private float image_Height;

	/** 当前状态的矩阵 */
	private Matrix matrix_Current;
	/** 初始状态的矩阵 */
	private Matrix matrix_First;
	/** 上一次状态的矩阵 */
	private Matrix matrix_Last;

	/** 计算的缩放值 */
	private float scale_Cacul = 0;
	/** 当前缩放大小 */
	private float scale_Current = 0;
	/** 图片的最小缩放比例 */
	private float minScaleR;
	/** 图片的最大缩放比例 */
	private static final float MAX_SCALE = 4f;//

	/** 图片第一次显示时的初始宽度 */
	private float image_initWidth = 0;
	/** 图片第一次显示时的初始高度 */
	private float image_initHeight = 0;

	/** 图片第一次显示时的左边距离 */
	private float image_InitLeft = 0;
	/** 图片第一次显示时的右边距离 */
	private float image_InitRight = 0;
	/** 图片第一次显示时的上边距离 */
	private float image_InitTop = 0;
	/** 图片第一次显示时的下边距离 */
	private float image_InitBottom = 0;

	/** 图片状态 */
	private ImageState current_ImageState = new ImageState();

	/** 图片矩阵数组 */
	private float[] values = new float[9];
	private float modifyValue = 0;

	/** 手势开始的点 */
	private PointF startPonit = new PointF();

	/** 缩放模式 */
	private String zoomMode = null;

	private float oldDist = 0;

	/** 用来放大的bitmap对象 */
	private Bitmap mBitmap = null;

	private Display display;

	// 拖动
	private float TranslateScale = 1.0f;

	public ZoomImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setScaleType(ScaleType.MATRIX);
	}

	public ZoomImageView(Context context) {
		super(context);
		this.setScaleType(ScaleType.MATRIX);
	}

	/**
	 * @return the oldDist
	 */
	public float getOldDist(MotionEvent event) {
		this.oldDist = this.spacing(event);
		if (oldDist > 10f) {
			matrix_Last.set(matrix_Current);
		}
		return oldDist;
	}

	/***
	 * 内部类，图片状态类，保存当前图片的上下左右边距
	 * 
	 * @version 1.0
	 * @date 2013-8-28
	 * 
	 */
	private class ImageState {
		private float left;
		private float top;
		private float right;
		private float bottom;
	}

	public void init(MotionEvent event) {
		matrix_Last.set(matrix_Current);
		startPonit.set(event.getX(), event.getY());
	}

	/**
	 * 设置屏幕大小
	 * 
	 * @version 1.0
	 * @createTime 2013-8-28,下午6:43:06
	 * @updateTime 2013-8-28,下午6:43:06
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context
	 * @param width
	 * @param height
	 */
	public void setScreenSize(Context context, int width, int height) {
		this.display = ((Activity) context).getWindowManager().getDefaultDisplay();
		screen_Width = width;
		screen_Height = height;
	}

	// public void setScreenSize(Context context) {
	// this.display =
	// ((Activity)context).getWindowManager().getDefaultDisplay();
	// screen_Width = display.getWidth();
	// screen_Height = display.getHeight();
	// }

	/**
	 * 获取bitmap对象
	 * 
	 * @version 1.0
	 * @createTime 2013-8-28,下午6:43:33
	 * @updateTime 2013-8-28,下午6:43:33
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return
	 */
	public Bitmap getmBitmap() {
		return mBitmap;
	}

	/**
	 * 设置bitmap对象
	 * 
	 * @version 1.0
	 * @createTime 2013-8-28,下午6:43:50
	 * @updateTime 2013-8-28,下午6:43:50
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param mBitmap
	 */
	public void setBitmap(Bitmap mBitmap) {
		this.mBitmap = mBitmap;

		image_Width = mBitmap.getWidth();
		image_Height = mBitmap.getHeight();
		float xScale = (float) screen_Width / image_Width;
		float yScale = (float) screen_Height / image_Height;
		scale_Cacul = xScale <= yScale ? xScale : yScale;
		scale_Current = scale_Cacul < 1 ? scale_Cacul : 1;
		matrix_Current = new Matrix();
		matrix_First = new Matrix();

		matrix_Last = new Matrix();
		// 平移
		matrix_Current.postTranslate((screen_Width - image_Width) / 2, (screen_Height - image_Height) / 2);

		float sX = screen_Width / 2;
		float sY = screen_Height / 2;
		image_initWidth = image_Width * scale_Current;
		image_initHeight = image_Height * scale_Current;

		matrix_Current.postScale(scale_Current, scale_Current, sX, sY);
		matrix_First.set(matrix_Current);
		matrix_Last.set(matrix_Current);
		minZoom();
		updateView();

	}

	/**
	 * 刷新界面
	 * 
	 * @version 1.0
	 * @createTime 2013-8-28,下午6:45:22
	 * @updateTime 2013-8-28,下午6:45:22
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	public void updateView() {
		this.setImageMatrix(matrix_Current);
		Rect rect = this.getDrawable().getBounds();
		this.getImageMatrix().getValues(values);
		image_Width = rect.width() * values[0];
		image_Height = rect.height() * values[0];

		current_ImageState.left = values[2];
		current_ImageState.top = values[5];
		current_ImageState.right = current_ImageState.left + image_Width;
		current_ImageState.bottom = current_ImageState.top + image_Height;
	}

	/**
	 * 计算两指之间的中点坐标
	 * 
	 * @version 1.0
	 * @createTime 2013-8-28,下午6:44:41
	 * @updateTime 2013-8-28,下午6:44:41
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param event
	 * @return
	 */
	private PointF caculMiddlePoint(MotionEvent event) {
		PointF mPointF = new PointF();
		float x = screen_Width;
		float y = screen_Height;
		try {
			x = event.getX(0) + event.getX(1);
			y = event.getY(0) + event.getY(1);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		mPointF.set(x / 2, y / 2);

		return mPointF;
	}

	/**
	 * 缩放
	 * 
	 * @version 1.0
	 * @createTime 2013-8-28,下午6:45:30
	 * @updateTime 2013-8-28,下午6:45:30
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param event
	 */
	public void actionZoom(MotionEvent event) {
		float newDist = spacing(event);
		if (newDist > 10f) {
			matrix_Current.set(matrix_Last);
			scale_Current = newDist / oldDist;
			PointF mF = new PointF();
			mF = caculMiddlePoint(event);
			// 缩放模式为缩�?
			if (scale_Current < 1) {
				zoomMode = "small";
				matrix_Current.postScale(scale_Current, scale_Current, mF.x, mF.y);
			} else {// 缩放模式为放�?
				zoomMode = "enlarge";
				matrix_Current.postScale(scale_Current, scale_Current, mF.x, mF.y);
			}

		}
	}

	/**
	 * 拖动
	 * 
	 * @version 1.0
	 * @createTime 2013-8-28,下午6:41:26
	 * @updateTime 2013-8-28,下午6:41:26
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param event
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public boolean actionDrag(MotionEvent event) {
		matrix_Current.set(matrix_Last);
		// 上下左右都至少有�?��出界时，能随意拖�?
		if ((current_ImageState.left < -8 || current_ImageState.right > screen_Width)
				&& (current_ImageState.top <= -1 || current_ImageState.bottom > display.getHeight())) {
			matrix_Current.postTranslate((event.getX() - startPonit.x) * TranslateScale, (event.getY() - startPonit.y) * TranslateScale);
			// // 当只有上下一方出界，只能上下拖动
		} else if (current_ImageState.top <= -1 || current_ImageState.bottom > display.getHeight()) {
			matrix_Current.postTranslate(0, (event.getY() - startPonit.y) * TranslateScale);
			// 当只有左右一方出界时，只能左右拖�?
		} else if (current_ImageState.left < -8 || current_ImageState.right > screen_Width) {
			matrix_Current.postTranslate((event.getX() - startPonit.x) * TranslateScale, 0);
		} else {
			return false;
		}
		return true;
	}

	/**
	 * 计算移动距离
	 * 
	 * @version 1.0
	 * @createTime 2013-8-28,下午6:41:02
	 * @updateTime 2013-8-28,下午6:41:02
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param event
	 * @return
	 */
	private float spacing(MotionEvent event) {
		float x = 0;
		float y = 0;
		try {
			x = event.getX(0) - event.getX(1);
			y = event.getY(0) - event.getY(1);
		} catch (IllegalArgumentException e) {
		e.printStackTrace();
		}

		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * 是否往下翻页
	 * 
	 * @version 1.0
	 * @createTime 2013-8-28,下午6:39:17
	 * @updateTime 2013-8-28,下午6:39:17
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return
	 */
	public boolean getNext() {
		return current_ImageState.right <= screen_Width + 1 && current_ImageState.left <= 8;
	}

	/**
	 * 是否往上翻页
	 * 
	 * @version 1.0
	 * @createTime 2013-8-28,下午6:39:42
	 * @updateTime 2013-8-28,下午6:39:42
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return
	 */
	public boolean getBack() {
		return current_ImageState.left >= -8 && current_ImageState.right >= screen_Width;
	}

	/**
	 * 手指离开屏幕
	 * 
	 * @version 1.0
	 * @createTime 2013-8-28,下午6:38:35
	 * @updateTime 2013-8-28,下午6:38:35
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param mode
	 */
	public void actionUp(int mode) {
		// 当图片脱离左边界且图片右边界大于屏幕右边时，则弹到最左边或跳到上�?��
		if (current_ImageState.left >= 0 && current_ImageState.right >= screen_Width) {
			if (image_Width > screen_Width) {
				matrix_Current.postTranslate(0 - current_ImageState.left, 0);
			} else {
				matrix_Current.set(matrix_First);
			}
		}
		// 当图片脱离右边界时，则弹到最右边或跳到下�?��
		if (current_ImageState.right <= screen_Width && current_ImageState.left <= 0) {
			if (image_Width > screen_Width) {
				matrix_Current.postTranslate(screen_Width - current_ImageState.right, 0);
			} else {
				matrix_Current.set(matrix_First);
			}
		}

		// 当图片脱离上边界时，则弹到最上边
		if (current_ImageState.top >= 0 && current_ImageState.bottom >= screen_Height) {
			matrix_Current.postTranslate(0, 0 - current_ImageState.top);
		}
		// 当图片脱离下边界时，则弹到最下边，增加修正�?50DP
		if (current_ImageState.bottom + modifyValue <= screen_Height && current_ImageState.top <= 0) {
			matrix_Current.postTranslate(0, screen_Height - current_ImageState.bottom - modifyValue);

		}

		// 若为缩放模式
		if (mode == 2) {
			// 当图片长宽都小于屏大�?时，则图片大小弹为初始�?
			updateView();
			if ((image_Width < image_initWidth) && (image_Height < image_initHeight)) {
				matrix_Current.set(matrix_First);
			}
			// 当图片有X\Y两个方向都至少有�?��脱离�?
			if ((current_ImageState.left >= image_InitLeft || current_ImageState.right <= image_InitRight)
					&& (current_ImageState.top >= image_InitTop || current_ImageState.bottom <= image_InitBottom)) {
				// 且为缩小模式时，则图片大小弹为初始�?
				if ("small".equals(zoomMode)) {
					matrix_Current.set(matrix_First);
				}
			}

			if ("enlarge".equals(zoomMode)) {// 图片超出最大缩放
				if (image_Width > image_initWidth * MAX_SCALE * 2 && image_Height > image_initHeight * MAX_SCALE * 2) {
					if (MAX_SCALE < minScaleR) {
						matrix_Current.setScale(minScaleR, minScaleR, screen_Width / 2, screen_Height / 2);
					}else{
						matrix_Current.setScale(MAX_SCALE, MAX_SCALE, screen_Width / 2, screen_Height / 2);
					}
				}
			}
		}

		center();
	}

	/**
	 * 放大到最大
	 * 
	 * @version 1.0
	 * @createTime 2013-8-28,下午6:36:50
	 * @updateTime 2013-8-28,下午6:36:50
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	public void zoomToMax() {
		if (MAX_SCALE < minScaleR) {
			matrix_Current.setScale(minScaleR, minScaleR, screen_Width / 2, screen_Height / 2);
		}else{
			matrix_Current.setScale(MAX_SCALE, MAX_SCALE, screen_Width / 2, screen_Height / 2);
		}
		this.updateView();
	}

	/**
	 * 缩为原图
	 * 
	 * @version 1.0
	 * @createTime 2013-8-28,下午6:36:58
	 * @updateTime 2013-8-28,下午6:36:58
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	public void zoomToInit() {
		matrix_Current.set(matrix_First);
//		 matrix_Current.setScale(minScaleR, minScaleR);
		this.updateView();
	}

	/**
	 * 最小缩放比例，最大为100%
	 * 
	 * @version 1.0
	 * @createTime 2013-11-6,上午11:37:35
	 * @updateTime 2013-11-6,上午11:37:35
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	private void minZoom() {
		minScaleR = Math.min((float) screen_Width / (float) mBitmap.getWidth(), (float) screen_Height / (float) mBitmap.getHeight());
		matrix_First.setScale(minScaleR, minScaleR);
		matrix_Current.setScale(minScaleR, minScaleR);
		center();
	}

	/**
	 * 图片居中显示
	 * 
	 * @version 1.0
	 * @createTime 2013-11-6,上午11:31:30
	 * @updateTime 2013-11-6,上午11:31:30
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	public void center() {
		center(true, true);
	}

	/**
	 * 横向、纵向居中
	 * 
	 * @version 1.0
	 * @createTime 2013-11-6,上午11:33:38
	 * @updateTime 2013-11-6,上午11:33:38
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param horizontal
	 *            是否横向居中
	 * @param vertical
	 *            是否垂直居中
	 */
	private void center(boolean horizontal, boolean vertical) {

		Matrix m = new Matrix();
		m.set(matrix_Current);
		RectF rect = new RectF(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
		m.mapRect(rect);

		float height = rect.height();
		float width = rect.width();

		float deltaX = 0, deltaY = 0;

		if (vertical) {

			if (height < screen_Height) {
				deltaY = (screen_Height - height) / 2 - rect.top;
			} else if (rect.top > 0) {
				deltaY = -rect.top;
			} else if (rect.bottom < screen_Height) {
				deltaY = this.getHeight() - rect.bottom;
			}
		}

		if (horizontal) {
			if (width < screen_Width) {
				deltaX = (screen_Width - width) / 2 - rect.left;
			} else if (rect.left > 0) {
				deltaX = -rect.left;
			} else if (rect.right < screen_Width) {
				deltaX = screen_Width - rect.right;
			}
		}
		matrix_Current.postTranslate(deltaX, deltaY);
		
		updateView();

	}
	
	/**
	 * 获取图形矩形区域
	 * 
	 * @version 1.0
	 * @createTime 2013-11-6,上午11:34:30
	 * @updateTime 2013-11-6,上午11:34:30
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return
	 */
	public RectF getRectF() {
		Matrix m = new Matrix();
		m.set(matrix_Current);
		RectF rect = new RectF(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
		m.mapRect(rect);
		return rect;
	}

	/**
	 * 回收图片
	 * 
	 * @version 1.0
	 * @createTime 2013-8-28,下午6:44:07
	 * @updateTime 2013-8-28,下午6:44:07
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	public void RecycleBitmap() {
		if (this.mBitmap != null) {
			this.mBitmap.recycle();
		}
	}

}
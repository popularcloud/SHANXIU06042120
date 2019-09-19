package com.lwc.shanxiu.utils;

import java.lang.reflect.Field;

import com.lwc.shanxiu.configs.TApplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.IBinder;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

/**
 * 屏幕显示工具类
 * 
 * @author 何栋
 * @date 2013-04-02
 * @version 1.0
 * 
 */
public class DisplayUtil {

	/**
	 * 获取通知栏告诉.
	 * 
	 * @param context
	 *        上下文环境.
	 * @return 通知栏高度.
	 * 
	 * @version 1.0
	 * @createTime 2013-9-15,下午2:29:29
	 * @updateTime 2013-9-15,下午2:29:29
	 * @createAuthor paladin
	 * @updateAuthor paladin
	 * @updateInfo
	 */
	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return statusBarHeight;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 * 
	 * @author 罗文忠
	 * @date 2013-04-02
	 * @version 1.0
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 * 
	 * @author 罗文忠
	 * @date 2013-04-02
	 * @version 1.0
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 获取图片后缀
	 * 
	 * @version 1.0
	 * @createTime 2013-11-25,上午12:03:15
	 * @updateTime 2013-11-25,上午12:03:15
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return
	 */
	public static String getPictureSizeSuffix() {
		switch (TApplication.context.getResources().getDisplayMetrics().densityDpi) {
		case 120:
		case 160:
		case 240:
			return "_" + 150 + "x" + 150 + ".jpg";
		default:
			return "_" + 300 + "x" + 300 + ".jpg";
		}

	}

	/**
	 * 缩略图大小
	 * 
	 * @version 1.0
	 * @createTime 2013-11-25,上午12:06:07
	 * @updateTime 2013-11-25,上午12:06:07
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return
	 */
	public static int getThumPictureSize() {
		switch (TApplication.context.getResources().getDisplayMetrics().densityDpi) {
		case 120:
		case 160:
		case 240:
			return 150;
		default:
			return 300;
		}

	}

	/**
	 * 获取屏幕宽度
	 * 
	 * @version 1.0
	 * @createTime 2014年3月18日 下午12:54:02
	 * @updateTime 2014年3月18日 下午12:54:02
	 * @createAuthor liuyue
	 * @updateAuthor liuyue
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		int screenWidth = TApplication.context.getResources().getDisplayMetrics().widthPixels;
		return screenWidth;
	}

	/**
	 * 得到屏幕除去静态状态栏的显示高度(px)
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年5月21日,上午9:46:54
	 * @updateTime 2015年5月21日,上午9:46:54
	 * @createAuthor yanzhong
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param context
	 * @return
	 */
	public static int getShowHeight(Context context) {
		int screenHeight = TApplication.context.getResources().getDisplayMetrics().heightPixels;
		int showHeight = screenHeight - getStatusBarHeight(context);
		return showHeight;
	}

	/**
	 * 根据比例设置控件的宽高
	 * 
	 * @version 1.0
	 * @createTime 2015年3月6日,下午8:00:24
	 * @updateTime 2015年3月6日,下午8:00:24
	 * @createAuthor chencong
	 * @updateAuthor chencong
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context
	 *        上下文
	 * @param view
	 *        需要设置宽高的控件
	 * @param l
	 *        给定的比例
	 */
	public static void setViewWH(Context context, View view, float l) {
		int screenWidth = DisplayUtil.getScreenWidth(context);
		int height = (int) (screenWidth / l);
		view.setLayoutParams(new LayoutParams(screenWidth, height));
	}

	/**
	 * 根据比例设置控件的宽高(迪讯好友资料页面专用)
	 * 
	 * @version 1.0
	 * @createTime 2015年3月6日,下午8:00:24
	 * @updateTime 2015年3月6日,下午8:00:24
	 * @createAuthor chencong
	 * @updateAuthor chencong
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context
	 *        上下文
	 * @param view
	 *        需要设置宽高的控件
	 * @param l
	 *        给定的比例
	 */
	public static void setViewWH_Dixun(Context context, View view, int v, float l) {
		int screenWidth = DisplayUtil.getScreenWidth(context);
		int height = (int) (screenWidth / l);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(screenWidth, height);
		layoutParams.addRule(RelativeLayout.ABOVE, v);
		view.setLayoutParams(new RelativeLayout.LayoutParams(screenWidth, height));

	}

	/**
	 * 根据比例设置控件的宽高(引导页)
	 * 
	 * @version 1.0
	 * @createTime 2015年3月6日,下午8:00:24
	 * @updateTime 2015年3月6日,下午8:00:24
	 * @createAuthor chencong
	 * @updateAuthor chencong
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context
	 *        上下文
	 * @param view
	 *        需要设置宽高的控件
	 * @param l
	 *        宽高比
	 * @param scale
	 *        整体相对于屏幕宽比例
	 */
	public static void setViewWH_TopL(Context context, View view, float l, float scale) {
		int screenWidth = (int) (DisplayUtil.getScreenWidth(context) * scale);
		int height = (int) (screenWidth / l);
		LayoutParams layoutParams = new LayoutParams(screenWidth, height);
		layoutParams.topMargin = dip2px(context, 75 * scale);
		view.setLayoutParams(layoutParams);
	}

	/**
	 * 根据比例设置控件的宽高(引导页)
	 * 
	 * @version 1.0
	 * @createTime 2015年3月6日,下午8:00:24
	 * @updateTime 2015年3月6日,下午8:00:24
	 * @createAuthor chencong
	 * @updateAuthor chencong
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context
	 *        上下文
	 * @param view
	 *        需要设置宽高的控件
	 * @param l
	 *        给定的比例
	 * @param scale
	 *        整体相对于屏幕宽比例
	 */
	public static void setViewWH_TxtL(Context context, View view, float l, float scale) {
		int screenWidth = (int) (DisplayUtil.getScreenWidth(context) * scale);
		int height = (int) (screenWidth / l);
		LayoutParams layoutParams = new LayoutParams(screenWidth, height);
		layoutParams.topMargin = dip2px(context, 20 * scale);
		view.setLayoutParams(layoutParams);
	}

	/**
	 * 根据比例设置控件的宽高（使用在加载也的广告图上面）
	 * 
	 * @version 1.0
	 * @createTime 2015年3月6日,下午8:00:24
	 * @updateTime 2015年3月6日,下午8:00:24
	 * @createAuthor chencong
	 * @updateAuthor chencong
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context
	 *        上下文
	 * @param view
	 *        需要设置宽高的控件
	 * @param l
	 *        给定的比例
	 */
	public static void setViewWH_R(Context context, View view, float l) {
		int screenWidth = DisplayUtil.getScreenWidth(context);
		int height = (int) (screenWidth / l);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(screenWidth, height);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);// 设置控件在地部
		view.setLayoutParams(layoutParams);
	}

	public static void setLinLayoutW_H(Context context, View view, float l) {
		int screenWidth = DisplayUtil.getScreenWidth(context);
		int height = (int) (screenWidth / l);
		LayoutParams layoutParams = new LayoutParams(screenWidth, height);
		view.setLayoutParams(layoutParams);
	}

	/**
	 * 显示/隐藏 软键盘
	 * 
	 * @version 1.0
	 * @createTime 2015年3月8日,上午9:36:37
	 * @updateTime 2015年3月8日,上午9:36:37
	 * @createAuthor chencong
	 * @updateAuthor chencong
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param show
	 *        true显示软键盘，false关闭软键盘
	 * @param context
	 */
	public static void showInput(boolean show, Context context) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		View currentFocus = ((Activity) context).getCurrentFocus();
		if (currentFocus != null) {
			IBinder applicationWindowToken = currentFocus.getApplicationWindowToken();
			if (show) {
				if (applicationWindowToken != null) {
					imm.showSoftInputFromInputMethod(applicationWindowToken, 0);
				}
			} else {
				if (applicationWindowToken != null) {
					if (imm.isActive()) {
						imm.hideSoftInputFromWindow(applicationWindowToken, 0);
					}
				}
			}
		}
	}

	/**
	 * 判断是否是全面屏
	 */
	private volatile static boolean mHasCheckAllScreen;
	private volatile static boolean mIsAllScreenDevice;

	public static boolean isAllScreenDevice(Context context) {
		if (mHasCheckAllScreen) {
			return mIsAllScreenDevice;
		}
		mHasCheckAllScreen = true;
		mIsAllScreenDevice = false;
		// 低于 API 21的，都不会是全面屏。。。
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			return false;
		}
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		if (windowManager != null) {
			Display display = windowManager.getDefaultDisplay();
			Point point = new Point();
			display.getRealSize(point);
			float width, height;
			if (point.x < point.y) {
				width = point.x;
				height = point.y;
			} else {
				width = point.y;
				height = point.x;
			}
			if (height / width >= 1.97f) {
				mIsAllScreenDevice = true;
			}
		}
		return mIsAllScreenDevice;
	}
}

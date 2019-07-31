package com.lwc.shanxiu.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.utils.KeyboardUtil;

/**
 * 标题栏控件
 * 
 * @Description TODO
 * @author zhou wan
 * @date 2015年2月6日
 * @Copyright: Copyright (c) 2015 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 */
public class TitleView extends RelativeLayout {
	/** 标题栏右边布局 */
	private LinearLayout ll_title_right;
	/** 标题栏左边边 返回键 */
	private ImageView img_back;
	/** 标题栏左边边 返回键图标 */
	private TextView txt_back_left;
	/** 返回键布局 */
	private LinearLayout ll_title_back;
	/** 标题栏右边的图片 默认隐藏状态 */
	private TextView txt_right_img;
	/** 标题栏右边的文字 可隐藏或者显示 */
	private TextView txt_right;
	/** 标题名字 */
	private TextView txt_title_name;
	/** 右边提示数 */
	private TextView txt_right_count;
	/** 内容view */
	private View view_content;
	/** 标题的底部线条 */
	private View view_line;
	private Context context;
	private EditText et_search;

	@SuppressLint("Recycle")
	public TitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	/**
	 * 初始化控件
	 * 
	 * @version 1.0
	 * @createTime 2015年2月6日,下午6:15:08
	 * @updateTime 2015年2月6日,下午6:15:08
	 * @createAuthor zhou wan
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param context
	 *        上下文对象
	 *        标题名称
	 */
	public void initView(Context context) {
		this.context = context;
		View titleView = LayoutInflater.from(context).inflate(R.layout.view_title, null);
		view_content = titleView.findViewById(R.id.view_content);
		img_back = (ImageView) titleView.findViewById(R.id.img_back);
		ll_title_back = (LinearLayout) titleView.findViewById(R.id.ll_title_back);
		txt_back_left = (TextView) titleView.findViewById(R.id.txt_back_left);
		et_search = (EditText) titleView.findViewById(R.id.et_search);

		ll_title_right = (LinearLayout) titleView.findViewById(R.id.ll_title_right);
		txt_right = (TextView) titleView.findViewById(R.id.txt_right);
		txt_right_img = (TextView) titleView.findViewById(R.id.txt_right_img);
		txt_title_name = (TextView) titleView.findViewById(R.id.txt_title_name);
		txt_right_count = (TextView) titleView.findViewById(R.id.txt_right_count);
		view_line = titleView.findViewById(R.id.view_line);
		txt_right_count.setVisibility(View.GONE);
		addView(titleView);
		setLeftVisible(false);

		LinearGradient mLinearGradient = new LinearGradient(0, 0, txt_title_name.getMeasuredWidth(), 0, new int[] {0xFF2bb4f8,0xFF12d183}, null, Shader.TileMode.REPEAT);
		txt_title_name.getPaint().setShader(mLinearGradient);
	}

//	@Override
//	protected void onDraw(Canvas canvas) {
//		Rect mTextBound = new Rect();
//		int mViewWidth = txt_title_name.getMeasuredWidth();
//		Paint mPaint = txt_title_name.getPaint();
//		String mTipText = txt_title_name.getText().toString();
//		mPaint.getTextBounds(mTipText, 0, mTipText.length(), mTextBound);
//		LinearGradient mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0,
//				new int[] {0xFF2bb4f8,0xFF12d183},
//				null, Shader.TileMode.REPEAT);
//		mPaint.setShader(mLinearGradient);
//		canvas.drawText(mTipText, getMeasuredWidth() / 2 - mTextBound.width() / 2, getMeasuredHeight() / 2 +   mTextBound.height()/2, mPaint);
//		super.onDraw(canvas);
//	}

	public void setEditInputVisible(boolean b) {
		et_search.setText("");
		ll_title_right.setVisibility(b ? View.GONE : View.VISIBLE);
		txt_title_name.setVisibility(b ? View.GONE : View.VISIBLE);
		et_search.setVisibility(b ? View.VISIBLE : View.GONE);
	}

	public void showTitle() {
		KeyboardUtil.showInput(false, (Activity) context);
		if (et_search.getVisibility() == View.VISIBLE) {
			ll_title_right.setVisibility(View.VISIBLE);
			txt_title_name.setVisibility(View.VISIBLE);
			et_search.setVisibility(View.GONE);
		}
	}

	public void setLeftVisible(boolean visible) {
		if (visible) {
			ll_title_back.setVisibility(View.VISIBLE);
		} else {
			ll_title_back.setVisibility(View.INVISIBLE);
		}

	}

	public void setRightVisible(boolean visible) {
		if (visible) {
			ll_title_right.setVisibility(View.VISIBLE);
		} else {
			ll_title_right.setVisibility(View.INVISIBLE);
		}

	}

	public void setRight(String rightText, int pic, OnClickListener listener) {
		setRightVisible(true);
		setRightText(rightText);
		setRightDrawble(pic);
		setRightViewListener(listener);
	}

	private void setRightDrawble(int pic) {
		txt_right_img.setVisibility(View.VISIBLE);
		txt_right_img.setBackgroundResource(pic);
	}

	private void setRightText(String rightText) {
		if (!TextUtils.isEmpty(rightText)) {
			txt_right.setText(rightText);
		}
	}

	/**
	 * 按返回键结束activity
	 * 
	 * @version 1.0
	 * @createTime 2015年2月6日,下午6:22:23
	 * @updateTime 2015年2月6日,下午6:22:23
	 * @createAuthor zhou wan
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param mActivity
	 */
	public void setActivityFinish(final Activity mActivity) {
		setLeftVisible(true);
		ll_title_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				KeyboardUtil.showInput(false, mActivity);
				mActivity.finish();
				mActivity.overridePendingTransition(R.anim.exit_enter, R.anim.exit_exit);
			}
		});

	}

	/**
	 * 按返回键事件设置
	 * 
	 * @version 1.0
	 * @createTime 2015年2月6日,下午6:22:23
	 * @updateTime 2015年2月6日,下午6:22:23
	 * @createAuthor zhou wan
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param listener
	 */
	public void setBackClicker(OnClickListener listener) {
		setLeftVisible(true);
		ll_title_back.setOnClickListener(listener);
	}

	@Override
	public void setOnTouchListener(OnTouchListener l) {
		showInput(false);
		super.setOnTouchListener(l);
	}

	/**
	 * 显示/关闭软件盘
	 * 
	 * @version 1.0
	 * @createTime 2013-11-28,下午3:22:55
	 * @updateTime 2013-11-28,下午3:22:55
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param show
	 *        true显示软键盘，false关闭软键盘
	 */
	private void showInput(boolean show) {
		if (show) {
			((Activity) context).getWindow()
					.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
			InputMethodManager imm = (InputMethodManager) ((Activity) context).getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInputFromInputMethod(((Activity) context).getCurrentFocus().getApplicationWindowToken(), 0);
			if (imm.isActive()) {
				// 如果开启
				imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
				// 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
			}
		} else if (((Activity) context).getCurrentFocus() != null) {
			((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
			InputMethodManager imm = (InputMethodManager) ((Activity) context).getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getApplicationWindowToken(), 0);
		}
	}

	/**
	 * 给右边的view添加监听
	 * 
	 * @version 1.0
	 * @createTime 2015年2月6日,下午6:25:51
	 * @updateTime 2015年2月6日,下午6:25:51
	 * @createAuthor zhou wan
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param mOnClickListener
	 *        传入外部监听
	 */
	public void setRightViewListener(OnClickListener mOnClickListener) {
		ll_title_right.setOnClickListener(mOnClickListener);
	}

	/**
	 * 设置标题栏右边的view，是否隐藏
	 * 
	 * @version 1.0
	 * @createTime 2015年2月6日,下午6:19:33
	 * @updateTime 2015年2月6日,下午6:19:33
	 * @createAuthor zhou wan
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param isHide
	 *        true 隐藏 false 显示
	 */
	public void setRightViewHide(boolean isHide) {
		if (isHide) {
			ll_title_right.setVisibility(View.GONE);
		} else {
			ll_title_right.setVisibility(View.VISIBLE);
		}
	}

	/***
	 * 
	 * 修改标题名称
	 * 
	 * @version 1.0
	 * @createTime 2015年3月6日,下午1:58:29
	 * @updateTime 2015年3月6日,下午1:58:29
	 * @createAuthor zhou wan
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param titleName
	 */
	public void setTitle(String titleName) {
		txt_title_name.setText(titleName);

//		txt_title_name.getViewTreeObserver().addOnGlobalLayoutListener(new
//
//		ViewTreeObserver.OnGlobalLayoutListener() {
//
//			@Override
//			public void onGlobalLayout() {
//				txt_title_name.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//				int height = txt_title_name.getMeasuredHeight();
//				int width = txt_title_name.getMeasuredWidth();
//				Shader shader = new LinearGradient(0, 0, width, 0, 0xFF2bb4f8, 0xFF12d183, Shader.TileMode.CLAMP);
//				// Shader shader = new LinearGradient(0, 0, width, 0, new int[] { 0xFF36b2f0, 0xFF36b2f0, 0xFF28cf8a }, new float[] { 0, 0.5f, 1 },
//				// Shader.TileMode.CLAMP);
//				txt_title_name.getPaint().setShader(shader);
//			}
//		});
	}

	/**
	 * 设置左边返回按钮的监听事件
	 * 
	 * @version 1.0
	 * @createTime 2015年3月23日,上午10:21:12
	 * @updateTime 2015年3月23日,上午10:21:12
	 * @createAuthor chencong
	 * @updateAuthor chencong
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param onClickListener
	 */
	public void setBackButton(OnClickListener onClickListener) {
		ll_title_back.setOnClickListener(onClickListener);
	}

	/**
	 * 得到标题的textview
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年4月28日,下午5:39:14
	 * @updateTime 2015年4月28日,下午5:39:14
	 * @createAuthor
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @return
	 */
	public TextView getTxt_title_name() {
		return txt_title_name;
	}

	/**
	 * 
	 * 设置标题右边的计数值
	 * 
	 * @version 1.0
	 * @createTime 2015年4月28日,下午5:32:09
	 * @updateTime 2015年4月28日,下午5:32:09
	 * @createAuthor
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param size
	 */
	public void setRightBtnCounts(int size) {
		if (size > 0) {
			txt_right_count.setVisibility(View.VISIBLE);
			txt_right_count.setText(size + "");
		} else {
			txt_right_count.setVisibility(View.GONE);
		}
	}

	/**
	 * 得到标题右边的布局
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年4月28日,下午5:32:18
	 * @updateTime 2015年4月28日,下午5:32:18
	 * @createAuthor
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @return
	 */
	public LinearLayout getLl_title_right() {
		return ll_title_right;
	}

	/**
	 * 得到标题左边的imageview
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年4月28日,下午5:32:24
	 * @updateTime 2015年4月28日,下午5:32:24
	 * @createAuthor
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @return
	 */
	public ImageView getImg_back() {
		return img_back;
	}

	/**
	 * 得到标题左边的textview
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年4月28日,下午5:32:30
	 * @updateTime 2015年4月28日,下午5:32:30
	 * @createAuthor
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @return
	 */
	public TextView getTxt_back_left() {
		return txt_back_left;
	}

	/**
	 * 得到标题左边的布局
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年4月28日,下午5:32:37
	 * @updateTime 2015年4月28日,下午5:32:37
	 * @createAuthor
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @return
	 */
	public LinearLayout getLl_title_back() {
		return ll_title_back;
	}

	/**
	 * 得到标题右边textview
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年4月28日,下午5:32:43
	 * @updateTime 2015年4月28日,下午5:32:43
	 * @createAuthor
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @return
	 */
	public TextView getTxt_right() {
		return txt_right;
	}

	/**
	 * 得到标题右边图标的textview
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年4月28日,下午5:32:48
	 * @updateTime 2015年4月28日,下午5:32:48
	 * @createAuthor
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @return
	 */
	public TextView getTxt_right_img() {
		if (txt_right_img.getVisibility() != View.VISIBLE) {
			txt_right_img.setVisibility(View.VISIBLE);
		}
		return txt_right_img;
	}

	/**
	 * 得到红色点
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年4月28日,下午6:41:48
	 * @updateTime 2015年4月28日,下午6:41:48
	 * @createAuthor
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @return
	 */
	public TextView getTxt_right_count() {
		if (txt_right_count.getVisibility() != View.VISIBLE) {
			txt_right_count.setVisibility(View.VISIBLE);
		}
		return txt_right_count;
	}

	/**
	 * 设置内容view的背景
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年6月10日,下午8:08:32
	 * @updateTime 2015年6月10日,下午8:08:32
	 * @createAuthor yanzhong
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param color
	 */
	public void setContentBackgroud(int color) {
		view_content.setBackgroundColor(color);
	}

	/**
	 * 设置底部线条是否隐藏
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年6月10日,下午9:04:30
	 * @updateTime 2015年6月10日,下午9:04:30
	 * @createAuthor yanzhong
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param bool
	 */
	public void hideLine(boolean bool) {
		if (bool) {
			view_line.setVisibility(View.GONE);
		} else {
			view_line.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置右边按钮是否可点击和文字的颜色
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年7月7日,下午11:14:30
	 * @updateTime 2015年7月7日,下午11:14:30
	 * @createAuthor yanzhong
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param boo
	 *        true 字体为蓝色，false 字体为灰色
	 */
	public void setRightClickable(boolean boo) {
		ll_title_right.setEnabled(boo);
		ll_title_right.setClickable(boo);
		if (boo) {
			txt_right.setTextColor(getResources().getColor(R.color.text_blue_1b));
		} else {
			txt_right.setTextColor(getResources().getColor(R.color.gray78));
		}
	}

	public EditText getSearchView() {
		return et_search;
	}

}

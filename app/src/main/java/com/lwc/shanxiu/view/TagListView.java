/**
 * 
 */
package com.lwc.shanxiu.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;


import com.lwc.shanxiu.R;
import com.lwc.shanxiu.configs.TApplication;

import java.util.ArrayList;
import java.util.List;


/**
 * @author kince
 * @category 模仿最美应用底部tagview
 * 
 */
public class TagListView extends FlowLayout implements OnClickListener {

	private boolean mIsDeleteMode = true;
	private OnTagCheckedChangedListener mOnTagCheckedChangedListener;
	private OnTagClickListener mOnTagClickListener;
	private int mTagViewBackgroundResId;
	private int mTagViewTextColorResId= R.color.blue_00aaf5;
	private final List<Tag> mTags = new ArrayList<Tag>();
	private TApplication app;
	private int index;
	/**
	 * @param context
	 */
	public TagListView(Context context) {
		super(context);
		try {
			app = (TApplication)((Activity)context).getApplication();
		} catch (Exception e) {
			e.printStackTrace();
		}
		init();
	}

	/**
	 * @param context
	 * @param attributeSet
	 */
	public TagListView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		try {
			app = (TApplication)((Activity)context).getApplication();
		} catch (Exception e) {
			e.printStackTrace();
		}
		init();
	}

	/**
	 * @param context
	 * @param attributeSet
	 * @param defStyle
	 */
	public TagListView(Context context, AttributeSet attributeSet, int defStyle) {
		super(context, attributeSet, defStyle);
		try {
			app = (TApplication)((Activity)context).getApplication();
		} catch (Exception e) {
			e.printStackTrace();
		}
		init();
	}

	@Override
	public void onClick(View v) {
//		if ((v instanceof ImageView)) {
			Tag localTag = (Tag) v.getTag();
			if (this.mOnTagClickListener != null) {
				this.mOnTagClickListener.onTagClick(localTag);
			}
//		}
	}

	private void init() {

	}

	private void inflateTagView(final Tag t, boolean b) {
		View v = View.inflate(getContext(),
				R.layout.tag, null);
		TagView localTagView = (TagView) v.findViewById(R.id.tagview);
		localTagView.setText(t.getLabelName());
		localTagView.setTag(t);
		localTagView.setOnClickListener(this);

//		if (noShow && index < 4)
//			localTagView.setTextColor(getResources().getColor(R.color.blue_00aaf5));
		if (noShow)
			localTagView.setBackgroundResource(R.drawable.tag_bg2);
		if(t.isChecked()) {
//			if (mTagViewTextColorResId > 0) {
				int c = getResources().getColor(R.color.white);
				localTagView.setTextColor(c);
//			}
			if (mTagViewBackgroundResId > 0) {
				localTagView.setBackgroundResource(mTagViewBackgroundResId);
			}
		} else {
			int c = getResources().getColor(R.color.gray_99);
			localTagView.setTextColor(c);
		}
//		if (mTagViewTextColorResId > 0) {
//			int c = getResources().getColor(mTagViewTextColorResId);
//			localTagView.setTextColor(c);
//		}
//
//		if (mTagViewBackgroundResId > 0) {
//			localTagView.setBackgroundResource(mTagViewBackgroundResId);
//		}

//		localTagView.setChecked(t.isChecked());
//		localTagView.setCheckEnable(b);
//		if (mIsDeleteMode) {
//			int k = (int) TypedValue.applyDimension(1, 5.0F, getContext()
//					.getResources().getDisplayMetrics());
//			localTagView.setPadding(localTagView.getPaddingLeft(),
//					localTagView.getPaddingTop(), k,
//					localTagView.getPaddingBottom());
//			localTagView.setCompoundDrawablesWithIntrinsicBounds(0, 0,
//					R.drawable.brand_cancel, 0);
//		}
//		if (t.getBackgroundResId() > 0) {
//			localTagView.setBackgroundResource(t.getBackgroundResId());
//		}
//		if ((t.getLeftDrawableResId() > 0) || (t.getRightDrawableResId() > 0)) {
//			localTagView.setCompoundDrawablesWithIntrinsicBounds(
//					t.getLeftDrawableResId(), 0, t.getRightDrawableResId(), 0);
//		}
//		localTagView.setOnClickListener(this);
//		localTagView
//				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//					public void onCheckedChanged(
//							CompoundButton paramAnonymousCompoundButton,
//							boolean paramAnonymousBoolean) {
//						t.setChecked(paramAnonymousBoolean);
//						if (TagListView.this.mOnTagCheckedChangedListener != null) {
//							TagListView.this.mOnTagCheckedChangedListener
//									.onTagCheckedChanged(
//											(TagView) paramAnonymousCompoundButton,
//											t);
//						}
//					}
//				});
		addView(v);
	}

	public void addTag(String i, String s) {
		addTag(i, s, false);
	}

	public void addTag(String i, String s, boolean b) {
		addTag(new Tag(i, s), b);
	}

	public void addTag(Tag tag) {
		addTag(tag, false);
	}

	public void addTag(Tag tag, boolean b) {
		mTags.add(tag);
		inflateTagView(tag, b);
	}

	public void addTags(List<Tag> lists) {
		addTags(lists, false);
	}

	public void addTags(List<Tag> lists, boolean b) {
		for (int i = 0; i < lists.size(); i++) {
			addTag((Tag) lists.get(i), b);
		}
	}

	public List<Tag> getTags() {
		return mTags;
	}

	public View getViewByTag(Tag tag) {
		return findViewWithTag(tag);
	}

	public void removeTag(Tag tag) {
		mTags.remove(tag);
		removeView(getViewByTag(tag));
	}

	public void setDeleteMode(boolean b) {
		mIsDeleteMode = b;
	}

	public void setOnTagCheckedChangedListener(
			OnTagCheckedChangedListener onTagCheckedChangedListener) {
		mOnTagCheckedChangedListener = onTagCheckedChangedListener;
	}

	public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
		mOnTagClickListener = onTagClickListener;
	}

	public void setTagViewBackgroundRes(int res) {
		mTagViewBackgroundResId = res;
	}

	public void setTagViewTextColorRes(int res) {
		mTagViewTextColorResId = res;
	}

	public void setTags(List<? extends Tag> lists) {
		setTags(lists, false);
	}

	public void setTags(List<? extends Tag> lists, boolean b) {
		removeAllViews();
		mTags.clear();
		for (int i = 0; i < lists.size(); i++) {
			index = i;
			addTag((Tag) lists.get(i), b);
		}
	}

	public static abstract interface OnTagCheckedChangedListener {
		public abstract void onTagCheckedChanged(TagView tagView, Tag tag);
	}

	public static abstract interface OnTagClickListener {
		public abstract void onTagClick(Tag tag);
	}
	private boolean noShow = false;
	public void setNo(boolean b) {
		this.noShow = b;
	}

}

package com.lwc.shanxiu.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.bean.MySkillBean;
import com.lwc.shanxiu.view.JnItem;

import java.util.List;

public class MySkillsAdapter extends ComAdapter<MySkillBean> {

	public MySkillsAdapter(Context context, List<MySkillBean> datas, int layoutId) {
		super(context, datas, layoutId);
	}

	@Override
	public void convert(ComViewHolder holder, MySkillBean t) {
		final LinearLayout list = holder.getView(R.id.layout_jineng_list);
		TextView txt_skill = holder.getView(R.id.txt_skill);
		holder.setText(R.id.txt_skill, t.getDeviceTypeName());
		list.removeAllViews();
		if (t.getSkills() != null) {
			String[] arr = t.getSkills().split(",");
			for (int i = 0; i < arr.length; i++) {
				JnItem reItem = new JnItem(mContext);
				reItem.initData(arr[i]);
				list.addView(reItem);
			}
		}

		txt_skill.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (list.getVisibility() == View.VISIBLE) {
					list.setVisibility(View.GONE);
				} else {
					list.setVisibility(View.VISIBLE);
				}
			}
		});
	}

}

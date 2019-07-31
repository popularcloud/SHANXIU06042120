package com.lwc.shanxiu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.bean.MySkillBean;

public class JnItem extends LinearLayout {

	private View view;
	private Context mContext;
	private TextView txt_skill;

	public JnItem(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public JnItem(Context context) {
		this(context,null);
		this.mContext = context;
		view = View.inflate(context, R.layout.item_my_skill2, this);
		txt_skill = (TextView) view.findViewById(R.id.txt_skill);
	}

	public void initData(String name){
		//初始化数据
		txt_skill.setText(name);
	}
	
}

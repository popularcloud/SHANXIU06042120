package com.lwc.shanxiu.module.setting;

import android.os.Bundle;
import android.view.View;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.utils.IntentUtil;

/**
 * 常见问题
 * 
 * @author 何栋
 * @date 2018年01月19日
 * @Copyright: lwc
 */
public class IssueActivity extends BaseActivity {


	@Override
	protected int getContentViewId(Bundle savedInstanceState) {
		return R.layout.activity_issue;
	}

	@Override
	protected void findViews() {
		setTitle("常见问题");
		showBack();
	}

	public void onclickBmd(View v) {
		String tag = (String) v.getTag();
		Bundle bundle = new Bundle();
		bundle.putString("type", tag);
		IntentUtil.gotoActivity(this, WhitelistingActivity.class, bundle);
	}

	@Override
	protected void initGetData() {
	}

	@Override
	protected void widgetListener() {
	}

	@Override
	protected void init() {
	}

}

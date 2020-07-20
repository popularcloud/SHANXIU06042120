package com.lwc.shanxiu.module.authentication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxz.PagerSlidingTabStrip;
import com.gyf.immersionbar.ImmersionBar;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.BaseFragmentActivity;
import com.lwc.shanxiu.module.authentication.adapter.AuthenticationPagerAdapter;
import com.lwc.shanxiu.module.authentication.fragment.ExaminationFragment;
import com.lwc.shanxiu.module.authentication.fragment.TrainFragment;
import com.lwc.shanxiu.module.authentication.fragment.WrongTopicFragment;
import com.lwc.shanxiu.module.order.ui.fragment.FinishFragment;
import com.lwc.shanxiu.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 认证工程师
 */
public class AuthenticationMainActivity extends BaseFragmentActivity {

	@BindView(R.id.img_back)
	ImageView imgBack;
	@BindView(R.id.txtActionbarTitle)
	TextView txtActionbarTitle;
	@BindView(R.id.cViewPager)
	CustomViewPager cViewPager;

	@BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;

	private HashMap<Integer, Fragment> fragmentHashMap;
	private TrainFragment trainFragment;
	private String[] titles = new String[]{"培训","考试","错题"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_authenticaiton_main);
		ButterKnife.bind(this);
		imgBack.setVisibility(View.VISIBLE);
		txtActionbarTitle.setText("认证工程师");
		ImmersionBar.with(this)
				.statusBarColor(R.color.white)
				.statusBarDarkFont(true).init();

		addFragmenInList();
		bindViewPage();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}


	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		int position = intent.getIntExtra("position",2);
		if(cViewPager != null){
			cViewPager.setCurrentItem(position);
		}
	}

	/**
	 * 往fragmentHashMap中添加 Fragment
	 */
	private void addFragmenInList() {
		fragmentHashMap = new HashMap<>();
		fragmentHashMap.put(0,new TrainFragment());
		fragmentHashMap.put(1,new ExaminationFragment());
		fragmentHashMap.put(2,new WrongTopicFragment());
	}

	@OnClick({R.id.img_back})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.img_back:
				onBackPressed();
				break;
		}
	}


	private void bindViewPage() {
		//是否滑动
		cViewPager.setPagingEnabled(true);
		cViewPager.setAdapter(new AuthenticationPagerAdapter(getSupportFragmentManager(),titles,fragmentHashMap));
		tabs.setViewPager(cViewPager);
		cViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

		// 设置Tab底部线的高度,传入的是dp
		tabs.setUnderlineHeight(0);
		// 设置Tab 指示器Indicator的高度,传入的是dp
		tabs.setIndicatorHeight(1);
		// 设置Tab Indicator的颜色
		tabs.setIndicatorColor(getResources().getColor(R.color.btn_blue_nomal));
		// 设置Tab标题文字的大小,传入的是sp
		tabs.setTextSize(15);
		// 设置选中Tab文字的颜色
		tabs.setSelectedTextColor(getResources().getColor(R.color.btn_blue_nomal));
		//设置正常Tab文字的颜色
		tabs.setTextColor(getResources().getColor(R.color.color_33));
	}


	@Override
	public void initView() {

	}

	@Override
	public void initEngines() {

	}

	@Override
	public void getIntentData() {

	}
}

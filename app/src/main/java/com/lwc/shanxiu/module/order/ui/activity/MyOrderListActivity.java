package com.lwc.shanxiu.module.order.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.BaseFragmentActivity;
import com.lwc.shanxiu.module.common_adapter.FragmentsPagerAdapter;
import com.lwc.shanxiu.module.order.ui.fragment.FinishFragment;
import com.lwc.shanxiu.module.order.ui.fragment.ProceedFragment;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.widget.CustomViewPager;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的订单列表
 * 
 * @author 何栋
 * @date 2017年12月01日
 */
public class MyOrderListActivity extends BaseFragmentActivity {

	@BindView(R.id.txtActionbarTitle)
	TextView txtActionbarTitle;
	@BindView(R.id.img_back)
	ImageView imgBack;
	@BindView(R.id.imgRight)
	ImageView imgRight;
	@BindView(R.id.rBtnUnderway)
	RadioButton rBtnUnderway;
	@BindView(R.id.rBtnFinish)
	RadioButton rBtnFinish;
	@BindView(R.id.viewLine1)
	View viewLine1;
	@BindView(R.id.viewLine3)
	View viewLine3;
	@BindView(R.id.cViewPager)
	CustomViewPager cViewPager;
	private HashMap rButtonHashMap;
	private HashMap<Integer, Fragment> fragmentHashMap;
	private ProceedFragment proceedFragment;
	private FinishFragment finishFragment;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
//        getWindow().setFormat(PixelFormat.TRANSLUCENT);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_order_list);
		ButterKnife.bind(this);
		txtActionbarTitle.setText("我的订单");
		imgBack.setVisibility(View.VISIBLE);
		addFragmenInList();
		addRadioButtonIdInList();
		bindViewPage(fragmentHashMap);
		cViewPager.setCurrentItem(0, false);

		imgRight.setImageResource(R.drawable.search_right);
		imgRight.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				IntentUtil.gotoActivity(MyOrderListActivity.this, OrderListActivity.class);
			}
		});
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

	/**
	 * 往fragmentHashMap中添加 Fragment
	 */
	private void addFragmenInList() {

		fragmentHashMap = new HashMap<>();
		proceedFragment = new ProceedFragment();
		finishFragment = new FinishFragment();
		fragmentHashMap.put(0, proceedFragment);
		fragmentHashMap.put(1, finishFragment);
	}

	/**
	 * 往rButtonHashMap中添加 RadioButton Id
	 */
	private void addRadioButtonIdInList() {

		rButtonHashMap = new HashMap<>();
		rButtonHashMap.put(0, rBtnUnderway);
		rButtonHashMap.put(1, rBtnFinish);
	}

	@OnClick({R.id.rBtnUnderway, R.id.rBtnFinish, R.id.img_back})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.rBtnUnderway:
				showLineView(1);
				cViewPager.setCurrentItem(0);
				break;
			case R.id.rBtnFinish:
				showLineView(2);
				cViewPager.setCurrentItem(1);
				break;
			case R.id.img_back:
				onBackPressed();
				break;
		}
	}


	/**
	 * 显示RadioButton线条
	 *
	 * @param num 1 ： 显示已发布下的线条  2 ： 显示未完成下的线条  3： 显示已完成下的线条  。未选中的线条将会被隐藏
	 */
	private void showLineView(int num) {

		switch (num) {
			case 1:
				imgRight.setVisibility(View.GONE);
				viewLine1.setVisibility(View.VISIBLE);
				viewLine3.setVisibility(View.INVISIBLE);
				break;
			case 2:
				imgRight.setVisibility(View.VISIBLE);
				viewLine3.setVisibility(View.VISIBLE);
				viewLine1.setVisibility(View.INVISIBLE);
				break;
		}
	}

	private void bindViewPage(HashMap<Integer, Fragment> fragmentList) {
		//是否滑动
		cViewPager.setPagingEnabled(true);
		cViewPager.setAdapter(new FragmentsPagerAdapter(getSupportFragmentManager(), fragmentList));
		cViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				cViewPager.setChecked(rButtonHashMap, position);
				showLineView(position + 1);
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
	}

}

package com.lwc.shanxiu.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.user.LoginOrRegistActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页
 * @author 何栋
 * @date 2017-07-20
 */
public class IntroduceActivity extends Activity {

	private View view1, view2, view3, view4;// 需要滑动的页卡
	private ViewPager viewPager;// viewpager
	private List<View> viewList;// 把需要滑动的页卡添加到这个list中
	private List<String> titleList = new ArrayList<>();// viewpager的标题
	 // 底部小点的图片
    private ImageView[] points;
    // 记录当前选中位置
    private int currentIndex;
	private ImageView bnStart2;
	private LinearLayout linearLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);   
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);  
		setContentView(R.layout.introduce);
		initView();
	}
	
	/**
     * 初始化底部小点
     */
    private void initPoint() {
       linearLayout = (LinearLayout) findViewById(R.id.ll_dian);

        points = new ImageView[viewList.size()];

        // 循环取得小点图片
        for (int i = 0; i < viewList.size(); i++) {
            // 得到一个LinearLayout下面的每一个子元素
            points[i] = (ImageView) linearLayout.getChildAt(i);
            // 默认都设为灰色
            points[i].setEnabled(true);
            // 给每个小点设置监听
//            points[i].setOnClickListener(this);
            // 设置位置tag，方便取出与当前位置对应
            points[i].setTag(i);
        }

        // 设置当面默认的位置
        currentIndex = 0;
        // 设置为白色，即选中状态
        points[currentIndex].setEnabled(false);
    }

	/*
	 * 在这里需要说明一下，在上面的图片中我们看到了，PagerTabStrip，PagerTitleStrip，他们其实是viewpager的一个指示器，
	 * 前者效果就是一个横的粗的下划线
	 * ，后者用来显示各个页卡的标题，当然而这也可以共存。在使用他们的时候需要注意，看下面的布局文件，要在android.support
	 * .v4.view.ViewPager里面添加
	 * android.support.v4.view.PagerTabStrip以及android.support
	 * .v4.view.PagerTitleStrip。
	 */
	@SuppressLint("InflateParams")
	@SuppressWarnings("deprecation")
	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.viewpager);
//		 pagerTitleStrip = (PagerTitleStrip) findViewById(R.id.pagertitle);
//		 pagerTabStrip=(PagerTabStrip) findViewById(R.id.pagertab);
//		 pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.gold));
//		 pagerTabStrip.setDrawFullUnderline(false);
//		 pagerTabStrip.setBackgroundColor(getResources().getColor(R.color.azure));
//		 pagerTabStrip.setTextSpacing(50);

		@SuppressWarnings("static-access")
		LayoutInflater lf = getLayoutInflater().from(this);
		
		view1 = lf.inflate(R.layout.introduce_page, null);
		ImageView iv = (ImageView) view1.findViewById(R.id.imageView1);
		iv.setImageResource(R.drawable.nav_1);

		view2 = lf.inflate(R.layout.introduce_page, null);
		ImageView iv1 = (ImageView) view2.findViewById(R.id.imageView1);
		iv1.setImageResource(R.drawable.nav_2);

		view3 = lf.inflate(R.layout.introduce_page, null);
		ImageView iv3 = (ImageView) view3.findViewById(R.id.imageView1);
		iv3.setImageResource(R.drawable.nav_3);

		view4 = lf.inflate(R.layout.introduce_page1, null);
		ImageView iv4 = (ImageView) view4.findViewById(R.id.imageView1);
		iv4.setImageResource(R.drawable.nav_4);
		bnStart2 = (ImageView) view4.findViewById(R.id.bnStart);
		bnStart2.setImageResource(R.drawable.nav_button);

		bnStart2.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(IntroduceActivity.this, LoginOrRegistActivity.class);
				startActivity(i);
				finish();
			}
		});
		viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
		viewList.add(view1);
		viewList.add(view2);
		viewList.add(view3);
		viewList.add(view4);
		PagerAdapter pagerAdapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {

				return arg0 == arg1;
			}

			@Override
			public int getCount() {

				return viewList.size();
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView(viewList.get(position));

			}

			@Override
			public int getItemPosition(Object object) {

				return super.getItemPosition(object);
			}

			@Override
			public CharSequence getPageTitle(int position) {

				return titleList.get(position);// 直接用适配器来完成标题的显示，所以从上面可以看到，我们没有使用PagerTitleStrip。当然你可以使用。

			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				container.addView(viewList.get(position));
				return viewList.get(position);
			}

		};
		viewPager.setAdapter(pagerAdapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				 setCurDot(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		initPoint();
	}

	/**
     * 设置当前的小点的位置
     */
    private void setCurDot(int positon) {
        if (positon < 0 || positon > viewList.size() - 1 || currentIndex == positon) {
            return;
        }
        points[positon].setEnabled(false);
        points[currentIndex].setEnabled(true);

        currentIndex = positon;
		if (positon == 3) {
			linearLayout.setVisibility(View.GONE);
		} else {
			linearLayout.setVisibility(View.VISIBLE);
		}
    }

}

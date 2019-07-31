package com.lwc.shanxiu.module.setting;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.WhitelistingBean;
import com.lwc.shanxiu.map.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 白名单设置
 * 
 * @author 何栋
 * @date 2018年01月19日
 * @Copyright: lwc
 */
public class WhitelistingActivity extends BaseActivity {

	private List<WhitelistingBean> list = new ArrayList<>();
	@BindView(R.id.tv_ms1)
	TextView tv_ms1;
	@BindView(R.id.tv_ms2)
	TextView tv_ms2;
	@BindView(R.id.tv_ms3)
	TextView tv_ms3;
	@BindView(R.id.tv_ms4)
	TextView tv_ms4;
	@BindView(R.id.tv_ms5)
	TextView tv_ms5;
	@BindView(R.id.miv5)
	ImageView miv5;
	@BindView(R.id.miv4)
	ImageView miv4;
	@BindView(R.id.miv1)
	ImageView miv1;
	@BindView(R.id.miv2)
	ImageView miv2;
	@BindView(R.id.miv3)
	ImageView miv3;
	private String type;

	@Override
	protected int getContentViewId(Bundle savedInstanceState) {
		return R.layout.activity_whitelisting;
	}

	@Override
	protected void findViews() {
		ButterKnife.bind(this);
		setTitle("白名单设置");
		showBack();
		WhitelistingBean b1 = new WhitelistingBean();
		b1.setImg1(R.drawable.huawei5);
		b1.setBuz1("华为手机设置白名单步骤：\n启动密修工程师app，点击下图红框中的□符号，唤出任务管理器，选择密修工程师的程序向下滑动一下，App上显示小锁，表明成功加入白名单。");
		list.add(b1);
		WhitelistingBean b2 = new WhitelistingBean();
		b2.setBuz1("小米/红米手机设置白名单步骤：\n启动密修工程师App，点击后台应用管理键（即“≡”键）弹出正在后台运行的应用图标，选择密修工程师图标向下拉出现锁定任务，点击锁定任务，应用图标右上方出现小锁，即添加白名单成功");
		b2.setImg1(R.drawable.xiaomi1);
		b2.setImg2(R.drawable.xiaomi);
		list.add(b2);
		WhitelistingBean b3 = new WhitelistingBean();
		b3.setBuz1("OPPO手机设置白名单步骤：\n启动密修工程师App，长按手机左下角，唤出后台管理器，然后长按或者下拉密修工程师App，然后App会出现一个白色小锁，表明成功加入白名单。");
//		b3.setImg1(R.drawable.xiaomi);
		list.add(b3);
		WhitelistingBean b4 = new WhitelistingBean();
		b4.setBuz1("ViVO手机设置白名单步骤：\n启动密修工程师App，从屏幕底端空白处上划，唤出任务管理器，然后长按或者下拉密修工程师App，页面上会出现“加入白名单”，点击此文字会App出现小锁，表明成功加入白名单。");
//		b4.setImg1(R.drawable.xiaomi);
		list.add(b4);

		WhitelistingBean b5 = new WhitelistingBean();
		b5.setBuz1("魅族手机设置白名单步骤：\n启动密修工程师App，从屏幕底端空白处上划，唤出任务管理器，然后长按或者下拉密修工程师App，页面上会出现“加入白名单”，点击此文字会App出现小锁，表明成功加入白名单。");
		b5.setImg1(R.drawable.meizu);
		list.add(b5);

		WhitelistingBean b6 = new WhitelistingBean();
		b6.setBuz1("三星手机设置白名单步骤：\n点击智能管理器→内存→自启动应用程序→找到密修app打开开关即可。");
		list.add(b6);
		WhitelistingBean b7 = new WhitelistingBean();
		b7.setBuz1("手机管家设置白名单步骤：\n打开手机系统自带手机管家");
		b7.setImg1(R.drawable.huawei1);
		b7.setBuz2("点击右上角的齿轮图标");
		b7.setImg2(R.drawable.huawei2);
		b7.setBuz3("点击受保护的应用");
		b7.setImg3(R.drawable.huawei3);
		b7.setBuz4("找到密修工程师开启保护");
		b7.setImg4(R.drawable.huawei4);
		b7.setBuz5("其它手机管家设置步骤有：\n打开手机管家→点击“手机加速”→点击“设置按钮”→手机加速白名单→点击添加白名单→选择密修工程师App勾选并点击确定\n腾讯手机管家设置白名单：打开腾讯手机管家→点击右上角“”个人中心图标→点击   设置按钮→点击清理加速→点击加速保护名单→点击添加按钮→选择密修工程师App勾选并点击确定");
		list.add(b7);
	}

	@Override
	protected void initGetData() {
		type = getIntent().getStringExtra("type");
		if (TextUtils.isEmpty(type)) {
			type = "0";
		} else {
			type = Utils.jian(type, "1");
		}
		WhitelistingBean b1 = list.get(Integer.parseInt(type));
		if (!TextUtils.isEmpty(b1.getBuz1())) {
			tv_ms1.setText(b1.getBuz1());
		} else {
			tv_ms1.setVisibility(View.GONE);
		}
		if (!TextUtils.isEmpty(b1.getBuz2())) {
			tv_ms2.setText(b1.getBuz2());
		} else {
			tv_ms2.setVisibility(View.GONE);
		}
		if (!TextUtils.isEmpty(b1.getBuz3())) {
			tv_ms3.setText(b1.getBuz3());
		} else {
			tv_ms3.setVisibility(View.GONE);
		}
		if (!TextUtils.isEmpty(b1.getBuz4())) {
			tv_ms4.setText(b1.getBuz4());
		} else {
			tv_ms4.setVisibility(View.GONE);
		}
		if (!TextUtils.isEmpty(b1.getBuz5())) {
			tv_ms5.setText(b1.getBuz5());
		} else {
			tv_ms5.setVisibility(View.GONE);
		}
		if (b1.getImg1() != 0) {
			miv1.setImageResource(b1.getImg1());
		} else {
			miv1.setVisibility(View.GONE);
		}
		if (b1.getImg2() != 0) {
			miv2.setImageResource(b1.getImg2());
		} else {
			miv2.setVisibility(View.GONE);
		}
		if (b1.getImg3() != 0) {
			miv3.setImageResource(b1.getImg3());
		} else {
			miv3.setVisibility(View.GONE);
		}
		if (b1.getImg4() != 0) {
			miv4.setImageResource(b1.getImg4());
		} else {
			miv4.setVisibility(View.GONE);
		}
		if (b1.getImg5() != 0) {
			miv5.setImageResource(b1.getImg5());
		} else {
			miv5.setVisibility(View.GONE);
		}
	}

	@Override
	protected void widgetListener() {
	}

	@Override
	protected void init() {
	}

}

package com.lwc.shanxiu.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.module.user.UserInfoActivity;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.widget.CircleImageView;
import com.lwc.shanxiu.widget.StampView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * *岗位证
 */
public class WorkPositionActivity extends BaseActivity {

	private User user;
	private SharedPreferencesUtils preferencesUtils;
	private String name="";

	@BindView(R.id.tv_stamp)
	StampView tv_stamp;
	@BindView(R.id.imgHeadIcon)
	CircleImageView imgHeadIcon;
	@BindView(R.id.tv_unit)
	TextView tv_unit;
	@BindView(R.id.tv_name)
	TextView tv_name;
	@BindView(R.id.tv_sex)
	TextView tv_sex;
	@BindView(R.id.tv_num)
	TextView tv_num;
	@BindView(R.id.tv_office)
	TextView tv_office;

	@Override
	protected int getContentViewId(Bundle savedInstanceState) {
		return R.layout.activity_work_position;
	}

	@Override
	protected void findViews() {
		ButterKnife.bind(this);
		setTitle("岗位证");
		showBack();
	}

	@Override
	protected void init() {
	}

	@Override
	protected void initGetData() {
		preferencesUtils = SharedPreferencesUtils.getInstance(this);
		user = preferencesUtils.loadObjectData(User.class);

		if(user != null){
			if (!TextUtils.isEmpty(user.getMaintenanceHeadImage())) {
				ImageLoaderUtil.getInstance().displayFromNet(WorkPositionActivity.this, user.getMaintenanceHeadImage(), imgHeadIcon, R.drawable.default_portrait_100);
			}
			tv_unit.setText(user.getParentCompanyName());

			if (user.getMaintenanceName() != null && !TextUtils.isEmpty(user.getMaintenanceName())) {
				tv_name.setText(user.getMaintenanceName());
			} else {
				tv_name.setText("保密");
			}

			if (1 == user.getMaintenanceSex()) {
				tv_sex.setText("男");
			} else if (0 == user.getMaintenanceSex()) {
				tv_sex.setText("女");
			} else {
				tv_sex.setText("保密");
			}

			tv_num.setText(user.getUserId());

			tv_stamp.setText(user.getCompanyCityName()+"市国家保密局");
			tv_office.setText("发证机关："+user.getCompanyCityName()+"市国家保密局");
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		if(tv_stamp != null){
			tv_stamp.invalidate();
		}
	}


	@Override
	protected void widgetListener() {
	}

}

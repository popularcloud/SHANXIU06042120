package com.lwc.shanxiu.module.user;

import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.adapter.MySkillsAdapter;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.bean.MySkillBean;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 工程师擅长技能列表
 * 
 * @Description TODO
 * @author 何栋
 * @date 2016年4月24日
 * @Copyright: lwc
 */
public class MySkillsActivity extends BaseActivity {

	private ListView lv_skills;
	private MySkillsAdapter adapter;
	private final List<MySkillBean> skills = new ArrayList<MySkillBean>();
	private SharedPreferencesUtils preferencesUtils = null;
	private User user = null;

	@Override
	protected int getContentViewId(Bundle savedInstanceState) {
		return R.layout.activity_my_skills;
	}

	@Override
	protected void findViews() {
		showBack();
		setTitle("我的技能");
		lv_skills = (ListView) findViewById(R.id.lv_skills);
		adapter = new MySkillsAdapter(MySkillsActivity.this, skills, R.layout.item_my_skill);
		lv_skills.setAdapter(adapter);
	}

	@Override
	protected void init() {
		preferencesUtils = SharedPreferencesUtils.getInstance(MySkillsActivity.this);
		user = preferencesUtils.loadObjectData(User.class);
		if (user == null){
			IntentUtil.gotoActivity(MySkillsActivity.this, LoginOrRegistActivity.class);
			return;
		}
		getMySkills();
	}

	/**
	 * 获取我的技能
	 * 
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 */
	private void getMySkills() {
		HttpRequestUtils.httpRequest(this, "getMySkills", RequestValue.METHOD_GET_MY_SKILLS, null, "GET", new HttpRequestUtils.ResponseListener() {
			@Override
			public void getResponseData(String response) {
				Common common = JsonUtil.parserGsonToObject(response, Common.class);

				switch (common.getStatus()) {
					case "1":
						ArrayList<MySkillBean> current = JsonUtil.parserGsonToArray(JsonUtil.getGsonValueByKey(response, "data"), new TypeToken<ArrayList<MySkillBean>>() {
						});
						skills.addAll(current);
						adapter.notifyDataSetChanged();
						break;
					default:
						ToastUtil.showToast(MySkillsActivity.this, common.getInfo());
						break;
				}
			}

			@Override
			public void returnException(Exception e, String msg) {
				ToastUtil.showToast(MySkillsActivity.this, msg);
			}
		});
	}

	@Override
	protected void initGetData() {
	}

	@Override
	protected void widgetListener() {
	}

}

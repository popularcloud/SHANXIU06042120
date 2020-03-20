package com.lwc.shanxiu.module.setting;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.utils.DisplayUtil;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.JsonUtil;
import com.lwc.shanxiu.utils.SystemUtil;
import com.lwc.shanxiu.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 意见反馈页面
 * 
 * @Description TODO
 * @author 何栋
 * @date 2015年11月9日
 * @Copyright: lwc
 */
public class SuggestActivity extends BaseActivity {

	/** 评价内容 */
	private EditText et_comment_content;
	/** 提交评价 */
	private Button btn_comment_submit;

	private TextView tv_word_number;

	@Override
	protected int getContentViewId(Bundle savedInstanceState) {
		return R.layout.activity_suggest;
	}

	@Override
	protected void findViews() {
		setTitle("意见反馈");
		showBack();
		et_comment_content = (EditText) findViewById(R.id.et_comment_content);
		btn_comment_submit = (Button) findViewById(R.id.btn_comment_submit);
		tv_word_number = (TextView) findViewById(R.id.tv_word_number);

		et_comment_content.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				tv_word_number.setText(String.valueOf(s.length())+"/200");
			}
		});
	}

	@Override
	protected void initGetData() {
	}

	@Override
	protected void widgetListener() {
		btn_comment_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkData()) {// 上传评价
					setSuggest();
				}

			}
		});
	}

	@Override
	protected void init() {
	}

	/**
	 * 进行评价
	 * 
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 */
	private void setSuggest() {
		if (Utils.isFastClick(1000)) {
			return;
		}
		Map<String, String> map = new HashMap<>();
		map.put("content", et_comment_content.getText().toString().trim());
		map.put("device", "1");
		map.put("version", SystemUtil.getCurrentVersionName());
		HttpRequestUtils.httpRequest(this, "setSuggest", RequestValue.METHOD_SUGGEST, map, "POST", new HttpRequestUtils.ResponseListener() {
			@Override
			public void getResponseData(String response) {
				Common common = JsonUtil.parserGsonToObject(response, Common.class);

				switch (common.getStatus()) {
					case "1":
						DisplayUtil.showInput(false, SuggestActivity.this);
						finish();
						ToastUtil.showToast(SuggestActivity.this, "提交成功！");
						break;
					default:
						ToastUtil.showToast(SuggestActivity.this, common.getInfo());
						break;
				}

			}

			@Override
			public void returnException(Exception e, String msg) {
				ToastUtil.showToast(SuggestActivity.this, msg);
			}
		});
	}

	/**
	 * 检查数据
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年8月20日,下午5:31:28
	 * @updateTime 2015年8月20日,下午5:31:28
	 * @createAuthor chencong
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @return
	 */
	protected boolean checkData() {
		if (TextUtils.isEmpty(et_comment_content.getText().toString().trim())) {
			ToastUtil.showToast(SuggestActivity.this, "请输入意见内容!");
			return false;
		}
		return true;
	}

}

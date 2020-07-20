package com.lwc.shanxiu.module.authentication.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.authentication.adapter.AnswerTopicAdapter;
import com.lwc.shanxiu.module.authentication.bean.SubmitTopicBean;

import org.byteam.superadapter.OnItemClickListener;

import java.util.List;

/**
 * 自定义对话框
 * 
 */
public class AnswerTopicrDialog extends Dialog implements View.OnClickListener{

	/** 上下文 */
	private Context context;
	CallBack callBack;

	ImageView ic_close;
	TextView tv_submit;
	RecyclerView rv_myData;
	private AnswerTopicAdapter answerTopicAdapter;

	private List<SubmitTopicBean> submitTopicBeans;

	public AnswerTopicrDialog(Context context, List<String> reasons, List<SubmitTopicBean> submitTopicBeans, CallBack callBack) {
		super(context, R.style.BottomDialogStyle);
		// 拿到Dialog的Window, 修改Window的属性
		Window window = getWindow();
		window.getDecorView().setPadding(0, 0, 0, 0);
		// 获取Window的LayoutParams
		WindowManager.LayoutParams attributes = window.getAttributes();
		attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
		attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
		// 一定要重新设置, 才能生效
		window.setAttributes(attributes);
		this.callBack = callBack;
		this.submitTopicBeans = submitTopicBeans;
		init(context,reasons);
	}

	/**
	 *
	 * @param context
	 * @param theme
     */
	public AnswerTopicrDialog(Context context, int theme) {
		super(context, theme);
		init(context,null);
	}

	public AnswerTopicrDialog(Context context) {
		super(context, R.style.dialog_style);
		init(context,null);
	}

	/**
	 * 初始话对话框
	 * 
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context
	 * 
	 */
	protected void init(Context context,List<String> reasons) {
		this.context = context;
		this.setCancelable(true);
		this.setCanceledOnTouchOutside(true);
		this.setContentView(R.layout.dialog_answer_topic);

		ic_close = findViewById(R.id.ic_close);

		tv_submit = findViewById(R.id.tv_submit);
		rv_myData = findViewById(R.id.rv_myData);

		ic_close.setOnClickListener(this);
		tv_submit.setOnClickListener(this);

		answerTopicAdapter = new AnswerTopicAdapter(context, reasons,submitTopicBeans, R.layout.item_answer_topic);

		answerTopicAdapter.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(View itemView, int viewType, int position) {
				if(callBack != null){
					callBack.onSubmit(position);
				}
			}
		});
		rv_myData.setLayoutManager(new GridLayoutManager(context,7));
		rv_myData.setAdapter(answerTopicAdapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ic_close:
				dismiss();
				break;
			case R.id.tv_submit:
				if(callBack != null){
					callBack.onSubmit(-1);
				}
				break;
		}
	}

	public interface CallBack {
		void onSubmit(int position);
	}
}

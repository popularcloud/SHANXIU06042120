package com.lwc.shanxiu.module.lease_parts.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.lease_parts.adapter.CancelReasonAdapter;

import org.byteam.superadapter.OnItemClickListener;

import java.util.List;

/**
 * 自定义对话框
 * 
 */
public class CancelOrderDialog extends Dialog implements View.OnClickListener{

	/** 上下文 */
	private Context context;
	CallBack callBack;

	ImageView ic_close;
	TextView tv_no_cancel;
	TextView tv_submit;
	RecyclerView rv_myData;
	private int presentPosition = -1;
	private CancelReasonAdapter cancelReasonAdapter;
	private boolean hasBtn;
	private TextView tv_reason_desc;
	private TextView tv_title;
	private LinearLayout ll_btn;

	private String title;
	private String reasonDesc;

	public CancelOrderDialog(Context context, CallBack callBack, List<String> reasons, String title, String reasonDesc, boolean hasBtn) {
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
		this.hasBtn = hasBtn;
		this.title = title;
		this.reasonDesc = reasonDesc;
		init(context,reasons);
	}

	/**
	 *
	 * @param context
	 * @param theme
     */
	public CancelOrderDialog(Context context, int theme) {
		super(context, theme);
		init(context,null);
	}

	public CancelOrderDialog(Context context) {
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
		this.setContentView(R.layout.dialog_canlce_order);

		ic_close = findViewById(R.id.ic_close);

		tv_no_cancel = findViewById(R.id.tv_no_cancel);
		tv_submit = findViewById(R.id.tv_submit);
		rv_myData = findViewById(R.id.rv_myData);
		tv_reason_desc = findViewById(R.id.tv_reason_desc);
		tv_title = findViewById(R.id.tv_title);
		ll_btn = findViewById(R.id.ll_btn);

		ic_close.setOnClickListener(this);
		tv_submit.setOnClickListener(this);
		tv_no_cancel.setOnClickListener(this);


		tv_title.setText(title);
		tv_reason_desc.setText(reasonDesc);
		if(hasBtn){
			ll_btn.setVisibility(View.VISIBLE);
		}else{
			ll_btn.setVisibility(View.GONE);
		}

		cancelReasonAdapter = new CancelReasonAdapter(context, reasons, R.layout.item_cancel_reason, new OnCommClickCallBack() {
			@Override
			public void onCommClick(Object object) {
				presentPosition = (int) object;
				if(callBack != null && !hasBtn){
					cancelReasonAdapter.setItemChecked(presentPosition);
					new Handler().post(new Runnable() {
						@Override
						public void run() {
							// 刷新操作
							cancelReasonAdapter.notifyDataSetChanged();
						}
					});
					callBack.onSubmit(presentPosition);
				}else{
					cancelReasonAdapter.setItemChecked(presentPosition);
					new Handler().post(new Runnable() {
						@Override
						public void run() {
							// 刷新操作
							cancelReasonAdapter.notifyDataSetChanged();
						}
					});

				}
			}
		});

		cancelReasonAdapter.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(View itemView, int viewType, int position) {
				presentPosition = position;
				if(callBack != null && !hasBtn){
					cancelReasonAdapter.setItemChecked(presentPosition);
					new Handler().post(new Runnable() {
						@Override
						public void run() {
							// 刷新操作
							cancelReasonAdapter.notifyDataSetChanged();
						}
					});
					callBack.onSubmit(presentPosition);
				}else{
					cancelReasonAdapter.setItemChecked(presentPosition);
					new Handler().post(new Runnable() {
						@Override
						public void run() {
							// 刷新操作
							cancelReasonAdapter.notifyDataSetChanged();
						}
					});
				}
			}
		});
		rv_myData.setLayoutManager(new LinearLayoutManager(context));
		rv_myData.setAdapter(cancelReasonAdapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ic_close:
				dismiss();
				break;
			case R.id.tv_no_cancel:
				dismiss();
				break;
			case R.id.tv_submit:
				if(callBack != null && presentPosition != -1){
					callBack.onSubmit(presentPosition);
				}
				break;
		}
	}

	public interface CallBack {
		void onSubmit(int position);
	}
}

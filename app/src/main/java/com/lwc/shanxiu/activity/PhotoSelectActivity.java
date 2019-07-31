package com.lwc.shanxiu.activity;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.adapter.PhotoSelectAdapter;
import com.lwc.shanxiu.adapter.PhotoSelectAdapter.CheckClickListener;
import com.lwc.shanxiu.bean.PhotoAlbumBean;
import com.lwc.shanxiu.configs.ServerConfig;
import com.lwc.shanxiu.utils.HandlerUtil;
import com.lwc.shanxiu.utils.ImageUtil;
import com.lwc.shanxiu.utils.ProgressDialogUtil;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.view.TitleView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;

/**
 * 照片选择界面
 * 
 * @Description TODO
 * @author 何栋
 * @version 1.0
 * @date 2014年1月16日
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 * 
 */
public class PhotoSelectActivity extends BaseActivity {

	/** 列表显示控件 */
	private GridView gridView;
	/** 标题栏 */
	private TitleView view_Title;

	/** 图片选择列表适配器 */
	private PhotoSelectAdapter adapter;
	/** 图库属性 将从图库一级界面序列化传送到当前界面 */
	private PhotoAlbumBean photoAlbumBean;

	/** 发送按钮 */
	private Button btn_Send;
	/** 信息输入框 */
	private EditText edit_Message;

	/** 图片选中事件 */
	private CheckClickListener checkListener;

	/** 最大选择张数 */
	private int maxCounts = 5;

	/** 操作展示类型 : 默认0-迪粉汇 ，1-迪车会 */
	private int type = 0;
	/** 底部文字描述区域视图 */
	private RelativeLayout layout_bottom;

	@Override
	protected int getContentViewId(Bundle savedInstanceState) {
		return R.layout.activity_photoselect;
	}

	@Override
	protected void findViews() {
		view_Title = (TitleView) findViewById(R.id.view_title);
		gridView = (GridView) findViewById(R.id.activity_photoselect_gridview);
		layout_bottom = (RelativeLayout) findViewById(R.id.activity_photoselect_view_message);
		btn_Send = (Button) findViewById(R.id.btn_send);
		edit_Message = (EditText) findViewById(R.id.edit_message);

	}

	@Override
	protected void init() {
		if (photoAlbumBean != null) {
			adapter = new PhotoSelectAdapter(this, maxCounts, photoAlbumBean, gridView, checkListener);
			gridView.setAdapter(adapter);
			view_Title.setTitle(photoAlbumBean.getName());
		}

	}

	@Override
	protected void initGetData() {
		maxCounts = getIntent().getIntExtra(getString(R.string.intent_key_max_counts), PhotoAlbumActivity.MAX_COUNTS);
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			photoAlbumBean = (PhotoAlbumBean) bundle.getSerializable(getString(R.string.intent_key_serializable));
		}
		type = getIntent().getExtras().getInt("type");

	}

	@Override
	protected void widgetListener() {
		// 返回按钮
		view_Title.setActivityFinish(this);

		// 图片选中事件
		checkListener = new CheckClickListener() {

			@Override
			public void onClick(int position) {
				btn_Send.setText(getString(R.string.complete) + "(" + adapter.map_IsCheck.size() + ")");
			}
		};

		// 发送按钮
		btn_Send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				compressImages();
			}
		});

	}

	/**
	 * 压缩图片
	 * 
	 * @version 1.0
	 * @createTime 2014年1月17日,下午10:56:21
	 * @updateTime 2014年1月17日,下午10:56:21
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 */
	private void compressImages() {
		ProgressDialogUtil.showDialog(PhotoSelectActivity.this, getString(R.string.process_handle_wait), new Thread() {
			@Override
			public void run() {
				String images = "";
				for (int i = 0; i < photoAlbumBean.getBitList().size(); i++) {
					if (photoAlbumBean.getBitList().get(i).isCheck()) {
						if (TextUtils.isEmpty(images)) {
							images += photoAlbumBean.getBitList().get(i).getPhotoPath();
						} else {
							images += "," + photoAlbumBean.getBitList().get(i).getPhotoPath();
						}
					}
				}

				String imagePath = new ImageUtil().compressImages(images);
				HandlerUtil.sendMessage(handler, ServerConfig.WHAT_COMPRESS_IMAGE_FINISH, imagePath);
			}
		});
	}

	/**
	 * 异步Handler对象
	 */
	private final Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case ServerConfig.WHAT_COMPRESS_IMAGE_FINISH:
				ProgressDialogUtil.dismissDialog();

				if (TextUtils.isEmpty(msg.obj.toString())) {
					ToastUtil.showToast(PhotoSelectActivity.this, "请选择图片");
					return;
				}

				Intent data = new Intent();
				data.putExtra(getString(R.string.intent_key_image_path), msg.obj.toString());
				data.putExtra(getString(R.string.intent_key_content), edit_Message.getText().toString().trim());
				setResult(RESULT_OK, data);
				finish();
				break;

			default:
				break;
			}

		}

	};

}

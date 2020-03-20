package com.lwc.shanxiu.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.adapter.PhotoAlbumAdapter;
import com.lwc.shanxiu.bean.PhotoAlbumBean;
import com.lwc.shanxiu.bean.PhotoBean;
import com.lwc.shanxiu.configs.ServerConfig;
import com.lwc.shanxiu.executors.DataBaseRespon;
import com.lwc.shanxiu.executors.DataBaseTask;
import com.lwc.shanxiu.executors.RequestExecutor;
import com.lwc.shanxiu.utils.FileUtil;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.ToastUtil;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

/**
 * 相册主界面
 * 
 * @Description TODO
 * @author 何栋
 * @version 1.0
 * @date 2014年1月15日
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 * 
 */
public class PhotoAlbumActivity extends BaseActivity {

	/** 图片列表 */
	private ArrayList<PhotoAlbumBean> list_AlbumBeans;

	/** 相册列表显示控件 */
	private GridView gridView;

	/** 图片列表适配器 */
	private PhotoAlbumAdapter adapter;

	/** 最大选择张数 */
	public static int MAX_COUNTS = 9;
	/** 最大选择张数 */
	private int maxCounts = 9;
	/** 获取相册图片请求码 */
	private final static int WHAT_GET_PHOTOALBUM = 1;

	/** 查询系统图库使用的字段 */
	private static final String[] STORE_IMAGES = {
			MediaStore.Images.Media.DISPLAY_NAME, // 显示的名�?
			MediaStore.Images.Media.LATITUDE, // 维度
			MediaStore.Images.Media.LONGITUDE, // 经度
			MediaStore.Images.Media._ID, // id
			MediaStore.Images.Media.BUCKET_ID, // dir id 目录
			MediaStore.Images.Media.BUCKET_DISPLAY_NAME, // dir name 目录名字
			MediaStore.Images.Media.DATA, // 图片路径
	};

	/** 操作展示类型 : 默认0-迪粉汇 ，1-迪车会 */
	private int type = 0;

	@Override
	protected int getContentViewId(Bundle savedInstanceState) {
		return R.layout.activity_photoalbum;
	}

	@Override
	protected void findViews() {
		gridView = (GridView) findViewById(R.id.activity_photoalbum_gridview);

	}

	@Override
	protected void init() {

		RequestExecutor.addTask(new DataBaseTask() {

			@Override
			public void onExecuteSuccess(DataBaseRespon respon) {
				adapter = new PhotoAlbumAdapter(list_AlbumBeans, gridView.getContext());
				gridView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onExecuteFail(DataBaseRespon respon) {

			}

			@Override
			public DataBaseRespon executeOper() {
				DataBaseRespon dataBaseRespon = new DataBaseRespon();
				list_AlbumBeans = getPhotoAlbumList();
				if (list_AlbumBeans != null) {
					dataBaseRespon.setSuccess(true);
				}
				return dataBaseRespon;
			}
		});
	}

	@Override
	protected void initGetData() {
		maxCounts = getIntent().getIntExtra(getString(R.string.intent_key_max_counts), MAX_COUNTS);
		type = getIntent().getIntExtra("type", 0);

	}

	@Override
	protected void widgetListener() {
		// 列表点击事件
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Bundle bundle = new Bundle();
				bundle.putSerializable(getString(R.string.intent_key_serializable), list_AlbumBeans.get(position));
				bundle.putInt(getString(R.string.intent_key_max_counts), maxCounts);
				bundle.putInt("type", type);
				IntentUtil.gotoActivityForResult(PhotoAlbumActivity.this, PhotoSelectActivity.class, bundle, ServerConfig.REQUEST_CODE_SELECT_IMAGE);
			}
		});
	}

	/**
	 * 获取相册列表
	 * 
	 * @version 1.0
	 * @createTime 2014年1月15日,下午6:39:00
	 * @updateTime 2014年3月5日,上午11:39:00
	 * @createAuthor CodeApe
	 * @updateAuthor 王治粮
	 * @updateInfo (判断cursor对象为空时，弹出提示信息)
	 * @updateTime 2014年3月6日,上午9:39:00
	 * @updateAuthor 王wujianxing
	 * @updateInfo (判断查询的path对应的文件是否存在)
	 * 
	 * @return
	 */
	private ArrayList<PhotoAlbumBean> getPhotoAlbumList() {
		ArrayList<PhotoAlbumBean> list_Beans = new ArrayList<PhotoAlbumBean>();
		Cursor cursor = MediaStore.Images.Media.query(getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, STORE_IMAGES);
		Map<String, PhotoAlbumBean> countMap = new HashMap<String, PhotoAlbumBean>();
		PhotoAlbumBean pa = null;
		if (cursor != null) {
			while (cursor.moveToNext()) {
				String id = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID));
				String dir_id = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID));
				String dir = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
				String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
				if (FileUtil.fileIsExists(path)) {
					if (!countMap.containsKey(dir_id)) {
						pa = new PhotoAlbumBean();
						pa.setName(dir);
						pa.setBitmap(Integer.parseInt(id));
						pa.setCount("1");
						pa.getBitList().add(new PhotoBean(Integer.valueOf(id), path));
						countMap.put(dir_id, pa);
					} else {
						pa = countMap.get(dir_id);
						pa.setCount(String.valueOf(Integer.parseInt(pa.getCount()) + 1));
						pa.getBitList().add(new PhotoBean(Integer.valueOf(id), path));
					}
				}
			}
			cursor.close();
		} else {
			ToastUtil.showToast(this, getString(R.string.warning_not_search_photomessage));
			finish();
		}
		Iterable<String> it = countMap.keySet();
		for (String key : it) {
			list_Beans.add(countMap.get(key));
		}
		return list_Beans;
	}

	/**
	 * 选图片返回应答处理
	 * 
	 * @version 1.0
	 * @createTime 2014年1月19日,下午2:26:32
	 * @updateTime 2014年1月19日,下午2:26:32
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == ServerConfig.REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
			setResult(RESULT_OK, data);
			finish();
		}

	}

}

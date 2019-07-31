package com.lwc.shanxiu.executors;

import org.json.JSONException;

import com.lwc.shanxiu.bean.BaseBean;
import com.lwc.shanxiu.bean.ListBean;
import com.lwc.shanxiu.bean.ResponseBean;
import com.lwc.shanxiu.configs.ServerConfig;
import com.lwc.shanxiu.utils.HandlerUtil;
import com.lwc.shanxiu.utils.LogUtil;
import com.lwc.shanxiu.utils.SpUtil;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

/**
 * 基础事务
 *
 * @Description 基础事务可以实现单独的网络请求，带缓存的请求和带数据库缓存的请求可以继承此事务后作扩展。
 * @author 何栋
 * @version 1.0
 * @date 2014年4月23日
 * @Copyright: Copyright (c) 2014 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 *
 */
public abstract class BackTask implements Runnable {

	/** 请求缓存成功 */
	protected static final int REQUEST_CACHE = 110;
	/** 请求成功 */
	protected static final int REQUEST_SUCCESS = 100;
	/** 请求失败 */
	protected static final int REQUEST_FAIL = 120;

	/** 请求返回数据 */
	protected ResponseBean result;

	/** 等待提示信息 */
	protected String processMsg = "";
	/** 窗口是否可取消 */
	protected boolean cancelable = true;

	/** 当前线程对象 */
	protected Thread currentThread;

	/**
	 * 无参构造函数
	 *
	 * @version 1.0
	 * @createTime 2014年4月23日,上午10:38:11
	 * @updateTime 2014年4月23日,上午10:38:11
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 */
	public BackTask() {
	};

	/**
	 * 事务执行线程
	 *
	 * @version 1.0
	 * @createTime 2014年4月23日,上午10:43:27
	 * @updateTime 2014年4月23日,上午10:43:27
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 */
	@Override
	public void run() {
		currentThread = Thread.currentThread();

		// 读取网络数据
		result = sendRequest();
		if (result.getStatus().equals(ServerConfig.RESPONSE_STATUS_SUCCESS)) {
			HandlerUtil.sendMessage(mHandler, REQUEST_SUCCESS, result);
		} else {
			HandlerUtil.sendMessage(mHandler, REQUEST_FAIL, result);
		}

	}

	/**
	 * 发送请求
	 *
	 * @version 1.0
	 * @createTime 2014年4月19日,下午1:48:56
	 * @updateTime 2014年4月19日,下午1:48:56
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @return 请求返回数据
	 */
	public abstract ResponseBean sendRequest();

	/**
	 * 请求成功回调
	 *
	 * @version 1.0
	 * @createTime 2014年4月19日,下午12:52:26
	 * @updateTime 2014年4月19日,下午12:52:26
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param result
	 *        请求返回数据
	 */
	public abstract void onSuccess(ResponseBean result);

	/**
	 * 请求失败回调
	 *
	 * @version 1.0
	 * @createTime 2014年4月19日,下午12:52:26
	 * @updateTime 2014年4月19日,下午12:52:26
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param result
	 *        请求返回数据
	 */
	public abstract void onFail(ResponseBean result);

	/**
	 * 加载缓存列表
	 *
	 * @version 1.0
	 * @createTime 2014年4月19日,下午4:05:37
	 * @updateTime 2014年4月19日,下午4:05:37
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param method
	 *        方法名
	 * @param cls
	 *        解析成的数据模型
	 * @param fileKey
	 *        SP数据缓存的filekey
	 * @return 成功获取 返回解析后的数据 否则 null
	 */
	public ResponseBean loadListCache(String fileKey, String method, Class<? extends BaseBean> cls) {

		SpUtil spUtil = SpUtil.getSpUtil(fileKey, Context.MODE_PRIVATE);
		String cacheJson = spUtil.getSPValue(method, null);
		LogUtil.out("============>" + cacheJson);
		if (!TextUtils.isEmpty(cacheJson)) {
			try {
				ListBean mListBean = new ListBean(cacheJson, cls);
				if (null != mListBean.getModelList() && mListBean.getModelList().size() > 0) {
					ResponseBean bean = new ResponseBean(ServerConfig.RESPONSE_STATUS_SUCCESS, "", mListBean);
					return bean;
				}
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * 加载对象缓存数据
	 *
	 * @version 1.0
	 * @createTime 2014年4月19日,下午4:07:54
	 * @updateTime 2014年4月19日,下午4:07:54
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param fileKey
	 *        sp数据缓存的filekey
	 * @param method
	 *        方法名
	 * @param cls
	 *        解析后的对象类
	 * @return 解析后的对象 or null
	 */
	public ResponseBean loadObjectCache(String fileKey, String method, Class<? extends BaseBean> cls) {

		SpUtil spUtil = SpUtil.getSpUtil(fileKey, Context.MODE_PRIVATE);
		String cacheJson = spUtil.getSPValue(method, null);
		if (!TextUtils.isEmpty(cacheJson)) {
			try {
				return new ResponseBean(ServerConfig.RESPONSE_STATUS_SUCCESS, "", BaseBean.newInstance(cls, cacheJson));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 从本地加载缓存
	 *
	 * @version 1.0
	 * @createTime 2014年4月22日,下午2:56:32
	 * @updateTime 2014年4月22日,下午2:56:32
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param object
	 *        返回的缓存数据
	 * @return 请求返回数据
	 */
	public ResponseBean loadObjectCache(Object object) {
		if (null != object) {
			return new ResponseBean(ServerConfig.RESPONSE_STATUS_SUCCESS, "", object);
		}
		return null;
	}

	/**
	 * 生成返回数据
	 *
	 * @version 1.0
	 * @createTime 2014年4月23日,上午11:39:40
	 * @updateTime 2014年4月23日,上午11:39:40
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param isSuccess
	 *        是否成功
	 * @param object
	 *        附带内容
	 * @return 数据库操作返回数据对象
	 */
	public DataBaseRespon makeRespon(boolean isSuccess, String info, Object object) {
		DataBaseRespon respon;
		if (isSuccess && null != object) {
			respon = new DataBaseRespon(isSuccess, info, object);
		} else {
			respon = new DataBaseRespon(false, info, object);
		}
		return respon;
	}

	/**
	 * 终止线程
	 *
	 * @version 1.0
	 * @createTime 2014年4月19日,下午2:13:42
	 * @updateTime 2014年4月19日,下午2:13:42
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 */
	public void interrupt() {
		if (null != currentThread) {
			currentThread.interrupt();
		}
	}

	/**
	 * 异步处理Handler对象
	 */
	protected Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case REQUEST_SUCCESS: // 网络请求数据成功
				onSuccess((ResponseBean) msg.obj);
				break;
			case REQUEST_FAIL: // 网络请求数据失败
				onFail((ResponseBean) msg.obj);
				break;
			}
		}

	};

}

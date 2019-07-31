package com.lwc.shanxiu.executors;

import com.lwc.shanxiu.bean.ResponseBean;
import com.lwc.shanxiu.utils.HandlerUtil;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * 异步请求线程对象
 *
 * @Description TODO
 * @author 何栋
 * @version 1.0
 * @date 2014年4月19日
 * @Copyright: Copyright (c) 2014 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 *
 */
public abstract class RequestTask extends BaseTask {

	/** 请求缓存返回数据 */
	private ResponseBean cache;

	/**
	 * 无参构造函数
	 *
	 * @version 1.0
	 * @createTime 2014年4月19日,下午2:34:17
	 * @updateTime 2014年4月19日,下午2:34:17
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 */
	public RequestTask() {
	};

	/**
	 * 有参构造函数
	 *
	 * @version 1.0
	 * @createTime 2014年4月19日,下午2:29:03
	 * @updateTime 2014年4月19日,下午2:29:03
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param context
	 *        上下文
	 * @param processMsg
	 *        处理提示文本信息
	 * @param cnacelable
	 *        窗口是否可取消 true 可取消， false 不可取消
	 */
	public RequestTask(Context context, String processMsg, boolean cancelable) {
		super(context, processMsg, cancelable);
	}

	@Override
	public void run() {

		// 读取缓存数据
		cache = getRequestCache();
		if (null != cache) {
			HandlerUtil.sendMessage(mHandler, REQUEST_CACHE, cache);
		}

		super.run();

	}

	/**
	 * 请求缓存数据
	 *
	 * @version 1.0
	 * @createTime 2014年4月19日,下午3:28:06
	 * @updateTime 2014年4月19日,下午3:28:06
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 */
	public abstract ResponseBean getRequestCache();

	/**
	 * 请求缓存返回应答处理
	 *
	 * @version 1.0
	 * @createTime 2014年4月19日,下午3:27:40
	 * @updateTime 2014年4月19日,下午3:27:40
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param result
	 */
	public abstract void onRequestCache(ResponseBean result);

	/**
	 * 异步处理Handler对象
	 */
	protected Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case REQUEST_CACHE:// 本地读取缓存成功
				onRequestCache((ResponseBean) msg.obj);
				break;
			}
		}

	};

}

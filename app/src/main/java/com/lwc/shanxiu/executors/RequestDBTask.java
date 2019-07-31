package com.lwc.shanxiu.executors;

import com.lwc.shanxiu.utils.HandlerUtil;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * 网络请求带数据库缓存
 *
 * @Description TODO
 * @author 何栋
 * @version 1.0
 * @date 2014年4月23日
 * @Copyright: Copyright (c) 2014 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 *
 */
public abstract class RequestDBTask extends BaseTask {

	/** 数据库操作返回应答 */
	protected static final int REQUEST_DATABASE_RESULT = 5;

	/**
	 * 无参构造函数
	 *
	 * @version 1.0
	 * @createTime 2014年4月23日,上午11:30:51
	 * @updateTime 2014年4月23日,上午11:30:51
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 */
	public RequestDBTask() {
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
	public RequestDBTask(Context context, String processMsg, boolean cancelable) {
		super(context, processMsg, cancelable);
	}

	@Override
	public void run() {

		execDataBaseExecutor();
		super.run();
	}

	/**
	 * 在数据库线程池里边执行数据库操作
	 *
	 * @version 1.0
	 * @createTime 2014年4月23日,上午10:26:44
	 * @updateTime 2014年4月23日,上午10:26:44
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 */
	private void execDataBaseExecutor() {
		DataBaseExecutor.addTask(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				DataBaseRespon respon = getDataBaseCache();
				if (null != respon && respon.isSuccess()) {
					HandlerUtil.sendMessage(mHandler, REQUEST_DATABASE_RESULT, respon);
				}
			}
		});
	}

	/**
	 * 从数据库
	 *
	 * @version 1.0
	 * @createTime 2014年4月23日,上午10:27:22
	 * @updateTime 2014年4月23日,上午10:27:22
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @return 数据库操作返回数据
	 */
	public abstract DataBaseRespon getDataBaseCache();

	/**
	 * 数据库读取数据完毕返回应答处理
	 *
	 * @version 1.0
	 * @createTime 2014年4月23日,上午11:11:01
	 * @updateTime 2014年4月23日,上午11:11:01
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param respon
	 *        数据库操作返回数据
	 */
	public abstract void onDataBaseCache(DataBaseRespon respon);

	/**
	 * 异步处理Handler对象
	 */
	protected Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case REQUEST_DATABASE_RESULT:// 读取数据库缓存成功
				onDataBaseCache((DataBaseRespon) msg.obj);
				break;
			}
		}

	};

}

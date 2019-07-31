package com.lwc.shanxiu.executors;

import com.lwc.shanxiu.bean.ResponseBean;
import com.lwc.shanxiu.utils.HandlerUtil;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * 纯数据库操作事务
 *
 * @Description TODO
 * @author 何栋
 * @version 1.0
 * @date 2014年4月23日
 * @Copyright: Copyright (c) 2014 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 *
 */
public abstract class DataBaseTask extends BaseTask {

	/** 数据库操作返回数据 */
	protected DataBaseRespon respon;

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
	public DataBaseTask() {
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
	public DataBaseTask(Context context, String processMsg, boolean cancelable) {
		super(context, processMsg, cancelable);
	}

	@Override
	public void run() {

		// 执行数据库操作
		respon = executeOper();
		if (respon.isSuccess()) {
			HandlerUtil.sendMessage(mHandler, REQUEST_SUCCESS, respon);
		} else {
			HandlerUtil.sendMessage(mHandler, REQUEST_FAIL, respon);
		}

	}

	/**
	 * 执行数据库操作
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
	public abstract DataBaseRespon executeOper();

	/**
	 * 数据库操作成功回调
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
	public abstract void onExecuteSuccess(DataBaseRespon respon);

	/**
	 *
	 *
	 * @version 1.0
	 * @createTime 2014年4月23日,上午11:21:31
	 * @updateTime 2014年4月23日,上午11:21:31
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param respon
	 */
	public abstract void onExecuteFail(DataBaseRespon respon);

	/**
	 * 异步处理Handler对象
	 */
	protected Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case REQUEST_SUCCESS:// 数据库操作成功
				ProcessDialogUtil.dismissDialog();
				onExecuteSuccess((DataBaseRespon) msg.obj);
				break;
			case REQUEST_FAIL:// 数据库操作失败
				ProcessDialogUtil.dismissDialog();
				onExecuteFail((DataBaseRespon) msg.obj);
				break;
			}
		}

	};

	@Override
	public ResponseBean sendRequest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onSuccess(ResponseBean result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFail(ResponseBean result) {
		// TODO Auto-generated method stub

	}

}
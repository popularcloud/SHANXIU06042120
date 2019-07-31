package com.lwc.shanxiu.executors;

/**
 * 聊天事务
 *
 * @Description
 * @author 何栋
 * @version 1.0
 * @date 2014年4月23日
 * @Copyright: Copyright (c) 2014 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 *
 */
public abstract class ChatTask implements Runnable {

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
	public ChatTask() {
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
	}

	/**
	 * 终止线程
	 *
	 * @version 1.0
	 * @createTime 2014年6月7日,下午2:20:47
	 * @updateTime 2014年6月7日,下午2:20:47
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

}

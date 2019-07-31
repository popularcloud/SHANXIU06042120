package com.lwc.shanxiu.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 数据库操作队列
 *
 * @Description TODO
 * @author 何栋
 * @version 1.0
 * @date 2013-10-25
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 *
 */
public class DataBaseExecutor {

	/** 数据库操作线程池队列，同时只允许一个线程操作数据库 */
	private static ExecutorService executorService = Executors.newFixedThreadPool(1);

	/**
	 * 往线程池添加线程
	 *
	 * @version 1.0
	 * @createTime 2013-10-25,下午4:51:24
	 * @updateTime 2013-10-25,下午4:51:24
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param task
	 */
	public static void addTask(Runnable task) {
		executorService.submit(task);
	}

	/**
	 * 往线程池添加事务
	 *
	 * @version 1.0
	 * @createTime 2014年4月23日,上午11:28:29
	 * @updateTime 2014年4月23日,上午11:28:29
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param task
	 *        数据库操作事务对象
	 */
	public static void addTask(DataBaseTask task) {
		executorService.submit(task);
	}

	/**
	 * 关闭线程池
	 *
	 * @version 1.0
	 * @createTime 2013-10-25,下午4:58:51
	 * @updateTime 2013-10-25,下午4:58:51
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 */
	public static void shutdown() {
		executorService.shutdown();
	}

}

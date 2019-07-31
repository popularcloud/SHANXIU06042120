package com.lwc.shanxiu.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 请求工具类
 *
 * @author 何栋
 * @version 1.0
 * @Description TODO
 * @date 2014年5月19日
 * @Copyright: Copyright (c) 2014 Shenzhen Utoow Technology Co., Ltd. All rights reserved.
 */
public class RequestOperaExecutor {

	/**
	 * 请求线程池队列，同时允许5个线程操作
	 */
	private static ExecutorService executorService = Executors.newFixedThreadPool(5);

	/**
	 * 往线程池添加线程
	 *
	 * @param task
	 * @version 1.0
	 * @createTime 2013-10-25,下午4:51:24
	 * @updateTime 2013-10-25,下午4:51:24
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容, 若无修改可不写.)
	 */
	public static void addTask(RequestTask task) {
		executorService.submit(task);
	}

	/**
	 * 往线程池添加网络请求事务，带数据库缓存
	 *
	 * @param task
	 *        带数据库缓存的事务操作
	 * @version 1.0
	 * @createTime 2014年4月23日, 上午11:56:50
	 * @updateTime 2014年4月23日, 上午11:56:50
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容, 若无修改可不写.)
	 */
	public static void addTask(RequestDBTask task) {
		executorService.submit(task);
	}

	/**
	 * 往线程池添加网络请求事务
	 *
	 * @param task
	 *        纯请求事务操作
	 * @version 1.0
	 * @createTime 2014年4月23日, 上午11:56:50
	 * @updateTime 2014年4月23日, 上午11:56:50
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容, 若无修改可不写.)
	 */
	public static void addTask(BaseTask task) {
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
	 * @updateInfo (此处输入修改内容, 若无修改可不写.)
	 */
	public static void shutdown() {
		executorService.shutdown();
	}

}
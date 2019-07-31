package com.lwc.shanxiu.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 数据库操作队列
 *
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 *
 */
public class DataBaseOperQueue {

	/**  数据库操作线程池队列，同时只允许一个线程操作数据库*/
	private static ExecutorService executorService = Executors.newFixedThreadPool(1);
	
	/**
	 * 往线程池添加线程
	 *
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param task
	 */
	public static void addTask(Runnable task){
		executorService.submit(task);
	}
	
	/**
	 * 关闭线程池
	 *
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 */
	public static void shutdown(){
		executorService.shutdown();
	}
	
		
}

package com.lwc.shanxiu.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 *
 * Created by 何栋 on 2017/10/15.
 * 294663966@qq.com
 */
public class ExecutorServiceUtil {

    private static ExecutorService fixedThreadPool = null;

    public static ExecutorService getInstance() {
        if (fixedThreadPool == null) {
            synchronized (ExecutorService.class) {
                if (fixedThreadPool == null)
                    fixedThreadPool = Executors.newFixedThreadPool(5);
            }
        }
        return fixedThreadPool;
    }
}

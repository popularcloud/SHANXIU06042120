package com.lwc.shanxiu.utils;

import android.util.Log;

/**
 * Created by yang on 2016/10/27.
 * 294663966@qq.com
 * 何栋
 */

public class LLog {

    private static boolean isShowLog = true;

    public static void i(String info) {
        if (isShowLog) {
            Log.i("tag", info);
        }
    }

    public static void i(String tag, String info) {
        if (isShowLog) {
            Log.i(tag, info);
        }
    }

    /**
     * 带名字的tag 标签
     *
     * @param name
     * @param info
     */
    public static void iName(String name, String info) {
        if (isShowLog) {
            Log.i("tag", name + ":     " + info);
        }
    }

    /**
     * 带名字的tag 标签
     *
     * @param name
     * @param info
     */
    public static void iName(String tag, String name, String info) {
        if (isShowLog) {
            Log.i(tag, name + ":     " + info);
        }
    }

    public static void e(String info) {
        if (isShowLog) {
            Log.i("tag", info);
        }
    }

    public static void eNetError(String tag, String info) {
        if (isShowLog) {
            Log.e(tag, "联网失败" + info);
        }
    }

    public static void eNetError(String info) {
        if (isShowLog) {
            Log.e("tag", "联网失败" + info);
        }
    }

    public static void iNetSucceed(String tag, String info) {
        if (isShowLog) {
            Log.i(tag, "联网成功" + info);
        }
    }

    public static void iNetSucceed(String info) {
        if (isShowLog) {
            Log.i("tag", "联网成功" + info);
        }
    }

    public static void e(String tag, String info) {
        if (isShowLog) {
            Log.i(tag, info);
        }
    }

}

package com.lwc.shanxiu.module;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;


import com.lwc.shanxiu.utils.DisplayUtils;

import java.util.Stack;

/**
 * Application类,用于初始化各类信息
 *
 * @author 何栋
 */
public class GlobalApplication extends Application {

    private static Stack<Activity> activityStack;
    private static final GlobalApplication application = new GlobalApplication();
    public static Context CONTEXT;


    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = this;


        initEngine();

    }

    /**
     * /**
     * 获取App安装包信息
     *
     * @return 安装包信息
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }

    /**
     * 初始化引擎
     */
    private void initEngine() {

    }

    /**
     * 获取屏幕的长宽分辨率
     *
     * @return
     */
    public int[] getScreenWidthAndHeight() {
        return DisplayUtils.getScreenWidthAndHight(getApplicationContext());
    }

    /**
     * 单例,获取application
     *
     * @return
     */
    public static GlobalApplication getInstance() {
        return application;
    }

    /**
     * 添加Activity到堆栈
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity (堆栈中最后一个放入的)
     *
     * @return
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);

    }

    /**
     * 结束指定的Activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     *
     * @param cls
     */
    public void finishActivity(Class<?> cls) {

        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0; i < activityStack.size(); i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void exitApp() {
        try {
            //保存下载数据
            // DownloadUtil.getInstance().saveData(CONTEXT);
            // Log.e("111", "进入GlobalApplication====exitApp保存下载列表");
//            finishAllActivity();
            System.exit(0);
        } catch (Exception e) {
        }
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}

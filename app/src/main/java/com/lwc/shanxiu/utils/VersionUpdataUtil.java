package com.lwc.shanxiu.utils;

import android.app.Activity;
import android.content.Context;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;

/**
 * Created by 何栋 on 2017/10/15.
 * 294663966@qq.com
 * 版本升级（更新）工具类
 */
public class VersionUpdataUtil {

    private final String TAG = "VersionUpdataUtil";

    private Context context;
    /**
     * 版本信息
     */
    private int version = 0;
    /**
     * apk路径
     */
    private String akpPath = "";
    /**
     * 保存apk的文件
     */
    private File apkFile;

    public VersionUpdataUtil(Activity context) {
        this.context = context;
    }


    /**
     * 获取当前版本
     */
    public static int getCurrentVersion(Context context) {
        return SystemUtil.getCurrentVersionCode();
    }


    /**
     * 判断是否需要更新
     *
     * @param version
     * @return
     */
    public static boolean isNeedUpdate(Context context, int version) {

        int currentVersion = getCurrentVersion(context);
        if (version > currentVersion) {
            return true;
        }
        return false;
    }


    /**
     * 下载apk
     *
     * @param path apk路径
     */
    private void downloadAPK(String path) {

        OkHttpUtils
                .get()
                .url(path)
                .build()
                .execute(new FileCallBack(FileUtil.getAppCachePath(context), "common.apk") {

                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 LLog.eNetError(TAG, e.toString());
                                 ToastUtil.showLongToast(context, "更新失败，请检查网络是否连接正常");
                             }

                             @Override
                             public void onResponse(File response, int id) {

                                 LLog.iNetSucceed(TAG, response.getPath().toString());
                                 apkFile = response;
                                 installAPK(apkFile,null);
                             }
                         }
                );

    }

    /**
     * 安装apk
     *
     * @param file
     */
    private void installAPK(File file,Context context) {
        ApkUtil.installApk(file,context);
    }

}

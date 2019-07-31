package com.lwc.shanxiu.view;

import android.content.Context;

import com.lwc.shanxiu.R;


/**
 * 创建于 2017/5/9.
 * 作者： 何栋
 * 邮箱： 294663966@qq.com
 * 进度条工具类
 */
public class ProgressUtils {

    private DialogUtils dialogUtils;

    /**
     * 显示自定义进度条
     */
    public void showCustomProgressDialog(Context context) {
        if (dialogUtils == null) {
            dialogUtils = new DialogUtils(context);
            dialogUtils.showCustomProgressDialog(R.layout.loading_dialog, R.style.loading_dialog);
        }
    }

    /**
     * 显示默认的进度条
     *
     * @param message 要显示的文字
     */
    public void showDefaultProgressDialog(Context context, String message) {
        if (dialogUtils == null) {
            dialogUtils = new DialogUtils(context);
            dialogUtils.showDefaultProgressDialog(message);
        }
    }

    /**
     * 取消自定义进度条
     */
    public void dismissCustomProgressDialog() {
        if (dialogUtils != null) {
            dialogUtils.dismissCustomProgressDialog();
            dialogUtils = null;
        }
    }


    /**
     * 取消默认进度条
     */
    public void dissmissDefaultProgressDialog() {

        if (dialogUtils != null) {
            dialogUtils.dissmissDefaultProgressDialog();
            dialogUtils = null;
        }
    }
}

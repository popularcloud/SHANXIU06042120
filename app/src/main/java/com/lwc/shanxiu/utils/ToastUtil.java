package com.lwc.shanxiu.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.provider.MediaStore;
import android.widget.Toast;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.controler.global.GlobalValue;
import com.lwc.shanxiu.view.SelectPhotoDialog;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

/**
 * Toast操作工具类
 *
 * @author 何栋
 * @version 1.0.0
 * @date 2013-03-19
 */
public class ToastUtil {

    private static Toast toast;

    /**
     * 显示选择图片
     * @param context
     */
    public static void showPhotoSelect(final Activity context) {
        SelectPhotoDialog picturePopupWindow = new SelectPhotoDialog(context, new SelectPhotoDialog.CallBack() {
            @Override
            public void oneClick() {
                Intent openCameraIntent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                context.startActivityForResult(openCameraIntent, GlobalValue.CAMERA_REQUESTCODE);
            }

            @Override
            public void twoClick() {
                SystemInvokeUtils.invokeMapDepot(context, GlobalValue.PHOTO_REQUESTCODE);
            }

            @Override
            public void cancelCallback() {
            }
        }, "拍照", "手机相册");// 拍照弹出框
        picturePopupWindow.show();
    }

    /**
     * 显示选择图片
     * @param context
     */
    public static void showPhotoSelect(final Activity context, final int count) {
        SelectPhotoDialog picturePopupWindow = new SelectPhotoDialog(context, new SelectPhotoDialog.CallBack() {
            @Override
            public void oneClick() {
                Intent openCameraIntent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                context.startActivityForResult(openCameraIntent, GlobalValue.CAMERA_REQUESTCODE);
            }

            @Override
            public void twoClick() {
                Matisse
                        .from(context)
                        .choose(MimeType.ofImage())//照片视频全部显示
                        .countable(true)//有序选择图片
                        .maxSelectable(count)//最大选择数量为9
                        .gridExpectedSize(180)//图片显示表格的大小getResources()
//						.getDimensionPixelSize(R.dimen.grid_expected_size)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)//图像选择和预览活动所需的方向。
                        .thumbnailScale(0.95f)//缩放比例
                        .theme(R.style.Matisse_Zhihu)//主题  暗色主题 R.style.Matisse_Dracula
                        .imageEngine(new GlideEngine())//加载方式
                        .forResult(GlobalValue.PHOTO_REQUESTCODE);//请求码
            }

            @Override
            public void cancelCallback() {
            }
        }, "拍照", "手机相册");// 拍照弹出框
        picturePopupWindow.show();
    }

    /**
     * 显示提示信息
     *
     * @param text 提示内容
     * @author 罗文忠
     * @version 1.0
     * @date 2013-03-19
     */
    public static void showToast(Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();

    }

    /**
     * 显示提示信息(时间较长)
     *
     * @param text 提示内容
     * @author 罗文忠
     * @version 1.0
     * @date 2013-04-07
     */
    public static void showLongToast(Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        } else {
            toast.setText(text);
        }
        // toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, DisplayUtil.dip2px(Application.context, 150));
        toast.show();
    }

}

package com.lwc.shanxiu.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * Created by yang on 2016/11/22.
 * 374353845@qq.com
 * 调用系统工具类， 如拍照功能，获取图库功能
 */

public class SystemInvokeUtils {

    /**
     * 调用系统图库
     */
    public static void invokeMapDepot(Activity activity, int flag) {

       /* Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        activity.startActivityForResult(intent, flag);*/


        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.putExtra("crop", true);
        intent.putExtra("scale", true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//如果大于等于7.0使用FileProvider
            Uri uriForFile = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".fileProvider", new File(ToastUtil.path,ToastUtil.date));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
        }
        activity.startActivityForResult(intent, flag);

    }

    /**
     * 通过Uri传递图像信息以供裁剪
     * @param uri
     */
    public static void startImageZoom(Activity activity, Uri uri, Uri uritempFile, int flag){
      /*  Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");


        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        intent.putExtra("output", uritempFile);// 输出到文件
      //  intent.putExtra("outputFormat", "PNG");// 返回格式
        intent.putExtra("noFaceDetection", true); // 去除面部检测
        intent.putExtra("return-data", false); // 不要通过Intent传递截获的图片

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);

        *//**
         * 此方法返回的图片只能是小图片（sumsang测试为高宽160px的图片）
         * 故将图片保存在Uri中，调用时将Uri转换为Bitmap，此方法还可解决miui系统不能return data的问题
         *//*
        //intent.putExtra("return-data", true);

        //uritempFile为Uri类变量，实例化uritempFile
//        uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        activity.startActivityForResult(intent, flag);*/

        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File file = new File(ToastUtil.path,ToastUtil.date);
          //  Uri outPutUri = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName()+".fileProvider", file);
            intent.setDataAndType(uri, "image/*");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
            intent.putExtra("noFaceDetection", false);//去除默认的人脸识别，否则和剪裁匡重叠
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            intent.setDataAndType(uri, "image/*");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        }
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        activity.startActivityForResult(intent, flag);
    }

    /**
     * 调用系统拍照功能
     *
     * @param activity
     * @param savePath 保存的文件路径， 建议用FileUtil工具操作
     * @param flag
     */
    public static void invokeTakePhotos(Activity activity, File savePath, int flag) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 调用android自带的照相机
        Uri imageUri = Uri.fromFile(savePath);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        activity.startActivityForResult(intent, flag);
    }

    /**
     * 获取SHA1值
     *
     * @param context
     * @return
     */
    public static String getSHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}

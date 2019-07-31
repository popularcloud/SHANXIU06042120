package com.lwc.shanxiu.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author 何栋
 * @version 1.0
 * @date 2017/4/20 15:50
 * @email 294663966@qq.com
 * 关于图片的所有操作
 */
public class PictureUtils {

    /**
     * 专为OnActivityResult回调获取 data图片路径的方法，做了手机适配
     *
     * @param activity
     * @param data
     * @return
     */
    public static String getPath(Activity activity, Intent data) {

        String uriPath = data.getData().toString();
        String fileStr = null;
        String subPath = uriPath.substring(0, 7);
        LLog.i(subPath);
        if ("file://".equals(subPath)) {
            fileStr = uriPath.substring(8, uriPath.length());
            return fileStr;
        } else {
            fileStr = getPath(activity, data.getData());
            return fileStr;
        }
    }

    /**
     * 将Bitmap转换成文件
     * 保存文件
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static File saveFile(Bitmap bm,String path, String fileName){
        File dirFile = new File(path);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path , fileName);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myCaptureFile;
    }

    /**
     * 专为OnActivityResult回调获取 data图片路径的方法，做了手机适配
     *
     * @return
     */
    public static Bitmap getBitmap(Activity activity, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {

            if (data.getData() == null) {
                Toast.makeText(activity, "图片无效，请重试", Toast.LENGTH_LONG).show();
                return null;
            }

            String uriPath = data.getData().toString();
            String subPath = uriPath.substring(0, 7);
            Bitmap bitmap = null;
            LLog.i(subPath);
            if ("file://".equals(subPath)) {
                String imgPath = uriPath.substring(8, uriPath.length());
                bitmap = getBitmap(imgPath);
                return bitmap;
            } else {
                String fileStr = getPath(activity, data.getData());
                bitmap = getBitmap(fileStr);
                return bitmap;
            }
        } else return null;
    }

    /**
     * 获取图片的路径
     *
     * @param act
     * @param uri 图片的URI
     * @return
     */
    public static String getPath(Context act, Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = act.getContentResolver().query(uri, projection, null, null, null);
        int nIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(nIndex);
    }

    /**
     * 专为OnActivityResult回调获取， 根据图片路径获取图片bitmap
     *
     * @param imgPath 图片的路径
     * @return
     */
    public static Bitmap getBitmap(String imgPath) {

        int degree = readPictureDegree(imgPath);
        Bitmap cameraBitmap = BitmapFactory.decodeFile(imgPath, null);
        cameraBitmap = rotaingImageView(degree, cameraBitmap);

        return cameraBitmap;
    }


    /**
     * 获取图片，返回一个file文件
     *
     * @param activity
     * @param data
     * @return
     */
    public static File getFile(Activity activity, Intent data) {

        String path = getPath(activity, data);
        return FileUtil.createFile(path);
    }


    /**
     * 旋转图片
     *
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();

        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }
}

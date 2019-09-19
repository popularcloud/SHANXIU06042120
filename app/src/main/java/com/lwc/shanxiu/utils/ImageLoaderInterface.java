package com.lwc.shanxiu.utils;

import android.content.Context;
import android.widget.ImageView;

import java.io.File;

/**
 * 图片加载引擎对外暴露的方法
 *
 * @author 何栋
 */
public interface ImageLoaderInterface {

    /**
     * 加载本地资源图片
     * @param imageView  控件id
     * @param resourceId R.drawable.**
     */
    void displayFromLocal(Context context, ImageView imageView, int resourceId);

    /**
     * 加载本地资源图片
     * @param imageView 控件id
     * @param path      图片路径
     */
    void displayFromLocal(Context context, ImageView imageView, String path);

    /**
     * 加载File文件
     * @param context
     * @param imageView
     * @param file
     */
    void displayFromFile(Context context, ImageView imageView, File file);

    /**
     * 加载网络图片
     * @param url       图片地址
     * @param imageView 控件id
     */
    void displayFromNet(Context context, String url, ImageView imageView);

    /**
     * 加载网络图片
     * @param path       图片地址
     * @param imageView 控件id
     */
    void displayFromLocal(Context context, ImageView imageView, String path,int width,int height);

    /**
     * 加载网络图片
     * @param url       图片地址
     * @param imageView 控件id
     */
    void displayFromNet6(Context context, String url, ImageView imageView);
}

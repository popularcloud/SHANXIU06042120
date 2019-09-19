package com.lwc.shanxiu.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.lwc.shanxiu.R;

import java.io.File;

/**
 * 图片加载的引擎
 *
 * @author 何栋
 */
public class ImageLoaderUtil implements ImageLoaderInterface {

    private static ImageLoaderUtil loader = null;

    public static ImageLoaderUtil getInstance() {
        if (loader == null) {
            synchronized (ImageLoaderUtil.class) {
                if (loader == null)
                    loader = new ImageLoaderUtil();
            }
        }
        return loader;
    }
    public void displayFromNetD(Context context, String url, ImageView imageView) {
        if (context != null && imageView != null) {
            Glide.with(context).load(url).error(R.drawable.default_portrait_100).thumbnail(0.8f).fitCenter()
                    .into(imageView);
        }
    }
    @Override
    public void displayFromNet(Context context, String url, ImageView imageView) {
        if (context != null && imageView != null) {
            Glide.with(context).load(url).error(R.drawable.default_portrait_100).thumbnail(0.5f).override(140, 140).fitCenter()
                    .into(imageView);
        }
    }

    @Override
    public void displayFromNet6(Context context, String url, ImageView imageView) {
        if (context != null && imageView != null) {
            Glide.with(context).load(url).thumbnail(0.5f).error(R.drawable.default_portrait_100).override(140, 140).fitCenter().priority( Priority.HIGH)
                    .into(imageView);
        }
    }

    @Override
    public void displayFromLocal(Context context, ImageView imageView,
                                 int resourceId) {
        if (context != null && imageView != null) {
            Glide.with(context).load(resourceId).fitCenter().into(imageView);
        }
    }

    @Override
    public void displayFromLocal(Context context, ImageView imageView, String path) {
        if (context != null) {
            Glide.with(context).load(new File(path)).fitCenter().into(imageView);
        }
    }

    @Override
    public void displayFromLocal(Context context, ImageView imageView, String path,int width,int height) {
        if (context != null) {
            Glide.with(context).load(new File(path)).override(width,height).into(imageView);
        }
    }

    @Override
    public void displayFromFile(Context context, ImageView imageView, File file) {
        if (context != null) {
            Glide.with(context).load(file).fitCenter().into(imageView);
        }
    }
}

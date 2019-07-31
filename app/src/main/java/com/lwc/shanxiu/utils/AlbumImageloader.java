package com.lwc.shanxiu.utils;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lwc.shanxiu.configs.ServerConfig;
import com.lwc.shanxiu.configs.TApplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Video.Thumbnails;

/**
 *  相册
 *
 * @Description TODO
 * @author CodeApe
 * @version 1.0
 * @date 2014年2月15日
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 *
 */
public class AlbumImageloader {

	/** 锁对象 */
	private final Object lock = new Object();
	/** 是否允许加载 */
	private boolean mAllowLoad = true;
	/** 是否第一次加载 */
	private boolean firstLoad = true;
	/** 下载开始范围 */
	private int mStartLoadLimit = 0;
	/** 下载结束范围 */
	private int mStopLoadLimit = 0;

	/** 设置默认图片，加载图片失败的时候返回默认图片 */
	private Bitmap defaultBitmap = null;

	/** 使用线程池 */
	private final ExecutorService executorService;
	/** 最大允许线程数 */
	private int maxThread = 1;

	/** 引入内存缓存机制 ,将加载过的图片放入缓存 */
	private final Map<Integer, SoftReference<Bitmap>> imageCache = new HashMap<Integer, SoftReference<Bitmap>>();

	/**
	 * 构造方法
	 * 
	 * @version 1.0
	 * @createTime 2013-11-11,下午7:53:30
	 * @updateTime 2013-11-11,下午7:53:30
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param maxThread
	 *        最大允许线程数
	 * @param resId
	 *        默认图片资源Id，加载图片失败的时候将返回该图片
	 */
	public AlbumImageloader(int maxThread, int resId) {
		this.maxThread = maxThread;
		executorService = Executors.newFixedThreadPool(this.maxThread);
		defaultBitmap = BitmapFactory.decodeResource(TApplication.context.getResources(), resId);
	}

	/**
	 * 下载图片
	 * 
	 * @author 罗文忠
	 * @version 1.0
	 * @date 2013-4-16
	 * 
	 * @param mContext
	 *        上下文
	 * @param position
	 *        位置
	 * @param imagePath
	 *        图片路径
	 * @param callback
	 *        图片下载完成后回调
	 * @return 返回软引用图片
	 */
	public Bitmap loadImageBitmap(final int position, final int photoId, final ImageCallback callback) {
		// 如果缓存过就从缓存中取出数据
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case ServerConfig.WHAT_ASYN_LOADIMAGE_ERROR:
					callback.imageLoaded(position, (Bitmap) msg.obj, photoId);
					break;
				case ServerConfig.WHAT_ASYN_LOADIMAGE_SUCCESS:
					callback.imageLoaded(position, (Bitmap) msg.obj, photoId);
					break;
				}
			}
		};

		// 缓存中是否有该图片，有则直接从缓存中取出图片
		if (imageCache.containsKey(photoId)) {
			SoftReference<Bitmap> softReference = imageCache.get(photoId);
			if (softReference.get() != null) {
				return softReference.get();
			}
		}

		// 线程池提交线程
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				if (!mAllowLoad) {
					synchronized (lock) {
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				if (mAllowLoad && firstLoad) {
					cacheLoadBitmap(photoId, handler);
				}
				if (mAllowLoad && position <= mStopLoadLimit && position >= mStartLoadLimit) {
					cacheLoadBitmap(photoId, handler);
				}
			}
		});
		return defaultBitmap;
	}

	/**
	 * 缓存下载图片
	 * 
	 * @author 罗文忠
	 * @version 1.0
	 * @date 2013-4-16
	 * 
	 * @param imagePath
	 * @param handler
	 */
	private void cacheLoadBitmap(int photoId, Handler handler) {
		Bitmap bitmap = null;
		bitmap = loadImageFromPath(photoId);
		if (bitmap == null) {
			bitmap = defaultBitmap;
			imageCache.put(photoId, new SoftReference<Bitmap>(bitmap));
			handler.sendMessage(handler.obtainMessage(ServerConfig.WHAT_ASYN_LOADIMAGE_ERROR, bitmap));
		} else {
			imageCache.put(photoId, new SoftReference<Bitmap>(bitmap));
			handler.sendMessage(handler.obtainMessage(ServerConfig.WHAT_ASYN_LOADIMAGE_SUCCESS, bitmap));
		}
	}

	/**
	 * 根据图片id加载图片
	 *
	 * @version 1.0
	 * @createTime 2014年2月15日,上午1:04:26
	 * @updateTime 2014年2月15日,上午1:04:26
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param photoId
	 *        图片id
	 * @return
	 */
	private Bitmap loadImageFromPath(int photoId) {

		return MediaStore.Images.Thumbnails.getThumbnail(TApplication.context.getContentResolver(), photoId, Thumbnails.MICRO_KIND, null);
	}

	/**
	 * 设置图片加载开始位置
	 * 
	 * @author 罗文忠
	 * @version 1.0
	 * @date 2013-4-16
	 * 
	 * @param startLoadLimit
	 * @param stopLoadLimit
	 */
	public void setLoadLimit(int startLoadLimit, int stopLoadLimit) {
		if (startLoadLimit > stopLoadLimit) {
			return;
		}
		mStartLoadLimit = startLoadLimit;
		mStopLoadLimit = stopLoadLimit;
	}

	public void restore() {
		mAllowLoad = true;
		firstLoad = true;
	}

	/**
	 * 锁定下载图片线程
	 * 
	 * @author 罗文忠
	 * @version 1.0
	 * @date 2013-4-16
	 * 
	 */
	public void lock() {
		mAllowLoad = false;
		firstLoad = false;
	}

	/**
	 * 解锁下载图片的线程
	 * 
	 * @author 罗文忠
	 * @version 1.0
	 * @date 2013-4-16
	 * 
	 */
	public void unlock() {
		mAllowLoad = true;
		synchronized (lock) {
			lock.notifyAll();
		}
	}

	/**
	 * 加载图片回调接口
	 * 
	 * @Description TODO
	 * @author CodeApe
	 * @version 1.0
	 * @date 2013-10-24
	 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
	 * 
	 */
	public interface ImageCallback {

		/**
		 * 图片加载完成之后回调该接口
		 * 
		 * @version 1.0
		 * @createTime 2013-10-24,下午2:02:10
		 * @updateTime 2013-10-24,下午2:02:10
		 * @createAuthor CodeApe
		 * @updateAuthor CodeApe
		 * @updateInfo (此处输入修改内容,若无修改可不写.)
		 * 
		 * @param position
		 *        图片在list的位置
		 * @param bitmap
		 *        图片位图对象 ，null加载失败，否则返回得到的位图对象
		 * @param imagePath
		 *        图片路径
		 */
		public void imageLoaded(int position, Bitmap bitmap, int photoId);

	}

}

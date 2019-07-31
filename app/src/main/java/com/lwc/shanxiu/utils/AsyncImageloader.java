package com.lwc.shanxiu.utils;

import java.io.File;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.configs.TApplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Video.Thumbnails;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class AsyncImageloader {

	// *****************************本地消息状态码 ******************************//
	/** 异步下载图片失败 */
	public static final int WHAT_ASYN_LOADIMAGE_ERROR = 1;
	/** 异步下载图片成功 */
	public static final int WHAT_ASYN_LOADIMAGE_SUCCESS = 2;

	// *********************线程池管理*********************//
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
	public Bitmap defaultBitmap = null;
	/** 设置默认图片，加载图片失败的时候返回默认图片资源 */
	public int resid = 0;

	/** 使用线程池 */
	private final ExecutorService executorService;
	/** 最大允许线程数 */
	private int maxThread = 1;
	/** 图片大小 */
	private int size = 0;
	/** 图片圆角弧度 */
	private int radius = 0;

	/** 是否视频 */
	private boolean isVideo = false;
	/** 是否媒体图片 */
	private boolean isMediaPhoto = false;

	/** 引入内存缓存机制 ,将加载过的图片放入缓存 */
	private final Map<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();

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
	public AsyncImageloader(int maxThread, int resId) {
		this.maxThread = maxThread;
		this.resid = resId;
		executorService = Executors.newFixedThreadPool(this.maxThread);
		defaultBitmap = BitmapFactory.decodeResource(TApplication.context.getResources(), resId);
	}

	/**
	 * 构造函数
	 * 
	 * @version 1.0
	 * @createTime 2013-11-17,下午6:37:42
	 * @updateTime 2013-11-17,下午6:37:42
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param maxThread
	 *        最大允许线程数
	 * @param resId
	 *        默认图片资源
	 * @param size
	 *        图片大小
	 * @param radius
	 *        图片圆角
	 * @param isVideo
	 *        是否视屏
	 */
	public AsyncImageloader(int maxThread, int resId, int size, int radius, boolean isVideo) {
		this.maxThread = maxThread;
		this.size = size;
		this.radius = radius;
		this.isVideo = isVideo;
		this.resid = resId;
		executorService = Executors.newFixedThreadPool(this.maxThread);
		defaultBitmap = BitmapFactory.decodeResource(TApplication.context.getResources(), resId);
	}

	/**
	 * 构造函数
	 * 
	 * @version 1.0
	 * @createTime 2013-11-11,下午7:57:26
	 * @updateTime 2013-11-11,下午7:57:26
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param maxThread
	 *        最大允许线程数
	 * @param resId
	 *        图片资源Id
	 * @param size
	 *        图片大小
	 * @param radius
	 *        圆角弧度
	 */
	public AsyncImageloader(int maxThread, int resId, int size, int radius) {
		this.maxThread = maxThread;
		this.size = size;
		this.radius = radius;
		this.resid = resId;
		executorService = Executors.newFixedThreadPool(this.maxThread);
		defaultBitmap = BitmapFactory.decodeResource(TApplication.context.getResources(), resId);
	}

	/**
	 * 描述：设置头像默认的图片
	 * 
	 * @version 1.0
	 * @createTime 2014-4-9 下午2:36:35
	 * @createAuthor 健兴
	 * 
	 * @updateTime 2014-4-9 下午2:36:35
	 * @updateAuthor 健兴
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param image
	 */
	public void setPortraitDefaul(ImageView image) {
		if (image != null) {
			image.setScaleType(ScaleType.FIT_CENTER);
			image.setImageBitmap(defaultBitmap);
		}
	}

	/**
	 * 描述：设置默认的图片
	 * 
	 * @version 1.0
	 * @createTime 2014-4-9 下午2:36:35
	 * @createAuthor 健兴
	 * 
	 * @updateTime 2014-4-9 下午2:36:35
	 * @updateAuthor 健兴
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param image
	 */
	public void setDefaul(ImageView image) {
		if (image != null) {
			image.setScaleType(ScaleType.FIT_CENTER);
			image.setImageBitmap(defaultBitmap);
			image.setBackgroundResource(R.drawable.logo_new);
		}
	}

	/**
	 * 设置圆角
	 *
	 * @version 1.0
	 * @createTime 2014年6月4日,上午11:41:40
	 * @updateTime 2014年6月4日,上午11:41:40
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param radius
	 *        圆角弧度
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * 描述：设置错误图片
	 * 
	 * @version 1.0
	 * @createTime 2014-4-10 下午2:42:09
	 * @createAuthor 健兴
	 * 
	 * @updateTime 2014-4-10 下午2:42:09
	 * @updateAuthor 健兴
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param image
	 */
	public void setError(ImageView image) {
		setDefaul(image);
	}

	/**
	 * 下载图片
	 * 
	 * @author wujianxing
	 * @date 2014-3-4
	 * 
	 * @param context
	 *        上下文
	 * @param position
	 *        位置
	 * @param imagePath
	 *        图片路径
	 * @param callback
	 *        图片下载完成后回调
	 * @return 返回软引用图片
	 */
	public Bitmap loadImageBitmap(final Context context, final int position, final String imagePath, final ImageCallback callback) {
		return loadImageBitmap(context, null, position, imagePath, null, callback, false, null);
	}

	/**
	 * 下载图片
	 * 
	 * @author wujianxing
	 * @date 2014-3-4
	 * 
	 * @param context
	 *        上下文
	 * @param position
	 *        位置
	 * @param imagePath
	 *        图片路径
	 * @param callback
	 *        图片下载完成后回调
	 * @return 返回软引用图片
	 */
	public Bitmap loadImageBitmap(final Context context, final int position, final String imagePath, final String localPath,
			final ImageCallback callback) {
		return loadImageBitmap(context, null, position, imagePath, localPath, callback, false, null);
	}

	/**
	 * 下载图片
	 * 
	 * @author wujianxing
	 * @date 2014-3-4
	 * 
	 * @param context
	 *        上下文
	 * @param position
	 *        位置
	 * @param imagePath
	 *        图片路径
	 * @param callback
	 *        图片下载完成后回调
	 * @return 返回软引用图片
	 */
	public Bitmap loadImageBitmap(final Context context, final ImageView image, final int position, final String imagePath, final String localPath,
			final ImageCallback callback, boolean isVideo) {
		this.isVideo = isVideo;
		return loadImageBitmap(context, image, position, imagePath, localPath, callback, false, null);
	}

	/**
	 * 下载图片
	 * 
	 * @author Codeape
	 * @date 2014-6-4
	 * 
	 * @param context
	 *        上下文
	 * @param position
	 *        位置
	 * @param photoId
	 *        图片在相册里边的id
	 * @param callback
	 *        图片下载完成后回调
	 * @param isMediaPhoto
	 *        是否从系统相册加载图片
	 * @return 返回软引用图片
	 */
	public Bitmap loadImageBitmap(final Context context, final ImageView image, final int position, final int photoId, final String localPath,
			final ImageCallback callback, boolean isMediaPhoto) {
		this.isMediaPhoto = isMediaPhoto;
		return loadImageBitmap(context, image, position, photoId + "", localPath, callback, false, null);
	}

	/**
	 * 下载图片
	 * 
	 * @author 罗文忠
	 * @version 1.0
	 * @date 2013-4-16
	 * 
	 * @update author wujianxing
	 * @update info 添加image参数，修改为从内存中获取图片成功，就回调；否则，设置默认Bitmap
	 * @date 2014-3-4
	 * 
	 * @param context
	 *        上下文
	 * @param image
	 *        设置图片的控件
	 * @param position
	 *        位置
	 * @param imagePath
	 *        图片路径
	 * @param callback
	 *        图片下载完成后回调
	 * @param isPortrait
	 *        是否是头像，true是，false不是
	 * @param callback
	 *        图片下载完成后回调
	 * @return 返回软引用图片
	 */
	public Bitmap loadImageBitmap(final Context context, final ImageView image, final int position, final String imagePath, final String localPath,
			final ImageCallback callback, final boolean isPortrait, final String str) {
		// 如果缓存过就从缓存中取出数据
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case WHAT_ASYN_LOADIMAGE_ERROR:
					if (image == null) {
						callback.imageLoaded(position, (Bitmap) msg.obj, imagePath);
					} else {
						if (isPortrait) {
							setPortraitDefaul(image);
						} else {
							setError(image);
						}
					}
					break;
				case WHAT_ASYN_LOADIMAGE_SUCCESS:
					callback.imageLoaded(position, (Bitmap) msg.obj, imagePath);
					break;
				}
			}
		};

		// 缓存中是否有该图片，有则直接从缓存中取出图片
		if (imageCache.containsKey(imagePath)) {
			SoftReference<Bitmap> softReference = imageCache.get(imagePath);
			if (softReference.get() != null) {
				// return softReference.get();
				Bitmap bitmap = softReference.get();
				if (bitmap != null) {
					if (image != null) {
						callback.imageLoaded(position, bitmap, imagePath);
					}
					return bitmap;
				}
			}
		}

		if (image != null) {
			if (isPortrait) {
				setPortraitDefaul(image);
			} else {
				setError(image);
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
					cacheLoadBitmap(context, imagePath, size, radius, handler, localPath);
				}
				if (mAllowLoad && position <= mStopLoadLimit && position >= mStartLoadLimit) {
					cacheLoadBitmap(context, imagePath, size, radius, handler, localPath);
				}
			}
		});
		return defaultBitmap;
	}

	/**
	 * 
	 * 下载图片(本地下载)
	 * 
	 * @version 1.0
	 * @createTime 2014年12月1日,上午11:21:40
	 * @updateTime 2014年12月1日,上午11:21:40
	 * @createAuthor WangYuWen
	 * @updateAuthor WangYuWen
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context
	 *        上下文
	 * @param image
	 *        设置图片的控件
	 * @param position
	 *        位置
	 * @param imagePath
	 *        图片路径
	 * @param callback
	 *        图片下载完成后回调
	 * @param isPortrait
	 *        是否是头像，true是，false不是
	 * @param callback
	 *        图片下载完成后回调
	 * @return 返回软引用图片
	 */
	public Bitmap loadImageBitmapBenDi(final Context context, final ImageView image, final int position, final String imagePath,
			final String localPath, final ImageCallback callback, final boolean isPortrait, final String str) {
		// 如果缓存过就从缓存中取出数据
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case WHAT_ASYN_LOADIMAGE_ERROR:
					if (image == null) {
						callback.imageLoaded(position, (Bitmap) msg.obj, imagePath);
					} else {
						if (isPortrait) {
							setPortraitDefaul(image);
						} else {
							setError(image);
						}
					}
					break;
				case WHAT_ASYN_LOADIMAGE_SUCCESS:
					callback.imageLoaded(position, (Bitmap) msg.obj, imagePath);
					break;
				}
			}
		};

		// 缓存中是否有该图片，有则直接从缓存中取出图片
		if (imageCache.containsKey(imagePath)) {
			SoftReference<Bitmap> softReference = imageCache.get(imagePath);
			if (softReference.get() != null) {
				// return softReference.get();
				Bitmap bitmap = softReference.get();
				if (bitmap != null) {
					if (image != null) {
						callback.imageLoaded(position, bitmap, imagePath);
					}
					return bitmap;
				}
			}
		}

		if (image != null) {
			if (isPortrait) {
				setPortraitDefaul(image);
			} else {
				setError(image);
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
					cacheLoadBitmapBenDi(context, imagePath, size, radius, handler, localPath, str);
				}
				if (mAllowLoad && position <= mStopLoadLimit && position >= mStartLoadLimit) {
					cacheLoadBitmapBenDi(context, imagePath, size, radius, handler, localPath, str);
				}
			}
		});
		return defaultBitmap;
	}

	/**
	 * 下载图片
	 * 
	 * @author 罗文忠
	 * @version 1.0
	 * @date 2013-4-16
	 * 
	 * @param context
	 *        上下文
	 * @param position
	 *        位置
	 * @param imagePath
	 *        图片路径
	 * @param isCutRound
	 *        是否裁剪圆角
	 * @param callback
	 *        图片下载完成后回调
	 * @return 返回软引用图片
	 */
	public Bitmap loadImageBitmap(final Context context, final int position, final String imagePath, final boolean isCutRound,
			final ImageCallback callback) {
		// 如果缓存过就从缓存中取出数据
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case WHAT_ASYN_LOADIMAGE_ERROR:
					LogUtil.out("下载图片失败");
					callback.imageLoaded(position, (Bitmap) msg.obj, imagePath);
					break;
				case WHAT_ASYN_LOADIMAGE_SUCCESS:
					LogUtil.out("下载图片成功");
					callback.imageLoaded(position, (Bitmap) msg.obj, imagePath);
					break;
				}
			}
		};

		// 缓存中是否有该图片，有则直接从缓存中取出图片
		if (imageCache.containsKey(imagePath)) {
			SoftReference<Bitmap> softReference = imageCache.get(imagePath);
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
					if (isCutRound) {
						cacheLoadBitmap(context, imagePath, size, radius, handler, null);
					} else {
						cacheLoadBitmap(context, imagePath, 0, 0, handler, null);
					}
				}
				if (mAllowLoad && position <= mStopLoadLimit && position >= mStartLoadLimit) {
					if (isCutRound) {
						cacheLoadBitmap(context, imagePath, size, radius, handler, null);
					} else {
						cacheLoadBitmap(context, imagePath, 0, 0, handler, null);
					}
				}
			}
		});
		return defaultBitmap;
	}

	/**
	 * 缓存图片
	 * 
	 * @version 1.0
	 * @createTime 2014年4月5日,下午3:10:26
	 * @updateTime 2014年4月5日,下午3:10:26
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context
	 *        上下文
	 * @param imagePath
	 *        图片路径
	 * @param size
	 *        图片大小
	 * @param radius
	 *        圆角大小
	 * @param handler
	 *        异步通知
	 * @param savepath
	 *        保存路径
	 */
	private void cacheLoadBitmap(Context context, String imagePath, int size, int radius, Handler handler, String savepath) {
		Bitmap bitmap = null;
		bitmap = loadImageFromPath(imagePath, size, radius, savepath);
		if (bitmap == null) {
			bitmap = defaultBitmap;
			// imageCache.put(imagePath, new
			// SoftReference<Bitmap>(bitmap));
			handler.sendMessage(handler.obtainMessage(WHAT_ASYN_LOADIMAGE_ERROR, bitmap));
		} else {
			imageCache.put(imagePath, new SoftReference<Bitmap>(bitmap));
			handler.sendMessage(handler.obtainMessage(WHAT_ASYN_LOADIMAGE_SUCCESS, bitmap));
		}
	}

	/**
	 * 根据路径加载图片并处理
	 * 
	 * @version 1.0
	 * @createTime 2014年4月5日,下午3:13:47
	 * @updateTime 2014年4月5日,下午3:13:47
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param imagePath
	 *        图片路径
	 * @param size
	 *        图片大小
	 * @param radius
	 *        圆角弧度
	 * @param savePath
	 *        图片保存路径
	 * @return
	 */
	private Bitmap loadImageFromPath(String imagePath, int size, int radius, final String savePath) {

		Bitmap bitmap = null;
		String loaclPath = imagePath;
		if (savePath != null) {// 存储到指定文件夹下边
			// loaclPath = savePath;
			loaclPath = StringUtil.getUserLocalImagePath(savePath, imagePath);
		} else {
			if (!new File(imagePath).exists()) {
				loaclPath = StringUtil.getUserLocalImagePath(imagePath);
			}
		}

		if (isVideo) {// 如果是视屏，获取视频缩略图
			if (new File(loaclPath).exists()) {
				bitmap = ThumbnailUtils.createVideoThumbnail(imagePath, Thumbnails.MINI_KIND);
				if (bitmap != null) {
					bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * 300 / bitmap.getHeight(), 300, true);
				}
			}
		} else if (isMediaPhoto) { // 从媒体库获取图片
			bitmap = MediaStore.Images.Thumbnails.getThumbnail(TApplication.context.getContentResolver(), Integer.parseInt(imagePath),
					Thumbnails.MICRO_KIND, null);
		} else {// 普通图片
			if (new File(loaclPath).exists()) {
				bitmap = BitmapFactory.decodeFile(loaclPath);
				if (bitmap == null) {// 如果bitmap为空，文件已经损毁删除文件
					new File(loaclPath).delete();
				}
			} else {
				try {
					new NetUtil().downloadFile(imagePath, loaclPath);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (new File(loaclPath).exists()) {
					bitmap = BitmapFactory.decodeFile(loaclPath);
				}
			}
		}

		if (bitmap != null) {
			if (size > 0) {
				bitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);
			}
			if (radius > 0) {
				bitmap = OperationImage.cutRound(bitmap, radius);
			}
		}

		return bitmap;
	}

	private void cacheLoadBitmapBenDi(Context context, String imagePath, int size, int radius, Handler handler, String savepath, String suffix) {
		Bitmap bitmap = null;
		bitmap = loadImageFromPathBenDi(imagePath, size, radius, savepath, suffix);
		if (bitmap == null) {
			bitmap = defaultBitmap;
			// imageCache.put(imagePath, new
			// SoftReference<Bitmap>(bitmap));
			handler.sendMessage(handler.obtainMessage(WHAT_ASYN_LOADIMAGE_ERROR, bitmap));
		} else {
			imageCache.put(imagePath, new SoftReference<Bitmap>(bitmap));
			handler.sendMessage(handler.obtainMessage(WHAT_ASYN_LOADIMAGE_SUCCESS, bitmap));
		}
	}

	/**
	 * 根据路径加载图片并处理（本地）
	 *
	 * @version 1.0
	 * @createTime 2014年12月1日,上午10:58:34
	 * @updateTime 2014年12月1日,上午10:58:34
	 * @createAuthor WangYuWen
	 * @updateAuthor WangYuWen
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param imagePath
	 * @param size
	 * @param radius
	 * @param savePath
	 * @return
	 */
	private Bitmap loadImageFromPathBenDi(String imagePath, int size, int radius, final String savePath, final String suffix) {

		Bitmap bitmap = null;
		String loaclPath = StringUtil.getUserLocalImagePath(imagePath);

		// if (isVideo) {// 如果是视屏，获取视频缩略图
		// if (new File(loaclPath).exists()) {
		// bitmap = ThumbnailUtils.createVideoThumbnail(imagePath, Thumbnails.MINI_KIND);
		// if (bitmap != null) {
		// bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * 300 / bitmap.getHeight(), 300, true);
		// }
		// }
		// } else if (isMediaPhoto) { // 从媒体库获取图片
		// bitmap = MediaStore.Images.Thumbnails.getThumbnail(TApplication.context.getContentResolver(), Integer.parseInt(imagePath),
		// Thumbnails.MICRO_KIND, null);
		// } else {// 普通图片
		if (new File(loaclPath).exists()) {
			bitmap = BitmapFactory.decodeFile(loaclPath);
			if (bitmap == null) {// 如果bitmap为空，文件已经损毁删除文件
				new File(loaclPath).delete();
			}
		} else {
			String pathName = new ImageUtil().compressImage(imagePath, imagePath.substring(imagePath.lastIndexOf("/") + 1), 80, suffix);
			bitmap = BitmapFactory.decodeFile(pathName);
			if (bitmap == null) {// 如果bitmap为空，文件已经损毁删除文件
				new File(imagePath).delete();
			}
		}
		// }

		if (bitmap != null) {
			if (size > 0) {
				bitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);
			}
			if (radius > 0) {
				bitmap = OperationImage.cutRound(bitmap, radius);
			}
		}

		return bitmap;
	}

	/**
	 * 根据路径加载图片并处理
	 * 
	 * @version 1.0
	 * @createTime 2014年4月5日,下午3:13:47
	 * @updateTime 2014年4月5日,下午3:13:47
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param imagePath
	 *        图片路径
	 * @param size
	 *        图片大小
	 * @param radius
	 *        圆角弧度
	 * @param savePath
	 *        图片保存路径
	 * @return
	 */
	// private Bitmap loadImageFromPath(String imagePath, int size, int
	// radius, final String savePath) {
	//
	// Bitmap bitmap = null;
	// String loaclPath = imagePath;
	// if (savePath != null) {// 存储到指定文件夹下边
	// // loaclPath = savePath;
	// loaclPath = StringUtil.getUserLocalImagePath(savePath, imagePath);
	// } else {
	// if (!new File(imagePath).exists()) {
	// loaclPath = StringUtil.getUserLocalImagePath(imagePath);
	// }
	// }
	//
	// if (isVideo) {// 如果是视屏，获取视频缩略图
	// if (new File(loaclPath).exists()) {
	// bitmap = ThumbnailUtils.createVideoThumbnail(imagePath,
	// Thumbnails.MINI_KIND);
	// if (bitmap != null) {
	// bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * 300 /
	// bitmap.getHeight(), 300, true);
	// }
	// }
	// } else if (isMediaPhoto) { // 从媒体库获取图片
	// bitmap =
	// MediaStore.Images.Thumbnails.getThumbnail(TApplication.context.getContentResolver(),
	// Integer.parseInt(imagePath), Thumbnails.MICRO_KIND, null);
	// } else {// 普通图片
	// if (new File(loaclPath).exists()) {
	// bitmap = BitmapFactory.decodeFile(loaclPath);
	// if (bitmap == null) {// 如果bitmap为空，文件已经损毁删除文件
	// new File(loaclPath).delete();
	// }
	// } else {
	// try {
	// new Net().downloadFile(imagePath, loaclPath);
	// } catch (MalformedURLException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// if (new File(loaclPath).exists()) {
	// bitmap = BitmapFactory.decodeFile(loaclPath);
	// }
	// }
	// }
	//
	// if (bitmap != null) {
	// if (size > 0) {
	// bitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);
	// }
	// if (radius > 0) {
	// bitmap = OperationImage.cutRound(bitmap, radius);
	// }
	// }
	//
	// return bitmap;
	// }

	/**
	 * 设置图片加载开始位置
	 * 
	 * @author 罗文忠
	 * @version 1.0
	 * @date 2013-4-16
	 * 
	 * @param startLoadLimit
	 *        开始加载图片范围
	 * @param stopLoadLimit
	 *        结束图片加载范围
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
	 * @Description
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
		public void imageLoaded(int position, Bitmap bitmap, String imagePath);

	}

}

package com.lwc.shanxiu.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.lwc.shanxiu.interf.LoadImageCallBack;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * 异步工具类
 * 
 * @version 1.0
 * @date 2013-04-05
 */
public class AsynUntil {

	/** 加载的位图对象 */
	private Bitmap bitmap;

	/**
	 * 异步加载图片
	 * 
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context
	 * @param netPath
	 * @param callBack
	 */
	public void loadImage(final Context context, final String netPath, final LoadImageCallBack callBack) {

		final String loaclPath;
		if (!new File(netPath).exists()) {
			loaclPath = StringUtil.getUserLocalImagePath(netPath);
		} else {
			loaclPath = netPath;
		}

		// final String loaclPath = StringUtil.getUserLocalImagePath(netPath);
		bitmap = null;
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				if (new File(loaclPath).exists()) {
					bitmap = BitmapFactory.decodeFile(loaclPath);
					if (bitmap == null) {// 如果bitmap为空，文件已经损毁删除文件
						new File(loaclPath).delete();
					}
				}
				if (bitmap == null) {
					try {
						new Net().downloadFile(netPath, loaclPath);
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (new File(loaclPath).exists()) {
						bitmap = BitmapFactory.decodeFile(loaclPath);
					}
				}

				// bitmap处理

				((Activity) context).runOnUiThread(new Runnable() {

					@Override
					public void run() {
						callBack.onCallBack(bitmap);
					}
				});
			}
		};
		ExecutorServiceUtil.getInstance().execute(runnable);
	}

	/**
	 * 下载图片并返回bitmap（未缓存）
	 * 
	 * @updateInfo
	 * @param
	 */
	public void loadImageBitMap(final Context context, final String path, final LoadImageCallBack callBack) {
		bitmap = null;
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				try {
					bitmap = new Net().downImage(path);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				((Activity) context).runOnUiThread(new Runnable() {

					@Override
					public void run() {
						callBack.onCallBack(bitmap);
					}
				});
			}
		};
		ExecutorServiceUtil.getInstance().execute(runnable);
	}

	/**
	 * 加载带圆角的图片
	 * 
	 * @version 1.0
	 * @date 2013-4-16
	 * 
	 * @param context
	 * @param netPath
	 * @param v
	 * @param round
	 *        圆角角度
	 * @param type
	 *        加载类型 1 用户头像 0 其他
	 */
	public void loadImage(final Context context, final String netPath, final int type, final int round, final LoadImageCallBack callBack) {

		bitmap = null;
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				String loaclPath = "";
				if (1 == type) {
					loaclPath = StringUtil.getUserLocalportraitImagePath(netPath);
				} else {
					loaclPath = StringUtil.getUserLocalImagePath(netPath);
				}

				if (new File(loaclPath).exists()) {
					bitmap = BitmapFactory.decodeFile(loaclPath);
					if (bitmap == null) {// 如果bitmap为空，文件已经损毁删除文件
						new File(loaclPath).delete();
					}
				} else {
					try {
						new Net().downloadFile(netPath, loaclPath);
					} catch (MalformedURLException e) {

						e.printStackTrace();
					} catch (IOException e) {

						e.printStackTrace();
					}
					if (new File(loaclPath).exists()) {
						bitmap = BitmapFactory.decodeFile(loaclPath);
					}
				}

				((Activity) context).runOnUiThread(new Runnable() {

					@Override
					public void run() {

						if (bitmap != null) {
							bitmap = OperationImage.cutRound(bitmap, round);
						}
						callBack.onCallBack(bitmap);
					}
				});
			}
		};
		ExecutorServiceUtil.getInstance().execute(runnable);
	}

	/**
	 * 压缩并加载图片
	 * 
	 * @version 1.0
	 * @createTime 2014-9-28 上午11:45:44
	 * @createAuthor liuyue
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param context
	 * @param netPath
	 * @param callBack
	 */
	public void loadImageWithScale(final Context context, final String netPath, final LoadImageCallBack callBack, final int reqWidth,
			final int reqHeight) {
		bitmap = null;
		final String loaclPath;
		if (netPath.startsWith("http")) {
			loadImageBitMap(context, netPath, callBack);
		} else {
			if (!new File(netPath).exists()) {
				loaclPath = StringUtil.getUserLocalImagePath(netPath);
			} else {
				loaclPath = netPath;
			}
			Runnable runnable = new Runnable() {

				@Override
				public void run() {
					if (new File(loaclPath).exists()) {
						bitmap = decodeSampledBitmapFromResource(loaclPath, reqWidth, reqHeight);
						if (bitmap == null) {// 如果bitmap为空，文件已经损毁删除文件
							new File(loaclPath).delete();
						}
					} else {
						try {
							new Net().downloadFile(netPath, loaclPath);
						} catch (MalformedURLException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						if (new File(loaclPath).exists()) {
							bitmap = decodeSampledBitmapFromResource(loaclPath, reqWidth, reqHeight);
						}
					}

					// bitmap处理

					((Activity) context).runOnUiThread(new Runnable() {

						@Override
						public void run() {
							callBack.onCallBack(bitmap);
						}
					});
				}
			};
			ExecutorServiceUtil.getInstance().execute(runnable);
		}

	}

	/**
	 * 计算图片压缩比例
	 * 
	 * @version 1.0
	 * @createTime 2014-9-28 上午11:46:34
	 * @createAuthor liuyue
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// 源图片的高度和宽度
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			// 计算出实际宽高和目标宽高的比率
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			// 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
			// 一定都会大于等于目标的宽和高。
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	/**
	 * 根据指定宽高压缩生成图片
	 * 
	 * @version 1.0
	 * @createTime 2014-9-28 上午11:52:23
	 * @createAuthor liuyue
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param path
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public Bitmap decodeSampledBitmapFromResource(String path, int reqWidth, int reqHeight) {
		// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		// 调用上面定义的方法计算inSampleSize值
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		// 使用获取到的inSampleSize值再次解析图片
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	/***
	 * 异步加载图片方法
	 * 
	 * @version 1.0
	 * @createTime 2015年3月8日,下午3:33:08
	 * @updateTime 2015年3月8日,下午3:33:08
	 * @createAuthor zhou wan
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param context
	 * @param path
	 * @param img_portrait
	 */
	public void loadImage(final Context context, final String path, final ImageView img_portrait, final int image_default) {
		img_portrait.setTag(path);
		new AsynUntil().loadImage(context, path, new LoadImageCallBack() {
			@Override
			public void onCallBack(Bitmap bitmap) {
				if (!path.equals(img_portrait.getTag())) {
					return;
				}
				if (null != bitmap) {
					img_portrait.setImageBitmap(bitmap);
				} else {
					img_portrait.setImageBitmap(OperationImage.toRoundBitmap(BitmapFactory.decodeResource(context.getResources(), image_default)));
				}
			}

		});
	}

}

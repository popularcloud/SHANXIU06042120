package com.lwc.shanxiu.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * 图片操作类
 * 
 * @Description 提供操作图片方法
 * @author 刘艺谋
 * @date 2013-3-7
 */
public class OperationImage {

	/**
	 * 处理图片圆角,将图片的四角处理成圆角.
	 * 
	 * @param bitmap
	 *        处理图片.
	 * @param pixels
	 *        处理角度.
	 * @return 处理完成的图片.
	 * @author 刘艺谋
	 * @version 1.0, 2013-3-7
	 */
	public static Bitmap cutRound(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		canvas.save();
		bitmap.recycle();
		return output;
	}

	/**
	 * 转换图片成圆形
	 * 
	 * @param bitmap
	 *        传入Bitmap对象
	 * @return
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);

		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		return output;
	}

	/**
	 * 降低图片质量压缩图片.
	 * 
	 * @param bitmap
	 *        压缩图片.
	 * @param size
	 *        压缩质量,单位kb.
	 * @return 压缩完成的图片.
	 * @author 刘艺谋
	 * @version 1.0, 2013-3-7
	 */
	public static Bitmap compressImage(Bitmap bitmap, int size) {
		if (bitmap == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		int flag = baos.toByteArray().length;
		if (flag / 1024 > size) { // 判断如果压缩后图片是否大于size,大于继续压缩
			options = (size * 1024 * 100) / flag;
			baos.reset();// 重置baos即清空baos
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bm = BitmapFactory.decodeStream(bais, null, null);// 把ByteArrayInputStream数据生成图片
		bitmap.recycle();
		return bm;
	}

	/**
	 * 按指定尺寸缩放图片.
	 * 
	 * @param path
	 *        图片路径.
	 * @param width
	 *        指定长度.
	 * @param height
	 *        指定高度.
	 * @return 处理完成的图片.
	 * @author 刘艺谋
	 * @version 1.0, 2013-3-7
	 */
	public static Bitmap scaleImage(String path, int width, int height) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回bitmap为空
		options.inJustDecodeBounds = false;
		int w = options.outWidth;
		int h = options.outHeight;
		int wScale = 1; // 长度缩放比例
		int hScale = 1; // 高度缩放比例
		if (w > width) // 若宽度大于指定宽度,则计算出宽度缩放比例.
			wScale = w / width;
		if (h > height) // 若高度大于指定高度,则计算出高度缩放比例.
			hScale = h / height;
		int scale = 1;
		if (wScale <= hScale)
			scale = hScale;
		else
			scale = wScale;
		options.inSampleSize = scale; // 设置缩放比例
		bitmap = BitmapFactory.decodeFile(path, options);
		return bitmap;
	}

	/**
	 * 按指定尺寸缩放图片.
	 * 
	 * @param path
	 *        图片路径.
	 * @param size
	 *        压缩质量,单位kb.
	 * @param width
	 *        指定长度.
	 * @param height
	 *        指定高度.
	 * @return 处理完成的图片.
	 * @author 刘艺谋
	 * @version 1.0, 2013-3-7
	 */
	public static Bitmap scaleImage(Bitmap bitmap, int size, int width, int height) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int flag = baos.toByteArray().length;
		if (flag / 1024 > size) {// 判断如果图片大于size,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			bitmap.compress(Bitmap.CompressFormat.JPEG, (size * 1024 * 100) / flag, baos);// 这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bm = BitmapFactory.decodeStream(bais, null, options);
		options.inJustDecodeBounds = false;
		int w = options.outWidth;
		int h = options.outHeight;
		int wScale = 1; // 长度缩放比例
		int hScale = 1; // 高度缩放比例
		if (w > width) // 若宽度大于指定宽度,则计算出宽度缩放比例.
			wScale = w / width;
		if (h > height) // 若高度大于指定高度,则计算出高度缩放比例.
			hScale = h / height;
		int scale = 1;
		if (wScale <= hScale)
			scale = hScale;
		else
			scale = wScale;
		options.inSampleSize = scale;// 设置缩放比例
		bais = new ByteArrayInputStream(baos.toByteArray());
		bm = BitmapFactory.decodeStream(bais, null, options);
		bitmap.recycle();
		return bm;// 压缩好比例大小后再进行质量压缩
	}

}

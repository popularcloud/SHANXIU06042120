package com.lwc.shanxiu.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 网络操作类.
 * 
 * @Description 提供网络操作的相关方法.
 * @author 何栋
 * @date 2013-5-29
 */
public class Net {

	/** 响应头信息 */
	private final HashMap<String, String> responseHearders = new HashMap<>();
	/** 返回结果 */
	private String result;
	/** 输入流 */
	private BufferedInputStream input;
	/** http链接 */
	private HttpURLConnection conn;
	/** 输出流 */
	private DataOutputStream output;
	private InputStream inStream;

	/**
	 * 获取当前网络链接状态.
	 * 
	 * @param context
	 *        上下文环境.
	 * @return 有任意网络通畅时返回true,否则返回false.
	 * 
	 * @version 1.0
	 * @createTime 2013-3-5,下午4:21:27
	 * @updateTime 2013-3-5,下午4:21:27
	 * @createAuthor paladin
	 * @updateAuthor paladin
	 * @updateInfo
	 */
	public boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); // 获取网络服务
		if (connectivity == null) { // 判断网络服务是否为空
			return false;
		} else { // 判断当前是否有任意网络服务开启
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 读取响应头信息.
	 * 
	 * @version 1.0
	 * @createTime 2014年6月17日,下午3:20:37
	 * @updateTime 2014年6月17日,下午3:20:37
	 * @createAuthor paladin
	 * @updateAuthor paladin
	 * @updateInfo
	 */
	private void readResponseHeaders() {
		Map<String, List<String>> headers = conn.getHeaderFields();
		Set<String> keys = headers.keySet();
		for (String key : keys) {
			responseHearders.put(key, conn.getHeaderField(key));
		}
		// ViolationsInfoView.attach=responseHearders.get("attach");
	}

	/**
	 * 获取响应头信息.
	 * 
	 * @return 响应头信息.
	 * @version 1.0
	 * @createTime 2014年6月17日,下午3:24:15
	 * @updateTime 2014年6月17日,下午3:24:15
	 * @createAuthor paladin
	 * @updateAuthor paladin
	 * @updateInfo
	 */
	public HashMap<String, String> getResponseHeaders() {
		return responseHearders;
	}

	/**
	 * 将发送请求的参数构造为指定格式.
	 * 
	 * @param attribute
	 *        发送请求的参数,key为属性名,value为属性值.
	 * @return 指定格式的请求参数.
	 * 
	 * @version 1.0
	 * @createTime 2013-3-5,下午4:49:45
	 * @updateTime 2013-3-5,下午4:49:45
	 * @createAuthor paladin
	 * @updateAuthor paladin
	 * @updateInfo
	 */
	private String getParams(HashMap<String, String> attribute) {
		Set<String> keys = attribute.keySet(); // 获取所有参数名
		Iterator<String> iterator = keys.iterator(); // 将所有参数名进行跌代
		StringBuffer params = new StringBuffer();
		// 取出所有参数进行构造
		while (iterator.hasNext()) {
			try {
				String key = iterator.next();
				String param = key + "=" + URLEncoder.encode(attribute.get(key)) + "&";
				params.append(param);
			} catch (Exception e) {
				// PLog.logErrorMessage("net construction params error!");
			}

		}
		// 返回构造结果
		return params.toString().substring(0, params.toString().length() - 1);
	}

	/**
	 * 关闭链接与所有从链接中获得的流.
	 * 
	 * @throws IOException
	 *         关闭发生错误时抛出IOException.
	 * 
	 * @version 1.0
	 * @createTime 2013-3-5,下午4:51:31
	 * @updateTime 2013-3-5,下午4:51:31
	 * @createAuthor paladin
	 * @updateAuthor paladin
	 * @updateInfo
	 */
	private void closeConnection() throws IOException {
		if (input != null) {
			input.close();
		}
		if (output != null) {
			output.close();
		}
		if (conn != null) {
			conn.disconnect();
		}
	}

	/**
	 * 下载文件,下载文件存储至指定路径.
	 * 
	 * @param path
	 *        下载路径.
	 * @param savePath
	 *        存储路径.
	 * @return 下载成功返回true,若下载失败则返回false.
	 * @throws MalformedURLException
	 *         建立连接发生错误抛出MalformedURLException.
	 * @throws IOException
	 *         下载过程产生错误抛出IOException.
	 * 
	 * @version 1.2
	 * @createTime 2013-3-6,下午4:10:13
	 * @updateTime 2013-5-29,下午4:56:43
	 * @createAuthor paladin
	 * @updateAuthor paladin
	 * @updateInfo 取消图片的下载后缀,取消文件下载（除.jpg文件外）的tmp流程.
	 */
	public boolean downloadFile(String path, String savePath) throws MalformedURLException, IOException {
		File file = null;
		InputStream input = null;
		OutputStream output = null;
		boolean success = false;
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.connect();
			int code = conn.getResponseCode();
			if (code == HttpURLConnection.HTTP_OK) {
				input = conn.getInputStream();
				file = new File(savePath);
				file.createNewFile(); // 创建文件
				output = new FileOutputStream(file);
				byte buffer[] = new byte[1024];
				int read = 0;
				while ((read = input.read(buffer)) != -1) { // 读取信息循环写入文件
					output.write(buffer, 0, read);
				}
				output.flush();
				success = true;
			} else {
				success = false;
			}
		} catch (MalformedURLException e) {
			success = false;
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			success = false;
			throw e;
		} finally {
			try {
				if (output != null) {
					output.close();
				}
				if (input != null) {
					input.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return success;
	}

	/**
	 * 下载图片
	 * 
	 * @version 1.0
	 * @createTime 2014年6月27日
	 * @updateTime 2014年6月27日
	 * @createAuthor 王治粮
	 * @updateAuthor 王治粮
	 * @updateInfo
	 * @param
	 */
	public Bitmap downImage(String path) throws MalformedURLException, IOException {
		InputStream input = null;
		ByteArrayOutputStream out = null;
		Bitmap bitmips = null;
		try {
			URL url = new URL(path);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.connect();
			int code = conn.getResponseCode();
			if (code == 200) {
				readResponseHeaders(); // 读取响应头信息.
				input = conn.getInputStream();
				bitmips = getFitSampleBitmap(input);
			}
		} catch (MalformedURLException e) {
			throw e;
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmips;

	}

	public Bitmap getFitSampleBitmap(InputStream  inputStream) throws Exception{
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		byte[] bytes = readStream(inputStream);
		BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
		//options.inSampleSize =4;
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
	}

	/**
	 * 从inputStream中获取字节流 数组大小
	 **/
	public static byte[] readStream(InputStream inStream) throws Exception{
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		outStream.close();
		inStream.close();
		return outStream.toByteArray();
	}

	// /**
	// * 上传文件
	// *
	// * @version 1.0
	// * @date 2013-4-15
	// *
	// * @param path
	// * @param files
	// * @return
	// */
	// public static String sendMultyPartRequest(String path, HashMap<String,
	// String> params, HashMap<String, File> files) {
	// HttpClient httpClient = new DefaultHttpClient();
	// HttpPost httpPost = new HttpPost(path);
	// MultipartEntity multipartEntity = new
	// MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
	// if (params.size() > 0) {
	// Set<String> paramKeys = params.keySet();
	// Iterator<String> paramIterator = paramKeys.iterator();
	// while (paramIterator.hasNext()) {
	// String key = paramIterator.next();
	// try {
	// StringBody stringBody = new StringBody(params.get(key),
	// Charset.forName(HTTP.UTF_8));
	// multipartEntity.addPart(key, stringBody);
	// } catch (UnsupportedEncodingException e) {
	// return TApplication.EXCEPTION_UPLOAD_ERROR_STATUS;
	// }
	// }
	// }
	// if (files.size() > 0) {
	// Set<String> fileKeys = files.keySet();
	// Iterator<String> fileIterator = fileKeys.iterator();
	// while (fileIterator.hasNext()) {
	// String key = fileIterator.next();
	// FileBody fileBody = new FileBody(files.get(key));
	// multipartEntity.addPart(key, fileBody);
	// }
	// }
	// httpPost.setEntity(multipartEntity);
	//
	// String result = null;
	// try {
	// HttpResponse response = httpClient.execute(httpPost);
	// int statueCode = response.getStatusLine().getStatusCode();
	// if (statueCode == HttpStatus.SC_OK) {
	// result = EntityUtils.toString(response.getEntity(), "utf-8");
	// } else {
	// result = TApplication.EXCEPTION_UPLOAD_ERROR_STATUS;
	// }
	// } catch (Exception e) {
	// result = TApplication.EXCEPTION_UPLOAD_ERROR_STATUS;
	// e.printStackTrace();
	// } finally {
	// try {
	// multipartEntity.consumeContent();
	// multipartEntity = null;
	// httpPost.abort();
	// } catch (UnsupportedOperationException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// return result;
	// }

}

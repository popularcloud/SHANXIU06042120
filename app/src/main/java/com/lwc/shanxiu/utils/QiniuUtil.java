package com.lwc.shanxiu.utils;

import com.qiniu.android.common.AutoZone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.utils.UrlSafeBase64;

import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class QiniuUtil {

	private static final String AccessKey = "QxKsmAvG635jWNx9JJXcW6yVjHTVmpDgjx9huvyC";
	private static final String SecretKey = "brqRHLOLB26VBV0vP7F0wwgXSqtFwFTfUH1nBgky";

	private static final String MAC_NAME = "HmacSHA1";
	private static final String ENCODING = "UTF-8";
	private static UploadManager uploadManager = null;
	public static UploadManager getUploadManager() {
//		Zone z1 = new AutoZone("", null);
		Configuration config = new Configuration.Builder()
//				.chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
//				.putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
				.connectTimeout(20)           // 链接超时。默认10秒
//				.useHttps(false)               // 是否使用https上传域名
				.responseTimeout(60)          // 服务器响应超时。默认60秒
//				.recorder(recorder)           // recorder分片上传时，已上传片记录器。默认null
//				.recorder(recorder, keyGen)   // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
				.zone(AutoZone.autoZone)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
				.build();
// 重用uploadManager。一般地，只需要创建一个uploadManager对象
		if (uploadManager == null) {
			uploadManager = new UploadManager(config);
		}
		return uploadManager;
	}

	public static String getTokey() {
		// 1 构造上传策略
		try {
			JSONObject _json = new JSONObject();
			long now = System.currentTimeMillis();

		    long _dataline = now / 1000 + 60;
			_json.put("deadline", _dataline);// 有效时间为一个小时
			_json.put("scope", "miuxiu");
			String _encodedPutPolicy = UrlSafeBase64.encodeToString(_json
					.toString().getBytes());
			byte[] _sign = HmacSHA1Encrypt(_encodedPutPolicy, SecretKey);
			String _encodedSign = UrlSafeBase64.encodeToString(_sign);
			String _uploadToken = AccessKey + ':' + _encodedSign + ':'
					+ _encodedPutPolicy;
			return _uploadToken;
		} catch (JSONException e) {
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 *
	 * 这个签名方法找了半天 一个个对出来的、、、、程序猿辛苦啊、、、 使用 HMAC-SHA1 签名方法对对encryptText进行签名
	 *
	 * @param encryptText
	 *            被签名的字符串
	 * @param encryptKey
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey)
			throws Exception {
		byte[] data = encryptKey.getBytes(ENCODING);
		// 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
		SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
		// 生成一个指定 Mac 算法 的 Mac 对象
		Mac mac = Mac.getInstance(MAC_NAME);
		// 用给定密钥初始化 Mac 对象
		mac.init(secretKey);
		byte[] text = encryptText.getBytes(ENCODING);
		// 完成 Mac 操作
		return mac.doFinal(text);
	}
}
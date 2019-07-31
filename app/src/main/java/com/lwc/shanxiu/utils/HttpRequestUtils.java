package com.lwc.shanxiu.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.RedPacketActivity;
import com.lwc.shanxiu.bean.ActivityBean;
import com.lwc.shanxiu.bean.Common;
import com.lwc.shanxiu.controler.http.NetManager;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.map.Utils;
import com.lwc.shanxiu.module.user.LoginOrRegistActivity;
import com.lwc.shanxiu.widget.CustomDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * 网络请求的工具类 1.请求方式 2.请求参数 3.response结果码 4.response message(信息) 5.response data
 * (数据)
 */
public class HttpRequestUtils {

	private static CustomDialog customDialog;

	/**
	 * 使用map传参
	 * @param context
	 *            上下文(Activity)
	 * @param URL
	 *            请求的网络地址
	 * @param map
	 *            请求的参数(没有就传null)
	 * @param RequestMethod
	 *            请求的方式(GET,PUT,POST三种)
	 * @param tag
	 *            实现网络请求的方法名称
	 */
	public static void httpRequest(final Activity context, final String tag,
								   final String URL, final Map<String, String> map, final String RequestMethod,
								   final ResponseListener listen) {
		if(!NetUtil.isNetworkAvailable(context)){
			try {
				listen.returnException(new Exception(), "网络不可用，请检查手机网络");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ;
		}
		String token = SharedPreferencesUtils.getInstance(context).loadString("token");
		if (token == null) {
			token = "";
		}
		String DEVICE_ID = Utils.getDeviceId(context);
		LLog.iNetSucceed("方法:" + tag+",URL:"+URL+",方式："+RequestMethod+",参数："+map);
		try {
			// 1.首先判断请求的URL是否为空
			if (TextUtils.isEmpty(URL)) {
				return;
			}
			String url = NetManager.getUrl(URL.replaceAll(" ", "%20"));
			//LLog.iNetSucceed("token:" + token+",deviceId:"+DEVICE_ID);
//			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//				url = url.replace("https://", "http://");
//			}
//					context.startService(new Intent(context, NetWorkService.class));
			// 2.判断请求的方式
			if (RequestMethod == "GET") {
				OkHttpUtils.get().url(url).params(map).addHeader("token", token).addHeader("code", DEVICE_ID).addHeader("phoneSystem", "ANDROID").addHeader("versionCode", SystemUtil.getCurrentVersionCode()+"").build().execute(new StringCallback() {
					@Override
					public void onError(Call call, Exception e, int id) {
						LLog.eNetError(e.toString());
						if (e.toString().toLowerCase().contains("timeout")) {
							listen.returnException(e, "请求超时，请稍后重试！");
						} else if(e.toString().contains("request failed") && e.toString().contains("code is : 404")) {
							listen.returnException(e, "请求失败，接口不存在！");
						} else {
							listen.returnException(e, "请求失败，请稍后重试！");
						}
					}
					@Override
					public void onResponse(String response, int id) {
						Common common = JsonUtil.parserGsonToObject(response, Common.class);
						if (common.getStatus().equals("2") || common.getInfo().contains("令牌错误")) {
							if (customDialog != null && customDialog.isShowing()) {
								customDialog.dismiss();
							}
							customDialog = DialogUtil.showUpdateAppDg(context, "温馨提示","重新登录", "您的登录信息已失效，请重新登录！", new CustomDialog.OnClickListener() {

								@Override
								public void onClick(CustomDialog dialog, int id, Object object) {
									dialog.dismiss();
//									SharedPreferencesUtils.getInstance(context).deleteAllData();
									SharedPreferencesUtils.getInstance(context).saveString("token","");
									IntentUtil.gotoActivityAndFinish(context, LoginOrRegistActivity.class);
								}
							});
						}else if (common.getStatus().equals("3")) {
							if (customDialog != null && customDialog.isShowing()) {
								customDialog.dismiss();
							}
							customDialog = DialogUtil.showUpdateAppDg(context, "温馨提示","确定", "您的账号已被停用，如有疑问，请联系客服！", new CustomDialog.OnClickListener() {

								@Override
								public void onClick(CustomDialog dialog, int id, Object object) {
									dialog.dismiss();
									SharedPreferencesUtils.getInstance(context).saveString("token","");
									IntentUtil.gotoActivityAndFinish(context, LoginOrRegistActivity.class);
								}
							});
						} else {
							if (common.getStatus().equals("1")) {
								List<ActivityBean> list = DataSupport.findAll(ActivityBean.class);
								if (list != null && list.size() > 0) {
									for (int i=0; i<list.size(); i++) {
										ActivityBean ab = list.get(i);
										if (ab.getConditionIndex().replace("/","").equals(URL.replace("/",""))) {
											gotoRedPacket(ab, context);
											return;
										}
									}
								}
								listen.getResponseData(response);
							} else {
								listen.getResponseData(response);
							}
						}
						LLog.iNetSucceed("方法:" + tag + ",结果：" + response);
					}
				});
			} else if (RequestMethod == "POST") {
				LLog.iNetSucceed("方法:" + tag);
				OkHttpUtils.post().url(url).params(map).addHeader("token", token).addHeader("code", DEVICE_ID).addHeader("phoneSystem", "ANDROID").addHeader("versionCode", SystemUtil.getCurrentVersionCode()+"").build().execute(new StringCallback() {
					@Override
					public void onError(Call call, Exception e, int id) {
						LLog.eNetError(e.toString());
						if (e != null && e.getMessage() != null && e.getMessage().toLowerCase().contains("timeout")) {
							listen.returnException(e, "请求超时，请稍后重试！");
						} else {
							listen.returnException(e, "请求失败，请稍后重试！");
						}
					}
					@Override
					public void onResponse(String response, int id) {
						Common common = JsonUtil.parserGsonToObject(response, Common.class);
						if (common.getStatus().equals("2") || common.getInfo().contains("令牌错误")) {
							if (customDialog != null && customDialog.isShowing()) {
								customDialog.dismiss();
							}
							customDialog = DialogUtil.showUpdateAppDg(context, "温馨提示","重新登录", "您的登录信息已失效，请重新登录！", new CustomDialog.OnClickListener() {

								@Override
								public void onClick(CustomDialog dialog, int id, Object object) {
									dialog.dismiss();
//									SharedPreferencesUtils.getInstance(context).deleteAllData();
									SharedPreferencesUtils.getInstance(context).saveString("token","");
									IntentUtil.gotoActivityAndFinish(context, LoginOrRegistActivity.class);
								}
							});
						} else if (common.getStatus().equals("3")) {
							if (customDialog != null && customDialog.isShowing()) {
								customDialog.dismiss();
							}
							customDialog = DialogUtil.showUpdateAppDg(context, "温馨提示","确定", "您的账号已被停用，如有疑问，请联系客服！", new CustomDialog.OnClickListener() {

								@Override
								public void onClick(CustomDialog dialog, int id, Object object) {
									dialog.dismiss();
									SharedPreferencesUtils.getInstance(context).saveString("token","");
									IntentUtil.gotoActivityAndFinish(context, LoginOrRegistActivity.class);
								}
							});
						} else {
							if (common.getStatus().equals("1")) {
								List<ActivityBean> list = DataSupport.findAll(ActivityBean.class);
								if (list != null && list.size() > 0) {
									for (int i=0; i<list.size(); i++) {
										ActivityBean ab = list.get(i);
										if (URL.equals(RequestValue.FINISH_ORDER)) {
											if (ab.getConditionIndex().replace("/","").equals(URL.replace("/","")) && map.get("hasAward").equals("1")) {
												gotoRedPacket(ab, context);
												return;
											}
										} else if (ab.getConditionIndex().replace("/","").equals(URL.replace("/",""))) {
											gotoRedPacket(ab, context);
											return;
										}
									}
								}
								listen.getResponseData(response);
							} else {
								listen.getResponseData(response);
							}
							LLog.iNetSucceed("方法:" + tag + ",结果：" + response);
						}
					}
				});
			} else if (RequestMethod == "PUT") {
			} else if (RequestMethod == "DELETE") {
			} else {
				toast(context, "请求方式错误");
				return;
			}
		} catch (final Exception e) {
			listen.returnException(new Exception(), "系统异常，请反馈给开发人员！");
		}
	}
	/**
	 * 新接口使用json格式传参
	 * @param context
	 *            上下文(Activity)
	 * @param URL
	 *            请求的网络地址
	 * @param map
	 *            请求的参数(没有就传null)
	 * @param RequestMethod
	 *            请求的方式(GET,PUT,POST三种)
	 * @param tag
	 *            实现网络请求的方法名称
	 */
	public static void httpRequestJson(final Activity context, final String tag,
									   final String URL, final JSONObject map, final String RequestMethod,
									   final ResponseListener listen) {
		if(!NetUtil.isNetworkAvailable(context)){
			try {
				listen.returnException(new Exception(), "网络不可用，请检查手机网络");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ;
		}
		String token = SharedPreferencesUtils.getInstance(context).loadString("token");
		if (token == null) {
			token = "";
		}
		String DEVICE_ID = Utils.getDeviceId(context);
		LLog.iNetSucceed("方法:" + tag+",URL:"+URL+",方式："+RequestMethod+",参数："+map.toString());
		try {
			// 1.首先判断请求的URL是否为空
			if (TextUtils.isEmpty(URL)) {
				return;
			}
			String url = NetManager.getUrl(URL.replaceAll(" ", "%20"));
			LLog.iNetSucceed("方法:" + tag);
			OkHttpUtils.postString().url(url).content(map.toString()).mediaType(MediaType.parse("application/json")).addHeader("token", token).addHeader("code", DEVICE_ID).addHeader("phoneSystem", "ANDROID").addHeader("versionCode", SystemUtil.getCurrentVersionCode()+"").build().execute(new StringCallback() {
				@Override
				public void onError(Call call, Exception e, int id) {
					LLog.eNetError(e.toString());
					if (e != null && e.getMessage() != null && e.getMessage().toLowerCase().contains("timeout")) {
						listen.returnException(e, "请求超时，请稍后重试！");
					} else {
						listen.returnException(e, "请求失败，请稍后重试！");
					}
				}
				@Override
				public void onResponse(String response, int id) {
					Common common = JsonUtil.parserGsonToObject(response, Common.class);
					if (common.getStatus().equals("2") || common.getInfo().contains("令牌错误")) {
						if (customDialog != null && customDialog.isShowing()) {
							customDialog.dismiss();
						}
						customDialog = DialogUtil.showUpdateAppDg(context, "温馨提示","重新登录", "您的登录信息已失效，请重新登录！", new CustomDialog.OnClickListener() {

							@Override
							public void onClick(CustomDialog dialog, int id, Object object) {
								dialog.dismiss();
//									SharedPreferencesUtils.getInstance(context).deleteAllData();
								SharedPreferencesUtils.getInstance(context).saveString("token","");
								IntentUtil.gotoActivityAndFinish(context, LoginOrRegistActivity.class);
							}
						});
					} else if (common.getStatus().equals("3")) {
						if (customDialog != null && customDialog.isShowing()) {
							customDialog.dismiss();
						}
						customDialog = DialogUtil.showUpdateAppDg(context, "温馨提示","确定", "您的账号已被停用，如有疑问，请联系客服！", new CustomDialog.OnClickListener() {

							@Override
							public void onClick(CustomDialog dialog, int id, Object object) {
								dialog.dismiss();
								SharedPreferencesUtils.getInstance(context).saveString("token","");
								IntentUtil.gotoActivityAndFinish(context, LoginOrRegistActivity.class);
							}
						});
					} else {
						if (common.getStatus().equals("1")) {
							List<ActivityBean> list = DataSupport.findAll(ActivityBean.class);
							if (list != null && list.size() > 0) {
								for (int i=0; i<list.size(); i++) {
									ActivityBean ab = list.get(i);
									if (URL.equals(RequestValue.FINISH_ORDER)) {
										if (ab.getConditionIndex().replace("/","").equals(URL.replace("/","")) && map.optString("hasAward").equals("1")) {
											gotoRedPacket(ab, context);
											return;
										}
									} else if (ab.getConditionIndex().replace("/","").equals(URL.replace("/",""))) {
										gotoRedPacket(ab, context);
										return;
									}
								}
							}
							listen.getResponseData(response);
						} else {
							listen.getResponseData(response);
						}
						LLog.iNetSucceed("方法:" + tag + ",结果：" + response);
					}
				}
			});
		} catch (final Exception e) {
			listen.returnException(new Exception(), "系统异常，请反馈给开发人员！");
		}
	}

	private static void gotoRedPacket(ActivityBean ab, Activity context) {
		Bundle bundle = new Bundle();
		bundle.putSerializable("activityBean", ab);
		IntentUtil.gotoActivityForResult(context, RedPacketActivity.class, bundle, 10003);
		context.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
	}

	private static final String KEY_STORE_TYPE_BKS = "bks";//证书类型 固定值
    private static final String KEY_STORE_TYPE_P12 = "PKCS12";//证书类型 固定值

    private static final String KEY_STORE_CLIENT_PATH = "client.p12";//客户端要给服务器端认证的证书
    private static final String KEY_STORE_TRUST_PATH = "client.truststore";//客户端验证服务器端的证书库
    private static final String KEY_STORE_PASSWORD = "123456";// 客户端证书密码
    private static final String KEY_STORE_TRUST_PASSWORD = "123456";//客户端证书库密码

    /**
     * 获取SslSocketFactory
     *
     * @param context 上下文
     * @return SSLSocketFactory
     */
    public static SSLSocketFactory getSslSocketFactory(Context context) {
        try {
            // 服务器端需要验证的客户端证书
            KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE_P12);
            // 客户端信任的服务器端证书
            KeyStore trustStore = KeyStore.getInstance(KEY_STORE_TYPE_BKS);

            InputStream ksIn = context.getResources().getAssets().open(KEY_STORE_CLIENT_PATH);
            InputStream tsIn = context.getResources().getAssets().open(KEY_STORE_TRUST_PATH);
            try {
                keyStore.load(ksIn, KEY_STORE_PASSWORD.toCharArray());
                trustStore.load(tsIn, KEY_STORE_TRUST_PASSWORD.toCharArray());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    ksIn.close();
                } catch (Exception ignore) {
                }
                try {
                    tsIn.close();
                } catch (Exception ignore) {
                }
            }
            return new SSLSocketFactory(keyStore, KEY_STORE_PASSWORD, trustStore);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
        return null;
    }

//	public static HttpClient getNewHttpClient(Context context) {
//
//		try {
//            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            trustStore.load(null, null);
//
//            EasySSLSocketFactory sf = new EasySSLSocketFactory(trustStore);
//            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//            //TODO ssl验证
////			HttpClient httpsClient = new DefaultHttpClient();
////            SSLSocketFactory sslSocketFactory = getSslSocketFactory(context);
////            if (sslSocketFactory != null) {
////                Scheme sch = new Scheme("https", sslSocketFactory, 443);
////                httpsClient.getConnectionManager().getSchemeRegistry().register(sch);
////            }
//            HttpParams params = new BasicHttpParams();
//            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
//            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
//
//            SchemeRegistry registry = new SchemeRegistry();
//            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
//            registry.register(new Scheme("https", sf, 443));
//
//            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
//
//            return new DefaultHttpClient(ccm, params);
////            return httpsClient;
//        } catch (Exception e) {
//            return new DefaultHttpClient();
//        }
//	}
//
//	public static void ehyHttpRequest(final Activity context, final String tag,
//			final String URL, final JSONObject RequestParams,
//			final boolean isToken, final String RequestMethod,
//			final ResponseListener listen) {
//		if(!NetworkUtil.isNetworkAvailable(context)){
//			listen.returnException(new Exception());
//			Toast.makeText(context, "网络不可用，请检查网络", 0).show();
//			return ;
//		}
//
//		new Thread() {
//			private HttpRequestBase httpRequest;
//
//			public void run() {
//				try {
//					String token = context.getSharedPreferences("account", 0)
//							.getString("ehyToken", "");
//					// 1.首先判断请求的URL是否为空
//					if (TextUtils.isEmpty(URL)) {
//						return;
//					}
//					// 2.判断请求的方式
//					if (RequestMethod == "GET") {
//						httpRequest = new HttpGet(URL);
//					} else if (RequestMethod == "POST") {
//						httpRequest = new HttpPost(URL);
//					} else if (RequestMethod == "PUT") {
//						httpRequest = new HttpPut(URL);
//					} else if (RequestMethod == "DELETE") {
//						httpRequest = new HttpDelete(URL);
//					} else {
//						toast(context, "请求方式错误");
//						return;
//					}
//					if (isToken) {
//						httpRequest.addHeader("Authorization", token);
//					}
//					if (RequestParams != null) {
//						((HttpEntityEnclosingRequestBase) httpRequest)
//								.setEntity(new StringEntity(RequestParams
//										.toString(), "UTF-8"));
//						Log.d("test",
//								" Requestparam = " + RequestParams.toString());
//					}
//					HttpClient client = new DefaultHttpClient();
//					if(URL.contains("https://")) {
//						client = getNewHttpClient(context);
//					}
//					HttpConnectionParams.setConnectionTimeout(
//							client.getParams(), 5000);
//					HttpConnectionParams.setSoTimeout(client.getParams(), 5000);
//					HttpResponse httpResponse = client.execute(httpRequest);
//					HttpEntity entity = httpResponse.getEntity();
//
//					final String result = EntityUtils.toString(entity);
//					Log.d("test", "RequestResult = " + result);
//					final JSONObject o = new JSONObject(result);
//					final String code = o.getString("code");
//					final String msg = o.optString("msg", "系统异常，请稍候重试");
//					if (!code.equals("200")) {
//						String FRequestArgs = "";
//						if (RequestParams != null) {
//							FRequestArgs = RequestParams.toString();
//						}
//						MyLog.e(context, "test", result, URL, FRequestArgs);
//					}
//					ThreadUtils.runOnUiThread(new Runnable() {
//						@Override
//						public void run() {
//							// 刷新UI显示数据
//							try {
//								listen.getResponseData(code, msg, o, tag);
//							} catch (Exception e) {
//								// 刷新UI显示数据
//								listen.returnException(e);
//								String FRequestArgs = "";
//								if (RequestParams != null) {
//									FRequestArgs = RequestParams.toString();
//								}
//								MyLog.e(context, "test", result+"--"+e.getMessage(), URL, FRequestArgs);
//							}
//						}
//					});
//				} catch (final Exception e) {
//					context.runOnUiThread(new Runnable() {
//						@Override
//						public void run() {
//							// 刷新UI显示数据
//							listen.returnException(e);
//							String FRequestArgs = "";
//							if (RequestParams != null) {
//								FRequestArgs = RequestParams.toString();
//							}
//							MyLog.e(context, "test", e.getMessage(), URL, FRequestArgs);
//						}
//					});
//				}
//
//			};
//		}.start();
//	}

	public interface ResponseListener {
		/**
		 * 
		 * @param response
		 *            返回的JSONObject数据
		 */
		public void getResponseData(String response);

		public void returnException(Exception e, String msg);
	}

	protected static void toast(final Activity context, final String id) {
		context.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
			}
		});
	}

	/**
	 * 网络请求的错误状态码对应msg的显示
	 */
//	public static boolean isSuccessCode(final Activity context, String code) {
//		boolean isSucess = false;
//		int state = Integer.parseInt(code);
//		switch (state) {
//		case 100:
//			isSucess = true;
//			break;
//		case 101:// "101": "操作失败",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "服务器异常，请稍候重试!", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 188:
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "网络不可用，请检查手机网络!", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 102:// "102": "您尚未登录，请登录后重试",
//			isSucess = false;
//			ThreadUtils.runOnUiThread(new Runnable() {
//
//				@Override
//				public void run() {
//					if(dialog!=null && dialog.isShowing()){
//						dialog.dismiss();
//					}
//					dialog = ToastUtils.showDialogMsgQd(context,
//							"登录账号信息已过期！\n去重新登录", new View.OnClickListener() {
//
//								@Override
//								public void onClick(View v) {
//									dialog.dismiss();
//									SharedPreferences sp = context.getSharedPreferences("account", 0);
//									sp.edit().putString("FType", "0").commit();
////									sp.edit().putString("userID", "").commit();
//									sp.edit().putString("token", "").commit();
//									sp.edit().putString("FName", "").commit();
//									sp.edit().putString("FCompany", "").commit();
//									sp.edit().putBoolean("isOpenHx", false).commit();
//									sp.edit().putBoolean("isSIGNHx", false).commit();
//									SharedPreferences sp2 = context.getSharedPreferences("logistics", Context.MODE_PRIVATE);
//									Editor editor = sp2.edit();
//									editor.putString("access_token", "");
//									editor.putString("refresh_token", "");
//									editor.putString("token_type", "");
//									editor.putString("expires_in", "");
//									editor.putString("id", "");
//									editor.commit();
////									DemoHelper.getInstance().logout(true, null);
//									context.startActivity(new Intent(context,
//											LoginActivity2.class));
//									if (context instanceof MainActivity || context instanceof MyActivity||context instanceof BuyCarActivity||context instanceof MoreCarSourceActivity||context instanceof CarsourceActivity) {
//										return;
//									}
//									context.finish();
//
//								}
//							});
//				}
//			});
//			break;
//		case 201:// "201": "用户尚未注册",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "用户尚未注册", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 202:// "202": "该用户已绑定",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "该用户已绑定", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 203:// "203": "用户还有在进行中的业务",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "用户还有在进行中的业务", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 204:// "204": "最多只可同时启用三个用户",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "最多只可同时启用三个用户", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 502:// "502": "参数缺失",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "参数缺失", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 503:// "503": "无效的手机号",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "无效的手机号", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 504:// "504": "无效的验证码",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "无效的验证码", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 505:// "505": "该手机号已注册",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "该手机号已注册", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 506:// "506": "无效的手机号或密码",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "无效的手机号或密码", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 507:// "507": "该手机号尚未注册",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "该手机号尚未注册", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 508:// "508": "请求过于频繁",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "请求过于频繁", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 509:// "509": "车辆信息不存在",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "车辆信息不存在", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 510:// "510": "数据库操作异常",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "数据库操作异常", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 511:// "511": "车源信息获取失败，请刷新后再试",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "车源信息获取失败，请刷新后再试", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 512:// "512": "无可用的优惠卷",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "无可用的优惠卷", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 513:// "513": "认证类型错误",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "认证类型错误", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 601:// "601": "数据不存在",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "数据不存在", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 602:// "602": "数据创建失败",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "数据创建失败", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 603:// "603": "数据更新失败",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "数据更新失败", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 604:// "604": "签名错误",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "签名错误", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 605:// "605": "数据已经存在",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "数据已经存在", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 701:// "701": "支付方法错误",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "支付方法错误", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 702:// "702": "支付金额错误",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "支付金额错误", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 703:// "703": "支付预下单错误",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "支付预下单错误", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 704:// "704": "问题或答案错误",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "问题或答案错误", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 705:// "705": "支付密码未设置",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "支付密码未设置", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 706:// "706": "未绑定提现帐号",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "未绑定提现帐号", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 707:// "707": "钱包余额不足",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "钱包余额不足", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
////		case 708:// "708": "支付密码错误",
////			isSucess = false;
////			ToastUtils.showDialogMsg(context, "支付密码错误", new OnDismissListener() {
////
////				@Override
////				public void onDismiss(DialogInterface dialog) {
////				}
////			});
////			break;
//		case 709:// "709": "提现帐号错误",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "提现帐号错误", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 710:// "710": "支付帐号已绑定",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "支付帐号已绑定", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 801:// "801": "寻车信息不存在",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "寻车信息不存在", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 802:// "802": "寻车信息不能更新",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "寻车信息不能更新", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 803:// "803": "寻车信息不能撤销",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "交易中的寻车不能取消", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 901:// "901": "报价不能超过3次编辑",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "报价不能超过3次编辑", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 902:// "902": "禁止报价",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "禁止报价", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 903:// "903": "竞价不存在",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "竞价不存在", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 904:// "904": "报价不能高于前次报价",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "报价不能高于前次报价", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 905:// "905":"报价已存在",
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "报价已存在", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 1001:// "1001": "参数错误"
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "参数错误", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 1006:// 1006-超过刷新限制（每天一次）
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "今日刷新次数已达上限！", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 1011:// 1011 发布车源的次数限制
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "车源发布次数已达上限", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 1010:// 1010 发布寻车的次数限制
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "寻车发布次数已达上限", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		case 1009:// 1009 自由信息的次数限制
//			isSucess = false;
//			ToastUtils.showDialogMsg(context, "自由信息发布次数已达上限", new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//				}
//			});
//			break;
//		default:
//			break;
//		}
//		return isSucess;
//	}
}

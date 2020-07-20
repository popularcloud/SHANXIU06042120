package com.lwc.shanxiu.module.lease_parts.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.activity.BaseActivity;
import com.lwc.shanxiu.controler.http.RequestValue;
import com.lwc.shanxiu.utils.HttpRequestUtils;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;
import com.lwc.shanxiu.view.TileButton;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

public class ShareType2Activity extends BaseActivity implements OnClickListener {
	private RelativeLayout layoutShare;
	private TileButton qqShare;// QQ分享
	private TileButton qZoneShare;// 空间分享
	private TileButton weiXinShare;// 微信分享
	private TileButton wxcircleShare;// 朋友圈分享
	private ShareAction shareAction;
	private String shareContent;
	private String shareTitle;
	private String FLink;
	private String urlPhoto;
	private Drawable drawable;
	private LinearLayout ll_qq;
	private LinearLayout ll_qzone;

	public static boolean isOrderShare = false;
	private String orderId;

	@Override
	protected int getContentViewId(Bundle savedInstanceState) {
		return R.layout.activity_share_type2;
	}

	@Override
	protected void findViews() {
		ll_qq = (LinearLayout) findViewById(R.id.ll_qq);
		ll_qzone = (LinearLayout) findViewById(R.id.ll_qzone);
		layoutShare = (RelativeLayout) findViewById(R.id.layout_share);
		qqShare = (TileButton) findViewById(R.id.img_qq);
		qZoneShare = (TileButton) findViewById(R.id.img_qzone);
		weiXinShare = (TileButton) findViewById(R.id.img_weixin);
		wxcircleShare = (TileButton) findViewById(R.id.img_wxcircle);
		layoutShare.setOnClickListener(this);
		qqShare.setOnClickListener(this);
		qZoneShare.setOnClickListener(this);
		weiXinShare.setOnClickListener(this);
		wxcircleShare.setOnClickListener(this);
	}

	@Override
	protected void init() {
	}

	@Override
	protected void initGetData() {
		shareContent = getIntent().getStringExtra("shareContent");
		shareTitle = getIntent().getStringExtra("shareTitle");
		FLink = getIntent().getStringExtra("FLink");
		urlPhoto = getIntent().getStringExtra("urlPhoto");
		String goneQQ = getIntent().getStringExtra("goneQQ");
		String sharePic = getIntent().getStringExtra("sharePic");

		orderId = getIntent().getStringExtra("orderId");
		if (!TextUtils.isEmpty(goneQQ) && goneQQ.equals("true")){
			ll_qq.setVisibility(View.GONE);
			ll_qzone.setVisibility(View.GONE);
		}
		if (TextUtils.isEmpty(shareContent)) {
			shareContent = "密修，让维修更简单！";
		}
		if (TextUtils.isEmpty(shareTitle)) {
			shareTitle = "密修一下 维修到家";
		}
		if (TextUtils.isEmpty(FLink)) {
			FLink = "http://www.lsh-sd.com:9999/download.jsp";
		}
		if (!TextUtils.isEmpty(urlPhoto)){
			RequestOptions mRequestOptions = RequestOptions.fitCenterTransform()
					.error(R.drawable.share_logo)
					.placeholder(R.drawable.share_logo)
					.diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
					.skipMemoryCache(true);//不做内存缓存
			Glide.with(this).load(urlPhoto)
					.into(new SimpleTarget<Drawable>() {
						@Override
						public void onResourceReady(Drawable resource, Transition<? super Drawable> glideAnimation) {
							drawable = resource;
						}
					});
		}
	}

	@Override
	protected void widgetListener() {
	}
	String type="";
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.layout_share:
			// 点击屏幕的空白位置退出当前的activity
			finish();
			break;
		case R.id.img_qq:
			// 点击进行QQ分享
			type = "1";
			sharedToQQ();
			break;
		case R.id.img_qzone:
			// 点击进行空间的分享
			type = "2";
			sharedToQZone();
			break;
		case R.id.img_weixin:
			// 点击进行微信的分享
			type = "3";
			sharedToWeiXin();
			break;
		case R.id.img_wxcircle:
			// 点击进行朋友圈的分享
			type = "4";
			sharedToWinXinCircle();
			break;

		default:
			break;
		}
	}

	private void sharedToQQ() {
		// 添加QQ在分享列表页中
		new ShareAction(ShareType2Activity.this)
				.withMedia(getWeb()).setPlatform(SHARE_MEDIA.QQ)
				.setCallback(umShareListener).share();
	}

	private UMWeb getWeb() {
//		if (!TextUtils.isEmpty(urlPhoto) && type.equals("4")){
//			FLink = FLink+"from=timeline&isappinstalled=0";
//		} else if (!TextUtils.isEmpty(urlPhoto) && type.equals("3")){
//			FLink = FLink+"from=singlemessage&isappinstalled=0";
//		}
//		else if (!TextUtils.isEmpty(urlPhoto) && type.equals("2")){
//			FLink = FLink+"from=groupmessage&isappinstalled=0";
//		} else if (!TextUtils.isEmpty(urlPhoto) && type.equals("1")){
//			FLink = FLink+"from=groupmessage&isappinstalled=0";
//		}

		if(isOrderShare){
			FLink = FLink + "?cover="+urlPhoto;
		}

		UMWeb web = new UMWeb(FLink);
		web.setTitle(shareTitle);//标题
		if (TextUtils.isEmpty(urlPhoto) || drawable == null) {
			web.setThumb(new UMImage(this, R.drawable.share_logo));  //缩略图
		} else {
			web.setThumb(new UMImage(this, drawableToBitmap(drawable)));
		}
		web.setDescription(shareContent);//描述
		return web;
	}
	public static Bitmap drawableToBitmap(Drawable drawable) {

		Bitmap bitmap = Bitmap.createBitmap(
				drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight(),
				drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
						: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		//canvas.setBitmap(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}
	private void sharedToQZone() {
		// 添加QQ在分享列表页中
		//创建一个分享内容的对象
		shareAction = new ShareAction(ShareType2Activity.this)
				.withMedia(getWeb()).setPlatform(SHARE_MEDIA.QZONE)
				.setCallback(umShareListener);
		shareAction.share();
	}



	private void sharedToWeiXin() {
		// 添加QQ在分享列表页中
		shareAction = new ShareAction(ShareType2Activity.this)
				.withMedia(getWeb()).setPlatform(SHARE_MEDIA.WEIXIN)
				.setCallback(umShareListener);
		shareAction.share();

	}
	private void sharedToWinXinCircle() {
		// 添加QQ在分享列表页中
		//创建一个分享内容的对象
		shareAction = new ShareAction(ShareType2Activity.this)
				.withMedia(getWeb()).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
				.setCallback(umShareListener);
		shareAction.share();
	}

	private UMShareListener umShareListener = new UMShareListener() {
		@Override
		public void onStart(SHARE_MEDIA share_media) {

		}

		@Override
		public void onResult(SHARE_MEDIA platform) {
			if (!TextUtils.isEmpty(urlPhoto) && !TextUtils.isEmpty(FLink)){
				String id = FLink.substring(FLink.indexOf("valId=")+6, FLink.lastIndexOf("?"));
				shareInformation(id);
			}
			ToastUtil.showLongToast(ShareType2Activity.this, platform + " 分享成功啦");
			if(shareAction != null){
				shareAction.close();
			}
			if(isOrderShare){
				isOrderShare = false;
				onOrderShared();
			}
		}

		@Override
		public void onError(SHARE_MEDIA platform, Throwable t) {
			ToastUtil.showLongToast(ShareType2Activity.this,platform + " 分享失败啦");
		}

		@Override
		public void onCancel(SHARE_MEDIA platform) {
			ToastUtil.showLongToast(ShareType2Activity.this,platform + " 分享取消了");
		}
	};

	private void shareInformation(String id) {
		String activityId = SharedPreferencesUtils.getInstance(this).loadString("activityId");
		if(TextUtils.isEmpty(activityId) && !TextUtils.isEmpty(id)) {
			activityId = id;
		}
		if (TextUtils.isEmpty(activityId)){
			return;
		}
		HttpRequestUtils.httpRequest(this, "shareInformation 红包分享统计", RequestValue.GET_INFORMATION_SHARE+activityId, null, "GET", new HttpRequestUtils.ResponseListener() {
			@Override
			public void getResponseData(String response) {
				finish();
			}

			@Override
			public void returnException(Exception e, String msg) {
			}
		});
	}

	/**
	 * 订单分享成功后告诉后台
	 */
	private void onOrderShared() {
		/*HttpRequestUtils.httpRequest(this, "订单分享成功!", RequestValue.GET_ORDER_ORDERSHARE+"?orderId="+orderId, null, "GET", new HttpRequestUtils.ResponseListener() {
			@Override
			public void getResponseData(String response) {
				//finish();
				//ToastUtil.showToast(ShareActivity.this,"订单分享提交成功了！");
				String isShare = (String) SharedPreferencesUtils.getParam(ShareType2Activity.this,"isShare","0");
				int settlementStatus = (int) SharedPreferencesUtils.getParam(ShareType2Activity.this,"settlementStatus",1);
				if(settlementStatus == 2 && "0".equals(isShare)){
					SharedPreferencesUtils.setParam(ShareType2Activity.this,"isShare","1");
					checkedActivity();
				}


			}

			@Override
			public void returnException(Exception e, String msg) {

			}
		});*/
	}

	/**
	 * 检查是否有活动
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
			UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

	}

	//	@Override
	public void finish() {  
	    super.finish();  
//	    //关闭窗体动画显示  
	    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);  
	}
}

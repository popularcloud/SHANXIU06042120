package com.lwc.shanxiu.module.setting;

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
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
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

import java.io.File;

public class ShareActivity extends BaseActivity implements OnClickListener {
	private RelativeLayout layoutShare;
	private TileButton qqShare;// QQ分享
	private TileButton qZoneShare;// 空间分享
	private TileButton weiXinShare;// 微信分享
	private TileButton wxcircleShare;// 朋友圈分享
	private ShareAction shareAction = new ShareAction(this);
	private String shareContent;
	private String shareTitle;
	private String FLink;
	private String urlPhoto;
	private Drawable drawable;
	private LinearLayout ll_qq;
	private LinearLayout ll_qzone;
	private int shareType;
	@Override
	protected int getContentViewId(Bundle savedInstanceState) {
		return R.layout.activity_share;
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
		shareType = getIntent().getIntExtra("type", 0);
		String goneQQ = getIntent().getStringExtra("goneQQ");
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
		if (!TextUtils.isEmpty(urlPhoto) && shareType != 3){
			Glide.with(this).load(urlPhoto).
					placeholder(R.drawable.share_logo)
					.error(R.drawable.share_logo)
					.into(new SimpleTarget<GlideDrawable>() {
						@Override
						public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
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
			case R.id.text_cancle:
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
		new ShareAction(ShareActivity.this)
				.withMedia(getWeb()).setPlatform(SHARE_MEDIA.QQ)
				.setCallback(umShareListener).share();

	}

	private UMWeb getWeb() {
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
		new ShareAction(ShareActivity.this)
				.withMedia(getWeb()).setPlatform(SHARE_MEDIA.QZONE)
				.setCallback(umShareListener).share();

	}

	private void sharedToWeiXin() {
		if (shareType == 3) {
			UMImage image = new UMImage(ShareActivity.this, new File(urlPhoto));//本地文件
			new ShareAction(ShareActivity.this).withMedia(image).setPlatform(SHARE_MEDIA.WEIXIN)
					.setCallback(umShareListener).share();
		} else {
			new ShareAction(ShareActivity.this)
					.withMedia(getWeb()).setPlatform(SHARE_MEDIA.WEIXIN)
					.setCallback(umShareListener).share();
		}
	}
	private void sharedToWinXinCircle() {
		if (shareType == 3) {
			UMImage image = new UMImage(ShareActivity.this, new File(urlPhoto));//本地文件
			new ShareAction(ShareActivity.this).withMedia(image).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
					.setCallback(umShareListener).share();
		} else {
			new ShareAction(ShareActivity.this)
					.withMedia(getWeb()).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
					.setCallback(umShareListener).share();
		}
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
			ToastUtil.showLongToast(ShareActivity.this, platform + " 分享成功啦");
		}

		@Override
		public void onError(SHARE_MEDIA platform, Throwable t) {
			ToastUtil.showLongToast(ShareActivity.this,platform + " 分享失败啦");
		}

		@Override
		public void onCancel(SHARE_MEDIA platform) {
			ToastUtil.showLongToast(ShareActivity.this,platform + " 分享取消了");
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
		HttpRequestUtils.httpRequest(this, "shareInformation 分享统计", RequestValue.GET_INFORMATION_SHARE+activityId, null, "GET", new HttpRequestUtils.ResponseListener() {
			@Override
			public void getResponseData(String response) {
				finish();
			}

			@Override
			public void returnException(Exception e, String msg) {
			}
		});
	}

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

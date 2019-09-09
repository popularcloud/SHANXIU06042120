package com.lwc.shanxiu.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.bean.User;
import com.lwc.shanxiu.module.setting.ShareActivity;
import com.lwc.shanxiu.utils.CreateCodeUtil;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.utils.SDFileHelper;
import com.lwc.shanxiu.utils.SharedPreferencesUtils;
import com.lwc.shanxiu.utils.ToastUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 工程师专属二维码页面
 * 
 * @author 何栋
 * @date 2018年3月9日
 * @Copyright: lwc
 */
public class CodeActivity extends BaseActivity {


	@BindView(R.id.tv_name)
	TextView tv_name;
	@BindView(R.id.iv_code)
	ImageView iv_code;
	@BindView(R.id.tv_desc)
	TextView tv_desc;
	@BindView(R.id.rl_content)
	RelativeLayout rl_content;
	@BindView(R.id.ll_bottom)
	LinearLayout ll_bottom;
	private User user;
	private SharedPreferencesUtils preferencesUtils;
	private String name="";

	@Override
	protected int getContentViewId(Bundle savedInstanceState) {
		return R.layout.activity_code;
	}

	@Override
	protected void findViews() {
		ButterKnife.bind(this);
		setTitle("二维码");
		showBack();
		setRightImg(R.drawable.ic_share, new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String photoUrl = preferencesUtils.loadString("share_photo_url"+user.getUserId());
				if (!TextUtils.isEmpty(photoUrl) && new File(photoUrl).exists()) {
					Bundle bundle = new Bundle();
					bundle.putInt("type", 3);
					bundle.putString("urlPhoto", photoUrl);
					bundle.putString("goneQQ", "true");
					IntentUtil.gotoActivity(CodeActivity.this, ShareActivity.class, bundle);
				} else {
					tv_desc.setText("长按识别二维码，成为专属客户");
					ll_bottom.setVisibility(View.INVISIBLE);
					savePic(3);
				}
			}
		});
	}

	@Override
	protected void init() {
	}

	@Override
	protected void initGetData() {
		preferencesUtils = SharedPreferencesUtils.getInstance(this);
		user = preferencesUtils.loadObjectData(User.class);
		tv_name.setText(user.getMaintenanceName()+"的推广二维码");
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			if (!TextUtils.isEmpty(user.getMaintenanceHeadImage())) {
				name = user.getMaintenanceHeadImage().substring(user.getMaintenanceHeadImage().lastIndexOf("/")+1, user.getMaintenanceHeadImage().length()).replace(".jpg", "");
				String filePath = Environment.getExternalStorageDirectory().getCanonicalPath() + "/mixiu/"+name + ".jpg";
				File dir1 = new File(filePath);
				if (!dir1.exists()) {
					final SDFileHelper helper = new SDFileHelper(this);
					Glide.with(this).load(user.getMaintenanceHeadImage()).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
						@Override
						public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
							try {
								helper.savaFileToSD(name + ".jpg", bytes);
								createCode();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				} else {
					createCode();
				}
			} else {
				createCode();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建二维码
	 * @throws IOException
     */
	private void createCode() throws IOException {
		Bitmap logo = BitmapFactory.decodeResource(super.getResources(), R.drawable.logo_new);
		if (!TextUtils.isEmpty(user.getMaintenanceHeadImage())) {
            String filePath = Environment.getExternalStorageDirectory().getCanonicalPath() + "/mixiu/" + name + ".jpg";
            File dir1 = new File(filePath);
            if (dir1.exists()) {
                FileInputStream fis = new FileInputStream(dir1);
                logo = BitmapFactory.decodeStream(fis);
            }
        }
		Bitmap code = CreateCodeUtil.createQRCode(user.getQrCode(), 1000, logo);
		if (code != null) {
            iv_code.setImageBitmap(code);
        }
	}

	/**
	 * 按钮点击监听
	 * @param view
     */
	@OnClick({R.id.btnSava})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btnSava:
				tv_desc.setText("长按识别二维码，成为专属客户");
				ll_bottom.setVisibility(View.INVISIBLE);
				savePic(1);
				break;
		}
	}

	@Override
	protected void widgetListener() {
	}
	private Handler mHandler = new Handler();

	/**
	 * 保存图片
	 * @param type
     */
	protected void savePic(final int type) {
		if (rl_content == null) {
			return;
		}
		rl_content.setDrawingCacheEnabled(true);
		rl_content.buildDrawingCache();
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				final Bitmap bmp = rl_content.getDrawingCache(); // 获取图片
				if (bmp != null) {
					saveImageToGallery(CodeActivity.this, bmp, type);
				} else {
					ToastUtil.showToast(CodeActivity.this, "图片保存失败！");
				}
				rl_content.destroyDrawingCache(); // 保存过后释放资源
			}
		},1000);
	}

	/**
	 * 保存二维码图片
	 * @param context
	 * @param bmp
	 * @param type
     */
	public void saveImageToGallery(Context context, Bitmap bmp, int type) {
		try {
			// 首先保存图片
			File appDir = new File(Environment.getExternalStorageDirectory().getCanonicalPath() + "/mixiu/");
			if (!appDir.exists()) {
				appDir.mkdir();
			}
			String fileName = user.getUserId()+ "_"+ type + ".jpg";
			File file = new File(appDir, fileName);
			FileOutputStream fos = new FileOutputStream(file);
			bmp.compress(CompressFormat.JPEG, 60, fos);
			fos.flush();
			fos.close();
			if (type==1) {
				// 其次把文件插入到系统图库
				try {
					MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			    // 最后通知图库更新
				LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
				preferencesUtils.saveString("share_photo_url"+user.getUserId(),file.getAbsolutePath());
				ToastUtil.showToast(CodeActivity.this, "图片保存成功！");
				tv_desc.setText("邀请好友扫描二维码 成为专属客户");
				ll_bottom.setVisibility(View.VISIBLE);
			} else if (type == 3) {
				preferencesUtils.saveString("share_photo_url"+user.getUserId(),file.getAbsolutePath());
				Bundle bundle = new Bundle();
				bundle.putInt("type", 3);
				bundle.putString("urlPhoto", file.getAbsolutePath());
				bundle.putString("goneQQ", "true");
				IntentUtil.gotoActivity(this, ShareActivity.class, bundle);
				tv_desc.setText("邀请好友扫描二维码 成为专属客户");
				ll_bottom.setVisibility(View.VISIBLE);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

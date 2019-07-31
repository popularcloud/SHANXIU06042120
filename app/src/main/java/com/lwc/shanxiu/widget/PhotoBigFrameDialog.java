package com.lwc.shanxiu.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.utils.ImageLoaderUtil;
import com.lwc.shanxiu.view.MatrixImageView;

import java.io.File;

/**
 * 作者：何栋
 * 创建于 2016/12/24.
 * 294663966@qq.com
 * 图片放大框
 */

public class PhotoBigFrameDialog extends Dialog {

    private Context context;
    private Activity activity;
    private File file;
    private MatrixImageView imgUrl;
    private ImageLoaderUtil imageLoader;
    private LinearLayout lLayout;

    public PhotoBigFrameDialog(Context context, Activity activity, String path) {
        super(context);
        this.context = context;
        this.activity = activity;
//        //去掉标题栏
        Window win = getWindow();
        win.requestFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialog_photo_big_frame);
        initViews();
        imageLoader.displayFromNetD(context, path, imgUrl);
        imgUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissNoticeDialog();
            }
        });
    }

    public PhotoBigFrameDialog(Context context, File file) {
        super(context);
        this.context = context;
        this.file = file;
        Window win = getWindow();
        win.requestFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialog_photo_big_frame);
        initViews();
    }

    public PhotoBigFrameDialog(Context context, int themeResId) {
        super(context);

        //去掉标题栏
        Window win = getWindow();
        win.requestFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialog_photo_big_frame);
//        initViews();
    }

    private void initViews() {
        imgUrl = (MatrixImageView) findViewById(R.id.imgUrl);
        lLayout = (LinearLayout) findViewById(R.id.lLayout);
        imageLoader = ImageLoaderUtil.getInstance();
    }


    public void setImageView() {
        imageLoader.displayFromFile(context, imgUrl, file);
    }


    /**
     * 显示
     */
    public void showNoticeDialog() {
        if (!isShowing())
            this.show();
    }

    /**
     * 隐藏
     */
    public void dismissNoticeDialog() {
        if (isShowing())
            this.dismiss();
    }
}

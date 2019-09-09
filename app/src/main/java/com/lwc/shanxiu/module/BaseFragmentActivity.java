package com.lwc.shanxiu.module;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.wallet.WalletActivity;
import com.lwc.shanxiu.utils.Constants;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.widget.SystemBarTintManager;


/**
 * activity的base类,用于基本数据的初始化
 *
 */
public abstract class BaseFragmentActivity extends FragmentActivity {

    private boolean isCloseBackKey = false;
    private boolean isOpenDoubleClickToExit = false;
    private long exitTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        PushAgent.getInstance(this).onAppStart();
    /*    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(this, true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintDrawable(getResources().getDrawable(R.drawable.title_bg_new));
        }*/
    }
    /*@TargetApi(19)
    private static void setTranslucentStatus(Activity activity, boolean on) {

        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }*/

    /**
     * 关闭返回键
     */
    public void closeBackKey() {
        this.isCloseBackKey = true;
    }

    /**
     * 打开返回键
     */
    public void openBackkey() {
        this.isCloseBackKey = false;
    }

    /**
     * 关闭二次点击退出功能
     */
    public void closeDoubleClickToExit() {
        this.isOpenDoubleClickToExit = false;
    }

    /**
     * 打开二次点击退出功能
     */
    public void openDoubleClickToExit() {
        this.isOpenDoubleClickToExit = true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isCloseBackKey) {
            if (keyCode == KeyEvent.KEYCODE_BACK)
                return true;
        }

        if (isOpenDoubleClickToExit) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if ((System.currentTimeMillis() - exitTime) > 2000) {

                    Toast.makeText(BaseFragmentActivity.this,
                            R.string.double_click_to_exit_app,
                            Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                    return true;
                } else {
                    //清除用户信息
                    Intent intent = new Intent();
// 为Intent设置Action、Category属性
                    intent.setAction(Intent.ACTION_MAIN);// "android.intent.action.MAIN"
                    intent.addCategory(Intent.CATEGORY_HOME); //"android.intent.category.HOME"
                    startActivity(intent);
                }
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    public void onBack(View v) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.RED_ID_TO_RESULT) {
            IntentUtil.gotoActivityAndFinish(this, WalletActivity.class);
            finish();
        } else if (resultCode == Constants.RED_ID_RESULT){
            finish();
        }
    }
    /**
     * 初始化view
     */
    public abstract void initView();

    /**
     * 初始化引擎
     */
    public abstract void initEngines();

    /**
     * 获取Intent传过来的数据
     */
    public abstract void getIntentData();

}

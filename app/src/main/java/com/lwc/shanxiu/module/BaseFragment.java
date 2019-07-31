package com.lwc.shanxiu.module;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.lwc.shanxiu.utils.Constants;
import com.lwc.shanxiu.utils.IntentUtil;
import com.lwc.shanxiu.module.wallet.WalletActivity;


/**
 * activity的base类,用于基本数据的初始化
 *
 * @author 何栋
 */
public abstract class BaseFragment extends Fragment {

    public TextView txtActionbarTitle;

    protected boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    /**
     * 此方法在Base中已调用，在覆写的faragment中会执行，无需再调用一次
     */
    protected abstract void lazyLoad();

    /**
     * faragment隐藏时调用此方法
     */
    protected void onInvisible() {
    }

    /**
     * 初始化view 和各种相关的数据
     */
    public abstract void init();

    /**
     * 初始化引擎
     */
    public abstract void initEngines(View view);

    /**
     * 获取Intent传过来的数据
     */
    public abstract void getIntentData();

    /**
     * 设置各种监听器
     */
    public abstract void setListener();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.RED_ID_TO_RESULT) {
            IntentUtil.gotoActivityAndFinish(getActivity(), WalletActivity.class);
            getActivity().finish();
        } else if (resultCode == Constants.RED_ID_RESULT){
            getActivity().finish();
        }
    }
}

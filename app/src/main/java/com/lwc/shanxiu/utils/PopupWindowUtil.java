package com.lwc.shanxiu.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.module.lease_parts.activity.LeaseHomeActivity;
import com.lwc.shanxiu.module.lease_parts.activity.MyCollectActivity;
import com.lwc.shanxiu.module.lease_parts.fragment.LeaseShoppingCartFragment;
import com.lwc.shanxiu.module.message.bean.MyMsg;
import com.lwc.shanxiu.module.message.ui.MsgListActivity;
import com.lwc.shanxiu.widget.CommonPopupWindow;


public class PopupWindowUtil {

    public static CommonPopupWindow popupWindow;

    public static void showHeaderPopupWindow(final Context context, View view, final LeaseShoppingCartFragment leaseShoppingCartFragment, final FrameLayout fragment_container, final FragmentManager fragmentManager){
        popupWindow = new CommonPopupWindow.Builder(context)
                //设置PopupWindow布局
                .setView(R.layout.popup_down)
                //设置宽高
                .setWidthAndHeight(DisplayUtil.dip2px(context,130),
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                //.setAnimationStyle(R.style.)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(0.5f)
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                       TextView tvMessage = view.findViewById(R.id.tv_message);
                       TextView tvMain = view.findViewById(R.id.tv_main);
                       TextView tvShopCart = view.findViewById(R.id.tv_shopCart);
                       TextView tvMyCollect = view.findViewById(R.id.tv_myCollect);
                       TextView tv_msg = view.findViewById(R.id.tv_msg);

                        int leaseOrderCount = (int) SharedPreferencesUtils.getParam(context,"leaseOrderCount",0);
                        if(leaseOrderCount > 0){
                            tv_msg.setVisibility(View.VISIBLE);
                            if(leaseOrderCount > 99){
                                tv_msg.setText("...");
                            }else{
                                tv_msg.setText(String.valueOf(leaseOrderCount));
                            }
                        }else{
                            tv_msg.setVisibility(View.GONE);
                        }

                       tvMessage.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               MyMsg msg = new MyMsg();
                               msg.setMessageType("6");
                               msg.setMessageTitle("租赁消息");
                               Bundle bundle = new Bundle();
                               bundle.putSerializable("myMsg", msg);
                               IntentUtil.gotoActivity(context, MsgListActivity.class,bundle);
                               popupWindow.dismiss();
                           }
                       });

                        tvMain.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                IntentUtil.gotoActivity(context, LeaseHomeActivity.class);
                            }
                        });

                        tvShopCart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                popupWindow.dismiss();
                                Bundle bundle = new Bundle();
                                bundle.putInt("isShowBack",1);
                                leaseShoppingCartFragment.setArguments(bundle);
                                fragment_container.setVisibility(View.VISIBLE);
                                fragmentManager   //
                                        .beginTransaction()
                                        .add(R.id.fragment_container,leaseShoppingCartFragment)   // 此处的R.id.fragment_container是要盛放fragment的父容器
                                        .commit();
                            }
                        });

                        tvMyCollect.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                IntentUtil.gotoActivity(context, MyCollectActivity.class);
                            }
                        });

                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();

        popupWindow.showAsDropDown(view,-220,0);
    }
}

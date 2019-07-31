package com.lwc.shanxiu.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lwc.shanxiu.map.LocationService;

import cn.jpush.android.service.PushService;

/**
 * 网络改变监控广播
 * <p>
 * 监听网络的改变状态,只有在用户操作网络连接开关(wifi,mobile)的时候接受广播,
 * 然后对相应的界面进行相应的操作，并将 状态 保存在我们的APP里面
 * <p>
 * <p>
 */
public class NetworkConnectChangedReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, PushService.class);
        context.startService(service);
        Intent locationIntent = new Intent(context, LocationService.class);
        context.startService(locationIntent);
    }

}
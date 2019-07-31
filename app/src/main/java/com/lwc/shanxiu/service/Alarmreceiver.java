package com.lwc.shanxiu.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lwc.shanxiu.map.LocationService;
public class Alarmreceiver extends BroadcastReceiver {

     @Override
     public void onReceive(Context context, Intent intent) {
         // TODO Auto-generated method stub
         try {
//             if (context != null && intent != null && intent.getAction() != null && intent.getAction().equals("arui.alarm.action")) {
                 Intent i = new Intent();
                 i.setClass(context, LocationService.class);
                 // 启动service
                 // 多次调用startService并不会启动多个service 而是会多次调用onStart
                 context.startService(i);
//             }
         } catch (Exception e){}
    }
}
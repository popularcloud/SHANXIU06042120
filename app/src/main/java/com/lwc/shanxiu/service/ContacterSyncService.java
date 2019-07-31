package com.lwc.shanxiu.service;

import com.lwc.shanxiu.utils.ContactsUtil;
import com.lwc.shanxiu.utils.LogUtil;

import android.app.Service;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.ContactsContract;

public class ContacterSyncService extends Service {

	private final static String TAG = "Any.ONE.ContacterSyncService";

	// 延时处理同步联系人，若在延时期间，通话记录数据库未改变，即判断为联系人被改变了
	private final static int ELAPSE_TIME = 1000;

	private Handler mHandler = null;

	public ContentObserver mObserver = new ContentObserver(new Handler()) {

		@Override
		public void onChange(boolean selfChange) {
			// 当系统联系人数据库发生更改时触发此操作

			// 去掉多余的或者重复的同步
			mHandler.removeMessages(0);

			// 延时ELAPSE_TIME(1秒）发送同步信号“0”
			mHandler.sendEmptyMessageDelayed(0, ELAPSE_TIME);
		}

	};

	// 在此处处理联系人被修改后应该执行的操作
	public void updataContact() {
		// DO SOMETHING HERE...
		LogUtil.out("手机联系人有更新 ==========》");
		new ContactsUtil().getPhoneContactList();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// 注册监听联系人数据库
		getContentResolver().registerContentObserver(ContactsContract.Contacts.CONTENT_URI, true, mObserver);

		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					updataContact();
					break;

				default:
					break;
				}
			}
		};
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return flags;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}

package com.lwc.shanxiu.utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.text.TextUtils;

import com.lwc.shanxiu.bean.PhoneContactBean;
import com.lwc.shanxiu.configs.TApplication;
import com.lwc.shanxiu.executors.DataBaseExecutor;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 手机联系人工具类
 * 
 * @Description TODO
 * @author 何栋
 * @version 1.0
 * @date 2013-12-13
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 * 
 */
public class ContactsUtil {

	public void getPhoneContactList() {
		DataBaseExecutor.addTask(new Runnable() {

			@Override
			public void run() {
				getPhoneContacts();
			}
		});
	}

	// ************************************手机联系人*****************************************//
	/**
	 * 获取手机联系人列表
	 * 
	 * @version 1.0
	 * @createTime 2013-12-13,下午12:00:48
	 * @updateTime 2013-12-13,下午12:00:48
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return
	 */
	public ArrayList<PhoneContactBean> getPhoneContacts() {

		// // 如果数据已经读取过，就不再读取
		// if (TApplication.list_Contacts != null && TApplication.list_Contacts.size() > 0) {
		// return null;
		// }

		ContentResolver resolver = TApplication.context.getContentResolver();
		ArrayList<PhoneContactBean> list_Beans = new ArrayList<PhoneContactBean>();

//		TApplication.list_Contacts = new ArrayList<PhoneContactBean>();
//		TApplication.map_Contacts = new HashMap<String, PhoneContactBean>();

		/** 获取库Phon表字段 **/
		String[] PHONES_PROJECTION = new String[] { Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID };
		// 获取手机联系人
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);
		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {

				PhoneContactBean bean = new PhoneContactBean();
				// 得到手机号码
				String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(Phone.NUMBER));

				// 当手机号码为空的或者为空字段 跳过当前循环
				if (TextUtils.isEmpty(phoneNumber)) {
					continue;
				}

				// 将手机号码格式成标准格式
				phoneNumber = phoneNumber.replaceAll(" ", "");
				phoneNumber = phoneNumber.replaceAll("-", "");
				if (phoneNumber.startsWith("+86")) {
					phoneNumber = phoneNumber.replace("+86", "");
				}

				// 是否是个合法的手机号码
				if (!PatternUtil.isValidMobilePhone(phoneNumber)) {
					continue;
				}

				// 已经存在该手机号码
//				if (TApplication.map_Contacts.containsKey(phoneNumber)) {
//					continue;
//				}

				// 得到联系人名称
				String contactName = phoneCursor.getString(phoneCursor.getColumnIndex(Phone.DISPLAY_NAME));
				// 得到联系人ID
				Long contactid = phoneCursor.getLong(phoneCursor.getColumnIndex(Phone.CONTACT_ID));
				// 得到联系人头像ID
				Long photoid = phoneCursor.getLong(phoneCursor.getColumnIndex(Phone.PHOTO_ID));

				bean.setName(contactName);
				bean.setPhone(phoneNumber);
				bean.setPinyinHead(PinyinUtil.toHanyuPinyinHeadString(contactName).toUpperCase());
				bean.setPinyin(PinyinUtil.toHanyuPinyinString(contactName).toUpperCase());
				bean.setContactID(contactid);
				bean.setPhotoID(photoid);

//				TApplication.map_Contacts.put(phoneNumber, bean);
//				TApplication.list_Contacts.add(bean);
				list_Beans.add(bean);

				// // 得到联系人头像Bitamp
				// Bitmap contactPhoto = null;
				// // photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
				// if (photoid > 0) {
				// Uri uri =
				// ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,
				// contactid);
				// InputStream input =
				// ContactsContract.Contacts.openContactPhotoInputStream(resolver,
				// uri);
				// contactPhoto = BitmapFactory.decodeStream(input);
				// } else {
				// contactPhoto = kk;
				// }

			}
			PhoneContactSortUtil sortUtil = new PhoneContactSortUtil();
			Collections.sort(list_Beans, sortUtil);
			phoneCursor.close();
		}

		return list_Beans;
	}

}

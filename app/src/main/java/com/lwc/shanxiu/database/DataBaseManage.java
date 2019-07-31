package com.lwc.shanxiu.database;

import com.lwc.shanxiu.configs.DataBaseFields;
import com.lwc.shanxiu.configs.TApplication;
import com.lwc.shanxiu.interf.OnOperationDataBase;
import com.lwc.shanxiu.utils.LogUtil;
import com.lwc.shanxiu.utils.OperationDataBaseUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * 数据库管理工具
 * 
 * @Description
 * @author 何栋
 * @version 1.0
 * @date 2014年5月18日
 * @Copyright: Copyright (c) 2014 Shenzhen Utoow Technology Co., Ltd. All rights reserved.
 * 
 */
public class DataBaseManage {

	/** 数据库版本 */
	public static final int DATA_BASE_VERSION = 1;

	/** 数据库操作对象 */
	private static OperationDataBaseUtil dataBaseHelper;

	/**
	 * 创建指定名称的数据库
	 * 
	 * @version 1.0
	 * @createTime 2013-10-22,下午3:51:28
	 * @updateTime 2013-10-22,下午3:51:28
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param dataBaseName
	 */
	public static void createDataBase(String dataBaseName) {
		dataBaseHelper = new OperationDataBaseUtil(TApplication.context, dataBaseName, null, DATA_BASE_VERSION, new OnOperationDataBase() {

			@Override
			public void updateDataBase(SQLiteDatabase db, int oldVersion, int newVersion) {
				if (newVersion > oldVersion) {// 如果是数据库升级
					updateTables(db);
				}
			}

			@Override
			public void createTable(SQLiteDatabase db) {
				createTables(db);
			}
		});
		dataBaseHelper.onCreate(dataBaseHelper.getWritableDatabase());
		dataBaseHelper.close();
	}

	/**
	 * 获取数据库操作对象
	 * 
	 * @version 1.0
	 * @createTime 2013-10-21,下午10:03:52
	 * @updateTime 2013-10-21,下午10:03:52
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param dataBaseName
	 * @return
	 */
	public static OperationDataBaseUtil getOperationDataBaseUtil(String dataBaseName) {

		if (null != dataBaseHelper) {
			dataBaseHelper.close();
			dataBaseHelper = null;
		}

		dataBaseHelper = new OperationDataBaseUtil(TApplication.context, dataBaseName, null, DATA_BASE_VERSION);
		return dataBaseHelper;

	}

	/**
	 * 更新数据库表
	 * 
	 * @version 1.0
	 * @createTime 2013-11-22,下午2:47:17
	 * @updateTime 2013-11-22,下午2:47:17
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param db
	 *        数据库操作对象
	 * @return
	 */
	private static void updateTables(SQLiteDatabase db) {
		try {
			int version = db.getVersion();
			switch (version) {
			case 1:// 数据库版本1-2
			case 2:// 数据库版本1-2
				db.execSQL("ALTER TABLE " + DataBaseFields.TB_USER + " ADD " + DataBaseFields.BINDDID + " varchar");
				db.execSQL("ALTER TABLE " + DataBaseFields.TB_USER + " ADD " + DataBaseFields.DEVICENAME + " varchar");
				db.execSQL("ALTER TABLE " + DataBaseFields.TB_USER + " ADD " + DataBaseFields.DID + " varchar");
				break;
			case 3:// 数据库版本3-4
				db.execSQL("ALTER TABLE " + DataBaseFields.TB_USER + " ADD " + DataBaseFields.AVGREPLY + " varchar");
				db.execSQL("ALTER TABLE " + DataBaseFields.TB_USER + " ADD " + DataBaseFields.AFFILIATION + " varchar");
				db.execSQL("ALTER TABLE " + DataBaseFields.TB_USER + " ADD " + DataBaseFields.AVGSERVICE + " varchar");
			case 4:// 数据库版本4-5
			case 5:// 数据库版本5-6
			case 6:// 数据库版本6-7
			case 7:// 数据库版本7-8
			case 8:// 数据库版本8-9
			case 9:// 数据库版本9-10
			case 10:// 数据库版本10-11
			}
		} catch (RuntimeException e) {
			// 数据库更新失败
			LogUtil.err("DataBase Update Error ============>\n" + e.getMessage());
		}

	}

	/**
	 * 创建数据表
	 * 
	 * @version 1.0
	 * @createTime 2013-10-21,下午4:20:03
	 * @updateTime 2013-10-21,下午4:20:03
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param db
	 *        数据库操作对象
	 */
	private static void createTables(SQLiteDatabase db) {

		/**
		 * 创建设备表
		 * 
		 * @version 1.0
		 * @createTime 2013-10-21,下午4:20:03
		 * @updateTime 2013-10-21,下午4:20:03
		 * @createAuthor 陈聪
		 * @updateAuthor 陈聪
		 */
		String sql = "create table if not exists " + DataBaseFields.TB_MECHES + "(" // 创建设备表
				+ DataBaseFields.ID + " integer PRIMARY KEY, " // 自增长id.
				+ DataBaseFields.MECHE_NAME + " varchar," // 设备名称
				+ DataBaseFields.MECHE_MODEL + " varchar," // 设备型号
				+ DataBaseFields.MECHE_DRES + " varchar" // 设备描述
				+ ");";
		db.execSQL(sql);
		/**
		 * 创建设备类别菜单表
		 * 
		 * @version 1.0
		 * @createTime 2013-10-21,下午4:20:03
		 * @updateTime 2013-10-21,下午4:20:03
		 * @createAuthor 陈聪
		 * @updateAuthor 陈聪
		 */
		sql = "create table if not exists " + DataBaseFields.TB_CATEGORY_MECHE + "(" // 创建设备类别表
				+ DataBaseFields.ID + " integer PRIMARY KEY, " // 自增长id.
				+ DataBaseFields.CATEGORY_MECHE_NAME + " varchar," // 设备名称
				+ DataBaseFields.CATEGORY_MECHE_DRES + " varchar," // 设备描述
				+ DataBaseFields.CATEGORY_MECHE_ID + " varchar," // 设备描述
				+ DataBaseFields.CATEGORY_TO_OF + " varchar," // 设备描述
				+ DataBaseFields.CATEGORY_MAIN_ID + " varchar" // 设备
				+ ");";
		db.execSQL(sql);
		/**
		 * 创建消息表
		 * 
		 * @version 1.0
		 * @createTime 2013-10-21,下午4:20:03
		 * @updateTime 2013-10-21,下午4:20:03
		 * @createAuthor 陈聪
		 * @updateAuthor 陈聪
		 */
		sql = "create table if not exists " + DataBaseFields.TB_MESSAGE_SEND + "(" // 创建功能表
				+ DataBaseFields.ID + " integer PRIMARY KEY, " // 自增长id.
				+ DataBaseFields.MESSAGE_SEND_CONTENT + " varchar," // 消息内容
				+ DataBaseFields.MESSAGE_SEND_STATUS + " varchar," // 消息状态
				+ DataBaseFields.MESSAGE_SEND_IS_READ + " varchar," // 是否已读
				+ DataBaseFields.MESSAGE_SEND_TIME + " varchar" // 消息时间
				+ ");";
		db.execSQL(sql);
		/**
		 * 创建订单表
		 * 
		 * @version 1.0
		 * @createTime 2013-10-21,下午4:20:03
		 * @updateTime 2013-10-21,下午4:20:03
		 * @createAuthor 陈聪
		 * @updateAuthor 陈聪
		 */
		sql = "create table if not exists " + DataBaseFields.TB_ORDERS + "(" // 创建功能表
				+ DataBaseFields.ID + " integer PRIMARY KEY, " // 自增长id.
				+ DataBaseFields.ORDERS_COUNT + " varchar," // 订单报价
				+ DataBaseFields.ORDERS_DESCRIPTION + " varchar," // 订单描述
				+ DataBaseFields.ORDERS_LOCATION + " varchar," // 订单坐标
				+ DataBaseFields.ORDERS_STATUS + " varchar," // 订单状态
				+ DataBaseFields.ORDERS_ADDRES + " varchar," // 订单地址
				+ DataBaseFields.ORDERS_GETTER_ID + " varchar," // 接单人id
				+ DataBaseFields.ORDERS_GETTER_COMPANY + " varchar," // 接单人所属公司
				+ DataBaseFields.ORDERS_GETTER_NAME + " varchar," // 接单人名字
				+ DataBaseFields.ORDERS_GETTER_PHONE + " varchar," // 接单人电话
				+ DataBaseFields.ORDERS_TIME + " varchar" // 接单时间
				+ ");";
		db.execSQL(sql);
		/**
		 * 创建用户表
		 * 
		 * @version 1.0
		 * @createTime 2013-10-21,下午4:20:03
		 * @updateTime 2013-10-21,下午4:20:03
		 * @createAuthor 陈聪
		 * @updateAuthor 陈聪
		 */
		sql = "create table if not exists " + DataBaseFields.TB_USER + "(" // 创建用户表
				+ DataBaseFields.ID + " integer PRIMARY KEY autoincrement, " // 自增长id.
				+ DataBaseFields.USER_ID + " varchar, " // 用户id
				+ DataBaseFields.USER_INFO_ID + " varchar, " // 用户详情id
				+ DataBaseFields.USERNAME + " varchar, " // 用户名
				+ DataBaseFields.PASSWORD + " varchar, " // 密码
				+ DataBaseFields.NICKNAME + " varchar, " // 昵称
				+ DataBaseFields.REALNAME + " varchar, " // 真实姓名
				+ DataBaseFields.SEX + " varchar, " // 性别
				+ DataBaseFields.PORTRAIT + " varchar, " // 头像
				+ DataBaseFields.BIRTHDAY + " varchar, " // 生日
				+ DataBaseFields.SIGNATURE + " varchar, " // 个性签名
				+ DataBaseFields.ID_CARD_NUMBER + " varchar, " // 身份证号
				+ DataBaseFields.PROVINCE + " varchar, " // 身份
				+ DataBaseFields.CITY + " varchar, " // 城市
				+ DataBaseFields.ADDRES + " varchar, " // 详细地址
				+ DataBaseFields.IS_REPAIRER + " varchar, " // 是否是工程师
				+ DataBaseFields.PHONE_NUM + " varchar, " // 手机号码
				+ DataBaseFields.SERIAL_NUM + " varchar, " // 手机串号（地址）
				+ DataBaseFields.TICKET + " varchar, " // CAS证书
				+ DataBaseFields.IS_REMEMBER + " varchar, " // 是否记住密码
				+ DataBaseFields.IS_AUTO_LOGIN + " varchar, " // 是否自动登录
				+ DataBaseFields.IS_ALLOW_RECOMMEND + " varchar, " // 是否允许推荐
				+ DataBaseFields.EMAIL + " varchar, " // 邮箱
				+ DataBaseFields.COMPANY + " varchar, " // 所属公司名称
				+ DataBaseFields.BINDDID + " varchar, " // 绑定设备udid
				+ DataBaseFields.DEVICENAME + " varchar, " // 绑定设备名称
				+ DataBaseFields.DID + " varchar, " // DID
				+ DataBaseFields.LAST_LOGIN_TIME + " varchar" // 最后登录时间
				+ DataBaseFields.AVGREPLY + " varchar" + DataBaseFields.AFFILIATION + " varchar" + DataBaseFields.AVGSERVICE + " varchar" + ");";
		db.execSQL(sql);
		/**
		 * json的缓存表
		 * 
		 * @version 1.0
		 * @createTime 2015-4-10,上午10:50:28
		 * @updateTime 2015-4-10,上午10:50:28
		 * @createAuthor 綦巍
		 * @updateAuthor 綦巍
		 */
		sql = "create table if not exists " + DataBaseFields.TB_JSON + "(" + DataBaseFields.ID + " integer PRIMARY KEY autoincrement, " // 自增长id.
				+ DataBaseFields.FIELD_TYPE + " varchar, " // 类型.
				+ DataBaseFields.FIELD_JSON + " varchar" // json
				+ ");";
		db.execSQL(sql);

	}

	/**
	 * 删除数据库
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年6月26日,上午10:04:57
	 * @updateTime 2015年6月26日,上午10:04:57
	 * @createAuthor chencong
	 * @updateAuthor chencong
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param dixunNumber
	 */
	public static boolean deleteDatabase(Context context, String dataBaseName) {
		return context.deleteDatabase(dataBaseName);
	}
}

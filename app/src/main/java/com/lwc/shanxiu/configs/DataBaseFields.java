package com.lwc.shanxiu.configs;

/**
 * @Description 数据库字段类
 * @author 何栋
 * @date 2015年8月26日
 * @Copyright: Copyright (c) 2015 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 */
public class DataBaseFields {

	// *******************************公用字段*****************************//
	/** 本地数据库自增长Id */
	public static final String ID = "id";
	/** 用户Id */
	public static final String USER_ID = "user_id";
	/** 用户详情Id */
	public static final String USER_INFO_ID = "user_info_id";
	/** 帐号、用户名 */
	public static final String USERNAME = "username";
	/** 用户昵称 */
	public static final String NICKNAME = "nickname";
	/** 真实姓名 */
	public static final String REALNAME = "realname";
	/** 用户头像 */
	public static final String PORTRAIT = "portrait";
	/** 性别 */
	public static final String SEX = "sex";
	/** 个性签名 */
	public static final String SIGNATURE = "signature";
	/** 是否允许推荐消息 */
	public static final String IS_ALLOW_RECOMMEND = "is_allow_recommend";

	public static final String AFFILIATION = "affiliation";

	// *************************************XMPP用户表***********************************//
	/** 用户表 */
	public static final String TB_USER = "tb_user";
	/** 密码 */
	public static final String PASSWORD = "password";
	/** 生日 */
	public static final String BIRTHDAY = "birthday";
	/** 身份证号码 */
	public static final String ID_CARD_NUMBER = "id_card_number";
	/** 省份 */
	public static final String PROVINCE = "province";
	/** 城市 */
	public static final String CITY = "city";
	/** 手机号码 */
	public static final String PHONE_NUM = "phone_num";
	/** 手机归属地 */
	public static final String SERIAL_NUM = "serial_num";
	/** ticket */
	public static final String TICKET = "ticket";
	/** 是否记住密码 */
	public static final String IS_REMEMBER = "is_remember";
	/** 是否自动登录 */
	public static final String IS_AUTO_LOGIN = "is_auto_login";
	/** 邮箱 */
	public static final String EMAIL = "email";
	/** 最后登录时间 */
	public static final String LAST_LOGIN_TIME = "last_login_time";
	/** 公司名称 */
	public static final String COMPANY = "company";
	/** 详细地址 */
	public static final String ADDRES = "addres";
	/** 是否是工程师 */
	public static final String IS_REPAIRER = "is_repairer";
	/** 绑定设备udid */
	public static final String BINDDID = "bindudid";
	/** 绑定设备名称 */
	public static final String DEVICENAME = "devicename";
	/** 绑定设备did */
	public static final String DID = "did";
	public static final String AVGSERVICE = "avgservice";
	public static final String AVGSPECIALTY = "avgspecialty";
	public static final String AVGREPLY = "avgreply";
	public static final String STAR = "star";
	// ************************************设备列表************************************//
	/** 设备列表 */
	public static String TB_MECHES = "tb_meches";
	/** 设备名称 */
	public static String MECHE_NAME = "meches_name";
	/** 设备型号 */
	public static String MECHE_MODEL = "meches_categary";
	/** 设备描述 */
	public static String MECHE_DRES = "meches_description";
	// ************************************设备类别菜单列表************************************//
	/** 设备类别表 */
	public static String TB_CATEGORY_MECHE = "tb_category_meches";
	/** 设备类别名称 */
	public static String CATEGORY_MECHE_NAME = "category_name";
	/** 设备类别id */
	public static String CATEGORY_MECHE_ID = "category_id";
	/** 设备主类别id */
	public static String CATEGORY_MAIN_ID = "did";
	/** 从属类别名称 */
	public static String CATEGORY_TO_OF = "category_to_of";
	/** 设备类别描述 */
	public static String CATEGORY_MECHE_DRES = "category_description";
	// ************************************推送拉取的消息列表************************************//
	/** 消息表 */
	public static String TB_MESSAGE_SEND = "tb_message_send";
	/** 消息内容 */
	public static String MESSAGE_SEND_CONTENT = "message_send_content";
	/** 消息时间 */
	public static String MESSAGE_SEND_TIME = "message_send_time";
	/** 消息状态 */
	public static String MESSAGE_SEND_STATUS = "message_send_status";
	/** 是否已读 */
	public static String MESSAGE_SEND_IS_READ = "message_send_is_read";
	// ************************************订单列表************************************//
	/** 订单表 */
	public static String TB_ORDERS = "tb_orders";
	/** 订单价格 */
	public static String ORDERS_COUNT = "orders_count";
	/** 订单时间 */
	public static String ORDERS_TIME = "orders_time";
	/** 订单状态 */
	public static String ORDERS_STATUS = "orders_status";
	/** 订单描述 */
	public static String ORDERS_DESCRIPTION = "orders_description";
	/** 订单地址 */
	public static String ORDERS_ADDRES = "orders_addres";
	/** 订单地图坐标 */
	public static String ORDERS_LOCATION = "orders_location";
	/** 订单接单人id */
	public static String ORDERS_GETTER_ID = "orders_getter_id";
	/** 订单接单人名称 */
	public static String ORDERS_GETTER_NAME = "orders_getter_name";
	/** 订单接单人电话号码 */
	public static String ORDERS_GETTER_PHONE = "orders_getter_phone";
	/** 订单接单人所属公司 */
	public static String ORDERS_GETTER_COMPANY = "orders_getter_company";
	// ************************************通用菜单表************************************//
	/** json通用字段 */
	public static final String TB_JSON = "tb_json";
	public static final String FIELD_JSON = "field_json";
	public static final String FIELD_TYPE = "field_type";
}

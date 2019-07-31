package com.lwc.shanxiu.configs;

public class BroadcastFilters {
	// ***************************注册广播接收动作 ***************************//
	/** 注册动作 */
	public static final String REGISTER_ACTION = "com.tentinet.baseproject.action.ChatService.register";
	/** 退出登陆动作 */
	public static final String LOGIN_OUT_ACTION = "LOGIN_OUT_ACTION";
	/** 登陆动作 */
	public static final String LOGIN_ACTION = "com.tentinet.baseproject.action.ChatService.login";
	/** 登陆动作结果 */
	public static final String LOGIN_ACTION_RESULT = "com.tentinet.baseproject.action.ChatService.login_result";
	/** PING服务器动作 */
	public static final String SEND_PING_ACTION = "com.tentinet.baseproject.action.ChatService.ping";
	/** 创建聊天室动作 */
	public static final String SEND_CREATE_CHAT_ACTION = "com.tentinet.baseproject.action.ChatService.create_chat";
	/** 发信息动作 */
	public static final String SEND_MESSAGE_ACTION = "com.tentinet.baseproject.service.ChatService.send_message";
	/** 发信息结果 */
	public static final String SEND_MESSAGE_RESPONSE = "com.tentinet.baseproject.service.ChatService.send_message_response";
	/** 被迫下线提醒 */
	public static final String WARNING_OUT_OF_LOGIN = "com.tentinet.baseproject.service.ChatService.warning_out_login";
	/** 新增好友动作 */
	public static final String SEND_ADD_FRIEND_ACTION = "com.tentinet.baseproject.service.ChatService.send_add_friend_action";
	/** 删除好友动作 */
	public static final String SEND_DELETE_FRIEND_ACTION = "com.tentinet.baseproject.service.ChatService.send_delete_friend_action";
	/** 请求发送结果 */
	public static final String SEND_REQUIRE_RESULT = "com.tentinet.baseproject.service.ChatService.send_result";

	/** 获取所有好友列表动作 */
	public static final String GET_ALL_FRIEND_ACTION = "com.tentinet.baseproject.service.ChatService.get_all_friend";
	/** 获取所有好友列表结果 */
	public static final String GET_ALL_FRIEND_REQUIRE_RESULT = "com.tentinet.baseproject.service.ChatService.get_all_friend_require_result";

	/** 手动设置好友申请 */
	public static final String SEND_NEW_FRIEND_MANUAL = "com.tentinet.baseproject.service.ChatService.new_friend_manual";

	/** 自动同意好友申请动作 */
	public static final String SEND_NEW_FRIEND_SUBSCRIBED_AUTO = "com.tentinet.baseproject.service.ChatService.send_new_friend_subscribed_action_auto";
	/** 同意好友申请动作 */
	public static final String SEND_NEW_FRIEND_SUBSCRIBED_ACTION = "com.tentinet.baseproject.service.ChatService.send_new_friend_subscribed_action";
	/** 拒绝好友申请动作 */
	public static final String SEND_NEW_FRIEND_UNSUBSCRIBED_ACTION = "com.tentinet.baseproject.service.ChatService.send_new_friend_unsubscribed_action";

	/** 同意好友申请发送结果 */
	public static final String SEND_NEW_FRIEND_SUBSCRIBED_REQUIRE_RESULT = "com.tentinet.baseproject.service.ChatService.send_new_friend_subscribed_result";
	/** 拒绝好友申请发送结果 */
	public static final String SEND_NEW_FRIEND_UNSUBSCRIBED_REQUIRE_RESULT = "com.tentinet.baseproject.service.ChatService.send_new_friend_unsubscribed_result";
	/** 获取到新好友申请请求 */
	public static final String GET_NEW_FRIEND_SUBSCRIBE = "com.tentinet.baseproject.service.ChatService.get_new_friend_subscribe";
	/** 获取到好友同意添加申请 */
	public static final String GET_NEW_FRIEND_SUBSCRIBED = "com.tentinet.baseproject.service.ChatService.get_new_friend_subscribed";
	/** 获取到好友拒绝添加申请 */
	public static final String GET_NEW_FRIEND_UNSUBSCRIBED = "com.tentinet.baseproject.service.ChatService.get_new_friend_unsubscribed";
	/** 获取到被好友删除 */
	public static final String GET_NEW_FRIEND_UNSUBSCRIBE = "com.tentinet.baseproject.service.ChatService.get_new_friend_unsubscribe";
	/** 收到消息 */
	public static final String RECEIVER_CHAT_MESSAGE = "com.tentinet.baseproject.service.ChatService.receiver_chat_message";
	/** 收到多人聊天消息 */
	public static final String RECEIVER_GROUP_CHAT_MESSAGE = "com.tentinet.baseproject.service.ChatService.receiver_group_chat_message";
	/** 获取多人聊天室 */
	public static final String GET_ALL_GROUP_ACTION = "com.tentinet.baseproject.service.ChatService.get_all_group_action";
	/** 获取所有多人聊天室结果 */
	public static final String GET_ALL_GROUP_REQUIRE_RESULT = "com.tentinet.baseproject.service.ChatService.get_all_group_require_result";
	/** 创建多人聊天动作 */
	public static final String SEND_CREATE_GROUP_ACTION = "com.tentinet.baseproject.service.ChatService.send_create_group_action";
	/** 创建多人聊天结果 */
	public static final String SEND_CREATE_GROUP_REQUIRE_RESULT = "com.tentinet.baseproject.service.ChatService.send_create_group_result";
	/** 邀请好友加入多人聊天动作 */
	public static final String SEND_GROUP_INVITE_FRIEND_ACTION = "com.tentinet.baseproject.service.ChatService.send_group_invite_friend_action";
	/** 获取到邀请加入多人聊天动作 */
	public static final String RECEIVER_GROUP_INVITE = "com.tentinet.baseproject.service.ChatService.receiver_group_invite";
	/** 获取多人聊天成员结果 */
	public static final String GET_GROUP_MEMBERS_REQUIRE_RESULT = "com.tentinet.baseproject.service.ChatService.get_group_members_require_result";
	/** 获取多人聊天成员动作 */
	public static final String GET_GROUP_MEMBERS_ACTION = "com.tentinet.baseproject.service.ChatService.get_group_members_action";
	/** 踢出多人聊天动作 */
	public static final String SEND_REMOVE_MEMBERS_ACTION = "com.tentinet.baseproject.service.ChatService.send_remove_member_action";
	/** 退出多人聊天动作 */
	public static final String SEND_LEAVE_GROUP_ACTION = "com.tentinet.baseproject.service.ChatService.send_leave_group_action";
	/** 踢出多人聊天成员 */
	public static final String SEND_KICK_GROUP_MEMBER_ACTION = "com.tentinet.baseproject.service.ChatService.send_kick_group_member_action";
	/** 加入多人聊天动作 */
	public static final String SEND_JOIN_GROUP_ACTION = "com.tentinet.baseproject.service.ChatService.send_join_group_action";
	/** 拒绝加入多人聊天动作 */
	public static final String SEND_DECLINE_GROUP_ACTION = "com.tentinet.baseproject.service.ChatService.send_decline_group_action";
	/** 发送多人聊天消息 */
	public static final String SEND_GROUP_MESSAGE_ACTION = "com.tentinet.baseproject.service.ChatService.send_group_message_action";
	/** 收到多人聊天消息 */
	public static final String RECEIVER_GROUP_MESSAGE = "com.tentinet.baseproject.service.ChatService.receiver_group_message";

	/** 获取离线消息 */
	public static final String GET_OFFLINE_MESSAGE_ACTION = "com.tentinet.baseproject.service.ChatService.get_offline_message_action";

	/** 本地数据库群成员变更结果 */
	public static final String DB_GROUP_MEMBER_UPDATE_RESULT = "com.tentinet.baseproject.service.ChatService.db_group_member_update_result";
	/** 本地数据库清除群相关内容结果 */
	public static final String DB_GROUP_CLEAR_RESULT = "com.tentinet.baseproject.service.ChatService.db_group_clear_result";

	/** 单聊设置添加好友聊天进入群聊时发送广播关闭单聊页面和单聊设置页面 */
	public static final String FINISH_ACTIVITY_ACTION_CHAT_SETTING = "com.tentinet.baseproject.dixun.acitvity.SendChatActivity.finish.chat.set";
	/** 单聊设置点击条目空白时关闭输入框 */
	public static final String CHAT_INIT_INPUT_ACTION = "com.tentinet.baseproject.xmpp.adapter.Chat.init.input";
	/** 单聊页面关闭 */
	public static final String FINISH_CHAT_ACTION = "com.tentinet.baseproject.xmpp.activity.ChatActivity.finish.chat";
	/** 群聊页面关闭 */
	public static final String FINISH_GROUP_CHAT_ACTION = "com.tentinet.baseproject.xmpp.activity.GroupChatActivity.reflush.group.chat";
	/** 单聊页面刷新数据 */
	public static final String REFLUSH_CHAT_ACTION = "com.tentinet.baseproject.xmpp.activity.ChatActivity.finish.chat";
	/** 无法连接到服务器 */
	public static final String DISCONNECTED_TO_SERVER = "com.tentinet.baseproject.service.ChatService.disconnect";
	/** 聊天界面退出时主界面刷新数据的广播 */
	public static final String CHAT_EXIT_REFLUSH_DATA = "com.tentinet.baseproject.activity.ChatActivity.chat_exit_reflush_data";
	/** 分享名片之后的广播 */
	public static final String CHAT_SHARE_CARD = "com.tentinet.baseproject.acitvity.SelectContactsActivity.share.card";
	/** 单聊界面分享资讯广播 */
	public static final String CHAT_SHARE_NEWS = "com.tentinet.baseproject.acitvity.SelectContactsActivity.share.news";

	static String BASE = TApplication.context.getPackageName();
	/** 添加设备成功之后的广播 */
	public static final String ADD_MACHINE_SUCCESS = "com.lwc.shanxiu.activity.AddMachineActivity.add.machine" + BASE;
	public static final String FINISH_TO_STOP_OUT_OF_M = "FINISH_TO_STOP_OUT_OF_M" + BASE;
	/** 分级确认之后的广播 */
	public static final String FENJI_TO_BE_SURE = "FENJI_TO_BE_SURE" + BASE;
	/** 登录完成的广播 */
	public static final String UPDATE_USER_LOGIN_SUCCESSED = "UPDATE_USER_LOGIN_SUCCESSED" + BASE;
	/** 用户头像改变之后的广播 */
	public static final String UPDATE_USER_INFO_ICON = "com.lwc.shanxiu.activity.UserInfoActivity.userinfo_icon" + BASE;
	/** 用户头像改变之后的广播 */
	public static final String SHOW_MACHINE_INFO = "SHOW_MACHINE_INFO" + BASE;
	/** 密码修改成功之后的广播 */
	public static final String UPDATE_PASSWORD = "UPDATE_PASSWORD" + BASE;
	/** 设备报修成功之后的广播 */
	public static final String NOTIFI_DATA_MACHINE_LIST = "NOTIFI_DATA_MACHINE_LIST" + BASE;
	/** 设备报修成功之后的广播 */
	public static final String NOTIFI_MAIN_ORDER_MENTION = "NOTIFI_MAIN_ORDER_MENTION" + BASE;

	/** 获取附近的人成功之后的广播 */
	public static final String NOTIFI_NEARBY_PEOPLE = "NOTIFI_NEARBY_PEOPLE" + BASE;
	/** 获取消息数成功之后的广播 */
	public static final String NOTIFI_MESSAGE_COUNT = "NOTIFI_MESSAGE_COUNT" + BASE;
	/** 获取未接订单成功之后的广播 */
	public static final String NOTIFI_WAITTING_ORDERS = "NOTIFI_WAITTING_ORDERS" + BASE;
	/** 订单状态刷新广播 */
	public static final String NOTIFI_ORDER_INFO_MENTION = "NOTIFI_ORDER_INFO_MENTION" + BASE;
	/** 自动接单按钮状态刷新广播 */
	public static final String NOTIFI_BUTTON_STATUS = "NOTIFI_BUTTON_STATUS" + BASE;
	/** 订单评价之后的刷新广播 */
	public static final String NOTIFI_ORDER_PRIASE_MENTION = "NOTIFI_ORDER_PRIASE_MENTION" + BASE;
	/** 订单被接单提示广播 */
	public static final String NOTIFI_ORDER_GETTED_MENTION = "NOTIFI_ORDER_GETTED_MENTION" + BASE;
	/** 订单挂起操作 */
	public static final String NOTIFI_ORDER_INFO_GUAQI = "NOTIFI_ORDER_INFO_GUAQI" + BASE;
	/** 订单弹窗操作 */
	public static final String NOTIFI_ORDER_INFO_TANCHUAN = "NOTIFI_ORDER_INFO_TANCHUAN" + BASE;
	/** 订单状态改变之后的广播 */
	public static final String NOTIFI_ORDER_INFO_CHANGE = "NOTIFI_ORDER_INFO_CHANGE" + BASE;
	public static final String NOTIFI_CLOSE_SLIDING_MENU = "NOTIFI_CLOSE_SLIDING_MENU" + BASE;
	public static final String NOTIFI_NEAR_ORDER = "NOTIFI_NEAR_ORDER" + BASE;

	public static final String NOTIFI_GET_NEAR_ORDER = "NOTIFI_GET_NEAR_ORDER" + BASE;
	public static final String NOTIFI_GET_ORDER_COUNT = "NOTIFI_GET_ORDER_COUNT" + BASE;
}

package com.lwc.shanxiu.controler.http;

/**
 * 网络请求接口
 *
 * @author 何栋
 * @version 1.0
 * @date 2017/3/9 09:16
 * @email 294663966@qq.com
 */
public class RequestValue {


    /**
     * 我的技能
     */
    public static final String METHOD_GET_MY_SKILLS = "/repair/getSkill";
    /**
     * 换件报价
     */
    public static final String METHOD_ORDER_STATUS_SET = "/order/replacement";
    /**
     * 意见反馈
     */
    public static final String METHOD_SUGGEST = "/feedback/insert";
    /**
     * 我的订单
     */
    public static final String MY_ORDERS = "/new/order/userOrder";
    /**
     * 手机号码登录
     */
    public static final String LOGIN_PHONE = "/user/login";
    /**
     * 找回密码
     */
    public static final String BACK_PWD = "/user/retrievePass";

    /**
     * 下单状态
     */
    public static final String ORDER_STATE = "/order/process/";

    /**
     * 工程师接单
     */
    public static final String UPDATE_ORDER = "/order/accept";

    /**
     * 工程师开始处理
     */
    public static final String DISPOSE_ORDER = "/order/dispose";

    /**
     * 工程师已完成
     */
    public static final String FINISH_ORDER = "/order/finish";
    /**
     * 工程师转单
     */
    public static final String CHANGE_ORDER = "/order/changeOrder";

    /**
     * 工程师确认转单
     */
    public static final String CHANGE_ORDER_QR = "/order/confirmChange";

    /**
     * 工程师接受售后
     */
    public static final String UPDATE_AFTER_ORDER = "/after/sale/accept";

    /**
     * 工程师开始处理售后
     */
    public static final String DISPOSE_AFTER_ORDER = "/after/sale/start";

    /**
     * 工程师完成售后订单
     */
    public static final String FINISH_AFTER_ORDER = "/after/sale/finish";

    /**
     * 退出APP
     **/
    public static final String EXIT = "/user/exit";

    /**
     * 重置订单
     **/
    public static final String RESET_ORDER ="/order/resetOrder.phone";

    /**
     * 版本升级（更新）
     **/
    public static final String UPDATE_APP = "/version/check_2_9";

    /**
     * 首页订单视图接口
     */
    public static final String ORDER_VIEW = "/inform/title";

    /**
     * 获取用户下单信息
     */
    public static final String ORDER_USER = "/order/getAcceptOrder";

    /**
     * 投诉工程师接口
     */
    public static final String COMPLAINT = "/fb/save.phone";

    /**
     * 附近的订单2
     */
    public static final String NEARBY_ORDER2 = "/new/near/order";

    /**
     * 维修师列表
     */
    public static final String WXY_LIST ="/near/maintenance";
    /**
     * 更新用户信息
     */
    public static final String UP_USER_INFOR = "/user/modify";
    /**
     * 用户信息
     */
    public static final String USER_INFO = "/user/info";

    /**
     * 图片token
     */
    public static final String GET_PICTURE = "/oss/getAuth";

    /**
     * 上传图片
     */
    public static final String UP_PICTURE = "/file/upload.phone";

    /**
     * 获取验证码
     */
    public static final String GET_CODE = "/inform/sendSms/";

    /**
     * 获取所有设备类型
     */
    public static final String GET_OLL_DEVICE_TYPE = "/type/getAll";

    /**
     * 收支记录
     */
    public static final String GET_WALLET_HISTORY = "/user/payment/record";

    /**
     * 拆红包
     */
    public static final String GET_RED_PACKET_MONEY = "/packet/open";

    /**
     * 申请提现
     */
    public static final String POST_WITHDRAW_DEPOSIT = "/packet/withdraw";

    /**
     * 检查是否有红包活动
     */
    public static final String GET_CHECK_ACTIVITY = "/user/check/activity";

    /**
     * 分享统计
     */
    public static final String GET_INFORMATION_SHARE = "/information/updateShare/";

    /**
     * 获取故障类型
     */
    public static final String GET_MALFUNCTION_FAULT = "/type/fault_2_8_5";
    /**
     * 获取返修类型
     */
    public static final String GET_MALFUNCTION = "/type/getAll";

    /**
     * 提交设备返修
     */
    public static final String POST_PERSONAL_DETECTION = "/order/personal/repair";

    /**
     * 工程师到达现场
     */
    public static final String POST_ARRIVE_SCENE = "/order/personal/arriveScene";

    /**
     * 提交检测报告
     */
    public static final String POST_ORDER_PERSONAL_DETECTION = "/new/order/personal/detection";

    /**
     * 查询订单详情
     */
    public static final String POST_ORDER_INFO = "/new/order/personal/info";

    /**
     * 设备送回安装
     */
    public static final String POST_ORDER_BACK = "/order/personal/backInstall";

    /**
     * 无法维修
     */
    public static final String POST_ORDER_CANT_REPAIR = "/order/personal/cantRepair";
    /**
     * 上传微信信息
     */
    public static final String UP_UPLOAD_INFO = "/newYear/activity/shareCall";

    /**
     * 查询我的消息列表
     */
    public static final String GET_MY_MESSAGE_LIST = "/message/list";

    /**
     * 查询某类型消息列表
     */
    public static final String GET_MESSAGE_LIST = "/message/detailsList/";

    /**
     * 检查是否有新消息
     */
    public static final String HAS_MESSAGE = "/message/hasMessage";

    /**
     * 新消息阅读回调
     */
  //  public static final String READ_MESSAGE = "/message/read/";


    /**
     * 新消息阅读回调
     */
    public static final String READ_MESSAGE = "/message/readOne/";

    /**
     * 机关单位订单无条件挂起
     */
    public static final String ORDER_UP = "/order/hangUp";
    /**
     * 启动广告图
     */
    public static final String INFORM_BOOT_PAGE = "/inform/bootPage";

    /**
     * 扫二维码
     */
    public static final String SCAN_CODE = "/scan/code";

    /**
     * 查看二维码信息（判断二维码是否存在）
     */
    public static final String SCAN_CODE_INFO = "/scan/codeInfo";

    /**
     * 二维码绑定设备信息
     */
    public static final String SCAN_CODE_ADD_DEVICE_INFO = "/scan/addDeviceInfo";

    /**
     * 查询维修师资料
     */
    public static final String POST_MAINTENANCE_INFO = "/user/maintenanceComment";

    /**
     * 查询工程师评价信息
     */
    public static final String GET_COMMENT_LIST = "/order/comment/list";

    /**
     * 查询配件
     */
    public static final String GET_PARTS_LIST ="/after/sale/getPartsList";

    /**
     * 查询厂家售后订单完成时的提示语
     */
    public static final String GET_FINISH_MSG = "/order/finishMsg";

    /**
     * 获取配件库类型
     */
    public static final String GET_ACCESSORIES_TYPES = "/accessories/getAccessoriesTypes";

    /**
     * 搜索配件库
     */
    public static final String GET_ACCESSORIES_ALL = "/accessories/getAccessoriesAll";

    /**
     * 获取配件详情
     */
    public static final String GET_ACCESSORIES = "/accessories/getAccessories";

    /**
     * 获取配件筛选
     */
    public static final String GET_ACCESSORIESTYPEALL = "/accessories/getAccessoriesTypeAll";

    /**
     * 查询轮播广告图
     */
    public static final String GET_ADVERTISING = "/information/advertising";

    /**
     * 充值
     */
    public static final String GET_WEBAPPPAY_APPPAY = "/main/webAppPay/appPay";

    public static final String GET_PAY_RECHARGE = "/pay/recharge";

    /**
     * 获取品牌数据
     */
    public static final String GET_SCAN_GETBRANDS = "/scan/getBrands";

    /**
     * 获取品牌型号数据
     */
    public static final String GET_SCAN_GETMODELS = "/scan/getModels";


    /**
     * 知识图谱首页
     */
    public static final String GET_KNOWLEDGE_INDEX = "/knowledge/index";

    /**
     * 知识图谱首页--新搜索
     */
    public static final String GET_KNOWLEDGE_SEARCH_SIMPLE = "/knowledge/search/simple";

    /**
     * 知识图谱详情
     */
    public static final String GET_KNOWLEDGE_DETAILS = "/knowledge/details";

    /**
     * 知识图谱筛选条件
     */
    public static final String GET_KNOWLEDGE_GETKNOWLEDGETYPEALL = "/knowledge/getKnowledgeTypeAll";

    /**
     * 报废二维码设备信息
     */
    public static final String GET_SCAN_SCRAPDEVICEINFO = "/scan/scrapDeviceInfo";

    /**
     * 修改二维码设备信息
     */
    public static final String GET_SCAN_UPDATEDEVICEINFO = "/scan/updateDeviceInfo";

    /**
     * 报销二维码设备信息
     */
    public static final String GET_SCAN_DESTRUCTIONDEVICEINFO = "/scan/destructionDeviceInfo";

    /**
     * 收费标准
     */
    public static final String GET_INFORMATION_CHARGINGSTANDARD = "/information/chargingStandard";

    /**
     * 获取绑定二维码的设备类型
     */
    public static final String SCAN_GETDEVICETYPES = "/scan/getDeviceTypes";


    /**
     * 文章和提问列表
     */
    public static final String GET_KNOWLEDGE_MAINTENANCEINDEX = "/knowledge/maintenanceIndex";

    /**
     * 获取搜索关键词
     */
    public static final String GET_KNOWLEDGE_KNOWLEDGEKEYWORDRANK = "/knowledge/knowledgeKeywordRank";

    /**
     * 工程师发表问题或提问
     */
   public static final String GET_KNOWLEDGE_KNOWLEDGEPUBLISH = "/knowledge/knowledgePublish";

    /**
     * 编辑文章和提问
 */
   public static final String GET_KNOWLEDGE_UPDATEPUBLISH = "/knowledge/updatePublish";

    /**
     * 工程师注册
     */
    public static final String COMPANY_ADDBUSSINESS = "/company/addBussiness";

    /**
     * 知识库下载
     */
    public static final String KNOWLEDGE_LOGIN = "/knowledge/login";

    /**
     * 扫描租赁二维码
     */
    public static final String SCAN_LEASECODE = "/scan/leaseCode";

    /**
     * 获取绑定租赁二维码设备类型
     */
    public static final String SCAN_GETLEASEDEVICETYPES = "/scan/getLeaseDeviceTypes";

    /**
     *  获取租赁设备品牌
     */
    public static final String SCAN_GETLEASEBRANDS = "/scan/getLeaseBrands";

    /**
     *  获取租赁设备品牌型号
     */
    public static final String SCAN_GETLEASEMODELS = "/scan/getLeaseModels";

    /**
     *  租赁二维码添加设备信息
     */
    public static final String SCAN_ADDDEVICEINFO = "/scan/addLeaseDeviceInfo";

    /**
     *  获得租赁二维码设备信息
     */
    public static final String SCAN_CODELEASEINFO = "/scan/codeLeaseInfo";


    /**
     *  问答模块---重新编辑问题
     */
   // public static final String QUESION_UPDATEPUBLISH = "/quesion/updatePublish";

    /**
     *  问答模块---工程师提问
     */
    public static final String QUESION_KNOWLEDGEPUBLISH = "/quesion/knowledgePublish";

    /**
     *  问答模块---问题详情
     */
    public static final String QUESION_DETAILS = "/quesion/details";


    /**
     *  问答模块---问答首页
     */
    public static final String QUESION_INDEX = "/quesion/index";

    /**
     *  问答模块---问答首页(新搜索)
     */
    public static final String QUESION_SEARCH_SIMPLE = "/quesion/search/simple";

    /**
     *  问答模块---我的提问
     */
    public static final String QUESION_MAINTENANCEQUESION = "/quesion/maintenanceQuesion";

    /**
     *  问答模块---我的回答
     */
    public static final String QUESION_MAINTENANCEANSWER = "/quesion/maintenanceAnswer";

    /**
     *  问答模块---回答问题
     */
    public static final String QUESION_QUESIONANSWER = "/quesion/quesionAnswer";
    /**
     *  问答模块---回答问题
     */
    public static final String QUESION_ANSWERUPDATE = "/quesion/answerUpdate";

 /**
     *  问答模块--采纳答案
     */
    public static final String QUESION_SELECTANSWER = "/quesion/selectAnswer";

    /**
     * 问题搜索历史
     */
    public static final String GET_QUESION_KNOWLEDGEKEYWORDRANK = "/quesion/knowledgeKeywordRank";
}

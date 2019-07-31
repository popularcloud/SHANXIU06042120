package com.lwc.shanxiu.controler.http;

import com.lwc.shanxiu.configs.ServerConfig;

/**
 * 网络请求管理类
 *
 * @author 何栋
 * @version 1.0
 * @date 2017/3/9 09:16
 * @email 294663966@qq.com
 */
public class NetManager {

    /**
     * 主机名称
     */
//    public final static String HOST = "http://www.lsh-sd.com:9999";
    //测试
//    public final static String HOST = "http://liyifu.ngrok.cc";
//    public static String HOST = "http://183.60.143.79:8090";
    public static String HOST = ServerConfig.SERVER_API_URL;
//    public static String HOST = "http://119.23.215.51";


    /**
     * 获取请求的url;
     *
     * @param key
     * @return
     */
    public static String getUrl(String key) {
        return HOST + key;
    }
}

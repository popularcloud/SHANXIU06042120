package com.lwc.shanxiu.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.lwc.shanxiu.controler.global.GlobalValue;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * SharedPreferences 工具操作类
 * @Description TODO
 * @author 何栋
 * @date 2017年09月09日
 * @Copyright: lwc
 */
public class SharedPreferencesUtils {
    public static final String SP_NAME = "global_data";
    private static final String TOKEN = "token";
    private static final String LOGIN_INFO = "login_info";
    private static final String LOGIN_STATUS = "login_status";
    public static SharedPreferencesUtils sharedPreferencesUtils;
    private static SharedPreferences sp;


    public static SharedPreferencesUtils getInstance(Context context) {
        if (sharedPreferencesUtils == null)
            sharedPreferencesUtils = new SharedPreferencesUtils(context);
        return sharedPreferencesUtils;
    }

    private SharedPreferencesUtils(Context context) {
        sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 保存数据
     *
     * @param serializable
     */
    public void saveObjectData(Serializable serializable) {
        sp.edit()
                .putString(serializable.getClass().getName(),
                        DecodeUtils.encode(JsonUtil.parserObjectToGson(serializable))).commit();
    }

    /**
     * 读取数据
     *
     * @param clazz
     * @return
     */
    public <T> T loadObjectData(Class<T> clazz) {

        String json = sp.getString(clazz.getName(), "");
        if (!TextUtils.isEmpty(json)) {
            return JsonUtil.parserGsonToObject(DecodeUtils.decode(json), clazz);
        } else {
            return null;
        }
    }

    /**
     * 删除指定对象信息
     *
     * @param clazz
     */
    public <T> void deleteAppointObjectData(Class<T> clazz) {
        sp.edit().remove(clazz.getName()).commit();
    }

    /**
     * 删除指定的boolean值
     *
     * @param key
     */
    public void delectAppointBooleanData(String key) {
        sp.edit().remove(key).commit();
    }

    /**
     * 保存boolean值
     *
     * @param key
     * @param value
     */
    public void saveBooleanData(String key, boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 读取boolean值
     *
     * @param key
     * @return
     */
    public boolean loadBooleanData(String key) {
        return sp.getBoolean(key, false);
    }

    /**
     * 删除指定的String值
     *
     * @param key
     */
    public void delectString(String key) {
        sp.edit().remove(key).commit();
    }

    /**
     * 删除所有的数据
     */
    public void deleteAllData() {
        sp.edit().clear().commit();
    }

    /**
     * 保存token
     *
     * @param token
     */
    public void saveToken(String token) {
        sp.edit().putString(TOKEN, DecodeUtils.encode(token)).commit();
    }

    /**
     * 读取token
     *
     * @return
     */
    public String loadToken() {
        return DecodeUtils.decode(sp.getString(TOKEN, ""));
    }

    /**
     * 清除token
     *
     * @return
     */
    public void deleteToken() {
        sp.edit().remove(TOKEN);
    }

    /**
     * 保存登录的账号密码
     *
     * @param mobile
     * @param password
     */
    public void saveMobileAndPassword(String mobile, String password) {
        sp.edit().putString(LOGIN_INFO, DecodeUtils.encode(mobile) + "@" + DecodeUtils.encode(password)).commit();
    }

    /**
     * 读取账号密码
     *
     * @return
     */
    public String[] loadMobileAndPassword() {
        String[] s = null;
        String string = sp.getString(LOGIN_INFO, "");
        if (!TextUtils.isEmpty(string) && string.contains("@")) {
            s = string.split("@");
            return new String[]{DecodeUtils.decode(s[0]), DecodeUtils.decode(s[1])};
        }
        return s;
    }

    /**
     * 保存string
     *
     * @param key
     * @param value
     */
    public void saveString(String key, String value) {
        sp.edit().putString(key, DecodeUtils.encode(value)).commit();
    }


    /**
     * 读取string
     *
     * @param key
     * @return
     */
    public String loadString(String key) {
        String string = sp.getString(key, "");
        if (!TextUtils.isEmpty(string)) {
            return DecodeUtils.decode(string);
        } else {
            return null;
        }
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void setParam(Context context, String key, Object object) {

        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if ("String".equals(type)) {
            editor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) object);
        }

        SharedPreferencesCompat.apply(editor);
    }


    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object getParam(Context context, String key, Object defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);

        if ("String".equals(type)) {
            return sp.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }
}

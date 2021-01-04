package com.lwc.shanxiu.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * json的解析,用Gson,fastJson
 * 
 * @author 何栋
 * 
 */
public class JsonUtil {

	/**
	 * 把对象转成json字符串
	 * 
	 * @param t
	 * @return
	 */
	public static <T> String parserObjectToGson(T t) {
		return (new Gson()).toJson(t);
	}

	/**
	 * 根据指定key提取json字符串
	 * 
	 * @param json
	 * @param key
	 * @return String or "";
	 */
	public static String getGsonValueByKey(String json, String key) {
		String value = "";
		try {
			value = (new JSONObject(json)).optString(key);
		} catch (Exception e) {
			return "";
		}
		return value;
	}

	/**
	 * 解析字符串,返回对象
	 * 
	 * @param <T>
	 * @param json
	 * @param classOft
	 * @return Object
	 */
	public static <T> T parserGsonToObject(String json, Class<T> classOft) {
		return (new Gson()).fromJson(json, classOft);
	}

	/**
	 * 
	 * 解析字符串,返回list对象
	 * 
	 * 
	 * 
	 * @param json
	 * 
	 * @param typeToken
	 * 
	 *            new TypeToken<List<T>>(){}
	 * 
	 * @return List<T> or null;
	 */
	public static <T> ArrayList<T> parserGsonToArray(String json,
			TypeToken<ArrayList<T>> typeToken) {
		return (new Gson()).fromJson(json, typeToken.getType());
	}

	/**
	 * 解析字符串,返回map对象
	 * 
	 * @param json
	 * @param typeToken
	 * @return
	 */
	public static <T, V> HashMap<T, V> parserGsonToMap(String json,
			TypeToken<HashMap<T, V>> typeToken) {
		return (new Gson()).fromJson(json, typeToken.getType());
	}

	/**
	 * 解析字符串,返回map对象
	 *
	 * @param json
	 * @param typeToken
	 * @return
	 */
	public static <T, V> LinkedHashMap<T, V> parserGsonToSortMap(String json,
															 TypeToken<LinkedHashMap<T, V>> typeToken) {
		return (new Gson()).fromJson(json, typeToken.getType());
	}

}

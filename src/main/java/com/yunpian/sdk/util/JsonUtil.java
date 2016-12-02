package com.yunpian.sdk.util;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * json格式化工具
 */
public class JsonUtil {
	private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	public static final Type TypeMapObject = new TypeToken<HashMap<String, Object>>() {
	}.getType();

	public static final Type TypeMapString = new TypeToken<HashMap<String, String>>() {
	}.getType();

	public static <T> T fromJson(String json, Class<T> clazz) {
		if (json == null || "".equals(json))
			return null;
		return gson.fromJson(json, clazz);
	}

	public static String toJson(Object obj) {
		return gson.toJson(obj);
	}

	public static Map<String, String> fromJsonToMap(String json) {
		return gson.fromJson(json, TypeMapString);
	}

	public static <T> T fromJsonToMap(Reader json, Type type) {
		return gson.<T>fromJson(json, type);
	}

	public static <T> T fromJson(String json, Type t) {
		return gson.fromJson(json, t);
	}

	public static <T> T fromJson(Reader json, Type t) {
		return gson.fromJson(json, t);
	}

}

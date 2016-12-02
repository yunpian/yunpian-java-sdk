package com.yunpian.sdk.util;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

/**
 * json格式化工具
 */
public class JsonUtil {
	private static final Gson Gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	// private static final Type TypeMap = new TypeToken<Map<String, String>>()
	// {
	// }.getType();

	public static <T> T fromJson(String json, Class<T> clazz) {
		if (json == null || "".equals(json))
			return null;
		return Gson.fromJson(json, clazz);
	}

	public static String toJson(Object obj) {
		return Gson.toJson(obj);
	}

	/**
	 * TODO 优化
	 * 
	 * @param json
	 * @return
	 * @throws IOException
	 */
	public static Map<String, String> fromJsonToMap(String json) throws IOException {
		return toMapStr(json);
	}

	public static <T> T fromJsonToMap(Reader json, Type type) {
		return Gson.<T>fromJson(json, type);
	}

	public static <T> T fromJson(String json, Type t) {
		return Gson.fromJson(json, t);
	}

	public static <T> T fromJson(Reader json, Type t) {
		return Gson.fromJson(json, t);
	}

	private static Map<String, String> toMapStr(String json) throws IOException {
		try (JsonReader in = new JsonReader(new StringReader(json))) {
			JsonToken token = in.peek();
			if (token.equals(JsonToken.BEGIN_OBJECT)) {
				Map<String, String> map = new LinkedHashMap<>();
				in.beginObject();
				while (in.hasNext()) {
					map.put(in.nextName(), read2Str(in).toString());
				}
				in.endObject();
				return map;
			}
			return Collections.emptyMap();
		}
	}

	private static Object read2Str(JsonReader in) throws IOException {
		JsonToken token = in.peek();
		switch (token) {
		case BEGIN_ARRAY:
			List<Object> list = new ArrayList<Object>();
			in.beginArray();
			while (in.hasNext()) {
				list.add(read2Str(in));
			}
			in.endArray();
			return JsonUtil.toJson(list);

		case BEGIN_OBJECT:
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			in.beginObject();
			while (in.hasNext()) {
				map.put(in.nextName(), read2Str(in));
			}
			in.endObject();
			return JsonUtil.toJson(map);

		case STRING:
			return in.nextString();

		case NUMBER:
			return in.nextString();

		case BOOLEAN:
			return in.nextBoolean();

		case NULL:
			in.nextNull();
			return "";
		default:
			return in.nextString();
		}
	}

}

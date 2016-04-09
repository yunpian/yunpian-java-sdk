package com.yunpian.sdk.util;

import java.io.IOException;
import java.lang.reflect.Type;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.yunpian.sdk.model.UserInfo;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by bingone on 15/11/6.
 */
public class JsonUtil {
    private static final Gson gson =
        new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public static <T> T fromJson(String json, Class clazz) {
        return (T) gson.fromJson(json, clazz);
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static Map<String, String> fromJsonToMap(String json) {
        // CHECKSTYLE:OFF
        Type t = new TypeToken<Map<String, String>>() {
        }.getType();
        // CHECKSTYLE:ON
        return gson.fromJson(json, t);
    }

    public static <T> T fromJson(String json, Type t) {
        return gson.fromJson(json, t);
    }


}

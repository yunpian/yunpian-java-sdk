package com.yunpian.sdk.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.yunpian.sdk.YunpianException;

/**
 * Created by bingone on 15/11/8.
 */
@Deprecated
public class Convert {
	public static Map<String, Object> ConvertObjToMap(Object obj) throws YunpianException {
		Map<String, Object> reMap = new HashMap<String, Object>();
		if (obj == null)
			return null;
		Field[] fields = obj.getClass().getDeclaredFields();
		try {
			for (int i = 0; i < fields.length; i++) {
				try {
					Field f = obj.getClass().getDeclaredField(fields[i].getName());
					f.setAccessible(true);
					Object o = f.get(obj);
					reMap.put(fields[i].getName(), o);
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
					throw new YunpianException(e);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					throw new YunpianException(e);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					throw new YunpianException(e);
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new YunpianException(e);
		}
		return reMap;
	}
}

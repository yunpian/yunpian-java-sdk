package com.yunpian.sdk.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by bingone on 16/1/18.
 */
public class SignUtil {
    public static String getSign(Map<String, String> parms, String secret) {
        Map<String, String> treeMap = new TreeMap<String, String>();
        for (Map.Entry<String, String> entry : parms.entrySet()) {
            if (!"_sign".equals(entry.getKey())) {
                treeMap.put(entry.getKey(), entry.getValue());
            }
        }
        treeMap.put("api_secret", TeaUtil.getApiSecret(secret));
        String _sign = DigestUtils.md5Hex(StringUtil.join(treeMap.values(), ","));
        return _sign;
    }

    public static void main(String[] args) {
        System.out.println(getSign(new HashMap<String, String>() {{
            put("mobile", "13000000000");
            put("text", "【yunpian】您的验证码是4444");
        }}, "12345678"));
    }
}

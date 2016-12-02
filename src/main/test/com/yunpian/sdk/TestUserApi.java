/**
 * 
 */
package com.yunpian.sdk;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.yunpian.sdk.api.UserApi;
import com.yunpian.sdk.constants.YunpianConstants;
import com.yunpian.sdk.model.UserInfo;
import com.yunpian.sdk.util.JsonUtil;

public class TestUserApi implements YunpianConstants {

	private YunpianClient clnt;

	@Before
	public void init() {
		String apikey = "";
		clnt = new YunpianClient(apikey).init();
	}

	@Test
	public void getTest() {

	}

	@Test
	public void map2userTest() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(NICK, "dzh");
		map.put(GMT_CREATED, "2016-11-25 12:22:39");
		map.put(MOBILE, "18616020610");
		map.put(EMAIL, "dzh@qq.com");
		map.put(IP_WHITELIST, "127.0.0.1");
		map.put(API_VERSION, "v2");
		map.put(ALARM_BALANCE, "0");
		map.put(EMERGENCY_CONTACT, "xxx");
		map.put(EMERGENCY_MOBILE, "yyy");
		map.put(BALANCE, "1");
		UserInfo user = UserApi.map2User(map);
		String json = JsonUtil.toJson(user);
		System.out.println(json);

		map = JsonUtil.fromJsonToMap(json);
		System.out.println(map.toString());
	}

	@After
	public void close() {
		clnt.close();
	}

}

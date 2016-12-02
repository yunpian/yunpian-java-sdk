/**
 * 
 */
package com.yunpian.sdk.api;

import java.util.Map;

import org.junit.Test;

import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.UserInfo;

/**
 * 账户API
 * 
 * @author dzh
 * @date Dec 3, 2016 12:09:10 AM
 * @since 1.2.0
 */
public class TestUserApi extends TestYunpianClient {

	@Test
	public void getTest() {
		Result<UserInfo> r = null;

		r = clnt.user().get();
		System.out.println(r);

		r = ((UserApi) clnt.user().version(VERSION_V1)).get();
		System.out.println(r);
	}

	@Test
	public void setTest() {
		Map<String, String> param = clnt.newParam(2);
		param.put(EMERGENCY_CONTACT, "dzh");
		param.put(EMERGENCY_MOBILE, "18610101010");
		param.put(ALARM_BALANCE, "10");
		Result<UserInfo> r = clnt.user().set(param);
		System.out.println(r);

		r = ((UserApi) clnt.user().version(VERSION_V1)).set(param);
		System.out.println(r);
	}

}

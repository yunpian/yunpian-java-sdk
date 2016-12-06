/**
 * 
 */
package com.yunpian.sdk.api;

import java.util.Map;

import org.junit.Test;

import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.Sign;

/**
 * 短信签名功能
 * 
 * https://www.yunpian.com/api2.0/api-domestic/sign_add.html
 * 
 * @author dzh
 * @date Dec 5, 2016 10:27:25 AM
 * @since 1.2.0
 */
public class TestSignApi extends TestYunpianClient {

	@Test
	public void addTest() {
		Map<String, String> param = clnt.newParam(5);
		param.put(SIGN, "你好吗");
		// param.put(NOTIFY, "true");
		// param.put(APPLYVIP, "false");
		// param.put(ISONLYGLOBAL, "false");
		// param.put(INDUSTRYTYPE, "other");
		Result<Sign> r = clnt.sign().add(param);
		System.out.println(r);
	}

}

package com.yunpian.sdk.api;

import java.util.Map;

import org.junit.Test;

import com.yunpian.sdk.model.CallBind;
import com.yunpian.sdk.model.Result;

/**
 * 隐私通话接口
 * 
 * https://www.yunpian.com/api2.0/api-anonymous.html
 * 
 * @author dzh
 * @date Dec 4, 2016 6:03:54 PM
 * @since 1.2.0
 */
public class TestCallApi extends TestYunpianClient {

	@Test
	public void bindTest() {
		Map<String, String> param = clnt.newParam(2);
		param.put(APIKEY, TESTKEY);
		param.put(FROM, "18616020610");
		param.put(TO, "13917162015");
		param.put(DURATION, "60");
		param.put(AREA_CODE, "+8621");
		Result<CallBind> r = clnt.call().bind(param);
		System.out.println(r);
	}

}

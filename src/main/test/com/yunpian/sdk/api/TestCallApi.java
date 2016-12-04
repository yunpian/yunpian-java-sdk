package com.yunpian.sdk.api;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.yunpian.sdk.model.CallBill;
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
		Map<String, String> param = clnt.newParam(4);
		param.put(FROM, "11111111111");
		param.put(TO, "22222222222");
		param.put(DURATION, "600");
		// param.put(AREA_CODE, "+8621");
		Result<CallBind> r = clnt.call().bind(param);
		System.out.println(r);
	}

	@Test
	public void unbindTest() {
		Map<String, String> param = clnt.newParam(3);
		param.put(FROM, "11111111111");
		param.put(TO, "22222222222");
		// param.put(DURATION, "600");
		Result<?> r = clnt.call().unbind(param);
		System.out.println(r);
	}

	@Test
	public void pullTest() {
		Map<String, String> param = clnt.newParam(0);
		// param.put(PAGE_SIZE, "20");
		Result<List<CallBill>> r = clnt.call().pull(param);
		System.out.println(r);
	}

}

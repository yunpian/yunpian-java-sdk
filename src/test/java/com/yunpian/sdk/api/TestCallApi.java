package com.yunpian.sdk.api;

import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import com.yunpian.sdk.model.CallBill;
import com.yunpian.sdk.model.CallBind;
import com.yunpian.sdk.model.CallRecord;
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
	@Ignore
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
	@Ignore
	public void unbindTest() {
		Map<String, String> param = clnt.newParam(3);
		param.put(FROM, "11111111111");
		param.put(TO, "22222222222");
		// param.put(DURATION, "600");
		Result<?> r = clnt.call().unbind(param);
		System.out.println(r);
	}

	@Test
	@Ignore
	public void pullTest() {
		Map<String, String> param = clnt.newParam(0);
		// param.put(PAGE_SIZE, "20");
		Result<List<CallBill>> r = clnt.call().pull(param);
		System.out.println(r);
	}

	@Test
	@Ignore
	public void recordTest() {
		Map<String, String> param = clnt.newParam(3);
		param.put(MESSAGE_ID, "dCdDHzED75g08q4h3JscK6wU");
		param.put(START_TIME, "1480310078000");
		param.put(END_TIME, "1485333948452");
		// param.put(PAGE_NUM, "1");
		// param.put(PAGE_SIZE, "10");

		Result<List<CallRecord>> r = clnt.call().record(param);
		System.out.println(r);
	}

	@Test
	@Ignore
	public void getRecordTest() {
		Map<String, String> param = clnt.newParam(2);
		param.put(MESSAGE_ID, "dCdDHzED75vmqngj5LAvL6");
		param.put(RECORD_ID, "5888660c70000fe15001");

		Result<Void> r = clnt.call().get_record(param, "/Users/dzh/temp/a.wav");
		System.out.println(r);
	}

}

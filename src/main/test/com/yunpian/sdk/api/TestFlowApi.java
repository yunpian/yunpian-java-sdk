package com.yunpian.sdk.api;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.yunpian.sdk.model.FlowPackage;
import com.yunpian.sdk.model.FlowSend;
import com.yunpian.sdk.model.FlowStatus;
import com.yunpian.sdk.model.Result;

/**
 * 手机流量API
 * 
 * https://www.yunpian.com/api2.0/api-flow.html
 * 
 * 
 * @author dzh
 * @date Dec 4, 2016 8:48:08 PM
 * @since 1.2.0
 */
public class TestFlowApi extends TestYunpianClient {

	@Test
	public void get_packageTest() {
		Map<String, String> param = clnt.newParam(1);
		// param.put(CARRIER, "10010");
		Result<List<FlowPackage>> r = clnt.flow().get_package(param);
		System.out.println(r);

		// r = ((FlowApi) clnt.flow().version(VERSION_V1)).get_package(param);
		// System.out.println(r);
	}

	@Test
	public void rechargeTest() {
		Map<String, String> param = clnt.newParam(3);
		param.put(MOBILE, "11111111111");
		param.put(SN, "1008601");
		// param.put(CALLBACK_URL, "http://your_receive_url_address");
		Result<FlowSend> r = clnt.flow().recharge(param);
		System.out.println(r);

		// r = ((FlowApi) clnt.flow().version(VERSION_V1)).recharge(param);
		// System.out.println(r);
	}

	@Test
	public void pull_statusTest() {
		Map<String, String> param = clnt.newParam(2);
		param.put(MOBILE, "11111111111");
		// param.put(PAGE_SIZE, "20");
		Result<List<FlowStatus>> r = clnt.flow().pull_status(param);
		System.out.println(r);

		// r = ((FlowApi) clnt.flow().version(VERSION_V1)).pull_status(param);
		// System.out.println(r);
	}

}

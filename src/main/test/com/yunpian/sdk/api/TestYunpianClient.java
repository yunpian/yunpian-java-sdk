/**
 * 
 */
package com.yunpian.sdk.api;

import org.junit.After;
import org.junit.Before;

import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.constant.YunpianConstant;

/**
 * @author dzh
 * @date Nov 18, 2016 7:58:27 PM
 * @since 1.2.0
 */
public class TestYunpianClient implements YunpianConstant {

	static final String TESTKEY = "79a8b86cdd26312cfabb48267da86599";

	YunpianClient clnt;

	@Before
	public void init() {
		clnt = new YunpianClient(TestYunpianClient.TESTKEY,
				TestYunpianClient.class.getResourceAsStream("yunpian.properties")).init();
	}

	@After
	public void close() {
		clnt.close();
	}

}
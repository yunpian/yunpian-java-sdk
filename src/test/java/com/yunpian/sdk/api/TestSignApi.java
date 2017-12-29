/**
 *
 */
package com.yunpian.sdk.api;

import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.Sign;
import com.yunpian.sdk.model.SignRecord;
import org.junit.Test;

import java.util.Map;

/**
 * 短信签名功能
 * <p>
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
		param.put(NOTIFY, "true");
		param.put(APPLYVIP, "false");
		param.put(ISONLYGLOBAL, "false");
		param.put(INDUSTRYTYPE, "其它");
		param.put(LICENSE_URL, "https://www.yunpian.com/");
		Result<Sign> r = clnt.sign().add(param);
		System.out.println(r);
	}

	@Test
	public void updateTest() {
		Map<String, String> param = clnt.newParam(5);
		param.put(OLD_SIGN, "你好吗");
		param.put(SIGN, "我很好");
		param.put(NOTIFY, "true");
		param.put(APPLYVIP, "false");
		param.put(ISONLYGLOBAL, "false");
		param.put(INDUSTRYTYPE, "其它");
		param.put(LICENSE_URL, "https://www.yunpian.com/");
		Result<Sign> r = clnt.sign().update(param);
		System.out.println(r);
	}

	@Test
	public void getTest() {
		Map<String, String> param = clnt.newParam(5);
		param.put(SIGN, "");
		param.put(PAGE_NUM, "1");
		param.put(PAGE_SIZE, "3");
		Result<SignRecord> r = clnt.sign().get(param);
		System.out.println(r);
	}

}

package com.yunpian.sdk.service;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.yunpian.sdk.model.FlowPackage;
import com.yunpian.sdk.model.FlowStatus;
import com.yunpian.sdk.model.ResultDO;
import com.yunpian.sdk.model.SmsBatchSend;
import com.yunpian.sdk.model.FlowSend;
import com.yunpian.sdk.model.SmsSingleSend;
import com.yunpian.sdk.model.VoiceSend;
import com.yunpian.sdk.model.SmsReply;
import com.yunpian.sdk.model.SmsStatus;
import com.yunpian.sdk.model.Template;
import com.yunpian.sdk.model.User;
import com.yunpian.sdk.model.VoiceStatus;

/**
 * Created by bingone on 16/1/19.
 */
@Deprecated
public class YunpianRestTest {
	public static YunpianRestClient client;

	@BeforeClass
	public static void init() {
		client = new YunpianRestClient("327d7f6aca319be83cbcb92eb9b6eff6");

	}

	@Test
	public void testSendSms() throws UnsupportedEncodingException {
		YunpianRestClient client = new YunpianRestClient("your apikey");
		SmsOperator smsOperator = client.getSmsOperator();
		// 单条发送
		ResultDO<SmsSingleSend> r1 = smsOperator.singleSend("18210374138", "【云片网】您的验证码是1234");
		System.out.println(r1);
		// 批量发送
		ResultDO<SmsBatchSend> r2 = smsOperator.batchSend("13012312316,13112312312,123321,333,111",
				"【云片网】您的验证码是1234");
		System.out.println(r2);

		List<String> mobile = Arrays.asList("13012312321,13012312322,13012312323,130123123".split(","));
		List<String> text = Arrays.asList("【云片网】您的验证码是1234,【云片网】您的验证码是1234,【云片网】您的验证码是1234,【云片网】您的验证码是1234".split(","));
		// 个性化发送
		ResultDO<SmsBatchSend> r3 = smsOperator.multiSend(mobile, text);
		System.out.println(r3);

		// （不推荐使用）
		// String tpl_value = URLEncoder.encode("#code#", Config.ENCODING) + "="
		// + URLEncoder
		// .encode("1234", Config.ENCODING) + "&" +
		// URLEncoder.encode("#company#", Config.ENCODING)
		// + "=" + URLEncoder.encode("云片网", Config.ENCODING);
		// // tpl batch send
		// ResultDO<SendBatchSmsInfo> r4 =
		// smsOperator.tplBatchSend("13200000000,13212312312,123321,333,111",
		// "1", tpl_value);
		// System.out.println(r4);
		// // tpl single send
		// ResultDO<SendSingleSmsInfo> r5 =
		// smsOperator.tplSingleSend("15404450000", "1", tpl_value);
		// System.out.println(r5);
		// System.out.println(smsOperator.getRecord(new
		// Date(System.currentTimeMillis()),new
		// Date(System.currentTimeMillis()),"","",""));
		// System.out.println(smsOperator
		// .getRecord(new Date(System.currentTimeMillis()), new
		// Date(System.currentTimeMillis()),"","",""));

	}

	@Test
	public void testUser() {
		UserOperator userOperator = client.getUserOperator();
		ResultDO<User> resultDO = userOperator.get();
		System.out.println(resultDO);
	}

	@Test
	public void testTpl() {
		TplOperator tplOperator = client.getTplOperator();
		ResultDO<List<Template>> resultDO = tplOperator.getDefault();
		System.out.println(resultDO);
		resultDO = tplOperator.get();
		System.out.println(resultDO);
		resultDO = tplOperator.getDefault("2");
		System.out.println(resultDO);

		ResultDO<Template> result = tplOperator.add("【bbb】大倪#asdf#");
		System.out.println(result);
		resultDO = tplOperator.get(String.valueOf(result.getData().getTpl_id()));
		System.out.println(resultDO);
		result = tplOperator.update(String.valueOf(result.getData().getTpl_id()), "【aaa】大倪#asdf#");
		System.out.println(result);
		result = tplOperator.del(String.valueOf(result.getData().getTpl_id()));
		System.out.println(result);
	}

	@Test
	public void testFlow() {
		FlowOperator flowOperator = client.getFlowOperator();
		ResultDO<List<FlowPackage>> r1 = flowOperator.getPackage();
		System.out.println(r1);
		r1 = flowOperator.getPackage("10086");
		System.out.println(r1);

		ResultDO<FlowSend> r2 = flowOperator.recharge("18700000000", "1008601");
		System.out.println(r2);

		ResultDO<List<FlowStatus>> r3 = flowOperator.pullStatus();
		System.out.println(r3);

	}

	@Test
	public void testVoice() {
		VoiceOperator voiceOperator = client.getVoiceOperator();
		ResultDO<VoiceSend> resultDO = voiceOperator.send("18700000000", "4325");
		System.out.println(resultDO);
		ResultDO<List<VoiceStatus>> result = voiceOperator.pullStatus();
		System.out.println(result);
	}

	@Test
	public void testReport() {
		SmsOperator smsOperator = client.getSmsOperator();
		ResultDO<List<SmsReply>> r1 = smsOperator.pullReply();
		System.out.println(r1);
		ResultDO<List<SmsStatus>> r2 = smsOperator.pullStatus();
		System.out.println(r2);
	}
}

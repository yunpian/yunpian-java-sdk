package com.yunpian.sdk.api;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsBatchSend;
import com.yunpian.sdk.model.SmsReply;
import com.yunpian.sdk.model.SmsSingleSend;
import com.yunpian.sdk.model.SmsStatus;

/**
 * 短信API
 * 
 * https://www.yunpian.com/api2.0/api-domestic/single_send.html
 * 
 * @author dzh
 * @date Dec 5, 2016 12:33:49 PM
 * @since 1.2.0
 */
public class TestSmsApi extends TestYunpianClient {

    @Test
    public void single_sendTest() {
        Map<String, String> param = clnt.newParam(5);
        param.put(MOBILE, "11111111111");
        param.put(TEXT, "【云片网】您的验证码是1234");
        // param.put(EXTEND, "001");
        // param.put(UID, "10001");
        // param.put(CALLBACK_URL, "http://yourreceiveurl_address");
        Result<SmsSingleSend> r = clnt.sms().single_send(param);
        System.out.println(r);
    }

    @Test
    public void batch_sendTest() {
        Map<String, String> param = clnt.newParam(5);
        param.put(MOBILE, "11111111111");
        param.put(TEXT, "【云片网】您的验证码是1234");
        // param.put(EXTEND, "001");
        // param.put(UID, "10001");
        // param.put(CALLBACK_URL, "http://yourreceiveurl_address");
        Result<SmsBatchSend> r = clnt.sms().batch_send(param);
        System.out.println(r);
    }

    @Test
    public void multi_sendTest() {
        Map<String, String> param = clnt.newParam(5);
        param.put(MOBILE, "11111111111");
        param.put(TEXT, "【云片网】您的验证码是1234");
        // param.put(EXTEND, "001");
        // param.put(UID, "10001");
        // param.put(CALLBACK_URL, "http://yourreceiveurl_address");
        Result<SmsBatchSend> r = clnt.sms().multi_send(param);
        System.out.println(r);
    }

    @Test
    @Deprecated
    public void tpl_single_sendTest() {
        Map<String, String> param = clnt.newParam(5);
        param.put(MOBILE, "11111111111");
        param.put(TPL_ID, "1");
        param.put(TPL_VALUE, "#company#=云片网");
        // param.put(EXTEND, "001");
        // param.put(UID, "10001");
        Result<SmsSingleSend> r = clnt.sms().tpl_single_send(param);
        System.out.println(r);
    }

    @Test
    @Deprecated
    public void tpl_batch_sendTest() {
        Map<String, String> param = clnt.newParam(5);
        param.put(MOBILE, "11111111111");
        param.put(TPL_ID, "1");
        param.put(TPL_VALUE, "#company#=云片网");
        // param.put(EXTEND, "001");
        // param.put(UID, "10001");
        Result<SmsBatchSend> r = clnt.sms().tpl_batch_send(param);
        System.out.println(r);
    }

    @Test
    public void pull_statusTest() {
        Map<String, String> param = clnt.newParam(1);
        param.put(PAGE_SIZE, "20");
        Result<List<SmsStatus>> r = clnt.sms().pull_status(param);
        System.out.println(r);

        // r = ((SmsApi) clnt.sms().version(VERSION_V1)).pull_status(param);
        // System.out.println(r);
    }

    @Test
    public void pull_replyTest() {
        Map<String, String> param = clnt.newParam(1);
        param.put(PAGE_SIZE, "20");
        Result<List<SmsReply>> r = clnt.sms().pull_reply(param);
        System.out.println(r);

        // r = ((SmsApi) clnt.sms().version(VERSION_V1)).pull_reply(param);
        // System.out.println(r);
    }

    @Test
    public void get_recordTest() {
        Map<String, String> param = clnt.newParam(1);
        param.put(START_TIME, "2013-08-11 00:00:00");
        param.put(END_TIME, "2016-12-05 00:00:00");
        param.put(PAGE_NUM, "1");
        param.put(PAGE_SIZE, "20");
        // param.put(MOBILE, "11111111111");
        Result<List<SmsReply>> r = clnt.sms().pull_reply(param);
        System.out.println(r);

        r = ((SmsApi) clnt.sms().version(VERSION_V1)).pull_reply(param);
        System.out.println(r);
    }

    @Test
    public void get_black_wordTest() {
        Map<String, String> param = clnt.newParam(1);
        param.put(TEXT, "高利贷,发票");
        Result<List<String>> r = clnt.sms().get_black_word(param);
        System.out.println(r);

        // r = ((SmsApi) clnt.sms().version(VERSION_V1)).get_black_word(param);
        // System.out.println(r);
    }

    @Test
    @Deprecated
    public void sendTest() {
        Map<String, String> param = clnt.newParam(5);
        param.put(MOBILE, "11111111111");
        param.put(TEXT, "【云片网】您的验证码是1234");
        // param.put(EXTEND, "001");
        // param.put(UID, "10001");
        // param.put(CALLBACK_URL, "http://yourreceiveurl_address");
        Result<SmsSingleSend> r = ((SmsApi) clnt.sms().version(VERSION_V1)).send(param);
        System.out.println(r);
    }

    @Test
    public void get_replyTest() {
        Map<String, String> param = clnt.newParam(5);
        param.put(START_TIME, "2013-08-11 00:00:00");
        param.put(END_TIME, "2016-12-05 00:00:00");
        param.put(PAGE_NUM, "1");
        param.put(PAGE_SIZE, "20");
        // param.put(MOBILE, "11111111111");
        Result<List<SmsReply>> r = clnt.sms().get_reply(param);
        System.out.println(r);

        // r = ((SmsApi) clnt.sms().version(VERSION_V1)).get_reply(param);
        // System.out.println(r);
    }

    @Test
    @Deprecated
    public void tpl_sendTest() {
        Map<String, String> param = clnt.newParam(5);
        param.put(MOBILE, "11111111111");
        param.put(TPL_ID, "1");
        param.put(TPL_VALUE, "#company#=云片网");
        // param.put(EXTEND, "001");
        // param.put(UID, "10001");
        Result<SmsSingleSend> r = clnt.sms().tpl_send(param);
        System.out.println(r);
    }

    @Test
    public void countTest() {
        Map<String, String> param = clnt.newParam(5);
        param.put(START_TIME, "2013-08-11 00:00:00");
        param.put(END_TIME, "2016-12-05 00:00:00");
        param.put(PAGE_NUM, "1");
        param.put(PAGE_SIZE, "20");
        // param.put(MOBILE, "11111111111");
        Result<Integer> r = clnt.sms().count(param);
        System.out.println(r);

        r = ((SmsApi) clnt.sms().version(VERSION_V1)).count(param);
        System.out.println(r);
    }

}

package com.yunpian.sdk.api;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.VoiceSend;
import com.yunpian.sdk.model.VoiceStatus;

/**
 * 语音验证码API
 * 
 * https://www.yunpian.com/api2.0/api-voice.html
 * 
 * @author dzh
 * @date Dec 4, 2016 8:24:07 PM
 * @since 1.2.0
 */
public class TestVoiceApi extends TestYunpianClient {

    @Test
    public void sendTest() {
        Map<String, String> param = clnt.newParam(4);
        param.put(MOBILE, "11111111111");
        param.put(CODE, "123412");
        // param.put(CALLBACK_URL, "");
        // param.put(DISPLAY_NUM, "");
        Result<VoiceSend> r = clnt.voice().send(param);
        System.out.println(r);

        // r = ((VoiceApi) clnt.voice().version(VERSION_V1)).send(param);
        // System.out.println(r);
    }

    @Test
    public void pull_statusTest() {
        Map<String, String> param = clnt.newParam(1);
        // param.put(page_size, "20");
        Result<List<VoiceStatus>> r = clnt.voice().pull_status(param);
        System.out.println(r);

        // r = ((VoiceApi) clnt.voice().version(VERSION_V1)).pull_status(param);
        // System.out.println(r);
    }

}

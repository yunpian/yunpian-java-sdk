package com.yunpian.sdk.api;

import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.ShortUrl;
import org.junit.Test;

import java.util.Map;

/**
 * Created by liujie on 2017/9/29.
 */
public class TestShortUrlApi extends TestYunpianClient {

    @Test
    public void shortenTest() {
        Map<String, String> param = clnt.newParam(5);
        param.put(LONG_URL, "https://www.yunpian.com/");
        param.put(NAME, "sdk-test1");
        param.put(STAT_DURATION, "3");
        param.put(PROVIDER, "1");
        Result<ShortUrl> r = clnt.shortUrl().shorten(param);
        System.out.println(r);
    }

    @Test
    public void statTest() {
        Map<String, String> param = clnt.newParam(4);
        param.put(SID, "ckAclC");
        param.put(START_TIME, "2017-09-29 11:00:00");
//        param.put(END_TIME, "2017-09-29 11:20:00");
        Result<Map<String, Long>> r = clnt.shortUrl().stat(param);
        System.out.println(r);
    }
}

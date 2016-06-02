
# SDK使用指南

---
## Java
获取更多: [more information](https://github.com/yunpian/yunpian-java-sdk)
### 添加依赖包
1. 在maven工程中添加下列引用

```
<dependency>
	<groupId>com.yunpian.sdk</groupId>
    <artifactId>yunpian-java-sdk</artifactId>
    <version>1.1.2</version>
</dependency>
```

2. 下载jar包[]()

### 使用


```
import com.yunpian.sdk.common.Config;
import com.yunpian.sdk.model.*;
import com.yunpian.sdk.service.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by bingone on 16/1/19.
 */
public class YunpianRestTest {
    public static YunpianRestClient client;

    @BeforeClass public static void init() {
    //You can get the APIKEY from http://www.yunpian.com/ when log in.
        client = new YunpianRestClient("your apikey");

    }

    @Test public void testSendSms() throws UnsupportedEncodingException {
        YunpianRestClient client = new YunpianRestClient("your apikey");
        SmsOperator smsOperator = client.getSmsOperator();
        // 单条发送
        ResultDO<SendSingleSmsInfo> r1 =
            smsOperator.singleSend("18210374138", "【云片网】您的验证码是1234");
        System.out.println(r1);
        // 批量发送
        ResultDO<SendBatchSmsInfo> r2 =
            smsOperator.batchSend("13012312316,13112312312,123321,333,111", "【云片网】您的验证码是1234");
        System.out.println(r2);

        List<String> mobile =
            Arrays.asList("13012312321,13012312322,13012312323,130123123".split(","));
        List<String> text = Arrays.asList(
            "【云片网】您的验证码是1234,【云片网】您的验证码是1234,【云片网】您的验证码是1234,【云片网】您的验证码是1234"
                .split(","));
        // 个性化发送
        ResultDO<SendBatchSmsInfo> r3 = smsOperator.multiSend(mobile, text);
        System.out.println(r3);

//        （不推荐使用）
//        String tpl_value = URLEncoder.encode("#code#", Config.ENCODING) + "=" + URLEncoder
//            .encode("1234", Config.ENCODING) + "&" + URLEncoder.encode("#company#", Config.ENCODING)
//            + "=" + URLEncoder.encode("云片网", Config.ENCODING);
//        // tpl batch send
//        ResultDO<SendBatchSmsInfo> r4 =
//            smsOperator.tplBatchSend("13200000000,13212312312,123321,333,111", "1", tpl_value);
//        System.out.println(r4);
//        // tpl single send
//        ResultDO<SendSingleSmsInfo> r5 =
//            smsOperator.tplSingleSend("15404450000", "1", tpl_value);
//        System.out.println(r5);
//        System.out.println(smsOperator.getRecord(new Date(System.currentTimeMillis()),new Date(System.currentTimeMillis()),"","",""));
//        System.out.println(smsOperator
//            .getRecord(new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()),"","",""));

    }

    @Test public void testUser() {
        UserOperator userOperator = client.getUserOperator();
        ResultDO<UserInfo> resultDO = userOperator.get();
        System.out.println(resultDO);
    }



    @Test public void testTpl() {
        TplOperator tplOperator = client.getTplOperator();
        ResultDO<List<TemplateInfo>> resultDO = tplOperator.getDefault();
        System.out.println(resultDO);
        resultDO = tplOperator.get();
        System.out.println(resultDO);
        resultDO = tplOperator.getDefault("2");
        System.out.println(resultDO);


        ResultDO<TemplateInfo> result = tplOperator.add("【bbb】大倪#asdf#");
        System.out.println(result);
        resultDO = tplOperator.get(String.valueOf(result.getData().getTpl_id()));
        System.out.println(resultDO);
        result = tplOperator.update(String.valueOf(result.getData().getTpl_id()), "【aaa】大倪#asdf#");
        System.out.println(result);
        result = tplOperator.del(String.valueOf(result.getData().getTpl_id()));
        System.out.println(result);
    }

    @Test public void testFlow() {
        FlowOperator flowOperator = client.getFlowOperator();
        ResultDO<List<FlowPackageInfo>> r1 = flowOperator.getPackage();
        System.out.println(r1);
        r1 = flowOperator.getPackage("10086");
        System.out.println(r1);

        ResultDO<SendFlowInfo> r2 = flowOperator.recharge("18700000000", "1008601");
        System.out.println(r2);

        ResultDO<List<FlowStatusInfo>> r3 = flowOperator.pullStatus();
        System.out.println(r3);

    }

    @Test public void testVoice() {
        VoiceOperator voiceOperator = client.getVoiceOperator();
        ResultDO<SendVoiceInfo> resultDO = voiceOperator.send("18700000000", "4325");
        System.out.println(resultDO);
        ResultDO<List<VoiceStatusInfo>> result = voiceOperator.pullStatus();
        System.out.println(result);
    }
    @Test
    public void testReport(){
        SmsOperator smsOperator = client.getSmsOperator();
        ResultDO<List<SmsReplyInfo>> r1 = smsOperator.pullReply();
        System.out.println(r1);
        ResultDO<List<SmsStatusInfo>> r2 = smsOperator.pullStatus();
        System.out.println(r2);
    }
}

```




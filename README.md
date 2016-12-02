yunpian-java-sdk
==========================================


# Quick Start

## 使用YunpianClient

## 如何定义http配置

## 关于工程构建

# 联系方式

# 其他
- sdk说明
- api文档

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

### 使用


```
import com.yunpian.sdk.model.*;
import com.yunpian.sdk.service.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by bingone on 16/1/19.
 */
public class YunpianRestMainTest {

    /**
     * 更多内容请参考 <url>https://www.yunpian.com/api2.0/howto.html</url>
     */

    /**
     *
     * 如您第一次使用云片网，强烈推荐先看云片网络设置教程 <url>https://blog.yunpian.com/?p=94</url>
     *
     * 使用说明
     *
     * 1、登陆 <url>http://www.yunpian.com/</url> 获取APIKEY
     * 2、使用APIKEY生成YunpianRestClient
     * 3、获取需要的操作类SmsOperator/UserOperator/TplOperator/FlowOperator/VoiceOperator
     * 4、通过ResultDO<>来接收返回值，通过isSuccess()判断是否成功。具体可参考示例
     *
     * 返回值参考
     * <url>https://www.yunpian.com/api2.0/sms.html</url>
     * <url>https://www.yunpian.com/api2.0/record.html</url>
     */

    public static YunpianRestClient client = new YunpianRestClient("your apikey");


    public static void main(String... args) throws Exception {
        // 短信
        testSendSms();
        // 用户
        testUser();
        // 流量
//        testFlow();
        // 状态报告
        testReport();
        // 模板
        testTpl();
        // 语音
//        testVoice();
    }



    /**
     * 短信发送示例
     *
     * @throws Exception
     */
    public static void testSendSms() throws Exception {
        SmsOperator smsOperator = client.getSmsOperator();
        // 单条发送
        ResultDO<SendSingleSmsInfo> r1 = smsOperator.singleSend("18210374138", "【云片网】您的验证码是1234");
        System.out.println(r1);
        // 批量短信发送（批量发送的接口耗时比单号码发送长，如果需要更高并发速度，推荐使用single_send/tpl_single_send）
//        ResultDO<SendBatchSmsInfo> r2 = smsOperator
//            .batchSend("13012312324,13112312323,13112312322,13112312320,13112312321",
//                "【云片网】您的验证码是1234");
//        System.out.println(r2);


        //        （这个是个性化接口发送，批量发送的接口耗时比单号码发送长，如果需要更高并发速度，推荐使用single_send/tpl_single_send，不推荐使用）
        //        List<String> mobile =
        //            Arrays.asList("13012312330,13012312331,13012312332,13012312333".split(","));
        //        List<String> text = Arrays
        //            .asList("【云片网】您的验证码是1234,【云片网】您的验证码是1234,【云片网】您的验证码是1234,【云片网】您的验证码是1234".split(","));
        //        // 个性化发送
        //        ResultDO<SendBatchSmsInfo> r3 = smsOperator.multiSend(mobile, text);
        //        System.out.println(r3);


        //        （这个是模板接口发送，会因为一些特殊字符产生编码问题导致发送失败，不推荐使用）
        //        String tpl_value = URLEncoder.encode("#code#", Config.ENCODING) + "=" + URLEncoder
        //            .encode("1234", Config.ENCODING) + "&" + URLEncoder.encode("#company#", Config.ENCODING)
        //            + "=" + URLEncoder.encode("云片网", Config.ENCODING);
        //        // tpl batch send
        //        ResultDO<SendBatchSmsInfo> r4 =
        //            smsOperator.tplBatchSend("13012312340,13212312341,13012332142,13012312343, tpl_value);
        //        System.out.println(r4);
        //        // tpl single send
        //        ResultDO<SendSingleSmsInfo> r5 =
        //            smsOperator.tplSingleSend("15404450000", "1", tpl_value);
        //        System.out.println(r5);
        //        System.out.println(smsOperator.getRecord(new Date(System.currentTimeMillis()),new Date(System.currentTimeMillis()),"","",""));
        //        System.out.println(smsOperator
        //            .getRecord(new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()),"","",""));

    }

    /**
     * 获取用户信息示例
     */
    public static void testUser() {
        UserOperator userOperator = client.getUserOperator();
        ResultDO<UserInfo> resultDO = userOperator.get();
        System.out.println(resultDO);
    }


    /**
     * 模板类操作示例
     */
    public static void testTpl() {
        TplOperator tplOperator = client.getTplOperator();
        // 获取默认模板
        ResultDO<List<TemplateInfo>> resultDO = tplOperator.getDefault();
        System.out.println(resultDO);
        // 获取模板
        resultDO = tplOperator.get();
        System.out.println(resultDO);
        resultDO = tplOperator.getDefault("2");
        System.out.println(resultDO);

        // 增加模板
        ResultDO<TemplateInfo> result = tplOperator.add("【云片网】尊敬的用户您好，您的验证码是#code#");
        System.out.println(result);
        // 获取添加模板
        resultDO = tplOperator.get(String.valueOf(result.getData().getTpl_id()));
        System.out.println(resultDO);
        // 修改模板
        result = tplOperator
            .update(String.valueOf(result.getData().getTpl_id()), "【云片网】尊敬的用户您好，您的校验码是#code#");
        System.out.println(result);
        // 删除模板
        result = tplOperator.del(String.valueOf(result.getData().getTpl_id()));
        System.out.println(result);
    }

    /**
     * 流量类操作示例
     */
    public static void testFlow() {
        FlowOperator flowOperator = client.getFlowOperator();
        // 获取流量包
        ResultDO<List<FlowPackageInfo>> r1 = flowOperator.getPackage();
        System.out.println(r1);
        // 获取指定流量包
        r1 = flowOperator.getPackage("10086");
        System.out.println(r1);
        // 发送流量包
        ResultDO<SendFlowInfo> r2 = flowOperator.recharge("18701230000", "1008601");
        System.out.println(r2);
        // 拉取语音状态报告
        ResultDO<List<FlowStatusInfo>> r3 = flowOperator.pullStatus();
        System.out.println(r3);

    }

    /**
     * 语音类操作示例
     */
    public static void testVoice() {
        VoiceOperator voiceOperator = client.getVoiceOperator();
        // 发送语音验证码
        ResultDO<SendVoiceInfo> resultDO = voiceOperator.send("18701230000", "4325");
        System.out.println(resultDO);
        // 拉取语音状态报告
        ResultDO<List<VoiceStatusInfo>> result = voiceOperator.pullStatus();
        System.out.println(result);
    }

    /**
     * 短信状态报告类示例
     */
    public static void testReport() {
        SmsOperator smsOperator = client.getSmsOperator();
        // 拉取回复短信
        ResultDO<List<SmsReplyInfo>> r1 = smsOperator.pullReply();
        System.out.println(r1);
        // 拉取状态报告
        ResultDO<List<SmsStatusInfo>> r2 = smsOperator.pullStatus();
        System.out.println(r2);
    }
}


```




package com.yunpian.sdk.constant;

import java.nio.charset.Charset;
import java.util.Properties;

/**
 * Created by bingone on 15/11/19.
 */
@Deprecated
public class Config {

    public static final Properties properties = new Properties();

    static {
        try {
            properties.load(
                    Thread.currentThread().getContextClassLoader().getResourceAsStream("yunpian_rest.properties"));
            // properties.load(Thread.currentThread().getContextClassLoader()
            // .getResourceAsStream("yunpian_log.properties"));
            // PropertyConfigurator.configure(properties);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static final String APIKEY = properties.getProperty("APIKEY");

    public static final String API_SECRET = properties.getProperty("API_SECRET");

    // Encoding UTF-8
    public static final String ENCODING = properties.getProperty("ENCODING");
    // System Vserion
    public static final String VERSION = properties.getProperty("VERSION");
    // Domain
    public static final String DOMAIN = properties.getProperty("DOMAIN");

    public static final String SMS_DOMAIN = properties.getProperty("SMS_DOMAIN");

    public static final String VOICE_DOMAIN = properties.getProperty("VOICE_DOMAIN");

    public static final String FLOW_DOMAIN = properties.getProperty("FLOW_DOMAIN");
    // Prrotocol
    public static final String PROTOCOL = properties.getProperty("PROTOCOL");

    public static final String PORT = properties.getProperty("PORT");

    // prefix
    public static final String PREFIX = PROTOCOL + DOMAIN + VERSION;

    public static final String SMS_PREFIX = PROTOCOL + SMS_DOMAIN + VERSION;

    public static final String VOICE_PREFIX = PROTOCOL + VOICE_DOMAIN + VERSION;

    public static final String FLOW_PREFIX = PROTOCOL + FLOW_DOMAIN + VERSION;

    public static final Charset CHARSET = Charset.forName(ENCODING);

    // About User
    // 查询账户信息
    public static final String URI_GET_USER_INFO = SMS_PREFIX + properties.getProperty("URI_GET_USER_INFO");
    // 设置账户信息
    public static final String URI_SET_USER_INFO = SMS_PREFIX + properties.getProperty("URI_SET_USER_INFO");

    // About Template
    // 获取默认模板
    public static final String URI_GET_DEFAULT_TPL_SMS = SMS_PREFIX + properties.getProperty("URI_GET_DEFAULT_TPL_SMS");
    // 新增模板
    public static final String URI_ADD_TPL_SMS = SMS_PREFIX + properties.getProperty("URI_ADD_TPL_SMS");
    // 获取模板
    public static final String URI_GET_TPL_SMS = SMS_PREFIX + properties.getProperty("URI_GET_TPL_SMS");
    // 更新模板
    public static final String URI_UPD_TPL_SMS = SMS_PREFIX + properties.getProperty("URI_UPD_TPL_SMS");
    // 删除模板
    public static final String URI_DEL_TPL_SMS = SMS_PREFIX + properties.getProperty("URI_DEL_TPL_SMS");

    // About Send
    // 智能匹配发送
    public static final String URI_SEND_SINGLE_SMS = SMS_PREFIX + properties.getProperty("URI_SEND_SINGLE_SMS");
    // 获取状态报告
    public static final String URI_PULL_SMS_STATUS = SMS_PREFIX + properties.getProperty("URI_PULL_SMS_STATUS");
    // 获取回复短信
    public static final String URI_PULL_SMS_REPLY = SMS_PREFIX + properties.getProperty("URI_PULL_SMS_REPLY");
    // 查询回复短信
    public static final String URI_GET_SMS_REPLY = SMS_PREFIX + properties.getProperty("URI_GET_SMS_REPLY");
    // 查询回复记录
    public static final String URI_GET_SMS_RECORD = SMS_PREFIX + properties.getProperty("URI_GET_SMS_RECORD");
    // 查询屏蔽词汇
    public static final String URI_GET_SMS_BLACK_WORD = SMS_PREFIX + properties.getProperty("URI_GET_SMS_BLACK_WORD");
    // 指定模板发送
    public static final String URI_SEND_TPL_SINGLE_SMS = SMS_PREFIX + properties.getProperty("URI_SEND_TPL_SINGLE_SMS");
    public static final String URI_SEND_TPL_BATCH_SMS = SMS_PREFIX + properties.getProperty("URI_SEND_TPL_BATCH_SMS");
    // 批量个性发送
    public static final String URI_SEND_MULTI_SMS = SMS_PREFIX + properties.getProperty("URI_SEND_MULTI_SMS");
    // 批量发送短信
    public static final String URI_SEND_BATCH_SMS = SMS_PREFIX + properties.getProperty("URI_SEND_BATCH_SMS");

    // About Voice
    // 发送语音验证码
    public static final String URI_SEND_VOICE_SMS = VOICE_PREFIX + properties.getProperty("URI_SEND_VOICE_SMS");
    // 获取状态报告
    public static final String URI_PULL_VOICE_STATUS = VOICE_PREFIX + properties.getProperty("URI_PULL_VOICE_STATUS");

    public static final String URI_GET_FLOW_PACKAGE = VOICE_PREFIX + properties.getProperty("URI_GET_FLOW_PACKAGE");

    public static final String URI_RECHARGE_FLOW = FLOW_PREFIX + properties.getProperty("URI_RECHARGE_FLOW");

    public static final String URI_PULL_FLOW_STATUS = FLOW_PREFIX + properties.getProperty("URI_PULL_FLOW_STATUS");

}

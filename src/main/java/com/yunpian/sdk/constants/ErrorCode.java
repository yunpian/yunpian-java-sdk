package com.yunpian.sdk.constants;

import java.util.HashMap;
import java.util.Map;

public class ErrorCode {
    /**
     * ************************** 0.1版本的定义 **************************
     */
    public static final int INVALID_ARGUMENT = 1;
    public static final int MISSING_ARGUMENT = 2;
    public static final int QUERY_FAIL = 3;
    public static final int AUTH_FAIL = 4;
    public static final int SUBMIT_FAIL = 5;
    public static final int API_CALL_LIMIT = 6;

    public static String getErrorMsg(int code) {
        switch (code) {
            case INVALID_ARGUMENT:
                return "Invalid arguments";
            case MISSING_ARGUMENT:
                return "Missing required arguments";
            case QUERY_FAIL:
                return "Query fail on server";
            case AUTH_FAIL:
                return "Auth fail";
            case SUBMIT_FAIL:
                return "Submit fail";
            case API_CALL_LIMIT:
                return "Api call limit";
            default:
                return "Unknown error";
        }
    }

    /*************** v2 重新定义的返回码 ******************/
    /**
     * 正确
     */
    public static final int OK = 0;

    /****************** 调用API时间发生的错误，需要开发者自己处理 ****************************/
    /**
     * 请求参数缺失
     */
    public static final int ARGUMENT_MISSING = 1;
    /**
     * 请求参数格式错误
     */
    public static final int BAD_ARGUMENT_FORMAT = 2;
    /**
     * 账户余额不足
     */
    public static final int MONEY_NOT_ENOUGH = 3;

    /**
     * 关键词过滤
     */
    public static final int BLACK_WORD = 4;

    /**
     * 未找到对应id的模板
     */
    public static final int TPL_NOT_FOUND = 5;
    /**
     * 添加模板失败
     */
    public static final int ADD_TPL_FAILED = 6;
    /**
     * 模板不可用
     */
    public static final int TPL_NOT_VALID = 7;
    /**
     * 同一手机号30秒内重复提交相同的内容
     */
    public static final int DUP_IN_SHORT_TIME = 8;
    /**
     * 同一手机号5分钟内重复提交相同内容超过3次
     */
    public static final int TOO_MANY_TIME_IN_5 = 9;
    /**
     * 手机号黑名单过滤
     */
    public static final int BLACK_PHONE_FILTER = 10;
    /**
     * 接口不支持GET方式调用
     */
    public static final int GET_METHOD_NOT_SUPPORT = 11;
    /**
     * 接口不支持POST方式调用
     */
    public static final int POST_METHOD_NOT_SUPPORT = 12;
    /**
     * 营销短信暂停发送
     */
    public static final int MARKET_FORBIDDEN = 13;
    /**
     * 解码失败
     */
    public static final int DECODE_ERROR = 14;
    /**
     * 签名不匹配
     */
    public static final int SIGN_NOT_MATCH = 15;
    /**
     * 签名格式不正确
     */
    public static final int BAD_SIGN_FORMAT = 16;
    /**
     * 24小时内同一手机号发送次数超过限制
     */
    public static final int DAY_LIMIT_PER_MOBILE = 17;
    /**
     * 签名校验失败
     */
    public static final int SIGN_NOT_VALID = 18;
    /**
     * 请求已失效
     */
    public static final int REQUEST_NOT_VALID = 19;
    /**
     * 解密失败
     */
    public static final int DECRYPT_ERROR = 21;

    /**
     * 不支持的国家地区
     */
    public static final int REGION_NOT_SUPPORT = 20;

    /**
     * 1小时内同一手机号发送次数超过限制
     */
    public static final int HOUR_LIMIT_PER_MOBILE = 22;

    /**
     * 发往模板支持的国家列表之外的地区
     */
    public static final int REGION_NOT_IN_TPL_LIST = 23;

    /**
     * 添加告警设置失败
     */
    public static final int ADD_ALARM_SETTING_FAILED = 24;
    /**
     * 手机号和内容个数不匹配
     */
    public static final int LENGTH_NOT_MATCH = 25;

    /**
     * 不支持的流量包
     */
    public static final int PACKAGE_ERROR = 26;

    /**
     * 未开通金额计费
     */
    public static final int NO_MONEY_FEE_TYPE_FAILED = 27;

    /**
     * 不支持的运营商
     */
    public static final int CARRIER_FAILED = 28;


    /*************** 权限相关的错误 需要开发者自己处理 *******************/
    /**
     * 非法的apikey
     */
    public static final int BAD_API_KEY = -1;
    /**
     * API没有权限
     */
    public static final int API_NOT_ALLOWED = -2;
    /**
     * IP没有权限
     */
    public static final int IP_NOT_ALLOWED = -3;
    /**
     * 访问次数超限
     */
    public static final int OVER_ACCESS_LIMIT = -4;
    /**
     * 访问频率超限
     */
    public static final int OVER_ACCESS_RATE = -5;


    /**
     * 不支持批量发送
     */
    public static final int NOT_SUPPORT_BATCH = -6;
    /**************** 系统内部错误 需要技术支持解决 *************************/
    /**
     * 未知异常
     */
    public static final int UNKNOWN_EXCEPTION = -50;
    /**
     * 数据库操作失败
     */
    public static final int DB_OPERATION_FAIL = -51;
    /**
     * 充值失败
     */
    public static final int RECHARGE_FAILED = -52;
    /**
     * 提交短信失败
     */
    public static final int SUBMIT_SMS_FAILED = -53;
    /**
     * 记录已经存在
     */
    public static final int RECORD_ALREADY_EXISTED = -54;
    /**
     * 记录不存在
     */
    public static final int RECORD_NOT_EXISTED = -55;
    /**
     * 赠送失败
     */
    public static final int PROM_FAILED = -56;
    /**
     * 开通固定签名功能的用户，签名未设置
     */
    public static final int SIGE_NOT_SET = -57;

    /**
     * 错误码和对应的错误消息
     */
    public static final Map<Integer, String> codeMsgMap = new HashMap<Integer, String>();


    static {
        codeMsgMap.put(OK, "OK");

        /****************** 调用API时间发生的错误，需要开发者自己处理 ****************************/
        codeMsgMap.put(ARGUMENT_MISSING, "请求参数缺失");
        codeMsgMap.put(BAD_ARGUMENT_FORMAT, "请求参数格式错误");
        codeMsgMap.put(MONEY_NOT_ENOUGH, "账户余额不足");
        codeMsgMap.put(BLACK_WORD, "关键词屏蔽");
        codeMsgMap.put(TPL_NOT_FOUND, "未找到匹配的模板");

        codeMsgMap.put(REGION_NOT_SUPPORT, "暂不支持的国家地区");
        codeMsgMap.put(REGION_NOT_IN_TPL_LIST, "号码归属地不在模板可发送的地区内");
        codeMsgMap.put(PACKAGE_ERROR, "流量包错误");

        /*************** 权限相关的错误 需要开发者自己处理 *******************/
        codeMsgMap.put(BAD_API_KEY, "非法的apikey");
        codeMsgMap.put(API_NOT_ALLOWED, "API没有权限");
        codeMsgMap.put(IP_NOT_ALLOWED, "IP没有权限");
        codeMsgMap.put(OVER_ACCESS_LIMIT, "访问次数超限");
        codeMsgMap.put(OVER_ACCESS_RATE, "访问频率超限");
        codeMsgMap.put(SUBMIT_SMS_FAILED, "提交短信失败");
        codeMsgMap.put(RECORD_ALREADY_EXISTED, "记录已经存在");
        codeMsgMap.put(RECORD_NOT_EXISTED, "记录不存在");
        codeMsgMap.put(ADD_TPL_FAILED, "添加模板失败");
        codeMsgMap.put(TPL_NOT_VALID, "模板不可用");
        codeMsgMap.put(DUP_IN_SHORT_TIME, "同一手机号30秒内重复提交相同的内容");
        codeMsgMap.put(TOO_MANY_TIME_IN_5, "同一手机号5分钟内重复提交相同的内容超过3次");
        codeMsgMap.put(BLACK_PHONE_FILTER, "手机号黑名单过滤");
        codeMsgMap.put(GET_METHOD_NOT_SUPPORT, "接口不支持GET方式调用");
        codeMsgMap.put(POST_METHOD_NOT_SUPPORT, "接口不支持POST方式调用");
        codeMsgMap.put(MARKET_FORBIDDEN, "营销短信暂停发送");
        codeMsgMap.put(DECODE_ERROR, "解码失败");
        codeMsgMap.put(SIGN_NOT_MATCH, "签名不匹配");
        codeMsgMap.put(BAD_SIGN_FORMAT, "签名格式不正确");
        codeMsgMap.put(DAY_LIMIT_PER_MOBILE, "24小时内同一手机号发送次数超过限制");
        codeMsgMap.put(SIGN_NOT_VALID, "签名校验失败");
        codeMsgMap.put(REQUEST_NOT_VALID, "请求已失效");
        codeMsgMap.put(DECRYPT_ERROR, "解密失败");

        /*************** 系统相关系统 需要技术支持 *******************/
        codeMsgMap.put(UNKNOWN_EXCEPTION, "未知异常");
        //		codeMsgMap.put(DB_OPERATION_FAIL, "数据库操作失败");
        codeMsgMap.put(DB_OPERATION_FAIL, "系统繁忙");
        codeMsgMap.put(RECHARGE_FAILED, "充值失败");
        codeMsgMap.put(PROM_FAILED, "赠送失败");
        codeMsgMap.put(SIGE_NOT_SET, "用户开通过固定签名功能，但签名未设置");
    }

    ;
}

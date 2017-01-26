package com.yunpian.sdk.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO optimize storing
 * 
 * https://test-web.yunpian.com/api2.0/recode.html
 */
public final class Code {
    /**
     * ************************** 0.1版本的定义 **************************
     */
    public static String getErrorMsg(int code) {
        return codeMsgMap.get(code);
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
        codeMsgMap.put(OK, Messages.getString("Code.ok")); //$NON-NLS-1$

        /****************** 调用API时间发生的错误，需要开发者自己处理 ****************************/
        codeMsgMap.put(ARGUMENT_MISSING, Messages.getString("Code.miss_argument")); //$NON-NLS-1$
        codeMsgMap.put(BAD_ARGUMENT_FORMAT, Messages.getString("Code.bad_argument_format")); //$NON-NLS-1$
        codeMsgMap.put(MONEY_NOT_ENOUGH, Messages.getString("Code.money_not_enough")); //$NON-NLS-1$
        codeMsgMap.put(BLACK_WORD, Messages.getString("Code.black_word")); //$NON-NLS-1$
        codeMsgMap.put(TPL_NOT_FOUND, Messages.getString("Code.tpl_not_found")); //$NON-NLS-1$

        codeMsgMap.put(REGION_NOT_SUPPORT, Messages.getString("Code.region_not_support")); //$NON-NLS-1$
        codeMsgMap.put(REGION_NOT_IN_TPL_LIST, Messages.getString("Code.region_exclude_tpl_list")); //$NON-NLS-1$
        codeMsgMap.put(PACKAGE_ERROR, Messages.getString("Code.package_error")); //$NON-NLS-1$

        /*************** 权限相关的错误 需要开发者自己处理 *******************/
        codeMsgMap.put(BAD_API_KEY, Messages.getString("Code.invalid_apikey")); //$NON-NLS-1$
        codeMsgMap.put(API_NOT_ALLOWED, Messages.getString("Code.api_refused")); //$NON-NLS-1$
        codeMsgMap.put(IP_NOT_ALLOWED, Messages.getString("Code.ip_refused")); //$NON-NLS-1$
        codeMsgMap.put(OVER_ACCESS_LIMIT, Messages.getString("Code.access_limit")); //$NON-NLS-1$
        codeMsgMap.put(OVER_ACCESS_RATE, Messages.getString("Code.access_rate")); //$NON-NLS-1$
        codeMsgMap.put(SUBMIT_SMS_FAILED, Messages.getString("Code.sms_submit_fail")); //$NON-NLS-1$
        codeMsgMap.put(RECORD_ALREADY_EXISTED, Messages.getString("Code.record_existence")); //$NON-NLS-1$
        codeMsgMap.put(RECORD_NOT_EXISTED, Messages.getString("Code.record_not_found")); //$NON-NLS-1$
        codeMsgMap.put(ADD_TPL_FAILED, Messages.getString("Code.tpl_add_fail")); //$NON-NLS-1$
        codeMsgMap.put(TPL_NOT_VALID, Messages.getString("Code.invalid_tpl")); //$NON-NLS-1$
        codeMsgMap.put(DUP_IN_SHORT_TIME, Messages.getString("Code.dup_in_short_time")); //$NON-NLS-1$
        codeMsgMap.put(TOO_MANY_TIME_IN_5, Messages.getString("Code.too_many_times_in_5")); //$NON-NLS-1$
        codeMsgMap.put(BLACK_PHONE_FILTER, Messages.getString("Code.black_mobile_filter")); //$NON-NLS-1$
        codeMsgMap.put(GET_METHOD_NOT_SUPPORT, Messages.getString("Code.not_support_get")); //$NON-NLS-1$
        codeMsgMap.put(POST_METHOD_NOT_SUPPORT, Messages.getString("Code.not_support_post")); //$NON-NLS-1$
        codeMsgMap.put(MARKET_FORBIDDEN, Messages.getString("Code.market_forbidden")); //$NON-NLS-1$
        codeMsgMap.put(DECODE_ERROR, Messages.getString("Code.decode_fail")); //$NON-NLS-1$
        codeMsgMap.put(SIGN_NOT_MATCH, Messages.getString("Code.sign_not_match")); //$NON-NLS-1$
        codeMsgMap.put(BAD_SIGN_FORMAT, Messages.getString("Code.bad_sign_format")); //$NON-NLS-1$
        codeMsgMap.put(DAY_LIMIT_PER_MOBILE, Messages.getString("Code.day_limit_per_mobile")); //$NON-NLS-1$
        codeMsgMap.put(SIGN_NOT_VALID, Messages.getString("Code.invalid_sign")); //$NON-NLS-1$
        codeMsgMap.put(REQUEST_NOT_VALID, Messages.getString("Code.invalid_request")); //$NON-NLS-1$
        codeMsgMap.put(DECRYPT_ERROR, Messages.getString("Code.decrypt_fail")); //$NON-NLS-1$

        /*************** 系统相关系统 需要技术支持 *******************/
        codeMsgMap.put(UNKNOWN_EXCEPTION, Messages.getString("Code.unknown_exception")); //$NON-NLS-1$
        // codeMsgMap.put(DB_OPERATION_FAIL, "数据库操作失败");
        codeMsgMap.put(DB_OPERATION_FAIL, Messages.getString("Code.db_operaion_fail")); //$NON-NLS-1$
        codeMsgMap.put(RECHARGE_FAILED, Messages.getString("Code.recharge_fail")); //$NON-NLS-1$
        codeMsgMap.put(PROM_FAILED, Messages.getString("Code.present_fail")); //$NON-NLS-1$
        codeMsgMap.put(SIGE_NOT_SET, Messages.getString("Code.sign_not_set")); //$NON-NLS-1$
    }

    ;
}

package com.yunpian.sdk.constants;

import java.nio.charset.Charset;
import java.util.Properties;

/**
 * Created by bingone on 15/11/6.
 */
public interface YunpianConstants {

    String API_KEY = "apikey";
    String APP_ID = "appid";
    String SIGN = "_sign";
    String TIMESTAMP = "timestamp";
    String USER_API_KEY = "user_apikey";
    String NICK = "nick";
    String COMPANY = "company";
    String UID = "uid";
    String EMAIL = "email";
    String PASSWD = "passwd";
    String ACCESS_LIMIT = "access_limit";
    String SEND_LIMIT = "send_limit";
    String TYPE = "type";
    String SMS_NUM = "sms_num";
    String TEXT = "text";
    String SMS_ID = "sms_id";
    String START_TIME = "start_time";
    String END_TIME = "end_time";
    String PAGE_NUM = "page_num";
    String PAGE_SIZE = "page_size";
    String RETURN_FIELDS = "return_fields";
    String SORT_FIELDS = "sort_fields";
    String ENCRYPT = "encrypt";
    String API_SECRET = "api_secret";
    String CARRIER = "carrier";
    /**
     * 模板id
     */
    String TPL_ID = "tpl_id";
    /**
     * 模板值
     */
    String TPL_VALUE = "tpl_value";
    /**
     * 模板内容
     */
    String TPL_CONTENT = "tpl_content";
    /**
     * 签名
     */
    String TPL_SIGN = "tpl_sign";
    /**
     * 余额告警阈值
     */
    String ALARM_BALANCE = "alarm_balance";
    /**
     * 紧急联系人电话
     */
    String EMERGENCY_MOBILE = "emergency_mobile";
    /**
     * 屏蔽词
     */
    String BLACK_WORD = "black_word";

    /**
     * 模板语言
     */
    String TPL_LANG = "lang";

    /**
     * 流量充值参数
     */
    String SN = "sn";


    // 返回值字段

    String CODE                 = "code";
    String MSG                  = "msg";
    String COUNT                = "count";
    String FEE                  = "fee";
    String UNIT                 = "unit";
    String MOBILE               = "mobile";
    String SID                  = "sid";

    /**
     * batch_send 接口 增添的返回值名
     */
    String TOTAL_COUNT          = "total_count";
    String TOTAL_FEE            = "total_fee";
    String DATA                 = "data";

    /**
     * user 接口返回值名
     */


    /**
     * tpl 接口返回值名
     */

    /**
     * 错误返回值名
     */
    String HTTP_STATUS_CODE     = "httpStatusCode";
    String DETAIL               = "detail";
}

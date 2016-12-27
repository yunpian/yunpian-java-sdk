package com.yunpian.sdk.constant;

/**
 * Created by bingone on 15/11/6.
 */
public interface YunpianConstant {

    /**************************** http *************************************/
    String HTTP_CONN_TIMEOUT = "http.conn.timeout";
    String HTTP_SO_TIMEOUT = "http.so.timeout";
    String HTTP_CHARSET = "http.charset";
    String HTTP_CONN_MAXPREROUTE = "http.conn.maxpreroute";
    String HTTP_CONN_MAXTOTAL = "http.conn.maxtotal";
    String HTTP_SSL_KEYSTORE = "http.ssl.keystore";
    String HTTP_SSL_PASSWD = "http.ssl.passwd";

    String HTTP_CHARSET_DEFAULT = "utf-8";

    /**************************** yunapian.properties *************************************/
    String YP_FILE = "yp.file";
    String YP_APIKEY = "yp.apikey";
    String YP_VERSION = "yp.version";
    String YP_USER_HOST = "yp.user.host";
    String YP_SIGN_HOST = "yp.sign.host";
    String YP_TPL_HOST = "yp.tpl.host";
    String YP_SMS_HOST = "yp.sms.host";
    String YP_VOICE_HOST = "yp.voice.host";
    String YP_FLOW_HOST = "yp.flow.host";
    String YP_CALL_HOST = "yp.call.host";

    /**************************** api *************************************/
    String VERSION_V1 = "v1";
    String VERSION_V2 = "v2";

    String APIKEY = "apikey";

    // 返回值字段
    String CODE = "code";
    String MSG = "msg";
    String DETAIL = "detail";
    String DATA = "data";

    // user
    String USER = "user";
    String BALANCE = "balance";
    /**
     * 紧急联系人电话
     */
    String EMERGENCY_MOBILE = "emergency_mobile";
    String EMERGENCY_CONTACT = "emergency_contact";
    /**
     * 余额告警阈值
     */
    String ALARM_BALANCE = "alarm_balance";
    String IP_WHITELIST = "ip_whitelist";
    String EMAIL = "email";
    String MOBILE = "mobile";
    String GMT_CREATED = "gmt_created";
    String API_VERSION = "api_version";

    // sign
    String SIGN = "sign";
    String NOTIFY = "notify";
    String APPLYVIP = "apply_vip";
    String ISONLYGLOBAL = "is_only_global";
    String INDUSTRYTYPE = "industry_type";
    String OLD_SIGN = "old_sign";

    // tpl
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
    String CHECK_STATUS = "check_status";
    String REASON = "reason";
    String TEMPLATE = "template";
    /**
     * 模板语言
     */
    String LANG = "lang";
    String COUNTRY_CODE = "country_code";
    String NOTIFY_TYPE = "notify_type";

    // call
    String FROM = "from";
    String TO = "to";
    String DURATION = "duration";
    String AREA_CODE = "area_code";
    String MESSAGE_ID = "message_id";
    String ANONYMOUS_NUMBER = "anonymous_number";
    String PAGE_SIZE = "page_size";

    // flow
    String CARRIER = "carrier";
    String FLOW_PACKAGE = "flow_package";
    String _SIGN = "_sign";
    String CALLBACK_URL = "callback_url";
    String RESULT = "result";
    String FLOW_STATUS = "flow_status";

    // voice
    String DISPLAY_NUM = "display_num";
    String VOICE_STATUS = "voice_status";

    // sms
    String EXTEND = "extend";
    String SMS_STATUS = "sms_status";
    String SMS_REPLY = "sms_reply";
    String SMS = "sms";
    String TOTAL = "total";

    String NICK = "nick";
    String UID = "uid";

    String TEXT = "text";
    String START_TIME = "start_time";
    String END_TIME = "end_time";
    String PAGE_NUM = "page_num";

    @Deprecated
    String ENCRYPT = "encrypt";
    @Deprecated
    String API_SECRET = "api_secret";
    @Deprecated
    String DEFAULT_ENCRYPT = "tea";

    /**
     * 流量充值参数
     */
    String SN = "sn";

    String COUNT = "count";
    String FEE = "fee";
    String UNIT = "unit";

    String SID = "sid";

    /**
     * batch_send 接口 增添的返回值名
     */
    String TOTAL_COUNT = "total_count";
    String TOTAL_FEE = "total_fee";
    
    String SEPERATOR_COMMA = ",";

}

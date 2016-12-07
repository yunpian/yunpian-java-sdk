/**
 * 
 */
package com.yunpian.sdk.api;

import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;

import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.constant.Code;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.User;
import com.yunpian.sdk.util.ApiUtil;
import com.yunpian.sdk.util.JsonUtil;

/**
 * https://www.yunpian.com/api2.0/user.html
 * 
 * @author dzh
 * @date Nov 23, 2016 1:10:42 PM
 * @since 1.2.0
 */
public class UserApi extends YunpianApi {

    public static final String NAME = "user";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public void init(YunpianClient clnt) {
        super.init(clnt);
        host(clnt.getConf().getConf(YP_USER_HOST, "https://sms.yunpian.com"));
    }

    /**
     * <h1>查账户信息</h1>
     * 
     * <p>
     * 参数名 类型 是否必须 描述 示例
     * </p>
     * <p>
     * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
     * </p>
     * 
     * @return
     */
    public Result<User> get() {
        Result<User> r = new Result<>();
        List<NameValuePair> list = param2pair(null, r, APIKEY);
        if (r.getCode() != Code.OK)
            return r;
        String data = format2Form(list);

        MapResultHandler<User> h = new MapResultHandler<User>() {
            @Override
            public User data(Map<String, String> rsp) {
                switch (version()) {
                case VERSION_V1:
                    return JsonUtil.fromJson(rsp.get(USER), User.class);
                case VERSION_V2:
                    return map2User(rsp);
                }
                return null;
            }

            @Override
            public Integer code(Map<String, String> rsp) {
                return YunpianApi.code(rsp, UserApi.this.version());
            }
        };

        try {
            return path("get.json").post(uri(), data, h);
        } catch (Exception e) {
            return h.catchExceptoin(e, null);
        }
    }

    /**
     * <h1>修改账户信息</h1>
     * 
     * <p>
     * 参数名 类型 是否必须 描述 示例
     * </p>
     * <p>
     * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
     * </p>
     * <p>
     * emergency_contact String 否 紧急联系人 zhangshan
     * </p>
     * <p>
     * emergency_mobile String 否 紧急联系人手机号 13012345678
     * </p>
     * <p>
     * alarm_balance Long 否 短信余额提醒阈值。 一天只提示一次 100
     * </p>
     * 
     * @param param
     *            emergency_contact emergency_mobile alarm_balance
     * @return
     */
    public Result<User> set(Map<String, String> param) {
        Result<User> r = new Result<>();
        List<NameValuePair> list = param2pair(param, r, APIKEY);
        if (r.getCode() != Code.OK)
            return r;
        String data = format2Form(list);

        MapResultHandler<User> h = new MapResultHandler<User>() {
            @Override
            public User data(Map<String, String> rsp) {
                switch (version()) {
                case VERSION_V2:
                    return map2User(rsp);
                }
                return null;
            }

            @Override
            public Integer code(Map<String, String> rsp) {
                return YunpianApi.code(rsp, UserApi.this.version());
            }
        };
        try {
            return path("set.json").post(uri(), data, h, r);
        } catch (Exception e) {
            return h.catchExceptoin(e, r);
        }
    }

    public static final User map2User(Map<String, String> map) {
        if (map == null || map.isEmpty())
            return null;
        try {
            User user = new User();
            user.setNick(map.get(NICK));
            user.setAlarm_balance(Long.parseLong(map.get(ALARM_BALANCE)));
            user.setBalance(Double.parseDouble(map.get(BALANCE)));
            user.setEmail(map.get(EMAIL));
            user.setEmergency_contact(map.get(EMERGENCY_CONTACT));
            user.setEmergency_mobile(map.get(EMERGENCY_MOBILE));
            user.setGmt_created(ApiUtil.str2date(map.get(GMT_CREATED)));
            user.setIp_whitelist(map.get(IP_WHITELIST));
            return user;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e.fillInStackTrace());
        }
        return null;
    }

}

package com.yunpian.sdk.api;

import com.google.gson.reflect.TypeToken;
import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.constant.Code;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.ShortUrl;
import com.yunpian.sdk.util.JsonUtil;
import org.apache.http.NameValuePair;

import java.util.List;
import java.util.Map;

/**
 * https://www.yunpian.com/api2.0/api-domestic/sms_click_statistics.html
 *
 * @author liujie
 * @date 2017/9/29
 * @since 1.2.5
 */
public class ShortUrlApi extends YunpianApi {

    public static final String NAME = "short_url";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public void init(YunpianClient clnt) {
        super.init(clnt);
        host(clnt.getConf().getConf(YP_SHORT_URL_HOST, "https://sms.yunpian.com"));
    }

    /**
     * <h1>生成短链接 only v2</h1>
     * <p>
     * <p>
     * 参数名 类型 是否必须 描述 示例
     * </p>
     * <p>
     * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
     * </p>
     * <p>
     * long_url String 是 待转换的长网址，必须http://或https://开头 https://www.yunpian.com
     * </p>
     * <p>
     * stat_duration Integer 否 点击量统计持续时间（天），过期后不再统计，区间[0,30]，0表示不统计，默认30 30
     * </p>
     * <p>
     * provider Integer 否 生成的链接的域名，传入1是yp2.cn，2是t.cn，默认1 1
     * </p>
     * <p>
     * name String 否 取名方便区分，默认为“MM-dd HH:mm生成的短链接” yunpian
     * </p>
     *
     * @param param
     * @return
     */
    public Result<ShortUrl> shorten(Map<String, String> param) {
        Result<ShortUrl> r = new Result<>();
        List<NameValuePair> list = param2pair(param, r, APIKEY, LONG_URL);
        if (r.getCode() != Code.OK) {
            return r;
        }
        String data = urlEncode(list);
        MapResultHandler<ShortUrl> h = new MapResultHandler<ShortUrl>() {
            @Override
            public ShortUrl data(Map<String, String> rsp) {
                switch (version()) {
                    case VERSION_V2:
                        return JsonUtil.fromJson(rsp.get(SHORT_URL), ShortUrl.class);
                }
                return null;
            }

            @Override
            public Integer code(Map<String, String> rsp) {
                return YunpianApi.code(rsp, ShortUrlApi.this.version());
            }
        };
        try {
            return path("shorten.json").post(data, h, r);
        } catch (Exception e) {
            return h.catchExceptoin(e, r);
        }
    }

    /**
     * <h1>获取短链接统计 only v2</h1>
     * <p>
     * <p>
     * 参数名 类型 是否必须 描述 示例
     * </p>
     * <p>
     * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
     * </p>
     * <p>
     * sid String 是 短链接唯一标识 ckAclC
     * </p>
     * <p>
     * start_time String 否 开始时间，默认一个小时前 2017-03-29 11:30:00
     * </p>
     * <p>
     * end_time String 否 结束时间，默认当前时间 2017-03-29 12:10:00
     * </p>
     *
     * @param param
     * @return
     */
    public Result<Map<String, Long>> stat(Map<String, String> param) {
        Result<Map<String, Long>> r = new Result<>();
        List<NameValuePair> list = param2pair(param, r, APIKEY, SID);
        if (r.getCode() != Code.OK) {
            return r;
        }
        String data = urlEncode(list);
        MapResultHandler<Map<String, Long>> h = new MapResultHandler<Map<String, Long>>() {
            @Override
            public Map<String, Long> data(Map<String, String> rsp) {
                switch (version()) {
                    case VERSION_V2:
                        TypeToken<Map<String, Long>> typeToken = new TypeToken<Map<String, Long>>() {
                        };
                        return JsonUtil.fromJson(rsp.get(STAT), typeToken.getType());
                }
                return null;
            }

            @Override
            public Integer code(Map<String, String> rsp) {
                return YunpianApi.code(rsp, ShortUrlApi.this.version());
            }
        };
        try {
            return path("stat.json").post(data, h, r);
        } catch (Exception e) {
            return h.catchExceptoin(e, r);
        }
    }
}

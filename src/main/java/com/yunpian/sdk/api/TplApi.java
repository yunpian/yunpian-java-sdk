/**
 * 
 */
package com.yunpian.sdk.api;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;

import com.google.gson.reflect.TypeToken;
import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.constant.Code;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.Template;
import com.yunpian.sdk.util.JsonUtil;

/**
 * https://www.yunpian.com/api2.0/tpl.html
 * 
 * @author dzh
 * @date Nov 23, 2016 1:11:40 PM
 * @since 1.2.0
 */
public class TplApi extends YunpianApi {

    public static final String NAME = "tpl";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public void init(YunpianClient clnt) {
        super.init(clnt);
        host(clnt.getConf().getConf(YP_TPL_HOST, "https://sms.yunpian.com"));
    }

    /**
     * <h1>取默认模板</h1>
     * <p>
     * 参数名 类型 是否必须 描述 示例
     * </p>
     * <p>
     * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
     * </p>
     * <p>
     * tpl_id Long 否 模板id，64位长整形。指定id时返回id对应的默认 模板。未指定时返回所有默认模板 1
     * </p>
     * 
     * @param param
     *            tpl_id
     * @return
     */
    public Result<List<Template>> get_default(Map<String, String> param) {
        Result<List<Template>> r = new Result<>();
        List<NameValuePair> list = param2pair(param, r, APIKEY);
        if (r.getCode() != Code.OK)
            return r;
        String data = format2Form(list);

        SimpleListResultHandler<Template> h = new SimpleListResultHandler<Template>() {
            @Override
            public List<Template> data(List<Template> rsp) {
                switch (version()) {
                case VERSION_V2:
                    return rsp;
                }
                return Collections.emptyList();
            }

            @Override
            public Integer code(List<Template> rsp) {
                if (rspMap != null) {
                    return YunpianApi.code(rspMap, TplApi.this.version());
                }
                return Code.OK;
            }

            @Override
            Type rspType() {
                return new TypeToken<List<Template>>() {
                }.getType();
            }
        };
        try {
            return path("get_default.json").post(uri(), data, h, r);
        } catch (Exception e) {
            return h.catchExceptoin(e, r);
        }
    }

    /**
     * <h1>取模板</h1>
     * 
     * <p>
     * 参数名 类型 是否必须 描述 示例
     * </p>
     * <p>
     * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
     * </p>
     * <p>
     * tpl_id Long 否 模板id，64位长整形。指定id时返回id对应的 模板。未指定时返回所有模板 1
     * </p>
     * 
     * @param param
     *            tpl_id
     * @return
     */
    public Result<List<Template>> get(Map<String, String> param) {
        Result<List<Template>> r = new Result<>();
        List<NameValuePair> list = param2pair(param, r, APIKEY);
        if (r.getCode() != Code.OK)
            return r;
        String data = format2Form(list);

        SimpleListResultHandler<Template> h = new SimpleListResultHandler<Template>() {
            @Override
            public List<Template> data(List<Template> rsp) {
                switch (version()) {
                case VERSION_V1:
                    if (rspMap == null)
                        break;
                    String t = rspMap.get(TEMPLATE);
                    return t.startsWith("[")
                            ? JsonUtil.<ArrayList<Template>>fromJson(t, new TypeToken<ArrayList<Template>>() {
                            }.getType()) : Arrays.asList(JsonUtil.fromJson(t, Template.class));
                case VERSION_V2:
                    if (rspMap != null) {
                        Template tpl = map2Template(rspMap);
                        return tpl == null ? null : Arrays.asList(tpl);
                    }
                    return rsp;
                }
                return Collections.emptyList();
            }

            @Override
            public Integer code(List<Template> rsp) {
                if (rspMap != null) {
                    return YunpianApi.code(rspMap, TplApi.this.version());
                }
                return Code.OK;
            }

            @Override
            Type rspType() {
                return new TypeToken<List<Template>>() {
                }.getType();
            }
        };
        try {
            return path("get.json").post(uri(), data, h, r);
        } catch (Exception e) {
            return h.catchExceptoin(e, r);
        }
    }

    /**
     * <h1>添加模板</h1>
     * 
     * <p>
     * 参数名 类型 是否必须 描述 示例
     * </p>
     * <p>
     * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
     * </p>
     * <p>
     * tpl_content String 是 模板内容，必须以带符号【】的签名开头 【云片网】您的验证码是#code#
     * </p>
     * <p>
     * notify_type Integer 否 审核结果短信通知的方式: 0表示需要通知,默认; 1表示仅审核不通过时通知; 2表示仅审核通过时通知;
     * 3表示不需要通知 1
     * </p>
     * <p>
     * lang String 否 国际短信模板所需参数，模板语言:简体中文zh_cn; 英文en; 繁体中文 zh_tw; 韩文ko,日文 ja
     * zh_cn
     * </p>
     * 
     * @param param
     * @return
     */
    public Result<Template> add(Map<String, String> param) {
        Result<Template> r = new Result<>();
        List<NameValuePair> list = param2pair(param, r, APIKEY, TPL_CONTENT);
        if (r.getCode() != Code.OK)
            return r;
        String data = format2Form(list);

        MapResultHandler<Template> h = new MapResultHandler<Template>() {
            @Override
            public Template data(Map<String, String> rsp) {
                switch (version()) {
                case VERSION_V1:
                    return JsonUtil.fromJson(rsp.get(TEMPLATE), Template.class);
                case VERSION_V2:
                    return map2Template(rsp);
                }
                return null;
            }

            @Override
            public Integer code(Map<String, String> rsp) {
                return YunpianApi.code(rsp, TplApi.this.version());
            }
        };
        try {
            return path("add.json").post(uri(), data, h, r);
        } catch (Exception e) {
            return h.catchExceptoin(e, r);
        }
    }

    /**
     * <h1>删除模板</h1>
     * 
     * <p>
     * 参数名 类型 是否必须 描述 示例
     * </p>
     * <p>
     * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
     * </p>
     * <p>
     * tpl_id Long 是 模板id，64位长整形 9527
     * </p>
     * 
     * @param param
     * @return
     */
    public Result<Template> del(Map<String, String> param) {
        Result<Template> r = new Result<>();
        List<NameValuePair> list = param2pair(param, r, APIKEY, TPL_ID);
        if (r.getCode() != Code.OK)
            return r;
        String data = format2Form(list);

        MapResultHandler<Template> h = new MapResultHandler<Template>() {
            @Override
            public Template data(Map<String, String> rsp) {
                switch (version()) {
                case VERSION_V2:
                    return map2Template(rsp);
                }
                return null;
            }

            @Override
            public Integer code(Map<String, String> rsp) {
                return YunpianApi.code(rsp, TplApi.this.version());
            }
        };
        try {
            return path("del.json").post(uri(), data, h, r);
        } catch (Exception e) {
            return h.catchExceptoin(e, r);
        }
    }

    /**
     * <h1>修改模板</h1>
     * 
     * <p>
     * 参数名 类型 是否必须 描述 示例
     * </p>
     * <p>
     * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
     * </p>
     * <p>
     * tpl_id Long 是 模板id，64位长整形，指定id时返回id对应的模板。未指定时返回所有模板 9527
     * </p>
     * <p>
     * tpl_content String 是
     * 模板id，64位长整形。指定id时返回id对应的模板。未指定时返回所有模板模板内容，必须以带符号【】的签名开头 【云片网】您的验证码是#code#
     * </p>
     * <p>
     * notify_type Integer 否 审核结果短信通知的方式: 0表示需要通知,默认; 1表示仅审核不通过时通知; 2表示仅审核通过时通知;
     * 3表示不需要通知 1
     * </p>
     * <p>
     * lang String 否 国际短信模板所需参数，模板语言:简体 中文zh_cn; 英文en; 繁体中文 zh_tw; 韩文ko,日文 ja
     * zh_cn
     * </p>
     * 
     * @param param
     * @return
     */
    public Result<Template> update(Map<String, String> param) {
        Result<Template> r = new Result<>();
        List<NameValuePair> list = param2pair(param, r, APIKEY, TPL_ID, TPL_CONTENT);
        if (r.getCode() != Code.OK)
            return r;
        String data = format2Form(list);

        MapResultHandler<Template> h = new MapResultHandler<Template>() {
            @Override
            public Template data(Map<String, String> rsp) {
                switch (version()) {
                case VERSION_V1:
                    if (rsp.containsKey(TEMPLATE))
                        return JsonUtil.fromJson(rsp.get(TEMPLATE), Template.class);
                case VERSION_V2:
                    if (rsp.containsKey(TEMPLATE))
                        return JsonUtil.fromJson(rsp.get(TEMPLATE), Template.class);
                    return map2Template(rsp);
                }
                return null;
            }

            @Override
            public Integer code(Map<String, String> rsp) {
                return YunpianApi.code(rsp, TplApi.this.version());
            }
        };
        try {
            return path("update.json").post(uri(), data, h, r);
        } catch (Exception e) {
            return h.catchExceptoin(e, r);
        }
    }

    private Template map2Template(Map<String, String> map) {
        if (map == null)
            return null;

        try {
            Template t = new Template();
            t.setCheck_status(map.get(CHECK_STATUS));
            t.setReason(map.get(REASON));
            t.setTpl_content(map.get(TPL_CONTENT));
            t.setTpl_id(Long.parseLong(map.get(TPL_ID)));
            return t;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e.fillInStackTrace());
        }
        return null;
    }

}

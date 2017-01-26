/**
 * 
 */
package com.yunpian.sdk.api;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;

import com.google.gson.reflect.TypeToken;
import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.constant.Code;
import com.yunpian.sdk.model.CallBill;
import com.yunpian.sdk.model.CallBind;
import com.yunpian.sdk.model.CallRecord;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.util.JsonUtil;

/**
 * https://www.yunpian.com/api/anonymous.html
 * 
 * @author dzh
 * @date Nov 23, 2016 1:13:49 PM
 * @since 1.2.0
 */
public class CallApi extends YunpianApi {

    public static final String NAME = "call";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public void init(YunpianClient clnt) {
        super.init(clnt);
        host(clnt.getConf().getConf(YP_CALL_HOST, "https://call.yunpian.com"));
    }

    /**
     * <h1>绑定号码</h1>
     * 
     * <p>
     * 参数名 类型 是否必须 描述 示例
     * </p>
     * <p>
     * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
     * </p>
     * <p>
     * from String 是 需要绑定的号码 +8615012341234
     * </p>
     * <p>
     * to String 是 需要绑定的号码 +8615011112222。 AX模式时to为空
     * </p>
     * <p>
     * duration Intger 是 有效时长，单位：秒 600
     * </p>
     * <p>
     * area_code String 否 区号，期望anonymous_number所属的地区 +8621（021）
     * </p>
     * 
     * @param param
     * @return
     */
    public Result<CallBind> bind(Map<String, String> param) {
        Result<CallBind> r = new Result<>();
        List<NameValuePair> list = param2pair(param, r, APIKEY, FROM, DURATION);
        if (r.getCode() != Code.OK)
            return r;
        String data = urlEncode(list);

        MapResultHandler<CallBind> h = new MapResultHandler<CallBind>() {
            @Override
            public CallBind data(Map<String, String> rsp) {
                switch (version()) {
                case VERSION_V2:
                    return map2CallBind(rsp);
                }
                return null;
            }

            @Override
            public Integer code(Map<String, String> rsp) {
                return YunpianApi.code(rsp, CallApi.this.version());
            }
        };
        try {
            return path("bind.json").post(uri(), data, h, r);
        } catch (Exception e) {
            return h.catchExceptoin(e, r);
        }
    }

    /**
     * <h1>解绑号码</h1>
     * 
     * <p>
     * 参数名 类型 是否必须 描述 示例
     * </p>
     * <p>
     * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
     * </p>
     * <p>
     * from String 是 需要绑定的号码 +8615012341234
     * </p>
     * <p>
     * to String 是 需要绑定的号码 +8615011112222。 AX模式时to为空
     * </p>
     * <p>
     * duration Intger 是 延迟解绑的时间，单位：秒，0表示立即解除绑定 0
     * </p>
     * 
     * @param param
     * @return
     */
    public Result<Void> unbind(Map<String, String> param) {
        Result<Void> r = new Result<>();
        List<NameValuePair> list = param2pair(param, r, APIKEY, FROM);
        if (r.getCode() != Code.OK)
            return r;
        String data = urlEncode(list);

        MapResultHandler<Void> h = new MapResultHandler<Void>() {
            @Override
            public Void data(Map<String, String> rsp) {
                return null;
            }

            @Override
            public Integer code(Map<String, String> rsp) {
                return YunpianApi.code(rsp, CallApi.this.version());
            }
        };
        try {
            return path("unbind.json").post(uri(), data, h, r);
        } catch (Exception e) {
            return h.catchExceptoin(e, r);
        }
    }

    /**
     * <h1>话单获取</h1>
     * <p>
     * 参数名 类型 是否必须 描述 示例
     * </p>
     * <p>
     * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
     * </p>
     * <p>
     * page_size Integer 否 每页个数，最大100个，默认20个 20
     * </p>
     * 
     * @param param
     * @return
     */
    public Result<List<CallBill>> pull(Map<String, String> param) {
        Result<List<CallBill>> r = new Result<>();
        List<NameValuePair> list = param2pair(param, r, APIKEY);
        if (r.getCode() != Code.OK)
            return r;
        String data = urlEncode(list);

        SimpleListResultHandler<CallBill> h = new SimpleListResultHandler<CallBill>() {
            @Override
            public List<CallBill> data(List<CallBill> rsp) {
                switch (version()) {
                case VERSION_V2:
                    return rsp;
                }
                return Collections.emptyList();
            }

            @Override
            public Integer code(List<CallBill> rsp) {
                if (rspMap != null) {
                    return YunpianApi.code(rspMap, CallApi.this.version());
                }
                return Code.OK;
            }

            @Override
            Type rspType() {
                return new TypeToken<List<CallBill>>() {
                }.getType();
            }
        };
        try {
            return path("pull.json").post(uri(), data, h, r);
        } catch (Exception e) {
            return h.catchExceptoin(e, r);
        }
    }

    /**
     * <h1>获取录音id</h1>
     * <p>
     * 参数名 类型 是否必须 描述 示例
     * </p>
     * <p>
     * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
     * </p>
     * <p>
     * message_id String 是 绑定关系，bind接口返回
     * dCdDHzED75vmqngj5LAvL6lvU_1orEqRDgTHYmRI2TTwCW3bnnCViQY7q-4udg08q4h3JscK6wU
     * </p>
     * <p>
     * start_time Long 是 查询开始时间戳，UTC 1480310078000
     * </p>
     * <p>
     * end_time Long 是 查询结束时间戳，UTC 1485333948452
     * </p>
     * <p>
     * page_num Integer 否 分页号，默认1 1
     * </p>
     * <p>
     * page_size Integer 否 每页个数，最大100个，默认10个 10
     * </p>
     * 
     * @param param
     * @return
     */
    public Result<List<CallRecord>> record(Map<String, String> param) {
        Result<List<CallRecord>> r = new Result<>();
        List<NameValuePair> list = param2pair(param, r, APIKEY, MESSAGE_ID, START_TIME, END_TIME);
        if (r.getCode() != Code.OK)
            return r;
        String data = urlEncode(list);

        MapResultHandler<List<CallRecord>> h = new MapResultHandler<List<CallRecord>>() {
            @Override
            public List<CallRecord> data(Map<String, String> rsp) {
                switch (version()) {
                case VERSION_V2:
                    return JsonUtil.fromJson(rsp.get(DATA), new TypeToken<List<CallRecord>>() {
                    }.getType());
                }
                return Collections.emptyList();
            }

            @Override
            public Integer code(Map<String, String> rsp) {
                return YunpianApi.code(rsp, CallApi.this.version());
            }
        };
        try {
            return path("record.json").post(uri(), data, h, r);
        } catch (Exception e) {
            return h.catchExceptoin(e, r);
        }
    }

    /**
     * <h1>下载录音文件</h1>
     * <p>
     * 参数名 类型 是否必须 描述 示例
     * </p>
     * <p>
     * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
     * </p>
     * <p>
     * message_id String 是 绑定关系，bind接口返回
     * dCdDHzED75vmqngj5LAvL6lvU_1orEqRDgTHYmRViQYwU
     * </p>
     * <p>
     * record_id String 是 文件记录ID，record接口获取 5888660c-1eff-11af-1c6e-700fe1500
     * </p>
     * 
     * @param param
     * @param file
     *            记录文件保存路径,比如/home/admin/yy.wav
     * @return
     */
    public Result<Void> get_record(Map<String, String> param, String file) {
        Result<Void> r = new Result<>();
        List<NameValuePair> list = param2pair(param, r, APIKEY, MESSAGE_ID, RECORD_ID);
        if (r.getCode() != Code.OK)
            return r;
        String data = urlEncode(list);

        FileOutputStream fos = null;
        try {
            HttpResponse rsp = path("get_record.json").client().post(uri(), data).get();
            if (ContentType.APPLICATION_JSON.getMimeType().equals(ContentType.getOrDefault(rsp.getEntity()).getMimeType())) {
                Map<String, String> rspMap = JsonUtil.fromJsonToMap(EntityUtils.toString(rsp.getEntity(), charset()));
                int code = YunpianApi.code(rspMap, CallApi.this.version());
                r.setCode(code).setMsg(rspMap.containsKey(MSG) ? rspMap.get(MSG) : Code.getErrorMsg(code));
            } else {
                File f = new File(file);
                if (f.exists()) {
                    r.setMsg(file + " rewrited");
                } else {
                    f.getParentFile().mkdirs();
                    r.setMsg(file + " created");
                }
                fos = new FileOutputStream(f);
                fos.write(EntityUtils.toByteArray(rsp.getEntity()));
                fos.flush();
            }
        } catch (Exception e) {
            r.setCode(Code.UNKNOWN_EXCEPTION);
            r.setThrowable(e);
        } finally {
            if (fos != null)
                try {
                    fos.close();
                } catch (Exception e) {
                }
        }
        return r;
    }

    protected CallBind map2CallBind(Map<String, String> rsp) {
        if (rsp == null)
            return null;
        CallBind bind = new CallBind();
        bind.setMessage_id(rsp.get(MESSAGE_ID));
        bind.setAnonymous_number(rsp.get(ANONYMOUS_NUMBER));
        return bind;
    }

}

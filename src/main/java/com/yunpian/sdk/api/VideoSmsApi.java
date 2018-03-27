/**
 * 
 */
package com.yunpian.sdk.api;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import com.google.gson.reflect.TypeToken;
import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.constant.Code;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsBatchSend;
import com.yunpian.sdk.model.SmsSingleSend;
import com.yunpian.sdk.model.Template;
import com.yunpian.sdk.util.HttpEntityWrapper;
import com.yunpian.sdk.util.JsonUtil;

/**
 * @author dzh
 * @date Dec 14, 2017 4:18:29 PM
 * @since 1.2.6
 */
public class VideoSmsApi extends YunpianApi {

    public static final String NAME = "vsms";

    /*
     * (non-Javadoc)
     * @see com.yunpian.sdk.api.YunpianApi#name()
     */
    @Override
    public String name() {
        return NAME;
    }

    @Override
    public void init(YunpianClient clnt) {
        super.init(clnt);
        host(clnt.getConf().getConf(YP_VIDEO_SMS_HOST, "https://vsms.yunpian.com"));
    }

    /**
     * 
     * @param param
     *            apikey sign
     * @param layout
     *            {@code VideoLayout}
     * @param material
     *            视频资料zip文件
     * 
     * @return
     */
    public Result<Template> addTpl(Map<String, String> param, String layout, byte[] material) {
        Result<Template> r = new Result<>();
        if (layout == null || material == null) return r.setCode(Code.ARGUMENT_MISSING);
        List<NameValuePair> list = param2pair(param, r, APIKEY, SIGN);
        if (r.getCode() != Code.OK) return r;

        Charset ch = Charset.forName(charset());
        MultipartEntityBuilder builder = MultipartEntityBuilder.create().setCharset(ch).setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        for (NameValuePair pair : list) {
            builder.addTextBody(pair.getName(), pair.getValue(), ContentType.create("text/plain", ch));
        }
        builder.addTextBody(LAYOUT, layout, ContentType.APPLICATION_JSON);
        builder.addBinaryBody(MATERIAL, material, ContentType.create("application/octet-stream", ch), null);

        StdResultHandler<Template> h = new StdResultHandler<>(new TypeToken<Result<Template>>() {}.getType());
        try {
            return path("add_tpl.json").post(new HttpEntityWrapper(builder.build()), h, r);
        } catch (Exception e) {
            return h.catchExceptoin(e, r);
        }
    }

    /**
     * 获取视频短信模版状态
     * 
     * @param param
     *            apikey tpl_id
     * @return
     */
    public Result<Template> getTpl(Map<String, String> param) {
        Result<Template> r = new Result<>();
        List<NameValuePair> list = param2pair(param, r, APIKEY, TPL_ID);
        if (r.getCode() != Code.OK) return r;
        String data = urlEncode(list);

        StdResultHandler<Template> h = new StdResultHandler<>(new TypeToken<Result<Template>>() {}.getType());
        try {
            return path("get_tpl.json").post(data, h, r);
        } catch (Exception e) {
            return h.catchExceptoin(e, r);
        }
    }

    /**
     * 批量发送视频短信
     * 
     * @param param
     *            apikey tpl_id mobile
     * @return
     */
    public Result<SmsBatchSend> tplBatchSend(Map<String, String> param) {
        Result<SmsBatchSend> r = new Result<>();
        List<NameValuePair> list = param2pair(param, r, APIKEY, TPL_ID, MOBILE);
        if (r.getCode() != Code.OK) return r;
        String data = urlEncode(list);

        MapResultHandler<SmsBatchSend> h = new MapResultHandler<SmsBatchSend>() {
            @Override
            public SmsBatchSend data(Map<String, String> rsp) {
                switch (version()) {
                case VERSION_V2:
                    return map2SendBatchSmsInfo(rsp);
                }
                return null;
            }

            @Override
            public Integer code(Map<String, String> rsp) {
                return YunpianApi.code(rsp, VideoSmsApi.this.version());
            }
        };
        try {
            return path("tpl_batch_send.json").post(data, h, r);
        } catch (Exception e) {
            return h.catchExceptoin(e, r);
        }
    }

    private static final Type TypeListBatch = new TypeToken<List<SmsSingleSend>>() {}.getType();

    protected SmsBatchSend map2SendBatchSmsInfo(Map<String, String> rsp) {
        if (rsp != null) {
            try {
                SmsBatchSend info = new SmsBatchSend();
                info.setTotal_count(Integer.parseInt(rsp.get(TOTAL_COUNT)));
                info.setTotal_fee(Double.parseDouble(rsp.get(TOTAL_FEE)));
                info.setUnit(rsp.get(UNIT));

                String data = rsp.get(DATA);
                if (data != null && data.startsWith("[")) info.setData(JsonUtil.<List<SmsSingleSend>> fromJson(data, TypeListBatch));
                return info;
            } catch (Exception e) {
                LOG.error(e.getMessage(), e.fillInStackTrace());
            }
        }
        return null;
    }

}

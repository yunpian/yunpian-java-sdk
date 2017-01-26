/**
 * 
 */
package com.yunpian.sdk.api;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.constant.Code;
import com.yunpian.sdk.constant.YunpianConstant;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.util.ApiUtil;

/**
 * TODO 优化:
 * <ul>
 * <li>返回值处理，接口设计问题，1小时代码现在1天</li>
 * <li>去除冗余字段,如api_version</li>
 * <li>时间处理，国际化</li>
 * </ul>
 * 
 * @mutable
 * @author dzh
 * @date Nov 23, 2016 1:10:02 PM
 * @since 1.2.0
 */
public abstract class YunpianApi implements YunpianConstant, YunpianApiResult {

    static final Logger LOG = LoggerFactory.getLogger(YunpianApi.class);

    private YunpianClient client;

    private String host;

    private String version;

    private String path;

    private String apikey;

    private String charset;

    public YunpianClient client() {
        return client;
    }

    /**
     * @param clnt
     */
    public synchronized void init(YunpianClient clnt) {
        if (clnt == null)
            return;
        this.client = clnt;
        apikey(clnt.getConf().getApikey()).version(clnt.getConf().getConf(YP_VERSION, VERSION_V2))
                .charset(clnt.getConf().getConf(HTTP_CHARSET, HTTP_CHARSET_DEFAULT));
    }

    public String charset() {
        return this.charset;
    }

    public YunpianApi charset(String charset) {
        this.charset = charset;
        return this;
    }

    /**
     * 
     * @return 请求全路径
     */
    public String uri() {
        StringBuilder buf = new StringBuilder(host().length() + version().length() + name().length() + path().length() + 3);
        buf.append(host()).append("/").append(version()).append("/").append(name()).append("/").append(path());
        return buf.toString();
    }

    /**
     * Api名称,如UserApi的name就是https://sms.yunpian.com/v2/user/get.json里的user
     * 
     * @return
     */
    abstract public String name();

    /**
     * @return 请求域名，如https://sms.yunpian.com
     */
    public String host() {
        return host;
    }

    public YunpianApi host(String host) {
        this.host = host;
        return this;
    }

    /**
     * 
     * @return 接口版本，如v2
     */
    public String version() {
        return version;
    }

    public YunpianApi version(String v) {
        this.version = v;
        return this;
    }

    /**
     * 
     * @return 请求路径，如user/get.json
     */
    public String path() {
        return path;
    }

    public YunpianApi path(String path) {
        this.path = path;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof YunpianApi) {
            return ((YunpianApi) obj).name().equals(name());
        }
        return false;
    }

    @Override
    public String toString() {
        return "YunpianApi-" + name();
    }

    public String apikey() {
        return apikey;
    }

    public YunpianApi apikey(String apikey) {
        this.apikey = apikey;
        return this;
    }

    // public Map<String, String> post(String uri, String data) throws Exception
    // {
    // Future<HttpResponse> rsp = client().post(uri, data);
    // try {
    // return JsonUtil.fromJsonToMap(new
    // InputStreamReader(rsp.get().getEntity().getContent(),
    // client().getConf().getConf(HTTP_CHARSET, HTTP_CHARSET_DEFAULT)),
    // JsonUtil.TypeMapString);
    // } finally {
    // EntityUtils.consumeQuietly(rsp.get().getEntity());
    // }
    // }

    public String post(String uri, String data) throws Exception {
        Future<HttpResponse> rsp = client().post(uri, data);
        return EntityUtils.toString(rsp.get().getEntity(), charset());
    }

    public <R, T> Result<T> post(String uri, String data, ResultHandler<R, T> h) {
        return post(uri, data, h, new Result<T>());
    }

    public <R, T> Result<T> post(String uri, String data, ResultHandler<R, T> h, Result<T> r) {
        LOG.debug("post uri-{} data-{}", uri, data);
        try {
            String rsp = post(uri, data);
            return result(rsp, h, r);
        } catch (Exception e) {
            return h.catchExceptoin(e, r);
        }
    }

    public static Integer code(Map<String, String> rsp, String version) {
        Integer code = Code.UNKNOWN_EXCEPTION;
        if (version == null) {
            version = VERSION_V2;
        }
        if (rsp != null) {
            switch (version) {
            case VERSION_V1:
                code = rsp.containsKey(CODE) ? (int) Double.parseDouble((rsp.get(CODE))) : Code.UNKNOWN_EXCEPTION;
                break;
            case VERSION_V2:
                code = rsp.containsKey(CODE) ? (int) Double.parseDouble((rsp.get(CODE))) : Code.OK;
                break;
            }
        }
        return code;
    }

    @Override
    public <R, T> Result<T> result(String rspContent, ResultHandler<R, T> h, Result<T> r) {
        try {
            R rsp = h.response(rspContent);
            Integer code = h.code(rsp);
            return code == Code.OK ? h.succ(code, rsp, r) : h.fail(code, rsp, r);
        } catch (Exception e) {
            return h.catchExceptoin(e, r);
        }
    }

    /**
     * 
     * @param param
     *            http request
     * 
     * @param r
     *            must be not null
     * @param must
     *            necessary parameters
     * @return
     */
    protected <T> List<NameValuePair> param2pair(Map<String, String> param, Result<T> r, String... must) {
        LOG.debug("param2pair param-{} must-{}", param, must);
        if (param == null)
            param = Collections.emptyMap();
        if (r == null)
            r = new Result<T>();

        List<NameValuePair> pair = new LinkedList<NameValuePair>();
        // validate apikey
        if (!param.containsKey(APIKEY)) {
            if (null == apikey()) {
                LOG.error("apikey is null");
                r.setCode(Code.ARGUMENT_MISSING).setMsg(Code.getErrorMsg(Code.ARGUMENT_MISSING) + "-" + APIKEY);
                return pair;
            }
            // default apikey
            pair.add(new BasicNameValuePair(APIKEY, apikey()));
        }

        // validate must
        for (String m : must) {
            if (m.equals(APIKEY))
                continue;
            if (param.get(m) == null) {
                LOG.error("miss must-{} in param-{}", m, param);
                r.setCode(Code.ARGUMENT_MISSING).setMsg(Code.getErrorMsg(Code.ARGUMENT_MISSING) + "-" + m);
                return pair;
            }
        }
        for (java.util.Map.Entry<String, String> e : param.entrySet())
            pair.add(new BasicNameValuePair(e.getKey(), e.getValue()));
        return pair;
    }

    /**
     * 
     * @param param
     * @return 'application/x-www-form-urlencoded' format
     */
    protected String urlEncode(List<NameValuePair> param) {
        if (param == null)
            return "";
        return URLEncodedUtils.format(param, charset());
    }

    /**
     * @since 1.2.2
     * @param text
     * @return
     */
    public String urlEncode(String text) {
        return ApiUtil.urlEncode(text, charset());
    }

}

/**
 * 
 */
package com.yunpian.sdk;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunpian.sdk.api.ApiFactory;
import com.yunpian.sdk.api.CallApi;
import com.yunpian.sdk.api.FlowApi;
import com.yunpian.sdk.api.SignApi;
import com.yunpian.sdk.api.SmsApi;
import com.yunpian.sdk.api.TplApi;
import com.yunpian.sdk.api.UserApi;
import com.yunpian.sdk.api.VoiceApi;
import com.yunpian.sdk.constant.YunpianConstant;

/**
 * 开始使用:
 * 
 * <pre>
 * YunpianClient yp = new YunpianClient("apikey").init();
 * yp.sms().*
 * yp.sign().*
 * yp.tpl().*
 * yp.sms().*
 * yp.voice().*
 * yp.flow().*
 * yp.call().*
 * yp.close();
 * </pre>
 * 
 * @author dzh
 * @date Nov 17, 2016 5:17:47 PM
 * @since 1.2.0
 */
public class YunpianClient implements YunpianConstant {

    static final Logger LOG = LoggerFactory.getLogger(YunpianClient.class);

    private CloseableHttpAsyncClient clnt;

    private YunpianConf conf = new YunpianConf();

    private ApiFactory api;

    /**
     * 构造器里的key作为默认值,方法请求时可以自定义
     */
    public YunpianClient() {
        this(System.getProperty(YunpianConf.YP_APIKEY), System.getProperty(YunpianConf.YP_FILE));
    }

    public YunpianClient(String apikey) {
        this(apikey, System.getProperty(YunpianConf.YP_FILE));
    }

    public YunpianClient(String apikey, String file) {
        conf.with(apikey);
        if (file != null)
            conf.with(new File(System.getProperty(YunpianConf.YP_FILE, file)));
    }

    public YunpianClient(String apikey, InputStream in) {
        conf.with(apikey).with(in);
    }

    public YunpianClient(String apikey, Properties props) {
        conf.with(apikey).with(props);
    }

    public UserApi user() {
        return api.<UserApi> api(UserApi.NAME);
    }

    public CallApi call() {
        return api.<CallApi> api(CallApi.NAME);
    }

    public FlowApi flow() {
        return api.<FlowApi> api(FlowApi.NAME);
    }

    public SignApi sign() {
        return api.<SignApi> api(SignApi.NAME);
    }

    public SmsApi sms() {
        return api.<SmsApi> api(SmsApi.NAME);
    }

    public TplApi tpl() {
        return api.<TplApi> api(TplApi.NAME);
    }

    public VoiceApi voice() {
        return api.<VoiceApi> api(VoiceApi.NAME);
    }

    private static ContentType DefaultContentType;

    @PostConstruct
    public YunpianClient init() {
        LOG.info("YunpianClient is initing!");
        try {
            clnt = createHttpAsyncClient(conf.build());
            DefaultContentType = ContentType.create("application/x-www-form-urlencoded",
                    Charset.forName(conf.getConf(YunpianConf.HTTP_CHARSET, "utf-8")));
            api = new ApiFactory(this);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e.fillInStackTrace());
        }
        return this;
    }

    public YunpianConf getConf() {
        return conf;
    }

    private CloseableHttpAsyncClient createHttpAsyncClient(YunpianConf conf) throws IOReactorException {
        IOReactorConfig ioReactorConfig = IOReactorConfig.custom().setIoThreadCount(Runtime.getRuntime().availableProcessors())
                .setConnectTimeout(conf.getConfInt(YunpianConf.HTTP_CONN_TIMEOUT, "10000"))
                .setSoTimeout(conf.getConfInt(YunpianConf.HTTP_SO_TIMEOUT, "30000")).build();
        ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);

        PoolingNHttpClientConnectionManager connManager = new PoolingNHttpClientConnectionManager(ioReactor);
        ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE)
                .setCharset(Charset.forName(conf.getConf(YunpianConf.HTTP_CHARSET, YunpianConf.HTTP_CHARSET_DEFAULT))).build();
        connManager.setDefaultConnectionConfig(connectionConfig);
        connManager.setMaxTotal(conf.getConfInt(YunpianConf.HTTP_CONN_MAXTOTAL, "100"));
        connManager.setDefaultMaxPerRoute(conf.getConfInt(YunpianConf.HTTP_CONN_MAXPREROUTE, "10"));

        CloseableHttpAsyncClient httpclient = HttpAsyncClients.custom().setConnectionManager(connManager).build();
        httpclient.start();
        return httpclient;
    }

    static Map<String, String> HEADERS = new HashMap<String, String>(1, 1);
    static {
        HEADERS.put("Api-Lang", "java");
    }

    public final Map<String, String> newParam(int size) {
        return size <= 0 ? Collections.<String, String> emptyMap() : new HashMap<String, String>(size, 1);
    }

    /**
     * 
     * @param uri
     * @param data
     */
    public Future<HttpResponse> post(String uri, String data) {
        return post(uri, data, DefaultContentType.getMimeType(), DefaultContentType.getCharset(), HEADERS);
    }

    public void closeResponse(HttpResponse rsp) {
        EntityUtils.consumeQuietly(rsp.getEntity());
    }

    public Future<HttpResponse> post(String uri, String data, String mimeType, Charset charset, Map<String, String> headers) {
        HttpPost req = new HttpPost(uri);
        req.setEntity(new StringEntity(data, ContentType.create(mimeType == null ? DefaultContentType.getMimeType() : mimeType,
                charset == null ? DefaultContentType.getCharset() : charset)));
        if (headers == null)
            headers = HEADERS;
        for (Entry<String, String> e : headers.entrySet()) {
            req.setHeader(e.getKey(), e.getValue());
        }
        return clnt.execute(req, null);
    }

    public HttpAsyncClient http() {
        return clnt;
    }

    @PreDestroy
    public void close() {
        LOG.info("YunpianClient is closing!");
        if (clnt != null) {
            try {
                clnt.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public String toString() {
        return conf.toString();
    }

}

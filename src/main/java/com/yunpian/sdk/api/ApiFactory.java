/**
 * 
 */
package com.yunpian.sdk.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunpian.sdk.YunpianClient;

/**
 * @author dzh
 * @date Nov 23, 2016 1:29:44 PM
 * @since 1.2.0
 */
public class ApiFactory {

    static Logger LOG = LoggerFactory.getLogger(ApiFactory.class);

    private YunpianClient clnt;

    public ApiFactory(YunpianClient clnt) {
        this.clnt = clnt;
    }

    @SuppressWarnings("unchecked")
    public <T extends YunpianApi> T api(String name) {
        T t = null;
        if (CallApi.NAME.equals(name)) {
            t = (T) new CallApi();
        } else if (FlowApi.NAME.equals(name)) {
            t = (T) new FlowApi();
        } else if (SignApi.NAME.equals(name)) {
            t = (T) new SignApi();
        } else if (SmsApi.NAME.equals(name)) {
            t = (T) new SmsApi();
        } else if (TplApi.NAME.equals(name)) {
            t = (T) new TplApi();
        } else if (UserApi.NAME.equals(name)) {
            t = (T) new UserApi();
        } else if (VoiceApi.NAME.equals(name)) {
            t = (T) new VoiceApi();
        }
        if (t != null)
            t.init(clnt);
        return t;
    }

}

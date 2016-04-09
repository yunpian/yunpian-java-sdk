package com.yunpian.sdk.service;

import com.google.gson.reflect.TypeToken;
import com.yunpian.sdk.common.Config;
import com.yunpian.sdk.common.YunpianException;
import com.yunpian.sdk.constants.YunpianConstants;
import com.yunpian.sdk.model.ResultDO;
import com.yunpian.sdk.model.TemplateInfo;
import com.yunpian.sdk.util.HttpUtil;
import com.yunpian.sdk.util.JsonUtil;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bingone on 16/1/19.
 */
public class TplOperator extends AbstractOperator {
    private String apikey;

    public TplOperator(String apikey) {
        this.apikey = apikey;
    }

    public ResultDO<List<TemplateInfo>> getDefault() {
        return getDefault("");
    }

    public ResultDO<List<TemplateInfo>> getDefault(final String tplId) {
        return send(Config.URI_GET_DEFAULT_TPL_SMS, new HashMap<String, String>() {{
            put(YunpianConstants.TPL_ID, tplId);
        }}, new TypeToken<List<TemplateInfo>>() {
        }.getType());
    }

    public ResultDO<TemplateInfo> add(final String Tplcontent) {
        return send(Config.URI_ADD_TPL_SMS, new HashMap<String, String>() {{
            put(YunpianConstants.TPL_CONTENT, Tplcontent);
        }});
    }

    public ResultDO<List<TemplateInfo>> get(final String tplId) {
        return send(Config.URI_GET_TPL_SMS, new HashMap<String, String>() {{
            put(YunpianConstants.TPL_ID, tplId);
        }}, new TypeToken<List<TemplateInfo>>() {
        }.getType());
    }

    public ResultDO<List<TemplateInfo>> get() {
        return get("");
    }

    public ResultDO<TemplateInfo> update(final String tplId, final String Tplcontent) {
        return send(Config.URI_UPD_TPL_SMS, new HashMap<String, String>() {{
            put(YunpianConstants.TPL_ID, tplId);
            put(YunpianConstants.TPL_CONTENT, Tplcontent);
        }});
    }

    public ResultDO<TemplateInfo> del(final String tplId) {
        return send(Config.URI_DEL_TPL_SMS, new HashMap<String, String>() {{
            put(YunpianConstants.TPL_ID, tplId);
        }});
    }

    public <T> ResultDO<T> send(String url, Map<String, String> parms) {
        return send(url, parms, new TypeToken<TemplateInfo>() {
        }.getType());
    }

    public <T> ResultDO<T> send(String url, Map<String, String> parms, Type t) {
        ResultDO<T> resultDO = new ResultDO<T>();
        parms.put(YunpianConstants.API_KEY, apikey);
        try {
            String ret = HttpUtil.post(url, parms);
            resultDO.setData(JsonUtil.<T>fromJson(ret, t));
            resultDO.setSuccess(true);
        } catch (Throwable e) {
            resultDO.setE(e);
        }
        return resultDO;
    }
}

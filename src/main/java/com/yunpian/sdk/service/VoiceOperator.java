package com.yunpian.sdk.service;

import com.google.gson.reflect.TypeToken;
import com.yunpian.sdk.common.Config;
import com.yunpian.sdk.common.YunpianException;
import com.yunpian.sdk.constants.YunpianConstants;
import com.yunpian.sdk.model.FlowStatusInfo;
import com.yunpian.sdk.model.ResultDO;
import com.yunpian.sdk.model.SendVoiceInfo;
import com.yunpian.sdk.model.VoiceStatusInfo;
import com.yunpian.sdk.util.*;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bingone on 16/1/18.
 */


/**
 * 语音操作类
 */
public class VoiceOperator extends AbstractOperator {
    private String apikey;
    private String apiSecret;

    public VoiceOperator(String apikey, String apiSecret) {
        this.apikey = apikey;
        this.apiSecret = apiSecret;
    }

    public ResultDO<SendVoiceInfo> send(String mobile, String code) {
        ResultDO<SendVoiceInfo> result = new ResultDO<SendVoiceInfo>();
        Type t = new TypeToken<SendVoiceInfo>() {
        }.getType();
        Map<String, String> parms = new HashMap<String, String>();
        parms.put(YunpianConstants.API_KEY, apikey);
        if (StringUtil.isNullOrEmpty(apiSecret)) {
            parms.put(YunpianConstants.MOBILE, mobile);
            parms.put(YunpianConstants.CODE, code);
        } else {
            try {
                parms.put(YunpianConstants.MOBILE, TeaUtil.encryptForYunpianV2(mobile, apiSecret));
                parms.put(YunpianConstants.CODE, TeaUtil.encryptForYunpianV2(code, apiSecret));
                parms.put(YunpianConstants.SIGN, SignUtil.getSign(parms, apiSecret));
            } catch (UnsupportedEncodingException e) {
                result.setE(e);
                return result;
            }
        }
        try {
            String ret = HttpUtil.post(Config.URI_SEND_VOICE_SMS, parms);
            result.setData((SendVoiceInfo) JsonUtil.fromJson(ret, t));
            result.setSuccess(true);
        } catch (Throwable e) {
            result.setE(e);
        }
        return result;
    }

    public ResultDO<List<VoiceStatusInfo>> pullStatus() {
        ResultDO<List<VoiceStatusInfo>> result = new ResultDO<List<VoiceStatusInfo>>();
        Type t = new TypeToken<List<VoiceStatusInfo>>() {
        }.getType();
        Map<String, String> parms = new HashMap<String, String>();
        parms.put(YunpianConstants.API_KEY, apikey);
        try {
            String ret = HttpUtil.post(Config.URI_PULL_VOICE_STATUS, parms);
            result.setData(JsonUtil.<List<VoiceStatusInfo>>fromJson(ret, t));
            result.setSuccess(true);
        } catch (Throwable e) {
            result.setE(e);
        }
        return result;
    }
}

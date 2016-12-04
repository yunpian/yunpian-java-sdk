package com.yunpian.sdk.service;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;
import com.yunpian.sdk.constant.Config;
import com.yunpian.sdk.constant.YunpianConstant;
import com.yunpian.sdk.model.ResultDO;
import com.yunpian.sdk.model.VoiceSend;
import com.yunpian.sdk.model.VoiceStatus;
import com.yunpian.sdk.util.HttpUtil;
import com.yunpian.sdk.util.JsonUtil;
import com.yunpian.sdk.util.SignUtil;
import com.yunpian.sdk.util.StringUtil;
import com.yunpian.sdk.util.TeaUtil;

/**
 * Created by bingone on 16/1/18.
 */

/**
 * 语音操作类
 */
@Deprecated
public class VoiceOperator extends AbstractOperator {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String apikey;
	private String apiSecret;

	public VoiceOperator(String apikey, String apiSecret) {
		this.apikey = apikey;
		this.apiSecret = apiSecret;
	}

	public ResultDO<VoiceSend> send(String mobile, String code) {
		ResultDO<VoiceSend> result = new ResultDO<VoiceSend>();
		Type t = new TypeToken<VoiceSend>() {
		}.getType();
		Map<String, String> parms = new HashMap<String, String>();
		parms.put(YunpianConstant.APIKEY, apikey);
		if (StringUtil.isNullOrEmpty(apiSecret)) {
			parms.put(YunpianConstant.MOBILE, mobile);
			parms.put(YunpianConstant.CODE, code);
		} else {
			try {
				parms.put(YunpianConstant.MOBILE, TeaUtil.encryptForYunpianV2(mobile, apiSecret));
				parms.put(YunpianConstant.CODE, TeaUtil.encryptForYunpianV2(code, apiSecret));
				parms.put(YunpianConstant.SIGN, SignUtil.getSign(parms, apiSecret));
			} catch (UnsupportedEncodingException e) {
				result.setE(e);
				return result;
			}
		}
		try {
			String ret = HttpUtil.post(Config.URI_SEND_VOICE_SMS, parms);
			result.setData((VoiceSend) JsonUtil.fromJson(ret, t));
			result.setSuccess(true);
		} catch (Throwable e) {
			result.setE(e);
		}
		return result;
	}

	public ResultDO<List<VoiceStatus>> pullStatus() {
		ResultDO<List<VoiceStatus>> result = new ResultDO<List<VoiceStatus>>();
		Type t = new TypeToken<List<VoiceStatus>>() {
		}.getType();
		Map<String, String> parms = new HashMap<String, String>();
		parms.put(YunpianConstant.APIKEY, apikey);
		try {
			String ret = HttpUtil.post(Config.URI_PULL_VOICE_STATUS, parms);
			result.setData(JsonUtil.<List<VoiceStatus>>fromJson(ret, t));
			result.setSuccess(true);
		} catch (Throwable e) {
			result.setE(e);
		}
		return result;
	}
}

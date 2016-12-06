package com.yunpian.sdk.service;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;
import com.yunpian.sdk.constant.Config;
import com.yunpian.sdk.constant.YunpianConstant;
import com.yunpian.sdk.model.FlowPackage;
import com.yunpian.sdk.model.FlowStatus;
import com.yunpian.sdk.model.ResultDO;
import com.yunpian.sdk.model.FlowSend;
import com.yunpian.sdk.util.HttpUtil;
import com.yunpian.sdk.util.JsonUtil;
import com.yunpian.sdk.util.SignUtil;
import com.yunpian.sdk.util.StringUtil;
import com.yunpian.sdk.util.TeaUtil;

/**
 * Created by bingone on 16/1/18.
 */

/**
 * 流量发送操作类
 */
@Deprecated
public class FlowOperator extends AbstractOperator {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String apikey;
	private String apiSecret;

	public FlowOperator(String apikey, String apiSecret) {
		this.apikey = apikey;
		this.apiSecret = apiSecret;
	}

	public ResultDO<List<FlowPackage>> getPackage() {
		return getPackage("");
	}

	public ResultDO<List<FlowPackage>> getPackage(String carrier) {
		ResultDO<List<FlowPackage>> result = new ResultDO<List<FlowPackage>>();
		Type t = new TypeToken<List<FlowPackage>>() {
		}.getType();
		Map<String, String> parms = new HashMap<String, String>();
		parms.put(YunpianConstant.APIKEY, apikey);
		parms.put(YunpianConstant.CARRIER, carrier);
		try {
			String ret = HttpUtil.post(Config.URI_GET_FLOW_PACKAGE, parms);
			result.setData(JsonUtil.<List<FlowPackage>>fromJson(ret, t));
			result.setSuccess(true);
		} catch (Throwable e) {
			result.setE(e);
		}
		return result;
	}

	public ResultDO<FlowSend> recharge(String mobile, String sn) {
		ResultDO<FlowSend> result = new ResultDO<FlowSend>();
		Type t = new TypeToken<FlowSend>() {
		}.getType();
		Map<String, String> parms = new HashMap<String, String>();
		parms.put(YunpianConstant.APIKEY, apikey);
		parms.put(YunpianConstant.SN, sn);
		try {
			if (!StringUtil.isNullOrEmpty(apiSecret)) {
				parms.put(YunpianConstant.MOBILE, TeaUtil.encryptForYunpianV2(mobile, apiSecret));
				parms.put(YunpianConstant.ENCRYPT, YunpianConstant.DEFAULT_ENCRYPT);
				parms.put(YunpianConstant.SIGN, SignUtil.getSign(parms, apiSecret));
			} else {
				parms.put(YunpianConstant.MOBILE, mobile);
			}
		} catch (UnsupportedEncodingException e) {
			result.setE(e);
			return result;
		}
		try {
			String ret = HttpUtil.post(Config.URI_RECHARGE_FLOW, parms);
			result.setData((FlowSend) JsonUtil.fromJson(ret, t));
			result.setSuccess(true);
		} catch (Throwable e) {
			result.setE(e);
		}
		return result;
	}

	public ResultDO<List<FlowStatus>> pullStatus() {
		ResultDO<List<FlowStatus>> result = new ResultDO<List<FlowStatus>>();
		Type t = new TypeToken<List<FlowStatus>>() {
		}.getType();
		Map<String, String> parms = new HashMap<String, String>();
		parms.put(YunpianConstant.APIKEY, apikey);
		try {
			String ret = HttpUtil.post(Config.URI_PULL_FLOW_STATUS, parms);
			result.setData(JsonUtil.<List<FlowStatus>>fromJson(ret, t));
			result.setSuccess(true);
		} catch (Throwable e) {
			result.setE(e);
		}
		return result;
	}
}

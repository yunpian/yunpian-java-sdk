package com.yunpian.sdk.service;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;
import com.yunpian.sdk.constants.Config;
import com.yunpian.sdk.constants.YunpianConstants;
import com.yunpian.sdk.constants.YunpianSdkConstants;
import com.yunpian.sdk.model.FlowPackageInfo;
import com.yunpian.sdk.model.FlowStatusInfo;
import com.yunpian.sdk.model.ResultDO;
import com.yunpian.sdk.model.SendFlowInfo;
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
public class FlowOperator extends AbstractOperator {
	private String apikey;
	private String apiSecret;

	public FlowOperator(String apikey, String apiSecret) {
		this.apikey = apikey;
		this.apiSecret = apiSecret;
	}

	public ResultDO<List<FlowPackageInfo>> getPackage() {
		return getPackage("");
	}

	public ResultDO<List<FlowPackageInfo>> getPackage(String carrier) {
		ResultDO<List<FlowPackageInfo>> result = new ResultDO<List<FlowPackageInfo>>();
		Type t = new TypeToken<List<FlowPackageInfo>>() {
		}.getType();
		Map<String, String> parms = new HashMap<String, String>();
		parms.put(YunpianConstants.API_KEY, apikey);
		parms.put(YunpianConstants.CARRIER, carrier);
		try {
			String ret = HttpUtil.post(Config.URI_GET_FLOW_PACKAGE, parms);
			result.setData(JsonUtil.<List<FlowPackageInfo>>fromJson(ret, t));
			result.setSuccess(true);
		} catch (Throwable e) {
			result.setE(e);
		}
		return result;
	}

	public ResultDO<SendFlowInfo> recharge(String mobile, String sn) {
		ResultDO<SendFlowInfo> result = new ResultDO<SendFlowInfo>();
		Type t = new TypeToken<SendFlowInfo>() {
		}.getType();
		Map<String, String> parms = new HashMap<String, String>();
		parms.put(YunpianConstants.API_KEY, apikey);
		parms.put(YunpianConstants.SN, sn);
		try {
			if (!StringUtil.isNullOrEmpty(apiSecret)) {
				parms.put(YunpianConstants.MOBILE, TeaUtil.encryptForYunpianV2(mobile, apiSecret));
				parms.put(YunpianConstants.ENCRYPT, YunpianSdkConstants.DEFAULT_ENCRYPT);
				parms.put(YunpianConstants.SIGN, SignUtil.getSign(parms, apiSecret));
			} else {
				parms.put(YunpianConstants.MOBILE, mobile);
			}
		} catch (UnsupportedEncodingException e) {
			result.setE(e);
			return result;
		}
		try {
			String ret = HttpUtil.post(Config.URI_RECHARGE_FLOW, parms);
			result.setData((SendFlowInfo) JsonUtil.fromJson(ret, t));
			result.setSuccess(true);
		} catch (Throwable e) {
			result.setE(e);
		}
		return result;
	}

	public ResultDO<List<FlowStatusInfo>> pullStatus() {
		ResultDO<List<FlowStatusInfo>> result = new ResultDO<List<FlowStatusInfo>>();
		Type t = new TypeToken<List<FlowStatusInfo>>() {
		}.getType();
		Map<String, String> parms = new HashMap<String, String>();
		parms.put(YunpianConstants.API_KEY, apikey);
		try {
			String ret = HttpUtil.post(Config.URI_PULL_FLOW_STATUS, parms);
			result.setData(JsonUtil.<List<FlowStatusInfo>>fromJson(ret, t));
			result.setSuccess(true);
		} catch (Throwable e) {
			result.setE(e);
		}
		return result;
	}
}

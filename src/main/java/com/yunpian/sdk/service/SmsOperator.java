package com.yunpian.sdk.service;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.reflect.TypeToken;
import com.yunpian.sdk.YunpianException;
import com.yunpian.sdk.constant.Config;
import com.yunpian.sdk.constant.YunpianConstant;
import com.yunpian.sdk.model.FlowStatusInfo;
import com.yunpian.sdk.model.ResultDO;
import com.yunpian.sdk.model.SendBatchSmsInfo;
import com.yunpian.sdk.model.SendSingleSmsInfo;
import com.yunpian.sdk.model.SmsRecordInfo;
import com.yunpian.sdk.model.SmsReplyInfo;
import com.yunpian.sdk.model.SmsStatusInfo;
import com.yunpian.sdk.util.DesUtil;
import com.yunpian.sdk.util.HttpUtil;
import com.yunpian.sdk.util.JsonUtil;
import com.yunpian.sdk.util.SignUtil;
import com.yunpian.sdk.util.StringUtil;
import com.yunpian.sdk.util.TeaUtil;

/**
 * Created by bingone on 16/1/18.
 */

/**
 * 短信发送操作类
 */
@Deprecated
public class SmsOperator extends AbstractOperator {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String apikey;
	private String apiSecret;
	private Type singleType = new TypeToken<SendSingleSmsInfo>() {
	}.getType();
	private Type batchType = new TypeToken<SendBatchSmsInfo>() {
	}.getType();
	private Type multiType = new TypeToken<SendBatchSmsInfo>() {
	}.getType();
	private Type tplBatchType = new TypeToken<SendBatchSmsInfo>() {
	}.getType();
	private Type tplSingleType = new TypeToken<SendSingleSmsInfo>() {
	}.getType();
	Logger logger = LoggerFactory.getLogger(SmsOperator.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public SmsOperator(String apikey, String apiSecret) {
		this.apikey = apikey;
		this.apiSecret = apiSecret;
	}

	@SuppressWarnings("unchecked")
	public ResultDO<SendBatchSmsInfo> multiSend(List<String> mobileList, List<String> textList) {
		String mobile = StringUtil.join(mobileList, ",");
		// String text = StringUtil.join(textList, ",");
		StringBuilder text = new StringBuilder();
		for (String s : textList) {
			try {
				text.append(URLEncoder.encode(s, Config.ENCODING) + ",");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				@SuppressWarnings("rawtypes")
				ResultDO resultDO = new ResultDO();
				resultDO.setE(e);
				return resultDO;
			}
		}
		String texts = text.toString();
		return multiSend(mobile, texts.substring(0, texts.length() - 1));
	}

	public ResultDO<SendBatchSmsInfo> batchSend(List<String> mobileList, String text) {
		String mobile = StringUtil.join(mobileList, ",");
		return batchSend(mobile, text);
	}

	public ResultDO<SendSingleSmsInfo> singleSend(String mobile, String text) {
		Map<String, String> parms = new HashMap<String, String>();
		parms.put(YunpianConstant.MOBILE, mobile);
		parms.put(YunpianConstant.TEXT, text);
		return send(Config.URI_SEND_SINGLE_SMS, parms, singleType);
	}

	public ResultDO<SendBatchSmsInfo> batchSend(String mobile, String text) {
		Map<String, String> parms = new HashMap<String, String>();
		parms.put(YunpianConstant.MOBILE, mobile);
		parms.put(YunpianConstant.TEXT, text);
		return send(Config.URI_SEND_BATCH_SMS, parms, batchType);
	}

	public ResultDO<SendBatchSmsInfo> multiSend(String mobile, String text) {
		Map<String, String> parms = new HashMap<String, String>();
		parms.put(YunpianConstant.MOBILE, mobile);
		parms.put(YunpianConstant.TEXT, text);
		return send(Config.URI_SEND_MULTI_SMS, parms, multiType);
	}

	public <T> ResultDO<T> send(String url, Map<String, String> parms, Type t) {
		return send(url, parms, t, YunpianConstant.DEFAULT_ENCRYPT);
	}

	public <T> ResultDO<T> send(String url, Map<String, String> parms, Type t, String encrypt) {
		logger.warn("message:" + parms + "to:" + url);
		ResultDO<T> result = new ResultDO<T>();
		String mobile = parms.get(YunpianConstant.MOBILE);
		String text = parms.get(YunpianConstant.TEXT);
		String tplValue = parms.get(YunpianConstant.TPL_VALUE);
		String tplId = parms.get(YunpianConstant.TPL_ID);

		if (StringUtil.isNullOrEmpty(mobile)) {
			result.setE(new YunpianException("手机号内容为空"));
		}
		if ((StringUtil.isNullOrEmpty(tplValue) && StringUtil.isNullOrEmpty(text))) {
			result.setE(new YunpianException("短信内容为空"));
			return result;
		}
		if (!StringUtil.isNullOrEmpty(tplValue) && StringUtil.isNullOrEmpty(tplId)) {
			result.setE(new YunpianException("模板短信ID为空"));
			return result;
		}

		parms.put(YunpianConstant.APIKEY, apikey);
		if (!StringUtil.isNullOrEmpty(apiSecret)) {
			try {
				if ("tea".equalsIgnoreCase(encrypt)) {
					parms.put(YunpianConstant.MOBILE, TeaUtil.encryptForYunpianV2(mobile, apiSecret));
					if (text != null)
						parms.put(YunpianConstant.TEXT, TeaUtil.encryptForYunpianV2(text, apiSecret));
					if (tplValue != null)
						parms.put(YunpianConstant.TPL_VALUE, TeaUtil.encryptForYunpianV2(tplValue, apiSecret));
				} else if ("des".equalsIgnoreCase(encrypt)) {
					parms.put(YunpianConstant.MOBILE, DesUtil.encryptForYunpian(mobile, apiSecret));
					if (text != null)
						parms.put(YunpianConstant.TEXT, DesUtil.decryptForYunpian(text, apiSecret));
					if (tplValue != null)
						parms.put(YunpianConstant.TPL_VALUE, DesUtil.decryptForYunpian(tplValue, apiSecret));
				}
				parms.put(YunpianConstant.ENCRYPT, YunpianConstant.DEFAULT_ENCRYPT);
				parms.put(YunpianConstant.SIGN, SignUtil.getSign(parms, apiSecret));
			} catch (Exception e) {
				logger.error("UnsupportedEncodingException" + "message:" + parms);
				result.setE(e);
				return result;
			}
		}
		try {
			String ret = HttpUtil.post(url, parms);
			result.setData(JsonUtil.<T>fromJson(ret, t));
			result.setSuccess(true);
		} catch (Throwable e) {
			logger.error(e.getMessage() + " message:" + parms);
			result.setE(e);
		}
		return result;
	}

	public ResultDO<SendBatchSmsInfo> tplBatchSend(String mobile, String tplId, String tplValue) {
		Map<String, String> parms = new HashMap<String, String>();
		parms.put(YunpianConstant.MOBILE, mobile);
		parms.put(YunpianConstant.TPL_ID, tplId);
		parms.put(YunpianConstant.TPL_VALUE, tplValue);
		return send(Config.URI_SEND_TPL_BATCH_SMS, parms, tplBatchType);
	}

	public ResultDO<SendSingleSmsInfo> tplSingleSend(String mobile, String tplId, String tplValue) {
		Map<String, String> parms = new HashMap<String, String>();
		parms.put(YunpianConstant.MOBILE, mobile);
		parms.put(YunpianConstant.TPL_ID, tplId);
		parms.put(YunpianConstant.TPL_VALUE, tplValue);
		return send(Config.URI_SEND_TPL_SINGLE_SMS, parms, tplSingleType);
	}

	public ResultDO<List<SmsStatusInfo>> pullStatus() {
		ResultDO<List<SmsStatusInfo>> result = new ResultDO<List<SmsStatusInfo>>();
		Type t = new TypeToken<List<FlowStatusInfo>>() {
		}.getType();
		Map<String, String> parms = new HashMap<String, String>();
		parms.put(YunpianConstant.APIKEY, apikey);
		try {
			String ret = HttpUtil.post(Config.URI_PULL_SMS_STATUS, parms);
			result.setData(JsonUtil.<List<SmsStatusInfo>>fromJson(ret, t));
			result.setSuccess(true);
		} catch (Throwable e) {
			result.setE(e);
		}
		return result;
	}

	public ResultDO<List<SmsReplyInfo>> pullReply() {
		ResultDO<List<SmsReplyInfo>> result = new ResultDO<List<SmsReplyInfo>>();
		Type t = new TypeToken<List<FlowStatusInfo>>() {
		}.getType();
		Map<String, String> parms = new HashMap<String, String>();
		parms.put(YunpianConstant.APIKEY, apikey);
		try {
			String ret = HttpUtil.post(Config.URI_PULL_SMS_REPLY, parms);
			result.setData(JsonUtil.<List<SmsReplyInfo>>fromJson(ret, t));
			result.setSuccess(true);
		} catch (Throwable e) {
			result.setE(e);
		}
		return result;
	}

	public ResultDO<List<SmsReplyInfo>> getReply(Date startTime, Date endTime, String mobile, String pageNum,
			String pageSize) {
		ResultDO<List<SmsReplyInfo>> result = new ResultDO<List<SmsReplyInfo>>();
		Type t = new TypeToken<List<FlowStatusInfo>>() {
		}.getType();
		Map<String, String> parms = new HashMap<String, String>();
		parms.put(YunpianConstant.APIKEY, apikey);
		parms.put(YunpianConstant.START_TIME, sdf.format(startTime));
		parms.put(YunpianConstant.END_TIME, sdf.format(endTime));
		parms.put(YunpianConstant.MOBILE, mobile);
		parms.put(YunpianConstant.PAGE_NUM, pageNum);
		parms.put(YunpianConstant.PAGE_SIZE, pageSize);
		try {
			String ret = HttpUtil.post(Config.URI_GET_SMS_REPLY, parms);
			result.setData(JsonUtil.<List<SmsReplyInfo>>fromJson(ret, t));
			result.setSuccess(true);
		} catch (Throwable e) {
			result.setE(e);
		}
		return result;
	}

	public ResultDO<List<SmsRecordInfo>> getRecord(Date startTime, Date endTime, String mobile, String pageNum,
			String pageSize) {
		ResultDO<List<SmsRecordInfo>> result = new ResultDO<List<SmsRecordInfo>>();
		Type t = new TypeToken<List<FlowStatusInfo>>() {
		}.getType();
		Map<String, String> parms = new HashMap<String, String>();
		parms.put(YunpianConstant.APIKEY, apikey);
		parms.put(YunpianConstant.START_TIME, sdf.format(startTime));
		parms.put(YunpianConstant.END_TIME, sdf.format(endTime));
		parms.put(YunpianConstant.MOBILE, mobile);
		parms.put(YunpianConstant.PAGE_NUM, pageNum);
		parms.put(YunpianConstant.PAGE_SIZE, pageSize);
		try {
			String ret = HttpUtil.post(Config.URI_PULL_SMS_REPLY, parms);
			result.setData(JsonUtil.<List<SmsRecordInfo>>fromJson(ret, t));
			result.setSuccess(true);
		} catch (Throwable e) {
			result.setE(e);
		}
		return result;
	}
}

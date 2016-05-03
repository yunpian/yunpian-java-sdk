package com.yunpian.sdk.service;

import com.google.gson.reflect.TypeToken;
import com.yunpian.sdk.common.Config;
import com.yunpian.sdk.common.YunpianException;
import com.yunpian.sdk.constants.YunpianConstants;
import com.yunpian.sdk.constants.YunpianSdkConstants;
import com.yunpian.sdk.model.*;
import com.yunpian.sdk.util.*;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bingone on 16/1/18.
 */
public class SmsOperator extends AbstractOperator {
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
    Logger logger = Logger.getLogger(getClass());
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public SmsOperator(String apikey, String apiSecret) {
        this.apikey = apikey;
        this.apiSecret = apiSecret;
    }


    public ResultDO<SendBatchSmsInfo> multiSend(List<String> mobileList, List<String> textList) {
        String mobile = StringUtil.join(mobileList, ",");
        //        String text = StringUtil.join(textList, ",");
        StringBuilder text = new StringBuilder();
        for (String s : textList) {
            try {
                text.append(URLEncoder.encode(s, Config.ENCODING) + ",");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
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
        parms.put(YunpianConstants.MOBILE, mobile);
        parms.put(YunpianConstants.TEXT, text);
        return send(Config.URI_SEND_SINGLE_SMS, parms, singleType);
    }

    public ResultDO<SendBatchSmsInfo> batchSend(String mobile, String text) {
        Map<String, String> parms = new HashMap<String, String>();
        parms.put(YunpianConstants.MOBILE, mobile);
        parms.put(YunpianConstants.TEXT, text);
        return send(Config.URI_SEND_BATCH_SMS, parms, batchType);
    }

    public ResultDO<SendBatchSmsInfo> multiSend(String mobile, String text) {
        Map<String, String> parms = new HashMap<String, String>();
        parms.put(YunpianConstants.MOBILE, mobile);
        parms.put(YunpianConstants.TEXT, text);
        return send(Config.URI_SEND_MULTI_SMS, parms, multiType);
    }

    public <T> ResultDO<T> send(String url, Map<String, String> parms, Type t) {
        return send(url, parms, t, YunpianSdkConstants.DEFAULT_ENCRYPT);
    }

    public <T> ResultDO<T> send(String url, Map<String, String> parms, Type t, String encrypt) {
        logger.warn("message:" + parms + "to:" + url);
        ResultDO<T> result = new ResultDO<T>();
        String mobile = parms.get(YunpianConstants.MOBILE);
        String text = parms.get(YunpianConstants.TEXT);
        String tplValue = parms.get(YunpianConstants.TPL_VALUE);
        String tplId = parms.get(YunpianConstants.TPL_ID);

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

        parms.put(YunpianConstants.API_KEY, apikey);
        if (!StringUtil.isNullOrEmpty(apiSecret)) {
            try {
                if ("tea".equalsIgnoreCase(encrypt)) {
                    parms.put(YunpianConstants.MOBILE,
                        TeaUtil.encryptForYunpianV2(mobile, apiSecret));
                    if (text != null)
                        parms.put(YunpianConstants.TEXT, TeaUtil.encryptForYunpianV2(text, apiSecret));
                    if (tplValue != null)
                        parms.put(YunpianConstants.TPL_VALUE,
                            TeaUtil.encryptForYunpianV2(tplValue, apiSecret));
                } else if ("des".equalsIgnoreCase(encrypt)) {
                    parms
                        .put(YunpianConstants.MOBILE, DesUtil.encryptForYunpian(mobile, apiSecret));
                    if (text != null)
                        parms.put(YunpianConstants.TEXT, DesUtil.decryptForYunpian(text, apiSecret));
                    if (tplValue != null)
                        parms.put(YunpianConstants.TPL_VALUE,
                            DesUtil.decryptForYunpian(tplValue, apiSecret));
                }
                parms.put(YunpianConstants.ENCRYPT, YunpianSdkConstants.DEFAULT_ENCRYPT);
                parms.put(YunpianConstants.SIGN, SignUtil.getSign(parms, apiSecret));
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
        parms.put(YunpianConstants.MOBILE, mobile);
        parms.put(YunpianConstants.TPL_ID, tplId);
        parms.put(YunpianConstants.TPL_VALUE, tplValue);
        return send(Config.URI_SEND_TPL_BATCH_SMS, parms, tplBatchType);
    }

    public ResultDO<SendSingleSmsInfo> tplSingleSend(String mobile, String tplId, String tplValue) {
        Map<String, String> parms = new HashMap<String, String>();
        parms.put(YunpianConstants.MOBILE, mobile);
        parms.put(YunpianConstants.TPL_ID, tplId);
        parms.put(YunpianConstants.TPL_VALUE, tplValue);
        return send(Config.URI_SEND_TPL_SINGLE_SMS, parms, tplSingleType);
    }

    public ResultDO<List<SmsStatusInfo>> pullStatus() {
        ResultDO<List<SmsStatusInfo>> result = new ResultDO<List<SmsStatusInfo>>();
        Type t = new TypeToken<List<FlowStatusInfo>>() {
        }.getType();
        Map<String, String> parms = new HashMap<String, String>();
        parms.put(YunpianConstants.API_KEY, apikey);
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
        parms.put(YunpianConstants.API_KEY, apikey);
        try {
            String ret = HttpUtil.post(Config.URI_PULL_SMS_REPLY, parms);
            result.setData(JsonUtil.<List<SmsReplyInfo>>fromJson(ret, t));
            result.setSuccess(true);
        } catch (Throwable e) {
            result.setE(e);
        }
        return result;
    }

    public ResultDO<List<SmsReplyInfo>> getReply(Date startTime, Date endTime, String mobile,
        String pageNum, String pageSize) {
        ResultDO<List<SmsReplyInfo>> result = new ResultDO<List<SmsReplyInfo>>();
        Type t = new TypeToken<List<FlowStatusInfo>>() {
        }.getType();
        Map<String, String> parms = new HashMap<String, String>();
        parms.put(YunpianConstants.API_KEY, apikey);
        parms.put(YunpianConstants.START_TIME, sdf.format(startTime));
        parms.put(YunpianConstants.END_TIME, sdf.format(endTime));
        parms.put(YunpianConstants.MOBILE, mobile);
        parms.put(YunpianConstants.PAGE_NUM, pageNum);
        parms.put(YunpianConstants.PAGE_SIZE, pageSize);
        try {
            String ret = HttpUtil.post(Config.URI_GET_SMS_REPLY, parms);
            result.setData(JsonUtil.<List<SmsReplyInfo>>fromJson(ret, t));
            result.setSuccess(true);
        } catch (Throwable e) {
            result.setE(e);
        }
        return result;
    }

    public ResultDO<List<SmsRecordInfo>> getRecord(Date startTime, Date endTime, String mobile,
        String pageNum, String pageSize) {
        ResultDO<List<SmsRecordInfo>> result = new ResultDO<List<SmsRecordInfo>>();
        Type t = new TypeToken<List<FlowStatusInfo>>() {
        }.getType();
        Map<String, String> parms = new HashMap<String, String>();
        parms.put(YunpianConstants.API_KEY, apikey);
        parms.put(YunpianConstants.START_TIME, sdf.format(startTime));
        parms.put(YunpianConstants.END_TIME, sdf.format(endTime));
        parms.put(YunpianConstants.MOBILE, mobile);
        parms.put(YunpianConstants.PAGE_NUM, pageNum);
        parms.put(YunpianConstants.PAGE_SIZE, pageSize);
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

package com.yunpian.sdk.service;

import java.util.HashMap;
import java.util.Map;

import com.yunpian.sdk.constant.Config;
import com.yunpian.sdk.constant.YunpianConstant;
import com.yunpian.sdk.model.ResultDO;
import com.yunpian.sdk.model.UserInfo;
import com.yunpian.sdk.util.HttpUtil;
import com.yunpian.sdk.util.JsonUtil;

/**
 * Created by bingone on 16/1/19.
 */
@SuppressWarnings("deprecation")
public class UserOperator {
	private String apikey;

	public UserOperator(String apikey) {
		this.apikey = apikey;
	}

	public ResultDO<UserInfo> get() {
		return get(Config.URI_GET_USER_INFO);
	}

	public ResultDO<UserInfo> get(String url) {
		ResultDO<UserInfo> resultDO = new ResultDO<UserInfo>();
		Map<String, String> parms = new HashMap<String, String>();
		parms.put(YunpianConstant.APIKEY, apikey);
		try {
			String ret = HttpUtil.post(url, parms);
			resultDO.setData(JsonUtil.<UserInfo>fromJson(ret, UserInfo.class));
			resultDO.setSuccess(true);
		} catch (Throwable e) {
			resultDO.setE(e);
		}
		return resultDO;
	}
}

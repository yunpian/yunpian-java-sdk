package com.yunpian.sdk.service;

import com.yunpian.sdk.constant.Config;

/**
 * Created by bingone on 16/1/18.
 */

/**
 * 请大家迁移到{@code YunpianClient}，提供更好的扩展和性能
 * 
 * 用户操作入口
 */
@Deprecated
@SuppressWarnings("static-access")
public class YunpianRestClient {
	private static Config config = new Config();
	private String apikey = config.APIKEY;
	private String apiSecret = config.API_SECRET;
	private FlowOperator flowOperator;
	private VoiceOperator voiceOperator;
	private TplOperator tplOperator;
	private UserOperator userOperator;
	private SmsOperator smsOperator;

	public YunpianRestClient(String apikey, String apiSecret) {
		this.apikey = apikey;
		this.apiSecret = apiSecret;
		init();
	}

	public YunpianRestClient(String apikey) {
		this.apikey = apikey;
		this.apiSecret = null;
		init();
	}

	// public YunpianRestClient() {
	// init();
	// }

	private void init() {
		smsOperator = new SmsOperator(apikey, apiSecret);
		flowOperator = new FlowOperator(apikey, apiSecret);
		voiceOperator = new VoiceOperator(apikey, apiSecret);
		tplOperator = new TplOperator(apikey);
		userOperator = new UserOperator(apikey);
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public String getApiSecret() {
		return apiSecret;
	}

	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
	}

	public static Config getConfig() {
		return config;
	}

	public static void setConfig(Config config) {
		YunpianRestClient.config = config;
	}

	public FlowOperator getFlowOperator() {
		return flowOperator;
	}

	public void setFlowOperator(FlowOperator flowOperator) {
		this.flowOperator = flowOperator;
	}

	public SmsOperator getSmsOperator() {
		return smsOperator;
	}

	public void setSmsOperator(SmsOperator smsOperator) {
		this.smsOperator = smsOperator;
	}

	public TplOperator getTplOperator() {
		return tplOperator;
	}

	public void setTplOperator(TplOperator tplOperator) {
		this.tplOperator = tplOperator;
	}

	public UserOperator getUserOperator() {
		return userOperator;
	}

	public void setUserOperator(UserOperator userOperator) {
		this.userOperator = userOperator;
	}

	public VoiceOperator getVoiceOperator() {
		return voiceOperator;
	}

	public void setVoiceOperator(VoiceOperator voiceOperator) {
		this.voiceOperator = voiceOperator;
	}

	@Override
	public String toString() {
		return "YunpianRestClient{" + "apikey='" + apikey + '\'' + ", apiSecret='" + apiSecret + '\''
				+ ", flowOperator=" + flowOperator + ", voiceOperator=" + voiceOperator + ", tplOperator=" + tplOperator
				+ ", userOperator=" + userOperator + ", smsOperator=" + smsOperator + '}';
	}
}

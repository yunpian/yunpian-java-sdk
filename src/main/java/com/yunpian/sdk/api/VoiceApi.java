/**
 * 
 */
package com.yunpian.sdk.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;

import com.google.gson.reflect.TypeToken;
import com.yunpian.sdk.constant.Code;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SendVoiceInfo;
import com.yunpian.sdk.model.VoiceStatusInfo;
import com.yunpian.sdk.util.JsonUtil;

/**
 * 
 * https://www.yunpian.com/api2.0/voice.html
 * 
 * @author dzh
 * @date Nov 23, 2016 1:12:57 PM
 * @since 1.2.0
 */
public class VoiceApi extends YunpianApi {

	public static final String NAME = "voice";

	@Override
	public String name() {
		return NAME;
	}

	/**
	 * <h1>发语音验证码</h1>
	 * 
	 * <p>
	 * 参数名 类型 是否必须 描述 示例
	 * </p>
	 * <p>
	 * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
	 * </p>
	 * <p>
	 * mobile String 是 接收的手机号、固话（需加区号） 15205201314 01088880000
	 * </p>
	 * <p>
	 * code String 是 验证码，支持4~6位阿拉伯数字 1234
	 * </p>
	 * <p>
	 * encrypt String 否 加密方式 使用加密 tea (不再使用)
	 * </p>
	 * <p>
	 * _sign String 否 签名字段 参考使用加密 393d079e0a00912335adfe46f4a2e10f (不再使用)
	 * </p>
	 * <p>
	 * callback_url String 否 本条语音验证码状态报告推送地址 http://your_receive_url_address
	 * </p>
	 * <p>
	 * display_num String 否 透传号码，为保证全国范围的呼通率，云片会自动选择最佳的线路，透传的主叫号码也会相应变化。
	 * 如需透传固定号码则需要单独注册报备，为了确保号码真实有效，客服将要求您使用报备的号码拨打一次客服电话
	 * </p>
	 * 
	 * @param param
	 * @return
	 */
	public Result<SendVoiceInfo> send(Map<String, String> param) {
		Result<SendVoiceInfo> r = new Result<>();
		List<NameValuePair> list = param2pair(param, r, APIKEY, MOBILE, CODE);
		if (r.getCode() != Code.OK)
			return r;
		String data = format2Form(list);

		MapResultHandler<SendVoiceInfo> h = new MapResultHandler<SendVoiceInfo>() {
			@Override
			public SendVoiceInfo data(Map<String, String> rsp) {
				switch (version()) {
				case VERSION_V1:
					return JsonUtil.fromJson(rsp.get(RESULT), SendVoiceInfo.class);
				case VERSION_V2:
					return map2VoiceResult(rsp);
				}
				return null;
			}

			@Override
			public Integer code(Map<String, String> rsp) {
				return YunpianApi.code(rsp, VoiceApi.this.version());
			}
		};
		try {
			return path("send.json").post(uri(), data, h, r);
		} catch (Exception e) {
			return h.catchExceptoin(e, r);
		}
	}

	/**
	 * <h1>获取状态报告</h1>
	 * 
	 * <p>
	 * 参数名 是否必须 描述 示例
	 * </p>
	 * <p>
	 * apikey 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
	 * </p>
	 * <p>
	 * page_size 否 每页个数，最大100个，默认20个 20
	 * </p>
	 * 
	 * @param param
	 * @return
	 */
	public Result<List<VoiceStatusInfo>> pull_status(Map<String, String> param) {
		Result<List<VoiceStatusInfo>> r = new Result<>();
		List<NameValuePair> list = param2pair(param, r, APIKEY);
		if (r.getCode() != Code.OK)
			return r;
		String data = format2Form(list);

		SimpleListResultHandler<VoiceStatusInfo> h = new SimpleListResultHandler<VoiceStatusInfo>() {
			@Override
			public List<VoiceStatusInfo> data(List<VoiceStatusInfo> rsp) {
				switch (version()) {
				case VERSION_V1:
					if (rspMap != null) {
						String flow = rspMap.get(VOICE_STATUS);
						return JsonUtil.<ArrayList<VoiceStatusInfo>>fromJson(flow,
								new TypeToken<ArrayList<VoiceStatusInfo>>() {
								}.getType());
					}
				case VERSION_V2:
					return rsp;
				}
				return Collections.emptyList();
			}

			@Override
			public Integer code(List<VoiceStatusInfo> rsp) {
				if (rspMap != null) {
					return YunpianApi.code(rspMap, VoiceApi.this.version());
				}
				return Code.OK;
			}
		};
		try {
			return path("pull_status.json").post(uri(), data, h, r);
		} catch (Exception e) {
			return h.catchExceptoin(e, r);
		}
	}

	protected SendVoiceInfo map2VoiceResult(Map<String, String> rsp) {
		if (rsp == null || rsp.isEmpty())
			return null;
		try {
			SendVoiceInfo voice = new SendVoiceInfo();
			voice.setCount(rsp.get(COUNT));
			voice.setFee(Double.parseDouble(rsp.get(FEE)));
			voice.setSid(rsp.get(SID));
			return voice;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e.fillInStackTrace());
		}
		return null;
	}

}

/**
 * 
 */
package com.yunpian.sdk.api;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;

import com.google.gson.reflect.TypeToken;
import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.constant.Code;
import com.yunpian.sdk.model.Blackword;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsBatchSend;
import com.yunpian.sdk.model.SmsRecord;
import com.yunpian.sdk.model.SmsReply;
import com.yunpian.sdk.model.SmsSingleSend;
import com.yunpian.sdk.model.SmsStatus;
import com.yunpian.sdk.util.JsonUtil;

/**
 * https://www.yunpian.com/api2.0/sms.html
 * 
 * @author dzh
 * @date Nov 23, 2016 1:12:05 PM
 * @since 1.2.0
 */
public class SmsApi extends YunpianApi {

	public static final String NAME = "sms";

	@Override
	public String name() {
		return NAME;
	}

	@Override
	public void init(YunpianClient clnt) {
		super.init(clnt);
		host(clnt.getConf().getConf(YP_SMS_HOST, "https://sms.yunpian.com"));
	}

	/**
	 * <h1>智能匹配模板发送 only v1</h1>
	 * 
	 * <p>
	 * 参数名 类型 是否必须 描述 示例
	 * </p>
	 * <p>
	 * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
	 * </p>
	 * <p>
	 * mobile String 是 接收的手机号;发送多个手机号请以逗号分隔，一次不要超过1000个
	 * 国际短信仅支持单号码发送，国际号码需包含国际地区前缀号码，格式必须是"+"号开头("+"号需要urlencode处理，否则会出现格式错误)，国际号码不以"+"开头将被认为是中国地区的号码
	 * （针对国际短信，mobile参数会自动格式化到E.164格式，可能会造成传入mobile参数跟后续的状态报告中的号码不一致。E.164格式说明，参见：
	 * https://en.wikipedia.org/wiki/E.164） 单号码：15205201314
	 * 多号码：15205201314,15205201315 国际短信：+93701234567
	 * </p>
	 * <p>
	 * text String 是 短信内容 【云片网】您的验证码是1234
	 * </p>
	 * <p>
	 * extend String 否 扩展号。默认不开放，如有需要请联系客服申请 001
	 * </p>
	 * <p>
	 * uid String 否 该条短信在您业务系统内的ID，比如订单号或者短信发送记录的流水号。填写后发送状态返回值内将包含这个ID
	 * 默认不开放，如有需要请联系客服申请 10001
	 * </p>
	 * <p>
	 * callback_url String 否
	 * 本条短信状态报告推送地址。短信发送后将向这个地址推送短信发送报告。"后台-系统设置-数据推送与获取”可以做批量设置。如果后台已经设置地址的情况下，单次请求内也包含此参数，将以请求内的推送地址为准。
	 * </p>
	 * 
	 * @param param
	 * @return
	 */
	@Deprecated
	public Result<SmsSingleSend> send(Map<String, String> param) {
		Result<SmsSingleSend> r = new Result<>();
		List<NameValuePair> list = param2pair(param, r, APIKEY, MOBILE, TEXT);
		if (r.getCode() != Code.OK)
			return r;
		String data = format2Form(list);

		MapResultHandler<SmsSingleSend> h = new MapResultHandler<SmsSingleSend>() {
			@Override
			public SmsSingleSend data(Map<String, String> rsp) {
				switch (version()) {
				case VERSION_V1:
					return JsonUtil.fromJson(rsp.get(RESULT), SmsSingleSend.class);
				}
				return null;
			}

			@Override
			public Integer code(Map<String, String> rsp) {
				return YunpianApi.code(rsp, SmsApi.this.version());
			}
		};
		try {
			return path("send.json").post(uri(), data, h, r);
		} catch (Exception e) {
			return h.catchExceptoin(e, r);
		}
	}

	/**
	 * <h1>单条发送</h1>
	 * 
	 * <p>
	 * 参数名 类型 是否必须 描述 示例
	 * </p>
	 * <p>
	 * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
	 * </p>
	 * <p>
	 * mobile String 是
	 * 接收的手机号；仅支持单号码发送；国际号码需包含国际地区前缀号码，格式必须是"+"号开头("+"号需要urlencode处理，否则会出现格式错误)，国际号码不以"+"开头将被认为是中国地区的号码
	 * （针对国际短信，mobile参数会自动格式化到E.164格式，可能会造成传入mobile参数跟后续的状态报告中的号码不一致。E.164格式说明，参见：
	 * https://en.wikipedia.org/wiki/E.164） 国内号码：15205201314
	 * 国际号码：urlencode("+93701234567");
	 * </p>
	 * <p>
	 * text String 是 短信内容 【云片网】您的验证码是1234
	 * </p>
	 * <p>
	 * extend String 否 扩展号。默认不开放，如有需要请联系客服申请 001
	 * </p>
	 * <p>
	 * uid String 否 该条短信在您业务系统内的ID，比如订单号或者短信发送记录的流水号。填写后发送状态返回值内将包含这个ID
	 * 默认不开放，如有需要请联系客服申请 10001
	 * </p>
	 * <p>
	 * callback_url String 否
	 * 本条短信状态报告推送地址。短信发送后将向这个地址推送短信发送报告。"后台-系统设置-数据推送与获取”可以做批量设置。如果后台已经设置地址的情况下，单次请求内也包含此参数，将以请求内的推送地址为准。
	 * http://your_receive_url_address
	 * </p>
	 * 
	 * @param param
	 * @return
	 */
	public Result<SmsSingleSend> single_send(Map<String, String> param) {
		Result<SmsSingleSend> r = new Result<>();
		List<NameValuePair> list = param2pair(param, r, APIKEY, MOBILE, TEXT);
		if (r.getCode() != Code.OK)
			return r;
		String data = format2Form(list);

		MapResultHandler<SmsSingleSend> h = new MapResultHandler<SmsSingleSend>() {
			@Override
			public SmsSingleSend data(Map<String, String> rsp) {
				switch (version()) {
				case VERSION_V2:
					return map2SendSingleSmsInfo(rsp);
				}
				return null;
			}

			@Override
			public Integer code(Map<String, String> rsp) {
				return YunpianApi.code(rsp, SmsApi.this.version());
			}
		};
		try {
			return path("single_send.json").post(uri(), data, h, r);
		} catch (Exception e) {
			return h.catchExceptoin(e, r);
		}
	}

	/**
	 * <h1>批量发送</h1>
	 * 
	 * <p>
	 * 参数名 类型 是否必须 描述 示例
	 * </p>
	 * <p>
	 * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
	 * </p>
	 * <p>
	 * mobile String 是 接收的手机号；发送多个手机号请以逗号分隔，一次不要超过1000个。 单号码：15205201314
	 * 多号码：15205201314,15205201315
	 * </p>
	 * <p>
	 * text String 是 短信内容 【云片网】您的验证码是1234
	 * </p>
	 * <p>
	 * extend String 否 扩展号。默认不开放，如有需要请联系客服申请 001
	 * </p>
	 * <p>
	 * uid String 否 该条短信在您业务系统内的ID，比如订单号或者短信发送记录的流水号。填写后发送状态返回值内将包含这个ID
	 * 默认不开放，如有需要请联系客服申请 10001
	 * </p>
	 * <p>
	 * callback_url String 否
	 * 本条短信状态报告推送地址。短信发送后将向这个地址推送短信发送报告。"后台-系统设置-数据推送与获取”可以做批量设置。如果后台已经设置地址的情况下，单次请求内也包含此参数，将以请求内的推送地址为准。
	 * http://your_receive_url_address
	 * </p>
	 * 
	 * @param param
	 * @return
	 */
	public Result<SmsBatchSend> batch_send(Map<String, String> param) {
		Result<SmsBatchSend> r = new Result<>();
		List<NameValuePair> list = param2pair(param, r, APIKEY, MOBILE, TEXT);
		if (r.getCode() != Code.OK)
			return r;
		String data = format2Form(list);

		MapResultHandler<SmsBatchSend> h = new MapResultHandler<SmsBatchSend>() {
			@Override
			public SmsBatchSend data(Map<String, String> rsp) {
				switch (version()) {
				case VERSION_V2:
					return map2SendBatchSmsInfo(rsp);
				}
				return null;
			}

			@Override
			public Integer code(Map<String, String> rsp) {
				return YunpianApi.code(rsp, SmsApi.this.version());
			}
		};
		try {
			return path("batch_send.json").post(uri(), data, h, r);
		} catch (Exception e) {
			return h.catchExceptoin(e, r);
		}
	}

	/**
	 * <h1>个性化发送</h1>
	 * 
	 * <p>
	 * 参数名 类型 是否必须 描述 示例
	 * </p>
	 * <p>
	 * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
	 * </p>
	 * <p>
	 * mobile String 是
	 * 接收的手机号；多个手机号请以逗号分隔，一次不要超过1000个且手机号个数必须与短信内容条数相等；不支持国际号码发送；
	 * 多号码：15205201314,15205201315
	 * </p>
	 * <p>
	 * text String 是
	 * 短信内容，多个短信内容请使用UTF-8做urlencode后，使用逗号分隔，一次不要超过1000条且短信内容条数必须与手机号个数相等
	 * 内容示意：UrlEncode("【云片网】您的验证码是1234", "UTF-8") + "," +
	 * UrlEncode("【云片网】您的验证码是5678", "UTF-8")
	 * </p>
	 * <p>
	 * extend String 否 扩展号。默认不开放，如有需要请联系客服申请 001
	 * </p>
	 * <p>
	 * uid String 否 该条短信在您业务系统内的ID，比如订单号或者短信发送记录的流水号。填写后发送状态返回值内将包含这个ID
	 * 默认不开放，如有需要请联系客服申请 10001
	 * </p>
	 * <p>
	 * callback_url String 否
	 * 本条短信状态报告推送地址。短信发送后将向这个地址推送短信发送报告。"后台-系统设置-数据推送与获取”可以做批量设置。如果后台已经设置地址的情况下，单次请求内也包含此参数，将以请求内的推送地址为准。
	 * http://your_receive_url_address
	 * </p>
	 * 
	 * @param param
	 * @return
	 */
	public Result<SmsBatchSend> multi_send(Map<String, String> param) {
		Result<SmsBatchSend> r = new Result<>();
		List<NameValuePair> list = param2pair(param, r, APIKEY, MOBILE, TEXT);
		if (r.getCode() != Code.OK)
			return r;
		String data = format2Form(list);

		MapResultHandler<SmsBatchSend> h = new MapResultHandler<SmsBatchSend>() {
			@Override
			public SmsBatchSend data(Map<String, String> rsp) {
				switch (version()) {
				case VERSION_V2:
					return map2SendBatchSmsInfo(rsp);
				}
				return null;
			}

			@Override
			public Integer code(Map<String, String> rsp) {
				return YunpianApi.code(rsp, SmsApi.this.version());
			}
		};
		try {
			return path("multi_send.json").post(uri(), data, h, r);
		} catch (Exception e) {
			return h.catchExceptoin(e, r);
		}
	}

	/**
	 * /v1/sms/multi_send.json
	 * 
	 * @param param
	 * @return
	 */
	public Result<List<Result<SmsSingleSend>>> multi_send_v1(Map<String, String> param) {
		Result<List<Result<SmsSingleSend>>> r = new Result<>();
		List<NameValuePair> list = param2pair(param, r, APIKEY, MOBILE, TEXT);
		if (r.getCode() != Code.OK)
			return r;
		String data = format2Form(list);

		SimpleListResultHandler<Result<SmsSingleSend>> h = new SimpleListResultHandler<Result<SmsSingleSend>>() {
			@Override
			public List<Result<SmsSingleSend>> data(List<Result<SmsSingleSend>> rsp) {
				switch (version()) {
				case VERSION_V1:
					return rsp;
				}
				return null;
			}

			@Override
			public Integer code(List<Result<SmsSingleSend>> rsp) {
				if (rspMap != null) {
					return YunpianApi.code(rspMap, SmsApi.this.version());
				}
				return Code.OK;
			}

			@Override
			Type rspType() {
				return new TypeToken<List<SmsSingleSend>>() {
				}.getType();
			}
		};
		try {
			return version(VERSION_V1).path("multi_send.json").post(uri(), data, h, r);
		} catch (Exception e) {
			return h.catchExceptoin(e, r);
		}
	}

	/**
	 * <h1>获取状态报告</h1>
	 * 
	 * <p>
	 * 参数名 类型 是否必须 描述 示例
	 * </p>
	 * <p>
	 * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
	 * </p>
	 * <p>
	 * page_size Integer 否 每页个数，最大100个，默认20个 20
	 * </p>
	 * 
	 * @param param
	 * @return
	 */
	public Result<List<SmsStatus>> pull_status(Map<String, String> param) {
		Result<List<SmsStatus>> r = new Result<>();
		List<NameValuePair> list = param2pair(param, r, APIKEY);
		if (r.getCode() != Code.OK)
			return r;
		String data = format2Form(list);

		SimpleListResultHandler<SmsStatus> h = new SimpleListResultHandler<SmsStatus>() {
			@Override
			public List<SmsStatus> data(List<SmsStatus> rsp) {
				switch (version()) {
				case VERSION_V1:
					if (rspMap != null) {
						String flow = rspMap.get(SMS_STATUS);
						return JsonUtil.<List<SmsStatus>>fromJson(flow, TypeListStatus);
					}
				case VERSION_V2:
					return rsp;
				}
				return Collections.emptyList();
			}

			@Override
			public Integer code(List<SmsStatus> rsp) {
				if (rspMap != null) {
					return YunpianApi.code(rspMap, SmsApi.this.version());
				}
				return Code.OK;
			}

			@Override
			Type rspType() {
				return new TypeToken<List<SmsStatus>>() {
				}.getType();
			}
		};
		try {
			return path("pull_status.json").post(uri(), data, h, r);
		} catch (Exception e) {
			return h.catchExceptoin(e, r);
		}
	}

	/**
	 * <h1>获取回复短信</h1>
	 * 
	 * <p>
	 * 参数名 类型 是否必须 描述 示例
	 * </p>
	 * <p>
	 * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
	 * </p>
	 * <p>
	 * page_size Integer 否 每页个数，最大100个，默认20个 20
	 * </p>
	 * 
	 * @param param
	 * @return
	 */
	public Result<List<SmsReply>> pull_reply(Map<String, String> param) {
		Result<List<SmsReply>> r = new Result<>();
		List<NameValuePair> list = param2pair(param, r, APIKEY);
		if (r.getCode() != Code.OK)
			return r;
		String data = format2Form(list);

		SimpleListResultHandler<SmsReply> h = new SimpleListResultHandler<SmsReply>() {
			@Override
			public List<SmsReply> data(List<SmsReply> rsp) {
				switch (version()) {
				case VERSION_V1:
					if (rspMap != null) {
						String smsReply = rspMap.get(SMS_REPLY);
						if (smsReply != null && smsReply.startsWith("[")) {
							return JsonUtil.fromJson(smsReply, TypeListReply);
						}
					}
				case VERSION_V2:
					return rsp;
				}
				return null;
			}

			@Override
			public Integer code(List<SmsReply> rsp) {
				if (rspMap != null) {
					return YunpianApi.code(rspMap, SmsApi.this.version());
				}
				return Code.OK;
			}

			@Override
			Type rspType() {
				return new TypeToken<List<SmsReply>>() {
				}.getType();
			}
		};
		try {
			return path("pull_reply.json").post(uri(), data, h, r);
		} catch (Exception e) {
			return h.catchExceptoin(e, r);
		}
	}

	/**
	 * <h1>查回复的短信</h1>
	 * 
	 * <p>
	 * 参数名 类型 是否必须 描述 示例
	 * </p>
	 * <p>
	 * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
	 * </p>
	 * <p>
	 * start_time String 是 短信回复开始时间 2013-08-11 00:00:00
	 * </p>
	 * <p>
	 * end_time String 是 短信回复结束时间 2013-08-12 00:00:00
	 * </p>
	 * <p>
	 * page_num Integer 是 页码，默认值为1 1
	 * </p>
	 * <p>
	 * page_size Integer 是 每页个数，最大100个 20
	 * </p>
	 * <p>
	 * mobile String 否 填写时只查该手机号的回复，不填时查所有的回复 15205201314
	 * </p>
	 * <p>
	 * return_fields 否 返回字段（暂未开放
	 * </p>
	 * <p>
	 * sort_fields 否 排序字段（暂未开放） 默认按提交时间降序
	 * </p>
	 * 
	 * @param param
	 * @return
	 */
	public Result<List<SmsReply>> get_reply(Map<String, String> param) {
		Result<List<SmsReply>> r = new Result<>();
		List<NameValuePair> list = param2pair(param, r, APIKEY, START_TIME, END_TIME, PAGE_NUM, PAGE_SIZE);
		if (r.getCode() != Code.OK)
			return r;
		String data = format2Form(list);

		SimpleListResultHandler<SmsReply> h = new SimpleListResultHandler<SmsReply>() {
			@Override
			public List<SmsReply> data(List<SmsReply> rsp) {
				switch (version()) {
				case VERSION_V1:
					if (rspMap != null) {
						String smsReply = rspMap.get(SMS_REPLY);
						if (smsReply != null && smsReply.startsWith("[")) {
							return JsonUtil.fromJson(smsReply, TypeListReply);
						}
					}
				case VERSION_V2:
					return rsp;
				}
				return null;
			}

			@Override
			public Integer code(List<SmsReply> rsp) {
				if (rspMap != null) {
					return YunpianApi.code(rspMap, SmsApi.this.version());
				}
				return Code.OK;
			}

			@Override
			Type rspType() {
				return new TypeToken<List<SmsReply>>() {
				}.getType();
			}
		};
		try {
			return path("get_reply.json").post(uri(), data, h, r);
		} catch (Exception e) {
			return h.catchExceptoin(e, r);
		}
	}

	/**
	 * <h1>查屏蔽词</h1>
	 * 
	 * <p>
	 * 参数名 类型 是否必须 描述 示例
	 * </p>
	 * <p>
	 * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
	 * </p>
	 * <p>
	 * text String 是 要检查的短信模板或者内容 这是一条测试短信
	 * </p>
	 * 
	 * @param param
	 * @return
	 */
	public Result<List<String>> get_black_word(Map<String, String> param) {
		Result<List<String>> r = new Result<>();
		List<NameValuePair> list = param2pair(param, r, APIKEY, TEXT);
		if (r.getCode() != Code.OK)
			return r;
		String data = format2Form(list);

		SimpleListResultHandler<String> h = new SimpleListResultHandler<String>() {
			@Override
			public List<String> data(List<String> rsp) {
				switch (version()) {
				case VERSION_V1:
					if (rspMap != null) {
						String result = rspMap.get(RESULT);
						if (result != null)
							return JsonUtil.fromJson(result, Blackword.class).toList();
					}
				case VERSION_V2:
					return rsp;
				}
				return null;
			}

			@Override
			public Integer code(List<String> rsp) {
				if (rspMap != null) {
					return YunpianApi.code(rspMap, SmsApi.this.version());
				}
				return Code.OK;
			}

			@Override
			Type rspType() {
				return new TypeToken<List<String>>() {
				}.getType();
			}
		};
		try {
			return path("get_black_word.json").post(uri(), data, h, r);
		} catch (Exception e) {
			return h.catchExceptoin(e, r);
		}
	}

	/**
	 * <h1>查短信发送记录</h1>
	 * 
	 * <p>
	 * 参数名 类型 是否必须 描述 示例
	 * </p>
	 * <p>
	 * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
	 * </p>
	 * <p>
	 * mobile String 否 需要查询的手机号 15205201314
	 * </p>
	 * <p>
	 * start_time String 是 短信发送开始时间 2013-08-11 00:00:00
	 * </p>
	 * <p>
	 * end_time String 是 短信发送结束时间 2013-08-12 00:00:00
	 * </p>
	 * <p>
	 * page_num Integer 否 页码，默认值为1 1
	 * </p>
	 * <p>
	 * page_size Integer 否 每页个数，最大100个 20
	 * </p>
	 * 
	 * @param param
	 * @return
	 */
	public Result<List<SmsRecord>> get_record(Map<String, String> param) {
		Result<List<SmsRecord>> r = new Result<>();
		List<NameValuePair> list = param2pair(param, r, APIKEY, START_TIME, END_TIME);
		if (r.getCode() != Code.OK)
			return r;
		String data = format2Form(list);

		SimpleListResultHandler<SmsRecord> h = new SimpleListResultHandler<SmsRecord>() {
			@Override
			public List<SmsRecord> data(List<SmsRecord> rsp) {
				switch (version()) {
				case VERSION_V1:
					if (rspMap != null) {
						String sms = rspMap.get(SMS);
						if (sms != null) {
							if (sms.startsWith("["))
								return JsonUtil.fromJson(sms, TypeListReply);
						}
					}
				case VERSION_V2:
					return rsp;
				}
				return null;
			}

			@Override
			public Integer code(List<SmsRecord> rsp) {
				if (rspMap != null) {
					return YunpianApi.code(rspMap, SmsApi.this.version());
				}
				return Code.OK;
			}

			@Override
			Type rspType() {
				return new TypeToken<List<SmsRecord>>() {
				}.getType();
			}
		};
		try {
			return path("get_record.json").post(uri(), data, h, r);
		} catch (Exception e) {
			return h.catchExceptoin(e, r);
		}
	}

	/**
	 * <h1>统计短信条数
	 * <h1>
	 * 
	 * <p>
	 * 参数名 类型 是否必须 描述 示例
	 * </p>
	 * <p>
	 * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
	 * </p>
	 * <p>
	 * start_time String 是 短信发送开始时间 2013-08-11 00:00:00
	 * </p>
	 * <p>
	 * end_time String 是 短信发送结束时间 2013-08-12 00:00:00
	 * </p>
	 * <p>
	 * mobile String 否 需要查询的手机号 15205201314
	 * </p>
	 * <p>
	 * page_num Integer 否 页码，默认值为1 1
	 * </p>
	 * <p>
	 * page_size Integer 否 每页个数，最大100个 20
	 * </p>
	 * 
	 * @param param
	 * @return
	 */
	public Result<Integer> count(Map<String, String> param) {
		Result<Integer> r = new Result<>();
		List<NameValuePair> list = param2pair(param, r, APIKEY, START_TIME, END_TIME);
		if (r.getCode() != Code.OK)
			return r;
		String data = format2Form(list);

		MapResultHandler<Integer> h = new MapResultHandler<Integer>() {
			@Override
			public Integer data(Map<String, String> rsp) {
				try {
					return Integer.parseInt(rsp.get(TOTAL));
				} catch (Exception e) {
				}
				return 0;
			}

			@Override
			public Integer code(Map<String, String> rsp) {
				return YunpianApi.code(rsp, SmsApi.this.version());
			}
		};
		try {
			return path("count.json").post(uri(), data, h, r);
		} catch (Exception e) {
			return h.catchExceptoin(e, r);
		}
	}

	/**
	 * <h1>指定模板发送 only v1</h1>
	 * 
	 * <p>
	 * 参数名 类型 是否必须 描述 示例
	 * </p>
	 * <p>
	 * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
	 * </p>
	 * <p>
	 * mobile String 是 接收的手机号 15205201314
	 * </p>
	 * <p>
	 * tpl_id Long 是 模板id 1
	 * </p>
	 * <p>
	 * tpl_value String 是 变量名和变量值对。请先对您的变量名和变量值分别进行urlencode再传递。使用参考：代码示例。
	 * 注：变量名和变量值都不能为空 模板： 【#company#】您的验证码是#code#。 最终发送结果： 【云片网】您的验证码是1234。
	 * tplvalue=urlencode("#code#") + "=" + urlencode("1234") + "&" +
	 * urlencode("#company#") + "=" + urlencode("云片网"); 若您直接发送报文请求则使用下面这种形式
	 * tplvalue=urlencode(urlencode("#code#") + "=" + urlencode("1234") + "&" +
	 * urlencode("#company#") + "=" + urlencode("云片网"));
	 * </p>
	 * <p>
	 * extend String 否 扩展号。默认不开放，如有需要请联系客服申请 001
	 * </p>
	 * <p>
	 * uid String 否 用户自定义唯一id。最大长度不超过256的字符串。 默认不开放，如有需要请联系客服申请 10001
	 * </p>
	 * 
	 * @param param
	 * @return
	 */
	@Deprecated
	public Result<SmsSingleSend> tpl_send(Map<String, String> param) {
		Result<SmsSingleSend> r = new Result<>();
		List<NameValuePair> list = param2pair(param, r, APIKEY, MOBILE, TPL_ID, TPL_VALUE);
		if (r.getCode() != Code.OK)
			return r;
		String data = format2Form(list);

		MapResultHandler<SmsSingleSend> h = new MapResultHandler<SmsSingleSend>() {
			@Override
			public SmsSingleSend data(Map<String, String> rsp) {
				switch (version()) {
				case VERSION_V1:
					String result = rsp.get(RESULT);
					if (result != null && result.startsWith("{")) {
						return JsonUtil.fromJson(result, SmsSingleSend.class);
					}
				}
				return null;
			}

			@Override
			public Integer code(Map<String, String> rsp) {
				return YunpianApi.code(rsp, SmsApi.this.version());
			}
		};
		try {
			return path("tpl_single_send.json").post(uri(), data, h, r);
		} catch (Exception e) {
			return h.catchExceptoin(e, r);
		}
	}

	/**
	 * <h1>指定模板单发 only v2</h1>
	 * 
	 * <p>
	 * 参数名 类型 是否必须 描述 示例
	 * </p>
	 * <p>
	 * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
	 * </p>
	 * <p>
	 * mobile String 是
	 * 接收的手机号（针对国际短信，mobile参数会自动格式化到E.164格式，可能会造成传入mobile参数跟后续的状态报告中的号码不一致。E.164格式说明，参见：
	 * https://en.wikipedia.org/wiki/E.164） 15205201314
	 * </p>
	 * <p>
	 * tpl_id Long 是 模板id 1
	 * </p>
	 * <p>
	 * tpl_value String 是 变量名和变量值对。请先对您的变量名和变量值分别进行urlencode再传递。使用参考：代码示例。
	 * 注：变量名和变量值都不能为空 模板： 【#company#】您的验证码是#code#。 最终发送结果： 【云片网】您的验证码是1234。
	 * tplvalue=urlencode("#code#") + "=" + urlencode("1234") + "&" +
	 * urlencode("#company#") + "=" + urlencode("云片网"); 若您直接发送报文请求则使用下面这种形式
	 * tplvalue=urlencode(urlencode("#code#") + "=" + urlencode("1234") + "&" +
	 * urlencode("#company#") + "=" + urlencode("云片网"));
	 * </p>
	 * <p>
	 * extend String 否 扩展号。默认不开放，如有需要请联系客服申请 001
	 * </p>
	 * <p>
	 * uid String 否 用户自定义唯一id。最大长度不超过256的字符串。 默认不开放，如有需要请联系客服申请 10001
	 * </p>
	 * 
	 * @param param
	 * @return
	 */
	@Deprecated
	public Result<SmsSingleSend> tpl_single_send(Map<String, String> param) {
		Result<SmsSingleSend> r = new Result<>();
		List<NameValuePair> list = param2pair(param, r, APIKEY, MOBILE, TPL_ID, TPL_VALUE);
		if (r.getCode() != Code.OK)
			return r;
		String data = format2Form(list);

		MapResultHandler<SmsSingleSend> h = new MapResultHandler<SmsSingleSend>() {
			@Override
			public SmsSingleSend data(Map<String, String> rsp) {
				switch (version()) {
				case VERSION_V2:
					return map2SendSingleSmsInfo(rsp);
				}
				return null;
			}

			@Override
			public Integer code(Map<String, String> rsp) {
				return YunpianApi.code(rsp, SmsApi.this.version());
			}
		};
		try {
			return path("tpl_single_send.json").post(uri(), data, h, r);
		} catch (Exception e) {
			return h.catchExceptoin(e, r);
		}
	}

	/**
	 * <h1>指定模板群发 only v2</h1>
	 * 
	 * <p>
	 * 参数名 类型 是否必须 描述 示例
	 * </p>
	 * <p>
	 * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
	 * </p>
	 * <p>
	 * mobile String 是
	 * 接收的手机号（针对国际短信，mobile参数会自动格式化到E.164格式，可能会造成传入mobile参数跟后续的状态报告中的号码不一致。E.164格式说明，参见：
	 * https://en.wikipedia.org/wiki/E.164） 15205201314
	 * </p>
	 * <p>
	 * tpl_id Long 是 模板id 1
	 * </p>
	 * <p>
	 * tpl_value String 是 变量名和变量值对。请先对您的变量名和变量值分别进行urlencode再传递。使用参考：代码示例。
	 * 注：变量名和变量值都不能为空 模板： 【#company#】您的验证码是#code#。 最终发送结果： 【云片网】您的验证码是1234。
	 * tplvalue=urlencode("#code#") + "=" + urlencode("1234") + "&" +
	 * urlencode("#company#") + "=" + urlencode("云片网"); 若您直接发送报文请求则使用下面这种形式
	 * tplvalue=urlencode(urlencode("#code#") + "=" + urlencode("1234") + "&" +
	 * urlencode("#company#") + "=" + urlencode("云片网"));
	 * </p>
	 * <p>
	 * extend String 否 扩展号。默认不开放，如有需要请联系客服申请 001
	 * </p>
	 * <p>
	 * uid String 否 用户自定义唯一id。最大长度不超过256的字符串。 默认不开放，如有需要请联系客服申请 10001
	 * </p>
	 * 
	 * @param param
	 * @return
	 */
	@Deprecated
	public Result<SmsBatchSend> tpl_batch_send(Map<String, String> param) {
		Result<SmsBatchSend> r = new Result<>();
		List<NameValuePair> list = param2pair(param, r, APIKEY, MOBILE, TPL_ID, TPL_VALUE);
		if (r.getCode() != Code.OK)
			return r;
		String data = format2Form(list);

		MapResultHandler<SmsBatchSend> h = new MapResultHandler<SmsBatchSend>() {
			@Override
			public SmsBatchSend data(Map<String, String> rsp) {
				switch (version()) {
				case VERSION_V2:
					return map2SendBatchSmsInfo(rsp);
				}
				return null;
			}

			@Override
			public Integer code(Map<String, String> rsp) {
				return YunpianApi.code(rsp, SmsApi.this.version());
			}
		};
		try {
			return path("tpl_batch_send.json").post(uri(), data, h, r);
		} catch (Exception e) {
			return h.catchExceptoin(e, r);
		}
	}

	private static final Type TypeListReply = new TypeToken<List<SmsReply>>() {
	}.getType();

	private static final Type TypeListStatus = new TypeToken<List<SmsStatus>>() {
	}.getType();

	private static final Type TypeListBatch = new TypeToken<List<SmsSingleSend>>() {
	}.getType();

	protected SmsBatchSend map2SendBatchSmsInfo(Map<String, String> rsp) {
		if (rsp != null) {
			try {
				SmsBatchSend info = new SmsBatchSend();
				info.setTotal_count(Integer.parseInt(rsp.get(TOTAL_COUNT)));
				info.setTotal_fee(Double.parseDouble(rsp.get(TOTAL_FEE)));
				info.setUnit(rsp.get(UNIT));

				String data = rsp.get(DATA);
				if (data != null && data.startsWith("["))
					info.setData(JsonUtil.<List<SmsSingleSend>>fromJson(data, TypeListBatch));
				return info;
			} catch (Exception e) {
				LOG.error(e.getMessage(), e.fillInStackTrace());
			}
		}
		return null;
	}

	protected SmsSingleSend map2SendSingleSmsInfo(Map<String, String> rsp) {
		if (rsp != null) {
			try {
				SmsSingleSend info = new SmsSingleSend();
				if (rsp.containsKey(CODE))
					info.setCode(Integer.parseInt(rsp.get(CODE)));
				if (rsp.containsKey(MSG))
					info.setMsg(rsp.get(MSG));
				if (rsp.containsKey(MOBILE))
					info.setMobile(rsp.get(MOBILE));
				if (rsp.containsKey(UNIT))
					info.setUnit(rsp.get(UNIT));
				info.setCount(Integer.parseInt(rsp.get(COUNT)));
				info.setFee(Double.parseDouble(rsp.get(FEE)));
				info.setSid(Long.parseLong(rsp.get(SID)));
				return info;
			} catch (Exception e) {
				LOG.error(e.getMessage(), e.fillInStackTrace());
			}
		}
		return null;
	}

}

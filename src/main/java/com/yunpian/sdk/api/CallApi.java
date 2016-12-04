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
import com.yunpian.sdk.model.CallBill;
import com.yunpian.sdk.model.CallBind;
import com.yunpian.sdk.model.Result;

/**
 * https://www.yunpian.com/api/anonymous.html
 * 
 * @author dzh
 * @date Nov 23, 2016 1:13:49 PM
 * @since 1.2.0
 */
public class CallApi extends YunpianApi {

	public static final String NAME = "call";

	@Override
	public String name() {
		return NAME;
	}

	@Override
	public void init(YunpianClient clnt) {
		super.init(clnt);
		host(clnt.getConf().getConf(YP_CALL_HOST, "https://call.yunpian.com"));
	}

	/**
	 * <h1>绑定号码</h1>
	 * 
	 * <p>
	 * 参数名 类型 是否必须 描述 示例
	 * </p>
	 * <p>
	 * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
	 * </p>
	 * <p>
	 * from String 是 需要绑定的号码 +8615012341234
	 * </p>
	 * <p>
	 * to String 是 需要绑定的号码 +8615011112222
	 * </p>
	 * <p>
	 * duration Intger 是 有效时长，单位：秒 600
	 * </p>
	 * <p>
	 * area_code String 否 区号，期望anonymous_number所属的地区 +8621（021）
	 * </p>
	 * 
	 * @param param
	 * @return
	 */
	public Result<CallBind> bind(Map<String, String> param) {
		Result<CallBind> r = new Result<>();
		List<NameValuePair> list = param2pair(param, r, APIKEY, FROM, TO, DURATION);
		if (r.getCode() != Code.OK)
			return r;
		String data = format2Form(list);

		MapResultHandler<CallBind> h = new MapResultHandler<CallBind>() {
			@Override
			public CallBind data(Map<String, String> rsp) {
				switch (version()) {
				case VERSION_V2:
					return map2CallBind(rsp);
				}
				return null;
			}

			@Override
			public Integer code(Map<String, String> rsp) {
				return YunpianApi.code(rsp, CallApi.this.version());
			}
		};
		try {
			return path("bind.json").post(uri(), data, h, r);
		} catch (Exception e) {
			return h.catchExceptoin(e, r);
		}
	}

	/**
	 * <h1>解绑号码</h1>
	 * 
	 * <p>
	 * 参数名 类型 是否必须 描述 示例
	 * </p>
	 * <p>
	 * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
	 * </p>
	 * <p>
	 * from String 是 需要绑定的号码 +8615012341234
	 * </p>
	 * <p>
	 * to String 是 需要绑定的号码 +8615011112222
	 * </p>
	 * <p>
	 * duration Intger 是 延迟解绑的时间，单位：秒，0表示立即解除绑定 0
	 * </p>
	 * 
	 * @param param
	 * @return
	 */
	public Result<Void> unbind(Map<String, String> param) {
		Result<Void> r = new Result<>();
		List<NameValuePair> list = param2pair(param, r, APIKEY, FROM, TO);
		if (r.getCode() != Code.OK)
			return r;
		String data = format2Form(list);

		MapResultHandler<Void> h = new MapResultHandler<Void>() {
			@Override
			public Void data(Map<String, String> rsp) {
				return null;
			}

			@Override
			public Integer code(Map<String, String> rsp) {
				return YunpianApi.code(rsp, CallApi.this.version());
			}
		};
		try {
			return path("unbind.json").post(uri(), data, h, r);
		} catch (Exception e) {
			return h.catchExceptoin(e, r);
		}
	}

	/**
	 * <h1>话单获取</h1>
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
	public Result<List<CallBill>> pull(Map<String, String> param) {
		Result<List<CallBill>> r = new Result<>();
		List<NameValuePair> list = param2pair(param, r, APIKEY);
		if (r.getCode() != Code.OK)
			return r;
		String data = format2Form(list);

		SimpleListResultHandler<CallBill> h = new SimpleListResultHandler<CallBill>() {
			@Override
			public List<CallBill> data(List<CallBill> rsp) {
				switch (version()) {
				case VERSION_V2:
					return rsp;
				}
				return Collections.emptyList();
			}

			@Override
			public Integer code(List<CallBill> rsp) {
				if (rspMap != null) {
					return YunpianApi.code(rspMap, CallApi.this.version());
				}
				return Code.OK;
			}

			@Override
			Type rspType() {
				return new TypeToken<List<CallBill>>() {
				}.getType();
			}
		};
		try {
			return path("pull.json").post(uri(), data, h, r);
		} catch (Exception e) {
			return h.catchExceptoin(e, r);
		}
	}

	protected CallBind map2CallBind(Map<String, String> rsp) {
		if (rsp == null)
			return null;
		CallBind bind = new CallBind();
		bind.setMessage_id(rsp.get(MESSAGE_ID));
		bind.setAnonymous_number(rsp.get(ANONYMOUS_NUMBER));
		return bind;
	}

}

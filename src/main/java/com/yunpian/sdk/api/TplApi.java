/**
 * 
 */
package com.yunpian.sdk.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.reflect.TypeToken;
import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.constants.Code;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.TemplateInfo;
import com.yunpian.sdk.util.JsonUtil;

/**
 * https://www.yunpian.com/api2.0/tpl.html
 * 
 * @author dzh
 * @date Nov 23, 2016 1:11:40 PM
 * @since 1.2.0
 */
public class TplApi extends YunpianApi {

	public static final String NAME = "tpl";

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
	 * <h1>取默认模板</h1>
	 * <p>
	 * 参数名 类型 是否必须 描述 示例
	 * </p>
	 * <p>
	 * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
	 * </p>
	 * <p>
	 * tpl_id Long 否 模板id，64位长整形。指定id时返回id对应的默认 模板。未指定时返回所有默认模板 1
	 * </p>
	 * 
	 * @param param
	 *            tpl_id
	 * @return
	 */
	public Result<List<TemplateInfo>> get_default(Map<String, String> param) {
		Result<List<TemplateInfo>> r = new Result<>();
		if (param == null || param.size() < 1) {
			return r.setCode(Code.ARGUMENT_MISSING).setMsg(Code.getErrorMsg(Code.ARGUMENT_MISSING));
		}

		List<NameValuePair> list = new LinkedList<NameValuePair>();
		if (param.containsKey(TPL_ID)) {
			list.add(new BasicNameValuePair(TPL_ID, param.get(TPL_ID)));
		}
		list.add(new BasicNameValuePair(API_KEY, apikey()));
		String data = URLEncodedUtils.format(list, charset());

		ListResultHandler<TemplateInfo, List<TemplateInfo>> h = new ListResultHandler<TemplateInfo, List<TemplateInfo>>() {
			@Override
			public List<TemplateInfo> data(List<TemplateInfo> rsp) {
				switch (version()) {
				case VERSION_V2:
					return rsp;
				}
				return Collections.emptyList();
			}

			@Override
			public Integer code(List<TemplateInfo> rsp) {
				if (rspMap != null) {
					return YunpianApi.code(rspMap, TplApi.this.version());
				}
				return Code.OK;
			}
		};
		try {
			return path("get_default.json").post(uri(), data, h, r);
		} catch (Exception e) {
			return h.catchExceptoin(e, r);
		}
	}

	/**
	 * <h1>取模板</h1>
	 * 
	 * <p>
	 * 参数名 类型 是否必须 描述 示例
	 * </p>
	 * <p>
	 * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
	 * </p>
	 * <p>
	 * tpl_id Long 否 模板id，64位长整形。指定id时返回id对应的 模板。未指定时返回所有模板 1
	 * </p>
	 * 
	 * @param param
	 *            tpl_id
	 * @return
	 */
	public Result<List<TemplateInfo>> get(Map<String, String> param) {
		Result<List<TemplateInfo>> r = new Result<>();
		if (param == null || param.size() < 1) {
			return r.setCode(Code.ARGUMENT_MISSING).setMsg(Code.getErrorMsg(Code.ARGUMENT_MISSING));
		}

		List<NameValuePair> list = new LinkedList<NameValuePair>();
		if (param.containsKey(TPL_ID)) {
			list.add(new BasicNameValuePair(TPL_ID, param.get(TPL_ID)));
		}
		list.add(new BasicNameValuePair(API_KEY, apikey()));
		String data = URLEncodedUtils.format(list, charset());

		ListResultHandler<TemplateInfo, List<TemplateInfo>> h = new ListResultHandler<TemplateInfo, List<TemplateInfo>>() {
			@Override
			public List<TemplateInfo> data(List<TemplateInfo> rsp) {
				switch (version()) {
				case VERSION_V1:
					if (rspMap == null)
						break;
					String t = rspMap.get(TEMPLATE);
					return t.startsWith("[")
							? JsonUtil.<ArrayList<TemplateInfo>>fromJson(t, new TypeToken<ArrayList<TemplateInfo>>() {
							}.getType()) : Arrays.asList(JsonUtil.fromJson(t, TemplateInfo.class));
				case VERSION_V2:
					if (rspMap != null)
						return Arrays.asList(map2Template(rspMap));
					return rsp;
				}
				return Collections.emptyList();
			}

			@Override
			public Integer code(List<TemplateInfo> rsp) {
				if (rspMap != null) {
					return YunpianApi.code(rspMap, TplApi.this.version());
				}
				return Code.OK;
			}
		};
		try {
			return path("get.json").post(uri(), data, h, r);
		} catch (Exception e) {
			return h.catchExceptoin(e, r);
		}
	}

	/**
	 * <h1>添加模板</h1>
	 * 
	 * <p>
	 * 参数名 类型 是否必须 描述 示例
	 * </p>
	 * <p>
	 * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
	 * </p>
	 * <p>
	 * tpl_content String 是 模板内容，必须以带符号【】的签名开头 【云片网】您的验证码是#code#
	 * </p>
	 * <p>
	 * notify_type Integer 否 审核结果短信通知的方式: 0表示需要通知,默认; 1表示仅审核不通过时通知; 2表示仅审核通过时通知;
	 * 3表示不需要通知 1
	 * </p>
	 * <p>
	 * lang String 否 国际短信模板所需参数，模板语言:简体中文zh_cn; 英文en; 繁体中文 zh_tw; 韩文ko,日文 ja
	 * zh_cn
	 * </p>
	 * 
	 * @param param
	 * @return
	 */
	public Result<TemplateInfo> add(Map<String, String> param) {
		Result<TemplateInfo> r = new Result<>();
		if (param == null || param.size() < 1) {
			return r.setCode(Code.ARGUMENT_MISSING).setMsg(Code.getErrorMsg(Code.ARGUMENT_MISSING));
		}

		List<NameValuePair> list = new LinkedList<NameValuePair>();
		if (param.containsKey(TPL_CONTENT)) {
			list.add(new BasicNameValuePair(TPL_CONTENT, param.get(TPL_CONTENT)));
		}
		if (param.containsKey(NOTIFY_TYPE)) {
			list.add(new BasicNameValuePair(NOTIFY_TYPE, param.get(NOTIFY_TYPE)));
		}
		if (param.containsKey(LANG)) {
			list.add(new BasicNameValuePair(LANG, param.get(LANG)));
		}
		if (list.size() == 0) {
			return r.setCode(Code.ARGUMENT_MISSING).setMsg(Code.getErrorMsg(Code.ARGUMENT_MISSING));
		}
		list.add(new BasicNameValuePair(API_KEY, apikey()));
		String data = URLEncodedUtils.format(list, charset());

		MapResultHandler<TemplateInfo> h = new MapResultHandler<TemplateInfo>() {
			@Override
			public TemplateInfo data(Map<String, String> rsp) {
				switch (version()) {
				case VERSION_V1:
					return JsonUtil.fromJson(rsp.get(TEMPLATE), TemplateInfo.class);
				case VERSION_V2:
					return map2Template(rsp);
				}
				return null;
			}

			@Override
			public Integer code(Map<String, String> rsp) {
				return YunpianApi.code(rsp, TplApi.this.version());
			}
		};
		try {
			return path("add.json").post(uri(), data, h, r);
		} catch (Exception e) {
			return h.catchExceptoin(e, r);
		}
	}

	/**
	 * <h1>删除模板</h1>
	 * 
	 * <p>
	 * 参数名 类型 是否必须 描述 示例
	 * </p>
	 * <p>
	 * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
	 * </p>
	 * <p>
	 * tpl_id Long 是 模板id，64位长整形 9527
	 * </p>
	 * 
	 * @param param
	 * @return
	 */
	public Result<TemplateInfo> del(Map<String, String> param) {
		Result<TemplateInfo> r = new Result<>();
		if (param == null || param.size() < 1) {
			return r.setCode(Code.ARGUMENT_MISSING).setMsg(Code.getErrorMsg(Code.ARGUMENT_MISSING));
		}

		List<NameValuePair> list = new LinkedList<NameValuePair>();
		if (param.containsKey(TPL_ID)) {
			list.add(new BasicNameValuePair(TPL_ID, param.get(TPL_ID)));
		}
		if (list.size() == 0) {
			return r.setCode(Code.ARGUMENT_MISSING).setMsg(Code.getErrorMsg(Code.ARGUMENT_MISSING));
		}
		list.add(new BasicNameValuePair(API_KEY, apikey()));
		String data = URLEncodedUtils.format(list, charset());

		MapResultHandler<TemplateInfo> h = new MapResultHandler<TemplateInfo>() {
			@Override
			public TemplateInfo data(Map<String, String> rsp) {
				switch (version()) {
				case VERSION_V2:
					return map2Template(rsp);
				}
				return null;
			}

			@Override
			public Integer code(Map<String, String> rsp) {
				return YunpianApi.code(rsp, TplApi.this.version());
			}
		};
		try {
			return path("del.json").post(uri(), data, h, r);
		} catch (Exception e) {
			return h.catchExceptoin(e, r);
		}
	}

	/**
	 * <h1>修改模板</h1>
	 * 
	 * <p>
	 * 参数名 类型 是否必须 描述 示例
	 * </p>
	 * <p>
	 * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
	 * </p>
	 * <p>
	 * tpl_id Long 是 模板id，64位长整形，指定id时返回id对应的模板。未指定时返回所有模板 9527
	 * </p>
	 * <p>
	 * tpl_content String 是
	 * 模板id，64位长整形。指定id时返回id对应的模板。未指定时返回所有模板模板内容，必须以带符号【】的签名开头 【云片网】您的验证码是#code#
	 * </p>
	 * </p>
	 * notify_type Integer 否 审核结果短信通知的方式: 0表示需要通知,默认; 1表示仅审核不通过时通知; 2表示仅审核通过时通知;
	 * 3表示不需要通知 1
	 * </p>
	 * <p>
	 * lang String 否 国际短信模板所需参数，模板语言:简体 中文zh_cn; 英文en; 繁体中文 zh_tw; 韩文ko,日文 ja
	 * zh_cn
	 * </p>
	 * 
	 * @param param
	 * @return
	 */
	public Result<TemplateInfo> update(Map<String, String> param) {
		Result<TemplateInfo> r = new Result<>();
		if (param == null || param.size() < 1) {
			return r.setCode(Code.ARGUMENT_MISSING).setMsg(Code.getErrorMsg(Code.ARGUMENT_MISSING));
		}

		List<NameValuePair> list = new LinkedList<NameValuePair>();
		if (param.containsKey(TPL_ID)) {
			list.add(new BasicNameValuePair(TPL_ID, param.get(TPL_ID)));
		}
		if (param.containsKey(TPL_CONTENT)) {
			list.add(new BasicNameValuePair(TPL_CONTENT, param.get(TPL_CONTENT)));
		}
		if (param.containsKey(NOTIFY_TYPE)) {
			list.add(new BasicNameValuePair(NOTIFY_TYPE, param.get(NOTIFY_TYPE)));
		}
		if (param.containsKey(LANG)) {
			list.add(new BasicNameValuePair(LANG, param.get(LANG)));
		}
		if (list.size() == 0) {
			return r.setCode(Code.ARGUMENT_MISSING).setMsg(Code.getErrorMsg(Code.ARGUMENT_MISSING));
		}
		list.add(new BasicNameValuePair(API_KEY, apikey()));
		String data = URLEncodedUtils.format(list, charset());

		MapResultHandler<TemplateInfo> h = new MapResultHandler<TemplateInfo>() {
			@Override
			public TemplateInfo data(Map<String, String> rsp) {
				switch (version()) {
				case VERSION_V1:
					if (rsp.containsKey(TEMPLATE))
						return JsonUtil.fromJson(rsp.get(TEMPLATE), TemplateInfo.class);
				case VERSION_V2:
					if (rsp.containsKey(TEMPLATE))
						return JsonUtil.fromJson(rsp.get(TEMPLATE), TemplateInfo.class);
					return map2Template(rsp);
				}
				return null;
			}

			@Override
			public Integer code(Map<String, String> rsp) {
				return YunpianApi.code(rsp, TplApi.this.version());
			}
		};
		try {
			return path("update.json").post(uri(), data, h, r);
		} catch (Exception e) {
			return h.catchExceptoin(e, r);
		}
	}

	private TemplateInfo map2Template(Map<String, String> map) {
		if (map == null)
			return null;

		TemplateInfo t = new TemplateInfo();
		t.setCheck_status(map.get(CHECK_STATUS));
		t.setReason(map.get(REASON));
		t.setTpl_content(map.get(TPL_CONTENT));
		t.setTpl_id(Long.parseLong(map.get(TPL_ID)));
		return t;
	}

}

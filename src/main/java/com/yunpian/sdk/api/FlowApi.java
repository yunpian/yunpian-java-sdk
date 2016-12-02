/**
 * 
 */
package com.yunpian.sdk.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.reflect.TypeToken;
import com.yunpian.sdk.constants.Code;
import com.yunpian.sdk.model.FlowPackageInfo;
import com.yunpian.sdk.model.FlowStatusInfo;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SendFlowInfo;
import com.yunpian.sdk.util.JsonUtil;

/**
 * 
 * https://www.yunpian.com/api2.0/flow.html
 * 
 * @author dzh
 * @date Nov 23, 2016 1:13:27 PM
 * @since 1.2.0
 */
public class FlowApi extends YunpianApi {

	public static final String NAME = "flow";

	@Override
	public String name() {
		return NAME;
	}

	/**
	 * <h1>查询流量包</h1>
	 * 
	 * <p>
	 * 参数名 类型 是否必须 描述 示例
	 * </p>
	 * <p>
	 * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
	 * </p>
	 * <p>
	 * carrier String 否 运营商ID 传入该参数则获取指定运营商的流量包， 否则获取所有运营商的流量包 移动：10086 联通：10010
	 * 电信：10000
	 * </p>
	 * 
	 * @param param
	 * @return
	 */
	public Result<List<FlowPackageInfo>> get_package(Map<String, String> param) {
		Result<List<FlowPackageInfo>> r = new Result<>();
		if (param == null || param.size() < 1) {
			return r.setCode(Code.ARGUMENT_MISSING).setMsg(Code.getErrorMsg(Code.ARGUMENT_MISSING));
		}

		List<NameValuePair> list = new LinkedList<NameValuePair>();
		if (param.containsKey(CARRIER)) {
			list.add(new BasicNameValuePair(CARRIER, param.get(CARRIER)));
		}
		list.add(new BasicNameValuePair(API_KEY, apikey()));
		String data = URLEncodedUtils.format(list, charset());

		ListResultHandler<FlowPackageInfo, List<FlowPackageInfo>> h = new ListResultHandler<FlowPackageInfo, List<FlowPackageInfo>>() {
			@Override
			public List<FlowPackageInfo> data(List<FlowPackageInfo> rsp) {
				switch (version()) {
				case VERSION_V1:
					if (rspMap != null) {
						String flow = rspMap.get(FLOW_PACKAGE);
						return JsonUtil.<ArrayList<FlowPackageInfo>>fromJson(flow,
								new TypeToken<ArrayList<FlowPackageInfo>>() {
								}.getType());
					}
				case VERSION_V2:
					return rsp;
				}
				return Collections.emptyList();
			}

			@Override
			public Integer code(List<FlowPackageInfo> rsp) {
				if (rspMap != null) {
					return YunpianApi.code(rspMap, FlowApi.this.version());
				}
				return Code.OK;
			}
		};
		try {
			return path("get_package.json").post(uri(), data, h, r);
		} catch (Exception e) {
			return h.catchExceptoin(e, r);
		}
	}

	/**
	 * <h1>充值流量</h1>
	 * 
	 * <p>
	 * 参数名 类型 是否必须 描述 示例
	 * </p>
	 * <p>
	 * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
	 * </p>
	 * <p>
	 * mobile String 是 接收的手机号（仅支持大陆号码） 15205201314
	 * </p>
	 * <p>
	 * sn String 是 流量包的唯一ID 点击查看 1008601
	 * </p>
	 * <p>
	 * callback_url String 否 本条流量充值的状态报告推送地址 http://your_receive_url_address
	 * </p>
	 * <p>
	 * encrypt String 否 加密方式 使用加密 tea (不再使用)
	 * </p>
	 * <p>
	 * _sign String 否 签名字段 参考使用加密 393d079e0a00912335adfe46f4a2e10f (不再使用)
	 * </p>
	 * 
	 * @param param
	 * @return
	 */
	public Result<SendFlowInfo> recharge(Map<String, String> param) {
		Result<SendFlowInfo> r = new Result<>();
		if (param == null || param.size() < 1) {
			return r.setCode(Code.ARGUMENT_MISSING).setMsg(Code.getErrorMsg(Code.ARGUMENT_MISSING));
		}

		List<NameValuePair> list = new LinkedList<NameValuePair>();
		list.add(new BasicNameValuePair(API_KEY, apikey()));
		if (param.containsKey(MOBILE)) {
			list.add(new BasicNameValuePair(MOBILE, param.get(MOBILE)));
		}
		if (param.containsKey(SN)) {
			list.add(new BasicNameValuePair(SN, param.get(SN)));
		}
		if (list.size() != 3) {
			return r.setCode(Code.ARGUMENT_MISSING).setMsg(Code.getErrorMsg(Code.ARGUMENT_MISSING));
		}
		// if (param.containsKey(ENCRYPT)) {
		// list.add(new BasicNameValuePair(ENCRYPT, param.get(ENCRYPT)));
		// }
		// if (param.containsKey(_SIGN)) {
		// list.add(new BasicNameValuePair(_SIGN, param.get(_SIGN)));
		// }
		if (param.containsKey(CALLBACK_URL)) {
			list.add(new BasicNameValuePair(CALLBACK_URL, param.get(CALLBACK_URL)));
		}
		String data = URLEncodedUtils.format(list, charset());

		MapResultHandler<SendFlowInfo> h = new MapResultHandler<SendFlowInfo>() {
			@Override
			public SendFlowInfo data(Map<String, String> rsp) {
				switch (version()) {
				case VERSION_V1:
					return JsonUtil.fromJson(rsp.get(RESULT), SendFlowInfo.class);
				case VERSION_V2:
					return map2flowResult(rsp);
				}
				return null;
			}

			@Override
			public Integer code(Map<String, String> rsp) {
				return YunpianApi.code(rsp, FlowApi.this.version());
			}
		};
		try {
			return path("recharge.json").post(uri(), data, h, r);
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
	public Result<List<FlowStatusInfo>> pull_status(Map<String, String> param) {
		Result<List<FlowStatusInfo>> r = new Result<>();
		if (param == null || param.size() < 1) {
			return r.setCode(Code.ARGUMENT_MISSING).setMsg(Code.getErrorMsg(Code.ARGUMENT_MISSING));
		}

		List<NameValuePair> list = new LinkedList<NameValuePair>();
		list.add(new BasicNameValuePair(API_KEY, apikey()));
		if (param.containsKey(PAGE_SIZE)) {
			list.add(new BasicNameValuePair(PAGE_SIZE, param.get(PAGE_SIZE)));
		}
		String data = URLEncodedUtils.format(list, charset());

		ListResultHandler<FlowStatusInfo, List<FlowStatusInfo>> h = new ListResultHandler<FlowStatusInfo, List<FlowStatusInfo>>() {
			@Override
			public List<FlowStatusInfo> data(List<FlowStatusInfo> rsp) {
				switch (version()) {
				case VERSION_V1:
					if (rspMap != null) {
						String flow = rspMap.get(FLOW_STATUS);
						return JsonUtil.<ArrayList<FlowStatusInfo>>fromJson(flow,
								new TypeToken<ArrayList<FlowStatusInfo>>() {
								}.getType());
					}
				case VERSION_V2:
					return rsp;
				}
				return Collections.emptyList();
			}

			@Override
			public Integer code(List<FlowStatusInfo> rsp) {
				if (rspMap != null) {
					return YunpianApi.code(rspMap, FlowApi.this.version());
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

	protected SendFlowInfo map2flowResult(Map<String, String> rsp) {
		if (rsp == null || rsp.isEmpty())
			return null;
		SendFlowInfo info = new SendFlowInfo();
		info.setCount(rsp.get(COUNT));
		info.setFee(Double.parseDouble(rsp.get(FEE)));
		info.setSid(rsp.get(SID));
		return info;
	}

}

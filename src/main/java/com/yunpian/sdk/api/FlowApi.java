/**
 *
 */
package com.yunpian.sdk.api;

import com.google.gson.reflect.TypeToken;
import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.constant.Code;
import com.yunpian.sdk.model.FlowPackage;
import com.yunpian.sdk.model.FlowSend;
import com.yunpian.sdk.model.FlowStatus;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.util.JsonUtil;
import org.apache.http.NameValuePair;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * https://www.yunpian.com/api2.0/api-flow.html
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

    @Override
    public void init(YunpianClient clnt) {
        super.init(clnt);
        host(clnt.getConf().getConf(YP_FLOW_HOST, "https://flow.yunpian.com"));
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
    public Result<List<FlowPackage>> get_package(Map<String, String> param) {
        Result<List<FlowPackage>> r = new Result<>();
        List<NameValuePair> list = param2pair(param, r, APIKEY);
        if (r.getCode() != Code.OK)
            return r;
        String data = urlEncode(list);

        SimpleListResultHandler<FlowPackage> h = new SimpleListResultHandler<FlowPackage>() {
            @Override
            public List<FlowPackage> data(List<FlowPackage> rsp) {
                switch (version()) {
                case VERSION_V1:
                    if (rspMap != null) {
                        String flow = rspMap.get(FLOW_PACKAGE);
                        return JsonUtil.<List<FlowPackage>> fromJson(flow, new TypeToken<ArrayList<FlowPackage>>() {
                        }.getType());
                    }
                case VERSION_V2:
                    return rsp;
                }
                return Collections.emptyList();
            }

            @Override
            public Integer code(List<FlowPackage> rsp) {
                if (rspMap != null) {
                    return YunpianApi.code(rspMap, FlowApi.this.version());
                }
                return Code.OK;
            }

            @Override
            Type rspType() {
                return new TypeToken<List<FlowPackage>>() {
                }.getType();
            }
        };
        try {
            return path("get_package.json").post(data, h, r);
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
    public Result<FlowSend> recharge(Map<String, String> param) {
        Result<FlowSend> r = new Result<>();
        List<NameValuePair> list = param2pair(param, r, APIKEY, MOBILE, SN);
        if (r.getCode() != Code.OK)
            return r;
        String data = urlEncode(list);

        MapResultHandler<FlowSend> h = new MapResultHandler<FlowSend>() {
            @Override
            public FlowSend data(Map<String, String> rsp) {
                switch (version()) {
                case VERSION_V1:
                    return JsonUtil.fromJson(rsp.get(RESULT), FlowSend.class);
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
            return path("recharge.json").post(data, h, r);
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
    public Result<List<FlowStatus>> pull_status(Map<String, String> param) {
        Result<List<FlowStatus>> r = new Result<>();
        List<NameValuePair> list = param2pair(param, r, APIKEY);
        if (r.getCode() != Code.OK)
            return r;
        String data = urlEncode(list);

        SimpleListResultHandler<FlowStatus> h = new SimpleListResultHandler<FlowStatus>() {
            @Override
            public List<FlowStatus> data(List<FlowStatus> rsp) {
                switch (version()) {
                case VERSION_V1:
                    if (rspMap != null) {
                        String flow = rspMap.get(FLOW_STATUS);
                        return JsonUtil.<ArrayList<FlowStatus>> fromJson(flow, new TypeToken<ArrayList<FlowStatus>>() {
                        }.getType());
                    }
                case VERSION_V2:
                    return rsp;
                }
                return Collections.emptyList();
            }

            @Override
            public Integer code(List<FlowStatus> rsp) {
                if (rspMap != null) {
                    return YunpianApi.code(rspMap, FlowApi.this.version());
                }
                return Code.OK;
            }

            @Override
            Type rspType() {
                return new TypeToken<List<FlowStatus>>() {
                }.getType();
            }
        };
        try {
            return path("pull_status.json").post(data, h, r);
        } catch (Exception e) {
            return h.catchExceptoin(e, r);
        }
    }

    protected FlowSend map2flowResult(Map<String, String> rsp) {
        if (rsp == null || rsp.isEmpty())
            return null;
        try {
            FlowSend info = new FlowSend();
            info.setCount(rsp.get(COUNT));
            info.setFee(Double.parseDouble(rsp.get(FEE)));
            info.setSid(rsp.get(SID));
            return info;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e.fillInStackTrace());
        }
        return null;
    }

}

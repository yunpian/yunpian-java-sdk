/**
 *
 */
package com.yunpian.sdk.api;

import com.google.gson.reflect.TypeToken;
import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.constant.Code;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.Sign;
import com.yunpian.sdk.model.SignRecord;
import com.yunpian.sdk.util.JsonUtil;
import org.apache.http.NameValuePair;

import java.util.List;
import java.util.Map;

/**
 * https://www.yunpian.com/api2.0/api-domestic/sign_add.html
 *
 * @author dzh liujie
 * @date Nov 23, 2016 1:11:12 PM
 * @since 1.2.0
 */
public class SignApi extends YunpianApi {

    public static final String NAME = "sign";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public void init(YunpianClient clnt) {
        super.init(clnt);
        host(clnt.getConf().getConf(YP_SIGN_HOST, "https://sms.yunpian.com"));
    }

    /**
     * <h1>添加签名API</h1>
     *
     * <p>
     * 参数名 类型 是否必须 描述 示例
     * </p>
     * <p>
     * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
     * </p>
     * <p>
     * sign String 是 签名内容 云片网
     * </p>
     * <p>
     * notify Boolean 否 是否短信通知结果，默认true true
     * </p>
     * <p>
     * apply_vip Boolean 否 是否申请专用通道，默认false false
     * </p>
     * <p>
     * is_only_global Boolean 否 是否仅发国际短信，默认false false
     * </p>
     * <p>
     * industry_type String 否 所属行业，默认“其它” 物联网 其他值例如:1. 游戏 2. 移动应用 3. 视频 4. 教育 5.
     * IT/通信/电子服务 6. 电子商务 7. 金融 8. 网站 9. 商业服务 10. 房地产/建筑 11. 零售/租赁/贸易 12.
     * 生产/加工/制造 13. 交通/物流 14. 文化传媒 15. 能源/电气 16. 政府企业 17. 农业 18. 物联网 19. 其它
     * </p>
     *
     * @param param
     *            sign notify apply_vip is_only_global industry_type
     * @return
     */
    public Result<Sign> add(Map<String, String> param) {
        Result<Sign> r = new Result<>();
        List<NameValuePair> list = param2pair(param, r, APIKEY, SIGN);
        if (r.getCode() != Code.OK)
            return r;
        String data = urlEncode(list);

        MapResultHandler<Sign> h = new MapResultHandler<Sign>() {
            @Override
            public Sign data(Map<String, String> rsp) {
                switch (version()) {
                case VERSION_V2:
                    return JsonUtil.fromJson(rsp.get(SIGN), Sign.class);
                }
                return null;
            }

            @Override
            public Integer code(Map<String, String> rsp) {
                return YunpianApi.code(rsp, SignApi.this.version());
            }
        };
        try {
            return path("add.json").post(data, h, r);
        } catch (Exception e) {
            return h.catchExceptoin(e, r);
        }
    }

    /**
     * <h1>修改签名API</h1>
     * <p>
     * 仅“审核中”或者“审核失败”的签名可以进行修改，修改后会重新提交给客服审核。
     * </p>
     * <p>
     * 参数notify，apply_vip，is_only_global如果没有将会修改为默认值
     * </p>
     *
     * <p>
     * <p>
     * 参数名 类型 是否必须 描述 示例
     * </p>
     * <p>
     * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
     * </p>
     * <p>
     * old_sign String 是 完整签名内容，用于指定修改哪个签名，可以加【】也可不加 云片网
     * </p>
     * <p>
     * sign String 否 修改后的签名内容（如果要改签名内容） 云片网
     * </p>
     * <p>
     * notify Boolean 否 是否短信通知结果，无此参数默认true true
     * </p>
     * <p>
     * apply_vip Boolean 否 是否申请专用通道，无此参数默认false false
     * </p>
     * <p>
     * is_only_global Boolean 否 是否仅发国际短信，无此参数默认false false
     * </p>
     * <p>
     * industry_type String 否 所属行业，默认“其它” 物联网 其他值例如:1. 游戏 2. 移动应用 3. 视频 4. 教育 5.
     * IT/通信/电子服务 6. 电子商务 7. 金融 8. 网站 9. 商业服务 10. 房地产/建筑 11. 零售/租赁/贸易 12.
     * 生产/加工/制造 13. 交通/物流 14. 文化传媒 15. 能源/电气 16. 政府企业 17. 农业 18. 物联网 19. 其它
     * </p>
     *
     * @param param
     *            old_sign sign notify apply_vip is_only_global industry_type
     * @return
     */
    public Result<Sign> update(Map<String, String> param) {
        Result<Sign> r = new Result<>();
        List<NameValuePair> list = param2pair(param, r, APIKEY, OLD_SIGN);
        if (r.getCode() != Code.OK)
            return r;
        String data = urlEncode(list);

        MapResultHandler<Sign> h = new MapResultHandler<Sign>() {
            @Override
            public Sign data(Map<String, String> rsp) {
                switch (version()) {
                case VERSION_V2:
                    return JsonUtil.fromJson(rsp.get(SIGN), Sign.class);
                }
                return null;
            }

            @Override
            public Integer code(Map<String, String> rsp) {
                return YunpianApi.code(rsp, SignApi.this.version());
            }
        };
        try {
            return path("update.json").post(data, h, r);
        } catch (Exception e) {
            return h.catchExceptoin(e, r);
        }
    }

    /**
     * <h1>获取签名API</h1>
     * 
     * <p>
     * 参数名 类型 是否必须 描述 示例
     * </p>
     * <p>
     * apikey String 是 用户唯一标识 9b11127a9701975c734b8aee81ee3526
     * </p>
     * <p>
     * id Long 否 签名id，暂未开放，如果传入此参数将会指定获取某个签名 9527
     * </p>
     * <p>
     * sign String 否 签名内容 云片网
     * </p>
     * <p>
     * page_num Integer 否 页码，1开始，不带或者格式错误返回全部 1
     * </p>
     * <p>
     * page_size Integer 否 返回条数，必须大于0，不带或者格式错误返回全部 20
     * </p>
     *
     * @param param
     *            sign notify page_num page_size
     * @return
     */
    public Result<SignRecord> get(Map<String, String> param) {
        Result<SignRecord> r = new Result<>();
        List<NameValuePair> list = param2pair(param, r, APIKEY);
        if (r.getCode() != Code.OK)
            return r;
        String data = urlEncode(list);

        MapResultHandler<SignRecord> h = new MapResultHandler<SignRecord>() {
            @Override
            public SignRecord data(Map<String, String> rsp) {
                switch (version()) {
                case VERSION_V2:
                    TypeToken<List<Sign>> token = new TypeToken<List<Sign>>() {
                    };
                    List<Sign> signs = JsonUtil.fromJson(rsp.get(SIGN), token.getType());
                    int total = Integer.valueOf(rsp.get(TOTAL));
                    SignRecord signRecord = new SignRecord();
                    signRecord.setSign(signs);
                    signRecord.setTotal(total);
                    return signRecord;
                }
                return null;
            }

            @Override
            public Integer code(Map<String, String> rsp) {
                return YunpianApi.code(rsp, SignApi.this.version());
            }
        };
        try {
            return path("get.json").post(data, h, r);
        } catch (Exception e) {
            return h.catchExceptoin(e, r);
        }
    }

}

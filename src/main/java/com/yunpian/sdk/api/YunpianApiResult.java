/**
 * 
 */
package com.yunpian.sdk.api;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.yunpian.sdk.constant.Code;
import com.yunpian.sdk.constant.YunpianConstant;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.util.JsonUtil;

/**
 * YunpianApi 结果处理
 * 
 * @author dzh
 * @date Nov 25, 2016 2:48:27 PM
 * @since 1.2.0
 */
public interface YunpianApiResult {

    /**
     * 获取最终的结果
     * 
     * @param rsp
     *            响应结果
     * @param h
     *            结果处理器
     * @param r
     *            返回结果，若r=null则自动创建
     * @return
     */
    <R, T> Result<T> result(String rsp, ResultHandler<R, T> h, Result<T> r);

    /**
     * 
     * @author dzh
     * @date Nov 25, 2016 5:36:59 PM
     * @since 1.2.0
     * @param <R>
     *            HttpResponse的转换类型
     * @param <T>
     *            {@code Result}的data类型
     */
    public static interface ResultHandler<R, T> {

        /**
         * 将响应输入流转换成R类型
         * 
         * @param rsp
         *            响应输入流
         * @return
         */
        R response(String rsp) throws Exception;

        /**
         * 根据rsp获取响应码
         * 
         * @param rsp
         * @return
         */
        Integer code(R rsp);

        /**
         * 处理成功响应
         * 
         * @param code
         *            0
         * @param rsp
         * @param r
         * @return
         */
        Result<T> succ(Integer code, R rsp, Result<T> r);

        /**
         * 处理失败响应
         * 
         * @param code
         * @param rsp
         * @param r
         * @return r
         */
        Result<T> fail(Integer code, R rsp, Result<T> r);

        /**
         * 处理异常
         * 
         * @param e
         * @param r
         * @return
         */
        Result<T> catchExceptoin(Throwable e, Result<T> r);
    }

    public static abstract class AbstractResultHandler<R, T> implements ResultHandler<R, T> {
        @Override
        public Result<T> catchExceptoin(Throwable e, Result<T> r) {
            if (r == null) {
                r = new Result<T>();
            }
            return r.setCode(Code.UNKNOWN_EXCEPTION).setMsg(Code.getErrorMsg(Code.UNKNOWN_EXCEPTION)).setThrowable(e);
        }
    }

    /**
     * 处理返回json对象(非数组)
     * 
     * @author dzh
     * @date Nov 25, 2016 8:31:07 PM
     * @since 1.2.0
     */
    public static abstract class MapResultHandler<T> extends AbstractResultHandler<Map<String, String>, T> implements YunpianConstant {

        @Override
        public Map<String, String> response(String rsp) throws Exception {
            if (rsp == null)
                return Collections.emptyMap();
            return JsonUtil.fromJsonToMap(rsp);
        }

        @Override
        public Result<T> succ(Integer code, Map<String, String> rsp, Result<T> r) {
            if (r == null) {
                r = new Result<T>();
            }
            r.setCode(code).setMsg(rsp.containsKey(MSG) ? rsp.get(MSG) : Code.getErrorMsg(code));
            T data = data(rsp);
            return r.setData(data);
        }

        /**
         * 成功时返回对象
         * 
         * @param rsp
         * @return 对象数据
         */
        public abstract T data(Map<String, String> rsp);

        /**
         * 错误流程 v1和v2返回格式一致
         */
        @Override
        public Result<T> fail(Integer code, Map<String, String> rsp, Result<T> r) {
            if (r == null) {
                r = new Result<T>();
            }
            return r.setCode(code).setMsg(rsp.containsKey(MSG) ? rsp.get(MSG) : Code.getErrorMsg(code)).setDetail(rsp.get(DETAIL));
        }

    }

    /**
     * 处理返回json数组的情况,在传回非json时，用map方式解析到rspMap。设计不好，最好统一用map格式
     * 
     * @author dzh
     * @date Nov 25, 2016 6:05:18 PM
     * @since 1.2.0
     */
    public static abstract class ListResultHandler<R, T> extends AbstractResultHandler<List<R>, T> implements YunpianConstant {

        /**
         * list解析错误时，按map方式解析
         */
        protected Map<String, String> rspMap;

        @Override
        public List<R> response(String rsp) throws Exception {
            if (rsp == null)
                return Collections.emptyList();
            if (rsp.startsWith("["))
                return JsonUtil.fromJson(rsp, rspType());
            else {
                rspMap = JsonUtil.fromJsonToMap(rsp);
                return Collections.emptyList();
            }
        }

        abstract Type rspType();

        @Override
        public Result<T> succ(Integer code, List<R> rsp, Result<T> r) {
            if (r == null) {
                r = new Result<T>();
            }
            r.setCode(code).setMsg(Code.getErrorMsg(code));
            T data = data(rsp);
            return r.setData(data);
        }

        /**
         * 成功时返回对象
         * 
         * @param rsp
         * @return 对象数据
         */
        public abstract T data(List<R> rsp);

        /**
         * 错误流程 v1和v2返回格式一致
         */
        @Override
        public Result<T> fail(Integer code, List<R> rsp, Result<T> r) {
            if (r == null) {
                r = new Result<T>();
            }
            Map<String, String> map = rspMap == null ? Collections.<String, String> emptyMap() : rspMap;
            return r.setCode(code).setMsg(map.containsKey(MSG) ? map.get(MSG) : Code.getErrorMsg(code)).setDetail(map.get(DETAIL));
        }

        @Override
        public Result<T> catchExceptoin(Throwable e, Result<T> r) {
            if (r == null) {
                r = new Result<T>();
            }
            return r.setCode(Code.UNKNOWN_EXCEPTION).setMsg(Code.getErrorMsg(Code.UNKNOWN_EXCEPTION)).setThrowable(e);
        }

    }

    public static abstract class SimpleListResultHandler<T> extends ListResultHandler<T, List<T>> implements YunpianConstant {
    }
}

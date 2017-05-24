/**
 * 
 */
package com.yunpian.sdk.model;

import com.yunpian.sdk.constant.Code;
import com.yunpian.sdk.util.JsonUtil;

/**
 * Api返回格式
 * 
 * @author dzh
 * @date Nov 24, 2016 7:29:08 PM
 * @since 1.2.0
 */
public class Result<T> {

    private Integer code = Code.OK;
    private String msg;
    private String detail;

    private Throwable e;

    private T data;

    public T getData() {
        return data;
    }

    public String getDetail() {
        return detail;
    }

    public Result<T> setDetail(String detail) {
        this.detail = detail;
        return this;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public Result<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Throwable getThrowable() {
        return e;
    }

    public Result<T> setThrowable(Throwable e) {
        this.e = e;
        return this;
    }

    public boolean isSucc() {
        return code == Code.OK;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }

}

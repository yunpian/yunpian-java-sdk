package com.yunpian.sdk.model;

/**
 * Created by bingone on 16/1/18.
 */
@Deprecated
public class ErrorInfo {
    private String http_status_code;
    private String code;
    private String msg;
    private String detail;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getHttp_status_code() {
        return http_status_code;
    }

    public void setHttp_status_code(String http_status_code) {
        this.http_status_code = http_status_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ErrorInfo{" + "code='" + code + '\'' + ", http_status_code='" + http_status_code + '\'' + ", msg='"
                + msg + '\'' + ", detail='" + detail + '\'' + '}';
    }
}

package com.yunpian.sdk.model;

import com.yunpian.sdk.util.JsonUtil;

/**
 * Created by bingone on 16/1/12.
 */
public class BaseStatus {
    protected String sid;
    protected String mobile;
    protected String report_status;
    protected String error_msg;
    protected String user_receive_time;

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getReport_status() {
        return report_status;
    }

    public void setReport_status(String report_status) {
        this.report_status = report_status;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getUser_receive_time() {
        return user_receive_time;
    }

    public void setUser_receive_time(String user_receive_time) {
        this.user_receive_time = user_receive_time;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}

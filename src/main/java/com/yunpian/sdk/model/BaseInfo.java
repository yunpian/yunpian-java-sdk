package com.yunpian.sdk.model;

import com.yunpian.sdk.util.JsonUtil;

/**
 * Created by bingone on 16/1/12.
 */
class BaseInfo {
    protected String sid;
    protected String count;
    protected Double fee;

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}

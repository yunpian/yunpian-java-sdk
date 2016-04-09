package com.yunpian.sdk.model;

/**
 * Created by bingone on 16/1/12.
 */
public class BaseInfo {
    protected String sid;
    protected String count;
    protected Double fee;

    @Override public String toString() {
        return "BaseInfo{" +
            "count='" + count + '\'' +
            ", sid='" + sid + '\'' +
            ", fee=" + fee +
            '}';
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

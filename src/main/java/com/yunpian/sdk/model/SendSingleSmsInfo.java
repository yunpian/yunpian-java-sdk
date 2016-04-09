package com.yunpian.sdk.model;

/**
 * Created by bingone on 15/11/20.
 */
public class SendSingleSmsInfo {
    private int code;
    private String msg;
    private int count;
    private double fee;
    private String unit;
    private String mobile;
    private String sid;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "SendSingleSmsInfo{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", fee=" + fee +
                ", unit='" + unit + '\'' +
                ", mobile='" + mobile + '\'' +
                ", sid='" + sid + '\'' +
                '}';
    }
}

package com.yunpian.sdk.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by bingone on 15/11/6.
 */
public class SmsReplyInfo implements Serializable {

    //用户自定义id
    private String base_extend;
    private String extend;
    private String reply_time;
    private String mobile;
    private String text;

    @Override public String toString() {
        return "SmsReplyInfo{" +
            "base_extend='" + base_extend + '\'' +
            ", extend='" + extend + '\'' +
            ", reply_time='" + reply_time + '\'' +
            ", mobile='" + mobile + '\'' +
            ", text='" + text + '\'' +
            '}';
    }

    public String getBase_extend() {
        return base_extend;
    }

    public void setBase_extend(String base_extend) {
        this.base_extend = base_extend;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getReply_time() {
        return reply_time;
    }

    public void setReply_time(String reply_time) {
        this.reply_time = reply_time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

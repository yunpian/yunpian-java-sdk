package com.yunpian.sdk.model;

/**
 * Created by liujie on 2017/9/29.
 */
public class ShortUrl {

    private String sid;
    private String long_url;
    private String short_url;
    private String enter_url;
    private String name;
    private String stat_expire;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getLong_url() {
        return long_url;
    }

    public void setLong_url(String long_url) {
        this.long_url = long_url;
    }

    public String getShort_url() {
        return short_url;
    }

    public void setShort_url(String short_url) {
        this.short_url = short_url;
    }

    public String getEnter_url() {
        return enter_url;
    }

    public void setEnter_url(String enter_url) {
        this.enter_url = enter_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStat_expire() {
        return stat_expire;
    }

    public void setStat_expire(String stat_expire) {
        this.stat_expire = stat_expire;
    }
}

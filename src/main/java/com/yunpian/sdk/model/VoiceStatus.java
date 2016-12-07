package com.yunpian.sdk.model;

import com.yunpian.sdk.util.JsonUtil;

/**
 * Created by bingone on 16/1/12.
 */
public class VoiceStatus extends BaseStatus {
    private String uid;
    private String duration;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}

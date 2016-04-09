package com.yunpian.sdk.model;

/**
 * Created by bingone on 16/1/12.
 */
public class VoiceStatusInfo extends BaseStatusInfo {
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

    @Override public String toString() {
        return "VoiceStatusInfo{" +
            "duration='" + duration + '\'' +
            ", uid='" + uid + '\'' +
            '}';
    }
}

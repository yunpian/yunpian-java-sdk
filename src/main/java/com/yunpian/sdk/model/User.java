package com.yunpian.sdk.model;

import java.util.Date;

import com.yunpian.sdk.util.JsonUtil;

/**
 * Created by jiacheo on 15/5/23.
 */
public class User {

    private String nick;

    private Date gmt_created;

    private String mobile;

    private String email;

    private String ip_whitelist;

    private String api_version;

    private Long alarm_balance;

    private String emergency_contact;

    private String emergency_mobile;

    private Double balance;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Date getGmt_created() {
        return gmt_created;
    }

    public void setGmt_created(Date gmt_created) {
        this.gmt_created = gmt_created;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIp_whitelist() {
        return ip_whitelist;
    }

    public void setIp_whitelist(String ip_whitelist) {
        this.ip_whitelist = ip_whitelist;
    }

    public String getApi_version() {
        return api_version;
    }

    public void setApi_version(String api_version) {
        this.api_version = api_version;
    }

    public Long getAlarm_balance() {
        return alarm_balance;
    }

    public void setAlarm_balance(Long alarm_balance) {
        this.alarm_balance = alarm_balance;
    }

    public String getEmergency_contact() {
        return emergency_contact;
    }

    public void setEmergency_contact(String emergency_contact) {
        this.emergency_contact = emergency_contact;
    }

    public String getEmergency_mobile() {
        return emergency_mobile;
    }

    public void setEmergency_mobile(String emergency_mobile) {
        this.emergency_mobile = emergency_mobile;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}

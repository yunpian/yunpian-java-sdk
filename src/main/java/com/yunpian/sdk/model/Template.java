package com.yunpian.sdk.model;

/**
 * Created by bingone on 15/11/19.
 */
public class Template {
    private Long tpl_id;
    private String tpl_content;
    private String check_status;
    private String reason;
    private String lang;
    private String country_code;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getCheck_status() {
        return check_status;
    }

    public void setCheck_status(String check_status) {
        this.check_status = check_status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTpl_content() {
        return tpl_content;
    }

    public void setTpl_content(String tpl_content) {
        this.tpl_content = tpl_content;
    }

    public Long getTpl_id() {
        return tpl_id;
    }

    public void setTpl_id(Long tpl_id) {
        this.tpl_id = tpl_id;
    }

    @Override
    public String toString() {
        return "TemplateInfo{" + "check_status='" + check_status + '\'' + ", tpl_id=" + tpl_id + ", tpl_content='"
                + tpl_content + '\'' + ", reason='" + reason + '\'' + '}';
    }
}

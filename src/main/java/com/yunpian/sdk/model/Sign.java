/**
 * 
 */
package com.yunpian.sdk.model;

/**
 * 签名
 * 
 * @author dzh
 * @date Nov 25, 2016 4:11:50 PM
 * @since 1.2.0
 */
public class Sign {

    private String apply_state;
    private String sign;
    private String is_apply_vip;
    private String is_only_global;
    private String industry_type;

    public String getApply_state() {
        return apply_state;
    }

    public void setApply_state(String apply_state) {
        this.apply_state = apply_state;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getIs_apply_vip() {
        return is_apply_vip;
    }

    public void setIs_apply_vip(String is_apply_vip) {
        this.is_apply_vip = is_apply_vip;
    }

    public String getIs_only_global() {
        return is_only_global;
    }

    public void setIs_only_global(String is_only_global) {
        this.is_only_global = is_only_global;
    }

    public String getIndustry_type() {
        return industry_type;
    }

    public void setIndustry_type(String industry_type) {
        this.industry_type = industry_type;
    }

}

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

    //申请签名时的申请信息
    private String apply_state;
    private String sign;
    private boolean is_apply_vip;
    private boolean is_only_global;
    private String industry_type;

    //获取签名时的返回参数（以及sign、industry_type），当前签名的信息
    private String chan;
    private String check_status;
    private boolean enabled;
    private String extend;
    private boolean only_global;
    private String remark;
    private boolean vip;


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

    public boolean is_apply_vip() {
        return is_apply_vip;
    }

    public void setIs_apply_vip(boolean is_apply_vip) {
        this.is_apply_vip = is_apply_vip;
    }

    public boolean is_only_global() {
        return is_only_global;
    }

    public void setIs_only_global(boolean is_only_global) {
        this.is_only_global = is_only_global;
    }

    public String getIndustry_type() {
        return industry_type;
    }

    public void setIndustry_type(String industry_type) {
        this.industry_type = industry_type;
    }

    public String getChan() {
        return chan;
    }

    public void setChan(String chan) {
        this.chan = chan;
    }

    public String getCheck_status() {
        return check_status;
    }

    public void setCheck_status(String check_status) {
        this.check_status = check_status;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public boolean isOnly_global() {
        return only_global;
    }

    public void setOnly_global(boolean only_global) {
        this.only_global = only_global;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }
}

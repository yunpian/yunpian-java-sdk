package com.yunpian.sdk.model;

import java.util.List;

/**
 * 获取签名结果
 * 
 * https://www.yunpian.com/api2.0/api-domestic/sign_get.html
 *
 * @author liujie
 * @date Dec 26, 2016 10:53:25 AM
 * @since 1.2.2
 */
public class SignRecord {

    private int total;
    private List<Sign> sign;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Sign> getSign() {
        return sign;
    }

    public void setSign(List<Sign> sign) {
        this.sign = sign;
    }
}

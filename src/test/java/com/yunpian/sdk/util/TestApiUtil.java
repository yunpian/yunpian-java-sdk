/**
 * 
 */
package com.yunpian.sdk.util;

import org.junit.Assert;
import org.junit.Test;

import com.yunpian.sdk.util.ApiUtil;

/**
 * @author dzh
 * @date Dec 21, 2016 3:37:53 PM
 * @since 1.2.0
 */
public class TestApiUtil {

    @Test
    public void urlEncodeAndLinkTest() {
        String text = ApiUtil.urlEncodeAndLink(null, ",", "【哈哈哈】您的验证码是1", "【哈哈哈】您的验证码是2", "【哈哈哈】您的验证码,是3");
        System.out.println(text);
        String[] split = text.split(",");
        Assert.assertEquals(3, split.length);

        text = ApiUtil.urlEncodeAndLink(null, ",");
        Assert.assertEquals("", text);
        split = text.split(",");
        Assert.assertEquals(0, 0);

        text = ApiUtil.urlEncodeAndLink(null, ",", "【哈哈哈】您的验证码是1");
        System.out.println(text);
        split = text.split(",");
        Assert.assertEquals(1, 1);
    }

}

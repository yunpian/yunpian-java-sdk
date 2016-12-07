/**
 * 
 */
package com.yunpian.sdk.model;

/**
 * 通话绑定
 * 
 * @author dzh
 * @date Nov 29, 2016 11:24:05 PM
 * @since 1.2.0
 */
public class CallBind {

    private String message_id;

    private String anonymous_number;

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getAnonymous_number() {
        return anonymous_number;
    }

    public void setAnonymous_number(String anonymous_number) {
        this.anonymous_number = anonymous_number;
    }

}

/**
 * 
 */
package com.yunpian.sdk.model;

/**
 * 通话账单
 * 
 * @author dzh
 * @date Nov 29, 2016 11:24:20 PM
 * @since 1.2.0
 */
public class CallBill {

    private String id;
    private String message_id;
    private String from;
    private String to;
    private String user_start_time;
    private String user_receive_time;
    private String call_end_time;
    private Integer duration;
    private Float fee;
    private String status;
    private String error_msg;
    private String anonymous_number;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getUser_start_time() {
        return user_start_time;
    }

    public void setUser_start_time(String user_start_time) {
        this.user_start_time = user_start_time;
    }

    public String getUser_receive_time() {
        return user_receive_time;
    }

    public void setUser_receive_time(String user_receive_time) {
        this.user_receive_time = user_receive_time;
    }

    public String getCall_end_time() {
        return call_end_time;
    }

    public void setCall_end_time(String call_end_time) {
        this.call_end_time = call_end_time;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Float getFee() {
        return fee;
    }

    public void setFee(Float fee) {
        this.fee = fee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public String getAnonymous_number() {
        return anonymous_number;
    }

    public void setAnonymous_number(String anonymous_number) {
        this.anonymous_number = anonymous_number;
    }

}

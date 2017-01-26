/**
 * 
 */
package com.yunpian.sdk.model;

/**
 * 通话记录
 * 
 * @author dzh
 * @date Jan 25, 2017 5:00:43 PM
 * @since 1.2.0
 */
public class CallRecord {

    // record id
    private String id;

    // call duration, unit is second
    private Integer duration;

    // call create time
    private Long createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

}

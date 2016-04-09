package com.yunpian.sdk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by bingone on 15/11/20.
 */
public class SendBatchSmsInfo {

    @SerializedName("total_count")
    private int totalCount;
    @SerializedName("total_fee")
    private double totalFee;

    private String unit;
    private List<SendSingleSmsInfo> data;

    public List<SendSingleSmsInfo> getData() {
        return data;
    }

    public void setData(List<SendSingleSmsInfo> data) {
        this.data = data;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "SendBatchSmsInfo{" +
                "data=" + data +
                ", totalCount=" + totalCount +
                ", totalFee=" + totalFee +
                ", unit='" + unit + '\'' +
                '}';
    }
}

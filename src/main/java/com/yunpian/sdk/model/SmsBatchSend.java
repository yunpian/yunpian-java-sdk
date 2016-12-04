package com.yunpian.sdk.model;

import java.util.List;

import com.yunpian.sdk.util.JsonUtil;

/**
 * Created by bingone on 15/11/20.
 */
public class SmsBatchSend {

	private Integer total_count;

	private Double total_fee;

	private String unit;

	private List<SmsSingleSend> data;

	public List<SmsSingleSend> getData() {
		return data;
	}

	public void setData(List<SmsSingleSend> data) {
		this.data = data;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getTotal_count() {
		return total_count;
	}

	public void setTotal_count(Integer total_count) {
		this.total_count = total_count;
	}

	public Double getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(Double total_fee) {
		this.total_fee = total_fee;
	}

	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}

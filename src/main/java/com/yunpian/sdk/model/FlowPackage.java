package com.yunpian.sdk.model;

/**
 * Created by bingone on 16/1/12.
 */
public class FlowPackage {

    private Long sn;
    private double carrier_price;
    private double discount;
    private Integer capacity;
    private Long carrier;
    private String name;

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Long getCarrier() {
        return carrier;
    }

    public void setCarrier(Long carrier) {
        this.carrier = carrier;
    }

    public double getCarrier_price() {
        return carrier_price;
    }

    public void setCarrier_price(double carrier_price) {
        this.carrier_price = carrier_price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSn() {
        return sn;
    }

    public void setSn(Long sn) {
        this.sn = sn;
    }

    @Override
    public String toString() {
        return "FlowPackageInfo{" + "capacity=" + capacity + ", sn=" + sn + ", carrier_price=" + carrier_price
                + ", discount=" + discount + ", carrier=" + carrier + ", name='" + name + '\'' + '}';
    }
}

package com.yunpian.sdk.model;

import java.io.Serializable;

/**
 * Created by bingone on 16/1/18.
 */
@Deprecated
public class ResultDO<T> implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private boolean success;
    private T data;
    private Throwable e;

    public ResultDO() {
        this.success = false;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Throwable getE() {
        return e;
    }

    public void setE(Throwable e) {
        this.e = e;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ResultDO{" + "data=" + data + ", success=" + success + ", e=" + e + '}';
    }
}

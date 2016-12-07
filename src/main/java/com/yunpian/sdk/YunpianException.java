package com.yunpian.sdk;

/**
 * Created by bingone on 15/11/6.
 */
public class YunpianException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public YunpianException(Throwable e) {
        super(e);
    }

    public YunpianException(String message, Throwable e) {
        super(message, e);
    }

    public YunpianException(String msg) {
        super(msg);
    }

}

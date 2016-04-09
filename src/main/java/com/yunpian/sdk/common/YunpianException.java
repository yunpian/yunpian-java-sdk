package com.yunpian.sdk.common;


/**
 * Created by bingone on 15/11/6.
 */
public class YunpianException extends Exception {


    public YunpianException(Throwable e){super(e);}

    public YunpianException(String message,Throwable e){super(message,e);}

    public YunpianException(String msg) {super(msg);
    }




}

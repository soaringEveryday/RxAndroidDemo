package com.jason.rxjavademo.network.exception;

/**
 * Created by Chen Haitao on 2016/5/27.
 */
public class ApiException extends Exception{
    private int errorCode;
    private String message;

    public ApiException(Throwable throwable, int errorCode, String message) {
        super(throwable);
        this.errorCode = errorCode;
        this.message = message;
    }

    public ApiException(String detailMessage, Throwable throwable, int errorCode, String message) {
        super(detailMessage, throwable);
        this.errorCode = errorCode;
        this.message = message;
    }

    public ApiException(String detailMessage, int errorCode, String message) {
        super(detailMessage);
        this.errorCode = errorCode;
        this.message = message;
    }

    public ApiException(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}

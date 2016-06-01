package com.fpd.core.response;

/**
 * Created by t450s on 2016/6/1.
 */
public class CoreResponse<T> {
    private String errorCode;
    private String errorMessage;
    private T result;

    public CoreResponse(){}

    public boolean isSuccess() {
        return "0".equals(errorCode);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}

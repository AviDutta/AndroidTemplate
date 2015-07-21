package com.claricetechnologies.exception;

import android.util.Log;

import com.claricetechnologies.data.ClConstants;

import org.json.JSONObject;

public class ClException extends Exception implements ClErrorCallback {
    private static final long serialVersionUID = -8885164800008713815L;

    private final String MESSAGE = "message";
    private final String CODE = "code";
    private final String ERROR_MESSAGE = "Method argument not valid.";
    private final String FIELD_ERRORS = "fieldErrors";
    private int statusCode;
    private int errorCode;
    private String errorMsg;
    private Throwable cause;

    public ClException(int errorCode, String errorMsg, Throwable cause) {
        super(errorMsg, cause);
        this.statusCode = errorCode;
        this.errorMsg = parseJSONString(errorMsg);
        this.cause = cause;
    }

    public ClException(int errorCode, String errorMsg) {
        super(errorMsg);
        this.statusCode = errorCode;
        this.errorMsg = parseJSONString(errorMsg);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        Log.e("Error Response", errorMsg);
        this.errorMsg = errorMsg;
    }

    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "EnException [errorCode=" + statusCode + ", errorMsg="
                + errorMsg + "]";
    }

    public String parseJSONString(String errorMsg) {
        Log.e(ClConstants.TAG, errorMsg);
        JSONObject msgJson;
        String message = null;
        return "abs";
    }

    @Override
    public void handleError(String tag) {

    }
}

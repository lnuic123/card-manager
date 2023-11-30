package com.exercise.card.manager.exception;

import org.springframework.http.HttpStatus;

public class CardManagerException extends RuntimeException {
    private String[] errorMessages;
    private Integer errorCode;
    private Object[] requestParameters;

    private HttpStatus httpStatus;

    public CardManagerException(String[] errorMessages, Integer errorCode, Object[] requestParameters, HttpStatus httpStatus) {
        this.errorMessages = errorMessages;
        this.errorCode = errorCode;
        this.requestParameters = requestParameters;
        this.httpStatus = httpStatus;
    }

    public CardManagerException(String[] errorMessages, Integer errorCode, Object[] requestParameters) {
        this.errorMessages = errorMessages;
        this.errorCode = errorCode;
        this.requestParameters = requestParameters;
    }

    public CardManagerException(String message, Integer errorCode, Object[] requestParameters, HttpStatus httpStatus) {
        super(message);
        this.errorMessages = new String[]{message};
        this.errorCode = errorCode;
        this.requestParameters = requestParameters;
        this.httpStatus = httpStatus;
    }

    public CardManagerException(String message, Integer errorCode, Object[] requestParameters) {
        super(message);
        this.errorMessages = new String[]{message};
        this.errorCode = errorCode;
        this.requestParameters = requestParameters;
    }

    public CardManagerException(String message, Integer errorCode) {
        super(message);
        this.errorMessages = new String[]{message};
        this.errorCode = errorCode;
    }

    public String[] getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(String[] errorMessages) {
        this.errorMessages = errorMessages;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public Object[] getRequestParameters() {
        return requestParameters;
    }

    public void setRequestParameters(Object[] requestParameters) {
        this.requestParameters = requestParameters;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}

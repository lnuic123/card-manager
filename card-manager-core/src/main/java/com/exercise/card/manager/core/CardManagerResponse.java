package com.exercise.card.manager.core;

import com.exercise.card.manager.core.enums.ResponseStatus;

public class CardManagerResponse<T> {
    private ResponseStatus responseStatus;
    private String[] responseMessage;
    private T response;

    public CardManagerResponse(String[] message) {
        this.responseStatus = ResponseStatus.OK;
        this.responseMessage = message;
        this.response = null;
    }

    public CardManagerResponse(T response) {
        this.responseStatus = ResponseStatus.OK;
        this.responseMessage = new String[]{"Success"};
        this.response = response;
    }

    public CardManagerResponse(String message, T response) {
        this.responseStatus = ResponseStatus.OK;
        this.responseMessage = new String[]{message};
        this.response = response;
    }

    public CardManagerResponse(ResponseStatus responseStatus, String message, T response) {
        this.responseStatus = responseStatus;
        this.responseMessage = new String[]{message};
        this.response = response;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String[] getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String[] responseMessage) {
        this.responseMessage = responseMessage;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}

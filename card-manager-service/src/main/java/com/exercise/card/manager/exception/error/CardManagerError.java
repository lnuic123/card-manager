package com.exercise.card.manager.exception.error;

import java.time.LocalDateTime;

public class CardManagerError {
    private Integer code;
    private String[] messages;
    private LocalDateTime date;

    public CardManagerError() {
    }

    public CardManagerError(Integer code, String[] messages, LocalDateTime date) {
        this.code = code;
        this.messages = messages;
        this.date = date;
    }

    public CardManagerError(Integer code, String message, LocalDateTime date) {
        this.code = code;
        this.messages = new String[]{message};
        this.date = date;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String[] getMessages() {
        return messages;
    }

    public void setMessages(String[] messages) {
        this.messages = messages;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

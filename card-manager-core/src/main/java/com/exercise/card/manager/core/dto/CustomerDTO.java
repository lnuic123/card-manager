package com.exercise.card.manager.core.dto;

import com.exercise.card.manager.core.enums.CustomerStatus;

public class CustomerDTO {
    private String name;
    private String surname;
    private String oib;
    private CustomerStatus status;

    public CustomerDTO() {
    }

    public CustomerDTO(String name, String surname, String oib, CustomerStatus status) {
        this.name = name;
        this.surname = surname;
        this.oib = oib;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }
}

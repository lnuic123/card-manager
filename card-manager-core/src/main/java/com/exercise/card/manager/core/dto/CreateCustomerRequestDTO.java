package com.exercise.card.manager.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class CreateCustomerRequestDTO {
    @NotBlank(message = "Customer name is required.")
    @Schema(name = "name", example = "Bob")
    private String name;

    @NotBlank(message = "Customer surname is required.")
    @Schema(name = "surname", example = "Ross")
    private String surname;

    @Schema(name = "oib", example = "12345678987")
    private String oib;

    public CreateCustomerRequestDTO() {
    }

    public CreateCustomerRequestDTO(String name, String surname, String oib) {
        this.name = name;
        this.surname = surname;
        this.oib = oib;
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
}

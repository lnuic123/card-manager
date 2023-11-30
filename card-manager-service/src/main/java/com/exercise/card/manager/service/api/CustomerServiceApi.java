package com.exercise.card.manager.service.api;

import com.exercise.card.manager.core.dto.CreateCustomerRequestDTO;
import com.exercise.card.manager.core.dto.CustomerDTO;

import java.util.Optional;

public interface CustomerServiceApi {

    void createCustomer(CreateCustomerRequestDTO request);

    Optional<CustomerDTO> findCustomer(String oib);

    String[] deleteCustomer(String oib);
}

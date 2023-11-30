package com.exercise.card.manager.util;

import com.exercise.card.manager.core.dto.CustomerDTO;
import com.exercise.card.manager.core.enums.CustomerStatus;
import com.exercise.card.manager.dao.model.Customer;
import com.exercise.card.manager.exception.CardManagerException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceHelper {

    public static void validateCustomerOib(String oib) {
        if (StringUtils.isEmpty(oib))
            throw new CardManagerException("Customer oib is required", 1010, new String[]{oib}, HttpStatus.BAD_REQUEST);
        if (oib.length() != 11)
            throw new CardManagerException("Oib length must be 11", 1011, new String[]{oib}, HttpStatus.BAD_REQUEST);
        if (!oib.matches("[0-9]+"))
            throw new CardManagerException("Oib must be only numeric", 1011, new String[]{oib}, HttpStatus.BAD_REQUEST);
    }

    public static CustomerDTO mapCustomerToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName(customer.getName());
        customerDTO.setSurname(customer.getSurname());
        customerDTO.setOib(customer.getOib());
        if (customer.getStatus() != null) {
            customerDTO.setStatus(CustomerStatus.ACTIVE.name().equals(customer.getStatus().getName()) ?
                    CustomerStatus.ACTIVE : CustomerStatus.INACTIVE);
        }
        return customerDTO;
    }
}

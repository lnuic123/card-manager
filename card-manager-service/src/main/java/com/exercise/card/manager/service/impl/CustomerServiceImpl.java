package com.exercise.card.manager.service.impl;

import com.exercise.card.manager.core.dto.CreateCustomerRequestDTO;
import com.exercise.card.manager.core.dto.CustomerDTO;
import com.exercise.card.manager.core.enums.CardCreationStatus;
import com.exercise.card.manager.core.enums.CustomerStatus;
import com.exercise.card.manager.dao.model.Customer;
import com.exercise.card.manager.dao.repository.CustomerRepository;
import com.exercise.card.manager.dao.repository.StatusRepository;
import com.exercise.card.manager.exception.CardManagerException;
import com.exercise.card.manager.service.api.CustomerServiceApi;
import com.exercise.card.manager.util.CardServiceHelper;
import com.exercise.card.manager.util.CustomerServiceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerServiceApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;
    private final StatusRepository statusRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, StatusRepository statusRepository) {
        this.customerRepository = customerRepository;
        this.statusRepository = statusRepository;
    }

    @Override
    public void createCustomer(CreateCustomerRequestDTO request) {
        String oib = request.getOib();

        if (customerExists(oib)) {
            LOGGER.error("Customer already exists: {}", oib);
            throw new CardManagerException(new String[]{"Customer already exists"}, 2010,
                    new String[]{oib}, HttpStatus.BAD_REQUEST);
        }
        LOGGER.info("Saving new customer: {}", oib);
        customerRepository.save(mapCreateCustomerRequestToCustomer(request));
        LOGGER.info("Customer {} successfully saved", oib);
    }

    @Override
    public Optional<CustomerDTO> findCustomer(String oib) {

        List<Customer> customerList = customerRepository.findByOib(oib);

        return !customerList.isEmpty() ?
                Optional.of(CustomerServiceHelper.mapCustomerToCustomerDTO(customerList.get(0))) : Optional.empty();
    }

    @Override
    public String[] deleteCustomer(String oib) {
        long nOfDeletedCustomers = customerRepository.deleteByOib(oib);

        if (nOfDeletedCustomers == 0) {
            LOGGER.error("Customer doesn't exist: {}", oib);
            throw new CardManagerException(new String[]{"Customer doesn't exist"}, 2010,
                    new String[]{oib}, HttpStatus.BAD_REQUEST);
        }
        LOGGER.info("Customer deleted: {}", oib);
        List<String> responseStatus = new ArrayList<>();
        responseStatus.add("Customer deleted: ".concat(oib));
        try {
            CardServiceHelper.updateCardCreationStatus(oib, CardCreationStatus.INACTIVE);
        } catch (CardManagerException e) {
            responseStatus.add(e.getMessage());
            return responseStatus.toArray(new String[0]);
        }
        responseStatus.add("Card creation status successfully updated");
        return responseStatus.toArray(new String[0]);
    }

    private Customer mapCreateCustomerRequestToCustomer(CreateCustomerRequestDTO request) {
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setSurname(request.getSurname());
        customer.setOib(request.getOib());
        customer.setStatus(statusRepository.findByName(String.valueOf(CustomerStatus.ACTIVE)).get(0));
        return customer;
    }

    private Boolean customerExists(final String oib) {
        return !customerRepository.findByOib(oib).isEmpty();
    }
}

package com.exercise.card.manager.service.impl;

import com.exercise.card.manager.api.CardManagerRestController;
import com.exercise.card.manager.core.dto.CustomerDTO;
import com.exercise.card.manager.core.enums.CardCreationStatus;
import com.exercise.card.manager.exception.CardManagerException;
import com.exercise.card.manager.service.api.CardServiceApi;
import com.exercise.card.manager.service.api.CustomerServiceApi;
import com.exercise.card.manager.util.CardServiceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardServiceApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(CardManagerRestController.class);

    private final CustomerServiceApi customerServiceApi;

    public CardServiceImpl(CustomerServiceApi customerServiceApi) {
        this.customerServiceApi = customerServiceApi;
    }

    @Override
    public CustomerDTO initializeCreation(String oib) {

        Optional<CustomerDTO> customerDTO = customerServiceApi.findCustomer(oib);

        if (customerDTO.isEmpty()) {
            LOGGER.error("Customer not found: {}", oib);
            throw new CardManagerException("Customer not found", 1020, new String[]{oib});
        }

        if (CardServiceHelper.isCreationStartedForCustomer(customerDTO.get())) {
            updateCardCreationStatus(oib);
        }

        CardServiceHelper.initializeCardCreation(customerDTO.get());

        LOGGER.info("Card creation started for customer: {}", oib);

        return customerDTO.get();
    }

    private void updateCardCreationStatus(String oib) {
        List<String> statusMessages = new ArrayList<>();
        statusMessages.add("Card creation process already started for customer: ".concat(oib));
        LOGGER.error("Card creation process already started for customer: {}", oib);

        String updateStatus = null;
        try {
            CardServiceHelper.updateCardCreationStatus(oib, CardCreationStatus.ACTIVE);
        } catch (CardManagerException exception) {
            updateStatus = exception.getMessage();
        }
        if (updateStatus == null) {
            updateStatus = "Card creation status successfully updated";
        }

        statusMessages.add(updateStatus);
        throw new CardManagerException(statusMessages.toArray(new String[0]), 1021, new String[]{oib});
    }
}

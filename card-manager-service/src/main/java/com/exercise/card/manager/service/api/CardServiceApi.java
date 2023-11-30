package com.exercise.card.manager.service.api;

import com.exercise.card.manager.core.dto.CustomerDTO;

public interface CardServiceApi {

    CustomerDTO initializeCreation(final String oib);

}

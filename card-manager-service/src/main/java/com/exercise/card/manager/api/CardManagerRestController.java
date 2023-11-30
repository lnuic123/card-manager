package com.exercise.card.manager.api;

import com.exercise.card.manager.core.CardManagerResponse;
import com.exercise.card.manager.core.dto.CustomerDTO;
import com.exercise.card.manager.service.api.CardServiceApi;
import com.exercise.card.manager.util.CustomerServiceHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
@Tag(name = "Card API")
public class CardManagerRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CardManagerRestController.class);
    private final CardServiceApi cardServiceApi;

    public CardManagerRestController(CardServiceApi cardServiceApi) {
        this.cardServiceApi = cardServiceApi;
    }

    @Operation(summary = "Initialize card creation process for customer",
            description = "Returns customer object if process successfully started")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card creation process successfully started"),
            @ApiResponse(responseCode = "400", description = "Invalid OIB inserted")
    })
    @GetMapping(value = "/initializeCreation/{oib}")
    public CardManagerResponse<CustomerDTO> initializeCreation(
            @PathVariable @Parameter(name = "oib", description = "Customer OIB", example = "12345678987") final String oib) {

        CustomerServiceHelper.validateCustomerOib(oib);

        LOGGER.info("initializeCreation oib: {}", oib);

        CustomerDTO customerDTO = cardServiceApi.initializeCreation(oib);

        return new CardManagerResponse<>("Card creation started for customer", customerDTO);
    }

}

package com.exercise.card.manager.api;

import com.exercise.card.manager.core.CardManagerResponse;
import com.exercise.card.manager.core.dto.CreateCustomerRequestDTO;
import com.exercise.card.manager.core.dto.CustomerDTO;
import com.exercise.card.manager.exception.CardManagerException;
import com.exercise.card.manager.service.api.CustomerServiceApi;
import com.exercise.card.manager.util.CustomerServiceHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/customer")
@Tag(name = "Customer API")
public class CustomerRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CardManagerRestController.class);

    private final CustomerServiceApi customerServiceApi;

    public CustomerRestController(CustomerServiceApi customerServiceApi) {
        this.customerServiceApi = customerServiceApi;
    }

    @Operation(summary = "Creates new customer entity", description = "Returns status of customer creation process")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer successfully created"),
            @ApiResponse(responseCode = "400", description = "Customer already exists")
    })
    @PostMapping(value = "/create")
    public CardManagerResponse<String> createCustomer(@Valid @RequestBody final CreateCustomerRequestDTO request) {

        CustomerServiceHelper.validateCustomerOib(request.getOib());

        LOGGER.info("createCustomer request: {}, {}, {}", request.getName(), request.getSurname(), request.getOib());

        customerServiceApi.createCustomer(request);

        return new CardManagerResponse<>("Customer successfully created");
    }

    @Operation(summary = "Find customer by OIB", description = "Returns found customer object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found"),
            @ApiResponse(responseCode = "400", description = "Invalid OIB inserted"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @GetMapping(value = "/find/{oib}")
    public CardManagerResponse<CustomerDTO> findCustomer(
            @PathVariable @Parameter(name = "oib", description = "Customer OIB", example = "12345678987") final String oib) {

        CustomerServiceHelper.validateCustomerOib(oib);

        LOGGER.info("findCustomer request: {}", oib);

        Optional<CustomerDTO> customerDTO = customerServiceApi.findCustomer(oib);

        if (customerDTO.isPresent()) {
            LOGGER.info("Customer found: {}", oib);
            return new CardManagerResponse<>(customerDTO.get());
        }
        LOGGER.error("Customer not found: {}", oib);
        throw new CardManagerException(new String[]{"Customer not found: ".concat(oib)}, 1033,
                new String[]{oib}, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Delete customer by OIB", description = "Returns status of customer deletion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Customer doesn't exist")
    })
    @GetMapping(value = "/delete/{oib}")
    public CardManagerResponse<String> deleteCustomer(
            @PathVariable @Parameter(name = "oib", description = "Customer OIB", example = "12345678987") final String oib) {

        CustomerServiceHelper.validateCustomerOib(oib);

        LOGGER.info("deleteCustomer request: {}", oib);

        String[] deleteStatus = customerServiceApi.deleteCustomer(oib);

        return new CardManagerResponse<>(deleteStatus);
    }
}

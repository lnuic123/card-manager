package com.exercise.card.manager.exception.handler;

import com.exercise.card.manager.api.CardManagerRestController;
import com.exercise.card.manager.exception.CardManagerException;
import com.exercise.card.manager.exception.error.CardManagerError;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CardManagerExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CardManagerRestController.class);

    @ExceptionHandler(CardManagerException.class)
    public ResponseEntity<CardManagerError> onCardManagerException(final HttpServletRequest request,
                                                                   final CardManagerException exception) {

        LOGGER.error("Error occurred for request URL: {}\nErrorCode: {}\nErrorMessage: {}", request.getRequestURL(),
                exception.getErrorCode(), exception.getErrorMessages());

        HttpStatus status = exception.getHttpStatus() != null ?
                exception.getHttpStatus() : HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(new CardManagerError(exception.getErrorCode(), exception.getErrorMessages(),
                LocalDateTime.now()), status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Map<String, List<String>>> handleGeneralExceptions(Exception ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Map<String, List<String>>> handleRuntimeExceptions(RuntimeException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}
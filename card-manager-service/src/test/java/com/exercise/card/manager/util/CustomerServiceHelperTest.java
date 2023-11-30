package com.exercise.card.manager.util;

import com.exercise.card.manager.exception.CardManagerException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceHelperTest {

    @Test
    void nullShouldThrowException() {

        CardManagerException e = assertThrows(
                CardManagerException.class,() -> CustomerServiceHelper.validateCustomerOib(null));

        String expectedMessage = "Customer oib is required";
        String actualMessage = e.getMessage();

        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void oibLengthNotElevenShouldThrowException() {

        CardManagerException e = assertThrows(
                CardManagerException.class,() -> CustomerServiceHelper.validateCustomerOib("45612379"));

        String expectedMessage = "Oib length must be 11";
        String actualMessage = e.getMessage();

        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void oibNotContainingOnlyNumbersShouldThrowException() {

        CardManagerException e = assertThrows(
                CardManagerException.class,() -> CustomerServiceHelper.validateCustomerOib("45794asd874"));

        String expectedMessage = "Oib must be only numeric";
        String actualMessage = e.getMessage();

        assertEquals(actualMessage, expectedMessage);
    }
}
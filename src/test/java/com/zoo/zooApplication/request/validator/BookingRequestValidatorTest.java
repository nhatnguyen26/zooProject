package com.zoo.zooApplication.request.validator;

import com.zoo.zooApplication.exception.InvalidRequestException;
import org.junit.Test;

import static org.junit.Assert.*;

public class BookingRequestValidatorTest {

    @Test
    public void validateBookingIdNormal() {
        BookingRequestValidator validator = new BookingRequestValidator();
        validator.validateBookingId("123");
    }

    @Test
    public void validateBookingIdInvalid() {
        BookingRequestValidator validator = new BookingRequestValidator();
        try {
            validator.validateBookingId("abc");
            fail();
        } catch (InvalidRequestException ire) {
            assertEquals("Invalid booking id: abc", ire.getMessage());
        }
    }
}
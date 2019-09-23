package com.zoo.zooApplication.request.validator;

import com.zoo.zooApplication.exception.InvalidRequestException;
import com.zoo.zooApplication.request.CreateBookingRequest;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

@Component
public class BookingRequestValidator {
    public void validateBookingId(String bookingId) {
        if (NumberUtils.toLong(bookingId) == 0) {
            throw new InvalidRequestException(String.format("Invalid booking id: %s", bookingId));
        }
    }

    public void validateCreateBookingRequest(CreateBookingRequest testRequest) {
        // TODO: implement and refactor exception
    }
}

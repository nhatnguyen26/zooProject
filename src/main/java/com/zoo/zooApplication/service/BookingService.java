package com.zoo.zooApplication.service;

import com.zoo.zooApplication.request.BookingRequest;
import com.zoo.zooApplication.request.SearchFieldBookingRequest;
import com.zoo.zooApplication.response.FieldBooking;
import com.zoo.zooApplication.response.FieldBookingResponse;

public interface BookingService {

    FieldBooking findBookingById(String bookingId);

    FieldBooking createBooking(BookingRequest bookingRequest);

    FieldBooking editBooking(String bookingId, BookingRequest editBookingRequest);

    FieldBooking deleteBooking(String bookingId);

    FieldBookingResponse search(SearchFieldBookingRequest searchRequest);

}

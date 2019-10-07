package com.zoo.zooApplication.service;

import com.zoo.zooApplication.request.BookingRequest;
import com.zoo.zooApplication.request.SearchFieldBookingRequest;
import com.zoo.zooApplication.response.FieldBooking;

import java.util.List;

public interface BookingService {

    FieldBooking findBookingById(String bookingId);

    FieldBooking createBooking(BookingRequest bookingRequest);

    FieldBooking editBooking(String bookingId, BookingRequest editBookingRequest);

    FieldBooking deleteBooking(String bookingId);

    List<FieldBooking> findAllBookingByFieldId(SearchFieldBookingRequest searchRequest);

    List<FieldBooking> findByUserInfo(SearchFieldBookingRequest searchFieldBookingRequest);
}

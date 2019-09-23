package com.zoo.zooApplication.service;

import com.zoo.zooApplication.request.CreateBookingRequest;
import com.zoo.zooApplication.request.SearchFieldBookingRequest;
import com.zoo.zooApplication.response.FieldBooking;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookingService {

    FieldBooking findBookingById(String bookingId);

    FieldBooking createBooking(CreateBookingRequest bookingRequest);

    List<FieldBooking> findAllBookingByFieldId(SearchFieldBookingRequest searchRequest);

    List<FieldBooking> findByUserInfo(SearchFieldBookingRequest searchFieldBookingRequest);
}

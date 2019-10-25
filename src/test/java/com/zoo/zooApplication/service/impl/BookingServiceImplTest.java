package com.zoo.zooApplication.service.impl;

import com.zoo.zooApplication.converter.FieldBookingDOToResponseConverter;
import com.zoo.zooApplication.dao.model.FieldBookingDO;
import com.zoo.zooApplication.dao.repository.FieldBookingRepository;
import com.zoo.zooApplication.request.BookingDetailRequest;
import com.zoo.zooApplication.request.BookingRequest;
import com.zoo.zooApplication.response.FieldBooking;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BookingServiceImplTest {

    @Mock
    private FieldBookingRepository fieldBookingRepository;

    @Mock
    private FieldBookingDOToResponseConverter fieldBookingDOToResponseConverter;

    private BookingServiceImpl bookingService;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        bookingService = new BookingServiceImpl(fieldBookingRepository, fieldBookingDOToResponseConverter);
    }

    @Test
    public void testFindBookingByIdValid() {
        Optional<FieldBookingDO> mockDO = Optional.of(mock(FieldBookingDO.class));
        when(fieldBookingRepository.findById(123L)).thenReturn(mockDO);
        FieldBooking mockResponse = mock(FieldBooking.class);
        when(fieldBookingDOToResponseConverter.convert(mockDO.get())).thenReturn(mockResponse);

        assertEquals(mockResponse, bookingService.findBookingById("123"));
    }

	@Test
    public void testCreateBookingValidRequest() {
        BookingRequest testRequest = mock(BookingRequest.class);
		List<BookingDetailRequest> detailList = new ArrayList<>();
		detailList.add(mock(BookingDetailRequest.class));
		when(testRequest.getBookingDetails()).thenReturn(detailList);
        FieldBookingDO mockDO = mock(FieldBookingDO.class);
        when(fieldBookingRepository.save(any(FieldBookingDO.class))).thenReturn(mockDO);
        FieldBooking mockResponse = mock(FieldBooking.class);
        when(fieldBookingDOToResponseConverter.convert(mockDO)).thenReturn(mockResponse);
        assertEquals(mockResponse, bookingService.createBooking(testRequest));
    }

    @Test
    public void testCreateBookingValidRequestVerifyBookerName() {
        BookingRequest testRequest = mock(BookingRequest.class);
        List<BookingDetailRequest> detailList = new ArrayList<>();
        detailList.add(mock(BookingDetailRequest.class));
        when(testRequest.getBookingDetails()).thenReturn(detailList);
        when(testRequest.getBookerName()).thenReturn("abc");
        FieldBookingDO requestDO = fieldRequestHelperBasicInfo(testRequest);
        assertEquals("abc", requestDO.getBookerName());
    }

    @Test
    public void testCreateBookingValidRequestVerifyBookerEmail() {
        BookingRequest testRequest = mock(BookingRequest.class);
        List<BookingDetailRequest> detailList = new ArrayList<>();
        detailList.add(mock(BookingDetailRequest.class));
        when(testRequest.getBookingDetails()).thenReturn(detailList);
        when(testRequest.getBookerEmail()).thenReturn("abc@gmail.com");
        FieldBookingDO requestDO = fieldRequestHelperBasicInfo(testRequest);
        assertEquals("abc@gmail.com", requestDO.getBookerEmail());
    }

    @Test
    public void testCreateBookingValidRequestVerifyBookerPhone() {
        BookingRequest testRequest = mock(BookingRequest.class);
        List<BookingDetailRequest> detailList = new ArrayList<>();
        detailList.add(mock(BookingDetailRequest.class));
        when(testRequest.getBookingDetails()).thenReturn(detailList);
        when(testRequest.getBookerPhone()).thenReturn("123456789");
        FieldBookingDO requestDO = fieldRequestHelperBasicInfo(testRequest);
        assertEquals("123456789", requestDO.getBookerPhone());
    }
//
    @Test
    public void testCreateBookingValidRequestVerifyPrice() {
        BookingRequest testRequest = mock(BookingRequest.class);
        List<BookingDetailRequest> detailList = new ArrayList<>();
        detailList.add(mock(BookingDetailRequest.class));
        when(testRequest.getBookingDetails()).thenReturn(detailList);
        when(testRequest.getPriceAmount()).thenReturn(100000.00);
        FieldBookingDO requestDO = fieldRequestHelperBasicInfo(testRequest);
        assertEquals("VND", requestDO.getCurrencyId());
        assertEquals(Double.valueOf(100000.00), requestDO.getPriceAmount());
    }

    private FieldBookingDO fieldRequestHelperBasicInfo(BookingRequest testRequest) {
        // basic stub as required
        ArgumentCaptor<FieldBookingDO> argumentCaptor = ArgumentCaptor.forClass(FieldBookingDO.class);
        FieldBookingDO mockDO = mock(FieldBookingDO.class);
        when(fieldBookingRepository.save(argumentCaptor.capture())).thenReturn(mockDO);
        bookingService.createBooking(testRequest);
        return argumentCaptor.getValue();
    }
}
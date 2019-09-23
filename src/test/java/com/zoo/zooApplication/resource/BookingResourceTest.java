package com.zoo.zooApplication.resource;

import com.zoo.zooApplication.request.CreateBookingRequest;
import com.zoo.zooApplication.response.FieldBooking;
import com.zoo.zooApplication.service.BookingService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookingResourceTest {

    @Mock
    private BookingService mockBookingService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindBookingByIdFound() {
        BookingResource bookingResource = new BookingResource(mockBookingService);
        FieldBooking mockResponse = mock(FieldBooking.class);
        when(mockBookingService.findBookingById("123")).thenReturn(mockResponse);
        assertEquals(mockResponse, bookingResource.findById("123"));
    }

    @Test
    public void testCreateBooking() {
        BookingResource bookingResource = new BookingResource(mockBookingService);
        FieldBooking mockResponse = mock(FieldBooking.class);
        when(mockResponse.getId()).thenReturn(123l);
        CreateBookingRequest request = mock(CreateBookingRequest.class);
        when(mockBookingService.createBooking(request)).thenReturn(mockResponse);
        UriInfo mockURIInfo = mock(UriInfo.class);
        UriBuilder mockBuilder = mock(UriBuilder.class);
        when(mockBuilder.path("123")).thenReturn(mockBuilder);
        URI expectURI = URI.create("/123");
        when(mockBuilder.build()).thenReturn(expectURI);
        when(mockURIInfo.getAbsolutePathBuilder()).thenReturn(mockBuilder);
        Response response = bookingResource.createBooking(request, mockURIInfo);
        assertEquals(Response.Status.CREATED, response.getStatusInfo());
        assertEquals(expectURI, response.getLocation());
        assertEquals(mockResponse, response.getEntity());
    }
}
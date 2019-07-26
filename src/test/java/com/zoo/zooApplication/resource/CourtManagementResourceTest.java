package com.zoo.zooApplication.resource;

import com.zoo.zooApplication.request.CreateCourtRequest;
import com.zoo.zooApplication.request.CreateFieldRequest;
import com.zoo.zooApplication.response.Court;
import com.zoo.zooApplication.service.CourtAndFieldService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CourtManagementResourceTest {

    @Mock
    private CourtAndFieldService mockCourtAndFieldService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindCourtById() {
        CourtManagementResource resource = new CourtManagementResource(mockCourtAndFieldService);
        Court expectCourt = mock(Court.class);
        when(mockCourtAndFieldService.findCourtById("123")).thenReturn(expectCourt);
        assertEquals(expectCourt, resource.findById("123"));
    }

    @Test
    public void testCreateCourt() {
        CourtManagementResource resource = new CourtManagementResource(mockCourtAndFieldService);
        Court expectCourt = mock(Court.class);
        when(expectCourt.getId()).thenReturn(123l);
        CreateCourtRequest mockCourtRequest = mock(CreateCourtRequest.class);
        when(mockCourtAndFieldService.createCourt(mockCourtRequest)).thenReturn(expectCourt);
        UriInfo mockURIInfo = mock(UriInfo.class);
        UriBuilder mockBuilder = mock(UriBuilder.class);
        when(mockBuilder.path("123")).thenReturn(mockBuilder);
        URI expectURI = URI.create("/123");
        when(mockBuilder.build()).thenReturn(expectURI);
        when(mockURIInfo.getAbsolutePathBuilder()).thenReturn(mockBuilder);
        Response response = resource.createBooking(mockCourtRequest, mockURIInfo);
        assertEquals(Response.Status.CREATED, response.getStatusInfo());
        assertEquals(expectURI, response.getLocation());
        assertEquals(expectCourt, response.getEntity());
    }

    @Test
    public void testAddFieldToCourt() {
        CourtManagementResource resource = new CourtManagementResource(mockCourtAndFieldService);
        Court expectCourt = mock(Court.class);
        CreateFieldRequest mockRequest = mock(CreateFieldRequest.class);
        when(mockCourtAndFieldService.addFieldToCourt("123", mockRequest)).thenReturn(expectCourt);
        assertEquals(expectCourt, resource.addFieldToCourt("123", mockRequest));
    }

}
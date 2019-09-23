package com.zoo.zooApplication.resource;

import com.zoo.zooApplication.firebaseadaptor.IFirebaseAuth;
import com.zoo.zooApplication.request.ClaimKeyRequest;
import com.zoo.zooApplication.request.CreateCourtRequest;
import com.zoo.zooApplication.request.CreateFieldRequest;
import com.zoo.zooApplication.request.CreateFieldTypeRequest;
import com.zoo.zooApplication.request.FieldRequest;
import com.zoo.zooApplication.request.FieldTypeRequest;
import com.zoo.zooApplication.response.ClaimKey;
import com.zoo.zooApplication.response.Court;
import com.zoo.zooApplication.response.CourtsResponse;
import com.zoo.zooApplication.response.Field;
import com.zoo.zooApplication.response.FieldType;
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
import static org.mockito.Mockito.*;

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
	public void testDeleteCourt() {
		CourtManagementResource resource = new CourtManagementResource(mockCourtAndFieldService);
		Court expectCourt = mock(Court.class);
		when(mockCourtAndFieldService.deleteCourt("123")).thenReturn(expectCourt);
		assertEquals(expectCourt, resource.deleteCourt("123"));
	}

	@Test
	public void testFindClaimKeyByCourtId() {
		CourtManagementResource resource = new CourtManagementResource(mockCourtAndFieldService);
		ClaimKey expectClaimKey = mock(ClaimKey.class);
		when(mockCourtAndFieldService.findClaimKeyByCourtId("123")).thenReturn(expectClaimKey);
		assertEquals(expectClaimKey, resource.findClaimKeyByCourtId("123"));
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
        Response response = resource.createCourt(mockCourtRequest, mockURIInfo);
        assertEquals(Response.Status.CREATED, response.getStatusInfo());
        assertEquals(expectURI, response.getLocation());
        assertEquals(expectCourt, response.getEntity());
    }

	@Test
	public void testFindAllCourtsManagedByUser() {
		CourtManagementResource resource = new CourtManagementResource(mockCourtAndFieldService);
		IFirebaseAuth mockFirebaseAuth = mock(IFirebaseAuth.class);
		when(mockFirebaseAuth.getUid()).thenReturn("uid");
		CourtsResponse expectResponse = mock(CourtsResponse.class);
		when(mockCourtAndFieldService.findAllCourtManageByUser("uid")).thenReturn(expectResponse);
		assertEquals(expectResponse, resource.findAllCourtsManagedByUser(mockFirebaseAuth));
	}

	@Test
	public void testClaimCourtAsOwner() {
		CourtManagementResource resource = new CourtManagementResource(mockCourtAndFieldService);
		IFirebaseAuth mockFirebaseAuth = mock(IFirebaseAuth.class);
		ClaimKeyRequest claimRequest = mock(ClaimKeyRequest.class);
		Court expectResponse = mock(Court.class);
		when(mockCourtAndFieldService.claimCourtAsOwner(claimRequest)).thenReturn(expectResponse);
		assertEquals(expectResponse, resource.claimCourtAsOwner(claimRequest, mockFirebaseAuth));
		verify(claimRequest, times(1)).setFirebaseAuth(mockFirebaseAuth);
	}

	@Test
	public void testFindByClaimKey() {
		CourtManagementResource resource = new CourtManagementResource(mockCourtAndFieldService);
		Court expectResponse = mock(Court.class);
		when(mockCourtAndFieldService.findCourtByClaimKey("testKey")).thenReturn(expectResponse);
		assertEquals(expectResponse, resource.findByClaimKey("testKey"));
	}

	@Test
	public void testEditCourt() {
		CourtManagementResource resource = new CourtManagementResource(mockCourtAndFieldService);
		Court expectCourt = mock(Court.class);
		CreateCourtRequest mockRequest = mock(CreateCourtRequest.class);
		when(mockCourtAndFieldService.editCourt("123", mockRequest)).thenReturn(expectCourt);
		assertEquals(expectCourt, resource.editCourt("123", mockRequest));
	}

    @Test
    public void testAddFieldToCourt() {
        CourtManagementResource resource = new CourtManagementResource(mockCourtAndFieldService);
        Court expectCourt = mock(Court.class);
        CreateFieldRequest mockRequest = mock(CreateFieldRequest.class);
        when(mockCourtAndFieldService.addFieldToCourt("123", mockRequest)).thenReturn(expectCourt);
        assertEquals(expectCourt, resource.addFieldToCourt("123", mockRequest));
    }

	@Test
	public void testEditField() {
		CourtManagementResource resource = new CourtManagementResource(mockCourtAndFieldService);
		Field expectField = mock(Field.class);
		FieldRequest mockRequest = mock(FieldRequest.class);
		when(mockCourtAndFieldService.editField("123", "1", mockRequest)).thenReturn(expectField);
		assertEquals(expectField, resource.editField("123", "1", mockRequest));
	}

	@Test
	public void testDeleteField() {
		CourtManagementResource resource = new CourtManagementResource(mockCourtAndFieldService);
		Field expectField = mock(Field.class);
		when(mockCourtAndFieldService.deleteField("123", "1")).thenReturn(expectField);
		assertEquals(expectField, resource.deleteField("123", "1"));
	}

	@Test
	public void testAddFieldTypeToCourt() {
		CourtManagementResource resource = new CourtManagementResource(mockCourtAndFieldService);
		Court expectCourt = mock(Court.class);
		CreateFieldTypeRequest mockRequest = mock(CreateFieldTypeRequest.class);
		when(mockCourtAndFieldService.addFieldTypeToCourt("123", mockRequest)).thenReturn(expectCourt);
		assertEquals(expectCourt, resource.addFieldTypeToCourt("123", mockRequest));
	}

	@Test
	public void testEditFieldType() {
		CourtManagementResource resource = new CourtManagementResource(mockCourtAndFieldService);
		FieldType expectFieldType = mock(FieldType.class);
		FieldTypeRequest mockRequest = mock(FieldTypeRequest.class);
		when(mockCourtAndFieldService.editFieldType("123", "1", mockRequest)).thenReturn(expectFieldType);
		assertEquals(expectFieldType, resource.editFieldType("123", "1", mockRequest));
	}

	@Test
	public void testDeleteFieldType() {
		CourtManagementResource resource = new CourtManagementResource(mockCourtAndFieldService);
		FieldType expectField = mock(FieldType.class);
		when(mockCourtAndFieldService.deleteFieldType("123", "1")).thenReturn(expectField);
		assertEquals(expectField, resource.deleteFieldType("123", "1"));
	}

}
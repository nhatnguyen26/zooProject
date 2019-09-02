package com.zoo.zooApplication.service.impl;

import com.zoo.zooApplication.converter.CourtDOToResponseConverter;
import com.zoo.zooApplication.converter.FieldDOToResponseConverter;
import com.zoo.zooApplication.converter.FieldTypeDOToResponseConverter;
import com.zoo.zooApplication.dao.model.CourtClaimOTPDO;
import com.zoo.zooApplication.dao.model.CourtDO;
import com.zoo.zooApplication.dao.model.CourtUserRoleDO;
import com.zoo.zooApplication.dao.model.FieldDO;
import com.zoo.zooApplication.dao.repository.CourtClaimOTPRepository;
import com.zoo.zooApplication.dao.repository.CourtRepository;
import com.zoo.zooApplication.dao.repository.CourtUserRoleRepository;
import com.zoo.zooApplication.dao.repository.FieldRepository;
import com.zoo.zooApplication.dao.repository.FieldTypeRepository;
import com.zoo.zooApplication.dao.repository.PriceChartRepository;
import com.zoo.zooApplication.request.ClaimKeyRequest;
import com.zoo.zooApplication.request.CreateCourtRequest;
import com.zoo.zooApplication.request.CreateFieldRequest;
import com.zoo.zooApplication.request.FieldRequest;
import com.zoo.zooApplication.response.Court;
import com.zoo.zooApplication.response.CourtsResponse;
import com.zoo.zooApplication.response.Field;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CourtAndFieldServiceImplTest {

	@Mock
	private CourtRepository courtRepository;

	@Mock
	private FieldRepository fieldRepository;

	@Mock
	private CourtDOToResponseConverter courtDOToResponseConverter;

	@Mock
	private CourtClaimOTPRepository courtClaimOTPRepository;

	@Mock
	private CourtUserRoleRepository courtUserRoleRepository;

	@Mock
	private FieldDOToResponseConverter fieldDOToResponseConverter;

	@Mock
	private FieldTypeRepository fieldTypeRepository;

	@Mock
	private PriceChartRepository priceChartRepository;

	@Mock
	private FieldTypeDOToResponseConverter fieldTypeDOToResponseConverter;

	private CourtAndFieldServiceImpl courtAndFieldService;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
		courtAndFieldService = new CourtAndFieldServiceImpl(courtRepository, courtClaimOTPRepository, fieldRepository,
			fieldTypeRepository, priceChartRepository,
			courtUserRoleRepository, courtDOToResponseConverter, fieldDOToResponseConverter, fieldTypeDOToResponseConverter);
	}

	/* COURT MANAGEMENT TESTS START */

	@Test
	public void testCreateCourt() {
		CourtDO mockCourtDO = mock(CourtDO.class);
		when(courtRepository.save(any(CourtDO.class))).thenReturn(mockCourtDO);

		CreateCourtRequest mockRequest = new CreateCourtRequest();
		Court response = mock(Court.class);
		when(courtDOToResponseConverter.convert(mockCourtDO)).thenReturn(response);
		assertEquals(response, courtAndFieldService.createCourt(mockRequest));
	}

	@Test
	public void testCreateCourtVerifyInputCourtName() {
		CourtDO mockCourtDO = mock(CourtDO.class);
		ArgumentCaptor<CourtDO> courtDOArgumentCaptor = ArgumentCaptor.forClass(CourtDO.class);
		when(courtRepository.save(courtDOArgumentCaptor.capture())).thenReturn(mockCourtDO);

		CreateCourtRequest mockRequest = new CreateCourtRequest();
		mockRequest.setName("testName");
		courtAndFieldService.createCourt(mockRequest);
		CourtDO courtDO = courtDOArgumentCaptor.getValue();
		assertNotNull(courtDO);
		assertEquals("testName", courtDO.getName());
		assertTrue(courtDO.getFields().isEmpty());
	}

	@Test
	public void testCreateCourtVerifyInputCourtAddressStreet() {
		CourtDO mockCourtDO = mock(CourtDO.class);
		ArgumentCaptor<CourtDO> courtDOArgumentCaptor = ArgumentCaptor.forClass(CourtDO.class);
		when(courtRepository.save(courtDOArgumentCaptor.capture())).thenReturn(mockCourtDO);

		CreateCourtRequest mockRequest = new CreateCourtRequest();
		mockRequest.setAddressStreet("testStreet");
		courtAndFieldService.createCourt(mockRequest);
		CourtDO courtDO = courtDOArgumentCaptor.getValue();
		assertNotNull(courtDO);
		assertEquals("testStreet", courtDO.getAddressStreet());
		assertTrue(courtDO.getFields().isEmpty());
	}

	@Test
	public void testCreateCourtVerifyInputCourtAddressWard() {
		CourtDO mockCourtDO = mock(CourtDO.class);
		ArgumentCaptor<CourtDO> courtDOArgumentCaptor = ArgumentCaptor.forClass(CourtDO.class);
		when(courtRepository.save(courtDOArgumentCaptor.capture())).thenReturn(mockCourtDO);

		CreateCourtRequest mockRequest = new CreateCourtRequest();
		mockRequest.setAddressWard("P1");
		courtAndFieldService.createCourt(mockRequest);
		CourtDO courtDO = courtDOArgumentCaptor.getValue();
		assertNotNull(courtDO);
		assertEquals("P1", courtDO.getAddressWard());
		assertTrue(courtDO.getFields().isEmpty());
	}

	@Test
	public void testCreateCourtVerifyInputCourtAddressDistrict() {
		CourtDO mockCourtDO = mock(CourtDO.class);
		ArgumentCaptor<CourtDO> courtDOArgumentCaptor = ArgumentCaptor.forClass(CourtDO.class);
		when(courtRepository.save(courtDOArgumentCaptor.capture())).thenReturn(mockCourtDO);

		CreateCourtRequest mockRequest = new CreateCourtRequest();
		mockRequest.setAddressDistrict("Q1");
		courtAndFieldService.createCourt(mockRequest);
		CourtDO courtDO = courtDOArgumentCaptor.getValue();
		assertNotNull(courtDO);
		assertEquals("Q1", courtDO.getAddressDistrict());
		assertTrue(courtDO.getFields().isEmpty());
	}

	@Test
	public void testCreateCourtVerifyInputCourtAddressCity() {
		CourtDO mockCourtDO = mock(CourtDO.class);
		ArgumentCaptor<CourtDO> courtDOArgumentCaptor = ArgumentCaptor.forClass(CourtDO.class);
		when(courtRepository.save(courtDOArgumentCaptor.capture())).thenReturn(mockCourtDO);

		CreateCourtRequest mockRequest = new CreateCourtRequest();
		mockRequest.setAddressCity("TP HCM");
		courtAndFieldService.createCourt(mockRequest);
		CourtDO courtDO = courtDOArgumentCaptor.getValue();
		assertNotNull(courtDO);
		assertEquals("TP HCM", courtDO.getAddressCity());
		assertTrue(courtDO.getFields().isEmpty());
	}

	@Test
	public void testCreateCourtVerifyInputCourtAddressCountry() {
		CourtDO mockCourtDO = mock(CourtDO.class);
		ArgumentCaptor<CourtDO> courtDOArgumentCaptor = ArgumentCaptor.forClass(CourtDO.class);
		when(courtRepository.save(courtDOArgumentCaptor.capture())).thenReturn(mockCourtDO);

		CreateCourtRequest mockRequest = new CreateCourtRequest();
		courtAndFieldService.createCourt(mockRequest);
		CourtDO courtDO = courtDOArgumentCaptor.getValue();
		assertNotNull(courtDO);
		assertEquals("VN", courtDO.getAddressCountry());
		assertTrue(courtDO.getFields().isEmpty());
	}

	@Test
	public void testCreateCourtVerifyInputCourtPhoneNumber() {
		CourtDO mockCourtDO = mock(CourtDO.class);
		ArgumentCaptor<CourtDO> courtDOArgumentCaptor = ArgumentCaptor.forClass(CourtDO.class);
		when(courtRepository.save(courtDOArgumentCaptor.capture())).thenReturn(mockCourtDO);

		CreateCourtRequest mockRequest = new CreateCourtRequest();
		mockRequest.setPhoneNumber("1234");
		courtAndFieldService.createCourt(mockRequest);
		CourtDO courtDO = courtDOArgumentCaptor.getValue();
		assertNotNull(courtDO);
		assertEquals("1234", courtDO.getPhoneNumber());
		assertTrue(courtDO.getFields().isEmpty());
	}

	@Test
	public void testFindCourtById() {
		CourtDO mockCourt = mock(CourtDO.class);
		when(courtRepository.findById(123L)).thenReturn(Optional.of(mockCourt));
		Court response = mock(Court.class);
		when(courtDOToResponseConverter.convert(mockCourt)).thenReturn(response);
		assertEquals(response, courtAndFieldService.findCourtById("123"));
	}

	@Test
	public void testEditCourtNotFound() {
		when(courtRepository.findById(123L)).thenReturn(Optional.empty());
		CreateCourtRequest mockRequest = mock(CreateCourtRequest.class);
		assertNull(courtAndFieldService.editCourt("123", mockRequest));
	}

	@Test
	public void testEditCourtSetName() {
		CourtDO mockCourt = mock(CourtDO.class);
		when(courtRepository.findById(123L)).thenReturn(Optional.of(mockCourt));
		when(courtRepository.save(mockCourt)).thenReturn(mockCourt);
		Court response = mock(Court.class);
		when(courtDOToResponseConverter.convert(mockCourt)).thenReturn(response);
		CreateCourtRequest mockRequest = new CreateCourtRequest();
		mockRequest.setName("newName");
		assertEquals(response, courtAndFieldService.editCourt("123", mockRequest));
		verify(mockCourt, times(1)).setName("newName");
		verifyNoMoreInteractions(mockCourt);
	}

	@Test
	public void testEditCourtSetStreet() {
		CourtDO mockCourt = mock(CourtDO.class);
		when(courtRepository.findById(123L)).thenReturn(Optional.of(mockCourt));
		when(courtRepository.save(mockCourt)).thenReturn(mockCourt);
		Court response = mock(Court.class);
		when(courtDOToResponseConverter.convert(mockCourt)).thenReturn(response);
		CreateCourtRequest mockRequest = new CreateCourtRequest();
		mockCourt.setAddressStreet("newStreet");
		assertEquals(response, courtAndFieldService.editCourt("123", mockRequest));
		verify(mockCourt, times(1)).setAddressStreet("newStreet");
		verifyNoMoreInteractions(mockCourt);
	}

	@Test
	public void testEditCourtSetWard() {
		CourtDO mockCourt = mock(CourtDO.class);
		when(courtRepository.findById(123L)).thenReturn(Optional.of(mockCourt));
		when(courtRepository.save(mockCourt)).thenReturn(mockCourt);
		Court response = mock(Court.class);
		when(courtDOToResponseConverter.convert(mockCourt)).thenReturn(response);
		CreateCourtRequest mockRequest = new CreateCourtRequest();
		mockRequest.setAddressWard("newWard");
		assertEquals(response, courtAndFieldService.editCourt("123", mockRequest));
		verify(mockCourt, times(1)).setAddressWard("newWard");
		verifyNoMoreInteractions(mockCourt);
	}

	@Test
	public void testEditCourtSetDistrict() {
		CourtDO mockCourt = mock(CourtDO.class);
		when(courtRepository.findById(123L)).thenReturn(Optional.of(mockCourt));
		when(courtRepository.save(mockCourt)).thenReturn(mockCourt);
		Court response = mock(Court.class);
		when(courtDOToResponseConverter.convert(mockCourt)).thenReturn(response);
		CreateCourtRequest mockRequest = new CreateCourtRequest();
		mockRequest.setAddressDistrict("newDist");
		assertEquals(response, courtAndFieldService.editCourt("123", mockRequest));
		verify(mockCourt, times(1)).setAddressDistrict("newDist");
		verifyNoMoreInteractions(mockCourt);
	}

	@Test
	public void testEditCourtSetCity() {
		CourtDO mockCourt = mock(CourtDO.class);
		when(courtRepository.findById(123L)).thenReturn(Optional.of(mockCourt));
		when(courtRepository.save(mockCourt)).thenReturn(mockCourt);
		Court response = mock(Court.class);
		when(courtDOToResponseConverter.convert(mockCourt)).thenReturn(response);
		CreateCourtRequest mockRequest = new CreateCourtRequest();
		mockRequest.setAddressCity("newCity");
		assertEquals(response, courtAndFieldService.editCourt("123", mockRequest));
		verify(mockCourt, times(1)).setAddressCity("newCity");
		verifyNoMoreInteractions(mockCourt);
	}

	@Test
	public void testEditCourtSetPhoneNumber() {
		CourtDO mockCourt = mock(CourtDO.class);
		when(courtRepository.findById(123L)).thenReturn(Optional.of(mockCourt));
		when(courtRepository.save(mockCourt)).thenReturn(mockCourt);
		Court response = mock(Court.class);
		when(courtDOToResponseConverter.convert(mockCourt)).thenReturn(response);
		CreateCourtRequest mockRequest = new CreateCourtRequest();
		mockRequest.setPhoneNumber("newNumber");
		assertEquals(response, courtAndFieldService.editCourt("123", mockRequest));
		verify(mockCourt, times(1)).setPhoneNumber("newNumber");
		verifyNoMoreInteractions(mockCourt);
	}

	@Test
	public void testClaimCourtAsOwner() {
		ClaimKeyRequest mockRequest = spy(new ClaimKeyRequest());
		doReturn("uid").when(mockRequest).getUid();
		mockRequest.setClaimKey("testKey");
		Court response = mock(Court.class);
		CourtDO mockCourtDO = mock(CourtDO.class);
		when(mockCourtDO.getId()).thenReturn(1L);
		CourtClaimOTPDO courtClaimDO = mock(CourtClaimOTPDO.class);
		when(courtRepository.findById(1L)).thenReturn(Optional.of(mockCourtDO));
		when(courtClaimDO.getCourtId()).thenReturn(1L);
		when(courtClaimOTPRepository.findByClaimKey("testKey")).thenReturn(Optional.of(courtClaimDO));
		when(courtDOToResponseConverter.convert(mockCourtDO)).thenReturn(response);
		assertEquals(response, courtAndFieldService.claimCourtAsOwner(mockRequest));
		verify(courtUserRoleRepository, times(1)).insert(any(CourtUserRoleDO.class));
		verify(courtClaimOTPRepository, times(1)).deleteById(1L);
	}

	@Test
	public void testFindAllCourtManageByUser() {
		List<CourtUserRoleDO> courtUserRoleList = new ArrayList<>();
		CourtUserRoleDO courtUserRole1 = mock(CourtUserRoleDO.class);
		when(courtUserRole1.getCourtId()).thenReturn(1L);
		courtUserRoleList.add(courtUserRole1);
		CourtUserRoleDO courtUserRole2 = mock(CourtUserRoleDO.class);
		when(courtUserRole2.getCourtId()).thenReturn(2L);
		courtUserRoleList.add(courtUserRole2);
		when(courtUserRoleRepository.findByUid("testUid")).thenReturn(courtUserRoleList);
		Court court1 = mock(Court.class);
		CourtDO mockCourtDO1 = mock(CourtDO.class);
		when(courtDOToResponseConverter.convert(mockCourtDO1)).thenReturn(court1);
		Court court2 = mock(Court.class);
		CourtDO mockCourtDO2 = mock(CourtDO.class);
		List<CourtDO> courtDOList = new ArrayList<>();
		courtDOList.add(mockCourtDO1);
		courtDOList.add(mockCourtDO2);
		when(courtRepository.findByIdIn(Arrays.asList(1L, 2L))).thenReturn(courtDOList);
		when(courtDOToResponseConverter.convert(mockCourtDO2)).thenReturn(court2);
		CourtsResponse response = courtAndFieldService.findAllCourtManageByUser("testUid");
		assertEquals(court1, response.getCourts().get(0));
		assertEquals(court2, response.getCourts().get(1));
	}

	@Test
	public void testFindCourtByClaimKey() {
		CourtClaimOTPDO mockCourClaimOTP = mock(CourtClaimOTPDO.class);
		when(mockCourClaimOTP.getCourtId()).thenReturn(1L);
		when(courtClaimOTPRepository.findByClaimKey("testClaimKey")).thenReturn(Optional.of(mockCourClaimOTP));
		CourtDO mockCourtDO = mock(CourtDO.class);
		when(courtRepository.findById(1L)).thenReturn(Optional.of(mockCourtDO));
		Court response = mock(Court.class);
		when(courtDOToResponseConverter.convert(mockCourtDO)).thenReturn(response);
		assertEquals(response, courtAndFieldService.findCourtByClaimKey("testClaimKey"));
	}

	/* COURT MANAGEMENT TESTS END */



	/* FIELD MANAGEMENT TESTS START */

	@Test
	public void testAddFieldToCourt() {
		CreateFieldRequest fieldRequest = new CreateFieldRequest();
		FieldRequest request1 = new FieldRequest();
		request1.setName("testName1");
		request1.setFieldTypeId(123L);

		FieldRequest request2 = new FieldRequest();
		request2.setName("testName2");
		request2.setFieldTypeId(123L);

		FieldRequest request3 = new FieldRequest();
		request3.setName("testName3");
		request3.setFieldTypeId(456L);
		request3.setSubFieldIds(Arrays.asList(123L, 456L));

		fieldRequest.setFieldRequests(Arrays.asList(request1, request2, request3));

		CourtDO courtDO = spy(CourtDO.builder().build());
		when(courtRepository.findById(123L)).thenReturn(Optional.of(courtDO));
		when(courtRepository.save(courtDO)).thenReturn(courtDO);

		Court response = mock(Court.class);
		when(courtDOToResponseConverter.convert(courtDO)).thenReturn(response);
		assertEquals(response, courtAndFieldService.addFieldToCourt("123", fieldRequest));
		ArgumentCaptor<FieldDO> fieldCaptor = ArgumentCaptor.forClass(FieldDO.class);
		verify(courtDO, times(3)).addField(fieldCaptor.capture());
		List<FieldDO> fieldValue = fieldCaptor.getAllValues();
		assertEquals("testName1", fieldValue.get(0).getName());
		assertEquals("testName2", fieldValue.get(1).getName());
		assertEquals("testName3", fieldValue.get(2).getName());
		assertEquals(Long.valueOf(456), fieldValue.get(2).getFieldTypeId());
		assertEquals(Arrays.asList(123L, 456L), fieldValue.get(2).getSubFieldIds());
	}

	@Test
	public void testEditFieldCourtNotFound() {
		when(courtRepository.findById(1L)).thenReturn(Optional.empty());
		FieldRequest fieldRequest = new FieldRequest();
		assertNull(courtAndFieldService.editField("1", "1", fieldRequest));
	}

	@Test
	public void testEditFieldCourtFoundFieldNotFound() {
		CourtDO mockCourt = mock(CourtDO.class);
		when(mockCourt.findFieldById(1L)).thenReturn(Optional.empty());
		when(courtRepository.findById(1L)).thenReturn(Optional.of(mockCourt));
		FieldRequest fieldRequest = new FieldRequest();
		assertNull(courtAndFieldService.editField("1", "1", fieldRequest));
	}

	@Test
	public void testEditFieldName() {
		CourtDO mockCourt = mock(CourtDO.class);
		FieldDO mockField = mock(FieldDO.class);
		when(mockCourt.findFieldById(1L)).thenReturn(Optional.of(mockField));
		when(courtRepository.findById(1L)).thenReturn(Optional.of(mockCourt));
		FieldRequest fieldRequest = new FieldRequest();
		fieldRequest.setName("testName");
		Field expectField = mock(Field.class);
		when(fieldRepository.save(mockField)).thenReturn(mockField);
		when(fieldDOToResponseConverter.convert(mockField)).thenReturn(expectField);
		assertEquals(expectField, courtAndFieldService.editField("1", "1", fieldRequest));
		verify(mockField, times(1)).setName("testName");
		verifyNoMoreInteractions(mockField);
	}

	@Test
	public void testEditFieldFieldType() {
		CourtDO mockCourt = mock(CourtDO.class);
		FieldDO mockField = mock(FieldDO.class);
		when(mockCourt.findFieldById(1L)).thenReturn(Optional.of(mockField));
		when(courtRepository.findById(1L)).thenReturn(Optional.of(mockCourt));
		FieldRequest fieldRequest = new FieldRequest();
		fieldRequest.setFieldTypeId(Long.valueOf(123));
		Field expectField = mock(Field.class);
		when(fieldRepository.save(mockField)).thenReturn(mockField);
		when(fieldDOToResponseConverter.convert(mockField)).thenReturn(expectField);
		assertEquals(expectField, courtAndFieldService.editField("1", "1", fieldRequest));
		verify(mockField, times(1)).setFieldTypeId(Long.valueOf(123));
		verifyNoMoreInteractions(mockField);
	}

	@Test
	public void testEditFieldSubField() {
		CourtDO mockCourt = mock(CourtDO.class);
		FieldDO mockField = mock(FieldDO.class);
		when(mockCourt.findFieldById(1L)).thenReturn(Optional.of(mockField));
		when(courtRepository.findById(1L)).thenReturn(Optional.of(mockCourt));
		FieldRequest fieldRequest = new FieldRequest();
		fieldRequest.setSubFieldIds(Arrays.asList(Long.valueOf(123), Long.valueOf(121)));
		Field expectField = mock(Field.class);
		when(fieldRepository.save(mockField)).thenReturn(mockField);
		when(fieldDOToResponseConverter.convert(mockField)).thenReturn(expectField);
		assertEquals(expectField, courtAndFieldService.editField("1", "1", fieldRequest));
		verify(mockField, times(1)).setSubFieldIds(Arrays.asList(Long.valueOf(123), Long.valueOf(121)));
		verifyNoMoreInteractions(mockField);
	}

	@Test
	public void testDeleteFieldCourtNotFound() {
		when(courtRepository.findById(1L)).thenReturn(Optional.empty());
		assertNull(courtAndFieldService.deleteField("1", "1"));
	}

	@Test
	public void testDeleteFieldCourtFoundFieldNotFound() {
		CourtDO mockCourt = mock(CourtDO.class);
		when(mockCourt.findFieldById(1L)).thenReturn(Optional.empty());
		when(courtRepository.findById(1L)).thenReturn(Optional.of(mockCourt));
		assertNull(courtAndFieldService.deleteField("1", "1"));
	}

	@Test
	public void testDeleteFieldNormal() {
		CourtDO mockCourt = mock(CourtDO.class);
		FieldDO mockField = mock(FieldDO.class);
		when(mockField.getId()).thenReturn(Long.valueOf(1));
		List<FieldDO> fieldList = new ArrayList<>(Arrays.asList(mockField));
		when(mockCourt.getFields()).thenReturn(fieldList);
		when(mockCourt.findFieldById(1L)).thenReturn(Optional.of(mockField));
		when(courtRepository.findById(1L)).thenReturn(Optional.of(mockCourt));
		assertNull(courtAndFieldService.deleteField("1", "1"));
		verify(courtRepository, times(1)).save(mockCourt);
		assertTrue(fieldList.isEmpty());
	}

	/* FIELD MANAGEMENT TESTS END */

}
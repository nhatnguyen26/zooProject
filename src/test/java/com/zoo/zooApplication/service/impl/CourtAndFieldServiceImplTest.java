package com.zoo.zooApplication.service.impl;

import com.zoo.zooApplication.converter.CourtDOToResponseConverter;
import com.zoo.zooApplication.converter.FieldDOToResponseConverter;
import com.zoo.zooApplication.converter.FieldTypeDOToResponseConverter;
import com.zoo.zooApplication.dao.model.CourtClaimOTPDO;
import com.zoo.zooApplication.dao.model.CourtDO;
import com.zoo.zooApplication.dao.model.CourtUserRoleDO;
import com.zoo.zooApplication.dao.model.FieldDO;
import com.zoo.zooApplication.dao.model.FieldTypeDO;
import com.zoo.zooApplication.dao.model.PriceChartDO;
import com.zoo.zooApplication.dao.repository.CourtClaimOTPRepository;
import com.zoo.zooApplication.dao.repository.CourtRepository;
import com.zoo.zooApplication.dao.repository.CourtUserRoleRepository;
import com.zoo.zooApplication.dao.repository.FieldRepository;
import com.zoo.zooApplication.dao.repository.FieldTypeRepository;
import com.zoo.zooApplication.dao.repository.PriceChartRepository;
import com.zoo.zooApplication.request.ClaimKeyRequest;
import com.zoo.zooApplication.request.CreateCourtRequest;
import com.zoo.zooApplication.request.CreateFieldRequest;
import com.zoo.zooApplication.request.CreateFieldTypeRequest;
import com.zoo.zooApplication.request.FieldRequest;
import com.zoo.zooApplication.request.FieldTypeRequest;
import com.zoo.zooApplication.request.PriceChartRequest;
import com.zoo.zooApplication.response.Court;
import com.zoo.zooApplication.response.CourtsResponse;
import com.zoo.zooApplication.response.Field;
import com.zoo.zooApplication.response.FieldType;
import com.zoo.zooApplication.response.PriceChart;
import com.zoo.zooApplication.type.MainFieldTypeEnum;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
	private FieldTypeDOToResponseConverter fieldTypeDOToResponseConverter;

	private CourtAndFieldServiceImpl courtAndFieldService;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
		courtAndFieldService = new CourtAndFieldServiceImpl(courtRepository, courtClaimOTPRepository, fieldRepository,
			fieldTypeRepository,
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
	public void testDeleteCourt() {
		CourtDO mockCourt = mock(CourtDO.class);
		when(mockCourt.getId()).thenReturn(123L);
		when(courtRepository.findById(123L)).thenReturn(Optional.of(mockCourt));

		Court court = mock(Court.class);
		when(courtDOToResponseConverter.convert(mockCourt)).thenReturn(court);
		assertEquals(court, courtAndFieldService.deleteCourt("123"));
		verify(courtRepository, times(1)).delete(mockCourt);
		verify(courtClaimOTPRepository, times(1)).deleteByCourtId(Long.valueOf(123));
		verify(courtUserRoleRepository, times(1)).deleteByCourtId(Long.valueOf(123));
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

	@Test
	public void testFindClaimKeyByCourtId() {
		CourtClaimOTPDO mockCourClaimOTP = mock(CourtClaimOTPDO.class);
		when(courtClaimOTPRepository.findById(Long.valueOf(123))).thenReturn(Optional.of(mockCourClaimOTP));
		when(mockCourClaimOTP.getClaimKey()).thenReturn("testKey");
		assertEquals("testKey", courtAndFieldService.findClaimKeyByCourtId("123").getClaimKey());
	}

	/* COURT MANAGEMENT TESTS END */



	/* FIELD MANAGEMENT TESTS START */

	@Test
	public void testAddFieldToCourt() {
		CreateFieldRequest fieldRequest = new CreateFieldRequest();
		FieldRequest request1 = new FieldRequest();
		request1.setMainFieldType(MainFieldTypeEnum.SOCCER_5);
		request1.setName("testName1");
		request1.setFieldTypeId(123L);

		FieldRequest request2 = new FieldRequest();
		request2.setMainFieldType(MainFieldTypeEnum.SOCCER_7);
		request2.setName("testName2");
		request2.setFieldTypeId(123L);

		FieldRequest request3 = new FieldRequest();
		request3.setMainFieldType(MainFieldTypeEnum.SOCCER_11);
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
		assertEquals(MainFieldTypeEnum.SOCCER_5, fieldValue.get(0).getMainFieldType());
		assertEquals(MainFieldTypeEnum.SOCCER_7, fieldValue.get(1).getMainFieldType());
		assertEquals(MainFieldTypeEnum.SOCCER_11, fieldValue.get(2).getMainFieldType());
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
	public void testEditFieldMainFieldType() {
		CourtDO mockCourt = mock(CourtDO.class);
		FieldDO mockField = mock(FieldDO.class);
		when(mockCourt.findFieldById(1L)).thenReturn(Optional.of(mockField));
		when(courtRepository.findById(1L)).thenReturn(Optional.of(mockCourt));
		FieldRequest fieldRequest = new FieldRequest();
		fieldRequest.setMainFieldType(MainFieldTypeEnum.SOCCER_5);
		Field expectField = mock(Field.class);
		when(fieldRepository.save(mockField)).thenReturn(mockField);
		when(fieldDOToResponseConverter.convert(mockField)).thenReturn(expectField);
		assertEquals(expectField, courtAndFieldService.editField("1", "1", fieldRequest));
		verify(mockField, times(1)).setMainFieldType(MainFieldTypeEnum.SOCCER_5);
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

	/* FIELD TYPE MANAGEMENT TEST START */
	@Test
	public void testAddFieldTypeToCourt() {
		CreateFieldTypeRequest fieldTypeRequest = new CreateFieldTypeRequest();
		FieldTypeRequest request1 = new FieldTypeRequest();
		request1.setFieldTypeName("testName1");
		List<PriceChartRequest> priceChartReq1 = new ArrayList<>();
		request1.setPriceChartRequests(priceChartReq1);

		FieldTypeRequest request2 = new FieldTypeRequest();
		request2.setFieldTypeName("testName2");
		List<PriceChartRequest> priceChartReq2 = new ArrayList<>();
		PriceChartRequest priceChartReq = new PriceChartRequest();
		priceChartReq.setTimeStart(1000);
		priceChartReq.setTimeEnd(2000);
		priceChartReq.setPriceAmount(2000.0);
		priceChartReq2.add(priceChartReq);
		request2.setPriceChartRequests(priceChartReq2);


		fieldTypeRequest.setFieldTypeRequests(Arrays.asList(request1, request2));

		CourtDO courtDO = spy(CourtDO.builder().build());
		when(courtRepository.findById(123L)).thenReturn(Optional.of(courtDO));
		when(courtRepository.save(courtDO)).thenReturn(courtDO);

		Court response = mock(Court.class);
		when(courtDOToResponseConverter.convert(courtDO)).thenReturn(response);
		assertEquals(response, courtAndFieldService.addFieldTypeToCourt("123", fieldTypeRequest));
		ArgumentCaptor<FieldTypeDO> fieldTypeCaptor = ArgumentCaptor.forClass(FieldTypeDO.class);
		verify(courtDO, times(2)).addFieldType(fieldTypeCaptor.capture());
		List<FieldTypeDO> fieldValue = fieldTypeCaptor.getAllValues();
		assertEquals("testName1", fieldValue.get(0).getName());
		assertEquals("testName2", fieldValue.get(1).getName());
		assertEquals(0, fieldValue.get(0).getPriceCharts().size());
		assertEquals(1, fieldValue.get(1).getPriceCharts().size());
		assertEquals(Integer.valueOf(1000), fieldValue.get(1).getPriceCharts().get(0).getTimeStart());
		assertEquals(Integer.valueOf(2000), fieldValue.get(1).getPriceCharts().get(0).getTimeEnd());
		assertEquals(Double.valueOf(2000.0), fieldValue.get(1).getPriceCharts().get(0).getPriceAmount());
		assertEquals("VND", fieldValue.get(1).getPriceCharts().get(0).getCurrencyId());
	}

	@Test
	public void testEditFieldTypeCourtNotFound() {
		when(courtRepository.findById(1L)).thenReturn(Optional.empty());
		FieldTypeRequest fieldTypeRequest = new FieldTypeRequest();
		assertNull(courtAndFieldService.editFieldType("1", "1", fieldTypeRequest));
	}

	@Test
	public void testEditFieldTypeCourtFoundFieldTypeNotFound() {
		CourtDO mockCourt = mock(CourtDO.class);
		when(mockCourt.findFieldTypeById(1L)).thenReturn(Optional.empty());
		when(courtRepository.findById(1L)).thenReturn(Optional.of(mockCourt));
		FieldTypeRequest fieldTypeRequest = new FieldTypeRequest();
		assertNull(courtAndFieldService.editFieldType("1", "1", fieldTypeRequest));
	}

	@Test
	public void testEditFieldTypeName() {
		CourtDO mockCourt = mock(CourtDO.class);
		FieldTypeDO mockFieldType = mock(FieldTypeDO.class);
		when(mockCourt.findFieldTypeById(1L)).thenReturn(Optional.of(mockFieldType));
		when(courtRepository.findById(1L)).thenReturn(Optional.of(mockCourt));
		FieldTypeRequest fieldTypeRequest = new FieldTypeRequest();
		fieldTypeRequest.setFieldTypeName("testName");
		FieldType expectFieldType = mock(FieldType.class);
		when(fieldTypeRepository.save(mockFieldType)).thenReturn(mockFieldType);
		when(fieldTypeDOToResponseConverter.convert(mockFieldType)).thenReturn(expectFieldType);
		assertEquals(expectFieldType, courtAndFieldService.editFieldType("1", "1", fieldTypeRequest));
		verify(mockFieldType, times(1)).setName("testName");
		verifyNoMoreInteractions(mockFieldType);
	}

	@Test
	public void testEditFieldTypePriceChartAdd() {
		CourtDO mockCourt = mock(CourtDO.class);
		FieldTypeDO mockFieldType = mock(FieldTypeDO.class);
		List<PriceChartDO> priceChartDOList = new ArrayList<>();
		PriceChartDO priceChartDO = mock(PriceChartDO.class);
		priceChartDOList.add(priceChartDO);
		when(mockFieldType.getPriceCharts()).thenReturn(priceChartDOList);
		when(mockCourt.findFieldTypeById(1L)).thenReturn(Optional.of(mockFieldType));
		when(courtRepository.findById(1L)).thenReturn(Optional.of(mockCourt));
		FieldTypeRequest fieldTypeRequest = new FieldTypeRequest();
		List<PriceChartRequest> priceChartList = new ArrayList<>();
		PriceChartRequest priceChart1 = new PriceChartRequest();
		priceChart1.setTimeStart(1);
		priceChart1.setTimeEnd(2);
		priceChartList.add(priceChart1);
		PriceChartRequest priceChart2 = new PriceChartRequest();
		priceChart2.setTimeStart(2);
		priceChart2.setTimeEnd(4);
		priceChartList.add(priceChart2);
		fieldTypeRequest.setPriceChartRequests(priceChartList);
		FieldType expectFieldType = mock(FieldType.class);

		when(fieldTypeRepository.save(mockFieldType)).thenReturn(mockFieldType);
		when(fieldTypeDOToResponseConverter.convert(mockFieldType)).thenReturn(expectFieldType);
		assertEquals(expectFieldType, courtAndFieldService.editFieldType("1", "1", fieldTypeRequest));
		verify(mockFieldType, times(1)).getPriceCharts();
		verify(mockFieldType, times(1)).addPriceChart(any(PriceChartDO.class));
		verifyNoMoreInteractions(mockFieldType);
	}

	@Test
	public void testEditFieldTypePriceChartRemove() {
		CourtDO mockCourt = mock(CourtDO.class);
		FieldTypeDO mockFieldType = mock(FieldTypeDO.class);
		List<PriceChartDO> priceChartDOList = new ArrayList<>();
		priceChartDOList.add(mock(PriceChartDO.class));
		priceChartDOList.add(mock(PriceChartDO.class));
		when(mockFieldType.getPriceCharts()).thenReturn(priceChartDOList);
		when(mockCourt.findFieldTypeById(1L)).thenReturn(Optional.of(mockFieldType));
		when(courtRepository.findById(1L)).thenReturn(Optional.of(mockCourt));
		FieldTypeRequest fieldTypeRequest = new FieldTypeRequest();
		List<PriceChartRequest> priceChartList = new ArrayList<>();
		PriceChartRequest priceChart1 = new PriceChartRequest();
		priceChart1.setTimeStart(1);
		priceChart1.setTimeEnd(2);
		priceChartList.add(priceChart1);
		fieldTypeRequest.setPriceChartRequests(priceChartList);
		FieldType expectFieldType = mock(FieldType.class);

		when(fieldTypeRepository.save(mockFieldType)).thenReturn(mockFieldType);
		when(fieldTypeDOToResponseConverter.convert(mockFieldType)).thenReturn(expectFieldType);
		assertEquals(expectFieldType, courtAndFieldService.editFieldType("1", "1", fieldTypeRequest));
		verify(mockFieldType, times(1)).getPriceCharts();
		verifyNoMoreInteractions(mockFieldType);
		assertEquals(1, priceChartDOList.size());
	}

	@Test
	public void testEditFieldTypePriceChartEdit() {
		CourtDO mockCourt = mock(CourtDO.class);
		FieldTypeDO mockFieldType = mock(FieldTypeDO.class);
		List<PriceChartDO> priceChartDOList = new ArrayList<>();
		PriceChartDO priceChartDO1 = mock(PriceChartDO.class);
		priceChartDOList.add(priceChartDO1);
		PriceChartDO priceChartDO2 = mock(PriceChartDO.class);
		priceChartDOList.add(priceChartDO2);
		when(mockFieldType.getPriceCharts()).thenReturn(priceChartDOList);
		when(mockCourt.findFieldTypeById(1L)).thenReturn(Optional.of(mockFieldType));
		when(courtRepository.findById(1L)).thenReturn(Optional.of(mockCourt));
		FieldTypeRequest fieldTypeRequest = new FieldTypeRequest();
		List<PriceChartRequest> priceChartList = new ArrayList<>();
		PriceChartRequest priceChart1 = new PriceChartRequest();
		priceChart1.setTimeStart(1);
		priceChart1.setTimeEnd(2);
		priceChartList.add(priceChart1);
		PriceChartRequest priceChart2 = new PriceChartRequest();
		priceChart2.setTimeStart(2);
		priceChart2.setTimeEnd(4);
		priceChart2.setPriceAmount(2.0);
		priceChartList.add(priceChart2);
		fieldTypeRequest.setPriceChartRequests(priceChartList);
		FieldType expectFieldType = mock(FieldType.class);

		when(fieldTypeRepository.save(mockFieldType)).thenReturn(mockFieldType);
		when(fieldTypeDOToResponseConverter.convert(mockFieldType)).thenReturn(expectFieldType);
		assertEquals(expectFieldType, courtAndFieldService.editFieldType("1", "1", fieldTypeRequest));
		verify(mockFieldType, times(1)).getPriceCharts();
		verifyNoMoreInteractions(mockFieldType);
		verify(priceChartDO1).setTimeStart(Integer.valueOf(1));
		verify(priceChartDO1).setTimeEnd(Integer.valueOf(2));
		verify(priceChartDO1).setCurrencyId("VND");
		verify(priceChartDO2).setTimeStart(Integer.valueOf(2));
		verify(priceChartDO2).setTimeEnd(Integer.valueOf(4));
		verify(priceChartDO2).setPriceAmount(Double.valueOf(2.0));
		verify(priceChartDO2).setCurrencyId("VND");
	}

	@Test
	public void testDeleteFieldTypeCourtNotFound() {
		when(courtRepository.findById(1L)).thenReturn(Optional.empty());
		assertNull(courtAndFieldService.deleteFieldType("1", "1"));
	}

	@Test
	public void testDeleteFieldTypeCourtFoundFieldNotFound() {
		CourtDO mockCourt = mock(CourtDO.class);
		when(mockCourt.findFieldTypeById(1L)).thenReturn(Optional.empty());
		when(courtRepository.findById(1L)).thenReturn(Optional.of(mockCourt));
		assertNull(courtAndFieldService.deleteFieldType("1", "1"));
	}

	@Test
	public void testDeleteFieldTypeNormal() {
		CourtDO mockCourt = mock(CourtDO.class);
		FieldTypeDO mockFieldType = mock(FieldTypeDO.class);
		when(mockFieldType.getId()).thenReturn(Long.valueOf(1));
		List<FieldTypeDO> fieldTypeList = new ArrayList<>(Arrays.asList(mockFieldType));
		when(mockCourt.getFieldTypes()).thenReturn(fieldTypeList);
		when(mockCourt.findFieldTypeById(1L)).thenReturn(Optional.of(mockFieldType));
		when(courtRepository.findById(1L)).thenReturn(Optional.of(mockCourt));
		assertNull(courtAndFieldService.deleteFieldType("1", "1"));
		verify(courtRepository, times(1)).save(mockCourt);
		assertTrue(fieldTypeList.isEmpty());
	}

	/* FIELD TYPE MANAGEMENT TEST END */

}
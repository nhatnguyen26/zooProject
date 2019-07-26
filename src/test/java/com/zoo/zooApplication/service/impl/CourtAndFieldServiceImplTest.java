package com.zoo.zooApplication.service.impl;

import com.zoo.zooApplication.converter.CourtDOToResponseConverter;
import com.zoo.zooApplication.converter.FieldTypeDOToResponseConverter;
import com.zoo.zooApplication.dao.model.CourtDO;
import com.zoo.zooApplication.dao.model.FieldDO;
import com.zoo.zooApplication.dao.repository.CourtRepository;
import com.zoo.zooApplication.dao.repository.FieldRepository;
import com.zoo.zooApplication.dao.repository.FieldTypeRepository;
import com.zoo.zooApplication.dao.repository.PriceChartRepository;
import com.zoo.zooApplication.request.CreateCourtRequest;
import com.zoo.zooApplication.request.CreateFieldRequest;
import com.zoo.zooApplication.request.FieldRequest;
import com.zoo.zooApplication.response.Court;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    private FieldTypeRepository fieldTypeRepository;

    @Mock
    private PriceChartRepository priceChartRepository;

    @Mock
    private FieldTypeDOToResponseConverter fieldTypeDOToResponseConverter;

    private CourtAndFieldServiceImpl courtAndFieldService;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        courtAndFieldService = new CourtAndFieldServiceImpl(courtRepository, fieldRepository,
                fieldTypeRepository,priceChartRepository,
                courtDOToResponseConverter,fieldTypeDOToResponseConverter);
    }

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


}
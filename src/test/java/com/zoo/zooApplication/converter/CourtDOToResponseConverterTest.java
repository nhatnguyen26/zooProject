package com.zoo.zooApplication.converter;

import com.zoo.zooApplication.dao.model.CourtDO;
import com.zoo.zooApplication.dao.model.FieldDO;
import com.zoo.zooApplication.dao.model.FieldTypeDO;
import com.zoo.zooApplication.response.Court;
import com.zoo.zooApplication.response.Field;
import com.zoo.zooApplication.response.FieldType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CourtDOToResponseConverterTest {

    private CourtDOToResponseConverter testConverter;

    private CourtDO courtDO;

    private FieldDOToResponseConverter fieldDOToResponseConverter;

    private FieldTypeDOToResponseConverter fieldTypeDOToResponseConverter;

    @Before
    public void setUp() {
        fieldDOToResponseConverter = mock(FieldDOToResponseConverter.class);
        fieldTypeDOToResponseConverter = mock(FieldTypeDOToResponseConverter.class);
        testConverter = new CourtDOToResponseConverter(fieldDOToResponseConverter, fieldTypeDOToResponseConverter);
        courtDO = mock(CourtDO.class);
    }

    @Test(expected = NullPointerException.class)
    public void testConvertWithNull() {
        testConverter.convert(null);
    }

    @Test
    public void testConvertWithCourtId() {
        when(courtDO.getId()).thenReturn(Long.valueOf(123));
        Court court = testConverter.convert(courtDO);
        assertEquals(Long.valueOf(123), court.getId());
    }

    @Test
    public void testConvertWithCourtName() {
        when(courtDO.getName()).thenReturn("name");
        Court court = testConverter.convert(courtDO);
        assertEquals("name", court.getName());
    }

    @Test
    public void testConvertWithCourtAddressStreet() {
        when(courtDO.getAddressStreet()).thenReturn("123 Test");
        Court court = testConverter.convert(courtDO);
        assertEquals("123 Test", court.getAddressStreet());
    }

    @Test
    public void testConvertWithCourtAddressWard() {
        when(courtDO.getAddressWard()).thenReturn("Phuong 15");
        Court court = testConverter.convert(courtDO);
        assertEquals("Phuong 15", court.getAddressWard());
    }

    @Test
    public void testConvertWithCourtAddressDistrict() {
        when(courtDO.getAddressDistrict()).thenReturn("Quan 1");
        Court court = testConverter.convert(courtDO);
        assertEquals("Quan 1", court.getAddressDistrict());
    }

    @Test
    public void testConvertWithCourtAddressCity() {
        when(courtDO.getAddressCity()).thenReturn("TP Ho Chi Minh");
        Court court = testConverter.convert(courtDO);
        assertEquals("TP Ho Chi Minh", court.getAddressCity());
    }

    @Test
    public void testConvertWithCourtAddressCountry() {
        when(courtDO.getAddressCountry()).thenReturn("VN");
        Court court = testConverter.convert(courtDO);
        assertEquals("VN", court.getAddressCountry());
    }

    @Test
    public void testConvertWithCourtPhoneNumber() {
        when(courtDO.getPhoneNumber()).thenReturn("123456");
        Court court = testConverter.convert(courtDO);
        assertEquals("123456", court.getPhoneNumber());
    }

    @Test
    public void testConvertWithEmptyField() {
        when(courtDO.getFields()).thenReturn(new LinkedHashSet<>());
        Court court = testConverter.convert(courtDO);
        assertTrue(court.getFields().isEmpty());
    }

    @Test
    public void testConvertWithSomeFields() {
        Set<FieldDO> mockList = new LinkedHashSet<>();
        FieldDO firstDO = mock(FieldDO.class);
        mockList.add(firstDO);
        FieldDO secondDO = mock(FieldDO.class);
        mockList.add(secondDO);

        List<Field> expectList = new ArrayList<>();
        Field firstField = mock(Field.class);
        expectList.add(firstField);
        Field secondField = mock(Field.class);
        expectList.add(secondField);

        when(fieldDOToResponseConverter.convert(firstDO)).thenReturn(firstField);
        when(fieldDOToResponseConverter.convert(secondDO)).thenReturn(secondField);

        when(courtDO.getFields()).thenReturn(mockList);
        Court court = testConverter.convert(courtDO);
        assertEquals(expectList, court.getFields());
    }

    @Test
    public void testConvertWithEmptyFieldType() {
        when(courtDO.getFields()).thenReturn(new LinkedHashSet<>());
        Court court = testConverter.convert(courtDO);
        assertTrue(court.getFieldTypes().isEmpty());
    }

    @Test
    public void testConvertWithSomeFieldTypes() {
        Set<FieldTypeDO> mockList = new LinkedHashSet<>();
        FieldTypeDO firstDO = mock(FieldTypeDO.class);
        mockList.add(firstDO);
        FieldTypeDO secondDO = mock(FieldTypeDO.class);
        mockList.add(secondDO);

        List<FieldType> expectList = new ArrayList<>();
        FieldType firstFieldType = mock(FieldType.class);
        expectList.add(firstFieldType);
        FieldType secondFieldType = mock(FieldType.class);
        expectList.add(secondFieldType);

        when(fieldTypeDOToResponseConverter.convert(firstDO)).thenReturn(firstFieldType);
        when(fieldTypeDOToResponseConverter.convert(secondDO)).thenReturn(secondFieldType);

        when(courtDO.getFieldTypes()).thenReturn(mockList);
        Court court = testConverter.convert(courtDO);
        assertEquals(expectList, court.getFieldTypes());
    }

}
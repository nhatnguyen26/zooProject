package com.zoo.zooApplication.converter;

import com.zoo.zooApplication.dao.model.FieldBookingDO;
import com.zoo.zooApplication.response.FieldBooking;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FieldBookingDOToResponseConverterTest {

    private FieldBookingDOToResponseConverter testConverter;

    private FieldBookingDO mockDO;

    @Before
    public void setUp() {
        testConverter = new FieldBookingDOToResponseConverter();
        mockDO = mock(FieldBookingDO.class);
    }

    @Test(expected = NullPointerException.class)
    public void testConvertWithNull() {
        testConverter.convert(null);
    }

    @Test
    public void testConvertWithId() {
        when(mockDO.getId()).thenReturn(Long.valueOf(123));
        FieldBooking fieldBooking = testConverter.convert(mockDO);
        assertEquals(123l, fieldBooking.getId());
    }

    @Test
    public void testConvertWithFieldId() {
        when(mockDO.getFieldId()).thenReturn(Long.valueOf(123));
        FieldBooking fieldBooking = testConverter.convert(mockDO);
        assertEquals(123l, fieldBooking.getFieldId());
    }

    @Test
    public void testConvertWithCourtId() {
        when(mockDO.getCourtId()).thenReturn(Long.valueOf(123));
        FieldBooking fieldBooking = testConverter.convert(mockDO);
        assertEquals(123l, fieldBooking.getCourtId());
    }

}
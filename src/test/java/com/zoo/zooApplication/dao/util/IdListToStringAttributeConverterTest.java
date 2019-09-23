package com.zoo.zooApplication.dao.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class IdListToStringAttributeConverterTest {

    @Test
    public void testToDatabaseColumn() {
        IdListToStringAttributeConverter converter = new IdListToStringAttributeConverter();
        String result = converter.convertToDatabaseColumn(Arrays.asList(123L, 456L));
        assertEquals("123,456", result);
    }

    @Test
    public void testToDatabaseColumnEmpty() {
        IdListToStringAttributeConverter converter = new IdListToStringAttributeConverter();
        String result = converter.convertToDatabaseColumn(new ArrayList<>());
        assertNull(result);
    }

    @Test
    public void testToEntityAttribute() {
        IdListToStringAttributeConverter converter = new IdListToStringAttributeConverter();
        List<Long> result = converter.convertToEntityAttribute("123,456");
        assertEquals(Arrays.asList(123L, 456L), result);
    }

    @Test
    public void testToEntityAttributeNull() {
        IdListToStringAttributeConverter converter = new IdListToStringAttributeConverter();
        List<Long> result = converter.convertToEntityAttribute(null);
        assertTrue(result.isEmpty());
    }
}
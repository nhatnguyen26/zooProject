package com.zoo.zooApplication.dao.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class IdSetToStringAttributeConverterTest {

    @Test
    public void testToDatabaseColumn() {
        IdSetToStringAttributeConverter converter = new IdSetToStringAttributeConverter();
        Set<Long> dataSet = new LinkedHashSet<>();
        dataSet.add(123L);
        dataSet.add(456L);
        String result = converter.convertToDatabaseColumn(dataSet);
        assertEquals("123,456", result);
    }

    @Test
    public void testToDatabaseColumnEmpty() {
        IdSetToStringAttributeConverter converter = new IdSetToStringAttributeConverter();
        String result = converter.convertToDatabaseColumn(new HashSet<>());
        assertNull(result);
    }

    @Test
    public void testToEntityAttribute() {
        IdSetToStringAttributeConverter converter = new IdSetToStringAttributeConverter();
        Set<Long> result = converter.convertToEntityAttribute("123,456");
        assertEquals(Set.of(123L, 456L), result);
    }

    @Test
    public void testToEntityAttributeNull() {
        IdSetToStringAttributeConverter converter = new IdSetToStringAttributeConverter();
        Set<Long> result = converter.convertToEntityAttribute(null);
        assertTrue(result.isEmpty());
    }
}
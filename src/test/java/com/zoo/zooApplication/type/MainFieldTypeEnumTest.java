package com.zoo.zooApplication.type;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainFieldTypeEnumTest {

    @Test
    public void testGetFromId() {
        assertNull(MainFieldTypeEnum.getFromId(0));
        for (MainFieldTypeEnum value : MainFieldTypeEnum.values()) {
            assertEquals(value, MainFieldTypeEnum.getFromId(value.getId()));
        }
    }

    @Test
    public void canCombineFromType() {
        assertFalse(MainFieldTypeEnum.SOCCER_5.canCombineFromType(MainFieldTypeEnum.SOCCER_5));
        assertTrue(MainFieldTypeEnum.SOCCER_7.canCombineFromType(MainFieldTypeEnum.SOCCER_5));
        assertFalse(MainFieldTypeEnum.SOCCER_7.canCombineFromType(MainFieldTypeEnum.SOCCER_11));
    }
}
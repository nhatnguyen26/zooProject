package com.zoo.zooApplication.dao.util;

import com.zoo.zooApplication.type.MainFieldTypeEnum;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainFieldTypeEnumConverterTest {

	@Test
	public void testConvertToDatabaseColumnNull() {
		MainFieldTypeEnumConverter converter = new MainFieldTypeEnumConverter();
		assertEquals(Integer.valueOf(-1), converter.convertToDatabaseColumn(null));
	}

	@Test
	public void testConvertToDatabaseColumnNormal() {
		MainFieldTypeEnumConverter converter = new MainFieldTypeEnumConverter();
		for (MainFieldTypeEnum value : MainFieldTypeEnum.values()) {
			assertEquals(Integer.valueOf(value.getId()), converter.convertToDatabaseColumn(value));
		}
	}

	@Test
	public void testConvertToEntityAttributeInvalidValue() {
		MainFieldTypeEnumConverter converter = new MainFieldTypeEnumConverter();
		assertNull(converter.convertToEntityAttribute(-1));
		assertNull(converter.convertToEntityAttribute(0));
	}

	@Test
	public void testConvertToEntityAttribute() {
		MainFieldTypeEnumConverter converter = new MainFieldTypeEnumConverter();
		for (MainFieldTypeEnum value : MainFieldTypeEnum.values()) {
			assertEquals(value, converter.convertToEntityAttribute(value.getId()));
		}
	}

	@Test
	public void testConvertToEntityAttributeNull() {
		MainFieldTypeEnumConverter converter = new MainFieldTypeEnumConverter();
		assertNull(converter.convertToEntityAttribute(null));
	}
}
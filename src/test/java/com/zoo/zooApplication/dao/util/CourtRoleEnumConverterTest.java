package com.zoo.zooApplication.dao.util;

import com.zoo.zooApplication.type.CourtRoleEnum;
import com.zoo.zooApplication.type.MainFieldTypeEnum;
import org.junit.Test;

import static org.junit.Assert.*;

public class CourtRoleEnumConverterTest {

	@Test
	public void testConvertToDatabaseColumnNormal() {
		CourtRoleEnumConverter converter = new CourtRoleEnumConverter();
		assertEquals(Integer.valueOf(1), converter.convertToDatabaseColumn(CourtRoleEnum.OWNER));
	}

	@Test
	public void testConvertToDatabaseColumnNull() {
		CourtRoleEnumConverter converter = new CourtRoleEnumConverter();
		assertEquals(Integer.valueOf(-1), converter.convertToDatabaseColumn(null));
	}

	@Test
	public void testConvertToEntityAttributeInvalidValue() {
		CourtRoleEnumConverter converter = new CourtRoleEnumConverter();
		assertNull(converter.convertToEntityAttribute(-1));
		assertNull(converter.convertToEntityAttribute(0));
	}

	@Test
	public void testConvertToEntityAttribute() {
		CourtRoleEnumConverter converter = new CourtRoleEnumConverter();
		for (CourtRoleEnum value : CourtRoleEnum.values()) {
			assertEquals(value, converter.convertToEntityAttribute(value.getId()));
		}
	}

	@Test
	public void testConvertToEntityAttributeNull() {
		CourtRoleEnumConverter converter = new CourtRoleEnumConverter();
		assertNull(converter.convertToEntityAttribute(null));
	}
}
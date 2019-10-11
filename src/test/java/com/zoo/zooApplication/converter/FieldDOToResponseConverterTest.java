package com.zoo.zooApplication.converter;

import com.zoo.zooApplication.dao.model.FieldDO;
import com.zoo.zooApplication.response.Field;
import com.zoo.zooApplication.response.FieldResponse;
import com.zoo.zooApplication.type.MainFieldTypeEnum;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FieldDOToResponseConverterTest {

	private FieldDOToResponseConverter testConverter;

	private FieldDO fieldDO;

	@Before
	public void setUp() {
		testConverter = new FieldDOToResponseConverter();
		fieldDO = mock(FieldDO.class);
	}

	@Test(expected = NullPointerException.class)
	public void testConvertWithNull() {
		testConverter.convert((FieldDO) null);
		testConverter.convert((List<FieldDO>) null);
	}

	@Test
	public void testConvertWithFieldId() {
		when(fieldDO.getId()).thenReturn(Long.valueOf(123));
		Field field = testConverter.convert(fieldDO);
		assertEquals(Long.valueOf(123), field.getId());
	}

	@Test
	public void testConvertWithFieldName() {
		when(fieldDO.getName()).thenReturn("name");
		Field field = testConverter.convert(fieldDO);
		assertEquals("name", field.getName());
	}

	@Test
	public void testConvertWithMainFieldType() {
		when(fieldDO.getMainFieldType()).thenReturn(MainFieldTypeEnum.SOCCER_5);
		Field field = testConverter.convert(fieldDO);
		assertEquals(MainFieldTypeEnum.SOCCER_5, field.getMainFieldType());
	}

	@Test
	public void testConvertWithFieldTypeId() {
		when(fieldDO.getFieldTypeId()).thenReturn(Long.valueOf(123));
		Field field = testConverter.convert(fieldDO);
		assertEquals(Long.valueOf(123), field.getFieldTypeId());
	}

	@Test
	public void testConvertWithSubFieldIds() {
		when(fieldDO.getSubFieldIds()).thenReturn(Arrays.asList(123L, 456L));
		Field field = testConverter.convert(fieldDO);
		assertEquals(Arrays.asList(123L, 456L), field.getSubFieldIds());
	}

	@Test
	public void testConvertResponse() {
		List<FieldDO> fieldDOList = LongStream
			.range(1, 10)
			.boxed()
			.map(id -> FieldDO.builder().id(id).build())
			.collect(Collectors.toList());
		FieldResponse response = testConverter.convert(fieldDOList);
		LongStream
			.range(1, 10)
			.boxed()
			.forEach(id -> assertEquals(id, response.getFields().get(id.intValue() - 1).getId()));
	}

}
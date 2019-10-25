package com.zoo.zooApplication.converter;

import com.zoo.zooApplication.dao.model.CourtDO;
import com.zoo.zooApplication.dao.model.FieldDO;
import com.zoo.zooApplication.response.Field;
import com.zoo.zooApplication.response.FieldResponse;
import com.zoo.zooApplication.type.MainFieldTypeEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FieldDOToResponseConverterTest {

	private FieldDOToResponseConverter testConverter;

	private FieldDO fieldDO;

	private CourtDO courtDO;

	@Before
	public void setUp() {
		testConverter = new FieldDOToResponseConverter();
		fieldDO = mock(FieldDO.class);
		courtDO = mock(CourtDO.class);
	}

	@Test(expected = NullPointerException.class)
	public void testConvertWithNullDO() {
		testConverter.convert((FieldDO) null);
	}

	@Test(expected = NullPointerException.class)
	public void testConvertWithListDO() {
		testConverter.convert((List<FieldDO>) null);
	}

	@Test(expected = NullPointerException.class)
	public void testConvertResponseWithNullList() {
		testConverter.convertToResponse(null);
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
		Set<Long> subIdSet = new LinkedHashSet<>(Arrays.asList(123L, 456L));
		when(fieldDO.getSubFieldIds()).thenReturn(subIdSet);
		Field field = testConverter.convert(fieldDO);
		assertEquals(Set.of(123L, 456L), new HashSet<>(field.getSubFieldIds()));
	}

	@Test
	public void testCovertWithPartOfFieldIdsFromSingleFieldVerifyOneLevelOnly() {
		when(fieldDO.getCourt()).thenReturn(courtDO);
		when(fieldDO.getId()).thenReturn(1L);
		FieldDO fieldDO1 = createFieldDO(3L, Set.of(1L, 2L));
		FieldDO fieldDo2 = createFieldDO(2L, null);
		FieldDO fieldDo3 = createFieldDO(4L, Set.of(3L, 2L));
		Set<FieldDO> fieldSet = Set.of(fieldDO, fieldDO1, fieldDo2, fieldDo3);
		when(courtDO.getFields()).thenReturn(fieldSet);
		Field field = testConverter.convert(fieldDO);
		assertEquals(Set.of(3L), new HashSet<>(field.getPartOfFieldIds()));
	}

	@Test
	public void testCovertWithPartOfFieldIdsFromSingleFieldMultiplePartOf() {
		when(fieldDO.getCourt()).thenReturn(courtDO);
		when(fieldDO.getId()).thenReturn(1L);
		FieldDO fieldDO1 = createFieldDO(3L, Set.of(1L, 2L));
		FieldDO fieldDo2 = createFieldDO(2L, null);
		FieldDO fieldDo3 = createFieldDO(4L, Set.of(1L, 5L));
		Set<FieldDO> fieldSet = Set.of(fieldDO, fieldDO1, fieldDo2, fieldDo3);
		when(courtDO.getFields()).thenReturn(fieldSet);
		Field field = testConverter.convert(fieldDO);
		assertEquals(Set.of(3L, 4L), new HashSet<>(field.getPartOfFieldIds()));
	}

	@Test
	public void testConvertFieldWithCoBlockAllGrandPartOf() {
		when(fieldDO.getCourt()).thenReturn(courtDO);
		when(fieldDO.getId()).thenReturn(1L);
		FieldDO fieldDO2 = createFieldDO(2L, null);
		FieldDO fieldDo3 = createFieldDO(3L, Set.of(1L, 2L));
		FieldDO fieldDo4 = createFieldDO(4L, null);
		FieldDO fieldDo5 = createFieldDO(5L, Set.of(3L, 4L));
		FieldDO fieldDo6 = createFieldDO(6L, null);
		FieldDO fieldDo7 = createFieldDO(7L, Set.of(5L, 6L));
		Set<FieldDO> fieldSet = Set.of(fieldDO, fieldDO2, fieldDo3, fieldDo4, fieldDo5, fieldDo6, fieldDo7);
		when(courtDO.getFields()).thenReturn(fieldSet);
		Field field = testConverter.convert(fieldDO);
		assertEquals(Set.of(3L, 5L, 7L), new HashSet<>(field.getCoBlockingFieldIds()));
	}

	@Test
	public void testConvertFieldWithCoBlockAllGrandPartOfHandlingIfHasCycle() {
		when(fieldDO.getCourt()).thenReturn(courtDO);
		when(fieldDO.getId()).thenReturn(1L);
		when(fieldDO.getSubFieldIds()).thenReturn(Set.of(3L));
		// naturally there should be as least 2 sub field, but one will work to prove the cycle handling
		FieldDO fieldDO2 = createFieldDO(2L, Set.of(1L));
		FieldDO fieldDo3 = createFieldDO(3L, Set.of(2L));
		FieldDO fieldDo4 = createFieldDO(4L, Set.of(1L));
		Set<FieldDO> fieldSet = Set.of(fieldDO, fieldDO2, fieldDo3, fieldDo4);
		when(courtDO.getFields()).thenReturn(fieldSet);
		Field field = testConverter.convert(fieldDO);
		assertEquals(Set.of(2L, 3L, 4L), new HashSet<>(field.getCoBlockingFieldIds()));
	}

	@Test
	public void testConvertAllFieldWithCoBlockHasCycle() {
		// safe case but in practice, there should be no cycle
		when(fieldDO.getId()).thenReturn(1L);
		when(fieldDO.getSubFieldIds()).thenReturn(Set.of(3L));
		// naturally there should be as least 2 sub field, but one will work to prove the cycle handling
		FieldDO fieldDO2 = createFieldDO(2L, Set.of(1L));
		FieldDO fieldDo3 = createFieldDO(3L, Set.of(2L));
		FieldDO fieldDo4 = createFieldDO(4L, Set.of(1L));
		Set<FieldDO> fieldSet = Set.of(fieldDO, fieldDO2, fieldDo3, fieldDo4);
		List<Field> fieldList = testConverter.convert(fieldSet);
		// sort by id for assertion
		Collections.sort(fieldList, Comparator.comparing(Field::getId));
		assertEquals(Set.of(2L, 3L, 4L), new HashSet<>(fieldList.get(0).getCoBlockingFieldIds()));
		assertEquals(Set.of(1L, 3L, 4L), new HashSet<>(fieldList.get(1).getCoBlockingFieldIds()));
		assertEquals(Set.of(1L, 2L, 4L), new HashSet<>(fieldList.get(2).getCoBlockingFieldIds()));
		assertEquals(Set.of(1L, 2L, 3L), new HashSet<>(fieldList.get(3).getCoBlockingFieldIds()));
	}

	@Test
	public void testConvertAllField() {
		// naturally there should be as least 2 sub field, but one will work to prove the cycle handling
		FieldDO fieldDO1 = createFieldDO(1L, null);
		FieldDO fieldDO2 = createFieldDO(2L, null);
		FieldDO fieldDo3 = createFieldDO(3L, null);
		FieldDO fieldDo4 = createFieldDO(4L, null);
		FieldDO fieldDo5 = createFieldDO(5L, Set.of(1L, 2L));
		FieldDO fieldDo6 = createFieldDO(6L, Set.of(2L, 3L));
		FieldDO fieldDo7 = createFieldDO(7L, Set.of(4L, 6L));
		Set<FieldDO> fieldSet = Set.of(fieldDO1, fieldDO2, fieldDo3, fieldDo4, fieldDo5, fieldDo6, fieldDo7);
		List<Field> fieldList = testConverter.convert(fieldSet);
		// sort by id for assertion
		Collections.sort(fieldList, Comparator.comparing(Field::getId));
		assertEquals(Set.of(5L), new HashSet<>(fieldList.get(0).getCoBlockingFieldIds()));
		assertEquals(Set.of(5L, 6L, 7L), new HashSet<>(fieldList.get(1).getCoBlockingFieldIds()));
		assertEquals(Set.of(6L, 7L), new HashSet<>(fieldList.get(2).getCoBlockingFieldIds()));
		assertEquals(Set.of(7L), new HashSet<>(fieldList.get(3).getCoBlockingFieldIds()));
		assertEquals(Set.of(1L, 2L, 6L, 7L), new HashSet<>(fieldList.get(4).getCoBlockingFieldIds()));
		assertEquals(Set.of(2L, 3L, 5L, 7L), new HashSet<>(fieldList.get(5).getCoBlockingFieldIds()));
		assertEquals(Set.of(2L, 3L, 4L, 5L, 6L), new HashSet<>(fieldList.get(6).getCoBlockingFieldIds()));
	}

	@Test
	public void testConvertFieldWithCoBlockAllGrandPartOfAndAllSubWithAllTheirGrandPartOfNoGrandSubField() {
		when(fieldDO.getCourt()).thenReturn(courtDO);
		FieldDO fieldDO1 = createFieldDO(1L, null);
		FieldDO fieldDO2 = createFieldDO(2L, null);
		FieldDO fieldDo3 = createFieldDO(3L, Set.of(1L, 2L));
		FieldDO fieldDo4 = createFieldDO(4L, null);
		FieldDO fieldDo5 = createFieldDO(5L, Set.of(3L, 4L));
		FieldDO fieldDo6 = createFieldDO(6L, null);
		FieldDO fieldDo7 = createFieldDO(7L, Set.of(5L, 6L));
		FieldDO fieldDo8 = createFieldDO(8L, null);
		when(fieldDO.getId()).thenReturn(9L);
		when(fieldDO.getSubFieldIds()).thenReturn(Set.of(1L, 8L));
		Set<FieldDO> fieldSet = Set.of(fieldDO, fieldDO1, fieldDO2, fieldDo3, fieldDo4, fieldDo5, fieldDo6, fieldDo7, fieldDo8);
		when(courtDO.getFields()).thenReturn(fieldSet);
		Field field = testConverter.convert(fieldDO);
		assertEquals(Set.of(1L,3L, 5L, 7L, 8L), new HashSet<>(field.getCoBlockingFieldIds()));
	}

	private FieldDO createFieldDO(Long id, Set<Long> subField) {
		FieldDO fieldDOMock = mock(FieldDO.class);
		when(fieldDOMock.getId()).thenReturn(id);
		if (CollectionUtils.isNotEmpty(subField)) {
			when(fieldDOMock.getSubFieldIds()).thenReturn(subField);
		}
		return fieldDOMock;
	}

	@Test
	public void testConvertResponse() {
		List<FieldDO> fieldDOList = LongStream
			.range(1, 10)
			.boxed()
			.map(id -> FieldDO.builder().id(id).subFieldIds(new LinkedHashSet<>()).build())
			.collect(Collectors.toList());
		FieldResponse response = testConverter.convertToResponse(fieldDOList);
		LongStream
			.range(1, 10)
			.boxed()
			.forEach(id -> assertEquals(id, response.getFields().get(id.intValue() - 1).getId()));
	}

}
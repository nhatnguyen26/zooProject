package com.zoo.zooApplication.dao.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CourtDOTest {

	@Test
	public void testAddField() {
		CourtDO courtDO = CourtDO.builder().id(1L).build();
		FieldDO mockFieldDO = mock(FieldDO.class);
		assertEquals(courtDO, courtDO.addField(mockFieldDO));
		assertEquals(Arrays.asList(new FieldDO[]{mockFieldDO}), courtDO.getFields());
		verify(mockFieldDO, times(1)).setCourtId(1L);
	}

	@Test
	public void testFindFieldById() {
		CourtDO courtDO = new CourtDO();
		FieldDO mockFieldDO = mock(FieldDO.class);
		when(mockFieldDO.getId()).thenReturn(Long.valueOf(1));
		courtDO.addField(mockFieldDO);
		Optional<FieldDO> optionalFieldDO = courtDO.findFieldById(Long.valueOf(1));
		assertEquals(mockFieldDO, optionalFieldDO.get());
	}

	@Test
	public void testAddFieldType() {
		CourtDO courtDO = CourtDO.builder().id(1L).build();
		FieldTypeDO mockFieldTypeDO = mock(FieldTypeDO.class);
		assertEquals(courtDO, courtDO.addFieldType(mockFieldTypeDO));
		assertEquals(Arrays.asList(new FieldTypeDO[]{mockFieldTypeDO}), courtDO.getFieldTypes());
		verify(mockFieldTypeDO, times(1)).setCourtId(1L);
	}

	@Test
	public void testFindFieldTypeById() {
		CourtDO courtDO = new CourtDO();
		FieldTypeDO mockFieldTypeDO = mock(FieldTypeDO.class);
		when(mockFieldTypeDO.getId()).thenReturn(Long.valueOf(1));
		courtDO.addFieldType(mockFieldTypeDO);
		Optional<FieldTypeDO> optionalFieldDO = courtDO.findFieldTypeById(Long.valueOf(1));
		assertEquals(mockFieldTypeDO, optionalFieldDO.get());

	}
}
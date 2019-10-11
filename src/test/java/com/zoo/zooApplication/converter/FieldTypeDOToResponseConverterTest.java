package com.zoo.zooApplication.converter;

import com.zoo.zooApplication.dao.model.FieldTypeDO;
import com.zoo.zooApplication.dao.model.PriceChartDO;
import com.zoo.zooApplication.response.FieldType;
import com.zoo.zooApplication.response.FieldTypeResponse;
import com.zoo.zooApplication.response.PriceChart;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FieldTypeDOToResponseConverterTest {

	private FieldTypeDOToResponseConverter testConverter;

	private PriceChartDOToResponseConverter priceChartDOToResponseConverter;

	private FieldTypeDO mockDO;

	@Before
	public void setUp() {
		priceChartDOToResponseConverter = mock(PriceChartDOToResponseConverter.class);
		testConverter = new FieldTypeDOToResponseConverter(priceChartDOToResponseConverter);
		mockDO = mock(FieldTypeDO.class);
	}

	@Test(expected = NullPointerException.class)
	public void testConvertWithNull() {
		testConverter.convert((FieldTypeDO) null);
		testConverter.convert((List<FieldTypeDO>) null);
	}

	@Test
	public void testConvertVerifyId() {
		when(mockDO.getId()).thenReturn(123L);
		FieldType fieldType = testConverter.convert(mockDO);
		assertEquals(Long.valueOf(123L), fieldType.getId());
	}

	@Test
	public void testConvertVerifyName() {
		when(mockDO.getName()).thenReturn("test");
		FieldType fieldType = testConverter.convert(mockDO);
		assertEquals("test", fieldType.getName());
	}

	@Test
	public void testConvertWithPriceChart() {
		List<PriceChartDO> priceChartList = new ArrayList<>();
		priceChartList.add(mock(PriceChartDO.class));
		priceChartList.add(mock(PriceChartDO.class));

		List<PriceChart> expectList = new ArrayList<>();
		expectList.add(mock(PriceChart.class));
		expectList.add(mock(PriceChart.class));

		for (int i = 0; i < 2; i++) {
			when(priceChartDOToResponseConverter.convert(priceChartList.get(i))).thenReturn(expectList.get(i));
		}

		when(mockDO.getPriceCharts()).thenReturn(priceChartList);
		FieldType fieldType = testConverter.convert(mockDO);
		assertEquals(expectList, fieldType.getPriceCharts());
	}

	@Test
	public void testConvertResponse() {
		List<FieldTypeDO> fieldTypeDOList = LongStream
			.range(1, 10)
			.boxed()
			.map(id -> FieldTypeDO.builder().id(id).build())
			.collect(Collectors.toList());
		FieldTypeResponse response = testConverter.convert(fieldTypeDOList);
		LongStream
			.range(1, 10)
			.boxed()
			.forEach(id -> assertEquals(id, response.getFieldTypes().get(id.intValue() - 1).getId()));
	}

}
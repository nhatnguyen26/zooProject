package com.zoo.zooApplication.converter;

import com.zoo.zooApplication.dao.model.FieldTypeDO;
import com.zoo.zooApplication.dao.model.PriceChartDO;
import com.zoo.zooApplication.response.FieldType;
import com.zoo.zooApplication.response.PriceChart;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        assertEquals(123L, fieldType.getId());
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

}
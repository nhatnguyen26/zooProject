package com.zoo.zooApplication.converter;

import com.zoo.zooApplication.dao.model.PriceChartDO;
import com.zoo.zooApplication.response.Court;
import com.zoo.zooApplication.response.Field;
import com.zoo.zooApplication.response.PriceChart;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PriceChartDOToResponseConverterTest {

    private PriceChartDOToResponseConverter testConverter;

    private PriceChartDO priceChartDO;

    @Before
    public void setUp(){
        testConverter = new PriceChartDOToResponseConverter();
        priceChartDO = mock(PriceChartDO.class);
    }

    @Test(expected = NullPointerException.class)
    public void testConverterWithNull(){
        testConverter.convert(null);
    }

    @Test
    public void testConvertWithPriceAmount() {
        when(priceChartDO.getPriceAmount()).thenReturn(Double.valueOf(123));
        PriceChart priceChart = testConverter.convert(priceChartDO);
        assertEquals(Double.valueOf(123), priceChart.getPriceAmount());
    }

    @Test
    public void testConvertWithCurrencyId() {
        when(priceChartDO.getCurrencyId()).thenReturn("VND");
        PriceChart priceChart = testConverter.convert(priceChartDO);
        assertEquals("VND", priceChart.getCurrencyId());
    }

    @Test
    public void testConvertWithTimeStart() {
        when(priceChartDO.getTimeStart()).thenReturn(Integer.valueOf(123));
        PriceChart priceChart = testConverter.convert(priceChartDO);
        assertEquals(Integer.valueOf(123), priceChart.getTimeStart());
    }

    @Test
    public void testConvertWithTimeEnd() {
        when(priceChartDO.getTimeEnd()).thenReturn(Integer.valueOf(123));
        PriceChart priceChart = testConverter.convert(priceChartDO);
        assertEquals(Integer.valueOf(123), priceChart.getTimeEnd());
    }

}

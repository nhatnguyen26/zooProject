package com.zoo.zooApplication.util;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.Assert.*;

public class DateTimeUtilTest {

    @Test
    public void testParseISO8601Format() {
        assertEquals(ZonedDateTime.of(LocalDateTime.of(2019, 03, 10, 21, 06, 28), (ZoneId.of("UTC"))),
                DateTimeUtil.parseISO8601Format("2019-03-10T21:06:28Z"));
    }

    @Test
    public void testParseISO8601FormatNull() {
        assertNull(DateTimeUtil.parseISO8601Format(null));
    }
}
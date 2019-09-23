package com.zoo.zooApplication.util;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    public static ZonedDateTime parseISO8601Format(String stringISO8601) {
        if (stringISO8601 == null) {
            return null;
        }
        return Instant.parse(stringISO8601).atZone(ZoneOffset.UTC.normalized()).withZoneSameInstant(ZoneId.of("UTC"));
    }

    public static LocalTime parseISO8601TimeFormat(String stringISO) {
        if (stringISO == null) {
            return null;
        }
        return Instant.parse(stringISO).atZone(ZoneOffset.UTC.normalized())
                .withZoneSameInstant(ZoneId.of("UTC")).toLocalTime();
    }

    public static String FormatISOTimeToString(LocalTime localTime){
        if (localTime == null){
            return null;
        }
        return localTime.format(DateTimeFormatter.ISO_TIME);
    }
}

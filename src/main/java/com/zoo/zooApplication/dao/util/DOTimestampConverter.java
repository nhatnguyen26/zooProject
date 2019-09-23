package com.zoo.zooApplication.dao.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Objects;

@Converter
public class DOTimestampConverter implements AttributeConverter<ZonedDateTime, Long> {

    @Override
    public Long convertToDatabaseColumn(ZonedDateTime localDateTime) {
        Objects.requireNonNull(localDateTime);
        return localDateTime.toInstant().toEpochMilli();
    }

    @Override
    public ZonedDateTime convertToEntityAttribute(Long milliseconds) {
        Objects.requireNonNull(milliseconds);
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneOffset.UTC.normalized());
    }
}

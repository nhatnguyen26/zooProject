package com.zoo.zooApplication.dao.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Objects;

@Converter(autoApply = true)
public class DOTimestampConverter implements AttributeConverter<ZonedDateTime, Long> {

    @Override
    public Long convertToDatabaseColumn(ZonedDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.toInstant().toEpochMilli();
    }

    @Override
    public ZonedDateTime convertToEntityAttribute(Long milliseconds) {
        return milliseconds == null ? null : ZonedDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneOffset.UTC.normalized());
    }
}

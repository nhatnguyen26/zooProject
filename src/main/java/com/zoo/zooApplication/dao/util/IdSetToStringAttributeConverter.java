package com.zoo.zooApplication.dao.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class IdSetToStringAttributeConverter implements AttributeConverter<Set<Long>, String> {
    private static final String COMMA = ",";

    @Override
    public String convertToDatabaseColumn(Set<Long> attribute) {
        if (CollectionUtils.isNotEmpty(attribute)) {
            return StringUtils.join(attribute, COMMA);
        } else {
            return null;
        }
    }

    @Override
    public Set<Long> convertToEntityAttribute(String dbData) {
        if (StringUtils.isNotBlank(dbData)) {
            String[] idList = StringUtils.split(dbData, COMMA);
            return Arrays.stream(idList)
                    .map(NumberUtils::toLong)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        } else {
            return new LinkedHashSet<>();
        }
    }
}

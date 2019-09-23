package com.zoo.zooApplication.dao.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class IdListToStringAttributeConverter implements AttributeConverter<List<Long>, String> {
    private static final String COMMA = ",";

    @Override
    public String convertToDatabaseColumn(List<Long> attribute) {
        if (CollectionUtils.isNotEmpty(attribute)) {
            return StringUtils.join(attribute, COMMA);
        } else {
            return null;
        }
    }

    @Override
    public List<Long> convertToEntityAttribute(String dbData) {
        if (StringUtils.isNotBlank(dbData)) {
            String[] idList = StringUtils.split(dbData, COMMA);
            return Arrays.stream(idList)
                    .map(id -> NumberUtils.toLong(id))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}

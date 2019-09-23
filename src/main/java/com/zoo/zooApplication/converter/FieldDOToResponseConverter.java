package com.zoo.zooApplication.converter;

import com.zoo.zooApplication.dao.model.FieldDO;
import com.zoo.zooApplication.response.Field;
import com.zoo.zooApplication.response.FieldResponse;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class FieldDOToResponseConverter {
    public Field convert(@NotNull final FieldDO fieldDO) {
        Objects.requireNonNull(fieldDO);

        return Field
                .builder()
                .id(fieldDO.getId())
                .name(fieldDO.getName())
                .mainFieldType(fieldDO.getMainFieldType())
                .fieldTypeId(fieldDO.getFieldTypeId())
                .subFieldIds(fieldDO.getSubFieldIds())
                .build();
    }

    public FieldResponse convert(@NotNull final List<FieldDO> fieldDOList) {
        Objects.requireNonNull(fieldDOList);

        return FieldResponse
            .builder()
            .fields(convertFields(fieldDOList))
            .build();
    }

    private List<Field> convertFields(List<FieldDO> fieldDOList) {
        return fieldDOList
            .stream()
            .map(this::convert)
            .collect(Collectors.toList());
    }
}

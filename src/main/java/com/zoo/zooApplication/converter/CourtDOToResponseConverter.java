package com.zoo.zooApplication.converter;

import com.zoo.zooApplication.dao.model.CourtDO;
import com.zoo.zooApplication.response.Court;
import com.zoo.zooApplication.response.Field;
import com.zoo.zooApplication.response.FieldType;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CourtDOToResponseConverter {

    private FieldDOToResponseConverter fieldDOToResponseConverter;
    private FieldTypeDOToResponseConverter fieldTypeDOToResponseConverter;


    @Inject
    public CourtDOToResponseConverter(FieldDOToResponseConverter fieldDOToResponseConverter,
                                      FieldTypeDOToResponseConverter fieldTypeDOToResponseConverter) {
        this.fieldDOToResponseConverter = fieldDOToResponseConverter;
        this.fieldTypeDOToResponseConverter = fieldTypeDOToResponseConverter;
    }

    public Court convert(@NotNull final CourtDO courtDO) {
        Objects.requireNonNull(courtDO);
        return Court
                .builder()
                .id(courtDO.getId())
                .name(courtDO.getName())
                .addressStreet(courtDO.getAddressStreet())
                .addressWard(courtDO.getAddressWard())
                .addressDistrict(courtDO.getAddressDistrict())
                .addressCity(courtDO.getAddressCity())
                .addressCountry(courtDO.getAddressCountry())
                .phoneNumber(courtDO.getPhoneNumber())
                .fields(convertFields(courtDO))
                .fieldTypes(convertFieldTypes(courtDO))
                .build();
    }

    private List<Field> convertFields(CourtDO courtDO) {
        if (CollectionUtils.isNotEmpty(courtDO.getFields())) {
            return courtDO
                    .getFields()
                    .stream()
                    .map(fieldDOToResponseConverter::convert)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private List<FieldType> convertFieldTypes(CourtDO courtDO) {
        if (CollectionUtils.isNotEmpty(courtDO.getFieldTypes())) {
            return courtDO
                    .getFieldTypes()
                    .stream()
                    .map(fieldTypeDOToResponseConverter::convert)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}

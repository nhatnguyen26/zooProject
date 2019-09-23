package com.zoo.zooApplication.dao.util;

import com.zoo.zooApplication.type.CourtRoleEnum;

import javax.persistence.AttributeConverter;

public class CourtRoleEnumConverter implements AttributeConverter<CourtRoleEnum, Integer> {
	@Override
	public Integer convertToDatabaseColumn(CourtRoleEnum attribute) {
		return attribute != null ? attribute.getId() : -1;
	}

	@Override
	public CourtRoleEnum convertToEntityAttribute(Integer dbData) {
		return dbData != null ? CourtRoleEnum.getFromId(dbData) : null;
	}
}

package com.zoo.zooApplication.dao.util;

import com.zoo.zooApplication.type.BookingStatusEnum;

import javax.persistence.AttributeConverter;

public class BookingStatusEnumConverter implements AttributeConverter<BookingStatusEnum,Integer> {

    @Override
    public Integer convertToDatabaseColumn(BookingStatusEnum attribute) {
        return attribute != null ? attribute.getId() : -1;
    }

    @Override
    public BookingStatusEnum convertToEntityAttribute(Integer dbData) {
        if (dbData != null){
            return BookingStatusEnum.getFromId(dbData);
        }
        return null;
    }
}

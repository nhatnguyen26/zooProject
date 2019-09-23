package com.zoo.zooApplication.converter;

import com.zoo.zooApplication.dao.model.FieldBookingDO;
import com.zoo.zooApplication.response.FieldBooking;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Component
public class FieldBookingDOToResponseConverter {
    public FieldBooking convert(@NotNull final FieldBookingDO fieldBookingDO) {
        Objects.requireNonNull(fieldBookingDO);

        return FieldBooking
                .builder()
                .id(fieldBookingDO.getId())
                .fieldId(fieldBookingDO.getFieldId())
                .courtId(fieldBookingDO.getCourtId())
                .bookerEmail(fieldBookingDO.getBookerEmail())
                .bookerPhone(fieldBookingDO.getBookerPhone())
                .build();
    }
}

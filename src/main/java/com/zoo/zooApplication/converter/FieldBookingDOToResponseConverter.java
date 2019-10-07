package com.zoo.zooApplication.converter;

import com.zoo.zooApplication.dao.model.FieldBookingDO;
import com.zoo.zooApplication.response.FieldBooking;
import com.zoo.zooApplication.util.DateTimeUtil;
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
			.courtId(fieldBookingDO.getCourtId())
			.fieldId(fieldBookingDO.getFieldId())
			.fieldTypeId(fieldBookingDO.getFieldTypeId())
			.timeIn(DateTimeUtil.formatISOTimeToString(fieldBookingDO.getTimeIn()))
			.actualTimeIn(DateTimeUtil.formatISOTimeToString(fieldBookingDO.getActualTimeIn()))
			.timeOut(DateTimeUtil.formatISOTimeToString(fieldBookingDO.getTimeOut()))
			.actualTimeOut(DateTimeUtil.formatISOTimeToString(fieldBookingDO.getActualTimeOut()))
			.status(fieldBookingDO.getStatus())
			.priceAmount(fieldBookingDO.getPriceAmount())
			.depositAmount(fieldBookingDO.getDepositAmount())
			.actualChargedAmount(fieldBookingDO.getActualChargedAmount())
			.currencyId(fieldBookingDO.getCurrencyId())
			.bookerName(fieldBookingDO.getBookerName())
			.bookerEmail(fieldBookingDO.getBookerEmail())
			.bookerPhone(fieldBookingDO.getBookerPhone())
			.regularBooker(fieldBookingDO.getRegularBooker())
			.adminNote(fieldBookingDO.getAdminNote())
			.build();
	}
}

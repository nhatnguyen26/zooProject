package com.zoo.zooApplication.converter;

import com.zoo.zooApplication.dao.model.FieldBookingDO;
import com.zoo.zooApplication.response.FieldBooking;
import com.zoo.zooApplication.response.FieldBookingDetail;
import com.zoo.zooApplication.util.DateTimeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class FieldBookingDOToResponseConverter {
	public FieldBooking convert(@NotNull final FieldBookingDO fieldBookingDO) {
		Objects.requireNonNull(fieldBookingDO);

		return FieldBooking
			.builder()
			.id(fieldBookingDO.getId())
			.courtId(fieldBookingDO.getCourtId())
			.bookingNumber(fieldBookingDO.getBookingNumber())
			.priceAmount(fieldBookingDO.getPriceAmount())
			.depositAmount(fieldBookingDO.getDepositAmount())
			.actualChargedAmount(fieldBookingDO.getActualChargedAmount())
			.currencyId(fieldBookingDO.getCurrencyId())
			.bookerName(fieldBookingDO.getBookerName())
			.bookerEmail(fieldBookingDO.getBookerEmail())
			.bookerPhone(fieldBookingDO.getBookerPhone())
			.regularBooker(fieldBookingDO.getRegularBooker())
			.mainFieldType(fieldBookingDO.getMainFieldType())
			.bookingDetails(convertBookingDetails(fieldBookingDO))
			.build();
	}

	private List<FieldBookingDetail> convertBookingDetails(FieldBookingDO fieldBookingDO) {
		List<FieldBookingDO> allBookingDO = new ArrayList<>();
		allBookingDO.add(fieldBookingDO);
		if (CollectionUtils.isNotEmpty(fieldBookingDO.getSubBookings())) {
			allBookingDO.addAll(fieldBookingDO.getSubBookings());
		}
		return allBookingDO
			.stream()
			.map(this::convertBookingDetail)
			.collect(Collectors.toList());
	}

	private FieldBookingDetail convertBookingDetail(FieldBookingDO fieldBookingDO) {
		return FieldBookingDetail.builder()
			.fieldId(fieldBookingDO.getFieldId())
			.fieldTypeId(fieldBookingDO.getFieldTypeId())
			.timeIn(DateTimeUtil.formatISOTimeToString(fieldBookingDO.getTimeIn()))
			.actualTimeIn(DateTimeUtil.formatISOTimeToString(fieldBookingDO.getActualTimeIn()))
			.timeOut(DateTimeUtil.formatISOTimeToString(fieldBookingDO.getTimeOut()))
			.actualTimeOut(DateTimeUtil.formatISOTimeToString(fieldBookingDO.getActualTimeOut()))
			.status(fieldBookingDO.getStatus())
			.adminNote(fieldBookingDO.getAdminNote())
			.build();
	}

}

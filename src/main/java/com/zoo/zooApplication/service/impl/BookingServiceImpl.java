package com.zoo.zooApplication.service.impl;

import com.zoo.zooApplication.converter.FieldBookingDOToResponseConverter;
import com.zoo.zooApplication.dao.model.CourtDO;
import com.zoo.zooApplication.dao.model.FieldBookingDO;
import com.zoo.zooApplication.dao.repository.FieldBookingRepository;
import com.zoo.zooApplication.request.BookingRequest;
import com.zoo.zooApplication.request.SearchFieldBookingRequest;
import com.zoo.zooApplication.response.FieldBooking;
import com.zoo.zooApplication.service.BookingService;
import com.zoo.zooApplication.util.DateTimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

	private FieldBookingRepository fieldBookingRepository;

	private FieldBookingDOToResponseConverter fieldBookingDOToResponseConverter;

	@Inject
	public BookingServiceImpl(FieldBookingRepository fieldBookingRepository, FieldBookingDOToResponseConverter fieldBookingDOToResponseConverter) {
		this.fieldBookingRepository = fieldBookingRepository;
		this.fieldBookingDOToResponseConverter = fieldBookingDOToResponseConverter;
	}

	@Override
	public FieldBooking findBookingById(String bookingId) {
		Optional<FieldBookingDO> fieldBookingDO = fieldBookingRepository.findById(NumberUtils.toLong(bookingId));
		return fieldBookingDO
			.map(fieldBookingDOToResponseConverter::convert)
			.orElse(null);
	}

	@Transactional
	@Override
	public FieldBooking createBooking(BookingRequest bookingRequest) {
		FieldBookingDO.FieldBookingDOBuilder doBuilder = FieldBookingDO.builder()
			.courtId(bookingRequest.getCourtId())
			.fieldTypeId(bookingRequest.getFieldTypeId())
			.fieldId(bookingRequest.getFieldId())
			.status(bookingRequest.getStatus())
			.timeIn(DateTimeUtil.parseISO8601Format(bookingRequest.getTimeIn()))
			.actualTimeIn(DateTimeUtil.parseISO8601Format(bookingRequest.getActualTimeIn()))
			.timeOut(DateTimeUtil.parseISO8601Format(bookingRequest.getTimeOut()))
			.actualTimeOut(DateTimeUtil.parseISO8601Format(bookingRequest.getActualTimeOut()))
			.bookerPhone(bookingRequest.getBookerPhone())
			.bookerEmail(bookingRequest.getBookerEmail())
			.bookerName(bookingRequest.getBookerName())
			.regularBooker(bookingRequest.getRegularBooker())
			.adminNote(bookingRequest.getAdminNote())
			.priceAmount(bookingRequest.getPriceAmount())
			.depositAmount(bookingRequest.getDepositAmount())
			.actualChargedAmount(bookingRequest.getActualChargedAmount())
			.currencyId("VND"); // NOTE; hard-code to VND


		FieldBookingDO result = fieldBookingRepository.save(doBuilder.build());
		return fieldBookingDOToResponseConverter.convert(result);
	}

	@Transactional
	@Override
	public FieldBooking editBooking(String bookingId, BookingRequest editBookingRequest) {
		Optional<FieldBookingDO> fieldBookingDOOptional = fieldBookingRepository.findById(NumberUtils.toLong(bookingId));

		return fieldBookingDOOptional
			.map(fieldBookingDO -> editBookingInfo(fieldBookingDO, editBookingRequest))
			.map(fieldBookingRepository::save)
			.map(fieldBookingDOToResponseConverter::convert)
			.orElse(null);
	}

	private FieldBookingDO editBookingInfo(FieldBookingDO fieldBookingDO, BookingRequest editBookingRequest) {
		if (editBookingRequest.getFieldTypeId() != null) {
			fieldBookingDO.setFieldTypeId(editBookingRequest.getFieldTypeId());
		}
		if (editBookingRequest.getFieldId() != null) {
			fieldBookingDO.setFieldId(editBookingRequest.getFieldId());
		}
		if (editBookingRequest.getStatus() != null) {
			fieldBookingDO.setStatus(editBookingRequest.getStatus());
		}
		if (StringUtils.isNotBlank(editBookingRequest.getTimeIn())) {
			fieldBookingDO.setTimeIn(DateTimeUtil.parseISO8601Format(editBookingRequest.getTimeIn()));
		}
		if (StringUtils.isNotBlank(editBookingRequest.getTimeOut())) {
			fieldBookingDO.setTimeOut(DateTimeUtil.parseISO8601Format(editBookingRequest.getTimeOut()));
		}
		if (StringUtils.isNotBlank(editBookingRequest.getActualTimeIn())) {
			fieldBookingDO.setActualTimeIn(DateTimeUtil.parseISO8601Format(editBookingRequest.getActualTimeIn()));
		}
		if (StringUtils.isNotBlank(editBookingRequest.getActualTimeOut())) {
			fieldBookingDO.setActualTimeOut(DateTimeUtil.parseISO8601Format(editBookingRequest.getActualTimeOut()));
		}
		if (StringUtils.isNotBlank(editBookingRequest.getBookerEmail())) {
			fieldBookingDO.setBookerEmail(editBookingRequest.getBookerEmail());
		}
		if (StringUtils.isNotBlank(editBookingRequest.getBookerName())) {
			fieldBookingDO.setBookerName(editBookingRequest.getBookerName());
		}
		if (StringUtils.isNotBlank(editBookingRequest.getBookerPhone())) {
			fieldBookingDO.setBookerPhone(editBookingRequest.getBookerPhone());
		}
		if (editBookingRequest.getRegularBooker() != null) {
			fieldBookingDO.setRegularBooker(editBookingRequest.getRegularBooker());
		}
		if (StringUtils.isNotBlank(editBookingRequest.getAdminNote())) {
			fieldBookingDO.setAdminNote(editBookingRequest.getAdminNote());
		}
		if (editBookingRequest.getActualChargedAmount() != null) {
			fieldBookingDO.setActualChargedAmount(editBookingRequest.getActualChargedAmount());
		}
		if (editBookingRequest.getDepositAmount() != null) {
			fieldBookingDO.setDepositAmount(editBookingRequest.getDepositAmount());
		}

		return fieldBookingDO;
	}

	@Transactional
	@Override
	public FieldBooking deleteBooking(String bookingId) {
		Optional<FieldBookingDO> fieldBookingDOOptional = fieldBookingRepository.findById(NumberUtils.toLong(bookingId));
		if (fieldBookingDOOptional.isPresent()) {
			FieldBookingDO fieldBookingDO = fieldBookingDOOptional.get();
			fieldBookingRepository.delete(fieldBookingDO);
			return fieldBookingDOToResponseConverter.convert(fieldBookingDO);
		}

		return null;
	}

	@Transactional(readOnly = true)
	@Override
	public List<FieldBooking> findAllBookingByFieldId(SearchFieldBookingRequest searchRequest) {
		FieldBookingDO fieldBookingDO = FieldBookingDO
			.builder()
			.fieldId(searchRequest.getFieldId())
			.build();
		Example<FieldBookingDO> exampleBookingDO = Example.of(fieldBookingDO);
		Page<FieldBookingDO> listFieldBookingDO =
			fieldBookingRepository.findAll(exampleBookingDO, searchRequest.getPageable());
		return listFieldBookingDO
			.stream()
			.map(fieldBookingDOToResponseConverter::convert)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	@Override
	public List<FieldBooking> findByUserInfo
		(SearchFieldBookingRequest searchRequest) {
		ZonedDateTime timeIn = DateTimeUtil.parseISO8601Format(searchRequest.getTimeIn());
		List<FieldBookingDO> listFieldBookingDO =
			fieldBookingRepository.findByBookerPhoneOrBookerEmailAndTimeInGreaterThanEqual(
				searchRequest.getBookerPhone(),
				searchRequest.getBookerEmail(),
				timeIn,
				searchRequest.getPageable()
			);
		return listFieldBookingDO
			.stream()
			.map(fieldBookingDOToResponseConverter::convert)
			.collect(Collectors.toList());
	}
}

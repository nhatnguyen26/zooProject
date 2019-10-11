package com.zoo.zooApplication.service.impl;

import com.zoo.zooApplication.converter.FieldBookingDOToResponseConverter;
import com.zoo.zooApplication.dao.model.FieldBookingDO;
import com.zoo.zooApplication.dao.repository.FieldBookingRepository;
import com.zoo.zooApplication.request.BookingDetailRequest;
import com.zoo.zooApplication.request.BookingRequest;
import com.zoo.zooApplication.request.SearchFieldBookingRequest;
import com.zoo.zooApplication.response.FieldBooking;
import com.zoo.zooApplication.response.FieldBookingResponse;
import com.zoo.zooApplication.service.BookingService;
import com.zoo.zooApplication.type.MainFieldTypeEnum;
import com.zoo.zooApplication.util.DateTimeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
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

	@Transactional(readOnly = true)
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
			.bookerPhone(bookingRequest.getBookerPhone())
			.bookerEmail(bookingRequest.getBookerEmail())
			.bookerName(bookingRequest.getBookerName())
			.regularBooker(bookingRequest.getRegularBooker())
			.priceAmount(bookingRequest.getPriceAmount())
			.depositAmount(bookingRequest.getDepositAmount())
			.actualChargedAmount(bookingRequest.getActualChargedAmount())
			.currencyId("VND")
			.mainFieldType(bookingRequest.getMainFieldType()); // NOTE; hard-code to VND

		FieldBookingDO doToSave = createSingleBooking(doBuilder, bookingRequest.getBookingDetails().get(0));
		if (bookingRequest.getBookingDetails().size() > 1) {
			doToSave = appendDetails(doBuilder, bookingRequest.getBookingDetails().subList(1, bookingRequest.getBookingDetails().size()));
		}
		FieldBookingDO result = fieldBookingRepository.save(doToSave);
		return fieldBookingDOToResponseConverter.convert(result);
	}

	private FieldBookingDO appendDetails(FieldBookingDO.FieldBookingDOBuilder parentBuilder, List<BookingDetailRequest> bookingDetails) {
		List<FieldBookingDO> bookingDODetails = bookingDetails.stream()
			.map(bookingDetailRequest -> createSingleBooking(FieldBookingDO.builder(), bookingDetailRequest))
			.collect(Collectors.toList());

		// pick the earliest time in to put into the parent record, given use case always within a day to support fetch
		FieldBookingDO parentRecord = parentBuilder.build();
		bookingDODetails.forEach(parentRecord::addSubBooking);
		return parentRecord;
	}

	private FieldBookingDO createSingleBooking(FieldBookingDO.FieldBookingDOBuilder doBuilder, BookingDetailRequest detailRequest) {
		return doBuilder
			.fieldTypeId(detailRequest.getFieldTypeId())
			.fieldId(detailRequest.getFieldId())
			.status(detailRequest.getStatus())
			.timeIn(DateTimeUtil.parseISO8601Format(detailRequest.getTimeIn()))
			.actualTimeIn(DateTimeUtil.parseISO8601Format(detailRequest.getActualTimeIn()))
			.timeOut(DateTimeUtil.parseISO8601Format(detailRequest.getTimeOut()))
			.actualTimeOut(DateTimeUtil.parseISO8601Format(detailRequest.getActualTimeOut()))
			.adminNote(detailRequest.getAdminNote())
			.build();

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

		if (editBookingRequest.getActualChargedAmount() != null) {
			fieldBookingDO.setActualChargedAmount(editBookingRequest.getActualChargedAmount());
		}
		if (editBookingRequest.getDepositAmount() != null) {
			fieldBookingDO.setDepositAmount(editBookingRequest.getDepositAmount());
		}

		if (CollectionUtils.isNotEmpty(editBookingRequest.getBookingDetails())) {
			// edit the very first detail as parent record
			editBookingSingle(fieldBookingDO, editBookingRequest.getBookingDetails().get(0));

			List<BookingDetailRequest> bookingDetailRequests = editBookingRequest.getBookingDetails().subList(1, editBookingRequest.getBookingDetails().size());
			List<FieldBookingDO> subBookings = fieldBookingDO.getSubBookings();
			int sizeDiff = subBookings.size() - bookingDetailRequests.size();
			// if there is size diff, match the count for do
			for (int i = 0; i < Math.abs(sizeDiff); i++) {
				// there is new detail in booking, could be another booking or change field
				if (sizeDiff < 0) {
					fieldBookingDO.addSubBooking(FieldBookingDO.builder().build());
				} else {
					subBookings.remove(subBookings.size() - 1);
				}
			}

			for (int i = 0; i < subBookings.size(); i++) {
				editBookingSingle(subBookings.get(i), bookingDetailRequests.get(i));
			}

		}

		return fieldBookingDO;
	}

	private void editBookingSingle(FieldBookingDO fieldBookingDO, BookingDetailRequest bookingDetailRequest) {
		fieldBookingDO.setFieldTypeId(bookingDetailRequest.getFieldTypeId());
		fieldBookingDO.setFieldId(bookingDetailRequest.getFieldId());
		fieldBookingDO.setStatus(bookingDetailRequest.getStatus());
		fieldBookingDO.setTimeIn(DateTimeUtil.parseISO8601Format(bookingDetailRequest.getTimeIn()));
		fieldBookingDO.setTimeOut(DateTimeUtil.parseISO8601Format(bookingDetailRequest.getTimeOut()));
		fieldBookingDO.setActualTimeIn(DateTimeUtil.parseISO8601Format(bookingDetailRequest.getActualTimeIn()));
		fieldBookingDO.setActualTimeOut(DateTimeUtil.parseISO8601Format(bookingDetailRequest.getActualTimeOut()));
		fieldBookingDO.setAdminNote(bookingDetailRequest.getAdminNote());
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
	public FieldBookingResponse search(SearchFieldBookingRequest searchRequest) {
		Long courtId = NumberUtils.toLong(searchRequest.getCourtId());
		Long lowerBound = DateTimeUtil.parseISO8601Format(searchRequest.getTimeFrom()).toInstant().toEpochMilli();
		Long upperBound = DateTimeUtil.parseISO8601Format(searchRequest.getTimeTo()).toInstant().toEpochMilli();
		Pageable pageable = searchRequest.getPageable();

		MainFieldTypeEnum mainFieldTypeEnum = searchRequest.getMainFieldType();

		Page<FieldBookingDO> fieldBookingPage = (mainFieldTypeEnum != null) ?
			fieldBookingRepository.findByCourtIdAndMainFieldTypeWithTimeRange(courtId, mainFieldTypeEnum.getId(), lowerBound, upperBound, pageable) :
			fieldBookingRepository.findByCourtIdWithTimeRange(courtId, lowerBound, upperBound, pageable);


		List<FieldBooking> fieldBookings = fieldBookingPage.getContent()
			.stream()
			.map(fieldBookingDOToResponseConverter::convert)
			.collect(Collectors.toList());
		return FieldBookingResponse
			.builder()
			.fieldBookings(fieldBookings)
			.build();
	}

}

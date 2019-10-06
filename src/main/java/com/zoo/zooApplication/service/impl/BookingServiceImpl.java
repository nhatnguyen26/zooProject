package com.zoo.zooApplication.service.impl;

import com.zoo.zooApplication.converter.FieldBookingDOToResponseConverter;
import com.zoo.zooApplication.dao.model.FieldBookingDO;
import com.zoo.zooApplication.dao.repository.FieldBookingRepository;
import com.zoo.zooApplication.request.BookingRequest;
import com.zoo.zooApplication.request.CreateBookingRequest;
import com.zoo.zooApplication.request.SearchFieldBookingRequest;
import com.zoo.zooApplication.request.validator.BookingRequestValidator;
import com.zoo.zooApplication.response.FieldBooking;
import com.zoo.zooApplication.service.BookingService;
import com.zoo.zooApplication.util.DateTimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private FieldBookingRepository fieldBookingRepository;

    private FieldBookingDOToResponseConverter fieldBookingDOToResponseConverter;

    private BookingRequestValidator bookingRequestValidator;

    @Inject
    public BookingServiceImpl(FieldBookingRepository fieldBookingRepository, FieldBookingDOToResponseConverter fieldBookingDOToResponseConverter, BookingRequestValidator bookingRequestValidator) {
        this.fieldBookingRepository = fieldBookingRepository;
        this.fieldBookingDOToResponseConverter = fieldBookingDOToResponseConverter;
        this.bookingRequestValidator = bookingRequestValidator;
    }

    @Override
    public FieldBooking findBookingById(String bookingId) {
        bookingRequestValidator.validateBookingId(bookingId);
        Optional<FieldBookingDO> fieldBookingDO = fieldBookingRepository.findById(NumberUtils.toLong(bookingId));
        return fieldBookingDO
                .map(fieldBookingDOToResponseConverter::convert)
                .orElse(null);
    }

    @Override
    public FieldBooking createBooking(CreateBookingRequest bookingRequest) {
        bookingRequestValidator.validateCreateBookingRequest(bookingRequest);
        ZonedDateTime timeIn = DateTimeUtil.parseISO8601Format(bookingRequest.getTimeIn());
        FieldBookingDO.FieldBookingDOBuilder doBuilder = FieldBookingDO.builder()
                .fieldId(bookingRequest.getFieldId())
                .timeIn(timeIn)
                .timeOut(timeIn.plusMinutes(bookingRequest.getDuration()))
                .bookerPhone(bookingRequest.getBookerPhone())
                .bookerEmail(bookingRequest.getBookerEmail())
                .bookerName(bookingRequest.getBookerName())
                .priceAmount(bookingRequest.getPriceAmount())
                .currencyId("VND"); // NOTE; hard-code to VND

        if (bookingRequest.getFieldId() != null && bookingRequest.getFieldId() > 0) {
            // TODO: find courtId and fieldType from fieldID and ignore the extra data
            doBuilder.fieldId(bookingRequest.getFieldId());
        } else {
            doBuilder.courtId(bookingRequest.getCourtId());
            doBuilder.fieldType(bookingRequest.getFieldType());
        }

        FieldBookingDO result = fieldBookingRepository.save(doBuilder.build());
        return fieldBookingDOToResponseConverter.convert(result);
    }

    @Override
    public FieldBooking editBooking(String bookingId, BookingRequest editBookingRequest) {
        bookingRequestValidator.validateBookingId(bookingId);
        Optional<FieldBookingDO> fieldBookingDOOptional = fieldBookingRepository.findById(NumberUtils.toLong(bookingId));

        return fieldBookingDOOptional
                .map(fieldBookingDO -> editBookingInfo(fieldBookingDOOptional.get(),editBookingRequest))
                .map(fieldBookingRepository::save)
                .map(fieldBookingDOToResponseConverter::convert)
                .orElse(null);
    }

    @Override
    public FieldBooking deleteBooking(String bookingId) {
        FieldBooking fieldBooking = findBookingById(bookingId);
        if (fieldBooking != null) {
            fieldBookingRepository.deleteById(NumberUtils.toLong(bookingId));
            return fieldBooking;
        }
        return null;
    }

    private FieldBookingDO editBookingInfo(FieldBookingDO fieldBookingDO, BookingRequest editBookingRequest){
        if (StringUtils.isNotBlank(editBookingRequest.getBookerEmail())) {
            fieldBookingDO.setBookerEmail(editBookingRequest.getBookerEmail());
        }
        if (StringUtils.isNotBlank(editBookingRequest.getBookerName())){
            fieldBookingDO.setBookerName(editBookingRequest.getBookerName());
        }
        if (StringUtils.isNotBlank(editBookingRequest.getBookerPhone())){
            fieldBookingDO.setBookerPhone(editBookingRequest.getBookerPhone());
        }
        if (editBookingRequest.getStatus() != null) {
            fieldBookingDO.setStatus(editBookingRequest.getStatus());
        }
        if (StringUtils.isNotBlank(editBookingRequest.getTimeIn())){
            fieldBookingDO.setTimeIn(DateTimeUtil.parseISO8601Format(editBookingRequest.getTimeIn()));
        }
        if (StringUtils.isNotBlank(editBookingRequest.getTimeOut())){
            fieldBookingDO.setTimeOut(DateTimeUtil.parseISO8601Format(editBookingRequest.getTimeOut()));
        }
        if (StringUtils.isNotBlank(Integer.toString(editBookingRequest.getDuration()))){
            fieldBookingDO.setTimeOut(fieldBookingDO.getTimeIn().plusMinutes(editBookingRequest.getDuration()));
        }
        if (StringUtils.isNotBlank(editBookingRequest.getActualTimeIn())){
            fieldBookingDO.setActualTimeIn(DateTimeUtil.parseISO8601Format(editBookingRequest.getActualTimeIn()));
        }
        if (StringUtils.isNotBlank(editBookingRequest.getActualTimeOut())){
            fieldBookingDO.setActualTimeOut(DateTimeUtil.parseISO8601Format(editBookingRequest.getActualTimeOut()));
        }
        return fieldBookingDO;
    }

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

    @Override
    public List<FieldBooking> findByUserInfo
            (SearchFieldBookingRequest searchRequest){
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

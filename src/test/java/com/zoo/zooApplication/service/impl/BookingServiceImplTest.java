package com.zoo.zooApplication.service.impl;

import com.zoo.zooApplication.converter.FieldBookingDOToResponseConverter;
import com.zoo.zooApplication.dao.model.FieldBookingDO;
import com.zoo.zooApplication.dao.repository.FieldBookingRepository;
import com.zoo.zooApplication.request.BookingDetailRequest;
import com.zoo.zooApplication.request.BookingRequest;
import com.zoo.zooApplication.request.SearchFieldBookingRequest;
import com.zoo.zooApplication.response.FieldBooking;
import com.zoo.zooApplication.response.FieldBookingResponse;
import com.zoo.zooApplication.type.BookingStatusEnum;
import com.zoo.zooApplication.type.MainFieldTypeEnum;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BookingServiceImplTest {

    @Mock
    private FieldBookingRepository fieldBookingRepository;

    @Mock
    private FieldBookingDOToResponseConverter fieldBookingDOToResponseConverter;

    private BookingServiceImpl bookingService;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        bookingService = new BookingServiceImpl(fieldBookingRepository, fieldBookingDOToResponseConverter);
    }

    @Test
    public void testFindBookingByIdValid() {
        Optional<FieldBookingDO> mockDO = Optional.of(mock(FieldBookingDO.class));
        when(fieldBookingRepository.findById(123L)).thenReturn(mockDO);
        FieldBooking mockResponse = mock(FieldBooking.class);
        when(fieldBookingDOToResponseConverter.convert(mockDO.get())).thenReturn(mockResponse);

        assertEquals(mockResponse, bookingService.findBookingById("123"));
    }

	@Test
    public void testCreateBookingValidRequest() {
        BookingRequest testRequest = mock(BookingRequest.class);
		List<BookingDetailRequest> detailList = new ArrayList<>();
		detailList.add(mock(BookingDetailRequest.class));
		when(testRequest.getBookingDetails()).thenReturn(detailList);
        FieldBookingDO mockDO = mock(FieldBookingDO.class);
        when(fieldBookingRepository.save(any(FieldBookingDO.class))).thenReturn(mockDO);
        FieldBooking mockResponse = mock(FieldBooking.class);
        when(fieldBookingDOToResponseConverter.convert(mockDO)).thenReturn(mockResponse);
        assertEquals(mockResponse, bookingService.createBooking(testRequest));
    }

    @Test
    public void testCreateBookingValidRequestVerifyCourtId() {
        BookingRequest testRequest = mock(BookingRequest.class);
        List<BookingDetailRequest> detailList = new ArrayList<>();
        detailList.add(mock(BookingDetailRequest.class));
        when(testRequest.getBookingDetails()).thenReturn(detailList);
        when(testRequest.getCourtId()).thenReturn(1L);
        FieldBookingDO requestDO = fieldRequestHelperBasicInfo(testRequest);
        assertEquals(Long.valueOf(1), requestDO.getCourtId());
    }

    @Test
    public void testCreateBookingValidRequestVerifyMainFieldType() {
        BookingRequest testRequest = mock(BookingRequest.class);
        List<BookingDetailRequest> detailList = new ArrayList<>();
        detailList.add(mock(BookingDetailRequest.class));
        when(testRequest.getBookingDetails()).thenReturn(detailList);
        when(testRequest.getMainFieldType()).thenReturn(MainFieldTypeEnum.SOCCER_5);
        FieldBookingDO requestDO = fieldRequestHelperBasicInfo(testRequest);
        assertEquals(MainFieldTypeEnum.SOCCER_5, requestDO.getMainFieldType());
    }

    @Test
    public void testCreateBookingValidRequestVerifyBookerName() {
        BookingRequest testRequest = mock(BookingRequest.class);
        List<BookingDetailRequest> detailList = new ArrayList<>();
        detailList.add(mock(BookingDetailRequest.class));
        when(testRequest.getBookingDetails()).thenReturn(detailList);
        when(testRequest.getBookerName()).thenReturn("abc");
        FieldBookingDO requestDO = fieldRequestHelperBasicInfo(testRequest);
        assertEquals("abc", requestDO.getBookerName());
    }

    @Test
    public void testCreateBookingValidRequestVerifyBookerEmail() {
        BookingRequest testRequest = mock(BookingRequest.class);
        List<BookingDetailRequest> detailList = new ArrayList<>();
        detailList.add(mock(BookingDetailRequest.class));
        when(testRequest.getBookingDetails()).thenReturn(detailList);
        when(testRequest.getBookerEmail()).thenReturn("abc@gmail.com");
        FieldBookingDO requestDO = fieldRequestHelperBasicInfo(testRequest);
        assertEquals("abc@gmail.com", requestDO.getBookerEmail());
    }

    @Test
    public void testCreateBookingValidRequestVerifyBookerPhone() {
        BookingRequest testRequest = mock(BookingRequest.class);
        List<BookingDetailRequest> detailList = new ArrayList<>();
        detailList.add(mock(BookingDetailRequest.class));
        when(testRequest.getBookingDetails()).thenReturn(detailList);
        when(testRequest.getBookerPhone()).thenReturn("123456789");
        FieldBookingDO requestDO = fieldRequestHelperBasicInfo(testRequest);
        assertEquals("123456789", requestDO.getBookerPhone());
    }

    @Test
    public void testCreateBookingValidRequestVerifyRegularBooker() {
        BookingRequest testRequest = mock(BookingRequest.class);
        List<BookingDetailRequest> detailList = new ArrayList<>();
        detailList.add(mock(BookingDetailRequest.class));
        when(testRequest.getBookingDetails()).thenReturn(detailList);
        when(testRequest.getRegularBooker()).thenReturn(true);
        FieldBookingDO requestDO = fieldRequestHelperBasicInfo(testRequest);
        assertTrue(requestDO.getRegularBooker());
    }

    @Test
    public void testCreateBookingValidRequestVerifyPrice() {
        BookingRequest testRequest = mock(BookingRequest.class);
        List<BookingDetailRequest> detailList = new ArrayList<>();
        detailList.add(mock(BookingDetailRequest.class));
        when(testRequest.getBookingDetails()).thenReturn(detailList);
        when(testRequest.getPriceAmount()).thenReturn(100000.00);
        when(testRequest.getDepositAmount()).thenReturn(50000.00);
        when(testRequest.getActualChargedAmount()).thenReturn(150000.00);
        FieldBookingDO requestDO = fieldRequestHelperBasicInfo(testRequest);
        assertEquals("VND", requestDO.getCurrencyId());
        assertEquals(Double.valueOf(100000.00), requestDO.getPriceAmount());
        assertEquals(Double.valueOf(50000.00), requestDO.getDepositAmount());
        assertEquals(Double.valueOf(150000.00), requestDO.getActualChargedAmount());
    }

    @Test
    public void testCreateBookingValidRequestVerifyDetailOneBooking() {
        BookingRequest testRequest = mock(BookingRequest.class);
        List<BookingDetailRequest> detailList = new ArrayList<>();
        when(testRequest.getBookingDetails()).thenReturn(detailList);
        BookingDetailRequest mockDetail = createBookingRequestDetail(1L, 2L, BookingStatusEnum.RESERVED, "2019-10-06T22:39:10Z", "2019-10-06T22:39:11Z", "2019-10-06T22:39:12Z", "2019-10-06T22:39:13Z", "note");
        detailList.add(mockDetail);
        FieldBookingDO requestDO = fieldRequestHelperBasicInfo(testRequest);
        assertEquals(Long.valueOf(1L), requestDO.getFieldTypeId());
        assertEquals(Long.valueOf(2L), requestDO.getFieldId());
        assertEquals(BookingStatusEnum.RESERVED, requestDO.getStatus());
        assertEquals(ZonedDateTime.of(LocalDateTime.of(2019, 10, 06, 22, 39, 10), (ZoneId.of("UTC"))), requestDO.getTimeIn());
        assertEquals(ZonedDateTime.of(LocalDateTime.of(2019, 10, 06, 22, 39, 11), (ZoneId.of("UTC"))), requestDO.getActualTimeIn());
        assertEquals(ZonedDateTime.of(LocalDateTime.of(2019, 10, 06, 22, 39, 12), (ZoneId.of("UTC"))), requestDO.getTimeOut());
        assertEquals(ZonedDateTime.of(LocalDateTime.of(2019, 10, 06, 22, 39, 13), (ZoneId.of("UTC"))), requestDO.getActualTimeOut());
        assertEquals("note", requestDO.getAdminNote());
    }

    @Test
    public void testCreateBookingValidRequestVerifyDetailMultipleDetail() {
        BookingRequest testRequest = mock(BookingRequest.class);
        List<BookingDetailRequest> detailList = new ArrayList<>();
        when(testRequest.getBookingDetails()).thenReturn(detailList);
        BookingDetailRequest mockDetail = createBookingRequestDetail(1L, 2L, BookingStatusEnum.RESERVED, "2019-10-06T22:39:10Z", "2019-10-06T22:39:11Z", "2019-10-06T22:39:12Z", "2019-10-06T22:39:13Z", "note");
        detailList.add(mockDetail);
        BookingDetailRequest mockDetail1 = createBookingRequestDetail(3L, 4L, BookingStatusEnum.CHECKED_OUT, "2019-10-06T22:39:15Z", "2019-10-06T22:39:16Z", "2019-10-06T22:39:17Z", "2019-10-06T22:39:18Z", "note1");
        detailList.add(mockDetail1);
        BookingDetailRequest mockDetail2 = createBookingRequestDetail(3L, 5L, BookingStatusEnum.CHECKED_IN, "2019-10-06T22:50:15Z", "2019-10-06T22:50:16Z", "2019-10-06T22:50:17Z", "2019-10-06T22:50:18Z", "note2");
        detailList.add(mockDetail2);

        FieldBookingDO requestDO = fieldRequestHelperBasicInfo(testRequest);
        assertEquals(Long.valueOf(1L), requestDO.getFieldTypeId());
        assertEquals(Long.valueOf(2L), requestDO.getFieldId());
        assertEquals(BookingStatusEnum.RESERVED, requestDO.getStatus());
        assertEquals(ZonedDateTime.of(LocalDateTime.of(2019, 10, 06, 22, 39, 10), (ZoneId.of("UTC"))), requestDO.getTimeIn());
        assertEquals(ZonedDateTime.of(LocalDateTime.of(2019, 10, 06, 22, 39, 11), (ZoneId.of("UTC"))), requestDO.getActualTimeIn());
        assertEquals(ZonedDateTime.of(LocalDateTime.of(2019, 10, 06, 22, 39, 12), (ZoneId.of("UTC"))), requestDO.getTimeOut());
        assertEquals(ZonedDateTime.of(LocalDateTime.of(2019, 10, 06, 22, 39, 13), (ZoneId.of("UTC"))), requestDO.getActualTimeOut());
        assertEquals("note", requestDO.getAdminNote());

        assertEquals(2, requestDO.getSubBookings().size());
        FieldBookingDO detail1 = requestDO.getSubBookings().get(0);
        assertEquals(Long.valueOf(3L), detail1.getFieldTypeId());
        assertEquals(Long.valueOf(4L), detail1.getFieldId());
        assertEquals(BookingStatusEnum.CHECKED_OUT, detail1.getStatus());
        assertEquals(ZonedDateTime.of(LocalDateTime.of(2019, 10, 06, 22, 39, 15), (ZoneId.of("UTC"))), detail1.getTimeIn());
        assertEquals(ZonedDateTime.of(LocalDateTime.of(2019, 10, 06, 22, 39, 16), (ZoneId.of("UTC"))), detail1.getActualTimeIn());
        assertEquals(ZonedDateTime.of(LocalDateTime.of(2019, 10, 06, 22, 39, 17), (ZoneId.of("UTC"))), detail1.getTimeOut());
        assertEquals(ZonedDateTime.of(LocalDateTime.of(2019, 10, 06, 22, 39, 18), (ZoneId.of("UTC"))), detail1.getActualTimeOut());
        assertEquals("note1", detail1.getAdminNote());

        FieldBookingDO detail2 = requestDO.getSubBookings().get(1);
        assertEquals(Long.valueOf(3L), detail2.getFieldTypeId());
        assertEquals(Long.valueOf(5L), detail2.getFieldId());
        assertEquals(BookingStatusEnum.CHECKED_IN, detail2.getStatus());
        assertEquals(ZonedDateTime.of(LocalDateTime.of(2019, 10, 06, 22, 50, 15), (ZoneId.of("UTC"))), detail2.getTimeIn());
        assertEquals(ZonedDateTime.of(LocalDateTime.of(2019, 10, 06, 22, 50, 16), (ZoneId.of("UTC"))), detail2.getActualTimeIn());
        assertEquals(ZonedDateTime.of(LocalDateTime.of(2019, 10, 06, 22, 50, 17), (ZoneId.of("UTC"))), detail2.getTimeOut());
        assertEquals(ZonedDateTime.of(LocalDateTime.of(2019, 10, 06, 22, 50, 18), (ZoneId.of("UTC"))), detail2.getActualTimeOut());
        assertEquals("note2", detail2.getAdminNote());

    }

    private BookingDetailRequest createBookingRequestDetail(
        Long fieldTypeId,
        Long fieldId,
        BookingStatusEnum bookingStatus,
        String timeIn,
        String actualTimeIn,
        String timeOut,
        String actualTimeOut,
        String note) {
        BookingDetailRequest mockDetail = new BookingDetailRequest();

        mockDetail.setFieldTypeId(fieldTypeId);
        mockDetail.setFieldId(fieldId);
        mockDetail.setStatus(bookingStatus);
        mockDetail.setTimeIn(timeIn);
        mockDetail.setActualTimeIn(actualTimeIn);
        mockDetail.setTimeOut(timeOut);
        mockDetail.setActualTimeOut(actualTimeOut);
        mockDetail.setAdminNote(note);

        return mockDetail;
    }


    private FieldBookingDO fieldRequestHelperBasicInfo(BookingRequest testRequest) {
        // basic stub as required
        ArgumentCaptor<FieldBookingDO> argumentCaptor = ArgumentCaptor.forClass(FieldBookingDO.class);
        FieldBookingDO mockDO = mock(FieldBookingDO.class);
        when(fieldBookingRepository.save(argumentCaptor.capture())).thenReturn(mockDO);
        FieldBooking fieldResponse = mock(FieldBooking.class);
        when(fieldBookingDOToResponseConverter.convert(mockDO)).thenReturn(fieldResponse);
        assertEquals(fieldResponse, bookingService.createBooking(testRequest));
        return argumentCaptor.getValue();
    }

    @Test
    public void testDeleteBookingValid() {
        FieldBookingDO mockDO = mock(FieldBookingDO.class);
        when(fieldBookingRepository.findById(1L)).thenReturn(Optional.of(mockDO));

        FieldBooking mockResponse = mock(FieldBooking.class);
        when(fieldBookingDOToResponseConverter.convert(mockDO)).thenReturn(mockResponse);
        assertEquals(mockResponse, bookingService.deleteBooking("1"));
        verify(fieldBookingRepository, times(1)).delete(mockDO);
    }

    @Test
    public void testDeleteBookingInvalid() {
        when(fieldBookingRepository.findById(1L)).thenReturn(Optional.empty());
        assertNull(bookingService.deleteBooking("1"));
        verify(fieldBookingRepository, times(0)).delete(any(FieldBookingDO.class));
    }

    @Test
    public void testSearchWithinCourt() {
        SearchFieldBookingRequest searchRequest = new SearchFieldBookingRequest();
        searchRequest.setCourtId("1");
        searchRequest.setTimeFrom("2019-10-06T22:50:15Z");
        searchRequest.setTimeTo("2019-10-06T22:50:20Z");
        searchRequest.setPageSize(10);
        searchRequest.setPage(1);

        List<FieldBookingDO> contentList = new ArrayList<>();
        contentList.add(mock(FieldBookingDO.class));
        FieldBooking mockBookingRes = FieldBooking.builder().build();
        when(fieldBookingDOToResponseConverter.convert(contentList.get(0))).thenReturn(mockBookingRes);
        Page<FieldBookingDO> pageResponse = new PageImpl<>(contentList);
        when(fieldBookingRepository.findByCourtIdWithTimeRange(1L, 1570402215000L, 1570402220000L, PageRequest.of(1, 10))).thenReturn(pageResponse);
        FieldBookingResponse expectResponse = bookingService.search(searchRequest);
        assertEquals(1, expectResponse.getFieldBookings().size());
        assertEquals(mockBookingRes, expectResponse.getFieldBookings().get(0));

    }

    @Test
    public void testSearchWithinCourtFilterMainFieldType() {
        SearchFieldBookingRequest searchRequest = new SearchFieldBookingRequest();
        searchRequest.setCourtId("1");
        searchRequest.setTimeFrom("2019-10-06T22:50:15Z");
        searchRequest.setTimeTo("2019-10-06T22:50:20Z");
        searchRequest.setPageSize(10);
        searchRequest.setPage(1);
        searchRequest.setMainFieldType(MainFieldTypeEnum.SOCCER_7);

        List<FieldBookingDO> contentList = new ArrayList<>();
        contentList.add(mock(FieldBookingDO.class));
        FieldBooking mockBookingRes = FieldBooking.builder().build();
        when(fieldBookingDOToResponseConverter.convert(contentList.get(0))).thenReturn(mockBookingRes);
        Page<FieldBookingDO> pageResponse = new PageImpl<>(contentList);
        when(fieldBookingRepository.findByCourtIdAndMainFieldTypeWithTimeRange(1L, 2, 1570402215000L, 1570402220000L, PageRequest.of(1, 10))).thenReturn(pageResponse);
        FieldBookingResponse expectResponse = bookingService.search(searchRequest);
        assertEquals(1, expectResponse.getFieldBookings().size());
        assertEquals(mockBookingRes, expectResponse.getFieldBookings().get(0));
    }

    @Test
    public void testEditBookingChangeBookerName() {
        BookingRequest testRequest = new BookingRequest();
        testRequest.setBookerName("test");

        FieldBookingDO bookingDO = mock(FieldBookingDO.class);
        when(fieldBookingRepository.save(bookingDO)).thenReturn(bookingDO);
        when(fieldBookingRepository.findById(1L)).thenReturn(Optional.of(bookingDO));
        FieldBooking expectResponse = mock(FieldBooking.class);
        when(fieldBookingDOToResponseConverter.convert(bookingDO)).thenReturn(expectResponse);
        assertEquals(expectResponse,bookingService.editBooking("1", testRequest));
        verify(bookingDO).setBookerName("test");
        verifyNoMoreInteractions(bookingDO);
    }

    @Test
    public void testEditBookingChangeBookerEmail() {
        BookingRequest testRequest = new BookingRequest();
        testRequest.setBookerEmail("test");

        FieldBookingDO bookingDO = mock(FieldBookingDO.class);
        when(fieldBookingRepository.save(bookingDO)).thenReturn(bookingDO);
        when(fieldBookingRepository.findById(1L)).thenReturn(Optional.of(bookingDO));
        FieldBooking expectResponse = mock(FieldBooking.class);
        when(fieldBookingDOToResponseConverter.convert(bookingDO)).thenReturn(expectResponse);
        assertEquals(expectResponse,bookingService.editBooking("1", testRequest));
        verify(bookingDO).setBookerEmail("test");
        verifyNoMoreInteractions(bookingDO);
    }

    @Test
    public void testEditBookingChangeBookerPhone() {
        BookingRequest testRequest = new BookingRequest();
        testRequest.setBookerPhone("test");

        FieldBookingDO bookingDO = mock(FieldBookingDO.class);
        when(fieldBookingRepository.save(bookingDO)).thenReturn(bookingDO);
        when(fieldBookingRepository.findById(1L)).thenReturn(Optional.of(bookingDO));
        FieldBooking expectResponse = mock(FieldBooking.class);
        when(fieldBookingDOToResponseConverter.convert(bookingDO)).thenReturn(expectResponse);
        assertEquals(expectResponse,bookingService.editBooking("1", testRequest));
        verify(bookingDO).setBookerPhone("test");
        verifyNoMoreInteractions(bookingDO);
    }

    @Test
    public void testEditBookingChangeDeposit() {
        BookingRequest testRequest = new BookingRequest();
        testRequest.setDepositAmount(Double.valueOf(100000.00));

        FieldBookingDO bookingDO = mock(FieldBookingDO.class);
        when(fieldBookingRepository.save(bookingDO)).thenReturn(bookingDO);
        when(fieldBookingRepository.findById(1L)).thenReturn(Optional.of(bookingDO));
        FieldBooking expectResponse = mock(FieldBooking.class);
        when(fieldBookingDOToResponseConverter.convert(bookingDO)).thenReturn(expectResponse);
        assertEquals(expectResponse,bookingService.editBooking("1", testRequest));
        verify(bookingDO).setDepositAmount(Double.valueOf(100000.00));
        verifyNoMoreInteractions(bookingDO);
    }

    @Test
    public void testEditBookingChangeActualCharge() {
        BookingRequest testRequest = new BookingRequest();
        testRequest.setActualChargedAmount(Double.valueOf(100000.00));

        FieldBookingDO bookingDO = mock(FieldBookingDO.class);
        when(fieldBookingRepository.save(bookingDO)).thenReturn(bookingDO);
        when(fieldBookingRepository.findById(1L)).thenReturn(Optional.of(bookingDO));
        FieldBooking expectResponse = mock(FieldBooking.class);
        when(fieldBookingDOToResponseConverter.convert(bookingDO)).thenReturn(expectResponse);
        assertEquals(expectResponse,bookingService.editBooking("1", testRequest));
        verify(bookingDO).setActualChargedAmount(Double.valueOf(100000.00));
        verifyNoMoreInteractions(bookingDO);
    }

    @Test
    public void testEditBookingChangePrice() {
        BookingRequest testRequest = new BookingRequest();
        testRequest.setPriceAmount(Double.valueOf(100000.00));

        FieldBookingDO bookingDO = mock(FieldBookingDO.class);
        when(fieldBookingRepository.save(bookingDO)).thenReturn(bookingDO);
        when(fieldBookingRepository.findById(1L)).thenReturn(Optional.of(bookingDO));
        FieldBooking expectResponse = mock(FieldBooking.class);
        when(fieldBookingDOToResponseConverter.convert(bookingDO)).thenReturn(expectResponse);
        assertEquals(expectResponse,bookingService.editBooking("1", testRequest));
        verify(bookingDO).setPriceAmount(Double.valueOf(100000.00));
        verifyNoMoreInteractions(bookingDO);
    }

    @Test
    public void testEditBookingChangeRegularBooker() {
        BookingRequest testRequest = new BookingRequest();
        testRequest.setRegularBooker(true);

        FieldBookingDO bookingDO = mock(FieldBookingDO.class);
        when(fieldBookingRepository.save(bookingDO)).thenReturn(bookingDO);
        when(fieldBookingRepository.findById(1L)).thenReturn(Optional.of(bookingDO));
        FieldBooking expectResponse = mock(FieldBooking.class);
        when(fieldBookingDOToResponseConverter.convert(bookingDO)).thenReturn(expectResponse);
        assertEquals(expectResponse,bookingService.editBooking("1", testRequest));
        verify(bookingDO).setRegularBooker(true);
        verifyNoMoreInteractions(bookingDO);
    }

    @Test
    public void testEditBookingDetailOneDetail() {
        BookingRequest testRequest = new BookingRequest();
        List<BookingDetailRequest> detailList = new ArrayList<>();
        BookingDetailRequest detail = new BookingDetailRequest();
        detail.setFieldTypeId(1L);
        detail.setFieldId(3L);
        detail.setStatus(BookingStatusEnum.RESERVED);
        detail.setAdminNote("test");
        detail.setTimeIn("2019-10-06T22:50:15Z");
        detail.setActualTimeIn("2019-10-06T22:55:15Z");
        detail.setTimeOut("2019-10-06T23:50:15Z");
        detail.setActualTimeOut("2019-10-06T23:50:15Z");
        detailList.add(detail);
        testRequest.setBookingDetails(detailList);

        FieldBookingDO bookingDO = mock(FieldBookingDO.class);
        when(bookingDO.getSubBookings()).thenReturn(new ArrayList<>());
        when(fieldBookingRepository.save(bookingDO)).thenReturn(bookingDO);
        when(fieldBookingRepository.findById(1L)).thenReturn(Optional.of(bookingDO));
        FieldBooking expectResponse = mock(FieldBooking.class);
        when(fieldBookingDOToResponseConverter.convert(bookingDO)).thenReturn(expectResponse);
        assertEquals(expectResponse,bookingService.editBooking("1", testRequest));
        verify(bookingDO).getSubBookings();
        verify(bookingDO).setFieldTypeId(1L);
        verify(bookingDO).setFieldId(3L);
        verify(bookingDO).setStatus(BookingStatusEnum.RESERVED);
        verify(bookingDO).setAdminNote("test");
        verify(bookingDO).setTimeIn(ZonedDateTime.of(LocalDateTime.of(2019, 10, 06, 22, 50, 15), (ZoneId.of("UTC"))));
        verify(bookingDO).setActualTimeIn(ZonedDateTime.of(LocalDateTime.of(2019, 10, 06, 22, 55, 15), (ZoneId.of("UTC"))));
        verify(bookingDO).setTimeOut(ZonedDateTime.of(LocalDateTime.of(2019, 10, 06, 23, 50, 15), (ZoneId.of("UTC"))));
        verify(bookingDO).setActualTimeOut(ZonedDateTime.of(LocalDateTime.of(2019, 10, 06, 23, 50, 15), (ZoneId.of("UTC"))));
        verifyNoMoreInteractions(bookingDO);
    }

    @Test
    public void testEditBookingDetailTwoDetailSame() {
        BookingRequest testRequest = new BookingRequest();
        List<BookingDetailRequest> detailList = new ArrayList<>();

        BookingDetailRequest detail1 = new BookingDetailRequest();
        detailList.add(detail1);
        BookingDetailRequest detail2 = new BookingDetailRequest();
        detail2.setFieldTypeId(1L);
        detail2.setFieldId(3L);
        detail2.setStatus(BookingStatusEnum.RESERVED);
        detail2.setAdminNote("test");
        detail2.setTimeIn("2019-10-06T22:50:15Z");
        detail2.setActualTimeIn("2019-10-06T22:55:15Z");
        detail2.setTimeOut("2019-10-06T23:50:15Z");
        detail2.setActualTimeOut("2019-10-06T23:50:15Z");
        detailList.add(detail2);
        testRequest.setBookingDetails(detailList);

        FieldBookingDO bookingDO = mock(FieldBookingDO.class);
        List<FieldBookingDO> subBookingList = new ArrayList<>();
        FieldBookingDO subDetail = mock(FieldBookingDO.class);
        subBookingList.add(subDetail);
        when(bookingDO.getSubBookings()).thenReturn(subBookingList);
        when(fieldBookingRepository.save(bookingDO)).thenReturn(bookingDO);
        when(fieldBookingRepository.findById(1L)).thenReturn(Optional.of(bookingDO));
        FieldBooking expectResponse = mock(FieldBooking.class);
        when(fieldBookingDOToResponseConverter.convert(bookingDO)).thenReturn(expectResponse);
        assertEquals(expectResponse,bookingService.editBooking("1", testRequest));
        verify(subDetail).setFieldTypeId(1L);
        verify(subDetail).setFieldId(3L);
        verify(subDetail).setStatus(BookingStatusEnum.RESERVED);
        verify(subDetail).setAdminNote("test");
        verify(subDetail).setTimeIn(ZonedDateTime.of(LocalDateTime.of(2019, 10, 06, 22, 50, 15), (ZoneId.of("UTC"))));
        verify(subDetail).setActualTimeIn(ZonedDateTime.of(LocalDateTime.of(2019, 10, 06, 22, 55, 15), (ZoneId.of("UTC"))));
        verify(subDetail).setTimeOut(ZonedDateTime.of(LocalDateTime.of(2019, 10, 06, 23, 50, 15), (ZoneId.of("UTC"))));
        verify(subDetail).setActualTimeOut(ZonedDateTime.of(LocalDateTime.of(2019, 10, 06, 23, 50, 15), (ZoneId.of("UTC"))));
        verifyNoMoreInteractions(subDetail);
    }

    @Test
    public void testEditBookingDetailMultipleDetailAdded() {
        BookingRequest testRequest = new BookingRequest();
        List<BookingDetailRequest> detailList = new ArrayList<>();

        detailList.add(new BookingDetailRequest());
        detailList.add(new BookingDetailRequest());
        detailList.add(new BookingDetailRequest());
        testRequest.setBookingDetails(detailList);

        FieldBookingDO bookingDO = mock(FieldBookingDO.class);
        List<FieldBookingDO> subBookingList = new ArrayList<>();
        FieldBookingDO subDetail = mock(FieldBookingDO.class);
        subBookingList.add(subDetail);
        when(bookingDO.getSubBookings()).thenReturn(subBookingList);
        when(fieldBookingRepository.save(bookingDO)).thenReturn(bookingDO);
        when(fieldBookingRepository.findById(1L)).thenReturn(Optional.of(bookingDO));
        FieldBooking expectResponse = mock(FieldBooking.class);
        when(fieldBookingDOToResponseConverter.convert(bookingDO)).thenReturn(expectResponse);
        assertEquals(expectResponse,bookingService.editBooking("1", testRequest));
        verify(bookingDO).getSubBookings();
        verify(bookingDO).addSubBooking(any(FieldBookingDO.class));
    }

    @Test
    public void testEditBookingDetailMultipleDetailRemoved() {
        BookingRequest testRequest = new BookingRequest();
        List<BookingDetailRequest> detailList = new ArrayList<>();

        detailList.add(new BookingDetailRequest());
        detailList.add(new BookingDetailRequest());
        testRequest.setBookingDetails(detailList);

        FieldBookingDO bookingDO = mock(FieldBookingDO.class);
        List<FieldBookingDO> subBookingList = new ArrayList<>();
        subBookingList.add(mock(FieldBookingDO.class));
        subBookingList.add(mock(FieldBookingDO.class));
        when(bookingDO.getSubBookings()).thenReturn(subBookingList);
        when(fieldBookingRepository.save(bookingDO)).thenReturn(bookingDO);
        when(fieldBookingRepository.findById(1L)).thenReturn(Optional.of(bookingDO));
        FieldBooking expectResponse = mock(FieldBooking.class);
        when(fieldBookingDOToResponseConverter.convert(bookingDO)).thenReturn(expectResponse);
        assertEquals(expectResponse,bookingService.editBooking("1", testRequest));
        verify(bookingDO).getSubBookings();
        assertEquals(1, subBookingList.size());
    }
}
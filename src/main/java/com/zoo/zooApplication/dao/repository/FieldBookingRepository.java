package com.zoo.zooApplication.dao.repository;

import com.zoo.zooApplication.dao.model.FieldBookingDO;
import com.zoo.zooApplication.response.FieldBooking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface FieldBookingRepository extends JpaRepository<FieldBookingDO, Long> {

    @Query(value = "SELECT * FROM field_bookings fb WHERE fb.court_id = ?1 AND time_in >= ?2 AND time_in <= ?3 AND parent_booking_id is NULL",
    countQuery = "SELECT count(*) FROM field_bookings fb WHERE fb.court_id = ?1 AND parent_booking_id is NULL",
    nativeQuery = true)
    Page<FieldBookingDO> findByCourtIdWithTimeRange
            (Long courtId, Long lowerBound, Long upperBound, Pageable pageable);

}

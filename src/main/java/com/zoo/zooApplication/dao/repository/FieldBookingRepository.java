package com.zoo.zooApplication.dao.repository;

import com.zoo.zooApplication.dao.model.FieldBookingDO;
import com.zoo.zooApplication.response.FieldBooking;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface FieldBookingRepository extends JpaRepository<FieldBookingDO, Long> {

    List<FieldBookingDO> findByBookerPhoneOrBookerEmailAndTimeInGreaterThanEqual
            (String bookerEmail, String bookerPhone, ZonedDateTime timeIn, Pageable pageable);
}

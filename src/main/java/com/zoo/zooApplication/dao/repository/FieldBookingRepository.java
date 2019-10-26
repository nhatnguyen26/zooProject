package com.zoo.zooApplication.dao.repository;

import com.zoo.zooApplication.dao.model.FieldBookingDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FieldBookingRepository extends JpaRepository<FieldBookingDO, Long> {

	// when find by id, fetch subBookings as eager
	@EntityGraph(attributePaths = {"subBookings"})
	@Override
	Optional<FieldBookingDO> findById(Long aLong);

	@Query(value = "SELECT * FROM field_bookings fb WHERE fb.court_id = ?1 AND fb.time_in >= ?2 AND fb.time_in <= ?3 AND fb.parent_booking_id is NULL ORDER BY fb.time_in ASC, fb.id ASC",
		countQuery = "SELECT count(*) FROM field_bookings fb WHERE fb.court_id = ?1 AND fb.time_in >= ?2 AND fb.time_in <= ?3 AND fb.parent_booking_id is NULL",
		nativeQuery = true)
	Page<FieldBookingDO> findByCourtIdWithTimeRange
		(Long courtId, Long lowerBound, Long upperBound, Pageable pageable);

	@Query(value = "SELECT * FROM field_bookings fb WHERE fb.court_id = ?1 AND fb.main_field_type = ?2 AND fb.time_in >= ?3 AND fb.time_in <= ?4 AND fb.parent_booking_id is NULL ORDER BY fb.time_in ASC, fb.id ASC",
		countQuery = "SELECT count(*) FROM field_bookings fb WHERE fb.court_id = ?1 AND fb.main_field_type = ?2 AND fb.time_in >= ?3 AND fb.time_in <= ?4 AND fb.parent_booking_id is NULL",
		nativeQuery = true)
	Page<FieldBookingDO> findByCourtIdAndMainFieldTypeWithTimeRange
		(Long courtId, Integer mainFieldTypeId, Long lowerBound, Long upperBound, Pageable pageable);


}

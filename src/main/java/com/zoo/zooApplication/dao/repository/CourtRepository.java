package com.zoo.zooApplication.dao.repository;

import com.zoo.zooApplication.dao.model.CourtDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourtRepository extends JpaRepository<CourtDO, Long> {
	List<CourtDO> findByIdIn(List<Long> allCourtId);
}

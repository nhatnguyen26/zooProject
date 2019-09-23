package com.zoo.zooApplication.dao.repository;

import com.zoo.zooApplication.dao.model.FieldTypeDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldTypeRepository extends JpaRepository<FieldTypeDO, Long> {
	List<FieldTypeDO> findAllByCourtId(Long courtId);
}

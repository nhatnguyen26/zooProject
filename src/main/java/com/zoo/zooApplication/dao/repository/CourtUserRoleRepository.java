package com.zoo.zooApplication.dao.repository;

import com.zoo.zooApplication.dao.model.CourtUserRoleDO;
import com.zoo.zooApplication.dao.model.CourtUserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourtUserRoleRepository extends JpaRepository<CourtUserRoleDO, CourtUserRoleId>, CourtUserRoleRepositoryCustom {
	List<CourtUserRoleDO> findByUid(String uid);

	void deleteByCourtId(Long courtId);
}

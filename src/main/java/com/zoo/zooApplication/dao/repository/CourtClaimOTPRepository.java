package com.zoo.zooApplication.dao.repository;

import com.zoo.zooApplication.dao.model.CourtClaimOTPDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourtClaimOTPRepository extends JpaRepository<CourtClaimOTPDO, Long> {
	Optional<CourtClaimOTPDO> findByClaimKey(String claimKey);

	void deleteByCourtId(Long id);
}

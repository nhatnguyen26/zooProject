package com.zoo.zooApplication.dao.repository;

import com.zoo.zooApplication.dao.model.CourtUserRoleDO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * CourtUserRoleRepository is not extend JPA since JPARepository because JPA only allow save (update or save)
 * And the courtUserRoleId is not generated so it will always do a select before insert and is not so efficient and might cause unwanted updated
 */
@Repository
@Transactional
public class CourtUserRoleRepositoryCustomImpl implements CourtUserRoleRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;

	public void insert(CourtUserRoleDO courtUserRoleDO) {
		this.entityManager.persist(courtUserRoleDO);
	}
}

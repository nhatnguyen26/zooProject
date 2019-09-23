package com.zoo.zooApplication.dao.model;

import com.zoo.zooApplication.dao.util.CourtRoleEnumConverter;
import com.zoo.zooApplication.dao.util.DOTimestampConverter;
import com.zoo.zooApplication.type.CourtRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Entity
@IdClass(CourtUserRoleId.class)
@Table(name = "court_user_roles")
@Getter
@Setter
@Builder
@AllArgsConstructor // require for @Builder to work correctly
@NoArgsConstructor // required for hibernate mapping
public class CourtUserRoleDO {

	@Id
	private String uid;

	@Id
	private Long courtId;

	@Column
	@Convert(converter = CourtRoleEnumConverter.class)
	private CourtRoleEnum courtRole;

	@Column(nullable = false)
	@Convert(converter = DOTimestampConverter.class)
	@CreationTimestamp
	private ZonedDateTime createdAt;

	@Column(nullable = false)
	@Convert(converter = DOTimestampConverter.class)
	@UpdateTimestamp
	private ZonedDateTime updatedAt;
}

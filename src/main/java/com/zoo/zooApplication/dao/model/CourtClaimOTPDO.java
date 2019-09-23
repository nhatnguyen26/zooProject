package com.zoo.zooApplication.dao.model;

import com.zoo.zooApplication.dao.util.ClaimKeyGenerator;
import com.zoo.zooApplication.dao.util.DOTimestampConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Entity
@Table(name = "court_claim_otps")
@Getter
@Setter
@Builder
@AllArgsConstructor // require for @Builder to work correctly
@NoArgsConstructor // required for hibernate mapping
@DynamicUpdate
@SelectBeforeUpdate(false)
public class CourtClaimOTPDO {

	@Id
	private Long courtId;

	@Column
	@GeneratorType(type = ClaimKeyGenerator.class, when = GenerationTime.INSERT)
	private String claimKey;

	@Column(nullable = false)
	@Convert(converter = DOTimestampConverter.class)
	@CreationTimestamp
	private ZonedDateTime createdAt;

	@Column(nullable = false)
	@Convert(converter = DOTimestampConverter.class)
	@UpdateTimestamp
	private ZonedDateTime updatedAt;
}

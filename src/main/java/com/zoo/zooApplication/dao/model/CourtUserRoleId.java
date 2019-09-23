package com.zoo.zooApplication.dao.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor // require for @Builder to work correctly
@NoArgsConstructor
public class CourtUserRoleId implements Serializable {

	private String uid;

	private Long courtId;

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(uid)
			.append(courtId)
			.build();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof  CourtUserRoleId)) {
			return false;
		}
		CourtUserRoleId courtUserRoleId = (CourtUserRoleId) obj;
		return new EqualsBuilder()
			.appendSuper(super.equals(obj))
			.append(this.uid, courtUserRoleId.uid)
			.append(this.courtId, courtUserRoleId.courtId)
			.build();
	}
}

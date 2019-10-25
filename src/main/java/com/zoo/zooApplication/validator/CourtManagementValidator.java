package com.zoo.zooApplication.validator;

import com.zoo.zooApplication.dao.model.CourtDO;
import com.zoo.zooApplication.dao.model.CourtUserRoleDO;
import com.zoo.zooApplication.dao.repository.CourtUserRoleRepository;
import com.zoo.zooApplication.exception.ForbiddenAccessException;
import com.zoo.zooApplication.exception.InvalidRequestException;
import com.zoo.zooApplication.request.CreateFieldRequest;
import com.zoo.zooApplication.request.FieldRequest;
import com.zoo.zooApplication.type.CourtRoleEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CourtManagementValidator {

	private CourtUserRoleRepository courtUserRoleRepository;

	@Inject
	public CourtManagementValidator(CourtUserRoleRepository courtUserRoleRepository) {
		this.courtUserRoleRepository = courtUserRoleRepository;
	}

	public void validateCreateFieldRequest(Optional<CourtDO> courtDOOptional, CreateFieldRequest createFieldRequest) {
		if (courtDOOptional.isEmpty()) {
			throw new InvalidRequestException(1000, "Can not find requested court");
		}

		CourtDO courtDO = courtDOOptional.get();

		List<CourtUserRoleDO> courtUserHasAccess = courtUserRoleRepository.findByUid(createFieldRequest.getUid());
		Optional<CourtUserRoleDO> courtAccess = courtUserHasAccess
			.stream()
			.filter(courtRole -> courtRole.getCourtId().equals(courtDO.getId()))
			.findFirst();
		if (courtAccess.isEmpty() || courtAccess.get().getCourtRole() != CourtRoleEnum.OWNER) {
			throw new ForbiddenAccessException(1001, "Only owner can modify court details");
		}

		for (FieldRequest fieldRequest : createFieldRequest.getFieldRequests()) {
			if (new HashSet<>(fieldRequest.getSubFieldIds()).size() < 2) {
				throw new InvalidRequestException(1002, String.format("Sub fields need to be more than two uniques field"));
			}
		}

		// validate sub field ids
		Set<Long> allPresentFieldId = courtDO.getFields()
			.stream()
			.map(fieldDO -> fieldDO.getId())
			.collect(Collectors.toSet());
		Set<Long> invalidIds = createFieldRequest.getFieldRequests()
			.stream()
			.flatMap(request -> CollectionUtils.emptyIfNull(request.getSubFieldIds()).stream())
			.filter(id -> !allPresentFieldId.contains(id))
			.collect(Collectors.toSet());
		if (CollectionUtils.isNotEmpty(invalidIds)) {
			throw new InvalidRequestException(1003, String.format("Sub fields are not valid: %s", StringUtils.join(invalidIds, ",")));
		}


	}
}

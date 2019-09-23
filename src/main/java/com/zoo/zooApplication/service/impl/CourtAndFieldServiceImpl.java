package com.zoo.zooApplication.service.impl;

import com.zoo.zooApplication.converter.CourtDOToResponseConverter;
import com.zoo.zooApplication.converter.FieldDOToResponseConverter;
import com.zoo.zooApplication.converter.FieldTypeDOToResponseConverter;
import com.zoo.zooApplication.dao.model.CourtClaimOTPDO;
import com.zoo.zooApplication.dao.model.CourtDO;
import com.zoo.zooApplication.dao.model.CourtUserRoleDO;
import com.zoo.zooApplication.dao.model.FieldDO;
import com.zoo.zooApplication.dao.model.FieldTypeDO;
import com.zoo.zooApplication.dao.model.PriceChartDO;
import com.zoo.zooApplication.dao.repository.CourtClaimOTPRepository;
import com.zoo.zooApplication.dao.repository.CourtRepository;
import com.zoo.zooApplication.dao.repository.CourtUserRoleRepository;
import com.zoo.zooApplication.dao.repository.FieldRepository;
import com.zoo.zooApplication.dao.repository.FieldTypeRepository;
import com.zoo.zooApplication.request.ClaimKeyRequest;
import com.zoo.zooApplication.request.CreateCourtRequest;
import com.zoo.zooApplication.request.CreateFieldRequest;
import com.zoo.zooApplication.request.CreateFieldTypeRequest;
import com.zoo.zooApplication.request.FieldRequest;
import com.zoo.zooApplication.request.FieldTypeRequest;
import com.zoo.zooApplication.request.PriceChartRequest;
import com.zoo.zooApplication.response.ClaimKey;
import com.zoo.zooApplication.response.Court;
import com.zoo.zooApplication.response.CourtsResponse;
import com.zoo.zooApplication.response.Field;
import com.zoo.zooApplication.response.FieldResponse;
import com.zoo.zooApplication.response.FieldType;
import com.zoo.zooApplication.response.FieldTypeResponse;
import com.zoo.zooApplication.service.CourtAndFieldService;
import com.zoo.zooApplication.type.CourtRoleEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourtAndFieldServiceImpl implements CourtAndFieldService {

	// NOTE: ideally the design is fit for nosql db but initially use sql as prototype but it make good sense for court and field to be belong to a same service
	private CourtRepository courtRepository;

	private CourtClaimOTPRepository courtClaimOTPRepository;

	private FieldRepository fieldRepository;

	private FieldTypeRepository fieldTypeRepository;

	private CourtUserRoleRepository courtUserRoleRepository;

	private CourtDOToResponseConverter courtDOToResponseConverter;

	private FieldDOToResponseConverter fieldDOToResponseConverter;

	private FieldTypeDOToResponseConverter fieldTypeDOToResponseConverter;

	@Inject
	public CourtAndFieldServiceImpl(CourtRepository courtRepository,
									CourtClaimOTPRepository courtClaimOTPRepository,
									FieldRepository fieldRepository,
									FieldTypeRepository fieldTypeRepository,
									CourtUserRoleRepository courtUserRoleRepository,
									CourtDOToResponseConverter courtDOToResponseConverter,
									FieldDOToResponseConverter fieldDOToResponseConverter,
									FieldTypeDOToResponseConverter fieldTypeDOToResponseConverter) {
		this.courtRepository = courtRepository;
		this.courtClaimOTPRepository = courtClaimOTPRepository;
		this.fieldRepository = fieldRepository;
		this.courtUserRoleRepository = courtUserRoleRepository;
		this.courtDOToResponseConverter = courtDOToResponseConverter;
		this.fieldTypeRepository = fieldTypeRepository;
		this.fieldDOToResponseConverter = fieldDOToResponseConverter;
		this.fieldTypeDOToResponseConverter = fieldTypeDOToResponseConverter;
	}

	@Transactional
	@Override
	public Court createCourt(CreateCourtRequest createCourtRequest) {
		CourtDO courtDO = CourtDO.builder()
			.name(createCourtRequest.getName())
			.addressStreet(createCourtRequest.getAddressStreet())
			.addressWard(createCourtRequest.getAddressWard())
			.addressDistrict(createCourtRequest.getAddressDistrict())
			.addressCity(createCourtRequest.getAddressCity())
			.phoneNumber(createCourtRequest.getPhoneNumber())
			.addressCountry("VN") // hard code to Viet Nam for now
			.build();
		courtDO = courtRepository.save(courtDO);
		CourtClaimOTPDO courtClaimOTPDO = CourtClaimOTPDO.builder()
			.courtId(courtDO.getId())
			.build();
		courtClaimOTPRepository.save(courtClaimOTPDO);

		Court court = courtDOToResponseConverter.convert(courtDO);
		return court;
	}

	@Transactional
	@Override
	public Court editCourt(String courtId, CreateCourtRequest createCourtRequest) {
		Optional<CourtDO> court = courtRepository.findById(NumberUtils.toLong(courtId));

		return court
			.map(courtDO -> editCourtInfo(courtDO, createCourtRequest))
			.map(courtRepository::save)
			.map(courtDOToResponseConverter::convert)
			.orElse(null);
	}

	private CourtDO editCourtInfo(CourtDO courtDO, CreateCourtRequest createCourtRequest) {
		if (StringUtils.isNotBlank(createCourtRequest.getName())) {
			courtDO.setName(createCourtRequest.getName());
		}

		if (StringUtils.isNotBlank(createCourtRequest.getAddressStreet())) {
			courtDO.setAddressStreet(createCourtRequest.getAddressStreet());
		}

		if (StringUtils.isNotBlank(createCourtRequest.getAddressWard())) {
			courtDO.setAddressWard(createCourtRequest.getAddressWard());
		}

		if (StringUtils.isNotBlank(createCourtRequest.getAddressDistrict())) {
			courtDO.setAddressDistrict(createCourtRequest.getAddressDistrict());
		}

		if (StringUtils.isNotBlank(createCourtRequest.getAddressCity())) {
			courtDO.setAddressCity(createCourtRequest.getAddressCity());
		}

		if (StringUtils.isNotBlank(createCourtRequest.getPhoneNumber())) {
			courtDO.setPhoneNumber(createCourtRequest.getPhoneNumber());
		}

		return courtDO;
	}

	@Transactional
	@Override
	public Court deleteCourt(String courtId) {
		Optional<CourtDO> court = courtRepository.findById(NumberUtils.toLong(courtId));
		if (court.isPresent()) {
			CourtDO courtDO = court.get();
			courtRepository.delete(courtDO);
			courtClaimOTPRepository.deleteByCourtId(courtDO.getId());
			courtUserRoleRepository.deleteByCourtId(courtDO.getId());
			return courtDOToResponseConverter.convert(courtDO);
		}

		return null;
	}

	@Transactional(readOnly = true)
	@Override
	public Court findCourtById(String courtId) {
		Optional<CourtDO> court = courtRepository.findById(NumberUtils.toLong(courtId));
		return court
			.map(courtDOToResponseConverter::convert)
			.orElse(null);
	}

	@Transactional
	@Override
	public Court claimCourtAsOwner(ClaimKeyRequest claimRequest) {
		Optional<CourtClaimOTPDO> courtClaimOTPDO = courtClaimOTPRepository.findByClaimKey(claimRequest.getClaimKey());

		return courtClaimOTPDO
			.flatMap(courtClaim -> courtRepository.findById(courtClaim.getCourtId()))
			.map(courtDO -> claimCourtAsOwner(courtDO, claimRequest))
			.map(courtDOToResponseConverter::convert)
			.orElse(null);
	}

	private CourtDO claimCourtAsOwner(CourtDO courtDO, ClaimKeyRequest claimRequest) {
		CourtUserRoleDO courtUserRoleDO = CourtUserRoleDO
			.builder()
			.courtId(courtDO.getId())
			.uid(claimRequest.getUid())
			.courtRole(CourtRoleEnum.OWNER)
			.build();
		courtUserRoleRepository.insert(courtUserRoleDO);
		courtClaimOTPRepository.deleteById(courtDO.getId());
		return courtDO;
	}

	@Transactional(readOnly = true)
	@Override
	public CourtsResponse findAllCourtManageByUser(String uid) {
		List<CourtUserRoleDO> allCourtUserDO = courtUserRoleRepository.findByUid(uid);
		List<Long> allCourtId = allCourtUserDO
			.stream()
			.map(courtUserRoleDO -> courtUserRoleDO.getCourtId())
			.collect(Collectors.toList());
		List<CourtDO> allCourtDO = courtRepository.findByIdIn(allCourtId);
		List<Court> allCourt = allCourtDO
			.stream()
			.map(courtDOToResponseConverter::convert)
			.collect(Collectors.toList());
		return CourtsResponse
			.builder()
			.courts(allCourt)
			.build();
	}

	@Transactional(readOnly = true)
	@Override
	public Court findCourtByClaimKey(String claimKey) {
		Optional<CourtClaimOTPDO> courtClaimOTPDOOpt = courtClaimOTPRepository.findByClaimKey(claimKey);
		Optional<CourtDO> courtDO = courtClaimOTPDOOpt
			.map(courtClaimOTPDO -> courtClaimOTPDO.getCourtId())
			.flatMap(courtId -> courtRepository.findById(courtId));
		return courtDO
			.map(courtDOToResponseConverter::convert)
			.orElse(null);
	}

	@Transactional(readOnly = true)
	@Override
	public ClaimKey findClaimKeyByCourtId(String courtId) {
		Optional<CourtClaimOTPDO> courtClaimOTP = courtClaimOTPRepository.findById(NumberUtils.toLong(courtId));
		String claimKey = courtClaimOTP
			.map(courtClaimOTPDO -> courtClaimOTPDO.getClaimKey())
			.orElse(null);
		return ClaimKey
			.builder()
			.claimKey(claimKey)
			.build();
	}

	@Transactional
	@Override
	public Court addFieldToCourt(String courtId, CreateFieldRequest createFieldRequest) {
		// TODO: eventually, will need to validate CreateFieldRequest, right now assume input correct
		Optional<CourtDO> court = courtRepository.findById(NumberUtils.toLong(courtId));

		return court
			.map(courtDO -> addFieldDO(courtDO, createFieldRequest.getFieldRequests()))
			.map(courtRepository::save)
			.map(courtDOToResponseConverter::convert)
			.orElse(null);

	}

	private CourtDO addFieldDO(CourtDO courtDO, List<FieldRequest> fieldRequests) {
		if (CollectionUtils.isNotEmpty(fieldRequests)) {
			fieldRequests
				.stream()
				.map(request -> FieldDO
					.builder()
					.name(request.getName())
					.mainFieldType(request.getMainFieldType())
					.fieldTypeId(request.getFieldTypeId())
					.subFieldIds(request.getSubFieldIds())
					.build())
				.forEach(fieldDO -> courtDO.addField(fieldDO));
		}
		return courtDO;
	}

	@Transactional(readOnly = true)
	@Override
	public FieldResponse getAllFieldsInCourt(String courtId) {
		List<FieldDO> fieldDOList = fieldRepository.findAllByCourtId(NumberUtils.toLong(courtId));
		return fieldDOToResponseConverter.convert(fieldDOList);
	}

	@Transactional
	@Override
	public Field editField(String courtId, String fieldId, FieldRequest fieldRequest) {
		Optional<CourtDO> court = courtRepository.findById(NumberUtils.toLong(courtId));

		return court
			.flatMap(courtDO -> courtDO.findFieldById(NumberUtils.toLong(fieldId)))
			.map(fieldDO -> editFieldDOInfo(fieldDO, fieldRequest))
			.map(fieldRepository::save)
			.map(fieldDOToResponseConverter::convert)
			.orElse(null);
	}

	private FieldDO editFieldDOInfo(FieldDO fieldDO, FieldRequest fieldRequest) {
		// TODO: same as creation, assume info is correct and valid, need validation and proper handling if the api is more open
		if (StringUtils.isNotBlank(fieldRequest.getName())) {
			fieldDO.setName(fieldRequest.getName());
		}

		if (fieldRequest.getMainFieldType() != null) {
			fieldDO.setMainFieldType(fieldRequest.getMainFieldType());
		}

		if (fieldRequest.getFieldTypeId() != null) {
			fieldDO.setFieldTypeId(fieldRequest.getFieldTypeId());
		}

		if (CollectionUtils.isNotEmpty(fieldRequest.getSubFieldIds())) {
			fieldDO.setSubFieldIds(fieldRequest.getSubFieldIds());
		}
		return fieldDO;
	}

	@Transactional
	@Override
	public Field deleteField(String courtId, String fieldId) {
		Optional<CourtDO> court = courtRepository.findById(NumberUtils.toLong(courtId));

		return court
			.flatMap(courtDO -> findFieldAndDeleteFromCourt(courtDO, fieldId))
			.map(fieldDOToResponseConverter::convert)
			.orElse(null);
	}

	private Optional<FieldDO> findFieldAndDeleteFromCourt(CourtDO courtDO, String fieldId) {
		Optional<FieldDO> fieldDOOptional = courtDO.findFieldById(NumberUtils.toLong(fieldId));
		if (fieldDOOptional.isPresent()) {
			courtDO
				.getFields()
				.removeIf(fieldDO -> fieldDO.getId().equals(NumberUtils.toLong(fieldId)));
			courtRepository.save(courtDO);
			return fieldDOOptional;
		}
		return Optional.empty();
	}

	@Transactional
	@Override
	public Court addFieldTypeToCourt(String courtId, CreateFieldTypeRequest createFieldTypeRequest) {
		Optional<CourtDO> court = courtRepository.findById(NumberUtils.toLong(courtId));

		return court
			.map(courtDO -> addFieldTypeDO(courtDO, createFieldTypeRequest.getFieldTypeRequests()))
			.map(courtRepository::save)
			.map(courtDOToResponseConverter::convert)
			.orElse(null);
	}

	private CourtDO addFieldTypeDO(CourtDO courtDO, List<FieldTypeRequest> fieldTypeRequests) {
		if (CollectionUtils.isNotEmpty(fieldTypeRequests)) {
			fieldTypeRequests
				.stream()
				.map(request -> buildFieldType(request))
				.forEach(fieldTypeDO -> courtDO.addFieldType(fieldTypeDO));
		}
		return courtDO;
	}

	private FieldTypeDO buildFieldType(FieldTypeRequest fieldTypeRequest) {
		FieldTypeDO fieldTypeDO = FieldTypeDO
			.builder()
			.name(fieldTypeRequest.getFieldTypeName())
			.build();

		if (CollectionUtils.isNotEmpty(fieldTypeRequest.getPriceChartRequests())) {
			fieldTypeRequest
				.getPriceChartRequests()
				.stream()
				.map(priceChartRequest -> buildPriceChart(priceChartRequest))
				.forEach(priceChartDO -> fieldTypeDO.addPriceChart(priceChartDO));

		}

		return fieldTypeDO;
	}

	private PriceChartDO buildPriceChart(PriceChartRequest priceChartRequest) {
		return PriceChartDO
			.builder()
			.timeStart(priceChartRequest.getTimeStart())
			.timeEnd(priceChartRequest.getTimeEnd())
			.priceAmount(priceChartRequest.getPriceAmount())
			.currencyId("VND")
			.build();
	}

	@Transactional(readOnly = true)
	@Override
	public FieldTypeResponse getAllFieldTypesInCourt(String courtId) {
		List<FieldTypeDO> fieldTypeDOList = fieldTypeRepository.findAllByCourtId(NumberUtils.toLong(courtId));
		return fieldTypeDOToResponseConverter.convert(fieldTypeDOList);
	}

	@Transactional
	@Override
	public FieldType editFieldType(String courtId, String fieldTypeId, FieldTypeRequest fieldTypeRequest) {
		Optional<CourtDO> court = courtRepository.findById(NumberUtils.toLong(courtId));

		return court
			.flatMap(courtDO -> courtDO.findFieldTypeById(NumberUtils.toLong(fieldTypeId)))
			.map(fieldTypeDO -> editFieldTypeDOInfo(fieldTypeDO, fieldTypeRequest))
			.map(fieldTypeRepository::save)
			.map(fieldTypeDOToResponseConverter::convert)
			.orElse(null);
	}

	private FieldTypeDO editFieldTypeDOInfo(FieldTypeDO fieldTypeDO, FieldTypeRequest fieldTypeRequest) {
		if (StringUtils.isNotBlank(fieldTypeRequest.getFieldTypeName())) {
			fieldTypeDO.setName(fieldTypeRequest.getFieldTypeName());
		}

		// since price chart can be add/remove or edit in place, reuse object if possible
		// rather than complete remove then re-add, edit without checking diff for price chart
		if (CollectionUtils.isNotEmpty(fieldTypeRequest.getPriceChartRequests())) {
			List<PriceChartRequest> priceChartRequests = fieldTypeRequest.getPriceChartRequests();
			List<PriceChartDO> priceChartDOs = fieldTypeDO.getPriceCharts();
			int sizeDiff = priceChartDOs.size() - priceChartRequests.size();
			// if there is size diff, match the count for do
			for (int i = 0; i < Math.abs(sizeDiff); i++) {
				if (sizeDiff < 0) {
					// more price chart in request, add
					// add using fieldTypeDO to sync the mapping
					// the list is same
					fieldTypeDO.addPriceChart(PriceChartDO.builder().build());
				} else {
					priceChartDOs.remove(priceChartDOs.size() - 1);
				}
			}
			// now sync the info
			for (int i = 0; i < priceChartDOs.size(); i++) {
				priceChartDOs.get(i).setTimeStart(priceChartRequests.get(i).getTimeStart());
				priceChartDOs.get(i).setTimeEnd(priceChartRequests.get(i).getTimeEnd());
				priceChartDOs.get(i).setPriceAmount(priceChartRequests.get(i).getPriceAmount());
				priceChartDOs.get(i).setCurrencyId("VND");
			}

		}

		return fieldTypeDO;
	}

	@Transactional
	@Override
	public FieldType deleteFieldType(String courtId, String fieldTypeId) {
		Optional<CourtDO> court = courtRepository.findById(NumberUtils.toLong(courtId));

		return court
			.flatMap(courtDO -> findFieldTypeAndDeleteFromCourt(courtDO, fieldTypeId))
			.map(fieldTypeDOToResponseConverter::convert)
			.orElse(null);
	}

	private Optional<FieldTypeDO> findFieldTypeAndDeleteFromCourt(CourtDO courtDO, String fieldTypeId) {
		Optional<FieldTypeDO> fieldTypeDOOptional = courtDO.findFieldTypeById(NumberUtils.toLong(fieldTypeId));
		if (fieldTypeDOOptional.isPresent()) {
			courtDO
				.getFieldTypes()
				.removeIf(fieldTypeDO -> fieldTypeDO.getId().equals(NumberUtils.toLong(fieldTypeId)));
			courtRepository.save(courtDO);
			return fieldTypeDOOptional;
		}
		return Optional.empty();
	}

}

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
import com.zoo.zooApplication.dao.repository.PriceChartRepository;
import com.zoo.zooApplication.request.ClaimKeyRequest;
import com.zoo.zooApplication.request.CreateCourtRequest;
import com.zoo.zooApplication.request.CreateFieldRequest;
import com.zoo.zooApplication.request.CreateFieldTypeRequest;
import com.zoo.zooApplication.request.CreatePriceChartRequest;
import com.zoo.zooApplication.request.FieldRequest;
import com.zoo.zooApplication.request.FieldTypeRequest;
import com.zoo.zooApplication.request.PriceChartRequest;
import com.zoo.zooApplication.response.Court;
import com.zoo.zooApplication.response.Field;
import com.zoo.zooApplication.response.FieldType;
import com.zoo.zooApplication.service.CourtAndFieldService;
import com.zoo.zooApplication.type.CourtRoleEnum;
import com.zoo.zooApplication.util.DateTimeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Service
public class CourtAndFieldServiceImpl implements CourtAndFieldService {

    // NOTE: ideally the design is fit for nosql db but initially use sql as prototype but it make good sense for court and field to be belong to a same service
    private CourtRepository courtRepository;

    private CourtClaimOTPRepository courtClaimOTPRepository;

    private FieldRepository fieldRepository;

    private FieldTypeRepository fieldTypeRepository;

    private PriceChartRepository priceChartRepository;

    private CourtUserRoleRepository courtUserRoleRepository;

    private CourtDOToResponseConverter courtDOToResponseConverter;

    private FieldDOToResponseConverter fieldDOToResponseConverter;

    private FieldTypeDOToResponseConverter fieldTypeDOToResponseConverter;

    @Inject
    public CourtAndFieldServiceImpl(CourtRepository courtRepository, CourtClaimOTPRepository courtClaimOTPRepository, FieldRepository fieldRepository,
                                    FieldTypeRepository fieldTypeRepository, PriceChartRepository priceChartRepository,
                                    CourtUserRoleRepository courtUserRoleRepository, CourtDOToResponseConverter courtDOToResponseConverter, FieldDOToResponseConverter fieldDOToResponseConverter, FieldTypeDOToResponseConverter fieldTypeDOToResponseConverter) {
        this.courtRepository = courtRepository;
        this.courtClaimOTPRepository = courtClaimOTPRepository;
        this.fieldRepository = fieldRepository;
        this.courtUserRoleRepository = courtUserRoleRepository;
        this.courtDOToResponseConverter = courtDOToResponseConverter;
        this.fieldTypeRepository = fieldTypeRepository;
        this.priceChartRepository = priceChartRepository;
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

        Court court = courtDOToResponseConverter.convert(courtRepository.save(courtDO));
        CourtClaimOTPDO courtClaimOTPDO = CourtClaimOTPDO.builder()
            .courtId(court.getId())
            .build();
        courtClaimOTPRepository.save(courtClaimOTPDO);
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

    @Override
    public Court findCourtById(String courtId) {
        Optional<CourtDO> court = courtRepository.findById(NumberUtils.toLong(courtId));
        return court
                .map(courtDOToResponseConverter::convert)
                .orElse(null);
    }

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
                            .fieldTypeId(request.getFieldTypeId())
                            .subFieldIds(request.getSubFieldIds())
                            .build())
                    .forEach(fieldDO -> courtDO.addField(fieldDO));
        }
        return courtDO;
    }

    @Transactional
    @Override
    public Field editField(String courtId, String fieldId, FieldRequest fieldRequest) {
        Optional<CourtDO> court = courtRepository.findById(NumberUtils.toLong(courtId));

        return court
            .flatMap(courtDO -> courtDO.findFieldById(NumberUtils.toLong(fieldId)))
			.map(fieldDO -> editFieldDOInfo(fieldDO, court.get(), fieldRequest))
            .map(fieldRepository::save)
            .map(fieldDOToResponseConverter::convert)
            .orElse(null);
    }

	private FieldDO editFieldDOInfo(FieldDO fieldDO, CourtDO courtDO, FieldRequest fieldRequest) {
		// TODO: same as creation, assume info is correct and valid, need validation and proper handling if the api is more open
		if (StringUtils.isNotBlank(fieldRequest.getName())) {
			fieldDO.setName(fieldRequest.getName());
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

    @Override
    public Court addFieldTypeToCourt(String courtId, CreateFieldTypeRequest createFieldTypeRequest){
        Optional<CourtDO> court = courtRepository.findById(NumberUtils.toLong(courtId));

        return court
                .map(courtDO -> addFieldTypeDO(courtDO,createFieldTypeRequest.getFieldTypeRequestList()))
                .map(courtRepository::save)
                .map(courtDOToResponseConverter::convert)
                .orElse(null);
    }

    private CourtDO addFieldTypeDO(CourtDO courtDO, List<FieldTypeRequest> fieldTypeRequests) {
        if (CollectionUtils.isNotEmpty(fieldTypeRequests)) {
            fieldTypeRequests
                    .stream()
                    .map(request -> FieldTypeDO
                            .builder()
                            //.fieldType(MainFieldTypeEnum.getById(request.getMainType()))
                            .name(request.getFieldTypeName())
                            .build())
                    .forEach(fieldTypeDO -> courtDO.addFieldType(fieldTypeDO));
        }
        return courtDO;
    }

//    TODO
    @Override
    public FieldType addPriceChartToFieldType(String fieldTypeId, CreatePriceChartRequest createPriceChartRequest){
        Optional<FieldTypeDO> fieldType = fieldTypeRepository.findById(NumberUtils.toLong(fieldTypeId));

        return fieldType
                .map(fieldTypeDO -> addPriceChartDO(fieldTypeDO,createPriceChartRequest.getPriceChartRequests()))
                .map(fieldTypeRepository::save)
                .map(fieldTypeDOToResponseConverter::convert)
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

    private FieldTypeDO addPriceChartDO(FieldTypeDO fieldTypeDO, List<PriceChartRequest> priceChartRequest) {
        if (CollectionUtils.isNotEmpty(priceChartRequest)) {
            priceChartRequest
                    .stream()
                    .map(request -> PriceChartDO
                            .builder()
                            .priceAmount(request.getPriceAmount())
                            .timeStart(DateTimeUtil.parseISO8601TimeFormat(request.getTimeStart()))
                            .timeEnd(DateTimeUtil.parseISO8601TimeFormat(request.getTimeEnd()))
                            .build())
                    .forEach(priceChartDO -> fieldTypeDO.addPriceChart(priceChartDO));
        }
        return fieldTypeDO;
    }
}

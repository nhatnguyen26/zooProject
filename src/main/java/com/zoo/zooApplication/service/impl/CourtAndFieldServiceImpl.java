package com.zoo.zooApplication.service.impl;

import com.zoo.zooApplication.converter.CourtDOToResponseConverter;
import com.zoo.zooApplication.converter.FieldTypeDOToResponseConverter;
import com.zoo.zooApplication.dao.model.CourtDO;
import com.zoo.zooApplication.dao.model.FieldDO;
import com.zoo.zooApplication.dao.model.FieldTypeDO;
import com.zoo.zooApplication.dao.model.PriceChartDO;
import com.zoo.zooApplication.dao.repository.CourtRepository;
import com.zoo.zooApplication.dao.repository.FieldRepository;
import com.zoo.zooApplication.dao.repository.FieldTypeRepository;
import com.zoo.zooApplication.dao.repository.PriceChartRepository;
import com.zoo.zooApplication.request.*;
import com.zoo.zooApplication.response.Court;
import com.zoo.zooApplication.response.FieldType;
import com.zoo.zooApplication.service.CourtAndFieldService;
import com.zoo.zooApplication.util.DateTimeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Service
public class CourtAndFieldServiceImpl implements CourtAndFieldService {

    // NOTE: ideally the design is fit for nosql db but initially use sql as prototype but it make good sense for court and field to be belong to a same service
    private CourtRepository courtRepository;

    private FieldRepository fieldRepository;

    private FieldTypeRepository fieldTypeRepository;

    private PriceChartRepository priceChartRepository;

    private CourtDOToResponseConverter courtDOToResponseConverter;

    private FieldTypeDOToResponseConverter fieldTypeDOToResponseConverter;

    @Inject
    public CourtAndFieldServiceImpl(CourtRepository courtRepository, FieldRepository fieldRepository,
                                    FieldTypeRepository fieldTypeRepository, PriceChartRepository priceChartRepository,
                                    CourtDOToResponseConverter courtDOToResponseConverter, FieldTypeDOToResponseConverter fieldTypeDOToResponseConverter) {
        this.courtRepository = courtRepository;
        this.fieldRepository = fieldRepository;
        this.courtDOToResponseConverter = courtDOToResponseConverter;
        this.fieldTypeRepository = fieldTypeRepository;
        this.priceChartRepository = priceChartRepository;
        this.fieldTypeDOToResponseConverter = fieldTypeDOToResponseConverter;
    }

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
        return court;
    }

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

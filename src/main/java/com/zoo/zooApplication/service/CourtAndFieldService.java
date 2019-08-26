package com.zoo.zooApplication.service;


import com.zoo.zooApplication.request.ClaimKeyRequest;
import com.zoo.zooApplication.request.CreateCourtRequest;
import com.zoo.zooApplication.request.CreateFieldRequest;
import com.zoo.zooApplication.request.CreateFieldTypeRequest;
import com.zoo.zooApplication.request.CreatePriceChartRequest;
import com.zoo.zooApplication.request.FieldRequest;
import com.zoo.zooApplication.response.Court;
import com.zoo.zooApplication.response.Field;
import com.zoo.zooApplication.response.FieldType;

public interface CourtAndFieldService {
    Court createCourt(CreateCourtRequest createCourtRequest);
    Court findCourtById(String courtId);
    Court editCourt(String courtId, CreateCourtRequest createCourtRequest);

    Court addFieldToCourt(String courtId, CreateFieldRequest createFieldRequest);
    Field editField(String courtId, String fieldId, FieldRequest fieldRequest);
    Field deleteField(String courtId, String fieldId);

    Court addFieldTypeToCourt(String courtId, CreateFieldTypeRequest createFieldTypeRequest);
//    TODO unit test
    FieldType addPriceChartToFieldType(String fieldTypeId, CreatePriceChartRequest createPriceChartRequest);

	Court claimCourtAsOwner(ClaimKeyRequest claimRequest);
}

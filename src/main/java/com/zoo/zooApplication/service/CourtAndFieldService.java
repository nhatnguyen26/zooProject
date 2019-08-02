package com.zoo.zooApplication.service;


import com.zoo.zooApplication.request.CreateCourtRequest;
import com.zoo.zooApplication.request.CreateFieldRequest;
import com.zoo.zooApplication.request.CreateFieldTypeRequest;
import com.zoo.zooApplication.request.CreatePriceChartRequest;
import com.zoo.zooApplication.response.Court;
import com.zoo.zooApplication.response.FieldType;

public interface CourtAndFieldService {
    Court createCourt(CreateCourtRequest createCourtRequest);
    Court findCourtById(String courtId);
    Court addFieldToCourt(String courtId, CreateFieldRequest createFieldRequest);
    Court addFieldTypeToCourt(String courtId, CreateFieldTypeRequest createFieldTypeRequest);
//    TODO unit test
    FieldType addPriceChartToFieldType(String fieldTypeId, CreatePriceChartRequest createPriceChartRequest);

    Court editCourt(String courtId, CreateCourtRequest createCourtRequest);
}

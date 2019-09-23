package com.zoo.zooApplication.service;


import com.zoo.zooApplication.request.ClaimKeyRequest;
import com.zoo.zooApplication.request.CreateCourtRequest;
import com.zoo.zooApplication.request.CreateFieldRequest;
import com.zoo.zooApplication.request.CreateFieldTypeRequest;
import com.zoo.zooApplication.request.FieldRequest;
import com.zoo.zooApplication.request.FieldTypeRequest;
import com.zoo.zooApplication.response.ClaimKey;
import com.zoo.zooApplication.response.Court;
import com.zoo.zooApplication.response.CourtsResponse;
import com.zoo.zooApplication.response.Field;
import com.zoo.zooApplication.response.FieldResponse;
import com.zoo.zooApplication.response.FieldType;
import com.zoo.zooApplication.response.FieldTypeResponse;

public interface CourtAndFieldService {
	Court createCourt(CreateCourtRequest createCourtRequest);

	Court findCourtById(String courtId);

	Court editCourt(String courtId, CreateCourtRequest createCourtRequest);

	Court deleteCourt(String courtId);

	Court claimCourtAsOwner(ClaimKeyRequest claimRequest);

	CourtsResponse findAllCourtManageByUser(String uid);

	Court findCourtByClaimKey(String claimKey);

	ClaimKey findClaimKeyByCourtId(String courtId);

	Court addFieldToCourt(String courtId, CreateFieldRequest createFieldRequest);

	FieldResponse getAllFieldsInCourt(String courtId);

	Field editField(String courtId, String fieldId, FieldRequest fieldRequest);

	Field deleteField(String courtId, String fieldId);

	Court addFieldTypeToCourt(String courtId, CreateFieldTypeRequest createFieldTypeRequest);

	FieldTypeResponse getAllFieldTypesInCourt(String courtId);

	FieldType editFieldType(String courtId, String fieldTypeId, FieldTypeRequest fieldTypeRequest);

	FieldType deleteFieldType(String courtId, String fieldTypeId);

}

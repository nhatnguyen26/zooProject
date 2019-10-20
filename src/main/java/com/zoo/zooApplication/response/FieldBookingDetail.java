package com.zoo.zooApplication.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zoo.zooApplication.type.BookingStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Builder
@ApiModel(value = "FieldBookingDetail", description = "Represent a field booking")
public class FieldBookingDetail {

	// TODO this should be a detail object, use id for now as it should be stand-alone entity
	@ApiModelProperty(value = "The unique identifier of the field associate with the booking")
	private Long fieldId;

	// TODO this should be a detail object, use id for now as it should be stand-alone entity
	@ApiModelProperty(value = "The fieldTypeId of the booking, this should be long to the court and associate with correct field")
	private Long fieldTypeId;

	@ApiModelProperty(value = "Time for the booking to start in format YYYY-MM-ddTHH:mm:ssZ (ISO-8601) in UTC", example = "2019-10-06T22:39:10Z")
	private String timeIn;

	@ApiModelProperty(value = "The actual time when customers checkin format YYYY-MM-ddTHH:mm:ssZ", example = "2019-10-06T22:39:10Z")
	private String actualTimeIn;

	@ApiModelProperty(value = "Time for the booking to finish in format YYYY-MM-ddTHH:mm:ssZ", example = "2019-10-06T22:39:10Z")
	private String timeOut;

	@ApiModelProperty(value = "The actual time when customers checkout format YYYY-MM-ddTHH:mm:ssZ", example = "2019-10-06T22:39:10Z")
	private String actualTimeOut;

	@ApiModelProperty(value = "The status of the booking field", allowableValues = "RESERVED, CHECKED_IN, CHECKED_OUT, CANCELLED, HIDDEN")
	private BookingStatusEnum status;

	@ApiModelProperty(value = "admin note about this booking")
	private String adminNote;
}

package com.zoo.zooApplication.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zoo.zooApplication.type.BookingStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ApiModel(value = "BookingDetailRequest", description = "The booking detail")
public class BookingDetailRequest {

	@ApiModelProperty(value = "The unique identifier of the field associate with the booking")
	private Long fieldId;

	@ApiModelProperty(value = "The fieldTypeId of the booking, this should be long to the court and associate with correct field, this can be use for pricing or reference")
	private Long fieldTypeId;

	@ApiModelProperty(value = "Time for the booking to start in format YYYY-MM-ddTHH:mm:ssZ (ISO-8601) in UTC", example = "2019-10-06T22:39:10Z")
	private String timeIn;

	@ApiModelProperty(value = "Time for the booking to finish in format YYYY-MM-ddTHH:mm:ssZ", example = "2019-10-06T22:39:10Z")
	private String timeOut;

	@ApiModelProperty(value = "The status of the booking field", allowableValues = "RESERVED, CHECKED_IN, CHECKED_OUT, CANCELLED, HIDDEN")
	private BookingStatusEnum status;

	@ApiModelProperty(value = "The actual time when customers checkin format YYYY-MM-ddTHH:mm:ssZ", example = "2019-10-06T22:39:10Z")
	private String actualTimeIn;

	@ApiModelProperty(value = "The actual time when customers checkout format YYYY-MM-ddTHH:mm:ssZ", example = "2019-10-06T22:39:10Z")
	private String actualTimeOut;

	@ApiModelProperty(value = "admin note about this detail or the over all booking")
	private String adminNote;
}

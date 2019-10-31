package com.zoo.zooApplication.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Builder
@ApiModel(value = "FieldBookingResponse", description = "Represent a list of field booking entities")
public class FieldBookingResponse {

	@ApiModelProperty(value = "The list of fieldBookings", readOnly = true)
	private List<FieldBooking> fieldBookings;

	@ApiModelProperty(value = "pagination information", readOnly = true)
	private Pagination pagination;

}

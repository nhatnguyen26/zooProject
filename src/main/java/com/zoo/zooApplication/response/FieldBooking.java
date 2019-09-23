package com.zoo.zooApplication.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Builder
@ApiModel(value = "FieldBooking", description = "Represent a field booking")
public class FieldBooking {

    @ApiModelProperty(value = "The unique identifier of the booking", readOnly = true)
    private long id;

    @ApiModelProperty(value = "The identifier of the court this booking belong to", readOnly = true)
    private long courtId;

    @ApiModelProperty(value = "The identifier of the field this booking for", readOnly = true)
    private long fieldId;

    @ApiModelProperty(value = "The booker's email address of the relevant booking", readOnly = true)
    private String bookerEmail;

    @ApiModelProperty(value = "The booker's phone number of the relevant booking", readOnly = true)
    private String bookerPhone;
}

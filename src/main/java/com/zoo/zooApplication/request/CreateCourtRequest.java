package com.zoo.zooApplication.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ApiModel(value = "CreateCourtRequest", description = "The request to create a court")
public class CreateCourtRequest {

    @ApiModelProperty(value = "Business name of the court")
    private String name;

    @ApiModelProperty(value = "Business address of the court, street part")
    private String addressStreet;

    @ApiModelProperty(value = "Business address of the court, ward part")
    private String addressWard;

    @ApiModelProperty(value = "Business address of the court, district part")
    private String addressDistrict;

    @ApiModelProperty(value = "Business address of the court, city part")
    private String addressCity;

    @ApiModelProperty(value = "Business phone number of the court")
    private String phoneNumber;

}

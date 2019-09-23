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
@ApiModel(value = "Court", description = "Represent a court entity")
public class Court {

    @ApiModelProperty(value = "The unique identifier of the court", readOnly = true)
    private Long id;

    @ApiModelProperty(value = "The court name which is also the business name", readOnly = true)
    private String name;

    @ApiModelProperty(value = "The street address of the court", readOnly = true)
    private String addressStreet;

    @ApiModelProperty(value = "The ward address of the court", readOnly = true)
    private String addressWard;

    @ApiModelProperty(value = "The district address of the court", readOnly = true)
    private String addressDistrict;

    @ApiModelProperty(value = "The city address of the court", readOnly = true)
    private String addressCity;

    @ApiModelProperty(value = "The country address of the court", readOnly = true)
    private String addressCountry;

    @ApiModelProperty(value = "The phone number of the court", readOnly = true)
    private String phoneNumber;

    @ApiModelProperty(value = "The all the field belong to this court", readOnly = true)
    private List<Field> fields;

    @ApiModelProperty(value = "The all the field type belong to this court and price chart belong to this type", readOnly = true)
    private List<FieldType> fieldTypes;
}

package com.zoo.zooApplication.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ApiModel(value = "FieldTypeRequest", description = "The specific types of all available fields")
public class FieldTypeRequest {

    @ApiModelProperty(value = "The name of field's type")
    private String fieldTypeName;

    @ApiModelProperty(value = "The price charts for this type")
    private List<PriceChartRequest> priceChartRequests;
}

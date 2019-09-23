package com.zoo.zooApplication.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ApiModel(value = "PriceChartRequest", description = "The price chart info to add to court")
public class PriceChartRequest {

    @ApiModelProperty(value = "Price amount of booking. Default: VND")
    private Double priceAmount;

    @ApiModelProperty(value = "Starting time of the price in format HHMM", required = true)
    private Integer timeStart;

    @ApiModelProperty(value = "Ending time of the price in format HHMM", required = true)
    private Integer timeEnd;
}

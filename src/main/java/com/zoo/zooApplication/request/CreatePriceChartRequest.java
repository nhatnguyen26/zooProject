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
@ApiModel(value = "CreatePriceChartRequest", description = "The request to create price chart")
public class CreatePriceChartRequest {

    @ApiModelProperty(value = "List of price chart request to add to a court")
    private List<PriceChartRequest> priceChartRequests;
}

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
@ApiModel(value = "CreateFieldRequest", description = "The request to add field(s) to a court")
public class CreateFieldRequest {

    @ApiModelProperty(value = "List of field request to add to a court")
    private List<FieldRequest> fieldRequests;


}

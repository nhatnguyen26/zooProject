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
@ApiModel(value = "CreateFieldTypeRequest", description = "The request to create field type")
public class CreateFieldTypeRequest {

    @ApiModelProperty(value = "List of field type request to add to a court")
    private List<FieldTypeRequest> fieldTypeRequests;
}

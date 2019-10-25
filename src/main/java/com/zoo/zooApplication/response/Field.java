package com.zoo.zooApplication.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zoo.zooApplication.type.MainFieldTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Builder
@ApiModel(value = "Field", description = "Represent a field entity")
public class Field {

    @ApiModelProperty(value = "The unique identifier of the court", readOnly = true)
    private Long id;

    @ApiModelProperty(value = "The field name if available", readOnly = true)
    private String name;

    @ApiModelProperty(value = "The main field type (archetype) of this field", readOnly = true)
    private MainFieldTypeEnum mainFieldType;

    @ApiModelProperty(value = "The field type this field belong to", readOnly = true)
    private Long fieldTypeId;

    @ApiModelProperty(value = "The list of field id that combine to this field", readOnly = true)
    private List<Long> subFieldIds;

    @ApiModelProperty(value = "The list of field id that this field is a part of ", readOnly = true)
    private List<Long> partOfFieldIds;

    @ApiModelProperty(value = "The list of field id that this field has a mutually block, that is if this field is blocked, all those field will be blocked, vice versa, if any field from list block, this field is blocked", readOnly = true)
    private List<Long> coBlockingFieldIds;

}

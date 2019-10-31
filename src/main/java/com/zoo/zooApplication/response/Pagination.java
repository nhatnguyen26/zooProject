package com.zoo.zooApplication.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Getter
public class Pagination {

	@ApiModelProperty(value = "number of elements in current page", readOnly = true)
	private long size;

	@ApiModelProperty(value = "the total amount of element", readOnly = true)
	private long totalCount;

	@ApiModelProperty(value = "number of page", readOnly = true)
	private long pageCount;
}

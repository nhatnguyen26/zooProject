package com.zoo.zooApplication.request;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@Getter
@Setter
public class SearchFieldBookingRequest {

    @ApiModelProperty(value = "The unique identifier of the requested searching field ")
    private String fieldId;

    @ApiModelProperty(value = "The number of records returns per page ")
    private int limit;

    @ApiModelProperty(value = "The index of starting page ")
    private int offset;

    @ApiModelProperty(value = "The email address of the booker")
    private String bookerEmail;

    @ApiModelProperty(value = "The phone number of the booker")
    private String bookerPhone;

    @ApiModelProperty(value = "The starting time when customer occupies the field")
    private String timeIn;

    public Long getFieldId(){
        return NumberUtils.toLong(fieldId);
    }

    public Pageable getPageable(){
        return PageRequest.of(offset, limit);
    }
}

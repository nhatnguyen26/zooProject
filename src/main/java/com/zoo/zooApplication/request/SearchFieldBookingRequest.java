package com.zoo.zooApplication.request;

import com.zoo.zooApplication.type.MainFieldTypeEnum;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

@Getter
@Setter
public class SearchFieldBookingRequest {

    @QueryParam("courtId")
    @ApiParam(value = "filter by court id")
    private String courtId;

    @QueryParam("mainFieldType")
    @ApiParam(value = "filter by main field type")
    private MainFieldTypeEnum mainFieldType;

    @QueryParam("limit")
    @DefaultValue(value = "100")
    @ApiParam(value = "page size", defaultValue = "100")
    private int limit;

    @QueryParam("offset")
    @ApiParam(value = "pagination offset", defaultValue = "0")
    private int offset;

    @QueryParam("timeFrom")
    @ApiParam(value = "lower bound for time range in format YYYY-MM-ddTHH:mm:ssZ (ISO-8601) in UTC", example = "2019-10-06T22:39:10Z", required = true)
    private String timeFrom;

    @QueryParam("timeTo")
    @ApiParam(value = "upper bound for time range in format YYYY-MM-ddTHH:mm:ssZ (ISO-8601) in UTC", example = "2019-10-06T22:39:10Z", required = true)
    private String timeTo;

    public Pageable getPageable(){
        return PageRequest.of(offset, limit);
    }
}

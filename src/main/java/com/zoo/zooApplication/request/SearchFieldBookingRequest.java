package com.zoo.zooApplication.request;

import com.zoo.zooApplication.type.MainFieldTypeEnum;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

@Getter
@Setter
public class SearchFieldBookingRequest {

    @QueryParam("searchType")
    @DefaultValue(value = "COURT")
    @ApiParam(value = "search type, must have to decide the use case", defaultValue = "COURT", required = true)
    private SearchTypeEnum searchType;

    @QueryParam("courtId")
    @ApiParam(value = "filter by court id", required = true)
    private String courtId;

    @QueryParam("bookerEmail")
    @ApiParam(value = "lookup booking by email, has precedence over other filtering and ignore other ")
    private String bookerEmail;

    @QueryParam("bookerPhone")
    @ApiParam(value = "lookup booking by phone, has precedence over other filtering and ignore other ")
    private String bookerPhone;

    @QueryParam("mainFieldType")
    @ApiParam(value = "filter by main field type")
    private MainFieldTypeEnum mainFieldType;

    @QueryParam("pageSize")
    @DefaultValue(value = "100")
    @ApiParam(value = "page size", defaultValue = "100")
    private int pageSize;

    @QueryParam("page")
    @ApiParam(value = "pagination offset, 0-index", defaultValue = "0")
    private int page;

    @QueryParam("timeFrom")
    @ApiParam(value = "lower bound for time range in format YYYY-MM-ddTHH:mm:ssZ (ISO-8601) in UTC", example = "2019-10-06T22:39:10Z", required = true)
    private String timeFrom;

    @QueryParam("timeTo")
    @ApiParam(value = "upper bound for time range in format YYYY-MM-ddTHH:mm:ssZ (ISO-8601) in UTC", example = "2019-10-06T22:39:10Z", required = true)
    private String timeTo;

    public Pageable getPageable(){
        return PageRequest.of(page, pageSize);
    }

    public BookingSearchHintEnum getSearchHint() {
        BookingSearchHintEnum searchHint = null;
        if (searchType == SearchTypeEnum.COURT) {
            if (StringUtils.isNotBlank(bookerEmail)) {
                searchHint = BookingSearchHintEnum.COURT_BOOKER_EMAIL;
            } else if (StringUtils.isNotBlank(bookerPhone)) {
                searchHint = BookingSearchHintEnum.COURT_BOOKER_PHONE;
            } else if (mainFieldType != null) {
                searchHint = BookingSearchHintEnum.COURT_FILTER_BY_MAIN_FIELD_TYPE;
            } else {
                searchHint = BookingSearchHintEnum.COURT;
            }

        } else if (searchType == SearchTypeEnum.PLAYER) {
            // TODO implement
        }
        return searchHint;
    }

    public enum BookingSearchHintEnum {
        COURT,  // court overall look up, full calendar use case
        COURT_FILTER_BY_MAIN_FIELD_TYPE, // court overall loop up and filter by a main field type, use for booking calendar use case
        COURT_BOOKER_EMAIL, // look up by booker email, use case most likely to look up booking to do action against
        COURT_BOOKER_PHONE, // look up by booker phone
    }

    public enum  SearchTypeEnum {
        COURT, // use for court admin/owner to search booking, hint that court id must present and log in user must have access
        PLAYER, // use for player to search all of one's booking, some query param will got ignore
    }
}

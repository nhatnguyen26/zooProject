package com.zoo.zooApplication.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zoo.zooApplication.type.BookingStatusEnum;
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
@ApiModel(value = "FieldBooking", description = "Represent a field booking")
public class FieldBooking {

    @ApiModelProperty(value = "The unique identifier of the booking")
    private Long id;

    @ApiModelProperty(value = "The human friendly booking number for this booking")
    private String bookingNumber;

    // TODO this should be a detail object, use id for now as it should be stand-alone entity
    @ApiModelProperty(value = "The unique identifier of the court associate with the booking")
    private Long courtId;

    @ApiModelProperty(value = "If this booking is from regular booker")
    private Boolean regularBooker;

    @ApiModelProperty(value = "Name for booker")
    private String bookerName;

    @ApiModelProperty(value = "Email of the booker")
    private String bookerEmail;

    @ApiModelProperty(value = "Phone of the booker")
    private String bookerPhone;

    @ApiModelProperty(value = "Price amount calculated at the time of booking. Once set cannot be changed. Default: VND")
    private Double priceAmount;

    @ApiModelProperty(value = "If there is deposit for this booking. Default: VND")
    private Double depositAmount;

    @ApiModelProperty(value = "The actual amount got charged for this booking, there could be discount and extra. Default: VND")
    private Double actualChargedAmount;

    @ApiModelProperty(value = "currencyId used for all the amount of this booking")
    private String currencyId;

    @ApiModelProperty(value = "the main field type of this booking")
    private MainFieldTypeEnum mainFieldType;

    @ApiModelProperty(value = "the details of this booking")
    private List<FieldBookingDetail> bookingDetails;
}

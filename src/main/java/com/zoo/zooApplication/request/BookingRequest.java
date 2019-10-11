package com.zoo.zooApplication.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zoo.zooApplication.type.BookingStatusEnum;
import com.zoo.zooApplication.type.MainFieldTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ApiModel(value = "BookingRequest", description = "The request body of a booking")
public class BookingRequest {

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

    @ApiModelProperty(value = "The main field type of the booking")
    private MainFieldTypeEnum mainFieldType;

    @ApiModelProperty(value = "the booking details")
    private List<BookingDetailRequest> bookingDetails;
}

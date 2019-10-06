package com.zoo.zooApplication.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zoo.zooApplication.type.BookingStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ApiModel(value = "CreateBookingRequest", description = "The request to create a booking")
public class BookingRequest {

    @ApiModelProperty(value = "The unique identifier of the field associate with the booking, when fieldId is provided, courtId and fieldType will be ignored")
    private Long fieldId;

    @ApiModelProperty(value = "The unique identifier of the court associate with the booking, when courtId is provided a fieldType is also required")
    private Long courtId;

    @ApiModelProperty(value = "The fieldType of the booking, when this field is provided also required with courtId")
    private String fieldType;

    @ApiModelProperty(value = "Time for the booking to start in format YYYY-MM-ddTHH:mm:ssZ (ISO-8601)")
    private String timeIn;

    @ApiModelProperty(value = "The duration of taking the filed (in minutes)")
    private int duration;

    @ApiModelProperty(value = "Time for the booking to finish in format YYYY-MM-ddTHH:mm:ssZ")
    private String timeOut;

    @ApiModelProperty(value = "The status of the booking field archetype (Reserved/CheckIn/CheckOut/Cancelled/Hidden")
    private BookingStatusEnum status;

    @ApiModelProperty(value = "The actual time when customers checkin format YYYY-MM-ddTHH:mm:ssZ")
    private String actualTimeIn;

    @ApiModelProperty(value = "The actual time when customers checkout format YYYY-MM-ddTHH:mm:ssZ")
    private String actualTimeOut;

    @ApiModelProperty(value = "Name for booker")
    private String bookerName;

    @ApiModelProperty(value = "Email of the booker")
    private String bookerEmail;

    @ApiModelProperty(value = "Phone of the booker")
    private String bookerPhone;

    @ApiModelProperty(value = "Price amount of booking. Default: VND")
    private Double priceAmount;
}

package com.zoo.zooApplication.resource;

import com.zoo.zooApplication.request.CreateBookingRequest;
import com.zoo.zooApplication.request.SearchFieldBookingRequest;
import com.zoo.zooApplication.response.FieldBooking;
import com.zoo.zooApplication.service.BookingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("/v1/bookings")
@Api(value = "Operations relating to field bookings and reservations")
public class BookingResource {

    private BookingService bookingService;

    @Inject
    public BookingResource(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @ApiOperation(value = "get current version", response = String.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Resource found")})
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/version")
    public String getVersion() {
        return "0.0.1";
    }

    @ApiOperation(value = "find the field bookings by id")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Booking found", response = FieldBooking.class)})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{bookingId}")
    public FieldBooking findById(@PathParam("bookingId") String bookingId) {
        return bookingService.findBookingById(bookingId);
    }

    @ApiOperation(value = "create a booking entries")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Booking created", response = FieldBooking.class)})
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response createBooking(@RequestBody CreateBookingRequest bookingRequest, @Context UriInfo uriInfo) {
        FieldBooking bookingCreated = bookingService.createBooking(bookingRequest);
        URI locationURI = uriInfo
                .getAbsolutePathBuilder()
                .path(String.valueOf(bookingCreated.getId()))
                .build();
        return Response
                .created(locationURI)
                .entity(bookingCreated)
                .build();
    }

    @ApiOperation(value = "find the field bookings by field id", response = FieldBooking.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Resource found")})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/search")
    public List<FieldBooking> findByFieldId(@QueryParam("fieldId") String fieldId,
                                            @QueryParam("limit") int limit,
                                            @QueryParam("offset") int offset) {

        SearchFieldBookingRequest searchRequest =
                SearchFieldBookingRequest.builder()
                        .fieldId(fieldId)
                        .offset(offset)
                        .limit(limit)
                        .build();
        return bookingService.findAllBookingByFieldId(searchRequest);
    }

    @ApiOperation(value = "proceed to user information after time slot allocation")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Resource found")})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("searchByUserInfo")
    public List<FieldBooking> findByUserInfo
            (@QueryParam("bookerPhone") String bookerPhone,
             @QueryParam("bookerEmail") String bookerEmail,
             @QueryParam("limit") int limit,
             @QueryParam("offset") int offset){
        SearchFieldBookingRequest searchRequest =
                SearchFieldBookingRequest.builder()
                        .bookerEmail(bookerEmail)
                        .bookerPhone(bookerPhone)
                        .limit(limit)
                        .offset(offset)
                        .build();
        return bookingService.findByUserInfo(searchRequest);
    }
}

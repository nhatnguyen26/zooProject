package com.zoo.zooApplication.resource;

import com.zoo.zooApplication.application.filter.FirebaseAuthentication;
import com.zoo.zooApplication.application.filter.ZooMasterAuthentication;
import com.zoo.zooApplication.request.BookingRequest;
import com.zoo.zooApplication.request.SearchFieldBookingRequest;
import com.zoo.zooApplication.response.FieldBooking;
import com.zoo.zooApplication.response.FieldBookingResponse;
import com.zoo.zooApplication.service.BookingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestBody;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

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
    @FirebaseAuthentication
    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-Authorization-Firebase", paramType = "header", dataTypeClass = String.class)
    })
    public FieldBooking findById(@PathParam("bookingId") String bookingId) {
        return bookingService.findBookingById(bookingId);
    }

    @ApiOperation(value = "create a booking entries")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Booking created", response = FieldBooking.class)})
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    @FirebaseAuthentication
    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-Authorization-Firebase", paramType = "header", dataTypeClass = String.class)
    })
    public Response createBooking(@RequestBody BookingRequest bookingRequest, @Context UriInfo uriInfo) {
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

    @ApiOperation(value = "edit field information of a field from court", response = FieldBooking.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully edited the field", response = FieldBooking.class)})
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/booking/{bookingId}")
    @FirebaseAuthentication
    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-Authorization-Firebase", paramType = "header", dataTypeClass = String.class)
    })
    public FieldBooking editFieldBooking(@PathParam("bookingId") String bookingId, @RequestBody BookingRequest bookingRequest) {
        return bookingService.editBooking(bookingId,bookingRequest);
    }

    @ApiOperation(value = "delete a field from court", response = FieldBooking.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully remove the booking", response = FieldBooking.class)})
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/booking/{bookingId}")
    @ZooMasterAuthentication
    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-Authorization-Master", paramType = "header", dataTypeClass = String.class)
    })
    public FieldBooking deleteField(@PathParam("bookingId") String bookingId) {
        return bookingService.deleteBooking(bookingId);
    }

    @ApiOperation(value = "search for bookings using different criteria", response = FieldBooking.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Resource found")})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @FirebaseAuthentication
    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-Authorization-Firebase", paramType = "header", dataTypeClass = String.class)
    })
    @Path("/search")
    public FieldBookingResponse searchFieldBooking(@BeanParam SearchFieldBookingRequest searchRequest) {
        return bookingService.search(searchRequest);
    }

}

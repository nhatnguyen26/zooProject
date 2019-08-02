package com.zoo.zooApplication.resource;

import com.zoo.zooApplication.request.CreateCourtRequest;
import com.zoo.zooApplication.request.CreateFieldRequest;
import com.zoo.zooApplication.request.CreateFieldTypeRequest;
import com.zoo.zooApplication.request.CreatePriceChartRequest;
import com.zoo.zooApplication.response.Court;
import com.zoo.zooApplication.response.FieldType;
import com.zoo.zooApplication.service.CourtAndFieldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestBody;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("/v1/courtManagement")
@Api(value = "Operations relating to courts and fields")
public class CourtManagementResource {
    private CourtAndFieldService courtAndFieldService;

    @Inject
    public CourtManagementResource(CourtAndFieldService courtAndFieldService) {
        this.courtAndFieldService = courtAndFieldService;
    }

    @ApiOperation(value = "get current version", response = String.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Resource found")})
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/version")
    public String getVersion() {
        return "0.0.1";
    }

    @ApiOperation(value = "create a new court", response = Court.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Court has been added")})
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/courts/")
    public Response createBooking(@RequestBody CreateCourtRequest createCourtRequest, @Context UriInfo uriInfo) {
        Court court = courtAndFieldService.createCourt(createCourtRequest);
        URI locationURI = uriInfo
                .getAbsolutePathBuilder()
                .path(String.valueOf(court.getId()))
                .build();
        return Response
                .created(locationURI)
                .entity(court)
                .build();
    }

    @ApiOperation(value = "edit the information of the court", response = Court.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Court edited")})
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/courts/{courtId}")
    public Court editCourt(@PathParam("courtId") String courtId, @RequestBody CreateCourtRequest createCourtRequest) {
        return courtAndFieldService.editCourt(courtId, createCourtRequest);
    }

    @ApiOperation(value = "find the court and its fields by court id", response = Court.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Court found and all the field belong to it")})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/courts/{courtId}")
    public Court findById(@PathParam("courtId") String courtId) {
        return courtAndFieldService.findCourtById(courtId);
    }

    @ApiOperation(value = "add a new field to the court", response = Court.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully added the field to court")})
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/courts/{courtId}/fields")
    public Court addFieldToCourt(@PathParam("courtId") String courtId, CreateFieldRequest createFieldRequest) {
        return courtAndFieldService.addFieldToCourt(courtId, createFieldRequest);
    }

    @ApiOperation(value = "add a new field type price to the court", response = Court.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully added the field price type to court")})
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/courts/{courtId}/fieldTypes")
    public Court addFieldTypeToCourt(@PathParam("courtId") String courtId,
                                     CreateFieldTypeRequest createFieldTypeRequest){
        return courtAndFieldService.addFieldTypeToCourt(courtId, createFieldTypeRequest);
    }

    @ApiOperation(value = "add a price chart to specific field type", response = Court.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully added the price chart to specific field type")})
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/fieldTypes/{fieldTypeId}/priceCharts")
    public FieldType addPriceChartToFieldType(@PathParam("fieldTypeId") String fieldTypeId,
                                              CreatePriceChartRequest createPriceChartRequest){
        return courtAndFieldService.addPriceChartToFieldType(fieldTypeId, createPriceChartRequest);
    }
}

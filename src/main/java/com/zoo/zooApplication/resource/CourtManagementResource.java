package com.zoo.zooApplication.resource;

import com.zoo.zooApplication.application.filter.FirebaseAuthentication;
import com.zoo.zooApplication.firebaseadaptor.IFirebaseAuth;
import com.zoo.zooApplication.request.ClaimKeyRequest;
import com.zoo.zooApplication.request.CreateCourtRequest;
import com.zoo.zooApplication.request.CreateFieldRequest;
import com.zoo.zooApplication.request.CreateFieldTypeRequest;
import com.zoo.zooApplication.request.CreatePriceChartRequest;
import com.zoo.zooApplication.request.FieldRequest;
import com.zoo.zooApplication.response.Court;
import com.zoo.zooApplication.response.Field;
import com.zoo.zooApplication.response.FieldType;
import com.zoo.zooApplication.service.CourtAndFieldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestBody;

import javax.inject.Inject;
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

    @ApiOperation(value = "claim to be owner of the court", response = Court.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Court has been claimed")})
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/courts/claimAsOwner")
    @FirebaseAuthentication
    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-Authorization-Firebase", required = true, paramType = "header", dataTypeClass = String.class)
    })
    public Court claimCourtAsOwner(@RequestBody ClaimKeyRequest claimRequest, @Context IFirebaseAuth userAuth) {
        claimRequest.setFirebaseAuth(userAuth);
        return courtAndFieldService.claimCourtAsOwner(claimRequest);
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
    public Court addFieldToCourt(@PathParam("courtId") String courtId, @RequestBody CreateFieldRequest createFieldRequest) {
        return courtAndFieldService.addFieldToCourt(courtId, createFieldRequest);
    }

    @ApiOperation(value = "edit field information of a field from court", response = Field.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully edited the field")})
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/courts/{courtId}/fields/{fieldId}")
    public Field editField(@PathParam("courtId") String courtId, @PathParam("fieldId") String fieldId, @RequestBody FieldRequest fieldRequest) {
        return courtAndFieldService.editField(courtId, fieldId, fieldRequest);
    }

    @ApiOperation(value = "delete a field from court", response = Field.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully edited the field")})
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/courts/{courtId}/fields/{fieldId}")
    public Field deleteField(@PathParam("courtId") String courtId, @PathParam("fieldId") String fieldId) {
        return courtAndFieldService.deleteField(courtId, fieldId);
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

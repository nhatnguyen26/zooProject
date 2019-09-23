package com.zoo.zooApplication.resource;

import com.zoo.zooApplication.application.filter.FirebaseAuthentication;
import com.zoo.zooApplication.application.filter.ZooMasterAuthentication;
import com.zoo.zooApplication.firebaseadaptor.IFirebaseAuth;
import com.zoo.zooApplication.request.ClaimKeyRequest;
import com.zoo.zooApplication.request.CreateCourtRequest;
import com.zoo.zooApplication.request.CreateFieldRequest;
import com.zoo.zooApplication.request.CreateFieldTypeRequest;
import com.zoo.zooApplication.request.FieldRequest;
import com.zoo.zooApplication.request.FieldTypeRequest;
import com.zoo.zooApplication.response.ClaimKey;
import com.zoo.zooApplication.response.Court;
import com.zoo.zooApplication.response.CourtsResponse;
import com.zoo.zooApplication.response.Field;
import com.zoo.zooApplication.response.FieldResponse;
import com.zoo.zooApplication.response.FieldType;
import com.zoo.zooApplication.response.FieldTypeResponse;
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
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Court has been added", response = Court.class)})
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	@ZooMasterAuthentication
    @Path("/courts/")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-Authorization-Master", paramType = "header", dataTypeClass = String.class)
	})
    public Response createCourt(@RequestBody CreateCourtRequest createCourtRequest, @Context UriInfo uriInfo) {
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

	@ApiOperation(value = "delete a court", response = Court.class)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Court has been delete", response = Court.class)})
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@ZooMasterAuthentication
	@Path("/courts/{courtId}")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-Authorization-Master", paramType = "header", dataTypeClass = String.class)
	})
	public Court deleteCourt(@PathParam("courtId") String courtId) {
    	return courtAndFieldService.deleteCourt(courtId);
	}

    @ApiOperation(value = "Find claim key for a court if unclaimed", response = ClaimKey.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Found claim key", response = ClaimKey.class)})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/courts/{courtId}/claimKey")
    @ZooMasterAuthentication
    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-Authorization-Master", paramType = "header", dataTypeClass = String.class)
    })
    public ClaimKey findClaimKeyByCourtId(@PathParam("courtId") String courtId) {
        return courtAndFieldService.findClaimKeyByCourtId(courtId);
    }

    @ApiOperation(value = "Find all court that current user has managing role on", response = CourtsResponse.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "All courts managed by user found", response = CourtsResponse.class)})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/courts/managedByUser")
    @FirebaseAuthentication
    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-Authorization-Firebase", paramType = "header", dataTypeClass = String.class)
    })
    public CourtsResponse findAllCourtsManagedByUser(@Context IFirebaseAuth userAuth) {
        return courtAndFieldService.findAllCourtManageByUser(userAuth.getUid());
    }

    @ApiOperation(value = "claim to be owner of the court", response = Court.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Court has been claimed", response = Court.class)})
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/courts/claimAsOwner")
    @FirebaseAuthentication
    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-Authorization-Firebase", paramType = "header", dataTypeClass = String.class)
    })
    public Court claimCourtAsOwner(@RequestBody ClaimKeyRequest claimRequest, @Context IFirebaseAuth userAuth) {
        claimRequest.setFirebaseAuth(userAuth);
        return courtAndFieldService.claimCourtAsOwner(claimRequest);
    }

    @ApiOperation(value = "edit the information of the court", response = Court.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Court edited", response = Court.class)})
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/courts/{courtId}")
    @FirebaseAuthentication
    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-Authorization-Firebase", paramType = "header", dataTypeClass = String.class)
    })
    public Court editCourt(@PathParam("courtId") String courtId, @RequestBody CreateCourtRequest createCourtRequest) {
        return courtAndFieldService.editCourt(courtId, createCourtRequest);
    }

    @ApiOperation(value = "find the court and its fields by court the claimKey only if field is unclaimed or allow to be re-claim", response = Court.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Court found and all the field belong to it")})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/claimKeys/{claimKey}")
    @ZooMasterAuthentication
    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-Authorization-Master", paramType = "header", dataTypeClass = String.class)
    })
    public Court findByClaimKey(@PathParam("claimKey") String claimKey) {
        return courtAndFieldService.findCourtByClaimKey(claimKey);
    }

    @ApiOperation(value = "find the court and its fields by court id", response = Court.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Court found and all the field belong to it", response = Court.class)})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/courts/{courtId}")
    @FirebaseAuthentication
    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-Authorization-Firebase", paramType = "header", dataTypeClass = String.class)
    })
    public Court findById(@PathParam("courtId") String courtId) {
        return courtAndFieldService.findCourtById(courtId);
    }

    @ApiOperation(value = "add a new field to the court", response = Court.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully added the field to court", response = Court.class)})
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/courts/{courtId}/fields")
    @FirebaseAuthentication
    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-Authorization-Firebase", paramType = "header", dataTypeClass = String.class)
    })
    public Court addFieldToCourt(@PathParam("courtId") String courtId, @RequestBody CreateFieldRequest createFieldRequest) {
        return courtAndFieldService.addFieldToCourt(courtId, createFieldRequest);
    }

    @ApiOperation(value = "get all the fields within the court", response = FieldResponse.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "found all fields belong to court", response = FieldResponse.class)})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/courts/{courtId}/fields")
    @FirebaseAuthentication
    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-Authorization-Firebase", paramType = "header", dataTypeClass = String.class)
    })
    public FieldResponse getAllFieldsInCourt(@PathParam("courtId") String courtId) {
        return courtAndFieldService.getAllFieldsInCourt(courtId);
    }

    @ApiOperation(value = "edit field information of a field from court", response = Field.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully edited the field", response = Field.class)})
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/courts/{courtId}/fields/{fieldId}")
    @FirebaseAuthentication
    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-Authorization-Firebase", paramType = "header", dataTypeClass = String.class)
    })
    public Field editField(@PathParam("courtId") String courtId, @PathParam("fieldId") String fieldId, @RequestBody FieldRequest fieldRequest) {
        return courtAndFieldService.editField(courtId, fieldId, fieldRequest);
    }

    @ApiOperation(value = "delete a field from court", response = Field.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully edited the field", response = Field.class)})
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/courts/{courtId}/fields/{fieldId}")
    @FirebaseAuthentication
    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-Authorization-Firebase", paramType = "header", dataTypeClass = String.class)
    })
    public Field deleteField(@PathParam("courtId") String courtId, @PathParam("fieldId") String fieldId) {
        return courtAndFieldService.deleteField(courtId, fieldId);
    }

    @ApiOperation(value = "add a new field type price to the court", response = Court.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully added the field price type to court", response = Court.class)})
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/courts/{courtId}/fieldTypes")
    @FirebaseAuthentication
    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-Authorization-Firebase", paramType = "header", dataTypeClass = String.class)
    })
    public Court addFieldTypeToCourt(@PathParam("courtId") String courtId,
                                     CreateFieldTypeRequest createFieldTypeRequest){
        return courtAndFieldService.addFieldTypeToCourt(courtId, createFieldTypeRequest);
    }

    @ApiOperation(value = "get all the field types within the court", response = FieldTypeResponse.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Found all field types belong to court", response = FieldTypeResponse.class)})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/courts/{courtId}/fieldTypes")
    @FirebaseAuthentication
    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-Authorization-Firebase", paramType = "header", dataTypeClass = String.class)
    })
    public FieldTypeResponse getAllFieldTypesInCourt(@PathParam("courtId") String courtId){
        return courtAndFieldService.getAllFieldTypesInCourt(courtId);
    }

    @ApiOperation(value = "edit field type information of the court", response = FieldType.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully edit the field type", response = FieldType.class)})
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/courts/{courtId}/fieldTypes/{fieldTypeId}")
    @FirebaseAuthentication
    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-Authorization-Firebase", paramType = "header", dataTypeClass = String.class)
    })
    public FieldType editFieldType(@PathParam("courtId") String courtId,
                                         @PathParam("fieldTypeId") String fieldTypeId,
                                         FieldTypeRequest fieldTypeRequest){
        return courtAndFieldService.editFieldType(courtId, fieldTypeId, fieldTypeRequest);
    }

    @ApiOperation(value = "delete a field from court", response = FieldType.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully edited the field", response = FieldType.class)})
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/courts/{courtId}/fieldTypes/{fieldTypeId}")
    @FirebaseAuthentication
    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-Authorization-Firebase", paramType = "header", dataTypeClass = String.class)
    })
    public FieldType deleteFieldType(@PathParam("courtId") String courtId, @PathParam("fieldTypeId") String fieldTypeId) {
        return courtAndFieldService.deleteFieldType(courtId, fieldTypeId);
    }


}

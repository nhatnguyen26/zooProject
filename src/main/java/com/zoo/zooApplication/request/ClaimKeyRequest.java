package com.zoo.zooApplication.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ApiModel(value = "ClaimKeyRequest", description = "The request to claim a court as owner")
public class ClaimKeyRequest extends UserBasedRequest {
	private String claimKey;
}

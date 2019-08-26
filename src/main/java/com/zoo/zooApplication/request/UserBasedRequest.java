package com.zoo.zooApplication.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zoo.zooApplication.firebaseadaptor.IFirebaseAuth;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class UserBasedRequest {

	@JsonIgnore
	private IFirebaseAuth firebaseAuth;

	@JsonIgnore
	public String getUid() {
		return firebaseAuth.getUid();
	}

	@JsonIgnore
	public String getEmail() {
		return firebaseAuth.getEmail();
	}
}

package com.zoo.zooApplication.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidRequestExceptionMapper extends BaseExceptionMapper<InvalidRequestException> {

	@Override
	protected Response.Status getStatus() {
		return Response.Status.BAD_REQUEST;
	}
}

package com.zoo.zooApplication.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class ForbiddenAccessExceptionMapper extends BaseExceptionMapper<ForbiddenAccessException> {

	@Override
	protected Response.Status getStatus() {
		return Response.Status.FORBIDDEN;
	}
}

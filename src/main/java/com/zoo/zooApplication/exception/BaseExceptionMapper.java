package com.zoo.zooApplication.exception;

import com.zoo.zooApplication.response.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public abstract class BaseExceptionMapper<T extends BaseErrorIdException> implements ExceptionMapper<T> {
	@Override
	public Response toResponse(T exception) {
		ErrorMessage errorMessage = ErrorMessage.builder()
			.errorId(String.valueOf(exception.getErrorId()))
			.message(exception.getMessage())
			.build();
		return Response.status(getStatus()).entity(errorMessage).build();
	}

	protected abstract Response.Status getStatus();
}

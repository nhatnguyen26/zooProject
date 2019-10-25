package com.zoo.zooApplication.exception;

public class ForbiddenAccessException extends BaseErrorIdException {

	public ForbiddenAccessException(long errorId, String message) {
		super(errorId, message);
	}
}

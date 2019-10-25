package com.zoo.zooApplication.exception;

import lombok.Getter;

public abstract class BaseErrorIdException extends RuntimeException {
	@Getter
	private long errorId;

	public BaseErrorIdException(long errorId, String message) {
		super(message);
		this.errorId = errorId;
	}
}

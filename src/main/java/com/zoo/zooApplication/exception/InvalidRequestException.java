package com.zoo.zooApplication.exception;

public class InvalidRequestException extends BaseErrorIdException {

    public InvalidRequestException(long errorId, String message) {
        super(errorId, message);
    }
}

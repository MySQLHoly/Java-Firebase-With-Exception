package com.sandro.postmanagement.Exception;

public class StringContainsNumberException extends RuntimeException{

    public StringContainsNumberException() {

    }

    public StringContainsNumberException(String message) {
        super(message);
    }
}

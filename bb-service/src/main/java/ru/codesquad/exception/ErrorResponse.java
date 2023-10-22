package ru.codesquad.exception;

import java.util.Date;

public class ErrorResponse {

    private final String error;

    private Date timestamp;

    public ErrorResponse(String error) {
        this.error = error;
        this.timestamp = new Date();
    }

    public String getError() {
        return error;
    }
}
package com.cardcharity.web.exception;

public class Response {
    private String error;

    public Response() {
    }

    public Response(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

package com.cardcharity.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ServerException extends Exception {

    public ServerException(String message) {
        super(message);
    }

}

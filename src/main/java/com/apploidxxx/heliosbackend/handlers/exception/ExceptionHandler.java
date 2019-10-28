package com.apploidxxx.heliosbackend.handlers.exception;

import com.apploidxxx.heliosbackend.rest.exceptions.WebException;
import com.apploidxxx.heliosbackend.rest.model.ErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;

/**
 * @author Arthur Kupriyanov
 */
@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(WebException.class)
    public ResponseEntity<ErrorMessage> userNotFoundExceptionHandle(WebException e) {
        return new ResponseEntity<>(e.getErrorMessage(), e.getStatusCode());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<String> httpStatusCodeException(HttpStatusCodeException e) {
        return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
    }
}

package com.apploidxxx.heliosbackend.handlers.exception;

import com.apploidxxx.heliosbackend.rest.exceptions.WebException;
import com.apploidxxx.heliosbackend.rest.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;

/**
 *
 * Handles some of exceptions caused in runtime
 *
 * @author Arthur Kupriyanov
 */
@ControllerAdvice
public class ExceptionHandler {

    /**
     * Caught WebException errors which have {@link WebException#getErrorMessage()} method
     * @param e caused exception
     * @return response with status code and {@link ErrorMessage}
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(WebException.class)
    public ResponseEntity<ErrorMessage> userNotFoundExceptionHandle(WebException e) {
        return new ResponseEntity<>(e.getErrorMessage(), e.getStatusCode());
    }

    /**
     * Caught the Exceptions caused by {@link org.springframework.web.client.RestTemplate}
     * @param e caused exception
     * @return response with status code and response body of error
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<String> httpStatusCodeException(HttpStatusCodeException e) {
        return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
    }
    /**
     * Caught the Exceptions caused by {@link MissingRequestCookieException}
     * @param e caused exception
     * @return response with status code and response body of error
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(MissingRequestCookieException.class)
    public ResponseEntity<ErrorMessage> MissingRequestCookieException(MissingRequestCookieException e) {
        return new ResponseEntity<>(new ErrorMessage("invalid_cookie", "you cookie expired or invalid"), HttpStatus.UNAUTHORIZED);
    }
}

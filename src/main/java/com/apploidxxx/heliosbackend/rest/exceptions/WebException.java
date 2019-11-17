package com.apploidxxx.heliosbackend.rest.exceptions;

import com.apploidxxx.heliosbackend.rest.model.ErrorMessage;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;

/**
 *
 * All child of WebException will be caught by
 * {@link com.apploidxxx.heliosbackend.handlers.exception.ExceptionHandler}
 * when they are caused in runtime
 *
 * @author Arthur Kupriyanov
 */
public abstract class WebException extends RuntimeException {

    public abstract String getError();

    public abstract String getErrorDescription();

    public abstract HttpStatus getStatusCode();

    public ErrorMessage getErrorMessage() {
        return new ErrorMessage(getError(), getErrorDescription());
    }

    public ErrorMessage wrapResponse(HttpServletResponse response) {
        return getErrorMessage();
    }

}

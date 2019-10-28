package com.apploidxxx.heliosbackend.rest.exceptions;

import com.apploidxxx.heliosbackend.rest.model.ErrorMessage;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;

/**
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

package com.apploidxxx.heliosbackend.rest.exceptions;

import com.apploidxxx.heliosbackend.rest.model.ErrorMessage;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Arthur Kupriyanov
 */
public abstract class WebException extends Exception {

    public abstract String getError();

    public abstract String getErrorDescription();

    public ErrorMessage getErrorMessage() {
        return new ErrorMessage(getError(), getErrorDescription());
    }

    public ErrorMessage wrapResponse(HttpServletResponse response) {
        return getErrorMessage();
    }

}

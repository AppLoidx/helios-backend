package com.apploidxxx.heliosbackend.rest.exceptions;

import com.apploidxxx.heliosbackend.rest.model.ErrorMessage;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Arthur Kupriyanov
 */
public class UserNotFoundException extends WebException {
    @Override
    public String getError() {
        return "user_not_found";
    }

    @Override
    public String getErrorDescription() {
        return "User not found with this session";
    }

    @Override
    public ErrorMessage wrapResponse(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return super.wrapResponse(response);
    }
}

package com.apploidxxx.heliosbackend.rest.exceptions;

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
}

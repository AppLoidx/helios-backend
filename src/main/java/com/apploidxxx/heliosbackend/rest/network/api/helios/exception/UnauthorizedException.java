package com.apploidxxx.heliosbackend.rest.network.api.helios.exception;

import com.apploidxxx.heliosbackend.rest.model.ErrorMessage;
import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Arthur Kupriyanov
 */
@Getter
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(ErrorMessage errMsg) {
        this.errMsg = errMsg;
    }

    private ErrorMessage errMsg;
    private int status = HttpServletResponse.SC_UNAUTHORIZED;
}

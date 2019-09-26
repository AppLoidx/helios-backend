package com.apploidxxx.heliosbackend.rest.util.request;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Map;

/**
 * @author Arthur Kupriyanov
 */
@FunctionalInterface
public interface RequestFailHandler<T> {
    ResponseEntity<T> doRequest(String path, Class<T> model, Object object, HttpStatusCodeException e, Map<String, String> args) throws Throwable;
}

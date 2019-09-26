package com.apploidxxx.heliosbackend.rest.util.request;

import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * @author Arthur Kupriyanov
 */
@FunctionalInterface
interface RequestCall<T>{
    ResponseEntity<T> doRequest(String path, Class<T> model, Object object, Map<String, String> args);
}
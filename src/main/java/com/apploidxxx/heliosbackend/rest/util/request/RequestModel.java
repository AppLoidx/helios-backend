package com.apploidxxx.heliosbackend.rest.util.request;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Map;

/**
 * @author Arthur Kupriyanov
 */
class RequestModel<T> {
    private String path;
    private Class<T> model;
    private Object object;
    private Map<String, String> args;

    RequestModel(String path, Class<T> model, Object object, String... args) {
        this.path = path;
        this.model = model;
        this.object = object;
        this.args = RequestUtil.getMap(args);
    }


    private RequestFailHandler<T> failCallback;
    private RequestCall<T> request;

    RequestModel onFail(RequestFailHandler<T> requestCall) {
        this.failCallback = requestCall;
        return this;
    }

    RequestModel<T> setOperation(RequestCall<T> request) {
        this.request = request;
        return this;
    }

    public ResponseEntity<T> execute() throws Throwable {
        if (this.request != null) {
            try {
                return this.request.doRequest(path, model, object, args);
            } catch (HttpStatusCodeException e) {
                if (failCallback != null) return this.failCallback.doRequest(path, model, object, e, args);
                else throw e.getCause();
            }
        } else {
            throw new NullPointerException("Request operation doesn't exist!");
        }
    }
}

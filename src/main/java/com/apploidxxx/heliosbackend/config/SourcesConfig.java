package com.apploidxxx.heliosbackend.config;

import com.apploidxxx.heliosbackend.rest.util.PropertyManager;

import java.io.IOException;

/**
 * @author Arthur Kupriyanov
 */
public class SourcesConfig {

    public static final boolean connectToLocalAPI = false;

    // API Side URI's
    private static final String SERVICE_URL;

    public static final String DOMAIN;

    static {    // setting up domain to redirect here after OAuth processing
        String domain = "http://localhost:8080";
        try {
            domain = PropertyManager.getProperty("DOMAIN");
        } catch (IOException e) {
            e.printStackTrace();
        }
        DOMAIN = domain;
    }

    static {    // setting up URL to API

        if (connectToLocalAPI) {
            SERVICE_URL = "http://localhost:3000";
        } else {
            SERVICE_URL = "https://helios-service.herokuapp.com";
        }
    }

    public static final String heliosApiUri = SERVICE_URL + "/api/";   // last slash is necessary
    public static final String oAuthUri = SERVICE_URL + "/auth/login.html";
}

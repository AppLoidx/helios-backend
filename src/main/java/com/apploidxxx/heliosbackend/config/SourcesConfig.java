package com.apploidxxx.heliosbackend.config;

/**
 * @author Arthur Kupriyanov
 */
public class SourcesConfig {

    // API Side URI's
    private static final String BASE_URI = "https://helios-service.herokuapp.com";
//    private static final String BASE_URI = "http://localhost:3000";

    // Dynamic addresses
    public static final String heliosApiUri = BASE_URI + "/api/";   // last slash is necessary
    public static final String oAuthUri = BASE_URI + "/html/external/login.html";

    // Local or another domain names
    public static final String DOMAIN = "https://itmo-helios.herokuapp.com";
//    public static final String DOMAIN = "http://localhost:8080";

}

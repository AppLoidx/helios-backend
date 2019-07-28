package com.apploidxxx.heliosbackend.rest.util;

import java.util.Base64;

/**
 * @author Arthur Kupriyanov
 */
public class SessionGenerator {
    public static String generateSession(String login, String password) {
        // todo: write correct body to session generator

        return Base64.getEncoder().encodeToString((login + password).getBytes());
    }
}

package com.apploidxxx.heliosbackend.rest.util;

import org.apache.commons.codec.digest.Md5Crypt;

import java.util.Base64;
import java.util.Date;

/**
 * @author Arthur Kupriyanov
 */
public class SessionGenerator {
    public static String generateSession(String login, String password) {
        return Base64.getEncoder().encodeToString(Md5Crypt.md5Crypt((login + new Date().toString() + password).getBytes()).getBytes());
    }
}

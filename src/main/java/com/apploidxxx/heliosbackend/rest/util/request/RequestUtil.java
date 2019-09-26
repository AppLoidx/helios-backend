package com.apploidxxx.heliosbackend.rest.util.request;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Arthur Kupriyanov
 */
public class RequestUtil {
    public static Map<String, String> getMap(String... vars) {
        Map<String, String> map = new HashMap<>();
        for (int i = vars.length; i > 1; i = i - 2) {
            map.put(vars[i - 2], vars[i - 1]);
        }
        return map;
    }
}

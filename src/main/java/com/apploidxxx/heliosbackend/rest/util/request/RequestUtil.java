package com.apploidxxx.heliosbackend.rest.util.request;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Arthur Kupriyanov
 */
public class RequestUtil {

    /**
     * Converts the String[] array to Map with key[k] and value[k+1] from
     * k = 0 (+2) to k = array.length - 2
     * <p>
     * if key or value is null - he is skipping this param and don't pass
     * them to result map
     *
     * @param vars string array with key, value ordered
     * @return map of key with values
     */
    public static Map<String, String> getMap(String... vars) {
        Map<String, String> map = new HashMap<>();
        for (int i = vars.length; i > 1; i = i - 2) {
            if (vars[i - 1] == null || vars[i - 2] == null) continue;
            map.put(vars[i - 2], vars[i - 1]);
        }
        return map;
    }
}

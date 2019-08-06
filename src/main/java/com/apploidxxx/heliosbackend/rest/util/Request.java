package com.apploidxxx.heliosbackend.rest.util;

import com.apploidxxx.heliosbackend.config.ExternalSourcesConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Arthur Kupriyanov
 */
public class Request {
    public <T> ResponseEntity<T> get(String path, Class<T> model, Map<String, String> vars) {
        return new RestTemplate().getForEntity(ExternalSourcesConfig.heliosApiUri + path, model, vars);
    }

    public <T> ResponseEntity<T> get(String path, Class<T> model, String... vars) {
        return get(path, model, getMap(vars));
    }

    public <T> ResponseEntity<T> post(String path, Object object, Class<T> model, Map<String, String> vars) {
        return new RestTemplate().postForEntity(path, object, model, vars);
    }

    public <T> ResponseEntity<T> post(String path, Object object, Class<T> model, String... vars) {
        return post(path, object, model, getMap(vars));
    }

    public <T> ResponseEntity<T> post(String path, Class<T> model, Map<String, String> vars) {
        return new RestTemplate().postForEntity(path, null, model, vars);
    }

    public <T> ResponseEntity<T> post(String path, Class<T> model, String... vars) {
        return post(path, null, model, getMap(vars));
    }

    public static <T> void delete(String path, Class<T> model, Map<String, String> vars) {
        new RestTemplate().delete(ExternalSourcesConfig.heliosApiUri + path, model, vars);
    }

    public static <T> void delete(String path, Class<T> model, String... vars) {
        delete(path, model, getMap(vars));
    }

    public static void put(String path, Object object, Map<String, String> vars) {
        new RestTemplate().put(path, object, vars);
    }

    public static void put(String path, Object object, String... vars) {
        new RestTemplate().put(path, object, getMap(vars));
    }

    private static Map<String, String> getMap(String... vars) {
        Map<String, String> map = new HashMap<>();
        for (int i = vars.length; i > 1; i = i - 2) {
            map.put(vars[0], vars[1]);
        }
        return map;
    }
}

package com.apploidxxx.heliosbackend.rest.util.request;

import com.apploidxxx.heliosbackend.config.SourcesConfig;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static com.apploidxxx.heliosbackend.rest.util.request.RequestUtil.getMap;

/**
 * Обертка над {@link RestTemplate} для запросов к Helios API
 *
 * @see SourcesConfig#heliosApiUri
 *
 * @author Arthur Kupriyanov
 */
@NoArgsConstructor
public class Request {

    /**
     *
     * @param path относительный путь к API
     * @param model POJO class
     * @param vars params
     * @param <T> model's class
     * @return ResponseEntity wrapped by model
     * @see SourcesConfig
     * @see #get(String, Class, String...)
     */
    public <T> ResponseEntity<T> get(String path, Class<T> model, Map<String, String> vars) {

        return new RestTemplate().getForEntity(SourcesConfig.heliosApiUri + generatePathWithParams(path, vars), model, vars);
    }

    public <T> ResponseEntity<T> get(String path, Class<T> model, String... vars) {
        return get(path, model, getMap(vars));
    }


    /**
     *
     * @param path path относительный путь к API
     * @param object посылаемый объект
     * @param model POJO class
     * @param vars params
     * @param <T> model's class
     * @return ResponseEntity wrapped by model
     * @see SourcesConfig
     * @see #post(String, Class, String...)
     */
    public <T> ResponseEntity<T> post(String path, Object object, Class<T> model, Map<String, String> vars) {
        return new RestTemplate().postForEntity(SourcesConfig.heliosApiUri + generatePathWithParams(path, vars), object, model, vars);
    }

    public <T> ResponseEntity<T> post(String path, Object object, Class<T> model, String... vars) {
        return post(path, object, model, getMap(vars));
    }

    public <T> ResponseEntity<T> post(String path, Class<T> model, Map<String, String> vars) {
        return new RestTemplate().postForEntity(SourcesConfig.heliosApiUri + generatePathWithParams(path, vars), null, model, vars);
    }

    public <T> ResponseEntity<T> post(String path, Class<T> model, String... vars) {
        return post(path, null, model, getMap(vars));
    }

    /**
     *
     * @param path path относительный путь к API
     * @param model POJO class
     * @param vars params
     * @param <T> model's class
     */
    public static <T> void delete(String path, Class<T> model, Map<String, String> vars) {
        new RestTemplate().delete(SourcesConfig.heliosApiUri + generatePathWithParams(path, vars), model, vars);
    }

    public static <T> void delete(String path, Class<T> model, String... vars) {
        delete(path, model, getMap(vars));
    }

    /**
     *
     * @param path path относительный путь к API
     * @param object посылаемый объект
     * @param vars params
     */
    public static void put(String path, Object object, Map<String, String> vars) {
        new RestTemplate().put(SourcesConfig.heliosApiUri + generatePathWithParams(path, vars), object, vars);
    }

    public static void put(String path, Object object, String... vars) {
        put(path, object, getMap(vars));
    }



    private static String generatePathWithParams(String path, Map<String, String> map) {
        if (map.isEmpty()) return path;
        StringBuilder sb = new StringBuilder();

        for (String k : map.keySet()) {
            sb.append(k).append("={").append(k).append("}&");
        }
        return path + "?" + sb.toString().substring(0, sb.length() - 1);
    }

}

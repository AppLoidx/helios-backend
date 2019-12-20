package com.apploidxxx.heliosbackend.rest.util.request;

import com.apploidxxx.heliosbackend.config.SourcesConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.apploidxxx.heliosbackend.rest.util.request.RequestUtil.getMap;

/**
 * Обертка над {@link RestTemplate} для запросов к Helios API
 *
 * @author Arthur Kupriyanov
 * @see SourcesConfig#heliosApiUri
 */
@NoArgsConstructor
@Component
public class Request {

    private static RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
        jsonMessageConverter.setObjectMapper(new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE));
        messageConverters.add(jsonMessageConverter);
        restTemplate.setMessageConverters(messageConverters);

        return restTemplate;
    }

    /**
     * @param path  относительный путь к API
     * @param model POJO class
     * @param vars  params
     * @param <T>   model's class
     * @return ResponseEntity wrapped by model
     * @see SourcesConfig
     * @see #get(String, Class, String...)
     */
    public <T> ResponseEntity<T> get(String path, Class<T> model, Map<String, String> vars) {

         return restTemplate().getForEntity(SourcesConfig.heliosApiUri + generatePathWithParams(path, vars), model, vars);
    }

    public <T> ResponseEntity<T> get(String path, Class<T> model, String... vars) {
        return get(path, model, getMap(vars));
    }


    /**
     * @param path   path относительный путь к API
     * @param object посылаемый объект
     * @param model  POJO class
     * @param vars   params
     * @param <T>    model's class
     * @return ResponseEntity wrapped by model
     * @see SourcesConfig
     * @see #post(String, Class, String...)
     */
    public <T> ResponseEntity<T> post(String path, Object object, Class<T> model, Map<String, String> vars) {
        return restTemplate().postForEntity(SourcesConfig.heliosApiUri + generatePathWithParams(path, vars), object, model, vars);
    }

    public <T> ResponseEntity<T> post(String path, Object object, Class<T> model, String... vars) {
        return post(path, object, model, getMap(vars));
    }

    public <T> ResponseEntity<T> post(String path, Class<T> model, Map<String, String> vars) {
        return restTemplate().postForEntity(SourcesConfig.heliosApiUri + generatePathWithParams(path, vars), null, model, vars);
    }

    public <T> ResponseEntity<T> post(String path, Class<T> model, String... vars) {
        return post(path, null, model, getMap(vars));
    }

    /**
     * @param path path относительный путь к API
     * @param vars params
     */
    public static void delete(String path, Map<String, String> vars) {
        restTemplate().delete(SourcesConfig.heliosApiUri + generatePathWithParams(path, vars), vars);
    }

    public static void delete(String path, String... vars) {
        delete(path, getMap(vars));
    }

    /**
     * @param path   path относительный путь к API
     * @param object посылаемый объект
     * @param vars   params
     */
    public static void put(String path, Object object, Map<String, String> vars) {
        restTemplate().put(SourcesConfig.heliosApiUri + generatePathWithParams(path, vars), object, vars);
    }

    public static void put(String path, Object object, String... vars) {
        put(path, object, getMap(vars));
    }


    private static String generatePathWithParams(String path, Map<String, String> map) {
        if (map.isEmpty()) return path;
        StringBuilder sb = new StringBuilder();

        for (String k : map.keySet()) {
            if (k == null) continue;
            sb.append(k).append("={").append(k).append("}&");
        }
        return path + "?" + sb.toString().substring(0, sb.length() - 1);
    }

}

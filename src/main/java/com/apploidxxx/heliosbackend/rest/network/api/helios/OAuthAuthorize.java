package com.apploidxxx.heliosbackend.rest.network.api.helios;

import com.apploidxxx.heliosbackend.config.ExternalSourcesConfig;
import com.apploidxxx.heliosbackend.data.entity.Token;
import com.apploidxxx.heliosbackend.rest.model.ErrorMessage;
import com.apploidxxx.heliosbackend.rest.network.api.helios.exception.UnauthorizedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Arthur Kupriyanov
 */
public class OAuthAuthorize {
    private static final RestTemplate restTemplate = new RestTemplate();

    public static Token getTokens(String login, String password) throws UnauthorizedException {
        Map<String, String> vars = new HashMap<>();
        vars.put("login", login);
        vars.put("password", password);

        try {
            return restTemplate
                    .getForObject(ExternalSourcesConfig.heliosApiUri + "auth?login={login}&password={password}",
                            Token.class, vars);

        } catch (HttpClientErrorException e) {
            System.err.println(e.getResponseBodyAsString());
            try {
                throw new UnauthorizedException(new ObjectMapper().readValue(e.getResponseBodyAsString(), ErrorMessage.class));
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new UnauthorizedException(new ErrorMessage("internal server error", "Exception caused while parsing json"));
            }

        }

    }
}

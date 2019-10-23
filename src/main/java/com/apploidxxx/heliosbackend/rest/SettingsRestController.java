package com.apploidxxx.heliosbackend.rest;

import com.apploidxxx.heliosbackend.data.entity.User;
import com.apploidxxx.heliosbackend.data.repository.UserRepository;
import com.apploidxxx.heliosbackend.rest.model.ErrorMessage;
import com.apploidxxx.heliosbackend.rest.model.UserSettings;
import com.apploidxxx.heliosbackend.rest.util.request.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author Arthur Kupriyanov
 */
@RestController
@RequestMapping("/api/settings/{username}")
public class SettingsRestController {
    private final UserRepository userRepository;

    public SettingsRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(produces = "application/json")
    public Object get(@PathVariable("username") String username,
                            @CookieValue("session") String session,
                            HttpServletResponse response){
        Optional<User> user = userRepository.findBySession(session);

        if (!user.isPresent()){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ErrorMessage("invalid_session", "Your session is expired or invalid");
        }
        ResponseEntity<UserSettings> responseEntity = new Request().get("settings/" + username, UserSettings.class,
                    "access_token", user.get().getUserToken().getAccessToken());

        response.setStatus(responseEntity.getStatusCode().value());
        System.out.println(responseEntity.getBody());
        return responseEntity.getBody();

    }

    @PutMapping
    public Object put(@PathVariable("username") String username,
                      @CookieValue("session") String session,
                      HttpServletResponse response,
                      @RequestParam("property") String property,
                      @RequestParam("value") String value){
        Optional<User> user = userRepository.findBySession(session);

        if (!user.isPresent()){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ErrorMessage("invalid_session", "Your session is expired or invalid");
        }
        try {
            Request.put("settings/" + username, UserSettings.class,
                    "access_token", user.get().getUserToken().getAccessToken(),
                    "property", property,
                    "value", value);
        } catch (HttpClientErrorException e){
            response.setStatus(e.getStatusCode().value());
            return null;
        }

        return null;
    }
}

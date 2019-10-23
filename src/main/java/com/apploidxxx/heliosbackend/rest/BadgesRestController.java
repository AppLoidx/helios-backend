package com.apploidxxx.heliosbackend.rest;

import com.apploidxxx.heliosbackend.data.entity.User;
import com.apploidxxx.heliosbackend.data.repository.UserRepository;
import com.apploidxxx.heliosbackend.rest.model.ErrorMessage;
import com.apploidxxx.heliosbackend.rest.util.request.Request;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author Arthur Kupriyanov
 */
@RestController
@RequestMapping("/api/badges")
public class BadgesRestController {

    private final UserRepository userRepository;

    public BadgesRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public Object getBadges(
            HttpServletResponse response,
            @CookieValue("session") String session,
            @RequestParam(value = "username", required = false) String username
    ){
        Optional<User> user = userRepository.findBySession(session);
        if (!user.isPresent()) return new ErrorMessage("invalid_token", "your session expired");
        try {
            if (username == null) {
                return new Request().get("badges", String.class,
                        "access_token", user.get().getUserToken().getAccessToken()).getBody();
            } else {
                return new Request().get("badges", String.class,
                        "access_token", user.get().getUserToken().getAccessToken(),
                        "username", username);
            }
        } catch (HttpClientErrorException e){
            response.setStatus(e.getStatusCode().value());
            return e.getResponseBodyAsString();
        }
    }

    @PutMapping
    public Object putBadge(
            HttpServletResponse response,
            @CookieValue("session") String session,
            @RequestParam(value = "username", required = false) String username
    ){
        Optional<User> user = userRepository.findBySession(session);
        if (!user.isPresent()) return new ErrorMessage("invalid_token", "your session expired");
        try {
            if (username == null) {
                Request.put("badges", null,
                        "access_token", user.get().getUserToken().getAccessToken());
            } else {
                Request.put("badges", null,
                        "access_token", user.get().getUserToken().getAccessToken(),
                        "username", username);
            }
        } catch (HttpClientErrorException e){
            response.setStatus(e.getStatusCode().value());
            return e.getResponseBodyAsString();
        }

        return null;
    }

    @DeleteMapping
    public Object removeBadge(
            HttpServletResponse response,
            @CookieValue("session") String session,
            @RequestParam(value = "username", required = false) String username
    ){
        Optional<User> user = userRepository.findBySession(session);
        if (!user.isPresent()) return new ErrorMessage("invalid_token", "your session expired");
        try {
            if (username == null) {
                Request.delete("badges", null,
                        "access_token", user.get().getUserToken().getAccessToken());
            } else {
                Request.delete("badges", null,
                        "access_token", user.get().getUserToken().getAccessToken(),
                        "username", username);
            }
        } catch (HttpClientErrorException e){
            response.setStatus(e.getStatusCode().value());
            return e.getResponseBodyAsString();
        }

        return null;
    }
}

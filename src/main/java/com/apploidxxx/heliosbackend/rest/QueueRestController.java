package com.apploidxxx.heliosbackend.rest;


import com.apploidxxx.heliosbackend.data.repository.UserRepository;
import com.apploidxxx.heliosbackend.rest.exceptions.UserNotFoundException;
import com.apploidxxx.heliosbackend.rest.model.Queue;
import com.apploidxxx.heliosbackend.rest.util.UserManager;
import com.apploidxxx.heliosbackend.rest.util.request.Request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Arthur Kupriyanov
 */
@Slf4j
@RestController
@RequestMapping("/api/queue")
public class QueueRestController {
    private final UserRepository userRepository;

    public QueueRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(produces = "application/json")
    public Object get(
            HttpServletResponse response,
            @CookieValue("session") String session,
            @RequestParam("queue_name") String queueName
    ) {
        try {
            new UserManager(userRepository).getUser(session);
        } catch (UserNotFoundException e) {
            return e.wrapResponse(response);
        }
        ResponseEntity<Queue> queueResponseEntity;
        try {
            queueResponseEntity = new Request().get("queue", Queue.class,
                    "queue_name", queueName);
        } catch (HttpStatusCodeException e) {
            response.setStatus(e.getStatusCode().value());
            return e.getResponseBodyAsString();
        }
        response.setStatus(queueResponseEntity.getStatusCode().value());
        return queueResponseEntity.getBody();
    }

    @PutMapping( produces = "application/json")
    public Object put(
            HttpServletResponse response,
            @CookieValue("session") String session,
            @RequestParam(value = "queue_name") String queueName,
            @RequestParam(value = "password", defaultValue = "") String password
    ) {

        try {
            Request.put("queue", null,
                    "access_token", new UserManager(userRepository).getUser(session).getUserToken().getAccessToken(),
                    "queue_name", queueName,
                    "password", password);
        } catch (UserNotFoundException e) {
            return e.wrapResponse(response);
        } catch (HttpStatusCodeException e) {
            response.setStatus(e.getStatusCode().value());
            return e.getResponseBodyAsString();
        }
        return null;
    }

    @PostMapping(produces = "application/json")
    public Object post(
            HttpServletResponse response,
            @CookieValue("session") String session,

            @RequestParam("queue_name") String queueName,
            @RequestParam("fullname") String fullname,
            @RequestParam(value = "generation", defaultValue = "") String generation,
            @RequestParam(value = "password", defaultValue = "") String password
    ) {

        try {
            new Request().post("queue", null,
                    "access_token", new UserManager(userRepository).getUser(session).getUserToken().getAccessToken(),
                    "queue_name", queueName,
                    "password", password,
                    "fullname", fullname,
                    "generation", generation);
        } catch (UserNotFoundException e) {
            return e.wrapResponse(response);
        } catch (HttpStatusCodeException e) {
            log.error(e.getResponseBodyAsString());
            log.error("Error during post queue api ", e);
            response.setStatus(e.getStatusCode().value());
            return e.getResponseBodyAsString();
        }
        return null;
    }

    @DeleteMapping( produces = "application/json")
    public Object delete (
            HttpServletResponse response,
            @CookieValue("session") String session,
            @RequestParam("queue_name") String queueName,
            @RequestParam("target") String target,
            @RequestParam(value = "username", required = false) String username
    ){
        try {

            if (username == null) Request.delete("queue",
                    "access_token", new UserManager(userRepository).getUser(session).getUserToken().getAccessToken(),
                    "queue_name", queueName,
                    "target", target);

            else Request.delete("queue",
                    "access_token", new UserManager(userRepository).getUser(session).getUserToken().getAccessToken(),
                    "queue_name", queueName,
                    "target", target,
                    "username", username);

        } catch (UserNotFoundException e) {
            return e.wrapResponse(response);
        } catch (HttpStatusCodeException e) {
            response.setStatus(e.getStatusCode().value());
            return e.getResponseBodyAsString();
        }
        return null;
    }


}

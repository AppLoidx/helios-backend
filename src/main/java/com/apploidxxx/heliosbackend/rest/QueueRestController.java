package com.apploidxxx.heliosbackend.rest;

import com.apploidxxx.heliosbackend.data.repository.UserRepository;
import com.apploidxxx.heliosbackend.rest.exceptions.UserNotFoundException;
import com.apploidxxx.heliosbackend.rest.model.Queue;
import com.apploidxxx.heliosbackend.rest.util.Request;
import com.apploidxxx.heliosbackend.rest.util.UserManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Arthur Kupriyanov
 */
@RestController
@RequestMapping("/api/queue")
public class QueueRestController {
    private final UserRepository userRepository;

    public QueueRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    Object
    get(HttpServletResponse response, @CookieValue("session") String session, @RequestParam("queue_name") String queueName) throws IOException {
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

    @RequestMapping(method = RequestMethod.PUT, produces = "application/json")
    public @ResponseBody
    Object
    put(HttpServletResponse response, @CookieValue("session") String session,
        @RequestParam(value = "queue_name") String queueName,
        @RequestParam(value = "password", defaultValue = "") String password) {

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

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    Object
    post(HttpServletResponse response, @CookieValue("session") String session,
         @RequestParam("queue_name") String queueName,
         @RequestParam("fullname") String fullname,
         @RequestParam(value = "generation", defaultValue = "") String genaration,
         @RequestParam(value = "password", defaultValue = "") String password) {

        try {
            new Request().post("queue", null,
                    "access_token", new UserManager(userRepository).getUser(session).getUserToken().getAccessToken(),
                    "queue_name", queueName,
                    "password", password,
                    "fullname", fullname,
                    "generation", genaration);
        } catch (UserNotFoundException e) {
            return e.wrapResponse(response);
        } catch (HttpStatusCodeException e) {
            response.setStatus(e.getStatusCode().value());
            return e.getResponseBodyAsString();
        }
        return null;
    }


}

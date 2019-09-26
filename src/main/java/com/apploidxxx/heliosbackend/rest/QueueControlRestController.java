package com.apploidxxx.heliosbackend.rest;

import com.apploidxxx.heliosbackend.data.entity.User;
import com.apploidxxx.heliosbackend.data.repository.UserRepository;
import com.apploidxxx.heliosbackend.rest.exceptions.UserNotFoundException;
import com.apploidxxx.heliosbackend.rest.model.ErrorMessage;
import com.apploidxxx.heliosbackend.rest.util.UserManager;
import com.apploidxxx.heliosbackend.rest.util.request.Request;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Arthur Kupriyanov
 */
@RestController@RequestMapping("/api/queue/{id}")
public class QueueControlRestController {
    private final UserRepository userRepository;

    public QueueControlRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PutMapping(produces = "application/json")
    public @ResponseBody Object putInstruction(@PathVariable("id") String queueName,
                                               @RequestParam("action") String actionType,
                                               @RequestParam(value = "type", defaultValue = "") String type,
                                               @RequestParam(value = "admin", defaultValue = "") String admin,
                                               @CookieValue("session") String session,
                                               HttpServletResponse response){
        User user;
        try {
            user = new UserManager(userRepository).getUser(session);
        } catch (UserNotFoundException e) {
            return e.wrapResponse(response);
        }
        try {
            Request.put("queue/" + queueName, null,
                    "action", actionType,
                    "type", type,
                    "admin", admin,
                    "access_token", user.getUserToken().getAccessToken());
            response.setStatus(200);
            return null;
        } catch (HttpStatusCodeException e){
            response.setStatus(e.getStatusCode().value());
            return new ErrorMessage(e.getMessage(), e.getResponseBodyAsString());
        }
    }
}

package com.apploidxxx.heliosbackend.rest;

import com.apploidxxx.heliosbackend.data.entity.User;
import com.apploidxxx.heliosbackend.data.repository.UserRepository;
import com.apploidxxx.heliosbackend.rest.exceptions.UserNotFoundException;
import com.apploidxxx.heliosbackend.rest.model.ErrorMessage;
import com.apploidxxx.heliosbackend.rest.util.UserManager;
import com.apploidxxx.heliosbackend.rest.util.request.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Arthur Kupriyanov
 */
@RestController
@RequestMapping("/api/swap")
public class SwapRestController {
    private final UserRepository userRepository;
    public SwapRestController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping(produces = "application/json")
    public Object post(
            HttpServletResponse response,
            @CookieValue("session") String session,

            @RequestParam("target") String target,
            @RequestParam("queue_name") String queueName
    ){

        User user;
        try {
            user = new UserManager(userRepository).getUser(session);
        } catch (UserNotFoundException e) {
            return e.wrapResponse(response);
        }
        ResponseEntity<ErrorMessage> resEnt;
        try {
            resEnt = new Request().post("swap", ErrorMessage.class,
                    "access_token", user.getUserToken().getAccessToken(),
                    "target", target,
                    "queue_name", queueName);
        } catch (HttpClientErrorException e){
            response.setStatus(e.getStatusCode().value());
            return e.getResponseBodyAsString();
        }

        response.setStatus(resEnt.getStatusCode().value());

        if (resEnt.getStatusCode().is2xxSuccessful()) return null;
        else return resEnt.getBody();

    }
}

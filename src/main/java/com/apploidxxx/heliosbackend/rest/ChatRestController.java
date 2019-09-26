package com.apploidxxx.heliosbackend.rest;

import com.apploidxxx.heliosbackend.data.entity.User;
import com.apploidxxx.heliosbackend.data.repository.UserRepository;
import com.apploidxxx.heliosbackend.rest.exceptions.UserNotFoundException;
import com.apploidxxx.heliosbackend.rest.util.UserManager;
import com.apploidxxx.heliosbackend.rest.util.request.Request;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Arthur Kupriyanov
 */
@RestController@RequestMapping("/api/chat/{queue_name}")
public class ChatRestController {
    private final UserRepository userRepository;
    public ChatRestController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping(produces = "application/json")
    public @ResponseBody Object get(
            HttpServletResponse response,
            @PathVariable("queue_name") String queueName,
            @RequestParam("last_msg_id") String lastMsgId
    ){
        try {

            return new Request().get("chat/" + queueName, String.class, "last_msg_id", lastMsgId);
        } catch (HttpStatusCodeException e){
            response.setStatus(e.getStatusCode().value());
            return null;
        }
    }

    @PutMapping
    public @ResponseBody Object put(
            HttpServletResponse response,
            @RequestParam("message") String message,
            @PathVariable("queue_name") String queueName,
            @CookieValue("session") String session
    ){

        User user;
        try {
            user = new UserManager(userRepository).getUser(session);
        } catch (UserNotFoundException e) {
            e.wrapResponse(response);
            return null;
        }
        try {

            Request.put("chat/" + queueName, Object.class, "message", message, "access_token", user.getUserToken().getAccessToken());
        } catch (HttpStatusCodeException e){
            response.setStatus(e.getStatusCode().value());

        }
        return null;

    }
}


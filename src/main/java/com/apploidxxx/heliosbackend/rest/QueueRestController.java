package com.apploidxxx.heliosbackend.rest;


import com.apploidxxx.heliosbackend.data.entity.User;
import com.apploidxxx.heliosbackend.data.entity.access.repository.UserRepository;
import com.apploidxxx.heliosbackend.rest.model.Queue;
import com.apploidxxx.heliosbackend.rest.util.UserManager;
import com.apploidxxx.heliosbackend.rest.util.request.Request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        new UserManager(userRepository).getUser(session);

        ResponseEntity<Queue> queueResponseEntity;
        queueResponseEntity = new Request().get("queue", Queue.class,
                "queue_name", queueName);
        response.setStatus(queueResponseEntity.getStatusCode().value());
        return queueResponseEntity.getBody();
    }

    @PutMapping(produces = "application/json")
    public Object put(
            @CookieValue("session") String session,

            @RequestParam(value = "queue_name") String queueName,
            @RequestParam(value = "password", defaultValue = "") String password
    ) {

        Request.put("queue", null,
                "access_token", new UserManager(userRepository).getUser(session).getUserToken().getAccessToken(),
                "queue_name", queueName,
                "password", password);

        return null;
    }

    @PostMapping(produces = "application/json")
    public Object post(
            @CookieValue("session") String session,

            @RequestParam("queue_name") String queueName,
            @RequestParam("fullname") String fullname,
            @RequestParam(value = "generation", defaultValue = "") String generation,
            @RequestParam(value = "password", defaultValue = "") String password
    ) {
        new Request().post("queue", null,
                "access_token", new UserManager(userRepository).getUser(session).getUserToken().getAccessToken(),
                "queue_name", queueName,
                "password", password,
                "fullname", fullname,
                "generation", generation);

        return null;
    }

    @DeleteMapping(produces = "application/json")
    public Object delete(
            @CookieValue("session") String session,

            @RequestParam("queue_name") String queueName,
            @RequestParam("target") String target,
            @RequestParam(value = "username", required = false) String username
    ) {
        User user = new UserManager(userRepository).getUser(session);
        sendDeleteRequest(user, queueName, target, username);
        return null;
    }


    private void sendDeleteRequest(User user, String queueName, String target, String username) {
        Request.delete("queue",
                "access_token", user.getUserToken().getAccessToken(),
                "queue_name", queueName,
                "target", target,
                "username", username);
    }


}

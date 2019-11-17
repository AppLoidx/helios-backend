package com.apploidxxx.heliosbackend.rest;

import com.apploidxxx.heliosbackend.data.entity.User;
import com.apploidxxx.heliosbackend.rest.model.Queue;
import com.apploidxxx.heliosbackend.rest.util.UserManager;
import com.apploidxxx.heliosbackend.rest.util.request.Request;
import org.springframework.web.bind.annotation.*;

/**
 * @author Arthur Kupriyanov
 */
@RestController
@RequestMapping("/api/queue/{id}")
public class QueueControlRestController {
    private final UserManager userManager;

    public QueueControlRestController(UserManager userManager) {
        this.userManager = userManager;
    }

    @GetMapping(produces = "application/json")
    public Object nextUserEventRequest(
            @PathVariable("id") String queueName,
            @CookieValue("session") String session,

            @RequestParam(value = "username", required = false) String username
    ) {
        User user =userManager.getUser(session);
        return sendNextUserAndGetNewFormattedQueueRequest(queueName, user, username);
    }

    private Queue sendNextUserAndGetNewFormattedQueueRequest(String queueName, User user, String username) {
        return new Request()
                .get("queue/" + queueName, Queue.class,
                        "access_token", user.getUserToken().getAccessToken(),
                        "username", username).getBody();
    }

    @PutMapping(produces = "application/json")
    public Object putInstruction(
            @PathVariable("id") String queueName,
            @CookieValue("session") String session,

            @RequestParam("action") String actionType,
            @RequestParam(value = "type", defaultValue = "") String type,
            @RequestParam(value = "admin", defaultValue = "") String admin
    ) {
        User user = userManager.getUser(session);
        sendNewQueueSettingRequest(queueName, actionType, type, admin, user);

        return null;
    }

    private void sendNewQueueSettingRequest(String queueName, String actionType, String type, String admin, User user) {
        Request.put("queue/" + queueName, null,
                "action", actionType,
                "type", type,
                "admin", admin,
                "access_token", user.getUserToken().getAccessToken());
    }
}

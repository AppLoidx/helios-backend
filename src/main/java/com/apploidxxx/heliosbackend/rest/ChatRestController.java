package com.apploidxxx.heliosbackend.rest;

import com.apploidxxx.heliosbackend.data.entity.User;
import com.apploidxxx.heliosbackend.rest.util.UserManager;
import com.apploidxxx.heliosbackend.rest.util.request.Request;
import org.springframework.web.bind.annotation.*;

/**
 * @author Arthur Kupriyanov
 */
@RestController
@RequestMapping("/api/chat/{queue_name}")
public class ChatRestController {
    private final UserManager userManager;

    public ChatRestController(UserManager userManager) {

        this.userManager = userManager;
    }

    @GetMapping(produces = "application/json")
    public Object get(
            @PathVariable("queue_name") String queueName,
            @RequestParam("last_msg_id") String lastMsgId
    ) {
        return new Request().get("chat/" + queueName, String.class, "last_msg_id", lastMsgId);
    }

    @PutMapping
    public Object put(
            @RequestParam("message") String message,
            @PathVariable("queue_name") String queueName,
            @CookieValue("session") String session
    ) {

        User user;
        user = userManager.getUser(session);
        Request.put("chat/" + queueName, Object.class, "message", message, "access_token", user.getUserToken().getAccessToken());

        return null;

    }
}


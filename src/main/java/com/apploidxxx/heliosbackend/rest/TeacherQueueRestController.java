package com.apploidxxx.heliosbackend.rest;

import com.apploidxxx.heliosbackend.data.entity.User;
import com.apploidxxx.heliosbackend.rest.util.UserManager;
import com.apploidxxx.heliosbackend.rest.util.request.Request;
import org.springframework.web.bind.annotation.*;

/**
 * @author Arthur Kupriyanov
 */
@RequestMapping("/api/teacher/queue")
@RestController
public class TeacherQueueRestController {
    private final UserManager userManager;

    public TeacherQueueRestController(UserManager userManager) {
        this.userManager = userManager;
    }

    @GetMapping
    public Object getNextAndCurrentUser(
            @CookieValue("session") String session,
            @RequestParam(value = "queue_name") String queueName
    ){
        User user = this.userManager.getUser(session);
        return new Request().get("teacher/queue", Object.class,
                "access_token", user.getUserToken().getAccessToken(),
                "queue_name", queueName).getBody();

    }

    @PutMapping
    public void userPassed(
            @CookieValue("session") String session,
            @RequestParam("queue_name") String queueName,
            @RequestParam("passed_user") String username
    ){
        User user = this.userManager.getUser(session);
        Request.put("teacher/queue", null,
                "access_token", user.getUserToken().getAccessToken(),
                "passed_user", username,
                "queue_name", queueName);
    }

}

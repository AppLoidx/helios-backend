package com.apploidxxx.heliosbackend.rest;

import com.apploidxxx.heliosbackend.data.entity.User;
import com.apploidxxx.heliosbackend.rest.exceptions.UserNotFoundException;
import com.apploidxxx.heliosbackend.rest.util.UserManager;
import com.apploidxxx.heliosbackend.rest.util.request.Request;
import org.springframework.web.bind.annotation.*;

/**
 * @author Arthur Kupriyanov
 */
@RestController
@RequestMapping("/api/timeline")
public class TimelineRestController {
    private final UserManager userManager;

    public TimelineRestController(UserManager userManager) {
        this.userManager = userManager;
    }

    @GetMapping
    public Object getUserTimeline(
            @RequestParam(value = "username", required = false) String username,
            @CookieValue("session") String session
    ) throws UserNotFoundException {
        User user = userManager.getUser(session);
        return new Request().get("timeline", Object.class,
                "access_token", user.getUserToken().getAccessToken()).getBody();

    }
}

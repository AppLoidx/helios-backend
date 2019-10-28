package com.apploidxxx.heliosbackend.rest;

import com.apploidxxx.heliosbackend.data.entity.User;
import com.apploidxxx.heliosbackend.data.entity.access.repository.UserRepository;
import com.apploidxxx.heliosbackend.rest.util.UserManager;
import com.apploidxxx.heliosbackend.rest.util.request.Request;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Arthur Kupriyanov
 */
@RestController
@RequestMapping("/api/badges")
public class BadgesRestController {

    private final UserRepository userRepository;

    public BadgesRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public Object getBadges(
            HttpServletResponse response,
            @CookieValue("session") String session,
            @RequestParam(value = "username", required = false) String username
    ) {
        User user = new UserManager(userRepository).getUser(session);

        return new Request().get("badges", String.class,
                "access_token", user.getUserToken().getAccessToken(),
                "username", username);

    }

    @PutMapping
    public Object putBadge(
            @CookieValue("session") String session,
            @RequestParam(value = "username", required = false) String username
    ) {
        User user = new UserManager(userRepository).getUser(session);

        Request.put("badges", null,
                "access_token", user.getUserToken().getAccessToken(),
                "username", username);

        return null;
    }

    @DeleteMapping
    public Object removeBadge(
            @CookieValue("session") String session,
            @RequestParam(value = "username", required = false) String username
    ) {
        User user = new UserManager(userRepository).getUser(session);

        Request.delete("badges", null,
                "access_token", user.getUserToken().getAccessToken(),
                "username", username);


        return null;
    }
}

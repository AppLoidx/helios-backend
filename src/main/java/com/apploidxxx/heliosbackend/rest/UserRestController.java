package com.apploidxxx.heliosbackend.rest;

import com.apploidxxx.heliosbackend.data.entity.User;
import com.apploidxxx.heliosbackend.rest.exceptions.UserNotFoundException;
import com.apploidxxx.heliosbackend.rest.model.UserModel;
import com.apploidxxx.heliosbackend.rest.util.UserManager;
import com.apploidxxx.heliosbackend.rest.util.request.Request;
import org.springframework.web.bind.annotation.*;

/**
 * @author Arthur Kupriyanov
 */
@RestController
@RequestMapping("/api/user")
public class UserRestController {
    private final UserManager userManager;

    public UserRestController(UserManager userManager) {
        this.userManager = userManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Object getUser(
            @CookieValue("session") String session,
            @RequestParam(value = "username", required = false) String username
    ) throws UserNotFoundException {

        User user = userManager.getUser(session);

        // todo: refactor this fcking shit

        if (username == null) {
            return new Request().get("user", UserModel.class,
                    "access_token", user.getUserToken().getAccessToken()).getBody();
        } else
            return new Request().get("user", UserModel.class,
                    "access_token", user.getUserToken().getAccessToken(),
                    "username", username).getBody();

    }
}

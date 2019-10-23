package com.apploidxxx.heliosbackend.rest;

import com.apploidxxx.heliosbackend.data.entity.User;
import com.apploidxxx.heliosbackend.data.repository.UserRepository;
import com.apploidxxx.heliosbackend.rest.exceptions.UserNotFoundException;
import com.apploidxxx.heliosbackend.rest.model.UserModel;
import com.apploidxxx.heliosbackend.rest.util.UserManager;
import com.apploidxxx.heliosbackend.rest.util.request.Request;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Arthur Kupriyanov
 */
@RestController
@RequestMapping("/api/user")
public class UserRestController {
    private final UserRepository userRepository;

    public UserRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Object getUser(
            HttpServletResponse response,
            @CookieValue("session") String session,
            @RequestParam(value = "username", required = false) String username
    ) {

        User user;
        try {
            user = new UserManager(userRepository).getUser(session);
        } catch (UserNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return e.getErrorMessage();
        }

        // TODO: refactor this thing

        try {
            if (username == null) {
                return new Request().get("user", UserModel.class,
                        "access_token", user.getUserToken().getAccessToken()).getBody();
            }
            else
                return new Request().get("user", UserModel.class,
                        "access_token", user.getUserToken().getAccessToken(),
                        "username", username).getBody();
        } catch (HttpStatusCodeException e){
            response.setStatus(e.getStatusCode().value());
            return null;
        }
    }
}

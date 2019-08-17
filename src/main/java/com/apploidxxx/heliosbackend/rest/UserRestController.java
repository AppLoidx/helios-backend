package com.apploidxxx.heliosbackend.rest;

import com.apploidxxx.heliosbackend.data.entity.User;
import com.apploidxxx.heliosbackend.data.repository.UserRepository;
import com.apploidxxx.heliosbackend.rest.exceptions.UserNotFoundException;
import com.apploidxxx.heliosbackend.rest.model.UserModel;
import com.apploidxxx.heliosbackend.rest.util.Request;
import com.apploidxxx.heliosbackend.rest.util.UserManager;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
    public Object getUser(@CookieValue("session") String session, HttpServletResponse response) {
        User user;
        try {
            user = new UserManager(userRepository).getUser(session);
        } catch (UserNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return e.getErrorMessage();
        }
        try {
            return new Request().get("user", UserModel.class, "access_token", user.getUserToken().getAccessToken());
        } catch (HttpStatusCodeException e){
            response.setStatus(e.getStatusCode().value());
            return null;
        }
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String createUser() {
        return "200";
    }
}

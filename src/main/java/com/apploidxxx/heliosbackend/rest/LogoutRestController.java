package com.apploidxxx.heliosbackend.rest;

import com.apploidxxx.heliosbackend.data.repository.UserRepository;
import com.apploidxxx.heliosbackend.rest.exceptions.UserNotFoundException;
import com.apploidxxx.heliosbackend.rest.util.UserManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Arthur Kupriyanov
 */
@RestController
@RequestMapping("api/logout")
public class LogoutRestController {
    private final UserRepository userRepository;

    public LogoutRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public @ResponseBody
    Object logout(@CookieValue("session") String session,
                  HttpServletResponse response) {

        try {
            new UserManager(userRepository).getUser(session);
        } catch (UserNotFoundException e) {
            return e.wrapResponse(response);
        }
        response.setStatus(200);
        Cookie c = new Cookie("session", null);
        c.setMaxAge(0);
        response.addCookie(c);

        try {
            response.sendRedirect("/external/login.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
